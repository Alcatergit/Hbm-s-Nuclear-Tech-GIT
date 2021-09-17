package com.hbm.explosion;

import java.util.List;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.util.ContaminationUtil;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

public class ExplosionHurtUtil {

	/**
	 * Adds radiation to entities in an AoE
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @param outer The least amount of radiation received on the very edge of the AoE
	 * @param inner The greatest amount of radiation received on the very center of the AoE
	 * @param radius
	 */
	public static void doRadiation(World world, double x, double y, double z, float outer, float inner, double radius) {

		List<EntityLivingBase> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(x - radius, y - radius, z - radius, x + radius, y + radius, z + radius));

		for(EntityLivingBase entity : entities) {

			Vec3 vec = Vec3.createVectorHelper(x - entity.posX, y - entity.posY, z - entity.posZ);

			double dist = vec.lengthVector();

			if(dist > radius)
				continue;

			double interpolation = 1 - (dist / radius);
			float rad = (float) (outer + (inner - outer) * interpolation);

			ContaminationUtil.applyRadData(entity, rad);
		}
	}
}
