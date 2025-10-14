package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.CellularDungeonRoom;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class VaultDungeonRoom extends CellularDungeonRoom {

    public static IBlockState shielding = Blocks.BEDROCK.getDefaultState();
    public static IBlockState tunnel = ModBlocks.ducrete_smooth.getDefaultState();
    public static IBlockState air = Blocks.AIR.getDefaultState();
    public int lineColor;
    public IBlockState line;
    public static IBlockState light = ModBlocks.reinforced_light.getDefaultState();
    public static IBlockState cableLine = ModBlocks.ducrete_reinforced.getDefaultState();

    public VaultDungeonRoom(CellularDungeon parent, int lineColor) {
		super(parent);
        this.line = Blocks.CONCRETE.getStateFromMeta(lineColor);
        this.lineColor = lineColor;
	}

    public void resetLine(){
        this.line = Blocks.CONCRETE.getStateFromMeta(this.lineColor);
    }

    public void generateMain(final World world, final int x, final int y, final int z, int w, int h){
        DungeonToolbox.generateBox(world, x, y, z, w, h, w, shielding);

        DungeonToolbox.generateBox(world, x+1, y+1, z+1, w-2, 1, w-2, parent.floor);
        DungeonToolbox.generateBox(world, x+1, y + h - 2, z+1, w-2, 1, w-2, parent.ceiling);

        DungeonToolbox.generateBox(world, x+1, y + 2, z+1, w-2, h - 4, w-2, air);
    }
	
	@Override
	public void generateMain(final World world, final int x, final int y, final int z) {
        ITimedJob job = () -> {
            generateMain(world, x, y, z, getW(), getH());
            for(int dx=-4; dx<5; dx+=2) {
                for (int dz=-4; dz<5; dz+=2) {
                    world.setBlockState(new BlockPos(x + getW() / 2 + dx, y + getH() - 2, z + getW() / 2 + dz), light);
                }
            }
        };
        TimedGenerator.addOp(world, job);
    }

    public int getW(){
        return parent.width;
    }

    public int getH(){
        return parent.height;
    }

    public IBlockState getLine(int x, int z){
        return line;
    }

    @Override
    public void generateWall(final World world, final int x, final int y, final int z, final EnumFacing wall, final boolean door) {

        ITimedJob job = () -> {

            if(wall == EnumFacing.NORTH) {
                DungeonToolbox.generateBox(world, x+1, y + 2, z+1, getW()-2, getH()-4, 1, parent.wall);
                DungeonToolbox.generateBox(world, x+1, y + 3, z+1, getW()-2, 1, 1, getLine(x, z));

                if(door) {
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 2, y + 2, z, 5, 4, 1, tunnel);
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 2, y + 1, z, 5, 1, 1, parent.floor);
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 1, y + 2, z - 1, 3, 3, 3, air);

                    DungeonToolbox.generateBox(world, x + 1, y + getH()-2, z, getW()-2, 1, 1, cableLine);
                }
            }

            else if(wall == EnumFacing.SOUTH) {
                DungeonToolbox.generateBox(world, x+1, y + 2, z + getW()-2, getW()-2, getH()-4, 1, parent.wall);
                DungeonToolbox.generateBox(world, x+1, y + 3, z + getW()-2, getW()-2, 1, 1, getLine(x, z));

                if(door) {
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 2, y + 2, z + getW()-1, 5, 4, 1, tunnel);
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 2, y + 1, z + getW()-1, 5, 1, 1, parent.floor);
                    DungeonToolbox.generateBox(world, x + getW() / 2 - 1, y + 2, z + getW() - 2, 3, 3, 3, air);

                    DungeonToolbox.generateBox(world, x + 1, y + getH()-2, z + getW()-1, getW()-2, 1, 1, cableLine);
                }
            }

            else if(wall == EnumFacing.WEST) {
                DungeonToolbox.generateBox(world, x+1, y + 2, z+1, 1, getH()-4, getW()-2, parent.wall);
                DungeonToolbox.generateBox(world, x+1, y + 3, z+1, 1, 1, getW()-2, getLine(x, z));

                if(door) {
                    DungeonToolbox.generateBox(world, x, y + 2, z + getW() / 2 - 2, 1, 4, 5, tunnel);
                    DungeonToolbox.generateBox(world, x, y + 1, z + getW() / 2 - 2, 1, 1, 5, parent.floor);
                    DungeonToolbox.generateBox(world, x-1, y + 2, z + getW() / 2 - 1, 3, 3, 3, air);

                    DungeonToolbox.generateBox(world, x, y + getH()-2, z+1, 1, 1, getW()-2, cableLine);
                }
            }

            else if(wall == EnumFacing.EAST) {
                DungeonToolbox.generateBox(world, x + getW() - 2, y + 2, z+1, 1, getH()-4, getW()-2, parent.wall);
                DungeonToolbox.generateBox(world, x + getW() - 2, y + 3, z+1, 1, 1, getW()-2, getLine(x, z));

                if(door) {
                    DungeonToolbox.generateBox(world, x + getW() - 1, y + 2, z + getW() / 2 - 2, 1, 4, 5, tunnel);
                    DungeonToolbox.generateBox(world, x + getW() - 1, y + 1, z + getW() / 2 - 2, 1, 1, 5, parent.floor);
                    DungeonToolbox.generateBox(world, x + getW() - 2, y + 2, z + getW() / 2 - 1, 3, 3, 3, air);

                    DungeonToolbox.generateBox(world, x + getW() - 1, y + getH()-2, z+1, 1, 1, getW()-2, cableLine);
                }
            }
        };
        TimedGenerator.addOp(world, job);
    }
}