package com.hbm.render.entity;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSmallNukeMK3 extends Render<EntityNukeCloudSmall> {
		//what the fuck is all this, i thought you made the resource manager
		//Drillgon200: ^^ To whom is he talking, I wonder.
		private static final ResourceLocation objTesterModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/mush.hmf");
		private IModelCustom blastModel;
		private static final ResourceLocation ringModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/Ring.obj");
		private IModelCustom ringModel;
	    private ResourceLocation ringTexture;
		private static final ResourceLocation ringBigModelRL = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/RingBig.obj");
		private IModelCustom ringBigModel;
	    private ResourceLocation ringBigTexture;
	    public float scale = 0;
	    public float ring = 0;
	    
	//can't you just instantiate the AMLs as a whole like a normal person smh
	//Drillgon200: Are you talking to yourself?
	public RenderSmallNukeMK3(RenderManager renderManager) {
		super(renderManager);
		blastModel = AdvancedModelLoader.loadModel(objTesterModelRL);
    	ringModel = AdvancedModelLoader.loadModel(ringModelRL);
    	ringTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	ringBigModel = AdvancedModelLoader.loadModel(ringBigModelRL);
    	ringBigTexture = new ResourceLocation(RefStrings.MODID, "textures/models/Ring2.png");
    	scale = 0;
    	ring = 0;
	}

	//choreographic analysis of a nuclear blast
		//by VeeTee McFuckface
		
		//shockwave: rapidly expanding sphere of compressed air
		//           optional: wave of ground dust, make sphere white to pair it with the flash
		//           use multiple rings if necessary, but make them thinner (maybe fire-y)
		//           duration: something like two seconds, the thing is fast
		
		//fireball: similar story to the shockwave, but with color
		//          slower expanding bubble that raises upwards
		//          use one of those large donuts for the dust on the ground
		//          optional: dust clouds descending from top to bottom of the sphere (adjust radius based on height for smoothness)
		//                    use lighter tone (vapor)
		//          duration: ~5 seconds, maybe less
		
		//mushroom: large donut to simulate breakthrough of the cloud layer (more vapor)
		//          use more rings around the shaft, make them wobble (use a sine function for the height, look at RR for reference)
		//          make the thing larger, this isn't a children's birthday and your excuses "too big" are shit
		//          make the fireball fade so it looks like the cloud is formed by it
		//          use more rings. really.
		//          more polygons, this is a cloud, not a metal pillar made by a seven-yo
		//          duration: as long as you want
		
		
	
	//your other render classes look like shit, write code like a sensible person for once. good luck, you'll need it
	@Override
	public void doRender(EntityNukeCloudSmall cloud, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glPushAttrib(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_LIGHTING_BIT);
        GL11.glTranslatef((float)x, (float)y + 0.25F, (float)z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_CULL_FACE);
    	float size = cloud.getDataManager().get(EntityNukeCloudSmall.SCALE);
        GL11.glScalef(size, size, size);

		int age = cloud.age;
        int shockScale = age * 4;
        int fireScale = (int)((age - 25) * 1.5);
       //cloud.setDead();
        if(age < 50) {
    		GL11.glPushMatrix();
    		GL11.glColor4f(0.2F, 0.2F, 0.2F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	
	        GL11.glScalef(shockScale, shockScale, shockScale);
	        GL11.glScalef(2, 2, 2);
	        
	        for(float i = 0.9F; i <= 1; i += 0.05F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	        	ResourceManager.sphere_iuv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
	        
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	
	        GL11.glScalef(0.6F, 1F / shockScale * 5, 0.6F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
	        GL11.glScalef(1.1F, 1F, 1.1F);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        if(age >= 50 && age < 150) {
    		GL11.glPushMatrix();
    		
    		if(cloud.getDataManager().get(EntityNukeCloudSmall.SOMETHING) == 1)
    			GL11.glColor4f(0.2F, 0.7F, 0.0F, 0.9F);
    		else
    			GL11.glColor4f(0.4F, 0.15F, 0.0F, 0.9F);
    		
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glEnable(GL11.GL_BLEND);
			GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
	        GL11.glTranslatef(0, fireScale * 0.75F, 0);
	
	        GL11.glScalef(fireScale * 0.85F, fireScale, fireScale * 0.85F);
	        
	        for(float i = 0.6F; i <= 1; i += 0.2F) {
	            GL11.glScalef(i, i, i);
	        	ResourceManager.sphere_ruv.renderAll();
	            GL11.glScalef(1/i, 1/i, 1/i);
	        }
	        
			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        if(age >= 150) {
    		GL11.glPushMatrix();
            bindTexture(getEntityTexture(cloud));
	        GL11.glTranslatef(0, -50, 0);
	        GL11.glScalef(6, 6, 6);
	        GL11.glDisable(GL11.GL_CULL_FACE);
            blastModel.renderAll();
            GL11.glEnable(GL11.GL_CULL_FACE);
    		GL11.glPopMatrix();
    		
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(1.5F, 1.5F, 1.5F);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
	        GL11.glScalef(10, 10, 10);
	        float f = 1.8F + (((float)Math.sin(((double)age) / 20 + 90) * 0.25F) * 0.5F);
	        float f1 = 1 + ((float)Math.sin(((double)age) / 10) * 0.15F);
	        GL11.glScalef(f, 1, f);
	        GL11.glTranslatef(0, 3.5F + f1 * 0.25F, 0);
			ringModel.renderAll();
	        GL11.glTranslatef(0, - f1 * 0.25F * 2, 0);
			ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
    		GL11.glPopMatrix();
        }
        
        if(age >= 50) {
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.4F, 0.4F, 0.4F, 1F);
			float f = (float)Math.min((age - 50) * 0.5, 20);
	        GL11.glScalef(f, 15, f);
			//ringBigModel.renderAll();
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
			//ringBigModel.renderAll();
	        GL11.glScalef(1.5F, 1, 1.5F);
	        GL11.glTranslatef(0, -0.15F, 0);
			//ringBigModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
			
    		GL11.glPushMatrix();
    		GL11.glDisable(GL11.GL_TEXTURE_2D);
	        GL11.glScalef(2, 2, 2);
			GL11.glColor4f(0.6F, 0.6F, 0.6F, 1F);
			float f0 = (float)Math.min((age - 50) * 0.25, 20) * 5F;
	        GL11.glScalef(f0, 15, f0);
	        GL11.glTranslatef(0, 3.5F, 0);
			ringBigModel.renderAll();
	        GL11.glTranslatef(0, 1F, 0);
	        GL11.glScalef(0.65F, 1, 0.65F);
			ringModel.renderAll();
	        //GL11.glTranslatef(0, -2F, 0);
			//ringModel.renderAll();
			GL11.glEnable(GL11.GL_TEXTURE_2D);
			GL11.glPopMatrix();
        }
        
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_CULL_FACE);
        GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	//very professional, i love me some null textures /s
	
	//there, fixed it ~bob
	
	//Drillgon200: Who is the other guy??? There's a whole conversation in here
	@Override
	protected ResourceLocation getEntityTexture(EntityNukeCloudSmall entity) {
		if(entity.getDataManager().get(EntityNukeCloudSmall.SOMETHING) == 1)
			return ResourceManager.balefire;
		return ResourceManager.fireball;
	}

}
