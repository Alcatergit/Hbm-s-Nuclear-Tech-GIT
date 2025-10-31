package com.hbm.packet;

import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TEMissilePacket implements IMessage {

	int x;
	int y;
	int z;
	int type;

	public TEMissilePacket()
	{
		
	}

	public TEMissilePacket(int x, int y, int z, ItemStack stack)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.type = 0;
		if(stack != null) {
			if(stack.getItem() == ModItems.missile_generic)
				type = 1;
			if(stack.getItem() == ModItems.missile_strong)
				type = 2;
			if(stack.getItem() == ModItems.missile_cluster)
				type = 3;
			if(stack.getItem() == ModItems.missile_nuclear)
				type = 4;
			if(stack.getItem() == ModItems.missile_incendiary)
				type = 5;
			if(stack.getItem() == ModItems.missile_buster)
				type = 6;
			if(stack.getItem() == ModItems.missile_incendiary_strong)
				type = 7;
			if(stack.getItem() == ModItems.missile_cluster_strong)
				type = 8;
			if(stack.getItem() == ModItems.missile_buster_strong)
				type = 9;
			if(stack.getItem() == ModItems.missile_burst)
				type = 10;
			if(stack.getItem() == ModItems.missile_inferno)
				type = 11;
			if(stack.getItem() == ModItems.missile_rain)
				type = 12;
			if(stack.getItem() == ModItems.missile_drill)
				type = 13;
			if(stack.getItem() == ModItems.missile_endo)
				type = 14;
			if(stack.getItem() == ModItems.missile_exo)
				type = 15;
			if(stack.getItem() == ModItems.missile_nuclear_cluster)
				type = 16;
			if(stack.getItem() == ModItems.missile_doomsday)
				type = 17;
			if(stack.getItem() == ModItems.missile_taint)
				type = 18;
			if(stack.getItem() == ModItems.missile_micro)
				type = 19;
			if(stack.getItem() == ModItems.missile_carrier)
				type = 20;
			if(stack.getItem() == ModItems.missile_anti_ballistic)
				type = 21;
			if(stack.getItem() == ModItems.missile_bhole)
				type = 22;
			if(stack.getItem() == ModItems.missile_schrabidium)
				type = 23;
			if(stack.getItem() == ModItems.missile_emp)
				type = 24;
			if(stack.getItem() == ModItems.missile_emp_strong)
				type = 25;
			if(stack.getItem() == ModItems.missile_volcano)
				type = 26;
			if(stack.getItem() == ModItems.missile_n2)
				type = 27;
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
		type = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
		buf.writeInt(type);
		
	}
	
	public static class Handler implements IMessageHandler<TEMissilePacket, IMessage>{

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(TEMissilePacket message, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() -> {
				World world = Minecraft.getMinecraft().world;
				BlockPos pos = new BlockPos(message.x, message.y, message.z);
				if(world.isBlockLoaded(pos)){
					TileEntity te = world.getTileEntity(pos);
					if(te instanceof TileEntityLaunchPad){
						((TileEntityLaunchPad)te).state = message.type;
					}
				}
			});
			return null;
		}
	}
}
