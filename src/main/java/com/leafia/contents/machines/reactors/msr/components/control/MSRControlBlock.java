package com.leafia.contents.machines.reactors.msr.components.control;

import api.hbm.block.IToolable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemTooling;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlTE;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;

public class MSRControlBlock extends BlockContainer implements ILookOverlay {

	public static final PropertyDirection FACING = BlockDirectional.FACING;

	public MSRControlBlock(Material materialIn,String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	protected BlockStateContainer createBlockState(){
		return new BlockStateContainer(this, new IProperty[] { FACING });
	}

	@Override
	public int getMetaFromState(IBlockState state){
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.byIndex(meta);
		return this.getDefaultState().withProperty(FACING, enumfacing);
	}

	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot){
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn){
		return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}

	@Override
	public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, EnumFacing.getDirectionFromEntityLiving(pos, placer).getOpposite()));
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new MSRControlTE();
	}

	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		TileEntity entity = worldIn.getTileEntity(pos);
		if (!(entity instanceof MSRControlTE control))
			return true;
		if (playerIn.getHeldItem(hand).getItem() instanceof ItemTooling) {
			ItemTooling tool = (ItemTooling)(playerIn.getHeldItem(hand).getItem());
			if (tool.getType().equals(IToolable.ToolType.SCREWDRIVER)) {
				if (!worldIn.isRemote) {
					double ogPos = control.targetInsertion;
					if (hand.equals(EnumHand.OFF_HAND))
						control.targetInsertion = Math.min(control.targetInsertion+0.25,control.length);
					else
						control.targetInsertion = Math.max(control.targetInsertion-0.25,0);
					if (control.targetInsertion == ogPos)
						return false;
					worldIn.playSound(null,pos,HBMSoundEvents.lockOpen,SoundCategory.BLOCKS,0.5f,1);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void printHook(Pre event,World world,int x,int y,int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
		if (te instanceof MSRControlTE control) {
			List<String> texts = new ArrayList<>();
			texts.add(I18nUtil.resolveKey("tile.msr_control.insertion",String.format("%01.2f",control.insertion)));
			ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xFF55FF, 0x3F153F, texts);
		}
	}
}
