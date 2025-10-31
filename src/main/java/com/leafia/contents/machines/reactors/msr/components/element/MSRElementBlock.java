package com.leafia.contents.machines.reactors.msr.components.element;

import com.hbm.blocks.BlockContainerBase;
import com.hbm.blocks.ILookOverlay;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MSRElementBlock extends BlockContainerBase implements ILookOverlay {
	public MSRElementBlock(Material m,String s) {
		super(m,s);
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	@Override
	public @Nullable TileEntity createNewTileEntity(World worldIn,int meta) {
		return new MSRElementTE();
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void printHook(RenderGameOverlayEvent.Pre event,World world,int x,int y,int z) {
		List<String> texts = new ArrayList<>();
		MSRTEBase.appendPrintHook(texts,world,x,y,z);

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xFF55FF, 0x3F153F, texts);
	}
}
