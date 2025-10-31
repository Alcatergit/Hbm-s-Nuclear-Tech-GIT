package com.leafia.contents.machines.processing.advcent;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;
import com.leafia.dev.MachineTooltip;
import com.leafia.dev.proxy.TileEntityProxyFluidIO;
import com.leafia.dev.proxy.TileEntityProxyFluidIO.ProxyType;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvCentBlock extends BlockDummyable {
	public AdvCentBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[]{3,0,1,0,0,1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		if (meta >= 12)
			return new AdvCentTE();
		else if (meta >= extra)
			return new TileEntityProxyFluidIO(true,true,(vec)->vec.getZ() == -1 ? ProxyType.INPUT : ProxyType.OUTPUT);
		return null;
	}

	@Override
	protected void fillSpace(World world,int x,int y,int z,ForgeDirection back,int o) {
		super.fillSpace(world,x,y,z,back,o);
		ForgeDirection front = back.getOpposite();
		ForgeDirection right = front.getRotation(ForgeDirection.UP);
		BlockPos pos = new BlockPos(x,y,z);
		this.makeExtra(world,pos.offset(right.toEnumFacing()));
		this.makeExtra(world,pos.offset(front.toEnumFacing()));
		this.makeExtra(world,pos.offset(front.toEnumFacing()).offset(right.toEnumFacing()));
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		return standardOpenBehavior(world, pos.getX(), pos.getY(), pos.getZ(), player, 0);
	}

	@Override
	public void addInformation(ItemStack stack,@Nullable World worldIn,List<String> tooltip,ITooltipFlag flagIn) {
		MachineTooltip.addWIP(tooltip);
		super.addInformation(stack,worldIn,tooltip,flagIn);
	}
}
