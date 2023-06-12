package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyEnergy;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class MachineMiningLaser extends BlockDummyable {

	public MachineMiningLaser(Material mat, String s) {
		super(mat, s);
	}

	@Override
	public TileEntity createNewTileEntity(World p_149915_1_, int meta) {

		if(meta >= 12)
			return new TileEntityMachineMiningLaser();
		if(meta == 7)
			return new TileEntityProxyEnergy();

		if(meta >= 6)
			return new TileEntityProxyCombo(true, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {1, 1, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 0;
	}
	
	@Override
	public int getHeightOffset() {
		return -1;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos1, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {

			int[] pos = this.findCore(world, pos1.getX(), pos1.getY(), pos1.getZ());

			if(pos == null)
				return false;

			FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_mining_laser, world, pos[0], pos[1], pos[2]);
			return true;
		} else {
			return true;
		}
	}

	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {

		super.fillSpace(world, x, y, z, dir, o);

		x += dir.offsetX * o;
		z += dir.offsetZ * o;

		this.makeExtra(world, x + 1, y, z);
		this.makeExtra(world, x - 1, y, z);
		this.makeExtra(world, x, y, z + 1);
		this.makeExtra(world, x, y, z - 1);

		this.makeExtra(world, x, y + 1, z);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("3x3x3 Multiblock");
		tooltip.add("Only placeable on a ceiling.");
	}
}