package net.minecraft.block;

import com.hbm.main.MainRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class BlockOldLeafNTM extends BlockOldLeaf{

    public BlockOldLeafNTM(){
        super();
        MainRegistry.logger.log(Level.INFO, "Vanilla BlockOldLeaf overwritten with patched version for crash fix");
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        if (state.getBlock() instanceof BlockOldLeaf && state.getValue(VARIANT) == BlockPlanks.EnumType.OAK && worldIn.rand.nextInt(chance) == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
        }
    }

    @Override
    protected int getSaplingDropChance(IBlockState state) {
        return state.getBlock() instanceof BlockOldLeaf && state.getValue(VARIANT) == BlockPlanks.EnumType.JUNGLE ? 30 : 20;
    }
}