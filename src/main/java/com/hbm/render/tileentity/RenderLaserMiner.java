package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.math.MathHelper;

public class RenderLaserMiner extends TileEntitySpecialRenderer<TileEntityMachineMiningLaser> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineMiningLaser te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineMiningLaser te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y - 1, z + 0.5);

		TileEntityMachineMiningLaser laser = (TileEntityMachineMiningLaser)te;
		double tx = x;
		double ty = 0;
		double tz = z;
		if(laser.beam) {
			tx = (laser.targetX - laser.lastTargetX) * partialTicks + laser.lastTargetX;
			ty = (laser.targetY - laser.lastTargetY) * partialTicks + laser.lastTargetY;
			tz = (laser.targetZ - laser.lastTargetZ) * partialTicks + laser.lastTargetZ;
		}
		double vx = tx - laser.getPos().getX();
		double vy = ty - laser.getPos().getY() + 3;
		double vz = tz - laser.getPos().getZ();

		Vec3 nVec = Vec3.createVectorHelper(vx, vy, vz);
		nVec = nVec.normalize();

		double d = 1.5D;
		nVec.xCoord *= d;
		nVec.yCoord *= d;
		nVec.zCoord *= d;

		Vec3 vec = Vec3.createVectorHelper(vx - nVec.xCoord, vy - nVec.yCoord, vz - nVec.zCoord);

		double length = vec.lengthVector();
		double yaw = Math.toDegrees(Math.atan2(vec.xCoord, vec.zCoord));
		double sqrt = MathHelper.sqrt(vec.xCoord * vec.xCoord + vec.zCoord * vec.zCoord);
		double pitch = Math.toDegrees(Math.atan2(vec.yCoord, sqrt));
		//turns out using tan(vec.yCoord, length) was inaccurate,
		//the emitter wouldn't match the laser perfectly when pointing down

		bindTexture(ResourceManager.mining_laser_base_tex);
		ResourceManager.mining_laser.renderPart("Base");

		//GlStateManager.shadeModel(GL11.GL_SMOOTH);
		GL11.glPushMatrix();
		GL11.glRotated(yaw, 0, 1, 0);
		bindTexture(ResourceManager.mining_laser_pivot_tex);
		ResourceManager.mining_laser.renderPart("Pivot");
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glTranslated(0, -1, 0);
		GL11.glRotated(pitch + 90, -1, 0, 0);
		GL11.glTranslated(0, 1, 0);
		bindTexture(ResourceManager.mining_laser_laser_tex);
		ResourceManager.mining_laser.renderPart("Laser");
		GL11.glPopMatrix();
		//GlStateManager.shadeModel(GL11.GL_FLAT);

		if(laser.beam) {
			length = vec.lengthVector();
			GL11.glTranslated(nVec.xCoord, nVec.yCoord - 1, nVec.zCoord);
			int range = (int)Math.ceil(length * 0.5);
			BeamPronter.prontBeam(vec, EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0xa00000, 0xFFFFFF, 0, 1, 0, 3, 0.09F);
		}

		GL11.glPopMatrix();
	}
}
