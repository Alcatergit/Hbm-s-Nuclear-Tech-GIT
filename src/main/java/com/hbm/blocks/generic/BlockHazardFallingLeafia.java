package com.hbm.blocks.generic;

import com.hbm.interfaces.IItemHazard;
import com.hbm.modules.ItemHazardModule;
import com.hbm.saveddata.RadiationSavedData;
import com.leafia.shit.BlockFallingLeafia;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

public class BlockHazardFallingLeafia extends BlockFallingLeafia implements IItemHazard {

	ItemHazardModule module;

	private float rad = 0.0F;

	private boolean beaconable = false;

	public BlockHazardFallingLeafia(Material mat, String s, SoundType type) {
		super(mat,s,type);
		this.module = new ItemHazardModule();
	}

	public BlockHazardFallingLeafia(SoundType type, String s) {
		this(Material.SAND, s, type);
	}

	public BlockHazardFallingLeafia makeBeaconable() {
		this.beaconable  = true;
		return this;
	}

	@Override
	public boolean isBeaconBase(IBlockAccess worldObj, BlockPos pos, BlockPos beacon){
		return beaconable;
	}
	
	@Override
	public ItemHazardModule getModule() {
		return module;
	}

	@Override
	public IItemHazard addRadiation(float radiation) {
		this.getModule().addRadiation(radiation);
		this.rad = radiation * 0.1F;
		return this;
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(this.rad > 0) {
			RadiationSavedData.incrementRad(world, pos, rad*0.01F, rad);
		}
		super.updateTick(world, pos, state, rand);
	}
	
	@Override
	public int tickRate(World world) {

		if(this.rad > 0)
			return 20;

		return super.tickRate(world);
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state){
		super.onBlockAdded(world, pos, state);
		if(this.rad > 0){
			this.setTickRandomly(true);
			world.scheduleUpdate(pos, this, this.tickRate(world));
		}
	}
}