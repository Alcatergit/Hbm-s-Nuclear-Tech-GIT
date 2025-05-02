package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ILookOverlay;
import com.hbm.lib.Library;
import com.hbm.tileentity.machine.TileEntityHadronPower;
import com.hbm.util.I18nUtil;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class BlockHadronPower extends BlockContainer implements ILookOverlay {

	public BlockHadronPower(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityHadronPower();
	}
	
	@Override
	public Block setSoundType(SoundType sound) {
		return super.setSoundType(sound);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(te instanceof TileEntityHadronPower))
			return;
		
		TileEntityHadronPower extractor = (TileEntityHadronPower) te;
		
		List<String> text = new ArrayList();
		text.add(Library.getShortNumber(extractor.power) + "/" + Library.getShortNumber(extractor.getMaxPower()) + " HE");
		text.add("&["+Library.getColorProgress((double)extractor.power/(double)extractor.getMaxPower())+"&]    "+Library.getPercentage((double)extractor.power/(double)extractor.getMaxPower())+"%");
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
}
