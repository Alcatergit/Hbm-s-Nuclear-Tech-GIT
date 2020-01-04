package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.bomb.BlockCloudResidue;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.bomb.NukeFleija;
import com.hbm.blocks.bomb.NukeMan;
import com.hbm.blocks.fluid.ToxicBlock;
import com.hbm.blocks.fluid.ToxicFluid;
import com.hbm.blocks.generic.BlockMush;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.BlockOre;
import com.hbm.blocks.generic.WasteEarth;
import com.hbm.blocks.generic.WasteLog;
import com.hbm.blocks.machine.BlockCable;
import com.hbm.blocks.machine.BlockConverterHeRf;
import com.hbm.blocks.machine.BlockConverterRfHe;
import com.hbm.blocks.machine.DummyBlockAssembler;
import com.hbm.blocks.machine.DummyBlockChemplant;
import com.hbm.blocks.machine.DummyBlockMachine;
import com.hbm.blocks.machine.MachineAssembler;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.blocks.machine.MachineChemplant;
import com.hbm.blocks.machine.MachineCoal;
import com.hbm.blocks.machine.MachineDiFurnace;
import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.blocks.machine.MachinePress;
import com.hbm.blocks.machine.MachineRTG;
import com.hbm.blocks.machine.MachineReactorSmall;
import com.hbm.blocks.machine.MachineTransformer;
import com.hbm.blocks.test.TestRender;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.lib.ModDamageSource;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	//public static final Block fatduck = new BlockBase(Material.IRON, "fatduck");
	
	public static final Block test_render = new TestRender(Material.ROCK, "test_render");
	public static final Block taint = new BlockTaint(Material.IRON, "taint").setCreativeTab(null).setHardness(15.0F).setResistance(10.0F);
	public static final Block residue = new BlockCloudResidue(Material.IRON, "residue").setHardness(0.5F).setResistance(0.5F).setCreativeTab(null);
	
	//Generic blocks
	public static final Block asphalt = new BlockBase(Material.ROCK, "asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F);
	public static final Block concrete_smooth = new BlockBase(Material.ROCK, "concrete_smooth").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(4000.0F);
	
	//Ores
	public static final Block ore_uranium = new BlockBase(Material.ROCK, "ore_uranium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block ore_schrabidium = new BlockOre(Material.ROCK, 0.1F, 0.5F, "ore_schrabidium").setHardness(15.0F).setResistance(600.0F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block ore_nether_uranium = new BlockOre(Material.ROCK, "ore_nether_uranium").setHardness(0.4F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block ore_nether_schrabidium = new BlockBase(Material.ROCK, "ore_nether_schrabidium").setHardness(15.0F).setResistance(600.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block ore_thorium = new BlockBase(Material.ROCK, "ore_thorium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_titanium = new BlockBase(Material.ROCK, "ore_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_sulfur = new BlockOre(Material.ROCK, "ore_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_niter = new BlockOre(Material.ROCK, "ore_niter").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_copper = new BlockBase(Material.ROCK, "ore_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_tungsten = new BlockBase(Material.ROCK, "ore_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_aluminium = new BlockBase(Material.ROCK, "ore_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_fluorite = new BlockOre(Material.ROCK, "ore_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_lead = new BlockBase(Material.ROCK, "ore_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_beryllium = new BlockBase(Material.ROCK, "ore_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block ore_lignite = new BlockOre(Material.ROCK, "ore_lignite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block ore_rare = new BlockOre(Material.ROCK, "ore_rare").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_nether_plutonium = new BlockBase(Material.ROCK, "ore_nether_plutonium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_tungsten = new BlockBase(Material.ROCK, "ore_nether_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_sulfur = new BlockOre(Material.ROCK, "ore_nether_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	
	public static final Block ore_meteor_uranium = new BlockOre(Material.ROCK, "ore_meteor_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_thorium = new BlockOre(Material.ROCK, "ore_meteor_thorium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_titanium = new BlockOre(Material.ROCK, "ore_meteor_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_sulfur = new BlockOre(Material.ROCK, "ore_meteor_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_copper = new BlockOre(Material.ROCK, "ore_meteor_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_tungsten = new BlockOre(Material.ROCK, "ore_meteor_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_aluminium = new BlockOre(Material.ROCK, "ore_meteor_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_lead = new BlockOre(Material.ROCK, "ore_meteor_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_lithium = new BlockOre(Material.ROCK, "ore_meteor_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_meteor_starmetal = new BlockOre(Material.ROCK, "ore_meteor_starmetal").setCreativeTab(MainRegistry.blockTab).setHardness(10.0F).setResistance(100.0F);
	
	public static final Block ore_oil_sand = new BlockFallingBase(Material.SAND, "ore_oil_sand", SoundType.SAND).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(1.0F);
	
	//Material Blocks
	public static final Block block_niter = new BlockBase(Material.IRON, "block_niter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_sulfur = new BlockBase(Material.IRON, "block_sulfur").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_thorium = new BlockBase(Material.IRON, "block_thorium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_uranium = new BlockOre(Material.IRON, 0.1F, 1.5F, "block_uranium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_titanium = new BlockBase(Material.IRON, "block_titanium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_copper = new BlockBase(Material.IRON, "block_copper").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_red_copper = new BlockBase(Material.IRON, "block_red_copper").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_advanced_alloy = new BlockBase(Material.IRON, "block_advanced_alloy").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_tungsten = new BlockBase(Material.IRON, "block_tungsten").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_aluminium = new BlockBase(Material.IRON, "block_aluminium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_fluorite = new BlockBase(Material.IRON, "block_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_steel = new BlockBase(Material.IRON, "block_steel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_lead = new BlockBase(Material.IRON, "block_lead").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_beryllium = new BlockBase(Material.IRON, "block_beryllium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_schrabidium = new BlockBase(Material.IRON, "block_schrabidium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_combine_steel = new BlockBase(Material.IRON, "block_combine_steel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_magnetized_tungsten = new BlockBase(Material.IRON, "block_magnetized_tungsten").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(35.0F);
	public static final Block block_desh = new BlockBase(Material.IRON, "block_desh").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block gravel_obsidian = new BlockFallingBase(Material.IRON, "gravel_obsidian", SoundType.GROUND).setHardness(5.0F).setResistance(600F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block brick_concrete = new BlockBase(Material.ROCK, "brick_concrete").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(6000.0F);
	public static final Block brick_light = new BlockBase(Material.ROCK, "brick_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(1000.0F);
	public static final Block block_scrap = new BlockFallingBase(Material.SAND, "block_scrap", SoundType.GROUND).setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F);
	public static final Block brick_obsidian = new BlockBase(Material.ROCK, "brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(8000.0F);
	
	//Radiation blocks
	public static final Block mush = new BlockMush(Material.PLANTS, "mush").setLightLevel(0.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block = new BlockMushHuge(Material.PLANTS, "mush_block").setLightLevel(1.0F).setHardness(0.2F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block_stem = new BlockMushHuge(Material.PLANTS, "mush_block_stem").setLightLevel(1.0F).setHardness(0.3F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block block_waste = new BlockOre(Material.IRON, 5F, 60F, "block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block waste_earth = new WasteEarth(Material.GROUND, 0.25F, 7.5F, "waste_earth").setSoundType(SoundType.GROUND).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_mycelium = new WasteEarth(Material.GROUND, 1.0F, 25.0F, "waste_mycelium").setSoundType(SoundType.GROUND).setLightLevel(1.0F).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block frozen_grass = new WasteEarth(Material.GROUND, "frozen_grass").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite = new BlockOre(Material.SAND, "waste_trinitite").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite_red = new BlockOre(Material.SAND, "waste_trinitite_red").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_log = new WasteLog(Material.WOOD, "waste_log").setSoundType(SoundType.WOOD).setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block frozen_log = new WasteLog(Material.WOOD, "frozen_log").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_planks = new BlockOre(Material.WOOD, "waste_planks").setSoundType(SoundType.WOOD).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block sellafield_slaked = new BlockBase(Material.ROCK, "sellafield_slaked").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_0 = new BlockOre(Material.ROCK, 0.5F, 10.0F, "sellafield_0").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_1 = new BlockOre(Material.ROCK, 1.0F, 15.0F, "sellafield_1").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_2 = new BlockOre(Material.ROCK, 2.5F, 25.0F, "sellafield_2").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_3 = new BlockOre(Material.ROCK, 4.0F, 40.0F, "sellafield_3").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_4 = new BlockOre(Material.ROCK, 5.0F, 50.0F, "sellafield_4").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_core = new BlockOre(Material.ROCK, 10.0F, 150.0F, "sellafield_core").setSoundType(SoundType.STONE).setHardness(10.0F).setCreativeTab(MainRegistry.blockTab);
	
	//Bombs
	public static final Block nuke_man = new NukeMan(Material.IRON, "nuke_man").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_man = 6;
	
	public static final Block nuke_fleija = new NukeFleija(Material.IRON, "nuke_fleija").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_fleija = 17;
	
	
	//Machines
	public static final Block machine_battery_potato = new MachineBattery(Material.IRON, 10000, "machine_battery_potato").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_battery = new MachineBattery(Material.IRON, 1000000, "machine_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_lithium_battery = new MachineBattery(Material.IRON, 15000000, "machine_lithium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_schrabidium_battery = new MachineBattery(Material.IRON, 500000000, "machine_schrabidium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_dineutronium_battery = new MachineBattery(Material.IRON, 150000000000L, "machine_dineutronium_battery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_battery = 21;
	
	public static final Block machine_transformer = new MachineTransformer(Material.IRON, 10000L, 1, "machine_transformer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_transformer_20 = new MachineTransformer(Material.IRON, 10000L, 20, "machine_transformer_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_transformer_dnt = new MachineTransformer(Material.IRON, 1000000000000000L, 1, "machine_transformer_dnt").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_transformer_dnt_20 = new MachineTransformer(Material.IRON, 1000000000000000L, 20, "machine_transformer_dnt_20").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block machine_converter_he_rf = new BlockConverterHeRf(Material.IRON, "machine_converter_he_rf").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_converter_he_rf = 28;
	public static final Block machine_converter_rf_he = new BlockConverterRfHe(Material.IRON, "machine_converter_rf_he").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_converter_rf_he = 29;
	
	public static final Block machine_press = new MachinePress(Material.IRON, "machine_press").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_press = 53;
	
	public static final Block machine_difurnace_on = new MachineDiFurnace(Material.IRON, "machine_difurnace_on", true).setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null);
	public static final Block machine_difurnace_off = new MachineDiFurnace(Material.IRON, "machine_difurnace_off", false).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_test_difurnace = 1;
	
	public static final Block machine_coal_off = new MachineCoal(Material.IRON, "machine_coal_off", false).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_coal_on = new MachineCoal(Material.IRON, "machine_coal_on", true).setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_machine_coal = 22;
	
	public static final Block machine_generator = new MachineGenerator(Material.IRON, "machine_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_machine_generator = 15;
	
	public static final Block machine_reactor_small = new MachineReactorSmall(Material.IRON, "machine_reactor_small").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_reactor_small = 65;
	
	public static final Block machine_assembler = new MachineAssembler(Material.IRON, "machine_assembler").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(100.0F);
	public static final int guiID_machine_assembler = 48;
	
	public static final Block machine_chemplant = new MachineChemplant(Material.IRON, "machine_chemplant").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_chemplant = 49;
	
	public static final Block machine_rtg_grey = new MachineRTG(Material.IRON, "machine_rtg_grey").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_rtg = 42;
	
	//Cables
	public static final Block red_cable = new BlockCable(Material.IRON, "red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	//Fluids
	public static final Material fluidtoxic = new MaterialLiquid(MapColor.GREEN);
	public static Block toxic_block;
	
	//Dummy blocks
	public static final Block dummy_block_assembler = new DummyBlockAssembler(Material.IRON, "dummy_block_assembler").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F);
	public static final Block dummy_port_assembler = new DummyBlockAssembler(Material.IRON, "dummy_port_assembler").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block dummy_block_chemplant = new DummyBlockChemplant(Material.IRON, "dummy_block_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_chemplant = new DummyBlockChemplant(Material.IRON, "dummy_port_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_reactor_small = new DummyBlockMachine(Material.IRON, "dummy_block_reactor_small", false, guiID_reactor_small, machine_reactor_small).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_reactor_small = new DummyBlockMachine(Material.IRON, "dummy_port_reactor_small", true, guiID_reactor_small, machine_reactor_small).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static void preInit(){
		for(Block block : ALL_BLOCKS){
			ForgeRegistries.BLOCKS.register(block);
		}
	}
	
	public static void init(){
		
	}
	
	public static void postInit(){
		
	}
}
