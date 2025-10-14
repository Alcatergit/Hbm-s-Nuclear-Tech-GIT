package com.hbm.world.generator.room;

import com.hbm.world.NuclearReactor;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultDungeonRoomPower extends VaultDungeonRoomElevator {
    public VaultDungeonRoomPower(CellularDungeon parent) {
        super(parent, 3);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        int centerX = x + getW()/2;
        int centerZ = z + getW()/2;

        new NuclearReactor().generate(world, world.rand, new BlockPos(centerX, y+2, centerZ));
        DungeonToolbox.generateWalls(world, centerX-3, y+2, centerZ-3, 7, getH()-4, 7, parent.wall);
        DungeonToolbox.generateWalls(world, centerX-3, y+3, centerZ-3, 7, 1, 7, getLine(x, z));

        DungeonToolbox.generateWalls(world, centerX-3, y+2, centerZ, 1, getH()-4, 1, air);
        DungeonToolbox.generateWalls(world, centerX, y+2, centerZ-3, 1, getH()-4, 1, air);
        DungeonToolbox.generateWalls(world, centerX, y+2, centerZ+3, 1, getH()-4, 1, air);
        DungeonToolbox.generateWalls(world, centerX+3, y+2, centerZ, 1, getH()-4, 1, air);
    }

    @Override
    public boolean spawnGlow(){
        return false;
    }
}