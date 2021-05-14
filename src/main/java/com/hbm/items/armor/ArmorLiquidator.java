package com.hbm.items.armor;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Multimap;
import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.items.gear.ArmorFSB;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelM65;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorLiquidator extends ArmorFSB {

	private ModelM65 model;
	private ResourceLocation hazmatBlur = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_dark.png");
	
	public ArmorLiquidator(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String texture, String name) {
		super(materialIn, renderIndexIn, equipmentSlotIn, texture, name);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (this == ModItems.liquidator_helmet) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.model == null) {
					this.model = new ModelM65();
				}
				return this.model;
			}
		}
		return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
	}
	
	@Override
	public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
		Multimap<String, AttributeModifier> map = super.getItemAttributeModifiers(equipmentSlot);
		if(equipmentSlot == this.getEquipmentSlot()){
			map.put(SharedMonsterAttributes.KNOCKBACK_RESISTANCE.getName(), new AttributeModifier(ArmorModHandler.fixedUUIDs[this.armorType.getIndex()], "Armor modifier", 100D, 0));
			map.put(SharedMonsterAttributes.MOVEMENT_SPEED.getName(), new AttributeModifier(ArmorModHandler.fixedUUIDs[this.armorType.getIndex()], "Armor modifier", (double) -0.1D, 1));
		}
		return map;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		GlStateManager.disableDepth();
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.disableAlpha();
		Minecraft.getMinecraft().getTextureManager().bindTexture(hazmatBlur);
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(0.0D, (double) resolution.getScaledHeight(), -90.0D).tex(0, 1).endVertex();
		buf.pos((double) resolution.getScaledWidth(), (double) resolution.getScaledHeight(), -90.0D).tex(1, 1).endVertex();
		buf.pos((double) resolution.getScaledWidth(), 0.0D, -90.0D).tex(1, 0).endVertex();
		buf.pos(0.0D, 0.0D, -90.0D).tex(0, 0).endVertex();
		tes.draw();
		GlStateManager.depthMask(true);
		GlStateManager.enableDepth();
		GlStateManager.enableAlpha();
		GlStateManager.color(1, 1, 1, 1);
	}
	
}
