package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.model.ModelBroadcaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRendererMachine extends TEISRBase {

	///// THIS IS A TEST CLASS. CARVE THIS INTO A BASE CLASS FOR LESS CRAPPY BLOCK ITEM RENDERERS IN THE FUTURE ////
	double scale = 1.0D;
	private ModelBroadcaster broadcaster;
	private static final ResourceLocation broadcasterTex = new ResourceLocation(RefStrings.MODID + ":" + "textures/models/ModelRadioReceiver.png");
	
	public ItemRendererMachine(double scale) {
		this.scale = scale;
		this.broadcaster = new ModelBroadcaster();
	}
	
	@Override
	public void renderByItem(ItemStack stack) {
		GL11.glTranslated(0.5, 0, 0.5);
		GL11.glScaled(0.55, 0.55, 0.55);
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			break;
		case GUI:
			break;
		case NONE:
			break;
		}
		
		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.machine_selenium)) {

            GlStateManager.disableCull();
			Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_body_tex);
	        ResourceManager.selenium_body.renderAll();

	        GL11.glTranslated(0.0D, 1.0D, 0.0D);

	        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_piston_tex);
	        for(int i = 0; i < 7; i++) {
	            ResourceManager.selenium_piston.renderAll();
	    		GL11.glRotatef(360F/7F, 0, 0, 1);
	        }

            Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.selenium_rotor_tex);
            ResourceManager.selenium_rotor.renderAll();
            GlStateManager.enableCull();
		}

		if(stack.getItem() == Item.getItemFromBlock(ModBlocks.radiorec)) {
			GL11.glTranslated(0, 1.5, 0);
			GL11.glRotated(180, 1, 0, 0);
            Minecraft.getMinecraft().renderEngine.bindTexture(broadcasterTex);
			broadcaster.renderModel(0.0625F);
		}
	}
}
