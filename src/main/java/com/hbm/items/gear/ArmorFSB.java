package com.hbm.items.gear;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.hbm.items.ModItems;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorFSB extends ItemArmor {

	private String texture = "";
	private ResourceLocation overlay = null;
	public List<PotionEffect> effects = new ArrayList<PotionEffect>();
	public HashMap<String, Float> resistance = new HashMap<String, Float>();
	public float blastProtection = -1;
	public float damageCap = -1;
	public float damageMod = -1;
	public boolean fireproof = false;
	public boolean noHelmet = false;
	
	public ArmorFSB(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String texture, String name) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(name);
		this.setRegistryName(name);
		this.texture = texture;
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public ArmorFSB addEffect(PotionEffect effect) {
		effects.add(effect);
		return this;
	}
	
	public ArmorFSB addResistance(String damage, float mod) {
		resistance.put(damage, mod);
		return this;
	}
	
	public ArmorFSB setCap(float cap) {
		this.damageCap = cap;
		return this;
	}
	
	public ArmorFSB setMod(float mod) {
		this.damageMod = mod;
		return this;
	}
	
	public ArmorFSB setFireproof(boolean fire) {
		this.fireproof = fire;
		return this;
	}
	
	public ArmorFSB setNoHelmet(boolean noHelmet) {
		this.noHelmet = noHelmet;
		return this;
	}
	
	public ArmorFSB setOverlay(String path) {
		this.overlay = new ResourceLocation(path);
		return this;
	}
	
	public ArmorFSB cloneStats(ArmorFSB original) {
		
		//lists aren't being modified after instantiation, so there's no need to dereference
		this.effects = original.effects;
		this.resistance = original.resistance;
		this.damageCap = original.damageCap;
		this.damageMod = original.damageMod;
		this.fireproof = original.fireproof;
		this.noHelmet = original.noHelmet;
		//overlay doesn't need to be copied because it's helmet exclusive
		return this;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		return texture;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		list.add(TextFormatting.GOLD + "Full set bonus:");
    	
    	if(!effects.isEmpty()) {
    		
    		for(PotionEffect effect : effects) {
	    		list.add("  " + I18n.format(effect.getPotion().getName()));
    		}
    	}
    	
    	if(!resistance.isEmpty()) {

        	for(Entry<String, Float> struct : resistance.entrySet()) {
        		list.add("  Damage modifier of " + struct.getValue() + " against " + I18n.format(struct.getKey()));
        	}
    	}
    	
    	if(blastProtection != -1) {

    		list.add("  Damage modifier of " + blastProtection + " against explosions");
    	}
    	
    	if(damageCap != -1) {
			list.add("  Hard damage cap of " + damageCap);
    	}
    	
    	if(damageMod != -1) {
			list.add("  General damage modifier of " + damageMod);
    	}
    	
    	if(fireproof) {
			list.add("  Fireproof");
    	}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		if(overlay == null)
    		return;
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);
        GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableAlpha();
        Minecraft.getMinecraft().getTextureManager().bindTexture(overlay);
        RenderHelper.startDrawingTexturedQuads();
        RenderHelper.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        RenderHelper.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        RenderHelper.draw();
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();
        GlStateManager.enableAlpha();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	}

}
