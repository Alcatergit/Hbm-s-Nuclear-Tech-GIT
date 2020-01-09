package com.hbm.items.gear;

import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;
import com.hbm.render.model.ModelGasMask;
import com.hbm.render.model.ModelGoggles;
import com.hbm.render.model.ModelM65;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArmorModel extends ItemArmor {

	@SideOnly(Side.CLIENT)
	private ModelGoggles modelGoggles;
	@SideOnly(Side.CLIENT)
	private ModelGasMask modelGas;
	/*@SideOnly(Side.CLIENT)
	private ModelCloak modelCloak;
	@SideOnly(Side.CLIENT)
	private ModelOxygenMask modelOxy;*/
	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;
	
	private ResourceLocation goggleBlur0 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_0.png");
	private ResourceLocation goggleBlur1 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_1.png");
	private ResourceLocation goggleBlur2 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_2.png");
	private ResourceLocation goggleBlur3 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_3.png");
	private ResourceLocation goggleBlur4 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_4.png");
	private ResourceLocation goggleBlur5 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_goggles_5.png");
	private ResourceLocation gasmaskBlur0 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_0.png");
	private ResourceLocation gasmaskBlur1 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_1.png");
	private ResourceLocation gasmaskBlur2 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_2.png");
	private ResourceLocation gasmaskBlur3 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_3.png");
	private ResourceLocation gasmaskBlur4 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_4.png");
	private ResourceLocation gasmaskBlur5 = new ResourceLocation(RefStrings.MODID + ":textures/misc/overlay_gasmask_5.png");
	
	public ArmorModel(ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn, String s) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(CreativeTabs.COMBAT);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
		if (this == ModItems.goggles) {
			return armorType == EntityEquipmentSlot.HEAD;
		}
		if (this == ModItems.gas_mask) {
			return armorType == EntityEquipmentSlot.HEAD;
		}
		if (this == ModItems.gas_mask_m65) {
			return armorType == EntityEquipmentSlot.HEAD;
		}
		if (this == ModItems.hazmat_helmet_red) {
			return armorType == EntityEquipmentSlot.HEAD;
		}
		if (this == ModItems.hazmat_helmet_grey) {
			return armorType == EntityEquipmentSlot.HEAD;
		}
		/*if (this == ModItems.oxy_mask) {
			return armorType == 0;
		}
		if (this == ModItems.cape_test) {
			return armorType == 1;
		}
		if (this == ModItems.cape_radiation) {
			return armorType == 1;
		}
		if (this == ModItems.cape_gasmask) {
			return armorType == 1;
		}
		if (this == ModItems.cape_schrabidium) {
			return armorType == 1;
		}*/
		/*if (this == ModItems.cape_hbm) {
			return armorType == 1;
		}
		if (this == ModItems.cape_dafnik) {
			return armorType == 1;
		}
		if (this == ModItems.cape_lpkukin) {
			return armorType == 1;
		}
		if (this == ModItems.cape_vertice) {
			return armorType == 1;
		}
		if (this == ModItems.cape_codered_) {
			return armorType == 1;
		}
		if (this == ModItems.cape_ayy) {
			return armorType == 1;
		}
		if (this == ModItems.cape_nostalgia) {
			return armorType == 1;
		}*/
		return armorType == EntityEquipmentSlot.HEAD;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped _default) {
		if (this == ModItems.goggles) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.modelGoggles == null) {
					this.modelGoggles = new ModelGoggles();
				}
				return this.modelGoggles;
			}
		}
		if (this == ModItems.gas_mask) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.modelGas == null) {
					this.modelGas = new ModelGasMask();
				}
				return this.modelGas;
			}
		}
		if (this == ModItems.gas_mask_m65 || this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey) {
			if (armorSlot == EntityEquipmentSlot.HEAD) {
				if (this.modelM65 == null) {
					this.modelM65 = new ModelM65();
				}
				return this.modelM65;
			}
		}
		/*if (this == ModItems.oxy_mask) {
			if (armorSlot == 0) {
				if (this.modelOxy == null) {
					this.modelOxy = new ModelOxygenMask();
				}
				return this.modelOxy;
			}
		}
		if (this == ModItems.cape_test || this == ModItems.cape_radiation || this == ModItems.cape_gasmask || this == ModItems.cape_schrabidium) {
			if (armorSlot == 1) {
				if (this.modelCloak == null) {
					this.modelCloak = new ModelCloak();
				}
				return this.modelCloak;
			}
		}*/
		/*if (this == ModItems.cape_hbm || this == ModItems.cape_dafnik || this == ModItems.cape_lpkukin || this == ModItems.cape_vertice || this == ModItems.cape_codered_ || this == ModItems.cape_ayy || this == ModItems.cape_nostalgia) {
			if (armorSlot == 1) {
				if (this.modelCloak == null) {
					this.modelCloak = new ModelCloak();
				}
				return this.modelCloak;
			}
		}*/
		return null;
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
		if (stack.getItem() == ModItems.goggles) {
			return "hbm:textures/models/Goggles.png";
		}
		if (stack.getItem() == ModItems.gas_mask) {
			return "hbm:textures/models/GasMask.png";
		}
		if (stack.getItem() == ModItems.gas_mask_m65) {
			return "hbm:textures/models/ModelM65.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_red) {
			return "hbm:textures/models/ModelHazRed.png";
		}
		if (stack.getItem() == ModItems.hazmat_helmet_grey) {
			return "hbm:textures/models/ModelHazGrey.png";
		}
		/*if (stack.getItem() == ModItems.oxy_mask) {
			return null;
		}
		if (stack.getItem() == ModItems.cape_test) {
			return "hbm:textures/models/TestCape.png";
		}
		if (stack.getItem() == ModItems.cape_radiation) {
			return "hbm:textures/models/CapeRadiation.png";
		}
		if (stack.getItem() == ModItems.cape_gasmask) {
			return "hbm:textures/models/CapeGasMask.png";
		}
		if (stack.getItem() == ModItems.cape_schrabidium) {
			return "hbm:textures/models/CapeSchrabidium.png";
		}*/
		/*if (stack.getItem() == ModItems.cape_hbm && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.HbMinecraft)) {
			if(MainRegistry.polaroidID == 11)
				return "hbm:textures/models/CapeHbm.png";
			else
				return "hbm:textures/models/CapeHbm2.png";
		}
		if (stack.getItem() == ModItems.cape_dafnik && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.Dafnik)) {
			return "hbm:textures/models/CapeDafnik.png";
		}
		if (stack.getItem() == ModItems.cape_lpkukin && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.LPkukin)) {
			return "hbm:textures/models/CapeShield.png";
		}
		if (stack.getItem() == ModItems.cape_vertice && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.LordVertice)) {
			return "hbm:textures/models/CapeVertice_2.png";
		}
		if (stack.getItem() == ModItems.cape_codered_ && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.CodeRed_)) {
			return "hbm:textures/models/CapeRed.png";
		}
		if (stack.getItem() == ModItems.cape_ayy && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.dxmaster769)) {
			return "hbm:textures/models/CapeAyy.png";
		}
		if (stack.getItem() == ModItems.cape_nostalgia && entity instanceof EntityPlayer && ((EntityPlayer)entity).getUniqueID().toString().equals(Library.nostalgia)) {
			return "hbm:textures/models/CapeNostalgia.png";
		}*/
		return "hbm:textures/models/CapeUnknown.png";
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
		if(this != ModItems.goggles && this != ModItems.gas_mask && this != ModItems.gas_mask_m65 && this != ModItems.hazmat_helmet_red && this != ModItems.hazmat_helmet_grey)
    		return;
    	

        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glDepthMask(false);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        
        if(this == ModItems.goggles || this == ModItems.gas_mask_m65 || this == ModItems.hazmat_helmet_red || this == ModItems.hazmat_helmet_grey) {
        	switch((int)((double)stack.getItemDamage() / (double)stack.getMaxDamage() * 6D)) {
        	case 0:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur0); break;
        	case 1:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur1); break;
        	case 2:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur2); break;
        	case 3:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur3); break;
        	case 4:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur4); break;
        	case 5:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur5); break;
        	default:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(goggleBlur5); break;
        	}
        }
        if(this == ModItems.gas_mask) {
        	switch((int)((double)stack.getItemDamage() / (double)stack.getMaxDamage() * 6D)) {
        	case 0:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur0); break;
        	case 1:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur1); break;
        	case 2:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur2); break;
        	case 3:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur3); break;
        	case 4:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur4); break;
        	case 5:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur5); break;
        	default:
            	Minecraft.getMinecraft().getTextureManager().bindTexture(gasmaskBlur5); break;
        	}
        }
        
        RenderHelper.startDrawingTexturedQuads();
        RenderHelper.addVertexWithUV(0.0D, (double)resolution.getScaledHeight(), -90.0D, 0.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), (double)resolution.getScaledHeight(), -90.0D, 1.0D, 1.0D);
        RenderHelper.addVertexWithUV((double)resolution.getScaledWidth(), 0.0D, -90.0D, 1.0D, 0.0D);
        RenderHelper.addVertexWithUV(0.0D, 0.0D, -90.0D, 0.0D, 0.0D);
        RenderHelper.draw();
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		/*if (stack.getItem() == ModItems.cape_radiation) {
			list.add("Avalible for everyone");
		}
		if (stack.getItem() == ModItems.cape_gasmask) {
			list.add("Avalible for everyone");
		}
		if (stack.getItem() == ModItems.cape_schrabidium) {
			list.add("Avalible for everyone");
		}*/
	}
	
}
