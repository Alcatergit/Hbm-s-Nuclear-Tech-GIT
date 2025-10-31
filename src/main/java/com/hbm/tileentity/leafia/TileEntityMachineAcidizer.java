package com.hbm.tileentity.leafia;

import com.hbm.blocks.ModBlocks;
import com.hbm.render.tileentity.RenderAcidizer;
import com.hbm.tileentity.machine.CrystallizerCopyBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityMachineAcidizer extends CrystallizerCopyBase implements LeafiaQuickModel {
	public TileEntityMachineAcidizer() {
		super();
	}

	@Override
	public String _resourcePath() {
		return "acidizer";
	}

	@Override
	public String _assetPath() {
		return "machines/acidizer";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new RenderAcidizer();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_acidizer;
	}

	@Override
	public double _sizeReference() {
		return 9.7;
	}

	@Override
	public double _itemYoffset() {
		return -0.15;
	}
}