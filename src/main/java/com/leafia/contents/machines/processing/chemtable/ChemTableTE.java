package com.leafia.contents.machines.processing.chemtable;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ChemTableTE extends TileEntityMachineBase implements LeafiaQuickModel, IGUIProvider {
	public ChemTableTE() {
		super(8);
	}

	@Override
	public String _resourcePath() {
		return "chemtable";
	}

	@Override
	public String _assetPath() {
		return "leafia/production/chemtable";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new ChemTableRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.chemtable;
	}

	@Override
	public double _sizeReference() {
		return 3.8;
	}

	@Override
	public double _itemYoffset() {
		return -0.1;
	}

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return null;
	}
	@Override
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return null;
	}

	@Override
	public String getName() {
		return "tile.chemtable.name";
	}
}
