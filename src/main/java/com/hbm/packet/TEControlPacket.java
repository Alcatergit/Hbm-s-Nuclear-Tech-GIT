package com.hbm.packet;

import com.hbm.tileentity.machine.TileEntityReactorControl;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEControlPacket implements IMessage {

	int x;
	int y;
	int z;
	int hullHeat;
	int coreHeat;
	int fuel;
	int water;
	int cool;
	int steam;
	int maxWater;
	int maxCool;
	int maxSteam;
	int compression;
	int rods;
	int maxRods;
	boolean isOn;
	boolean auto;
	boolean isLinked;

	public TEControlPacket() {

	}

	public TEControlPacket(int x, int y, int z, int hullHeat, int coreHeat, int fuel, int water, int cool, int steam, int maxWater, int maxCool, int maxSteam, int compression, int rods, int maxRods, boolean isOn, boolean auto, boolean isLinked) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.hullHeat = hullHeat;
		this.coreHeat = coreHeat;
		this.fuel = fuel;
		this.water = water;
		this.cool = cool;
		this.steam = steam;
		this.maxWater = maxWater;
		this.maxCool = maxCool;
		this.maxSteam = maxSteam;
		this.compression = compression;
		this.rods = rods;
		this.maxRods = maxRods;
		this.isOn = isOn;
		this.auto = auto;
		this.isLinked = isLinked;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		hullHeat = buf.readInt();
		coreHeat = buf.readInt();
		fuel = buf.readInt();
		water = buf.readInt();
		cool = buf.readInt();
		steam = buf.readInt();
		maxWater = buf.readInt();
		maxCool = buf.readInt();
		maxSteam = buf.readInt();
		compression = buf.readInt();
		rods = buf.readInt();
		maxRods = buf.readInt();
		isOn = buf.readBoolean();
		auto = buf.readBoolean();
		isLinked = buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(hullHeat);
		buf.writeInt(coreHeat);
		buf.writeInt(fuel);
		buf.writeInt(water);
		buf.writeInt(cool);
		buf.writeInt(steam);
		buf.writeInt(maxWater);
		buf.writeInt(maxCool);
		buf.writeInt(maxSteam);
		buf.writeInt(compression);
		buf.writeInt(rods);
		buf.writeInt(maxRods);
		buf.writeBoolean(isOn);
		buf.writeBoolean(auto);
		buf.writeBoolean(isLinked);
	}

	public static class Handler implements IMessageHandler<TEControlPacket, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEControlPacket m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

				try {
					
					if(te instanceof TileEntityReactorControl) {
						TileEntityReactorControl control = (TileEntityReactorControl)te;

						control.hullHeat = m.hullHeat;
						control.coreHeat = m.coreHeat;
						control.fuel = m.fuel;
						control.water = m.water;
						control.cool = m.cool;
						control.steam = m.steam;
						control.maxWater = m.maxWater;
						control.maxCool = m.maxCool;
						control.maxSteam = m.maxSteam;
						control.compression = m.compression;
						control.rods = m.rods;
						control.maxRods = m.maxRods;
						control.isOn = m.isOn;
						control.auto = m.auto;
						control.isLinked = m.isLinked;
					}
					
				} catch (Exception x) {
				}
			});
			
			return null;
		}
	}
}
