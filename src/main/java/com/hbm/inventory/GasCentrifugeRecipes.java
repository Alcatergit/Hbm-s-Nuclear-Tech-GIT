package com.hbm.inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;

//TODO: clean this shit up
//Alcater: on it
//Alcater: almost done yay
@Spaghetti("everything")
public class GasCentrifugeRecipes {

    public static HashMap<Fluid, GasCentRecipe> recipes = new HashMap();

    public static void registerRecipes(){
        addRecipe(ModForgeFluids.UF6,
                200, new Object[]{6, new ItemStack(ModItems.nugget_u238), 6, new ItemStack(ModItems.nugget_uranium_fuel), 4, new ItemStack(ModItems.fluorite)},
                200, new Object[]{11, new ItemStack(ModItems.nugget_u238), 1, new ItemStack(ModItems.nugget_u235), 4, new ItemStack(ModItems.fluorite)}
        );

        addRecipe(ModForgeFluids.PUF6,
                100, new Object[]{4, new ItemStack(ModItems.nugget_plutonium_fuel), 3, new ItemStack(ModItems.nugget_pu240), 3, new ItemStack(ModItems.fluorite)},
                100, new Object[]{3, new ItemStack(ModItems.nugget_pu238), 2, new ItemStack(ModItems.nugget_pu239), 4, new ItemStack(ModItems.nugget_pu240), 2, new ItemStack(ModItems.fluorite)}
        );
        addRecipe(ModForgeFluids.SAS3, 100, new Object[]{4, new ItemStack(ModItems.nugget_schrabidium), 4, new ItemStack(ModItems.nugget_schrabidium), 1, new ItemStack(ModItems.nugget_solinium), 2, new ItemStack(ModItems.sulfur)});
        addRecipe(ModForgeFluids.MUD_FLUID,
                1000, new Object[]{1, new ItemStack(ModItems.powder_iron), 1, new ItemStack(ModItems.powder_lead), 1, new ItemStack(ModItems.nuclear_waste_tiny), 2, new ItemStack(ModItems.dust)},
                1500, new Object[]{1, new ItemStack(ModItems.nugget_solinium), 2, new ItemStack(ModItems.nugget_uranium), 5, new ItemStack(ModItems.powder_lead), 8, new ItemStack(ModItems.dust)}
        );
        addRecipe(ModForgeFluids.COOLANT, 2000, new Object[]{1, new ItemStack(ModItems.niter), 1, new ItemStack(ModItems.niter), 1, new ItemStack(ModItems.niter), 1, new ItemStack(ModItems.niter)});
        addRecipe(ModForgeFluids.CRYOGEL, 1000, new Object[]{1, new ItemStack(ModItems.powder_ice), 1, new ItemStack(ModItems.powder_ice), 1, new ItemStack(ModItems.niter), 1, new ItemStack(ModItems.niter)});
        addRecipe(ModForgeFluids.NITAN, 500, new Object[]{1, new ItemStack(ModItems.powder_nitan_mix), 1, new ItemStack(ModItems.powder_nitan_mix), 1, new ItemStack(ModItems.powder_nitan_mix), 1, new ItemStack(ModItems.powder_nitan_mix)});
        addRecipe(ModForgeFluids.LIQUID_OSMIRIDIUM, 1000, new Object[]{1, new ItemStack(ModItems.powder_impure_osmiridium), 2, new ItemStack(ModItems.powder_meteorite), 4, new ItemStack(ModItems.powder_meteorite_tiny), 1, new ItemStack(ModItems.powder_paleogenite_tiny)});
    }


    public static void addRecipe(Fluid f, int amount, Object[] outputs){
        recipes.put(f, new GasCentRecipe(amount, toCentOutList(outputs)));
    }

    public static void addRecipe(Fluid f, int amountA, Object[] outputs, int amountB, Object[] outputsWithUpgrade){
        recipes.put(f, new GasCentRecipe(amountA, amountB, toCentOutList(outputs), toCentOutList(outputsWithUpgrade)));
    }

    public static List<GasCentOutput> toCentOutList(Object[] outputs){
        List<GasCentOutput> outputList = new ArrayList<GasCentOutput>();
        for(int i=0; i < outputs.length; i+=2){
            outputList.add(new GasCentOutput((Integer) outputs[i], (ItemStack) outputs[i+1], (i>>1)+1));
        }
        return outputList;
    }

    public static GasCentRecipe getGasCentRecipe(Fluid f){
        if(f != null) return recipes.get(f);
        return null;
    }

    public static class GasCentRecipe{
        public int amountA;
        public int amountB;
        public List<GasCentOutput> outputListA;
        public List<GasCentOutput> outputListB;
        public int totalWeightA;
        public int totalWeightB;

        public GasCentRecipe(int fluidAmountA, int fluidAmountB, List<GasCentOutput> outputs, List<GasCentOutput> outputsWithUpgrade) {
            amountA = fluidAmountA;
            amountB = fluidAmountB;
            outputListA = outputs;
            outputListB = outputsWithUpgrade;
            totalWeightA = countWeight(outputs);
            totalWeightB = countWeight(outputsWithUpgrade);
        }

        public GasCentRecipe(int fluidAmountA, List<GasCentOutput> outputs) {
            amountA = fluidAmountA;
            amountB = 0;
            outputListA = outputs;
            outputListB = null;
            totalWeightA = countWeight(outputs);
            totalWeightB = 0;
        }

        public int countWeight(List<GasCentOutput> outputs){
            int count = 0;
            for(GasCentOutput out : outputs){
                count += out.weight;
            }
            return count;
        }
    }
	
	public static class GasCentOutput {
		public int weight;
		public ItemStack output;
		public int slot;
		
		public GasCentOutput(int w, ItemStack s, int i) {
			weight = w;
			output = s;
			slot = i;
		}
	}

	public static int getFluidConsumedGasCent(boolean useB, Fluid fluid) {
		if(fluid == null)
			return 0;

        GasCentRecipe rec = recipes.get(fluid);
        if(rec != null) return useB && rec.amountB > 0 ? rec.amountB : rec.amountA;
        return 0;
	}
}
