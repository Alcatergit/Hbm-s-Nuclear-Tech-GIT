package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge.ReactorFuelType;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LargeReactorPacket implements IMessage {

	int x, y, z, rods, coreHeat, hullHeat, fuel, maxFuel, waste, maxWaste, type;
	
	public LargeReactorPacket() {
	}
	
	public LargeReactorPacket(BlockPos pos, int rods, int coreHeat, int hullHeat, int fuel, int maxFuel, int waste, int maxWaste, int type) {
		x = pos.getX();
		y = pos.getY();
		z = pos.getZ();
		this.rods = rods;
		this.coreHeat = coreHeat;
		this.hullHeat = hullHeat;
		this.fuel = fuel;
		this.maxFuel = maxFuel;
		this.waste = waste;
		this.maxWaste = maxWaste;
		this.type = type;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		rods = buf.readInt();
		coreHeat = buf.readInt();
		hullHeat = buf.readInt();
		fuel = buf.readInt();
		maxFuel = buf.readInt();
		waste = buf.readInt();
		maxWaste = buf.readInt();
		type = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(rods);
		buf.writeInt(coreHeat);
		buf.writeInt(hullHeat);
		buf.writeInt(fuel);
		buf.writeInt(maxFuel);
		buf.writeInt(waste);
		buf.writeInt(maxWaste);
		buf.writeInt(type);
	}

	
	public static class Handler implements IMessageHandler<LargeReactorPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(LargeReactorPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));
				if(te instanceof TileEntityMachineReactorLarge){
					TileEntityMachineReactorLarge r = (TileEntityMachineReactorLarge)te;
					r.rods = m.rods;
					r.coreHeat = m.coreHeat;
					r.hullHeat = m.hullHeat;
					r.fuel = m.fuel;
					r.maxFuel = m.maxFuel;
					r.waste = m.waste;
					r.maxWaste = m.maxWaste;
					r.type = ReactorFuelType.getEnum(m.type);
				}
			});
			return null;
		}
		
	}

}
