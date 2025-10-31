package com.leafia.contents.machines.panel.controltorch;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.control_panel.*;
import com.leafia.dev.LeafiaDebug;
import net.minecraft.block.BlockTorch;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.*;

public class ControlTorchTE extends TileEntity implements IControllable {
	final boolean isOn;
	ControlTorchTE(boolean isOn) {
		this.isOn = isOn;
	}

	@Override
	public List<String> getInEvents() {
		return Collections.singletonList("torch_set_state");
	}

	@Override
	public Map<String,DataValue> getQueryData() {
		Map<String,DataValue> map = new HashMap<>();
		map.put("isOn",new DataValueFloat(isOn ? 1 : 0));
		return map;
	}

	@Override
	public void receiveEvent(BlockPos from,ControlEvent e) {
		if (e.name.equals("torch_set_state")) {
			boolean newState = e.vars.get("isOn").getNumber() >= 1f;
			if (newState != isOn)
				world.setBlockState(pos, (newState ? ModBlocks.control_torch : ModBlocks.control_torch_unlit).getDefaultState().withProperty(BlockTorch.FACING, world.getBlockState(pos).getValue(BlockTorch.FACING)), 3);
		}
	}

	@Override
	public BlockPos getControlPos() {
		return getPos();
	}

	@Override
	public World getControlWorld() {
		return getWorld();
	}

	@Override
	public void invalidate() {
		ControlEventSystem.get(world).removeControllable(this);
		super.invalidate();
	}

	@Override
	public void validate() {
		ControlEventSystem.get(world).addControllable(this);
		super.validate();
	}
}
