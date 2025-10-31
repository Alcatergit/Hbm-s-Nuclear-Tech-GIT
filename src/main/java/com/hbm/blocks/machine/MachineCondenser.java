package com.hbm.blocks.machine;

import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.machine.TileEntityCondenser;
import com.hbm.util.I18nUtil;
import com.leafia.dev.MachineTooltip;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class MachineCondenser extends BlockContainer implements ILookOverlay {

	public MachineCondenser(Material mat, String s) {
		super(mat);
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack,@Nullable World player,List<String> tooltip,ITooltipFlag advanced) {
		MachineTooltip.addCondenser(tooltip);
		super.addInformation(stack,player,tooltip,advanced);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCondenser();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state){
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
			
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(te instanceof TileEntityCondenser))
			return;
		
		TileEntityCondenser chungus = (TileEntityCondenser) te;
		
		List<String> text = new ArrayList();

		text.add("§a-> §r" + ModForgeFluids.SPENTSTEAM.getLocalizedName(new FluidStack(ModForgeFluids.SPENTSTEAM, 1)) + ": " + chungus.tanks[0].getFluidAmount() + "/" + chungus.tanks[0].getCapacity() + "mB");
		text.add("§c<- §r" + FluidRegistry.WATER.getLocalizedName(new FluidStack(FluidRegistry.WATER, 1)) + ": " + chungus.tanks[1].getFluidAmount() + "/" + chungus.tanks[1].getCapacity() + "mB");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
}
