package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class ItemSatInterface extends ItemSatChip {

	public ItemSatInterface(String s) {
		super(s);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(world.isRemote)
			player.openGui(MainRegistry.instance, ModItems.guiID_item_sat_interface, world, 0, 0, 0);
		return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(handIn));
	}
	
	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int itemSlot, boolean isSelected) {
		//Drillgon200: what in the world
		/*if(!world.isRemote) {
		    SatelliteSavedData data = (SatelliteSavedData)entity.world.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
			
		    if(data != null) {
			    for(int j = 0; j < data.satellites.size(); j++) {
			    	PacketDispatcher.wrapper.sendToAll(new SatPanelPacket(data.satellites.get(j)));
			    }
		    }
		}*/
	}
}
