package com.hbm.forgefluid;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

import net.minecraft.item.Item;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

//That's it, I'm making my own fluid container registry if forge won't provide one. It won't be compatible with other mods, and it's not as
//good as the forge one, but it'll sure fix that one terrible override in FFUtils.
public class FluidContainerRegistry {
	
	private static Map<Item, FluidContainerData> containers = new HashMap<>();
	private static Map<Pair<Item, Fluid>, Item> containerToItem = new HashMap<>();
	
	public static void registerContainer(Item item, Item container, FluidStack fluid){
		containers.put(item, new FluidContainerData(container, fluid));
		containerToItem.put(Pair.of(container, fluid.getFluid()), item);
	}
	
	public static boolean hasFluid(Item item){
		return containers.containsKey(item);
	}
	
	public static FluidStack getFluidFromItem(Item item){
		return containers.get(item).containedFluid.copy();
	}
	
	public static Item getContainerItem(Item item){
		return containers.get(item).container;
	}
	
	public static Item getFullContainer(Item item, Fluid f){
		return containerToItem.get(Pair.of(item, f));
	}
	
	private static class FluidContainerData {
		public Item container;
		public FluidStack containedFluid;
		
		public FluidContainerData(Item container, FluidStack fluid) {
			this.container = container;
			this.containedFluid = fluid;
		}
	}
}
