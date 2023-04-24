package com.hbm.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;
import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.NbtComparableStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.interfaces.Spaghetti;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.ModItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

//TODO: clean this shit up
//Alcater: on it
@Spaghetti("everything")
public class DiFurnaceRecipes {

	public static HashMap<AStack[], ItemStack> diRecipes = new HashMap<AStack[], ItemStack>();
	public static HashMap<AStack, Integer> diFuels = new HashMap<AStack, Integer>();

	public static void registerRecipes(){
		addRecipe(new OreDictStack(W.ingot()), new OreDictStack(COAL.gem()), new ItemStack(ModItems.neutron_reflector, 2));
		addRecipe(new OreDictStack(W.dust()), new OreDictStack(COAL.gem()), new ItemStack(ModItems.neutron_reflector, 2));

		addRecipe(new OreDictStack(CU.ingot()), new OreDictStack(PB.ingot()), new ItemStack(ModItems.neutron_reflector, 4));
		addRecipe(new OreDictStack(CU.dust()), new OreDictStack(PB.ingot()), new ItemStack(ModItems.neutron_reflector, 4));
		addRecipe(new OreDictStack(CU.ingot()), new OreDictStack(PB.dust()), new ItemStack(ModItems.neutron_reflector, 4));
		addRecipe(new OreDictStack(CU.dust()), new OreDictStack(PB.dust()), new ItemStack(ModItems.neutron_reflector, 4));

		addRecipe(new OreDictStack(CU.plate()), new OreDictStack(PB.plate()), new ItemStack(ModItems.neutron_reflector, 1));

		addRecipe(new OreDictStack(IRON.ingot()), new OreDictStack(COAL.gem()), new ItemStack(ModItems.ingot_steel, 2));
		addRecipe(new OreDictStack(IRON.dust()), new OreDictStack(COAL.gem()), new ItemStack(ModItems.ingot_steel, 2));
		addRecipe(new OreDictStack(IRON.ingot()), new OreDictStack(COAL.dust()), new ItemStack(ModItems.ingot_steel, 2));
		addRecipe(new OreDictStack(IRON.dust()), new OreDictStack(COAL.dust()), new ItemStack(ModItems.ingot_steel, 2));

		addRecipe(new OreDictStack(CU.ingot()), new OreDictStack(REDSTONE.dust()), new ItemStack(ModItems.ingot_red_copper, 2));
		addRecipe(new OreDictStack(CU.dust()), new OreDictStack(REDSTONE.dust()), new ItemStack(ModItems.ingot_red_copper, 2));

		addRecipe(new NbtComparableStack(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel)), new ComparableStack(Items.SLIME_BALL), new ItemStack(ModItems.canister_napalm, 1));
		
		addRecipe(new OreDictStack(MINGRADE.ingot()), new OreDictStack(STEEL.ingot()), new ItemStack(ModItems.ingot_advanced_alloy, 2));
		addRecipe(new OreDictStack(MINGRADE.dust()), new OreDictStack(STEEL.ingot()), new ItemStack(ModItems.ingot_advanced_alloy, 2));
		addRecipe(new OreDictStack(MINGRADE.ingot()), new OreDictStack(STEEL.dust()), new ItemStack(ModItems.ingot_advanced_alloy, 2));
		addRecipe(new OreDictStack(MINGRADE.dust()), new OreDictStack(STEEL.dust()), new ItemStack(ModItems.ingot_advanced_alloy, 2));

		addRecipe(new OreDictStack(W.ingot()), new OreDictStack(SA326.nugget()), new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
		addRecipe(new OreDictStack(W.dust()), new OreDictStack(SA326.nugget()), new ItemStack(ModItems.ingot_magnetized_tungsten, 1));

		addRecipe(new ComparableStack(ModItems.plate_mixed), new OreDictStack(GOLD.plate()), new ItemStack(ModItems.plate_paa, 2));
		
		addRecipe(new OreDictStack(STEEL.ingot()), new OreDictStack(W.ingot()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.dust()), new OreDictStack(W.ingot()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.ingot()), new OreDictStack(W.dust()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.dust()), new OreDictStack(W.dust()), new ItemStack(ModItems.ingot_dura_steel, 2));

		addRecipe(new OreDictStack(STEEL.ingot()), new OreDictStack(CO.ingot()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.dust()), new OreDictStack(CO.ingot()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.ingot()), new OreDictStack(CO.dust()), new ItemStack(ModItems.ingot_dura_steel, 2));
		addRecipe(new OreDictStack(STEEL.dust()), new OreDictStack(CO.dust()), new ItemStack(ModItems.ingot_dura_steel, 2));

		addRecipe(new OreDictStack(BIGMT.ingot()), new ComparableStack(ModItems.powder_meteorite), new ItemStack(ModItems.ingot_starmetal, 2));

		addRecipe(new OreDictStack(STEEL.ingot()), new OreDictStack(TC99.nugget()), new ItemStack(ModItems.ingot_tcalloy, 1));
		addRecipe(new OreDictStack(STEEL.dust()), new OreDictStack(TC99.nugget()), new ItemStack(ModItems.ingot_tcalloy, 1));

		addRecipe(new ComparableStack(Item.getItemFromBlock(ModBlocks.block_meteor)), new OreDictStack(CO.ingot()), new ItemStack(ModItems.ingot_meteorite));
		addRecipe(new ComparableStack(Item.getItemFromBlock(ModBlocks.block_meteor)), new OreDictStack(CO.dust()), new ItemStack(ModItems.ingot_meteorite));

		addRecipe(new ComparableStack(ModItems.meteorite_sword_hardened), new OreDictStack(CO.ingot()), new ItemStack(ModItems.meteorite_sword_alloyed));
		addRecipe(new ComparableStack(ModItems.meteorite_sword_hardened), new OreDictStack(CO.dust()), new ItemStack(ModItems.meteorite_sword_alloyed));

		if(GeneralConfig.enableDebugMode) {
			addRecipe(new OreDictStack(IRON.ingot()), new OreDictStack(NETHERQUARTZ.gem()), new ItemStack(ModBlocks.test_render, 1));
		}
		if(GeneralConfig.enableBabyMode) {
			addRecipe(new ComparableStack(ModItems.canister_generic), new OreDictStack(COAL.gem()), ItemFluidCanister.getFullCanister(ModForgeFluids.oil));
		}
	}

	public static void registerFuels(){
		addFuel(new OreDictStack(COAL.gem()), 200);
		addFuel(new OreDictStack(COAL.dust()), 220);
		addFuel(new OreDictStack(COAL.block()), 2000);
		addFuel(new OreDictStack(LIGNITE.gem()), 150);
		addFuel(new OreDictStack(LIGNITE.dust()), 150);
		addFuel(new OreDictStack("fuelCoke"), 400);
		addFuel(new OreDictStack(ANY_COKE.gem()), 400);
		addFuel(new ComparableStack(Items.LAVA_BUCKET), 12800);
		addFuel(new ComparableStack(Items.BLAZE_ROD), 1000);
		addFuel(new ComparableStack(Items.BLAZE_POWDER), 300);
		addFuel(new ComparableStack(Items.COAL, 1, 1), 200);
		addFuel(new ComparableStack(ModItems.solid_fuel), 400);
		addFuel(new ComparableStack(ModItems.briquette_lignite), 200);
	}

	public static void addRecipe(AStack inputTop, AStack inputBottom, ItemStack output){
		diRecipes.put(new AStack[]{ inputTop, inputBottom}, output);
		diRecipes.put(new AStack[]{ inputBottom, inputTop}, output);
	}

	public static void removeRecipe(AStack inputTop, AStack inputBottom){
		diRecipes.remove(new AStack[]{ inputTop, inputBottom});
		diRecipes.remove(new AStack[]{ inputBottom, inputTop});
	}

	public static void addFuel(AStack fuel, int power){
		diFuels.put(fuel, power);
	}

	public static void removeFuel(AStack fuel){
		diFuels.remove(fuel);
	}

	public static ItemStack getFurnaceProcessingResult(ItemStack stack1, ItemStack stack2) {
		if (stack1 == null || stack1.isEmpty() || stack2== null || stack2.isEmpty())
			return null;
		ItemStack item1 = stack1.copy();
		ItemStack item2 = stack2.copy();
		item1.setCount(1);
		item2.setCount(1);
		boolean hasTag1 = item1.hasTagCompound();
		boolean hasTag2 = item2.hasTagCompound();
		AStack input1;
		AStack input2;
		ItemStack outputItem;

		if(hasTag1){
			input1 = new NbtComparableStack(item1);
		}else{
			input1 = new ComparableStack(item1);
		}

		if(hasTag2){
			input2 = new NbtComparableStack(item2);
		}else{
			input2 = new ComparableStack(item2);
		}

		outputItem = diRecipes.get(new AStack[]{ input1, input2 });
		if(outputItem != null)
			return outputItem;

		boolean haveTriedAllID2 = false;
		int[] ids1 = OreDictionary.getOreIDs(new ItemStack(item1.getItem(), 1, item1.getItemDamage()));
		int[] ids2 = OreDictionary.getOreIDs(new ItemStack(item2.getItem(), 1, item2.getItemDamage()));
		
		for(int id1 = 0; id1 < ids1.length; id1++) {

			OreDictStack oreTag1 = new OreDictStack(OreDictionary.getOreName(ids1[id1]));
			if(hasTag1){
				outputItem = diRecipes.get(new AStack[]{ oreTag1, new NbtComparableStack(item2) });
			} else {
				outputItem = diRecipes.get(new AStack[]{ oreTag1, new ComparableStack(item2) });
			}
			if(outputItem != null)
				return outputItem;

			for(int id2 = 0; id2 < ids2.length; id2++) {
				OreDictStack oreTag2 = new OreDictStack(OreDictionary.getOreName(ids2[id2]));
				if(!haveTriedAllID2){
					if(hasTag1){
						outputItem = diRecipes.get(new AStack[]{ new NbtComparableStack(item1), oreTag2 });
					} else {
						outputItem = diRecipes.get(new AStack[]{ new ComparableStack(item1), oreTag2 });
					}
					if(outputItem != null)
						return outputItem;
				}
				outputItem = diRecipes.get(new AStack[]{ oreTag1, oreTag2 });
				if(outputItem != null)
					return outputItem;
			}
			haveTriedAllID2 = true;
		}
		return null;
	}

	public static int getItemPower(ItemStack stack) {
		if(stack == null || stack.isEmpty()){
			return 0;
		}
		ItemStack item = stack.copy();
		item.setCount(1);
		int power = 0;
		if(item.hasTagCompound()){
			power = toInt(diFuels.get(new NbtComparableStack(item)));
		}else{
			power = toInt(diFuels.get(new ComparableStack(item)));
		}
		if(power > 0){
			return power;
		}
		for(int id : OreDictionary.getOreIDs(new ItemStack(item.getItem(), 1, item.getItemDamage()))){
			power = toInt(diFuels.get(new OreDictStack(OreDictionary.getOreName(id))));
			if(power > 0){
				return power;
			}
		}
		return 0;
	}

	public static int toInt(Integer i){
		if(i == null)
			return 0;
		return i;
	}

	public static List<ItemStack> getAlloyFuels() {
		HashSet uniqueFuels = new HashSet<ItemStack>();
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		for(AStack entry : DiFurnaceRecipes.diFuels.keySet()){
			fuels.addAll(entry.getStackList());
		}
		fuels.addAll(uniqueFuels);
		return fuels;
	}
}
