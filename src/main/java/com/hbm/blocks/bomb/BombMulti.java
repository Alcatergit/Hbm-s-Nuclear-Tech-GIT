package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.interfaces.IBomb;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityBombMulti;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.List;

public class BombMulti extends BlockContainer implements IBomb {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final AxisAlignedBB MULTI_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

    public final float explosionBaseValue = 8.0F;
    public float explosionValue = 0.0F;
    public int clusterCount = 0;
    public int fireRadius = 0;
    public int poisonRadius = 0;
    public int gasCloud = 0;

    // ========== Added: Field for storing information about players who have been vandalized ==========
    private EntityPlayer lastBreaker = null;
    // ========== Added: Mark whether the destruction was caused by an explosion ==========
    private boolean isExploding = false;

    public BombMulti(Material materialIn, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);

        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityBombMulti();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(world.isRemote)
        {
            return true;
        } else if(!player.isSneaking())
        {
            TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(pos);
            if(entity != null)
            {
                player.openGui(MainRegistry.instance, ModBlocks.guiID_bomb_multi, world, pos.getX(), pos.getY(), pos.getZ());
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos) {
        TileEntityBombMulti entity = (TileEntityBombMulti) worldIn.getTileEntity(pos);
        if (worldIn.getRedstonePowerFromNeighbors(pos) > 0)
        {
            if(entity.isLoaded())
            {
                // ========== Modification: Set a detonation flag, then clear the blocks ==========
                this.isExploding = true;
                this.onPlayerDestroy(worldIn, pos, state);
                igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ());
                this.isExploding = false;
            }
        }
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

    // ========== Modification: Using player information recorded by removedByPlayer ==========
    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        // ========== If the destruction is caused by an explosion, the entire block will be cleared, and no items will be dropped. ==========
        if (isExploding) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity != null) {
                worldIn.removeTileEntity(pos);
            }
            return;
        }

        TileEntity tileentity = worldIn.getTileEntity(pos);

        // ========== New: Configuration File Controls NBT Saving ==========
        if (!GeneralConfig.enableBlockItemNBTSaving) {
            // Configure NBT saving is disabled; use the original logic.
            InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
        } else {
            // ========== New Logic: Using player information recorded by removedByPlayer ==========
            if (tileentity instanceof TileEntityBombMulti) {
                TileEntityBombMulti bombMulti = (TileEntityBombMulti)tileentity;

                // Create NBT tags to store block entity data
                NBTTagCompound tileData = new NBTTagCompound();
                bombMulti.writeToNBT(tileData);

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
                    spawnAsEntity(worldIn, pos, itemstack);

                    // Empty the contents
                    bombMulti.clearSlots();
                } else if (!isCreativeMode) {
                    // ========== Survival Mode Empty bomb: Drops a regular bomb ==========
                    spawnAsEntity(worldIn, pos, new ItemStack(Item.getItemFromBlock(this), 1));
                }
                // Creative Mode Empty Bomb: Nothing drops
            }
        }
        // Call the parent class's breakBlock but don't let it handle the falling object.
        super.breakBlock(worldIn, pos, state);
    }
    // ========== Modification complete ==========

    // ========== Modification: onBlockPlacedBy method - Support for data recovery from simplified NBT ==========
    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()));

        // ========== Modification: Recovering from Simplified NBT Data ==========
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            if (tileentity instanceof TileEntityBombMulti) {
                NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");

                if (blockEntityTag.hasKey("inventory")) {
                    NBTTagCompound savedInventory = blockEntityTag.getCompoundTag("inventory");

                    // Create complete TileEntity NBT data
                    NBTTagCompound tileData = new NBTTagCompound();
                    tileData.setInteger("x", pos.getX());
                    tileData.setInteger("y", pos.getY());
                    tileData.setInteger("z", pos.getZ());

                    // ========== Restore only Items data in inventory ==========
                    NBTTagCompound newInventory = new NBTTagCompound();
                    if (savedInventory.hasKey("Items")) {
                        newInventory.setTag("Items", savedInventory.getTagList("Items", 10).copy());
                    }

                    tileData.setTag("inventory", newInventory);

                    // Loading data from NBT to block entities
                    ((TileEntityBombMulti) tileentity).readFromNBT(tileData);

                    // The marker blocks need to be updated.
                    worldIn.notifyBlockUpdate(pos, state, state, 3);

                    // Important: Mark block entities as dirty data to ensure data preservation.
                    tileentity.markDirty();
                }
            }
        }
    }
    // ========== Modification complete ==========

    public boolean igniteTestBomb(World world, int x, int y, int z)
    {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(pos);
        if (!world.isRemote)
        {
            if(entity.isLoaded())
            {
                this.explosionValue = this.explosionBaseValue;
                switch(entity.return2type())
                {
                    case 1:
                        this.explosionValue += 1.0F;
                        break;
                    case 2:
                        this.explosionValue += 4.0F;
                        break;
                    case 3:
                        this.clusterCount += 50;
                        break;
                    case 4:
                        this.fireRadius += 10;
                        break;
                    case 5:
                        this.poisonRadius += 15;
                        break;
                    case 6:
                        this.gasCloud += 50;
                }
                switch(entity.return5type())
                {
                    case 1:
                        this.explosionValue += 1.0F;
                        break;
                    case 2:
                        this.explosionValue += 4.0F;
                        break;
                    case 3:
                        this.clusterCount += 50;
                        break;
                    case 4:
                        this.fireRadius += 10;
                        break;
                    case 5:
                        this.poisonRadius += 15;
                        break;
                    case 6:
                        this.gasCloud += 50;
                }

                entity.clearSlots();
                world.setBlockToAir(pos);
                //world.createExplosion(null, x , y , z , this.explosionValue, true);
                ExplosionLarge.explode(world, x, y, z, explosionValue, true, true, true);
                this.explosionValue = 0;

                if(this.clusterCount > 0)
                {
                    ExplosionChaos.cluster(world, x, y, z, this.clusterCount, 0.5);
                }

                if(this.fireRadius > 0)
                {
                    ExplosionChaos.burn(world, pos, this.fireRadius);
                }

                if(this.poisonRadius > 0)
                {
                    ExplosionNukeGeneric.wasteNoSchrab(world, pos, this.poisonRadius);
                }

                if(this.gasCloud > 0)
                {
                    ExplosionChaos.spawnChlorine(world, x, y, z, this.gasCloud, this.gasCloud / 50, 0);
                }

                this.clusterCount = 0;
                this.fireRadius = 0;
                this.poisonRadius = 0;
                this.gasCloud = 0;


            }
        }
        return false;
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return MULTI_BB;
    }

    @Override
    public void explode(World world, BlockPos pos) {
        TileEntityBombMulti entity = (TileEntityBombMulti) world.getTileEntity(pos);
        if(entity.isLoaded())
        {
            // ========== Modification: Set a detonation flag, then clear the blocks ==========
            this.isExploding = true;
            this.onPlayerDestroy(world, pos, world.getBlockState(pos));
            igniteTestBomb(world, pos.getX(), pos.getY(), pos.getZ());
            this.isExploding = false;
        }
    }

    // ========== Added: Method for adding prompt messages ==========
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        // Create a temporary TileEntityBombMulti instance to check the assembly status.
        TileEntityBombMulti tempEntity = new TileEntityBombMulti();
        
        // Restore the item to a temporary entity from the item NBT.
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("BlockEntityTag")) {
            NBTTagCompound blockEntityTag = stack.getTagCompound().getCompoundTag("BlockEntityTag");
            
            if (blockEntityTag.hasKey("inventory")) {
                NBTTagCompound inventoryTag = blockEntityTag.getCompoundTag("inventory");
                
                // Create complete TileEntity NBT data
                NBTTagCompound tileData = new NBTTagCompound();
                NBTTagCompound newInventory = new NBTTagCompound();
                
                if (inventoryTag.hasKey("Items")) {
                    newInventory.setTag("Items", inventoryTag.getTagList("Items", 10).copy());
                }
                
                tileData.setTag("inventory", newInventory);
                
                // Loading data from NBT to a temporary entity
                tempEntity.readFromNBT(tileData);
            }
        }
        
        // Check if assembly is complete.
        if (tempEntity.isLoaded()) {
            // Get the effect type and determine the color
            int type2 = tempEntity.return2type();
            int type5 = tempEntity.return5type();
            
            String colorCode = "§2"; // Default dark green

            // Determine the effect type based on priority.
            if (type2 == 3 || type5 == 3) {
                // Cluster bomb effect - yellow
                colorCode = "§e";
            } else if (type2 == 4 || type5 == 4) {
                // Flame effect - Crimson
                colorCode = "§4";
            } else if (type2 == 5 || type5 == 5) {
                // Poison Effect - Green
                colorCode = "§a";
            } else if (type2 == 6 || type5 == 6) {
                // Gas cloud effect - purple
                colorCode = "§5";
            } else if (type2 == 1 || type5 == 1) {
                // Gunpowder effect - dark green
                colorCode = "§2";
            } else if (type2 == 2 || type5 == 2) {
                // TNT Effect - Red
                colorCode = "§c";
            }
            
            // Display prompt text
            tooltip.add(colorCode + "[Is ready]§r");
        }
        // Remove partially assembled and unassembled cases
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
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
    {
        return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
    }

}
