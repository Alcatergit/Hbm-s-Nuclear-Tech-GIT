package com.leafia.dev.custompacket;

import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExplosionNTSyncPacket;
import com.hbm.forgefluid.FFUtils.FuzzyIdentifierPacket;
import com.hbm.items.tool.ItemWandS.WandStructurePacket;
import com.hbm.packet.ItemFolderPacket.FolderResponsePacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityCore.DFCShockPacket;
import com.leafia.contents.gear.wands.ItemWandSaving.HighlightSavingWandProduct;
import com.leafia.contents.gear.wands.ItemWandSaving.HighlightSavingWandRemove;
import com.leafia.contents.gear.wands.ItemWandSaving.HighlightSavingWandSave;
import com.leafia.contents.machines.elevators.car.ElevatorEntity.*;
import com.leafia.dev.LeafiaDebug.Tracker.VisualizerPacket;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.llib.exceptions.LeafiaDevFlaw;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

import java.util.function.Consumer;

public class LeafiaCustomPacket extends RecordablePacket {
	public enum CustomPacketType { // add your packets here, that's literally all registering you have to do!
		NONE,
		VISUALIZER_TRACE(new VisualizerPacket()),
		HIGHLIGHT_SAVING_SAVE(new HighlightSavingWandSave()),
		HIGHLIGHT_SAVING_REMOVE(new HighlightSavingWandRemove()),
		HIGHLIGHT_SAVING_PRODUCT(new HighlightSavingWandProduct()),
		WAND_STRUCTURE(new WandStructurePacket()),
		FUZZY_IDENTIFIER(new FuzzyIdentifierPacket()),
		DFC_SHOCK(new DFCShockPacket()),
		ELEVATOR_BUTTON_SYNC(new EvButtonSyncPacket()),
		ELEVATOR_BUTTON_ENABLED_SYNC(new EvButtonEnablePacket()),
		ELEVATOR_BUTTON_CLICK_SYNC(new EvButtonClickPacket()),
		ELEVATOR_BUTTON_CLICKED(new EvButtonInteractPacket()),
		ELEVATOR_BUTTON_MODIFY(new EvButtonModifyPacket()),
		ELEVATOR_SPECIAL_SYNC(new EvSpecialFloorsSyncPacket()),
		ELEVATOR_SYNC_REQUEST(new EvSyncRequestPacket()),
		ELEVATOR_INVENTORY_SYNC(new EvInventorySyncPacket()),
		CLOSE_GUI(new FolderResponsePacket()),
		EXPLOSION_NT(new ExplosionNTSyncPacket())
		;
		final LeafiaCustomPacketEncoder encoder;
		CustomPacketType() { encoder = null; }
		CustomPacketType(LeafiaCustomPacketEncoder encoder) { this.encoder = encoder; }
	}
	CustomPacketType packetType = CustomPacketType.NONE;
	LeafiaCustomPacketEncoder encoder = null;
	Consumer<MessageContext> callback = null;
	public static LeafiaCustomPacket __start(LeafiaCustomPacketEncoder encoder) {
		for (CustomPacketType type : CustomPacketType.values()) {
			if (type.encoder != null && type.encoder.getClass().isInstance(encoder)) {
				LeafiaCustomPacket packet = new LeafiaCustomPacket();
				packet.encoder = encoder;
				packet.packetType = type;
				return packet;
			}
		}
		throw new LeafiaDevFlaw("Encoder "+encoder.getClass().getSimpleName()+" isn't registered to enum CustomPacketType");
	}
	public void __sendToAll() {
		PacketDispatcher.wrapper.sendToAll(this);
	}
	public void __sendToAllAround(int dim,BlockPos pos,double range) {
		PacketDispatcher.wrapper.sendToAllAround(this,new TargetPoint(dim,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,range));
	}
	public void __sendToAllAround(int dim,Vec3d pos,double range) {
		PacketDispatcher.wrapper.sendToAllAround(this,new TargetPoint(dim,pos.x,pos.y,pos.z,range));
	}
	@Deprecated
	public void __sendToAllInDimension(int dimension) {
		PacketDispatcher.wrapper.sendToDimension(this,dimension);
	}
	public void __sendToServer() {
		PacketDispatcher.wrapper.sendToServer(this);
	}
	public void __sendToClient(EntityPlayer player) {
		_sendToClient(this,player);
	}
	public static void _sendToClient(IMessage message,EntityPlayer player) {
		if (player instanceof EntityPlayerMP)
			PacketDispatcher.wrapper.sendTo(message,(EntityPlayerMP)player);
		else
			PacketDispatcher.wrapper.sendToAll(message);
	}
	@Override
	public void fromBits(LeafiaBuf buf) {
		short protocol = buf.readShort();
		if ((protocol&0xFF) != CustomPacketType.values().length) {
			packetType = CustomPacketType.NONE;
			callback = (ctx)->{
				ITextComponent reason = new TextComponentString("########").setStyle(new Style().setColor(TextFormatting.GOLD))
						.appendSibling(new TextComponentString(" NTM:LCE FATAL ERROR ").setStyle(new Style().setColor(TextFormatting.WHITE)))
						.appendSibling(new TextComponentString("########\nInvalid protocol on LeafiaCustomPacket").setStyle(new Style().setColor(TextFormatting.GOLD)))
						.appendSibling(
								new TextComponentString("\nThe server supports "+(protocol&0xFF)+" custom packet variations. Your client supports "+CustomPacketType.values().length).setStyle(new Style().setColor(TextFormatting.WHITE))
						)
						.appendSibling(new TextComponentString("\n\nPossible reasons are:").setStyle(new Style().setColor(TextFormatting.AQUA)))
						.appendSibling(
								new TextComponentString("\n- Your client is outdated. Check for any updates on github (I don't add version numbers!)\n- Or the server is outdated. Contact server owner\n- Else perhaps it's some unpredictable fucks going on idfk").setStyle(new Style().setColor(TextFormatting.GRAY))
						);
				if (ctx.side.isClient())
					Minecraft.getMinecraft().player.connection.getNetworkManager().closeChannel(reason);
				else if (ctx.side.isServer())
					ctx.getServerHandler().disconnect(reason);
			};
			return;
		}
		packetType = CustomPacketType.values()[protocol>>8];
		if (packetType.encoder != null)
			callback = packetType.encoder.decode(buf);
	}
	@Override
	public void toBits(LeafiaBuf buf) {
		buf.writeShort(packetType.ordinal()<<8|CustomPacketType.values().length);
		if (encoder != null)
			encoder.encode(buf);
	}
	public static class Handler implements IMessageHandler<LeafiaCustomPacket,IMessage> {
		@Override
		public IMessage onMessage(LeafiaCustomPacket message,MessageContext ctx) {
			if (message.callback != null)
				message.callback.accept(ctx);
			return null;
		}
	}
}