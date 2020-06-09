package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandlerXR;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntitySoyuzLauncher;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SoyuzLauncher extends BlockDummyable {

	public SoyuzLauncher(Material materialIn, String s) {
		super(materialIn, s);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		if(meta >= ForgeDirection.UNKNOWN.ordinal())
			return new TileEntitySoyuzLauncher();
		
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			int[] pos1 = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());
			
			if(pos1 == null)
				return false;
			
			TileEntitySoyuzLauncher entity = (TileEntitySoyuzLauncher) world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_soyuz_launcher, world, pos1[0], pos1[1], pos1[2]);
			}
			return true;
		} else {
			return false;
		}
	}
	
	int height = 4;
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase player, ItemStack itemStack) {
		if(!(player instanceof EntityPlayer))
			return;
		
		EntityPlayer pl = (EntityPlayer) player;
		EnumHand hand = player.getHeldItemMainhand() == itemStack ? EnumHand.MAIN_HAND : EnumHand.OFF_HAND;
		
		int o = -getOffset();
		
		ForgeDirection dir = ForgeDirection.EAST;
		
		/*if(i == 0)
		{
			dir = ForgeDirection.getOrientation(2);
		}
		if(i == 1)
		{
			dir = ForgeDirection.getOrientation(5);
		}
		if(i == 2)
		{
			dir = ForgeDirection.getOrientation(3);
		}
		if(i == 3)
		{
			dir = ForgeDirection.getOrientation(4);
		}*/
		
		if(!checkRequirement(world, pos.getX(), pos.getY(), pos.getZ(), dir, o)) {
			world.setBlockToAir(pos);
			
			if(!pl.capabilities.isCreativeMode) {
				ItemStack stack = pl.inventory.mainInventory.get(pl.inventory.currentItem);
				Item item = Item.getItemFromBlock(this);
				
				if(stack == null) {
					pl.inventory.mainInventory.set(pl.inventory.currentItem, new ItemStack(this));
				} else {
					if(stack.getItem() != item || stack.getCount() == stack.getMaxStackSize()) {
						pl.inventory.addItemStackToInventory(new ItemStack(this));
					} else {
						pl.getHeldItem(hand).grow(1);
					}
				}
			}
			
			return;
		}
		
		int x = pos.getX();
		int y = pos.getY();
		int z = pos.getZ();
		
		world.setBlockState(new BlockPos(x + dir.offsetX * o , y + dir.offsetY * o + height, z + dir.offsetZ * o), this.getDefaultState().withProperty(META, dir.ordinal() + offset), 3);
		fillSpace(world, x, y, z, dir, o);
		world.scheduleUpdate(pos, this, 1);
		world.scheduleUpdate(pos, this, 2);
		
	}
	
	@Override
	protected boolean checkRequirement(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x = x + dir.offsetX * o;
		y = y + dir.offsetY * o + height;
		z = z + dir.offsetZ * o;

		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 1, 6, 6, 6, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, -3, 6, -3, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, 6, -3, -3, 6 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, 6, -3, 6, -3 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { -2, 4, -3, 6, 6, -3 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 4, 1, 1, -6, 8 }, x, y, z, dir)) return false;
		if(!MultiblockHandlerXR.checkSpace(world, x, y, z, new int[] { 0, 4, 2, 2, 9, -5 }, x, y, z, dir)) return false;
		
		return true;
	}
	
	@Override
	protected void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		x = x + dir.offsetX * o;
		y = y + dir.offsetY * o + height;
		z = z + dir.offsetZ * o;

		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 1, 6, 6, 6, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, -3, 6, -3, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, 6, -3, -3, 6 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, 6, -3, 6, -3 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { -2, 4, -3, 6, 6, -3 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 4, 1, 1, -6, 8 }, this, dir);
		MultiblockHandlerXR.fillSpace(world, x, y, z, new int[] { 0, 4, 2, 2, 9, -5 }, this, dir);
		
	}
	
	@Override
	public int[] getDimensions() {
		//because we'll implement our own gnarly behavior here
		return new int[] { 0, 0, 0, 0, 0, 0 };
	}
	
	@Override
	public int getOffset() {
		return 0;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, world.getTileEntity(pos));
		super.breakBlock(world, pos, state);
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

}
