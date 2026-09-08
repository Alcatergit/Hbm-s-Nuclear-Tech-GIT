package com.hbm.blocks.bomb;

import com.hbm.blocks.ModBlocks;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityBombMulti;

import net.minecraft.block.Block;
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

public class BombMulti extends BlockNukeBase {

    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final AxisAlignedBB MULTI_BB = new AxisAlignedBB(0.0, 0.0, 0.0, 1.0, 0.5, 1.0);

    public final float explosionBaseValue = 8.0F;
    public float explosionValue = 0.0F;
    public int clusterCount = 0;
    public int fireRadius = 0;
    public int poisonRadius = 0;
    public int gasCloud = 0;

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
    protected Item getBlockItem() {
        return Item.getItemFromBlock(ModBlocks.bomb_multi);
    }

    @Override
    protected Class<? extends TileEntity> getTileEntityClass() {
        return TileEntityBombMulti.class;
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
                // ========== Modified: Set detonation flag, then clear the block ==========
                this.isExploding = true;
                this.onPlayerDestroy(worldIn, pos, state);
                igniteTestBomb(worldIn, pos.getX(), pos.getY(), pos.getZ());
                this.isExploding = false;
            }
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

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
            // ========== Modified: Set detonation flag, then clear the block ==========
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

                // Load data from NBT into the temporary entity
                tempEntity.readFromNBT(tileData);
            }
        }

        // Check if assembly is complete
        if (tempEntity.isLoaded()) {
            int type2 = tempEntity.return2type();
            int type5 = tempEntity.return5type();

            String toolTip = "";

            if (type2 == type5) {
                if (type2 == 1) {
                    // Gunpowder effect - dark green
                    toolTip = "§2[Gunpowder]§r";
                } else if (type2 == 2) {
                    // TNT effect - red
                    toolTip = "§c[TNT]§r";
                } else if (type2 == 3) {
                    // Cluster bomb effect - yellow
                    toolTip = "§e[Cluster]§r";
                } else if (type2 == 4) {
                    // Fire effect - dark red
                    toolTip = "§4[Fire]§r";
                } else if (type2 == 5) {
                    // Poison effect - green
                    toolTip = "§a[Poison]§r";
                } else if (type2 == 6) {
                    // Gas cloud effect - purple
                    toolTip = "§5[Gas]§r";
                }
            } else {
                String type2ToolTip = "";
                String type5ToolTip = "";

                if (type2 == 3) {
                    // Cluster bomb effect - yellow
                    type2ToolTip = "§e[Cluster &§r";
                } else if (type2 == 4) {
                    // Fire effect - dark red
                    type2ToolTip = "§4[Fire &§r";
                } else if (type2 == 5) {
                    // Poison effect - green
                    type2ToolTip = "§a[Poison &§r";
                } else if (type2 == 6) {
                    // Gas cloud effect - purple
                    type2ToolTip = "§5[Gas &§r";
                } else if (type2 == 1) {
                    // Gunpowder effect - dark green
                    type2ToolTip = "§2[Gunpowder &§r";
                } else if (type2 == 2) {
                    // TNT effect - red
                    type2ToolTip = "§c[TNT &§r";
                }

                if (type5 == 3) {
                    // Cluster bomb effect - yellow
                    type5ToolTip = "§e& Cluster]§r";
                } else if (type5 == 4) {
                    // Fire effect - dark red
                    type5ToolTip = "§4& Fire]§r";
                } else if (type5 == 5) {
                    // Poison effect - green
                    type5ToolTip = "§a& Poison]§r";
                } else if (type5 == 6) {
                    // Gas cloud effect - purple
                    type5ToolTip = "§5& Gas]§r";
                } else if (type5 == 1) {
                    // Gunpowder effect - dark green
                    type5ToolTip = "§2& Gunpowder]§r";
                } else if (type5 == 2) {
                    // TNT effect - red
                    type5ToolTip = "§c& TNT]§r";
                }
                toolTip = type2ToolTip + type5ToolTip;
            }

            // Add tooltip text
            tooltip.add(toolTip);
        }
        // Skip cases where assembly is partial or not assembled
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
