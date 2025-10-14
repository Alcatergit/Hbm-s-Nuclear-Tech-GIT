package com.hbm.interfaces;

import com.hbm.util.I18nUtil;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public interface IRadResistantBlock {

	//Anything implementing this must override onBlockAdded and breakBlock and call RadiationSystemNT.markChunkForRebuild or it won't work

    default boolean isRadResistant(){
        return true;
    }

	default boolean isRadResistant(World worldIn, BlockPos blockPos){
		return true;
	}


    static void addShieldInfo(ItemStack stack, List<String> tooltip, ITooltipFlag advanced) {
        Block b = Block.getBlockFromItem(stack.getItem());
        if(b == Blocks.AIR) return;
        float hardness = b.getExplosionResistance(null);
        if(hardness >= 3_600_000 || (b instanceof IRadResistantBlock bRad && bRad.isRadResistant())) {
            tooltip.add("§2[" + I18nUtil.resolveKey("trait.radshield") + "]");
        }
        if(hardness > 50 || advanced.isAdvanced()){
            tooltip.add("§6" + I18nUtil.resolveKey("trait.blastres", hardness));
        }
    }
}
