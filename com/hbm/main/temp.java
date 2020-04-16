package com.hbm.main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.items.special.ItemBattery;
import com.hbm.items.special.ItemCell;
import com.hbm.items.tool.ItemAssemblyTemplate;
import com.hbm.items.tool.ItemFluidCanister;
import com.hbm.items.tool.ItemFluidTank;

import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class temp {

	public enum EnumAssemblyTemplate {

		IRON_PLATE,
		GOLD_PLATE,
		TITANIUM_PLATE,
		ALUMINIUM_PLATE,
		STEEL_PLATE,
		LEAD_PLATE,
		COPPER_PLATE,
		ADVANCED_PLATE,
		SCHRABIDIUM_PLATE,
		CMB_PLATE,
		SATURN_PLATE,
		MIXED_PLATE,
		ALUMINIUM_WIRE,
		COPPER_WIRE,
		TUNGSTEN_WIRE,
		REDCOPPER_WIRE,
		ADVANCED_WIRE,
		GOLD_WIRE,
		SCHRABIDIUM_WIRE,
		MAGNETIZED_WIRE,
		HAZMAT_CLOTH,
		ASBESTOS_CLOTH,
		COAL_FILTER,
		CENTRIFUGE_ELEMENT,
		CENTRIFUGE_TOWER,
		DEE_MAGNET,
		FLAT_MAGNET,
		CYCLOTRON_TOWER,
		REACTOR_CORE,
		RTG_UNIT,
		HEAT_UNIT,
		GRAVITY_UNIT,
		TITANIUM_DRILL,
		TELEPAD, TELEKIT,
		GEASS_REACTOR,
		MISSILE_ASSEMBLY,
		CARRIER,
		WT1_GENERIC,
		WT2_GENERIC,
		WT3_GENERIC,
		WT1_FIRE,
		WT2_FIRE,
		WT3_FIRE,
		WT1_CLUSTER,
		WT2_CLUSTER,
		WT3_CLUSTER,
		WT1_BUSTER,
		WT2_BUSTER,
		WT3_BUSTER,
		W_NUCLEAR,
		W_MIRVLET,
		W_MIRV,
		W_ENDOTHERMIC,
		W_EXOTHERMIC,
		T1_TANK,
		T2_TANK,
		T3_TANK,
		T1_THRUSTER,
		T2_THRUSTER,
		T3_THRUSTER,
		NUCLEAR_THRUSTER,
		SAT_BASE,
		SAT_MAPPER,
		SAT_SCANNER,
		SAT_RADAR,
		SAT_LASER,
		SAT_RESONATOR,
		SAT_FOEQ,
		SAT_MINER,
		CHOPPER_HEAD,
		CHOPPER_GUN,
		CHOPPER_BODY,
		CHOPPER_TAIL,
		CHOPPER_WING,
		CHOPPER_BLADES,
		CIRCUIT_1,
		CIRCUIT_2,
		CIRCUIT_3,
		RTG_PELLET,
		WEAK_PELLET,
		FUSION_PELLET,
		CLUSTER_PELLETS,
		GUN_PELLETS,
		AUSTRALIUM_MACHINE,
		MAGNETRON,
		W_SP,
		W_SHE,
		W_SME,
		W_SLE,
		W_B,
		W_N,
		W_L,
		W_A,
		UPGRADE_TEMPLATE,
		UPGRADE_RED_I,
		UPGRADE_RED_II,
		UPGRADE_RED_III,
		UPGRADE_GREEN_I,
		UPGRADE_GREEN_II,
		UPGRADE_GREEN_III,
		UPGRADE_BLUE_I,
		UPGRADE_BLUE_II,
		UPGRADE_BLUE_III,
		UPGRADE_PURPLE_I,
		UPGRADE_PURPLE_II,
		UPGRADE_PURPLE_III,
		UPGRADE_PINK_I,
		UPGRADE_PINK_II,
		UPGRADE_PINK_III,
		UPGRADE_RANGE,
		UPGRADE_HEALTH,
		FUSE,
		REDCOIL_CAPACITOR,
		TITANIUM_FILTER,
		LITHIUM_BOX,
		BERYLLIUM_BOX,
		COAL_BOX,
		COPPER_BOX,
		PLUTONIUM_BOX,
		THERMO_ELEMENT,
		ANGRY_METAL,
		METEOR_BLOCK,
		CMB_TILE,
		CMB_BRICKS,
		HATCH_FRAME,
		HATCH_CONTROLLER,
		BLAST_DOOR,
		SLIDING_DOOR,
		CENTRIFUGE,
		CENTRIFUGE_GAS,
		BREEDING_REACTOR,
		RTG_FURNACE,
		RAD_GEN,
		DIESEL_GENERATOR,
		SELENIUM_GENERATOR,
		NUCLEAR_GENERATOR,
		CYCLOTRON,
		RT_GENERATOR,
		BATTERY,
		BATTERY_L,
		BATTERY_S,
		BATTERY_D,
		//HE_TO_RF,
		//RF_TO_HE,
		SHREDDER,
		DERRICK,
		PUMPJACK,
		FLARE_STACK,
		REFINERY,
		EPRESS,
		CHEMPLANT,
		TANK,
		MINER,
		TURBOFAN,
		TELEPORTER,
		SCHRABTRANS,
		CMB_FURNACE,
		FA_HULL,
		FA_HATCH,
		FA_CORE,
		FA_PORT,
		LR_ELEMENT,
		LR_CONTROL,
		LR_HATCH,
		LR_PORT,
		LR_CORE,
		LF_MAGNET,
		LF_CENTER,
		LF_MOTOR,
		LF_HEATER,
		LF_HATCH,
		LF_CORE,
		LW_ELEMENT,
		LW_CONTROL,
		LW_COOLER,
		LW_STRUTURE,
		LW_HATCH,
		LW_PORT,
		LW_CORE,
		FW_PORT,
		FW_MAGNET,
		FW_COMPUTER,
		FW_CORE,
		GADGET,
		LITTLE_BOY,
		FAT_MAN,
		IVY_MIKE,
		TSAR_BOMB,
		PROTOTYPE,
		FLEIJA,
		SOLINIUM,
		N2,
		CUSTOM_NUKE,
		BOMB_LEV,
		BOMB_ENDO,
		BOMB_EXO,
		LAUNCH_PAD,
		HUNTER_CHOPPER,
		TURRET_LIGHT,
		TURRET_HEAVY,
		TURRET_ROCKET,
		TURRET_FLAMER,
		TURRET_TAU,
		TURRET_SPITFIRE,
		TURRET_CIWS,
		TURRET_CHEAPO,
		MISSILE_HE_1,
		MISSILE_FIRE_1,
		MISSILE_CLUSTER_1,
		MISSILE_BUSTER_1,
		MISSILE_HE_2,
		MISSILE_FIRE_2,
		MISSILE_CLUSTER_2,
		MISSILE_BUSTER_2,
		MISSILE_HE_3,
		MISSILE_FIRE_3,
		MISSILE_CLUSTER_3,
		MISSILE_BUSTER_3,
		MISSILE_NUCLEAR,
		MISSILE_MIRV,
		MISSILE_ENDO,
		MISSILE_EXO,
		DEFAB,
		MINI_NUKE,
		MINI_MIRV,
		DARK_PLUG,
		COMBINE_BALL,
		GRENADE_FLAME,
		GRENADE_SHRAPNEL,
		GRENAGE_CLUSTER,
		GREANADE_FLARE,
		GRENADE_LIGHTNING,
		GRENADE_IMPULSE,
		GRENADE_PLASMA,
		GRENADE_TAU,
		GRENADE_SCHRABIDIUM,
		GRENADE_NUKE,
		GRENADE_ZOMG,
		GRENADE_BLACK_HOLE,
		POWER_FIST,
		GADGET_PROPELLANT,
		GADGET_WIRING,
		GADGET_CORE,
		BOY_SHIELDING,
		BOY_TARGET,
		BOY_BULLET,
		BOY_PRPELLANT,
		BOY_IGNITER,
		MAN_PROPELLANT,
		MAN_IGNITER,
		MAN_CORE,
		MIKE_TANK,
		MIKE_DEUT,
		MIKE_COOLER,
		FLEIJA_IGNITER,
		FLEIJA_CORE,
		FLEIJA_PROPELLANT,
		SOLINIUM_IGNITER,
		SOLINIUM_CORE,
		SOLINIUM_PROPELLANT,
		SCHRABIDIUM_HAMMER,
		COMPONENT_LIMITER,
		COMPONENT_EMITTER,
		AMS_LIMITER,
		AMS_EMITTER,
		RADAR,
		FORCEFIELD,
		
		MP_T_10_KEROSENE(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.pipes_steel, 1),
				new ItemStack(ModItems.ingot_tungsten, 4),
				new ItemStack(ModItems.plate_steel, 4)),
				new ItemStack(ModItems.mp_thruster_10_kerosene)),
		MP_T_10_SOLID(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.coil_tungsten, 1),
				new ItemStack(ModItems.ingot_dura_steel, 4),
				new ItemStack(ModItems.plate_steel, 4)),
				new ItemStack(ModItems.mp_thruster_10_solid)),
		MP_T_10_XENON(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 4),
				new ItemStack(ModItems.pipes_steel, 2),
				new ItemStack(ModItems.arc_electrode, 4)),
				new ItemStack(ModItems.mp_thruster_10_xenon)),
		MP_T_15_KEROSENE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.pipes_steel, 4),
				new ItemStack(ModItems.ingot_tungsten, 8),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_desh, 4)),
				new ItemStack(ModItems.mp_thruster_15_kerosene)),
		MP_T_15_KEROSENE_TWIN(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.pipes_steel, 2),
				new ItemStack(ModItems.ingot_tungsten, 4),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_desh, 1)),
				new ItemStack(ModItems.mp_thruster_15_kerosene_dual)),
		MP_T_15_KEROSENE_TRIPLE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.pipes_steel, 3),
				new ItemStack(ModItems.ingot_tungsten, 6),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_desh, 2)),
				new ItemStack(ModItems.mp_thruster_15_kerosene_triple)),
		MP_T_15_SOLID(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_dura_steel, 6),
				new ItemStack(ModItems.coil_tungsten, 3)),
				new ItemStack(ModItems.mp_thruster_15_solid)),
		MP_T_15_SOLID_HEXDECUPLE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_dura_steel, 12),
				new ItemStack(ModItems.coil_tungsten, 6)),
				new ItemStack(ModItems.mp_thruster_15_solid_hexdecuple)),
		MP_T_15_HYDROGEN(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.pipes_steel, 4),
				new ItemStack(ModItems.ingot_tungsten, 8),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.tank_steel, 1),
				new ItemStack(ModItems.ingot_desh, 4)),
				new ItemStack(ModItems.mp_thruster_15_hydrogen)),
		MP_T_15_HYDROGEN_TWIN(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.pipes_steel, 2),
				new ItemStack(ModItems.ingot_tungsten, 4),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.tank_steel, 1),
				new ItemStack(ModItems.ingot_desh, 1)),
				new ItemStack(ModItems.mp_thruster_15_hydrogen_dual)),
		MP_T_15_BALEFIRE_SHORT(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_polymer, 8),
				new ItemStack(ModBlocks.reactor_element, 1),
				new ItemStack(ModItems.ingot_desh, 8),
				new ItemStack(ModItems.plate_saturnite, 12),
				new ItemStack(ModItems.board_copper, 2),
				new ItemStack(ModItems.ingot_uranium_fuel, 4),
				new ItemStack(ModItems.pipes_steel, 2)),
				new ItemStack(ModItems.mp_thruster_15_balefire_short)),
		MP_T_15_BALEFIRE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_polymer, 16),
				new ItemStack(ModBlocks.reactor_element, 2),
				new ItemStack(ModItems.ingot_desh, 16),
				new ItemStack(ModItems.plate_saturnite, 24),
				new ItemStack(ModItems.board_copper, 4),
				new ItemStack(ModItems.ingot_uranium_fuel, 8),
				new ItemStack(ModItems.pipes_steel, 2)),
				new ItemStack(ModItems.mp_thruster_15_balefire)),
		MP_T_15_BALEFIRE_LARGE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_polymer, 16),
				new ItemStack(ModBlocks.reactor_element, 2),
				new ItemStack(ModItems.ingot_desh, 24),
				new ItemStack(ModItems.plate_saturnite, 32),
				new ItemStack(ModItems.board_copper, 4),
				new ItemStack(ModItems.ingot_uranium_fuel, 8),
				new ItemStack(ModItems.pipes_steel, 2)),
				new ItemStack(ModItems.mp_thruster_15_balefire_large)),
		MP_T_20_KEROSENE(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.pipes_steel, 8),
				new ItemStack(ModItems.ingot_tungsten, 16),
				new ItemStack(ModItems.plate_steel, 12),
				new ItemStack(ModItems.ingot_desh, 8)),
				new ItemStack(ModItems.mp_thruster_20_kerosene)),
		MP_T_20_KEROSENE_TWIN(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.pipes_steel, 4),
				new ItemStack(ModItems.ingot_tungsten, 8),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_desh, 4)),
				new ItemStack(ModItems.mp_thruster_20_kerosene_dual)),
		MP_T_20_KEROSENE_TRIPLE(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.pipes_steel, 6),
				new ItemStack(ModItems.ingot_tungsten, 12),
				new ItemStack(ModItems.plate_steel, 8),
				new ItemStack(ModItems.ingot_desh, 6)),
				new ItemStack(ModItems.mp_thruster_20_kerosene_triple)),
		MP_T_20_SOLID(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.coil_tungsten, 8),
				new ItemStack(ModItems.ingot_dura_steel, 16),
				new ItemStack(ModItems.plate_steel, 12)),
				new ItemStack(ModItems.mp_thruster_20_solid)),
		MP_T_20_SOLID_MULTI(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.coil_tungsten, 12),
				new ItemStack(ModItems.ingot_dura_steel, 18),
				new ItemStack(ModItems.plate_steel, 12)),
				new ItemStack(ModItems.mp_thruster_20_solid_multi)),
		MP_T_20_SOLID_MULTIER(500, Arrays.asList(
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModItems.coil_tungsten, 16),
				new ItemStack(ModItems.ingot_dura_steel, 20),
				new ItemStack(ModItems.plate_steel, 12)),
				new ItemStack(ModItems.mp_thruster_20_solid_multier)),
		
		MP_F_10_KEROSENE(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 2),
				new ItemStack(ModBlocks.steel_scaffold, 3),
				new ItemStack(ModItems.plate_titanium, 12),
				new ItemStack(ModItems.plate_steel, 3)),
				new ItemStack(ModItems.mp_fuselage_10_kerosene)),
		MP_F_10_SOLID(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 2),
				new ItemStack(ModBlocks.steel_scaffold, 3),
				new ItemStack(ModItems.plate_titanium, 12),
				new ItemStack(ModItems.plate_aluminium, 3)),
				new ItemStack(ModItems.mp_fuselage_10_solid)),
		MP_F_10_XENON(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 2),
				new ItemStack(ModBlocks.steel_scaffold, 3),
				new ItemStack(ModItems.plate_titanium, 12),
				new ItemStack(ModItems.board_copper, 3)),
				new ItemStack(ModItems.mp_fuselage_10_xenon)),
		MP_F_10_KEROSENE_LONG(200, Arrays.asList(
				new ItemStack(ModItems.seg_10, 2),
				new ItemStack(ModBlocks.steel_scaffold, 6),
				new ItemStack(ModItems.plate_titanium, 24),
				new ItemStack(ModItems.plate_steel, 6)),
				new ItemStack(ModItems.mp_fuselage_10_long_kerosene)),
		MP_F_10_SOLID_LONG(200, Arrays.asList(
				new ItemStack(ModItems.seg_10, 2),
				new ItemStack(ModBlocks.steel_scaffold, 6),
				new ItemStack(ModItems.plate_titanium, 24),
				new ItemStack(ModItems.plate_aluminium, 6)),
				new ItemStack(ModItems.mp_fuselage_10_long_solid)),
		MP_F_10_15_KEROSENE(300, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModBlocks.steel_scaffold, 9),
				new ItemStack(ModItems.plate_titanium, 36),
				new ItemStack(ModItems.plate_steel, 9)),
				new ItemStack(ModItems.mp_fuselage_10_15_kerosene)),
		MP_F_10_15_SOLID(300, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModBlocks.steel_scaffold, 9),
				new ItemStack(ModItems.plate_titanium, 36),
				new ItemStack(ModItems.plate_aluminium, 9)),
				new ItemStack(ModItems.mp_fuselage_10_15_solid)),
		MP_F_10_15_HYDROGEN(300, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModBlocks.steel_scaffold, 9),
				new ItemStack(ModItems.plate_titanium, 36),
				new ItemStack(ModItems.plate_iron, 9)),
				new ItemStack(ModItems.mp_fuselage_10_15_hydrogen)),
		MP_F_10_15_BALEFIRE(300, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModBlocks.steel_scaffold, 9),
				new ItemStack(ModItems.plate_titanium, 36),
				new ItemStack(ModItems.plate_saturnite, 9)),
				new ItemStack(ModItems.mp_fuselage_10_15_balefire)),
		MP_F_15_KEROSENE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 2),
				new ItemStack(ModBlocks.steel_scaffold, 12),
				new ItemStack(ModItems.plate_titanium, 48),
				new ItemStack(ModItems.plate_steel, 12)),
				new ItemStack(ModItems.mp_fuselage_15_kerosene)),
		MP_F_15_SOLID(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 2),
				new ItemStack(ModBlocks.steel_scaffold, 12),
				new ItemStack(ModItems.plate_titanium, 48),
				new ItemStack(ModItems.plate_aluminium, 12)),
				new ItemStack(ModItems.mp_fuselage_15_solid)),
		MP_F_15_HYDROGEN(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 2),
				new ItemStack(ModBlocks.steel_scaffold, 12),
				new ItemStack(ModItems.plate_titanium, 48),
				new ItemStack(ModItems.plate_iron, 12)),
				new ItemStack(ModItems.mp_fuselage_15_hydrogen)),
		MP_F_15_BALEFIRE(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 2),
				new ItemStack(ModBlocks.steel_scaffold, 12),
				new ItemStack(ModItems.plate_titanium, 48),
				new ItemStack(ModItems.plate_saturnite, 12)),
				new ItemStack(ModItems.mp_fuselage_15_balefire)),
		MP_F_15_20_KEROSENE(600, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModBlocks.steel_scaffold, 16),
				new ItemStack(ModItems.plate_titanium, 64),
				new ItemStack(ModItems.plate_steel, 16)),
				new ItemStack(ModItems.mp_fuselage_15_20_kerosene)),
		MP_F_15_20_SOLID(600, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.seg_20, 1),
				new ItemStack(ModBlocks.steel_scaffold, 16),
				new ItemStack(ModItems.plate_titanium, 64),
				new ItemStack(ModItems.plate_aluminium, 16)),
				new ItemStack(ModItems.mp_fuselage_15_20_solid)),

		MP_W_10_HE(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(Blocks.TNT, 3),
				new ItemStack(ModItems.circuit_targeting_tier2, 1)),
				new ItemStack(ModItems.mp_warhead_10_he)),
		MP_W_10_INC(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_titanium, 4),
				new ItemStack(ModItems.powder_fire, 3),
				new ItemStack(Blocks.TNT, 2),
				new ItemStack(ModItems.circuit_targeting_tier2, 1)),
				new ItemStack(ModItems.mp_warhead_10_incendiary)),
		MP_W_10_BUSTER(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_titanium, 4),
				new ItemStack(ModBlocks.det_charge, 1),
				new ItemStack(ModBlocks.det_cord, 4),
				new ItemStack(ModItems.board_copper, 4),
				new ItemStack(ModItems.circuit_targeting_tier3, 1)),
				new ItemStack(ModItems.mp_warhead_10_buster)),
		MP_W_10_TATER(200, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 6),
				new ItemStack(ModItems.ingot_pu239, 1),
				new ItemStack(Blocks.TNT, 2),
				new ItemStack(ModItems.circuit_targeting_tier3, 1)),
				new ItemStack(ModItems.mp_warhead_10_nuclear)),
		MP_W_10_BORIS(300, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 8),
				new ItemStack(ModItems.plate_aluminium, 4),
				new ItemStack(ModItems.ingot_pu239, 2),
				new ItemStack(ModBlocks.det_charge, 2),
				new ItemStack(ModItems.circuit_targeting_tier4, 1)),
				new ItemStack(ModItems.mp_warhead_10_nuclear_large)),
		MP_W_10_TAINT(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 12),
				new ItemStack(ModBlocks.det_cord, 2),
				new ItemStack(ModItems.powder_magic, 12),
				FluidUtil.getFilledBucket(new FluidStack(ModForgeFluids.mud_fluid, 1000))),
				new ItemStack(ModItems.mp_warhead_10_taint)),
		MP_W_10_CLOUD(100, Arrays.asList(
				new ItemStack(ModItems.seg_10, 1),
				new ItemStack(ModItems.plate_steel, 12),
				new ItemStack(ModBlocks.det_cord, 2),
				new ItemStack(ModItems.grenade_pink_cloud, 2)),
				new ItemStack(ModItems.mp_warhead_10_cloud)),
		MP_W_15_HE(200, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 16),
				new ItemStack(ModBlocks.det_charge, 4),
				new ItemStack(ModItems.circuit_targeting_tier3, 1)),
				new ItemStack(ModItems.mp_warhead_15_he)),
		MP_W_15_INC(200, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 16),
				new ItemStack(ModBlocks.det_charge, 2),
				new ItemStack(ModItems.powder_fire, 8),
				new ItemStack(ModItems.circuit_targeting_tier3, 1)),
				new ItemStack(ModItems.mp_warhead_15_incendiary)),
		MP_W_15_BERTHA(500, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 24),
				new ItemStack(ModItems.plate_titanium, 12),
				new ItemStack(ModItems.ingot_pu239, 3),
				new ItemStack(ModBlocks.det_charge, 4),
				new ItemStack(ModItems.circuit_targeting_tier4, 1)),
				new ItemStack(ModItems.mp_warhead_15_nuclear)),
		MP_W_15_NERV(400, Arrays.asList(
				new ItemStack(ModItems.seg_15, 1),
				new ItemStack(ModItems.plate_steel, 8),
				new ItemStack(ModItems.plate_titanium, 20),
				new ItemStack(ModBlocks.det_charge, 24),
				new ItemStack(Blocks.REDSTONE_BLOCK, 12),
				new ItemStack(ModItems.powder_magnetized_tungsten, 6),
				new ItemStack(ModItems.circuit_targeting_tier4, 1)),
				new ItemStack(ModItems.mp_warhead_15_n2));

		private EnumAssemblyTemplate() { }
		
		private EnumAssemblyTemplate(int time, List<ItemStack> ingredients, ItemStack output) {
			this.time = time;
			this.ingredients = ingredients;
			this.output = output;
		}
		
		public int time = 0;
		public List<ItemStack> ingredients = null;
		public ItemStack output = null;
		
		public static EnumAssemblyTemplate getEnum(int i) {
			return EnumAssemblyTemplate.values()[i];
		}
		
		public String getName() {
			return this.toString();
		}
	}
	
	public static List<ItemStack> getRecipeFromTempate(EnumAssemblyTemplate template) {
		
		List<ItemStack> list = new ArrayList<ItemStack>();
		
		if(template.ingredients != null)
			return template.ingredients;
		
		switch(template) {
        case IRON_PLATE:
			list.add(new ItemStack(Items.IRON_INGOT, 3));
			break;
		case GOLD_PLATE:
			list.add(new ItemStack(Items.GOLD_INGOT, 3));
			break;
		case TITANIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_titanium, 3));
			break;
		case ALUMINIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_aluminium, 3));
			break;
		case STEEL_PLATE:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			break;
		case LEAD_PLATE:
			list.add(new ItemStack(ModItems.ingot_lead, 3));
			break;
		case COPPER_PLATE:
			list.add(new ItemStack(ModItems.ingot_copper, 3));
			break;
		case ADVANCED_PLATE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 3));
			break;
		case SCHRABIDIUM_PLATE:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 3));
			break;
		case CMB_PLATE:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 3));
			break;
		case SATURN_PLATE:
			list.add(new ItemStack(ModItems.ingot_saturnite, 3));
			break;
		case ALUMINIUM_WIRE:
			list.add(new ItemStack(ModItems.ingot_aluminium, 1));
			break;
		case COPPER_WIRE:
			list.add(new ItemStack(ModItems.ingot_copper, 1));
			break;
		case TUNGSTEN_WIRE:
			list.add(new ItemStack(ModItems.ingot_tungsten, 1));
			break;
		case REDCOPPER_WIRE:
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			break;
		case ADVANCED_WIRE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 1));
			break;
		case GOLD_WIRE:
			list.add(new ItemStack(Items.GOLD_INGOT, 1));
			break;
		case SCHRABIDIUM_WIRE:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 1));
			break;
		case MAGNETIZED_WIRE:
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
			break;
		case CIRCUIT_1:
			list.add(new ItemStack(ModItems.circuit_raw, 1));
			break;
		case SCHRABIDIUM_HAMMER:
			list.add(new ItemStack(ModBlocks.block_schrabidium, 15));
			list.add(new ItemStack(ModItems.ingot_polymer, 64*2));
			list.add(new ItemStack(Items.NETHER_STAR, 3));
			list.add(new ItemStack(ModItems.fragment_meteorite, 64*8));
			break;
		case MIXED_PLATE:
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_lead, 4));
			break;
		case HAZMAT_CLOTH:
			list.add(new ItemStack(ModItems.powder_lead, 4));
			list.add(new ItemStack(Items.STRING, 8));
			break;
		case ASBESTOS_CLOTH:
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			list.add(new ItemStack(Items.STRING, 6));
			list.add(new ItemStack(Blocks.WOOL, 1));
			break;
		case COAL_FILTER:
			list.add(new ItemStack(ModItems.powder_coal, 4));
			list.add(new ItemStack(Items.STRING, 6));
			list.add(new ItemStack(Items.PAPER, 1));
			break;
		case CENTRIFUGE_ELEMENT:
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CENTRIFUGE_TOWER:
			list.add(new ItemStack(ModItems.centrifuge_element, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case DEE_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 6));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			break;
		case FLAT_MAGNET:
			list.add(new ItemStack(ModBlocks.fusion_conductor, 5));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			break;
		case CYCLOTRON_TOWER:
			list.add(new ItemStack(ModItems.magnet_circular, 6));
			list.add(new ItemStack(ModItems.magnet_dee, 3));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 24));
			break;
		case REACTOR_CORE:
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			break;
		case RTG_UNIT:
			list.add(new ItemStack(ModItems.thermo_element, 6));
			list.add(new ItemStack(ModItems.board_copper, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case HEAT_UNIT:
			list.add(new ItemStack(ModItems.coil_copper_torus, 3));
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			break;
		case GRAVITY_UNIT:
			list.add(new ItemStack(ModItems.coil_copper, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.nugget_schrabidium, 2));
			break;
		case TITANIUM_DRILL:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 2));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case TELEPAD:
			list.add(new ItemStack(ModItems.ingot_polymer, 12));
			list.add(new ItemStack(ModItems.plate_schrabidium, 2));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case TELEKIT:
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_lead, 16));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.singularity_counter_resonant, 1));
			list.add(new ItemStack(ModItems.singularity_super_heated, 1));
			list.add(new ItemStack(ModItems.powder_power, 4));
			break;
		case GEASS_REACTOR:
			list.add(new ItemStack(ModItems.plate_steel, 15));
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.rod_quad_empty, 10));
			list.add(new ItemStack(Items.DYE, 4, 3));
			break;
		case WT1_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 5));
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(Blocks.TNT, 2));
			break;
		case WT2_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 8));
			list.add(new ItemStack(ModItems.plate_steel, 5));
			list.add(new ItemStack(Blocks.TNT, 4));
			break;
		case WT3_GENERIC:
			list.add(new ItemStack(ModItems.plate_titanium, 15));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(Blocks.TNT, 8));
			break;
		case WT1_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.powder_fire, 4));
			break;
		case WT2_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.powder_fire, 8));
			break;
		case WT3_FIRE:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.powder_fire, 16));
			break;
		case MISSILE_ASSEMBLY:
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_aluminium, 6));
			list.add(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case CARRIER:
			list.add(ItemFluidTank.getFullBarrel(ModForgeFluids.kerosene, 16));
			list.add(new ItemStack(ModItems.thruster_medium, 4));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 6));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 12));
			list.add(new ItemStack(ModItems.plate_titanium, 24));
			list.add(new ItemStack(ModItems.plate_polymer, 128));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			break;
		case WT1_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 4));
			break;
		case WT2_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 8));
			break;
		case WT3_CLUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 16));
			break;
		case WT1_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 8));
			break;
		case WT2_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModBlocks.det_cord, 4));
			list.add(new ItemStack(ModBlocks.det_charge, 4));
			break;
		case WT3_BUSTER:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModBlocks.det_charge, 8));
			break;
		case W_NUCLEAR:
			list.add(new ItemStack(ModItems.boy_shielding, 1));
			list.add(new ItemStack(ModItems.boy_target, 1));
			list.add(new ItemStack(ModItems.boy_bullet, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			break;
		case W_MIRVLET:
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			list.add(new ItemStack(ModItems.plate_steel, 18));
			list.add(new ItemStack(ModItems.ingot_pu239, 1));
			list.add(new ItemStack(Blocks.TNT, 2));
			break;
		case W_MIRV:
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.ingot_pu239, 1));
			list.add(new ItemStack(Blocks.TNT, 8));
			list.add(new ItemStack(ModItems.neutron_reflector, 6));
			list.add(new ItemStack(ModItems.lithium, 4));
			list.add(ItemCell.getFullCell(ModForgeFluids.deuterium, 6));
			break;
		case W_ENDOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_endo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case W_EXOTHERMIC:
			list.add(new ItemStack(ModBlocks.therm_exo, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case T1_TANK:
			list.add(ItemFluidCanister.getFullCanister(ModForgeFluids.kerosene, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T2_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_small, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T3_TANK:
			list.add(new ItemStack(ModItems.fuel_tank_medium, 3));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case T1_THRUSTER:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			break;
		case T2_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_copper, 4));
			break;
		case T3_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case NUCLEAR_THRUSTER:
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.board_copper, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModBlocks.machine_reactor_small, 1));
			break;
		case SAT_BASE:
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_desh, 4));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(ItemFluidTank.getFullBarrel(ModForgeFluids.kerosene));
			list.add(new ItemStack(ModItems.photo_panel, 24));
			list.add(new ItemStack(ModItems.board_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case SAT_MAPPER:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 3));
			list.add(new ItemStack(ModItems.plate_desh, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			list.add(new ItemStack(Items.REDSTONE, 6));
			list.add(new ItemStack(Items.DIAMOND, 1));
			list.add(new ItemStack(Blocks.GLASS_PANE, 6));
			break;
		case SAT_SCANNER:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.plate_titanium, 32));
			list.add(new ItemStack(ModItems.plate_desh, 6));
			list.add(new ItemStack(ModItems.magnetron, 6));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 2));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 6));
			list.add(new ItemStack(Items.DIAMOND, 1));
			break;
		case SAT_RADAR:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 32));
			list.add(new ItemStack(ModItems.magnetron, 12));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.wire_red_copper, 16));
			list.add(new ItemStack(ModItems.coil_gold, 3));
			list.add(new ItemStack(ModItems.circuit_gold, 5));
			list.add(new ItemStack(Items.DIAMOND, 1));
			break;
		case SAT_LASER:
			list.add(new ItemStack(ModItems.ingot_steel, 12));
			list.add(new ItemStack(ModItems.ingot_tungsten, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.board_copper, 24));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 2));
			list.add(new ItemStack(Items.REDSTONE, 16));
			list.add(new ItemStack(Items.DIAMOND, 5));
			list.add(new ItemStack(Blocks.GLASS_PANE, 16));
			break;
		case SAT_RESONATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 32));
			list.add(new ItemStack(ModItems.ingot_polymer, 48));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			list.add(new ItemStack(ModItems.crystal_xen, 1));
			list.add(new ItemStack(ModItems.ingot_starmetal, 7));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier6, 2));
			break;
		case SAT_FOEQ:
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.plate_desh, 8));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(ItemFluidTank.getFullBarrel(FluidRegistry.WATER));
			list.add(new ItemStack(ModItems.photo_panel, 16));
			list.add(new ItemStack(ModItems.thruster_nuclear, 1));
			list.add(new ItemStack(ModItems.rod_quad_uranium_fuel, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 6));
			list.add(new ItemStack(ModItems.magnetron, 3));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case SAT_MINER:
			list.add(new ItemStack(ModItems.plate_saturnite, 24));
			list.add(new ItemStack(ModItems.plate_desh, 8));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.drill_titanium, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(ItemFluidTank.getFullBarrel(ModForgeFluids.kerosene));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.photo_panel, 12));
			list.add(new ItemStack(ModItems.centrifuge_element, 4));
			list.add(new ItemStack(ModItems.magnetron, 3));
			list.add(new ItemStack(ModItems.plate_polymer, 12));
			list.add(ItemBattery.getEmptyBattery(ModItems.battery_lithium_cell_6));
			break;
		case CHOPPER_HEAD:
			list.add(new ItemStack(ModBlocks.reinforced_glass, 2));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 22));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			break;
		case CHOPPER_GUN:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 6));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 1));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CHOPPER_BODY:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 26));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_TAIL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 5));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.chopper_blades, 2));
			break;
		case CHOPPER_WING:
			list.add(new ItemStack(ModItems.plate_combine_steel, 6));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 3));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			break;
		case CHOPPER_BLADES:
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 2));
			break;
		case CIRCUIT_2:
			list.add(new ItemStack(ModItems.circuit_aluminium, 1));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			list.add(new ItemStack(ModItems.plate_copper, 1));
			break;
		case CIRCUIT_3:
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.powder_gold, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 1));
			break;
		case RTG_PELLET:
			list.add(new ItemStack(ModItems.nugget_pu238, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case WEAK_PELLET:
			list.add(new ItemStack(ModItems.nugget_u238, 4));
			list.add(new ItemStack(ModItems.nugget_pu238, 1));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case FUSION_PELLET:
			list.add(ItemCell.getFullCell(ModForgeFluids.deuterium, 6));
			list.add(ItemCell.getFullCell(ModForgeFluids.tritium, 2));
			list.add(new ItemStack(ModItems.lithium, 4));
			break;
		case CLUSTER_PELLETS:
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(Blocks.TNT, 1));
			break;
		case GUN_PELLETS:
			list.add(new ItemStack(ModItems.nugget_lead, 6));
			break;
		case AUSTRALIUM_MACHINE:
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case MAGNETRON:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.wire_tungsten, 1));
			list.add(new ItemStack(ModItems.coil_tungsten, 1));
			break;
		case W_SP:
			list.add(new ItemStack(ModItems.ingot_schrabidium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SHE:
			list.add(new ItemStack(ModItems.ingot_hes, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SME:
			list.add(new ItemStack(ModItems.ingot_schrabidium_fuel, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_SLE:
			list.add(new ItemStack(ModItems.ingot_les, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_B:
			list.add(new ItemStack(ModItems.ingot_beryllium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_N:
			list.add(new ItemStack(ModItems.ingot_neptunium, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_L:
			list.add(new ItemStack(ModItems.ingot_lead, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case W_A:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 5));
			list.add(new ItemStack(ModItems.plate_iron, 2));
			break;
		case UPGRADE_TEMPLATE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			break;
		case UPGRADE_RED_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 4));
			list.add(new ItemStack(Items.REDSTONE, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_RED_II:
			list.add(new ItemStack(ModItems.upgrade_speed_1, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.REDSTONE, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_RED_III:
			list.add(new ItemStack(ModItems.upgrade_speed_2, 1));
			list.add(new ItemStack(ModItems.powder_red_copper, 2));
			list.add(new ItemStack(Items.REDSTONE, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_GREEN_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 4));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_GREEN_II:
			list.add(new ItemStack(ModItems.upgrade_effect_1, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_GREEN_III:
			list.add(new ItemStack(ModItems.upgrade_effect_2, 1));
			list.add(new ItemStack(ModItems.powder_dura_steel, 2));
			list.add(new ItemStack(ModItems.powder_steel, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_BLUE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 4));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_BLUE_II:
			list.add(new ItemStack(ModItems.upgrade_power_1, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_BLUE_III:
			list.add(new ItemStack(ModItems.upgrade_power_2, 1));
			list.add(new ItemStack(ModItems.powder_lapis, 2));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PURPLE_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PURPLE_II:
			list.add(new ItemStack(ModItems.upgrade_fortune_1, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PURPLE_III:
			list.add(new ItemStack(ModItems.upgrade_fortune_2, 1));
			list.add(new ItemStack(ModItems.powder_diamond, 2));
			list.add(new ItemStack(ModItems.powder_iron, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_PINK_I:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 4));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case UPGRADE_PINK_II:
			list.add(new ItemStack(ModItems.upgrade_afterburn_1, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			break;
		case UPGRADE_PINK_III:
			list.add(new ItemStack(ModItems.upgrade_afterburn_2, 1));
			list.add(new ItemStack(ModItems.powder_polymer, 2));
			list.add(new ItemStack(ModItems.powder_tungsten, 6));
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			break;
		case UPGRADE_RANGE:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 6));
			list.add(new ItemStack(ModItems.powder_diamond, 4));
			break;
		case UPGRADE_HEALTH:
			list.add(new ItemStack(ModItems.upgrade_template, 1));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 6));
			list.add(new ItemStack(ModItems.powder_titanium, 4));
			break;
		case FUSE:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Blocks.GLASS_PANE, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 1));
			break;
		case REDCOIL_CAPACITOR:
			list.add(new ItemStack(ModItems.plate_gold, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 6));
			list.add(new ItemStack(Blocks.REDSTONE_BLOCK, 2));
			break;
		case TITANIUM_FILTER:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.fuse, 1));
			list.add(new ItemStack(ModItems.wire_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.ingot_u238, 2));
			break;
		case LITHIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_lithium, 2));
			break;
		case BERYLLIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_beryllium, 2));
			break;
		case COAL_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_coal, 2));
			break;
		case COPPER_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_copper, 2));
			break;
		case PLUTONIUM_BOX:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.powder_plutonium, 2));
			break;
		case THERMO_ELEMENT:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_aluminium, 2));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case ANGRY_METAL:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			break;
		case METEOR_BLOCK:
			list.add(new ItemStack(ModItems.fragment_meteorite, 100));
			break;
		case CMB_TILE:
			list.add(new ItemStack(ModItems.ingot_combine_steel, 1));
			list.add(new ItemStack(ModItems.plate_combine_steel, 8));
			break;
		case CMB_BRICKS:
			list.add(new ItemStack(ModBlocks.block_magnetized_tungsten, 4));
			list.add(new ItemStack(ModBlocks.brick_concrete, 4));
			list.add(new ItemStack(ModBlocks.cmb_brick, 1));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case HATCH_FRAME:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.wire_aluminium, 4));
			list.add(new ItemStack(Items.REDSTONE, 2));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case HATCH_CONTROLLER:
			list.add(new ItemStack(ModItems.ingot_steel, 3));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.ingot_red_copper, 1));
			list.add(new ItemStack(Items.REDSTONE, 4));
			list.add(new ItemStack(ModBlocks.steel_roof, 5));
			break;
		case BLAST_DOOR:
			list.add(new ItemStack(ModItems.ingot_steel, 128));
			list.add(new ItemStack(ModItems.ingot_tungsten, 32));
			list.add(new ItemStack(ModItems.plate_lead, 48));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 16));
			list.add(new ItemStack(ModItems.bolt_tungsten, 18));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 27));
			list.add(new ItemStack(ModItems.motor, 5));
			break;
		case SLIDING_DOOR:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_tungsten, 8));
			list.add(new ItemStack(ModItems.plate_lead, 12));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 3));
			list.add(new ItemStack(ModItems.plate_polymer, 3));
			list.add(new ItemStack(ModItems.bolt_tungsten, 3));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 3));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case CENTRIFUGE:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(Items.IRON_INGOT, 4));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case CENTRIFUGE_GAS:
			list.add(new ItemStack(ModItems.centrifuge_tower, 1));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.ingot_desh, 2));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			list.add(new ItemStack(ModItems.wire_gold, 4));
			break;
		case BREEDING_REACTOR:
			list.add(new ItemStack(ModItems.reactor_core, 1));
			list.add(new ItemStack(ModItems.ingot_lead, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case RTG_FURNACE:
			list.add(new ItemStack(Blocks.FURNACE, 1));
			list.add(new ItemStack(ModItems.rtg_unit, 3));
			list.add(new ItemStack(ModItems.plate_lead, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case RAD_GEN:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 24));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.reactor_core, 3));
			list.add(new ItemStack(ModItems.ingot_starmetal, 1));
			list.add(new ItemStack(Items.DYE, 1, 1));
			break;
		case DIESEL_GENERATOR:
			list.add(new ItemStack(ModItems.hull_small_steel, 4));
			list.add(new ItemStack(Blocks.PISTON, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case SELENIUM_GENERATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.plate_copper, 8));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.hull_small_steel, 9));
			list.add(new ItemStack(ModItems.pedestal_steel, 1));
			list.add(new ItemStack(ModItems.coil_copper, 4));
			break;
		case NUCLEAR_GENERATOR:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.plate_lead, 8));
			list.add(new ItemStack(ModItems.plate_copper, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.circuit_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			break;
		case CYCLOTRON:
			list.add(new ItemStack(ModItems.cyclotron_tower, 1));
			list.add(new ItemStack(ModItems.board_copper, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 24));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModBlocks.machine_battery, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 20));
			list.add(new ItemStack(ModItems.circuit_red_copper, 12));
			list.add(new ItemStack(ModItems.circuit_gold, 3));
			break;
		case RT_GENERATOR:
			list.add(new ItemStack(ModItems.rtg_unit, 5));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			break;
		case BATTERY:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.sulfur, 12));
			list.add(new ItemStack(ModItems.powder_lead, 12));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BATTERY_L:
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.powder_cobalt, 12));
			list.add(new ItemStack(ModItems.powder_lithium, 12));
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BATTERY_S:
			list.add(new ItemStack(ModItems.ingot_desh, 4));
			list.add(new ItemStack(ModItems.powder_neptunium, 12));
			list.add(new ItemStack(ModItems.powder_schrabidium, 12));
			list.add(new ItemStack(ModItems.ingot_schrabidium, 2));
			list.add(new ItemStack(ModItems.wire_schrabidium, 4));
			break;
		case BATTERY_D:
			list.add(new ItemStack(ModItems.ingot_dineutronium, 24));
			list.add(new ItemStack(ModItems.powder_spark_mix, 12));
			list.add(new ItemStack(ModItems.battery_spark_cell_1000, 1));
			list.add(new ItemStack(ModItems.ingot_combine_steel, 32));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 8));
			break;
		/*case HE_TO_RF:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;
		case RF_TO_HE:
			list.add(new ItemStack(ModItems.ingot_beryllium, 4));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.coil_copper_torus, 1));
			break;*/
		case SHREDDER:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			list.add(new ItemStack(ModBlocks.steel_beam, 2));
			list.add(new ItemStack(Blocks.IRON_BARS, 2));
			list.add(new ItemStack(ModBlocks.red_wire_coated, 1));
			break;
		/*case DEUTERIUM_EXTRACTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			break;*/
		case DERRICK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 20));
			list.add(new ItemStack(ModBlocks.steel_beam, 8));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			break;
		case PUMPJACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 8));
			list.add(new ItemStack(ModBlocks.block_steel, 8));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 24));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			break;
		case FLARE_STACK:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 28));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.thermo_element, 3));
			break;
		case REFINERY:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_copper, 16));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.coil_tungsten, 10));
			list.add(new ItemStack(ModItems.wire_red_copper, 8));
			list.add(new ItemStack(ModItems.circuit_red_copper, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			break;
		case EPRESS:
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.plate_polymer, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 1));
			list.add(new ItemStack(ModItems.bolt_tungsten, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(ItemFluidCanister.getFullCanister(ModForgeFluids.lubricant));
			break;
		case CHEMPLANT:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.tank_steel, 4));
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 16));
			list.add(new ItemStack(ModItems.wire_tungsten, 3));
			list.add(new ItemStack(ModItems.circuit_copper, 4));
			list.add(new ItemStack(ModItems.circuit_red_copper, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			break;
		case TANK:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.hull_big_steel, 4));
			break;
		case MINER:
			list.add(new ItemStack(ModBlocks.steel_scaffold, 6));
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 1));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.ingot_dura_steel, 2));
			list.add(new ItemStack(ModItems.bolt_dura_steel, 2));
			list.add(new ItemStack(ModItems.drill_titanium, 1));
			break;
		case TURBOFAN:
			list.add(new ItemStack(ModItems.hull_big_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 3));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.turbine_tungsten, 1));
			list.add(new ItemStack(ModItems.turbine_titanium, 7));
			list.add(new ItemStack(ModItems.bolt_compound, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 12));
			list.add(new ItemStack(ModItems.wire_red_copper, 24));
			break;
		case TELEPORTER:
			list.add(new ItemStack(ModItems.ingot_titanium, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 12));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(ModItems.telepad, 1));
			list.add(new ItemStack(ModItems.entanglement_kit, 1));
			list.add(new ItemStack(ModBlocks.machine_battery, 2));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 4));
			break;
		case SCHRABTRANS:
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 1));
			list.add(new ItemStack(ModItems.ingot_titanium, 24));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 18));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModItems.plate_desh, 6));
			list.add(new ItemStack(ModItems.plate_polymer, 8));
			list.add(new ItemStack(ModBlocks.machine_battery, 5));
			list.add(new ItemStack(ModItems.circuit_gold, 5));
			break;
		case CMB_FURNACE:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.plate_copper, 6));
			list.add(new ItemStack(ModItems.circuit_gold, 6));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.coil_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_magnetized_tungsten, 12));
			break;
		case FA_HULL:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 6));
			break;
		case FA_HATCH:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			break;
		case FA_CORE:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.plate_steel, 8));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.motor, 16));
			list.add(new ItemStack(Blocks.PISTON, 6));
			break;
		case FA_PORT:
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.fuse, 6));
			break;
		case LR_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.neutron_reflector, 4));
			list.add(new ItemStack(ModItems.plate_lead, 2));
			list.add(new ItemStack(ModItems.rod_empty, 8));
			break;
		case LR_CONTROL:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 6));
			list.add(new ItemStack(ModItems.bolt_tungsten, 6));
			list.add(new ItemStack(ModItems.motor, 1));
			break;
		case LR_HATCH:
			list.add(new ItemStack(ModBlocks.brick_concrete, 1));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			break;
		case LR_PORT:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_copper, 12));
			list.add(new ItemStack(ModItems.wire_tungsten, 4));
			break;
		case LR_CORE:
			list.add(new ItemStack(ModBlocks.reactor_conductor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			break;
		case LF_MAGNET:
			list.add(new ItemStack(ModItems.plate_steel, 10));
			list.add(new ItemStack(ModItems.coil_advanced_alloy, 5));
			break;
		case LF_CENTER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 24));
			break;
		case LF_MOTOR:
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.motor, 4));
			break;
		case LF_HEATER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.neutron_reflector, 6));
			list.add(new ItemStack(ModItems.magnetron, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 4));
			break;
		case LF_HATCH:
			list.add(new ItemStack(ModBlocks.fusion_heater, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LF_CORE:
			list.add(new ItemStack(ModBlocks.fusion_center, 3));
			list.add(new ItemStack(ModItems.circuit_red_copper, 48));
			list.add(new ItemStack(ModItems.circuit_gold, 12));
			break;
		case LW_ELEMENT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 6));
			list.add(new ItemStack(ModItems.rod_empty, 4));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			break;
		case LW_CONTROL:
			list.add(new ItemStack(ModItems.ingot_tungsten, 4));
			list.add(new ItemStack(ModItems.ingot_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 4));
			list.add(new ItemStack(ModItems.circuit_copper, 2));
			break;
		case LW_COOLER:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.niter, 6));
			list.add(new ItemStack(ModItems.powder_quartz, 4));
			break;
		case LW_STRUTURE:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 5));
			break;
		case LW_HATCH:
			list.add(new ItemStack(ModBlocks.reinforced_brick, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			break;
		case LW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.ingot_lead, 2));
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.fuse, 4));
			break;
		case LW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 8));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 12));
			break;
		case FW_PORT:
			list.add(new ItemStack(ModItems.ingot_tungsten, 6));
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			break;
		case FW_MAGNET:
			list.add(new ItemStack(ModItems.plate_combine_steel, 10));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 5));
			break;
		case FW_COMPUTER:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 16));
			list.add(new ItemStack(ModItems.powder_diamond, 6));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 6));
			list.add(new ItemStack(ModItems.powder_desh, 4));
			break;
		case FW_CORE:
			list.add(new ItemStack(ModBlocks.block_meteor, 1));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 24));
			list.add(new ItemStack(ModItems.powder_diamond, 8));
			list.add(new ItemStack(ModItems.powder_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.powder_desh, 8));
			list.add(new ItemStack(ModItems.upgrade_power_3, 1));
			list.add(new ItemStack(ModItems.upgrade_speed_3, 1));
			break;
		case GADGET:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.fins_flat, 2));
			list.add(new ItemStack(ModItems.pedestal_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			list.add(new ItemStack(Items.DYE, 6, 8));
			break;
		case LITTLE_BOY:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			list.add(new ItemStack(ModItems.wire_aluminium, 6));
			list.add(new ItemStack(Items.DYE, 4, 4));
			break;
		case FAT_MAN:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.fins_big_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 2));
			list.add(new ItemStack(ModItems.wire_copper, 6));
			list.add(new ItemStack(Items.DYE, 6, 11));
			break;
		case IVY_MIKE:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_aluminium, 4));
			list.add(new ItemStack(ModItems.cap_aluminium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(ModItems.wire_gold, 18));
			list.add(new ItemStack(Items.DYE, 12, 7));
			break;
		case TSAR_BOMB:
			list.add(new ItemStack(ModItems.sphere_steel, 1));
			list.add(new ItemStack(ModItems.hull_big_titanium, 6));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_tri_steel, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 5));
			list.add(new ItemStack(ModItems.wire_gold, 24));
			list.add(new ItemStack(ModItems.wire_tungsten, 12));
			list.add(new ItemStack(Items.DYE, 6, 0));
			break;
		case PROTOTYPE:
			list.add(new ItemStack(ModItems.dysfunctional_reactor, 1));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.ingot_euphemium, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			list.add(new ItemStack(ModItems.wire_gold, 16));
			break;
		case FLEIJA:
			list.add(new ItemStack(ModItems.hull_small_aluminium, 1));
			list.add(new ItemStack(ModItems.fins_quad_titanium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModItems.wire_gold, 8));
			list.add(new ItemStack(Items.DYE, 4, 15));
			break;
		case SOLINIUM:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_quad_titanium, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(ModItems.wire_gold, 10));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(Items.DYE, 4, 8));
			break;
		case N2:
			list.add(new ItemStack(ModItems.hull_big_steel, 3));
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.wire_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.pipes_steel, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 3));
			list.add(new ItemStack(Items.DYE, 12, 0));
			break;
		case CUSTOM_NUKE:
			list.add(new ItemStack(ModItems.hull_small_steel, 2));
			list.add(new ItemStack(ModItems.fins_small_steel, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			list.add(new ItemStack(Items.DYE, 4, 8));
			break;
		case BOMB_LEV:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.levitation_unit, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_ENDO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_endo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case BOMB_EXO:
			list.add(new ItemStack(ModItems.plate_titanium, 12));
			list.add(new ItemStack(ModItems.thermo_unit_exo, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			list.add(new ItemStack(ModItems.wire_gold, 6));
			break;
		case LAUNCH_PAD:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.ingot_polymer, 2));
			list.add(new ItemStack(ModItems.plate_steel, 12));
			list.add(new ItemStack(ModBlocks.machine_battery, 1));
			list.add(new ItemStack(ModItems.circuit_gold, 2));
			break;
		case TURRET_LIGHT:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 2));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 2));
			break;
		case TURRET_HEAVY:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_aluminium, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.hull_small_steel, 1));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 3));
			break;
		case TURRET_ROCKET:
			list.add(new ItemStack(ModItems.ingot_steel, 12));
			list.add(new ItemStack(ModItems.ingot_titanium, 4));
			list.add(new ItemStack(ModItems.hull_small_steel, 8));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 2));
			break;
		case TURRET_FLAMER:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.ingot_tungsten, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 1));
			list.add(new ItemStack(ModItems.tank_steel, 2));
			list.add(new ItemStack(ModItems.ingot_red_copper, 4));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 2));
			break;
		case TURRET_TAU:
			list.add(new ItemStack(ModItems.ingot_steel, 16));
			list.add(new ItemStack(ModItems.ingot_titanium, 8));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 4));
			list.add(new ItemStack(ModItems.redcoil_capacitor, 3));
			list.add(new ItemStack(ModItems.ingot_red_copper, 12));
			list.add(new ItemStack(ModItems.motor, 2));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			break;
		case TURRET_SPITFIRE:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 6));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.hull_small_steel, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 2));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case TURRET_CIWS:
			list.add(new ItemStack(ModItems.ingot_steel, 6));
			list.add(new ItemStack(ModItems.ingot_red_copper, 8));
			list.add(new ItemStack(ModItems.plate_steel, 10));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.hull_small_aluminium, 2));
			list.add(new ItemStack(ModItems.pipes_steel, 6));
			list.add(new ItemStack(ModItems.motor, 4));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 2));
			list.add(new ItemStack(ModItems.magnetron, 3));
			break;
		case TURRET_CHEAPO:
			list.add(new ItemStack(ModItems.ingot_steel, 4));
			list.add(new ItemStack(ModItems.plate_iron, 4));
			list.add(new ItemStack(ModItems.pipes_steel, 3));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 4));
			break;
		case HUNTER_CHOPPER:
			list.add(new ItemStack(ModItems.chopper_blades, 5));
			list.add(new ItemStack(ModItems.chopper_gun, 1));
			list.add(new ItemStack(ModItems.chopper_head, 1));
			list.add(new ItemStack(ModItems.chopper_tail, 1));
			list.add(new ItemStack(ModItems.chopper_torso, 1));
			list.add(new ItemStack(ModItems.chopper_wing, 2));
			break;
		case MISSILE_HE_1:
			list.add(new ItemStack(ModItems.warhead_generic_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_FIRE_1:
			list.add(new ItemStack(ModItems.warhead_incendiary_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_CLUSTER_1:
			list.add(new ItemStack(ModItems.warhead_cluster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_BUSTER_1:
			list.add(new ItemStack(ModItems.warhead_buster_small, 1));
			list.add(new ItemStack(ModItems.fuel_tank_small, 1));
			list.add(new ItemStack(ModItems.thruster_small, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.circuit_targeting_tier1, 1));
			break;
		case MISSILE_HE_2:
			list.add(new ItemStack(ModItems.warhead_generic_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_FIRE_2:
			list.add(new ItemStack(ModItems.warhead_incendiary_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_CLUSTER_2:
			list.add(new ItemStack(ModItems.warhead_cluster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_BUSTER_2:
			list.add(new ItemStack(ModItems.warhead_buster_medium, 1));
			list.add(new ItemStack(ModItems.fuel_tank_medium, 1));
			list.add(new ItemStack(ModItems.thruster_medium, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 10));
			list.add(new ItemStack(ModItems.plate_steel, 14));
			list.add(new ItemStack(ModItems.circuit_targeting_tier2, 1));
			break;
		case MISSILE_HE_3:
			list.add(new ItemStack(ModItems.warhead_generic_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_FIRE_3:
			list.add(new ItemStack(ModItems.warhead_incendiary_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_CLUSTER_3:
			list.add(new ItemStack(ModItems.warhead_cluster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_BUSTER_3:
			list.add(new ItemStack(ModItems.warhead_buster_large, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier3, 1));
			break;
		case MISSILE_NUCLEAR:
			list.add(new ItemStack(ModItems.warhead_nuclear, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case MISSILE_MIRV:
			list.add(new ItemStack(ModItems.warhead_mirv, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 20));
			list.add(new ItemStack(ModItems.plate_steel, 24));
			list.add(new ItemStack(ModItems.plate_aluminium, 16));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			break;
		case MISSILE_ENDO:
			list.add(new ItemStack(ModItems.warhead_thermo_endo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case MISSILE_EXO:
			list.add(new ItemStack(ModItems.warhead_thermo_exo, 1));
			list.add(new ItemStack(ModItems.fuel_tank_large, 1));
			list.add(new ItemStack(ModItems.thruster_large, 1));
			list.add(new ItemStack(ModItems.plate_titanium, 14));
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_aluminium, 12));
			list.add(new ItemStack(ModItems.circuit_targeting_tier4, 1));
			break;
		case DEFAB:
			list.add(new ItemStack(ModItems.ingot_steel, 2));
			list.add(new ItemStack(ModItems.ingot_polymer, 8));
			list.add(new ItemStack(ModItems.plate_iron, 5));
			list.add(new ItemStack(ModItems.mechanism_special, 3));
			list.add(new ItemStack(Items.DIAMOND, 1));
			list.add(new ItemStack(ModItems.plate_dalekanium, 3));
			break;
		case MINI_NUKE:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 3));
			break;
		case MINI_MIRV:
			list.add(new ItemStack(ModItems.plate_steel, 20));
			list.add(new ItemStack(ModItems.plate_iron, 10));
			list.add(new ItemStack(ModItems.nugget_pu239, 24));
			break;
		case DARK_PLUG:
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(Items.REDSTONE, 1));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 1));
			break;
		case COMBINE_BALL:
			list.add(new ItemStack(ModItems.plate_combine_steel, 4));
			list.add(new ItemStack(Items.REDSTONE, 7));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_FLAME:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.powder_fire, 1));
			list.add(new ItemStack(ModItems.plate_copper, 2));
			break;
		case GRENADE_SHRAPNEL:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_buckshot, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GRENAGE_CLUSTER:
			list.add(new ItemStack(ModItems.grenade_frag, 1));
			list.add(new ItemStack(ModItems.pellet_cluster, 1));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			break;
		case GREANADE_FLARE:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(Items.GLOWSTONE_DUST, 1));
			list.add(new ItemStack(ModItems.plate_aluminium, 2));
			break;
		case GRENADE_LIGHTNING:
			list.add(new ItemStack(ModItems.grenade_generic, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.plate_gold, 2));
			break;
		case GRENADE_IMPULSE:
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.plate_iron, 3));
			list.add(new ItemStack(ModItems.wire_red_copper, 6));
			list.add(new ItemStack(Items.DIAMOND, 1));
			break;
		case GRENADE_PLASMA:
			list.add(new ItemStack(ModItems.plate_steel, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(ItemCell.getFullCell(ModForgeFluids.deuterium));
			list.add(ItemCell.getFullCell(ModForgeFluids.tritium));
			break;
		case GRENADE_TAU:
			list.add(new ItemStack(ModItems.plate_lead, 3));
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 1));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 1));
			list.add(new ItemStack(ModItems.gun_xvl1456_ammo, 1));
			break;
		case GRENADE_SCHRABIDIUM:
			list.add(new ItemStack(ModItems.grenade_flare, 1));
			list.add(new ItemStack(ModItems.powder_schrabidium, 1));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			break;
		case GRENADE_NUKE:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.nugget_pu239, 2));
			list.add(new ItemStack(ModItems.wire_red_copper, 2));
			break;
		case GRENADE_ZOMG:
			list.add(new ItemStack(ModItems.plate_paa, 3));
			list.add(new ItemStack(ModItems.neutron_reflector, 1));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 3));
			list.add(new ItemStack(ModItems.powder_power, 3));
			break;
		case GRENADE_BLACK_HOLE:
			list.add(new ItemStack(ModItems.ingot_polymer, 6));
			list.add(new ItemStack(ModItems.neutron_reflector, 3));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 2));
			list.add(new ItemStack(ModItems.black_hole, 1));
			break;
		case POWER_FIST:
			list.add(new ItemStack(ModItems.rod_reiium, 1));
			list.add(new ItemStack(ModItems.rod_weidanium, 1));
			list.add(new ItemStack(ModItems.rod_australium, 1));
			list.add(new ItemStack(ModItems.rod_verticium, 1));
			list.add(new ItemStack(ModItems.rod_unobtainium, 1));
			list.add(new ItemStack(ModItems.rod_daffergon, 1));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 1));
			list.add(new ItemStack(ModItems.ducttape, 1));
			break;
		case GADGET_PROPELLANT:
			list.add(new ItemStack(Blocks.TNT, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_gold, 3));
			break;
		case GADGET_WIRING:
			list.add(new ItemStack(ModItems.plate_iron, 1));
			list.add(new ItemStack(ModItems.wire_gold, 12));
			break;
		case GADGET_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 7));
			list.add(new ItemStack(ModItems.nugget_u238, 3));
			break;
		case BOY_SHIELDING:
			list.add(new ItemStack(ModItems.neutron_reflector, 12));
			list.add(new ItemStack(ModItems.plate_steel, 4));
			break;
		case BOY_TARGET:
			list.add(new ItemStack(ModItems.nugget_u235, 7));
			break;
		case BOY_BULLET:
			list.add(new ItemStack(ModItems.nugget_u235, 3));
			break;
		case BOY_PRPELLANT:
			list.add(new ItemStack(Blocks.TNT, 3));
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.plate_aluminium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 4));
			break;
		case BOY_IGNITER:
			list.add(new ItemStack(ModItems.plate_aluminium, 6));
			list.add(new ItemStack(ModItems.plate_steel, 1));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_PROPELLANT:
			list.add(new ItemStack(Blocks.TNT, 3));
			list.add(new ItemStack(ModItems.plate_steel, 2));
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.wire_red_copper, 3));
			break;
		case MAN_IGNITER:
			list.add(new ItemStack(ModItems.plate_steel, 6));
			list.add(new ItemStack(ModItems.circuit_red_copper, 1));
			list.add(new ItemStack(ModItems.wire_red_copper, 9));
			break;
		case MAN_CORE:
			list.add(new ItemStack(ModItems.nugget_pu239, 8));
			list.add(new ItemStack(ModItems.nugget_beryllium, 2));
			break;
		case MIKE_TANK:
			list.add(new ItemStack(ModItems.nugget_u238, 24));
			list.add(new ItemStack(ModItems.ingot_lead, 6));
			break;
		case MIKE_DEUT:
			list.add(new ItemStack(ModItems.plate_iron, 12));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(ItemCell.getFullCell(ModForgeFluids.deuterium, 10));
			break;
		case MIKE_COOLER:
			list.add(new ItemStack(ModItems.plate_iron, 8));
			list.add(new ItemStack(ModItems.coil_copper, 5));
			list.add(new ItemStack(ModItems.coil_tungsten, 5));
			list.add(new ItemStack(ModItems.motor, 2));
			break;
		case FLEIJA_IGNITER:
			list.add(new ItemStack(ModItems.plate_titanium, 6));
			list.add(new ItemStack(ModItems.wire_schrabidium, 2));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			break;
		case FLEIJA_CORE:
			list.add(new ItemStack(ModItems.nugget_u235, 8));
			list.add(new ItemStack(ModItems.nugget_neptunium, 2));
			list.add(new ItemStack(ModItems.nugget_beryllium, 4));
			list.add(new ItemStack(ModItems.coil_copper, 2));
			break;
		case FLEIJA_PROPELLANT:
			list.add(new ItemStack(Blocks.TNT, 3));
			list.add(new ItemStack(ModItems.plate_schrabidium, 8));
			break;
		case SOLINIUM_IGNITER:
			list.add(new ItemStack(ModItems.plate_titanium, 4));
			list.add(new ItemStack(ModItems.wire_advanced_alloy, 2));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 1));
			list.add(new ItemStack(ModItems.coil_gold, 1));
			break;
		case SOLINIUM_CORE:
			list.add(new ItemStack(ModItems.nugget_solinium, 9));
			list.add(new ItemStack(ModItems.nugget_euphemium, 1));
			break;
		case SOLINIUM_PROPELLANT:
			list.add(new ItemStack(Blocks.TNT, 3));
			list.add(new ItemStack(ModItems.neutron_reflector, 2));
			list.add(new ItemStack(ModItems.plate_polymer, 6));
			list.add(new ItemStack(ModItems.wire_tungsten, 6));
			list.add(new ItemStack(ModItems.biomass_compressed, 4));
			break;
		case COMPONENT_LIMITER:
			list.add(new ItemStack(ModItems.hull_big_steel, 2));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.plate_titanium, 18));
			list.add(new ItemStack(ModItems.plate_desh, 12));
			list.add(new ItemStack(ModItems.pipes_steel, 4));
			list.add(new ItemStack(ModItems.circuit_gold, 8));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 4));
			list.add(new ItemStack(ModItems.ingot_starmetal, 14));
			list.add(new ItemStack(ModItems.plate_dalekanium, 5));
			list.add(new ItemStack(ModItems.powder_magic, 16));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 3));
			break;
		case COMPONENT_EMITTER:
			list.add(new ItemStack(ModItems.hull_big_steel, 3));
			list.add(new ItemStack(ModItems.hull_big_titanium, 2));
			list.add(new ItemStack(ModItems.plate_steel, 32));
			list.add(new ItemStack(ModItems.plate_lead, 24));
			list.add(new ItemStack(ModItems.plate_desh, 24));
			list.add(new ItemStack(ModItems.pipes_steel, 8));
			list.add(new ItemStack(ModItems.circuit_gold, 12));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 8));
			list.add(new ItemStack(ModItems.ingot_starmetal, 26));
			list.add(new ItemStack(ModItems.powder_magic, 48));
			list.add(new ItemStack(ModBlocks.fwatz_computer, 2));
			list.add(new ItemStack(ModItems.crystal_xen, 1));
			break;
		case AMS_LIMITER:
			list.add(new ItemStack(ModItems.component_limiter, 5));
			list.add(new ItemStack(ModItems.plate_steel, 64));
			list.add(new ItemStack(ModItems.plate_titanium, 128));
			list.add(new ItemStack(ModItems.plate_dineutronium, 16));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 6));
			list.add(new ItemStack(ModItems.pipes_steel, 16));
			list.add(new ItemStack(ModItems.motor, 12));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 12));
			list.add(new ItemStack(ModItems.entanglement_kit, 1));
			break;
		case AMS_EMITTER:
			list.add(new ItemStack(ModItems.component_emitter, 16));
			list.add(new ItemStack(ModItems.plate_steel, 128));
			list.add(new ItemStack(ModItems.plate_titanium, 192));
			list.add(new ItemStack(ModItems.plate_dineutronium, 32));
			list.add(new ItemStack(ModItems.circuit_schrabidium, 12));
			list.add(new ItemStack(ModItems.coil_advanced_torus, 24));
			list.add(new ItemStack(ModItems.entanglement_kit, 3));
			list.add(new ItemStack(ModItems.crystal_horn, 1));
			list.add(new ItemStack(ModBlocks.fwatz_core, 1));
			break;
		case RADAR:
			list.add(new ItemStack(ModItems.ingot_steel, 8));
			list.add(new ItemStack(ModItems.plate_steel, 16));
			list.add(new ItemStack(ModItems.ingot_polymer, 4));
			list.add(new ItemStack(ModItems.plate_polymer, 24));
			list.add(new ItemStack(ModItems.magnetron, 10));
			list.add(new ItemStack(ModItems.motor, 3));
			list.add(new ItemStack(ModItems.circuit_gold, 4));
			list.add(new ItemStack(ModItems.coil_copper, 12));
			break;
		case FORCEFIELD:
			list.add(new ItemStack(ModItems.plate_advanced_alloy, 8));
			list.add(new ItemStack(ModItems.plate_desh, 4));
			list.add(new ItemStack(ModItems.coil_gold_torus, 6));
			list.add(new ItemStack(ModItems.coil_magnetized_tungsten, 12));
			list.add(new ItemStack(ModItems.motor, 1));
			list.add(new ItemStack(ModItems.upgrade_radius, 1));
			list.add(new ItemStack(ModItems.upgrade_health, 1));
			list.add(new ItemStack(ModItems.circuit_targeting_tier5, 1));
			list.add(new ItemStack(ModBlocks.machine_transformer, 1));
			break;
		default:
			list.add(new ItemStack(Items.STICK));
			break;
		}
		
		if(list.isEmpty())
			return null;
		else
			return list;
	}
	
	public static ItemStack getOutputFromTempate(EnumAssemblyTemplate template) {
		
		if(template.output != null)
			return template.output;
		
		ItemStack output = null;
		
		switch(template) {
        case IRON_PLATE:
			output = new ItemStack(ModItems.plate_iron, 2);
			break;
		case GOLD_PLATE:
			output = new ItemStack(ModItems.plate_gold, 2);
			break;
		case TITANIUM_PLATE:
			output = new ItemStack(ModItems.plate_titanium, 2);
			break;
		case ALUMINIUM_PLATE:
			output = new ItemStack(ModItems.plate_aluminium, 2);
			break;
		case STEEL_PLATE:
			output = new ItemStack(ModItems.plate_steel, 2);
			break;
		case LEAD_PLATE:
			output = new ItemStack(ModItems.plate_lead, 2);
			break;
		case COPPER_PLATE:
			output = new ItemStack(ModItems.plate_copper, 2);
			break;
		case ADVANCED_PLATE:
			output = new ItemStack(ModItems.plate_advanced_alloy, 2);
			break;
		case SCHRABIDIUM_PLATE:
			output = new ItemStack(ModItems.plate_schrabidium, 2);
			break;
		case CMB_PLATE:
			output = new ItemStack(ModItems.plate_combine_steel, 2);
			break;
		case SATURN_PLATE:
			output = new ItemStack(ModItems.plate_saturnite, 2);
			break;
		case ALUMINIUM_WIRE:
			output = new ItemStack(ModItems.wire_aluminium, 6);
			break;
		case COPPER_WIRE:
			output = new ItemStack(ModItems.wire_copper, 6);
			break;
		case TUNGSTEN_WIRE:
			output = new ItemStack(ModItems.wire_tungsten, 6);
			break;
		case REDCOPPER_WIRE:
			output = new ItemStack(ModItems.wire_red_copper, 6);
			break;
		case ADVANCED_WIRE:
			output = new ItemStack(ModItems.wire_advanced_alloy, 6);
			break;
		case GOLD_WIRE:
			output = new ItemStack(ModItems.wire_gold, 6);
			break;
		case SCHRABIDIUM_WIRE:
			output = new ItemStack(ModItems.wire_schrabidium, 6);
			break;
		case MAGNETIZED_WIRE:
			output = new ItemStack(ModItems.wire_magnetized_tungsten, 6);
			break;
		case CIRCUIT_1:
			output = new ItemStack(ModItems.circuit_aluminium, 1);
			break;
		case SCHRABIDIUM_HAMMER:
			output = new ItemStack(ModItems.schrabidium_hammer, 1);
			break;
		case MIXED_PLATE:
			output = new ItemStack(ModItems.plate_mixed, 6);
			break;
		case HAZMAT_CLOTH:
			output = new ItemStack(ModItems.hazmat_cloth, 4);
			break;
		case ASBESTOS_CLOTH:
			output = new ItemStack(ModItems.asbestos_cloth, 4);
			break;
		case COAL_FILTER:
			output = new ItemStack(ModItems.filter_coal, 1);
			break;
		case CENTRIFUGE_ELEMENT:
			output = new ItemStack(ModItems.centrifuge_element, 1);
			break;
		case CENTRIFUGE_TOWER:
			output = new ItemStack(ModItems.centrifuge_tower, 1);
			break;
		case DEE_MAGNET:
			output = new ItemStack(ModItems.magnet_dee, 1);
			break;
		case FLAT_MAGNET:
			output = new ItemStack(ModItems.magnet_circular, 1);
			break;
		case CYCLOTRON_TOWER:
			output = new ItemStack(ModItems.cyclotron_tower, 1);
			break;
		case REACTOR_CORE:
			output = new ItemStack(ModItems.reactor_core, 1);
			break;
		case RTG_UNIT:
			output = new ItemStack(ModItems.rtg_unit, 2);
			break;
		case HEAT_UNIT:
			output = new ItemStack(ModItems.thermo_unit_empty, 1);
			break;
		case GRAVITY_UNIT:
			output = new ItemStack(ModItems.levitation_unit, 1);
			break;
		case TITANIUM_DRILL:
			output = new ItemStack(ModItems.drill_titanium, 1);
			break;
		case TELEPAD:
			output = new ItemStack(ModItems.telepad, 1);
			break;
		case TELEKIT:
			output = new ItemStack(ModItems.entanglement_kit, 1);
			break;
		case GEASS_REACTOR:
			output = new ItemStack(ModItems.dysfunctional_reactor, 1);
			break;
		case WT1_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_small, 1);
			break;
		case WT2_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_medium, 1);
			break;
		case WT3_GENERIC:
			output = new ItemStack(ModItems.warhead_generic_large, 1);
			break;
		case WT1_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_small, 1);
			break;
		case WT2_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_medium, 1);
			break;
		case WT3_FIRE:
			output = new ItemStack(ModItems.warhead_incendiary_large, 1);
			break;
		case MISSILE_ASSEMBLY:
			output = new ItemStack(ModItems.missile_assembly, 1);
			break;
		case CARRIER:
			output = new ItemStack(ModItems.missile_carrier, 1);
			break;
		case WT1_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_small, 1);
			break;
		case WT2_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_medium, 1);
			break;
		case WT3_CLUSTER:
			output = new ItemStack(ModItems.warhead_cluster_large, 1);
			break;
		case WT1_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_small, 1);
			break;
		case WT2_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_medium, 1);
			break;
		case WT3_BUSTER:
			output = new ItemStack(ModItems.warhead_buster_large, 1);
			break;
		case W_NUCLEAR:
			output = new ItemStack(ModItems.warhead_nuclear, 1);
			break;
		case W_MIRVLET:
			output = new ItemStack(ModItems.warhead_mirvlet, 1);
			break;
		case W_MIRV:
			output = new ItemStack(ModItems.warhead_mirv, 1);
			break;
		case W_ENDOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_endo, 1);
			break;
		case W_EXOTHERMIC:
			output = new ItemStack(ModItems.warhead_thermo_exo, 1);
			break;
		case T1_TANK:
			output = new ItemStack(ModItems.fuel_tank_small, 1);
			break;
		case T2_TANK:
			output = new ItemStack(ModItems.fuel_tank_medium, 1);
			break;
		case T3_TANK:
			output = new ItemStack(ModItems.fuel_tank_large, 1);
			break;
		case T1_THRUSTER:
			output = new ItemStack(ModItems.thruster_small, 1);
			break;
		case T2_THRUSTER:
			output = new ItemStack(ModItems.thruster_medium, 1);
			break;
		case T3_THRUSTER:
			output = new ItemStack(ModItems.thruster_large, 1);
			break;
		case NUCLEAR_THRUSTER:
			output = new ItemStack(ModItems.thruster_nuclear, 1);
			break;
		case SAT_BASE:
			output = new ItemStack(ModItems.sat_base, 1);
			break;
		case SAT_MAPPER:
			output = new ItemStack(ModItems.sat_head_mapper, 1);
			break;
		case SAT_SCANNER:
			output = new ItemStack(ModItems.sat_head_scanner, 1);
			break;
		case SAT_RADAR:
			output = new ItemStack(ModItems.sat_head_radar, 1);
			break;
		case SAT_LASER:
			output = new ItemStack(ModItems.sat_head_laser, 1);
			break;
		case SAT_RESONATOR:
			output = new ItemStack(ModItems.sat_head_resonator, 1);
			break;
		case SAT_FOEQ:
			output = new ItemStack(ModItems.sat_foeq, 1);
			break;
		case SAT_MINER:
			output = new ItemStack(ModItems.sat_miner, 1);
			break;
		case CHOPPER_HEAD:
			output = new ItemStack(ModItems.chopper_head, 1);
			break;
		case CHOPPER_GUN:
			output = new ItemStack(ModItems.chopper_gun, 1);
			break;
		case CHOPPER_BODY:
			output = new ItemStack(ModItems.chopper_torso, 1);
			break;
		case CHOPPER_TAIL:
			output = new ItemStack(ModItems.chopper_tail, 1);
			break;
		case CHOPPER_WING:
			output = new ItemStack(ModItems.chopper_wing, 1);
			break;
		case CHOPPER_BLADES:
			output = new ItemStack(ModItems.chopper_blades, 1);
			break;
		case CIRCUIT_2:
			output = new ItemStack(ModItems.circuit_copper, 1);
			break;
		case CIRCUIT_3:
			output = new ItemStack(ModItems.circuit_red_copper, 1);
			break;
		case RTG_PELLET:
			output = new ItemStack(ModItems.pellet_rtg, 1);
			break;
		case WEAK_PELLET:
			output = new ItemStack(ModItems.pellet_rtg_weak, 1);
			break;
		case FUSION_PELLET:
			output = new ItemStack(ModItems.tritium_deuterium_cake, 1);
			break;
		case CLUSTER_PELLETS:
			output = new ItemStack(ModItems.pellet_cluster, 1);
			break;
		case GUN_PELLETS:
			output = new ItemStack(ModItems.pellet_buckshot, 1);
			break;
		case AUSTRALIUM_MACHINE:
			output = new ItemStack(ModItems.australium_iii, 1);
			break;
		case MAGNETRON:
			output = new ItemStack(ModItems.magnetron, 1);
			break;
		case W_SP:
			output = new ItemStack(ModItems.pellet_schrabidium, 1);
			break;
		case W_SHE:
			output = new ItemStack(ModItems.pellet_hes, 1);
			break;
		case W_SME:
			output = new ItemStack(ModItems.pellet_mes, 1);
			break;
		case W_SLE:
			output = new ItemStack(ModItems.pellet_les, 1);
			break;
		case W_B:
			output = new ItemStack(ModItems.pellet_beryllium, 1);
			break;
		case W_N:
			output = new ItemStack(ModItems.pellet_neptunium, 1);
			break;
		case W_L:
			output = new ItemStack(ModItems.pellet_lead, 1);
			break;
		case W_A:
			output = new ItemStack(ModItems.pellet_advanced, 1);
			break;
		case UPGRADE_TEMPLATE:
			output = new ItemStack(ModItems.upgrade_template, 1);
			break;
		case UPGRADE_RED_I:
			output = new ItemStack(ModItems.upgrade_speed_1, 1);
			break;
		case UPGRADE_RED_II:
			output = new ItemStack(ModItems.upgrade_speed_2, 1);
			break;
		case UPGRADE_RED_III:
			output = new ItemStack(ModItems.upgrade_speed_3, 1);
			break;
		case UPGRADE_GREEN_I:
			output = new ItemStack(ModItems.upgrade_effect_1, 1);
			break;
		case UPGRADE_GREEN_II:
			output = new ItemStack(ModItems.upgrade_effect_2, 1);
			break;
		case UPGRADE_GREEN_III:
			output = new ItemStack(ModItems.upgrade_effect_3, 1);
			break;
		case UPGRADE_BLUE_I:
			output = new ItemStack(ModItems.upgrade_power_1, 1);
			break;
		case UPGRADE_BLUE_II:
			output = new ItemStack(ModItems.upgrade_power_2, 1);
			break;
		case UPGRADE_BLUE_III:
			output = new ItemStack(ModItems.upgrade_power_3, 1);
			break;
		case UPGRADE_PURPLE_I:
			output = new ItemStack(ModItems.upgrade_fortune_1, 1);
			break;
		case UPGRADE_PURPLE_II:
			output = new ItemStack(ModItems.upgrade_fortune_2, 1);
			break;
		case UPGRADE_PURPLE_III:
			output = new ItemStack(ModItems.upgrade_fortune_3, 1);
			break;
		case UPGRADE_PINK_I:
			output = new ItemStack(ModItems.upgrade_afterburn_1, 1);
			break;
		case UPGRADE_PINK_II:
			output = new ItemStack(ModItems.upgrade_afterburn_2, 1);
			break;
		case UPGRADE_PINK_III:
			output = new ItemStack(ModItems.upgrade_afterburn_3, 1);
			break;
		case UPGRADE_RANGE:
			output = new ItemStack(ModItems.upgrade_radius, 1);
			break;
		case UPGRADE_HEALTH:
			output = new ItemStack(ModItems.upgrade_health, 1);
			break;
		case FUSE:
			output = new ItemStack(ModItems.fuse, 1);
			break;
		case REDCOIL_CAPACITOR:
			output = new ItemStack(ModItems.redcoil_capacitor, 1);
			break;
		case TITANIUM_FILTER:
			output = new ItemStack(ModItems.titanium_filter, 1);
			break;
		case LITHIUM_BOX:
			output = new ItemStack(ModItems.part_lithium, 1);
			break;
		case BERYLLIUM_BOX:
			output = new ItemStack(ModItems.part_beryllium, 1);
			break;
		case COAL_BOX:
			output = new ItemStack(ModItems.part_carbon, 1);
			break;
		case COPPER_BOX:
			output = new ItemStack(ModItems.part_copper, 1);
			break;
		case PLUTONIUM_BOX:
			output = new ItemStack(ModItems.part_plutonium, 1);
			break;
		case THERMO_ELEMENT:
			output = new ItemStack(ModItems.thermo_element, 1);
			break;
		case ANGRY_METAL:
			output = new ItemStack(ModItems.plate_dalekanium, 1);
			break;
		case METEOR_BLOCK:
			output = new ItemStack(ModBlocks.block_meteor, 1);
			break;
		case CMB_TILE:
			output = new ItemStack(ModBlocks.cmb_brick, 8);
			break;
		case CMB_BRICKS:
			output = new ItemStack(ModBlocks.cmb_brick_reinforced, 8);
			break;
		case HATCH_FRAME:
			output = new ItemStack(ModBlocks.seal_frame, 1);
			break;
		case HATCH_CONTROLLER:
			output = new ItemStack(ModBlocks.seal_controller, 1);
			break;
		case BLAST_DOOR:
			output = new ItemStack(ModBlocks.vault_door, 1);
			break;
		case SLIDING_DOOR:
			output = new ItemStack(ModBlocks.blast_door, 1);
			break;
		case CENTRIFUGE:
			output = new ItemStack(ModBlocks.machine_centrifuge, 1);
			break;
		case CENTRIFUGE_GAS:
			output = new ItemStack(ModBlocks.machine_gascent, 1);
			break;
		case BREEDING_REACTOR:
			output = new ItemStack(ModBlocks.machine_reactor, 1);
			break;
		case RTG_FURNACE:
			output = new ItemStack(ModBlocks.machine_rtg_furnace_off, 1);
			break;
		case RAD_GEN:
			output = new ItemStack(ModBlocks.machine_radgen, 1);
			break;
		case DIESEL_GENERATOR:
			output = new ItemStack(ModBlocks.machine_diesel, 1);
			break;
		case SELENIUM_GENERATOR:
			output = new ItemStack(ModBlocks.machine_selenium, 1);
			break;
		case NUCLEAR_GENERATOR:
			output = new ItemStack(ModBlocks.machine_reactor_small, 1);
			break;
		case CYCLOTRON:
			output = new ItemStack(ModBlocks.machine_cyclotron, 1);
			break;
		case RT_GENERATOR:
			output = new ItemStack(ModBlocks.machine_rtg_grey, 1);
			break;
		case BATTERY:
			output = new ItemStack(ModBlocks.machine_battery, 1);
			break;
		case BATTERY_L:
			output = new ItemStack(ModBlocks.machine_lithium_battery, 1);
			break;
		case BATTERY_S:
			output = new ItemStack(ModBlocks.machine_schrabidium_battery, 1);
			break;
		case BATTERY_D:
			output = new ItemStack(ModBlocks.machine_dineutronium_battery, 1);
			break;
		/*case HE_TO_RF:
			output = new ItemStack(ModBlocks.machine_converter_he_rf, 1);
			break;
		case RF_TO_HE:
			output = new ItemStack(ModBlocks.machine_converter_rf_he, 1);
			break;*/
		case SHREDDER:
			output = new ItemStack(ModBlocks.machine_shredder, 1);
			break;
		//case DEUTERIUM_EXTRACTOR:
		//	output = new ItemStack(ModBlocks.machine_deuterium, 1);
		//	break;
		case DERRICK:
			output = new ItemStack(ModBlocks.machine_well, 1);
			break;
		case PUMPJACK:
			output = new ItemStack(ModBlocks.machine_pumpjack, 1);
			break;
		case FLARE_STACK:
			output = new ItemStack(ModBlocks.machine_flare, 1);
			break;
		case REFINERY:
			output = new ItemStack(ModBlocks.machine_refinery, 1);
			break;
		case EPRESS:
			output = new ItemStack(ModBlocks.machine_epress, 1);
			break;
		case CHEMPLANT:
			output = new ItemStack(ModBlocks.machine_chemplant, 1);
			break;
		case TANK:
			output = new ItemStack(ModBlocks.machine_fluidtank, 1);
			break;
		case MINER:
			output = new ItemStack(ModBlocks.machine_drill, 1);
			break;
		case TURBOFAN:
			output = new ItemStack(ModBlocks.machine_turbofan, 1);
			break;
		case TELEPORTER:
			output = new ItemStack(ModBlocks.machine_teleporter, 1);
			break;
		case SCHRABTRANS:
			output = new ItemStack(ModBlocks.machine_schrabidium_transmutator, 1);
			break;
		case CMB_FURNACE:
			output = new ItemStack(ModBlocks.machine_combine_factory, 1);
			break;
		case FA_HULL:
			output = new ItemStack(ModBlocks.factory_advanced_hull, 1);
			break;
		case FA_HATCH:
			output = new ItemStack(ModBlocks.factory_advanced_furnace, 1);
			break;
		case FA_CORE:
			output = new ItemStack(ModBlocks.factory_advanced_core, 1);
			break;
		case FA_PORT:
			output = new ItemStack(ModBlocks.factory_advanced_conductor, 1);
			break;
		case LR_ELEMENT:
			output = new ItemStack(ModBlocks.reactor_element, 1);
			break;
		case LR_CONTROL:
			output = new ItemStack(ModBlocks.reactor_control, 1);
			break;
		case LR_HATCH:
			output = new ItemStack(ModBlocks.reactor_hatch, 1);
			break;
		case LR_PORT:
			output = new ItemStack(ModBlocks.reactor_conductor, 1);
			break;
		case LR_CORE:
			output = new ItemStack(ModBlocks.reactor_computer, 1);
			break;
		case LF_MAGNET:
			output = new ItemStack(ModBlocks.fusion_conductor, 1);
			break;
		case LF_CENTER:
			output = new ItemStack(ModBlocks.fusion_center, 1);
			break;
		case LF_MOTOR:
			output = new ItemStack(ModBlocks.fusion_motor, 1);
			break;
		case LF_HEATER:
			output = new ItemStack(ModBlocks.fusion_heater, 1);
			break;
		case LF_HATCH:
			output = new ItemStack(ModBlocks.fusion_hatch, 1);
			break;
		case LF_CORE:
			output = new ItemStack(ModBlocks.fusion_core, 1);
			break;
		case LW_ELEMENT:
			output = new ItemStack(ModBlocks.watz_element, 1);
			break;
		case LW_CONTROL:
			output = new ItemStack(ModBlocks.watz_control, 1);
			break;
		case LW_COOLER:
			output = new ItemStack(ModBlocks.watz_cooler, 1);
			break;
		case LW_STRUTURE:
			output = new ItemStack(ModBlocks.watz_end, 1);
			break;
		case LW_HATCH:
			output = new ItemStack(ModBlocks.watz_hatch, 1);
			break;
		case LW_PORT:
			output = new ItemStack(ModBlocks.watz_conductor, 1);
			break;
		case LW_CORE:
			output = new ItemStack(ModBlocks.watz_core, 1);
			break;
		case FW_PORT:
			output = new ItemStack(ModBlocks.fwatz_hatch, 1);
			break;
		case FW_MAGNET:
			output = new ItemStack(ModBlocks.fwatz_conductor, 1);
			break;
		case FW_COMPUTER:
			output = new ItemStack(ModBlocks.fwatz_computer, 1);
			break;
		case FW_CORE:
			output = new ItemStack(ModBlocks.fwatz_core, 1);
			break;
		case GADGET:
			output = new ItemStack(ModBlocks.nuke_gadget, 1);
			break;
		case LITTLE_BOY:
			output = new ItemStack(ModBlocks.nuke_boy, 1);
			break;
		case FAT_MAN:
			output = new ItemStack(ModBlocks.nuke_man, 1);
			break;
		case IVY_MIKE:
			output = new ItemStack(ModBlocks.nuke_mike, 1);
			break;
		case TSAR_BOMB:
			output = new ItemStack(ModBlocks.nuke_tsar, 1);
			break;
		case PROTOTYPE:
			output = new ItemStack(ModBlocks.nuke_prototype, 1);
			break;
		case FLEIJA:
			output = new ItemStack(ModBlocks.nuke_fleija, 1);
			break;
		case SOLINIUM:
			output = new ItemStack(ModBlocks.nuke_solinium, 1);
			break;
		case N2:
			output = new ItemStack(ModBlocks.nuke_n2, 1);
			break;
		case CUSTOM_NUKE:
			output = new ItemStack(ModBlocks.nuke_custom, 1);
			break;
		case BOMB_LEV:
			output = new ItemStack(ModBlocks.float_bomb, 1);
			break;
		case BOMB_ENDO:
			output = new ItemStack(ModBlocks.therm_endo, 1);
			break;
		case BOMB_EXO:
			output = new ItemStack(ModBlocks.therm_exo, 1);
			break;
		case LAUNCH_PAD:
			output = new ItemStack(ModBlocks.launch_pad, 1);
			break;
		case TURRET_LIGHT:
			output = new ItemStack(ModBlocks.turret_light, 1);
			break;
		case TURRET_HEAVY:
			output = new ItemStack(ModBlocks.turret_heavy, 1);
			break;
		case TURRET_ROCKET:
			output = new ItemStack(ModBlocks.turret_rocket, 1);
			break;
		case TURRET_FLAMER:
			output = new ItemStack(ModBlocks.turret_flamer, 1);
			break;
		case TURRET_TAU:
			output = new ItemStack(ModBlocks.turret_tau, 1);
			break;
		case TURRET_SPITFIRE:
			output = new ItemStack(ModBlocks.turret_spitfire, 1);
			break;
		case TURRET_CIWS:
			output = new ItemStack(ModBlocks.turret_cwis, 1);
			break;
		case TURRET_CHEAPO:
			output = new ItemStack(ModBlocks.turret_cheapo, 1);
			break;
		case HUNTER_CHOPPER:
			output = new ItemStack(ModItems.chopper, 1);
			break;
		case MISSILE_HE_1:
			output = new ItemStack(ModItems.missile_generic, 1);
			break;
		case MISSILE_FIRE_1:
			output = new ItemStack(ModItems.missile_incendiary, 1);
			break;
		case MISSILE_CLUSTER_1:
			output = new ItemStack(ModItems.missile_cluster, 1);
			break;
		case MISSILE_BUSTER_1:
			output = new ItemStack(ModItems.missile_buster, 1);
			break;
		case MISSILE_HE_2:
			output = new ItemStack(ModItems.missile_strong, 1);
			break;
		case MISSILE_FIRE_2:
			output = new ItemStack(ModItems.missile_incendiary_strong, 1);
			break;
		case MISSILE_CLUSTER_2:
			output = new ItemStack(ModItems.missile_cluster_strong, 1);
			break;
		case MISSILE_BUSTER_2:
			output = new ItemStack(ModItems.missile_buster_strong, 1);
			break;
		case MISSILE_HE_3:
			output = new ItemStack(ModItems.missile_burst, 1);
			break;
		case MISSILE_FIRE_3:
			output = new ItemStack(ModItems.missile_inferno, 1);
			break;
		case MISSILE_CLUSTER_3:
			output = new ItemStack(ModItems.missile_rain, 1);
			break;
		case MISSILE_BUSTER_3:
			output = new ItemStack(ModItems.missile_drill, 1);
			break;
		case MISSILE_NUCLEAR:
			output = new ItemStack(ModItems.missile_nuclear, 1);
			break;
		case MISSILE_MIRV:
			output = new ItemStack(ModItems.missile_nuclear_cluster, 1);
			break;
		case MISSILE_ENDO:
			output = new ItemStack(ModItems.missile_endo, 1);
			break;
		case MISSILE_EXO:
			output = new ItemStack(ModItems.missile_exo, 1);
			break;
		case DEFAB:
			output = new ItemStack(ModItems.gun_defabricator, 1);
			break;
		case MINI_NUKE:
			output = new ItemStack(ModItems.gun_fatman_ammo, 1);
			break;
		case MINI_MIRV:
			output = new ItemStack(ModItems.gun_mirv_ammo, 1);
			break;
		case DARK_PLUG:
			output = new ItemStack(ModItems.gun_osipr_ammo, 24);
			break;
		case COMBINE_BALL:
			output = new ItemStack(ModItems.gun_osipr_ammo2, 1);
			break;
		case GRENADE_FLAME:
			output = new ItemStack(ModItems.grenade_fire, 1);
			break;
		case GRENADE_SHRAPNEL:
			output = new ItemStack(ModItems.grenade_shrapnel, 1);
			break;
		case GRENAGE_CLUSTER:
			output = new ItemStack(ModItems.grenade_cluster, 1);
			break;
		case GREANADE_FLARE:
			output = new ItemStack(ModItems.grenade_flare, 1);
			break;
		case GRENADE_LIGHTNING:
			output = new ItemStack(ModItems.grenade_electric, 1);
			break;
		case GRENADE_IMPULSE:
			output = new ItemStack(ModItems.grenade_pulse, 4);
			break;
		case GRENADE_PLASMA:
			output = new ItemStack(ModItems.grenade_plasma, 2);
			break;
		case GRENADE_TAU:
			output = new ItemStack(ModItems.grenade_tau, 2);
			break;
		case GRENADE_SCHRABIDIUM:
			output = new ItemStack(ModItems.grenade_schrabidium, 1);
			break;
		case GRENADE_NUKE:
			output = new ItemStack(ModItems.grenade_nuclear, 1);
			break;
		case GRENADE_ZOMG:
			output = new ItemStack(ModItems.grenade_zomg, 1);
			break;
		case GRENADE_BLACK_HOLE:
			output = new ItemStack(ModItems.grenade_black_hole, 1);
			break;
		case POWER_FIST:
			ItemStack multitool = new ItemStack(ModItems.multitool_dig, 1);
			multitool.addEnchantment(Enchantments.LOOTING, 3);
			multitool.addEnchantment(Enchantments.FORTUNE, 3);
			output = multitool.copy();
			break;
		case GADGET_PROPELLANT:
			output = new ItemStack(ModItems.gadget_explosive, 1);
			break;
		case GADGET_WIRING:
			output = new ItemStack(ModItems.gadget_wireing, 1);
			break;
		case GADGET_CORE:
			output = new ItemStack(ModItems.gadget_core, 1);
			break;
		case BOY_SHIELDING:
			output = new ItemStack(ModItems.boy_shielding, 1);
			break;
		case BOY_TARGET:
			output = new ItemStack(ModItems.boy_target, 1);
			break;
		case BOY_BULLET:
			output = new ItemStack(ModItems.boy_bullet, 1);
			break;
		case BOY_PRPELLANT:
			output = new ItemStack(ModItems.boy_propellant, 1);
			break;
		case BOY_IGNITER:
			output = new ItemStack(ModItems.boy_igniter, 1);
			break;
		case MAN_PROPELLANT:
			output = new ItemStack(ModItems.man_explosive, 1);
			break;
		case MAN_IGNITER:
			output = new ItemStack(ModItems.man_igniter, 1);
			break;
		case MAN_CORE:
			output = new ItemStack(ModItems.man_core, 1);
			break;
		case MIKE_TANK:
			output = new ItemStack(ModItems.mike_core, 1);
			break;
		case MIKE_DEUT:
			output = new ItemStack(ModItems.mike_deut, 1);
			break;
		case MIKE_COOLER:
			output = new ItemStack(ModItems.mike_cooling_unit, 1);
			break;
		case FLEIJA_IGNITER:
			output = new ItemStack(ModItems.fleija_igniter, 1);
			break;
		case FLEIJA_CORE:
			output = new ItemStack(ModItems.fleija_core, 1);
			break;
		case FLEIJA_PROPELLANT:
			output = new ItemStack(ModItems.fleija_propellant, 1);
			break;
		case SOLINIUM_IGNITER:
			output = new ItemStack(ModItems.solinium_igniter, 1);
			break;
		case SOLINIUM_CORE:
			output = new ItemStack(ModItems.solinium_core, 1);
			break;
		case SOLINIUM_PROPELLANT:
			output = new ItemStack(ModItems.solinium_propellant, 1);
			break;
		case COMPONENT_LIMITER:
			output = new ItemStack(ModItems.component_limiter, 1);
			break;
		case COMPONENT_EMITTER:
			output = new ItemStack(ModItems.component_emitter, 1);
			break;
		case AMS_LIMITER:
			output = new ItemStack(ModBlocks.ams_limiter, 1);
			break;
		case AMS_EMITTER:
			output = new ItemStack(ModBlocks.ams_emitter, 1);
			break;
		case RADAR:
			output = new ItemStack(ModBlocks.machine_radar, 1);
			break;
		case FORCEFIELD:
			output = new ItemStack(ModBlocks.machine_forcefield, 1);
			break;
		default:
			output = new ItemStack(Items.STICK, 1);
			break;
		}
		
		return output;
	}
	
	public static int getProcessTime(EnumAssemblyTemplate enum1) {
    	
        
        if(enum1.time != 0)
        	return enum1.time;
        
        switch (enum1) {
        case IRON_PLATE:
			return 30;
		case GOLD_PLATE:
			return 30;
		case TITANIUM_PLATE:
			return 30;
		case ALUMINIUM_PLATE:
			return 30;
		case STEEL_PLATE:
			return 30;
		case LEAD_PLATE:
			return 30;
		case COPPER_PLATE:
			return 30;
		case ADVANCED_PLATE:
			return 30;
		case SCHRABIDIUM_PLATE:
			return 30;
		case CMB_PLATE:
			return 30;
		case SATURN_PLATE:
			return 30;
		case MIXED_PLATE:
			return 50;
		case ALUMINIUM_WIRE:
			return 20;
		case COPPER_WIRE:
			return 20;
		case TUNGSTEN_WIRE:
			return 20;
		case REDCOPPER_WIRE:
			return 20;
		case ADVANCED_WIRE:
			return 20;
		case GOLD_WIRE:
			return 20;
		case SCHRABIDIUM_WIRE:
			return 20;
		case MAGNETIZED_WIRE:
			return 20;
		case HAZMAT_CLOTH:
			return 50;
		case ASBESTOS_CLOTH:
			return 50;
		case COAL_FILTER:
			return 50;
		case CENTRIFUGE_ELEMENT:
			return 200;
		case CENTRIFUGE_TOWER:
			return 150;
		case DEE_MAGNET:
			return 100;
		case FLAT_MAGNET:
			return 150;
		case CYCLOTRON_TOWER:
			return 300;
		case REACTOR_CORE:
			return 100;
		case RTG_UNIT:
			return 100;
		case HEAT_UNIT:
			return 100;
		case GRAVITY_UNIT:
			return 100;
		case TITANIUM_DRILL:
			return 100;
		case TELEPAD:
			return 300;
		case TELEKIT:
			return 200;
		case GEASS_REACTOR:
			return 200;
		case MISSILE_ASSEMBLY:
			return 200;
		case CARRIER:
			return 4800;
		case WT1_GENERIC:
			return 100;
		case WT2_GENERIC:
			return 150;
		case WT3_GENERIC:
			return 200;
		case WT1_FIRE:
			return 100;
		case WT2_FIRE:
			return 150;
		case WT3_FIRE:
			return 200;
		case WT1_CLUSTER:
			return 100;
		case WT2_CLUSTER:
			return 150;
		case WT3_CLUSTER:
			return 200;
		case WT1_BUSTER:
			return 100;
		case WT2_BUSTER:
			return 150;
		case WT3_BUSTER:
			return 200;
		case W_NUCLEAR:
			return 300;
		case W_MIRVLET:
			return 250;
		case W_MIRV:
			return 500;
		case W_ENDOTHERMIC:
			return 300;
		case W_EXOTHERMIC:
			return 300;
		case T1_TANK:
			return 100;
		case T2_TANK:
			return 150;
		case T3_TANK:
			return 200;
		case T1_THRUSTER:
			return 100;
		case T2_THRUSTER:
			return 150;
		case T3_THRUSTER:
			return 200;
		case NUCLEAR_THRUSTER:
			return 600;
		case SAT_BASE:
			return 500;
		case SAT_MAPPER:
			return 400;
		case SAT_SCANNER:
			return 400;
		case SAT_RADAR:
			return 400;
		case SAT_LASER:
			return 450;
		case SAT_RESONATOR:
			return 1000;
		case SAT_FOEQ:
			return 1200;
		case SAT_MINER:
			return 600;
		case CHOPPER_HEAD:
			return 300;
		case CHOPPER_GUN:
			return 150;
		case CHOPPER_BODY:
			return 350;
		case CHOPPER_TAIL:
			return 200;
		case CHOPPER_WING:
			return 150;
		case CHOPPER_BLADES:
			return 200;
		case CIRCUIT_1:
			return 50;
		case CIRCUIT_2:
			return 100;
		case CIRCUIT_3:
			return 150;
		case RTG_PELLET:
			return 50;
		case WEAK_PELLET:
			return 50;
		case FUSION_PELLET:
			return 150;
		case CLUSTER_PELLETS:
			return 50;
		case GUN_PELLETS:
			return 50;
		case AUSTRALIUM_MACHINE:
			return 150;
		case MAGNETRON:
			return 100;
		case W_SP:
			return 200;
		case W_SHE:
			return 200;
		case W_SME:
			return 200;
		case W_SLE:
			return 200;
		case W_B:
			return 200;
		case W_N:
			return 200;
		case W_L:
			return 200;
		case W_A:
			return 200;
		case UPGRADE_TEMPLATE:
			return 100;
		case UPGRADE_RED_I:
			return 200;
		case UPGRADE_RED_II:
			return 300;
		case UPGRADE_RED_III:
			return 500;
		case UPGRADE_GREEN_I:
			return 200;
		case UPGRADE_GREEN_II:
			return 300;
		case UPGRADE_GREEN_III:
			return 500;
		case UPGRADE_BLUE_I:
			return 200;
		case UPGRADE_BLUE_II:
			return 300;
		case UPGRADE_BLUE_III:
			return 500;
		case UPGRADE_PURPLE_I:
			return 200;
		case UPGRADE_PURPLE_II:
			return 300;
		case UPGRADE_PURPLE_III:
			return 500;
		case UPGRADE_PINK_I:
			return 200;
		case UPGRADE_PINK_II:
			return 300;
		case UPGRADE_PINK_III:
			return 500;
		case UPGRADE_RANGE:
			return 500;
		case UPGRADE_HEALTH:
			return 500;
		case FUSE:
			return 100;
		case REDCOIL_CAPACITOR:
			return 200;
		case TITANIUM_FILTER:
			return 200;
		case LITHIUM_BOX:
			return 50;
		case BERYLLIUM_BOX:
			return 50;
		case COAL_BOX:
			return 50;
		case COPPER_BOX:
			return 50;
		case PLUTONIUM_BOX:
			return 50;
		case THERMO_ELEMENT:
			return 150;
		case ANGRY_METAL:
			return 50;
		case METEOR_BLOCK:
			return 500;
		case CMB_TILE:
			return 100;
		case CMB_BRICKS:
			return 200;
		case HATCH_FRAME:
			return 50;
		case HATCH_CONTROLLER:
			return 100;
		case BLAST_DOOR:
			return 300;
		case SLIDING_DOOR:
			return 200;
		case CENTRIFUGE:
			return 250;
		case CENTRIFUGE_GAS:
			return 300;
		case BREEDING_REACTOR:
			return 150;
		case RTG_FURNACE:
			return 150;
		case RAD_GEN:
			return 400;
		case DIESEL_GENERATOR:
			return 200;
		case SELENIUM_GENERATOR:
			return 250;
		case NUCLEAR_GENERATOR:
			return 300;
		case CYCLOTRON:
			return 600;
		case RT_GENERATOR:
			return 200;
		case BATTERY:
			return 200;
		case BATTERY_L:
			return 400;
		case BATTERY_S:
			return 800;
		case BATTERY_D:
			return 1600;
		//case HE_TO_RF:
		//	return 150;
		//case RF_TO_HE:
		//	return 150;
		case SHREDDER:
			return 200;
		case DERRICK:
			return 250;
		case PUMPJACK:
			return 400;
		case FLARE_STACK:
			return 200;
		case REFINERY:
			return 350;
		case EPRESS:
			return 160;
		case CHEMPLANT:
			return 200;
		case TANK:
			return 150;
		case MINER:
			return 200;
		case TURBOFAN:
			return 500;
		case TELEPORTER:
			return 300;
		case SCHRABTRANS:
			return 500;
		case CMB_FURNACE:
			return 150;
		case FA_HULL:
			return 50;
		case FA_HATCH:
			return 100;
		case FA_CORE:
			return 100;
		case FA_PORT:
			return 50;
		/*case LR_ELEMENT:
			return 150;
		case LR_CONTROL:
			return 100;
		case LR_HATCH:
			return 150;
		case LR_PORT:
			return 150;
		case LR_CORE:
			return 250;*/
		case LF_MAGNET:
			return 150;
		case LF_CENTER:
			return 200;
		case LF_MOTOR:
			return 250;
		case LF_HEATER:
			return 150;
		case LF_HATCH:
			return 250;
		case LF_CORE:
			return 350;
		case LW_ELEMENT:
			return 200;
		case LW_CONTROL:
			return 250;
		case LW_COOLER:
			return 300;
		case LW_STRUTURE:
			return 150;
		case LW_HATCH:
			return 200;
		case LW_PORT:
			return 250;
		case LW_CORE:
			return 350;
		case FW_PORT:
			return 250;
		case FW_MAGNET:
			return 250;
		case FW_COMPUTER:
			return 300;
		case FW_CORE:
			return 450;
		case GADGET:
			return 300;
		case LITTLE_BOY:
			return 300;
		case FAT_MAN:
			return 300;
		case IVY_MIKE:
			return 300;
		case TSAR_BOMB:
			return 600;
		case PROTOTYPE:
			return 500;
		case FLEIJA:
			return 400;
		case SOLINIUM:
			return 400;
		case N2:
			return 300;
		case CUSTOM_NUKE:
			return 300;
		case BOMB_LEV:
			return 250;
		case BOMB_ENDO:
			return 250;
		case BOMB_EXO:
			return 250;
		case LAUNCH_PAD:
			return 250;
		case TURRET_LIGHT:
			return 200;
		case TURRET_HEAVY:
			return 250;
		case TURRET_ROCKET:
			return 300;
		case TURRET_FLAMER:
			return 250;
		case TURRET_TAU:
			return 350;
		case TURRET_SPITFIRE:
			return 350;
		case TURRET_CIWS:
			return 400;
		case TURRET_CHEAPO:
			return 200;
		case HUNTER_CHOPPER:
			return 300;
		case MISSILE_HE_1:
			return 200;
		case MISSILE_FIRE_1:
			return 200;
		case MISSILE_CLUSTER_1:
			return 200;
		case MISSILE_BUSTER_1:
			return 200;
		case MISSILE_HE_2:
			return 250;
		case MISSILE_FIRE_2:
			return 250;
		case MISSILE_CLUSTER_2:
			return 250;
		case MISSILE_BUSTER_2:
			return 250;
		case MISSILE_HE_3:
			return 350;
		case MISSILE_FIRE_3:
			return 350;
		case MISSILE_CLUSTER_3:
			return 350;
		case MISSILE_BUSTER_3:
			return 350;
		case MISSILE_NUCLEAR:
			return 500;
		case MISSILE_MIRV:
			return 600;
		case MISSILE_ENDO:
			return 350;
		case MISSILE_EXO:
			return 350;
		case DEFAB:
			return 200;
		case MINI_NUKE:
			return 40;
		case MINI_MIRV:
			return 100;
		case DARK_PLUG:
			return 50;
		case COMBINE_BALL:
			return 200;
		case GRENADE_FLAME:
			return 150;
		case GRENADE_SHRAPNEL:
			return 150;
		case GRENAGE_CLUSTER:
			return 200;
		case GREANADE_FLARE:
			return 100;
		case GRENADE_LIGHTNING:
			return 200;
		case GRENADE_IMPULSE:
			return 300;
		case GRENADE_PLASMA:
			return 300;
		case GRENADE_TAU:
			return 300;
		case GRENADE_SCHRABIDIUM:
			return 300;
		case GRENADE_NUKE:
			return 200;
		case GRENADE_ZOMG:
			return 300;
		case GRENADE_BLACK_HOLE:
			return 500;
		case POWER_FIST:
			return 600;
		case GADGET_PROPELLANT:
			return 200;
		case GADGET_WIRING:
			return 100;
		case GADGET_CORE:
			return 200;
		case BOY_SHIELDING:
			return 150;
		case BOY_TARGET:
			return 200;
		case BOY_BULLET:
			return 100;
		case BOY_PRPELLANT:
			return 100;
		case BOY_IGNITER:
			return 150;
		case MAN_PROPELLANT:
			return 200;
		case MAN_IGNITER:
			return 150;
		case MAN_CORE:
			return 250;
		case MIKE_TANK:
			return 300;
		case MIKE_DEUT:
			return 200;
		case MIKE_COOLER:
			return 200;
		case FLEIJA_IGNITER:
			return 300;
		case FLEIJA_CORE:
			return 500;
		case FLEIJA_PROPELLANT:
			return 400;
		case SOLINIUM_IGNITER:
			return 400;
		case SOLINIUM_CORE:
			return 400;
		case SOLINIUM_PROPELLANT:
			return 350;
		case SCHRABIDIUM_HAMMER:
			return 1000;
		case COMPONENT_LIMITER:
			return 2500;
		case COMPONENT_EMITTER:
			return 2500;
		case AMS_LIMITER:
			return 6000;
		case AMS_EMITTER:
			return 6000;
		case RADAR:
			return 300;
		case FORCEFIELD:
			return 1000;
        default:
        	return 100;
        }
    }
	
}
