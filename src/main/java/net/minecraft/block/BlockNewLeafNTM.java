package net.minecraft.block;

import com.hbm.main.MainRegistry;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.apache.logging.log4j.Level;

public class BlockNewLeafNTM extends BlockNewLeaf{

    public BlockNewLeafNTM(){
        super();
        MainRegistry.logger.log(Level.INFO, "Vanilla BlockNewLeaf overwritten with patched version for crash fix");
    }

    @Override
    protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {
        if (state.getBlock() instanceof BlockNewLeaf && state.getValue(VARIANT) == BlockPlanks.EnumType.DARK_OAK && worldIn.rand.nextInt(chance) == 0) {
            spawnAsEntity(worldIn, pos, new ItemStack(Items.APPLE));
        }
    }
}