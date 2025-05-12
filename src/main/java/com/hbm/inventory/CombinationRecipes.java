package com.hbm.inventory;

import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.*;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
//import com.hbm.items.ItemEnums;
//import com.hbm.items.ItemEnums.EnumAshType;
//import com.hbm.items.ItemEnums.EnumBriquetteType;
import com.hbm.items.ItemEnums.EnumCokeType;
import com.hbm.items.ItemEnums.EnumTarType;
import com.hbm.items.ModItems;
import com.hbm.util.Tuple.Pair;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

public class CombinationRecipes {

	public static HashMap<AStack, Pair<ItemStack, FluidStack>> recipes = new HashMap<AStack, Pair<ItemStack, FluidStack>>();

	public static void registerDefaults() {
		addRecipe(COAL.gem(),							DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), 	new FluidStack(ModForgeFluids.COALCREOSOTE, 100));
		addRecipe(COAL.dust(),							DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), 	new FluidStack(ModForgeFluids.COALCREOSOTE, 100));
		//recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.COAL)), new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), new FluidStack(Fluids.COALCREOSOTE, 150)));

		addRecipe(LIGNITE.gem(),						DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(ModForgeFluids.COALCREOSOTE, 50));
		addRecipe(LIGNITE.dust(),						DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(ModForgeFluids.COALCREOSOTE, 50));
		//recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.LIGNITE)), new Pair(DictFrame.fromOne(ModItems.coke, EnumCokeType.LIGNITE), new FluidStack(Fluids.COALCREOSOTE, 100)));

		//recipes.put(CHLOROCALCITE.dust(),						new Pair(new ItemStack(ModItems.powder_calcium), new FluidStack(Fluids.CHLORINE, 250)));
		//recipes.put(MOLYSITE.dust(),							new Pair(new ItemStack(Items.iron_ingot), new FluidStack(Fluids.CHLORINE, 250)));
		addRecipe(CINNABAR.gem(), 					new ItemStack(ModItems.sulfur), 						new FluidStack(ModForgeFluids.MERCURY, 100));
		addRecipe(new ComparableStack(Items.GLOWSTONE_DUST),new ItemStack(ModItems.sulfur), 					new FluidStack(ModForgeFluids.CHLORINE, 100));
		//recipes.put(SODALITE.gem(),								new Pair(new ItemStack(ModItems.powder_sodium), new FluidStack(Fluids.CHLORINE, 100)));
		//recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.chunk_ore, ItemEnums.EnumChunkType.CRYOLITE)), new Pair(new ItemStack(ModItems.powder_aluminium, 1), new FluidStack(Fluids.LYE, 150)));
		//recipes.put(NA.dust(),									new Pair(null, new FluidStack(Fluids.SODIUM, 100)));
		//recipes.put(LIMESTONE.dust(),							new Pair(new ItemStack(ModItems.powder_calcium), new FluidStack(Fluids.CARBONDIOXIDE, 50)));

		addRecipe(KEY_LOG,								new ItemStack(Items.COAL, 1 ,1),			new FluidStack(ModForgeFluids.WOODOIL, 250));
		//recipes.put(KEY_SAPLING,	new Pair(DictFrame.fromOne(ModItems.powder_ash, EnumAshType.WOOD),	new FluidStack(Fluids.WOODOIL, 50)));
		//recipes.put(new ComparableStack(DictFrame.fromOne(ModItems.briquette, EnumBriquetteType.WOOD)), new Pair(new ItemStack(Items.coal, 1 ,1),	new FluidStack(Fluids.WOODOIL, 500)));

		addRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRUDE)), DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null);
		addRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.CRACK)), DictFrame.fromOne(ModItems.coke, EnumCokeType.PETROLEUM), null);
		addRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.COAL)), DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), null);
		addRecipe(new ComparableStack(DictFrame.fromOne(ModItems.oil_tar, EnumTarType.WOOD)), DictFrame.fromOne(ModItems.coke, EnumCokeType.COAL), null);

		addRecipe(Items.REEDS, 							new ItemStack(Items.SUGAR, 2), 					new FluidStack(ModForgeFluids.ETHANOL, 50));
		addRecipe(Blocks.CLAY, 							new ItemStack(Blocks.BRICK_BLOCK, 1), null);

		//for(BedrockOreType type : BedrockOreType.values()) {
		//	recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.BASE, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.BASE_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		//	recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.PRIMARY_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		//	recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.SULFURIC_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		//	recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.SOLVENT_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		//	recipes.put(new ComparableStack(ItemBedrockOreNew.make(BedrockOreGrade.RAD_BYPRODUCT, type)), new Pair(ItemBedrockOreNew.make(BedrockOreGrade.RAD_ROASTED, type), new FluidStack(Fluids.VITRIOL, 50)));
		//}
	}
	public static void addRecipe(Item in, ItemStack outItem, FluidStack outFluid){
		recipes.put(new ComparableStack(in), new Pair<ItemStack, FluidStack>(outItem, outFluid));
	}

	public static void addRecipe(Block in, ItemStack outItem, FluidStack outFluid){
		recipes.put(new ComparableStack(in), new Pair<ItemStack, FluidStack>(outItem, outFluid));
	}

	public static void addRecipe(ComparableStack in, ItemStack outItem, FluidStack outFluid){
		recipes.put(in, new Pair<ItemStack, FluidStack>(outItem, outFluid));
	}

	public static void addRecipe(String in, ItemStack outItem, FluidStack outFluid){
		recipes.put(new OreDictStack(in), new Pair<ItemStack, FluidStack>(outItem, outFluid));
	}

	public static Pair<ItemStack, FluidStack> getOutput(ItemStack stack) {

		if(stack == null || stack.isEmpty())
			return null;

		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());

		if(recipes.containsKey(comp)) {
			Pair<ItemStack, FluidStack> out = recipes.get(comp);
			if(out != null) return new Pair<ItemStack, FluidStack>(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
		}

		String[] dictKeys = comp.getDictKeys();
		for(String key : dictKeys) {
			OreDictStack oreStack = new OreDictStack(key);
            Pair<ItemStack, FluidStack> out = recipes.get(oreStack);
			if(out != null) return new Pair<ItemStack, FluidStack>(out.getKey() == null ? null : out.getKey().copy(), out.getValue());
        }

		return null;
	}
}
