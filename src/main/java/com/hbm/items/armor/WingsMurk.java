package com.hbm.items.armor;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmCapability.IHBMData;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.model.ModelArmorWings;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class WingsMurk extends ItemArmor {

	public WingsMurk(ArmorMaterial material, String s) {
		super(material, -1, EntityEquipmentSlot.CHEST);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@SideOnly(Side.CLIENT)
	ModelArmorWings model;

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default){
		if(model == null) {
			model = new ModelArmorWings(this == ModItems.wings_murk ? 0 : 1);
		}
		return model;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
		//Drillgon200: Unused, just so forge doesn't yell in the log
		return RefStrings.MODID + ":textures/armor/steel_1.png";
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		
		if(player.onGround)
			return;
		
		ArmorUtil.resetFlightTime(player);
		
		if(player.fallDistance > 0)
			player.fallDistance = 0;
		
		if(player.motionY < -0.4D)
			player.motionY = -0.4D;
		
		if(this == ModItems.wings_limp) {
			
			 if(player.isSneaking()) {
					
				if(player.motionY < -0.08) {

					double mo = player.motionY * -0.2;
					player.motionY += mo;

					Vec3 vec = new Vec3(player.getLookVec());
					vec.xCoord *= mo;
					vec.yCoord *= mo;
					vec.zCoord *= mo;

					player.motionX += vec.xCoord;
					player.motionY += vec.yCoord;
					player.motionZ += vec.zCoord;
				}
			}
		}

		IHBMData props = HbmCapability.getData(player);
		
		if(this == ModItems.wings_murk) {

			if(props.isJetpackActive()) {

				if(player.motionY < 0.6D)
					player.motionY += 0.2D;
				else
					player.motionY = 0.8D;
				
			} else if(props.getEnableBackpack() && !player.isSneaking()) {
				
				if(player.motionY < -1)
					player.motionY += 0.4D;
				else if(player.motionY < -0.1)
					player.motionY += 0.2D;
				else if(player.motionY < 0)
					player.motionY = 0;
			}
			
			if(props.getEnableBackpack()) {
				
				Vec3 orig = new Vec3(player.getLookVec());
				Vec3 look = Vec3.createVectorHelper(orig.xCoord, 0, orig.zCoord).normalize();
				double mod = player.isSneaking() ? 0.25D : 1D;
				
				if(player.moveForward != 0) {
					player.motionX += look.xCoord * 0.35 * player.moveForward * mod;
					player.motionZ += look.zCoord * 0.35 * player.moveForward * mod;
				}
				
				if(player.moveStrafing != 0) {
					look.rotateAroundY((float) Math.PI * 0.5F);
					player.motionX += look.xCoord * 0.15 * player.moveStrafing * mod;
					player.motionZ += look.zCoord * 0.15 * player.moveStrafing * mod;
				}
			}
		}
	}
}