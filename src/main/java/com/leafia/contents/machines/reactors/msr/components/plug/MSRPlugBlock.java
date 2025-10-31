package com.leafia.contents.machines.reactors.msr.components.plug;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.machine.BlockMachineBase;
import com.hbm.util.I18nUtil;
import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class MSRPlugBlock extends BlockMachineBase implements ILookOverlay {
	public MSRPlugBlock(Material materialIn,String s) {
		super(materialIn,0,s);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new MSRPlugTE();
	}
	@Override
	protected boolean rotatable() {
		return true;
	}
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void printHook(RenderGameOverlayEvent.Pre event,World world,int x,int y,int z) {
		List<String> texts = new ArrayList<>();
		TileEntity te = world.getTileEntity(new BlockPos(x,y,z));
		if (te instanceof MSRPlugTE plug) {
			if (plug.molten)
				texts.add(TextFormatting.DARK_RED+I18nUtil.resolveKey("tile.msr_plug.molten"));
		}
		MSRTEBase.appendPrintHook(texts,world,x,y,z);
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xFF55FF, 0x3F153F, texts);
	}

	@Override
	public boolean onBlockActivated(World world,BlockPos pos,IBlockState state,EntityPlayer player,EnumHand hand,EnumFacing facing,float hitX,float hitY,float hitZ) {
		return false;
	}
}
