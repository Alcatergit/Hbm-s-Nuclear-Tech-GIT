package com.hbm.items.weapon;

import java.util.List;

import com.hbm.items.ModItems;
import com.hbm.main.MainRegistry;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

public class ItemAmmo extends Item {

	public ItemAmmo(String s) {
		this.setUnlocalizedName(s);
		this.setRegistryName(s);
		this.setCreativeTab(MainRegistry.controlTab);

		ModItems.ALL_ITEMS.add(this);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn) {

		// FUEL
		if(this == ModItems.ammo_fuel_napalm) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Increased range");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_fuel_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Phosphorus splash");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Increased range");
			list.add(TextFormatting.BLUE + "+ Increased accuracy");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Single projectile");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_fuel_gas) {
			list.add(TextFormatting.BLUE + "+ No gravity");
			list.add(TextFormatting.BLUE + "+ Poison splash");
			list.add(TextFormatting.RED + "- No damage");
			list.add(TextFormatting.RED + "- Not incendiary");
		}
		if(this == ModItems.ammo_fuel_vaporizer) {
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.BLUE + "+ Increased flame count");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* For removing big mistakes");
			list.add(TextFormatting.RED + "- Highly decreased accuracy");
			list.add(TextFormatting.RED + "- Highly decreased range");
			list.add(TextFormatting.RED + "- Highly increased wear");
			list.add(TextFormatting.RED + "- No lingering fire");
		}
		// 12 GAUGE
		if(this == ModItems.ammo_44_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50bmg) {
			list.add(TextFormatting.YELLOW + "12.7mm anti-materiel round");
			list.add(TextFormatting.YELLOW + "You shoot down planes with these, using");
			list.add(TextFormatting.YELLOW + "them against people would be nasty.");
		}
		if(this == ModItems.ammo_50bmg_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50bmg_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Phosphorus splash");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Phosphorus splash");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_kampf) {
			list.add(TextFormatting.BLUE + "+ Rocket Propelled");
			list.add(TextFormatting.BLUE + "+ Increased blast radius");
			list.add(TextFormatting.BLUE + "+ Increased accuracy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_12gauge_incendiary) {
			list.add(TextFormatting.BLUE + "+ Incendiary");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_12gauge_shrapnel) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* Extra bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_12gauge_du) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Penetrating");
			list.add(TextFormatting.YELLOW + "* Heavy Metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_12gauge_marauder) {
			list.add(TextFormatting.BLUE + "+ Instantly removes annoying and unbalanced enemies");
			list.add(TextFormatting.YELLOW + "* No drawbacks lole");
		}
		if(this == ModItems.ammo_12gauge_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
		}
		
		// 20 GAUGE
		if(this == ModItems.ammo_20gauge_flechette) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_slug) {
			list.add(TextFormatting.BLUE + "+ Near-perfect accuracy");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_20gauge_incendiary) {
			list.add(TextFormatting.BLUE + "+ Incendiary");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_shrapnel) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* Extra bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_20gauge_explosive) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_caustic) {
			list.add(TextFormatting.BLUE + "+ Toxic");
			list.add(TextFormatting.BLUE + "+ Caustic");
			list.add(TextFormatting.YELLOW + "* Not bouncy");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_shock) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Stunning");
			list.add(TextFormatting.BLUE + "+ EMP");
			list.add(TextFormatting.YELLOW + "* Not bouncy");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_20gauge_wither) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Withering");
		}
		if(this == ModItems.ammo_20gauge_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
		}
		
		// .357 MAGNUM
		if(this == ModItems.ammo_357_desh) {
			list.add(TextFormatting.BLUE + "+ Fits every .357 model");
			list.add(TextFormatting.BLUE + "+ Above-average damage");
		}

		// .44 MAGNUM
		if(this == ModItems.ammo_44_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_44_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_44_pip) {
			list.add(TextFormatting.BLUE + "+ Boxcar");
			list.add(TextFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_bj) {
			list.add(TextFormatting.BLUE + "+ Boat");
			list.add(TextFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_silver) {
			list.add(TextFormatting.BLUE + "+ Building");
			list.add(TextFormatting.RED + "- Highly decreased damage");
		}
		if(this == ModItems.ammo_44_rocket) {
			list.add(TextFormatting.BLUE + "+ Rocket");
			list.add(TextFormatting.YELLOW + "* Uhhhh");
		}
		if(this == ModItems.ammo_44_star) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Starmetal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}

		// 5mm
		if(this == ModItems.ammo_5mm_explosive) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_5mm_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_5mm_star) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Starmetal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}

		// 9mm
		if(this == ModItems.ammo_9mm_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_9mm_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_9mm_rocket) {
			list.add(TextFormatting.BLUE + "+ Rocket");
			list.add(TextFormatting.YELLOW + "* Uhhhh");
		}

		// .22LR
		if(this == ModItems.ammo_22lr_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}

		// .50 BMG
		if(this == ModItems.ammo_50bmg_incendiary) {
			list.add(TextFormatting.BLUE + "+ Incendiary");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_50bmg_explosive) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_star) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Starmetal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50bmg_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}

		// .50 AE
		if(this == ModItems.ammo_50ae_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_50ae_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_50ae_star) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Starmetal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}

		// 84mm ROCKETS
		if(this == ModItems.ammo_rocket_he) {
			list.add(TextFormatting.BLUE + "+ Increased blast radius");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_incendiary) {
			list.add(TextFormatting.BLUE + "+ Incendiary explosion");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_shrapnel) {
			list.add(TextFormatting.BLUE + "+ Shrapnel");
		}
		if(this == ModItems.ammo_rocket_emp) {
			list.add(TextFormatting.BLUE + "+ EMP");
			list.add(TextFormatting.RED + "- Decreased blast radius");
		}
		if(this == ModItems.ammo_rocket_glare) {
			list.add(TextFormatting.BLUE + "+ Increased projectile speed");
			list.add(TextFormatting.BLUE + "+ Incendiary explosion");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_rocket_toxic) {
			list.add(TextFormatting.BLUE + "+ Chlorine gas");
			list.add(TextFormatting.RED + "- No explosion");
			list.add(TextFormatting.RED + "- Decreased projectile speed");
		}
		if(this == ModItems.ammo_rocket_sleek) {
			list.add(TextFormatting.BLUE + "+ Highly increased blast radius");
			list.add(TextFormatting.BLUE + "+ Not affected by gravity");
			list.add(TextFormatting.YELLOW + "* Jolt");
		}
		if(this == ModItems.ammo_rocket_nuclear) {
			list.add(TextFormatting.BLUE + "+ Nuclear");
			list.add(TextFormatting.RED + "- Very highly increased wear");
			list.add(TextFormatting.RED + "- Decreased projectile speed");
		}
		if(this == ModItems.ammo_rocket_rpc) {
			list.add(TextFormatting.BLUE + "+ Chainsaw");
			list.add(TextFormatting.BLUE + "+ Penetrating");
			list.add(TextFormatting.BLUE + "+ Not affected by gravity");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Non-explosive");
			list.add(TextFormatting.YELLOW + "* Uhhhh");
		}

		// 40mm GRENADES
		if(this == ModItems.ammo_grenade_he) {
			list.add(TextFormatting.BLUE + "+ Increased blast radius");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_incendiary) {
			list.add(TextFormatting.BLUE + "+ Incendiary explosion");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_grenade_toxic) {
			list.add(TextFormatting.BLUE + "+ Chlorine gas");
			list.add(TextFormatting.RED + "- No explosion");
		}
		if(this == ModItems.ammo_grenade_concussion) {
			list.add(TextFormatting.BLUE + "+ Increased blast radius");
			list.add(TextFormatting.RED + "- No block damage");
		}
		if(this == ModItems.ammo_grenade_finned) {
			list.add(TextFormatting.BLUE + "+ Decreased gravity");
			list.add(TextFormatting.RED + "- Decreased blast radius");
		}
		if(this == ModItems.ammo_grenade_sleek) {
			list.add(TextFormatting.BLUE + "+ Increased blast radius");
			list.add(TextFormatting.YELLOW + "* Jolt");
		}
		if(this == ModItems.ammo_grenade_nuclear) {
			list.add(TextFormatting.BLUE + "+ Nuclear");
			list.add(TextFormatting.BLUE + "+ Increased range");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}

		// FOLLY
		if(this == ModItems.ammo_folly) {
			list.add(TextFormatting.BLUE + "+ Focused starmetal reaction blast");
		}
		if(this == ModItems.ammo_folly_nuclear) {
			list.add(TextFormatting.BLUE + "+ Howitzer mini nuke shell");
		}
		if(this == ModItems.ammo_folly_du) {
			list.add(TextFormatting.BLUE + "+ Howitzer 17kg U238 shell");
		}
		// 23mm
		if(this == ModItems.ammo_4gauge_slug) {
			list.add(TextFormatting.BLUE + "+ Near-perfect accuracy");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_explosive) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* It's a 40mm grenade that we squeezed to fit the barrel!");
			list.add(TextFormatting.RED + "- Highly increased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_semtex) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Explosion drops all blocks");
			list.add(TextFormatting.RED + "- No splash damage");
			list.add(TextFormatting.RED + "- Highly increased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_balefire) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Balefire");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Highly increased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_kampf) {
			list.add(TextFormatting.BLUE + "+ Explosive");
			list.add(TextFormatting.BLUE + "+ Rocket Propelled");
			list.add(TextFormatting.BLUE + "+ Increased accuracy");
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Single projectile");
		}
		if(this == ModItems.ammo_4gauge_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a tracer which summons a storm of DU-flechettes");
		}
		if(this == ModItems.ammo_4gauge_flechette) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_4gauge_flechette_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.YELLOW + "* Twice the warcrime in a single round!");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		// 5.56mm
		if(this == ModItems.ammo_556_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.YELLOW + "* Technically a warcrime");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_ap) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.RED + "- Increased wear");
		}
		if(this == ModItems.ammo_556_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_star) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.YELLOW + "* Starmetal");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}
		if(this == ModItems.ammo_556_flechette) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_incendiary) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Incendiary");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_phosphorus) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Induces phosphorus burns");
			list.add(TextFormatting.YELLOW + "* Twice the warcrime in a single round!");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Increased wear");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_du) {
			list.add(TextFormatting.BLUE + "+ Highly increased damage");
			list.add(TextFormatting.BLUE + "+ Penetrating");
			list.add(TextFormatting.YELLOW + "* Heavy metal");
			list.add(TextFormatting.YELLOW + "* Less bouncy");
			list.add(TextFormatting.RED + "- Highly increased wear");
		}
		if(this == ModItems.ammo_556_flechette_sleek) {
			list.add(TextFormatting.YELLOW + "* Fires a high-damage round that summons a small meteorite");
		}
		if(this == ModItems.ammo_556_tracer) {
			list.add(TextFormatting.YELLOW + "* Tracer");
		}
		if(this == ModItems.ammo_556_k) {
			list.add(TextFormatting.YELLOW + "* It's a blank");
		}
		
		//Drillgon200: New stuff (organization? Whatever)
		if(this == ModItems.ammo_44_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_5mm_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_9mm_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_22lr_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50bmg_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_50ae_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
		if(this == ModItems.ammo_556_flechette_chlorophyte) {
			list.add(TextFormatting.BLUE + "+ Increased damage");
			list.add(TextFormatting.BLUE + "+ Decreased wear");
			list.add(TextFormatting.DARK_GREEN + "* Chlorophyte");
			list.add(TextFormatting.YELLOW + "* Homing");
			list.add(TextFormatting.RED + "- Not penetrating");
		}
	}
}
