package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet;

import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element.PWRVentElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet.PWRVentInletBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PWRVentOutletBlock extends PWRVentBlockBase {
	public PWRVentOutletBlock() {
		super(Material.IRON,"pwr_vent_outlet");
		setSoundType(SoundType.METAL);
	}
	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new PWRVentOutletTE();
	}

	@Override
	public boolean correctDirection(World world,BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof PWRVentBlockBase)) return false;
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
		pos = pos.offset(face.getOpposite());
		while (world.isValid(pos)) {
			IBlockState state2 = world.getBlockState(pos);
			if (state2.getBlock() instanceof PWRVentInletBlock) {
				return (state2.getValue(PWRVentBlockBase.FACING).equals(face));
			}
			if (!(state2.getBlock() instanceof PWRVentElementBlock)) return false;
			rotateTarget(world,pos,face);
			pos = pos.offset(face.getOpposite());
		}
		return false;
	}
	@Override
	public void addInformation(ItemStack stack,World player,List<String> list,ITooltipFlag advanced) {
		MachineTooltip.addWIP(list);
		MachineTooltip.addMultiblock(list);
		MachineTooltip.addModular(list);
		super.addInformation(stack,player,list,advanced);
	}
}