package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.WeightedRandomChestContentFrom1710;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class VaultDungeonRoomMedical extends VaultDungeonRoomElevator {

    public static List<IBlockState> lab = new ArrayList<>();


    public VaultDungeonRoomMedical(CellularDungeon parent) {
        super(parent, 14);
        lab.add(ModBlocks.tile_lab.getDefaultState());
        lab.add(ModBlocks.tile_lab_broken.getDefaultState());
        lab.add(ModBlocks.tile_lab_cracked.getDefaultState());
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        DungeonToolbox.generateBox(world, x+2, y+1, z+2, getW()-4, 1, getW()-4, lab);

        DungeonToolbox.generateWalls(world, x+1, y+2, z+1, getW()/2-2, getH()-4, getW()/2-2, parent.wall);
        DungeonToolbox.generateWalls(world, x+getW()/2+2, y+2, z+1, getW()/2-2, getH()-4, getW()/2-2, parent.wall);
        DungeonToolbox.generateWalls(world, x+1, y+2, z+getW()/2+2, getW()/2-2, getH()-4, getW()/2-2, parent.wall);
        DungeonToolbox.generateWalls(world, x+getW()/2+2, y+2, z+getW()/2+2, getW()/2-2, getH()-4, getW()/2-2, parent.wall);

        DungeonToolbox.generateWalls(world, x+1, y+3, z+1, getW()/2-2, 1, getW()/2-2, getLine(x, z));
        DungeonToolbox.generateWalls(world, x+getW()/2+2, y+3, z+1, getW()/2-2, 1, getW()/2-2, getLine(x, z));
        DungeonToolbox.generateWalls(world, x+1, y+3, z+getW()/2+2, getW()/2-2, 1, getW()/2-2, getLine(x, z));
        DungeonToolbox.generateWalls(world, x+getW()/2+2, y+3, z+getW()/2+2, getW()/2-2, 1, getW()/2-2, getLine(x, z));

        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(x+2, y+2, z+2), EnumFacing.EAST, 1);
        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(x+getW()-3, y+2, z+2), EnumFacing.WEST, 2);
        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(x+2, y+2, z+getW()-3), EnumFacing.EAST, 2);
        WeightedRandomChestContentFrom1710.placeLootChest(world, new BlockPos(x+getW()-3, y+2, z+getW()-3), EnumFacing.WEST, 3);


        DungeonToolbox.generateBox(world, x+4, y+2, z+getW()/2-2, 1, 2, 5, air);
        DungeonToolbox.generateBox(world, x+getW()-5, y+2, z+getW()/2-2, 1, 2, 5, air);

    }
}