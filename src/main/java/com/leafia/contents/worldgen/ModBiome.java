package com.leafia.contents.worldgen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;

import javax.annotation.Nullable;

public abstract class ModBiome extends Biome {
	public Runnable postInit = ()->{};

	/**
	 * Override this method to hardcode the way this biome is chosen.
	 * Returning <tt>layers</tt> unmodified is basically cancelling the override,
	 * as what you're doing is basically just passing back what you're given with.
	 * <p>Alternatively, you can return <b>null</b> to do the exact same thing, except
	 * if you have any modification already done on <tt>layers</tt>, those changes will be discarded in this option.
	 * @param seed
	 * @param layers The list of all layers used in biome generation.<br>
	 * <a href="https://www.youtube.com/watch?v=YyVAaJqYAfE">Watch a video</a> if you're not sure what a <tt>layer</tt> is in the first place.
	 * @param shaperIndex The index of layer resposible for terrain height calculations on the list.<br>
	 * Should contain numeric biome IDs of a biome and will use its properties to calculate actual terrain heights.<br>
	 * Numeric biome IDs can be obtained by using <pre>{@code Biome.getIdForBiome(this);}</pre>
	 * <hr>
	 * @param decoratorIndex The index of layer specifies what biomes should be generated where using numeric biome IDs.<br>
	 * Numeric biome IDs can be obtained by using <pre>{@code Biome.getIdForBiome(this);}</pre>
	 * <b>It would be worth noting that this layer does NOT apply biome height properties.</b>
	 * <hr>
	 * @param shaperScale Describes how many times smaller the resolution of shaper in comparison to the resolution of decorator.<br>
	 * In other words, each column's in coordinates given in decorator equals corresponding column in coordinates given in shaper times <tt>shaperScale</tt>.
	 * <p>For example, one <tt>(areaX)</tt> given in decorator equals <tt>(areaX*shaperScale)</tt> in shaper.
	 * <hr>
	 * @return Modified <tt>layers</tt>, or <tt>null</tt> to discard all changes done in the method
	 */
	@Nullable public GenLayer[] overrideGenLayers(long seed,GenLayer[] layers,int shaperIndex,int decoratorIndex,int shaperScale) { return null; }
	public ModBiome(String resource,BiomeProperties properties) {
		super(properties);
		this.setRegistryName(resource);
		ModBiomes.ALL_BIOMES.add(this);
	}
	/**
	 * Should return fog color in color code, in <b>Inverse-ARGB</b> format.
	 * <br>An alpha value of 00 would be opaque, whereas FF would be invisible.
	 * <p>In other words, <b>Alpha here basically indicates transparency, instead of opacity.</b>
	 * @return Color code [0xAA_RRGGBB]
	 */
	public int getFogColor() { return 0xFF_000000; }
	public float getFogDensity(float original) { return original; }
	public float getFogStart(float original) { return original; }
	public float getFogEnd(float original) { return original; }
}