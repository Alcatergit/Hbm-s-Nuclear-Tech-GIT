package com.hbm.blocks.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;
import com.hbm.config.GeneralConfig;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;

public class BlockConverterRfHe extends BlockContainer {

	public BlockConverterRfHe(Material materialIn, String s) {
		super(materialIn);
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.machineTab);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityConverterRfHe();
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(world.isRemote)
		{
			return true;
		} else if(!player.isSneaking())
		{
			TileEntityConverterRfHe entity = (TileEntityConverterRfHe) world.getTileEntity(pos);
			if(entity != null)
			{
				player.sendMessage(new TextComponentString("Note: Buffer may not accuratly represent current conversion rate, keep tact rates in mind."));
				player.sendMessage(new TextComponentString("HE: " + (entity.buf / GeneralConfig.rfConversionRate)));
				player.sendMessage(new TextComponentString("RF: " + entity.buf));
				//FMLNetworkHandler.openGui(player, MainRegistry.instance, ModBlocks.guiID_converter_rf_he, world, x, y, z);
			}
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
		tooltip.add("Also converts from forge energy");
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

}
