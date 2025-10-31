package com.hbm.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;

public interface IEquipReceiver {

	void onEquip(EntityPlayer player, EnumHand hand);
}
