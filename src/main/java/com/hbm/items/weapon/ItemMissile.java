package com.hbm.items.weapon;

import java.util.HashMap;
import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.items.special.ItemLootCrate;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemMissile extends Item {
	
	public PartType type;
	public PartSize top;
	public PartSize bottom;
	public Rarity rarity;
	public float health;
	private String title;
	private String author;
	private String witty;
	
	public ItemMissile(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setMaxStackSize(1);
		this.setCreativeTab(MainRegistry.missileTab);
		/*File file = new File(MainRegistry.proxy.getDataDir().getPath() + "/hbmtesterjsons/" + s + ".json");
		if (!file.exists())
			try {
				file.getParentFile().mkdirs();
				FileWriter write = new FileWriter(file);
				write.write("{\n"
						  + "	\"parent\": \"item/generated\",\n"
						  + "	\"textures\": {\n"
						  + "		\"layer0\": \"hbm:items/code\"\n"
						  + "	}\n"
						  + "}");
				write.close();
				
			} catch (IOException e) {
				MainRegistry.logger.log(Level.ERROR, "ERROR: Could not create json file for item missile: " + file.getAbsolutePath());
				e.printStackTrace();
				return;
			}*/
		
		ModItems.ALL_ITEMS.add(this);
	}
	
	public static HashMap<Integer, ItemMissile> parts = new HashMap<Integer, ItemMissile>();
	
	/**
	 * == Chips ==
	 * [0]: inaccuracy
	 * 
	 * == Warheads ==
	 * [0]: type
	 * [1]: strength/radius/cluster count
	 * [2]: weight
	 * 
	 * == Fuselages ==
	 * [0]: type
	 * [1]: tank size
	 * 
	 * == Stability ==
	 * [0]: inaccuracy mod
	 * 
	 * == Thrusters ===
	 * [0]: type
	 * [1]: consumption
	 * [2]: lift strength
	 */
	public Object[] attributes;
	
	public enum PartType {
		CHIP,
		WARHEAD,
		FUSELAGE,
		FINS,
		THRUSTER
	}
	
	public enum PartSize {
		
		//for chips
		ANY,
		//for missile tips and thrusters
		NONE,
		//regular sizes, 1.0m, 1.5m and 2.0m
		SIZE_10,
		SIZE_15,
		SIZE_20
	}
	
	public enum WarheadType {
		
		HE,
		INC,
		BUSTER,
		CLUSTER,
		NUCLEAR,
		TX,
		N2,
		BALEFIRE,
		SCHRAB,
		TAINT,
		CLOUD
	}
	
	public enum FuelType {
		
		KEROSENE,
		SOLID,
		HYDROGEN,
		XENON,
		BALEFIRE
	}
	
	public enum Rarity {
		
		COMMON(TextFormatting.GRAY + "Common"),
		UNCOMMON(TextFormatting.YELLOW + "Uncommon"),
		RARE(TextFormatting.AQUA + "Rare"),
		EPIC(TextFormatting.LIGHT_PURPLE + "Epic"),
		LEGENDARY(TextFormatting.DARK_GREEN + "Legendary"),
		SEWS_CLOTHES_AND_SUCKS_HORSE_COCK(TextFormatting.DARK_AQUA + "Strange");
		
		String name;
		
		Rarity(String name) {
			this.name = name;
		}
	}
	
	public ItemMissile makeChip(float inaccuracy) {
		
		this.type = PartType.CHIP;
		this.top = PartSize.ANY;
		this.bottom = PartSize.ANY;
		this.attributes = new Object[] { inaccuracy };
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeWarhead(WarheadType type, float punch, float weight, PartSize size) {

		this.type = PartType.WARHEAD;
		this.top = PartSize.NONE;
		this.bottom = size;
		this.attributes = new Object[] { type, punch, weight };
		//setTextureName(RefStrings.MODID + ":mp_warhead");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeFuselage(FuelType type, float fuel, PartSize top, PartSize bottom) {

		this.type = PartType.FUSELAGE;
		this.top = top;
		this.bottom = bottom;
		attributes = new Object[] { type, fuel };
		//setTextureName(RefStrings.MODID + ":mp_fuselage");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeStability(float inaccuracy, PartSize size) {

		this.type = PartType.FINS;
		this.top = size;
		this.bottom = size;
		this.attributes = new Object[] { inaccuracy };
		//setTextureName(RefStrings.MODID + ":mp_stability");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	public ItemMissile makeThruster(FuelType type, float consumption, float lift, PartSize size) {

		this.type = PartType.THRUSTER;
		this.top = size;
		this.bottom = PartSize.NONE;
		this.attributes = new Object[] { type, consumption, lift };
		//setTextureName(RefStrings.MODID + ":mp_thruster");
		
		parts.put(this.hashCode(), this);
		
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		if(title != null)
			list.add(TextFormatting.DARK_PURPLE + "\"" + title + "\"");
		
		try {
			switch(type) {
			case CHIP:
				list.add(TextFormatting.BOLD + "Inaccuracy: " + TextFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case WARHEAD:
				list.add(TextFormatting.BOLD + "Size: " + TextFormatting.GRAY + getSize(bottom));
				list.add(TextFormatting.BOLD + "Type: " + TextFormatting.GRAY + getWarhead((WarheadType)attributes[0]));
				list.add(TextFormatting.BOLD + "Strength: " + TextFormatting.GRAY + (Float)attributes[1]);
				list.add(TextFormatting.BOLD + "Weight: " + TextFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			case FUSELAGE:
				list.add(TextFormatting.BOLD + "Top size: " + TextFormatting.GRAY + getSize(top));
				list.add(TextFormatting.BOLD + "Bottom size: " + TextFormatting.GRAY + getSize(bottom));
				list.add(TextFormatting.BOLD + "Fuel type: " + TextFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(TextFormatting.BOLD + "Fuel amount: " + TextFormatting.GRAY + (Float)attributes[1] + "l");
				break;
			case FINS:
				list.add(TextFormatting.BOLD + "Size: " + TextFormatting.GRAY + getSize(top));
				list.add(TextFormatting.BOLD + "Inaccuracy: " + TextFormatting.GRAY + (Float)attributes[0] * 100 + "%");
				break;
			case THRUSTER:
				list.add(TextFormatting.BOLD + "Size: " + TextFormatting.GRAY + getSize(top));
				list.add(TextFormatting.BOLD + "Fuel type: " + TextFormatting.GRAY + getFuel((FuelType)attributes[0]));
				list.add(TextFormatting.BOLD + "Fuel consumption: " + TextFormatting.GRAY + (Float)attributes[1] + "l/tick");
				list.add(TextFormatting.BOLD + "Max. payload: " + TextFormatting.GRAY + (Float)attributes[2] + "t");
				break;
			}
		} catch(Exception ex) {
			list.add("### I AM ERROR ###");
		}
		
		if(type != PartType.CHIP)
			list.add(TextFormatting.BOLD + "Health: " + TextFormatting.GRAY + health + "HP");
		
		if(this.rarity != null)
			list.add(TextFormatting.BOLD + "Rarity: " + TextFormatting.GRAY + this.rarity.name);
		if(author != null)
			list.add(TextFormatting.WHITE + "   by " + author);
		if(witty != null)
			list.add(TextFormatting.GOLD + "   " + TextFormatting.ITALIC + "\"" + witty + "\"");
	}
	
	public String getSize(PartSize size) {
		
		switch(size) {
		case ANY:
			return "Any";
		case SIZE_10:
			return "1.0m";
		case SIZE_15:
			return "1.5m";
		case SIZE_20:
			return "2.0m";
		default:
			return "None";
		}
	}
	
	public String getWarhead(WarheadType type) {
		
		switch(type) {
		case HE:
			return TextFormatting.YELLOW + "HE";
		case INC:
			return TextFormatting.GOLD + "Incendiary";
		case CLUSTER:
			return TextFormatting.GRAY + "Cluster";
		case BUSTER:
			return TextFormatting.WHITE + "Bunker Buster";
		case NUCLEAR:
			return TextFormatting.DARK_GREEN + "Nuclear";
		case TX:
			return TextFormatting.DARK_PURPLE + "Thermonuclear (TX)";
		case N2:
			return TextFormatting.RED + "N²";
		case BALEFIRE:
			return TextFormatting.GREEN + "BF";
		case SCHRAB:
			return TextFormatting.AQUA + "Schrabidium";
		case TAINT:
			return TextFormatting.DARK_PURPLE + "Taint";
		case CLOUD:
			return TextFormatting.LIGHT_PURPLE + "Cloud";
		default:
			return TextFormatting.BOLD + "N/A";
		}
	}
	
	public String getFuel(FuelType type) {
		
		switch(type) {
		case KEROSENE:
			return TextFormatting.LIGHT_PURPLE + "Kerosene / Peroxide";
		case SOLID:
			return TextFormatting.GOLD + "Solid Fuel";
		case HYDROGEN:
			return TextFormatting.DARK_AQUA + "Hydrogen / Oxygen";
		case XENON:
			return TextFormatting.DARK_PURPLE + "Xenon Gas";
		case BALEFIRE:
			return TextFormatting.GREEN + "BF Rocket Fuel / Peroxide";
		default:
			return TextFormatting.BOLD + "N/A";
		}
	}
	
	//am i retarded?
	public ItemMissile copy(String s) {
		
		ItemMissile part = new ItemMissile(s);
		part.type = this.type;
		part.top = this.top;
		part.bottom = this.bottom;
		part.health = this.health;
		part.attributes = this.attributes;
		part.health = this.health;
		
		return part;
	}
	
	public ItemMissile setAuthor(String author) {
		this.author = author;
		return this;
	}
	
	public ItemMissile setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public ItemMissile setWittyText(String witty) {
		this.witty = witty;
		return this;
	}
	
	public ItemMissile setHealth(float health) {
		this.health = health;
		return this;
	}
	
	public ItemMissile setRarity(Rarity rarity) {
		this.rarity = rarity;
		
		if(this.type == PartType.FUSELAGE) {
			if(this.top == PartSize.SIZE_10)
				ItemLootCrate.list10.add(this);
			if(this.top == PartSize.SIZE_15)
				ItemLootCrate.list15.add(this);
		} else {
			ItemLootCrate.listMisc.add(this);
		}
		return this;
	}

}