package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.ArmorRegistry;
import com.hbm.util.ArmorRegistry.HazardClass;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BlockAshes extends BlockFalling {

	public BlockAshes(Material mat, String s) {
		super(mat);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	public static int ashes = 0;

	@Override
	public Block setSoundType(SoundType sound){
		return super.setSoundType(sound);
	}
	
	@Override
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand){
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		if(rand.nextInt(25) == 0) {
			
			if(ArmorUtil.checkArmorPiece(MainRegistry.proxy.me(), ModItems.ashglasses, 3)) {
				if(ashes < 256 * 0.25) {
					ashes++;
				}
			} else if(ArmorRegistry.hasAnyProtection(MainRegistry.proxy.me(), EntityEquipmentSlot.HEAD, HazardClass.SAND, HazardClass.LIGHT)) {
				if(ashes < 256 * 0.75) {
					ashes++;
				}
			} else {
				if(ashes < 256 * 0.95) {
					ashes++;
				}
			}
		}
	}
}
