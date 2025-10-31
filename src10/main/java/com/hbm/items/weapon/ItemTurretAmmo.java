package com.hbm.items.weapon;

import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.turret.TileEntityTurretBase;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemTurretAmmo extends Item {

	Block turret;
	int count;
	
	public ItemTurretAmmo(Block b, int i, String string) {
		this.setTranslationKey(string);
		this.setRegistryName(string);
		this.setCreativeTab(MainRegistry.weaponTab);
		this.turret = b;
		this.count = i;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking())
			return EnumActionResult.PASS;
		
		if(worldIn.getBlockState(pos).getBlock() == turret) {
			
			if(worldIn.getTileEntity(pos) instanceof TileEntityTurretBase) {
				((TileEntityTurretBase)worldIn.getTileEntity(pos)).ammo += count;
            	ItemStack stack = player.getHeldItem(hand);
            	stack.shrink(1);
            	
            	player.setHeldItem(hand, stack.copy());
            	if(stack.isEmpty())
            		player.inventory.deleteStack(stack);
    			worldIn.playSound(player.posX, player.posY, player.posZ, HBMSoundHandler.reloadTurret, SoundCategory.PLAYERS, 1.0F, 1.0F, true);
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}
}
