package com.hbm.render.misc;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import org.lwjgl.MemoryUtil;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = RefStrings.MODID)
public class LensVisibilityHandler {

	static Map<Integer, LensSpikeInfo> lensSpikes = new HashMap<>();
	static int currentId = 0;
	public static int checkSphere = -1;
	
	public static int findId(){
		while(lensSpikes.containsKey(currentId)){
			currentId ++;
		}
		return currentId;
	}
	
	public static int generate(FloatBuffer matrix){
		int id = findId();
		LensSpikeInfo i = new LensSpikeInfo(matrix);
		lensSpikes.put(id, i);
		return id;
	}
	
	public static void delete(int id){
		LensSpikeInfo i = lensSpikes.get(id);
		if(i != null){
			i.cleanup();
			lensSpikes.remove(id);
		}
	}
	
	public static float getVisibility(int id){
		LensSpikeInfo i = lensSpikes.get(id);
		if(i != null){
			return i.visibility;
		}
		return 0.0F;
	}
	
	public static FloatBuffer getMatrixBuf(int id){
		LensSpikeInfo i = lensSpikes.get(id);
		if(i != null){
			return i.modelviewMatrix;
		}
		return null;
	}
	
	@SubscribeEvent
	public static void renderLast(RenderWorldLastEvent event) {
		for(LensSpikeInfo i : lensSpikes.values()){
			i.updateVisibility();
		}
	}
	
	public static class LensSpikeInfo {
		public FloatBuffer modelviewMatrix;
		public float visibility = 0.0F;
		private int totalFragmentsQuery;
		private int fragmentsPassedQuery;
		
		public LensSpikeInfo(FloatBuffer matrix) {
			this.modelviewMatrix = matrix;
			totalFragmentsQuery = GL15.glGenQueries();
			fragmentsPassedQuery = GL15.glGenQueries();
			
			GlStateManager.colorMask(false, false, false, false);
			GlStateManager.depthMask(false);
			GlStateManager.disableCull();
			Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.turbofan_blades_tex);
			
			GL11.glPushMatrix();
			GL11.glLoadMatrix(modelviewMatrix);
			GL11.glScaled(0.05, 0.05, 0.05);
			GlStateManager.disableDepth();
			GL15.glBeginQuery(GL15.GL_SAMPLES_PASSED, totalFragmentsQuery);
			GL11.glCallList(checkSphere);
			GL15.glEndQuery(GL15.GL_SAMPLES_PASSED);
			
			GlStateManager.enableDepth();
			GL15.glBeginQuery(GL15.GL_SAMPLES_PASSED, fragmentsPassedQuery);
			GL11.glCallList(checkSphere);
			GL15.glEndQuery(GL15.GL_SAMPLES_PASSED);
			GL11.glPopMatrix();
			
			GlStateManager.colorMask(true, true, true, true);
			GlStateManager.depthMask(true);
			GlStateManager.enableCull();
		}
		
		public void updateVisibility(){
			int totalDone = GL15.glGetQueryObjectui(totalFragmentsQuery, GL15.GL_QUERY_RESULT_AVAILABLE);
			int passedDone = GL15.glGetQueryObjectui(fragmentsPassedQuery, GL15.GL_QUERY_RESULT_AVAILABLE);
			if(totalDone != 0 && passedDone != 0){
				float total = GL15.glGetQueryObjectui(totalFragmentsQuery, GL15.GL_QUERY_RESULT);
				float passed = GL15.glGetQueryObjectui(fragmentsPassedQuery, GL15.GL_QUERY_RESULT);
				visibility = passed/total;
				
				GlStateManager.colorMask(false, false, false, false);
				GlStateManager.depthMask(false);
				GlStateManager.disableCull();
				Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.turbofan_blades_tex);
				
				GL11.glPushMatrix();
				GL11.glLoadMatrix(modelviewMatrix);
				GL11.glScaled(0.1, 0.1, 0.1);
				GlStateManager.disableDepth();
				GL15.glBeginQuery(GL15.GL_SAMPLES_PASSED, totalFragmentsQuery);
				GL11.glCallList(checkSphere);
				GL15.glEndQuery(GL15.GL_SAMPLES_PASSED);
				
				GlStateManager.enableDepth();
				GL15.glBeginQuery(GL15.GL_SAMPLES_PASSED, fragmentsPassedQuery);
				GL11.glCallList(checkSphere);
				GL15.glEndQuery(GL15.GL_SAMPLES_PASSED);
				GL11.glPopMatrix();
				
				GlStateManager.colorMask(true, true, true, true);
				GlStateManager.depthMask(true);
				GlStateManager.enableCull();
			}
		}
		
		public void cleanup(){
			GL15.glDeleteQueries(totalFragmentsQuery);
			GL15.glDeleteQueries(fragmentsPassedQuery);
			//That modelViewMatrix not being deleted might cause a memory leak, but if it does, I don't know what to do about it in java!
		}
	}
	
	
}
