package com.hbm.items.gear;

import java.util.List;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmCapability.IHBMData;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.armor.JetpackBase;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.model.ModelJetPack;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackBooster extends JetpackBase {

	private ModelJetPack model;
	public static int maxFuel = 750;

	public JetpackBooster(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, Fluid fuel, int maxFuel, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn, fuel, maxFuel, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("High-powered vectorized jetpack.");
		tooltip.add("Highly increased fuel consumption.");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}


	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		return armorType == EntityEquipmentSlot.CHEST;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (armorSlot == EntityEquipmentSlot.CHEST) {
			if (model == null) {
				this.model = new ModelJetPack();
			}
			return this.model;
		}
		
		return null;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "hbm:textures/armor/JetPack.png";
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
    	
		IHBMData props = HbmCapability.getData(player);

		if(!world.isRemote) {

			if(getFuel(stack) > 0 && props.isJetpackActive()) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack");
				data.setInteger("player", player.getEntityId());
				data.setInteger("mode", 1);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 100));
			}
		}
		if(getFuel(stack) > 0 && props.isJetpackActive()) {
			if(player.motionY < 0.6D)
				player.motionY += 0.1D;

			Vec3d look = player.getLookVec();

			if(Vec3.createVectorHelper(player.motionX, player.motionY, player.motionZ).lengthVector() < 5) {
				player.motionX += look.x * 0.25;
				player.motionY += look.y * 0.25;
				player.motionZ += look.z * 0.25;

				if(look.y > 0)
					player.fallDistance = 0;
			}

			world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerShoot, SoundCategory.PLAYERS, 0.25F, 1.0F);
			this.useUpFuel(player, stack, 1);
		}
    }
}
