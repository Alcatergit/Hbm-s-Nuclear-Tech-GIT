package com.hbm.blocks.machine.rbmk;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.tool.ItemTooling;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKControlManual;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RBMKControl extends RBMKBase {

	public boolean moderated = false;
	
	public RBMKControl(boolean moderated, String s, String c) {
		super(s, c);
		this.moderated = moderated;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {

		if(meta >= offset)
			return new TileEntityRBMKControlManual();
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
		if(worldIn.isRemote) return true;

		int[] corePos = this.findCore(worldIn, pos.getX(), pos.getY(), pos.getZ());
		if(corePos == null) return false;

		TileEntity te = worldIn.getTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]));
		if(!(te instanceof TileEntityRBMKControlManual)) return false;

		TileEntityRBMKControlManual control = (TileEntityRBMKControlManual) te;

		if(!playerIn.isSneaking() && !playerIn.getHeldItem(hand).isEmpty() && playerIn.getHeldItem(hand).getItem() instanceof ItemTooling && ((ItemTooling)playerIn.getHeldItem(hand).getItem()).getType() == ToolType.SCREWDRIVER) {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("cycleColor", true);
			control.receiveControl(data);
			worldIn.playSound(null, corePos[0] + 0.5, corePos[1] + 0.5, corePos[2] + 0.5, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F);
			if(playerIn.getHeldItem(hand).getMaxDamage() > 0)
				playerIn.getHeldItem(hand).damageItem(1, playerIn);
			return true;
		}
		return openInv(worldIn, pos.getX(), pos.getY(), pos.getZ(), playerIn, ModBlocks.guiID_rbmk_control, hand);
	}

	@Override
	public boolean onScrew(World world, EntityPlayer player, int x, int y, int z, EnumFacing side, float fX, float fY, float fZ, EnumHand hand, ToolType tool){
		if(tool != ToolType.SCREWDRIVER)
			return false;

		int[] pos = this.findCore(world, x, y, z);
		if(pos == null) return false;

		TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
		if(!(te instanceof TileEntityRBMKControlManual)) return false;

		TileEntityRBMKControlManual control = (TileEntityRBMKControlManual) te;

		if(!world.isRemote) {
			NBTTagCompound data = new NBTTagCompound();
			data.setBoolean("cancelColor", true);
			control.receiveControl(data);
			world.playSound(null, pos[0] + 0.5, pos[1] + 0.5, pos[2] + 0.5, SoundEvents.UI_BUTTON_CLICK, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		return true;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}
}
