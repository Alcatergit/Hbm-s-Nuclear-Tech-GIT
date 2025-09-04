package com.hbm.hazard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.hbm.hazard.modifier.HazardModifier;
import com.hbm.hazard.transformer.HazardTransformerBase;
import com.hbm.hazard.type.HazardTypeBase;
import com.hbm.interfaces.Untested;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.util.ContaminationUtil;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

@Untested
public class HazardSystem {

	/*
	 * Map for OreDict entries, always evaluated first. Avoid registering HazardData with 'doesOverride', as internal order is based on the item's ore dict keys.
	 */
	public static final HashMap<String, HazardData> oreMap = new HashMap();
	/*
	 * Map for items, either with wildcard meta or stuff that's expected to have a variety of damage values, like tools.
	 */
	public static final HashMap<Item, HazardData> itemMap = new HashMap<>();
	/*
	 * Very specific stacks with item and meta matching. ComparableStack does not support NBT matching, to scale hazards with NBT please use HazardModifiers.
	 */
	public static final HashMap<ComparableStack, HazardData> stackMap = new HashMap<>();
	/*
	 * For items that should, for whichever reason, be completely exempt from the hazard system.
	 */
	public static final HashSet<ComparableStack> stackBlacklist = new HashSet<>();
	public static final HashSet<String> dictBlacklist = new HashSet<>();
	/*
	 * List of hazard transformers, called in order before and after unrolling all the HazardEntries.
	 */
	public static final List<HazardTransformerBase> trafos = new ArrayList<>();

	/*
	 * Map for Fluid entries using their unlocalized names.
	 */
	public static final HashMap<String, HazardData> fluidMap = new HashMap<>();
	
	public static void registerFluid(String o, HazardData data) {
		if(o != null) fluidMap.put(o, data);
	}
	
	/**
	 * Automatically casts the first parameter and registers it to the HazSys
	 * @param o
	 * @param data
	 */
	public static void register(Object o, HazardData data) {

		if(o instanceof String)
			oreMap.put((String)o, data);
		if(o instanceof Item)
			itemMap.put((Item)o, data);
		if(o instanceof Block)
			itemMap.put(Item.getItemFromBlock((Block)o), data);
		if(o instanceof ItemStack)
			stackMap.put(new ComparableStack((ItemStack)o), data);
		if(o instanceof ComparableStack)
			stackMap.put((ComparableStack)o, data);
	}
	
	/**
	 * Prevents the stack from returning any HazardData
	 * @param stack
	 */
	public static void blacklist(Object o) {
		
		if(o instanceof ItemStack) {
			stackBlacklist.add(new ComparableStack((ItemStack) o).makeSingular());
		} else if(o instanceof String) {
			dictBlacklist.add((String) o);
		}
	}
	
	public static boolean isItemBlacklisted(ItemStack stack) {
		
		if(stackBlacklist.contains(new ComparableStack(stack).makeSingular()))
			return true;

		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids) {
			String name = OreDictionary.getOreName(id);
			
			if(dictBlacklist.contains(name))
				return true;
		}
		
		return false;
	}

	public static List<HazardEntry> getHazardsFromFluid(String f) {
		List<HazardEntry> chronological = new ArrayList<>();
		if(fluidMap.containsKey(f))
			chronological.addAll(fluidMap.get(f).entries);
		return chronological;
	}
	
	/**
	 * Will return a full list of applicable HazardEntries for this stack.
	 * <br><br>ORDER:
	 * <ol>
	 * <li>ore dict (if multiple keys, in order of the ore dict keys for this stack)
	 * <li>item
	 * <li>item stack
	 * </ol>
	 * 
	 * "Applicable" means that entries that are overridden or excluded via mutex are not in this list.
	 * Entries that are marked as "overriding" will delete all fetched entries that came before it.
	 * Entries that use mutex will prevent subsequent entries from being considered, shall they collide. The mutex system already assumes that
	 * two keys are the same in priority, so the flipped order doesn't matter.
	 * @param stack
	 * @return
	 */
	public static List<HazardEntry> getHazardsFromStack(ItemStack stack) {
		
		if(stack == null || stack.isEmpty() || isItemBlacklisted(stack)) {
			return new ArrayList<>();
		}
		
		List<HazardData> chronological = new ArrayList<>();
		
		/// ORE DICT ///
		int[] ids = OreDictionary.getOreIDs(stack);
		for(int id : ids) {
			String name = OreDictionary.getOreName(id);
			
			if(oreMap.containsKey(name))
				chronological.add(oreMap.get(name));
		}
		
		/// ITEM ///
		if(itemMap.containsKey(stack.getItem()))
			chronological.add(itemMap.get(stack.getItem()));
		
		/// STACK ///
		ComparableStack comp = new ComparableStack(stack).makeSingular();
		if(stackMap.containsKey(comp))
			chronological.add(stackMap.get(comp));
		
		List<HazardEntry> entries = new ArrayList<>();
		
		for(HazardTransformerBase trafo : trafos) {
			trafo.transformPre(stack, entries);
		}
		
		int mutex = 0;
		
		for(HazardData data : chronological) {
			//if the current data is marked as an override, purge all previous data
			if(data.doesOverride)
				entries.clear();
			
			if((data.getMutex() & mutex) == 0) {
				entries.addAll(data.entries);
				mutex = mutex | data.getMutex();
			}
		}
		
		for(HazardTransformerBase trafo : trafos) {
			trafo.transformPost(stack, entries);
		}
		
		return entries;
	}
	
	public static float getHazardLevelFromStack(ItemStack stack, HazardTypeBase hazard) {
		List<HazardEntry> entries = getHazardsFromStack(stack);
		
		for(HazardEntry entry : entries) {
			if(entry.type == hazard) {
				return HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods);
			}
		}
		
		return 0F;
	}

	public static float getRawRadsFromBlock(Block b) {
		return getHazardLevelFromStack(new ItemStack(Item.getItemFromBlock(b)), HazardRegistry.RADIATION);
	}

	public static float getRawRadsFromStack(ItemStack stack) {
		return getHazardLevelFromStack(stack, HazardRegistry.RADIATION);
	}

	public static float getTotalRadsFromStack(ItemStack stack) {
		return getHazardLevelFromStack(stack, HazardRegistry.RADIATION) + ContaminationUtil.getNeutronRads(stack);
	}
	
	public static void applyHazards(Block b, EntityLivingBase entity) {
		applyHazards(new ItemStack(Item.getItemFromBlock(b)), entity);
	}
	/**
	 * Will grab and iterate through all assigned hazards of the given stack and apply their effects to the holder.
	 * @param stack
	 * @param entity
	 */
	public static void applyHazards(ItemStack stack, EntityLivingBase entity) {
		List<HazardEntry> hazards = getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.applyHazard(stack, entity);
		}
	}
	
	/**
	 * Will apply the effects of all carried items, including the armor inventory.
	 * @param player
	 */
	public static void updatePlayerInventory(EntityPlayer player) {
		int inventorySize = player.inventory.getSizeInventory();
		for(int i = 0; i < inventorySize; i++) {
			
			ItemStack stack = player.inventory.getStackInSlot(i);
			if(!stack.isEmpty()) {
				applyHazards(stack, player);
				
				if(stack.getCount() == 0) {
					player.inventory.setInventorySlotContents(i, ItemStack.EMPTY);
				}
			}
		}
		player.inventoryContainer.detectAndSendChanges();
	}

	public static void updateLivingInventory(EntityLivingBase entity) {
		
		for(EntityEquipmentSlot i : EntityEquipmentSlot.values()) {
			ItemStack stack = entity.getItemStackFromSlot(i);

			if(!stack.isEmpty()) {
				applyHazards(stack, entity);
			}
		}
	}

	public static void updateDroppedItem(EntityItem entity) {
		if(entity.isDead) return;
		ItemStack stack = entity.getItem();
		
		if(stack.isEmpty() || stack.getCount() <= 0) return;
		
		List<HazardEntry> hazards = getHazardsFromStack(stack);
		for(HazardEntry entry : hazards) {
			entry.type.updateEntity(entity, HazardModifier.evalAllModifiers(stack, null, entry.baseLevel, entry.mods));
		}
	}
	
	public static void addHazardInfo(ItemStack stack, EntityPlayer player, List<String> list, ITooltipFlag flagIn) {
		
		List<HazardEntry> hazards = getHazardsFromStack(stack);
		
		for(HazardEntry hazard : hazards) {
			hazard.type.addHazardInformation(player, list, hazard.baseLevel, stack, hazard.mods);
		}
	}
}
