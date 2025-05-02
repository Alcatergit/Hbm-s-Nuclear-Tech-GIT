package com.hbm.blocks.machine;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.entity.logic.EntityNukeExplosionMK3.ATEntry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MachineFieldDisturber extends Block {

	public MachineFieldDisturber(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public int tickRate(World world) {
		return 20;
	}

	@Override
	public void onBlockAdded(World world, BlockPos pos, IBlockState state) {
		if(!world.isRemote)
			world.scheduleUpdate(pos, this, this.tickRate(world));
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		EntityNukeExplosionMK3.at.remove(new ATEntry(worldIn.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ()));
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {

		if(!world.isRemote) {
			world.scheduleUpdate(pos, this, this.tickRate(world));
			EntityNukeExplosionMK3.at.put(new ATEntry(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ()),  world.getTotalWorldTime() + 100);
		}
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("§3[Anti-Antischrabidium-Field]§r");
		tooltip.add("§b Radius: 300m§r");
	}
}
