package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.capability.HbmLivingCapability.EntityHbmPropsProvider;
import com.hbm.capability.HbmLivingProps;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityDeconDi extends TileEntity implements ITickable {

	private static float digammaRemove;
	public TileEntityDeconDi(float dig) {
		super();
		this.digammaRemove = dig;
	}

	@Override
	public void update() {
		if(!this.world.isRemote) {
			List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5, pos.getY(), pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 2, pos.getZ() + 1.5));

			if(!entities.isEmpty()) {
				for(Entity e : entities) {
					if(e.hasCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null)){
						if(this.digammaRemove > 0.0F){
							e.getCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).decreaseDigamma(this.digammaRemove);
						}
					}
				}
			}
		}
	}
}
