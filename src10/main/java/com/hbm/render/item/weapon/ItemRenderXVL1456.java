package com.hbm.render.item.weapon;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemGunGauss;
import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.particle.ParticleFirstPerson.ParticleType;
import com.hbm.particle.ParticleFirstPerson;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.item.TEISRBase;
import com.hbm.render.model.ModelXVL1456;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;

public class ItemRenderXVL1456 extends TEISRBase {

	protected ModelXVL1456 swordModel;
	protected static ResourceLocation tau_rl = new ResourceLocation(RefStrings.MODID +":textures/models/weapons/ModelXVL1456.png");
	
	public ItemRenderXVL1456() {
		swordModel = new ModelXVL1456();
	}
	
	@Override
	public void renderByItem(ItemStack itemStackIn) {
		GL11.glPopMatrix();
		GlStateManager.enableCull();
		Minecraft.getMinecraft().renderEngine.bindTexture(tau_rl);
		float f = 0;
		if(this.entity instanceof EntityPlayer){
			EntityPlayer player = (EntityPlayer) this.entity;
			f = ((EntityPlayer)this.entity).getActiveItemStack().getItemUseAction() == EnumAction.BOW ? 0.05F : 0F;
			if(f == 0.05F && player.getHeldItemMainhand().getItem() == itemStackIn.getItem() && player.getHeldItemOffhand().getItem() == itemStackIn.getItem()){
				f = 0.025F;
			}
		}
		switch(type){
		case FIRST_PERSON_LEFT_HAND:
			GL11.glTranslated(50.5, 0, 8.4);
		case FIRST_PERSON_RIGHT_HAND:
			double[] recoil = HbmAnimations.getRelevantTransformation("RECOIL", type == TransformType.FIRST_PERSON_RIGHT_HAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			double[] spin = HbmAnimations.getRelevantTransformation("SPIN", type == TransformType.FIRST_PERSON_RIGHT_HAND ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND);
			spin = ItemGunGauss.getCharge(itemStackIn) > 0 ? spin : new double[]{0, 0, 0};
			
			GL11.glScaled(10, 10, 10);
			GL11.glTranslated(-1.7 - recoil[2]*0.3 - spin[1], 0.4, -0.8);
			GL11.glRotated(182, 1, 0, 0);
			GL11.glRotated(-5, 0, 1, 0);
			GL11.glRotated(30 + recoil[1]*0.2, 0, 0, 1);
			if(type == TransformType.FIRST_PERSON_LEFT_HAND){
				GL11.glRotated(100, 0, 0, 1);
				GL11.glRotated(180, 1, 0, 0);
			}
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, (float)spin[0]*0.000006F*(ItemGunGauss.getCharge(itemStackIn)+MainRegistry.proxy.partialTicks()));
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER2);
			GL11.glLoadIdentity();
			GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
			ClientProxy.AUX_GL_BUFFER.put(12, ClientProxy.AUX_GL_BUFFER2.get(12));
			ClientProxy.AUX_GL_BUFFER.put(13, ClientProxy.AUX_GL_BUFFER2.get(13));
			ClientProxy.AUX_GL_BUFFER.put(14, ClientProxy.AUX_GL_BUFFER2.get(14));
			GL11.glLoadMatrix(ClientProxy.AUX_GL_BUFFER2);
			for(ParticleFirstPerson p : ModEventHandlerClient.firstPersonAuxParticles){
				if(p.getType() == ParticleType.TAU)
				p.renderParticle(Tessellator.getInstance().getBuffer(), null, MainRegistry.proxy.partialTicks(), 0, 0, 0, 0, 0);
			}
			break;
		case THIRD_PERSON_RIGHT_HAND:
		case THIRD_PERSON_LEFT_HAND:
		case HEAD:
		case FIXED:
		case GROUND:
			GL11.glTranslated(-0.25, 0.1, -0.4);
			GL11.glRotated(90, 0, 1, 0);
			GL11.glRotated(180, 0, 0, 1);
			GL11.glScaled(0.75, 0.75, 0.75);
			swordModel.render(null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, f);
			break;
		default:
			break;
		}
		GL11.glPushMatrix();
	}
}
