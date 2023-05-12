package com.hbm.items.tool;

import java.util.List;

import com.hbm.interfaces.IFluidPipe;
import com.hbm.items.ModItems;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityLockableBase;
import com.hbm.tileentity.network.energy.TileEntityPylonBase;

import api.hbm.energy.IEnergyConnector;
import net.minecraft.block.Block;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemAnalyzer extends Item {

	public ItemAnalyzer(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		Block block = world.getBlockState(pos).getBlock();
		TileEntity te = world.getTileEntity(pos);
		
		if(world.isRemote) {
			player.sendMessage(new TextComponentTranslation(
					"Block: " + I18n.format(block.getUnlocalizedName() + ".name") + " (" + block.getUnlocalizedName() + ")"
					));
			
			player.sendMessage(new TextComponentTranslation(
					"Meta: " + block.getMetaFromState(world.getBlockState(pos))
					));
		}
		
		if(!world.isRemote) {
			
			if(te == null) {
				player.sendMessage(new TextComponentTranslation(
						"Tile Entity: none"));
			} else {
				
				if(te instanceof TileEntityDummy) {

					player.sendMessage(new TextComponentTranslation(
							"Dummy Block, references TE at " + ((TileEntityDummy)te).target.getX() + " / " + ((TileEntityDummy)te).target.getY() + " / " + ((TileEntityDummy)te).target.getZ()));
					
					te = world.getTileEntity(((TileEntityDummy)te).target);
				}
				
				String[] parts = te.toString().split("\\.");
				
				if(parts.length == 0)
					parts = new String[]{"error"};
				
				String post = parts[parts.length - 1];
				String name = post.split("@")[0];

				player.sendMessage(new TextComponentTranslation(
						"Tile Entity: " + name));
				
				if(te instanceof IInventory) {
					
					player.sendMessage(new TextComponentTranslation(
							"Slots: " + ((IInventory)te).getSizeInventory()));
				}
				
				if(te instanceof IEnergyConnector) {
					
					player.sendMessage(new TextComponentTranslation(
							"Electricity: " + ((IEnergyConnector)te).getPower() + " HE"));
				}
				
				if(te instanceof IFluidPipe) {
					
					player.sendMessage(new TextComponentTranslation(
							"Duct Type: " + I18n.format(((IFluidPipe)te).getType().getUnlocalizedName())));
				}
				
				if(te instanceof TileEntityPylonBase) {
					
					player.sendMessage(new TextComponentTranslation(
							"Connections:"));
					
					List<BlockPos> connections = ((TileEntityPylonBase)te).connected;
					
					for(int i = 0; i < connections.size(); i++) {
						player.sendMessage(new TextComponentTranslation(
								" *" + connections.get(i).getX() + " / " + connections.get(i).getY() + " / " + connections.get(i).getZ()));
					}
				}
				
				if(te instanceof TileEntityLockableBase) {
					
					player.sendMessage(new TextComponentTranslation(
							"Locked: " + ((TileEntityLockableBase)te).isLocked()));
					
					if(((TileEntityLockableBase)te).isLocked()) {

						//player.sendMessage(new TextComponentTranslation(
						//		"Pins: " + ((TileEntityLockableBase)te).getPins()));
						player.sendMessage(new TextComponentTranslation(
								"Pick Chance: " + (((TileEntityLockableBase)te).getMod() * 100D) + "%"));
					}
				}
			}

			player.sendMessage(new TextComponentTranslation(
					"----------------------------"
					));
		}
		
		return EnumActionResult.SUCCESS;
	}
}
