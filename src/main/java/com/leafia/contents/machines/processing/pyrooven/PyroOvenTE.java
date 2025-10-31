package com.leafia.contents.machines.processing.pyrooven;

import com.hbm.blocks.ModBlocks;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PyroOvenTE extends TileEntityMachineBase implements LeafiaQuickModel {
	public long power;
	public static final long maxPower = 10_000_000;
	public boolean isVenting;
	public boolean isProgressing;
	public float progress;
	public static int consumption = 10_000;

	public int prevAnim;
	public int anim = 0;

	public FluidTank[] tanks;

	private AudioWrapper audio;

	public PyroOvenTE() {
		super(6,50);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(24_000);
		tanks[1] = new FluidTank(24_000);
	}

	@Override
	public String getName() {
		return "container.machinePyroOven";
	}

	@Override
	public String _resourcePath() {
		return "pyrolysis";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/pyrooven";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new PyroOvenRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_pyrolysis;
	}

	@Override
	public double _sizeReference() {
		return 11.5;
	}

	@Override
	public double _itemYoffset() {
		return 0.03;
	}
}
