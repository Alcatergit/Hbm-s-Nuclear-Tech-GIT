package com.hbm.items.weapon;

import com.hbm.interfaces.IPostRender;
import com.hbm.items.IEquipReceiver;
import com.hbm.items.tool.ItemSwordAbility;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.particle.ParticleCrucibleLightning;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.anim.HbmAnimations.Animation;
import com.hbm.render.anim.HbmAnimations.BlenderAnimation;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemCrucible extends ItemSwordCutter implements IPostRender {

	public ItemCrucible(float damage, double movement, ToolMaterial material, String s) {
		super(damage, movement, material, s);
	}

	@Override
	public void onEquip(EntityPlayer player, EnumHand hand) {
		super.onEquip(player, hand);
		if(!(player instanceof EntityPlayerMP))
			return;
		World world = player.world;
		world.playSound(null, player.posX, player.posY, player.posZ, HBMSoundHandler.cDeploy, SoundCategory.PLAYERS, 1.0F, 1.0F);
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(!(entityLiving instanceof EntityPlayerMP))
			return true;
		EnumHand hand = stack == entityLiving.getHeldItemMainhand() ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;

		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setString("type", "anim");
		nbt.setInteger("hand", hand.ordinal());
		nbt.setString("mode", "cSwing");
		nbt.setString("name", this.getRegistryName().getResourcePath());
		PacketDispatcher.wrapper.sendTo(new AuxParticlePacketNT(nbt, 0, 0, 0), (EntityPlayerMP)entityLiving);

		return true;
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		if(isSelected && worldIn.isRemote && entityIn instanceof EntityPlayer){
			updateClient(worldIn, (EntityPlayer) entityIn, itemSlot);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void updateClient(World w, EntityPlayer player, int slot){
		if(player != Minecraft.getMinecraft().player)
			return;
		Animation anim = HbmAnimations.hotbar[slot];
		boolean flag = false;
		if(anim instanceof BlenderAnimation){
			if(System.currentTimeMillis() - ((BlenderAnimation) anim).wrapper.startTime > ((BlenderAnimation) anim).wrapper.anim.length*0.7F){
				flag = true;
			}
		}
		if(flag && w.rand.nextInt(20) == 0){
			ModEventHandlerClient.firstPersonAuxParticles.add(new ParticleCrucibleLightning(w, 0, (w.rand.nextFloat()-0.5F)*0.2F, 0.7F-w.rand.nextFloat()*0.25F).lifetime(10));
		}
	}
	
	@Override
	public boolean hitEntity(ItemStack stack, EntityLivingBase victim, EntityLivingBase attacker) {
		attacker.world.playSound(null, victim.posX, victim.posY, victim.posZ, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.HOSTILE, 1.0F, 0.75F + victim.getRNG().nextFloat() * 0.2F);

		if(!attacker.world.isRemote && !victim.isEntityAlive()) {
			int count = Math.min((int)Math.ceil(victim.getMaxHealth() / 3D), 250);

			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setInteger("count", count * 4);
			data.setDouble("motion", 0.1D);
			data.setString("mode", "blockdust");
			data.setInteger("block", Block.getIdFromBlock(Blocks.REDSTONE_BLOCK));
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, victim.posX, victim.posY + victim.height * 0.5, victim.posZ), new TargetPoint(victim.dimension, victim.posX, victim.posY + victim.height * 0.5, victim.posZ, 50));
		}
		return super.hitEntity(stack, victim, attacker);
	}

}
