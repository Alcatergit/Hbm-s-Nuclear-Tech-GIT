package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.tileentity.bomb.TileEntityNukeBalefire;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;

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
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeBalefire extends BlockNukeBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public NukeBalefire(Material materialIn, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityNukeBalefire();
    }

    @Override
    protected Item getBlockItem() {
        return Item.getItemFromBlock(ModBlocks.nuke_fstbmb);
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityNukeBalefire.class;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote) {
            return true;
        } else if(!player.isSneaking()) {
            TileEntityNukeBalefire entity = (TileEntityNukeBalefire) world.getTileEntity(pos);
            if(entity != null)
            {
                player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_fstbmb, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        } else {
            return false;
        }
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
                // ========== Modified: Set detonation flag, then clear the block ==========
                this.isExploding = true;
                bomb.explode();
                this.isExploding = false;
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("§a["+ I18nUtil.resolveKey("trait.balefirebomb")+"]"+"§r");
        tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", 250)+"§r");

        // ========== Modified: Check using isItemReady method ==========
        if (isItemReady(stack)) {
            tooltip.add("§2[Is ready]§r");
        }
    }

    // ========== Modified: Correctly parse the NBT format of ItemStackHandler ==========
    private boolean isItemReady(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
            NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

            if (blockEntityTag.hasKey("inventory")) {
                NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");

                // Check if it contains the Items tag (the serialization format of ItemStackHandler)
                if (inventoryTag.hasKey("Items")) {
                    NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

                    // Check if the required items are present (based on TileEntityNukeBalefire's isLoaded logic)
                    boolean hasEgg = false;
                    boolean hasBattery = false;

                    for (int i = 0; i < itemsList.tagCount(); i++) {
                        NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
                        int slot = itemTag.getByte("Slot");

                        // Check item ID (using registry name instead of string ID)
                        String itemId = itemTag.getString("id");

                        // Check corresponding items based on slot
                        if (slot == 0 && itemId.equals(ModItems.egg_balefire.getRegistryName().toString())) {
                            hasEgg = true;
                        } else if (slot == 1) {
                            // Check if the battery is fully charged
                            if (itemId.equals(ModItems.battery_spark.getRegistryName().toString()) ||
                                    itemId.equals(ModItems.battery_trixite.getRegistryName().toString())) {
                                hasBattery = true;
                            }
                        }
                    }

                    // Return whether the isLoaded condition is met (requires egg and battery)
                    return hasEgg && hasBattery;
                }
            }
        }

        return false;
    }
    // ========== End of modification ==========
}
