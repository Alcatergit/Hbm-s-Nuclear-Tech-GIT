package com.leafia.contents.machines.processing.electrolyzer;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElectrolyzerTE extends TileEntityMachineBase implements LeafiaQuickModel {
	public long power;
	public static final long maxPower = 20000000;
	public static final int usageOreBase = 10_000;
	public static final int usageFluidBase = 10_000;
	public int usageOre;
	public int usageFluid;

	public int progressFluid;
	public int processFluidTime = 100;
	public int progressOre;
	public int processOreTime = 600;

	public MaterialStack leftStack;
	public MaterialStack rightStack;
	public int maxMaterial = MaterialShapes.BLOCK.q(16);

	public FluidTank[] tanks;
	public ElectrolyzerTE() {
		super(21);
		tanks = new FluidTank[4];
		tanks[0] = new FluidTank(16000);
		tanks[1] = new FluidTank(16000);
		tanks[2] = new FluidTank(16000);
		tanks[3] = new FluidTank(16000);
	}

	@Override
	public String getName() {
		return "container.machineElectrolyzer";
	}

	@Override
	public String _resourcePath() {
		return "electrolyzer";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/electrolyser";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new ElectrolyzerRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_electrolyzer;
	}

	@Override
	public double _sizeReference() {
		return 16.2;
	}

	@Override
	public double _itemYoffset() {
		return 0.05;
	}
}
