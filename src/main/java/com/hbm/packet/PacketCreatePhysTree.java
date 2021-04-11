package com.hbm.packet;

import java.util.HashSet;
import java.util.Set;

import com.hbm.main.ModEventHandlerClient;
import com.hbm.physics.ParticlePhysicsBlocks;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class PacketCreatePhysTree implements IMessage {

	public BlockPos pos;
	
	public PacketCreatePhysTree() {
	}
	
	public PacketCreatePhysTree(BlockPos pos) {
		this.pos = pos;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.pos = BlockPos.fromLong(buf.readLong());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(pos.toLong());
	}
	
	public static int recurseFloodFill(BlockPos current, int count, Set<BlockPos> blocks){
		if(count > 100)
			return -1;
		if(Minecraft.getMinecraft().world.getBlockState(current).getBlock() == Blocks.AIR){
			return count;
		}
		blocks.add(current);
		count ++;
		for(EnumFacing e : EnumFacing.VALUES){
			BlockPos newPos = current.offset(e);
			if(!blocks.contains(newPos)){
				count = recurseFloodFill(newPos, count, blocks);
				if(count == -1)
					return -1;
			}
		}
		return count;
	}
	
	public static class Handler implements IMessageHandler<PacketCreatePhysTree, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(PacketCreatePhysTree m, MessageContext ctx) {
			Minecraft.getMinecraft().addScheduledTask(() ->{
				Set<BlockPos> blocks = new HashSet<>();
				int recurse = recurseFloodFill(m.pos, 0, blocks);
				if(recurse > 0 && blocks.size() > 0){
					//Minecraft.getMinecraft().effectRenderer.addEffect(new ParticlePhysicsBlocks(Minecraft.getMinecraft().world, m.pos.getX(), m.pos.getY(), m.pos.getZ(), m.pos, blocks.toArray(new BlockPos[0])));
					ModEventHandlerClient.firstPersonAuxParticles.add(new ParticlePhysicsBlocks(Minecraft.getMinecraft().world, m.pos.getX(), m.pos.getY(), m.pos.getZ(), m.pos, blocks.toArray(new BlockPos[0])));
				}
			});
			return null;
		}
		
	}

}
