package com.hbm.items.machine;

import java.util.List;

import com.hbm.interfaces.IHasCustomModel;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import com.hbm.util.I18nUtil;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemChemistryTemplate extends Item implements IHasCustomModel {

	public static final ModelResourceLocation chemModel = new ModelResourceLocation(RefStrings.MODID + ":chemistry_template", "inventory");
	
	public ItemChemistryTemplate(String s){
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.templateTab);
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		String s = ("" + I18n.format(this.getUnlocalizedName() + ".name")).trim();
        String s1 = ("" + I18n.format("chem." + ChemplantRecipes.getName(stack))).trim();

        if (s1 != null) {
            s = s + " " + s1;
        }

        return s;
	}
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for (int i: ChemplantRecipes.recipeNames.keySet()){
	            list.add(new ItemStack(this, 1, i));
	        }
		}
	}
	
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(!(stack.getItem() instanceof ItemChemistryTemplate))
    			return;

	    	List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(stack);
	    	FluidStack[] fluidInputs = ChemplantRecipes.getFluidInputFromTempate(stack);
	    	ItemStack[] itemOutputs = ChemplantRecipes.getChemOutputFromTempate(stack);
	    	FluidStack[] fluidOutputs = ChemplantRecipes.getFluidOutputFromTempate(stack);
	    	int time = ChemplantRecipes.getProcessTime(stack);

	    	list.add("§6" + I18nUtil.resolveKey("info.templatefolder"));
			list.add("");

	    	try {
	    		list.add("§l" + I18nUtil.resolveKey("info.template_out_p"));
	    		if(itemOutputs != null){
	    			for(ItemStack ouputItem : itemOutputs){
	    				list.add(" §a"+ ouputItem.getCount() + "x " + ouputItem.getDisplayName());
	    			}
	    		}
	    		if(fluidOutputs != null){
	    			for(FluidStack outputFluid : fluidOutputs){
	    				list.add(" §b"+ outputFluid.amount + "mB " + outputFluid.getFluid().getLocalizedName(outputFluid));
	    			}
	    		}
	    		list.add("§l" + I18nUtil.resolveKey("info.template_in_p"));
	    		
	    		if(itemInputs != null){
	    			for(AStack o : itemInputs){
		    			if(o instanceof ComparableStack)  {
							ItemStack input = ((ComparableStack)o).toStack();
				    		list.add(" §c"+ input.getCount() + "x " + input.getDisplayName());

						} else if(o instanceof OreDictStack)  {
							OreDictStack input = (OreDictStack) o;
							NonNullList<ItemStack> ores = OreDictionary.getOres(input.name);

							if(ores.size() > 0) {
								ItemStack inStack = ores.get((int) (Math.abs(System.currentTimeMillis() / 1000) % ores.size()));
					    		list.add(" §c"+ input.count() + "x " + inStack.getDisplayName());
							} else {
					    		list.add("I AM ERROR - No OrdDict match found for "+o.toString());
							}
						}
					}
	    		}
	    		
    			if(fluidInputs != null){
    				for(FluidStack inputFluid : fluidInputs){
    					list.add(" §e" + inputFluid.amount + "mB " + inputFluid.getFluid().getLocalizedName(inputFluid));
    				}
    			}
	    		
	    		list.add("§l" + I18nUtil.resolveKey("info.template_time"));
	        	list.add(" §3"+ Math.floor((float)(time) / 20 * 100) / 100 + " " + I18nUtil.resolveKey("info.template_seconds"));
	    	} catch(Exception e) {
	    		list.add("###INVALID###");
	    		list.add("0x334077-0x6A298F-0xDF3795-0x334077");
	    	}
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return chemModel;
	}
}
