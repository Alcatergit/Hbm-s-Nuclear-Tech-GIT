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

public class TileEntityDecon extends TileEntity implements ITickable {

	private static float radRemove;
	private static float digammaRemove;
	public TileEntityDecon(float rad, float dig) {
		System.out.println("New Tile+ "+rad+"rad "+dig+"dig");
		super();
		this.radRemove = rad;
		this.digammaRemove = dig;
	}

	@Override
	public void update() {
		if(!this.world.isRemote) {
			List<Entity> entities = this.world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5, pos.getY(), pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 2, pos.getZ() + 1.5));

			if(!entities.isEmpty()) {
				for(Entity e : entities) {
					if(e.hasCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null)){
						if(this.radRemove > 0.0F){
							System.out.println("Raddec "this.radRemove);
							e.getCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).decreaseRads(this.radRemove);
						}
						if(this.digammaRemove > 0.0F){
							System.out.println("DigaDec "this.digammaRemove);
							e.getCapability(EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).decreaseDigamma(this.digammaRemove);
						}
					}
				}
			}
		}
	}
}
