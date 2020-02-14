package com.hbm.items.weapon;

import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class WeaponizedCell extends Item {

	public WeaponizedCell(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public boolean onEntityItemUpdate(EntityItem item) {
		World world = item.world ;
    	
    	if(item.ticksExisted > 50 * 20 || item.isBurning()) {
			
	    	if(!world.isRemote) {
	    		world.playSound(null, item.posX, item.posY, item.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 100.0f, world.rand.nextFloat() * 0.1F + 0.9F);

				EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(world);
				exp.posX = item.posX;
				exp.posY = item.posY;
				exp.posZ = item.posZ;
				exp.destructionRange = 100;
				exp.speed = 25;
				exp.coefficient = 1.0F;
				exp.waste = false;

				world.spawnEntity(exp);
	    		
	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, 100);
	    		cloud.posX = item.posX;
	    		cloud.posY = item.posY;
	    		cloud.posZ = item.posZ;
	    		world.spawnEntity(cloud);
	    	}
	    		
	    	item.setDead();
    	}
    	
    	int randy = (50 * 20) - item.ticksExisted;
    	
    	if(randy < 1)
    		randy = 1;
    	
    	if(item.world.rand.nextInt(50 * 20) >= randy)
    		world.spawnParticle(EnumParticleTypes.REDSTONE, item.posX + item.world.rand.nextGaussian() * item.width / 2, item.posY + item.world.rand.nextGaussian() * item.height, item.posZ + item.world.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	else
    		world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, item.posX + item.world.rand.nextGaussian() * item.width / 2, item.posY + item.world.rand.nextGaussian() * item.height, item.posZ + item.world.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	
    	if(randy < 100)
    		world.spawnParticle(EnumParticleTypes.LAVA, item.posX + item.world.rand.nextGaussian() * item.width / 2, item.posY + item.world.rand.nextGaussian() * item.height, item.posZ + item.world.rand.nextGaussian() * item.width / 2, 0.0, 0.0, 0.0);
    	
        return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("A charged energy cell, rigged to explode");
		tooltip.add("when left on the floor for too long.");
	}
}
