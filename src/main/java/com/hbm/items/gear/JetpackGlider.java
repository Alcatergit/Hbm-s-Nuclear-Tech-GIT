package com.hbm.items.gear;

import java.util.List;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorModHandler;
import com.hbm.handler.JetpackHandler;
import com.hbm.interfaces.IItemFluidHandler;
import com.hbm.items.armor.ItemArmorMod;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class JetpackGlider extends ItemArmorMod implements IItemFluidHandler {

	public int capacity;
	
	public JetpackGlider(ArmorMaterial enumArmorMaterialSteel, int i, EntityEquipmentSlot chest, int capacity, String s) {
		super(ArmorModHandler.plate_only, false, true, false, false, s);
		this.capacity = capacity;
	}

	public FluidTank getTank(ItemStack stack){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			return new FluidTank(capacity);
		}
		return new FluidTank(capacity).readFromNBT(stack.getTagCompound().getCompoundTag("fuelTank"));
	}
	
	public void setTank(ItemStack stack, FluidTank tank){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setTag("fuelTank", tank.writeToNBT(new NBTTagCompound()));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		FluidTank tank = getTank(stack);
		if(tank.getFluid() == null){
			list.add(TextFormatting.RED + "    Fuel Type: None");
			list.add(TextFormatting.RED + "    Fuel Speed: " + JetpackHandler.getSpeed(null));
		} else {
			list.add(TextFormatting.RED + "    Fuel Type: " + I18n.format(tank.getFluid().getUnlocalizedName()));
			list.add(TextFormatting.RED + "    Fuel Speed: " + JetpackHandler.getSpeed(tank.getFluid().getFluid()));
		}
		int percent = (int)(((float)tank.getFluidAmount()/tank.getCapacity())*100);
		list.add(TextFormatting.RED + "    Fuel Amount: " + tank.getFluidAmount() + "/" + tank.getCapacity() + " (" + percent + "%)");
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor) {
		super.addDesc(list, stack, armor);
		addInformation(stack, null, list, null);
	}
	
	@Override
	public int fill(ItemStack stack, FluidStack fluid, boolean doFill) {
		if(fluid == null)
			return 0;
		if(fluid.getFluid() == ModForgeFluids.kerosene || fluid.getFluid() == ModForgeFluids.balefire || fluid.getFluid() == ModForgeFluids.nitan){
			FluidTank tank = getTank(stack);
			int fill = tank.fill(fluid, doFill);
			if(doFill)
				setTank(stack, tank);
			return fill;
		}
		return 0;
	}

	@Override
	public FluidStack drain(ItemStack stack, FluidStack resource, boolean doDrain) {
		FluidTank tank = getTank(stack);
		FluidStack drain = tank.drain(resource, doDrain);
		if(doDrain)
			setTank(stack, tank);
		return drain;
	}

	@Override
	public FluidStack drain(ItemStack stack, int maxDrain, boolean doDrain) {
		FluidTank tank = getTank(stack);
		FluidStack drain = tank.drain(maxDrain, doDrain);
		if(doDrain)
			setTank(stack, tank);
		return drain;
	}
}
