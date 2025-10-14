package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.world.World;

public class VaultDungeonRoomSleep extends VaultDungeonRoomElevator {
    public VaultDungeonRoomSleep(CellularDungeon parent) {
        super(parent, 15);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        DungeonToolbox.generateBox(world, x+getW()/2-2, y+2, z+2, 1, getH()-4, getW()-4, parent.wall);
        DungeonToolbox.generateBox(world, x+getW()/2-2, y+3, z+2, 1, 1, getW()-4, getLine(x, z));

        DungeonToolbox.generateBox(world, x+getW()/2+2, y+2, z+2, 1, getH()-4, getW()-4, parent.wall);
        DungeonToolbox.generateBox(world, x+getW()/2+2, y+3, z+2, 1, 1, getW()-4, getLine(x, z));

        DungeonToolbox.generateBox(world, x+getW()/2-2, y+2, z+getW()/2-1, 5, 3, 3, air);
    }
}