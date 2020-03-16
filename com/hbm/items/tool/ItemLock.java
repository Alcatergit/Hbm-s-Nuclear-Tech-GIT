package com.hbm.items.tool;

import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityLockableBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemLock extends ItemKeyPin {

	public double lockMod = 0.1D;
	
	public ItemLock(double mod, String s) {
		super(s);
		lockMod = mod;
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(getPins(stack) != 0) {
			TileEntity te = world.getTileEntity(pos);
			
			if(te != null && te instanceof TileEntityLockableBase) {
				TileEntityLockableBase tile = (TileEntityLockableBase)te;
				
				if(tile.isLocked())
					return EnumActionResult.FAIL;
				
				tile.setPins(getPins(stack));
				tile.lock();
				tile.setMod(lockMod);

	        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockHang, SoundCategory.PLAYERS, 1.0F, 1.0F);
				stack.shrink(1);
				
				return EnumActionResult.SUCCESS;
			}
			
			if(te != null && te instanceof TileEntityDummy) {
				
				TileEntityDummy dummy = (TileEntityDummy)te;
				TileEntity target = world.getTileEntity(dummy.target);

				if(target != null && target instanceof TileEntityLockableBase) {
					TileEntityLockableBase tile = (TileEntityLockableBase)target;
					
					if(tile.isLocked())
						return EnumActionResult.FAIL;
					
					tile.setPins(getPins(stack));
					tile.lock();
					tile.setMod(lockMod);

		        	world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.lockHang, SoundCategory.PLAYERS, 1.0F, 1.0F);
					stack.shrink(1);
					
					return EnumActionResult.SUCCESS;
				}
			}
		}
		
		return EnumActionResult.PASS;
	}
	
}
