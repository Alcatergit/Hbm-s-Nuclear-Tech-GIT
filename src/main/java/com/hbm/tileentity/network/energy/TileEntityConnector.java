package com.hbm.tileentity.network.energy;

import com.hbm.lib.ForgeDirection;
import com.hbm.render.amlfrom1710.Vec3;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class TileEntityConnector extends TileEntityPylonBase {

    @Override
    public ConnectionType getConnectionType() {
        return ConnectionType.SINGLE;
    }

    @Override
    public Vec3[] getMountPos() {
        return new Vec3[] {Vec3.createVectorHelper(0.5, 0.5, 0.5)};
    }

    @Override
    public int getMaxWireLength() {
        return 10;
    }

    @Override
    public boolean canConnect(ForgeDirection dir) { //i've about had it with your fucking bullshit
        return ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite() == dir;
    }
    @Override
    public List<BlockPos> getConnectionPoints() {
        List<BlockPos> positions = new ArrayList(connected);

        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata()).getOpposite();
        positions.add(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
        return positions;
    }
}
