package com.hbm.command;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.hbm.blocks.ModBlocks;
import com.hbm.world.Antenna;
import com.hbm.world.Barrel;
import com.hbm.world.Bunker;
import com.hbm.world.CrashedVertibird;
import com.hbm.world.DesertAtom001;
import com.hbm.world.Dud;
import com.hbm.world.Factory;
import com.hbm.world.Geyser;
import com.hbm.world.GeyserLarge;
import com.hbm.world.LibraryDungeon;
import com.hbm.world.Radio01;
import com.hbm.world.Relay;
import com.hbm.world.Satellite;
import com.hbm.world.Sellafield;
import com.hbm.world.Silo;
import com.hbm.world.Spaceship;
import com.hbm.world.Vertibird;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.init.Blocks;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class CommandHbm extends CommandBase {

	@Override
	public String getName() {
		return "hbm";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "[WIP] Usage: /hbm <subcommand> <args>\nDo /hbm subcommands for a list of subcommands";
	}
	
	@Override
	public int getRequiredPermissionLevel() {
		//Level 2 ops can do commands like setblock, gamemode, and give. They can't kick/ban or stop the server.
		return 2;
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		if(args.length == 1) {
			return getSubCommands().stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());
		} else if(args.length == 2) {
			if("subcommands".equals(args[0])) {
				return Lists.newArrayList("gen").stream().filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
			} else if("gen".equals(args[0])) {
				return Lists.newArrayList("antenna", "relay", "dud", "silo", "factory", "barrel", "vertibird", "vertibird_crashed", "satellite", "spaceship", "sellafield", "radio", "bunker", "desert_atom", "library", "geysir_water", "geysir_vapor", "geysir_chlorine").stream().filter(s ->  s.startsWith(args[1])).collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		if(args.length == 0) {
			throw new CommandException(getUsage(sender));
		} else if(args.length > 0) {
			if("subcommands".equals(args[0])) {
				doSubcommandCommand(server, sender, args);
				return;
			} else if("gen".equals(args[0])) {
				doGenCommand(server, sender, args);
				return;
			}
		}
	}

	protected List<String> getSubCommands() {
		return Lists.newArrayList("subcommands", "gen");
	}

	protected void doSubcommandCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if(args.length == 1) {
			//If no subcommand is specified, list available subcommands.
			StringBuilder builder = new StringBuilder();
			builder.append("Hbm command list [DEBUG]\n\n");
			for(String s : getSubCommands()) {
				builder.append(s).append("\n");
			}
			builder.delete(builder.length() - 1, builder.length());
			sender.sendMessage(new TextComponentTranslation(builder.toString()));
		} else if(args.length > 1){
			//If a subcommand is specified, try to give info about that command. If it doesn't exist, send an error message.
			if("gen".equals(args[1])){
				StringBuilder builder = new StringBuilder();
				builder.append("Info for command: gen\n\n");
				builder.append("Generates a structure at the block under your current position. Generation can be forced.\n\n");
				builder.append("Available structures:\n\n");
				builder.append("antenna      relay\ndud           silo\nfactory      barrel\nvertibird     vertibird_crashed\nsatellite      spaceship\nsellafield     radio\nbunker       desert_atom\nlibrary      geysir_water\ngeysir_vapor      geysir_chlorine");
				//builder.delete(builder.length() - 1, builder.length());
				sender.sendMessage(new TextComponentTranslation(builder.toString()));
			} else {
				sender.sendMessage(new TextComponentTranslation("Unknown command: " + args[1]));
			}
		}
	}

	protected void doGenCommand(MinecraftServer server, ICommandSender sender, String[] args) {
		if(args.length > 1) {
			boolean force = false;
			World world = sender.getEntityWorld();
			Random rand = world.rand;
			Vec3d senderPos = sender.getPositionVector();
			BlockPos genPos = new BlockPos(senderPos.x, world.getHeight((int) senderPos.x, (int) senderPos.z), senderPos.z);
			
			if(args.length > 2 && "f".equals(args[2]))
				force = true;

			if("antenna".equals(args[1])) {
				new Antenna().generate(world, rand, genPos, force);
			} else if("relay".equals(args[1])) {
				new Relay().generate(world, rand, genPos, force);
			} else if("dud".equals(args[1])){
				new Dud().generate(world, rand, genPos, force);
			} else if("silo".equals(args[1])){
				new Silo().generate(world, rand, genPos, force);
			} else if("factory".equals(args[1])){
				new Factory().generate(world, rand, genPos, force);
			} else if("barrel".equals(args[1])){
				new Barrel().generate(world, rand, genPos, force);
			} else if("vertibird".equals(args[1])){
				new Vertibird().generate(world, rand, genPos, force);
			} else if("vertibird_crashed".equals(args[1])){
				new CrashedVertibird().generate(world, rand, genPos, force);
			} else if("satellite".equals(args[1])){
				new Satellite().generate(world, rand, genPos, force);
			} else if("spaceship".equals(args[1])){
				new Spaceship().generate(world, rand, genPos, force);
			} else if("sellafield".equals(args[1])){
				double r = rand.nextInt(15) + 10;
				if (rand.nextInt(50) == 0)
					r = 50;

				new Sellafield().generate(world, (int)senderPos.x, (int)senderPos.z, r, r * 0.35D);
			} else if("radio".equals(args[1])){
				new Radio01().generate(world, rand, genPos, force);
			} else if("bunker".equals(args[1])){
				new Bunker().generate(world, rand, genPos, force);
			} else if("desert_atom".equals(args[1])){
				new DesertAtom001().generate(world, rand, genPos, force);
			} else if("library".equals(args[1])){
				new LibraryDungeon().generate(world, rand, genPos, force);
			} else if("geysir_water".equals(args[1])){
				if(force){
					new GeyserLarge().generate(world, rand, genPos);
				} else {
					if (world.getBlockState(genPos.down()).getBlock() == Blocks.SAND)
						new GeyserLarge().generate(world, rand, genPos);
				}
			} else if("geysir_vapor".equals(args[1])){
				if(force){
					world.setBlockState(genPos.down(), ModBlocks.geysir_vapor.getDefaultState());
				} else {
					if (world.getBlockState(genPos.down()).getBlock() == Blocks.STONE)
						world.setBlockState(genPos.down(), ModBlocks.geysir_vapor.getDefaultState());
				}
			} else if("geysir_chlorine".equals(args[1])){
				if(force){
					new Geyser().generate(world, rand, genPos);
				} else {
					if (world.getBlockState(genPos.down()).getBlock() == Blocks.GRASS)
						new Geyser().generate(world, rand, genPos);
				}
			}
		}
	}

}
