package com.hbm.tileentity.machine;

import com.hbm.lib.ForgeDirection;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.inventory.material.Mats.MaterialStack;

import api.hbm.block.ICrucibleAcceptor;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Base class for all foundry channel type blocks - channels, casts, basins, tanks, etc.
 * Foundry type blocks can only hold one type at a time and usually either store or move it around.
 * @author hbm
 *
 */
public abstract class TileEntityFoundryBase extends TileEntity implements ITickable, ICrucibleAcceptor {
	
	public NTMMaterial type;
	protected NTMMaterial lastType;
	public int amount;
	protected int lastAmount;
	
	@Override
	public void update() {
		if(this.lastType != this.type || this.lastAmount != this.amount){
			if(!world.isRemote || (world.isRemote && shouldClientReRender())) {
			
				IBlockState state = world.getBlockState(pos);
				world.markAndNotifyBlock(pos, world.getChunkFromBlockCoords(pos), state, state, 2);
				this.lastType = this.type;
				this.lastAmount = this.amount;
				this.markDirty();
			}
		}
	}
	
	/** Recommended FALSE for things that update a whole lot. TRUE if updates only happen once every few ticks. */
	protected boolean shouldClientReRender() {
		return true;
	}
	
	@Override
	public SPacketUpdateTileEntity getUpdatePacket(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return new SPacketUpdateTileEntity(this.getPos(), 0, nbt);
	}
	
	@Override
	public NBTTagCompound getUpdateTag(){
		NBTTagCompound nbt = new NBTTagCompound();
		this.writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag) {
		this.readFromNBT(tag);
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		this.type = Mats.matById.get(nbt.getInteger("type"));
		this.amount = nbt.getInteger("amount");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(this.type == null) {
			nbt.setInteger("type", -1);
		} else {
			nbt.setInteger("type", this.type.id);
		}
		nbt.setInteger("amount", this.amount);
		
		return super.writeToNBT(nbt);
	}
	
	public abstract int getCapacity();
	
	/**
	 * Standard check for testing if this material stack can be added to the casting block. Checks:<br>
	 * - type matching<br>
	 * - amount being at max<br>
	 */
	public boolean standardCheck(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		if(this.type != null && this.type != stack.material && this.amount > 0) return false; //reject if there's already a different material
		if(this.amount >= this.getCapacity()) return false; //reject if the buffer is already full
		return true;
	}
	
	/**
	 * Standardized adding of material via pouring or flowing. Does:<br>
	 * - sets material to match the input
	 * - adds the amount, not exceeding the maximum
	 * - returns the amount that cannot be added
	 */
	public MaterialStack standardAdd(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		this.type = stack.material;
		
		if(stack.amount + this.amount <= this.getCapacity()) {
			this.amount += stack.amount;
			return null;
		}
		
		int required = this.getCapacity() - this.amount;
		this.amount = this.getCapacity();
		
		stack.amount -= required;
		
		return stack;
	}

	/** Standard check with no additional limitations added */
	@Override
	public boolean canAcceptPartialFlow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		return this.standardCheck(world, p, side, stack);
	}
	
	/** Standard flow, no special handling required */
	@Override
	public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) {
		return this.standardAdd(world, p, side, stack);
	}

	/** Standard check, but with the additional limitation that the only valid source direction is UP */
	@Override
	public boolean canAcceptPartialPour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		if(side != ForgeDirection.UP) return false;
		return this.standardCheck(world, p, side, stack);
	}

	/** Standard flow, no special handling required */
	@Override
	public MaterialStack pour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {
		return this.standardAdd(world, p, side, stack);
	}
}
