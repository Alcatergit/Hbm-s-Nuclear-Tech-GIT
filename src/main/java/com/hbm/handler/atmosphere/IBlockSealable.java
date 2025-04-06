package com.hbm.handler.atmosphere;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.handler.ThreeInts;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IBlockSealable {
    
    public boolean isSealed(World world, int x, int y, int z);

    public default void updateSealedState(World world, int x, int y, int z) {
        ThreeInts pos = new ThreeInts(x, y, z);
        List<AtmosphereBlob> nearbyBlobs = ChunkAtmosphereManager.proxy.getBlobsWithinRadius(world, pos, 256);

        if(this instanceof BlockDummyable) {
            BlockDummyable block = (BlockDummyable)this;
            int meta = world.getBlockState(new BlockPos(x, y, z)).getBlock().getMetaFromState(world.getBlockState(new BlockPos(x, y, z)));

            int[] rot = MultiblockHandlerXR.rotate(block.getDimensions(), EnumFacing.byIndex(meta - BlockDummyable.offset));
    
            for(int a = x - rot[4]; a <= x + rot[5]; a++) {
                for(int b = y - rot[1]; b <= y + rot[0]; b++) {
                    for(int c = z - rot[2]; c <= z + rot[3]; c++) {
                        ChunkAtmosphereManager.proxy.onSealableChange(world, new ThreeInts(a, b, c), (IBlockSealable)this, nearbyBlobs);
                    }
                }
            }
        } else {
            ChunkAtmosphereManager.proxy.onSealableChange(world, pos, (IBlockSealable)this, nearbyBlobs);
        }
    }

}
