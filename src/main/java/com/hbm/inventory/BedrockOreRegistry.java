package com.hbm.inventory;

import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.config.BedrockOreJsonConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.util.Tuple;
import com.hbm.util.Tuple.Pair;
import com.hbm.util.WeightedRandomObject;

import net.minecraft.init.Items;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fluids.FluidStack;
import org.lwjgl.Sys;

import static com.hbm.items.ModItems.*;

//TODO: clean this shit up
@Spaghetti("everything")
public class BedrockOreRegistry {

	public static HashMap<Integer, String> oreIndexes = new HashMap<>();
	public static HashMap<String, Integer> oreToIndexes = new HashMap<>();

	public static HashMap<String, String> oreResults = new HashMap<>();
	public static HashMap<String, Integer> oreColors = new HashMap<>();
	public static HashMap<String, Integer> oreTiers = new HashMap<>();
	
	public static HashMap<Pair<Integer, Integer>, List<WeightedRandomObject>> oreCasino = new HashMap<>();

    public static List<WeightedRandomObject> tierCasino = new ArrayList<>();

	public static void registerBedrockOres(){
		collectBedrockOres();
		fillOreCasino();
        fillTierCasino();
        registerBedrockOreOreDict();
	}

	public static boolean is3DBlock(String ore){
		boolean isBlock = false;
		for(ItemStack item : OreDictionary.getOres(ore))
			isBlock |= (item != null && !item.isEmpty() && item.getItem() instanceof ItemBlock);
        return isBlock;
    }

	public static boolean isActualItem(String ore){
		boolean isActualItem = false;
		for(ItemStack item : OreDictionary.getOres(ore))
			isActualItem |= (item != null && !item.isEmpty() && item.getItem() != Items.AIR);
        return isActualItem;
    }

	public static boolean tryRegister(int index, String oreName, String output){
		if(OreDictionary.doesOreNameExist(output) && isActualItem(output)){
			oreIndexes.put(index, oreName);
			oreToIndexes.put(oreName, index);
			oreResults.put(oreName, output);
			oreTiers.put(oreName, Math.max(1, 1+getDirectOreTier(oreName)));
			return true;
		}
		return false;
	}

    public static ItemStack getNugget(String oreName){
        if(oreName.equals("oreLead") || oreName.equals("oreCopper")) return new ItemStack(ModItems.nugget_cadmium, 1);
        if(oreName.equals("oreGold") || oreName.equals("oreTungsten")) return new ItemStack(ModItems.nugget_bismuth, 1);
        if(oreName.equals("oreUranium")) return new ItemStack(ModItems.nugget_ra226, 1);
        if(oreName.equals("oreThorium")) return new ItemStack(ModItems.nugget_technetium, 1);
        if(oreName.equals("oreStarmetal")) return new ItemStack(ModItems.powder_meteorite_tiny, 1);
        if(oreName.equals("oreRedstone")) return new ItemStack(ModItems.nugget_mercury, 1);
        if(oreName.equals("oreRedPhosphorus")) return new ItemStack(ModItems.nugget_arsenic, 1);
        if(oreName.equals("oreNeodymium")) return new ItemStack(ModItems.nugget_tantalium, 1);
        if(oreName.equals("oreCertusQuartz")) return new ItemStack(ModItems.nugget_silicon, 1);
        return new ItemStack(ModItems.dust, 1);
    }

    public static void registerBedrockOreOreDict(){
        for(Map.Entry<Integer, String> e: oreIndexes.entrySet()) {
            int oreMeta = e.getKey();
            String name = e.getValue().substring(3);
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_centrifuged, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_cleaned, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_separated, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_deepcleaned, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_purified, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_nitrated, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_nitrocrystalline, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_seared, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_exquisite, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_perfect, 1, oreMeta));
            OreDictionary.registerOre("bedrockOre"+name, new ItemStack(ore_bedrock_enriched, 1, oreMeta));
        }
    }

	public static void collectBedrockOres(){
		int index = 0;
		for(String oreName : OreDictionary.getOreNames()){
			if(oreName.startsWith("ore") && is3DBlock(oreName) && !CompatibilityConfig.bedrockOreBlacklist.contains(oreName)){

				String resourceName = oreName.substring(3);
                if(resourceName.startsWith("Nether")) continue;
				
				String oreOutput = "gem"+resourceName;
				if(tryRegister(index, oreName, oreOutput)){
					index++;
					continue;
				}

				oreOutput = "ingot"+resourceName;
				if(tryRegister(index, oreName, oreOutput)){
					index++;
					continue;
				}

				oreOutput = "dust"+resourceName;
				if(tryRegister(index, oreName, oreOutput)){
					index++;
					continue;
				}

				oreOutput = "item"+resourceName;
				if(tryRegister(index, oreName, oreOutput)){
					index++;
				}
			}
		}
	}

	public static int getOreIndex(String ore){
		Integer x = oreToIndexes.get(ore);
		if(x == null) return -1;
		return x;
	}

	public static int getOreTier(String ore){
		Integer x = oreTiers.get(ore);
		if(x == null) return 0;
		return x;
	}

	public static FluidStack getFluidRequirement(int tier){
		if(tier == 1) return new FluidStack(ModForgeFluids.ACID, 8000);
		if(tier == 2) return new FluidStack(ModForgeFluids.SULFURIC_ACID, 500);
		if(tier == 3) return new FluidStack(ModForgeFluids.NITRIC_ACID, 500);
		if(tier == 4) return new FluidStack(ModForgeFluids.RADIOSOLVENT, 200);
		if(tier == 5) return new FluidStack(ModForgeFluids.SCHRABIDIC, 200);
		if(tier == 6) return new FluidStack(ModForgeFluids.UU_MATTER, 200);
		if(tier > 6) return new FluidStack(ModForgeFluids.LIQUID_OSMIRIDIUM, 100);
		return new FluidStack(ModForgeFluids.SOLVENT, 300);
	}

	public static int getTierWeight(int tier){
		if(tier <= 1) return 64;
		if(tier == 2) return 48;
		if(tier == 3) return 32;
		if(tier == 4) return 8;
		if(tier == 5) return 4;
        if(tier == 6) return 2;
        return 1;
    }

	public static void fillOreCasino(){
        for(String oreName : oreResults.keySet()){
            for(Integer dimID : BedrockOreJsonConfig.dimOres.keySet()){
                if(BedrockOreJsonConfig.isOreAllowed(dimID, oreName)){
                    addOreToCasino(dimID, oreTiers.get(oreName), oreName);
                }
            }
		}
	}

    public static void addOreToCasino(int dimID, int tier, String oreName){
        List<WeightedRandomObject> oreWeights = oreCasino.computeIfAbsent(new Pair<>(dimID, tier), k -> new ArrayList<>());
        oreWeights.add(new WeightedRandomObject(oreName, 1));
    }

    public static void fillTierCasino() {
        for(int tier=1; tier<8; tier++){
            tierCasino.add(new WeightedRandomObject(tier, getTierWeight(tier)));
        }
    }

    public static int rollOreTier(Random rand){
        Integer i = WeightedRandom.getRandomItem(rand, tierCasino).asInteger();
        if(i == null) return -1;
        return i;
	}

	public static int getDirectOreTier(String oreName){
		int tierCount = 0;
		int tierSum = 0;
		List<ItemStack> outputs = OreDictionary.getOres(oreName);
		Block ore;
		for(ItemStack stack : outputs){
			ore = Block.getBlockFromItem(stack.getItem());
			int tier = ore.getHarvestLevel(ore.getDefaultState());
			if(tier > -1){
				tierSum += tier;
				tierCount++;
			}
		}
		if(tierCount > 0)
			return tierSum/tierCount;
		return 0;
	}

    public static int getRandomOreByTier(Random rand, int tier, int dim){
        List<WeightedRandomObject> oreWeights = oreCasino.get(new Pair<>(dim, tier));
        if(oreWeights == null){
            if(tier == 1) return -1;
            return getRandomOreByTier(rand, tier-1, dim);
        }
        return getOreIndex(WeightedRandom.getRandomItem(rand, oreWeights).asString());
    }

	public static String getOreName(String oreName){
		return oreName.substring(3).replaceAll("([A-Z])", " $1").trim();
	}

	public static void registerOreColors(){
		for(Map.Entry<String, String> entry : oreResults.entrySet()) {
			List<ItemStack> oreResult = OreDictionary.getOres(entry.getValue());
			if(!oreResult.isEmpty()){
				int color = Library.getColorFromItemStack(oreResult.get(0));
				oreColors.put(entry.getKey(), color);
			}
		}
		registerScannerOreColors();
	}
	//used by Resource Scanner Sat
	public static HashMap<String, Integer> oreScanColors = new HashMap<>();
	public static void registerScannerOreColors(){
		for(String entry : OreDictionary.getOreNames()) {
			if(!entry.startsWith("ore")) continue;
			List<ItemStack> oreResult = OreDictionary.getOres(entry);
			if(!oreResult.isEmpty()){
				int color = Library.getColorFromItemStack(oreResult.get(0));
				oreScanColors.put(entry, color);
			}
		}
	}

	public static int getOreScanColor(String ore){
		Integer x = oreScanColors.get(ore);
		if(x == null) return 0;
		return x;
	}

	public static ItemStack getResource(String ore){
		List<ItemStack> outputs = OreDictionary.getOres(oreResults.get(ore));
		if(!outputs.isEmpty()) return outputs.get(0);
		return new ItemStack(Items.AIR);
	}

	public static int getOreColor(String ore){
		Integer x = oreColors.get(ore);
		if(x == null) return 0xFFFFFF;
		return x;
	}
}
