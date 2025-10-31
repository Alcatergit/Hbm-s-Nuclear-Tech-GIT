package com.leafia.contents.network.fluid.gauges;

import com.hbm.blocks.ILookOverlay;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.tileentity.conductor.TileEntityFFDuctBaseMk2;
import com.hbm.util.I18nUtil;
import com.leafia.contents.network.fluid.FluidDuctEquipmentBase;
import com.leafia.contents.network.fluid.FluidDuctEquipmentTE;
import com.leafia.dev.math.FiaMatrix;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;
import net.minecraftforge.fluids.Fluid;

import java.util.ArrayList;
import java.util.List;

public class FluidDuctGauge extends FluidDuctEquipmentBase {
	public FluidDuctGauge(Material materialIn,String s) {
		super(materialIn,s);
	}
	@Override
	public TileEntity createNewTileEntity(World worldIn,int meta) {
		return new FluidDuctGaugeTE();
	}

	@Override
	public void printHook(Pre event,World world,int x,int y,int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));

		if(!(te instanceof FluidDuctGaugeTE))
			return;

		FluidDuctGaugeTE gauge = (FluidDuctGaugeTE)te;

		Fluid ductFluid = gauge.getType();

		List<String> text = new ArrayList();
		if(ductFluid == null){
			text.add("§7" + I18nUtil.resolveKey("desc.none"));
		} else{
			int color = ModForgeFluids.getFluidColor(ductFluid);
			text.add("&[" + color + "&]" +I18nUtil.resolveKey(ductFluid.getUnlocalizedName()));
		}
		text.add(gauge.local_fillPerSec+"mB/s");

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}
}
