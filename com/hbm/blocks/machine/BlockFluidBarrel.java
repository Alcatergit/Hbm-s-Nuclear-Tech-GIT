package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.YellowBarrel;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.InventoryHelper;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityBarrel;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFluidBarrel extends BlockContainer {

	private int capacity;
	public static boolean keepInventory;
	
	public BlockFluidBarrel(Material materialIn, int cap, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		capacity = cap;
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBarrel(capacity);
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> list, ITooltipFlag advanced) {
		if(this == ModBlocks.barrel_plastic) {
			list.add(TextFormatting.AQUA + "Capacity: 12,000mB");
			list.add(TextFormatting.YELLOW + "Cannot store hot fluids");
			list.add(TextFormatting.YELLOW + "Cannot store corrosive fluids");
			list.add(TextFormatting.YELLOW + "Cannot store antimatter");
		}

		if(this == ModBlocks.barrel_corroded) {
			list.add(TextFormatting.AQUA + "Capacity: 6,000mB");
			list.add(TextFormatting.GREEN + "Can store hot fluids");
			list.add(TextFormatting.GREEN + "Can store corrosive fluids");
			list.add(TextFormatting.YELLOW + "Cannot store antimatter");
			list.add(TextFormatting.RED + "Leaky");
		}
		
		if(this == ModBlocks.barrel_iron) {
			list.add(TextFormatting.AQUA + "Capacity: 8,000mB");
			list.add(TextFormatting.GREEN + "Can store hot fluids");
			list.add(TextFormatting.YELLOW + "Cannot store corrosive fluids properly");
			list.add(TextFormatting.YELLOW + "Cannot store antimatter");
		}
		
		if(this == ModBlocks.barrel_steel) {
			list.add(TextFormatting.AQUA + "Capacity: 16,000mB");
			list.add(TextFormatting.GREEN + "Can store hot fluids");
			list.add(TextFormatting.GREEN + "Can store corrosive fluids");
			list.add(TextFormatting.YELLOW + "Cannot store antimatter");
		}
		
		if(this == ModBlocks.barrel_antimatter) {
			list.add(TextFormatting.AQUA + "Capacity: 16,000mB");
			list.add(TextFormatting.GREEN + "Can store hot fluids");
			list.add(TextFormatting.GREEN + "Can store corrosive fluids");
			list.add(TextFormatting.GREEN + "Can store antimatter");
		}
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote) {
			return true;
			
		} else if(!player.isSneaking()) {
			player.openGui(MainRegistry.instance, ModBlocks.guiID_barrel, world, pos.getX(), pos.getY(), pos.getZ());
			return true;
			
		} else {
			return false;
		}
	}
	
	//Drillgon200: No need for a base class, a helper method works just fine
	@Spaghetti("stop doing that and make a base class for fuck's sake")
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		if(!keepInventory)
			InventoryHelper.dropInventoryItems(worldIn, pos, worldIn.getTileEntity(pos));
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}
	
	@Override
	public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
		return YellowBarrel.BARREL_BB;
	}
}
