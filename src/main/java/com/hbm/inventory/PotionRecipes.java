package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.potion.HbmPotion;
import com.hbm.lib.RefStrings;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionHelper;
import net.minecraft.potion.PotionType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreIngredient;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class PotionRecipes {

	public static void registerPotionRecipes() {
		addPotionRecipe(HbmPotion.radiation, ModItems.nuclear_waste, 30*20, 2*60*20, 15*20);
		addPotionRecipe(HbmPotion.radx, ModItems.pill_iodine, 30*20, 2*60*20, 15*20);
		addPotionRecipe(HbmPotion.radaway, ModBlocks.mush, 60*20, 10*60*20, 4*60*20);
		addPotionRecipe(HbmPotion.mutation, ModItems.egg_balefire, 30*20, 2*60*20, 0);
		addPotionRecipe(HbmPotion.taint, ModItems.syringe_taint, 30*20, 2*60*20, 15*20);
		addPotionRecipe(HbmPotion.phosphorus, ModItems.crystal_phosphorus, 30*20, 2*60*20, 0);
		addPotionRecipe(HbmPotion.bang, ModItems.coin_ufo, 30*20, 15*20, 2*30*20);
		addPotionRecipe(HbmPotion.stability, ModItems.five_htp, 30*20, 2*60*20, 0);
		addPotionRecipe(HbmPotion.lead, ModItems.apple_lead1, 30*20, 2*60*20, 15*20);
		addPotionRecipe(HbmPotion.telekinesis, ModBlocks.float_bomb, 30*20, 2*60*20, 15*20);
	}

	public static void addPotionRecipe(Potion type, Object input, int normalTime, int longTime, int strongTime){
		addPotionRecipe(type, input, normalTime, longTime, strongTime, 0, 0, 1);
	}

	public static void addPotionRecipe(Potion type, Object input, int normalTime, int longTime, int strongTime, int normalLvl, int longLvl, int strongLvL) {
		String baseName = type.getRegistryName().getResourcePath();
		PotionType normalType = addPotion(new PotionEffect(type, normalTime, normalLvl), baseName, baseName);
		
		Ingredient brewItem = getIngredient(input);
		PotionHelper.addMix(PotionTypes.WATER, brewItem, PotionTypes.MUNDANE);
		// actual potion
		PotionHelper.addMix(PotionTypes.AWKWARD, brewItem, normalType);

		if (strongTime > 0){
			PotionType strongType = addPotion(new PotionEffect(type, strongTime, strongLvL), baseName, "strong_" + baseName);
			PotionHelper.addMix(normalType, Items.GLOWSTONE_DUST, strongType);
		}
		if (longTime > 0){
			PotionType longType = addPotion(new PotionEffect(type, longTime, longLvl), baseName, "long_" + baseName);
			PotionHelper.addMix(normalType, Items.REDSTONE, longType);
		}
	}

	private static PotionType addPotion(PotionEffect effect, String baseName, String name) {
		PotionType type = new PotionType(baseName, effect).setRegistryName(new ResourceLocation(RefStrings.MODID, name));
		ForgeRegistries.POTION_TYPES.register(type);

		return type;
	}

	public static Ingredient getIngredient(Object reagent){
		if (reagent instanceof Item)
			return Ingredient.fromItem((Item) reagent);
		else if (reagent instanceof Block)
			return Ingredient.fromStacks(new ItemStack((Block) reagent));
		else if (reagent instanceof ItemStack)
			return Ingredient.fromStacks((ItemStack) reagent);
		else if (reagent instanceof String)
			return new OreIngredient((String) reagent);
		return null;
	}
}