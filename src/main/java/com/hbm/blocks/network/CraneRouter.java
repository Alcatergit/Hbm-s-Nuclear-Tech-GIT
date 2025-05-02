package com.hbm.blocks.network;

import api.hbm.block.IConveyorBelt;
import api.hbm.block.IConveyorItem;
import api.hbm.block.IConveyorPackage;
import api.hbm.block.IEnterableBlock;
import com.hbm.items.tool.ItemTooling;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.entity.item.EntityMovingItem;
import com.hbm.modules.ModulePatternMatcher;
import com.hbm.tileentity.network.TileEntityCraneRouter;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class CraneRouter extends BlockContainer implements IEnterableBlock {
    public CraneRouter(Material materialIn, String s) {
        super(materialIn);
        this.setTranslationKey(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }
    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityCraneRouter();
    }

    @Override
    public boolean canItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) {
        return true;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(playerIn.getHeldItem(hand).getItem() instanceof ItemTooling) {
            return false;
        } else if(worldIn.isRemote) {
            return true;
        } else if(!playerIn.isSneaking()) {
            playerIn.openGui(MainRegistry.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    private static EnumFacing[] customEnumOrder = new EnumFacing[]{
        EnumFacing.NORTH,
        EnumFacing.UP,
        EnumFacing.EAST,
        EnumFacing.SOUTH,
        EnumFacing.DOWN,
        EnumFacing.WEST
    };

    @Override
    public void onItemEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorItem entity) {
        TileEntityCraneRouter router = (TileEntityCraneRouter) world.getTileEntity(new BlockPos(x, y, z));
        ItemStack stack = entity.getItemStack();

        List<EnumFacing> validDirs = new ArrayList<>();

        //check filters for all sides
        for(int i = 0; i<6; i++) {

            ModulePatternMatcher matcher = router.patterns[i];
            int mode = router.modes[i];

            //if the side is disabled or wildcard, skip
            if(mode == router.MODE_NONE || mode == router.MODE_WILDCARD)
                continue;

            boolean matchesFilter = false;

            for(int slot = 0; slot < 5; slot++) {
                ItemStack filter = router.inventory.getStackInSlot(i * 5 + slot);

                if(filter.isEmpty())
                    continue;

                //the filter kicks in so long as one entry matches
                if(matcher.isValidForFilter(filter, slot, stack)) {
                    matchesFilter = true;
                    break;
                }
            }

            //add dir if matches with whitelist on or doesn't match with blacklist on
            if((mode == router.MODE_WHITELIST && matchesFilter) || (mode == router.MODE_BLACKLIST && !matchesFilter)) {
                validDirs.add(customEnumOrder[i]);
            }
        }

        //if no valid dirs have yet been found, use wildcard
        if(validDirs.isEmpty()) {
            for(int i = 0; i<6; i++) {
                if(router.modes[i] == router.MODE_WILDCARD) {
                    validDirs.add(customEnumOrder[i]);
                }
            }
        }

        if(validDirs.isEmpty()) {
            world.spawnEntity(new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack.copy()));
            return;
        }

        int i = world.rand.nextInt(validDirs.size());
        sendOnRoute(world, x, y, z, entity, validDirs.get(i));
    }

    protected void sendOnRoute(World world, int x, int y, int z, IConveyorItem item, EnumFacing dir) {
        IConveyorBelt belt = null;
        BlockPos targetPos = new BlockPos(x + dir.getXOffset(), y + dir.getYOffset(), z + dir.getZOffset());
        Block block = world.getBlockState(targetPos).getBlock();

        if (block instanceof IConveyorBelt) {
            belt = (IConveyorBelt) block;
        }

        if (belt != null) {
            EntityMovingItem moving = new EntityMovingItem(world);
            Vec3d pos = new Vec3d(x + 0.5 + dir.getXOffset() * 0.55, y + 0.5 + dir.getYOffset() * 0.55, z + 0.5 + dir.getZOffset() * 0.55);
            Vec3d snap = belt.getClosestSnappingPosition(world, targetPos, pos);
            moving.setPosition(snap.x, snap.y, snap.z);
            moving.setItemStack(item.getItemStack());
            world.spawnEntity(moving);
        } else {
            world.spawnEntity(new EntityItem(world, x + 0.5 + dir.getXOffset() * 0.55, y + 0.5 + dir.getYOffset() * 0.55, z + 0.5 + dir.getZOffset() * 0.55, item.getItemStack()));
        }
    }

    @Override public boolean canPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity) { return false; }
    @Override public void onPackageEnter(World world, int x, int y, int z, EnumFacing dir, IConveyorPackage entity) { }

}
