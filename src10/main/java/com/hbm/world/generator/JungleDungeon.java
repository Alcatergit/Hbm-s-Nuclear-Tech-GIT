package com.hbm.world.generator;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.TimedGenerator.ITimedJob;

import net.minecraft.block.Block;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class JungleDungeon extends CellularDungeon {

	public boolean hasHole = false;
	
	public JungleDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(ModBlocks.brick_jungle.getDefaultState());
		this.floor.add(ModBlocks.brick_jungle_cracked.getDefaultState());
		this.wall.add(ModBlocks.brick_jungle.getDefaultState());
		this.wall.add(ModBlocks.brick_jungle_cracked.getDefaultState());
		this.ceiling.add(ModBlocks.brick_jungle.getDefaultState());
		this.ceiling.add(ModBlocks.brick_jungle_cracked.getDefaultState());
	}
	
	@Override
	public void generate(final World world, final int x, final int y, final int z, final Random rand) {
		super.generate(world, x, y, z, rand);

		TimedGenerator.addOp(world, () -> {

            JungleDungeon that = JungleDungeon.this;

            //A hole has not been made -> this is the bottom floor
            if(!that.hasHole) {
                generateCircleRoom(world, x, y, z);
                connectCircleRoomToDungeon(world, x, y, z);
            }

            that.hasHole = false;
        });

		//since all the building is timed jobs, this has to be as well. timed jobs are ordered so this works!
		//is it shitty coding? is it not? who knows?
	}



    public void generateCircleRoom(final World world, final int x, final int y, final int z){
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(x, y, z);
        for(int i=0; i < 5; i++){
            for(int j=-2; j<3; j++){
                for(int k=-2; k<3; k++){
                    p.setPos(x+j, y+i, z+k);

                    if((i > 0 && i < 4) && (Math.abs(j) < 2 && Math.abs(k) < 2))
                        world.setBlockToAir(p);
                    else if(i == 0){
                        if(j == 0 && k == 0) world.setBlockState(p, ModBlocks.brick_jungle_circle.getDefaultState());
                        else if(Math.abs(j) == 2 || Math.abs(k) == 2) world.setBlockState(p, ModBlocks.brick_dungeon_flat.getDefaultState());
                        else world.setBlockState(p, ModBlocks.brick_jungle.getDefaultState());
                    }
                    else if(i == 2 && (j == 0 || k == 0))
                        world.setBlockState(p, ModBlocks.brick_dungeon_tile.getDefaultState());
                    else if(i==4 || (Math.abs(j) == 2 && Math.abs(k) == 2))
                        world.setBlockState(p, ModBlocks.brick_jungle.getDefaultState());
                    else world.setBlockState(p, ModBlocks.brick_dungeon.getDefaultState());
                }
            }
        }
    }

    public int getDungeonDistance(final World world, final int x, final int y, final int z, EnumFacing dir){
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(x, y, z);
        for(int i=0; i<100; i++){
            p.move(dir);
            Block b = world.getBlockState(p).getBlock();
            if(b == ModBlocks.brick_jungle || b == ModBlocks.brick_jungle_cracked) return i;
        }
        return -1;
    }

    public void buildTunnel(final World world, int x, int y, int z, int dist, EnumFacing dir, Block b){
        BlockPos.MutableBlockPos p = new BlockPos.MutableBlockPos(x, y, z);
        boolean isNorthSouth = dir.getXOffset() != 0;
        for(int i=0; i<dist; i++) {
            for (int j = -2; j < 3; j++) {
                for (int k = -2; k < 3; k++) {
                    BlockPos bp = p.add(isNorthSouth ? 0 : j, k, isNorthSouth ? j : 0);
                    if (Math.abs(j) == 2 || Math.abs(k) == 2) world.setBlockState(bp, b.getDefaultState());
                    else world.setBlockToAir(bp);
                }
            }
            p.move(dir);
        }
    }

    public void connectCircleRoomToDungeon(final World world, final int x, final int y, final int z){
        EnumFacing direction = null;
        int distance = 200;
        for(EnumFacing dir : EnumFacing.HORIZONTALS){
            Block b = world.getBlockState(new BlockPos(x, y, z).offset(dir, 3)).getBlock();
            if(b == ModBlocks.brick_jungle || b == ModBlocks.brick_jungle_cracked) return;

            int d = getDungeonDistance(world, x, y+2, z, dir);
            if(d > 0 && d < distance){
                distance = d;
                direction = dir;
            }
        }

        if(direction != null && distance > 3){
            buildTunnel(world, x+direction.getXOffset()*3, y+2, z+direction.getZOffset()*3, distance-1, direction, ModBlocks.brick_jungle);
        }
    }
}