package com.leafia.contents.network.computers.audiocable;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import pl.asie.computronics.tile.TileAudioCable;

/**
 * Important: Do NOT <b>EVER</b> refer to this class in ANY way unless it's guaranteed that Computronics is installed.
 */
public class AudioCableBlock extends BlockContainer {
	public AudioCableBlock(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.blockTab);

		ModBlocks.ALL_BLOCKS.add(this);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new TileAudioCable();
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
