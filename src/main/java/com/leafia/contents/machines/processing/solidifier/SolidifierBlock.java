package com.leafia.contents.machines.processing.solidifier;

import com.hbm.blocks.BlockDummyable;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class SolidifierBlock extends BlockDummyable {
	public SolidifierBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		super.addInformation(stack,player,tooltip,advanced);
		MachineTooltip.addWIP(tooltip);
		tooltip.add(TextFormatting.RED+"DO NOT PLACE THIS BLOCK! It may and "+TextFormatting.BOLD +"WILL MAKE YOUR WORLD BASICALLY INACCESSIBLE");
	}

	@Override
	public int[] getDimensions() {
		return new int[] {3, 0, 1, 1, 1, 1};
	}
	@Override
	public int getOffset() {
		return 1;
	}
	@Override
	public void fillSpace(World world,int x,int y,int z,ForgeDirection dir,int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x = x + dir.offsetX * o;
		z = z + dir.offsetZ * o;

		this.makeExtra(world, x, y + 3, z);

		this.makeExtra(world, x + 1, y + 1, z);
		this.makeExtra(world, x - 1, y + 1, z);
		this.makeExtra(world, x, y + 1, z + 1);
		this.makeExtra(world, x, y + 1, z - 1);
	}
	@Nullable
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		if(meta >= 12) return new SolidifierTE();
		if (hasExtra(meta)) {
			return new TileEntityProxyCombo(true, true, true);
		}
		return null;
	}
}
