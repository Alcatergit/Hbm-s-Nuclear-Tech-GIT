package com.hbm.world.generator.room;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.VaultDoor;
import com.hbm.world.generator.CellularDungeon;
import com.hbm.world.generator.DungeonToolbox;
import com.hbm.world.generator.TimedGenerator;
import com.hbm.world.generator.VaultDungeon;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

import static com.hbm.blocks.generic.BlockNTMLadder.FACING;

public class VaultDungeonRoomElevator extends VaultDungeonRoom {

    public static int doorRoomX= 19;
    public static int doorRoomY= 9;
    public static int doorRoomZ= 17;

    public static List<IBlockState> bricks = new ArrayList<>();
    public static IBlockState haz = ModBlocks.concrete_hazard.getDefaultState();
    public static IBlockState decoSteel = ModBlocks.deco_steel.getDefaultState();
    public static IBlockState ducRef = ModBlocks.ducrete_reinforced.getDefaultState();
    public static IBlockState plating = ModBlocks.deco_tungsten.getDefaultState();
    public static IBlockState dark = ModBlocks.concrete_gray.getDefaultState();
    public static IBlockState pillar = ModBlocks.concrete_pillar.getDefaultState();
    public static IBlockState grate = ModBlocks.steel_grate.getStateFromMeta(7);
    public static IBlockState ladderE = ModBlocks.ladder_steel.getDefaultState().withProperty(FACING, EnumFacing.WEST);
    public static IBlockState ladderW = ModBlocks.ladder_steel.getDefaultState().withProperty(FACING, EnumFacing.EAST);
    public static IBlockState railing = ModBlocks.railing_normal.getStateFromMeta(5);

    public VaultDungeonRoomElevator(CellularDungeon parent, int lineColor) {
        super(parent, lineColor);
        bricks.add(ModBlocks.brick_concrete.getDefaultState());
        bricks.add(ModBlocks.brick_concrete_broken.getDefaultState());
        bricks.add(ModBlocks.brick_concrete_cracked.getDefaultState());
        bricks.add(ModBlocks.brick_concrete_mossy.getDefaultState());
    }

    public static int getGroundHeight(World world, int x, int z){
        BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos(x, 255, z);
        for(int y = 255; y > 1; y--){
            pos.setY(y);
            IBlockState state = world.getBlockState(pos);
            Block b = state.getBlock();
            if(!b.isReplaceable(world, pos)){
                if(b.isOpaqueCube(state)){
                    if(b.getMaterial(state) != Material.WOOD){
                        return y;
                    }
                }
            }
        }
        return 1;
    }

    @Override
    public IBlockState getLine(int x, int z){
        if(parent instanceof VaultDungeon vault && vault.hasElevator && vault.elevatorRoom == this){
            if(vault.eX != x + parent.width / 2 || vault.eZ != z + parent.width / 2){
                resetLine();
            }
        }
        return line;
    }

    public boolean spawnGlow(){
        return true;
    }

    @Override
    public void generateMain(final World world, final int x, final int y, final int z) {
        super.generateMain(world, x, y, z);
        TimedGenerator.ITimedJob job = () -> {
            if(parent instanceof VaultDungeon vault && !vault.hasElevator){
                vault.eX = x + parent.width / 2;
                vault.eZ = z + parent.width / 2;
                vault.hasElevator = true;

                this.line = Blocks.CONCRETE.getStateFromMeta(4);
                vault.elevatorRoom = this;

                int h = Math.max(getGroundHeight(world, vault.eX, vault.eZ)-25, y+parent.height+10);
                generateVaultDoorRoom(world, vault.eX, h, vault.eZ);
                generateElevator(world, vault.eX, y, vault.eZ, h, doorRoomY-2);
            } else {
                generateRoom(world, x, y, z);
                if(spawnGlow()) world.setBlockState(new BlockPos(x+parent.width/2, y+2, z+parent.width/2), ModBlocks.glow_spawner.getDefaultState());
            }
        };
        TimedGenerator.addOp(world, job);
    }

    public void generateRoom(final World world, final int x, final int y, final int z){
    }

    public void generateVaultDoorRoom(final World world, final int x, final int y, final int z){
        DungeonToolbox.generateHollowBox(world, x-5, y-1, z-doorRoomZ/2-1, doorRoomX+2, doorRoomY+2, doorRoomZ+2, parent.wall);
        DungeonToolbox.generateHollowBox(world, x-4, y, z-doorRoomZ/2, doorRoomX, doorRoomY, doorRoomZ, shielding);
        DungeonToolbox.generateBox(world, x-3, y+1, z-doorRoomZ/2+1, doorRoomX-2, 1, doorRoomZ-2, parent.floor);
        DungeonToolbox.generateWalls(world, x-3, y+2, z-doorRoomZ/2+1, doorRoomX-2, doorRoomY-4, doorRoomZ-2, dark);
        DungeonToolbox.generateBox(world, x-3, y+doorRoomY-2, z-doorRoomZ/2+1, doorRoomX-2, 1, doorRoomZ-2, parent.ceiling);
        DungeonToolbox.generateBox(world, x-2, y+2, z-doorRoomZ/2+2, doorRoomX-4, doorRoomY-4, doorRoomZ-4, air);

        DungeonToolbox.generateBox(world, x+doorRoomX-5, y+1, z-5, 1, 7, 7, ducRef);
        DungeonToolbox.generateBox(world, x+doorRoomX-6, y+2, z-4, 3, 5, 5, air);

        VaultDoor.placeVaultDoor(world, new BlockPos(x+doorRoomX-6, y+2, z-2), EnumFacing.EAST);
        world.setBlockState(new BlockPos(x+doorRoomX-7, y+4, z-5), Blocks.LEVER.getStateFromMeta(2), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-5, y+4, z-4), Blocks.STONE_BUTTON.getStateFromMeta(3), 3);

        DungeonToolbox.generateBox(world, x+doorRoomX-8, y+1, z-doorRoomZ/2+2, 1, 1, doorRoomZ-4, haz);
        DungeonToolbox.generateBox(world, x+doorRoomX-8, y+2, z-doorRoomZ/2+2, 1, 1, doorRoomZ-4, railing);
        DungeonToolbox.generateBox(world, x+doorRoomX-6, y+4, z+1, 1, 1, 6, decoSteel);
        DungeonToolbox.generateBox(world, x+doorRoomX-11, y+2, z-3, 4, 1, 3, grate);

        world.setBlockState(new BlockPos(x+doorRoomX-8, y+3, z-3), ModBlocks.railing_end_flipped_self.getStateFromMeta(3), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-9, y+3, z-3), ModBlocks.railing_normal.getStateFromMeta(3), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-10, y+3, z-3), ModBlocks.railing_normal.getStateFromMeta(3), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-11, y+3, z-3), ModBlocks.railing_end_self.getStateFromMeta(3), 3);

        world.setBlockState(new BlockPos(x+doorRoomX-8, y+3, z-1), ModBlocks.railing_end_self.getStateFromMeta(2), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-9, y+3, z-1), ModBlocks.railing_normal.getStateFromMeta(2), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-10, y+3, z-1), ModBlocks.railing_normal.getStateFromMeta(2), 3);
        world.setBlockState(new BlockPos(x+doorRoomX-11, y+3, z-1), ModBlocks.railing_end_flipped_self.getStateFromMeta(2), 3);

        DungeonToolbox.generateBox(world, x+doorRoomX-11, y+2, z-4, 3, 1, 1, ModBlocks.concrete_stairs.getStateFromMeta(2));
        DungeonToolbox.generateBox(world, x+doorRoomX-11, y+2, z, 3, 1, 1, ModBlocks.concrete_stairs.getStateFromMeta(3));

        DungeonToolbox.generateBox(world, x+doorRoomX-12, y+2, z-4, 1, 1, 5, ModBlocks.concrete_stairs.getStateFromMeta(0));



        int h = getGroundHeight(world, x+doorRoomX+8, z-2)+1;
        DungeonToolbox.generateHollowBox(world, x+doorRoomX-3, y, z-6, 10, 9, 9, bricks);
        DungeonToolbox.generateHollowBox(world, x+doorRoomX+6, y, z-4, 5, h-y, 5, bricks);
        DungeonToolbox.generateBox(world, x+doorRoomX+7, y+1, z-3, 3, h-y, 3, air);
        DungeonToolbox.generateBox(world, x+doorRoomX-4, y+1, z-5, 10, 7, 7, air);
        DungeonToolbox.generateBox(world, x+doorRoomX+6, y+1, z-3, 1, 3, 3, air);

        for(int dx=-6; dx<6; dx+=2) {
            for(int dz=-4; dz<5; dz+=2) {
                world.setBlockState(new BlockPos(x + 6 + dx, y + doorRoomY - 2, z-doorRoomZ/2+8 + dz), light);
            }
        }
    }

    public void generateElevator(final World world, final int x, final int y, final int z, int h, int ladderOffset){
        int baseY = y+2;
        int baseH = h-y-2+ladderOffset;
        DungeonToolbox.generateWalls(world, x-3, y+getH(), z-3, 7, h-getH()-y, 7, shielding);
        DungeonToolbox.generateWalls(world, x-2, baseY, z-2, 5, baseH, 5, plating);
        DungeonToolbox.generateBox(world, x-2, baseY, z, 1, baseH, 1, pillar);
        DungeonToolbox.generateBox(world, x+2, baseY, z, 1, baseH, 1, pillar);
        DungeonToolbox.generateBox(world, x, baseY, z-2, 1, baseH, 1, pillar);
        DungeonToolbox.generateBox(world, x, baseY, z+2, 1, baseH, 1, pillar);
        DungeonToolbox.generateBox(world, x-1, baseY, z-2, 3, 3, 5, air);
        DungeonToolbox.generateBox(world, x-1, baseY, z-1, 1, baseH, 3, ladderW);
        DungeonToolbox.generateBox(world, x, baseY, z-1, 1, baseH, 3, air);
        DungeonToolbox.generateBox(world, x+1, baseY, z-1, 1, baseH, 3, ladderE);

        DungeonToolbox.generateBox(world, x-1, h+2, z-2, 3, 5, 1, air);
        DungeonToolbox.generateBox(world, x-1, h+2, z+2, 3, 5, 1, air);
        world.setBlockState(new BlockPos(x, h+1, z+2), plating, 3);
        world.setBlockState(new BlockPos(x, h+1, z-2), plating, 3);
        world.setBlockState(new BlockPos(x, y+5, z+2), plating, 3);
        world.setBlockState(new BlockPos(x, y+5, z-2), plating, 3);
    }
}