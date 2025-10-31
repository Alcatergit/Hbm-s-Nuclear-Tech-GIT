package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.BlockDummyable;
import com.hbm.inventory.material.Mats.MaterialStack;
import com.hbm.items.machine.ItemScraps;
import com.hbm.lib.ForgeDirection;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyInventory;
//import com.hbm.tileentity.machine.TileEntityCrucible;

import api.hbm.block.ICrucibleAcceptor;
import com.hbm.tileentity.machine.TileEntityCrucible;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.internal.FMLNetworkHandler;

public class MachineCrucible extends BlockDummyable implements ICrucibleAcceptor {

	protected static final AxisAlignedBB AABB_BIG_BOX = new AxisAlignedBB(-0.75D, 0D, -0.75D, 1.75D, 1.5D, 1.75D);
	protected static final AxisAlignedBB AABB_FLOOR = new AxisAlignedBB(-1.5D, 0D, -1.5D, 2.5D, 0.5D, 2.5D);

	public MachineCrucible(Material materialIn, String s) {
		super(materialIn, s);

		this.bounding.add(new AxisAlignedBB(-1.5D, 0D, -1.5D, 1.5D, 0.5D, 1.5D));
        this.bounding.add(new AxisAlignedBB(-1.25D, 0.5D, -1.25D, 1.25D, 1.5D, -1D));
        this.bounding.add(new AxisAlignedBB(-1.25D, 0.5D, -1.25D, -1D, 1.5D, 1.25D));
        this.bounding.add(new AxisAlignedBB(-1.25D, 0.5D, 1D, 1.25D, 1.5D, 1.25D));
        this.bounding.add(new AxisAlignedBB(1D, 0.5D, -1.25D, 1.25D, 1.5D, 1.25D));
        this.FULL_BLOCK_AABB.setMaxY(0.999D); //item bounce prevention
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityCrucible();
		return new TileEntityProxyInventory();
	}

	public boolean hasShovelInHand(EntityPlayer player, EnumHand hand){
		return player.getHeldItem(hand) != null && player.getHeldItem(hand).getItem() instanceof ItemTool && (((ItemTool) player.getHeldItem(hand).getItem()).getToolClasses(player.getHeldItem(hand)).contains("shovel"));
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {

		if(world.isRemote) {
			return true;
		} else if(!player.isSneaking()) {
			BlockPos p = this.findCore(world, pos);

			if(p == null)
				return false;
			if(hasShovelInHand(player, hand)) {
				TileEntityCrucible crucible = (TileEntityCrucible) world.getTileEntity(p);
				List<MaterialStack> stacks = new ArrayList();
				stacks.addAll(crucible.recipeStack);
				stacks.addAll(crucible.wasteStack);

				for(MaterialStack stack : stacks) {
					ItemStack scrap = ItemScraps.create(new MaterialStack(stack.material, stack.amount));
					if(!player.inventory.addItemStackToInventory(scrap)) {
						player.dropItem(scrap, false);
					}
				}

				crucible.recipeStack.clear();
				crucible.wasteStack.clear();
				crucible.markDirty();

			} else {
				FMLNetworkHandler.openGui(player, MainRegistry.instance, 0, world, p.getX(), p.getY(), p.getZ());
			}
			return true;
		} else {
			return true;
		}
	}



	@Override
	public int[] getDimensions() {
		return new int[] {1, 0, 1, 1, 1, 1};
	}

	@Override
	public int getOffset() {
		return 1;
	}
/*
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {

		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityCrucible) {
			TileEntityCrucible crucible = (TileEntityCrucible) te;

			List<MaterialStack> stacks = new ArrayList();
			stacks.addAll(crucible.recipeStack);
			stacks.addAll(crucible.wasteStack);

			for(MaterialStack stack : stacks) {
				world.spawnEntity(new EntityItem(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, ItemScraps.create(new MaterialStack(stack.material, stack.amount))));
			}

			crucible.recipeStack.clear();
			crucible.wasteStack.clear();
		}

		super.breakBlock(world, pos, state);
	}*/

    @Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
		if(getMetaFromState(state) >= 12){
			return AABB_BIG_BOX;
		} else {
			BlockPos p = this.findCore(world, pos);
			if(p != null){
				return AABB_BIG_BOX.offset(p.getX()-pos.getX(), p.getY()-pos.getY(), p.getZ()-pos.getZ());
			}
		}
        return FULL_BLOCK_AABB;
    }

	@Override
	public boolean canAcceptPartialPour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {

		BlockPos pos = this.findCore(world, p);
		if(pos == null) return false;
		TileEntity tile = world.getTileEntity(pos);
		if(!(tile instanceof TileEntityCrucible)) return false;
		TileEntityCrucible crucible = (TileEntityCrucible) tile;

		return crucible.canAcceptPartialPour(world, p, dX, dY, dZ, side, stack);
	}

	@Override
	public MaterialStack pour(World world, BlockPos p, double dX, double dY, double dZ, ForgeDirection side, MaterialStack stack) {

		BlockPos pos = this.findCore(world, p);
		if(pos == null) return stack;
		TileEntity tile = world.getTileEntity(pos);
		if(!(tile instanceof TileEntityCrucible)) return stack;
		TileEntityCrucible crucible = (TileEntityCrucible) tile;

		return crucible.pour(world, p, dX, dY, dZ, side, stack);
	}

	@Override public boolean canAcceptPartialFlow(World world,BlockPos p,ForgeDirection side,MaterialStack stack) { return false; }
	@Override public MaterialStack flow(World world, BlockPos p, ForgeDirection side, MaterialStack stack) { return null; }
}
