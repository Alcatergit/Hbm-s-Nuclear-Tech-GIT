package com.hbm.blocks.network.energy;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.tileentity.network.energy.TileEntityPylonBase;

import net.minecraft.item.ItemStack;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.block.material.Material;
import net.minecraft.block.BlockContainer;

public abstract class PylonBase extends BlockContainer implements ITooltipProvider
{
    protected PylonBase(Material materialIn, String s) {
        super(materialIn);
        this.setUnlocalizedName(s);
        this.setRegistryName(s);
        ModBlocks.ALL_BLOCKS.add(this);
    }
    
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te != null && te instanceof TileEntityPylonBase) {
            ((TileEntityPylonBase)te).disconnectAll();
        }
        super.breakBlock(world, pos, state);
    }
    
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }
    
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }
    
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }
    
    public boolean isNormalCube(IBlockState state) {
        return false;
    }
    
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        return false;
    }
    
    public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
        this.addStandardInfo((List)list);
        super.addInformation(stack, worldIn, (List)list, flagIn);
    }
}