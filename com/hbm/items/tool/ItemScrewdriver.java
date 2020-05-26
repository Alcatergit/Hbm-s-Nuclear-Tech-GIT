package com.hbm.items.tool;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockFluidPipeMk2;
import com.hbm.items.ModItems;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class ItemScrewdriver extends Item {

	public ItemScrewdriver(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		if(state.getBlock() == ModBlocks.fluid_duct_mk2){
			Fluid type = null;
			TileEntity te = world.getTileEntity(pos);
			if(te instanceof TileEntityFFDuctBaseMk2){
				type = ((TileEntityFFDuctBaseMk2) te).getType();
			}
			
			boolean extracts = state.getValue(BlockFluidPipeMk2.EXTRACTS);
			world.setBlockState(pos, ModBlocks.fluid_duct_mk2.getDefaultState().withProperty(BlockFluidPipeMk2.EXTRACTS, !extracts));
			
			te = world.getTileEntity(pos);
			if(te instanceof TileEntityFFDuctBaseMk2){
				((TileEntityFFDuctBaseMk2) te).setType(type);
			}
			
			player.swingArm(hand);
		}
		return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		tooltip.add("Right clicking fluid pipes will toggle extraction mode");
		tooltip.add("Could be used instead of a fuse...");
	}
}
