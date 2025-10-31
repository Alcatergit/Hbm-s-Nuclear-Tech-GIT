package com.leafia.contents.gear.detonator_laser;

import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.render.amlfrom1710.CompositeBrush;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.item.TEISRBase;
import com.leafia.dev.items.LeafiaGripOffsetHelper;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.Random;

import static org.lwjgl.opengl.GL11.GL_FLAT;
import static org.lwjgl.opengl.GL11.GL_SMOOTH;

public class ItemRenderLaserDetonator extends TEISRBase {

	protected static ResourceLocation texture = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/detonator_laser.png");
	protected static IModelCustom model = new HFRWavefrontObject(new ResourceLocation(RefStrings.MODID, "models/weapons/detonator_laser.obj"));

	LeafiaGripOffsetHelper offsets = new LeafiaGripOffsetHelper()
			.get(TransformType.GUI)
			.setScale(0.25).setPosition(-2.25,1.45,-1.25).setRotation(-40,0,0).getHelper()

			.get(TransformType.FIXED)
			.copySettings(TransformType.GUI).getHelper()

			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(-2.25,1,-1.25).setRotation(-10,0,5).getHelper()
			.get(TransformType.FIRST_PERSON_LEFT_HAND) // Whoops
			.setScale(0.25).setPosition(12.75,4.75,-13.75).setRotation(0,-5,-10).getHelper()
			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(-2.6,1.6,-0.6).setRotation(-53,7,-7).getHelper()
			.get(TransformType.THIRD_PERSON_LEFT_HAND)
			.setScale(1).setPosition(1.25,0,0).setRotation(0,-9,0).getHelper()

			.get(TransformType.GROUND)
			.setScale(0.25).setPosition(-2,0.25,-1.75).setRotation(0,0,0).getHelper();

	LeafiaGripOffsetHelper.LeafiaGripOffset ADS = new LeafiaGripOffsetHelper.LeafiaGripOffset(offsets)
			.setScale(1.01).setPosition(-3.35,1,-0.9).setRotation(-14,1,-5);

	@Override
	public void renderByItem(ItemStack item) {
		LeafiaGls._push();
		LeafiaGls.pushMatrix();
		LeafiaGls.enableCull();
		LeafiaGls.shadeModel(GL_SMOOTH);
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		switch(type) {
			case FIRST_PERSON_LEFT_HAND:
			case FIRST_PERSON_RIGHT_HAND:
				float ads = Math.signum(ModEventHandlerClient.getViewADS(Minecraft.getMinecraft().player));
				if (ads == ((type.equals(TransformType.FIRST_PERSON_RIGHT_HAND)) ? 1 : -1)) {
					offsets.apply(TransformType.FIRST_PERSON_RIGHT_HAND);
					offsets.applyCustomOffset(ADS);
				} else
					offsets.apply(type);
				break;
			default:
				offsets.apply(type);
				break;
		}
		/*
		double s0 = 0.25D;
		switch(type){
			case FIRST_PERSON_LEFT_HAND:
				//GL11.glRotatef(80F, 0.0F, 1.0F, 0.0F);
				//GL11.glRotatef(-20F, 1.0F, 0.0F, 0.0F);
				//GL11.glTranslatef(1.0F, 0.5F, 3.0F);
				break;
			case FIRST_PERSON_RIGHT_HAND:
				GL11.glScaled(s0, s0, s0);
				GL11.glRotatef(-95F,0.0F,1.0F,0.0F);
				GL11.glRotatef(-20F,1.0F,0.0F,0.0F);
				GL11.glTranslatef(0.4f,0.98f,0f);
				break;
			case THIRD_PERSON_LEFT_HAND:
				GL11.glTranslatef(2.25f,1f,0f);
			case THIRD_PERSON_RIGHT_HAND:
				double scale = 0.25D;
				GL11.glScaled(scale,scale,scale);
				GL11.glRotatef(-100F,0.0F,1.0F,0.0F);
				GL11.glRotatef(-60F,1.0F,0.0F,0.0F);
				GL11.glRotatef(10F,0.0F,0.0F,1.0F);
				GL11.glTranslatef(0.9f,1.0f,0f);
				break;
			case HEAD:
			case FIXED:
			case GROUND:
				double s1 = 0.25D;
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glScaled(s1, s1, s1);
				break;
			case GUI:
				GL11.glEnable(GL11.GL_LIGHTING);
				double s = 0.35D;
				GL11.glScaled(s, s, -s);
				GL11.glTranslatef(1.5F, 2.75F, 0.0F);
				GL11.glRotatef(180F, 1.0F, 0.0F, 0.0F);
				GL11.glRotatef(-90F, 0.0F, 1.0F, 0.0F);
				GL11.glRotatef(-45F, 1.0F, 0.0F, 0.0F);
				break;
			default:
				break;
		}*/ // Alright fuck it.
		model.renderPart("Main");

		LeafiaGls.pushMatrix();
		GL11.glPushAttrib(GL11.GL_LIGHTING_BIT);
		LeafiaGls.disableLighting();
		LeafiaGls.disableCull();
		LeafiaGls.tryBlendFuncSeparate(770, 771, 1, 0);
		//OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);

		LeafiaGls.disableLighting();
		LeafiaGls.disableTexture2D();
		LeafiaGls.color(1f,0f,0f);
		LeafiaGls._push();
		RenderHelper.disableStandardItemLighting();
		model.renderPart("Lights");
		LeafiaGls._pop();
		LeafiaGls.color(1f,1f,1f);

		LeafiaGls.pushMatrix();

		float px = 0.0625F;
		GL11.glTranslatef(0.5626F, px * 18, -px * 14);

		CompositeBrush tess = CompositeBrush.instance;
		tess.startDrawing(GL11.GL_QUADS);

		int sub = 32;
		double width = px * 8;
		double len = width / sub;
		double time = System.currentTimeMillis() / -100D;
		double amplitude = 0.075;

		tess.setColorOpaque_I(0xffff00);

		for(int i = 0; i < sub; i++) {
			double h0 = Math.sin(i * 0.5 + time) * amplitude;
			double h1 = Math.sin((i + 1) * 0.5 + time) * amplitude;
			tess.addVertex(0, -px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h1, len * (i + 1));
			tess.addVertex(0, px * 0.25 + h0, len * i);
			tess.addVertex(0, -px * 0.25 + h0, len * i);
		}
		tess.setColorOpaque_F(1F, 1F, 1F);

		tess.draw();

		LeafiaGls.popMatrix();

		LeafiaGls.enableTexture2D();

		LeafiaGls.pushMatrix();
		String s;
		Random rand = new Random(System.currentTimeMillis() / 500);
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		float f3 = 0.01F;
		GL11.glTranslatef(0.5625F, 1.3125F, 0.875F);
		GL11.glScalef(f3, -f3, f3);
		GL11.glRotatef(90, 0, 1, 0);
		GL11.glNormal3f(0.0F, 0.0F, -1.0F * f3);

		GL11.glTranslatef(3F, -2F, 0.2F);

		for(int i = 0; i < 3; i++) {
			s = (rand.nextInt(900000) + 100000) + "";
			font.drawString(s, 0, 0, 0xff0000);
			GL11.glTranslatef(0F, 12.5F, 0F);
		}
		LeafiaGls.popMatrix();

		LeafiaGls.enableLighting();
		LeafiaGls.popMatrix();
		LeafiaGls.popAttrib();

		LeafiaGls.shadeModel(GL_FLAT);

		LeafiaGls.popMatrix();
		LeafiaGls._pop();
	}
}
