package com.hbm.items.weapon;

import com.hbm.handler.GunConfiguration;
import com.hbm.lib.Library;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.SetGunAnimPacket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemGunShotty extends ItemGunBase {

	public ItemGunShotty(GunConfiguration config, String s) {
		super(config, s);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void startActionClient(ItemStack stack, World world, EntityPlayer player, boolean main, EnumHand hand) {
		if(mainConfig.firingMode == GunConfiguration.FIRE_MANUAL && ((hand == EnumHand.MAIN_HAND && m1r) || (hand == EnumHand.OFF_HAND && m1l)) && tryShoot(stack, world, player, main)){
			long time = System.currentTimeMillis();
			float mult = player.getUniqueID().toString().equals(Library.Dr_Nostalgia) ? 10 : 1;
			NBTTagCompound anim = new NBTTagCompound();
			anim.setLong("time", time);
			anim.setInteger("id", 0);
			anim.setFloat("mult", mult);
			if(stack.getTagCompound() == null)
				stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setTag("animation", anim);
			PacketDispatcher.wrapper.sendToServer(new SetGunAnimPacket(time, 0, mult, player.getHeldItemMainhand() == stack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND));
		}
		super.startActionClient(stack, world, player, main, hand);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateServer(stack, world, player, slot, hand);
		if(player.getUniqueID().toString().equals(Library.Dr_Nostalgia) && getDelay(stack) < this.mainConfig.rateOfFire * 0.9){
			setDelay(stack, 0);
		}
		
	}
	
}