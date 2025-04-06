package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityDummy;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;
// Five fucking years, yeah finally reworking the shit that should've been done 3 years ago
public class MachineChemplant extends BlockDummyable {
	
	public MachineChemplant(Material materialIn, String s) {
		super(materialIn, s);
		this.bounding.add(new AxisAlignedBB(-1.0, 0.0, -2.0, 2.0, 3.0, 1.0));

		// some guy once told me not to use magic numbers
		// so I turned him into a newt
		this.bounding.add(new AxisAlignedBB(-0.14375, 0.34375, -2.5, 0.15625, 0.65625, 1.5));
		this.bounding.add(new AxisAlignedBB(0.85625, 0.34375, -2.5, 1.15625, 0.65625, 1.5));

		this.bounding.add(new AxisAlignedBB(-1.5, 0.0, -0.5, 0.0, 1.0, 0.5));
		this.bounding.add(new AxisAlignedBB(1.0, 0.0, -1.5, 2.5, 1.0, -0.5));
		this.setCreativeTab(MainRegistry.machineTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineChemplant();
		if(meta >= 6) return new TileEntityProxyCombo(false, true, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {2, 0, 2, 1, 2, 1};
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.machine_chemplant);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
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
	public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
		return false;
	}
	@Override
	public int getOffset() {
		return 1;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return super.standardOpenBehavior(world, pos.getX(), pos.getY(), pos.getZ(), player, 0);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		x -= dir.offsetX;
		z -= dir.offsetZ;

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		this.makeExtra(world, x + rot.offsetX * 2,					y,	z + rot.offsetZ * 2);
		this.makeExtra(world, x - rot.offsetX * 1,					y,	z - rot.offsetZ * 1);
		this.makeExtra(world, x + rot.offsetX * 2 - dir.offsetX,	y,	z + rot.offsetZ * 2 - dir.offsetZ);
		this.makeExtra(world, x - rot.offsetX * 1 - dir.offsetX,	y,	z - rot.offsetZ * 1 - dir.offsetZ);
	}

}
