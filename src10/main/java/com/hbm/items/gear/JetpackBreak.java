package com.hbm.items.gear;

import java.util.List;

import com.hbm.capability.HbmCapability;
import com.hbm.capability.HbmCapability.IHBMData;
import com.hbm.handler.HbmKeybinds.EnumKeybind;
import com.hbm.items.armor.JetpackBase;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.KeybindPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class JetpackBreak extends JetpackBase {

	public JetpackBreak(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, Fluid fuel, int maxFuel, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn, fuel, maxFuel, s);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return "hbm:textures/armor/JetPackBlue.png";
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn){
		tooltip.add("Regular jetpack that will automatically hover mid-air.");
		tooltip.add("Sneaking will stop hover mode.");
    	tooltip.add("Hover mode will consume less fuel and increase air-mobility.");
		super.addInformation(stack, worldIn, tooltip, flagIn);
	}
	
	public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
		IHBMData props = HbmCapability.getData(player);
		if(world.isRemote) {

			if(player == MainRegistry.proxy.me() && props.isJetpackActive()) {

				boolean last = props.getKeyPressed(EnumKeybind.JETPACK);
				boolean current = MainRegistry.proxy.getIsKeyPressed(EnumKeybind.JETPACK);

				if(last != current) {
					PacketDispatcher.wrapper.sendToServer(new KeybindPacket(EnumKeybind.JETPACK, current));
					props.setKeyPressed(EnumKeybind.JETPACK, current);
				}
			}

		} else {

			if(getFuel(stack) > 0 && (props.isJetpackActive() || (!player.onGround && !player.isSneaking() && props.getEnableBackpack()))) {

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "jetpack");
				data.setInteger("player", player.getEntityId());
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, player.posX, player.posY, player.posZ), new TargetPoint(world.provider.getDimension(), player.posX, player.posY, player.posZ, 100));
			}
		}
		if(getFuel(stack) > 0) {

			if(props.isJetpackActive()) {
				player.fallDistance = 0;

				if(player.motionY < 0.4D)
					player.motionY += 0.1D;

				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerShoot, SoundCategory.PLAYERS, 0.25F, 1.5F);
				this.useUpFuel(player, stack, 5);

			} else if(!player.isSneaking() && !player.onGround && props.getEnableBackpack()) {
				player.fallDistance = 0;

				if(player.motionY < -1)
					player.motionY += 0.2D;

				else if(player.motionY < -0.1)
					player.motionY += 0.1D;
				
				else if(player.motionY < 0)
					player.motionY = 0;

				world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.flamethrowerShoot, SoundCategory.PLAYERS, 0.25F, 1.5F);
				this.useUpFuel(player, stack, 10);
			}
		}
	}
}
