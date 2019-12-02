package com.hbm.items.tool;

import java.util.Arrays;
import java.util.List;

import com.hbm.interfaces.IHasCustomModel;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class ItemAssemblyTemplate extends Item implements IHasCustomModel {

	public static final ModelResourceLocation location = new ModelResourceLocation(RefStrings.MODID + ":assembly_template", "inventory");
	
	// TODO Yeah, uh, this a big one. I'll do it later.
	public enum EnumAssemblyTemplate {
		IRON_PLATE(30, Arrays.asList(new ItemStack(Items.IRON_INGOT, 3)), new ItemStack(ModItems.plate_iron, 2));

		private EnumAssemblyTemplate() {
		}

		private EnumAssemblyTemplate(int time, List<ItemStack> ingredients, ItemStack output) {
			this.time = time;
			this.ingredients = ingredients;
			this.output = output;
		}

		private int time = 0;
		private List<ItemStack> ingredients = null;
		private ItemStack output = null;

		public static EnumAssemblyTemplate getEnum(int i) {
			return EnumAssemblyTemplate.values()[i];
		}

		public List<ItemStack> getIngredients() {
			return this.ingredients;
		}

		public int getTime() {
			return this.time;
		}

		public ItemStack getOutput() {
			return this.output;
		}

		public String getName() {
			return this.toString();
		}
	}

	public ItemAssemblyTemplate(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(MainRegistry.templateTab);
		
		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		// TODO Find the real way of translating to local
		String s = ("" + I18n.format(this.getUnlocalizedName() + ".name")).trim();
		ItemStack out = MachineRecipes.getOutputFromTempate(stack);
		String s1 = ("" + I18n.format((out != ItemStack.EMPTY ? out.getUnlocalizedName() : "") + ".name"))
				.trim();

		if (s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			for (int i = 0; i < EnumAssemblyTemplate.values().length; ++i) {
				list.add(new ItemStack(this, 1, i));
			}
		}
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (!(stack.getItem() instanceof ItemAssemblyTemplate))
			return;

		List<ItemStack> stacks = MachineRecipes.getRecipeFromTempate(stack);
		ItemStack out = MachineRecipes.getOutputFromTempate(stack);

		list.add("[CREATED USING TEMPLATE FOLDER]");
		list.add("");

		try {
			list.add("Output:");
			list.add(out.getCount() + "x " + out.getDisplayName());
			list.add("Inputs:");

			for (int i = 0; i < stacks.size(); i++) {
				if (stacks.get(i) != null)
					list.add(stacks.get(i).getCount() + "x " + stacks.get(i).getDisplayName());
			}
			list.add("Production time:");
			list.add(Math.floor((float) (getProcessTime(stack)) / 20 * 100) / 100 + " seconds");
		} catch (Exception e) {
			list.add("###INVALID###");
			list.add("0x334077-0x6A298F-0xDF3795-0x334077");
		}
	}

	public static int getProcessTime(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemAssemblyTemplate))
			return 100;

		int i = stack.getItemDamage();
		EnumAssemblyTemplate enum1 = EnumAssemblyTemplate.getEnum(i);

		if (enum1.time != 0)
			return enum1.time;
		switch (enum1) {
		case IRON_PLATE:
			return 30;
		}
		return 0;
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return location;
	}
	
}
