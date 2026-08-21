package com.hbm.blocks.bomb;

import java.util.List;
import java.util.Random;

import com.hbm.config.GeneralConfig;
import com.hbm.items.ModItems;
import com.hbm.lib.InventoryHelper;
import com.hbm.util.I18nUtil;
import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.interfaces.IBomb;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.blocks.ModBlocks;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeBalefire extends BlockMachineBase implements IBomb {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    // ========== Added: Field for storing information about players who have been vandalized ==========
    private EntityPlayer lastBreaker = null;
    // ========== Added: Mark whether the destruction was caused by an explosion ==========
    private boolean isExploding = false;

    public NukeBalefire(Material materialIn, int guiID, String s) {
        super(materialIn, guiID, s);
    }

    @Override
    protected boolean rotatable() {
        return true;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityNukeBalefire();
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return ((EnumFacing)state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        if (world.getRedstonePowerFromNeighbors(pos) > 0) {
            explode(world, pos);
        }
    }

    @Override
    public void explode(World world, BlockPos pos) {
        if(!world.isRemote) {
            TileEntityNukeBalefire bomb = (TileEntityNukeBalefire) world.getTileEntity(pos);

            if(bomb.isLoaded()) {
                // ========== Modification: Set a detonation flag, then clear the blocks ==========
                this.isExploding = true;
                bomb.explode();
                this.isExploding = false;
            }
        }
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        // ========== Modification: Adding drop control logic upon detonation ==========
        // If the destruction is caused by an explosion, no items will be dropped.
        if (isExploding) {
            return null;
        }

        // When NBT saving is enabled, the drop is completely controlled by breakBlock.
        if (GeneralConfig.enableBlockItemNBTSaving) {
            return null; // Returning null, the fall is controlled by breakBlock.
        }
        // ========== Modification complete ==========

        return Item.getItemFromBlock(ModBlocks.nuke_fstbmb);
    }

    // ========== Added: Override the removedByPlayer method to get the destroyed player ==========
    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        // Recording and destroying player information
        this.lastBreaker = player;

        // Calling the parent class method to continue executing the disruptive logic
        boolean result = super.removedByPlayer(state, world, pos, player, willHarvest);

        // Clean up player information
        this.lastBreaker = null;

        return result;
    }

    // ========== Added: Initialize block entity state when a block is placed ==========
    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        world.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

        // ========== Recovery from Simplified NBT Data ==========
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity instanceof TileEntityNukeBalefire) {
                NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

                if (blockEntityTag.hasKey("inventory")) {
                    NBTTagCompound savedInventory = blockEntityTag.getCompoundTag("inventory");

                    // Create complete TileEntity NBT data
                    NBTTagCompound tileData = new NBTTagCompound();
                    tileData.setInteger("x", pos.getX());
                    tileData.setInteger("y", pos.getY());
                    tileData.setInteger("z", pos.getZ());
                    tileData.setBoolean("started", false);  // Ensure started is false
                    tileData.setInteger("timer", 18000);    // Ensure the timer is set to 18000.


                    // ========== Restore only Items data in inventory ==========
                    NBTTagCompound newInventory = new NBTTagCompound();
                    if (savedInventory.hasKey("Items")) {
                        newInventory.setTag("Items", savedInventory.getTagList("Items", 10).copy());
                    }

                    tileData.setTag("inventory", newInventory);

                    // Loading data from NBT to block entities
                    ((TileEntityNukeBalefire) tileentity).readFromNBT(tileData);

                    // The marker blocks need to be updated.
                    world.notifyBlockUpdate(pos, state, state, 3);

                    // Important: Mark block entities as dirty data to ensure data preservation.
                    tileentity.markDirty();
                }
            }
        }
    }
    // ========== Modification complete ==========

    // ========== Modification: Using player information recorded by removedByPlayer ==========
    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        // ========== If the destruction is caused by an explosion, the entire block will be cleared, and no items will be dropped. ==========
        if (isExploding) {
            TileEntity tileentity = world.getTileEntity(pos);
            if (tileentity != null) {
                world.removeTileEntity(pos);
            }
            return;
        }

        TileEntity tileentity = world.getTileEntity(pos);

        // ========== New Logic: Configuration File Controls NBT Saving ==========
        if (!GeneralConfig.enableBlockItemNBTSaving) {
            // Configure NBT saving is disabled; use the original logic.
            InventoryHelper.dropInventoryItems(world, pos, world.getTileEntity(pos));
        } else {
            if (tileentity instanceof TileEntityNukeBalefire) {
                TileEntityNukeBalefire nukeBalefire = (TileEntityNukeBalefire)tileentity;

                // Create NBT tags to store block entity data
                NBTTagCompound tileData = new NBTTagCompound();
                nukeBalefire.writeToNBT(tileData);

                // ========== Check for any items inside ==========
                boolean hasItems = false;
                if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
                    NBTTagList itemsList = tileData.getCompoundTag("inventory").getTagList("Items", 10);
                    hasItems = itemsList.tagCount() > 0;
                }

                // ========== Player information recorded using removedByPlayer ==========
                boolean isCreativeMode = (lastBreaker != null && lastBreaker.capabilities.isCreativeMode);

                if (hasItems) {
                    // ========== Internal Items Found: Drops a bomb containing NBT ==========
                    ItemStack itemstack = new ItemStack(Item.getItemFromBlock(this), 1);
                    NBTTagCompound nbttagcompound = new NBTTagCompound();

                    // ========== Simplify NBT data: Only keep BlockEntityTag->inventory->Items ==========
                    NBTTagCompound blockEntityTag = new NBTTagCompound();
                    NBTTagCompound inventoryTag = new NBTTagCompound();

                    if (tileData.hasKey("inventory") && tileData.getCompoundTag("inventory").hasKey("Items")) {
                        // Copy only the Items tag and all its child tags.
                        NBTTagList items = tileData.getCompoundTag("inventory").getTagList("Items", 10).copy();
                        inventoryTag.setTag("Items", items);
                    }

                    blockEntityTag.setTag("inventory", inventoryTag);

                    // Write the BlockEntityTag to the item NBT
                    nbttagcompound.setTag("BlockEntityTag", blockEntityTag);
                    itemstack.setTagCompound(nbttagcompound);

                    // Generate drops
                    spawnAsEntity(world, pos, itemstack);

                    // ========== Modification: Directly empty the item slots instead of using the clearSlots method ==========
                    for(int i = 0; i < nukeBalefire.inventory.getSlots(); i++) {
                        nukeBalefire.inventory.setStackInSlot(i, ItemStack.EMPTY);
                    }
                } else if (!isCreativeMode) {
                    // ========== Survival Mode Empty bomb: Drops a regular bomb ==========
                    spawnAsEntity(world, pos, new ItemStack(Item.getItemFromBlock(this), 1));
                }
                // Creative Mode Empty Bomb: Nothing drops
            }
        }
        // Call the parent class's breakBlock but don't let it handle the falling object.
        super.breakBlock(world, pos, state);
    }
    // ========== Modification complete ==========

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("§a["+ I18nUtil.resolveKey("trait.balefirebomb")+"]"+"§r");
        tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", 250)+"§r");

        // ========== Modification begins: Use the isItemReady method for judgment ==========
        if (isItemReady(stack)) {
            tooltip.add("§2[Is ready]§r");
        }
    }

    // ========== Modification begins: Correctly parsing the NBT format of ItemStackHandler ==========
    private boolean isItemReady(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
            NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

            if (blockEntityTag.hasKey("inventory")) {
                NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

                // Check if it contains the Items tag (the serialization format of ItemStackHandler).
                if (inventoryTag.hasKey("Items")) {
                    NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

                    // Check if the required items are included (based on the isLoaded logic of TileEntityNukeBalefire).
                    boolean hasEgg = false;
                    boolean hasBattery = false;

                    for (int i = 0; i < itemsList.tagCount(); i++) {
                        NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
                        int slot = itemTag.getByte("Slot");

                        // Check the item ID (use the registered name instead of the string ID).
                        String itemId = itemTag.getString("id");

                        // Check the corresponding items according to the slot.
                        if (slot == 0 && itemId.equals(ModItems.egg_balefire.getRegistryName().toString())) {
                            hasEgg = true;
                        } else if (slot == 1) {
                            // Check if the battery is fully charged.
                            if (itemId.equals(ModItems.battery_spark.getRegistryName().toString()) ||
                                    itemId.equals(ModItems.battery_trixite.getRegistryName().toString())) {
                                hasBattery = true;
                            }
                        }
                    }

                    // Returns whether the isLoaded condition is met (requires an egg and a battery).
                    return hasEgg && hasBattery;
                }
            }
        }

        return false;
    }
    // ========== Modification complete ==========
}
