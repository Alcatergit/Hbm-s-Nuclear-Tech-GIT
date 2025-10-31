package com.hbm.render.tileentity;

import java.nio.DoubleBuffer;

import com.hbm.interfaces.IDoor;
import org.lwjgl.opengl.GL11;

import com.hbm.animloader.AnimationWrapper;
import com.hbm.animloader.AnimationWrapper.EndResult;
import com.hbm.animloader.AnimationWrapper.EndType;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntitySlidingBlastDoor;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderSlidingBlastDoor extends TileEntitySpecialRenderer<TileEntitySlidingBlastDoor> {

	private static DoubleBuffer buf = null;
	
	@Override
	public boolean isGlobalRenderer(TileEntitySlidingBlastDoor te) {
		return true;
	}
	
	@Override
	public void render(TileEntitySlidingBlastDoor te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x+0.5, y, z+0.5);
        
        GL11.glEnable(GL11.GL_CLIP_PLANE0);
        GL11.glEnable(GL11.GL_CLIP_PLANE1);
        GlStateManager.enableCull();
        GL11.glEnable(GL11.GL_CULL_FACE);
		GlStateManager.enableLighting();
		GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);

		switch(te.getBlockMetadata() - BlockDummyable.offset) {
		case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
		}
		
		if(buf == null){
			buf = GLAllocation.createDirectByteBuffer(8*4).asDoubleBuffer();
		}
		buf.put(new double[]{1, 0, 0, 0});
		buf.rewind();
		GL11.glPushMatrix();
		GL11.glTranslated(-3.50001, 0, 0);
		GL11.glClipPlane(GL11.GL_CLIP_PLANE0, buf);
		GL11.glPopMatrix();
		buf.put(new double[]{-1, 0, 0, 0});
		buf.rewind();
		GL11.glPushMatrix();
		GL11.glTranslated(3.50001, 0, 0);
		GL11.glClipPlane(GL11.GL_CLIP_PLANE1, buf);
		GL11.glPopMatrix();
        
        long time = System.currentTimeMillis();
        long startTime = te.state.isMovingState() ? te.sysTime : time;
        boolean reverse = te.state == IDoor.DoorState.OPEN || te.state == IDoor.DoorState.CLOSING;
        AnimationWrapper w = new AnimationWrapper(startTime, ResourceManager.door0_open);
        if(reverse){
        	w.reverse();
        }
        w.onEnd(new EndResult(EndType.STAY, null));
        if(te.getBlockType() == ModBlocks.sliding_blast_door){
        	ResourceLocation tex = null;
        	switch(te.texture){
        	case 0: tex = ResourceManager.sliding_blast_door_tex; break;
        	case 1: tex = ResourceManager.sliding_blast_door_variant1_tex; break;
        	case 2: tex = ResourceManager.sliding_blast_door_variant2_tex; break;
        	}
        	Minecraft.getMinecraft().getTextureManager().bindTexture(tex);
        	ResourceManager.door0.controller.setAnim(w);
            ResourceManager.door0.renderAnimated(time);
        } else if(te.getBlockType() == ModBlocks.sliding_blast_door_2){
        	Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.sliding_blast_door_keypad_tex);
        	ResourceManager.door0_1.controller.setAnim(w);
            ResourceManager.door0_1.renderAnimated(time);
        }
        
        GL11.glDisable(GL11.GL_CLIP_PLANE0);
        GL11.glDisable(GL11.GL_CLIP_PLANE1);
        GlStateManager.shadeModel(GL11.GL_FLAT);
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
	}
}
