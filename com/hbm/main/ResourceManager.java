package com.hbm.main;

import java.io.FileNotFoundException;

import org.apache.logging.log4j.Level;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.obj.OBJLoader;

public class ResourceManager {

	//God
	public static final IModelCustom error = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/error.obj"));
	
	
	//Drillgon200 model loading test
	//Hey it worked! I wonder if I can edit the tessellator to call 1.12.2 builder buffer commands, because that's a lot less laggy.
	public static final IModelCustom press_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_body.obj"));
	public static final IModelCustom press_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/press_head.obj"));
	public static final IModelCustom epress_body = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_body.obj"));
	public static final IModelCustom epress_head = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/epress_head.obj"));
	
	//Satellites
	public static final IModelCustom sat_base = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_base.obj"));
	public static final IModelCustom sat_radar = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_radar.obj"));
	public static final IModelCustom sat_resonator = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_resonator.obj"));
	public static final IModelCustom sat_scanner = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_scanner.obj"));
	public static final IModelCustom sat_mapper = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_mapper.obj"));
	public static final IModelCustom sat_laser = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_laser.obj"));
	public static final IModelCustom sat_foeq = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq.obj"));
	public static final IModelCustom sat_foeq_burning = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq_burning.obj"));
	public static final IModelCustom sat_foeq_fire = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sat_foeq_fire.obj"));
	
	//Sphere
		public static final IModelCustom sphere_ruv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_ruv.obj"));
		public static final IModelCustom sphere_iuv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_iuv.obj"));
		public static final IModelCustom sphere_uv = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.obj"));
		public static final IModelCustom sphere_uv_anim = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID, "models/sphere_uv.hmf"));
	////Textures TEs
	
	public static final ResourceLocation universal = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");

	public static final ResourceLocation turret_heavy_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_base.png");
	
	public static final ResourceLocation turret_heavy_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_rotor.png");
	public static final ResourceLocation turret_heavy_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_heavy_gun.png");
	public static final ResourceLocation turret_light_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_light_rotor.png");
	public static final ResourceLocation turret_light_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_light_gun.png");
	public static final ResourceLocation turret_rocket_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_rocket_rotor.png");
	public static final ResourceLocation turret_rocket_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_rocket_gun.png");
	public static final ResourceLocation turret_flamer_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_flamer_rotor.png");
	public static final ResourceLocation turret_flamer_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_flamer_gun.png");
	public static final ResourceLocation turret_tau_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_tau_rotor.png");
	public static final ResourceLocation turret_tau_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_tau_gun.png");
	public static final ResourceLocation turret_ciws_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_base.png");
	public static final ResourceLocation turret_ciws_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_rotor.png");
	public static final ResourceLocation turret_ciws_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_head.png");
	public static final ResourceLocation turret_ciws_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/cwis_gun.png");
	public static final ResourceLocation turret_cheapo_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_base.png");
	public static final ResourceLocation turret_cheapo_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_rotor.png");
	public static final ResourceLocation turret_cheapo_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_head.png");
	public static final ResourceLocation turret_cheapo_gun_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turret_cheapo_gun.png");

	//Landmines
	public static final ResourceLocation mine_ap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_ap.png");
	public static final ResourceLocation mine_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_he.png");
	public static final ResourceLocation mine_shrap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_shrap.png");
	public static final ResourceLocation mine_fat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/mine_fat.png");
	
	//Pumpjack
	public static final ResourceLocation pumpjack_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_base.png");
	public static final ResourceLocation pumpjack_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_head.png");
	public static final ResourceLocation pumpjack_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/pumpjack_rotor.png");
	
	//Pumpjack
	public static final ResourceLocation turbofan_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_body.png");
	public static final ResourceLocation turbofan_blades_tex = new ResourceLocation(RefStrings.MODID, "textures/models/turbofan_blades.png");
	
	//Selenium Engine
	public static final ResourceLocation selenium_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_body.png");
	public static final ResourceLocation selenium_piston_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_piston.png");
	public static final ResourceLocation selenium_rotor_tex = new ResourceLocation(RefStrings.MODID, "textures/models/selenium_engine_rotor.png");
	
	//Press
	public static final ResourceLocation press_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_body.png");
	public static final ResourceLocation press_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/press_head.png");
	public static final ResourceLocation epress_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_body.png");
	public static final ResourceLocation epress_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/epress_head.png");
	
	//Chemplant
	public static final ResourceLocation chemplant_new_tex = new ResourceLocation(RefStrings.MODID, "textures/models/chemplant_main_new.png");
	
	//Centrifuge
	public static final ResourceLocation centrifuge_new_tex = new ResourceLocation(RefStrings.MODID, "textures/models/centrifuge_new.png");
	public static final ResourceLocation centrifuge_gas_tex = new ResourceLocation(RefStrings.MODID, "textures/models/centrifuge_gas.png");
	
	//Anti Mass Spectrometer
	public static final ResourceLocation ams_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_base.png");
	public static final ResourceLocation ams_emitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_emitter.png");
	public static final ResourceLocation ams_limiter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_limiter.png");
	public static final ResourceLocation ams_destroyed_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ams_destroyed.png");
	
	//Dark Matter Core
	public static final ResourceLocation dfc_emitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_emitter.png");
	public static final ResourceLocation dfc_receiver_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_receiver.png");
	public static final ResourceLocation dfc_injector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/core_injector.png");
	
	//Radgen
	public static final ResourceLocation radgen_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rad_gen_body.png");
	
	//Small Reactor
	public static final ResourceLocation reactor_small_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_base.png");
	public static final ResourceLocation reactor_small_rods_tex = new ResourceLocation(RefStrings.MODID, "textures/models/reactor_small_rods.png");
	
	//Radar
	public static final ResourceLocation radar_body_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_base.png");
	public static final ResourceLocation radar_head_tex = new ResourceLocation(RefStrings.MODID, "textures/models/radar_head.png");
	
	//Forcefield
	public static final ResourceLocation forcefield_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_base.png");
	public static final ResourceLocation forcefield_top_tex = new ResourceLocation(RefStrings.MODID, "textures/models/forcefield_top.png");
	
	//Bombs
	public static final ResourceLocation bomb_solinium_tex = new ResourceLocation(RefStrings.MODID, "textures/models/ufp.png");
	public static final ResourceLocation n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n2.png");
	public static final ResourceLocation n45_globe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_globe.png");
	public static final ResourceLocation n45_knob_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_knob.png");
	public static final ResourceLocation n45_rod_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_rod.png");
	public static final ResourceLocation n45_stand_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_stand.png");
	public static final ResourceLocation n45_chain_tex = new ResourceLocation(RefStrings.MODID, "textures/models/n45_chain.png");
	
	//Satellites
	public static final ResourceLocation sat_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_base.png");
	public static final ResourceLocation sat_radar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_radar.png");
	public static final ResourceLocation sat_resonator_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_resonator.png");
	public static final ResourceLocation sat_scanner_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_scanner.png");
	public static final ResourceLocation sat_mapper_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_mapper.png");
	public static final ResourceLocation sat_laser_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_laser.png");
	public static final ResourceLocation sat_foeq_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_foeq.png");
	public static final ResourceLocation sat_foeq_burning_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_foeq_burning.png");
	
	//SatDock
	public static final ResourceLocation satdock_tex = new ResourceLocation(RefStrings.MODID, "textures/models/sat_dock.png");
	
	//Vault Door
	public static final ResourceLocation vault_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_cog.png");
	public static final ResourceLocation vault_frame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_frame.png");
	public static final ResourceLocation vault_label_101_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_101.png");
	public static final ResourceLocation vault_label_87_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_87.png");
	public static final ResourceLocation vault_label_106_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault_label_106.png");
	public static final ResourceLocation stable_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_cog.png");
	public static final ResourceLocation stable_label_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_label.png");
	public static final ResourceLocation stable_label_99_tex = new ResourceLocation(RefStrings.MODID, "textures/models/stable_label_99.png");
	public static final ResourceLocation vault4_cog_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_cog.png");
	public static final ResourceLocation vault4_label_111_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_111.png");
	public static final ResourceLocation vault4_label_81_tex = new ResourceLocation(RefStrings.MODID, "textures/models/vault4_label_81.png");
	
	//Blast Door
	public static final ResourceLocation blast_door_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_base.png");
	public static final ResourceLocation blast_door_tooth_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_tooth.png");
	public static final ResourceLocation blast_door_slider_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_slider.png");
	public static final ResourceLocation blast_door_block_tex = new ResourceLocation(RefStrings.MODID, "textures/models/blast_door_block.png");

	
	
	////Obj Items
	
	//Shimmer Sledge
	
	////Texture Items

	//Shimmer Sledge
	public static final ResourceLocation shimmer_sledge_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_sledge.png");
	public static final ResourceLocation shimmer_axe_tex = new ResourceLocation(RefStrings.MODID, "textures/models/shimmer_axe.png");

	public static final ResourceLocation brimstone_tex = new ResourceLocation(RefStrings.MODID, "textures/models/brimstone.png");
	public static final ResourceLocation hk69_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/hk69.png");
	
	
	
	////Texture Entities
	
	//Blast
	public static final ResourceLocation fireball = new ResourceLocation(RefStrings.MODID, "textures/models/fireball.png");
	public static final ResourceLocation balefire = new ResourceLocation(RefStrings.MODID, "textures/models/balefire.png");
	
	//Boxcar
	public static final ResourceLocation boxcar_tex = new ResourceLocation(RefStrings.MODID, "textures/models/boxcar.png");
	public static final ResourceLocation duchessgambit_tex = new ResourceLocation(RefStrings.MODID, "textures/models/duchessgambit.png");
	public static final ResourceLocation rpc_tex = new ResourceLocation(RefStrings.MODID, "textures/models/rpc.png");
	public static final ResourceLocation tom_main_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_main.png");
	public static final ResourceLocation tom_flame_tex = new ResourceLocation(RefStrings.MODID, "textures/models/weapons/tom_flame.png");
	
	//Bomber
	public static final ResourceLocation dornier_0_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_0.png");
	public static final ResourceLocation dornier_1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_1.png");
	public static final ResourceLocation dornier_2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_2.png");
	public static final ResourceLocation dornier_3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_3.png");
	public static final ResourceLocation dornier_4_tex = new ResourceLocation(RefStrings.MODID, "textures/models/dornier_4.png");
	public static final ResourceLocation b29_0_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_0.png");
	public static final ResourceLocation b29_1_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_1.png");
	public static final ResourceLocation b29_2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_2.png");
	public static final ResourceLocation b29_3_tex = new ResourceLocation(RefStrings.MODID, "textures/models/b29_3.png");
	
	//Missiles
	public static final ResourceLocation missileV2_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_HE.png");
	public static final ResourceLocation missileV2_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_IN.png");
	public static final ResourceLocation missileV2_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_CL.png");
	public static final ResourceLocation missileV2_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileV2_BU.png");
	public static final ResourceLocation missileAA_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileAA.png");
	public static final ResourceLocation missileStrong_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_HE.png");
	public static final ResourceLocation missileStrong_EMP_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_EMP.png");
	public static final ResourceLocation missileStrong_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_IN.png");
	public static final ResourceLocation missileStrong_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_CL.png");
	public static final ResourceLocation missileStrong_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileStrong_BU.png");
	public static final ResourceLocation missileHuge_HE_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_HE.png");
	public static final ResourceLocation missileHuge_IN_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_IN.png");
	public static final ResourceLocation missileHuge_CL_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_CL.png");
	public static final ResourceLocation missileHuge_BU_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileHuge_BU.png");
	public static final ResourceLocation missileNuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNeon.png");
	public static final ResourceLocation missileMIRV_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileNeonH.png");
	public static final ResourceLocation missileEndo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileEndo.png");
	public static final ResourceLocation missileExo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileExo.png");
	public static final ResourceLocation missileDoomsday_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileDoomsday.png");
	public static final ResourceLocation missileTaint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileTaint.png");
	public static final ResourceLocation missileMicro_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicro.png");
	public static final ResourceLocation missileCarrier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileCarrier.png");
	public static final ResourceLocation missileBooster_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileBooster.png");
	public static final ResourceLocation minerRocket_tex = new ResourceLocation(RefStrings.MODID, "textures/models/minerRocket.png");
	public static final ResourceLocation bobmazon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/bobmazon.png");
	public static final ResourceLocation missileMicroBHole_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroBHole.png");
	public static final ResourceLocation missileMicroSchrab_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroSchrab.png");
	public static final ResourceLocation missileMicroEMP_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missileMicroEMP.png");
	
	public static final ResourceLocation soyuz_engineblock = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/engineblock.png");
	public static final ResourceLocation soyuz_bottomstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/bottomstage.png");
	public static final ResourceLocation soyuz_topstage = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/topstage.png");
	public static final ResourceLocation soyuz_payload = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/payload.png");
	public static final ResourceLocation soyuz_payloadblocks = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/payloadblocks.png");
	public static final ResourceLocation soyuz_les = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/les.png");
	public static final ResourceLocation soyuz_lesthrusters = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/lesthrusters.png");
	public static final ResourceLocation soyuz_mainengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/mainengines.png");
	public static final ResourceLocation soyuz_sideengines = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/sideengines.png");
	public static final ResourceLocation soyuz_booster = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/booster.png");
	public static final ResourceLocation soyuz_boosterside = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/boosterside.png");
	public static final ResourceLocation soyuz_memento = new ResourceLocation(RefStrings.MODID, "textures/items/polaroid_memento.png");

	public static final ResourceLocation soyuz_launcher_legs_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_leg.png");
	public static final ResourceLocation soyuz_launcher_table_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_table.png");
	public static final ResourceLocation soyuz_launcher_tower_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_tower_base.png");
	public static final ResourceLocation soyuz_launcher_tower_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_tower.png");
	public static final ResourceLocation soyuz_launcher_support_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_support_base.png");
	public static final ResourceLocation soyuz_launcher_support_tex = new ResourceLocation(RefStrings.MODID, "textures/models/soyuz/launcher_support.png");
	
	//Missile Parts
	public static final ResourceLocation missile_assembly_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_assembly.png");
	public static final ResourceLocation strut_tex = new ResourceLocation(RefStrings.MODID, "textures/models/strut.png");
	public static final ResourceLocation compact_launcher_tex = new ResourceLocation(RefStrings.MODID, "textures/models/compact_launcher.png");
	public static final ResourceLocation launch_table_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table.png");
	public static final ResourceLocation launch_table_large_pad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_pad.png");
	public static final ResourceLocation launch_table_small_pad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_pad.png");
	public static final ResourceLocation launch_table_large_scaffold_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_scaffold_base.png");
	public static final ResourceLocation launch_table_large_scaffold_connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_large_scaffold_connector.png");
	public static final ResourceLocation launch_table_small_scaffold_base_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_scaffold_base.png");
	public static final ResourceLocation launch_table_small_scaffold_connector_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/launch_table_small_scaffold_connector.png");
	
	public static final ResourceLocation mp_t_10_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_kerosene.png");
	public static final ResourceLocation mp_t_10_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_solid.png");
	public static final ResourceLocation mp_t_10_xenon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_10_xenon.png");
	public static final ResourceLocation mp_t_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_kerosene.png");
	public static final ResourceLocation mp_t_15_kerosene_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_kerosene_dual.png");
	public static final ResourceLocation mp_t_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_solid.png");
	public static final ResourceLocation mp_t_15_solid_hexdecuple_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_solid_hexdecuple.png");
	public static final ResourceLocation mp_t_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_hydrogen.png");
	public static final ResourceLocation mp_t_15_hydrogen_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_hydrogen_dual.png");
	public static final ResourceLocation mp_t_15_balefire_short_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_short.png");
	public static final ResourceLocation mp_t_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire.png");
	public static final ResourceLocation mp_t_15_balefire_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_large.png");
	public static final ResourceLocation mp_t_15_balefire_large_rad_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_15_balefire_large_rad.png");
	
	public static final ResourceLocation mp_t_20_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_kerosene.png");
	public static final ResourceLocation mp_t_20_kerosene_dual_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_kerosene_dual.png");
	public static final ResourceLocation mp_t_20_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid.png");
	public static final ResourceLocation mp_t_20_solid_multi_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid_multi.png");
	public static final ResourceLocation mp_t_20_solid_multier_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/thrusters/mp_t_20_solid_multier.png");

	public static final ResourceLocation mp_s_10_flat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_flat.png");
	public static final ResourceLocation mp_s_10_cruise_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_cruise.png");
	public static final ResourceLocation mp_s_10_space_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_10_space.png");
	public static final ResourceLocation mp_s_15_flat_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_flat.png");
	public static final ResourceLocation mp_s_15_thin_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_thin.png");
	public static final ResourceLocation mp_s_15_soyuz_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/stability/mp_s_15_soyuz.png");

	public static final ResourceLocation mp_f_10_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene.png");
	public static final ResourceLocation mp_f_10_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_camo.png");
	public static final ResourceLocation mp_f_10_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_desert.png");
	public static final ResourceLocation mp_f_10_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_sky.png");
	public static final ResourceLocation mp_f_10_kerosene_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_flames.png");
	public static final ResourceLocation mp_f_10_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_insulation.png");
	public static final ResourceLocation mp_f_10_kerosene_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_sleek.png");
	public static final ResourceLocation mp_f_10_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_kerosene_metal.png");
	public static final ResourceLocation mp_f_10_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_kerosene_taint.png");
	
	public static final ResourceLocation mp_f_10_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid.png");
	public static final ResourceLocation mp_f_10_solid_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_flames.png");
	public static final ResourceLocation mp_f_10_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_insulation.png");
	public static final ResourceLocation mp_f_10_solid_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_sleek.png");
	public static final ResourceLocation mp_f_10_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_10_solid_moonlit_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_moonlit.png");
	public static final ResourceLocation mp_f_10_solid_cathedral_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_cathedral.png");
	public static final ResourceLocation mp_f_10_solid_battery_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_solid_battery.png");
	public static final ResourceLocation mp_f_10_solid_duracell_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_solid_duracell.png");

	public static final ResourceLocation mp_f_10_xenon_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_xenon.png");
	public static final ResourceLocation mp_f_10_xenon_bhole_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_xenon_bhole.png");
	
	public static final ResourceLocation mp_f_10_long_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene.png");
	public static final ResourceLocation mp_f_10_long_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_camo.png");
	public static final ResourceLocation mp_f_10_long_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_desert.png");
	public static final ResourceLocation mp_f_10_long_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_sky.png");
	public static final ResourceLocation mp_f_10_long_kerosene_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_flames.png");
	public static final ResourceLocation mp_f_10_long_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_insulation.png");
	public static final ResourceLocation mp_f_10_long_kerosene_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_sleek.png");
	public static final ResourceLocation mp_f_10_long_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_kerosene_metal.png");
	public static final ResourceLocation mp_f_10_long_kerosene_dash_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_dash.png");
	public static final ResourceLocation mp_f_10_long_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_taint.png");
	public static final ResourceLocation mp_f_10_long_kerosene_vap_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_kerosene_vap.png");
	
	public static final ResourceLocation mp_f_10_long_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid.png");
	public static final ResourceLocation mp_f_10_long_solid_flames_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_flames.png");
	public static final ResourceLocation mp_f_10_long_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_insulation.png");
	public static final ResourceLocation mp_f_10_long_solid_sleek_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_sleek.png");
	public static final ResourceLocation mp_f_10_long_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_long_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_10_long_solid_bullet_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_solid_bullet.png");
	public static final ResourceLocation mp_f_10_long_solid_silvermoonlight_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_10_long_solid_silvermoonlight.png");
	
	public static final ResourceLocation mp_f_10_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_kerosene.png");
	public static final ResourceLocation mp_f_10_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_solid.png");
	public static final ResourceLocation mp_f_10_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_hydrogen.png");
	public static final ResourceLocation mp_f_10_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_10_15_balefire.png");
	
	public static final ResourceLocation mp_f_15_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene.png");
	public static final ResourceLocation mp_f_15_kerosene_camo_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_camo.png");
	public static final ResourceLocation mp_f_15_kerosene_desert_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_desert.png");
	public static final ResourceLocation mp_f_15_kerosene_sky_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_sky.png");
	public static final ResourceLocation mp_f_15_kerosene_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_insulation.png");
	public static final ResourceLocation mp_f_15_kerosene_metal_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_metal.png");
	public static final ResourceLocation mp_f_15_kerosene_decorated_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_decorated.png");
	public static final ResourceLocation mp_f_15_kerosene_steampunk_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_steampunk.png");
	public static final ResourceLocation mp_f_15_kerosene_polite_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_polite.png");
	public static final ResourceLocation mp_f_15_kerosene_blackjack_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/base/mp_f_15_kerosene_blackjack.png");
	public static final ResourceLocation mp_f_15_kerosene_lambda_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_lambda.png");
	public static final ResourceLocation mp_f_15_kerosene_minuteman_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_minuteman.png");
	public static final ResourceLocation mp_f_15_kerosene_pip_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_pip.png");
	public static final ResourceLocation mp_f_15_kerosene_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_kerosene_taint.png");
	public static final ResourceLocation mp_f_15_kerosene_yuck_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_kerosene_yuck.png");
	
	public static final ResourceLocation mp_f_15_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid.png");
	public static final ResourceLocation mp_f_15_solid_insulation_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_insulation.png");
	public static final ResourceLocation mp_f_15_solid_desh_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_desh.png");
	public static final ResourceLocation mp_f_15_solid_soviet_glory_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_soviet_glory.png");
	public static final ResourceLocation mp_f_15_solid_soviet_stank_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_soviet_stank.png");
	public static final ResourceLocation mp_f_15_solid_faust_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_faust.png");
	public static final ResourceLocation mp_f_15_solid_silvermoonlight_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_silvermoonlight.png");
	public static final ResourceLocation mp_f_15_solid_snowy_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_solid_snowy.png");
	public static final ResourceLocation mp_f_15_solid_panorama_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_panorama.png");
	public static final ResourceLocation mp_f_15_solid_roses_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_solid_roses.png");

	public static final ResourceLocation mp_f_15_hydrogen_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_hydrogen.png");
	public static final ResourceLocation mp_f_15_hydrogen_cathedral_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/contest/mp_f_15_hydrogen_cathedral.png");

	public static final ResourceLocation mp_f_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_balefire.png");

	public static final ResourceLocation mp_f_15_20_kerosene_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_kerosene.png");
	public static final ResourceLocation mp_f_15_20_kerosene_magnusson_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_kerosene_magnusson.png");
	public static final ResourceLocation mp_f_15_20_solid_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/fuselages/mp_f_15_20_solid.png");
	
	public static final ResourceLocation mp_w_10_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_he.png");
	public static final ResourceLocation mp_w_10_incendiary_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_incendiary.png");
	public static final ResourceLocation mp_w_10_buster_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_buster.png");
	public static final ResourceLocation mp_w_10_nuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_nuclear.png");
	public static final ResourceLocation mp_w_10_nuclear_large_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_nuclear_large.png");
	public static final ResourceLocation mp_w_10_taint_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_taint.png");
	public static final ResourceLocation mp_w_10_cloud_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_10_cloud.png");
	public static final ResourceLocation mp_w_15_he_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_he.png");
	public static final ResourceLocation mp_w_15_incendiary_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_incendiary.png");
	public static final ResourceLocation mp_w_15_nuclear_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_nuclear.png");
	public static final ResourceLocation mp_w_15_nuclear_shark_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_nuclear_shark.png");
	public static final ResourceLocation mp_w_15_n2_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_n2.png");
	public static final ResourceLocation mp_w_15_balefire_tex = new ResourceLocation(RefStrings.MODID, "textures/models/missile_parts/warheads/mp_w_15_balefire.png");
	
	//ISBRHs
	
	public static void init(){
	}
}
