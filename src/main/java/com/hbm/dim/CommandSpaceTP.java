package com.hbm.dim;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

public class CommandSpaceTP extends CommandBase {

    @Override
    public String getName() {
        return "dimtp";
    }

    @Override
    public String getUsage(ICommandSender sender) {
        return "/dimtp <dimension_id>";
    }

    @Override
    public List<String> getAliases() {
        return Collections.singletonList("dimtp");
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] args) throws CommandException {
        if (args.length < 1) {
            throw new CommandException("commands.dimtp.usage");
        }

        int dimensionId;
        try {
            dimensionId = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            throw new CommandException("commands.dimtp.invalid_dimension", args[0]);
        }

        if (sender instanceof EntityPlayerMP) {
            Side sidex = FMLCommonHandler.instance().getEffectiveSide();
            if (sidex == Side.SERVER) {
                MinecraftServer mServer = FMLCommonHandler.instance().getMinecraftServerInstance();
                EntityPlayerMP player = (EntityPlayerMP) sender;
                WorldServer sourceServer = player.getServerWorld();
                WorldServer targetWorld = mServer.getWorld(dimensionId);

                if (targetWorld == null) {
                    throw new CommandException("commands.dimtp.dimension_not_found", dimensionId);
                }

                BlockPos pos = player.getPosition();
                player.changeDimension(dimensionId, new DebugTeleporter(sourceServer, targetWorld, player, pos.getX(), pos.getY(), pos.getZ(), true));
            }
        } else {
            throw new CommandException("commands.dimtp.not_player");
        }
    }

    @Override
    public boolean checkPermission(MinecraftServer server, ICommandSender sender) {
        return sender.canUseCommand(2, this.getName());
    }

    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos targetPos) {
        return Collections.emptyList();
    }

    @Override
    public boolean isUsernameIndex(String[] args, int index) {
        return false;
    }
}
