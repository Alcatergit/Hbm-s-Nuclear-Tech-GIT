package com.hbm.world.generator.room;

import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultDungeonRoomControl extends VaultDungeonRoomElevator {
    public VaultDungeonRoomControl(CellularDungeon parent) {
        super(parent, 0);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        int centerX = x + getW() / 2;
        int centerZ = z + getW() / 2;

        DungeonToolbox.generateWalls(world, centerX - 3, y + 2, centerZ - 3, 7, getH() - 4, 7, parent.wall);
        DungeonToolbox.generateWalls(world, centerX - 3, y + 3, centerZ - 3, 7, 1, 7, line);

        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(centerX-2, y+2, centerZ), EnumFacing.EAST, 3);
        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(centerX+2, y+2, centerZ), EnumFacing.WEST, 3);

        DungeonToolbox.generateBox(world, centerX, y + 2, centerZ - 3, 1, 2, 1, air);
    }
}