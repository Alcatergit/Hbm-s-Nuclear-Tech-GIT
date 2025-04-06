package com.hbm.items.tool;

import com.hbm.blocks.BlockDummyable;
import com.hbm.items.ItemBase;
import com.hbm.lib.Library;
import com.hbm.tileentity.turret.TileEntityTurretBaseArtillery;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ItemDesignatorArtyRange extends ItemBase {

    public ItemDesignatorArtyRange(String s) {
        super(s);
        this.setFull3D();
        this.setMaxStackSize(1);
    }

    @Override
        public void addInformation(ItemStack itemStack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        if(itemStack.getTagCompound() == null) {
            tooltip.add(TextFormatting.RED + "No turret linked!");
        } else {
            tooltip.add(TextFormatting.YELLOW + "Linked to " + itemStack.getTagCompound().getInteger("x") + ", " + itemStack.getTagCompound().getInteger("y") + ", " + itemStack.getTagCompound().getInteger("z"));
        }
    }

    @Override
        public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos clickPos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = player.getHeldItem(hand);
        Block b = world.getBlockState(clickPos).getBlock();

        if(b instanceof BlockDummyable) {
            int pos[] = ((BlockDummyable) b).findCore(world, clickPos.getX() , clickPos.getY() , clickPos.getZ()); //TODO: Make this run on blockPos

            if(pos == null)
                return EnumActionResult.FAIL;

            TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));

            if(te instanceof TileEntityTurretBaseArtillery) {

                if(world.isRemote)
                    return EnumActionResult.SUCCESS;

                if(!stack.hasTagCompound())
                    stack.setTagCompound(new NBTTagCompound());

                stack.getTagCompound().setInteger("x", pos[0]);
                stack.getTagCompound().setInteger("y", pos[1]);
                stack.getTagCompound().setInteger("z", pos[2]);
                player.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("hbm:item.techBleep")), 1.0F, 1.0F);
                return EnumActionResult.SUCCESS;
            }
        }

        return EnumActionResult.FAIL;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

        ItemStack stack = player.getHeldItem(hand);
        if(!stack.hasTagCompound())
            return new ActionResult(EnumActionResult.FAIL, stack);

        BlockPos pos = Library.rayTrace(player, 500, 1).getBlockPos();

        if(!world.isRemote) {
            TileEntity te = world.getTileEntity( new BlockPos(stack.getTagCompound().getInteger("x"), stack.getTagCompound().getInteger("y"), stack.getTagCompound().getInteger("z")));

            if(te instanceof TileEntityTurretBaseArtillery) {
                TileEntityTurretBaseArtillery arty = (TileEntityTurretBaseArtillery) te;
                arty.enqueueTarget( pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);
                player.playSound(SoundEvent.REGISTRY.getObject(new ResourceLocation("hbm:item.techBleep")), 1.0F, 1.0F);
            }
        }

        return new ActionResult(EnumActionResult.SUCCESS, stack);
    }
}
