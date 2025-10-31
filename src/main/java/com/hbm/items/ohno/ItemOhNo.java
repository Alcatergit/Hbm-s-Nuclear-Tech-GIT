package com.hbm.items.ohno;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.config.GeneralConfig;
import com.hbm.items.special.ItemHazard;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemOhNo extends ItemHazard {
    public ItemOhNo(String s) {super(s);}

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem){
        if(entityItem != null && !entityItem.world.isRemote && entityItem.onGround) {
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
            for(int i = 0; i < 85; i++) {
                int a = itemRand.nextInt(7) + (int)entityItem.posX - 3;
                int b = itemRand.nextInt(7) + (int)entityItem.posY - 3;
                int c = itemRand.nextInt(7) + (int)entityItem.posZ - 3;
                pos.setPos(a, b, c);
                if(entityItem.world.getBlockState(pos).getBlock().isReplaceable(entityItem.world, pos) && BlockTaint.hasPosNeightbour(entityItem.world, pos)) {

                    if(GeneralConfig.enableHardcoreTaint)
                        entityItem.world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, itemRand.nextInt(6) + 10), 2);
                    else
                        entityItem.world.setBlockState(pos, ModBlocks.taint.getBlockState().getBaseState().withProperty(BlockTaint.TEXTURE, itemRand.nextInt(3) + 4), 2);
                }
            }
            entityItem.setDead();
            return true;
        }
        return false;
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> list, ITooltipFlag flagIn){
        super.addInformation(stack, world, list, flagIn);
        list.add(TextFormatting.RED + "[" + I18nUtil.resolveKey("trait.drop") + "]");
    }
}
