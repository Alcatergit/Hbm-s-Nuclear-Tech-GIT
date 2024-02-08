package com.hbm.blocks.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.lib.Library;
import com.hbm.blocks.ILookOverlay;
import com.hbm.blocks.ITooltipProvider;
import com.hbm.blocks.ModBlocks;
import com.hbm.tileentity.machine.TileEntityCharger;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

public class MachineCharger extends BlockContainer implements ITooltipProvider, ILookOverlay {
	
	public final long maxThroughput;
	public final boolean pointingUp;

	public MachineCharger(Material mat, String s, long max, boolean pointingUp) {
		super(mat);
		this.maxThroughput = max / 20L;
		this.pointingUp = pointingUp;
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		ModBlocks.ALL_BLOCKS.add(this);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCharger();
	}
	
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
    	if(maxThroughput == Long.MAX_VALUE / 20L)
    		tooltip.add("§aMax Chargerate: Infinite HE/s");
    	else
    		tooltip.add("§aMax Chargerate: "+Library.getShortNumber(20 * maxThroughput)+"HE/s");
        this.addStandardInfo(tooltip);
        super.addInformation(stack, player, tooltip, advanced);
    }

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
		
		if(!(te instanceof TileEntityCharger))
			return;
		
		TileEntityCharger charger = (TileEntityCharger) te;
		
		List<String> text = new ArrayList();

		if(charger.totalCapacity > 0){
			text.add(Library.getShortNumber(charger.totalEnergy) + "/" + Library.getShortNumber(charger.totalCapacity) + " HE");
			text.add("§a-> §r" + Library.getShortNumber(20 * charger.actualCharge) + "/" + Library.getShortNumber(20 * charger.charge) + "HE/s");
			text.add("&["+Library.getColorProgress((double)charger.totalEnergy/(double)charger.totalCapacity)+"&]    "+Library.getPercentage((double)charger.totalEnergy/(double)charger.totalCapacity)+"%");
		} else {
			text.add("Nothing to charge");
		}
		ILookOverlay.printGeneric(event, getLocalizedName(), 0xffff00, 0x404000, text);
	}
}
