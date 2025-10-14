package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.mob.EntityCyberCrab;
import com.hbm.entity.mob.EntityGlowingOne;
import com.hbm.entity.mob.EntityTeslaCrab;

import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityCyberCrab extends TileEntity implements ITickable {

	@Override
	public void update() {
		if(!this.world.isRemote) {

			if(world.rand.nextInt(400) == 0 && world.isAreaLoaded(pos, 5) && world.getBlockState(pos.up()).getBlock() == Blocks.AIR) {
                if(this.getBlockType() == ModBlocks.meteor_spawner) {
                    List<Entity> entities = this.world.getEntitiesWithinAABB(EntityCyberCrab.class, new AxisAlignedBB(pos.getX() - 5, pos.getY() - 1, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 3, pos.getZ() + 5));

                    if (entities.size() < 7) {

                        EntityCyberCrab crab;

                        if (world.rand.nextInt(5) == 0)
                            crab = new EntityTeslaCrab(world);
                        else
                            crab = new EntityCyberCrab(world);

                        crab.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        world.spawnEntity(crab);
                    }
                } else {
                    List<Entity> entities = this.world.getEntitiesWithinAABB(EntityGlowingOne.class, new AxisAlignedBB(pos.getX() - 10, pos.getY() - 1, pos.getZ() - 10, pos.getX() + 10, pos.getY() + 3, pos.getZ() + 10));

                    if(entities.size() < 4) {

                        EntityGlowingOne glo = new EntityGlowingOne(world);
                        glo.setPosition(pos.getX() + 0.5, pos.getY() + 1, pos.getZ() + 0.5);
                        world.spawnEntity(glo);
                    }
                }
			}
		}
	}
}
