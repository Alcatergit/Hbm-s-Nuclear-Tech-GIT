package com.hbm.blocks.machine.rbmk;

import java.util.List;
import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.ContaminationUtil;
import com.hbm.util.ContaminationUtil.ContaminationType;
import com.hbm.util.ContaminationUtil.HazardType;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class RBMKDebrisDigamma extends RBMKDebris {

	public RBMKDebrisDigamma(String s){
		super(s);
	}

	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand){
		if(!world.isRemote) {
			
			digamma(world, pos.getX(), pos.getY(), pos.getZ());
			for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
				Block b = world.getBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ)).getBlock();
				if((b instanceof RBMKDebris  && b != this) || b == ModBlocks.corium_block || b == ModBlocks.block_corium)
					world.setBlockState(new BlockPos(pos.getX() + dir.offsetX, pos.getY() + dir.offsetY, pos.getZ() + dir.offsetZ), this.getDefaultState());
			}
		}
	}

	@SuppressWarnings("deprecation")
	private void digamma(World world, int x, int y, int z) {
		
		float rads = 1000F;
		double range = 100D;
		
		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x + 0.5, y + 0.5, z + 0.5, x + 0.5, y + 0.5, z + 0.5).grow(range, range, range));
		
		for(EntityLivingBase e : entities) {
			
			Vec3 vec = Vec3.createVectorHelper(e.posX - (x + 0.5), (e.posY + e.getEyeHeight()) - (y + 0.5), e.posZ - (z + 0.5));
			double len = vec.lengthVector();
			vec = vec.normalize();
			
			float res = 0;
			
			for(int i = 1; i < len; i++) {

				int ix = (int)Math.floor(x + 0.5 + vec.xCoord * i);
				int iy = (int)Math.floor(y + 0.5 + vec.yCoord * i);
				int iz = (int)Math.floor(z + 0.5 + vec.zCoord * i);
				
				res += world.getBlockState(new BlockPos(ix, iy, iz)).getBlock().getExplosionResistance(null);
			}
			
			if(res < 1)
				res = 1;
			
			float eRads = rads;
			eRads /= (float)res;
			eRads /= (float)(len * len);
			
			ContaminationUtil.contaminate(e, HazardType.DIGAMMA, ContaminationType.DIGAMMA, eRads);
		}
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state){
		super.onBlockAdded(worldIn, pos, state);
		worldIn.scheduleUpdate(pos, this, 2);
	}
	
}
