package com.hbm.render.item;

import java.util.HashMap;
import java.util.function.Consumer;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.items.ModItems;
import com.hbm.main.ResourceManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemRenderMissileGeneric extends TEISRBase {
	
	public static HashMap<ComparableStack, Consumer<TextureManager>> renderers = new HashMap();
	
	protected RenderMissileType category;
	
	public static enum RenderMissileType {
		TYPE_TIER0,
		TYPE_TIER1,
		TYPE_TIER2,
		TYPE_TIER3,
		TYPE_ABM,
		TYPE_NUCLEAR,
		TYPE_THERMAL,
		TYPE_DOOMSDAY,
		TYPE_CARRIER
	}
	
	public ItemRenderMissileGeneric(RenderMissileType category) {
		this.category = category;
	}

	@Override
	public void renderByItem(ItemStack item) {
	
		Consumer<TextureManager> renderer = renderers.get(new ComparableStack(item).makeSingular());
		if(renderer == null) return;
		
		GL11.glPushMatrix();

		double guiScale = 1;
		double guiOffset = 0;

		switch(this.category) {
		case TYPE_TIER0: guiScale = 4.2D; guiOffset = 1D; break; //Micro
		case TYPE_TIER1: guiScale = 2.75D; guiOffset = 0.5D; break; //Normal
		case TYPE_TIER2: guiScale = 1.1D; guiOffset = 0.5D; break; //Strong
		case TYPE_TIER3: guiScale = 1D; guiOffset = 0D; break; //Huge
		case TYPE_ABM: guiScale = 2.25D; guiOffset = 0.5D; break;
		case TYPE_NUCLEAR: guiScale = 1.75D; guiOffset = 0D; break;
		case TYPE_THERMAL: guiScale = 1.75D; guiOffset = 1D; break;
		case TYPE_DOOMSDAY: guiScale = 1.5D; guiOffset = 1D; break;
		case TYPE_CARRIER: guiScale = 0.625D; break;
		}

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glAlphaFunc(GL11.GL_GREATER, 0F);
		GL11.glEnable(GL11.GL_ALPHA_TEST);
		boolean l = false;
		switch(type) {
		case THIRD_PERSON_LEFT_HAND:
		case THIRD_PERSON_RIGHT_HAND:
			double s = 0.15;
			GL11.glTranslated(0.5, -0.25, 0.25);
			GL11.glScaled(s, s, s);
			GL11.glScaled(guiScale, guiScale, guiScale);
			
			break;
		case FIRST_PERSON_LEFT_HAND:
		case FIRST_PERSON_RIGHT_HAND:
			double heldScale = 0.1;
			GL11.glTranslated(0.5, 0.25, 0.3);
			GL11.glScaled(heldScale, heldScale, heldScale);
			GL11.glScaled(guiScale, guiScale, guiScale);
			break;
		case GROUND:
		case FIXED:
		case HEAD:
			double s2 = 0.15;
			GL11.glScaled(s2, s2, s2);
			break;
		case GUI:
			RenderHelper.enableGUIStandardItemLighting();
			double s3 = 0.0625;
			GL11.glScaled(s3, s3, s3);
			GL11.glTranslated(15 - guiOffset, 1 + guiOffset, 0);
			GL11.glScaled(guiScale, guiScale, guiScale);
			GL11.glRotated(45, 0, 0, 1);
			GL11.glRotatef(System.currentTimeMillis() / 15 % 360, 0, 1, 0);
			l = true;
			break;
		default: break;
		}
		
		GL11.glDisable(GL11.GL_CULL_FACE);
		renderer.accept(Minecraft.getMinecraft().renderEngine);
		GL11.glEnable(GL11.GL_CULL_FACE);
		if(l) RenderHelper.enableStandardItemLighting();
		GL11.glPopMatrix();
	}
	
	public static Consumer<TextureManager> generateStandard(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 1F); }
	public static Consumer<TextureManager> generateLarge(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 1.5F); }
	public static Consumer<TextureManager> generateDouble(ResourceLocation texture, IModelCustom model) { return generateWithScale(texture, model, 2F); }
	
	public static Consumer<TextureManager> generateWithScale(ResourceLocation texture, IModelCustom model, float scale) {
		return x -> {
			GL11.glScalef(scale, scale, scale);
			GL11.glShadeModel(GL11.GL_SMOOTH);
			x.bindTexture(texture); model.renderAll();
			GL11.glShadeModel(GL11.GL_FLAT);
		};
	}
	
	public static void init() {

		renderers.put(new ComparableStack(ModItems.missile_taint), generateStandard(ResourceManager.missileTaint_tex, ResourceManager.missileTaint));
		renderers.put(new ComparableStack(ModItems.missile_micro), generateStandard(ResourceManager.missileMicro_tex, ResourceManager.missileTaint));
		renderers.put(new ComparableStack(ModItems.missile_bhole), generateStandard(ResourceManager.missileMicroBHole_tex, ResourceManager.missileTaint));
		renderers.put(new ComparableStack(ModItems.missile_schrabidium), generateStandard(ResourceManager.missileMicroSchrab_tex, ResourceManager.missileTaint));
		renderers.put(new ComparableStack(ModItems.missile_emp), generateStandard(ResourceManager.missileMicroEMP_tex, ResourceManager.missileTaint));
		
		renderers.put(new ComparableStack(ModItems.missile_generic), generateStandard(ResourceManager.missileV2_HE_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_incendiary), generateStandard(ResourceManager.missileV2_IN_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_cluster), generateStandard(ResourceManager.missileV2_CL_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_buster), generateStandard(ResourceManager.missileV2_BU_tex, ResourceManager.missileV2));
		renderers.put(new ComparableStack(ModItems.missile_anti_ballistic), generateStandard(ResourceManager.missileAA_tex, ResourceManager.missileAB));
		
		renderers.put(new ComparableStack(ModItems.missile_strong), generateLarge(ResourceManager.missileStrong_HE_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_incendiary_strong), generateLarge(ResourceManager.missileStrong_IN_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_cluster_strong), generateLarge(ResourceManager.missileStrong_CL_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_buster_strong), generateLarge(ResourceManager.missileStrong_BU_tex, ResourceManager.missileStrong));
		renderers.put(new ComparableStack(ModItems.missile_emp_strong), generateLarge(ResourceManager.missileStrong_EMP_tex, ResourceManager.missileStrong));
		
		renderers.put(new ComparableStack(ModItems.missile_burst), generateStandard(ResourceManager.missileHuge_HE_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_inferno), generateStandard(ResourceManager.missileHuge_IN_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_rain), generateStandard(ResourceManager.missileHuge_CL_tex, ResourceManager.missileHuge));
		renderers.put(new ComparableStack(ModItems.missile_drill), generateStandard(ResourceManager.missileHuge_BU_tex, ResourceManager.missileHuge));

		renderers.put(new ComparableStack(ModItems.missile_nuclear), generateLarge(ResourceManager.missileNuclear_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_nuclear_cluster), generateLarge(ResourceManager.missileMIRV_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_volcano), generateLarge(ResourceManager.missileVolcano_tex, ResourceManager.missileNuclear));
		renderers.put(new ComparableStack(ModItems.missile_n2), generateLarge(ResourceManager.missileN2_tex, ResourceManager.missileNuclear));
		
		renderers.put(new ComparableStack(ModItems.missile_endo), generateLarge(ResourceManager.missileEndo_tex, ResourceManager.missileThermo));
		renderers.put(new ComparableStack(ModItems.missile_exo), generateLarge(ResourceManager.missileExo_tex, ResourceManager.missileThermo));

		renderers.put(new ComparableStack(ModItems.missile_doomsday), generateDouble(ResourceManager.missileDoomsday_tex, ResourceManager.missileDoomsday));

		renderers.put(new ComparableStack(ModItems.missile_carrier), x -> {
			GL11.glScalef(2F, 2F, 2F);
			x.bindTexture(ResourceManager.missileCarrier_tex);
			ResourceManager.missileCarrier.renderAll();
			GL11.glTranslated(0.0D, 0.5D, 0.0D);
			GL11.glTranslated(1.25D, 0.0D, 0.0D);
			x.bindTexture(ResourceManager.missileBooster_tex);
			ResourceManager.missileBooster.renderAll();
			GL11.glTranslated(-2.5D, 0.0D, 0.0D);
			ResourceManager.missileBooster.renderAll();
			GL11.glTranslated(1.25D, 0.0D, 0.0D);
			GL11.glTranslated(0.0D, 0.0D, 1.25D);
			ResourceManager.missileBooster.renderAll();
			GL11.glTranslated(0.0D, 0.0D, -2.5D);
			ResourceManager.missileBooster.renderAll();
			GL11.glTranslated(0.0D, 0.0D, 1.25D);
		});
	}
}
