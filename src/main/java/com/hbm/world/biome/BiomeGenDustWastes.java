package com.hbm.world.biome;

import com.hbm.config.WorldConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BiomeGenDustWastes extends Biome {

	public static final Biome dustWastes = new BiomeGenDustWastes(new Biome.BiomeProperties("Dust Wastes").setRainDisabled());

	public static void initDictionary() {
		BiomeDictionary.addTypes(dustWastes, BiomeDictionary.Type.DRY, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND);
	}

	public BiomeGenDustWastes(Biome.BiomeProperties properties) {
		super(properties);
		this.spawnableCreatureList.clear();
		this.spawnableWaterCreatureList.clear();
		this.spawnableCaveCreatureList.clear();
	}

	/** Extinction: no mobs of any kind spawn inside the crater. */
	@Override
	public List<Biome.SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
		return Collections.emptyList();
	}

	@Override
	public int getWaterColorMultiplier() {
		if (!WorldConfig.enableDustWastesBiome)
			return super.getWaterColorMultiplier();
		return 0x505020;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getGrassColorAtPos(BlockPos pos) {
		if (!WorldConfig.enableDustWastesBiome)
			return super.getGrassColorAtPos(pos);
		double noise = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.225D, pos.getZ() * 0.225D);
		return noise < -0.1D ? 0x404040 : 0x303030;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getFoliageColorAtPos(BlockPos pos) {
		if (!WorldConfig.enableDustWastesBiome)
			return super.getFoliageColorAtPos(pos);
		return 0x6A7039;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getSkyColorByTemp(float temp) {
		if (!WorldConfig.enableDustWastesBiome)
			return super.getSkyColorByTemp(temp);
		return 0x424A42;
	}

	// ==== Fog blending, kept together with the darkened sky above ====
	private static boolean fogInit = false;
	private static int fogX;
	private static int fogZ;
	private static Vec3d fogRGBMultiplier;
	private static boolean doesBiomeApply = false;
	private static long fogTimer = 0;

	@SideOnly(Side.CLIENT)
	@SubscribeEvent(priority = EventPriority.LOWEST)
	public static void tintFog(EntityViewRenderEvent.FogColors event) {
		if (!WorldConfig.enableDustWastesBiome) return;
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = mc.player;
		if (player == null) return;
		Entity view = mc.getRenderViewEntity();
		if (view == null) return;
		IBlockState viewState = ActiveRenderInfo.getBlockStateAtEntityViewpoint(view.world, view, (float) event.getRenderPartialTicks());
		if (viewState.getMaterial().isLiquid()) return;
		Vec3d color = getFogBlendColor(player.world, (int) Math.floor(player.posX), (int) Math.floor(player.posZ), event.getRed(), event.getGreen(), event.getBlue(), event.getRenderPartialTicks());
		if (color != null) {
			event.setRed((float) color.x);
			event.setGreen((float) color.y);
			event.setBlue((float) color.z);
		}
	}

	@SideOnly(Side.CLIENT)
	private static Vec3d getFogBlendColor(World world, int playerX, int playerZ, float red, float green, float blue, double partialTicks) {
		long millis = System.currentTimeMillis() - fogTimer;
		if (playerX == fogX && playerZ == fogZ && fogInit && millis < 3000) return fogRGBMultiplier;
		fogInit = true;
		fogTimer = System.currentTimeMillis();
		GameSettings settings = Minecraft.getMinecraft().gameSettings;
		int[] ranges = ForgeModContainer.blendRanges;
		int distance = 0;
		if (settings.fancyGraphics && settings.renderDistanceChunks >= 0) {
			distance = ranges[Math.min(settings.renderDistanceChunks, ranges.length - 1)];
		}
		float r = 0F, g = 0F, b = 0F;
		int divider = 0;
		doesBiomeApply = false;
		for (int x = -distance; x <= distance; x++) {
			for (int z = -distance; z <= distance; z++) {
				BlockPos pos = new BlockPos(playerX + x, 150, playerZ + z);
				Biome biome = world.getBiome(pos);
				Vec3d color = getBiomeFogColors(world, biome, red, green, blue, pos, partialTicks);
				r += (float) color.x;
				g += (float) color.y;
				b += (float) color.z;
				divider++;
			}
		}
		fogX = playerX;
		fogZ = playerZ;
		if (doesBiomeApply) {
			fogRGBMultiplier = new Vec3d(r / divider, g / divider, b / divider);
		} else {
			fogRGBMultiplier = null;
		}
		return fogRGBMultiplier;
	}

	@SideOnly(Side.CLIENT)
	private static Vec3d getBiomeFogColors(World world, Biome biome, float r, float g, float b, BlockPos pos, double partialTicks) {
		if (biome instanceof BiomeGenDustWastes) {
			int color = biome.getSkyColorByTemp(biome.getTemperature(pos));
			r = ((color & 0xff0000) >> 16) / 255F;
			g = ((color & 0x00ff00) >> 8) / 255F;
			b = (color & 0x0000ff) / 255F;
			float celestialAngle = world.getCelestialAngle((float) partialTicks);
			float skyBrightness = MathHelper.clamp(MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F, 0F, 1F);
			r *= skyBrightness;
			g *= skyBrightness;
			b *= skyBrightness;
			doesBiomeApply = true;
		}
		return new Vec3d(r, g, b);
	}

	// ==== Entities burn in the crater's eternal fire ====
	@SubscribeEvent
	public static void dustWastesEntityFire(TickEvent.WorldTickEvent event) {
		if (!WorldConfig.enableDustWastesBiome) return;
		if (event.world == null || event.world.isRemote || event.phase != TickEvent.Phase.START) return;
		if (event.world.loadedEntityList.isEmpty()) return;
		for (Object o : new ArrayList<Object>(event.world.loadedEntityList)) {
			if (!(o instanceof EntityLivingBase)) continue;
			EntityLivingBase entity = (EntityLivingBase) o;
			BlockPos pos = new BlockPos(entity.posX, entity.getEntityBoundingBox().maxY, entity.posZ);
			if (event.world.getBiome(pos) instanceof BiomeGenDustWastes && event.world.getLightFor(EnumSkyBlock.SKY, pos) > 7 && !entity.isImmuneToFire()) {
				entity.setFire(5);
				entity.attackEntityFrom(DamageSource.ON_FIRE, 2);
			}
		}
	}
}