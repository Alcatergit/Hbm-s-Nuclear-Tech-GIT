package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.inventory.control_panel.IControllable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityControlPanel extends TileEntity implements ITickable, IControllable {

	public ItemStackHandler inventory;
	public ControlPanel panel;
	
	public TileEntityControlPanel() {
		inventory = new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
			}
		};
	}

	@Override
	public void onLoad(){
		panel = new ControlPanel(this, 8, 4);
		loadClient();
	}
	
	@SideOnly(Side.CLIENT)
	public void loadClient(){
		Matrix4f mat = new Matrix4f();
		mat.translate(new Vector3f(-0.15F, 0.15F, -0.1F));
		mat.rotate(-0.528131795589244F, new Vector3f(0, 0, 1));
		mat.rotate((float)Math.toRadians(90), new Vector3f(0, 1, 0));
		mat.scale(new Vector3f(0.1F, 0.1F, 0.1F));
		panel.setTransform(mat);
	}
	
	@Override
	public void update() {
		panel.update();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
	}

	@Override
	public void receiveEvent(BlockPos from, ControlEvent e){
		panel.receiveEvent(from, e);
	}
	
	@Override
	public List<String> getInEvents(){
		return Arrays.asList("tick");
	}
}
