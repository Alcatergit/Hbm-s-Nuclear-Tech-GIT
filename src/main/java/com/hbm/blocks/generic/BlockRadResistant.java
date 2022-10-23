package com.hbm.blocks.generic;

import java.util.List;
import com.hbm.blocks.ModBlocks;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockRadResistant extends Block implements IRadResistantBlock {

	public BlockRadResistant(Material materialIn, String s) {
		super(materialIn);
		this.setRegistryName(s);
		this.setUnlocalizedName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}
	
	@Override
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.onBlockAdded(worldIn, pos, state);
	}
	
	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		RadiationSystemNT.markChunkForRebuild(worldIn, pos);
		super.breakBlock(worldIn, pos, state);
	}
	
	@Override
	public float getResistance(){
		return 1;
	}

	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		float hardness = this.getExplosionResistance(null);
		tooltip.add("§2[Radiation Shielding]§r");
		if(hardness > 50){
			tooltip.add("§6Blast Resistance: "+hardness+"§r");
		}
	}
}
