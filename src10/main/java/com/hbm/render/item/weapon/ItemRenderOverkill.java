package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelPip;
import com.hbm.render.model.ModelSpark;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderOverkill extends TEISRBase {

	/*protected ModelJack powerJack;
	*/protected ModelSpark sparkPlug;
	public static ResourceLocation sparkLoc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelSpark.png");
	/*protected ModelHP hppLaserjet;
	protected ModelEuthanasia euthanasia;
	protected ModelDefabricator defab;
	protected ModelDash dasher;
	protected ModelTwiGun rgottp;*/
	protected ModelPip pip;
	protected ResourceLocation pipLoc = new ResourceLocation(RefStrings.MODID + ":textures/models/weapons/ModelPip.png");
	protected ResourceLocation noPipLoc = new ResourceLocation(RefStrings.MODID + ":textures/models/weapons/ModelPipNoScope.png");
	protected ResourceLocation blackjackLoc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelPipGrey.png");
	protected ResourceLocation redLoc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelPipRed.png");
	protected ResourceLocation silverLoc = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelPipSilver.png");
	/*protected ModelLacunae lacunae;
	protected ModelFolly folly;
	
	protected ModelCalBarrel barrel;
	protected ModelCalStock stock;
	protected ModelCalDualStock saddle;*/

	public ItemRenderOverkill() {
		/*powerJack = new ModelJack();
		*/sparkPlug = new ModelSpark();
		/*hppLaserjet = new ModelHP();
		euthanasia = new ModelEuthanasia();
		defab = new ModelDefabricator();
		dasher = new ModelDash();
		rgottp = new ModelTwiGun();*/
		pip = new ModelPip();
		/*barrel = new ModelCalBarrel();
		stock = new ModelCalStock();
		saddle = new ModelCalDualStock();
		lacunae = new ModelLacunae();
		folly = new ModelFolly();*/
	}

	//Drillgon200: Oh god this class is messy. I'm going to stop using the same item renderer to render every powerful gun.
	@Override
	public void renderByItem(ItemStack stack) {
		if (stack.getItem() == ModItems.gun_revolver_pip)
			Minecraft.getMinecraft().renderEngine.bindTexture(pipLoc);
		if (stack.getItem() == ModItems.gun_revolver_nopip)
			Minecraft.getMinecraft().renderEngine.bindTexture(noPipLoc);
		if(stack.getItem() == ModItems.gun_revolver_blackjack)
			Minecraft.getMinecraft().renderEngine.bindTexture(blackjackLoc);
		if(stack.getItem() == ModItems.gun_revolver_red)
			Minecraft.getMinecraft().renderEngine.bindTexture(redLoc);
		if(stack.getItem() == ModItems.gun_revolver_silver)
			Minecraft.getMinecraft().renderEngine.bindTexture(silverLoc);
		if(stack.getItem() == ModItems.gun_spark)
			Minecraft.getMinecraft().renderEngine.bindTexture(sparkLoc);
		switch (type) {
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:

			if (type == TransformType.FIRST_PERSON_RIGHT_HAND) {
				if (stack.getItem() == ModItems.gun_revolver_pip || stack.getItem() == ModItems.gun_revolver_nopip || 
					stack.getItem() == ModItems.gun_revolver_blackjack || stack.getItem() == ModItems.gun_revolver_red || stack.getItem() == ModItems.gun_revolver_silver) {
					GL11.glScalef(0.70F, 0.70F, 0.70F);
					GL11.glTranslatef(-0.5F, 0.4F, 0.0F);
				}
				
				GL11.glTranslated(-0.2, 0.2, 0.2);
				if(stack.getItem() == ModItems.gun_spark){
					GL11.glScaled(0.7, 0.7, 0.7);
					GL11.glTranslated(1.0, 0.2, 0.35);
					GL11.glRotated(-10, 0, 0, 1);
				}
				GL11.glRotated(-10, 0, 1, 0);
				GL11.glRotated(-20, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			} else {
				if (stack.getItem() == ModItems.gun_revolver_pip || stack.getItem() == ModItems.gun_revolver_nopip || 
						stack.getItem() == ModItems.gun_revolver_blackjack || stack.getItem() == ModItems.gun_revolver_red || stack.getItem() == ModItems.gun_revolver_silver) {
					GL11.glTranslatef(1.5F, 0.5F, 0.2F);
					GL11.glScalef(0.70F, 0.70F, 0.70F);
				}
				
				if(stack.getItem() == ModItems.gun_spark){
					GL11.glScaled(0.7, 0.7, 0.7);
					GL11.glTranslated(1.0, 0.7, 0.3);
					GL11.glRotated(20, 0, 0, 1);
				}
				GL11.glRotated(180, 1, 0, 0);
				GL11.glRotated(175, 0, 1, 0);
				GL11.glRotated(23, 0, 0, 1);
			}

			if (stack.getItem() == ModItems.gun_revolver_pip || stack.getItem() == ModItems.gun_revolver_nopip || 
					stack.getItem() == ModItems.gun_revolver_blackjack || stack.getItem() == ModItems.gun_revolver_red || stack.getItem() == ModItems.gun_revolver_silver)
				pip.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(stack.getItem() == ModItems.gun_spark){
				sparkPlug.renderingInFirstPerson = true;
				sparkPlug.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			}
			break;
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(0.45, 0.25, 0.5);
			GL11.glRotated(-90, 0, 1, 0);
			GL11.glRotated(180, 1, 0, 0);
			if (stack.getItem() == ModItems.gun_revolver_pip || stack.getItem() == ModItems.gun_revolver_nopip || stack.getItem() == ModItems.gun_revolver_blackjack || stack.getItem() == ModItems.gun_revolver_red || stack.getItem() == ModItems.gun_revolver_silver) {
				GL11.glScalef(0.60F, 0.60F, 0.60F);
			}
			if (stack.getItem() == ModItems.gun_revolver_pip || stack.getItem() == ModItems.gun_revolver_nopip || stack.getItem() == ModItems.gun_revolver_blackjack || stack.getItem() == ModItems.gun_revolver_red || stack.getItem() == ModItems.gun_revolver_silver)
				pip.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			if(stack.getItem() == ModItems.gun_spark)
				sparkPlug.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F);
			break;
		default:
			break;
		}
	}
}
