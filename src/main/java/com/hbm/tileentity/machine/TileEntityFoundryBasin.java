package com.hbm.tileentity.machine;

import com.hbm.lib.ForgeDirection;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.inventory.material.NTMMaterial;

import com.hbm.main.MainRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityFoundryBasin extends TileEntityFoundryCastingBase implements IRenderFoundry {

	public TileEntityFoundryBasin() {
		super(2);
	}

	@Override
	public int getMoldSize() {
		return 1;
	}

	/* Basin can't accept sideways flowing */
	@Override public boolean canAcceptPartialFlow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return stack; }

	@Override
	public boolean shouldRender() {
		MainRegistry.logger.info("Basin type: " + this.type);
		MainRegistry.logger.info("Basin amount: " + this.amount);
		return this.type != null && this.amount > 0;
	}

	@Override
	public double getLevel() {
		return 0.125 + this.amount * 0.75D / this.getCapacity();
	}

	@Override
	public NTMMaterial getMat() {
		return this.type;
	}

	@Override public double minX() { return 0.125D; }
	@Override public double maxX() { return 0.875D; }
	@Override public double minZ() { return 0.125D; }
	@Override public double maxZ() { return 0.875D; }
	@Override public double moldHeight() { return 0.13D; }
	@Override public double outHeight() { return 0.875D; }
}
