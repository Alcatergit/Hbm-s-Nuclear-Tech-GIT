package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBulletBase;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderSparks;
import com.hbm.render.item.weapon.ItemRenderFatMan;
import com.hbm.render.model.ModelBuckshot;
import com.hbm.render.model.ModelBullet;
import com.hbm.render.model.ModelRocket;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderBulletMk2 extends Render<EntityBulletBase> {

	public static final IRenderFactory<EntityBulletBase> FACTORY = (RenderManager man) -> {
		return new RenderBulletMk2(man);
	};

	private ModelBullet bullet;
	private ModelBuckshot buckshot;
	private ModelRocket rocket;

	private ResourceLocation bullet_rl = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/bullet.png");
	private ResourceLocation emplacer = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/emplacer.png");
	private ResourceLocation tau = new ResourceLocation(RefStrings.MODID + ":textures/models/projectiles/tau.png");
	private ResourceLocation buckshot_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/buckshot.png");
	private ResourceLocation rocket_rl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocket.png");
	private ResourceLocation rocket_he = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketHE.png");
	private ResourceLocation rocket_in = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketIncendiary.png");
	private ResourceLocation rocket_sh = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketShrapnel.png");
	private ResourceLocation rocket_emp = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketEMP.png");
	private ResourceLocation rocket_gl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketGlare.png");
	private ResourceLocation rocket_sl = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketSleek.png");
	private ResourceLocation rocket_nu = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketNuclear.png");
	private ResourceLocation rocket_phos = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketPhosphorus.png");
	private ResourceLocation rocket_can = new ResourceLocation(RefStrings.MODID + ":textures/entity/ModelRocketCanister.png");

	protected RenderBulletMk2(RenderManager renderManager) {
		super(renderManager);
		bullet = new ModelBullet();
		buckshot = new ModelBuckshot();
		rocket = new ModelRocket();
	}

	@Override
	public void doRender(EntityBulletBase bullet, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		
		int style = bullet.getDataManager().get(EntityBulletBase.STYLE);
		int trail = bullet.getDataManager().get(EntityBulletBase.TRAIL);
		
		GL11.glTranslatef((float) x, (float) y, (float) z);
		if(style != BulletConfiguration.STYLE_TRACER){
			GL11.glRotatef(bullet.prevRotationYaw + (bullet.rotationYaw - bullet.prevRotationYaw) * partialTicks - 90.0F, 0.0F, 1.0F, 0.0F);
			GL11.glRotatef(bullet.prevRotationPitch + (bullet.rotationPitch - bullet.prevRotationPitch) * partialTicks + 180, 0.0F, 0.0F, 1.0F);
			
			GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glRotatef(new Random(bullet.getEntityId()).nextInt(90) - 45, 1.0F, 0.0F, 0.0F);
		}

		switch (style) {
		case BulletConfiguration.STYLE_NONE:
			break;
		case BulletConfiguration.STYLE_NORMAL:
			renderBullet(trail);
			break;
		case BulletConfiguration.STYLE_BOLT:
			renderDart(trail, bullet.getEntityId());
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
		case BulletConfiguration.STYLE_NUKE: 
			renderNuke(0, bullet.getDataManager().get(EntityBulletBase.BULLETCONFIG)); 
			break;
		case BulletConfiguration.STYLE_MIRV: 
			renderNuke(1, bullet.getDataManager().get(EntityBulletBase.BULLETCONFIG)); 
			break;
		case BulletConfiguration.STYLE_BF: 
			renderNuke(2, bullet.getDataManager().get(EntityBulletBase.BULLETCONFIG)); 
			break;
		case BulletConfiguration.STYLE_ORB:
			renderOrb(trail);
			break;
		case BulletConfiguration.STYLE_METEOR: 
			renderMeteor(trail); 
			break;
		case BulletConfiguration.STYLE_TRACER:
			renderTracer(new Vec3d(bullet.motionX, bullet.motionY, bullet.motionZ).normalize(), bullet.getPositionEyes(partialTicks), partialTicks);
			break;
		case BulletConfiguration.STYLE_APDS:
			renderAPDS(); 
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
		case 9:
			bindTexture(rocket_phos);
			break;
		case 10:
			bindTexture(rocket_can);
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
		GL11.glRotatef(90, 0, 0, 1);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.grenade_tex);
		ResourceManager.projectiles.renderPart("Grenade");
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
	
	private void renderNuke(int type, int config) {

		GL11.glScalef(0.0833F, 0.0833F, 0.0833F);
		GL11.glRotatef(180, 1, 0, -1);
		GL11.glTranslatef(0, -1, 1);
		GlStateManager.shadeModel(GL11.GL_SMOOTH);

		switch(type) {
		case 0:
			ItemRenderFatMan.bindNuke(config);
			ResourceManager.fatman.renderPart("MiniNuke");
			ResourceManager.fatman.renderPart("MiniNukeFins");
			break;
		case 1:
			ItemRenderFatMan.bindMirv(config);
			ResourceManager.fatman.renderPart("MiniMIRV");
			break;
		case 2:
			bindTexture(ResourceManager.fatman_balefire_tex);
			ResourceManager.fatman.renderPart("MiniNuke");
			ResourceManager.fatman.renderPart("MiniNukeFins");
			renderBalefireGlint();
			break;
		}

		GlStateManager.shadeModel(GL11.GL_FLAT);
	}

	private void renderBalefireGlint() {
		Minecraft mc = Minecraft.getMinecraft();

		// Preserve original state
		boolean wasLighting = GL11.glIsEnabled(GL11.GL_LIGHTING);
		boolean wasBlend = GL11.glIsEnabled(GL11.GL_BLEND);
		boolean wasDepthMask = GL11.glGetBoolean(GL11.GL_DEPTH_WRITEMASK);
		int prevDepthFunc = GL11.glGetInteger(GL11.GL_DEPTH_FUNC);
		int prevBlendSrc = GL11.glGetInteger(GL11.GL_BLEND_SRC);
		int prevBlendDst = GL11.glGetInteger(GL11.GL_BLEND_DST);

		// Kill the lightmap multiply; only the enable flag is touched, unlike disableLightmap/enableLightmap
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		boolean wasLightmapOn = GL11.glIsEnabled(GL11.GL_TEXTURE_2D);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);

		mc.renderEngine.bindTexture(new ResourceLocation(RefStrings.MODID, "textures/misc/glintBF.png"));

		float scale = 2F;
		float r = 0F, g = 0.8F, b = 0.15F;
		float speed = -6F;
		float glintColor = 0.76F;
		int layers = 3;
		float interp = mc.getRenderPartialTicks();

		float offset = mc.player.ticksExisted + interp;

		GlStateManager.disableLighting();
		GlStateManager.enableBlend();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.depthFunc(GL11.GL_EQUAL);
		GlStateManager.depthMask(false);
		GlStateManager.blendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);

		GlStateManager.matrixMode(GL11.GL_TEXTURE);
		GlStateManager.pushMatrix();

		for(int k = 0; k < layers; ++k) {
			GlStateManager.loadIdentity();
			float movement = offset * (0.001F + k * 0.003F) * speed;
			GlStateManager.scale(scale, scale, scale);
			GlStateManager.rotate(30.0F - k * 60.0F, 0, 0, 1);
			GlStateManager.translate(0F, movement, 0F);
			GlStateManager.matrixMode(GL11.GL_MODELVIEW);
			GlStateManager.color(r * glintColor, g * glintColor, b * glintColor, 1.0F);
			ResourceManager.fatman.renderPart("MiniNuke");
			GlStateManager.matrixMode(GL11.GL_TEXTURE);
		}

		GlStateManager.popMatrix();
		GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		// Restore to original state
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.depthMask(wasDepthMask);
		GlStateManager.depthFunc(prevDepthFunc);
		GlStateManager.tryBlendFuncSeparate(prevBlendSrc, prevBlendDst, prevBlendSrc, prevBlendDst);
		if(wasBlend) GlStateManager.enableBlend(); else GlStateManager.disableBlend();
		if(wasLighting) GlStateManager.enableLighting(); else GlStateManager.disableLighting();

		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		if(wasLightmapOn) GlStateManager.enableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
	
	private void renderFlechette() {
		GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
		GlStateManager.disableLighting();
		
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
		

        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
		
		GL11.glPopMatrix();
	}
	
	private void renderDart(int style, int eID) {
		
		
		float red = 1F;
		float green = 1F;
		float blue = 1F;
		switch(style) {
		case BulletConfiguration.BOLT_LASER: red = 1F; green = 0F; blue = 0F; break;
		case BulletConfiguration.BOLT_NIGHTMARE: red = 1F; green = 1F; blue = 0F; break;
		case BulletConfiguration.BOLT_LACUNAE: red = 0.25F; green = 0F; blue = 0.75F; break;
		case BulletConfiguration.BOLT_WORM: red = 0F; green = 1F; blue = 0F; break;
		case BulletConfiguration.BOLT_ZOMG:
			Random rand = new Random(eID * eID);
			red = rand.nextInt(2) * 0.8F;
			green = rand.nextInt(2) * 0.8F;
			blue = rand.nextInt(2) * 0.8F;
			break;
		}
		
		GL11.glPushMatrix();
        GlStateManager.disableTexture2D();
        GlStateManager.disableCull();
        GlStateManager.disableLighting();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.depthMask(false);

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
		
        GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableCull();
        GlStateManager.depthMask(true);
		
		GL11.glPopMatrix();
	}
	
	private void renderOrb(int type) {

        GlStateManager.enableCull();
        GlStateManager.disableLighting();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GlStateManager.depthMask(false);
		
		switch(type) {
		case 0:
			bindTexture(ResourceManager.tom_flame_tex);
			ResourceManager.sphere_uv_anim.renderAll();
			GL11.glScalef(0.3F, 0.3F, 0.3F);
			ResourceManager.sphere_uv_anim.renderAll();
			GL11.glScalef(1F/0.3F, 1F/0.3F, 1F/0.3F);
			for(int i = 0; i < 5; i++)
				RenderSparks.renderSpark((int) (System.currentTimeMillis() / 100 + 100 * i), 0, 0, 0, 0.5F, 2, 2, 0x8080FF, 0xFFFFFF);
			break;
		case 1:
			GL11.glScalef(0.5F, 0.5F, 0.5F);
			GlStateManager.disableTexture2D();
			GlStateManager.color(0.5F, 0.0F, 0.0F, 0.5F);
			ResourceManager.sphere_uv.renderAll();
			GL11.glScalef(0.75F, 0.75F, 0.75F);
			ResourceManager.sphere_uv.renderAll();
			GL11.glScalef(1F/0.75F, 1F/0.75F, 1F/0.75F);
			GlStateManager.enableTexture2D();
			for(int i = 0; i < 3; i++)
				RenderSparks.renderSpark((int) (System.currentTimeMillis() / 100 + 100 * i), 0, 0, 0, 1F, 2, 3, 0xFF0000, 0xFF8080);
			break;
		}
		
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.depthMask(true);

	}
	
	private void renderMeteor(int type) {

        GlStateManager.enableCull();
        GlStateManager.disableLighting();

		switch(type) {
		case 0:
			bindTexture(new ResourceLocation(RefStrings.MODID + ":textures/blocks/block_meteor_molten.png")); break;
		case 1:
			bindTexture(new ResourceLocation("textures/blocks/obsidian.png")); break;
		}

		ResourceManager.meteor.renderAll();

        GlStateManager.enableLighting();
	}

	private void renderTracer(Vec3d bulletDirection, Vec3d bulletPos, float partialTicks) {
		Entity rv = Minecraft.getMinecraft().getRenderViewEntity();
		double eyeX = rv.prevPosX + (rv.posX-rv.prevPosX)*partialTicks;
		double eyeY = rv.prevPosY + (rv.posY-rv.prevPosY)*partialTicks;
		double eyeZ = rv.prevPosZ + (rv.posZ-rv.prevPosZ)*partialTicks;
		Vec3d eyePos = new Vec3d(eyeX, eyeY, eyeZ).add(ActiveRenderInfo.getCameraPosition());
		Vec3d tan = eyePos.subtract(bulletPos).crossProduct(bulletDirection).normalize().scale(0.05);
		bulletDirection = bulletDirection.scale(10);
		
		bindTexture(ResourceManager.fresnel_ms);
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		GlStateManager.color(1, 0.7F, 0.5F, 1);
		GlStateManager.disableLighting();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.disableCull();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		buf.pos(tan.x, tan.y, tan.z).tex(1, 1).endVertex();
		buf.pos(tan.x+bulletDirection.x, tan.y+bulletDirection.y, tan.z+bulletDirection.z).tex(1, 0).endVertex();
		buf.pos(-tan.x+bulletDirection.x, -tan.y+bulletDirection.y, -tan.z+bulletDirection.z).tex(0, 0).endVertex();
		buf.pos(-tan.x, -tan.y, -tan.z).tex(0, 1).endVertex();
		buf.pos(tan.x, tan.y, tan.z).tex(1, 1).endVertex();
		buf.pos(tan.x+bulletDirection.x, tan.y+bulletDirection.y, tan.z+bulletDirection.z).tex(1, 0).endVertex();
		buf.pos(-tan.x+bulletDirection.x, -tan.y+bulletDirection.y, -tan.z+bulletDirection.z).tex(0, 0).endVertex();
		buf.pos(-tan.x, -tan.y, -tan.z).tex(0, 1).endVertex();
		tes.draw();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.enableCull();
		GlStateManager.depthMask(true);
		GlStateManager.disableBlend();
		GlStateManager.enableLighting();
	}
	
	private void renderAPDS() {
		
		GL11.glScaled(2, 2, 2);
		GL11.glRotated(90, 0, 0, 1);
		GL11.glRotated(90, 0, 1, 0);
		
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		bindTexture(ResourceManager.flechette_tex);
		ResourceManager.projectiles.renderPart("Flechette");
		GlStateManager.shadeModel(GL11.GL_FLAT);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBulletBase entity) {
		return bullet_rl;
	}

}
