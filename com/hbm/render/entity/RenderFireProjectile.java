package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;
import com.hbm.entity.projectile.EntityFire;
import com.hbm.items.ModItems;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFireProjectile extends Render<EntityFire> {

	public static final IRenderFactory<EntityFire> FACTORY = (RenderManager man) -> {return new RenderFireProjectile(man, ModItems.flame_1, 0);};
	
	Item item;
	int meta;
	
	protected RenderFireProjectile(RenderManager renderManager, Item item, int meta) {
		super(renderManager);
		this.item = item;
		this.meta = meta;
	}
	
	@Override
	public void doRender(EntityFire fx, double x, double y, double z, float entityYaw, float partialTicks) {

		if(fx.ticksExisted <= fx.maxAge && fx.ticksExisted >= fx.maxAge / 10 * 9)
		{
			item = ModItems.flame_10;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 9 && fx.ticksExisted >= fx.maxAge / 10 * 8)
		{
			item = ModItems.flame_9;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 8 && fx.ticksExisted >= fx.maxAge / 10 * 7)
		{
			item = ModItems.flame_8;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 7 && fx.ticksExisted >= fx.maxAge / 10 * 6)
		{
			item = ModItems.flame_7;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 6 && fx.ticksExisted >= fx.maxAge / 10 * 5)
		{
			item = ModItems.flame_6;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 5 && fx.ticksExisted >= fx.maxAge / 10 * 4)
		{
			item = ModItems.flame_5;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 4 && fx.ticksExisted >= fx.maxAge / 10 * 3)
		{
			item = ModItems.flame_4;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 3 && fx.ticksExisted >= fx.maxAge / 10 * 2)
		{
			item = ModItems.flame_3;
		}

		if(fx.ticksExisted < fx.maxAge / 10 * 2 && fx.ticksExisted >= fx.maxAge / 10 * 1)
		{
			item = ModItems.flame_2;
		}
		
		if(fx.ticksExisted < fx.maxAge / 10 && fx.ticksExisted >= 0 && !fx.isDead)
		{
			item = ModItems.flame_1;
		}
		
		TextureAtlasSprite iicon = RenderHelper.getItemTexture(item);

        if (iicon != null)
        {
            GL11.glPushMatrix();
            GlStateManager.disableLighting();
            GL11.glTranslatef((float)x, (float)y, (float)z);
            GlStateManager.enableRescaleNormal();
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            GL11.glScalef(7.5F, 7.5F, 7.5F);
            GL11.glTranslatef(0.0F, -0.25F, 0.0F);
            this.bindEntityTexture(fx);
            Tessellator tessellator = Tessellator.getInstance();

            this.func_77026_a(tessellator, iicon);
            GlStateManager.disableRescaleNormal();
            GlStateManager.enableLighting();
            GL11.glPopMatrix();
        }
	}
	
	private void func_77026_a(Tessellator tes, TextureAtlasSprite p_77026_2_)
    {
        float f = p_77026_2_.getMinU();
        float f1 = p_77026_2_.getMaxU();
        float f2 = p_77026_2_.getMinV();
        float f3 = p_77026_2_.getMaxV();
        float f4 = 1.0F;
        float f5 = 0.5F;
        float f6 = 0.25F;
        GL11.glRotatef(180.0F - this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(-this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
        RenderHelper.startDrawingTexturedQuads(tes);
        //Drillgon200: I hope this setNormal isn't needed for anything
        //p_77026_1_.setNormal(0.0F, 1.0F, 0.0F);
        RenderHelper.addVertexWithUV(0.0F - f5, 0.0F - f6, 0.0D, f, f3, tes);
        RenderHelper.addVertexWithUV(f4 - f5, 0.0F - f6, 0.0D, f1, f3, tes);
        RenderHelper.addVertexWithUV(f4 - f5, f4 - f6, 0.0D, f1, f2, tes);
        RenderHelper.addVertexWithUV(0.0F - f5, f4 - f6, 0.0D, f, f2, tes);
        tes.draw();
    }
	
	@Override
	public void doRenderShadowAndFire(Entity entityIn, double x, double y, double z, float yaw, float partialTicks) {}

	@Override
	protected ResourceLocation getEntityTexture(EntityFire entity) {
		return TextureMap.LOCATION_BLOCKS_TEXTURE;
	}

}
