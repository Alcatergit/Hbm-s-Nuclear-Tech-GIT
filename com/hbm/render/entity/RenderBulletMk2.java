package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBuckshot;
import com.hbm.render.model.ModelBullet;
import com.hbm.render.model.ModelGrenade;
import com.hbm.render.model.ModelRocket;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBulletMk2 extends Render<EntityBulletBase> {

	public static final IRenderFactory<EntityBulletBase> FACTORY = (RenderManager man) -> {
		return new RenderBulletMk2(man);
	};

	private ModelBullet bullet;
	private ModelBuckshot buckshot;
	private ModelRocket rocket;
	private ModelGrenade grenade;

	private ResourceLocation bullet_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/bullet.png");
	private ResourceLocation emplacer = new ResourceLocation(RefStrings.MODID + ":textures/models/emplacer.png");
	private ResourceLocation tau = new ResourceLocation(RefStrings.MODID + ":textures/models/tau.png");
	private ResourceLocation buckshot_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/buckshot.png");
	private ResourceLocation rocket_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocket.png");
	private ResourceLocation rocket_he = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketHE.png");
	private ResourceLocation rocket_in = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketIncendiary.png");
	private ResourceLocation rocket_sh = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketShrapnel.png");
	private ResourceLocation rocket_emp = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketEMP.png");
	private ResourceLocation rocket_gl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketGlare.png");
	private ResourceLocation rocket_sl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketSleek.png");
	private ResourceLocation rocket_nu = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketNuclear.png");
	private ResourceLocation grenade_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenade.png");
	private ResourceLocation grenade_he = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeHE.png");
	private ResourceLocation grenade_in = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeIncendiary.png");
	private ResourceLocation grenade_to = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeToxic.png");
	private ResourceLocation grenade_sl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelGrenadeSleek.png");

	protected RenderBulletMk2(RenderManager renderManager) {
		super(renderManager);
		bullet = new ModelBullet();
		buckshot = new ModelBuckshot();
		rocket = new ModelRocket();
		grenade = new ModelGrenade();
	}

	@Override
	public void doRender(EntityBulletBase bullet, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslatef((float) x, (float) y, (float) z);
		GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * partialTicks + 180, 0.0F, 0.0F, 1.0F);
		GL11.glScalef(1.5F, 1.5F, 1.5F);

		GL11.glRotatef(new Random(bullet.getEntityId()).nextInt(90) - 45, 1.0F, 0.0F, 0.0F);

		int style = bullet.getDataManager().get(EntityBulletBase.STYLE);
		int trail = bullet.getDataManager().get(EntityBulletBase.TRAIL);

		switch (style) {
		case BulletConfiguration.STYLE_NORMAL:
			renderBullet(trail);
			break;
		case BulletConfiguration.STYLE_BOLT:
			renderDart(trail);
			break;
		case BulletConfiguration.STYLE_FLECHETTE:
			renderFlechette();
			break;
		case BulletConfiguration.STYLE_FOLLY:
			renderBullet(trail);
			break;
		case BulletConfiguration.STYLE_PELLET:
			renderBuckshot();
			break;
		case BulletConfiguration.STYLE_ROCKET:
			renderRocket(trail);
			break;
		case BulletConfiguration.STYLE_GRENADE:
			renderGrenade(trail);
			break;
		default:
			renderBullet(trail);
			break;
		}

		GL11.glPopMatrix();
	}

	private void renderBullet(int type) {

		if (type == 2) {
			bindTexture(emplacer);
		} else if (type == 1) {
			bindTexture(tau);
		} else if (type == 0) {
			bindTexture(bullet_rl);
		}

		bullet.renderAll(0.0625F);
	}

	private void renderBuckshot() {

		bindTexture(buckshot_rl);

		buckshot.renderAll(0.0625F);
	}

	private void renderRocket(int type) {

		switch (type) {
		case 0:
			bindTexture(rocket_rl);
			break;
		case 1:
			bindTexture(rocket_he);
			break;
		case 2:
			bindTexture(rocket_in);
			break;
		case 3:
			bindTexture(rocket_sh);
			break;
		case 4:
			bindTexture(rocket_emp);
			break;
		case 5:
			bindTexture(rocket_gl);
			break;
		case 6:
			bindTexture(rocket_sl);
			break;
		case 7:
			bindTexture(rocket_nu);
			break;
		}

		if (type == 8) {
			bindTexture(ResourceManager.rpc_tex);
			GL11.glScalef(0.25F, 0.25F, 0.25F);
			GL11.glRotatef(180, 1, 0, 0);
			ResourceManager.rpc.renderAll();
			return;
		}

		rocket.renderAll(0.0625F);
	}

	private void renderGrenade(int type) {

		GL11.glScalef(0.25F, 0.25F, 0.25F);

		switch (type) {
		case 0:
			bindTexture(grenade_rl);
			break;
		case 1:
			bindTexture(grenade_he);
			break;
		case 2:
			bindTexture(grenade_in);
			break;
		case 3:
			bindTexture(grenade_to);
			break;
		case 4:
			bindTexture(grenade_sl);
			break;
		}

		grenade.renderAll(0.0625F);
	}
	
	private void renderFlechette() {
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_LIGHTING);
		
        GL11.glScalef(1F/16F, 1F/16F, 1F/16F);
        GL11.glScalef(-1, 1, 1);
        
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		
		//Drillgon200: Removed all those extra draw calls that just cause extra lag.
		//back
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		buf.pos(0, -1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, 1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, 1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, -1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		
		//base
		buf.pos(0, -1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, 1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(1, -0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, -1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, 1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(1, -0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, -1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, -1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(0, 1, -1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(0, 1, 1).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		tess.draw();
		
		//pin
		buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		
		buf.pos(1, 0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(6, 0, 0).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(6, 0, 0).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(6, 0, 0).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, -0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();

		buf.pos(1, 0.5, 0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(1, 0.5, -0.5).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		buf.pos(6, 0, 0).color(0.15F, 0.15F, 0.15F, 1.0F).endVertex();
		
		tess.draw();
		

        GL11.glEnable(GL11.GL_TEXTURE_2D);
		
		GL11.glPopMatrix();
	}
	
	private void renderDart(int style) {
		
		float red = 1F;
		float green = 1F;
		float blue = 1F;
		
		switch(style) {
		case BulletConfiguration.BOLT_LASER: red = 1F; green = 0F; blue = 0F; break;
		case BulletConfiguration.BOLT_NIGHTMARE: red = 1F; green = 1F; blue = 0F; break;
		case BulletConfiguration.BOLT_LACUNAE: red = 0.25F; green = 0F; blue = 0.75F; break;
		}
		
		GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glDepthMask(false);

        GL11.glScalef(1F/4F, 1F/8F, 1F/8F);
        GL11.glScalef(-1, 1, 1);

        GL11.glScalef(2, 2, 2);
        
		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		
		//front
		buf.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_COLOR);
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(3, -1, -1).color(red, green, blue, 0).endVertex();
		buf.pos(3, 1, -1).color(red, green, blue, 0).endVertex();

		buf.pos(3, -1, 1).color(red, green, blue, 0).endVertex();
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(3, 1, 1).color(red, green, blue, 0).endVertex();

		buf.pos(3, -1, -1).color(red, green, blue, 0).endVertex();
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(3, -1, 1).color(red, green, blue, 0).endVertex();

		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(3, 1, -1).color(red, green, blue, 0).endVertex();
		buf.pos(3, 1, 1).color(red, green, blue, 0).endVertex();
		
		//mid
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(4, -0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, -0.5).color(red, green, blue, 1).endVertex();

		buf.pos(4, -0.5, 0.5).color(red, green, blue, 1).endVertex();
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, 0.5).color(red, green, blue, 1).endVertex();

		buf.pos(4, -0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(4, -0.5, 0.5).color(red, green, blue, 1).endVertex();

		buf.pos(6, 0, 0).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, 0.5).color(red, green, blue, 1).endVertex();
		
		tess.draw();
		
		//tail
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_COLOR);
		
		buf.pos(4, 0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, 0.5).color(red, green, blue, 1).endVertex();
		buf.pos(0, 0.5, 0.5).color(red, green, blue, 0).endVertex();
		buf.pos(0, 0.5, -0.5).color(red, green, blue, 0).endVertex();

		
		buf.pos(4, -0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, -0.5, 0.5).color(red, green, blue, 1).endVertex();
		buf.pos(0, -0.5, 0.5).color(red, green, blue, 0).endVertex();
		buf.pos(0, -0.5, -0.5).color(red, green, blue, 0).endVertex();

		buf.pos(4, -0.5, 0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, 0.5).color(red, green, blue, 1).endVertex();
		buf.pos(0, 0.5, 0.5).color(red, green, blue, 0).endVertex();
		buf.pos(0, -0.5, 0.5).color(red, green, blue, 0).endVertex();

		buf.pos(4, -0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(4, 0.5, -0.5).color(red, green, blue, 1).endVertex();
		buf.pos(0, 0.5, -0.5).color(red, green, blue, 0).endVertex();
		buf.pos(0, -0.5, -0.5).color(red, green, blue, 0).endVertex();
		
		tess.draw();
		
        GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glDepthMask(true);
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBulletBase entity) {
		return bullet_rl;
	}

}
