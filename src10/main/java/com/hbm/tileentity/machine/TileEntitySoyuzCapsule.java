package com.hbm.tileentity.machine;

import com.hbm.tileentity.TileEntityInventoryBase;

import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySoyuzCapsule extends TileEntityInventoryBase {

	private static final AxisAlignedBB SOYUZ_CAPSULE_BOX = new AxisAlignedBB(-1, -1, -1, 2, 3, 2);
	
	public TileEntitySoyuzCapsule() {
		super(19);
	}

	@Override
	public String getName() {
		return "container.soyuzCapsule";
	}

	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return SOYUZ_CAPSULE_BOX.offset(pos);
    }
}
