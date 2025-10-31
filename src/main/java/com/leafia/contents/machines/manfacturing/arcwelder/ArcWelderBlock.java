package com.leafia.contents.machines.manfacturing.arcwelder;

import com.hbm.blocks.BlockDummyable;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class ArcWelderBlock extends BlockDummyable {
	public ArcWelderBlock(Material materialIn,String s) {
		super(materialIn,s);
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 0, 1, 1};
	}
	@Override
	public int getOffset() {
		return 0;
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		if(meta >= 12) return new ArcWelderTE();
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
