package com.hbm.handler;

import com.hbm.interfaces.IDummy;
import com.hbm.main.MainRegistry;
import com.hbm.packet.NBTPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public final class MultiblockHandler {

	private MultiblockHandler() {
	}

	@Deprecated
	public static EnumFacing intToEnumFacing(int dir){
		if(dir == 2)
			return EnumFacing.NORTH;
		if(dir == 5)
			return EnumFacing.EAST;
		if(dir == 3)
			return EnumFacing.SOUTH;
		if(dir == 4)
			return EnumFacing.WEST;
		return EnumFacing.NORTH;
	}

	public static boolean checkSpace(World world, BlockPos pos, MultiblockDefinition def) {
		boolean placable = true;
		MutableBlockPos replace = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		for(int a = x - def.getNegX(); a <= x + def.getPosX(); a++) {
			for(int b = y - def.getNegY(); b <= y + def.getPosY(); b++) {
				for(int c = z - def.getNegZ(); c <= z + def.getPosZ(); c++) {
					if(!(a == x && b == y && c == z)) {
						Block block = world.getBlockState(replace.setPos(a, b, c)).getBlock();
						if(block != Blocks.AIR && !block.isReplaceable(world, replace)) {
							placable = false;
						}
					}
				}
			}
		}

		return placable;
	}

	public static boolean fillUp(World world, BlockPos pos, MultiblockDefinition def, Block block) {
		boolean placable = true;
		MutableBlockPos replace = new BlockPos.MutableBlockPos();
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		for(int a = x - def.getNegX(); a <= x + def.getPosX(); a++) {
			for(int b = y - def.getNegY(); b <= y + def.getPosY(); b++) {
				for(int c = z - def.getNegZ(); c <= z + def.getPosZ(); c++) {
					if(!(a == x && b == y && c == z)) {
						world.setBlockState(replace.setPos(a, b, c), block.getDefaultState());
						TileEntity te = world.getTileEntity(replace.setPos(a, b, c));
						if(te instanceof TileEntityDummy) {
							TileEntityDummy dummy = (TileEntityDummy)te;
							dummy.target = pos;
						}
					}
				}
			}
		}

		return placable;
	}
}
