package com.hbm.render.entity.item;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.entity.item.EntityMovingPackage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.lwjgl.opengl.GL11;

public class RenderMovingPackage extends Render<EntityMovingPackage> {

    private ItemStack dummy;

    public static final IRenderFactory<EntityMovingPackage> FACTORY = man -> new RenderMovingPackage(man);

    protected RenderMovingPackage(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityMovingPackage entity, double x, double y, double z, float f1, float f2) {

        GL11.glPushMatrix();
        GL11.glTranslated(x, y + 0.3125, z);

        if(this.dummy == null) {
            this.dummy = new ItemStack(ModBlocks.crate);
        }

        double scale = 1.25;
        GL11.glScaled(scale, scale, scale);
        IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(dummy, entity.world, null);
        model = ForgeHooksClient.handleCameraTransforms(model, ItemCameraTransforms.TransformType.FIXED, false);
        Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        Minecraft.getMinecraft().getRenderItem().renderItem(dummy, model);

        GL11.glPopMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityMovingPackage p_110775_1_) {
        return null;
    }
}
