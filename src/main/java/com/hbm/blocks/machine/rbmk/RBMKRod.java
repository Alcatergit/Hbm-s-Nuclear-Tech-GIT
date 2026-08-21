package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.BossSpawnHandler;
import com.hbm.items.machine.ItemRBMKRod;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.tileentity.TileEntityProxyInventory;
import com.hbm.tileentity.machine.rbmk.RBMKDials;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKBase;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKRod;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RBMKRod extends RBMKBase {

	public boolean moderated = false;
	
	public RBMKRod(boolean moderated, String s, String c) {
		super(s, c);
		this.moderated = moderated;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		
		if(meta >= offset)
			return new TileEntityRBMKRod();
		
		if(hasExtra(meta))
			return new TileEntityProxyInventory();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		BossSpawnHandler.markFBI(playerIn);
		if(worldIn.isRemote) return true;

		int[] corePos = this.findCore(worldIn, pos.getX(), pos.getY(), pos.getZ());
		if(corePos == null) return false;

		TileEntity te = worldIn.getTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]));
		if(!(te instanceof TileEntityRBMKRod)) return false;

		TileEntityRBMKRod rod = (TileEntityRBMKRod) te;

		if(!playerIn.getHeldItem(hand).isEmpty() && playerIn.getHeldItem(hand).getItem() instanceof ItemRBMKRod && rod.inventory.getStackInSlot(0).isEmpty()) {
			ItemStack rodStack = playerIn.getHeldItem(hand).copy();
			rodStack.setCount(1);
			rod.inventory.setStackInSlot(0, rodStack);
			if(!playerIn.capabilities.isCreativeMode) playerIn.getHeldItem(hand).shrink(1);
			rod.markDirty();
			((EntityPlayerMP)playerIn).connection.sendPacket(new SPacketUpdateTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]), 0, rod.getUpdateTag()));
			worldIn.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundHandler.upgradePlug, SoundCategory.BLOCKS, 1.0F, 1.0F);
			return false;
		}
		return openInv(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, ModBlocks.guiID_rbmk_rod, hand);
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		int meta = getMetaFromState(state);

		if(!world.isRemote && meta >= offset && !RBMKDials.getMeltdownsDisabled(world)) {
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof TileEntityRBMKRod tile) {
				if(RBMKDials.getMeltdownOverpressure(world) && TileEntityRBMKBase.explodeOnBroken) {
					if(!tile.inventory.getStackInSlot(0).isEmpty() && tile.inventory.getStackInSlot(0).getItem() instanceof ItemRBMKRod && ItemRBMKRod.getHullHeat(tile.inventory.getStackInSlot(0)) >= 1500) {
						tile.meltdown();
					}
				}
			}
		}

		super.breakBlock(world, pos, state);
		world.removeTileEntity(pos);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}
	
}
