package com.hbm.items.tool;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemDesignatorManual extends ItemDesignator {

    public ItemDesignatorManual(String s) {
        super(s);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (stack.getTagCompound() != null) {
            tooltip.add(TextFormatting.GREEN + I18nUtil.resolveKey("desc.targetcoord") + "§r");
            tooltip.add("§aX: " + String.valueOf(stack.getTagCompound().getInteger("xCoord")) + "§r");
            tooltip.add("§aZ: " + String.valueOf(stack.getTagCompound().getInteger("zCoord")) + "§r");
        } else {
            tooltip.add(TextFormatting.YELLOW + I18nUtil.resolveKey("desc.choosetarget2"));
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (worldIn.isRemote)
            playerIn.openGui(MainRegistry.instance, ModItems.guiID_item_designator, worldIn, handIn == EnumHand.MAIN_HAND ? 1 : 0, 0, 0);
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }
}
