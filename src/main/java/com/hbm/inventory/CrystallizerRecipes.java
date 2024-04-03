package com.hbm.inventory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;
import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemBedrockOre;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidRegistry;

//This time we're doing this right
//...right?

//yes this time i will
public class CrystallizerRecipes {

	//'Object' is either a ComparableStack or the String for the ore dict
	private static LinkedHashMap<Object, ItemStack> itemOutputRecipes = new LinkedHashMap<Object, ItemStack>();
	private static HashMap<Object, FluidStack> fluidInputRecipes = new HashMap<Object, FluidStack>();
	private static HashSet<Fluid> allowedFluids = new HashSet<Fluid>();
	private static List<CrystallizerRecipe> jeiCrystalRecipes = null;

	public static void register() {
		addRecipe(COAL.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_coal));
		addRecipe(IRON.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_iron));
		addRecipe(GOLD.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_gold));
		addRecipe(REDSTONE.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_redstone));
		addRecipe(LAPIS.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_lapis));
		addRecipe(DIAMOND.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_diamond));
		addRecipe(U.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_uranium));
		addRecipe(TH232.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_thorium));
		addRecipe(PU.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_plutonium));
		addRecipe(TI.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_titanium));
		addRecipe(S.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_sulfur));
		addRecipe(KNO.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_niter));
		addRecipe(CU.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_copper));
		addRecipe(W.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_tungsten));
		addRecipe(AL.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_aluminium));
		addRecipe(F.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_fluorite));
		addRecipe(BE.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_beryllium));
		addRecipe(PB.ore(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_lead));
		addRecipe(SA326.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_schrabidium));
		addRecipe(LI.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_lithium));
		addRecipe(STAR.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_starmetal));
		addRecipe(RAREEARTH.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_rare));
		addRecipe(CO.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_cobalt));
		addRecipe(CINNABAR.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_cinnebar));
		addRecipe(ASBESTOS.ore(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_asbestos));
		
		addRecipe(new ComparableStack(ModBlocks.ore_nether_fire), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_phosphorus));
		addRecipe(new ComparableStack(ModBlocks.ore_tikite), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.crystal_trixite));
		addRecipe(SRN.ingot(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.crystal_schraranium));
		
		addRecipe(KEY_SAND, new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.ingot_fiberglass));
		addRecipe(new ComparableStack(Blocks.COBBLESTONE), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModBlocks.reinforced_stone));
		addRecipe(new ComparableStack(ModBlocks.gravel_obsidian), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModBlocks.brick_obsidian));
		addRecipe(REDSTONE.block(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.nugget_mercury));
		
		addRecipe(CINNABAR.gem(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.nugget_mercury, 3));
		addRecipe(COAL.block(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModBlocks.block_graphite));
		addRecipe(new ComparableStack(ModBlocks.stone_gneiss), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.powder_lithium));
		
		addRecipe(BORAX.dust(), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.powder_boron_tiny, 3));
		addRecipe(DIAMOND.dust(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(Items.DIAMOND));
		addRecipe(EMERALD.dust(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(Items.EMERALD));
		addRecipe(LAPIS.dust(), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(Items.DYE, 1, 4));
		addRecipe(new ComparableStack(ModItems.powder_semtex_mix), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.ingot_semtex));
		addRecipe(new ComparableStack(ModItems.powder_desh_ready), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.ingot_desh));
		addRecipe(new ComparableStack(ModItems.powder_meteorite), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(ModItems.fragment_meteorite, 1));
		addRecipe(CD.dust(), new FluidStack(ModForgeFluids.fishoil, 250), new ItemStack(ModItems.ingot_rubber));
		addRecipe(new ComparableStack(ModItems.powder_impure_osmiridium), new FluidStack(ModForgeFluids.schrabidic, 1000), new ItemStack(ModItems.crystal_osmiridium, 1));
		addRecipe(new ComparableStack(ModItems.meteorite_sword_treated), new FluidStack(ModForgeFluids.sulfuric_acid, 8000), new ItemStack(ModItems.meteorite_sword_etched, 1));
		
		addRecipe(new ComparableStack(Items.SNOWBALL), new FluidStack(FluidRegistry.WATER, 8000), new ItemStack(Blocks.ICE, 1));
		addRecipe(new ComparableStack(Blocks.ICE), new FluidStack(ModForgeFluids.coolant, 500), new ItemStack(Blocks.PACKED_ICE, 1));
		addRecipe(KEY_GRAVEL, new FluidStack(FluidRegistry.LAVA, 1000), new ItemStack(ModBlocks.gravel_obsidian, 1));
		addRecipe(new ComparableStack(Items.ROTTEN_FLESH), new FluidStack(ModForgeFluids.acid, 500), new ItemStack(Items.LEATHER));
		addRecipe(new ComparableStack(Items.BONE), new FluidStack(ModForgeFluids.sulfuric_acid, 1000), new ItemStack(Items.SLIME_BALL, 16));
		addRecipe(new ComparableStack(Items.DYE, 1, 15), new FluidStack(ModForgeFluids.sulfuric_acid, 250), new ItemStack(Items.SLIME_BALL, 4));
		addRecipe(new ComparableStack(ModItems.powder_lithium, 1), new FluidStack(ModForgeFluids.heatingoil, 4000), new ItemStack(ModItems.crystal_redstone, 1));
		addRecipe(new ComparableStack(ModItems.coal_infernal), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.solid_fuel));
		addRecipe(new ComparableStack(ModBlocks.block_fallout), new FluidStack(ModForgeFluids.corium_fluid, 8000), new ItemStack(ModItems.powder_balefire));
		
		addRecipe(new ComparableStack(Blocks.DIRT), new FluidStack(ModForgeFluids.colloid, 1000), new ItemStack(Blocks.CLAY));
		addRecipe(new ComparableStack(ModBlocks.sand_quartz), new FluidStack(ModForgeFluids.nitroglycerin, 1000), new ItemStack(ModItems.ball_dynamite, 16));
		addRecipe(NETHERQUARTZ.dust(), new FluidStack(ModForgeFluids.nitroglycerin, 250), new ItemStack(ModItems.ball_dynamite, 4));

		List<ItemStack> quartz = OreDictionary.getOres("crystalCertusQuartz");
		
		if(quartz != null && !quartz.isEmpty()) {
			ItemStack qItem = quartz.get(0).copy();
			qItem.setCount(6);
			addRecipe("oreCertusQuartz", new FluidStack(ModForgeFluids.acid, 500), qItem);
		}

		for(Integer oreMeta : BedrockOreRegistry.oreIndexes.keySet()) {
			addRecipe(new ComparableStack(ModItems.ore_bedrock_centrifuged, 1, oreMeta), new FluidStack(ModForgeFluids.acid, 8000), new ItemStack(ModItems.ore_bedrock_cleaned, 1, oreMeta));
			addRecipe(new ComparableStack(ModItems.ore_bedrock_separated, 1, oreMeta), new FluidStack(ModForgeFluids.sulfuric_acid, 500), new ItemStack(ModItems.ore_bedrock_deepcleaned, 1, oreMeta));
			addRecipe(new ComparableStack(ModItems.ore_bedrock_purified, 1, oreMeta), new FluidStack(ModForgeFluids.nitric_acid, 250), new ItemStack(ModItems.ore_bedrock_nitrated, 1, oreMeta));
			addRecipe(new ComparableStack(ModItems.ore_bedrock_nitrocrystalline, 1, oreMeta), new FluidStack(ModForgeFluids.solvent, 100), new ItemStack(ModItems.ore_bedrock_seared, 1, oreMeta));
			addRecipe(new ComparableStack(ModItems.ore_bedrock_exquisite, 1, oreMeta), new FluidStack(ModForgeFluids.radiosolvent, 50), new ItemStack(ModItems.ore_bedrock_perfect, 1, oreMeta));
		}
	}

	public static void addRecipe(Object itemInput, FluidStack fluidInput, ItemStack itemOutput){
		itemOutputRecipes.put(itemInput, itemOutput);
		fluidInputRecipes.put(itemInput, fluidInput);
		allowedFluids.add(fluidInput.getFluid());
	}


	public static ItemStack getOutputItem(ItemStack stack) {

		if(stack == null || stack.getItem() == null || stack.isEmpty())
			return null;

		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());

		if(itemOutputRecipes.containsKey(comp))
			return itemOutputRecipes.get(comp).copy();

		String[] dictKeys = comp.getDictKeys();

		for(String key : dictKeys) {

			if(itemOutputRecipes.containsKey(key))
				return itemOutputRecipes.get(key).copy();
		}

		return null;
	}

	public static FluidStack getOutputFluid(ItemStack stack) {

		if(stack == null || stack.getItem() == null || stack.isEmpty())
			return null;

		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());

		if(fluidInputRecipes.containsKey(comp))
			return fluidInputRecipes.get(comp).copy();

		String[] dictKeys = comp.getDictKeys();

		for(String key : dictKeys) {

			if(fluidInputRecipes.containsKey(key))
				return fluidInputRecipes.get(key).copy();
		}

		return null;
	}

	public static boolean isAllowedFluid(Fluid f){
		if(f != null){
			return allowedFluids.contains(f);
		}
		return false;
	}

	public static List<CrystallizerRecipe> getRecipes() {
		if(jeiCrystalRecipes != null)
			return jeiCrystalRecipes;
		jeiCrystalRecipes = new ArrayList<CrystallizerRecipe>();

		for(Entry<Object, ItemStack> entry : CrystallizerRecipes.itemOutputRecipes.entrySet()) {
			List<ItemStack> ingredients;
			if(entry.getKey() instanceof String) {
				String oreKey = (String)entry.getKey();
				ingredients = OreDictionary.getOres(oreKey);
			}else{
				ItemStack stack = ((ComparableStack)entry.getKey()).toStack();
				ingredients = new ArrayList<ItemStack>();
				ingredients.add(stack);
			}
			ItemStack inputFluid = ItemFluidIcon.getStackWithQuantity(fluidInputRecipes.get(entry.getKey()));
			ItemStack outputItem = entry.getValue();

			List<List<ItemStack>> totalInput = new ArrayList<List<ItemStack>>();
			totalInput.add(ingredients);
			totalInput.add(Arrays.asList(inputFluid));


			jeiCrystalRecipes.add(new CrystallizerRecipe(totalInput, outputItem));

		}

		return jeiCrystalRecipes;
	}
	
	public static class CrystallizerRecipe implements IRecipeWrapper {
		
		
		public final List<List<ItemStack>> inputs;
		private final ItemStack output;
		
		
		public CrystallizerRecipe(List<List<ItemStack>> inputs, ItemStack output) {
			this.inputs = inputs;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputLists(VanillaTypes.ITEM, inputs);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}

}