package com.leafia.contents.network.fluid;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.util.I18nUtil;
import com.leafia.contents.network.fluid.valves.FluidBoxValveTE;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class FluidDuctEquipmentBase extends BlockContainer implements ILookOverlay {
	public FluidDuctEquipmentBase(Material materialIn,String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);

		ModBlocks.ALL_BLOCKS.add(this);
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new FluidBoxValveTE();
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
	}

	@Override
	public void onBlockPlacedBy(World worldIn,BlockPos pos,IBlockState state,EntityLivingBase placer,ItemStack stack) {
		TileEntity te = worldIn.getTileEntity(pos);
		if (te instanceof FluidDuctEquipmentTE) {
			FluidDuctEquipmentTE duct = (FluidDuctEquipmentTE)te;
			AxisAlignedBB top = new AxisAlignedBB(pos.up());
			AxisAlignedBB btm = new AxisAlignedBB(pos.down());
			Vec3d vecA = placer.getPositionEyes(0);
			Vec3d vecB = vecA.add(placer.getLookVec().scale(10));
			duct.vertical = false;
			if (vecA.y < pos.getY()+1)
				duct.vertical = duct.vertical || top.calculateIntercept(vecA,vecB) != null;
			if (vecA.y > pos.getY())
				duct.vertical = duct.vertical || btm.calculateIntercept(vecA,vecB) != null;
			duct.direction = placer.getHorizontalFacing();
			if (placer.getLookVec().y > 0.707)
				duct.face = -1;
			else if (placer.getLookVec().y < -0.707)
				duct.face = 1;
			else
				duct.face = 0;
		}
	}

	@Override
	public void onNeighborChange(IBlockAccess world,BlockPos pos,BlockPos neighbor) {
		TileEntity te = world.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2){
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		}
	}
	@Override
	public void neighborChanged(IBlockState state,World worldIn,BlockPos pos,Block blockIn,BlockPos fromPos) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2){
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		}
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		//getActualState appears to be called when the neighbor changes on client, so I can use this to update instead of a buggy packet.
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof TileEntityFFDuctBaseMk2)
			((TileEntityFFDuctBaseMk2)te).onNeighborChange();
		return state;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity te = worldIn.getTileEntity(pos);

		if(te instanceof TileEntityFFDuctBaseMk2){
			TileEntityFFDuctBaseMk2.breakBlock(worldIn, pos);
		}
		super.breakBlock(worldIn, pos, state);
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
	public void printHook(Pre event,World world,int x,int y,int z) {

		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

		if(!(te instanceof TileEntityFFDuctBaseMk2))
			return;

		Fluid ductFluid = ((TileEntityFFDuctBaseMk2) te).getType();

		List<String> text = new ArrayList();
		if(ductFluid == null){
			text.add("§7" + I18nUtil.resolveKey("desc.none"));
		} else{
			int color = ModForgeFluids.getFluidColor(ductFluid);
			text.add("&[" + color + "&]" +I18nUtil.resolveKey(ductFluid.getUnlocalizedName()));
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
}
