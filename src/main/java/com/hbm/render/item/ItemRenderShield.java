package com.hbm.render.item;

import net.minecraft.client.model.ModelShield;
import net.minecraft.client.renderer.BannerTextures;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.tileentity.TileEntityBanner;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderShield extends TEISRBase {

    private final TileEntityBanner banner = new TileEntityBanner();
    private final ModelShield modelShield = new ModelShield();
    private final ResourceLocation tex;
    private final BannerTextures.Cache shieldDesings;

    public ItemRenderShield(String id, ResourceLocation texture, ResourceLocation textureBlank){
        tex = texture;
        shieldDesings = new BannerTextures.Cache(id, textureBlank, "textures/entity/shield/");
    }

    @Override
	public void renderByItem(ItemStack itemStackIn) {

        if (itemStackIn.getSubCompound("BlockEntityTag") != null) {
            this.banner.setItemValues(itemStackIn, true);
            Minecraft.getMinecraft().getTextureManager().bindTexture(shieldDesings.getResourceLocation(this.banner.getPatternResourceLocation(), this.banner.getPatternList(), this.banner.getColorList()));
        } else {
            Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        }

        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0F, -1.0F, -1.0F);
        this.modelShield.render();
        GlStateManager.popMatrix();
	}
}
