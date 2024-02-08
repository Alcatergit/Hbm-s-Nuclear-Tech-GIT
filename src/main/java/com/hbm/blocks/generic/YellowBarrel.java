package com.hbm.blocks.generic;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class YellowBarrel extends Block {

	public static final AxisAlignedBB BARREL_BB = new AxisAlignedBB(2 * 0.0625F, 0.0F, 2 * 0.0625F, 14 * 0.0625F, 1.0F, 14 * 0.0625F);
	Random rand = new Random();
	
	public YellowBarrel(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		if (!worldIn.isRemote && worldIn instanceof WorldServer) {
			((WorldServer)worldIn).addScheduledTask(() -> {
        		explode(worldIn, pos.getX(), pos.getY(), pos.getZ());
        	});
        }
	}
	
	public void explode(World p_149695_1_, int x, int y, int z) {
		if(rand.nextInt(3) == 0) {
			p_149695_1_.setBlockState(new BlockPos(x, y, z), ModBlocks.toxic_block.getDefaultState());
		} else {
			p_149695_1_.createExplosion(null, x, y, z, 18.0F, true);
		}
    	ExplosionNukeGeneric.waste(p_149695_1_, x, y, z, 35);
        
        RadiationSavedData.incrementRad(p_149695_1_, new BlockPos(x, y, z), 35, 1500);
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
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
        
		if(this == ModBlocks.yellow_barrel){
    		RadiationSavedData.incrementRad(world, pos, 5, 75);
		} else {
			RadiationSavedData.incrementRad(world, pos, 0.5F, 5);
		}

        world.scheduleUpdate(pos, this, this.tickRate(world));
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 20;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		super.randomDisplayTick(stateIn, worldIn, pos, rand);
		worldIn.spawnParticle(EnumParticleTypes.TOWN_AURA, pos.getX() + rand.nextFloat() * 0.5F + 0.25F, pos.getY() + 1.1F, pos.getZ() + rand.nextFloat() * 0.5F + 0.25F, 0.0D, 0.0D, 0.0D);
	}

	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		super.onBlockAdded(worldIn, pos, state);
		worldIn.scheduleUpdate(pos, this, this.tickRate(worldIn));
	}
	
}
