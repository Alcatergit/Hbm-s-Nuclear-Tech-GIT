package com.leafia.contents.machines.processing.mixingvat;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;
import com.leafia.contents.machines.processing.mixingvat.proxy.MixingVatProxy;
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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MixingVatBlock extends BlockDummyable {
	public MixingVatBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[]{1,0,2,0,0,1};
	}

	@Override
	public int getOffset() {
		return 0;
	}

	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		if (meta >= 12)
			return new MixingVatTE();
		else if (meta >= extra) {
			return new MixingVatProxy();
		}
		return null;
	}

	@Override
	protected void fillSpace(World world,int x,int y,int z,ForgeDirection back,int o) {
		super.fillSpace(world,x,y,z,back,o);
		ForgeDirection front = back.getOpposite();
		ForgeDirection right = front.getRotation(ForgeDirection.UP);
		BlockPos pos = new BlockPos(x,y,z);
		LeafiaDebug.debugPos(world,new BlockPos(x,y,z).offset(front.toEnumFacing()),2,0x0000FF,"Front");
		LeafiaDebug.debugPos(world,new BlockPos(x,y,z).offset(right.toEnumFacing()),2,0xFF0000,"Right");
		this.makeExtra(world,pos.offset(right.toEnumFacing()));
		this.makeExtra(world,pos.offset(front.toEnumFacing(),2));
		this.makeExtra(world,pos.offset(front.toEnumFacing(),2).offset(right.toEnumFacing()));
	}

	@Override
	public boolean onBlockActivated(World worldIn,BlockPos pos,IBlockState state,EntityPlayer playerIn,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		return standardOpenBehavior(worldIn,pos.getX(),pos.getY(),pos.getZ(),playerIn,0);
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World worldIn,List<String> tooltip,ITooltipFlag flagIn) {
		MachineTooltip.addWIP(tooltip);
		super.addInformation(stack,worldIn,tooltip,flagIn);
	}
}
