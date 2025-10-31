package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.items.ModItems;
import com.hbm.items.ModItems.BedrockOreV2;
import com.hbm.items.ModItems.Materials.Crystals;
import com.hbm.items.ModItems.Materials.Ingots;
import com.hbm.items.ModItems.Materials.Nuggies;
import com.hbm.items.ModItems.Materials.Powders;
import com.hbm.items.special.ItemBedrockOre;
import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Grade;
import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Type;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import static com.hbm.inventory.OreDictManager.*;

public class CentrifugeRecipes {

	private static HashMap<Object, ItemStack[]> recipes = new HashMap<Object, ItemStack[]>();
	private static List<CentrifugeRecipe> centrifugeRecipes = null;

	public static void registerBedrock() {
		for (V2Type type : V2Type.values()) {
			Item item = BedrockOreV2.types[type.ordinal()];
			{ // RAW
				{
					ComparableStack stack0 = new ComparableStack(item,1,V2Grade.BASE.ordinal());
					ComparableStack stack1 = new ComparableStack(item,1,V2Grade.BASE_ROASTED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(item,1,V2Grade.PRIMARY.ordinal()),
							new ItemStack(Blocks.GRAVEL,1)
					};
					recipes.put(stack0,stacks);
					recipes.put(stack1,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.BASE_WASHED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(item,1,V2Grade.PRIMARY.ordinal()),
							new ItemStack(item,1,V2Grade.PRIMARY.ordinal()),
							new ItemStack(Blocks.GRAVEL,1)
					};
					recipes.put(stack,stacks);
				}
			}
			{ // PRIMARY FRACTION
				// FROM RAW
				{
					ComparableStack stack0 = new ComparableStack(item,1,V2Grade.PRIMARY.ordinal());
					ComparableStack stack1 = new ComparableStack(item,1,V2Grade.PRIMARY_ROASTED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.primary1.amount,type.primary1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary2.amount,type.primary2.mat.id)
					};
					recipes.put(stack0,stacks);
					recipes.put(stack1,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.PRIMARY_SULFURIC.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(item,2,V2Grade.PRIMARY_NOSULFURIC.ordinal()),
							new ItemStack(item,2,V2Grade.SULFURIC_BYPRODUCT.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack0 = new ComparableStack(item,1,V2Grade.PRIMARY_NOSULFURIC.ordinal());
					ComparableStack stack1 = new ComparableStack(item,1,V2Grade.PRIMARY_NOSOLVENT.ordinal());
					ComparableStack stack2 = new ComparableStack(item,1,V2Grade.PRIMARY_NORAD.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.primary1.amount,type.primary1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary2.amount,type.primary2.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack0,stacks);
					recipes.put(stack1,stacks);
					recipes.put(stack2,stacks);
				}
				// OTHER
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.PRIMARY_SOLVENT.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(item,2,V2Grade.PRIMARY_NOSOLVENT.ordinal()),
							new ItemStack(item,2,V2Grade.SULFURIC_BYPRODUCT.ordinal()),
							new ItemStack(item,2,V2Grade.SOLVENT_BYPRODUCT.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.PRIMARY_RAD.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(item,2,V2Grade.PRIMARY_NORAD.ordinal()),
							new ItemStack(item,2,V2Grade.SULFURIC_BYPRODUCT.ordinal()),
							new ItemStack(item,2,V2Grade.SOLVENT_BYPRODUCT.ordinal()),
							new ItemStack(item,2,V2Grade.RAD_BYPRODUCT.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.PRIMARY_FIRST.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.primary1.amount,type.primary1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary1.amount,type.primary1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary2.amount,type.primary2.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.PRIMARY_SECOND.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.primary1.amount,type.primary1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary2.amount,type.primary2.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.primary2.amount,type.primary2.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack,stacks);
				}
			}
			{ // WASHED BYPRODUCT
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.SULFURIC_WASHED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.byproductAcid1.amount,type.byproductAcid1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductAcid2.amount,type.byproductAcid2.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductAcid3.amount,type.byproductAcid3.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.SOLVENT_WASHED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.byproductSolvent1.amount,type.byproductSolvent1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductSolvent2.amount,type.byproductSolvent2.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductSolvent3.amount,type.byproductSolvent3.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack,stacks);
				}
				{
					ComparableStack stack = new ComparableStack(item,1,V2Grade.RAD_WASHED.ordinal());
					ItemStack[] stacks = new ItemStack[]{
							new ItemStack(BedrockOreV2.fragment,type.byproductRad1.amount,type.byproductRad1.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductRad2.amount,type.byproductRad2.mat.id),
							new ItemStack(BedrockOreV2.fragment,type.byproductRad3.amount,type.byproductRad3.mat.id),
							new ItemStack(item,1,V2Grade.CRUMBS.ordinal())
					};
					recipes.put(stack,stacks);
				}
			}
		}
	}
	
	public static void register() {
		
		recipes.put(new ComparableStack(ModItems.waste_uranium), new ItemStack[] {
				new ItemStack(Nuggies.nugget_pu_mix, 2),
				new ItemStack(Nuggies.nugget_plutonium, 1),
				new ItemStack(Nuggies.nugget_technetium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) });
		
		recipes.put(new ComparableStack(ModItems.waste_plutonium), new ItemStack[] {
				new ItemStack(Nuggies.nugget_pu_mix, 1),
				new ItemStack(Nuggies.nugget_pu_mix, 1),
				new ItemStack(Nuggies.nugget_technetium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) });
		
		recipes.put(new ComparableStack(ModItems.waste_mox), new ItemStack[] {
				new ItemStack(Nuggies.nugget_pu_mix, 1),
				new ItemStack(Nuggies.nugget_technetium, 1),
				new ItemStack(Nuggies.nugget_u238, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 3) });
		
		recipes.put(new ComparableStack(ModItems.waste_schrabidium), new ItemStack[] {
				new ItemStack(Nuggies.nugget_beryllium, 2),
				new ItemStack(Nuggies.nugget_lead, 1),
				new ItemStack(Nuggies.nugget_solinium, 1),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) });
		
		recipes.put(new ComparableStack(ModItems.waste_thorium), new ItemStack[] {
				new ItemStack(Nuggies.nugget_u238, 1),
				new ItemStack(Nuggies.nugget_th232, 1),
				new ItemStack(Nuggies.nugget_u233, 2),
				new ItemStack(ModItems.nuclear_waste_tiny, 2) });
		
		recipes.put(new ComparableStack(Powders.powder_cloud), new ItemStack[] {
				new ItemStack(Powders.powder_copper, 1),
				new ItemStack(ModItems.sulfur, 1),
				new ItemStack(ModItems.dust, 1),
				new ItemStack(ModItems.dust, 1) });

		recipes.put(COAL.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_coal, 2),
				new ItemStack(Powders.powder_coal, 2),
				new ItemStack(Powders.powder_coal, 2),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(LIGNITE.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_lignite, 2),
				new ItemStack(Powders.powder_lignite, 2),
				new ItemStack(Powders.powder_lignite, 2),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(IRON.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(GOLD.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_gold, 1),
				new ItemStack(Powders.powder_gold, 1),
				new ItemStack(Powders.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(DIAMOND.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_diamond, 1),
				new ItemStack(Powders.powder_diamond, 1),
				new ItemStack(Powders.powder_diamond, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(EMERALD.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_emerald, 1),
				new ItemStack(Powders.powder_emerald, 1),
				new ItemStack(Powders.powder_emerald, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(TI.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_titanium, 1),
				new ItemStack(Powders.powder_titanium, 1),
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put("oreQuartz", new ItemStack[] {
				new ItemStack(Powders.powder_quartz, 1),
				new ItemStack(Powders.powder_quartz, 1),
				new ItemStack(Powders.powder_lithium_tiny, 1),
				new ItemStack(Blocks.NETHERRACK, 1) });
		
		recipes.put(W.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_tungsten, 1),
				new ItemStack(Powders.powder_tungsten, 1),
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(CU.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_copper, 1),
				new ItemStack(Powders.powder_copper, 1),
				new ItemStack(Powders.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(AL.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_aluminium, 1),
				new ItemStack(Powders.powder_aluminium, 1),
				new ItemStack(Powders.powder_iron, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(PB.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_lead, 1),
				new ItemStack(Powders.powder_lead, 1),
				new ItemStack(Powders.powder_gold, 1),
				new ItemStack(Blocks.GRAVEL, 1) });

		recipes.put(ASBESTOS.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_asbestos, 1),
				new ItemStack(Powders.powder_asbestos, 1),
				new ItemStack(Powders.powder_boron_tiny, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(SA326.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_schrabidium, 1),
				new ItemStack(Powders.powder_schrabidium, 1),
				new ItemStack(Nuggies.nugget_solinium, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(RAREEARTH.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_desh_mix, 1),
				new ItemStack(Nuggies.nugget_zirconium, 1),
				new ItemStack(Nuggies.nugget_zirconium, 1),
				new ItemStack(Blocks.GRAVEL, 1) });

		recipes.put(CINNABAR.ore(), new ItemStack[] {
				new ItemStack(ModItems.cinnebar, 2),
				new ItemStack(ModItems.cinnebar, 2),
				new ItemStack(Items.REDSTONE, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(PU.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_plutonium, 1),
				new ItemStack(Powders.powder_plutonium, 1),
				new ItemStack(Nuggies.nugget_polonium, 3),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(U.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_uranium, 1),
				new ItemStack(Powders.powder_uranium, 1),
				new ItemStack(Nuggies.nugget_ra226, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(TH232.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_thorium, 1),
				new ItemStack(Powders.powder_thorium, 1),
				new ItemStack(Powders.powder_uranium, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(BE.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_beryllium, 1),
				new ItemStack(Powders.powder_beryllium, 1),
				new ItemStack(Powders.powder_emerald, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(REDSTONE.ore(), new ItemStack[] {
				new ItemStack(Items.REDSTONE, 3),
				new ItemStack(Items.REDSTONE, 3),
				new ItemStack(Nuggies.nugget_mercury, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(new ComparableStack(ModBlocks.ore_tikite), new ItemStack[] {
				new ItemStack(Powders.powder_plutonium, 2),
				new ItemStack(Powders.powder_cobalt, 2),
				new ItemStack(Powders.powder_nitan_mix, 1),
				new ItemStack(Blocks.END_STONE, 1) });
		
		recipes.put(LAPIS.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_lapis, 3),
				new ItemStack(Powders.powder_lapis, 3),
				new ItemStack(Powders.powder_cobalt_tiny, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
		
		recipes.put(STAR.ore(), new ItemStack[] {
				new ItemStack(Powders.powder_dura_steel, 3),
				new ItemStack(Powders.powder_astatine, 1),
				new ItemStack(Powders.powder_cobalt, 2),
				new ItemStack(Blocks.GRAVEL, 1) });

		recipes.put(new ComparableStack(Powders.powder_tektite), new ItemStack[] {
				new ItemStack(Powders.powder_paleogenite_tiny, 2),
				new ItemStack(Powders.powder_meteorite_tiny, 1),
				new ItemStack(Powders.powder_meteorite_tiny, 1),
				new ItemStack(ModItems.dust, 6) });
		
		recipes.put(new ComparableStack(ModBlocks.block_euphemium_cluster), new ItemStack[] {
				new ItemStack(Nuggies.nugget_euphemium, 7),
				new ItemStack(Powders.powder_schrabidium, 4),
				new ItemStack(Ingots.ingot_starmetal, 2),
				new ItemStack(Nuggies.nugget_solinium, 2) });
		
		recipes.put(P_RED.ore(), new ItemStack[] {
				new ItemStack(Items.BLAZE_POWDER, 2),
				new ItemStack(Powders.powder_fire, 2),
				new ItemStack(Ingots.ingot_phosphorus),
				new ItemStack(Blocks.NETHERRACK) });
		
		recipes.put(new ComparableStack(Items.BLAZE_ROD), new ItemStack[] {new ItemStack(Items.BLAZE_POWDER, 1), new ItemStack(Items.BLAZE_POWDER, 1), new ItemStack(Powders.powder_fire, 1), new ItemStack(Powders.powder_fire, 1) });
		
		recipes.put(SRN.ingot(), new ItemStack[] { new ItemStack(Nuggies.nugget_schrabidium, 2), new ItemStack(Nuggies.nugget_schrabidium, 1), new ItemStack(Nuggies.nugget_uranium, 3), new ItemStack(Nuggies.nugget_plutonium, 2) });
		
		recipes.put(COAL.crystal(), new ItemStack[] { new ItemStack(Powders.powder_coal, 3), new ItemStack(Powders.powder_coal, 3), new ItemStack(Powders.powder_coal, 3), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(IRON.crystal(), new ItemStack[] { new ItemStack(Powders.powder_iron, 2), new ItemStack(Powders.powder_iron, 2), new ItemStack(Powders.powder_titanium, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(GOLD.crystal(), new ItemStack[] { new ItemStack(Powders.powder_gold, 2), new ItemStack(Powders.powder_gold, 2), new ItemStack(Nuggies.nugget_mercury, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(REDSTONE.crystal(), new ItemStack[] { new ItemStack(Items.REDSTONE, 3), new ItemStack(Items.REDSTONE, 3), new ItemStack(Items.REDSTONE, 3), new ItemStack(Nuggies.nugget_mercury, 3) });
		recipes.put(LAPIS.crystal(), new ItemStack[] { new ItemStack(Powders.powder_lapis, 8), new ItemStack(Powders.powder_lapis, 8), new ItemStack(Powders.powder_cobalt, 1), new ItemStack(Blocks.GRAVEL, 1) });
		recipes.put(DIAMOND.crystal(), new ItemStack[] { new ItemStack(Powders.powder_diamond, 1), new ItemStack(Powders.powder_diamond, 1), new ItemStack(Powders.powder_diamond, 1), new ItemStack(Powders.powder_diamond, 1) });
		recipes.put(U.crystal(), new ItemStack[] { new ItemStack(Powders.powder_uranium, 2), new ItemStack(Powders.powder_uranium, 2), new ItemStack(Nuggies.nugget_ra226, 2), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(TH232.crystal(), new ItemStack[] { new ItemStack(Powders.powder_thorium, 2), new ItemStack(Powders.powder_thorium, 2), new ItemStack(Powders.powder_uranium, 1), new ItemStack(Nuggies.nugget_ra226, 1) });
		recipes.put(PU.crystal(), new ItemStack[] { new ItemStack(Powders.powder_plutonium, 2), new ItemStack(Powders.powder_plutonium, 2), new ItemStack(Powders.powder_polonium, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(TI.crystal(), new ItemStack[] { new ItemStack(Powders.powder_titanium, 2), new ItemStack(Powders.powder_titanium, 2), new ItemStack(Powders.powder_iron, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(S.crystal(), new ItemStack[] { new ItemStack(ModItems.sulfur, 4), new ItemStack(ModItems.sulfur, 4), new ItemStack(Powders.powder_iron, 1), new ItemStack(Nuggies.nugget_mercury, 1) });
		recipes.put(KNO.crystal(), new ItemStack[] { new ItemStack(ModItems.niter, 3), new ItemStack(ModItems.niter, 3), new ItemStack(ModItems.niter, 3), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(CU.crystal(), new ItemStack[] { new ItemStack(Powders.powder_copper, 2), new ItemStack(Powders.powder_copper, 2), new ItemStack(ModItems.sulfur, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(W.crystal(), new ItemStack[] { new ItemStack(Powders.powder_tungsten, 2), new ItemStack(Powders.powder_tungsten, 2), new ItemStack(Powders.powder_iron, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(AL.crystal(), new ItemStack[] { new ItemStack(Powders.powder_aluminium, 2), new ItemStack(Powders.powder_aluminium, 2), new ItemStack(Powders.powder_iron, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(F.crystal(), new ItemStack[] { new ItemStack(Ingots.fluorite, 3), new ItemStack(Ingots.fluorite, 3), new ItemStack(Ingots.fluorite, 3), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(BE.crystal(), new ItemStack[] { new ItemStack(Powders.powder_beryllium, 2), new ItemStack(Powders.powder_beryllium, 2), new ItemStack(Powders.powder_quartz, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(PB.crystal(), new ItemStack[] { new ItemStack(Powders.powder_lead, 2), new ItemStack(Powders.powder_lead, 2), new ItemStack(Powders.powder_gold, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(SRN.crystal(), new ItemStack[] { new ItemStack(Nuggies.nugget_schrabidium, 2), new ItemStack(Nuggies.nugget_schrabidium, 2), new ItemStack(Nuggies.nugget_uranium, 2), new ItemStack(Nuggies.nugget_plutonium, 2) });
		recipes.put(SA326.crystal(), new ItemStack[] { new ItemStack(Powders.powder_schrabidium, 2), new ItemStack(Powders.powder_schrabidium, 2), new ItemStack(Powders.powder_plutonium, 1), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(RAREEARTH.crystal(), new ItemStack[] { new ItemStack(Powders.powder_desh_mix, 1), new ItemStack(Powders.powder_desh_mix, 1), new ItemStack(Nuggies.nugget_zirconium, 2), new ItemStack(Nuggies.nugget_zirconium, 2) });
		recipes.put(CINNABAR.crystal(), new ItemStack[] { new ItemStack(ModItems.cinnebar, 3), new ItemStack(ModItems.cinnebar, 3), new ItemStack(Items.REDSTONE, 2), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(P_RED.crystal(), new ItemStack[] { new ItemStack(Powders.powder_fire, 3), new ItemStack(Powders.powder_fire, 3), new ItemStack(Ingots.ingot_phosphorus, 2), new ItemStack(Items.BLAZE_POWDER, 2) });
		recipes.put(new ComparableStack(Crystals.crystal_trixite), new ItemStack[] { new ItemStack(Powders.powder_plutonium, 3), new ItemStack(Powders.powder_cobalt, 3), new ItemStack(Powders.powder_spark_mix, 1), new ItemStack(Powders.powder_nitan_mix, 2) });
		recipes.put(LI.crystal(), new ItemStack[] { new ItemStack(Powders.powder_lithium, 2), new ItemStack(Powders.powder_lithium, 2), new ItemStack(Powders.powder_quartz, 1), new ItemStack(Ingots.fluorite, 1) });
		recipes.put(STAR.crystal(), new ItemStack[] { new ItemStack(Powders.powder_dura_steel, 3), new ItemStack(Powders.powder_cobalt, 3), new ItemStack(Powders.powder_astatine, 2), new ItemStack(Nuggies.nugget_mercury, 5) });
		recipes.put(CO.crystal(), new ItemStack[] { new ItemStack(Powders.powder_cobalt, 2), new ItemStack(Powders.powder_iron, 3), new ItemStack(Powders.powder_copper, 3), new ItemStack(Powders.powder_lithium_tiny, 1) });
		recipes.put(ASBESTOS.crystal(), new ItemStack[] { new ItemStack(Powders.powder_asbestos, 2), new ItemStack(Powders.powder_asbestos, 2), new ItemStack(Powders.powder_boron_tiny, 1), new ItemStack(Blocks.GRAVEL, 1) });

		for(Entry<Integer, String> entry : BedrockOreRegistry.oreIndexes.entrySet()) {
			int oreMeta = entry.getKey();
			String oreName = entry.getValue();
 			recipes.put(new ComparableStack(ModItems.ore_bedrock, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_centrifuged, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_centrifuged, 1, oreMeta), 
				new ItemStack(Blocks.GRAVEL, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_cleaned, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_separated, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_separated, 1, oreMeta), 
				new ItemStack(Blocks.GRAVEL, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_deepcleaned, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_purified, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_purified, 1, oreMeta), 
				new ItemStack(Blocks.GRAVEL, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_nitrated, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_nitrocrystalline, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_nitrocrystalline, 1, oreMeta), 
				getNugget(oreName), 
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_seared, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_exquisite, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_exquisite, 1, oreMeta), 
				getNugget(oreName),
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_perfect, 1, oreMeta), new ItemStack[] { 
				new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 
				new ItemStack(ModItems.ore_bedrock_enriched, 1, oreMeta), 
				new ItemStack(Blocks.GRAVEL, 1),
				new ItemStack(Blocks.GRAVEL, 1) });
			recipes.put(new ComparableStack(ModItems.ore_bedrock_enriched, 1, oreMeta), new ItemStack[] { 
				ItemBedrockOre.getOut(oreMeta, 1), 
				ItemBedrockOre.getOut(oreMeta, 1), 
				ItemBedrockOre.getOut(oreMeta, 1), 
				ItemBedrockOre.getOut(oreMeta, 1) });
		}
		registerBedrock();
	}

	public static ItemStack getNugget(String oreName){
		if(oreName.equals("oreLead") || oreName.equals("oreCopper")) return new ItemStack(Nuggies.nugget_cadmium, 1);
		if(oreName.equals("oreGold") || oreName.equals("oreTungsten")) return new ItemStack(Nuggies.nugget_bismuth, 1);
		if(oreName.equals("oreUranium")) return new ItemStack(Nuggies.nugget_ra226, 1);
		if(oreName.equals("oreThorium")) return new ItemStack(Nuggies.nugget_technetium, 1);
		if(oreName.equals("oreStarmetal")) return new ItemStack(Powders.powder_meteorite_tiny, 1);
		if(oreName.equals("oreRedstone")) return new ItemStack(Nuggies.nugget_mercury, 1);
		if(oreName.equals("oreRedPhosphorus")) return new ItemStack(Nuggies.nugget_arsenic, 1);
		if(oreName.equals("oreNeodymium")) return new ItemStack(Nuggies.nugget_tantalium, 1);
		return new ItemStack(Blocks.GRAVEL, 1);
	}

	public static void addRecipe(ItemStack in, ItemStack[] outputs){
		recipes.put(new ComparableStack(in), outputs);
	}

	public static void removeRecipe(ItemStack in){
		recipes.remove(new ComparableStack(in));
	}
	
	public static ItemStack[] getOutput(ItemStack stack) {
		
		if(stack == null || stack.getItem() == null)
			return null;
	
		ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
		if(recipes.containsKey(comp))
			return RecipesCommon.copyStackArray(recipes.get(comp));
		
		String[] dictKeys = comp.getDictKeys();
		
		for(String key : dictKeys) {

			if(recipes.containsKey(key))
				return RecipesCommon.copyStackArray(recipes.get(key));
		}
		
		return null;
	}

	public static List<CentrifugeRecipe> getCentrifugeRecipes() {
		if(centrifugeRecipes != null)
			return centrifugeRecipes;
		centrifugeRecipes = new ArrayList<CentrifugeRecipe>();
		
		for(Entry<Object, ItemStack[]> entry : CentrifugeRecipes.recipes.entrySet()) {
			
			if(entry.getKey() instanceof String) {
				List<ItemStack> ingredients = OreDictionary.getOres((String)entry.getKey());
				centrifugeRecipes.add(new CentrifugeRecipe(ingredients, Arrays.asList(entry.getValue())));
			} else {
				centrifugeRecipes.add(new CentrifugeRecipe(((ComparableStack)entry.getKey()).toStack(), Arrays.asList(entry.getValue())));
			}
		}
		
		return centrifugeRecipes;
	}
	
	public static class CentrifugeRecipe implements IRecipeWrapper {
		
		private final ItemStack input;
		private final List<ItemStack> outputs;
		public final List<ItemStack> inputs;
		
		public CentrifugeRecipe(ItemStack input, List<ItemStack> outputs) {
			this.input = input;
			this.inputs = null;
			this.outputs = outputs; 
		}
		
		public CentrifugeRecipe(List<ItemStack> inputs, List<ItemStack> outputs) {
			this.inputs = inputs;
			this.input = ItemStack.EMPTY;
			this.outputs = outputs; 
		}
		
		@Override
		public void getIngredients(IIngredients ingredients) {
			if(inputs != null){
				ingredients.setInputLists(VanillaTypes.ITEM, Arrays.asList(inputs));
			} else {
				ingredients.setInput(VanillaTypes.ITEM, input);
			}
			ingredients.setOutputs(VanillaTypes.ITEM, outputs);
		}
		
	}
}