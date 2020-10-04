package com.hbm.packet;

import com.hbm.interfaces.ITankPacketAcceptor;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidTankPacket implements IMessage {

	int x;
	int y;
	int z;
	FluidTank[] tanks;
	NBTTagCompound[] tags;
	int length;
	
	public FluidTankPacket() {

	}

	public FluidTankPacket(int x, int y, int z, FluidTank[] tanks) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.tanks = tanks;
		this.length = tanks.length;
	}
	
	public FluidTankPacket(BlockPos pos, FluidTank... tanks){
		this(pos.getX(), pos.getY(), pos.getZ(), tanks);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		length = buf.readInt();
		tags = new NBTTagCompound[length];
		for(int i = 0; i < length; i++){
			int amount = buf.readInt();
			byte[] bytes = new byte[buf.readInt()];
			buf.readBytes(bytes);
			String id = new String(bytes);
			NBTTagCompound tag = new NBTTagCompound();
			if(id.equals("HBM_EMPTY") || FluidRegistry.getFluid(id) == null){
				tag.setString("Empty", "");
			} else {
				new FluidStack(FluidRegistry.getFluid(id), amount).writeToNBT(tag);
			}
			tags[i] = tag;
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(length);
		for(int i = 0; i < length ; i++){
			buf.writeInt(tanks[i].getFluidAmount());
			byte[] bytes = tanks[i].getFluid() == null ? "HBM_EMPTY".getBytes() : FluidRegistry.getFluidName(tanks[i].getFluid()).getBytes();
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
		}
	}

	public static class Handler implements IMessageHandler<FluidTankPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(FluidTankPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));
				if (te != null && te instanceof ITankPacketAcceptor) {
					((ITankPacketAcceptor)te).recievePacket(m.tags);
				}
			});
			

			return null;
		}
	}
}
