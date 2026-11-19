package com.hbm.hazard.type;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import com.hbm.config.RadiationConfig;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import com.hbm.util.I18nUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class HazardTypeRadiation extends HazardTypeBase {

	private static final Map<World, ChunkSources> chunkSources = new WeakHashMap<>();

	private static final class ChunkSources {
		private long lastTick = Long.MIN_VALUE;
		private final Map<Long, Map<Long, Float>> chunks = new HashMap<>();
	}

	@Override
	public void onUpdate(EntityLivingBase target, float level, ItemStack stack) {
		if(ContaminationUtil.isRadImmune(target)) return;
		boolean reacher = false;

        if(target instanceof EntityPlayer player && !GeneralConfig.enable528)
            reacher = Library.checkForHeld(player, ModItems.reacher) || Library.checkForBauble(player, ModItems.reacher);
			
		if(level > 0) {
			float rad = level * stack.getCount() / 20F;
			
			if(reacher)
				rad = (float) Math.min(Math.sqrt(rad), rad); //to prevent radiation from going up when being <1
			
			ContaminationUtil.contaminate(target, HazardType.RADIATION, ContaminationType.CREATIVE, rad);
		}
	}

	@Override
	public void updateEntity(EntityItem item, float level) {
		if(!RadiationConfig.enableItemRadiation || item.world.isRemote || level <= 0)
			return;

		ItemStack stack = item.getItem();
		float rads = level * stack.getCount();
		float radPerTick = rads / 20F;
		double range = Math.min(Math.sqrt(rads), 1000);
		ContaminationUtil.radiate(item.world, item.posX, item.posY, item.posZ, range, radPerTick, 0, 1.0D, null);
		addChunkSource(item.world, new BlockPos(item.posX, item.posY, item.posZ), rads);
	}

	private static void addChunkSource(World world, BlockPos pos, float rads) {
		long now = world.getTotalWorldTime();
		ChunkSources sources = chunkSources.get(world);
		if(sources == null) {
			sources = new ChunkSources();
			chunkSources.put(world, sources);
		}
		if(sources.lastTick != now) {
			incrementChunkRad(world, sources);
			sources.lastTick = now;
			sources.chunks.clear();
		}
		Map<Long, Float> chunk = sources.chunks.computeIfAbsent(ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4), k -> new HashMap<>());
		chunk.merge(pos.toLong(), rads, Float::sum);
	}

	private static void incrementChunkRad(World world, ChunkSources sources) {
		float envFactor = 0.00004F;
		float envThreshold = RadiationConfig.neutronActivationThreshold;

		for(Map<Long, Float> chunk : sources.chunks.values()) {
			float chunkRads = 0F;
			for(float rad : chunk.values()) {
				chunkRads += rad;
			}
			float envRadPerTick = chunkRads * envFactor - envThreshold * envFactor;
			if(envRadPerTick <= 0F) {
				continue;
			}
			for(Map.Entry<Long, Float> e : chunk.entrySet()) {
				RadiationSavedData.incrementRad(world, BlockPos.fromLong(e.getKey()), envRadPerTick * e.getValue() / chunkRads, chunkRads);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addHazardInformation(EntityPlayer player, List<String> list, float level, ItemStack stack, List<HazardModifier> modifiers) {
		
		level = HazardModifier.evalAllModifiers(stack, player, level, modifiers);
		if(level == 0) return;
		list.add("§a[" + I18nUtil.resolveKey("trait.radioactive") + "]");
		list.add(" §e" + (Library.roundFloat(getNewValue(level), 3)+ getSuffix(level) + " " + I18nUtil.resolveKey("desc.rads")));
			
		if(stack.getCount() > 1) {
			float stackRad = level * stack.getCount();
			list.add(" §e" + I18nUtil.resolveKey("desc.stack")+" " + Library.roundFloat(getNewValue(stackRad), 3) + getSuffix(stackRad) + " " + I18nUtil.resolveKey("desc.rads"));
		}
	}


	public static float getNewValue(float radiation){
		if(radiation < 1000000){
			return radiation;
		} else if(radiation < 1000000000){
			return radiation * 0.000001F;
		} else{
			return radiation * 0.000000001F;
		}
	}

	public static String getSuffix(float radiation){
		if(radiation < 1_000_000){
			return "";
		} else if(radiation < 1_000_000_000){
			return I18nUtil.resolveKey("desc.mil");
		} else{
			return I18nUtil.resolveKey("desc.bil");
		}
	}
}
