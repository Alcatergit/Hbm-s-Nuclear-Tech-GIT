package com.hbm.blocks.generic;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.explosion.ExplosionThermo;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class RedBarrel extends Block {

	public static final AxisAlignedBB BARREL_BB = new AxisAlignedBB(2 * 0.0625F, 0.0F, 2 * 0.0625F, 14 * 0.0625F, 1.0F, 14 * 0.0625F);
	
	public RedBarrel(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Static fluid barrel");
	}
	
	@Override
	public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
		if(!worldIn.isRemote && worldIn instanceof WorldServer) {
			((WorldServer)worldIn).addScheduledTask(() -> {
        		explode(worldIn, pos.getX(), pos.getY(), pos.getZ());
        	});
        }
	}
	
	@Override
	public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if ((this == ModBlocks.red_barrel || this == ModBlocks.pink_barrel) && worldIn.getBlockState(pos.east()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.west()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.up()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.down()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.south()).getBlock() == Blocks.FIRE || worldIn.getBlockState(pos.north()).getBlock() == Blocks.FIRE)
        {
        	if(!worldIn.isRemote && worldIn instanceof WorldServer) {
				((WorldServer)worldIn).addScheduledTask(() -> {
	        		explode(worldIn, pos.getX(), pos.getY(), pos.getZ());
	        		worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
	        	});
	        }
        }
	}
	
	public void explode(World p_149695_1_, int x, int y, int z) {

		if(this == ModBlocks.red_barrel || this == ModBlocks.pink_barrel)
			p_149695_1_.newExplosion((Entity)null, x + 0.5F, y + 0.5F, z + 0.5F, 2.5F, true, true);
		
		if(this == ModBlocks.lox_barrel) {

			p_149695_1_.newExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, 1F, false, false);
			
    		ExplosionThermo.freeze(p_149695_1_, x, y, z, 7);
		}
		
		if(this == ModBlocks.taint_barrel) {

			p_149695_1_.newExplosion(null, x + 0.5F, y + 0.5F, z + 0.5F, 1F, false, false);
			
			Random rand = p_149695_1_.rand;
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
		    for(int i = 0; i < 100; i++) {
		    	int a = rand.nextInt(9) - 4 + x;
		    	int b = rand.nextInt(9) - 4 + y;
		    	int c = rand.nextInt(9) - 4 + z;
		           if(p_149695_1_.getBlockState(pos.setPos(a, b, c)).getBlock().isReplaceable(p_149695_1_, pos.setPos(a, b, c)) && BlockTaint.hasPosNeightbour(p_149695_1_, pos.setPos(a, b, c))) {
		        	   p_149695_1_.setBlockState(pos.setPos(a, b, c), ModBlocks.taint.getDefaultState().withProperty(BlockTaint.TEXTURE, rand.nextInt(3) + 4), 2);
		           }
		    }
		}
	}
	
	@Override
	public boolean canDropFromExplosion(Explosion explosionIn) {
		return false;
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return BARREL_BB;
	}

}
