package com.leafia.contents.machines.processing.assemtable;

import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.processing.assemtable.container.AssemTableContainer;
import com.leafia.contents.machines.processing.assemtable.container.AssemTableGUI;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.group.LeafiaSet;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class AssemTableTE extends TileEntityMachineBase implements LeafiaQuickModel, IGUIProvider, ITickable, LeafiaPacketReceiver {
	public AssemTableTE() {
		super(8);
	}

	@Override
	public String _resourcePath() {
		return "assemtable";
	}

	@Override
	public String _assetPath() {
		return "leafia/production/assemtable";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new AssemTableRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.assemtable;
	}

	@Override
	public double _sizeReference() {
		return 3.2;
	}

	@Override
	public double _itemYoffset() {
		return -0.05;
	}

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AssemTableContainer(player.inventory,this);
	}
	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AssemTableGUI(player.inventory,this);
	}

	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();
	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}

	@Override
	public String getPacketIdentifier() {
		return "assemtable";
	}

	@Override
	public void onReceivePacketLocal(byte key,Object value) {

	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {

	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}

	@Override
	public void update() {

	}

	@Override
	public String getName() {
		return "tile.assemtable.name";
	}
}
