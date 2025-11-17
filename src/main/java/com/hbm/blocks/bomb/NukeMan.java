package com.hbm.blocks.bomb;

import java.util.List;

import com.hbm.util.I18nUtil;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.items.ModItems;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class NukeMan extends BlockNukeBase {

    public static final PropertyInteger FACING = PropertyInteger.create("facing", 2, 5);

    private static boolean keepInventory = false;

    public NukeMan(Material materialIn, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        this.setCreativeTab(MainRegistry.nukeTab);

        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityNukeMan();
    }

    @Override
    protected Item getBlockItem() {
        return Item.getItemFromBlock(ModBlocks.nuke_man);
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityNukeMan.class;
    }

    @Override
    protected void onNonNBTSavingBreak(World world, BlockPos pos, TileEntity te) {
        if (!keepInventory) {
            InventoryHelper.dropInventoryItems(world, pos, te);
            world.updateComparatorOutputLevel(pos, this);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        int i = MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

        if(i == 0)
        {
            return this.getDefaultState().withProperty(FACING, 5);
        }
        if(i == 1)
        {
            return this.getDefaultState().withProperty(FACING, 3);
        }
        if(i == 2)
        {
            return this.getDefaultState().withProperty(FACING, 4);
        }
        return this.getDefaultState().withProperty(FACING, 2);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
        {
            return true;
        } else if(!player.isSneaking())
        {
            TileEntityNukeMan entity = (TileEntityNukeMan) world.getTileEntity(pos);
            if(entity != null)
            {
                player.openGui(MainRegistry.instance, ModBlocks.guiID_nuke_man, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntityNukeMan entity = (TileEntityNukeMan) world.getTileEntity(pos);
        if (world.getRedstonePowerFromNeighbors(pos) > 0 && !world.isRemote)
        {

            if(entity.isReady())
            {
                // ========== Modified: Set detonation flag, then clear the block ==========
                this.isExploding = true;
                entity.clearSlots();
                world.setBlockToAir(pos);

                igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ());
                this.isExploding = false;
            }
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


    public boolean igniteTestBomb(World world, int x, int y, int z)
    {
        if (!world.isRemote) {

            if(world.getTileEntity(new BlockPos(x, y, z)) instanceof TileEntityNukeMan)
                ((TileEntityNukeMan)world.getTileEntity(new BlockPos(x, y, z))).clearSlots();
            world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 1.0f, world.rand.nextFloat() * 0.1F + 0.9F);

            world.spawnEntity(EntityNukeExplosionMK5.statFac(world, BombConfig.manRadius, x + 0.5, y + 0.5, z + 0.5));
            if (BombConfig.enableNukeClouds) {
                EntityNukeTorex.statFac(world, x + 0.5, y + 0.5, z + 0.5, BombConfig.manRadius);
            }
        }

        return false;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING);
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        if(meta >= 2 && meta <=5)
            return this.getDefaultState().withProperty(FACING, meta);
        return this.getDefaultState().withProperty(FACING, 2);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[]{FACING});
    }

    @Override
    public void explode(World world, BlockPos pos) {
        if(!(world.getTileEntity(pos) instanceof TileEntityNukeMan))
            return;
        TileEntityNukeMan entity = (TileEntityNukeMan) world.getTileEntity(pos);
        //if (p_149695_1_.getStrongPower(x, y, z))
        {
            if(entity.isReady())
            {
                // ========== Modified: Set detonation flag, then clear the block ==========
                this.isExploding = true;
                entity.clearSlots();
                world.setBlockToAir(pos);

                igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ());
                this.isExploding = false;
            }
        }
    }

    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        tooltip.add("§2["+ I18nUtil.resolveKey("trait.nuclearbomb")+"]"+"§r");
        tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", BombConfig.manRadius)+"§r");
        if(!BombConfig.disableNuclear){
            tooltip.add("§2["+ I18nUtil.resolveKey("trait.fallout")+"]"+"§r");
            tooltip.add(" §e"+I18nUtil.resolveKey("desc.radius", (int)(BombConfig.manRadius*(1+BombConfig.falloutRange/100.0)))+"§r");
        }

        // ========== Modified: Use logic matching entity.isReady for the check ==========
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

                // Check if it contains the Items tag (the serialization format of ItemStackHandler).
                if (inventoryTag.hasKey("Items")) {
                    NBTTagList itemsList = inventoryTag.getTagList("Items", 10);

                    // Check if the required items are present.
                    boolean hasIgniter = false;
                    boolean hasCore = false;
                    boolean hasExplosive1 = false;
                    boolean hasExplosive2 = false;
                    boolean hasExplosive3 = false;
                    boolean hasExplosive4 = false;
                    
                    for (int i = 0; i < itemsList.tagCount(); i++) {
                        NBTTagCompound itemTag = itemsList.getCompoundTagAt(i);
                        int slot = itemTag.getByte("Slot");

                        // Check item ID (using registry name instead of string ID).
                        String itemId = itemTag.getString("id");

                        // Check corresponding items based on slot.
                        if (slot == 0 && itemId.equals(ModItems.man_igniter.getRegistryName().toString())) {
                            hasIgniter = true;
                        } else if (slot == 5 && itemId.equals(ModItems.man_core.getRegistryName().toString())) {
                            hasCore = true;
                        } else if (slot == 1 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
                            hasExplosive1 = true;
                        } else if (slot == 2 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
                            hasExplosive2 = true;
                        } else if (slot == 3 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
                            hasExplosive3 = true;
                        } else if (slot == 4 && itemId.equals(ModItems.gadget_explosive8.getRegistryName().toString())) {
                            hasExplosive4 = true;
                        }
                    }

                    // Return whether the isReady condition is met.
                    return hasIgniter && hasCore && hasExplosive1 && hasExplosive2 && hasExplosive3 && hasExplosive4;
                }
            }
        }
        
        return false;
    }
    // ========== End of modification ==========
}
