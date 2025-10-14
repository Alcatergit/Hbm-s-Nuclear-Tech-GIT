package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.world.World;

public class VaultDungeonRoomCanteen extends VaultDungeonRoomElevator {
    public VaultDungeonRoomCanteen(CellularDungeon parent) {
        super(parent, 1);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        int centerX = x + getW()/2;
        int centerZ = z + getW()/2;

        DungeonToolbox.generateBox(world, centerX-2, y+2, centerZ-4, 1, getH()-4, 9, parent.wall);
        DungeonToolbox.generateBox(world, centerX-2, y+3, centerZ-4, 1, 1, 9, getLine(x, z));

        DungeonToolbox.generateBox(world, centerX+2, y+2, centerZ-4, 1, getH()-4, 9, parent.wall);
        DungeonToolbox.generateBox(world, centerX+2, y+3, centerZ-4, 1, 1, 9, getLine(x, z));

        DungeonToolbox.generateBox(world, centerX-4, y+2, centerZ-2, 9, getH()-4, 1, parent.wall);
        DungeonToolbox.generateBox(world, centerX-4, y+3, centerZ-2, 9, 1, 1, getLine(x, z));

        DungeonToolbox.generateBox(world, centerX-4, y+2, centerZ+2, 9, getH()-4, 1, parent.wall);
        DungeonToolbox.generateBox(world, centerX-4, y+3, centerZ+2, 9, 1, 1, getLine(x, z));

        DungeonToolbox.generateBox(world, centerX-2, y+2, centerZ-1, 5, getH()-4, 3, air);
        DungeonToolbox.generateBox(world, centerX-1, y+2, centerZ-2, 3, getH()-4, 5, air);
    }
}