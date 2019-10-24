package com.hbm.blocks.command;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.particle.EntityFogFX;
import com.hbm.saveddata.RadiationSaveStructure;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

public class CommandRadiation extends CommandBase {

	@Override
	public String getName() {
		return "hbmrad";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Usage: /hbmrad <block x> <block z> <increase/decrease amount> \n /hbmrad <clearall/reset>";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			list.add("set");
			list.add("clearall");
			list.add("reset");
		} else if (args.length == 2 && (args[0].equals("clearall") || args[0].equals("reset"))) {
			
		} else if (args.length == 2 && args[0].equals("set")){
			list.add(String.valueOf(sender.getPosition().getX()));
		} else if (args.length == 3 && args[0].equals("set")){
			list.add(String.valueOf(sender.getPosition().getZ()));
		} else if (args.length == 4 && args[0].equals("set")){
			list.add(String.valueOf(0));
		}
		return list;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
		System.out.println(args.length);
		if (args.length == 4 && args[0].equals("set") && isInteger(args[1]) && isInteger(args[2]) && isInteger(args[3])) {
			int blockX;
			int blockZ;
			int amount;
			if (args[1].equals("~"))
				blockX = sender.getPosition().getX();
			else
				blockX = Integer.parseInt(args[1]);
			if (args[2].equals("~"))
				blockZ = sender.getPosition().getZ();
			else
				blockZ = Integer.parseInt(args[2]);
			amount = Integer.parseInt(args[3]);
			RadiationSavedData.getData(sender.getEntityWorld()).setRadForCoord(blockX >> 4, blockZ >> 4, amount);
			sender.sendMessage(new TextComponentTranslation(
					"Set radiation at coords (" + blockX + ", " + blockZ + ") to " + amount + "."));
			return;
		} else if (args.length == 1 && (args[0].equals("clearall") || args[0].equals("reset"))) {
			RadiationSavedData.getData(sender.getEntityWorld()).jettisonData();
			for (Entity e : sender.getEntityWorld().loadedEntityList) {
				if (e instanceof EntityFogFX)
					e.setDead();
			}
			sender.sendMessage(new TextComponentTranslation(
					"Removed all loaded radiation for dimension " + sender.getEntityWorld().provider.getDimension() + "."));
			return;
		}
		throw new CommandException(this.getUsage(sender), new Object[0]);
	}

	public boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			if (s.equals("~"))
				return true;
			return false;
		}
	}

}
