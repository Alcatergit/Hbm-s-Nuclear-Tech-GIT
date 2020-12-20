package com.hbm.render.entity;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityRubble;
import com.hbm.lib.RefStrings;
import com.hbm.render.model.ModelRubble;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderRubble extends Render<EntityRubble> {

	ModelRubble mine;
	Random rand;
	
	public RenderRubble(RenderManager renderManager) {
		super(renderManager);
		mine = new ModelRubble();
		rand = new Random();
	}
	
	@Override
	public void doRender(EntityRubble rocket, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		GL11.glScalef(1.0F, 1.0F, 1.0F);
		GL11.glRotatef(180, 1, 0, 0);
		GL11.glRotatef((rocket.ticksExisted % 360) * 10, 1, 1, 1);

		try {
			int block = rocket.getDataManager().get(EntityRubble.BLOCKID);
			int meta = rocket.getDataManager().get(EntityRubble.BLOCKMETA);
			@SuppressWarnings("deprecation")
			//Minecraft uses it to load blocks. Guess I will, too. I wonder what I'm supposed to use?
			IBlockState state = Block.getBlockById(block).getStateFromMeta(meta);
			String s = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(state).getParticleTexture().toString();
			s = s.substring(25, (s.indexOf("',")));
			
			if(s == null || s.isEmpty() || s.equals("missingno") || s.equals("missing"))
				s = "minecraft:blocks/stone";
	
			String[] split = s.split(":");
			
			String prefix = "";
			String suffix = "";
			
			if(split.length == 2) {
				prefix = split[0];
				suffix = split[1];
			} else {
				prefix = "minecraft";
				suffix = s;
			}
			bindTexture(new ResourceLocation(prefix + ":textures/" + suffix + ".png"));
			
			mine.renderAll(0.0625F);
		} catch(Exception ex) { }
		
		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRubble entity) {
		return new ResourceLocation(RefStrings.MODID + ":textures/models/ModelRubbleScrap.png");
	}

}
