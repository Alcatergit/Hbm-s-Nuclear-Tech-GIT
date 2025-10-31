package com.hbm.items.machine;

import java.util.ArrayList;
import java.util.TreeMap;
import java.util.HashMap;
import java.util.Set;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.interfaces.IHasCustomMetaModels;
import com.hbm.items.ModItems;
import com.hbm.lib.RefStrings;
import com.hbm.util.I18nUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.util.NonNullList;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.oredict.OreDictionary;

public class ItemMold extends Item implements IHasCustomMetaModels {
	
	public static List<Mold> molds = new ArrayList(); //molds in "pretty" order, variable between versions
	public static TreeMap<Integer, Mold> moldById = new TreeMap(); //molds by their static ID -> stack item damage
	
	public HashMap<NTMMaterial, ItemStack> blockOverrides = new HashMap();
	
	public ItemMold(String s) {
		this.setTranslationKey(s);
		this.setRegistryName(s);
		
		this.setHasSubtypes(true);
		this.setMaxDamage(0);

		blockOverrides.put(Mats.MAT_STONE,		new ItemStack(Blocks.STONE));
		blockOverrides.put(Mats.MAT_OBSIDIAN,	new ItemStack(Blocks.OBSIDIAN));
		
		int S = 0;
		int L = 1;
		registerMold(new MoldShape(		0, S, "nugget", MaterialShapes.NUGGET));
		registerMold(new MoldShape(		1, S, "billet", MaterialShapes.BILLET));
		registerMold(new MoldShape(		2, S, "ingot", MaterialShapes.INGOT));
		registerMold(new MoldShape(		3, S, "plate", MaterialShapes.PLATE));
		registerMold(new MoldShape(		4, S, "wire", MaterialShapes.WIRE));

		registerMold(new MoldShape(		5, S, "plate_cast", MaterialShapes.CASTPLATE));
		registerMold(new MoldShape(		6, S, "wire_dense", MaterialShapes.DENSEWIRE));
		
		registerMold(new MoldMulti(		7, S, "blade", MaterialShapes.INGOT.q(3),
				Mats.MAT_TITANIUM, new ItemStack(ModItems.blade_titanium),
				Mats.MAT_TUNGSTEN, new ItemStack(ModItems.blade_tungsten)));
		
		registerMold(new MoldMulti(		8, S, "blades", MaterialShapes.INGOT.q(5),
				Mats.MAT_ALUMINIUM,		new ItemStack(ModItems.blades_aluminum),
				Mats.MAT_GOLD,			new ItemStack(ModItems.blades_gold),
				Mats.MAT_IRON,			new ItemStack(ModItems.blades_iron),
				Mats.MAT_STEEL,			new ItemStack(ModItems.blades_steel),
				Mats.MAT_TITANIUM,		new ItemStack(ModItems.blades_titanium),
				Mats.MAT_ALLOY,			new ItemStack(ModItems.blades_advanced_alloy),
                Mats.MAT_CMB,			new ItemStack(ModItems.blades_combine_steel),
				Mats.MAT_SCHRABIDIUM,	new ItemStack(ModItems.blades_schrabidium)));
		
		registerMold(new MoldMulti(		9, S, "stamp", MaterialShapes.INGOT.q(3),
				Mats.MAT_STONE,			new ItemStack(ModItems.stamp_stone_flat),
				Mats.MAT_IRON,			new ItemStack(ModItems.stamp_iron_flat),
				Mats.MAT_STEEL,			new ItemStack(ModItems.stamp_steel_flat),
				Mats.MAT_TITANIUM,		new ItemStack(ModItems.stamp_titanium_flat),
				Mats.MAT_OBSIDIAN,		new ItemStack(ModItems.stamp_obsidian_flat),
				Mats.MAT_SCHRABIDIUM,	new ItemStack(ModItems.stamp_schrabidium_flat)));

		registerMold(new MoldShape(		10, S, "shell", MaterialShapes.SHELL));
		registerMold(new MoldShape(		11, S, "pipe", MaterialShapes.PIPE));
		
		registerMold(new MoldShape(		12, L, "ingots", MaterialShapes.INGOT, 9));
		registerMold(new MoldShape(		13, L, "plates", MaterialShapes.PLATE, 9));
        registerMold(new MoldShape(		14, L, "wires", MaterialShapes.WIRE, 16));
        registerMold(new MoldShape(		15, L, "wires_dense", MaterialShapes.DENSEWIRE, 9));
		registerMold(new MoldBlock(		16, L, "block", MaterialShapes.BLOCK));
		registerMold(new MoldSingle(	17, L, "pipes", new ItemStack(ModItems.pipes_steel), Mats.MAT_STEEL, MaterialShapes.BLOCK.q(3)));

		registerMold(new MoldSingle(	18, S, "c357", new ItemStack(ModItems.casing_357), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	19, S, "c44", new ItemStack(ModItems.casing_44), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	20, S, "c9", new ItemStack(ModItems.casing_9), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	21, S, "c50", new ItemStack(ModItems.casing_50), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		registerMold(new MoldSingle(	22, S, "cbuckshot", new ItemStack(ModItems.casing_buckshot), Mats.MAT_COPPER, MaterialShapes.PLATE.q(1)));
		ModItems.ALL_ITEMS.add(this);
	}
	
	public void registerMold(Mold mold) {
		molds.add(mold);
		moldById.put(mold.id, mold);
	}

	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
		if(tab == this.getCreativeTab() || tab == CreativeTabs.SEARCH){
			for(int i = 0; i < molds.size(); i++) {
				Mold mold = molds.get(i);
				list.add(new ItemStack(this, 1, mold.id));
			}
		}
	}

	@Override
	public Set<Integer> getMetaValues(){
		return moldById.keySet();
	}

	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		Mold mold = moldById.get(meta);
		return new ModelResourceLocation(RefStrings.MODID + ":mold_"+mold.name, "inventory");
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		Mold mold = getMold(stack);
		list.add("§e" + mold.getTitle());
		
		if(mold.size == 0) list.add("§6" + I18nUtil.resolveKey(ModBlocks.foundry_mold.getTranslationKey() + ".name"));
		else if(mold.size == 1) list.add("§c" + I18nUtil.resolveKey(ModBlocks.foundry_basin.getTranslationKey() + ".name"));
	}
	
	public Mold getMold(ItemStack stack) {
		Mold mold = moldById.get(stack.getItemDamage());
		return mold != null ? mold : molds.get(0);
	}
	
	public static int nextOrder = 0;

	public abstract class Mold {
		public int order;
		public int id;
		public int size;
		public String name;
		
		public Mold(int id, int size, String name) {
			this.order = nextOrder++;
			this.id = id;
			this.size = size;
			this.name = name;
		}
		
		public abstract ItemStack getOutput(NTMMaterial mat);
		public abstract int getCost();
		public abstract String getTitle();
	}

	public class MoldShape extends Mold {
		
		public MaterialShapes shape;
		public int amount;

		public MoldShape(int id, int size, String name, MaterialShapes shape) {
			this(id, size, name, shape, 1);
		}

		public MoldShape(int id, int size, String name, MaterialShapes shape, int amount) {
			super(id, size, name);
			this.shape = shape;
			this.amount = amount;
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			
			for(String name : mat.names) {
				String od = shape.name() + name;
				List<ItemStack> ores = OreDictionary.getOres(od);
				if(!ores.isEmpty()) {
					ItemStack copy = ores.get(0).copy();
					copy.setCount(this.amount);
					return copy;
				}
			}
			
			return null;
		}

		@Override
		public int getCost() {
			return shape.q(amount);
		}

		@Override
		public String getTitle() {
			return I18nUtil.resolveKey("shape." + shape.name()) + " x" + amount;
		}
	}

	public class MoldBlock extends MoldShape {

		public MoldBlock(int id, int size, String name, MaterialShapes shape) {
			super(id, size, name, shape);
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			
			ItemStack override = blockOverrides.get(mat);
			
			if(override != null)
				return override.copy();
			
			return super.getOutput(mat);
		}
	}

	/* because why not */
	public class MoldSingle extends Mold {
		
		public ItemStack out;
		public NTMMaterial mat;
		public int amount;

		public MoldSingle(int id, int size, String name, ItemStack out, NTMMaterial mat, int amount) {
			super(id, size, name);
			this.out = out;
			this.mat = mat;
			this.amount = amount;
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			return this.mat == mat ? out.copy() : null;
		}

		@Override
		public int getCost() {
			return amount;
		}

		@Override
		public String getTitle() {
			return out.getDisplayName() + " x" + this.out.getCount();
		}
	}

	/* not so graceful but it does the job and it does it well */
	public class MoldMulti extends Mold {
		
		public HashMap<NTMMaterial, ItemStack> map = new HashMap();
		public int amount;
		public int stacksize;

		public MoldMulti(int id, int size, String name, int amount, Object... inputs) {
			super(id, size, name);
			this.amount = amount;
			
			for(int i = 0; i < inputs.length; i += 2) {
				map.put((NTMMaterial) inputs[i], (ItemStack) inputs[i + 1]);
				
				if(i == 0) 
					stacksize = (((ItemStack) inputs[i + 1])).getCount();
			}
		}

		@Override
		public ItemStack getOutput(NTMMaterial mat) {
			ItemStack out = this.map.get(mat);
			
			if(out != null)
				return out.copy();
			
			return out;
		}

		@Override
		public int getCost() {
			return amount;
		}

		@Override
		public String getTitle() {
			return I18nUtil.resolveKey("shape." + name) + " x" + this.stacksize;
		}
	}
}
