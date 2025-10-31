package com.leafia.dev;

import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemWandD;
import com.hbm.packet.PacketDispatcher;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.Tuple.Triplet;
import com.leafia.dev.LeafiaDebug.Tracker.Action;
import com.leafia.dev.LeafiaDebug.Tracker.LeafiaTrackerPacket;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.leafia.passive.rendering.TopRender.Highlight;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import com.llib.technical.FifthString;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class LeafiaDebug {
	static boolean flagDebugger = false;
	public static void flagDebug() { flagDebugger = true; }
	public static void debugLog(World world,Object text) {
		if (text == null)
			text = "NULL";
		String prefix = (world.isRemote ? TextFormatting.LIGHT_PURPLE+"[REMOTE] " : TextFormatting.AQUA+"[SERVER] ");
		if (flagDebugger)
			prefix = TextFormatting.GOLD+"[VISUAL] ";
		flagDebugger = false;
		sendMessage(world,new TextComponentString(prefix + TextFormatting.RESET + text.toString()));
	}
	static void sendMessage(World world,ITextComponent component) {
		for (EntityPlayer plr : world.playerEntities) {
			if (plr.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d || plr.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.wand_d) {
				plr.sendMessage(component);
			}
		}
	}
	public static void debugPos(World world,BlockPos pos,float duration,int color,String... message) {
		LeafiaTrackerPacket packet = null;
		for (EntityPlayer plr : world.playerEntities) {
			if (plr.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d || plr.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.wand_d) {
				if (packet == null) {
					packet = new LeafiaTrackerPacket();
					packet.mode = Action.SHOW_BOX;
					packet.writer = (buf)->{
						buf.writeFloat(duration);
						buf.writeInt(color);
						buf.writeByte(message.length);
						for (String s : message)
							buf.writeFifthString(new FifthString(s));
						buf.writeVec3i(pos);
					};
				}
				LeafiaPacket._sendToClient(packet,plr);
			}
		}
	}
	public static class Tracker {
		public static final LeafiaSet<BlockPos> recorded = new LeafiaSet<>();
		public static boolean stepperEnabled = false; // change this one so it will only take effect next tick onward
		public static boolean stepperEnabledCurrent = false;
		public static boolean stepperBlockTracing = false;
		public static boolean trailerEnabled = false;
		public static boolean trailerEnabledCurrent = false;
		public static boolean trailerOpaqueTrails = true;
		public static List<VisualizerTrace> stepperTraces = new ArrayList<>();
		public static int stepperIndex = 0;
		public static int stepDelay = 64;
		public static int stepTick = 0;
		public static void resetStepper() {
			recorded.clear();
			stepperTraces.clear();
			stepTick = 0;
			stepperIndex = 0;
			stepperEnabledCurrent = false;
			stepperBlockTracing = false;
		}
		static boolean resetProfiles = false;
		public static void preTick(World world) {
			if (!trailerEnabledCurrent) {
				Tracker.visibleTraces.clear();
				resetProfiles = true;
			} else {
				for (VisualizerTrace trace : Tracker.visibleTraces) {
					trace.multiplier = trailerOpaqueTrails ? VisualizerTrace.default_multiplier : 0b11111111__01011111_01011111_01011111;
					for (int i = 0; i < trace.label.length; i++) {
						trace.label[i] = trace.label[i]
								.replace(TextFormatting.BLUE.toString(),TextFormatting.DARK_BLUE.toString())
								.replace(TextFormatting.GREEN.toString(),TextFormatting.DARK_GREEN.toString())
								.replace(TextFormatting.AQUA.toString(),TextFormatting.DARK_AQUA.toString())
								.replace(TextFormatting.RED.toString(),TextFormatting.DARK_RED.toString())
								.replace(TextFormatting.LIGHT_PURPLE.toString(),TextFormatting.DARK_PURPLE.toString())
								.replace(TextFormatting.YELLOW.toString(),TextFormatting.GOLD.toString())
								.replace(TextFormatting.GRAY.toString(),TextFormatting.DARK_GRAY.toString())
								.replace(TextFormatting.WHITE.toString(),TextFormatting.DARK_GRAY.toString());
					}
					if (getBlacklistedProfiles(new BlockPos(trace.pos)).contains(trace.profile))
						Tracker.visibleTraces.remove(trace); // LeafiaSet allows concurrent modifications, hooray!
				}
			}
			Tracker.stepperEnabledCurrent = Tracker.stepperEnabled;
			Tracker.trailerEnabledCurrent = Tracker.trailerEnabled;
			Tracker.linkMap.clear();
			Tracker.profileStacks.clear();
			if (!Tracker.stepperEnabled) {
				Tracker.recorded.clear();
				Tracker.stepperTraces.clear();
				Tracker.stepperIndex = 0;
				Tracker.stepTick = 0;
			} else if (Tracker.stepDelay > 0 && Tracker.stepperIndex < Tracker.stepperTraces.size()) {
				Tracker.stepTick++;
				if (Tracker.stepTick >= Tracker.stepDelay) {
					Tracker.stepTick = 0;
					Tracker.stepperIndex++;
				}
			}
			Tracker.stepperBlockTracing = Tracker.stepperEnabledCurrent && (Tracker.getSubjects().size() == Tracker.recorded.size());
		}
		static int sleepTimer = 0;
		public static void postTick(World world) {
			if (Tracker.stepperEnabledCurrent) {
				Tracker.visibleTraces.clear();
				for (int i = 0; i < (int)Math.min(Tracker.stepperTraces.size(),stepperIndex+1); i++) {
					VisualizerTrace trace = Tracker.stepperTraces.get(i);
					trace.opacity = 1d/Math.pow(2,Math.min(stepperIndex-i,15));
					Tracker.visibleTraces.add(trace);
				}
			} else {
				for (VisualizerTrace trace : Tracker.visibleTraces)
					trace.opacity = 1;
			}
			if (subjects.size() > 0 || subjectsRemote.size() > 0)
				sleepTimer = 20;
			if (sleepTimer > 0) {
				VisualizerPacket encoder = new VisualizerPacket();
				encoder.traces.addAll(Tracker.visibleTraces);
				LeafiaCustomPacket packet = LeafiaCustomPacket.__start(encoder);
				for (EntityPlayer player : world.playerEntities) // for some reason __sendToAll() didn't work
					packet.__sendToClient(player); // perhaps __sendToClient() actually waits for the player to receive or smth??
				sleepTimer--;
			}
			/*
			for (EntityPlayer player : world.playerEntities) {
				if (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d || player.getHeldItem(EnumHand.MAIN_HAND).getItem() == ModItems.wand_d) {
					if (packet == null) {
					}
				}
			}*/
		}
		public static int priorityMode = 0;
		public static LeafiaSet<VisualizerTrace> visibleTraces = new LeafiaSet<>();
		public static LeafiaMap<BlockPos,VisualizerTrace> linkMap = new LeafiaMap<>();

		static final LeafiaMap<BlockPos,String> subjects = new LeafiaMap<>();
		static final LeafiaMap<BlockPos,String> subjectsRemote = new LeafiaMap<>();
		public static BlockPos selected = null;
		public static LeafiaMap<BlockPos,String> getSubjects() {
			return ItemWandD.remote ? subjectsRemote : subjects;
		}
		public static LeafiaMap<BlockPos,List<String>> profileStacks = new LeafiaMap<>();
		public static LeafiaSet<String> profileFilter = new LeafiaSet<>();
		static LeafiaMap<BlockPos,LeafiaSet<String>> shownProfiles = new LeafiaMap<>();
		static List<String> getProfileStack(BlockPos pos) {
			if (!profileStacks.containsKey(pos))
				profileStacks.put(pos,new ArrayList<>());
			return profileStacks.get(pos);
		}
		public static LeafiaSet<String> getShownProfiles(BlockPos pos) {
			if (!shownProfiles.containsKey(pos))
				shownProfiles.put(pos,new LeafiaSet<>());
			return shownProfiles.get(pos);
		}
		public static LeafiaSet<String> getBlacklistedProfiles(BlockPos pos) {
			return profileFilter;
		}
		public static void _startProfile(TileEntity entity,String method) {
			LeafiaMap<BlockPos,String> mop = entity.getWorld().isRemote ? subjectsRemote : subjects;
			if (!mop.containsKey(entity.getPos())) return;
			resetProfiles();
			List<String> stacc = getProfileStack(entity.getPos());
			stacc.add(0,method);
			for (String s : profileFilter) {
				if (stacc.contains(s))
					return;
			}
			getShownProfiles(entity.getPos()).add(method);
		}
		public static void _endProfile(TileEntity entity) {
			LeafiaMap<BlockPos,String> mop = entity.getWorld().isRemote ? subjectsRemote : subjects;
			if (!mop.containsKey(entity.getPos())) return;
			List<String> stack = getProfileStack(entity.getPos());
			if (stack.size() > 0)
				stack.remove(0);
			else {
				flagDebug();
				debugLog(entity.getWorld(),"endProfile called, but there no exists corresponding stack! This should NOT happen!");
			}
		}
		public static VisualizerTrace _tracePosition(TileEntity entity,BlockPos pos,Object... details) {
			Triplet<String,String,Integer> triplet = traceCheck(entity,(sideColor,gray)->{return ""+pos.getX()+sideColor+","+gray+pos.getY()+sideColor+","+gray+pos.getZ();},details);
			if (triplet == null) return new VisualizerTrace(entity);
			String message = triplet.getA();
			String profile = triplet.getB();
			VisualizerTrace trace = new VisualizerTrace(new Vec3d(pos).add(0.5,0.5,0.5),message.split("\n"),triplet.getC(),profile);
			return addTrace(entity,trace);
		}
		public static VisualizerTrace _traceLine(TileEntity entity,Vec3d start,Vec3d end,Object... details) {
			Triplet<String,String,Integer> triplet = traceCheck(entity,(sideColor,gray)->{return ""+Math.round(start.distanceTo(end)*100)/100d+sideColor+"m";},details);
			if (triplet == null) return new VisualizerTrace(entity);
			String message = triplet.getA();
			String profile = triplet.getB();
			VisualizerTrace trace = new VisualizerTrace(start,message.split("\n"),triplet.getC(),profile);
			trace.tgtPos = end;
			return addTrace(entity,trace);
		}
		static void resetProfiles() {
			if (resetProfiles && !stepperBlockTracing) {
				resetProfiles = false;
				Tracker.shownProfiles.clear();
			}
		}
		@Nullable
		static Triplet<String,String,Integer> traceCheck(TileEntity entity,BiFunction<TextFormatting,TextFormatting,String> header,Object... details) {
			resetProfiles();
			LeafiaMap<BlockPos,String> mop = entity.getWorld().isRemote ? subjectsRemote : subjects;
			if (!mop.containsKey(entity.getPos())) return null;
			if (stepperBlockTracing) return null;
			List<String> stack = getProfileStack(entity.getPos());
			for (String s : profileFilter) {
				if (stack.contains(s))
					return null;
			}
			String profile = "";
			if (stack.size() > 0) {
				profile = stack.get(0);
				getShownProfiles(entity.getPos()).add(profile);
			}
			TextFormatting sideColor = entity.getWorld().isRemote ? TextFormatting.LIGHT_PURPLE : TextFormatting.AQUA;
			String message = sideColor+"["+TextFormatting.GRAY+header.apply(sideColor,TextFormatting.GRAY)+sideColor+"]\n"+mop.get(entity.getPos())+" ["+profile+"]";
			boolean first = true;
			for (Object detail : details) {
				if (first)
					message = message + "\n";
				first = false;
				message = message + "\n";
				String str = detail.toString();
				message = message + (str.equals("") ? "---" : str);
			}
			return new Triplet<>(message,profile,calculateColor(mop.get(entity.getPos())));
		}
		static VisualizerTrace addTrace(TileEntity entity,VisualizerTrace trace) {
			if (stepperEnabledCurrent) {
				recorded.add(entity.getPos());
				stepperTraces.add(trace);
			} else {
				if (linkMap.containsKey(entity.getPos())) {
					if (priorityMode == 1) return trace;
					else if (priorityMode == 2) {
						visibleTraces.remove(linkMap.get(entity.getPos()));
					}
				}
				visibleTraces.addAnyway(trace);
				linkMap.put(entity.getPos(),trace);
			}
			return trace;
		}
		public static class VisualizerTrace {
			final Vec3d pos;
			final String[] label;
			public int color;
			final String profile;
			static final int default_multiplier = 0b11111111__11111111_11111111_11111111;
			public int multiplier = default_multiplier;
			public double opacity = 1;
			final boolean replicated;
			Vec3d tgtPos = null;
			public VisualizerTrace(Vec3d pos,String[] label,int color,String profile) {
				this.pos = pos;
				this.label = label;
				this.color = color;
				this.profile = profile;
				replicated = false;
			}
			public VisualizerTrace(LeafiaBuf buf) {
				profile = buf.readFifthString().toString();
				label = new String[buf.readByte()];
				for (int i = 0; i < label.length; i++)
					label[i] = buf.readFifthString().toString();
				color = buf.readInt();
				multiplier = buf.readInt();
				pos = new Vec3d(buf.readDouble(),buf.readDouble(),buf.readDouble());
				replicated = true;
				if (buf.readBoolean())
					tgtPos = new Vec3d(buf.readDouble(),buf.readDouble(),buf.readDouble());
			}
			public VisualizerTrace(TileEntity entity) {
				pos = new Vec3d(entity.getPos()).add(0.5,0.5,0.5);
				label = null;
				profile = null;
				color = 0xFFFFFF;
				replicated = false;
			}
			void encode(LeafiaBuf buf) {
				buf.writeFifthString(new FifthString(profile));
				buf.writeByte((opacity >= 1) ? label.length : 0);
				if (opacity >= 1) {
					for (String s : label)
						buf.writeFifthString(new FifthString(s));
				}
				buf.writeInt(color|((int)(255*(1-opacity))<<24));
				buf.writeInt(multiplier);
				buf.writeDouble(pos.x); buf.writeDouble(pos.y); buf.writeDouble(pos.z);
				if (tgtPos == null) buf.writeBoolean(false);
				else {
					buf.writeBoolean(true);
					buf.writeDouble(tgtPos.x);
					buf.writeDouble(tgtPos.y);
					buf.writeDouble(tgtPos.z);
				}
			}
			@Override
			public boolean equals(Object obj) {
				if (obj instanceof VisualizerTrace && !stepperEnabledCurrent && !replicated) {
					return ((VisualizerTrace)obj).pos.equals(this.pos) && ((VisualizerTrace)obj).profile.equals(profile);
				}
				return super.equals(obj);
			}
		}
		public static class VisualizerPacket implements LeafiaCustomPacketEncoder {
			public List<VisualizerTrace> traces = new ArrayList<>();
			@Override
			public void encode(LeafiaBuf buf) {
				for (VisualizerTrace trace : traces)
					trace.encode(buf);
			}
			@Nullable
			@Override
			public Consumer<MessageContext> decode(LeafiaBuf buf) {
				boolean remote = false;
				List<VisualizerTrace> tracez = new ArrayList<>();
				while (buf.isReadable())
					tracez.add(new VisualizerTrace(buf));
				return (ctx)->{
					for (Highlight subHighlight : TrackerLocal.subHighlights)
						subHighlight.hide();
					TrackerLocal.subHighlights.clear();
					for (VisualizerTrace trace : tracez) {
						Highlight highlight = new Highlight(trace.pos);
						highlight.color = (trace.color&0xFF_000000)|LeafiaUtil.colorFromTextFormat(remote ? TextFormatting.LIGHT_PURPLE : TextFormatting.AQUA)&trace.multiplier;
						highlight.colorTop = trace.color&trace.multiplier;
						highlight.size = highlight.size.scale(0.2);
						highlight.textSize = 0.35;
						highlight.label = trace.label;
						TrackerLocal.subHighlights.add(highlight);
						highlight.show();
						if (trace.tgtPos != null) {
							highlight.ray = trace.tgtPos.subtract(trace.pos);
							Vec3d div = new Vec3d(Math.abs(highlight.ray.x)*4+1,Math.abs(highlight.ray.y)*4+1,Math.abs(highlight.ray.z)*4+1);
							highlight.size = new Vec3d(highlight.size.x/div.x,highlight.size.y/div.y,highlight.size.z/div.z);
							if (trace.opacity < 1) {
								highlight.ray = new Vec3d(0,0,0);
								continue; // hide most of it because it gets chaotic
							}
							Highlight ptr = new Highlight(trace.tgtPos);
							ptr.setColor(highlight.colorTop);
							ptr.size = new Vec3d(0.1/div.x,0.1/div.y,0.1/div.z);
							TrackerLocal.subHighlights.add(ptr);
							ptr.show();
						}
					}
				};
			}
		}
		static int calculateColor(String name) {
			int ref = Math.floorMod(name.hashCode(),6+6*4);
			int paletteBits = Math.floorDiv(ref,6);
			int colorBits = ref-paletteBits*6+1;

			int bit1 = 0x9F+(paletteBits>>1)*0x60;//-(paletteBits&1)*0x30;
			int bit0 = 2* 0x20*((paletteBits>>1)+1);

			int bitD = bit1-bit0;
			return (bit0+(colorBits>>2&1)*bitD)<<16|(bit0+(colorBits>>1&1)*bitD)<<8|(bit0+(colorBits&1)*bitD);
		}

		@SideOnly(Side.CLIENT)
		public static class TrackerLocal {
			static final LeafiaMap<BlockPos,Highlight> highlights = new LeafiaMap<>();
			static final LeafiaMap<BlockPos,Highlight> highlightsRemote = new LeafiaMap<>();
			static final List<Highlight> subHighlights = new ArrayList<>();
			static Highlight selection = new Highlight(BlockPos.ORIGIN);
			static void updateHighlights(LeafiaMap<BlockPos,String> internal,LeafiaMap<BlockPos,Highlight> visual,int sideColor) {
				for (Entry<BlockPos,Highlight> entry : visual.entrySet()) {
					if (!internal.containsKey(entry.getKey())) {
						visual.remove(entry.getKey());
						entry.getValue().hide();
					}
				}
				for (Entry<BlockPos,String> entry : internal.entrySet()) {
					Highlight highlight = visual.get(entry.getKey());
					if (!visual.containsKey(entry.getKey())) {
						highlight = new Highlight(entry.getKey());
						highlight.size = highlight.size.subtract(0.2,0.2,0.2);
						highlight.color = sideColor;
						visual.put(entry.getKey(),highlight);
						highlight.show();
					}
					highlight.label = new Object[]{entry.getValue()};
					highlight.colorTop = calculateColor(entry.getValue());
				}
			}
			public static void localTick(EntityPlayer player) {
				/*
				boolean show = player.getHeldItemOffhand().getItem() == ModItems.wand_d || player.getHeldItemMainhand().getItem() == ModItems.wand_d;
				for (Highlight subHighlight : TrackerLocal.subHighlights) {
					if (subHighlight.getVisibility()) {
						if (!show) subHighlight.hide();
					} else if (show)
						subHighlight.show();
				}*/ // i changed my mind
			}
		}
		public static void notifySubjectMapChanges(boolean remote) {
			LeafiaTrackerPacket packet = new LeafiaTrackerPacket();
			packet.mode = remote ? Action.SUBJECTS_REMOTE : Action.SUBJECTS;
			packet.writer = (buf)->{
				for (Entry<BlockPos,String> entry : subjects.entrySet()) {
					buf.writeInt(entry.getKey().getX());
					buf.writeInt(entry.getKey().getY());
					buf.writeInt(entry.getKey().getZ());
					buf.writeUTF8String(entry.getValue());
				}
			};
			PacketDispatcher.wrapper.sendToAll(packet);
		}
		public static void notifySelectionChange() {
			LeafiaTrackerPacket packet = new LeafiaTrackerPacket();
			packet.mode = Action.SELECTION;
			packet.writer = (buf)->{
				if (selected != null) {
					buf.writeInt(selected.getX());
					buf.writeInt(selected.getY());
					buf.writeInt(selected.getZ());
				}
			};
			PacketDispatcher.wrapper.sendToAll(packet);
		}
		public static void changeSide(boolean remote) {
			ItemWandD.remote = remote;
			LeafiaTrackerPacket packet = new LeafiaTrackerPacket();
			packet.mode = Action.SELECTION;
			packet.writer = (buf)->{
				buf.writeBoolean(remote);
			};
			PacketDispatcher.wrapper.sendToAll(packet);
		}
		enum Action {
			NONE,
			SELECTION((buf)->{
				if (buf.isReadable()) {
					BlockPos pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
					return ()->{
						Tracker.selected = pos;
						TrackerLocal.selection.setBlock(pos);
						TrackerLocal.selection.show();
					};
				} else {
					return ()->{
						Tracker.selected = null;
						TrackerLocal.selection.hide();
					};
				}
			}),
			SUBJECTS((buf)->{
				List<Pair<BlockPos,String>> queue = new ArrayList<>();
				while (buf.isReadable())
					queue.add(new Pair<>(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readUTF8String()));
				return ()->{
					subjects.clear();
					for (Pair<BlockPos,String> pair : queue)
						subjects.put(pair.getA(),pair.getB());
					TrackerLocal.updateHighlights(subjects,TrackerLocal.highlights,LeafiaUtil.colorFromTextFormat(TextFormatting.AQUA));
				};
			}),
			SUBJECTS_REMOTE((buf)->{
				List<Pair<BlockPos,String>> queue = new ArrayList<>();
				while (buf.isReadable())
					queue.add(new Pair<>(new BlockPos(buf.readInt(),buf.readInt(),buf.readInt()),buf.readUTF8String()));
				return ()->{
					subjectsRemote.clear();
					for (Pair<BlockPos,String> pair : queue)
						subjectsRemote.put(pair.getA(),pair.getB());
					TrackerLocal.updateHighlights(subjectsRemote,TrackerLocal.highlightsRemote,LeafiaUtil.colorFromTextFormat(TextFormatting.LIGHT_PURPLE));
				};
			}),
			CHANGE_SIDE((buf)->{
				boolean remote = buf.readBoolean();
				return ()->{
					ItemWandD.remote = remote;
				};
			}),
			SHOW_BOX((buf)->{
				float duration = buf.readFloat();
				int color = buf.readInt();
				String[] label = new String[buf.readByte()];
				for (int i = 0; i < label.length; i++)
					label[i] = buf.readFifthString().toString();
				BlockPos pos = buf.readPos();
				return ()->{
					Highlight highlight = new Highlight(pos);
					highlight.setColor(color);
					highlight.label = label;
					highlight.lifetime = duration;
					highlight.show();
				};
			});
			;
			final Function<LeafiaBuf,Runnable> builder;
			Action() { builder = null; }
			Action(Function<LeafiaBuf,Runnable> builder) { this.builder = builder; }
		}
		public static class LeafiaTrackerPacket extends RecordablePacket {
			Action mode = Action.NONE;
			public Runnable callback;
			public Consumer<LeafiaBuf> writer;
			@Override
			public void fromBits(LeafiaBuf buf) {
				mode = Action.values()[buf.readInt()];
				if (mode.builder != null)
					callback = mode.builder.apply(buf);
			}
			@Override
			public void toBits(LeafiaBuf buf) {
				buf.writeInt(mode.ordinal());
				writer.accept(buf);
			}
			public static class Handler implements IMessageHandler<LeafiaTrackerPacket,IMessage> {
				@Override
				@SideOnly(Side.CLIENT)
				public IMessage onMessage(LeafiaTrackerPacket message,MessageContext ctx) {
					if (message.callback != null)
						Minecraft.getMinecraft().addScheduledTask(message.callback);
					return null;
				}
			}
		}
	}
}
