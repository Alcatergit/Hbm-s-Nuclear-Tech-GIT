package com.hbm.world.generator;

import com.hbm.blocks.ModBlocks;
import com.hbm.world.generator.room.VaultDungeonRoom;
import net.minecraft.world.World;

import java.util.Random;

public class VaultDungeon extends CellularDungeon {

    public boolean hasElevator = false;
    public int eX, eZ;
    public VaultDungeonRoom elevatorRoom;

    public VaultDungeon(int width, int height, int dimX, int dimZ, int tries, int branches) {
		super(width, height, dimX, dimZ, tries, branches);

		this.floor.add(ModBlocks.brick_compound.getDefaultState());
		this.wall.add(ModBlocks.concrete_smooth.getDefaultState());
		this.ceiling.add(ModBlocks.ducrete.getDefaultState());
	}

    @Override
    public void generate(final World world, final int x, final int y, final int z, final Random rand) {
        super.generate(world, x, y, z, rand);
        hasElevator = false;
        elevatorRoom = null;
    }
}
