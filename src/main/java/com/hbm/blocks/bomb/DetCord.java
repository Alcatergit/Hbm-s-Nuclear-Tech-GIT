package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
				ExplosionLarge.explode(world, pos.getX(), pos.getY(), pos.getZ(), 15, true, false, false);
			}
			if(this == ModBlocks.det_nuke) {
				world.spawnEntity(EntityNukeExplosionMK4.statFac(world, BombConfig.missileRadius, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5));

				EntityNukeCloudSmall entity2 = new EntityNukeCloudSmall(world, 1000, BombConfig.missileRadius * 0.005F);
				entity2.posX = pos.getX();
				entity2.posY = pos.getY();
				entity2.posZ = pos.getZ();
				world.spawnEntity(entity2);
			}
		}
	}

}
