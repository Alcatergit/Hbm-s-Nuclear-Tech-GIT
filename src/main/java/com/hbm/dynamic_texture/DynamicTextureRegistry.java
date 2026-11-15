package com.hbm.dynamic_texture;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.WasteDirt;
import com.hbm.blocks.generic.WasteEarth;
import com.hbm.blocks.generic.WasteLeaves;
import com.hbm.dynamic_texture.DynamicTextureData.ItemModelBinding;
import com.hbm.dynamic_texture.DynamicTextureData.Layer;
import com.hbm.dynamic_texture.DynamicTextureData.TextureRegion;
import com.hbm.dynamic_texture.DynamicTextureData.Tint;
import com.hbm.dynamic_texture.DynamicTextureData.TintMode;
import com.hbm.dynamic_texture.DynamicTextureData.TintParams;
import com.hbm.dynamic_texture.DynamicTextureData.Transform;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public final class DynamicTextureRegistry {

	private static final Map<Block, DynamicTextureData> BLOCK_ENTRIES = new LinkedHashMap<>();
	private static final Map<ResourceLocation, DynamicTextureData> TEXTURE_ENTRIES = new LinkedHashMap<>();
	private static final Map<String, Integer> TEXTURE_CACHE = new HashMap<>();

	static {
		registerBlocks();
		registerExternalTextures();
	}

	private DynamicTextureRegistry() { }

	public static void registerBlock(Block block, DynamicTextureData data) {
		BLOCK_ENTRIES.put(block, data);
	}

	public static void registerTexture(ResourceLocation base, DynamicTextureData data) {
		data.base(base);
		TEXTURE_ENTRIES.put(base, data);
	}

	private static void addMetaBlock(Block block, int[] curve) {
		registerBlock(block, new DynamicTextureData()
				.blockTint(new Tint(-1, curve))
				.itemTint(new Tint(-1, curve)));
	}

	private static void registerBlocks() {
		// sprite-only blocks
		registerBlock(ModBlocks.waste_trinitite, new DynamicTextureData()
				.layer(new Layer("hbm:blocks/contamination/trinitite/trinitite_gen", Transform.MULTIPASS)
						.source("minecraft:blocks/sand").source("hbm:blocks/trinitite_overlay"))
				.blockTint(new Tint(-1, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740)))
				.itemTint(new Tint(-1, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740))));
		registerBlock(ModBlocks.waste_trinitite_red, new DynamicTextureData()
				.layer(new Layer("hbm:blocks/contamination/red_trinitite/trinitite_red_gen", Transform.MULTIPASS)
						.source("minecraft:blocks/red_sand").source("hbm:blocks/trinitite_overlay"))
				.blockTint(new Tint(-1, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740)))
				.itemTint(new Tint(-1, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740))));
		registerBlock(ModBlocks.waste_ice, new DynamicTextureData()
				.layer(new Layer("hbm:blocks/waste_ice_gen", Transform.GRAYSCALE)
						.source("minecraft:blocks/ice")));

		// mushroom sprites
		DynamicTextureData mushrooms = new DynamicTextureData()
				.layer(new Layer("hbm:blocks/mush_block_skin_gen", Transform.HUE_SHIFT)
						.source("minecraft:blocks/mushroom_block_skin_brown").param(85f / 360f).param(1f))
				.layer(new Layer("hbm:blocks/mush_block_stem_gen", Transform.HUE_SHIFT)
						.source("minecraft:blocks/mushroom_block_skin_stem").param(85f / 360f).param(1f))
				.layer(new Layer("hbm:blocks/mush_block_inside_gen", Transform.HUE_SHIFT)
						.source("minecraft:blocks/mushroom_block_inside").param(85f / 360f).param(1f))
				.layer(new Layer("hbm:blocks/mush_block_red_skin_gen", Transform.HUE_SHIFT)
						.source("minecraft:blocks/mushroom_block_skin_red").param(85f / 360f).param(1f));
		registerBlock(ModBlocks.mush_block, mushrooms);

		// simple meta blocks
		addMetaBlock(ModBlocks.waste_gravel, DynamicTextureData.ratioCurve(0x7F7C7B, 0x323231));
		addMetaBlock(ModBlocks.waste_sand, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740));
		addMetaBlock(ModBlocks.waste_sand_red, DynamicTextureData.ratioCurve(0xC5BE90, 0x484740));
		addMetaBlock(ModBlocks.waste_snow_block, DynamicTextureData.ratioCurve(0xE4EEEE, 0x6F7070));
		addMetaBlock(ModBlocks.waste_terracotta, DynamicTextureData.ratioCurve(0xA25426, 0x32251E));
		addMetaBlock(ModBlocks.waste_sandstone, DynamicTextureData.ratioCurve(0xC3BD8E, 0x48473F));
		addMetaBlock(ModBlocks.waste_sandstone_red, DynamicTextureData.ratioCurve(0x954C1B, 0x2D2119));

		// fallout
		registerBlock(ModBlocks.fallout, new DynamicTextureData()
				.blockTint(new Tint(-1, DynamicTextureData.of(0xFFFFFF, 0xEFEFEF, 0xDFDFDF, 0xC4C4C4, 0xB1B1B1, 0x949494, 0x7B7B7B)))
				.itemTint(new Tint(-1, DynamicTextureData.of(0xFFFFFF, 0xEFEFEF, 0xDFDFDF, 0xC4C4C4, 0xB1B1B1, 0x949494, 0x7B7B7B))));
		registerBlock(ModBlocks.block_fallout, new DynamicTextureData()
				.blockTint(Tint.fixed(-1, 0xFFFFFF))
				.itemTint(Tint.fixed(-1, 0xFFFFFF)));

		// waste earth
		int[] grass = DynamicTextureData.directCurve(0x6F6752, 0x303030);
		int[] dirt = DynamicTextureData.ratioCurve(0x866043, 0x2E2723);
		registerBlock(ModBlocks.waste_earth, new DynamicTextureData()
				.blockTint(new Tint(2, grass))
				.blockTint(new Tint(3, grass))
				.blockTint(new Tint(-1, dirt))
				.itemTint(new Tint(2, grass))
				.itemTint(new Tint(3, grass))
				.itemTint(new Tint(-1, dirt)));

		// grass tall
		int[] grassTall = DynamicTextureData.ratioCurve(0x8C7A53, 0x5C5340);
		registerBlock(ModBlocks.waste_grass_tall, new DynamicTextureData()
				.blockTint(new Tint(0, grassTall).op(color -> DynamicTextureData.mulColor(color, 0xFFE59B)))
				.blockTint(new Tint(-1, grassTall))
				.itemTint(new Tint(0, grassTall).op(color -> DynamicTextureData.mulColor(color, 0xFFE59B)))
				.itemTint(new Tint(-1, grassTall)));

		// leaves
		DynamicTextureData leaves = new DynamicTextureData();
		leaves.blockTint(Tint.fixed(-1, 0xE69160).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 0));
		leaves.blockTint(Tint.fixed(-1, 0xB37F5A).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 1));
		leaves.blockTint(Tint.fixed(-1, 0xE6A860).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 2));
		leaves.blockTint(Tint.fixed(-1, 0xE37B65).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 3));
		leaves.blockTint(Tint.fixed(-1, 0xF28D54).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 4));
		leaves.blockTint(Tint.fixed(-1, 0xE6A04E).filter(state -> state.getValue(WasteLeaves.VARIANT).getMetadata() == 5));
		leaves.itemTint(Tint.fixed(-1, 0xE69160).itemFilter(damage -> damage == 0));
		leaves.itemTint(Tint.fixed(-1, 0xB37F5A).itemFilter(damage -> damage == 1));
		leaves.itemTint(Tint.fixed(-1, 0xE6A860).itemFilter(damage -> damage == 2));
		leaves.itemTint(Tint.fixed(-1, 0xE37B65).itemFilter(damage -> damage == 3));
		leaves.itemTint(Tint.fixed(-1, 0xF28D54).itemFilter(damage -> damage == 4));
		leaves.itemTint(Tint.fixed(-1, 0xE6A04E).itemFilter(damage -> damage == 5));
		registerBlock(ModBlocks.waste_leaves, leaves);

		// mycelium
		DynamicTextureData mycelium = new DynamicTextureData()
				.layer(new Layer("hbm:blocks/contamination/mycelium/waste_mycelium_side_gen", Transform.MYCELIUM_DIRT)
						.source("minecraft:blocks/dirt").source("hbm:blocks/contamination/mycelium/waste_mycelium_overlay"))
				.blockTint(Tint.fixed(0, 0xFFFFFF))
				.blockTint(new Tint(-1, dirt))
				.itemTint(Tint.fixed(0, 0xFFFFFF))
				.itemTint(new Tint(-1, dirt));
		for(int m = 0; m <= 6; m++) {
			mycelium.layer(new Layer("hbm:blocks/contamination/mycelium/waste_mycelium_particle_gen_" + m, Transform.MYCELIUM_PARTICLE)
					.source("minecraft:blocks/dirt").source("hbm:blocks/contamination/mycelium/waste_mycelium_overlay")
					.param(dirt[m]).metaRange(m, m));
		}
		registerBlock(ModBlocks.waste_mycelium, mycelium);

		// waste dirt + podzol
		int[] podzol = DynamicTextureData.ratioCurve(0x6F6752, 0x303030);
		DynamicTextureData wasteDirt = new DynamicTextureData()
				.itemModel(0, 0, new ModelResourceLocation("hbm:waste_dirt", "inventory"))
				.itemModel(1, 1, new ModelResourceLocation("hbm:waste_podzol", "inventory"))
				.blockTint(new Tint(2, podzol)
						.filter(state -> state.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL)
						.meta(state -> state.getValue(WasteEarth.META)))
				.blockTint(Tint.fixed(0, 0xFFFFFF)
						.filter(state -> state.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL))
				.blockTint(new Tint(1, dirt)
						.filter(state -> state.getValue(WasteDirt.VARIANT) == WasteDirt.Variant.PODZOL)
						.meta(state -> state.getValue(WasteEarth.META)))
				.blockTint(new Tint(-1, dirt))
				.itemTint(new Tint(2, podzol)
						.itemFilter(damage -> damage == 1)
						.itemMeta(damage -> 0))
				.itemTint(Tint.fixed(0, 0xFFFFFF)
						.itemFilter(damage -> damage == 1))
				.itemTint(new Tint(1, dirt)
						.itemFilter(damage -> damage == 1))
				.itemTint(new Tint(-1, dirt));
		for(int m = 0; m <= 6; m++) {
			wasteDirt.layer(new Layer("hbm:blocks/contamination/podzol/waste_podzol_particle_gen_" + m, Transform.OVERLAY_PARTICLE)
					.source("minecraft:blocks/dirt").source("hbm:blocks/contamination/podzol/waste_podzol_overlay")
					.param(dirt[m]).param(podzol[m]).metaRange(m, m));
		}
		registerBlock(ModBlocks.waste_dirt, wasteDirt);
	}

	private static void registerExternalTextures() {
		registerTexture(ResourceManager.fatman_mininuke_tex, new DynamicTextureData()
				.region(0, 20, 96, 4, TintMode.HSB)
				.variant("NUKE_SAFE", 0.2614F, 0.73F)
				.variant("NUKE_LOW", 0.5401F, 0.73F)
				.variant("NUKE_HIGH", 0.0219F, 0.73F)
				.variant("NUKE_PUMPKIN", 0.0865F, 0.73F));
		registerTexture(ResourceManager.fatman_minimirv_tex, new DynamicTextureData()
				.region(0, 12, 32, 2, TintMode.HSB)
				.variant("MIRV_SAFE", 0.2793F, 0.82F)
				.variant("MIRV_LOW", 0.5575F, 0.82F)
				.variant("MIRV_HIGH", 0.0368F, 0.82F)
				.variant("MIRV_SPECIAL", 0.8621F, 0.82F));

		DynamicTextureData rbmk = new DynamicTextureData()
				.region(16, 30, 16, 2, TintMode.MULTIPLY)
				.region(0, 0, 16, 16, TintMode.MULTIPLY)
				.variant("RED", 0xFF0000)
				.variant("YELLOW", 0xFFD800)
				.variant("GREEN", 0x4CFF00)
				.variant("BLUE", 0x0026FF)
				.variant("PURPLE", 0xB200FF);
		registerTexture(new ResourceLocation(RefStrings.MODID, "textures/blocks/rbmk/rbmk_control.png"), rbmk);
		registerTexture(new ResourceLocation(RefStrings.MODID, "textures/blocks/rbmk/rbmk_control_mod.png"), rbmk);
	}

	@SideOnly(Side.CLIENT)
	public static void registerSprites(TextureStitchEvent.Pre evt) {
		clearTextureCache();
		for(DynamicTextureData data : BLOCK_ENTRIES.values()) {
			for(Layer layer : data.layers) {
				for(int meta = layer.metaFrom; meta <= layer.metaTo; meta++) {
					evt.getMap().setTextureEntry(layer.transform.create(layer.name(meta), layer.sources(), layer.params()));
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerBlockColors(ColorHandlerEvent.Block evt) {
		for(Map.Entry<Block, DynamicTextureData> entry : BLOCK_ENTRIES.entrySet()) {
			if(entry.getValue().blockTints.isEmpty()) continue;
			evt.getBlockColors().registerBlockColorHandler((state, world, pos, tintIndex) -> {
				for(Tint tint : entry.getValue().blockTints) {
					if(tint.matchesBlock(state, tintIndex)) return tint.color(state);
				}
				return 0xFFFFFF;
			}, entry.getKey());
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemColors(ColorHandlerEvent.Item evt) {
		for(Map.Entry<Block, DynamicTextureData> entry : BLOCK_ENTRIES.entrySet()) {
			if(entry.getValue().itemTints.isEmpty()) continue;
			Item item = Item.getItemFromBlock(entry.getKey());
			if(item == null) continue;
			evt.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
				for(Tint tint : entry.getValue().itemTints) {
					if(tint.matchesItem(stack, tintIndex)) return tint.color(stack);
				}
				return 0xFFFFFF;
			}, item);
		}
	}

	@SideOnly(Side.CLIENT)
	public static void registerItemModels() {
		for(Map.Entry<Block, DynamicTextureData> entry : BLOCK_ENTRIES.entrySet()) {
			if(entry.getValue().itemModels.isEmpty()) continue;
			Item item = Item.getItemFromBlock(entry.getKey());
			if(item == null) continue;
			for(ItemModelBinding binding : entry.getValue().itemModels) {
				for(int damage = binding.fromDamage; damage <= binding.toDamage; damage++) {
					net.minecraftforge.client.model.ModelLoader.setCustomModelResourceLocation(item, damage, binding.model);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public static int getTexture(ResourceLocation base, String variant) {
		if(variant == null || "NONE".equals(variant)) return -1;
		DynamicTextureData data = TEXTURE_ENTRIES.get(base);
		if(data == null) return -1;
		TintParams params = data.variants.get(variant);
		if(params == null) return -1;

		String key = base.toString() + "|" + variant;
		Integer cached = TEXTURE_CACHE.get(key);
		if(cached != null) return cached;

		try {
			BufferedImage image = buildTexture(base, data, params);
			if(image == null) return -1;
			DynamicTexture texture = new DynamicTexture(image);
			int gl = texture.getGlTextureId();
			GlStateManager.bindTexture(gl);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
			TEXTURE_CACHE.put(key, gl);
			return gl;
		} catch(Exception ex) {
			FMLClientHandler.instance().trackBrokenTexture(base, ex.getMessage());
			return -1;
		}
	}

	private static BufferedImage buildTexture(ResourceLocation base, DynamicTextureData data, TintParams params) throws IOException {
		try(InputStream stream = Minecraft.getMinecraft().getResourceManager().getResource(base).getInputStream()) {
			BufferedImage source = ImageIO.read(stream);
			if(source == null) return null;
			int width = source.getWidth();
			int height = source.getHeight();
			BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
			image.setRGB(0, 0, width, height, source.getRGB(0, 0, width, height, null, 0, width), 0, width);
			for(TextureRegion region : data.regions) {
				int x1 = Math.min(image.getWidth(), region.x + region.width);
				int y1 = Math.min(image.getHeight(), region.y + region.height);
				for(int y = Math.max(0, region.y); y < y1; y++) {
					for(int x = Math.max(0, region.x); x < x1; x++) {
						image.setRGB(x, y, apply(image.getRGB(x, y), region.mode, params));
					}
				}
			}
			return image;
		}
	}

	private static int apply(int pixel, TintMode mode, TintParams params) {
		int a = (pixel >>> 24) & 0xFF;
		int r = (pixel >> 16) & 0xFF;
		int g = (pixel >> 8) & 0xFF;
		int b = pixel & 0xFF;
		if(mode == TintMode.REPLACE) {
			r = (params.color >> 16) & 0xFF;
			g = (params.color >> 8) & 0xFF;
			b = params.color & 0xFF;
		} else if(mode == TintMode.MULTIPLY) {
			r = r * ((params.color >> 16) & 0xFF) / 255;
			g = g * ((params.color >> 8) & 0xFF) / 255;
			b = b * (params.color & 0xFF) / 255;
		} else {
			float[] hsb = new float[3];
			Color.RGBtoHSB(r, g, b, hsb);
			hsb[0] = params.hue;
			hsb[1] = Math.max(hsb[1], params.saturation);
			int rgb = Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]);
			r = (rgb >> 16) & 0xFF;
			g = (rgb >> 8) & 0xFF;
			b = rgb & 0xFF;
		}
		return (a << 24) | (r << 16) | (g << 8) | b;
	}

	private static void clearTextureCache() {
		for(Integer gl : TEXTURE_CACHE.values()) {
			if(gl != null && gl >= 0) TextureUtil.deleteTexture(gl);
		}
		TEXTURE_CACHE.clear();
	}
}