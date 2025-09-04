package com.hbm.inventory;

import com.hbm.blocks.ModBlocks;
import com.hbm.items.ModItems;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.RecipesCommon.ComparableStack;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;

import static com.hbm.inventory.OreDictManager.*;

public class LiquefactionRecipes {

    public static HashMap<Object, FluidStack> recipes = new HashMap();
    
    public static void registerDefaults() {

        //oil processing
        addRecipe(COAL.gem(),										ModForgeFluids.COALOIL, 100);
        addRecipe(COAL.dust(),									    ModForgeFluids.COALOIL, 100);
        addRecipe(LIGNITE.gem(),									ModForgeFluids.COALOIL, 50);
        addRecipe(LIGNITE.dust(),									ModForgeFluids.COALOIL, 50);
        addRecipe(KEY_OIL_TAR,									    ModForgeFluids.BITUMEN, 75);
        addRecipe(KEY_CRACK_TAR,									ModForgeFluids.BITUMEN, 100);
        addRecipe(KEY_COAL_TAR,									    ModForgeFluids.BITUMEN, 50);
        addRecipe(KNO.dust(),										ModForgeFluids.NITRIC_ACID, 250);
        //general utility recipes because why not
        addRecipe(new ComparableStack(Blocks.NETHERRACK),			FluidRegistry.LAVA, 250);
        addRecipe(new ComparableStack(Blocks.COBBLESTONE),		    FluidRegistry.LAVA, 250);
        addRecipe(new ComparableStack(Blocks.STONE),				FluidRegistry.LAVA, 250);
        addRecipe(new ComparableStack(Blocks.MAGMA),			    FluidRegistry.LAVA, 500);
        addRecipe(new ComparableStack(Blocks.OBSIDIAN),			    FluidRegistry.LAVA, 500);
        addRecipe(new ComparableStack(Items.SNOWBALL),			    FluidRegistry.WATER, 125);
        addRecipe(new ComparableStack(Blocks.SNOW),				    FluidRegistry.WATER, 500);
        addRecipe(new ComparableStack(Blocks.ICE),				    FluidRegistry.WATER, 1000);
        addRecipe(new ComparableStack(Blocks.PACKED_ICE),			FluidRegistry.WATER, 1000);
        addRecipe(new ComparableStack(ModBlocks.ore_oil_sand),	    ModForgeFluids.BITUMEN, 100);

        addRecipe(new ComparableStack(Items.SUGAR),				    ModForgeFluids.ETHANOL, 100);
        addRecipe(new ComparableStack(ModItems.biomass),			ModForgeFluids.BIOGAS, 125);
        addRecipe(new ComparableStack(Items.FISH, 1, OreDictionary.WILDCARD_VALUE), ModForgeFluids.FISHOIL, 100);
        addRecipe(new ComparableStack(Blocks.DOUBLE_PLANT, 1, 0),	ModForgeFluids.SUNFLOWEROIL, 100);


        addRecipe("oreClathrateGlowstone", "glowstone", 1000);
        addRecipe("dustGlowstone", "glowstone", 250);
        addRecipe("blockGlowstone", "glowstone", 1000);

        addRecipe("oreClathrateEnder", "ender", 1000);
        addRecipe("oreClathrateRedstone", "ender", 1000);
        addRecipe("enderpearl", "ender", 250);

        addRecipe("dustPetrotheum", "petrotheum", 250);
        addRecipe("dustAerotheum", "aerotheum", 250);
        addRecipe("dustPyrotheum", "pyrotheum", 250);
        addRecipe("dustCryotheum", "cryotheum", 250);

        addRecipe("blockRestone", "redstone", 900);
        addRecipe("dustRedstone", "redstone", 100);
        addRecipe("clathrateRedstone", "redstone", 1000);
        addRecipe("oreClathrateRedstone", "redstone", 1000);
//        addRecipe(new ComparableStack(Items.WHEAT_SEEDS),			50, Fluids.SEEDSLURRY);
//        addRecipe(new ComparableStack(Blocks.TALLGRASS, 1, 1),	100, Fluids.SEEDSLURRY);
//        addRecipe(new ComparableStack(Blocks.TALLGRASS, 1, 2),	100, Fluids.SEEDSLURRY);
//        addRecipe(new ComparableStack(Blocks.VINE),				100, Fluids.SEEDSLURRY);
        addRecipe(MUD.ingot(),	    ModForgeFluids.MUD_FLUID, 16_000);
    }

    public static void addRecipe(Object input, Fluid fluid, int amount){
        recipes.put(input, new FluidStack(fluid, amount));
    }
    public static void addRecipe(String input, String fluidName, int amount){
        if(FluidRegistry.isFluidRegistered(fluidName) && !OreDictionary.getOres(input).isEmpty()){
            recipes.put(input, new FluidStack(FluidRegistry.getFluid(fluidName), amount));
        }
    }

    public static FluidStack getOutput(ItemStack stack) {

        if(stack == null || stack.isEmpty())
            return null;

        ComparableStack comp = new ComparableStack(stack.getItem(), 1, stack.getItemDamage());
        if(recipes.containsKey(comp))
            return recipes.get(comp);

        String[] dictKeys = comp.getDictKeys();
        comp = new ComparableStack(stack.getItem(), 1, OreDictionary.WILDCARD_VALUE);
        if(recipes.containsKey(comp))
            return recipes.get(comp);

        for(String key : dictKeys) {
            if(recipes.containsKey(key))
                return recipes.get(key);
        }
        return null;
    }
}
