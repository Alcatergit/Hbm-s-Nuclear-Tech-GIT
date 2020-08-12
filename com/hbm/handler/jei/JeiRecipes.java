package com.hbm.handler.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.BreederRecipes;
import com.hbm.inventory.BreederRecipes.BreederRecipe;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.MachineRecipes.GasCentOutput;
import com.hbm.inventory.MagicRecipes;
import com.hbm.inventory.MagicRecipes.MagicRecipe;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemFluidIcon;
import com.hbm.items.special.ItemCell;
import com.hbm.lib.Library;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.main.MainRegistry;

import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

public class JeiRecipes {

	private static List<ChemRecipe> chemRecipes = null;
	private static List<CyclotronRecipe> cyclotronRecipes = null;
	private static List<PressRecipe> pressRecipes = null;
	private static List<AlloyFurnaceRecipe> alloyFurnaceRecipes = null;
	private static List<BoilerRecipe> boilerRecipes = null;
	private static List<CMBFurnaceRecipe> cmbRecipes = null;
	private static List<GasCentRecipe> gasCentRecipes = null;
	private static List<ReactorRecipe> reactorRecipes = null;
	private static List<RefineryRecipe> refineryRecipes = null;
	private static List<FluidRecipe> fluidEquivalences = null;
	private static List<BookRecipe> bookRecipes = null;
	
	private static List<ItemStack> batteries = null;
	private static Map<Integer, List<ItemStack>> reactorFuelMap = new HashMap<Integer, List<ItemStack>>();
	private static List<ItemStack> blades = null;
	
	
	public static class ChemRecipe implements IRecipeWrapper {
		
		private final List<ItemStack> inputs;
		private final List<ItemStack> outputs;
		
		public ChemRecipe(List<ItemStack> inputs, List<ItemStack> outputs) {
			this.inputs = inputs;
			this.outputs = outputs; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutputs(VanillaTypes.ITEM, outputs);
		}
		
	}
	
	public static class CyclotronRecipe implements IRecipeWrapper {
		
		private final List<ItemStack> inputs;
		private final ItemStack output;
		
		public CyclotronRecipe(List<ItemStack> inputs, ItemStack output) {
			this.inputs = inputs;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class PressRecipe implements IRecipeWrapper {

		private final List<ItemStack> stamps;
		private final ItemStack input;
		private final ItemStack output;
		
		public PressRecipe(List<ItemStack> stamps, ItemStack input, ItemStack output) {
			this.stamps = stamps;
			this.input = input;
			this.output = output; 
		}
		
		public List<ItemStack> getStamps() {
			return stamps;
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class AlloyFurnaceRecipe implements IRecipeWrapper {
		
		private final List<ItemStack> inputs;
		private final ItemStack output;
		
		public AlloyFurnaceRecipe(List<ItemStack> inputs, ItemStack output) {
			this.inputs = inputs;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class BoilerRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final ItemStack output;
		
		public BoilerRecipe(ItemStack input, ItemStack output) {
			this.input = input;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class CMBFurnaceRecipe implements IRecipeWrapper {
		
		private final List<ItemStack> inputs;
		private final ItemStack output;
		
		public CMBFurnaceRecipe(List<ItemStack> inputs, ItemStack output) {
			this.inputs = inputs;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class GasCentRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final List<ItemStack> outputs;
		
		public GasCentRecipe(ItemStack input, List<ItemStack> outputs) {
			this.input = input;
			this.outputs = outputs; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutputs(VanillaTypes.ITEM, outputs);
		}
		
	}
	
	public static class ReactorRecipe implements IRecipeWrapper {
		
		public static IDrawableStatic heatTex;
		
		private final ItemStack input;
		private final ItemStack output;
		public final int heat;
		
		public ReactorRecipe(ItemStack input, ItemStack output, int heat) {
			this.input = input;
			this.output = output; 
			this.heat = heat;
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
		@Override
		public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
			heatTex.draw(minecraft, 1, 20, 16-heat*4, 0, 0, 0);
		}
		
	}
	
	public static class RefineryRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final List<ItemStack> outputs;
		
		public RefineryRecipe(ItemStack input, List<ItemStack> outputs) {
			this.input = input;
			this.outputs = outputs; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, input);
			ingredients.setOutputs(VanillaTypes.ITEM, outputs);
		}
		
	}
	
	public static class FluidRecipe implements IRecipeWrapper {
		
		protected final FluidStack input;
		protected final ItemStack output;
		
		public FluidRecipe(FluidStack input, ItemStack output) {
			this.input = input;
			this.output = output; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.FLUID, input);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	public static class FluidRecipeInverse extends FluidRecipe implements IRecipeWrapper {
		
		public FluidRecipeInverse(FluidStack input, ItemStack output) {
			super(input, output);
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInput(VanillaTypes.ITEM, output);
			ingredients.setOutput(VanillaTypes.FLUID, input);
		}
		
	}
	
	public static class AssemblerRecipeWrapper implements IRecipeWrapper {

		ItemStack output;
		List<ItemStack> inputs;
		int time;
		
		public AssemblerRecipeWrapper(ItemStack output, List<ItemStack> inputs, int time) {
			this.output = output;
			this.inputs = inputs;
			this.time = time;
		}
		
		public AssemblerRecipeWrapper(ItemStack output, AStack[] inputs, int time) {
			this.output = output;
			List<ItemStack> list = new ArrayList<>(inputs.length);
			for(AStack s : inputs)
				list.add(s.getStack());
			this.inputs = list;
			this.time = time;
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			List<ItemStack> in = Library.copyItemStackList(inputs);
			while(in.size() < 12)
				in.add(new ItemStack(ModItems.nothing));
			int index = -1;
			for(int i = 0; i < AssemblerRecipes.recipeList.size(); i++){
				if(AssemblerRecipes.recipeList.get(i).isApplicable(output)){
					index = i;
					break;
				}
			}
			if(index >= 0)
				in.add(ItemAssemblyTemplate.getTemplate(index));
			else {
				in.add(new ItemStack(ModItems.nothing));
			}
			ingredients.setInputs(VanillaTypes.ITEM, in);
			ingredients.setOutput(VanillaTypes.ITEM, output.copy());
		}
		
	}
	
	public static class BookRecipe implements IRecipeWrapper {

		List<ItemStack> inputs;
		ItemStack output;
		
		public BookRecipe(MagicRecipe recipe) {
			inputs = new ArrayList<>(4);
			for(int i = 0; i < recipe.in.size(); i ++)
				inputs.add(recipe.in.get(i).getStack());
			while(inputs.size() < 4)
				inputs.add(new ItemStack(ModItems.nothing));
			output = recipe.getResult();
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			ingredients.setInputs(VanillaTypes.ITEM, inputs);
			ingredients.setOutput(VanillaTypes.ITEM, output);
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static List<ChemRecipe> getChemistryRecipes() {
		if(chemRecipes != null)
			return chemRecipes;
		chemRecipes = new ArrayList<ChemRecipe>();
		
        for (int i = 0; i < ItemChemistryTemplate.EnumChemistryTemplate.values().length; ++i)
        {
        	List<ItemStack> inputs = new ArrayList<ItemStack>(7);
        	for(int j = 0; j < 7; j ++)
        		inputs.add(j, new ItemStack(ModItems.nothing));
        	List<ItemStack> outputs = new ArrayList<ItemStack>(6);
        	for(int j = 0; j < 6; j ++)
        		outputs.add(j, new ItemStack(ModItems.nothing));
        	
        	inputs.set(6, new ItemStack(ModItems.chemistry_template, 1, i));
        	
        	List<ItemStack> listIn = MachineRecipes.getChemInputFromTempate(inputs.get(6));
        	if(listIn != null)
        		for(int j = 0; j < listIn.size(); j++)
        			if(listIn.get(j) != null)
        				inputs.set(j + 2, listIn.get(j).copy());
        	FluidStack[] fluidIn = MachineRecipes.getFluidInputFromTempate(inputs.get(6));
        	for(int j = 0; j < fluidIn.length; j++)
        		if(fluidIn[j] != null)
        			inputs.set(j, ItemFluidIcon.getStackWithQuantity(fluidIn[j].getFluid(), fluidIn[j].amount));
        	
        	ItemStack[] listOut = MachineRecipes.getChemOutputFromTempate(inputs.get(6));
        	for(int j = 0; j < listOut.length; j++)
        		if(listOut[j] != null)
        			outputs.set(j + 2, listOut[j].copy());
        	
        	FluidStack[] fluidOut = MachineRecipes.getFluidOutputFromTempate(inputs.get(6));
        	for(int j = 0; j < fluidOut.length; j++)
        		if(fluidOut[j] != null)
        			outputs.set(j, ItemFluidIcon.getStackWithQuantity(fluidOut[j].getFluid(), fluidOut[j].amount));
        	
        	chemRecipes.add(new ChemRecipe(inputs, outputs));
        }
		
		return chemRecipes;
	}
	
	public static List<CyclotronRecipe> getCyclotronRecipes() {
		if(cyclotronRecipes != null)
			 return cyclotronRecipes;
		
		cyclotronRecipes = new ArrayList<CyclotronRecipe>();
		
		Map<ItemStack[], ItemStack> recipes = new HashMap<ItemStack[], ItemStack>();
		Item part = ModItems.part_lithium;
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_schrabidium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_schrabidium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_reiium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_reiium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				ItemCell.getFullCell(ModForgeFluids.amat));

		part = ModItems.part_beryllium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_weidanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_weidanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				ItemCell.getFullCell(ModForgeFluids.amat));
		
		part = ModItems.part_carbon;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_australium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_australium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_strontium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_strontium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				ItemCell.getFullCell(ModForgeFluids.amat));
		
		part = ModItems.part_copper;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.sulfur) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.sulfur)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.niter) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.niter)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.fluorite) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.fluorite)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_coal) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_coal)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iron) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iron)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_gold) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_gold)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_quartz) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_quartz)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_titanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_titanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_copper) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_copper)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tungsten) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tungsten)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_aluminium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_aluminium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lead) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lead)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_beryllium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_beryllium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lithium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lithium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_iodine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_iodine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_thorium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_thorium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neodymium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neodymium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_astatine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_astatine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_caesium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_caesium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_verticium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_verticium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cobalt) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cobalt)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_bromine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_bromine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_niobium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_niobium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_tennessine) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_tennessine)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_cerium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_cerium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_actinium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_actinium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_lanthanium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_lanthanium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				ItemCell.getFullCell(ModForgeFluids.amat));
		
		part = ModItems.part_plutonium;
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_uranium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_uranium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_plutonium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_plutonium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_neptunium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_neptunium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.powder_unobtainium) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), new ItemStack(ModItems.powder_unobtainium)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), ItemCell.getFullCell(ModForgeFluids.amat) },
				MachineRecipes.getCyclotronOutput(new ItemStack(part), ItemCell.getFullCell(ModForgeFluids.amat)));
		
		recipes.put(new ItemStack[] { new ItemStack(part), new ItemStack(ModItems.nothing) },
				ItemCell.getFullCell(ModForgeFluids.amat));
		
		for(Entry<ItemStack[], ItemStack> entry : recipes.entrySet()){
			List<ItemStack> inputs = new ArrayList<ItemStack>(entry.getKey().length);
			for(ItemStack s : entry.getKey())
				inputs.add(s);
			cyclotronRecipes.add(new CyclotronRecipe(inputs, entry.getValue()));
		}
		
		return cyclotronRecipes;
	}
	
	@SuppressWarnings("unchecked")
	public static List<PressRecipe> getPressRecipes() {
		if(pressRecipes != null)
			return pressRecipes;
		pressRecipes = new ArrayList<PressRecipe>();
		Map<Object[], ItemStack> recipes = new HashMap<Object[], ItemStack>();

		List<ItemStack> i_stamps_flat = new ArrayList<ItemStack>();
		for(Item i : MachineRecipes.stamps_flat)
			i_stamps_flat.add(new ItemStack(i));
		List<ItemStack> i_stamps_plate = new ArrayList<ItemStack>();
		for(Item i : MachineRecipes.stamps_plate)
			i_stamps_plate.add(new ItemStack(i));
		List<ItemStack> i_stamps_wire = new ArrayList<ItemStack>();
		for(Item i : MachineRecipes.stamps_wire)
			i_stamps_wire.add(new ItemStack(i));
		List<ItemStack> i_stamps_circuit = new ArrayList<ItemStack>();
		for(Item i : MachineRecipes.stamps_circuit)
			i_stamps_circuit.add(new ItemStack(i));

		List<ItemStack> i_stamps_357 = new ArrayList<ItemStack>();
		i_stamps_357.add(new ItemStack(ModItems.stamp_357));
		List<ItemStack> i_stamps_44 = new ArrayList<ItemStack>();
		i_stamps_44.add(new ItemStack(ModItems.stamp_44));
		List<ItemStack> i_stamps_9 = new ArrayList<ItemStack>();
		i_stamps_9.add(new ItemStack(ModItems.stamp_9));
		List<ItemStack> i_stamps_50 = new ArrayList<ItemStack>();
		i_stamps_50.add(new ItemStack(ModItems.stamp_50));
		
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_coal) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_coal));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_quartz) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_quartz));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_lapis) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_lapis));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_diamond) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_diamond));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_emerald) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_emerald));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.pellet_coal) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.pellet_coal));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.biomass) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.biomass));
		recipes.put(new Object[] { i_stamps_flat, new ItemStack(ModItems.powder_lignite) }, getPressResultNN(MachineRecipes.stamps_flat.get(0), ModItems.powder_lignite));

		recipes.put(new Object[] { i_stamps_plate, new ItemStack(Items.IRON_INGOT) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), Items.IRON_INGOT));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(Items.GOLD_INGOT) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), Items.GOLD_INGOT));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_titanium) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_titanium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_aluminium) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_aluminium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_steel) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_steel));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_lead) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_lead));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_copper) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_copper));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_advanced_alloy) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_advanced_alloy));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_schrabidium) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_schrabidium));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_combine_steel) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_combine_steel));
		recipes.put(new Object[] { i_stamps_plate, new ItemStack(ModItems.ingot_saturnite) }, getPressResultNN(MachineRecipes.stamps_plate.get(0), ModItems.ingot_saturnite));

		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_aluminium) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_aluminium));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_copper) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_copper));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_tungsten) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_tungsten));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_red_copper) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_red_copper));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(Items.GOLD_INGOT) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), Items.GOLD_INGOT));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_schrabidium) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_schrabidium));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_advanced_alloy) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_advanced_alloy));
		recipes.put(new Object[] { i_stamps_wire, new ItemStack(ModItems.ingot_magnetized_tungsten) }, getPressResultNN(MachineRecipes.stamps_wire.get(0), ModItems.ingot_magnetized_tungsten));

		recipes.put(new Object[] { i_stamps_circuit, new ItemStack(ModItems.circuit_raw) }, getPressResultNN(MachineRecipes.stamps_circuit.get(0), ModItems.circuit_raw));

		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_iron) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_iron));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_steel) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_steel));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_lead) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_lead));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_gold) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_gold));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_schrabidium) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_schrabidium));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.ingot_steel) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.ingot_steel));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_nightmare) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_nightmare));
		recipes.put(new Object[] { i_stamps_357, new ItemStack(ModItems.assembly_desh) }, getPressResultNN(i_stamps_357.get(0).getItem(), ModItems.assembly_desh));

		recipes.put(new Object[] { i_stamps_44, new ItemStack(ModItems.assembly_nopip) }, getPressResultNN(i_stamps_44.get(0).getItem(), ModItems.assembly_nopip));
		//recipes.put(new Object[] { i_stamps_44, new ItemStack(ModItems.assembly_pip) }, getPressResultNN(i_stamps_44.get(0).getItem(), ModItems.assembly_pip));

		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_smg) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_smg));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_uzi) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_uzi));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_lacunae) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_lacunae));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(Items.GOLD_INGOT) }, getPressResultNN(i_stamps_9.get(0).getItem(), Items.GOLD_INGOT));
		recipes.put(new Object[] { i_stamps_9, new ItemStack(ModItems.assembly_556) }, getPressResultNN(i_stamps_9.get(0).getItem(), ModItems.assembly_556));
		
		recipes.put(new Object[] { i_stamps_50, new ItemStack(ModItems.assembly_actionexpress) }, getPressResultNN(i_stamps_50.get(0).getItem(), ModItems.assembly_actionexpress));
		recipes.put(new Object[] { i_stamps_50, new ItemStack(ModItems.assembly_calamity) }, getPressResultNN(i_stamps_50.get(0).getItem(), ModItems.assembly_calamity));
		
		for(Map.Entry<Object[], ItemStack> entry : recipes.entrySet()){
			pressRecipes.add(new PressRecipe((List<ItemStack>) entry.getKey()[0], (ItemStack) entry.getKey()[1], entry.getValue()));
		}
		
		return pressRecipes;
	}
	
	public static ItemStack getPressResultNN(ItemStack stamp, ItemStack input) {
		return MachineRecipes.getPressResult(input, stamp) == null ? new ItemStack(ModItems.nothing) : MachineRecipes.getPressResult(input, stamp);
	}

	public static ItemStack getPressResultNN(Item stamp, Item input) {
		return MachineRecipes.getPressResult(new ItemStack(input), new ItemStack(stamp)) == null ? new ItemStack(ModItems.nothing) : MachineRecipes.getPressResult(new ItemStack(input), new ItemStack(stamp));
	}
	
	public static List<AlloyFurnaceRecipe> getAlloyRecipes() {
		if(alloyFurnaceRecipes != null)
			return alloyFurnaceRecipes;
		alloyFurnaceRecipes = new ArrayList<AlloyFurnaceRecipe>();
		Map<ItemStack[], ItemStack> recipes = new HashMap<ItemStack[], ItemStack>();
		
		if (MainRegistry.enableDebugMode) {
			recipes.put(new ItemStack[] { new ItemStack(Items.IRON_INGOT), new ItemStack(Items.QUARTZ) },
					new ItemStack(Item.getItemFromBlock(ModBlocks.test_render)));
		}
		try {
			recipes.put(new ItemStack[] { new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL) },
					MachineRecipes.getFurnaceOutput(new ItemStack(Items.IRON_INGOT), new ItemStack(Items.COAL)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_lead), new ItemStack(ModItems.ingot_copper)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.plate_lead), new ItemStack(ModItems.plate_copper)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.COAL) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_tungsten), new ItemStack(Items.COAL)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_copper), new ItemStack(Items.REDSTONE) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_copper), new ItemStack(Items.REDSTONE)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_red_copper), new ItemStack(ModItems.ingot_steel)).copy());
			recipes.put(new ItemStack[] { ItemFluidCanister.getFullCanister(ModForgeFluids.diesel), new ItemStack(Items.SLIME_BALL) },
					MachineRecipes.getFurnaceOutput(ItemFluidCanister.getFullCanister(ModForgeFluids.diesel), new ItemStack(Items.SLIME_BALL)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_tungsten), new ItemStack(ModItems.nugget_schrabidium)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.plate_mixed), new ItemStack(ModItems.plate_gold)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_tungsten) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_tungsten)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_cobalt) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_steel), new ItemStack(ModItems.ingot_cobalt)).copy());
			recipes.put(new ItemStack[] { new ItemStack(ModItems.ingot_saturnite), new ItemStack(ModItems.powder_meteorite) },
					MachineRecipes.getFurnaceOutput(new ItemStack(ModItems.ingot_saturnite), new ItemStack(ModItems.powder_meteorite)).copy());
		} catch (Exception x) {
			MainRegistry.logger.error("Unable to register alloy recipes for NEI!");
		}
		for(Map.Entry<ItemStack[], ItemStack> entry : recipes.entrySet()){
			List<ItemStack> inputs = new ArrayList<ItemStack>(2);
			for(ItemStack stack : entry.getKey())
				inputs.add(stack);
			alloyFurnaceRecipes.add(new AlloyFurnaceRecipe(inputs, entry.getValue()));
		}
		return alloyFurnaceRecipes;
	}
	
	public static ArrayList<ItemStack> getAlloyFuels() {
		ArrayList<ItemStack> fuels = new ArrayList<ItemStack>();
		fuels.add(new ItemStack(Items.COAL));
		fuels.add(new ItemStack(Blocks.COAL_BLOCK));
		fuels.add(new ItemStack(Items.LAVA_BUCKET));
		fuels.add(new ItemStack(Items.BLAZE_ROD));
		fuels.add(new ItemStack(Items.BLAZE_POWDER));
		fuels.add(new ItemStack(ModItems.lignite));
		fuels.add(new ItemStack(ModItems.powder_lignite));
		fuels.add(new ItemStack(ModItems.briquette_lignite));
		fuels.add(new ItemStack(ModItems.coke));
		fuels.add(new ItemStack(ModItems.solid_fuel));
		fuels.add(new ItemStack(ModItems.powder_coal));
		return fuels;
	}
	
	public static List<BoilerRecipe> getBoilerRecipes() {
		if(boilerRecipes != null)
			return boilerRecipes;
		boilerRecipes = new ArrayList<BoilerRecipe>();
		
		for(Fluid f : FluidRegistry.getRegisteredFluids().values()){
			Object[] outs = MachineRecipes.getBoilerOutput(f);
			if(outs != null){
				boilerRecipes.add(new BoilerRecipe(ItemFluidIcon.getStackWithQuantity(f, (Integer) outs[2]), ItemFluidIcon.getStackWithQuantity((Fluid) outs[0], (Integer) outs[1])));
			}
		}
		
		return boilerRecipes;
	}
	
	public static List<ItemStack> getBatteries() {
		if(batteries != null)
			return batteries;
		batteries = new ArrayList<ItemStack>();
		batteries.add(new ItemStack(ModItems.battery_potato));
		batteries.add(new ItemStack(ModItems.battery_potatos));
		batteries.add(new ItemStack(ModItems.battery_su));
		batteries.add(new ItemStack(ModItems.battery_su_l));
		batteries.add(new ItemStack(ModItems.battery_generic));
		batteries.add(new ItemStack(ModItems.battery_red_cell));
		batteries.add(new ItemStack(ModItems.battery_red_cell_6));
		batteries.add(new ItemStack(ModItems.battery_red_cell_24));
		batteries.add(new ItemStack(ModItems.battery_advanced));
		batteries.add(new ItemStack(ModItems.battery_advanced_cell));
		batteries.add(new ItemStack(ModItems.battery_advanced_cell_4));
		batteries.add(new ItemStack(ModItems.battery_advanced_cell_12));
		batteries.add(new ItemStack(ModItems.battery_lithium));
		batteries.add(new ItemStack(ModItems.battery_lithium_cell));
		batteries.add(new ItemStack(ModItems.battery_lithium_cell_3));
		batteries.add(new ItemStack(ModItems.battery_lithium_cell_6));
		batteries.add(new ItemStack(ModItems.battery_schrabidium));
		batteries.add(new ItemStack(ModItems.battery_schrabidium_cell));
		batteries.add(new ItemStack(ModItems.battery_schrabidium_cell_2));
		batteries.add(new ItemStack(ModItems.battery_schrabidium_cell_4));
		batteries.add(new ItemStack(ModItems.battery_spark));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_6));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_25));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_100));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_1000));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_10000));
		batteries.add(new ItemStack(ModItems.battery_spark_cell_power));
		batteries.add(new ItemStack(ModItems.fusion_core));
		batteries.add(new ItemStack(ModItems.energy_core));
		return batteries;
	}
	
	public static List<CMBFurnaceRecipe> getCMBRecipes() {
		if(cmbRecipes != null)
			return cmbRecipes;
		cmbRecipes = new ArrayList<CMBFurnaceRecipe>();
		
		cmbRecipes.add(new CMBFurnaceRecipe(Arrays.asList(new ItemStack(ModItems.ingot_advanced_alloy), new ItemStack(ModItems.ingot_magnetized_tungsten)), new ItemStack(ModItems.ingot_combine_steel, 4)));
		cmbRecipes.add(new CMBFurnaceRecipe(Arrays.asList(new ItemStack(ModItems.powder_advanced_alloy), new ItemStack(ModItems.powder_magnetized_tungsten)), new ItemStack(ModItems.ingot_combine_steel, 4)));
		
		return cmbRecipes;
	}
	
	public static List<GasCentRecipe> getGasCentrifugeRecipes() {
		if(gasCentRecipes != null)
			return gasCentRecipes;
		gasCentRecipes = new ArrayList<GasCentRecipe>();
		
		for(Fluid f : FluidRegistry.getRegisteredFluids().values()){
			List<GasCentOutput> outputs = MachineRecipes.getGasCentOutput(f);
			
			if(outputs != null){
				int totalWeight = 0;
				
				for(GasCentOutput o : outputs) {
					totalWeight += o.weight;
				}
				
				ItemStack input = ItemFluidIcon.getStackWithQuantity(f, MachineRecipes.getFluidConsumedGasCent(f) * totalWeight);
				
				List<ItemStack> result = new ArrayList<ItemStack>(4);
				
				for(GasCentOutput o : outputs){
					ItemStack stack = o.output.copy();
					stack.setCount(stack.getCount() * o.weight);
					result.add(stack);
				}
				
				gasCentRecipes.add(new GasCentRecipe(input, result));
			}
		}
		
		return gasCentRecipes;
	}
	
	public static List<BookRecipe> getBookRecipes(){
		if(bookRecipes != null)
			return bookRecipes;
		bookRecipes = new ArrayList<>();
		for(MagicRecipe m : MagicRecipes.getRecipes()){
			bookRecipes.add(new BookRecipe(m));
		}
		return bookRecipes;
	}
	
	public static List<ReactorRecipe> getReactorRecipes(){
		if(reactorRecipes != null)
			return reactorRecipes;
		reactorRecipes = new ArrayList<ReactorRecipe>();
		
		for(Map.Entry<ItemStack, BreederRecipe> entry : BreederRecipes.getAllRecipes().entrySet()){
			reactorRecipes.add(new ReactorRecipe(entry.getKey(), entry.getValue().output, entry.getValue().heat));
		}
		
		return reactorRecipes;
	}
	
	public static List<ItemStack> getReactorFuels(int heat){
		if(reactorFuelMap.containsKey(heat))
			return reactorFuelMap.get(heat);
		reactorFuelMap.put(heat, BreederRecipes.getAllFuelsFromHEAT(heat));
		return reactorFuelMap.get(heat);
	}
	
	public static List<RefineryRecipe> getRefineryRecipe() {
		if(refineryRecipes != null)
			return refineryRecipes;
		refineryRecipes = new ArrayList<RefineryRecipe>(1);
        
        refineryRecipes.add(new RefineryRecipe(ItemFluidIcon.getStackWithQuantity(ModForgeFluids.hotoil, 1000), 
        		Arrays.asList(
        				ItemFluidIcon.getStackWithQuantity(ModForgeFluids.heavyoil, 500),
        				ItemFluidIcon.getStackWithQuantity(ModForgeFluids.naphtha, 250),
        				ItemFluidIcon.getStackWithQuantity(ModForgeFluids.lightoil, 150),
        				ItemFluidIcon.getStackWithQuantity(ModForgeFluids.petroleum, 100),
        				new ItemStack(ModItems.sulfur)
        				)));
		
		return refineryRecipes;
	}
	
	public static List<ItemStack> getBlades() {
		if(blades != null)
			return blades;
		
		blades = new ArrayList<ItemStack>();
		blades.add(new ItemStack(ModItems.blades_advanced_alloy));
		blades.add(new ItemStack(ModItems.blades_aluminum));
		blades.add(new ItemStack(ModItems.blades_combine_steel));
		blades.add(new ItemStack(ModItems.blades_gold));
		blades.add(new ItemStack(ModItems.blades_iron));
		blades.add(new ItemStack(ModItems.blades_steel));
		blades.add(new ItemStack(ModItems.blades_titanium));
		blades.add(new ItemStack(ModItems.blades_schrabidium));
		return blades;
	}
	
	public static List<FluidRecipe> getFluidEquivalences(){
		if(fluidEquivalences != null)
			return fluidEquivalences;
		fluidEquivalences = new ArrayList<FluidRecipe>();
		
		for(Fluid f : FluidRegistry.getRegisteredFluids().values()){
			fluidEquivalences.add(new FluidRecipe(new FluidStack(f, 1000), ItemFluidIcon.getStack(f)));
			fluidEquivalences.add(new FluidRecipeInverse(new FluidStack(f, 1000), ItemFluidIcon.getStack(f)));
		}
		
		return fluidEquivalences;
	}
	
}
