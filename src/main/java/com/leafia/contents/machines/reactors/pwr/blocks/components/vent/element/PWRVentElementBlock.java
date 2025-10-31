package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element;

import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.inlet.PWRVentInletBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.outlet.PWRVentOutletBlock;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.Block;
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

public class PWRVentElementBlock extends PWRVentBlockBase implements IRadResistantBlock {
	public PWRVentElementBlock() {
		super(Material.IRON,"pwr_vent_element");
		setSoundType(SoundType.METAL);
	}
	@Override
	public boolean tileEntityShouldCreate(World world,BlockPos pos) {
		return false;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new PWRVentElementTE();
	}



	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public boolean isRadResistant(World worldIn, BlockPos blockPos){
		return true;
	}

	@Override
	public void addInformation(ItemStack stack,World player,List<String> list,ITooltipFlag advanced) {
		MachineTooltip.addWIP(list);
		MachineTooltip.addMultiblock(list);
		MachineTooltip.addModular(list);
		super.addInformation(stack,player,list,advanced);
		list.add("§2[" + I18nUtil.resolveKey("trait.radshield") + "]");
	}
	@Override
	public boolean correctDirection(World world,BlockPos pos) {
		IBlockState state = world.getBlockState(pos);
		if (!(state.getBlock() instanceof PWRVentBlockBase)) return false;
		EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
		pos = pos.offset(face);
		while (world.isValid(pos)) {
			Block block = world.getBlockState(pos).getBlock();
			if (!(block instanceof PWRVentBlockBase)) return false;
			PWRVentBlockBase vent = (PWRVentBlockBase)block;
			if (vent instanceof PWRVentInletBlock || vent instanceof PWRVentOutletBlock)
				return vent.correctDirection(world,pos);
			if (!(vent instanceof PWRVentElementBlock)) return false;
			pos = pos.offset(face);
		}
		return false;
	}
}