package com.hbm.blocks.bomb;

import java.util.Random;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class DetCord extends Block implements IBomb {

	public DetCord(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World world, BlockPos pos, Explosion explosionIn) {
		this.explode(world, pos);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(worldIn.isBlockIndirectlyGettingPowered(pos) > 0){
			explode(worldIn, pos);
		}
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Items.AIR;
	}

	@Override
	public void explode(World world, BlockPos pos) {
		if(!world.isRemote) {
			
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			if(this == ModBlocks.det_cord) {
				world.createExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 1.5F, true);
			}
			if(this == ModBlocks.det_charge) {
				ExplosionLarge.explode(world, pos.getX(), pos.getY(), pos.getZ(), 20, true, false, false);
			}
			if(this == ModBlocks.det_n2) {
				world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int)(BombConfig.n2Radius/12) * 5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
				if(BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFac(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, (int)(BombConfig.n2Radius/12) * 5);
				}
			}
			if(this == ModBlocks.det_nuke) {
				world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.missileRadius, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));
				if(BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFac(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, BombConfig.missileRadius);
				}
			}
			if(this == ModBlocks.det_bale) {
				EntityBalefire bf = new EntityBalefire(world);
				bf.posX = pos.getX() + 0.5;
				bf.posY = pos.getY() + 0.5;
				bf.posZ = pos.getZ() + 0.5;
				bf.destructionRange = (int) 130;
				world.spawnEntity(bf);
				if(BombConfig.enableNukeClouds) {
					EntityNukeTorex.statFacBale(world, pos.getX() + 0.5, pos.getY() + 5, pos.getZ() + 0.5, 130F);
				}
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		if(this == ModBlocks.det_n2){
			tooltip.add("§c[Extreme Bomb]§r");
			tooltip.add(" §eRadius: "+((int)(BombConfig.n2Radius/12) * 5)+"m§r");
		}
		if(this == ModBlocks.det_nuke){
			tooltip.add("§2[Nuclear Bomb]§r");
			tooltip.add(" §eRadius: "+BombConfig.missileRadius+"m§r");
			if(!BombConfig.disableNuclear){
				tooltip.add("§2[Fallout]§r");
				tooltip.add(" §aRadius: "+(int)BombConfig.missileRadius*(1+BombConfig.falloutRange/100)+"m§r");
			}
		}
		if(this == ModBlocks.det_bale){
			tooltip.add("§a[Balefire Bomb]§r");
			tooltip.add(" §eRadius: 130m§r");
		}
	}
}
