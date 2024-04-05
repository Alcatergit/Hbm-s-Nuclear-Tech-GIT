package com.hbm.tileentity.network;

import com.hbm.blocks.network.BlockConveyor;
import com.hbm.blocks.network.BlockConveyorChute;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.PipeUpdatePacket;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileEntityConveyorChute extends TileEntity {
    public EnumFacing[] connections = new EnumFacing[6];
    public boolean isBeingDestroyed = false;

    public TileEntityConveyorChute(){}

    @Override
    public void onLoad() {
        if(!world.isRemote){
            world.getMinecraftServer().addScheduledTask(() -> {
                onNeighborChange();
            });
        } else {
            onNeighborChange();
        }
    }

    public static void breakBlock(World world, BlockPos pos) {
        TileEntity te = world.getTileEntity(pos);
        if(te instanceof TileEntityConveyorChute) {
            ((TileEntityConveyorChute) te).isBeingDestroyed = true;
        }
    }

    public void onNeighborChange() {
        updateConnections();
        if(!world.isRemote)
            PacketDispatcher.wrapper.sendToAllTracking(new PipeUpdatePacket(pos), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
    }

    public void updateConnections() {
        if (this.world.getBlockState(pos.up()).getBlock() instanceof BlockConveyor) {
            connections[0] = EnumFacing.UP;
        } else {
            connections[0] = null;
        }

        connections[1] = null;

        if (this.world.getBlockState(pos.north()).getBlock() instanceof BlockConveyor) {
            connections[2] = EnumFacing.NORTH;
        } else {
            connections[2] = null;
        }

        if (this.world.getBlockState(pos.east()).getBlock() instanceof BlockConveyor) {
            connections[3] = EnumFacing.EAST;
        } else {
            connections[3] = null;
        }

        if (this.world.getBlockState(pos.south()).getBlock() instanceof BlockConveyor) {
            connections[4] = EnumFacing.SOUTH;
        } else {
            connections[4] = null;
        }

        if (this.world.getBlockState(pos.west()).getBlock() instanceof BlockConveyor) {
            connections[5] = EnumFacing.WEST;
        } else {
            connections[5] = null;
        }
    }

    private boolean isConnectedToBlock(BlockPos pos, EnumFacing facing, Block block) {
        block = world.getBlockState(pos).getBlock();
        return block == blockType;
    }

    public boolean isValidForBuilding() {
        return !isBeingDestroyed;
    }
}
