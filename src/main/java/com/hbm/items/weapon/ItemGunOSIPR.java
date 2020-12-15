package com.hbm.items.weapon;

import com.hbm.entity.projectile.EntityCombineBall;
import com.hbm.handler.GunConfiguration;
import com.hbm.lib.HBMSoundHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class ItemGunOSIPR extends ItemGunBase {

	public ItemGunOSIPR(GunConfiguration config, GunConfiguration alt, String s) {
		super(config, alt, s);
	}
	
	@Override
	protected void altFire(ItemStack stack, World world, EntityPlayer player, EnumHand hand) {
		
		setCharge(stack, 1);
		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.osiprCharging, SoundCategory.PLAYERS, 1.0F, 1F);
	}
	
	@Override
	protected void updateServer(ItemStack stack, World world, EntityPlayer player, int slot, EnumHand hand) {
		super.updateServer(stack, world, player, slot, hand);
		
		if(hand == null) {
			setCharge(stack, 0);
			return;
		}
		
		int i = getCharge(stack);
		
		if(i >= 20) {
			EntityCombineBall entityarrow = new EntityCombineBall(player.world, player, 3.0F, hand);
			entityarrow.setDamage(1000);
			world.spawnEntity(entityarrow);
			world.playSound(null, player.posX, player.posY, player.posZ, altConfig.firingSound, SoundCategory.PLAYERS, 1.0F, 1F);
			setCharge(stack, 0);
			setDelay(stack, altConfig.rateOfFire);
			
		} else if(i > 0)
			setCharge(stack, i + 1);
	}
	
	@Override
	protected boolean tryShoot(ItemStack stack, World world, EntityPlayer player, boolean main) {
		
		return super.tryShoot(stack, world, player, main) && getCharge(stack) == 0;
	}
	
	/// CMB charge state ///
	public static void setCharge(ItemStack stack, int i) {
		writeNBT(stack, "cmb_charge", i);
	}
	
	public static int getCharge(ItemStack stack) {
		return readNBT(stack, "cmb_charge");
	}
}