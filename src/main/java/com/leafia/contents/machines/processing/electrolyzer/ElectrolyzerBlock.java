package com.leafia.contents.machines.processing.electrolyzer;

import com.hbm.blocks.BlockDummyable;
import com.hbm.handler.MultiblockHandlerXR;
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

public class ElectrolyzerBlock extends BlockDummyable {
	public ElectrolyzerBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {0, 0, 5, 5, 1, 3};
	}
	@Override
	public int getOffset() {
		return 5;
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		if(meta >= 12) return new ElectrolyzerTE();
		if (hasExtra(meta)) {
			return new TileEntityProxyCombo(true, true, true);
		}
		return null;
	}
	@Override
	public void fillSpace(World world,int x,int y,int z,ForgeDirection dir,int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {2, -1, 5, 5, 1, 1}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -3, 5, 5, 0, 0}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 4, -4, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 2, -2, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, 0, 0, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, -2, 2, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] {3, -1, -4, 4, -3, 3}, this, dir);
		MultiblockHandlerXR.fillSpace(world,x + dir.offsetX * 4, y + 3, z + dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world,x + dir.offsetX * 2, y + 3, z + dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y + 3, z, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - dir.offsetX * 2, y + 3, z - dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, this, dir);
		MultiblockHandlerXR.fillSpace(world, x - dir.offsetX * 4, y + 3, z - dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, this, dir);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x - dir.offsetX * 5, y, z - dir.offsetZ * 5);
		this.makeExtra(world, x - dir.offsetX * 5 + rot.offsetX, y, z - dir.offsetZ * 5 + rot.offsetZ);
		this.makeExtra(world, x - dir.offsetX * 5 - rot.offsetX, y, z - dir.offsetZ * 5 - rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5, y, z + dir.offsetZ * 5);
		this.makeExtra(world, x + dir.offsetX * 5 + rot.offsetX, y, z + dir.offsetZ * 5 + rot.offsetZ);
		this.makeExtra(world, x + dir.offsetX * 5 - rot.offsetX, y, z + dir.offsetZ * 5 - rot.offsetZ);
	}

	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y , z, getDimensions(), x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {2, -1, 5, 5, 1, 1}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -3, 5, 5, 0, 0}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 4, -4, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 2, -2, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, 0, 0, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, -2, 2, -3, 3}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] {3, -1, -4, 4, -3, 3}, x, y, z, dir)) return false;

		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * 4, y + 3, z + dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x + dir.offsetX * 2, y + 3, z + dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y + 3, z, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x - dir.offsetX * 2, y + 3, z - dir.offsetZ * 2, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x - dir.offsetX * 4, y + 3, z - dir.offsetZ * 4, new int[] {0, 0, 0, 0, -1, 2}, x, y, z, dir)) return false;

		return true;
	}
	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		super.addInformation(stack,player,tooltip,advanced);
		MachineTooltip.addWIP(tooltip);
	}
}
