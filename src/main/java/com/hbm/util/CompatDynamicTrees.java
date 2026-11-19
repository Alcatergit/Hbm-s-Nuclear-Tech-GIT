package com.hbm.util;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * EX does not ship the Dynamic Trees optional dependency. These are no-op stubs so that the ported
 * executor and {@link ChunkUtil} can keep the community edition's call sites without a hard dependency.
 */
public final class CompatDynamicTrees {

    private CompatDynamicTrees() {
    }

    public static boolean isTreePart(Block block) {
        return false;
    }

    public static void destroyOrphanedNeighbors(World world, BlockPos pos) {
        // no-op
    }

    public static boolean destroyTreeAt(World world, BlockPos pos) {
        return false;
    }
}
