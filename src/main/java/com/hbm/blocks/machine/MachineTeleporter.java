package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.ILookOverlay;
import com.hbm.items.ModItems;
import com.hbm.util.I18nUtil;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineTeleporter extends BlockContainer implements ILookOverlay {

	public MachineTeleporter(Material materialIn, String s) {
		super(materialIn);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityMachineTeleporter();
	}
	
	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		
		TileEntity tile = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(tile instanceof TileEntityMachineTeleporter)) return;
		
		TileEntityMachineTeleporter tele = (TileEntityMachineTeleporter) tile;
		
		List<String> text = new ArrayList();
		
		text.add((tele.power >= tele.consumption ? "§a" : "§c") + String.format("%,d", tele.power) + " / " + String.format("%,d", tele.maxPower));
		if(tele.target == null) {
			text.add("§cNo destination set!");
		} else {
			text.add("Destination: " + tele.target.getX() + " / " + tele.target.getY() + " / " + tele.target.getZ());
		}
		
		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
}
