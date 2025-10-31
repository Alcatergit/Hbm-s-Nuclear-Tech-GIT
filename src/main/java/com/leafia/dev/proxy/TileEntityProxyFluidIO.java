package com.leafia.dev.proxy;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.math.FiaMatrix;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class TileEntityProxyFluidIO extends TileEntityProxyCombo implements IFluidHandler {
	public enum ProxyType { NONE, INPUT, OUTPUT, BOTH }
	public Function<Vec3i,ProxyType> function;
	Vec3i getRelativePosition() {
		BlockDummyable dummyable = (BlockDummyable)getBlockType();
		BlockPos core = dummyable.findCore(world,pos);
		if (core == null) return null;
		FiaMatrix mat = new FiaMatrix(new Vec3d(pos).add(0.5,0.5,0.5));
		FiaMatrix rot = dummyable.getRotationMat(world,core);
		if (rot == null) return null;
		FiaMatrix cmat = new FiaMatrix(new Vec3d(core).add(0.5,0.5,0.5)).rotateAlong(rot);
		FiaMatrix relative = cmat.toObjectSpace(mat);
		return new Vec3i((int)Math.round(relative.position.x),(int)Math.round(relative.position.y),(int)Math.round(relative.position.z));
	}
/*
	@Override
	public void update() {
		Vec3i mat = getRelativePosition();
		if (mat == null) return;
		ProxyType type = function.apply(mat);
		LeafiaDebug.debugPos(world,pos,0.05f,0xFFFF00,type.name());
	}
*/

	public TileEntityProxyFluidIO() { }

	void tryUpdateFunction() {
		if (function == null && te() != null) {
			IBlockState state = world.getBlockState(pos);
			Block block = te().getBlockType();
			if (block instanceof BlockDummyable dummyable) {
				TileEntity te = dummyable.createNewTileEntity(world,state.getValue(BlockDummyable.META));
				if (te instanceof TileEntityProxyFluidIO io)
					function = io.function;
			}
		}
	}

	public TileEntityProxyFluidIO(boolean inventory,boolean power,Function<Vec3i,ProxyType> function) {
		super(inventory,power,true);
		this.function = function;
	}

	TileEntity te() {
		if(tile == null)
			tile = this.getTE();
		return tile;
	}

	IFluidHandler tef() {
		return (IFluidHandler)te();
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tef().getTankProperties();
	}
	@Override
	public int fill(FluidStack resource,boolean doFill) {
		if (this.function == null) tryUpdateFunction();
		if (this.function == null) return 0;
		Vec3i mat = getRelativePosition();
		if (mat == null) return 0;
		ProxyType type = function.apply(mat);
		IFluidHandler tef = tef();
		return (type == ProxyType.INPUT || type == ProxyType.BOTH) ?
				(tef instanceof IFluidHandlerOut out ? out.fillForce(resource,doFill) : tef.fill(resource,doFill)) : 0;
	}
	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		if (this.function == null) tryUpdateFunction();
		if (this.function == null) return null;
		Vec3i mat = getRelativePosition();
		if (mat == null) return null;
		ProxyType type = function.apply(mat);
		IFluidHandler tef = tef();
		return (type == ProxyType.OUTPUT || type == ProxyType.BOTH) ?
				(tef instanceof IFluidHandlerIn in ? in.drainForce(resource,doDrain) : tef.drain(resource,doDrain)) : null;
	}
	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		if (this.function == null) tryUpdateFunction();
		if (this.function == null) return null;
		Vec3i mat = getRelativePosition();
		if (mat == null) return null;
		ProxyType type = function.apply(mat);
		IFluidHandler tef = tef();
		return (type == ProxyType.OUTPUT || type == ProxyType.BOTH) ?
				(tef instanceof IFluidHandlerIn in ? in.drainForce(maxDrain,doDrain) : tef.drain(maxDrain,doDrain)) : null;
	}

	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
}
