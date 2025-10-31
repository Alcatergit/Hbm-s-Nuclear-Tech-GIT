package com.hbm.blocks.leafia;

import com.hbm.blocks.machine.MachineCrystallizer;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.leafia.TileEntityMachineAcidizer;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MachineAcidizer extends MachineCrystallizer {

	public MachineAcidizer(Material mat, String s) {
		super(mat, s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineAcidizer();
		if(meta >= 6) return new TileEntityProxyCombo(true,true,true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] { 5, 0, 1, 1, 1, 1 };
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o + 1);
		this.makeExtra(world, x + dir.offsetX * o + 1, y, z + dir.offsetZ * o - 1);
		this.makeExtra(world, x + dir.offsetX * o - 1, y, z + dir.offsetZ * o - 1);
	}
}