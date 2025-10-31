package com.leafia.contents.machines.manfacturing.soldering;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SolderingBlock extends BlockDummyable {
	public SolderingBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new SolderingTE();
		if (hasExtra(meta)) {
			return new TileEntityProxyCombo(true, true, true);
		}
		return null;
	}

	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		if (worldIn.isRemote)
			return true;
		else if(!playerIn.isSneaking()) {
			int[] pos1 = this.findCore(worldIn, pos.getX(), pos.getY(), pos.getZ());

			if(pos1 == null)
				return false;

			SolderingTE te = (SolderingTE)worldIn.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));
			if (te != null) {
				te.startPacket().__sendToClient(playerIn);
				playerIn.openGui(MainRegistry.instance,ModBlocks.guiID_soldering,worldIn,pos1[0],pos1[1],pos1[2]);
			}
			return true;
		} else
			return false;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 1, 0, 1, 0};
	}
	@Override
	public int getOffset() {
		return 0;
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		super.addInformation(stack,player,tooltip,advanced);
	}
}
