package com.hbm.blocks.bomb;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IBomb;
import com.hbm.interfaces.IMultiBlock;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.machine.TileEntityDummy;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class LaunchTable extends BlockContainer implements IMultiBlock, IBomb {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	
	public LaunchTable(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityLaunchTable();
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
	
	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[]{FACING});
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
		return ((EnumFacing)state.getValue(FACING)).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		EnumFacing enumfacing = EnumFacing.getFront(meta);

        if (enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }

        return this.getDefaultState().withProperty(FACING, enumfacing);
	}
	
	
	
	@Override
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(FACING, rot.rotate((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn)
	{
	   return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue(FACING)));
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
		return Item.getItemFromBlock(ModBlocks.struct_launcher_core_large);
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return new ItemStack(ModBlocks.struct_launcher_core_large);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityLaunchTable entity = (TileEntityLaunchTable) world.getTileEntity(pos);
			if(entity != null)
			{
				player.openGui(MainRegistry.instance, ModBlocks.guiID_launch_table, world, pos.getX(), pos.getY(), pos.getZ());
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
		EnumFacing e = placer.getHorizontalFacing().getOpposite();
		world.setBlockState(pos, state.withProperty(FACING, e));
		
		for(int k = -4; k <= 4; k++)
			for(int l = -4; l <= 4; l++)
				if(l != 0 && k != 0)
					if(!world.getBlockState(pos.add(k, 0, l)).getBlock().isReplaceable(world, pos.add(k, 0, l))) {
						world.destroyBlock(pos, true);
						return;
					}

		if (e == EnumFacing.NORTH) {
			
			for(int i = 1; i < 12; i++)
				world.setBlockState(pos.add(3, i, 0), Blocks.AIR.getDefaultState());
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX() + i, pos.getY(), pos.getZ(), pos, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX(), pos.getY(), pos.getZ() + i, pos, ModBlocks.dummy_port_launch_table);
		}
		if (e == EnumFacing.EAST) {
			for(int i = 1; i < 12; i++)
				world.setBlockState(pos.add(0, i, 3), Blocks.AIR.getDefaultState());
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX() + i, pos.getY(), pos.getZ(), pos, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX(), pos.getY(), pos.getZ() + i, pos, ModBlocks.dummy_plate_launch_table);
		}
		if (e == EnumFacing.SOUTH) {
			for(int i = 1; i < 12; i++)
				world.setBlockState(pos.add(-3, i, 0), Blocks.AIR.getDefaultState());
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX() + i, pos.getY(), pos.getZ(), pos, ModBlocks.dummy_plate_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX(), pos.getY(), pos.getZ() + i, pos, ModBlocks.dummy_port_launch_table);
		}
		if (e == EnumFacing.WEST) {
			for(int i = 1; i < 12; i++)
				world.setBlockState(pos.add(0, i, -3), Blocks.AIR.getDefaultState());
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX() + i, pos.getY(), pos.getZ(), pos, ModBlocks.dummy_port_launch_table);
			
			for(int i = -4; i <= 4; i++)
				if(i != 0)
					placeDummy(world, pos.getX(), pos.getY(), pos.getZ() + i, pos, ModBlocks.dummy_plate_launch_table);
		}

		for(int i = -4; i <= 4; i++)
			for(int j = -4; j <= 4; j++)
				if(i != 0 && j != 0)
					placeDummy(world, pos.getX() + i, pos.getY(), pos.getZ() + j, pos, ModBlocks.dummy_port_launch_table);
	}
	
	private void placeDummy(World world, int x, int y, int z, BlockPos target, Block block) {
		BlockPos pos = new BlockPos(x, y, z);
		world.setBlockState(pos, block.getDefaultState());
		
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.target = target;
		}
	}



	@Override
	public void explode(World world, BlockPos pos) {
		TileEntityLaunchTable entity = (TileEntityLaunchTable) world.getTileEntity(pos);
		
		if(entity.canLaunch())
			entity.launch();
	}

}
