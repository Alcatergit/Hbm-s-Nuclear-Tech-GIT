package com.leafia.contents.machines.reactors.pwr.blocks.components.vent.element;

import com.hbm.forgefluid.ModForgeFluids;
import com.leafia.contents.machines.reactors.pwr.blocks.components.vent.PWRVentBlockBase;
import com.leafia.passive.LeafiaPassiveLocal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class PWRVentElementTE extends TileEntity {
	@SideOnly(Side.CLIENT) EnumFacing face = null;
	@SideOnly(Side.CLIENT) public boolean topConnected = false;
	@SideOnly(Side.CLIENT) public boolean btmConnected = false;
	public Fluid fluid = ModForgeFluids.CRYOGEL;
	@Override
	public void validate() {
		super.validate();
		if (world.isRemote)
			LeafiaPassiveLocal.queueFunctionPost(this::updateConnections);
	}
	@Override
	public void invalidate() {
		if (!this.isInvalid() && world.isRemote && face != null) {
			int i = 0;
			for (BlockPos offset = pos.offset(face); i < 2; offset = pos.offset(face.getOpposite())) {
				i++;
				IBlockState state2 = world.getBlockState(offset);
				if (state2.getBlock() instanceof PWRVentElementBlock) {
					TileEntity entity = world.getTileEntity(offset);
					if (entity instanceof PWRVentElementTE)
						LeafiaPassiveLocal.queueFunctionPost(((PWRVentElementTE) entity)::updateConnections);
				}
			}
		}
		super.invalidate();
	}
	@SideOnly(Side.CLIENT) public void updateConnections() {
		if (this.isInvalid()) return;
		if (!world.isValid(pos)) return;
		IBlockState state = world.getBlockState(pos);
		if (state.getBlock() instanceof PWRVentElementBlock) {
			EnumFacing face = state.getValue(PWRVentBlockBase.FACING);
			this.face = face;
			List<PWRVentElementTE> assembly = new ArrayList<>();
			assembly.add(this);
			for (BlockPos offset = pos.offset(face.getOpposite()); world.isValid(offset); offset = offset.offset(face.getOpposite())) {
				IBlockState state2 = world.getBlockState(offset);
				if (state2.getBlock() instanceof PWRVentElementBlock) {
					TileEntity entity = world.getTileEntity(offset);
					if (entity instanceof PWRVentElementTE && state2.getValue(PWRVentElementBlock.FACING).equals(face)) {
						assembly.add(0,(PWRVentElementTE)entity);
						continue;
					}
				}
				break;
			}
			for (BlockPos offset = pos.offset(face); world.isValid(offset); offset = offset.offset(face)) {
				IBlockState state2 = world.getBlockState(offset);
				if (state2.getBlock() instanceof PWRVentElementBlock) {
					TileEntity entity = world.getTileEntity(offset);
					if (entity instanceof PWRVentElementTE && state2.getValue(PWRVentElementBlock.FACING).equals(face)) {
						assembly.add((PWRVentElementTE)entity);
						continue;
					}
				}
				break;
			}
			for (int i = 0; i < assembly.size(); i++) {
				PWRVentElementTE element = assembly.get(i);
				element.btmConnected = i > 0;
				element.topConnected = i < assembly.size()-1;
			}
		}
	}
}
