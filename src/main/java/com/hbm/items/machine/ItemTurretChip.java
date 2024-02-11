package com.hbm.items.machine;

import java.util.Arrays;

import com.hbm.blocks.turret.TurretBase;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.turret.TileEntityTurretBase;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemTurretChip extends ItemTurretBiometry {

	public ItemTurretChip(String s) {
		super(s);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if((world.getBlockState(pos).getBlock() instanceof TurretBase))
		{
			ItemStack stack = player.getHeldItem(hand);
			if(getNames(stack) == null)
				return EnumActionResult.FAIL;
			
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof TileEntityTurretBase) {
				((TileEntityTurretBase)te).isAI = true;
				((TileEntityTurretBase)te).players.clear();
				((TileEntityTurretBase)te).players.addAll(Arrays.asList(getNames(stack)));
				((TileEntityTurretBase)te).playerListChanged = true;
				((TileEntityTurretBase)te).markDirty();
			}
	        if(world.isRemote)
			{
	        	player.sendMessage(new TextComponentTranslation("chat.turret.ownertransfer"));
			}
			world.playSound(player.posX, player.posY, player.posZ, HBMSoundHandler.techBleep, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
        	
	        return EnumActionResult.SUCCESS;
		}
    	
        return EnumActionResult.PASS;
	}

}
