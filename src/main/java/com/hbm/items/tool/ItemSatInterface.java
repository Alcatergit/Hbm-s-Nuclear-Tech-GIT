package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.SatPanelPacket;
import com.hbm.saveddata.satellites.Satellite;
import com.hbm.saveddata.satellites.SatelliteSavedData;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemSatInterface extends ItemSatChip {

	@SideOnly(Side.CLIENT)
	public static Satellite currentSat;
	
	public ItemSatInterface(String s) {
		super(s);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
		if(world.isRemote) {

			if(this == ModItems.sat_interface)
				player.openGui(MainRegistry.instance, ModItems.guiID_item_sat_interface, world, 0, 0, 0);
			if(this == ModItems.sat_coord)
				player.openGui(MainRegistry.instance, ModItems.guiID_item_sat_coord, world, 0, 0, 0);
		}
		
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
		if(world.isRemote || !(entity instanceof EntityPlayerMP))
    		return;
    	
    	if(((EntityPlayerMP)entity).getHeldItemMainhand() != stack)
    		return;
    	
    	Satellite sat = SatelliteSavedData.getData(world).getSatFromFreq(getFreq(stack));
    	
    	if(sat != null && entity.ticksExisted % 2 == 0) {
    		PacketDispatcher.sendTo(new SatPanelPacket(sat), (EntityPlayerMP) entity);
    	}
	}
}
