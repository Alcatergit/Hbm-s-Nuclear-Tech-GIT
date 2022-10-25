package com.hbm.render.entity.effect;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.effect.EntityNukeCloudSmall.Cloudlet;
import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderSmallNukeMK4 extends Render<EntityNukeCloudSmall> {

	public static final IRenderFactory<EntityNukeCloudSmall> FACTORY = man -> new RenderSmallNukeMK4(man);
	
	public static final IModelCustom mush = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/mush.obj"));
	public static final IModelCustom shockwave = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/ring_roller.obj"));
	public static final IModelCustom thinring = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/effect/ring_thin.obj"));
	private static final ResourceLocation cloudlet = new ResourceLocation(RefStrings.MODID + ":textures/particle/particle_base.png");
	
	protected RenderSmallNukeMK4(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityNukeCloudSmall cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        mushWrapper(cloud, partialTicks);
        cloudletWrapper(cloud, partialTicks);
        flashWrapper(cloud, partialTicks);

		GL11.glPopMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNukeCloudSmall entity) {
		return null;
	}
	
	/*
	 *     //      //  //////  //////  //////  //////  //////  //////  //////
	 *    //      //  //  //  //  //  //  //  //  //  //      //  //  //
	 *   //  //  //  ////    //////  //////  //////  ////    ////    //////
	 *  ////  ////  //  //  //  //  //      //      //      //  //      //
	 * //      //  //  //  //  //  //      //      //////  //  //  //////
	 */
	
	/**
	 * Wrapper for the initial flash
	 * Caps the rendering at 60 ticks and sets the alpha function
	 * @param cloud
	 * @param interp
	 */
	private void flashWrapper(EntityNukeCloudSmall cloud, float interp) {

        if(cloud.age < 60) {

    		GL11.glPushMatrix();
    		//Function [0, 1] that determines the scale and intensity (inverse!) of the flash
        	double scale = (cloud.ticksExisted + interp) / 60D;
        	GlStateManager.alphaFunc(GL11.GL_GREATER, 0.0F);

        	//Euler function to slow down the scale as it progresses
        	//Makes it start fast and the fade-out is nice and smooth
        	scale = scale * Math.pow(Math.E, -scale) * 2.717391304D;

        	renderFlash(scale);
            GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
    		GL11.glPopMatrix();
        }
	}

	private ResourceLocation getMushroomTexture(float cloudAgePercent, boolean isBalefire){
		if(isBalefire){
			if(cloudAgePercent < 0.010)
				return ResourceManager.balefire_0;
			else if(cloudAgePercent < 0.15)
				return ResourceManager.balefire_1;
			else if(cloudAgePercent < 0.19)
				return ResourceManager.balefire_2;
			else if(cloudAgePercent < 0.23)
				return ResourceManager.balefire_3;
			else if(cloudAgePercent < 0.28)
				return ResourceManager.balefire_4;
			else if(cloudAgePercent < 0.35)
				return ResourceManager.balefire_5;
			else if(cloudAgePercent < 0.44)
				return ResourceManager.balefire_6;
			else if(cloudAgePercent < 0.55)
				return ResourceManager.balefire_7;
			else if(cloudAgePercent < 0.70)
				return ResourceManager.balefire_8;
			else if(cloudAgePercent < 0.88)
				return ResourceManager.balefire_9;
			else
				return ResourceManager.balefire_10;
		}else{
			if(cloudAgePercent < 0.10)
				return ResourceManager.fireball_0;
			else if(cloudAgePercent < 0.15)
				return ResourceManager.fireball_1;
			else if(cloudAgePercent < 0.19)
				return ResourceManager.fireball_2;
			else if(cloudAgePercent < 0.23)
				return ResourceManager.fireball_3;
			else if(cloudAgePercent < 0.28)
				return ResourceManager.fireball_4;
			else if(cloudAgePercent < 0.35)
				return ResourceManager.fireball_5;
			else if(cloudAgePercent < 0.44)
				return ResourceManager.fireball_6;
			else if(cloudAgePercent < 0.55)
				return ResourceManager.fireball_7;
			else if(cloudAgePercent < 0.70)
				return ResourceManager.fireball_8;
			else if(cloudAgePercent < 0.88)
				return ResourceManager.fireball_9;
			else
				return ResourceManager.fireball_10;
		}
	}
	
	/**
	 * Wrapper for the entire mush (head + stem)
	 * Renders the entire thing twice to allow for smooth color gradients
	 * @param cloud
	 * @param interp
	 */
	private void mushWrapper(EntityNukeCloudSmall cloud, float interp) {

    	float size = cloud.getDataManager().get(EntityNukeCloudSmall.SCALE) * 5;
    	float maxage = cloud.getDataManager().get(EntityNukeCloudSmall.MAXAGE);

    	double height = Math.max(20 - 1000 / (cloud.ticksExisted + interp - 13), 0);
    	boolean balefire = cloud.getDataManager().get(EntityNukeCloudSmall.TYPE) == 1;
    	float percentageAge = (float)(cloud.ticksExisted+interp)/maxage;
    	double raise_speed = 0.08 * Math.pow(0.3, percentageAge);

		GL11.glPushMatrix();
		GL11.glScalef(size, size, size);
		bindTexture(getMushroomTexture(percentageAge, balefire));
		GlStateManager.disableCull();
		GlStateManager.color(1, 1, 1, 1);

		//And now we fuck with texture transformations
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GL11.glTranslated(0, -(cloud.ticksExisted + interp) * raise_speed, 0);
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

        if(percentageAge < 0.5){
	    	GL11.glPushMatrix();
	        	//It's the thing that makes glow-in-the-dark work
		        GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
		        renderMushHead(cloud.ticksExisted + interp, height);
		        renderMushStem(cloud.ticksExisted + interp, height);
		        GL11.glPopAttrib();
		    GlStateManager.enableLighting();
	        GL11.glPopMatrix();
        }
        else{
        	GL11.glPushMatrix();
	        	renderMushHead(cloud.ticksExisted + interp, height);
		        renderMushStem(cloud.ticksExisted + interp, height);
	         GlStateManager.disableLighting();
	        GL11.glPopMatrix();
        }
        
        //Clean this up otherwise the game becomes one-dimensional
        //Drillgon200: Really should have used push/pop matrix here, but whatever.
        GlStateManager.matrixMode(GL11.GL_TEXTURE);
        GL11.glLoadIdentity();
        GlStateManager.matrixMode(GL11.GL_MODELVIEW);

		GlStateManager.disableBlend();
		GlStateManager.enableCull();

		GL11.glPopMatrix();
	}
	
	/**
	 * Adds all cloudlets to the tessellator and then draws them
	 * @param cloud
	 * @param interp
	 */
	private void cloudletWrapper(EntityNukeCloudSmall cloud, float interp) {

		GL11.glPushMatrix();
		GlStateManager.enableBlend();
		//To prevent particles cutting off before fully fading out
		//Drillgon200: What'd the point of setting the alpha func if you're just going to disable the test anyway?
    	GlStateManager.alphaFunc(GL11.GL_GEQUAL, 0.01F);
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableAlpha();
        GlStateManager.depthMask(false);

    	bindTexture(cloudlet);

		Tessellator tess = Tessellator.getInstance();
		BufferBuilder buf = tess.getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);

    	for(Cloudlet cloudlet : cloud.cloudlets) {
    		float scale = cloud.age + interp - cloudlet.age;
    		tessellateCloudlet(buf, cloudlet.posX, cloudlet.posY - cloud.posY + 2, cloudlet.posZ, scale, (int) cloud.getDataManager().get(EntityNukeCloudSmall.TYPE));
    	}
    	tess.draw();

		GlStateManager.depthMask(true);
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1F);
		GlStateManager.disableBlend();
		GL11.glPopMatrix();
	}
	
	/*
	 *     //////  //////  //    //  ////    //////  //////  //////  //////  //////
	 *    //  //  //      ////  //  //  //  //      //  //  //      //  //  //
	 *   ////    ////    //  ////  //  //  ////    ////    ////    ////    //////
	 *  //  //  //      //    //  //  //  //      //  //  //      //  //      //
	 * //  //  //////  //    //  ////    //////  //  //  //////  //  //  //////
	 */
	
	/**
	 * Once again the recycled ender dragon death animation
	 * It worked so well the last 14 times, let's go for 15
	 * @param intensity Double [0, 1] that determines scale and alpha
	 */
	private void renderFlash(double intensity) {

    	GL11.glScalef(0.2F, 0.2F, 0.2F);

    	double inverse = 1.0D - intensity;

        net.minecraft.client.renderer.Tessellator tessellator = net.minecraft.client.renderer.Tessellator.getInstance();
        BufferBuilder buf = tessellator.getBuffer();
		RenderHelper.disableStandardItemLighting();

        Random random = new Random(432L);
        GlStateManager.disableTexture2D();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE);
        GlStateManager.disableAlpha();
        GlStateManager.enableCull();
        GlStateManager.depthMask(false);

        GL11.glPushMatrix();

        float scale = 100;

        for(int i = 0; i < 300; i++) {

            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(random.nextFloat() * 360.0F, 0.0F, 1.0F, 0.0F);

            float vert1 = (random.nextFloat() * 20.0F + 5.0F + 1 * 10.0F) * (float)(intensity * scale);
            float vert2 = (random.nextFloat() * 2.0F + 1.0F + 1 * 2.0F) * (float)(intensity * scale);

            buf.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
            buf.pos(0, 0, 0).color(1.0F, 1.0F, 1.0F, (float) inverse).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5F * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.866D * vert2, vert1, -0.5F * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(0.0D, vert1, 1.0F * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            buf.pos(-0.866D * vert2, vert1, -0.5F * vert2).color(1.0F, 1.0F, 1.0F, 0.0F).endVertex();
            tessellator.draw();
        }

        GL11.glPopMatrix();

        GlStateManager.depthMask(true);
        GlStateManager.disableCull();
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.color(1, 1, 1, 1);
        GlStateManager.enableTexture2D();
        GlStateManager.enableAlpha();
        RenderHelper.enableStandardItemLighting();
	}
	
	/**
	 * Render call for the mush head model
	 * Includes offset and smoothing
	 * Also scales the fireball along XZ
	 * @param progress Lifetime + interpolation number
	 * @param height The current animation offset
	 */
	private void renderMushHead(float progress, double height) {

		GL11.glPushMatrix();

		double expansion = 100;
		double width = Math.min(progress, expansion) / expansion * 0.3 + 0.7;

		GL11.glTranslated(0, -26 + height, 0);
		GL11.glScaled(width, 1, width);

		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		mush.renderPart("Ball");
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	/**
	 * Render call for the mush stem model
	 * Includes offset and smoothing
	 * @param progress Lifetime + interpolation number
	 * @param height The current animation offset
	 */
	private void renderMushStem(float progress, double height) {

		GL11.glPushMatrix();

		GL11.glTranslated(0, -26 + height, 0);

		GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
		mush.renderPart("Stem");
        GL11.glEnable(GL11.GL_ALPHA_TEST);
		GL11.glShadeModel(GL11.GL_FLAT);

		GL11.glPopMatrix();
	}

	/**
	 * Adds one cloudlet (one face) to the tessellator.
	 * Rotation is done using ActiveRenderInfo, which I'd assume runs on magic
	 * But hey, if it works for particles, why not here too?
	 * @param tess
	 * @param posX
	 * @param posY
	 * @param posZ
	 * @param age The mush' age when the cloudlet was created
	 * @param type DataWatcher byte #19 which differentiates between different mush types
	 */
	private void tessellateCloudlet(BufferBuilder buf, double posX, double posY, double posZ, float age, int type) {

		float alpha = 1F - Math.max(age / (float)(EntityNukeCloudSmall.cloudletLife), 0F);
		float alphaorig = alpha;

		float scale = 2.5F * alpha + 2.5F;

		if(age < 3)
			alpha = age * 0.333F;

        float f1 = ActiveRenderInfo.getRotationX();
        float f2 = ActiveRenderInfo.getRotationZ();
        float f3 = ActiveRenderInfo.getRotationYZ();
        float f4 = ActiveRenderInfo.getRotationXY();
        float f5 = ActiveRenderInfo.getRotationXZ();

        Random rand = new Random((long) ((posX * 5 + posY * 25 + posZ * 125) * 1000D));

        float brightness = rand.nextFloat() * 0.25F + 0.25F;

        float r, g, b, a;
        if(type == 1) {
        	r = 0.25F * alphaorig;
        	g = alphaorig - brightness * 0.5F;
        	b = 0.25F * alphaorig;
        	a = alpha;
        } else {
        	r = g = b = brightness;
        	a = alpha;
        }
        a = MathHelper.clamp(a, 0, 1);

		buf.pos((double)(posX - f1 * scale - f3 * scale), (double)(posY - f5 * scale), (double)(posZ - f2 * scale - f4 * scale)).tex(1, 1).color(r, g, b, a).endVertex();
		buf.pos((double)(posX - f1 * scale + f3 * scale), (double)(posY + f5 * scale), (double)(posZ - f2 * scale + f4 * scale)).tex(1, 0).color(r, g, b, a).endVertex();
		buf.pos((double)(posX + f1 * scale + f3 * scale), (double)(posY + f5 * scale), (double)(posZ + f2 * scale + f4 * scale)).tex(0, 0).color(r, g, b, a).endVertex();
		buf.pos((double)(posX + f1 * scale - f3 * scale), (double)(posY - f5 * scale), (double)(posZ + f2 * scale - f4 * scale)).tex(0, 1).color(r, g, b, a).endVertex();
	}
	
}
