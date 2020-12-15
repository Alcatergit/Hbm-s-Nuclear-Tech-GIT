package com.hbm.tileentity.bomb;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.Landmine;
import com.hbm.lib.HBMSoundHandler;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityLandmine extends TileEntity implements ITickable {

	private boolean isPrimed = false;

	@Override
	public void update() {
		if(!world.isRemote) {
			Block block = world.getBlockState(pos).getBlock();
			double range = 1;
			double height = 1;

			if (block == ModBlocks.mine_ap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_he) {
				range = 2;
				height = 5;
			}
			if (block == ModBlocks.mine_shrap) {
				range = 1.5D;
			}
			if (block == ModBlocks.mine_fat) {
				range = 2.5D;
			}
	
			List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - range, pos.getY() - height, pos.getZ() - range, pos.getX() + range, pos.getY() + height, pos.getZ() + range));
	
			boolean flag = false;
			for (Entity e : list) {
	
				if (e instanceof EntityLivingBase) {
	
					flag = true;
					
					if(isPrimed) {
						
						((Landmine)block).explode(world, pos);
					}
	
					return;
				}
			}
			
			if(!isPrimed && !flag) {
	
				this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.techBoop, SoundCategory.BLOCKS, 2.0F, 1.0F);
				isPrimed = true;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isPrimed = compound.getBoolean("primed");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("primed", isPrimed);
		return super.writeToNBT(compound);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
}
