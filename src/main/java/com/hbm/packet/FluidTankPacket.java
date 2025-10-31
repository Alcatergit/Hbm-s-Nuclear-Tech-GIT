package com.hbm.packet;

import com.hbm.interfaces.ITankPacketAcceptor;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidTankPacket extends RecordablePacket {

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
	public void fromBits(LeafiaBuf buf) {
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
			NBTTagCompound arbitaryTag = buf.readNBT();
			if(id.equals("HBM_EMPTY") || FluidRegistry.getFluid(id) == null){
				tag.setString("Empty", "");
			} else {
				FluidStack stack = new FluidStack(FluidRegistry.getFluid(id), amount);
				stack.tag = arbitaryTag;
				stack.writeToNBT(tag);
			}
			tags[i] = tag;
		}
	}

	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(length);
		for(int i = 0; i < length ; i++){
			buf.writeInt(tanks[i].getFluidAmount());
			byte[] bytes = tanks[i].getFluid() == null ? "HBM_EMPTY".getBytes() : FluidRegistry.getFluidName(tanks[i].getFluid()).getBytes();
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
			buf.writeNBT((tanks[i].getFluid() != null && tanks[i].getFluid().tag != null) ? tanks[i].getFluid().tag : new NBTTagCompound());
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
