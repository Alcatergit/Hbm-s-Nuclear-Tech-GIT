package com.hbm.blocks.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemDesignator;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.leafia.contents.machines.powercores.dfc.DFCBaseTE;
import com.leafia.contents.machines.powercores.dfc.creativeemitter.TileEntityCoreCreativeEmitter;
import com.leafia.contents.machines.powercores.dfc.exchanger.DFCExchangerTE;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class CoreComponent extends BlockContainer {

    public static final PropertyDirection FACING = BlockDirectional.FACING;

    public CoreComponent(Material materialIn, String s) {
        super(materialIn);
		this.setTranslationKey(s);
        this.setRegistryName(s);

        ModBlocks.ALL_BLOCKS.add(this);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World player, List<String> tooltip, ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        if (this == ModBlocks.dfc_receiver || this == ModBlocks.dfc_reinforced)
            MachineTooltip.addGenerator(tooltip);
        else if (this == ModBlocks.dfc_exchanger)
            MachineTooltip.addBoiler(tooltip);
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        if (this == ModBlocks.dfc_emitter)
            return new TileEntityCoreEmitter();
        if (this == ModBlocks.dfc_cemitter)
            return new TileEntityCoreCreativeEmitter();
        if (this == ModBlocks.dfc_receiver)
            return new TileEntityCoreReceiver();
        if (this == ModBlocks.dfc_injector)
            return new TileEntityCoreInjector();
        if (this == ModBlocks.dfc_stabilizer)
            return new TileEntityCoreStabilizer();
        if (this == ModBlocks.dfc_reinforced)
            return new TileEntityCoreReceiver();
        if (this == ModBlocks.dfc_exchanger)
            return new DFCExchangerTE();

        return null;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te0 = worldIn.getTileEntity(pos);
        if (te0 instanceof DFCBaseTE) {
            DFCBaseTE te = (DFCBaseTE) te0;
            te.setTargetPosition(pos.offset(EnumFacing.getDirectionFromEntityLiving(pos, placer)));
        }
        //worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer)));
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        } else if (!player.isSneaking()) {
            if (!player.getHeldItem(hand).isEmpty()) {
                TileEntity te = world.getTileEntity(pos);
                NBTTagCompound nbt = player.getHeldItem(hand).getTagCompound();
                if (player.getHeldItem(hand).getItem() instanceof ItemDesignator && te instanceof DFCBaseTE && nbt != null) {
                    BlockPos target =
                            new BlockPos(nbt.getInteger("xCoord"), nbt.getInteger("yCoord"), nbt.getInteger("zCoord"));
                    if (target.equals(pos)) {
                        world.playSound(null, pos, HBMSoundEvents.buttonNo, SoundCategory.BLOCKS, 1, 1);
                        return true;
                    }
                    ((DFCBaseTE) te).setTargetPosition(target);
                    world.playSound(null, pos, HBMSoundEvents.buttonYes, SoundCategory.BLOCKS, 1, 1);
                    return true;
                }
            }

            if (this == ModBlocks.dfc_emitter)
                player.openGui(MainRegistry.instance, ModBlocks.guiID_dfc_emitter, world, pos.getX(), pos.getY(), pos.getZ());

            if (this == ModBlocks.dfc_receiver || this == ModBlocks.dfc_reinforced)

                player.openGui(MainRegistry.instance, ModBlocks.guiID_dfc_receiver, world, pos.getX(), pos.getY(), pos.getZ());

            if (this == ModBlocks.dfc_injector)

                player.openGui(MainRegistry.instance, ModBlocks.guiID_dfc_injector, world, pos.getX(), pos.getY(), pos.getZ());

            if (this == ModBlocks.dfc_stabilizer)

                player.openGui(MainRegistry.instance, ModBlocks.guiID_dfc_stabilizer, world, pos.getX(), pos.getY(), pos.getZ());

            if (this == ModBlocks.dfc_cemitter)
                player.openGui(MainRegistry.instance, ModBlocks.guiID_dfc_cemitter, world, pos.getX(), pos.getY(), pos.getZ());

            if (this == ModBlocks.dfc_exchanger)
                player.openGui(MainRegistry.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
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
        return ((EnumFacing) state.getValue(FACING)).getIndex();
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing enumfacing = EnumFacing.byIndex(meta);
        return this.getDefaultState().withProperty(FACING, enumfacing);
    }


    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate((EnumFacing) state.getValue(FACING)));
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation((EnumFacing) state.getValue(FACING)));
    }

}
