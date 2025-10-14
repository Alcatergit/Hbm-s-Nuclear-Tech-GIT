package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.world.World;

public class VaultDungeonRoomArmory extends VaultDungeonRoomElevator {
    public VaultDungeonRoomArmory(CellularDungeon parent) {
        super(parent, 7);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {

        DungeonToolbox.generateBox(world, x+5, y+2, z+getW()/2-2, getW()-7, getH()-4, 1, parent.wall);
        DungeonToolbox.generateBox(world, x+5, y+3, z+getW()/2-2, getW()-7, 1, 1, getLine(x, z));

        DungeonToolbox.generateBox(world, x+2, y+2, z+getW()/2+2, getW()-7, getH()-4, 1, parent.wall);
        DungeonToolbox.generateBox(world, x+2, y+3, z+getW()/2+2, getW()-7, 1, 1, getLine(x, z));
    }
}