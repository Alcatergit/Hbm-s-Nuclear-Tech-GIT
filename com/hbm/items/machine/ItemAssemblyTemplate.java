package com.hbm.items.machine;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;

import com.hbm.interfaces.IHasCustomModel;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.ComparableStack;
import com.hbm.inventory.RecipesCommon.OreDictStack;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

public class ItemAssemblyTemplate extends Item implements IHasCustomModel {

	public static final ModelResourceLocation location = new ModelResourceLocation(
			RefStrings.MODID + ":assembly_template", "inventory");
	
	//private static IForgeRegistry<Item> itemRegistry;
	//private static IForgeRegistry<Block> blockRegistry;


	//public static List<AssemblerRecipe> recipes = new ArrayList<AssemblerRecipe>();
	//public static List<AssemblerRecipe> recipesBackup = null;
	

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
		int damage = getTagWithRecipeNumber(stack).getInteger("type");
		ItemStack out = damage < AssemblerRecipes.recipeList.size() ? AssemblerRecipes.recipeList.get(damage).toStack() : null;
		String s1 = ("" + I18n.format((out != ItemStack.EMPTY ? out.getUnlocalizedName() : "") + ".name")).trim();

		if (s1 != null) {
			s = s + " " + s1;
		}

		return s;
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if (tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH) {
			int count = AssemblerRecipes.recipeList.size();

	    	for(int i = 0; i < count; i++) {
				NBTTagCompound tag = new NBTTagCompound();
				tag.setInteger("type", i);
				ItemStack stack = new ItemStack(this, 1, 0);
				stack.setTagCompound(tag);
				list.add(stack);
			}
		}
	}
	
	public static ItemStack getTemplate(int id){
		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("type", id);
		ItemStack stack = new ItemStack(ModItems.assembly_template, 1, 0);
		stack.setTagCompound(tag);
		return stack;
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if (!(stack.getItem() instanceof ItemAssemblyTemplate))
			return;

		int i = getTagWithRecipeNumber(stack).getInteger("type");
		
		if(i < 0 || i >= AssemblerRecipes.recipeList.size()) {
    		list.add("I AM ERROR");
    		return;
    	}

    	ComparableStack out = AssemblerRecipes.recipeList.get(i);

    	if(out == null) {
    		list.add("I AM ERROR");
    		return;
    	}

    	Object[] in = AssemblerRecipes.recipes.get(out);

    	if(in == null) {
    		list.add("I AM ERROR");
    		return;
    	}

    	ItemStack output = out.toStack();

		list.add("Output:");
		list.add(output.getCount() + "x " + output.getDisplayName());
		list.add("Inputs:");

		Random rand = new Random(System.currentTimeMillis() / 1000);

		for(Object o : in) {

			if(o instanceof ComparableStack)  {
				ItemStack input = ((ComparableStack)o).toStack();
	    		list.add(input.getCount() + "x " + input.getDisplayName());

			} else if(o instanceof OreDictStack)  {
				OreDictStack input = (OreDictStack) o;
				NonNullList<ItemStack> ores = OreDictionary.getOres(input.name);

				if(ores.size() > 0) {
					ItemStack inStack = ores.get(rand.nextInt(ores.size()));
		    		list.add(input.count() + "x " + inStack.getDisplayName());
				} else {
		    		list.add("I AM ERROR");
				}
			}
		}

		list.add("Production time:");
    	list.add(Math.floor((float)(getProcessTime(stack)) / 20 * 100) / 100 + " seconds");
	}

	public static int getProcessTime(ItemStack stack) {
		if (!(stack.getItem() instanceof ItemAssemblyTemplate))
			return 100;
		
		int i = getTagWithRecipeNumber(stack).getInteger("type");

    	if(i < 0 || i >= AssemblerRecipes.recipeList.size())
    		return 100;

    	ComparableStack out = AssemblerRecipes.recipeList.get(i);
    	Integer time = AssemblerRecipes.time.get(out);

    	if(time != null)
    		return time;
    	else
    		return 100;

	}

	@Override
	public ModelResourceLocation getResourceLocation() {
		return location;
	}
	
	public static int getRecipeIndex(ItemStack stack){
		return getTagWithRecipeNumber(stack).getInteger("type");
	}

	public static NBTTagCompound getTagWithRecipeNumber(@Nonnull ItemStack stack){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setInteger("type", 0);
		}
		return stack.getTagCompound();
	}
	
	/*public static class AssemblerRecipe {
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
		
		for(EnumAssemblyTemplate e : temp.EnumAssemblyTemplate.values()){
			recipes.add(new AssemblerRecipe(temp.getProcessTime(e), temp.getRecipeFromTempate(e), temp.getOutputFromTempate(e)));
		}
		File recipeConfig = new File(MainRegistry.proxy.getDataDir().getPath() + "/config/hbm/assemblerConfig.cfg");
		if (!recipeConfig.exists())
			try {
				recipeConfig.getParentFile().mkdirs();
				FileWriter write = new FileWriter(recipeConfig);
				write.write("# Format: time;itemName,meta,amount|nextItemName,meta,amount;productName,meta,amount\n"
						  + "# Example for iron plates: 30;minecraft:iron_ingot,0,3;hbm:plate_iron,0,2\n"
						  + "# One line per recipe. I don't know what will happen if you mess up the formatting, so try not to do that.\n"
						  + "# Don't add recipes with overlapping results, that's almost guaranteed to screw something up.\n"
						  + "#\n"
						  + "# To remove a recipe, use the format: \n"
						  + "# remove hbm:plate_iron,0,2\n"
						  + "# This will remove any recipe with the output of two iron plates");
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
				if(currentLine.startsWith("remove"))
					parseRemoval(currentLine, lineCount);
				else
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
	
	public static void parseRemoval(String currentLine, int line){
		String[] parts = currentLine.split(" ");
		if(parts.length != 2){
			MainRegistry.logger.log(Level.WARN, "Could not parse assembler recipe removal on line " + line + ": does not have three parts. Skipping...");
			return;
		}
		ItemStack stack = parseItemStack(parts[1]);
		if(stack == null){
			MainRegistry.logger.log(Level.WARN, "Could not parse input itemstack from \"" + parts[1] + "\" on line " + line + ". Skipping...");
			return;
		}
		Iterator<AssemblerRecipe> itr = recipes.iterator();
		while(itr.hasNext()){
			AssemblerRecipe recipe = itr.next();
			ItemStack test = recipe.getOutput();
			if(test.getItem() == stack.getItem() && test.getMetadata() == stack.getMetadata() && test.getCount() == stack.getCount()){
				itr.remove();
			}
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
			write.write("\n");
	}*/
}
