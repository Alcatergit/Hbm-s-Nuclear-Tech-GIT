package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityITER;
import com.hbm.tileentity.machine.TileEntityMachineLargeTurbine;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class FluidTypePacketTest implements IMessage {

	int x;
	int y;
	int z;
	Fluid[] fluids;
	int length;
	
	public FluidTypePacketTest() {
	}
	
	public FluidTypePacketTest(int x, int y, int z, Fluid[] fluids) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.fluids = fluids;
		this.length = fluids.length;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		length = buf.readInt();
		fluids = new Fluid[length];
		for(int i = 0; i < length; i++){
			byte[] bytes = new byte[buf.readInt()];
			buf.readBytes(bytes);
			String name = new String(bytes);
			if(name.equals("HBM_EMPTY")){
				fluids[i] = null;
			} else {
				fluids[i] = FluidRegistry.getFluid(name);
			}
		}
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(length);
		
		for(int i = 0; i < length ; i++){
			byte[] bytes = fluids[i] == null ? "HBM_EMPTY".getBytes() : fluids[i].getName().getBytes();
			buf.writeInt(bytes.length);
			buf.writeBytes(bytes);
		}
	}
	
	public static class Handler implements IMessageHandler<FluidTypePacketTest, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(FluidTypePacketTest m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));
				if (te instanceof TileEntityMachineTurbine) {
					((TileEntityMachineTurbine)te).tankTypes[0] = m.fluids[0];
					((TileEntityMachineTurbine)te).tankTypes[1] = m.fluids[1];
				} else if(te instanceof TileEntityMachineReactorLarge){
					((TileEntityMachineReactorLarge) te).tankTypes[2] = m.fluids[0];
				} else if(te instanceof TileEntityITER){
					((TileEntityITER) te).plasmaType = m.fluids[0];
				} else if(te instanceof TileEntityMachineLargeTurbine){
					((TileEntityMachineLargeTurbine) te).types[0] = m.fluids[0];
					((TileEntityMachineLargeTurbine) te).types[1] = m.fluids[1];
				}
			});
			return null;
		}
		
	}

}
