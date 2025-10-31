package com.leafia.contents.machines.processing.solidifier;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.manfacturing.soldering.SolderingRender;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SolidifierTE extends TileEntityMachineBase implements LeafiaQuickModel {
	public long power;
	public static final long maxPower = 100000;
	public static final int usageBase = 500;
	public int usage;
	public int progress;
	public static final int processTimeBase = 100;
	public int processTime;

	public FluidTank tank;
	public SolidifierTE() {
		super(5);
		tank = new FluidTank(24000);
	}

	@Override
	public String getName() {
		return "container.machineSolidifier";
	}

	@Override
	public String _resourcePath() {
		return "solidifier";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/solidifier";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new SolderingRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_solidifier;
	}

	@Override
	public double _sizeReference() {
		return 15;
	}

	@Override
	public double _itemYoffset() {
		return 0;
	}
}
