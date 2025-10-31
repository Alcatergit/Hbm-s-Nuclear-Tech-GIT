package com.leafia.contents.machines.processing.pyrooven;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class PyroOvenBlock extends BlockDummyable {
	public PyroOvenBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 3, 3, 2, 2};
	}
	@Override
	public int getOffset() {
		return 3;
	}
	@Override
	protected void fillSpace(World world,int x,int y,int z,ForgeDirection dir,int o) {
		super.fillSpace(world, x, y, z, dir, o);
		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		ForgeDirection rot = dir.getRotation(ForgeDirection.DOWN);

		for(int i = -2; i <= 2; i++) {
			this.makeExtra(world, x + dir.offsetX * i + rot.offsetX * 2, y, z + dir.offsetZ * i + rot.offsetZ * 2);
		}

		this.makeExtra(world, x - rot.offsetX, y + 2, z - rot.offsetZ);
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		if(meta >= 12) return new PyroOvenTE();
		if (hasExtra(meta)) {
			return new TileEntityProxyCombo(true, true, true);
		}
		return null;
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		super.addInformation(stack,player,tooltip,advanced);
		MachineTooltip.addWIP(tooltip);
	}
}
