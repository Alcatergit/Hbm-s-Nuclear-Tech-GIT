package com.hbm.items.armor;

import java.util.List;

import org.lwjgl.opengl.GL11;
import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemArmorMod extends ItemCustomLore {

	public final int type;
	public final boolean helmet;
	public final boolean chestplate;
	public final boolean leggings;
	public final boolean boots;
	
	public ItemArmorMod(int type, boolean helmet, boolean chestplate, boolean leggings, boolean boots, String s) {
		super(s);
		this.type = type;
		this.helmet = helmet;
		this.chestplate = chestplate;
		this.leggings = leggings;
		this.boots = boots;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, list, flagIn);
		list.add(TextFormatting.DARK_PURPLE + "Applicable to:");
		
		if(helmet && chestplate && leggings && boots) {
			list.add("  All");
		} else {

			if(helmet)
				list.add("  Helmets");
			if(chestplate)
				list.add("  Chestplates");
			if(leggings)
				list.add("  Leggings");
			if(boots)
				list.add("  Boots");
		}
		list.add(TextFormatting.DARK_PURPLE + "Slot:");
		
		switch(this.type) {
		case ArmorModHandler.helmet_only: list.add("  Helmet"); break;
		case ArmorModHandler.plate_only: list.add("  Chestplate"); break;
		case ArmorModHandler.legs_only: list.add("  Leggings"); break;
		case ArmorModHandler.boots_only: list.add("  Boots"); break;
		case ArmorModHandler.servos: list.add("  Servos"); break;
		case ArmorModHandler.cladding: list.add("  Cladding"); break;
		case ArmorModHandler.kevlar: list.add("  Insert"); break;
		case ArmorModHandler.extra: list.add("  Special"); break;
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		list.add(stack.getDisplayName());
	}
	
	public void modUpdate(EntityLivingBase entity, ItemStack armor) { }
	
	public void modDamage(LivingHurtEvent event, ItemStack armor) { }
	
	public Multimap<String, AttributeModifier> getModifiers(EntityEquipmentSlot slot, ItemStack armor) { return null; }
	
	@SideOnly(Side.CLIENT)
	public void modRender(RenderPlayerEvent.Pre event, ItemStack armor) { }

	public void offset(EntityPlayer player, EntityPlayer me, float interp){
		float px = (float)(player.prevPosX + (player.posX - player.prevPosX) * interp);
		float py = (float)(player.prevPosY + (player.posY - player.prevPosY) * interp);
		float pz = (float)(player.prevPosZ + (player.posZ - player.prevPosZ) * interp);
		float mx = (float)(me.prevPosX + (me.posX - me.prevPosX) * interp);
		float my = (float)(me.prevPosY + (me.posY - me.prevPosY) * interp);
		float mz = (float)(me.prevPosZ + (me.posZ - me.prevPosZ) * interp);
		GL11.glTranslatef(mx-px, my-py, pz-mz);
	}
}
