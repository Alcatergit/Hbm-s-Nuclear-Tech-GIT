package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.TimedGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultDungeonRoomFarm extends VaultDungeonRoomElevator {

    public IBlockState farmland = Blocks.FARMLAND.getStateFromMeta(7);
    public IBlockState water = Blocks.WATER.getDefaultState();
    public IBlockState grate = ModBlocks.steel_grate.getStateFromMeta(7);
    public IBlockState pole = ModBlocks.steel_beam.getDefaultState();

    public IBlockState potato = Blocks.POTATOES.getDefaultState();
    public IBlockState carrot = Blocks.CARROTS.getDefaultState();
    public IBlockState beet = Blocks.BEETROOTS.getDefaultState();
    public IBlockState wheat = Blocks.WHEAT.getDefaultState();

    public VaultDungeonRoomFarm(CellularDungeon parent) {
        super(parent, 5);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        int farmSize = (getW()-7)/2;
        DungeonToolbox.generateBox(world, x+2, y+1, z+2, farmSize, 1, farmSize, farmland);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+1, z+2, farmSize, 1, farmSize, farmland);
        DungeonToolbox.generateBox(world, x+2, y+1, z+farmSize+5, farmSize, 1, farmSize, farmland);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+1, z+farmSize+5, farmSize, 1, farmSize, farmland);

        world.setBlockState(new BlockPos(x+2, y+1, z+2), water);
        world.setBlockState(new BlockPos(x+getW()-3, y+1, z+2), water);
        world.setBlockState(new BlockPos(x+2, y+1, z+getW()-3), water);
        world.setBlockState(new BlockPos(x+getW()-3, y+1, z+getW()-3), water);

        DungeonToolbox.generateBox(world, x+2, y+2, z+2, farmSize, 1, farmSize, potato);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+2, z+2, farmSize, 1, farmSize, carrot);
        DungeonToolbox.generateBox(world, x+2, y+2, z+farmSize+5, farmSize, 1, farmSize, beet);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+2, z+farmSize+5, farmSize, 1, farmSize, wheat);

        world.setBlockState(new BlockPos(x+2, y+2, z+2), light);
        world.setBlockState(new BlockPos(x+getW()-3, y+2, z+2), light);
        world.setBlockState(new BlockPos(x+2, y+2, z+getW()-3), light);
        world.setBlockState(new BlockPos(x+getW()-3, y+2, z+getW()-3), light);

        DungeonToolbox.generateBox(world, x+2, y+3, z+2, farmSize, 1, farmSize, grate);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+3, z+2, farmSize, 1, farmSize, grate);
        DungeonToolbox.generateBox(world, x+2, y+3, z+farmSize+5, farmSize, 1, farmSize, grate);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+3, z+farmSize+5, farmSize, 1, farmSize, grate);

        DungeonToolbox.generateBox(world, x+farmSize+1, y+4, z+farmSize+1, 1, getH()-6, 1, pole);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+4, z+farmSize+1, 1, getH()-6, 1, pole);
        DungeonToolbox.generateBox(world, x+farmSize+1, y+4, z+farmSize+5, 1, getH()-6, 1, pole);
        DungeonToolbox.generateBox(world, x+farmSize+5, y+4, z+farmSize+5, 1, getH()-6, 1, pole);
    }
}