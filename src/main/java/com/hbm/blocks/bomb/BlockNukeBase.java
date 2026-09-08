package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.lib.InventoryHelper;
import com.hbm.interfaces.IBomb;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockNukeBase extends BlockContainer implements IBomb {

    protected boolean isExploding = false;
    private boolean dropItems = false;
    private boolean brokenByPlayer = false;

    public BlockNukeBase(Material materialIn) {
        super(materialIn);
    }

    protected abstract Item getBlockItem();
    protected abstract Class<? extends TileEntity> getTileEntityClass();

    protected void onNonNBTSavingBreak(World world, BlockPos pos, TileEntity te) {
        InventoryHelper.dropInventoryItems(world, pos, te);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (isExploding) {
            return null;
        }
        if (GeneralConfig.enableBlockItemNBTSaving) {
            return null;
        }
        return getBlockItem();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        if (!brokenByPlayer) {
            dropItems = true;
        }
        brokenByPlayer = false;
        if (!isExploding && GeneralConfig.enableBlockItemNBTSaving) {
            return;
        }
        super.getDrops(drops, world, pos, state, fortune);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        brokenByPlayer = true;
        if (!player.capabilities.isCreativeMode) {
            dropItems = true;
        }
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        boolean shouldDrop = dropItems;
        boolean isCreativeBreak = brokenByPlayer && !shouldDrop;
        dropItems = false;
        if (isCreativeBreak) {
            brokenByPlayer = false;
        }

        if (isExploding) {
            TileEntity te = world.getTileEntity(pos);
            if (te != null) {
                world.removeTileEntity(pos);
            }
            return;
        }

        TileEntity te = world.getTileEntity(pos);

        if (!GeneralConfig.enableBlockItemNBTSaving) {
            if (te != null && getTileEntityClass().isInstance(te) && (shouldDrop || isCreativeBreak)) {
                onNonNBTSavingBreak(world, pos, te);
            }
        } else {
            if (te != null && getTileEntityClass().isInstance(te)) {
                NBTTagCompound tileData = new NBTTagCompound();
                te.writeToNBT(tileData);

                boolean hasItems = false;
                if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
                    NBTTagList itemsList = tileData.getCompoundTag("inventory").getTagList("Items", 10);
                    hasItems = itemsList.tagCount() > 0;
                }

                if (hasItems && (shouldDrop || isCreativeBreak)) {
                    ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this), 1);
                    NBTTagCompound nbttagcompound = new NBTTagCompound();
                    NBTTagCompound blockEntityTag = new NBTTagCompound();
                    NBTTagCompound inventoryTag = new NBTTagCompound();

                    if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
                        NBTTagList items = tileData.getCompoundTag("inventory").getTagList("Items", 10).copy();
                        inventoryTag.setTag("Items", items);
                    }

                    blockEntityTag.setTag("inventory", inventoryTag);
                    nbttagcompound.setTag("BlockEntityTag", blockEntityTag);
                    itemstack.setTagCompound(nbttagcompound);

                    spawnAsEntity(world, pos, itemstack);
                } else if (shouldDrop) {
                    spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
                }
            }
        }
        super.breakBlock(world, pos, state);
    }
}