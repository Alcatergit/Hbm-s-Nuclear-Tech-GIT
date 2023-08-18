package com.hbm.tileentity.machine;

import java.util.Arrays;
import java.util.List;

import com.hbm.main.MainRegistry;
import com.hbm.packet.NBTControlPacket;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.hbm.interfaces.IControlReceiver;
import com.hbm.inventory.control_panel.Control;
import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.packet.ControlPanelUpdatePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityControlPanel extends TileEntity implements ITickable, IControllable, IControlReceiver {

	public ItemStackHandler inventory;
	public ControlPanel panel;

	public TileEntityControlPanel(){
		inventory = new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot){
				markDirty();
			}
		};
		panel = new ControlPanel(this, 0.25F, (float) (Math.toRadians(20)), 0, 0, 0.25F, 0);
	}

	@Override
	public void onLoad(){
		if(world.isRemote)
			loadClient();
		else {
			for(Control c : panel.controls){
				for(BlockPos b : c.connectedSet){
					ControlEventSystem.get(world).subscribeTo(this, b);
				}
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public void updateTransform() {
		Matrix4f mat = new Matrix4f();

//		full block button stars on the north-east block.
//		first translate puts center of that button on the Y axis origin
//		now it can be rotated
//		then because it's still on the origin, it can be scaled seamlessly
//		then the button is translated into the center of the panel block

		mat.translate(new Vector3f(0.5F, panel.height, 0.5F));
		switch(getBlockMetadata()){
			case 4:
				mat.rotate((float)Math.toRadians(180), new Vector3f(0, 1, 0));
				break;
			case 2:
				mat.rotate((float)Math.toRadians(90), new Vector3f(0, 1, 0));
				break;
			case 5:
				break;
			case 3:
				mat.rotate((float)Math.toRadians(270), new Vector3f(0, 1, 0));
				break;
		}
		mat.rotate(-panel.angle, new Vector3f(0, 0, 1));
		mat.rotate((float)Math.toRadians(90), new Vector3f(0, 1, 0));
		mat.scale(new Vector3f(0.1F, 0.1F, 0.1F));
		mat.translate(new Vector3f(0.5F, 0, 0.5F));

		panel.setTransform(mat);
	}

	@SideOnly(Side.CLIENT)
	public void loadClient(){
		updateTransform();
	}

	@Override
	public void update(){
		panel.update();
		if(!panel.changedVars.isEmpty()) {
			markDirty();
			PacketDispatcher.wrapper.sendToAllTracking(new ControlPanelUpdatePacket(pos, panel.changedVars), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 1));
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound){
		compound.setTag("panel", panel.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound){
		panel.readFromNBT(compound.getCompoundTag("panel"));
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

	@Override
	public BlockPos getControlPos(){
		return getPos();
	}

	@Override
	public World getControlWorld(){
		return getWorld();
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		return new SPacketUpdateTileEntity(pos, -1, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag(){
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt){
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public boolean hasPermission(EntityPlayer player){
		return true;
	}

	@Override
	public void receiveControl(NBTTagCompound data){
		if(data.hasKey("full_set")) { //? flag to inform that this data has all required elements to update and restore nbt shit
			markDirty();
			//? remove every block associated with every component from sensitivity list
			for(Control c : panel.controls){
				for(BlockPos b : c.connectedSet){
					ControlEventSystem.get(world).unsubscribeFrom(this, b);
				}
			}
			this.panel.readFromNBT(data); //? remove all global vars and components, and reset them with those provided in nbt
			for(Control c : panel.controls){ //? resubscribe all blocks associated with the new components to sensitivity list
				for(BlockPos b : c.connectedSet){
					ControlEventSystem.get(world).subscribeTo(this, b);
				}
			}
			//? this TE block pos is the point of which all component-associated blocks are tracking for data updates
			PacketDispatcher.wrapper.sendToAllTracking(new ControlPanelUpdatePacket(pos, data), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 1));
		} else if(data.hasKey("click_control")) {
			ControlEvent evt = ControlEvent.readFromNBT(data);
			panel.controls.get(data.getInteger("click_control")).receiveEvent(evt);
		}
	}

	public void validate() {
		super.validate();
		ControlEventSystem.get(this.world).addControllable(this);
	}

	public void invalidate() {
		super.invalidate();
		ControlEventSystem.get(this.world).removeControllable(this);
	}

	public float[] getBox() {
		float baseSizeX = 1-(panel.b_off+panel.d_off);
		float baseSizeY = 1-(panel.a_off+panel.c_off);

		double base_hyp = 1/Math.cos(Math.abs(panel.angle));
		double panel_hyp = baseSizeY/Math.cos(Math.abs(panel.angle));

		float box_width = 10;
		float box_height = (float) (base_hyp*10);
		float minX = (-box_width/2) + (panel.d_off*box_width);
		float minY = (-box_height/2) + (panel.a_off*box_height);

		return new float[] { minX, minY, minX+baseSizeX*10, (float) (minY+panel_hyp*10)};
	}

	public AxisAlignedBB getBoundingBox(EnumFacing facing) {
		AxisAlignedBB defAABB = null;
		float height1 = ControlPanel.getSlopeHeightFromZ(1-panel.c_off, panel.height, -panel.angle);
		float height0 = ControlPanel.getSlopeHeightFromZ(panel.a_off, panel.height, -panel.angle);

		//TODO: base transform.
		switch (facing) {
			case WEST:
				defAABB = new AxisAlignedBB(panel.c_off, 0, panel.d_off, 1 - panel.a_off, Math.max(height0, height1), 1 - panel.b_off);
				break;
			case EAST:
				defAABB = new AxisAlignedBB(panel.a_off, 0, panel.b_off, 1 - panel.c_off, Math.max(height0, height1), 1 - panel.d_off);
				break;
			case NORTH:
				defAABB = new AxisAlignedBB(panel.b_off, 0, panel.c_off, 1 - panel.d_off, Math.max(height0, height1), 1 - panel.a_off);
				break;
			case SOUTH:
				defAABB = new AxisAlignedBB(panel.d_off, 0, panel.a_off, 1 - panel.b_off, Math.max(height0, height1), 1 - panel.c_off);
				break;
//				default:
//					defAABB = new AxisAlignedBB(panel.a_off, 0, panel.b_off, 1-panel.c_off, Math.max(height0, height1), 1-panel.d_off);
		}

		return defAABB;
	}
}
