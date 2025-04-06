package com.hbm.render.item;

import com.hbm.dim.SolarSystem;
import com.hbm.lib.RefStrings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class VOTVDriveRender extends TEISRBase{
    public static final VOTVDriveRender INSTANCE = new VOTVDriveRender();

    @Override
    public void renderByItem(ItemStack stack, float partialTicks) {
        int metadata = stack.getMetadata();
        ResourceLocation texture = getTextureFromDamage(metadata);
        Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
        GlStateManager.pushMatrix();
        if (!doNullTransform()) {
            GlStateManager.translate(0.5F, 0.5F, 0.5F);
        }
        Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);

        GlStateManager.popMatrix();
    }

    @SideOnly(Side.CLIENT)
    public ResourceLocation getTextureFromDamage(int metadata) {
        SolarSystem.Body destinationType = SolarSystem.Body.values()[metadata];

        if (destinationType == SolarSystem.Body.ORBIT) {
            return new ResourceLocation(RefStrings.MODID, "textures/items/votv_f0.png");
        }

        int processingLevel = destinationType.getProcessingLevel();
        if (processingLevel >= 0 && processingLevel < 4) {
            return new ResourceLocation(RefStrings.MODID, "textures/items/votv_f" + processingLevel + ".png");
        }

        // Default to the base texture for unprocessed drives
        return new ResourceLocation(RefStrings.MODID, "textures/items/votv_f0.png");
    }
}
