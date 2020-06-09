package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityRailgunBlast;
import com.hbm.entity.projectile.EntityTom;
import com.hbm.main.ClientProxy;
import com.hbm.render.misc.TomPronter2;
import com.hbm.render.util.TomPronter;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderTom<T extends Entity> extends Render<T> {

	public static final IRenderFactory<EntityRailgunBlast> RAIL_FACTORY = (RenderManager man) -> {
		return new RenderTom<EntityRailgunBlast>(man);
	};
	public static final IRenderFactory<EntityTom> TOM_FACTORY = (RenderManager man) -> {
		return new RenderTom<EntityTom>(man);
	};

	protected RenderTom(RenderManager renderManager) {
		super(renderManager);
	}

	@Override
	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		if(entity instanceof EntityRailgunBlast) {
			GL11.glPushMatrix();
			GL11.glTranslated(x, y, z);
			GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 0.0F, 0.0F, 1.0F);

			int i = 0;

			// if(entity instanceof EntityShell || entity instanceof
			// EntityMissileShell)
			// i = 1;

			TomPronter.prontTom(i);
			GL11.glPopMatrix();
		} else if(entity instanceof EntityTom) {
			if(ClientProxy.renderingConstant) {
				GL11.glPushMatrix();
				GL11.glTranslated(x, y - 50, z);

				TomPronter2.prontTom();
				GL11.glPopMatrix();
			}
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(T entity) {
		return null;
	}

}
