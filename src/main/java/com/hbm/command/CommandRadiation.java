package com.hbm.command;

import java.util.ArrayList;
import java.util.List;

import com.hbm.capability.HbmLivingCapability;
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
		return "Usage: /hbmrad <block x> <block y>  <block z> <new rad> \n /hbmrad <clearall/reset> \n or /hbmrad player <player> <newRad>";
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
			list.add(String.valueOf(sender.getPosition().getY()));
		} else if (args.length == 4 && args[0].equals("set")){
			list.add(String.valueOf(sender.getPosition().getZ()));
		} else if (args.length == 5 && args[0].equals("set")){
			list.add(String.valueOf(0));
		}
		else if(args.length == 3 && getPlayer(server, args[1]) != null){
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
		if (args.length == 5 && args[0].equals("set") && isInteger(args[1]) && isInteger(args[2]) && isInteger(args[3])) {
			int blockX;
			int blockY;
			int blockZ;
			int amount;
			if (args[1].equals("~"))
				blockX = sender.getPosition().getX();
			else
				blockX = Integer.parseInt(args[1]);
			if (args[2].equals("~"))
				blockY = sender.getPosition().getY();
			else
				blockY = Integer.parseInt(args[2]);
			if (args[3].equals("~"))
				blockZ = sender.getPosition().getZ();
			else
				blockZ = Integer.parseInt(args[3]);
			amount = Integer.parseInt(args[4]);
			RadiationSavedData.getData(sender.getEntityWorld()).setRadForCoord(new BlockPos(blockX, blockY, blockZ), amount);
			sender.sendMessage(new TextComponentTranslation(
					"Set radiation at coords (" + blockX + ", " + blockY + ", " + blockZ + ") to " + amount + "."));
			return;
		} else if (args.length == 1 && (args[0].equals("clearall") || args[0].equals("reset"))) {
			RadiationSavedData.getData(sender.getEntityWorld()).jettisonData();
			sender.sendMessage(new TextComponentTranslation("commands.hbmrad.removeall",
					sender.getEntityWorld().provider.getDimension()));
			return;
		} else if(args.length == 3 && args[0].equals("player")){
			EntityPlayerMP player = getPlayer(server, args[1]);
			if(player == null){
				throw new CommandException("commands.hbmrad.not_found_player", args[1]);
			}
			if(!isFloat(args[2]))
				throw new CommandException("commands.hbmrad.rad_not_int");
			float newRads = Float.parseFloat(args[2]);
			if(newRads < 0.0F)
				newRads = 0.0F;
			if(player.hasCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null))
				player.getCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).setRads(newRads);
			sender.sendMessage(new TextComponentTranslation("Set radiation for player " + player.getName() + " to " + newRads + "."));
			return;
		} else if(args.length == 2 && args[0].equals("player")){
			throw new CommandException("commands.hbmrad.not_rad");
		} else if(args.length == 1 && args[0].equals("resetplayers")){
			for(String s : server.getOnlinePlayerNames()){
				EntityPlayerMP player = getPlayer(server, s);
				if(player != null){
					if(player.hasCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null))
						player.getCapability(HbmLivingCapability.EntityHbmPropsProvider.ENT_HBM_PROPS_CAP, null).setRads(0.0F);
				}
			}
			sender.sendMessage(new TextComponentTranslation("commands.hbmrad.player_success"));
			return;
		}
		throw new CommandException(this.getUsage(sender));
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
