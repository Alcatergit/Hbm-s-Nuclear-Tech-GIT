package com.hbm.inventory;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.Library;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

//TODO: clean this shit up
@Spaghetti("everything")
public class BedrockOreRegistry {

	public static String NTM_BEDROCK_ORE_KEY = "NTM_BEDROCK_ORE";

	public static HashMap<Integer, String> oreIndexes = new HashMap<>();
	public static HashMap<String, String> oreResults = new HashMap<>();
	public static HashMap<String, Integer> oreColors = new HashMap<>();
	public static HashMap<String, String> oreNames = new HashMap<>();
	

	public static void registerBedrockOres(){
		collectBedrockOres();
	}

	public static void collectBedrockOres(){
		int index = 0;
		for(String oreName : OreDictionary.getOreNames()){
			if(oreName.startsWith("ore")){
				String resourceName = oreName.substring(3);
				
				String oreGem = "gem"+resourceName;
				if(OreDictionary.doesOreNameExist(oreGem)){
					oreIndexes.put(index, oreName);
					index++;
					oreResults.put(oreName, oreGem);
					continue;
				}

				String oreDust = "dust"+resourceName;
				if(OreDictionary.doesOreNameExist(oreDust)){
					oreIndexes.put(index, oreName);
					index++;
					oreResults.put(oreName, oreDust);
					continue;
				}

				String oreIngot = "ingot"+resourceName;
				if(OreDictionary.doesOreNameExist(oreIngot)){
					oreIndexes.put(index, oreName);
					index++;
					oreResults.put(oreName, oreIngot);
					continue;
				}
			}
		}
	}

	public static void registerOreColors(){
		for(Map.Entry<String, String> entry : oreResults.entrySet()) {
			List<ItemStack> oreResult = OreDictionary.getOres(entry.getValue());
			if(oreResult.size() > 0){
				int color = Library.getColorFromItemStack(oreResult.get(0));
				oreColors.put(entry.getKey(), color);
			}
		}
	}

	public static ItemStack getResource(String ore){
		List<ItemStack> outputs = OreDictionary.getOres(oreResults.get(ore));
		if(outputs.size() > 0) return outputs.get(0);
		return new ItemStack(Items.AIR);
	}
}
