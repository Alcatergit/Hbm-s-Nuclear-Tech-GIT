package com.hbm.command;

import java.util.ArrayList;
import java.util.List;

import com.hbm.capability.RadiationCapability;
import com.hbm.entity.particle.EntityFogFX;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;

//Well this class is a big mess. Needs some helper methods, should add later
public class CommandRadiation extends CommandBase {

	@Override
	public String getName() {
		return "hbmrad";
	}

	@Override
	public String getUsage(ICommandSender sender) {
		return "Usage: /hbmrad <block x> <block z> <new rad> \n /hbmrad <clearall/reset> \n or /hbmrad player <player> <newRad>";
	}

	@Override
	public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, BlockPos targetPos) {
		List<String> list = new ArrayList<String>();
		if (args.length == 1) {
			
			list.add("set");
			list.add("clearall");
			list.add("reset");
			list.add("player");
			list.add("resetplayers");
		} else if (args.length == 2 && (args[0].equals("clearall") || args[0].equals("reset") || args[0].equals("resetplayers"))) {
			
		} else if(args.length == 2 && args[0].equals("player")){
			for(String s : server.getOnlinePlayerNames())
				list.add(s);
		} else if (args.length == 2 && args[0].equals("set")){
			list.add(String.valueOf(sender.getPosition().getX()));
		} else if (args.length == 3 && args[0].equals("set")){
			list.add(String.valueOf(sender.getPosition().getZ()));
		} else if (args.length == 4 && args[0].equals("set")){
			list.add(String.valueOf(0));
		} else if(args.length == 3 && getPlayer(server, args[1]) != null){
			list.add(String.valueOf(0));
		}
		return list;
	}

	@Override
	public int getRequiredPermissionLevel() {
		return 3;
	}
	
	@Override
	public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
		//TODO UUID testing, remove later
		return super.checkPermission(server, sender) || Library.superuser.contains(sender.getCommandSenderEntity().getUniqueID().toString());
	}

	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
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
		} else if(args.length == 3 && args[0].equals("player")){
			EntityPlayerMP player = getPlayer(server, args[1]);
			if(player == null){
				throw new CommandException("Cannot find player '" + args[1] + "'!", new Object[0]);
			}
			if(!isFloat(args[2]))
				throw new CommandException("New rad value is not a number!");
			float newRads = Float.parseFloat(args[2]);
			if(newRads < 0.0F)
				newRads = 0.0F;
			if(player.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
				player.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).setRads(newRads);
			sender.sendMessage(new TextComponentTranslation("Set radiation for player " + player.getName() + " to " + newRads + "."));
			return;
		} else if(args.length == 2 && args[0].equals("player")){
			throw new CommandException("Please enter new rad value!");
		} else if(args.length == 1 && args[0].equals("resetplayers")){
			for(String s : server.getOnlinePlayerNames()){
				EntityPlayerMP player = getPlayer(server, s);
				if(player != null){
					if(player.hasCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null))
						player.getCapability(RadiationCapability.EntityRadiationProvider.ENT_RAD_CAP, null).setRads(0.0F);
				}
			}
			sender.sendMessage(new TextComponentTranslation("Successfully cleared radiation for all online players!"));
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
	
	public boolean isFloat(String s){
		try{
			Float.parseFloat(s);
			return true;
		} catch (NumberFormatException e){
			return false;
		}
	}
	
	public EntityPlayerMP getPlayer(MinecraftServer server, String name){
		return server.getPlayerList().getPlayerByUsername(name);
	}
	

}
