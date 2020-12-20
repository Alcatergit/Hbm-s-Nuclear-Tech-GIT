package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityTeslaCrab;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCyberCrab extends TileEntity implements ITickable {

	int age = 0;

	@Override
	public void update() {
		if(!this.world.isRemote) {

			age++;
			if(age > 200 && world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
				List<Entity> entities = this.world.getEntitiesWithinAABB(EntityCyberCrab.class, new AxisAlignedBB(pos.getX() - 5, pos.getY() - 2, pos.getZ() - 5, pos.getX() + 6, pos.getY() + 4, pos.getZ() + 6));

				if(entities.size() < 5) {

					EntityCyberCrab crab;

					if(world.rand.nextInt(5) == 0)
						crab = new EntityTeslaCrab(world);
					else
						crab = new EntityCyberCrab(world);

					crab.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
					world.spawnEntity(crab);
				}

				age = 0;
			}
		}
	}

}
