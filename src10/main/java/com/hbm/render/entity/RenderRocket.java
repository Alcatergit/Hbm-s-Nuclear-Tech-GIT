package com.hbm.render.entity;

import com.hbm.entity.projectile.EntityRocket;
import com.hbm.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderRocket extends Render<EntityRocket> {

	public static final IRenderFactory<EntityRocket> FACTORY = (RenderManager man) -> {return new RenderRocket(man, ModItems.man_core);};
	
	protected final Item item;
    private RenderItem itemRenderer = null;

    public RenderRocket(RenderManager renderManagerIn, Item itemIn)
    {
        super(renderManagerIn);
        this.item = itemIn;
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityRocket entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	if(itemRenderer == null)
    		itemRenderer = Minecraft.getMinecraft().getRenderItem();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(this.renderManager.options.thirdPersonView == 2 ? -1 : 1) * this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

        if (this.renderOutlines)
        {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        this.itemRenderer.renderItem(this.getStackToRender(entity), ItemCameraTransforms.TransformType.GROUND);

        if (this.renderOutlines)
        {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    @Override
    public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

    public ItemStack getStackToRender(EntityRocket entityIn)
    {
        return new ItemStack(this.item);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityRocket entity)
    {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

}
