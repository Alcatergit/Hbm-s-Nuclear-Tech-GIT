package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.TimedGenerator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;

import java.util.ArrayList;
import java.util.List;

public class VaultDungeonRoomWiese extends VaultDungeonRoomElevator {

    WorldGenTrees tree;
    public List<IBlockState> wiese = new ArrayList<>();

    public VaultDungeonRoomWiese(CellularDungeon parent) {
        super(parent, 13);
        wiese.add(null);
        for(int i=0; i<16; i++) wiese.add(Blocks.TALLGRASS.getStateFromMeta(1));
        wiese.add(Blocks.TALLGRASS.getStateFromMeta(2));
        tree = new WorldGenTrees(false);
    }

    @Override
    public void generateRoom(final World world, final int x, final int y, final int z) {
        DungeonToolbox.generateBox(world, x+2, y+1, z+2, getW()-4, 1, getW()-4, Blocks.GRASS.getDefaultState());
        for(int i = 0; i < world.rand.nextInt(2)+3; i++) {
            tree.generate(world, world.rand, new BlockPos(x+2+world.rand.nextInt(getW()-4), y+2,z+2+world.rand.nextInt(getW()-4)));
        }
        DungeonToolbox.generateBoxNoReplace(world, x+2, y+2, z+2, getW()-4, 1, getW()-4, wiese);
    }

    @Override
    public int getH(){
        return parent.height + 3;
    }
}