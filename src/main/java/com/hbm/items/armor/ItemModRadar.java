package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.capability.HbmLivingProps;

import api.hbm.entity.IRadarDetectable;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.SoundCategory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemModRadar extends ItemArmorMod {
	
	public int range;
	public ItemModRadar(String s, int range){
		super(ArmorModHandler.extra, true, false, false, false, s);
		this.range = range;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		list.add("§eAlerts when incoming missiles are detected");
		list.add("§eRange: "+range+"m");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor){
		list.add("§5  " + stack.getDisplayName() + " (Range: "+range+"m)");
	}
	
	@Override
	public void modUpdate(EntityLivingBase entity, ItemStack armor){
		if(entity.ticksExisted % 10 == 0 && isApproachingMissileDetected(entity, this.range)){
			entity.playSound(HBMSoundHandler.nullRadar, 1.0F, 1.0F);
		}
	}

	public boolean isEntityApproaching(EntityLivingBase entity, Entity e){
		boolean xAxisApproaching = (entity.posX < e.posX  && e.motionX < 0) || (entity.posX > e.posX  && e.motionX > 0);
		boolean zAxisApproaching = (entity.posZ < e.posZ && e.motionZ < 0) || (entity.posZ > e.posZ && e.motionZ > 0);
		return xAxisApproaching && zAxisApproaching;
	}
	
	private boolean isApproachingMissileDetected(EntityLivingBase entity, int r) {

		List<Entity> list = entity.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(entity.posX - r, 0D, entity.posZ - r, entity.posX + r, 10_000D, entity.posZ + r));
		for(Entity e : list) {
			
			if(e instanceof EntityLivingBase && HbmLivingProps.getDigamma((EntityLivingBase) e) > 0.001) {
				return false;
			}
			
			if(e instanceof IRadarDetectable && e.motionY <= 0 && isEntityApproaching(entity, e)){
				return true;
			}
		}
		return false;
	}
}
