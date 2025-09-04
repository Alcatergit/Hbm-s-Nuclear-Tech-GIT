package com.hbm.hazard;

import static com.hbm.blocks.ModBlocks.*;
import static com.hbm.items.ModItems.*;

import com.hbm.blocks.ModBlocks;
import com.hbm.config.GeneralConfig;
import com.hbm.hazard.modifier.*;
import com.hbm.hazard.transformer.*;
import com.hbm.hazard.type.*;
import com.hbm.inventory.OreDictManager.DictFrame;
import com.hbm.items.ModItems;
import com.hbm.forgefluid.FluidTypeHandler;

import com.hbm.items.machine.ItemWatzPellet;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@SuppressWarnings("unused") //shut the fuck up
public class HazardRegistry {

	//CO60		             5a		β−	030.00Rad/s	Spicy
	//SR90		            29a		β−	015.00Rad/s Spicy
	//TC99		       211,000a		β−	002.75Rad/s	Spicy
	//I181		           192h		β−	150.00Rad/s	2 much spice :(
	//XE135		             9h		β−	aaaaaaaaaaaaaaaa
	//CS137		            30a		β−	020.00Rad/s	Spicy
	//AU198		            64h		β−	500.00Rad/s	2 much spice :(
	//PB209		             3h		β−	10,000.00Rad/s mama mia my face is melting off
	//AT209		             5h		β+	like 7.5k or sth idk bruv
	//PO210		           138d		α	075.00Rad/s	Spicy
	//RA226		         1,600a		α	007.50Rad/s
	//AC227		            22a		β−	030.00Rad/s Spicy
	//TH232		14,000,000,000a		α	000.10Rad/s
	//U233		       160,000a		α	005.00Rad/s
	//U235		   700,000,000a		α	001.00Rad/s
	//U238		 4,500,000,000a		α	000.25Rad/s
	//NP237		     2,100,000a		α	002.50Rad/s
	//PU238		            88a		α	010.00Rad/s	Spicy
	//PU239		        24,000a		α	005.00Rad/s
	//PU240		         6,600a		α	007.50Rad/s
	//PU241		            14a		β−	025.00Rad/s	Spicy
	//AM241		           432a		α	008.50Rad/s
	//AM242		           141a		β−	009.50Rad/s

	public static final float co60 = 30.0F;
	public static final float sr90 = 15.0F;
	public static final float tc99 = 2.75F;
	public static final float i131 = 150.0F;
	public static final float xe135 = 1250.0F;
	public static final float cs137 = 20.0F;
	public static final float au198 = 500.0F;
	public static final float pb209 = 10000.0F;
	public static final float at209 = 7500.0F;
	public static final float po210 = 75.0F;
	public static final float ra226 = 7.5F;
	public static final float ac227 = 30.0F;
	public static final float th232 = 0.1F;
	public static final float thf = 1.75F;
	public static final float u = 0.35F;
	public static final float u233 = 5.0F;
	public static final float u235 = 1.0F;
	public static final float u238 = 0.25F;
	public static final float uf = 0.5F;
	public static final float np237 = 2.5F;
	public static final float npf = 1.5F;
	public static final float pu = 7.5F;
	public static final float purg = 6.25F;
	public static final float pu238 = 10.0F;
	public static final float pu239 = 5.0F;
	public static final float pu240 = 7.5F;
	public static final float pu241 = 25.0F;
	public static final float puf = 4.25F;
	public static final float am241 = 8.5F;
	public static final float am242 = 9.5F;
	public static final float amrg = 9.0F;
	public static final float amf = 4.75F;
	public static final float mox = 2.5F;
	public static final float sa326 = 15.0F;
	public static final float sa327 = 17.5F;
	public static final float les = 2.52F;
	public static final float mes = 5.25F;
	public static final float hes = 8.8F;
	public static final float sas3 = 5F;
	public static final float gh336 = 5.0F;
	public static final float mud = 15.0F;
	public static final float radsource_mult = 3F;
	public static final float pobe = po210 * radsource_mult;
	public static final float rabe = ra226 * radsource_mult;
	public static final float pube = pu238 * radsource_mult;
	public static final float zfb_bi = u235 * 0.35F;
	public static final float zfb_pu241 = pu241 * 0.5F;
	public static final float zfb_am_mix = amrg * 0.5F;
	public static final float bf = 300_000.0F;
	public static final float bfb = 500_000.0F;
	public static final float radspice = 20_000.0F;
	public static final float unof = 10_000.0F;
	public static final float ts = 120.0F;
	
	public static final float sr = sa326 * 0.1F;
	public static final float sb = sa326 * 0.2F;
	public static final float trx = 25.0F;
	public static final float trn = 0.1F;
	public static final float wst = 450F;
	public static final float wstv = 150F;
	public static final float yc = u * 1.2F;
	public static final float fo = 10F;

	public static final float nugget = 0.1F;
	public static final float ingot = 1.0F;
	public static final float gem = 1.0F;
	public static final float plate = ingot;
	public static final float plateCast = plate * 3;
	public static final float plateWeld = plate * 6;
	public static final float heavyComp = plateCast * 256;
	public static final float wire = ingot * 0.1F;
	public static final float wireDense = ingot;
	public static final float pipe = ingot * 3;
	public static final float shell = ingot * 4;
	public static final float bolt = nugget;
	public static final float powder_mult = 3.0F;
	public static final float powder = ingot * powder_mult;
	public static final float powder_tiny = nugget * powder_mult;
	public static final float ore = ingot * 0.05F;
	public static final float block = ingot * 10.0F;
	public static final float crystal = block;
	public static final float billet = 0.5F;
	public static final float rtg = billet * 3;
	public static final float rod = 0.5F;
	public static final float rod_dual = rod * 2;
	public static final float rod_quad = rod * 4;
	public static final float rod_rbmk = rod * 8;
	public static final float magt = nugget * 0.5F * sa326 * ingot;
	public static final float tcalloy = 0.07F * ingot;
	public static final float ferro = u238 * 0.7F * ingot;

	public static final HazardTypeBase RADIATION = new HazardTypeRadiation();
	public static final HazardTypeBase DIGAMMA = new HazardTypeDigamma();
	public static final HazardTypeBase HOT = new HazardTypeHot();
	public static final HazardTypeBase BLINDING = new HazardTypeBlinding();
	public static final HazardTypeBase ASBESTOS = new HazardTypeAsbestos();
	public static final HazardTypeBase COAL = new HazardTypeCoal();
	public static final HazardTypeBase HYDROACTIVE = new HazardTypeHydroactive();
	public static final HazardTypeBase EXPLOSIVE = new HazardTypeExplosive();
	public static final HazardTypeBase UNSTABLE = new HazardTypeUnstable();
	public static final HazardTypeBase CRYOGENIC = new HazardTypeCryogenic();
	public static final HazardTypeBase TOXIC = new HazardTypeToxic();
	public static final HazardTypeBase CONTAMINATING = new HazardTypeContaminating();
	
	public static void registerItems() {
		
		HazardSystem.register(Items.GUNPOWDER, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(Items.TNT_MINECART, makeData(EXPLOSIVE, 3F));
		HazardSystem.register(Blocks.TNT, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(Items.PUMPKIN_PIE, makeData(EXPLOSIVE, 1F));

		registerHazItem(Blocks.MAGMA, 0, 1F);
		
		registerHazItem(Blocks.END_ROD, 0.6F);
		registerHazItem(block_meteor_molten, 0, 1F);
		
		HazardSystem.register(ball_dynamite, makeData(EXPLOSIVE, 2F));
		// HazardSystem.register(stick_dynamite, makeData(EXPLOSIVE, 1F));
		// HazardSystem.register(stick_tnt, makeData(EXPLOSIVE, 1.5F));
		// HazardSystem.register(stick_semtex, makeData(EXPLOSIVE, 2.5F));
		// HazardSystem.register(stick_c4, makeData(EXPLOSIVE, 2.5F));

		HazardSystem.register(cordite, makeData(EXPLOSIVE, 2F));
		HazardSystem.register(ballistite, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(n2_charge, makeData(EXPLOSIVE, 20F));
		HazardSystem.register(custom_tnt, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(det_cord, makeData(EXPLOSIVE, 1F));
		HazardSystem.register(det_charge, makeData(EXPLOSIVE, 30F));
		
		HazardSystem.register(gadget_explosive, makeData(EXPLOSIVE, 4F));
		HazardSystem.register(gadget_explosive8, makeData(EXPLOSIVE, 40F));
		HazardSystem.register(man_explosive, makeData(EXPLOSIVE, 6F));
		HazardSystem.register(man_explosive8, makeData(EXPLOSIVE, 60F));
		
		registerHazItem(nugget_unobtainium_greater, unof * nugget);
		registerHazItem(billet_unobtainium, unof * billet);
		registerHazItem(insert_du, u238 * block);
		registerHazItem(insert_ferrouranium, ferro * 4);
		registerHazItem(insert_polonium, 100F);
		registerHazItem(insert_ghiorsium, gh336 * 4);

		HazardSystem.register(nugget_u238m2, makeData(UNSTABLE, 60F));
		HazardSystem.register(new ItemStack(ingot_u238m2, 1, 0), makeData(UNSTABLE, 360F));
		HazardSystem.register(ingot_electronium, makeData(UNSTABLE, 30F));

		registerHazItem(demon_core_open, 5F);
		registerHazItem(demon_core_closed, 10_000_000F, 0, 30);
		registerHazItem(lamp_demon, 10_000_000F, 0, 30);

		registerHazItem(bomb_waffle, 5F);
		registerHazItem(schnitzel_vegan, 600F);
		registerHazItem(cotton_candy, 0.25F);
		HazardSystem.register(apple_lead, makeData(TOXIC, 2F));
		HazardSystem.register(apple_lead1, makeData(TOXIC, 4F));
		HazardSystem.register(apple_lead2, makeData(TOXIC, 8F));
		registerHazItem(apple_schrabidium, 12F, 0, 50F);
		registerHazItem(apple_schrabidium1, 120F, 0, 50F);
		registerHazItem(apple_schrabidium2, 1200F, 0, 50F);
		registerHazItem(glowing_stew, 2F);
		registerHazItem(balefire_scrambled, radspice * powder + bf, 6F, 30F, 0, 6);
		registerHazItem(balefire_and_ham, 2 * radspice * powder + bf, 30F, 30F, 0, 6);

		// HazardSystem.register(cell_tritium, makeData(RADIATION, 0.001F));
		// HazardSystem.register(cell_sas3, makeData(RADIATION, sas3).addEntry(BLINDING, 60F));
		// HazardSystem.register(cell_balefire, makeData(RADIATION, 50F));
		HazardSystem.register(powder_balefire, makeData(RADIATION, bf * powder).addEntry(HOT, 6).addEntry(CONTAMINATING, 500));
		registerHazItem(egg_balefire_shard, bf * nugget);
		registerHazItem(egg_balefire, bf * ingot);

		HazardSystem.register(powder_poison, makeData(TOXIC, 30F));
		HazardSystem.register(powder_cloud, makeData(TOXIC, 14F));
		HazardSystem.register(nugget_mercury, makeData(TOXIC, 2F));
		HazardSystem.register(bottle_mercury, makeData(TOXIC, 6F));
		HazardSystem.register(pellet_mercury, makeData(TOXIC, 25F));
		HazardSystem.register(powder_ice, makeData(CRYOGENIC, 5F));
		
		HazardSystem.register(thermo_unit_endo, makeData(CRYOGENIC, 2F));
		HazardSystem.register(thermo_unit_exo, makeData(HOT, 10F));
		registerHazItem(levitation_unit, sa326 * nugget * 2);
		
		HazardSystem.register(powder_paleogenite_tiny, makeData(DIGAMMA, 0.0005F));
		HazardSystem.register(powder_paleogenite, makeData(DIGAMMA, 0.005F));
		HazardSystem.register(powder_impure_osmiridium, makeData(DIGAMMA, 0.010F));

		HazardSystem.register(solid_fuel_bf, makeData(RADIATION, 1000)); //roughly the amount of the balefire shard diluted in 250mB of rocket fuel
		HazardSystem.register(solid_fuel_presto_bf, makeData(RADIATION, 2000));
		HazardSystem.register(solid_fuel_presto_triplet_bf, makeData(RADIATION, 6000));
		HazardSystem.register(block_solid_fuel_bf, makeData(RADIATION, 10000));
		HazardSystem.register(block_solid_fuel_presto_bf, makeData(RADIATION, 20000));
		HazardSystem.register(block_solid_fuel_presto_triplet_bf, makeData(RADIATION, 60000));
		HazardSystem.register(nuclear_waste, makeData(RADIATION, wst * powder).addEntry(CONTAMINATING, 21F));
		HazardSystem.register(nuclear_waste_tiny, makeData(RADIATION, wst * powder_tiny).addEntry(CONTAMINATING, 7F));
		HazardSystem.register(nuclear_waste_vitrified, makeData(RADIATION, wstv * powder).addEntry(CONTAMINATING, 15F));
		HazardSystem.register(nuclear_waste_vitrified_tiny, makeData(RADIATION, wstv * powder_tiny).addEntry(CONTAMINATING, 5F));

		HazardSystem.register(nuclear_waste_long, makeData(RADIATION, 50).addEntry(CONTAMINATING, 7F));
		HazardSystem.register(nuclear_waste_long_tiny, makeData(RADIATION, 5).addEntry(CONTAMINATING, 2F));
		HazardSystem.register(nuclear_waste_short, makeData(RADIATION, 300).addEntry(HOT, 5F).addEntry(CONTAMINATING, 17F));
		HazardSystem.register(nuclear_waste_short_tiny, makeData(RADIATION, 30).addEntry(HOT, 5F).addEntry(CONTAMINATING, 5F));
		HazardSystem.register(nuclear_waste_long_depleted, makeData(RADIATION, 5).addEntry(CONTAMINATING, 4F));
		HazardSystem.register(nuclear_waste_long_depleted_tiny, makeData(RADIATION, 0.5F).addEntry(CONTAMINATING, 2F));
		HazardSystem.register(nuclear_waste_short_depleted, makeData(RADIATION, 30).addEntry(CONTAMINATING, 5F));
		HazardSystem.register(nuclear_waste_short_depleted_tiny, makeData(RADIATION, 3).addEntry(CONTAMINATING, 2F));

		HazardSystem.register(waste_uranium, makeData(RADIATION, 15).addEntry(CONTAMINATING, 4F));
		HazardSystem.register(waste_thorium, makeData(RADIATION, 10).addEntry(CONTAMINATING, 3F));
		HazardSystem.register(waste_plutonium, makeData(RADIATION, 20).addEntry(CONTAMINATING, 5F));
		HazardSystem.register(waste_mox, makeData(RADIATION, 17.5F).addEntry(CONTAMINATING, 3F));
		HazardSystem.register(waste_schrabidium, makeData(RADIATION, 40).addEntry(BLINDING, 50).addEntry(CONTAMINATING, 6F));
		
		HazardSystem.register(waste_uranium_hot, makeData(RADIATION, 15).addEntry(HOT, 5F).addEntry(CONTAMINATING, 4F));
		HazardSystem.register(waste_thorium_hot, makeData(RADIATION, 10).addEntry(HOT, 5F).addEntry(CONTAMINATING, 3F));
		HazardSystem.register(waste_plutonium_hot, makeData(RADIATION, 20).addEntry(HOT, 5F).addEntry(CONTAMINATING, 5F));
		HazardSystem.register(waste_mox_hot, makeData(RADIATION, 17.5F).addEntry(HOT, 5F).addEntry(CONTAMINATING, 3F));
		HazardSystem.register(waste_schrabidium_hot, makeData(RADIATION, 40).addEntry(HOT, 5F).addEntry(BLINDING, 50).addEntry(CONTAMINATING, 6F));
	
		HazardSystem.register(trinitite, makeData(RADIATION, trn * ingot));
		HazardSystem.register(block_trinitite, makeData(RADIATION, trn * block));
		HazardSystem.register(yellow_barrel, makeData(RADIATION, wst * ingot * 10));
		HazardSystem.register(billet_nuclear_waste, makeData(RADIATION, wst * billet));
		
		HazardSystem.register(block_waste, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_painted, makeData(RADIATION, wst * block));
		HazardSystem.register(block_waste_vitrified, makeData(RADIATION, wstv * block));
		
		registerHazItem(ancient_scrap, 150F);
		registerHazItem(block_corium, 10000F);
		registerHazItem(block_corium_cobble, 1000F);

		registerHazItem(sand_lead, 0, 0, 0, 3, 0);
		registerHazItem(sand_uranium, u * nugget);
		registerHazItem(sand_polonium, po210 * nugget, 10);
		registerHazItem(sand_gold198, au198 * block * powder_mult, 10);
		
		registerHazItem(block_schrabidium_cluster, 70F, 0, 30F);
		registerHazItem(block_euphemium_cluster, 50F, 0, 20F);
		
		registerHazItem(glass_lead, 0, 0, 0, 3, 0);
		registerHazItem(glass_uranium, u * nugget);
		registerHazItem(glass_trinitite, trn * 10F);
		registerHazItem(glass_polonium, po210 * nugget);
		
		registerHazItem(block_tritium, 4.5F);
		
		registerHazItem(brick_jungle_ooze, 10);
		
		registerHazItem(mush, 1);
		registerHazItem(mush_block, 4);
		registerHazItem(mush_block_stem, 4);
		registerHazItem(brick_jungle_ooze, 10);
		registerHazItem(brick_jungle_ooze, 10);
		registerHazItem(brick_jungle_ooze, 10);
		

		registerHazItem(blades_schrabidium, sa326 * ingot * 5, 0, 50F);
		registerHazItem(stamp_schrabidium_flat, sa326 * ingot * 3, 0, 50F);
		registerHazItem(stamp_schrabidium_plate, sa326 * ingot * 3, 0, 50F);
		registerHazItem(stamp_schrabidium_wire, sa326 * ingot * 3, 0, 50F);
		registerHazItem(stamp_schrabidium_circuit, sa326 * ingot * 3, 0, 50F);
			
		if(GeneralConfig.enable528){
			registerHazItem(gun_revolver_schrabidium, sa326 * block * 2 + sa326 * wire, 0, 30F);
			registerHazItem(schrabidium_hammer, sa326 * block * 35, 0, 50F);
			registerHazItem(schrabidium_helmet, sa326 * ingot * 5, 0, 50F);
			registerHazItem(schrabidium_plate, sa326 * ingot * 8, 0, 50F);
			registerHazItem(schrabidium_legs, sa326 * ingot * 7, 0, 50F);
			registerHazItem(schrabidium_boots, sa326 * ingot * 4, 0, 50F);
			registerHazItem(schrabidium_sword, sa326 * block, 0, 50F);
			registerHazItem(balefire_and_steel, bf * nugget, 5);
		}
		
		registerHazItem(debris_graphite, 700F, 5F);
		registerHazItem(debris_metal, 50F);
		registerHazItem(debris_fuel, 150000F, 5F);

		registerHazItem(billet_balefire_gold,  au198 * billet, 7F);
		registerHazItem(billet_flashlead,  pb209 * 1.25F * billet, 7F);
		registerHazItem(billet_po210be, pobe * billet);
		registerHazItem(billet_ra226be, rabe * billet);
		registerHazItem(billet_pu238be, pube * billet);
		registerHazItem(billet_zfb_bismuth, zfb_bi * billet);
		registerHazItem(billet_zfb_pu241, zfb_pu241 * billet);
		registerHazItem(billet_zfb_am_mix, zfb_am_mix * billet);
		
		registerRods(rod_th232, rod_dual_th232, rod_quad_th232, th232);
		registerRods(rod_uranium, rod_dual_uranium, rod_quad_uranium, u);
		registerRods(rod_u233, rod_dual_u233, rod_quad_u233, u233);
		registerRods(rod_u235, rod_dual_u235, rod_quad_u235, u235);
		registerRods(rod_u238, rod_dual_u238, rod_quad_u238, u238);
		registerRods(rod_plutonium, rod_dual_plutonium, rod_quad_plutonium, pu);
		registerRods(rod_pu238, rod_dual_pu238, rod_quad_pu238, pu238);
		registerRods(rod_pu239, rod_dual_pu239, rod_quad_pu239, pu239);
		registerRods(rod_pu240, rod_dual_pu240, rod_quad_pu240, pu240);
		registerRods(rod_rgp, rod_dual_rgp, rod_quad_rgp, purg);
		registerRods(rod_neptunium, rod_dual_neptunium, rod_quad_neptunium, np237);
		registerRods(rod_polonium, rod_dual_polonium, rod_quad_polonium, po210);
		registerRods(rod_schrabidium, rod_dual_schrabidium, rod_quad_schrabidium, sa326, 0, 50);
		registerRods(rod_solinium, rod_dual_solinium, rod_quad_solinium, sa327, 0, 50);
		registerRods(rod_balefire, rod_dual_balefire, rod_quad_balefire, bf);
		registerRods(rod_balefire_blazing, rod_dual_balefire_blazing, rod_quad_balefire_blazing, bf * 2, 5, 0);
		
		registerRods(rod_thorium_fuel, rod_dual_thorium_fuel, rod_quad_thorium_fuel, thf);
		registerRods(rod_uranium_fuel, rod_dual_uranium_fuel, rod_quad_uranium_fuel, uf);
		registerRods(rod_plutonium_fuel, rod_dual_plutonium_fuel, rod_quad_plutonium_fuel, puf);
		registerRods(rod_mox_fuel, rod_dual_mox_fuel, rod_quad_mox_fuel, mox);
		registerRods(rod_schrabidium_fuel, rod_dual_schrabidium_fuel, rod_quad_schrabidium_fuel, mes, 0, 50);
		
		registerRods(rod_thorium_fuel_depleted, rod_dual_thorium_fuel_depleted, rod_quad_thorium_fuel_depleted, 12 * thf, 10, 0);
		registerRods(rod_uranium_fuel_depleted, rod_dual_uranium_fuel_depleted, rod_quad_uranium_fuel_depleted, 12 * uf, 10, 0);
		registerRods(rod_plutonium_fuel_depleted, rod_dual_plutonium_fuel_depleted, rod_quad_plutonium_fuel_depleted, 12 * puf, 10, 0);
		registerRods(rod_mox_fuel_depleted, rod_dual_mox_fuel_depleted, rod_quad_mox_fuel_depleted, 12 * mox, 10, 0);
		registerRods(rod_schrabidium_fuel_depleted, rod_dual_schrabidium_fuel_depleted, rod_quad_schrabidium_fuel_depleted, 12 * mes, 10, 50);
		
		registerRods(rod_waste, rod_dual_waste, rod_quad_waste, wst);
		registerRods(rod_tritium, rod_dual_tritium, rod_quad_tritium, 1);
		registerRods(rod_ac227, rod_dual_ac227, rod_quad_ac227, ac227);
		registerRods(rod_co60, rod_dual_co60, rod_quad_co60, co60);
		registerRods(rod_ra226, rod_dual_ra226, rod_quad_ra226, ra226);
		
		registerHazItem(pile_rod_uranium, u * billet * 3);
		registerHazItem(pile_rod_plutonium, pu * billet * 3);
		registerHazItem(pile_rod_source, rabe * billet * 3);
		
		registerHazItem(pellet_rtg_depleted_bismuth, 1F);
		registerHazItem(pellet_rtg_depleted_lead, 0.5F, 0, 0, 2, 0);
		registerHazItem(pellet_rtg_depleted_mercury, 4.25F, 0, 0, 4, 0);
		registerHazItem(pellet_rtg_depleted_neptunium, 3.75F, 5);
		registerHazItem(pellet_rtg_depleted_zirconium, 2);
		
		registerHazItem(pellet_rtg_radium, rtg * ra226, 0, 0, 0, 7);
		registerHazItem(pellet_rtg_weak, rtg * u238);
		registerHazItem(pellet_rtg, rtg * pu238, 5);
		registerHazItem(pellet_rtg_strontium, rtg * sr90, 5);
		registerHazItem(pellet_rtg_cobalt, rtg * co60, 5);
		registerHazItem(pellet_rtg_actinium, rtg * ac227, 5);
		registerHazItem(pellet_rtg_americium, rtg * am241, 10);
		registerHazItem(pellet_rtg_polonium, rtg * po210, 15);
		registerHazItem(pellet_rtg_gold, rtg * au198, 15);
		registerHazItem(pellet_rtg_lead, rtg * pb209, 15, 5, 4, 0);
		registerHazItem(pellet_rtg_balefire, rtg * bf * 2, 20);
		
		registerHazItem(pellet_charged, 420);
		
		registerHazItem(sellafield_slaked, 2.5F);
		registerHazItem(sellafield_0, 5);
		registerHazItem(sellafield_1, 10);
		registerHazItem(sellafield_2, 20);
		registerHazItem(sellafield_3, 40, 5);
		registerHazItem(sellafield_4, 80, 5);
		registerHazItem(sellafield_core, 500, 5);
		
		registerHazItem(baleonitite_slaked, 25);
		registerHazItem(baleonitite_0, 50);
		registerHazItem(baleonitite_1, 100);
		registerHazItem(baleonitite_2, 200);
		registerHazItem(baleonitite_3, 400, 5);
		registerHazItem(baleonitite_4, 800, 5);
		registerHazItem(baleonitite_core, 5000, 5);
		
		registerHazItem(waste_leaves, 0.15F);
		registerHazItem(waste_ice, 20);

		for(int i=0; i<7; i++){
			registerHazItem(new ItemStack(waste_mycelium, 1, i), (i+1) * 7.5F);
			registerHazItem(new ItemStack(waste_earth, 1, i), (i+1) * 4);
			registerHazItem(new ItemStack(waste_dirt, 1, i), (i+1));
			registerHazItem(new ItemStack(waste_gravel, 1, i), (i+1) * 2.5F);
			registerHazItem(new ItemStack(waste_sandstone, 1, i), (i+1) * 2.5F);
			registerHazItem(new ItemStack(waste_sand, 1, i), (i+1) * 5);
			registerHazItem(new ItemStack(waste_trinitite, 1, i), (i+1) * 10);
			registerHazItem(new ItemStack(waste_sandstone_red, 1, i), (i+1) * 2.5F);
			registerHazItem(new ItemStack(waste_sand_red, 1, i), (i+1) * 5);
			registerHazItem(new ItemStack(waste_trinitite_red, 1, i), (i+1) * 10);
			registerHazItem(new ItemStack(waste_terracotta, 1, i), (i+1) * 2.5F);
			registerHazItem(new ItemStack(waste_grass_tall, 1, i), (i+1) * 2.5F);
			registerHazItem(new ItemStack(waste_snow, 1, i), (i+1) * 0.15F);
			registerHazItem(new ItemStack(waste_snow_block, 1, i), (i+1) * 1.5F);
		}

		registerHazItem(volcano_core, 0, 20);

		registerRBMKRod(rbmk_fuel_ueu, u * rod_rbmk, wst * rod_rbmk * 20F);
		registerRBMKRod(rbmk_fuel_meu, uf * rod_rbmk, wst * rod_rbmk * 21.5F);
		registerRBMKRod(rbmk_fuel_heu233, u233 * rod_rbmk, wst * rod_rbmk * 31F);
		registerRBMKRod(rbmk_fuel_heu235, u235 * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_thmeu, thf * rod_rbmk, wst * rod_rbmk * 17.5F);
		registerRBMKRod(rbmk_fuel_lep, puf * rod_rbmk, wst * rod_rbmk * 25F);
		registerRBMKRod(rbmk_fuel_mep, purg * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_hep239, pu239 * rod_rbmk, wst * rod_rbmk * 32.5F);
		registerRBMKRod(rbmk_fuel_hep241, pu241 * rod_rbmk, wst * rod_rbmk * 35F);
		registerRBMKRod(rbmk_fuel_lea, amf * rod_rbmk, wst * rod_rbmk * 26F);
		registerRBMKRod(rbmk_fuel_mea, amrg * rod_rbmk, wst * rod_rbmk * 30.5F);
		registerRBMKRod(rbmk_fuel_hea241, am241 * rod_rbmk, wst * rod_rbmk * 33.5F);
		registerRBMKRod(rbmk_fuel_hea242, am242 * rod_rbmk, wst * rod_rbmk * 34F);
		registerRBMKRod(rbmk_fuel_men, npf * rod_rbmk, wst * rod_rbmk * 22.5F);
		registerRBMKRod(rbmk_fuel_hen, np237 * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_mox, mox * rod_rbmk, wst * rod_rbmk * 25.5F);
		registerRBMKRod(rbmk_fuel_les, les * rod_rbmk, wst * rod_rbmk * 24.5F);
		registerRBMKRod(rbmk_fuel_mes, mes * rod_rbmk, wst * rod_rbmk * 30F);
		registerRBMKRod(rbmk_fuel_hes, hes * rod_rbmk, wst * rod_rbmk * 50F);
		registerRBMKRod(rbmk_fuel_leaus, 0.1F, wst * rod_rbmk * 37.5F);
		registerRBMKRod(rbmk_fuel_heaus, 0.1F, wst * rod_rbmk * 32.5F);
		registerRBMKRod(rbmk_fuel_po210be, pobe * rod_rbmk, pobe * rod_rbmk * 0.1F, true);
		registerRBMKRod(rbmk_fuel_ra226be, rabe * rod_rbmk, rabe * rod_rbmk * 0.4F, true);
		registerRBMKRod(rbmk_fuel_pu238be, pube * rod_rbmk, wst * rod_rbmk * 2.5F);
		registerRBMKRod(rbmk_fuel_balefire_gold, au198 * rod_rbmk, bf * rod_rbmk * 0.5F, true);
		registerRBMKRod(rbmk_fuel_flashlead, pb209 * 1.25F * rod_rbmk, pb209 * nugget * 0.05F * rod_rbmk, true);
		registerRBMKRod(rbmk_fuel_balefire, bf * rod_rbmk, bf * rod_rbmk * 100F, true);
		registerRBMKRod(rbmk_fuel_unobtainium, unof * rod_rbmk, bf * rod_rbmk * 20F);
		registerRBMKRod(rbmk_fuel_zfb_bismuth, zfb_bi * rod_rbmk, wst * rod_rbmk * 5F);
		registerRBMKRod(rbmk_fuel_zfb_pu241, zfb_pu241 * rod_rbmk, wst * rod_rbmk * 7.5F);
		registerRBMKRod(rbmk_fuel_zfb_am_mix, zfb_am_mix * rod_rbmk, wst * rod_rbmk * 10F);
		registerRBMK(rbmk_fuel_drx, bf * rod_rbmk * 1.2F, bf * rod_rbmk * 10F, true, true, 0, 1F/3F);
		
		registerRBMKPellet(rbmk_pellet_ueu, u * billet, wst * billet * 20F);
		registerRBMKPellet(rbmk_pellet_meu, uf * billet, wst * billet * 21.5F);
		registerRBMKPellet(rbmk_pellet_heu233, u233 * billet, wst * billet * 31F);
		registerRBMKPellet(rbmk_pellet_heu235, u235 * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_thmeu, thf * billet, wst * billet * 17.5F);
		registerRBMKPellet(rbmk_pellet_lep, puf * billet, wst * billet * 25F);
		registerRBMKPellet(rbmk_pellet_mep, purg * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_hep239, pu239 * billet, wst * billet * 32.5F);
		registerRBMKPellet(rbmk_pellet_hep241, pu241 * billet, wst * billet * 35F);
		registerRBMKPellet(rbmk_pellet_lea, amf * billet, wst * billet * 26F);
		registerRBMKPellet(rbmk_pellet_mea, amrg * billet, wst * billet * 30.5F);
		registerRBMKPellet(rbmk_pellet_hea241, am241 * billet, wst * billet * 33.5F);
		registerRBMKPellet(rbmk_pellet_hea242, am242 * billet, wst * billet * 34F);
		registerRBMKPellet(rbmk_pellet_men, npf * billet, wst * billet * 22.5F);
		registerRBMKPellet(rbmk_pellet_hen, np237 * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_mox, mox * billet, wst * billet * 25.5F);
		registerRBMKPellet(rbmk_pellet_les, les * billet, wst * billet * 24.5F);
		registerRBMKPellet(rbmk_pellet_mes, mes * billet, wst * billet * 30F);
		registerRBMKPellet(rbmk_pellet_hes, hes * billet, wst * billet * 50F);
		registerRBMKPellet(rbmk_pellet_leaus, 0.1F, wst * billet * 37.5F);
		registerRBMKPellet(rbmk_pellet_heaus, 0.1F, wst * billet * 32.5F);
		registerRBMKPellet(rbmk_pellet_po210be, pobe * billet, pobe * billet * 0.1F, true);
		registerRBMKPellet(rbmk_pellet_ra226be, rabe * billet, rabe * billet * 0.4F, true);
		registerRBMKPellet(rbmk_pellet_pu238be, pube * billet, wst * 1.5F);
		registerRBMKPellet(rbmk_pellet_balefire_gold, au198 * billet, bf * billet * 0.5F, true);
		registerRBMKPellet(rbmk_pellet_flashlead, pb209 * 1.25F * billet, pb209 * nugget * 0.05F, true);
		registerRBMKPellet(rbmk_pellet_balefire, bf * billet, bf * billet * 100F, true);
		registerRBMKPellet(rbmk_pellet_unobtainium, unof * billet, bf * billet * 20F);
		registerRBMKPellet(rbmk_pellet_zfb_bismuth, zfb_bi * billet, wst * billet * 5F);
		registerRBMKPellet(rbmk_pellet_zfb_pu241, zfb_pu241 * billet, wst * billet * 7.5F);
		registerRBMKPellet(rbmk_pellet_zfb_am_mix, zfb_am_mix * billet, wst * billet * 10F);
		registerRBMKPellet(rbmk_pellet_drx, bf * billet * 1.2F, bf * billet * 10F, true, 0F, 1F/24F);

		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.SCHRABIDIUM), makeData(RADIATION, sa326 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.HES), makeData(RADIATION, hes * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.MES), makeData(RADIATION, mes * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.LES), makeData(RADIATION, les * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.HEN), makeData(RADIATION, np237 * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.MEU), makeData(RADIATION, uf * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.MEP), makeData(RADIATION, purg * ingot * 4));
		HazardSystem.register(DictFrame.fromOne(ModItems.watz_pellet, ItemWatzPellet.EnumWatzType.DU), makeData(RADIATION, u238 * ingot * 4));
		
		registerHazItem(powder_yellowcake, yc * powder);
		registerHazItem(block_yellowcake, yc * block * powder_mult);
		HazardSystem.register(ModItems.fallout, makeData(RADIATION, fo * powder).addEntry(CONTAMINATING, 4));
		registerHazItem(ModBlocks.fallout, fo * powder * 2);
		registerHazItem(block_fallout, fo * powder * block);
		
		HazardSystem.register(brick_asbestos, makeData(ASBESTOS, 1F));
		HazardSystem.register(brick_asbestos_slab, makeData(ASBESTOS, 0.5F));
		HazardSystem.register(brick_asbestos_stairs, makeData(ASBESTOS, 0.75F));
		HazardSystem.register(tile_lab_broken, makeData(ASBESTOS, 1F));
		HazardSystem.register(tile_lab_broken_slab, makeData(ASBESTOS, 0.5F));
		HazardSystem.register(tile_lab_broken_stairs, makeData(ASBESTOS, 0.75F));
		HazardSystem.register(concrete_asbestos, makeData(ASBESTOS, 1F));
		HazardSystem.register(concrete_asbestos_slab, makeData(ASBESTOS, 0.5F));
		HazardSystem.register(concrete_asbestos_stairs, makeData(ASBESTOS, 0.75F));
		
		HazardSystem.register(deco_lead, makeData(TOXIC, 1F));
		HazardSystem.register(deco_asbestos, makeData(ASBESTOS, 0.75F));
		HazardSystem.register(powder_coltan_ore, makeData(ASBESTOS, 3F));
		
		HazardSystem.register(ash_digamma, makeData(DIGAMMA, 0.001F));
		HazardSystem.register(particle_digamma, makeData(RADIATION, 100F).addEntry(DIGAMMA, 0.3333F));
		
		HazardSystem.register(frozen_grass, makeData(CRYOGENIC, 3));
		HazardSystem.register(frozen_log, makeData(CRYOGENIC, 2));
		HazardSystem.register(frozen_planks, makeData(CRYOGENIC, 2));
		HazardSystem.register(frozen_dirt, makeData(CRYOGENIC, 1));
		
		HazardSystem.register(waste_log, makeData(COAL, 2));
		HazardSystem.register(waste_planks, makeData(COAL, 1));
		
		registerHazItem(block_meteor_molten, 0, 4F);
		registerHazItem(arc_electrode_burnt, 0, 2F);
		
		//crystals
		HazardSystem.register(ore_tikite, makeData(RADIATION, trx * ore).addEntry(CRYOGENIC, 1));
		HazardSystem.register(crystal_trixite, makeData(RADIATION, trx * crystal).addEntry(CRYOGENIC, 2));
		
		//nuke parts
		HazardSystem.register(boy_propellant, makeData(EXPLOSIVE, 2F));
		
		registerHazItem(gadget_core, pu239 * nugget * 10);
		registerHazItem(boy_target, u235 * ingot * 2);
		registerHazItem(boy_bullet, u235 * ingot);
		registerHazItem(man_core, pu239 * nugget * 10);
		registerHazItem(mike_core, u238 * nugget * 10);
		
		HazardSystem.register(fleija_propellant, makeData(RADIATION, 15F).addEntry(BLINDING, 50F).addEntry(EXPLOSIVE, 8F));
		registerHazItem(fleija_core, 10F);
		
		HazardSystem.register(solinium_propellant, makeData(EXPLOSIVE, 10F));
		registerHazItem(solinium_core, ts * 5 + sa327 * 3, 0, 45F);

		HazardSystem.register(part_lithium, makeData(HYDROACTIVE, 2F));
		HazardSystem.register(part_carbon, makeData(COAL, 2F));
		registerHazItem(part_plutonium, pu * powder * 0.25F);
		
		registerHazItem(assembly_schrabidium, sa326 / 6F);
		registerHazItem(gun_revolver_schrabidium_ammo, sa326 / 6F);
		registerHazItem(assembly_lead, 0.1F);
		registerHazItem(gun_revolver_lead_ammo, 0.1F);
		
		registerHazItem(drillbit_tcalloy, 20 * tcalloy);
		registerHazItem(drillbit_tcalloy_diamond, 20 * tcalloy);
		registerHazItem(drillbit_ferro, 24 * ferro);
		registerHazItem(drillbit_ferro_diamond, 24 * ferro);
		
		//Fluid Hazards
		for(Fluid entry : FluidRegistry.getRegisteredFluids().values()) {
			if(FluidTypeHandler.noContainer(entry)) continue;
			registerFluidBasic(entry);
		}
		registerFluid("radwater_fluid", 4, 0);
		registerFluid("hydrogen", 0, 0, 0, 4, 0);
		registerFluid("deuterium", 0, 0, 0, 4, 0);
		registerFluid("tritium", 0.5F, 0, 0, 4, 0);
		registerFluid("uf6", 2, 0);
		registerFluid("puf6", 10, 0);
		registerFluid("sas3", 20, 50);
		registerFluid("wastefluid", 80, 0);
		registerFluid("wastegas", 70, 0);
		registerFluid("toxic_fluid", 1000, 0);
		registerFluid("watz", 400, 0, 8, 0, 0);
		registerFluid("mud_fluid", 400, 0, 8, 0, 0);
		registerFluid("radiosolvent", 200, 0, 2, 0, 0);
		registerFluid("schrabidic", 700, 20);
		registerFluid("corium_fluid", 10000, 0);
		registerFluid("mercury", 0, 0, 3, 0, 0);
		registerFluid("gasoline", 0, 0, 2, 0, 0);
		registerFluid("balefire", 20000, 0);
		registerFluid("liquid_osmiridium", 20, 0, 0, 0, 0.005F);
		
		registerFluid("poison", 0, 0, 7, 0, 0);
		/*
		 * Blacklist
		 */
		// for(String ore : TH232.ores()) HazardSystem.blacklist(ore);
		// for(String ore : U.ores()) HazardSystem.blacklist(ore);

		
		registerTrafos();
	}
	
	public static void registerTrafos() {
		HazardSystem.trafos.add(new HazardTransformerRadiationContainer());
		HazardSystem.trafos.add(new HazardTransformerFluidContainer());
		// if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeCrates))	HazardSystem.trafos.add(new HazardTransformerRadiationContainer());
		// if(!(GeneralConfig.enableLBSM && GeneralConfig.enableLBSMSafeMEDrives))	HazardSystem.trafos.add(new HazardTransformerRadiationME());
	}

	private static void registerHazItem(Object item, float rads){
		registerHazItem(item, rads, 0, 0, 0, 0);
	}

	private static void registerHazItem(Object item, float rads, float hot){
		registerHazItem(item, rads, hot, 0, 0, 0);
	}

	private static void registerHazItem(Object item, float rads, float hot, float blind){
		registerHazItem(item, rads, hot, blind, 0, 0);
	}

	private static void registerHazItem(Object item, float rads, float hot, float blind, float tox, float hydro){
		HazardData data = new HazardData();
		if(rads > 0) data.addEntry(new HazardEntry(RADIATION, rads));
		if(hot > 0) data.addEntry(new HazardEntry(HOT, hot));
		if(tox > 0) data.addEntry(new HazardEntry(TOXIC, tox));
		if(blind > 0) data.addEntry(new HazardEntry(BLINDING, hot));
		if(hydro > 0) data.addEntry(new HazardEntry(HYDROACTIVE, hydro));
		if(!data.isEmpty())
			HazardSystem.register(item, data);
	}
	private static void registerFluid(String f, float rads, float blind){
		registerFluid(f, rads, blind, 0, 0, 0);
	}

	private static void registerFluid(String f, float rads, float blind, float tox, float expl, float dig){
		Fluid fluid = FluidRegistry.getFluid(f);
		if(fluid == null) return;
		int temp = fluid.getTemperature()-273;
		HazardData data = new HazardData();

		if(rads > 0) data.addEntry(new HazardEntry(RADIATION, rads));
		if(temp > 100) data.addEntry(new HazardEntry(HOT, Math.min(50, temp/50F)));
		if(temp < -60) data.addEntry(new HazardEntry(CRYOGENIC, Math.abs(temp/20F)));
		if(tox > 0) data.addEntry(new HazardEntry(TOXIC, tox));
		if(blind > 0) data.addEntry(new HazardEntry(BLINDING, blind));
		if(expl > 0) data.addEntry(new HazardEntry(EXPLOSIVE, expl));
		if(dig > 0) data.addEntry(new HazardEntry(DIGAMMA, dig));
		if(!data.isEmpty())
			HazardSystem.registerFluid(f, data);
	}

	private static void registerFluidBasic(Fluid fluid){
		int temp = fluid.getTemperature()-273;
		HazardData data = new HazardData();

		if(temp > 100) data.addEntry(new HazardEntry(HOT, Math.min(50, temp/50F)));
		if(temp < -60) data.addEntry(new HazardEntry(CRYOGENIC, Math.abs(temp/20F)));
		if(!data.isEmpty())
			HazardSystem.registerFluid(fluid.getName(), data);
	}
	
	private static HazardData makeData() { return new HazardData(); }
	private static HazardData makeData(HazardTypeBase hazard) { return new HazardData().addEntry(hazard); }
	private static HazardData makeData(HazardTypeBase hazard, float level) { return new HazardData().addEntry(hazard, level); }
	private static HazardData makeData(HazardTypeBase hazard, float level, boolean override) { return new HazardData().addEntry(hazard, level, override); }
	
	private static void registerRBMKPellet(Item pellet, float base, float dep) { registerRBMKPellet(pellet, base, dep, false, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear) { registerRBMKPellet(pellet, base, dep, linear, 0F, 0F); }
	private static void registerRBMKPellet(Item pellet, float base, float dep, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma).addMod(new HazardModifierRBMKRadiation(digamma * 10F, linear)));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRBMKRod(Item rod, float base, float dep) { registerRBMK(rod, base, dep, true, false, 0F, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, float blinding) { registerRBMK(rod, base, dep, true, false, blinding, 0F); }
	private static void registerRBMKRod(Item rod, float base, float dep, boolean linear) { registerRBMK(rod, base, dep, true, linear, 0F, 0F); }
	
	private static void registerRBMK(Item rod, float base, float dep, boolean hot, boolean linear, float blinding, float digamma) {
		
		HazardData data = new HazardData();
		if(base > 0) data.addEntry(new HazardEntry(RADIATION, base).addMod(new HazardModifierRBMKRadiation(dep, linear)));
		if(hot) data.addEntry(new HazardEntry(HOT, 0).addMod(new HazardModifierRBMKHot()));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		if(digamma > 0) data.addEntry(new HazardEntry(DIGAMMA, digamma).addMod(new HazardModifierRBMKRadiation(digamma * 10F, linear)));
		HazardSystem.register(rod, data);
	}
	
	private static void registerRTGPellet(Item pellet, float base, float target) { registerRTGPellet(pellet, base, target, 0, 0); }
	private static void registerRTGPellet(Item pellet, float base, float target, float hot) { registerRTGPellet(pellet, base, target, hot, 0); }
	
	private static void registerRTGPellet(Item pellet, float base, float target, float hot, float blinding) {
		HazardData data = new HazardData();
		data.addEntry(new HazardEntry(RADIATION, base));
		if(hot > 0) data.addEntry(new HazardEntry(HOT, hot));
		if(blinding > 0) data.addEntry(new HazardEntry(BLINDING, blinding));
		HazardSystem.register(pellet, data);
	}
	
	private static void registerRods(Item rodSingle, Item dualRod, Item quadRod, float base) {
		registerRods(rodSingle, dualRod, quadRod, base, 0, 0);
	}

	private static void registerRods(Item rodSingle, Item dualRod, Item quadRod, float base, float hot, float blind) {
		registerHazItem(rodSingle, base * rod, hot * rod, blind * rod);
		registerHazItem(dualRod, base * rod_dual, hot * rod_dual, blind * rod_dual);
		registerHazItem(quadRod, base * rod_quad, hot * rod_quad, blind * rod_quad);
	}
}
