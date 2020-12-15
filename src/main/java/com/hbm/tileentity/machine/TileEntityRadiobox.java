package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.Radiobox;
import com.hbm.lib.ModDamageSource;

import net.minecraft.entity.monster.EntityMob;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;

public class TileEntityRadiobox extends TileEntity implements ITickable {

	public double freq;
	public int type;
	public String message;
	public int music;
	
	@Override
	public void update() {
		if(!world.isRemote && world.getBlockState(pos).getValue(Radiobox.STATE)) {
			
			int range = 15;
			
			world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(pos.getX() - range, pos.getY() - range, pos.getZ() - range, pos.getX() + range, pos.getY() + range, pos.getZ() + range)).forEach(e -> e.attackEntityFrom(ModDamageSource.enervation, 20.0F));;
		}
	}

}
