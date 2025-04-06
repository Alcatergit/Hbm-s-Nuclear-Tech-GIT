package com.hbm.items.weapon;

import com.hbm.config.BombConfig;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemCustomLore;
import com.hbm.util.I18nUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import java.util.List;

public class ItemMissileStandard extends ItemCustomLore {

	public final MissileFormFactor formFactor;
	public final MissileTier tier;
	public final MissileFuel fuel;

	public int fuelCap;
	public boolean launchable = true;


	public ItemMissileStandard(String s, MissileFormFactor form, MissileTier tier) {
		this(s, form, tier, form.defaultFuel);
	}

	public ItemMissileStandard(String s, MissileFormFactor form, MissileTier tier, MissileFuel fuel) {
		super(s);
		this.formFactor = form;
		this.tier = tier;
		this.fuel = fuel;
		this.setFuelCap(this.fuel.defaultCap);
	}

	public ItemMissileStandard notLaunchable() {
		this.launchable = false;
		return this;
	}

	public ItemMissileStandard setFuelCap(int fuelCap) {
		this.fuelCap = fuelCap;
		return this;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {
		//HE
		if(this == ModItems.missile_generic) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "I")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 15));
		}
		if(this == ModItems.missile_strong) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "II")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 25));
		}
		if(this == ModItems.missile_burst) {
			list.add("§7["+I18nUtil.resolveKey("desc.hemissile", "III")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 50));
		}

		//IC
		if(this == ModItems.missile_incendiary) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "I")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 10));
		}
		if(this == ModItems.missile_incendiary_strong) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "II")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 20));
		}
		if(this == ModItems.missile_inferno) {
			list.add("§6["+I18nUtil.resolveKey("desc.icmissile", "III")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", 40));
		}
		
		//Cluster
		if(this == ModItems.missile_cluster) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "I")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 5));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 25));
		}
		if(this == ModItems.missile_cluster_strong) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "II")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 7));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 50));
		}
		if(this == ModItems.missile_rain) {
			list.add("§1["+I18nUtil.resolveKey("desc.clmissile", "III")+"]§r");
			list.add(TextFormatting.BLUE + " "+ I18nUtil.resolveKey("desc.radius", 10));
			list.add(" "+TextFormatting.BLUE+I18nUtil.resolveKey("desc.count", 100));
		}

		//Bunker
		if(this == ModItems.missile_buster) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "I")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 15));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 15));
		}
		if(this == ModItems.missile_buster_strong) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "II")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 20));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 20));
		}
		if(this == ModItems.missile_drill) {
			list.add("§8["+I18nUtil.resolveKey("desc.bumissile", "III")+"]§r");
			list.add(TextFormatting.GRAY + " "+ I18nUtil.resolveKey("desc.radius", 30));
			list.add(" "+TextFormatting.GRAY+I18nUtil.resolveKey("desc.depth", 30));
		}

		if(this == ModItems.missile_n2)	{
			list.add("§c["+I18nUtil.resolveKey("desc.n2missile")+"]§r");
			list.add(" "+TextFormatting.YELLOW+I18nUtil.resolveKey("desc.maxradius", (int)(BombConfig.n2Radius/12) * 5));
		}
		if(this == ModItems.missile_nuclear){
			list.add("§2["+I18nUtil.resolveKey("desc.numissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.missileRadius));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)BombConfig.missileRadius*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_nuclear_cluster){
			list.add("§6["+I18nUtil.resolveKey("desc.tumissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.missileRadius*2));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN +I18nUtil.resolveKey("desc.radius",(int)BombConfig.missileRadius*2*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_volcano){
			list.add("§4["+I18nUtil.resolveKey("desc.tecmissile")+"]§r");
		}
		if(this == ModItems.missile_emp_strong){
			list.add("§3["+I18nUtil.resolveKey("desc.empmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 100));
		}
		if(this == ModItems.missile_emp){
			list.add("§3["+I18nUtil.resolveKey("desc.empmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 50));
		}
		if(this == ModItems.missile_micro){
			list.add("§2["+I18nUtil.resolveKey("desc.numicromissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.fatmanRadius));
			if(!BombConfig.disableNuclear){
				list.add(TextFormatting.DARK_GREEN + "["+I18nUtil.resolveKey("trait.fallout")+"]");
				list.add(" " + TextFormatting.GREEN + I18nUtil.resolveKey("desc.radius", (int)BombConfig.fatmanRadius*(1+BombConfig.falloutRange/100)));
			}
		}
		if(this == ModItems.missile_endo){
			list.add("§3["+I18nUtil.resolveKey("desc.thermalmissile")+"]§r");
			list.add(TextFormatting.AQUA + " "+ I18nUtil.resolveKey("desc.radius", 30));
		}
		if(this == ModItems.missile_exo){
			list.add("§4["+I18nUtil.resolveKey("desc.thermalmissile")+"]§r");
			list.add(TextFormatting.GOLD + " "+ I18nUtil.resolveKey("desc.radius", 30));
		}
		if(this == ModItems.missile_doomsday){
			list.add("§5["+I18nUtil.resolveKey("desc.cmmmmissile")+"]§r");
		}
		if(this == ModItems.missile_taint){
			list.add("§d["+I18nUtil.resolveKey("desc.esmissile")+"]§r");
		}
		if(this == ModItems.missile_bhole){
			list.add("§0["+I18nUtil.resolveKey("desc.sinmissile")+"]§r");
		}
		if(this == ModItems.missile_schrabidium){
			list.add("§b["+I18nUtil.resolveKey("desc.schrabmissile")+"]§r");
			list.add(TextFormatting.YELLOW + " "+ I18nUtil.resolveKey("desc.radius", BombConfig.aSchrabRadius));
		}
		if(this == ModItems.missile_anti_ballistic){
			list.add("§2["+I18nUtil.resolveKey("desc.abmissile")+"]§r");
			list.add(TextFormatting.GREEN+" "+I18nUtil.resolveKey("desc.abmissile1"));
		}
	}

	public enum MissileFormFactor {
		ABM(MissileFuel.SOLID),
		MICRO(MissileFuel.SOLID),
		V2(MissileFuel.ETHANOL_PEROXIDE),
		STRONG(MissileFuel.KEROSENE_PEROXIDE),
		HUGE(MissileFuel.KEROSENE_LOXY),
		ATLAS(MissileFuel.JETFUEL_LOXY),
		OTHER(MissileFuel.KEROSENE_PEROXIDE);

		protected MissileFuel defaultFuel;

		private MissileFormFactor(MissileFuel defaultFuel) {
			this.defaultFuel = defaultFuel;
		}
	}

	public enum MissileTier {
		TIER0("Tier 0"),
		TIER1("Tier 1"),
		TIER2("Tier 2"),
		TIER3("Tier 3"),
		TIER4("Tier 4");

		public String display;

		private MissileTier(String display) {
			this.display = display;
		}
	}

	public enum MissileFuel {
		SOLID(TextFormatting.GOLD + "Solid Fuel (pre-fueled)", 0),
		ETHANOL_PEROXIDE(TextFormatting.AQUA + "Ethanol / Hydrogen Peroxide", 4_000),
		KEROSENE_PEROXIDE(TextFormatting.BLUE + "Kerosene / Hydrogen Peroxide", 8_000),
		KEROSENE_LOXY(TextFormatting.LIGHT_PURPLE + "Kerosene / Liquid Oxygen", 12_000),
		JETFUEL_LOXY(TextFormatting.RED + "Jet Fuel / Liquid Oxygen", 16_000);

		public String display;
		public int defaultCap;

		private MissileFuel(String display, int defaultCap) {
			this.display = display;
			this.defaultCap = defaultCap;
		}
	}
}