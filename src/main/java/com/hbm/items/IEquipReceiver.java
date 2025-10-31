package com.hbm.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public interface IEquipReceiver {

	public void onEquip(EntityPlayer player, EnumHand hand);
}
