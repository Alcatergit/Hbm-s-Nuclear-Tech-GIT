package com.leafia;

import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.items.ModItems;
import com.hbm.items.tool.ItemWandD;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.leafia.passive.effects.LeafiaShakecam;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.group.LeafiaSet;
import com.llib.technical.LeafiaEase;
import net.minecraft.client.Minecraft;
import net.minecraft.command.*;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.nbt.NBTException;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.event.HoverEvent;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CommandLeaf extends CommandBase {
	@Override
	public String getName() {
		return "hbmleaf";
	}
	@Override
	public String getUsage(ICommandSender sender) {
		return "myaaaaa";
	}
	@Override
	public int getRequiredPermissionLevel() {
		//Level 2 ops can do commands like setblock, gamemode, and give. They can't kick/ban or stop the server.
		return 2;
	}
	String[] shiftArgs(String[] args,int n) {
		if (n > args.length) return new String[0];
		String[] argsOut = new String[args.length-n];
		for (int i = 0; i < args.length-n; i++)
			argsOut[i] = args[i+n];
		return argsOut;
	}
	boolean darkRow = false;
	ITextComponent genSuggestion(String c) {
		TextComponentString compo = new TextComponentString("  "+c);
		Style style = new Style()
				.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Click to try out")))
				.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND,c))
				.setColor(darkRow ? TextFormatting.DARK_GRAY : TextFormatting.GRAY);
		darkRow = !darkRow;
		return compo.setStyle(style);
	}
	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if (args.length <= 0)
			return Collections.emptyList();
		else {
			String lastArg = args[args.length-1];
			List<String> list = new ArrayList<String>();
			boolean nosort = false;
			if (args.length-1 >= 1) { // we're currently at arg 2 (1 in index)
				switch (args[0]) { // so we look into what the player put for arg 1 (0 in index)
					case "shake":
						args = shiftArgs(args,1);
						if (args.length-1 <= 0) {
							list.add("?");
							list.addAll(Arrays.asList(server.getOnlinePlayerNames()));
						} else {
							nosort = true;
							args = shiftArgs(args,1);
							boolean showCoords = false;
							boolean showParams = true;
							if (args.length-1 > 0) {
								if ((!args[0].matches(".*\\D.*") || args[0].startsWith("~")) && args.length < 3) {
									showCoords = true;
									showParams = false;
								}
							} else showCoords = true;
							if (showCoords) {
								switch (args.length-1) {
									case 0: list.add(String.valueOf(sender.getPosition().getX())); break;
									case 1: list.add(String.valueOf(sender.getPosition().getY())); break;
									case 2: list.add(String.valueOf(sender.getPosition().getZ())); break;
								}
							}
							if (showParams) {
								list.add("type=simple");
								list.add("type=smooth");
								for (LeafiaShakecam.Preset preset : LeafiaShakecam.Preset.values()) {
									list.add("preset="+preset.name());
								}
								for (String s : new String[]{"range","intensity","curve","speed","duration","blurDulling","blurExponent","bloomDulling","bloomExponent"}) {
									list.add(s+"=");
									list.add(s+"+");
									list.add(s+"-");
									list.add(s+"*");
									list.add(s+"/");
								}
								for (String s : LeafiaEase.listEasesForCommands()) {
									list.add("ease="+s);
								}
								list.add("ease=none");
							}
						}
						break;
					case "torex":
						args = shiftArgs(args,1);
						if (args.length-1 <= 0) {
							list.add("statFac");
							list.add("statFacBale");
							list.add("summon");
						} else {
							switch(args[0]) {
								case "statFac":
								case "statFacBale":
									switch (args.length-1) {
										case 1: list.add(String.valueOf(sender.getPosition().getX())); break;
										case 2: list.add(String.valueOf(sender.getPosition().getY())); break;
										case 3: list.add(String.valueOf(sender.getPosition().getZ())); break;
										case 4: list.add("1"); break;
										case 5:
											list.add("true");
											list.add("false");
											break;
									}
									break;
								case "summon":
									if (args.length <= 4) list.add("~");
									break;
							}
						}
						break;
					case "visualizer":
						args = shiftArgs(args,1);
						if (args.length-1 <= 0) {
							list.add("select");
							list.add("remove");
							list.add("rename");
							list.add("side");
							list.add("teleport");
							list.add("display");
							list.add("exclude");
						} else {
							switch (args[0]) {
								case "select": case "remove": case "rename": case "teleport":
									args = shiftArgs(args,1);
									if (args.length-1 <= 0)
										list.addAll(Tracker.getSubjects().values());
									break;
								case "side":
									if (args.length-1 <= 1) {
										list.add("server");
										list.add("remote");
									}
									break;
								case "display":
									if (args.length-1 <= 1) {
										list.add("all");
										list.add("first");
										list.add("last");
										list.add("step");
										list.add("trailer");
										nosort = true;
									}
									break;
								case "exclude":
									args = shiftArgs(args,1);
									if (args.length-1 <= 0) {
										list.add("add");
										list.add("remove");
										list.add("clear");
										nosort = true;
									} else if (args[0].equals("add")) {
										list.addAll(Tracker.getShownProfiles(Tracker.selected));
									} else if (args[0].equals("remove")) {
										list.addAll(Tracker.getBlacklistedProfiles(Tracker.selected));
									}
									break;
							}
						}
						break;
					case "wand":
						args = shiftArgs(args,1);
						if (args.length-1 <= 0) {
							list.add("save");
							list.add("remove");
						}
						break;
				}
			} else {
				list.add("eases");
				list.add("shake");
				list.add("torex");
				list.add("wand");
				list.add("AAvisualizer");
			}
			if(list.size() > 1 && !nosort)
				list.sort((a,b) -> {
					if(a == null || b == null) {
						return -1;
					}
					return a.compareTo(b);
				});
			list.replaceAll((s)->{ // sorting bypass for better usability on visualizer utility
				if (s.equals("AAvisualizer"))
					return "visualizer";
				return s;
			});
			return list.stream().filter(s -> s.startsWith(lastArg)).collect(Collectors.toList());
		}
	}
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length <= 0) {
			throw new CommandException(getUsage(sender));
		} else {
			Style header = new Style().setColor(TextFormatting.LIGHT_PURPLE);
			darkRow = false;
			switch(args[0]) {
				case "wand": {
					args = shiftArgs(args,1);
					if (args.length < 1)
						throw new WrongUsageException("/hbmleaf wand save|remove", new Object[0]);
					switch(args[0]) {
						case "save":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf wand save <name>", new Object[0]);
							int status = ModItems.wand_leaf.trySave(getCommandSenderAsPlayer(sender),args[0]);
							switch(status) {
								case 0:
									notifyCommandListener(sender, this, "Saving structure! This might lag af", new Object[0]);
									break;
								case 1:
									throw new CommandException("You must have Saving Wand in either mainhand or offhand to use this command",new Object[0]);
								case 2:
									throw new CommandException("You must have area selected with your Saving Wand",new Object[0]);
								case 3:
									throw new CommandException("Server error",new Object[0]);
								default:
									throw new CommandException("Unknown error!! myaw",new Object[0]);
							}
							break;
						case "remove":
							if (ModItems.wand_leaf.tryRemove(getCommandSenderAsPlayer(sender)))
								notifyCommandListener(sender, this, "Successfully removed selection area", new Object[0]);
							else
								throw new CommandException("You must have Saving Wand in either mainhand or offhand to use this command",new Object[0]);
							break;
						default:
							throw new WrongUsageException("/hbmleaf wand save|remove", new Object[0]);
					}
				} break;
				case "visualizer": {
					args = shiftArgs(args,1);
					if (args.length < 1)
						throw new WrongUsageException("/hbmleaf visualizer select|remove|rename|side|teleport|display|exclude [...]", new Object[0]);
					BlockPos selectPos = Tracker.selected;
					switch(args[0]) {
						case "select":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf visualizer select <watchLabel>", new Object[0]);
							if (!Tracker.getSubjects().containsValue(args[0]))
								throw new CommandException("Watch "+args[0]+" does not exist", new Object[0]);
							Tracker.selected = Tracker.getSubjects().getKeyFor(args[0]);
							Tracker.notifySelectionChange();
							notifyCommandListener(sender, this, "Moved selection to "+args[0], new Object[0]);
							break;
						case "rename":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf visualizer rename <newName>", new Object[0]);
							else if (args.length < 2) {
								if (!Tracker.getSubjects().containsKey(Tracker.selected))
									throw new CommandException("Select watch by shift+right clicking a block with Debug Wand first!",new Object[0]);
							} else {
								if (!Tracker.getSubjects().containsValue(args[0]))
									throw new CommandException("Watch "+args[0]+" does not exist", new Object[0]);
								selectPos = Tracker.getSubjects().getKeyFor(args[0]);
								args = shiftArgs(args,1);
							}
							Tracker.getSubjects().put(selectPos,args[0]);
							notifyCommandListener(sender, this, "Successfully renamed watch", new Object[0]);
							Tracker.notifySubjectMapChanges(ItemWandD.remote);
							break;
						case "remove":
							args = shiftArgs(args,1);
							if (args.length < 1) {
								if (!Tracker.getSubjects().containsKey(Tracker.selected))
									throw new CommandException("Select watch by shift+right clicking a block with Debug Wand first!",new Object[0]);
							} else {
								if (!Tracker.getSubjects().containsValue(args[0]))
									throw new CommandException("Watch "+args[0]+" does not exist", new Object[0]);
								selectPos = Tracker.getSubjects().getKeyFor(args[0]);
							}
							Tracker.getSubjects().remove(selectPos);
							if (selectPos.equals(Tracker.selected)) {
								Tracker.selected = null;
								Tracker.notifySelectionChange();
							}
							notifyCommandListener(sender, this, "Successfully removed watch", new Object[0]);
							Tracker.notifySubjectMapChanges(ItemWandD.remote);
							break;
						case "teleport":
							args = shiftArgs(args,1);
							if (args.length < 1) {
								if (!Tracker.getSubjects().containsKey(Tracker.selected))
									throw new WrongUsageException("/hbmleaf visualizer teleport [watchLabel]", new Object[0]);
								sender.getCommandSenderEntity().setPositionAndUpdate(Tracker.selected.getX()+0.5,Tracker.selected.getY()+0.5,Tracker.selected.getZ()+0.5);
							} else {
								if (!Tracker.getSubjects().containsValue(args[0]))
									throw new CommandException("Watch "+args[0]+" does not exist", new Object[0]);
								BlockPos pos = Tracker.getSubjects().getKeyFor(args[0]);
								sender.getCommandSenderEntity().setPositionAndUpdate(pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5);
							}
							break;
						case "side":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf visualizer side remote|server", new Object[0]);
							LeafiaDebug.flagDebug();
							boolean remote = false;
							if (args[0].equals("remote"))
								remote = true;
							else if (!args[0].equals("server"))
								throw new WrongUsageException("/hbmleaf visualizer side remote|server", new Object[0]);
							LeafiaDebug.debugLog(server.getEntityWorld(),"New watches will be created on "+(remote ? "remote" : "server"));
							ItemWandD.remote = remote;
							break;
						case "display":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf visualizer display all|first|last|step|trailer", new Object[0]);
							Tracker.stepperEnabled = false;
							Tracker.trailerEnabled = false;
							Tracker.priorityMode = 0;
							switch (args[0]) {
								case "all":
									notifyCommandListener(sender, this, "Updated visualizer display mode", new Object[0]);
									break;
								case "first":
									Tracker.priorityMode = 1;
									notifyCommandListener(sender, this, "Updated visualizer display mode", new Object[0]);
									break;
								case "last":
									Tracker.priorityMode = 2;
									notifyCommandListener(sender, this, "Updated visualizer display mode", new Object[0]);
									break;
								case "step":
									Tracker.stepperEnabled = true;
									sender.sendMessage(new TextComponentString("=== STEPPER MODE ===").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(new TextComponentString("[CHANGE SPEED]").setStyle(new Style().setColor(TextFormatting.GRAY).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Change speed in ticks, or pause it. (Default 65, set 0 to pause)"))).setClickEvent(new ClickEvent(Action.SUGGEST_COMMAND,"/hbmleaf visualizer stepper delay 65"))))
									);
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(new TextComponentString("[<<]")
													.setStyle(new Style().setColor(TextFormatting.DARK_GRAY).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Previous step")))
															.setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer stepper previous"))
													)
											).appendSibling(new TextComponentString("  "))
											.appendSibling(new TextComponentString("[>>]")
													.setStyle(new Style().setColor(TextFormatting.DARK_GRAY).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Next step")))
															.setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer stepper next"))
													)
											)
									);
									sender.sendMessage(new TextComponentString(""));
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(new TextComponentString("[RESTART]").setStyle(new Style().setColor(TextFormatting.DARK_RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Record another tick"))).setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer stepper reset"))))
									);
									break;
								case "trailer":
									Tracker.trailerEnabled = true;
									sender.sendMessage(new TextComponentString("=== TRAILER MODE ===").setStyle(new Style().setColor(TextFormatting.GOLD)));
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(new TextComponentString("[TOGGLE VISIBILITY]").setStyle(new Style().setColor(TextFormatting.GRAY).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Toggle visibility of trailing visualizers"))).setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer trailer togglevis"))))
									);
									sender.sendMessage(new TextComponentString(""));
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(new TextComponentString("[CLEAR]").setStyle(new Style().setColor(TextFormatting.DARK_RED).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Record another tick"))).setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer trailer clear"))))
									);
									break;
								default:
									throw new WrongUsageException("/hbmleaf visualizer display all|first|last|step", new Object[0]);
							}
							break;
						case "stepper":
							args = shiftArgs(args,1);
							if (args.length < 1)
								return;
							switch(args[0]) {
								case "previous":
									Tracker.stepperIndex = Math.max(Tracker.stepperIndex-1,0);
									Tracker.stepDelay = 0;
									break;
								case "next":
									Tracker.stepperIndex = Math.min(Tracker.stepperIndex+1,Tracker.stepperTraces.size());
									Tracker.stepDelay = 0;
									break;
								case "delay":
									args = shiftArgs(args,1);
									if (args.length < 1)
										return;
									Tracker.stepDelay = Integer.parseInt(args[0]);
									notifyCommandListener(sender, this, "Visualizer will now advance step every "+Tracker.stepDelay+" ticks", new Object[0]);
									break;
								case "reset":
									Tracker.resetStepper();
									Tracker.stepDelay = 65;
									break;
							}
							break;
						case "trailer":
							args = shiftArgs(args,1);
							if (args.length < 1)
								return;
							switch(args[0]) {
								case "clear":
									Tracker.trailerEnabledCurrent = false;
									break;
								case "togglevis":
									Tracker.trailerOpaqueTrails = !Tracker.trailerOpaqueTrails;
									break;
							}
							break;
						case "exclude":
							args = shiftArgs(args,1);
							if (args.length < 1)
								throw new WrongUsageException("/hbmleaf visualizer exclude add|remove|clear", new Object[0]);
							if (!Tracker.getSubjects().containsKey(Tracker.selected))
								throw new CommandException("Select watch by shift+right clicking a block with Debug Wand first!", new Object[0]);
							LeafiaSet<String> profiles = null;
							switch(args[0]) {
								case "add":
									args = shiftArgs(args,1);
									if (args.length < 1) {
										profiles = Tracker.getShownProfiles(Tracker.selected);
										sender.sendMessage(new TextComponentString("Specify profiles to exclude from visualizer").setStyle(new Style().setColor(TextFormatting.GOLD)));
										sender.sendMessage(new TextComponentString("Currently visible:").setStyle(new Style().setColor(TextFormatting.GRAY)));
									} else
										Tracker.getBlacklistedProfiles(Tracker.selected).add(args[0]);
									break;
								case "remove": {
									args = shiftArgs(args,1);
									profiles = Tracker.getBlacklistedProfiles(Tracker.selected);
									if (args.length < 1) {
										sender.sendMessage(new TextComponentString("Specify hidden profiles to remove from blacklist").setStyle(new Style().setColor(TextFormatting.GOLD)));
										sender.sendMessage(new TextComponentString("Currently hidden:").setStyle(new Style().setColor(TextFormatting.GRAY)));
									} else {
										if (!profiles.contains(args[0]))
											throw new CommandException("Profile " + args[0] + " does not exist in blacklist",new Object[0]);
										profiles.remove(args[0]);
									}
									//notifyCommandListener(sender, this, "Removed "+args[0]+" from the blacklist", new Object[0]);
								} break;
								case "toggle": {
									args = shiftArgs(args,1);
									if (args.length < 1)
										return;
									profiles = Tracker.getBlacklistedProfiles(Tracker.selected);
									if (!profiles.contains(args[0]))
										profiles.add(args[0]);
									else
										profiles.remove(args[0]);
									profiles = null;
								} break;
								case "clear":
									Tracker.getBlacklistedProfiles(Tracker.selected).clear();
									notifyCommandListener(sender, this, "Blacklist cleared", new Object[0]);
									break;
							}
							if (profiles != null) {
								for (String profile : profiles) {
									sender.sendMessage(new TextComponentString("  ")
											.appendSibling(
													new TextComponentString(profile).setStyle(
															new Style().setColor(TextFormatting.DARK_GRAY)
																	.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,new TextComponentString("Click to toggle")))
																	.setClickEvent(new ClickEvent(Action.RUN_COMMAND,"/hbmleaf visualizer exclude toggle "+profile))
													)
											)
									);
								}
							}
							break;
						default:
							throw new WrongUsageException("/hbmleaf visualizer remove|rename|side|teleport|display [...]", new Object[0]);
					}
				} break;
				case "eases": {
					sender.sendMessage(new TextComponentString("Available eases:").setStyle(header));
					for (String s : LeafiaEase.listEasesForCommands()) {
						sender.sendMessage(new TextComponentString("  "+s));
					}
				} break;
				case "torex": {
					String usage = "/hbmleaf torex statFac|statFacBale <x> <y> <z> <scale> [sound]\n OR /hbmleaf torex summon [x] [y] [z] [dataTag]";
					args = shiftArgs(args,1);
					if (args.length < 1)
						throw new WrongUsageException(usage, new Object[0]);
					if (args[0].startsWith("statFac")) {
						if (args.length < 5)
							throw new WrongUsageException(usage, new Object[0]);
						BlockPos pos = parseBlockPos(sender,args,1,false);
						switch(args[0]+args.length) {
							case "statFac5":
								EntityNukeTorex.statFac(sender.getEntityWorld(),pos.getX(),pos.getY(),pos.getZ(),(float)parseDouble(args[4])); break;
							case "statFacBale5":
								EntityNukeTorex.statFacBale(sender.getEntityWorld(),pos.getX(),pos.getY(),pos.getZ(),(float)parseDouble(args[4])); break;
							case "statFac6":
								EntityNukeTorex.statFac(sender.getEntityWorld(),pos.getX(),pos.getY(),pos.getZ(),(float)parseDouble(args[4]),parseBoolean(args[5])); break;
							case "statFacBale6":
								EntityNukeTorex.statFacBale(sender.getEntityWorld(),pos.getX(),pos.getY(),pos.getZ(),(float)parseDouble(args[4]),parseBoolean(args[5])); break;
							default:
								throw new WrongUsageException(usage, new Object[0]);
						}
						notifyCommandListener(sender, this, "commands.summon.success", new Object[0]);
					} else if (args[0].equals("summon")) {
						//BlockPos blockpos = sender.getPosition();
						Vec3d vec3d = sender.getPositionVector();
						double d0 = vec3d.x;
						double d1 = vec3d.y;
						double d2 = vec3d.z;
						if (args.length >= 4)
						{
							d0 = parseDouble(d0, args[1], true);
							d1 = parseDouble(d1, args[2], false);
							d2 = parseDouble(d2, args[3], true);
							//blockpos = new BlockPos(d0, d1, d2);
						}
						NBTTagCompound nbttagcompound = new NBTTagCompound();
						boolean flag = false;
						if (args.length >= 5)
						{
							String s1 = buildString(args, 4);
							try
							{
								nbttagcompound = JsonToNBT.getTagFromJson(s1);
								flag = true;
							}
							catch (NBTException nbtexception)
							{
								throw new CommandException("commands.summon.tagError", new Object[] {nbtexception.getMessage()});
							}
						}
						EntityNukeTorex torex = new EntityNukeTorex(sender.getEntityWorld());
						torex.setPosition(d0,d1,d2);
						if (flag)
							torex.readFromNBT(nbttagcompound);
						EntityNukeTorex.spawnTorex(sender.getEntityWorld(),torex);
						notifyCommandListener(sender, this, "commands.summon.success", new Object[0]);
					} else
						throw new WrongUsageException(usage, new Object[0]);
				} break;
				case "shake": {
					args = shiftArgs(args,1);
					if (args.length < 1)
						throw new WrongUsageException("/hbmleaf shake ?\nOR /hbmleaf shake <player> [<x> <y> <z>] [params...]", new Object[0]);
					if (args[0].equals("?")) {
						sender.sendMessage(new TextComponentString("Many examples:").setStyle(header));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a ~ ~ ~"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a ~ ~ ~ duration*2"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a intensity=9 range=30 duration=15"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a type=smooth"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a type=smooth preset=RUPTURE"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a type=smooth preset=RUPTURE intensity*2"));
						sender.sendMessage(genSuggestion("/hbmleaf shake @a ~ ~ ~ type=smooth curve=60 intensity=20 duration=30"));
						sender.sendMessage(new TextComponentString(""));
						sender.sendMessage(new TextComponentString("Available types:").setStyle(header));
						sender.sendMessage(new TextComponentString("  simple | smooth"));
						sender.sendMessage(new TextComponentString(""));
						sender.sendMessage(new TextComponentString("Available presets:").setStyle(header));
						boolean isDarkRow = false;
						for (LeafiaShakecam.Preset preset : LeafiaShakecam.Preset.values()) {
							sender.sendMessage(new TextComponentString("  "+preset.name()).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE).setItalic(true)).appendSibling(new TextComponentString(" ("+preset.paramString+")").setStyle(new Style().setColor(isDarkRow ? TextFormatting.GRAY : TextFormatting.DARK_GRAY))));
							isDarkRow = !isDarkRow;
						}
						sender.sendMessage(new TextComponentString(""));
						sender.sendMessage(new TextComponentString("Default parameters:").setStyle(header));
						sender.sendMessage(new TextComponentString("  range=25 intensity=4 curve=2 speed=4 duration=5 ease=expoOut"));
						sender.sendMessage(new TextComponentString("  blurExponent=2 blurDulling=16 bloomExponent=6 bloomDulling=16"));
						sender.sendMessage(new TextComponentString(""));
						sender.sendMessage(new TextComponentString("Use /hbmleaf eases to see all available eases.").setStyle(new Style().setColor(TextFormatting.GREEN)));
						return;
					}
					List<EntityPlayerMP> players = getPlayers(server,sender,args[0]);
					args = shiftArgs(args,1);
					BlockPos pos = null;
					if (args.length > 0) {
						if (!args[0].matches(".*\\D.*") || args[0].startsWith("~")) {
							pos = parseBlockPos(sender,args,0,false);
							args = shiftArgs(args,3);
						}
					}
					for (EntityPlayerMP player : players) {
						ShakecamPacket packet = new ShakecamPacket(args);
						packet.pos = pos;
						LeafiaPacket._sendToClient(packet,player);
					}
				} break;
			}
		}
	}
	public static class ShakecamPacket extends RecordablePacket {
		public String[] params;
		public BlockPos pos = null;
		public ShakecamPacket() {
		}
		public ShakecamPacket(String[] args) {
			params = args;
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			params = new String[buf.readByte()];
			for (int i = 0; i < params.length; i++) params[i] = buf.readUTF8String();
			if (buf.readableBits() >= 32*3)
				pos = new BlockPos(buf.readInt(),buf.readInt(),buf.readInt());
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeByte(params.length);
			for (String param : params) buf.writeUTF8String(param);
			if (pos != null) {
				buf.writeInt(pos.getX());
				buf.writeInt(pos.getY());
				buf.writeInt(pos.getZ());
			}
		}
		public ShakecamPacket setPos(BlockPos pos) {
			this.pos = pos;
			return this;
		}
		public static class Handler implements IMessageHandler<ShakecamPacket, IMessage> {
			static final String[] numerics = new String[]{"range","intensity","curve","speed","duration","blurDulling","blurExponent","bloomDulling","bloomExponent"};
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(ShakecamPacket message, MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Float[] params = {null,null,null,null,null,null,null,null,null};
					float[] adds = {0,0,0,0,0,0,0,0,0};
					float[] multipliers = {1,1,1,1,1,1,1,1,1};
					LeafiaEase.Ease ease = null;
					LeafiaEase.Direction direction = null;
					String type = "simple";
					LeafiaShakecam.Preset preset = null;
					boolean removeEase = false;
					for (String arg : message.params) {
						if (arg.startsWith("type="))
							type = arg.substring(5);
						for (int i = 0; i < numerics.length; i++) {
							String s = numerics[i];
							if (arg.startsWith(s)) {
								arg = arg.substring(s.length());
								if (arg.length() >= 2) {
									String op = arg.substring(0,1);
									arg = arg.substring(1);
									try {
										switch (op) {
											case "=":
												params[i] = (float)parseDouble(arg);
												break;
											case "+":
												adds[i] = (float)parseDouble(arg);
												break;
											case "-":
												adds[i] = -(float)parseDouble(arg);
												break;
											case "*":
												multipliers[i] = (float)parseDouble(arg);
												break;
											case "/":
												multipliers[i] = 1/(float)parseDouble(arg);
												break;
											default:
												Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Invalid operator: "+op));
												break;
										}
									} catch (NumberInvalidException e) {
										Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia(e.getMessage()));
									}
								} else
									Minecraft.getMinecraft().player.sendMessage(new TextComponentString("Malformed numeric parameter!").setStyle(new Style().setColor(TextFormatting.RED)));
							}
						}
						if (arg.startsWith("ease=")) {
							arg = arg.substring(5);
							if (arg.equals("none"))
								removeEase = true;
							else {
								try {
									LeafiaEase easeInsta = LeafiaEase.parseEase(arg);
									ease = easeInsta.ease;
									direction = easeInsta.dir;
								} catch (CommandException e) {
									Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia(e.getMessage()));
								}
							}
						}
						if (arg.startsWith("preset=")) {
							try {
								preset = LeafiaShakecam.Preset.valueOf(arg.substring(7));
							} catch (IllegalArgumentException e) {
								Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia(e.getMessage()));
							}
						}
					}
					LeafiaShakecam.shakeInstance shake;
					switch(type) {
						case "simple": shake = new LeafiaShakecam.shakeSimple(params[4],ease,direction); break;
						case "smooth": shake = new LeafiaShakecam.shakeSmooth(params[4],ease,direction); break;
						default:
							Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Invalid type: "+type));
							return;
					}
					if (preset != null)
						shake.loadPreset(preset);
					shake.configure(params[0],params[1],params[2],params[3]);
					if (params[5] != null)
						shake.blurDulling = params[5];
					if (params[6] != null)
						shake.blurExponent = params[6];
					if (params[7] != null)
						shake.bloomDulling = params[7];
					if (params[8] != null)
						shake.bloomExponent = params[8];

					shake.range *= multipliers[0];
					shake.intensity *= multipliers[1];
					shake.curve *= multipliers[2];
					shake.speed *= multipliers[3];
					shake.duration *= multipliers[4];
					shake.blurDulling *= multipliers[5];
					shake.blurExponent *= multipliers[6];
					shake.bloomDulling *= multipliers[7];
					shake.bloomExponent *= multipliers[8];

					shake.range += adds[0];
					shake.intensity += adds[1];
					shake.curve += adds[2];
					shake.speed += adds[3];
					shake.duration += adds[4];
					shake.blurDulling += adds[5];
					shake.blurExponent += adds[6];
					shake.bloomDulling += adds[7];
					shake.bloomExponent += adds[8];

					if (removeEase)
						shake.easeInstance = null;
					LeafiaShakecam._addShake(message.pos,shake);
				});
				return null;
			}
		}
	}
}
