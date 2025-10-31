package com.leafia.contents.machines.manfacturing.arcwelder;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ArcWelderTE extends TileEntityMachineBase implements LeafiaQuickModel {
	public long power;
	public long maxPower = 2_000;
	public long consumption;

	public int progress;
	public int processTime = 1;

	public FluidTank tank;
	public ItemStack display;
	public ArcWelderTE() {
		super(8);
		tank = new FluidTank(24000);
	}

	@Override
	public String getName() {
		return "container.machineArcWelder";
	}

	@Override
	public String _resourcePath() {
		return "arcwelder";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/arc_welder";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new ArcWelderRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_arcwelder;
	}

	@Override
	public double _sizeReference() {
		return 5;
	}

	@Override
	public double _itemYoffset() {
		return 0;
	}
}
