package com.hbm.dynamic_texture;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.IntPredicate;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import com.hbm.render.icon.TextureAtlasSpriteSourceTransform;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public final class DynamicTextureData {

	public ResourceLocation baseTexture;
	public final List<Layer> layers = new ArrayList<>();
	public final List<Tint> blockTints = new ArrayList<>();
	public final List<Tint> itemTints = new ArrayList<>();
	public final List<ItemModelBinding> itemModels = new ArrayList<>();
	public final List<TextureRegion> regions = new ArrayList<>();
	public final Map<String, TintParams> variants = new LinkedHashMap<>();

	public DynamicTextureData base(ResourceLocation texture) {
		this.baseTexture = texture;
		return this;
	}

	public DynamicTextureData layer(Layer layer) {
		this.layers.add(layer);
		return this;
	}

	public DynamicTextureData blockTint(Tint tint) {
		this.blockTints.add(tint);
		return this;
	}

	public DynamicTextureData itemTint(Tint tint) {
		this.itemTints.add(tint);
		return this;
	}

	public DynamicTextureData itemModel(int from, int to, ModelResourceLocation model) {
		this.itemModels.add(new ItemModelBinding(null, from, to, model));
		return this;
	}

	public DynamicTextureData region(int x, int y, int w, int h, TintMode mode) {
		this.regions.add(new TextureRegion(x, y, w, h, mode));
		return this;
	}

	public DynamicTextureData variant(String name, float hue, float saturation) {
		this.variants.put(name, new TintParams(hue, saturation, 0));
		return this;
	}

	public DynamicTextureData variant(String name, int color) {
		this.variants.put(name, new TintParams(0, 0, color));
		return this;
	}

	public static int[] ratioCurve(int meta0, int meta6) {
		int r = ((meta6 >> 16) & 0xFF) * 255 / Math.max(1, (meta0 >> 16) & 0xFF);
		int g = ((meta6 >> 8) & 0xFF) * 255 / Math.max(1, (meta0 >> 8) & 0xFF);
		int b = (meta6 & 0xFF) * 255 / Math.max(1, meta0 & 0xFF);
		return directCurve(0xFFFFFF, (r << 16) | (g << 8) | b);
	}

	public static int[] directCurve(int meta0, int meta6) {
		int[] curve = new int[7];
		for(int m = 0; m < 7; m++) curve[m] = lerpColor(meta0, meta6, m / 6.0);
		return curve;
	}

	public static int[] of(int... values) {
		if(values.length != 7) throw new IllegalArgumentException("tint curve needs 7 values");
		return values;
	}

	public static int[] white() {
		return new int[]{0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF};
	}

	public static int mulColor(int a, int b) {
		int r = ((a >> 16) & 0xFF) * ((b >> 16) & 0xFF) / 255;
		int g = ((a >> 8) & 0xFF) * ((b >> 8) & 0xFF) / 255;
		int bl = (a & 0xFF) * (b & 0xFF) / 255;
		return (r << 16) | (g << 8) | bl;
	}

	private static int lerpColor(int a, int b, double t) {
		int r = (int) Math.round(((a >> 16) & 0xFF) + (((b >> 16) & 0xFF) - ((a >> 16) & 0xFF)) * t);
		int g = (int) Math.round(((a >> 8) & 0xFF) + (((b >> 8) & 0xFF) - ((a >> 8) & 0xFF)) * t);
		int bl = (int) Math.round((a & 0xFF) + ((b & 0xFF) - (a & 0xFF)) * t);
		return (r << 16) | (g << 8) | bl;
	}

	public enum Transform {
		GRAYSCALE,
		MULTIPASS,
		HUE_SHIFT,
		MYCELIUM_DIRT,
		MYCELIUM_PARTICLE,
		OVERLAY_PARTICLE;

		public TextureAtlasSpriteSourceTransform create(String name, String[] sources, float[] params) {
			switch(this) {
			case GRAYSCALE: return TextureAtlasSpriteSourceTransform.grayscale(name, sources[0]);
			case MULTIPASS: return TextureAtlasSpriteSourceTransform.multipass(name, sources[0], sources[1]);
			case HUE_SHIFT: return TextureAtlasSpriteSourceTransform.hueShift(name, sources[0], params[0], params[1]);
			case MYCELIUM_DIRT: return TextureAtlasSpriteSourceTransform.myceliumDirt(name, sources[0], sources[1]);
			case MYCELIUM_PARTICLE: return TextureAtlasSpriteSourceTransform.myceliumParticle(name, sources[0], sources[1], (int) params[0]);
			case OVERLAY_PARTICLE: return TextureAtlasSpriteSourceTransform.overlayParticle(name, sources[0], sources[1], (int) params[0], (int) params[1]);
			default: throw new IllegalStateException();
			}
		}
	}

	public enum TintMode {
		HSB,
		MULTIPLY,
		REPLACE
	}

	public static final class Layer {
		public final String namePattern;
		public final Transform transform;
		public final List<String> sources = new ArrayList<>();
		public final List<Float> params = new ArrayList<>();
		public int metaFrom = 0;
		public int metaTo = 0;

		public Layer(String namePattern, Transform transform) {
			this.namePattern = namePattern;
			this.transform = transform;
		}

		public Layer source(String source) {
			this.sources.add(source);
			return this;
		}

		public Layer param(float value) {
			this.params.add(value);
			return this;
		}

		public Layer metaRange(int from, int to) {
			this.metaFrom = from;
			this.metaTo = to;
			return this;
		}

		public String name(int meta) {
			return namePattern.indexOf('%') >= 0 ? String.format(namePattern, meta) : namePattern;
		}

		public String[] sources() {
			return sources.toArray(new String[0]);
		}

		public float[] params() {
			float[] out = new float[params.size()];
			for(int i = 0; i < out.length; i++) out[i] = params.get(i);
			return out;
		}
	}

	public static final class Tint {
		public final int index;
		public int[] curve;
		public int fixedColor = -1;
		public Predicate<IBlockState> stateFilter;
		public ToIntFunction<IBlockState> stateMeta;
		public IntPredicate itemFilter;
		public IntUnaryOperator itemMeta;
		public IntUnaryOperator colorOp;

		public Tint(int index, int[] curve) {
			this.index = index;
			this.curve = curve;
		}

		public static Tint fixed(int index, int color) {
			Tint tint = new Tint(index, white());
			tint.fixedColor = color;
			return tint;
		}

		public Tint filter(Predicate<IBlockState> filter) {
			this.stateFilter = filter;
			return this;
		}

		public Tint meta(ToIntFunction<IBlockState> meta) {
			this.stateMeta = meta;
			return this;
		}

		public Tint itemFilter(IntPredicate filter) {
			this.itemFilter = filter;
			return this;
		}

		public Tint itemMeta(IntUnaryOperator meta) {
			this.itemMeta = meta;
			return this;
		}

		public Tint op(IntUnaryOperator op) {
			this.colorOp = op;
			return this;
		}

		public boolean matchesBlock(IBlockState state, int tintIndex) {
			return (index < 0 || index == tintIndex) && (stateFilter == null || stateFilter.test(state));
		}

		public boolean matchesItem(ItemStack stack, int tintIndex) {
			return (index < 0 || index == tintIndex) && (itemFilter == null || itemFilter.test(stack.getMetadata()));
		}

		public int color(IBlockState state) {
			if(fixedColor >= 0) return fixedColor;
			int meta = stateMeta == null ? state.getBlock().getMetaFromState(state) : stateMeta.applyAsInt(state);
			int color = curve[Math.max(0, Math.min(6, meta))];
			return colorOp == null ? color : colorOp.applyAsInt(color);
		}

		public int color(ItemStack stack) {
			if(fixedColor >= 0) return fixedColor;
			int damage = stack.getMetadata();
			int meta = itemMeta == null ? damage : itemMeta.applyAsInt(damage);
			int color = curve[Math.max(0, Math.min(6, meta))];
			return colorOp == null ? color : colorOp.applyAsInt(color);
		}
	}

	public static final class TextureRegion {
		public final int x, y, width, height;
		public final TintMode mode;

		public TextureRegion(int x, int y, int width, int height, TintMode mode) {
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			this.mode = mode;
		}
	}

	public static final class TintParams {
		public final float hue;
		public final float saturation;
		public final int color;

		public TintParams(float hue, float saturation, int color) {
			this.hue = hue;
			this.saturation = saturation;
			this.color = color;
		}
	}

	public static final class ItemModelBinding {
		public final Item item;
		public final int fromDamage;
		public final int toDamage;
		public final ModelResourceLocation model;

		public ItemModelBinding(Item item, int fromDamage, int toDamage, ModelResourceLocation model) {
			this.item = item;
			this.fromDamage = fromDamage;
			this.toDamage = toDamage;
			this.model = model;
		}
	}
}
