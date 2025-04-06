package com.hbm.dim.laythe;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.dim.ChunkProviderCelestial;
import com.hbm.dim.laythe.biome.BiomeGenBaseLaythe;
import com.hbm.dim.mapgen.MapGenGreg;
import com.hbm.dim.mapgen.MapGenTiltedSpires;

import com.hbm.entity.mob.EntityCreeperFlesh;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;

import javax.annotation.Nullable;

public class ChunkProviderLaythe extends ChunkProviderCelestial {

	private MapGenGreg caveGenV3 = new MapGenGreg();
	private MapGenTiltedSpires spires = new MapGenTiltedSpires(2, 14, 0.8F);
	private MapGenTiltedSpires snowires = new MapGenTiltedSpires(2, 14, 0.8F);

	private List<Biome.SpawnListEntry> spawnedOfFlesh = new ArrayList<Biome.SpawnListEntry>();

	public ChunkProviderLaythe(World world, long seed, boolean hasMapFeatures) {
		super(world, seed, hasMapFeatures);
		
		spires.rock = Blocks.STONE;
		spires.regolith = ModBlocks.laythe_silt;
		spires.curve = true;
		spires.maxPoint = 6.0F;
		spires.maxTilt = 3.5F;

		seaBlock = Blocks.WATER;

		spawnedOfFlesh.add(new Biome.SpawnListEntry(EntityCreeperFlesh.class, 10, 4, 4));
		
		snowires.rock = Blocks.PACKED_ICE;
		snowires.regolith = Blocks.SNOW;
		snowires.curve = true;
		snowires.maxPoint = 6.0F;
		snowires.maxTilt = 3.5F;

	}

	@Override
	public ChunkPrimer getChunkPrimer(int x, int z) {
		ChunkPrimer buffer = super.getChunkPrimer(x, z);
		
		spires.generate(worldObj, x, z, buffer);
		caveGenV3.generate(worldObj, x, z, buffer);
		if(biomesForGeneration[0] == BiomeGenBaseLaythe.laythePolar) {
			snowires.generate(worldObj, x, z, buffer);
		}

		return buffer;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
		if(creatureType == EnumCreatureType.MONSTER && worldObj.getBlockState(pos.down()) == ModBlocks.tumor)
			return spawnedOfFlesh;

		return super.getPossibleCreatures(creatureType, pos);
	}

	@Override
	public boolean generateStructures(Chunk chunkIn, int x, int z){return false;}
	@Override
	@Nullable
	public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored){return null;}
	@Override
	public void recreateStructures(Chunk chunkIn, int x, int z){};
	@Override
	public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos){return false;}

}