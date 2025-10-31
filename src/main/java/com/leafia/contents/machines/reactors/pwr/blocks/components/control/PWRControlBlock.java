package com.leafia.contents.machines.reactors.pwr.blocks.components.control;

import api.hbm.block.IToolable;
import com.hbm.blocks.BlockBase;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.items.tool.ItemTooling;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.dev.MachineTooltip;
import com.leafia.dev.container_utility.LeafiaPacket;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemNameTag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class PWRControlBlock extends BlockBase implements ITooltipProvider, ITileEntityProvider, ILookOverlay, PWRComponentBlock {
    public PWRControlBlock(String s) {
        super(Material.IRON,s);
        this.setTranslationKey("pwr_control");
        setSoundType(SoundType.METAL);
    }
    @Override
    public boolean shouldRenderOnGUI() {
        return true;
    }
    @Override
    public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
        MachineTooltip.addMultiblock(tooltip);
        MachineTooltip.addModular(tooltip);
        addStandardInfo(tooltip);
        MachineTooltip.addUpdate(tooltip,"tile.reactor_control.name");
        super.addInformation(stack,player,tooltip,advanced);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn,int meta) {
        return new PWRControlTE();
    }
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
        pos = getTopControl(worldIn,pos);
        TileEntity entity = worldIn.getTileEntity(pos);
        if (!(entity instanceof PWRControlTE))
            return true;
        PWRControlTE control = (PWRControlTE)entity;
        control.updateHeight();
        if (playerIn.getHeldItem(hand).getItem() instanceof ItemTooling) {
            ItemTooling tool = (ItemTooling)(playerIn.getHeldItem(hand).getItem());
            if (tool.getType().equals(IToolable.ToolType.SCREWDRIVER)) {
                if (!worldIn.isRemote) {
                    double ogPos = control.targetPosition;
                    if (hand.equals(EnumHand.OFF_HAND))
                        control.targetPosition = Math.max(control.targetPosition-0.25/control.height,0);
                    else
                        control.targetPosition = Math.min(control.targetPosition+0.25/control.height,1);
                    if (control.targetPosition == ogPos)
                        return false;
                    worldIn.playSound(null,pos,HBMSoundEvents.lockOpen,SoundCategory.BLOCKS,0.5f,1);
                }
                return true;
            }
        } else if (playerIn.getHeldItem(hand).getItem() instanceof ItemNameTag) {
            if (!worldIn.isRemote) {
                ItemStack stack = playerIn.getHeldItem(hand);
                worldIn.playSound(null,pos,HBMSoundEvents.techBleep,SoundCategory.BLOCKS,1,1);
                if (stack.getSubCompound("display") != null) {
                    control.name = stack.getDisplayName();
                } else {
                    control.name = PWRControlTE.defaultName;
                }
                LeafiaPacket._start(entity).__write(2,control.name).__sendToAffectedClients();
                control.markDirty();
            }
            return true;
        }
        return false;
    }

    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    public boolean isNormalCube(IBlockState state,IBlockAccess world,BlockPos pos) {
        return false;
    }

    public BlockPos getTopControl(World world,BlockPos pos) {
        BlockPos upPos = pos.up();
        while (world.isValid(upPos)) {
            if (world.getBlockState(upPos).getBlock() instanceof PWRControlBlock) {
                pos = upPos;
            } else break;
            upPos = pos.up();
        }
        return pos;
    }
    @SideOnly(Side.CLIENT)
    @Override
    public void printHook(RenderGameOverlayEvent.Pre event,World world,int x,int y,int z) {
        BlockPos pos = getTopControl(world,new BlockPos(x,y,z));
        if (world.getBlockState(pos.up()).getBlock() instanceof PWRControlBlock)
            return;
        TileEntity entity = world.getTileEntity(pos);
        if (!(entity instanceof PWRControlTE))
            return;
        List<String> texts = new ArrayList<>();
        PWRControlTE control = (PWRControlTE)entity;

        texts.add("§e"+String.format("%01.2f",control.position*control.height)+"m");
        texts.add("Use screwdriver to raise rods");
        texts.add("Use with offhand to lower rods");
        texts.add("");
        texts.add("§8"+control.name);
        texts.add("Use name tag to label rods for group control");

        ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xFF55FF, 0x3F153F, texts);
    }

    @Override
    public boolean tileEntityShouldCreate(World world,BlockPos pos) {
        return !(world.getBlockState(pos.up()).getBlock() instanceof PWRControlBlock);
    }
    @Override
    public void neighborChanged(IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) { // Fired only on server
        super.neighborChanged(state,worldIn,pos,blockIn,fromPos);
        beginDiagnosis(worldIn,pos,fromPos);
    }
}
