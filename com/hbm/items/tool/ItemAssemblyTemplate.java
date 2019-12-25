package com.hbm.items.tool;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import org.apache.logging.log4j.Level;

import com.hbm.interfaces.IHasCustomModel;
import com.hbm.inventory.MachineRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemAssemblyTemplate extends Item implements IHasCustomModel {

	public static final ModelResourceLocation location = new ModelResourceLocation(
			RefStrings.MODID + ":assembly_template", "inventory");
	
	private static IForgeRegistry<Item> itemRegistry;
	private static IForgeRegistry<Block> blockRegistry;

	// TODO Yeah, uh, this a big one. I'll do it later.
/*	public enum EnumAssemblyTemplate {
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
	}*/

	public static List<AssemblerRecipe> recipes = new ArrayList<AssemblerRecipe>();
	public static List<AssemblerRecipe> recipesBackup = null;
	

	public ItemAssemblyTemplate(String s) {
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
		ItemStack out = MachineRecipes.getOutputFromTempate(stack);
		String s1 = ("" + I18n.format((out != ItemStack.EMPTY ? out.getUnlocalizedName() : "") + ".name")).trim();

		if (s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			for (int i = 0; i < ItemAssemblyTemplate.recipes.size(); ++i) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("type", i);
				ItemStack stack = new ItemStack(this, 1, 0);
				stack.setTagCompound(tag);
				list.add(stack);
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
		
		int i = getTagWithRecipeNumber(stack).getInteger("type");
		AssemblerRecipe recipe = recipes.get(i);
		if(recipe != null)
			if (recipe.getTime() != 0)
				return recipe.getTime();
		return 0;
	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return location;
	}

	public static NBTTagCompound getTagWithRecipeNumber(@Nonnull ItemStack stack){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("type", 0);
		}
		return stack.getTagCompound();
	}
	
	public static class AssemblerRecipe {
		private int time;
		private List<ItemStack> inputs;
		private ItemStack output;
		
		public AssemblerRecipe(int i, List<ItemStack> l, ItemStack o){
			this.time = i;
			this.inputs = l;
			this.output = o;
		}
		
		public int getTime(){
			return time;
		}
		
		public List<ItemStack> getInputs(){
			return inputs;
		}
		
		public ItemStack getOutput(){
			return output;
		}
	}
	
	//Drillgon200: Messy config parsing stuff below.
	
	public static void loadRecipesFromConfig() {
		itemRegistry = GameRegistry.findRegistry(Item.class);
		blockRegistry = GameRegistry.findRegistry(Block.class);
		File recipeConfig = new File(MainRegistry.proxy.getDataDir().getPath() + "/config/hbm/assemblerConfig.cfg");
		if (!recipeConfig.exists())
			try {
				recipeConfig.getParentFile().mkdirs();
				FileWriter write = new FileWriter(recipeConfig);
				write.write("# Format: time;itemName,meta,amount|nextItemName,meta,amount;productName,meta,amount\n"
						  + "# Example for iron plates: 30;minecraft:iron_ingot,0,3;hbm:plate_iron,0,2\n"
						  + "# One line per recipe. I don't know what will happen if you mess up the formatting, so try not to do that.\n");
				addConfigRecipes(write);
				write.close();
				
			} catch (IOException e) {
				MainRegistry.logger.log(Level.ERROR, "ERROR: Could not create config file: " + recipeConfig.getAbsolutePath());
				e.printStackTrace();
				return;
			}
		
		
		
		BufferedReader read = null;
		try {
			read = new BufferedReader(new FileReader(recipeConfig));
			String currentLine = null;
			int lineCount = 0;
			
			while((currentLine = read.readLine()) != null){
				lineCount ++;
				if(currentLine.startsWith("#") || currentLine.length() == 0)
					continue;
				
				parseRecipe(currentLine, lineCount);
			}
		} catch (FileNotFoundException e) {
			MainRegistry.logger.log(Level.ERROR, "Could not find assembler config file! This should never happen.");
			e.printStackTrace();
		} catch (IOException e){
			MainRegistry.logger.log(Level.ERROR, "Error reading assembler config!");
			e.printStackTrace();
		} finally {
			if(read != null)
				try {
					read.close();
				} catch (IOException e) {}
		}
		
	}

	private static void parseRecipe(String currentLine, int line) {
		String[] parts = currentLine.split(";");
		if(parts.length != 3){
			MainRegistry.logger.log(Level.WARN, "Could not parse assembler recipe on line " + line + ": does not have three parts. Skipping...");
			return;
		}
		int time = 0;
		try {
			time = Integer.parseInt(parts[0]);
		} catch (NumberFormatException e){
			MainRegistry.logger.log(Level.WARN, "Could not parse process time from \"" + parts[0] + "\" on line " + line + ". Skipping...");
			return;
		}
		List<ItemStack> input = new ArrayList<ItemStack>();
		for(String s : parts[1].split("\\|")){
			ItemStack stack = parseItemStack(s);
			if(stack == null){
				MainRegistry.logger.log(Level.WARN, "Could not parse input itemstack from \"" + s + "\" on line " + line + ". Skipping...");
				return;
			}
			input.add(stack);
		}
		ItemStack output = parseItemStack(parts[2]);
		if(output == null){
			MainRegistry.logger.log(Level.WARN, "Could not parse output itemstack from \"" + parts[2] + "\" on line " + line + ". Skipping...");
			return;
		}
		recipes.add(new AssemblerRecipe(time, input, output));
	}

	private static ItemStack parseItemStack(String s){
		String[] parts = s.split(",");
		if(parts.length == 3){
			Block block = null;
			Item item = itemRegistry.getValue(new ResourceLocation(parts[0]));
			if(item == null)
				block = blockRegistry.getValue(new ResourceLocation(parts[0]));
			if(item == null && block == null){
				MainRegistry.logger.log(Level.WARN, "Could not parse item or block from \"" + parts[0] + "\", probably isn't a registered item. Skipping...");
				return null;
			}
			int meta = 0;
			try {
				meta = Integer.parseInt(parts[1]);
			} catch (NumberFormatException e) {
				MainRegistry.logger.log(Level.WARN, "Could not parse item metadata from \"" + parts[1] + "\". Skipping...");
				return null;
			}
			int amount = 0;
			try {
				amount = Integer.parseInt(parts[2]);
			} catch (NumberFormatException e){
				MainRegistry.logger.log(Level.WARN, "Could not parse item amount from \"" + parts[2] + "\". Skipping...");
				return null;
			}
			if(item == null)
				return new ItemStack(block, amount, meta);
			return new ItemStack(item, amount, meta);
		}
		return null;
	}
	private static void addConfigRecipes(FileWriter write) throws IOException {
			write.write("30;minecraft:iron_ingot,0,3;hbm:plate_iron,0,2\n");
	}
}
