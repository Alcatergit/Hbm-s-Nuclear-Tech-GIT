package com.hbm.render.entity.projectile;

import org.lwjgl.opengl.GL11;

import com.hbm.entity.projectile.EntityBeamBase;
import com.hbm.lib.Library;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderVortexBeam extends Render<EntityBeamBase> {

	public static final IRenderFactory<EntityBeamBase> FACTORY = man -> new RenderVortexBeam(man);
	
	protected RenderVortexBeam(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityBeamBase laser, double x, double y, double z, float entityYaw, float partialTicks) {
		GL11.glPushMatrix();

		EntityPlayer player = laser.world.getPlayerEntityByName(laser.getDataManager().get(EntityBeamBase.PLAYER_NAME));

		if(player != null) {

			GL11.glTranslated(x, y, z);

			RayTraceResult pos = Library.rayTrace(player, 100, 1);

			Vec3 skeleton = Vec3.createVectorHelper(pos.hitVec.x - player.posX, pos.hitVec.y - player.posY - player.getEyeHeight(), pos.hitVec.z - player.posZ);
			int init = (int) -(System.currentTimeMillis() % 360);

	        BeamPronter.prontBeam(skeleton, EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x000040, 0x2020d0, init, 1, 0F, 4, 0.005F);
	        BeamPronter.prontBeam(skeleton, EnumWaveType.RANDOM, EnumBeamType.LINE, 0x8080ff, 0x8080ff, init, (int)skeleton.lengthVector() * 3 + 1, 0.01F, 1, 0.01F);
		}

		GL11.glPopMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBeamBase entity) {
		return null;
	}

}
