package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.bomb.Balefire;
import com.hbm.blocks.bomb.BlockCloudResidue;
import com.hbm.blocks.bomb.BlockCrashedBomb;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.bomb.BombFlameWar;
import com.hbm.blocks.bomb.BombFloat;
import com.hbm.blocks.bomb.BombMulti;
import com.hbm.blocks.bomb.BombThermo;
import com.hbm.blocks.bomb.DetCord;
import com.hbm.blocks.bomb.Landmine;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.blocks.bomb.NukeBoy;
import com.hbm.blocks.bomb.NukeCustom;
import com.hbm.blocks.bomb.NukeFleija;
import com.hbm.blocks.bomb.NukeGadget;
import com.hbm.blocks.bomb.NukeMan;
import com.hbm.blocks.bomb.NukeMike;
import com.hbm.blocks.bomb.NukeN2;
import com.hbm.blocks.bomb.NukeN45;
import com.hbm.blocks.bomb.NukePrototype;
import com.hbm.blocks.bomb.NukeSolinium;
import com.hbm.blocks.bomb.NukeTsar;
import com.hbm.blocks.bomb.RailgunPlasma;
import com.hbm.blocks.bomb.TurretCIWS;
import com.hbm.blocks.bomb.TurretCheapo;
import com.hbm.blocks.bomb.TurretFlamer;
import com.hbm.blocks.bomb.TurretHeavy;
import com.hbm.blocks.bomb.TurretLight;
import com.hbm.blocks.bomb.TurretRocket;
import com.hbm.blocks.bomb.TurretSpitfire;
import com.hbm.blocks.bomb.TurretTau;
import com.hbm.blocks.generic.BlockAbsorber;
import com.hbm.blocks.generic.BlockClorine;
import com.hbm.blocks.generic.BlockClorineSeal;
import com.hbm.blocks.generic.BlockMarker;
import com.hbm.blocks.generic.BlockMush;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.BlockNoDrop;
import com.hbm.blocks.generic.BlockOre;
import com.hbm.blocks.generic.BlockPlasma;
import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.generic.BlockVent;
import com.hbm.blocks.generic.DecoBlock;
import com.hbm.blocks.generic.Guide;
import com.hbm.blocks.generic.RedBarrel;
import com.hbm.blocks.generic.ReinforcedBlock;
import com.hbm.blocks.generic.ReinforcedLamp;
import com.hbm.blocks.generic.WasteEarth;
import com.hbm.blocks.generic.WasteLog;
import com.hbm.blocks.generic.YellowBarrel;
import com.hbm.blocks.machine.BlastDoor;
import com.hbm.blocks.machine.BlockCable;
import com.hbm.blocks.machine.BlockConverterHeRf;
import com.hbm.blocks.machine.BlockConverterRfHe;
import com.hbm.blocks.machine.BlockDecon;
import com.hbm.blocks.machine.BlockFluidDuct;
import com.hbm.blocks.machine.BlockGasDuct;
import com.hbm.blocks.machine.BlockHatch;
import com.hbm.blocks.machine.BlockOilDuct;
import com.hbm.blocks.machine.BlockReactor;
import com.hbm.blocks.machine.BlockRotatable;
import com.hbm.blocks.machine.BlockSeal;
import com.hbm.blocks.machine.CableSwitch;
import com.hbm.blocks.machine.DummyBlockAssembler;
import com.hbm.blocks.machine.DummyBlockBlast;
import com.hbm.blocks.machine.DummyBlockCentrifuge;
import com.hbm.blocks.machine.DummyBlockChemplant;
import com.hbm.blocks.machine.DummyBlockCyclotron;
import com.hbm.blocks.machine.DummyBlockDrill;
import com.hbm.blocks.machine.DummyBlockFlare;
import com.hbm.blocks.machine.DummyBlockFluidTank;
import com.hbm.blocks.machine.DummyBlockMachine;
import com.hbm.blocks.machine.DummyBlockPumpjack;
import com.hbm.blocks.machine.DummyBlockRadGen;
import com.hbm.blocks.machine.DummyBlockRefinery;
import com.hbm.blocks.machine.DummyBlockTurbofan;
import com.hbm.blocks.machine.DummyBlockVault;
import com.hbm.blocks.machine.DummyBlockWell;
import com.hbm.blocks.machine.FWatzCore;
import com.hbm.blocks.machine.FWatzHatch;
import com.hbm.blocks.machine.FactoryCoreAdvanced;
import com.hbm.blocks.machine.FactoryCoreTitanium;
import com.hbm.blocks.machine.FactoryHatch;
import com.hbm.blocks.machine.FusionCore;
import com.hbm.blocks.machine.FusionHatch;
import com.hbm.blocks.machine.GasDuctSolid;
import com.hbm.blocks.machine.GeigerCounter;
import com.hbm.blocks.machine.MachineAmgen;
import com.hbm.blocks.machine.MachineArcFurnace;
import com.hbm.blocks.machine.MachineAssembler;
import com.hbm.blocks.machine.MachineBattery;
import com.hbm.blocks.machine.MachineBoiler;
import com.hbm.blocks.machine.MachineCMBFactory;
import com.hbm.blocks.machine.MachineCentrifuge;
import com.hbm.blocks.machine.MachineChemplant;
import com.hbm.blocks.machine.MachineCoal;
import com.hbm.blocks.machine.MachineCyclotron;
import com.hbm.blocks.machine.MachineDiFurnace;
import com.hbm.blocks.machine.MachineEPress;
import com.hbm.blocks.machine.MachineElectricFurnace;
import com.hbm.blocks.machine.MachineFluidTank;
import com.hbm.blocks.machine.MachineGasCent;
import com.hbm.blocks.machine.MachineGasFlare;
import com.hbm.blocks.machine.MachineGenerator;
import com.hbm.blocks.machine.MachineKeyForge;
import com.hbm.blocks.machine.MachineMiningDrill;
import com.hbm.blocks.machine.MachineNukeFurnace;
import com.hbm.blocks.machine.MachineOilWell;
import com.hbm.blocks.machine.MachinePress;
import com.hbm.blocks.machine.MachinePuF6Tank;
import com.hbm.blocks.machine.MachinePumpjack;
import com.hbm.blocks.machine.MachineRTG;
import com.hbm.blocks.machine.MachineRadGen;
import com.hbm.blocks.machine.MachineReactor;
import com.hbm.blocks.machine.MachineReactorControl;
import com.hbm.blocks.machine.MachineReactorSmall;
import com.hbm.blocks.machine.MachineRefinery;
import com.hbm.blocks.machine.MachineRtgFurnace;
import com.hbm.blocks.machine.MachineSchrabidiumTransmutator;
import com.hbm.blocks.machine.MachineSeleniumEngine;
import com.hbm.blocks.machine.MachineShredder;
import com.hbm.blocks.machine.MachineSiren;
import com.hbm.blocks.machine.MachineTeleLinker;
import com.hbm.blocks.machine.MachineTeleporter;
import com.hbm.blocks.machine.MachineTransformer;
import com.hbm.blocks.machine.MachineTurbine;
import com.hbm.blocks.machine.MachineTurbofan;
import com.hbm.blocks.machine.MachineUF6Tank;
import com.hbm.blocks.machine.OilDuctSolid;
import com.hbm.blocks.machine.PinkCloudBroadcaster;
import com.hbm.blocks.machine.PylonRedWire;
import com.hbm.blocks.machine.RadioRec;
import com.hbm.blocks.machine.Radiobox;
import com.hbm.blocks.machine.ReactorCore;
import com.hbm.blocks.machine.ReactorHatch;
import com.hbm.blocks.machine.SPPBottom;
import com.hbm.blocks.machine.SPPTop;
import com.hbm.blocks.machine.VaultDoor;
import com.hbm.blocks.machine.WasteDrum;
import com.hbm.blocks.machine.WatzCore;
import com.hbm.blocks.machine.WatzHatch;
import com.hbm.blocks.machine.WireCoated;
import com.hbm.blocks.test.TestRender;
import com.hbm.lib.RefStrings;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	//public static final Block fatduck = new BlockBase(Material.IRON, "fatduck");
	
	public static final Block test_render = new TestRender(Material.ROCK, "test_render");
	public static final Block taint = new BlockTaint(Material.IRON, "taint").setCreativeTab(null).setHardness(15.0F).setResistance(10.0F);
	public static final Block residue = new BlockCloudResidue(Material.IRON, "residue").setHardness(0.5F).setResistance(0.5F).setCreativeTab(null);
	public static final Block balefire = new Balefire("balefire").setHardness(0.0F).setLightLevel(1.0F).setCreativeTab(null);
	
	
	//Generic blocks
	public static final Block asphalt = new BlockBase(Material.ROCK, "asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F);
	public static final Block reinforced_brick = new BlockBase(Material.ROCK, "reinforced_brick").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(8000.0F);
	public static final Block reinforced_glass = new ReinforcedBlock(Material.GLASS, "reinforced_glass").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(200.0F);
	public static final Block reinforced_light = new ReinforcedBlock(Material.ROCK, "reinforced_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setLightLevel(1.0F).setHardness(15.0F).setResistance(300.0F);
	public static final Block reinforced_sand = new BlockBase(Material.ROCK, "reinforced_sand").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(400.0F);
	public static final Block reinforced_lamp_off = new ReinforcedLamp(Material.ROCK, false, "reinforced_lamp_off").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(300.0F);
	public static final Block reinforced_lamp_on = new ReinforcedLamp(Material.ROCK, true, "reinforced_lamp_on").setCreativeTab(null).setHardness(15.0F).setResistance(300.0F);
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
	public static final Block ore_nether_fire = new BlockOre(Material.ROCK, "ore_nether_fire").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	
	public static final Block ore_oil = new BlockOre(Material.ROCK, "ore_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_oil_empty = new BlockBase(Material.ROCK, "ore_oil_empty").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_oil_sand = new BlockFallingBase(Material.SAND, "ore_oil_sand", SoundType.SAND).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(1.0F);
	
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
	
	public static final Block ore_tikite = new BlockBase(Material.ROCK, "ore_tikite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block block_meteor = new BlockOre(Material.ROCK, "block_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_cobble = new BlockOre(Material.ROCK, "block_meteor_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_broken = new BlockOre(Material.ROCK, "block_meteor_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_molten = new BlockOre(Material.ROCK, true, "block_meteor_molten").setLightLevel(0.75F).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_treasure = new BlockOre(Material.ROCK, "block_meteor_treasure").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	
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
	public static final Block brick_obsidian = new BlockBase(Material.ROCK, "brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(8000.0F);
	public static final Block block_scrap = new BlockFallingBase(Material.SAND, "block_scrap", SoundType.GROUND).setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F);
	public static final Block block_electrical_scrap = new BlockFallingBase(Material.IRON, "block_electrical_scrap", SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F);
	
	//Radiation blocks
	public static final Block mush = new BlockMush(Material.PLANTS, "mush").setLightLevel(0.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block = new BlockMushHuge(Material.PLANTS, "mush_block").setLightLevel(1.0F).setHardness(0.2F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block_stem = new BlockMushHuge(Material.PLANTS, "mush_block_stem").setLightLevel(1.0F).setHardness(0.3F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block block_waste = new BlockOre(Material.IRON, 5F, 60F, "block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block waste_earth = new WasteEarth(Material.GROUND, 0.25F, 7.5F, "waste_earth").setSoundType(SoundType.GROUND).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_mycelium = new WasteEarth(Material.GROUND, 1.0F, 25.0F, "waste_mycelium").setSoundType(SoundType.GROUND).setLightLevel(1.0F).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite = new BlockOre(Material.SAND, "waste_trinitite").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite_red = new BlockOre(Material.SAND, "waste_trinitite_red").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_log = new WasteLog(Material.WOOD, "waste_log").setSoundType(SoundType.WOOD).setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_planks = new BlockOre(Material.WOOD, "waste_planks").setSoundType(SoundType.WOOD).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block frozen_grass = new WasteEarth(Material.GROUND, "frozen_grass").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block frozen_log = new WasteLog(Material.WOOD, "frozen_log").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block frozen_planks = new BlockOre(Material.WOOD, "frozen_planks").setSoundType(SoundType.GLASS).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block frozen_dirt = new BlockOre(Material.GROUND, "frozen_dirt").setSoundType(SoundType.GLASS).setCreativeTab(MainRegistry.blockTab).setHardness(0.5F).setResistance(2.5F);
	
	public static final Block sellafield_slaked = new BlockBase(Material.ROCK, "sellafield_slaked").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_0 = new BlockOre(Material.ROCK, 0.5F, 10.0F, "sellafield_0").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_1 = new BlockOre(Material.ROCK, 1.0F, 15.0F, "sellafield_1").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_2 = new BlockOre(Material.ROCK, 2.5F, 25.0F, "sellafield_2").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_3 = new BlockOre(Material.ROCK, 4.0F, 40.0F, "sellafield_3").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_4 = new BlockOre(Material.ROCK, 5.0F, 50.0F, "sellafield_4").setSoundType(SoundType.STONE).setHardness(5.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sellafield_core = new BlockOre(Material.ROCK, 10.0F, 150.0F, "sellafield_core").setSoundType(SoundType.STONE).setHardness(10.0F).setCreativeTab(MainRegistry.blockTab);
	
	//Bombs
	public static final Block nuke_gadget = new NukeGadget(Material.IRON, "nuke_gadget").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_gadget = 3;
	
	public static final Block nuke_boy = new NukeBoy(Material.IRON, "nuke_boy").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_boy = 4;
	
	public static final Block nuke_man = new NukeMan(Material.IRON, "nuke_man").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_man = 6;
	
	public static final Block nuke_mike = new NukeMike(Material.IRON, "nuke_mike").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_mike = 11;
	
	public static final Block nuke_tsar = new NukeTsar(Material.IRON, "nuke_tsar").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_tsar = 12;
	
	public static final Block nuke_fleija = new NukeFleija(Material.IRON, "nuke_fleija").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_fleija = 17;
	
	public static final Block nuke_prototype = new NukePrototype(Material.IRON, "nuke_prototype").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_prototype = 23;
	
	public static final Block nuke_solinium = new NukeSolinium(Material.IRON, "nuke_solinium").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_solinium = 60;
	
	public static final Block nuke_n2 = new NukeN2(Material.IRON, "nuke_n2").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_n2 = 61;
	
	public static final Block nuke_n45 = new NukeN45(Material.IRON, "nuke_n45").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_n45 = 77;
	
	public static final Block nuke_custom = new NukeCustom(Material.IRON, "nuke_custom").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_custom = 37;
	
	public static final Block bomb_multi = new BombMulti(Material.IRON, "bomb_multi").setCreativeTab(MainRegistry.nukeTab).setResistance(6000.0F);
	public static final int guiID_bomb_multi = 10;
	
	public static final Block crashed_balefire = new BlockCrashedBomb(Material.IRON, "crashed_bomb").setCreativeTab(MainRegistry.nukeTab).setBlockUnbreakable().setResistance(6000.0F);
	
	public static final Block mine_ap = new Landmine(Material.IRON, "mine_ap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F);
	public static final Block mine_he = new Landmine(Material.IRON, "mine_he").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F);
	public static final Block mine_shrap = new Landmine(Material.IRON, "mine_shrap").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F);
	public static final Block mine_fat = new Landmine(Material.IRON, "mine_fat").setCreativeTab(MainRegistry.nukeTab).setHardness(1.0F);
	
	public static final Block flame_war = new BombFlameWar(Material.IRON, "flame_war").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final Block float_bomb = new BombFloat(Material.IRON, "float_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final Block emp_bomb = new BombFloat(Material.IRON, "emp_bomb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final Block therm_endo = new BombThermo(Material.IRON, "therm_endo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final Block therm_exo = new BombThermo(Material.IRON, "therm_exo").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	
	public static final Block det_cord = new DetCord(Material.IRON, "det_cord").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F);
	public static final Block det_charge = new DetCord(Material.IRON, "det_charge").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F);
	public static final Block det_nuke = new DetCord(Material.IRON, "det_nuke").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F);
	public static final Block red_barrel = new RedBarrel(Material.IRON, "red_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block pink_barrel = new RedBarrel(Material.IRON, "pink_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block yellow_barrel = new YellowBarrel(Material.IRON, "yellow_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block vitrified_barrel = new YellowBarrel(Material.IRON, "vitrified_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block lox_barrel = new RedBarrel(Material.IRON, "lox_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block taint_barrel = new RedBarrel(Material.IRON, "taint_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	
	//Turrets
	public static final Block turret_light = new TurretLight(Material.IRON, "turret_light").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_heavy = new TurretHeavy(Material.IRON, "turret_heavy").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_rocket = new TurretRocket(Material.IRON, "turret_rocket").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_flamer = new TurretFlamer(Material.IRON, "turret_flamer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_tau = new TurretTau(Material.IRON, "turret_tau").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_spitfire = new TurretSpitfire(Material.IRON, "turret_spitfire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_cwis = new TurretCIWS(Material.IRON, "turret_cwis").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_cheapo = new TurretCheapo(Material.IRON, "turret_cheapo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	
	//Machines
	public static final Block machine_siren = new MachineSiren(Material.IRON, "machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_siren = 57;
	
	public static final Block broadcaster_pc = new PinkCloudBroadcaster(Material.ROCK, "broadcaster_pc").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block geiger = new GeigerCounter(Material.ROCK, "geiger").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F);
	
	public static final Block seal_frame = new BlockBase(Material.IRON, "seal_frame").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block seal_controller = new BlockSeal(Material.IRON, "seal_controller").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block seal_hatch = new BlockHatch(Material.IRON, "seal_hatch").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	
	public static final Block vault_door = new VaultDoor(Material.IRON, "vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block blast_door = new BlastDoor(Material.IRON, "blast_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block crate_iron = new BlockStorageCrate(Material.IRON, "crate_iron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block crate_steel = new BlockStorageCrate(Material.IRON, "crate_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block safe = new BlockStorageCrate(Material.IRON, "safe").setHardness(7.5F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_crate_iron = 46;
	public static final int guiID_crate_steel = 47;
	public static final int guiID_safe = 70;
	
	public static final Block machine_keyforge = new MachineKeyForge(Material.IRON, "machine_keyforge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final int guiID_keyforge = 67;
	
	public static final Block machine_telelinker = new MachineTeleLinker(Material.IRON, "machine_telelinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);
	public static final int guiID_telelinker = 68;
	
	public static final Block book_guide = new Guide(Material.IRON, "book_guide").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);
	
	public static final Block machine_boiler_off = new MachineBoiler(Material.IRON, false, "machine_boiler_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_boiler_on = new MachineBoiler(Material.IRON, true, "machine_boiler_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null);
	public static final Block machine_boiler_electric_off = new MachineBoiler(Material.IRON, false, "machine_boiler_electric_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_boiler_electric_on = new MachineBoiler(Material.IRON, true, "machine_boiler_electric_on").setHardness(5.0F).setResistance(10.0F).setLightLevel(1.0F).setCreativeTab(null);
	public static final int guiID_machine_boiler = 72;
	public static final int guiID_machine_boiler_electric = 73;
	
	public static final Block red_pylon = new PylonRedWire(Material.IRON, "red_pylon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
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
	public static final Block machine_epress = new MachineEPress(Material.IRON, "machine_epress").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_epress = 81;
	
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
	
	public static final Block machine_turbine = new MachineTurbine(Material.IRON, "machine_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_turbine = 74;
	
	public static final Block machine_nuke_furnace_off = new MachineNukeFurnace(false, "machine_nuke_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_nuke_furnace_on = new MachineNukeFurnace(true, "machine_nuke_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
	public static final int guiID_nuke_furnace = 13;
	
	public static final Block machine_rtg_furnace_off = new MachineRtgFurnace(false, "machine_rtg_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_rtg_furnace_on = new MachineRtgFurnace(true, "machine_rtg_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
	public static final int guiID_rtg_furnace = 14;
	
	public static final Block machine_selenium = new MachineSeleniumEngine(Material.IRON, "machine_selenium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_selenium = 63;
	
	public static final Block machine_controller = new MachineReactorControl(Material.IRON, "machine_controller").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_controller = 78;
	
	public static final Block machine_reactor = new MachineReactor(false, "machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_reactor_on = new MachineReactor(true, "machine_reactor_on").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_reactor = 9;
	
	public static final Block launch_pad = new LaunchPad(Material.IRON, "launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_launch_pad = 19;
	
	public static final Block machine_centrifuge = new MachineCentrifuge(Material.IRON, "machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_centrifuge = 5;
	
	public static final Block machine_gascent = new MachineGasCent(Material.IRON, "machine_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_gascent = 71;
	
	public static final Block machine_shredder = new MachineShredder(Material.IRON, "machine_shredder").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_shredder = 34;
	
	public static final Block machine_waste_drum = new WasteDrum(Material.IRON, "machine_waste_drum").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_waste_drum = 79;
	
	public static final Block machine_well = new MachineOilWell(Material.IRON, "machine_well").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_pumpjack = new MachinePumpjack(Material.IRON, "machine_pumpjack").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block oil_pipe = new BlockNoDrop(Material.IRON, "oil_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_machine_well = 40;
	public static final int guiID_machine_pumpjack = 51;
	
	public static final Block machine_flare = new MachineGasFlare(Material.IRON, "machine_flare").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_flare = 44;
	
	public static final Block machine_drill = new MachineMiningDrill(Material.IRON, "machine_drill").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block drill_pipe = new BlockNoDrop(Material.IRON, "drill_pipe").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_machine_drill = 45;
	
	public static final Block machine_turbofan = new MachineTurbofan(Material.IRON, "machine_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_turbofan = 52;
	
	public static final Block machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.IRON, "machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_schrabidium_transmutator = 30;
	
	public static final Block machine_combine_factory = new MachineCMBFactory(Material.IRON, "machine_combine_factory").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_combine_factory = 35;
	
	public static final Block machine_teleporter = new MachineTeleporter(Material.IRON, "machine_teleporter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_teleporter = 36;
	
	public static final Block radiobox = new Radiobox(Material.IRON, "radiobox").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block radiorec = new RadioRec(Material.IRON, "radiorec").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_radiobox = 66;
	public static final int guiID_radiorec = 69;
	
	public static final Block machine_uf6_tank = new MachineUF6Tank(Material.IRON, "machine_uf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_uf6_tank = 7;
	public static final Block machine_puf6_tank = new MachinePuF6Tank(Material.IRON, "machine_puf6_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_puf6_tank = 8;
	
	public static final Block machine_fluidtank = new MachineFluidTank(Material.IRON, "machine_fluidtank").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_fluidtank = 50;
	
	public static final Block machine_refinery = new MachineRefinery(Material.IRON, "machine_refinery").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_refinery = 43;
	
	public static final Block machine_electric_furnace_off = new MachineElectricFurnace(Material.IRON, false, "machine_electric_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_electric_furnace_on = new MachineElectricFurnace(Material.IRON, true, "machine_electric_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
	public static final Block machine_arc_furnace_off = new MachineArcFurnace(Material.IRON, false, "machine_arc_furnace_off").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_arc_furnace_on = new MachineArcFurnace(Material.IRON, true, "machine_arc_furnace_on").setHardness(5.0F).setLightLevel(1.0F).setResistance(10.0F);
	public static final int guiID_electric_furnace = 16;
	public static final int guiID_machine_arc = 82;
	
	public static final Block machine_cyclotron = new MachineCyclotron(Material.IRON, "machine_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_cyclotron = 41;
	
	public static final Block machine_radgen = new MachineRadGen(Material.IRON, "machine_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_radgen = 58;
	
	public static final Block machine_amgen = new MachineAmgen(Material.IRON, "machine_amgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_geo = new MachineAmgen(Material.IRON, "machine_geo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_minirtg = new MachineAmgen(Material.IRON, "machine_minirtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block machine_spp_bottom = new SPPBottom(Material.IRON, "machine_spp_bottom").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_spp_top = new SPPTop(Material.IRON, "machine_spp_top").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block marker_structure = new BlockMarker(Material.IRON, "marker_structure").setHardness(0.0F).setResistance(0.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final int guiID_factory_titanium = 24;
	public static final Block factory_titanium_hull = new BlockBase(Material.IRON, "factory_titanium_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_titanium_furnace = new FactoryHatch(Material.IRON, "factory_titanium_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_titanium_conductor = new BlockReactor(Material.IRON, "factory_titanium_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_titanium_core = new FactoryCoreTitanium(Material.IRON, "factory_titanium_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_advanced_hull = new BlockBase(Material.IRON, "factory_advanced_hull").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_advanced_furnace = new FactoryHatch(Material.IRON, "factory_advanced_furnace").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_advanced_conductor = new BlockReactor(Material.IRON, "factory_advanced_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block factory_advanced_core = new FactoryCoreAdvanced(Material.IRON, "factory_advanced_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_factory_advanced = 25;
	
	public static final Block reactor_element = new BlockReactor(Material.IRON, "reactor_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_control = new BlockReactor(Material.IRON, "reactor_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_hatch = new ReactorHatch(Material.IRON, "reactor_hatch").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_ejector = new BlockRotatable(Material.IRON, "reactor_ejector").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_inserter = new BlockRotatable(Material.IRON, "reactor_inserter").setHardness(5.0F).setResistance(1000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_conductor = new BlockReactor(Material.IRON, "reactor_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block reactor_computer = new ReactorCore(Material.IRON, "reactor_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_reactor_multiblock = 26;
	
	public static final Block fusion_conductor = new BlockReactor(Material.IRON, "fusion_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fusion_center = new BlockReactor(Material.IRON, "fusion_center").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fusion_motor = new BlockReactor(Material.IRON, "fusion_motor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fusion_heater = new BlockReactor(Material.IRON, "fusion_heater").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fusion_hatch = new FusionHatch(Material.IRON, "fusion_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fusion_core = new FusionCore(Material.IRON, "fusion_core_block").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block plasma = new BlockPlasma(Material.IRON, "plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_fusion_multiblock = 27;
	
	public static final Block watz_element = new BlockReactor(Material.IRON, "watz_element").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_control = new BlockReactor(Material.IRON, "watz_control").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_cooler = new BlockBase(Material.IRON, "watz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_end = new BlockBase(Material.IRON, "watz_end").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_hatch = new WatzHatch(Material.IRON, "watz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_conductor = new BlockReactor(Material.IRON, "watz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block watz_core = new WatzCore(Material.IRON, "watz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_watz_multiblock = 32;
	
	public static final Block fwatz_conductor = new BlockReactor(Material.IRON, "fwatz_conductor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_cooler = new BlockReactor(Material.IRON, "fwatz_cooler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_tank = new ReinforcedBlock(Material.IRON, "fwatz_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_scaffold = new BlockBase(Material.IRON, "fwatz_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_hatch = new FWatzHatch(Material.IRON, "fwatz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_computer = new BlockBase(Material.IRON, "fwatz_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_core = new FWatzCore(Material.IRON, "fwatz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_plasma = new BlockPlasma(Material.IRON, "fwatz_plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_fwatz_multiblock = 33;
	
	public static final Block absorber = new BlockAbsorber(Material.IRON, 2.5F, "absorber").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block absorber_red = new BlockAbsorber(Material.IRON, 10F, "absorber_red").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block absorber_green = new BlockAbsorber(Material.IRON, 100F, "absorber_green").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block absorber_pink = new BlockAbsorber(Material.IRON, 10000F, "absorber_pink").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block decon = new BlockDecon(Material.IRON, "decon").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block vent_chlorine = new BlockVent(Material.IRON, "vent_chlorine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block vent_cloud = new BlockVent(Material.IRON, "vent_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block vent_pink_cloud = new BlockVent(Material.IRON, "vent_pink_cloud").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block vent_chlorine_seal = new BlockClorineSeal(Material.IRON, "vent_chlorine_seal").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block chlorine_gas = new BlockClorine(Material.CLOTH, "chlorine_gas").setHardness(0.0F).setResistance(0.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block railgun_plasma = new RailgunPlasma(Material.IRON, "railgun_plasma").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final int guiID_railgun = 86;
	
	//Cables
	public static final Block red_cable = new BlockCable(Material.IRON, "red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block red_wire_coated = new WireCoated(Material.IRON, "red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block cable_switch = new CableSwitch(Material.IRON, "cable_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block oil_duct_solid = new OilDuctSolid(Material.IRON, "oil_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block oil_duct = new BlockOilDuct(Material.IRON, "oil_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block gas_duct_solid = new GasDuctSolid(Material.IRON, "gas_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block gas_duct = new BlockGasDuct(Material.IRON, "gas_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fluid_duct = new BlockFluidDuct(Material.IRON, "fluid_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	//Fluids
	public static final Material fluidtoxic = new MaterialLiquid(MapColor.GREEN).setReplaceable();
	public static Block toxic_block;
	
	public static final Material fluidmud = (new MaterialLiquid(MapColor.ADOBE).setReplaceable());
	public static Block mud_block;
	
	//Weird stuff
	public static final Block boxcar = new DecoBlock(Material.IRON, "boxcar").setSoundType(SoundType.METAL).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block boat = new DecoBlock(Material.IRON, "boat").setSoundType(SoundType.METAL).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	
	//Dummy blocks
	public static final Block dummy_block_assembler = new DummyBlockAssembler(Material.IRON, "dummy_block_assembler").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F);
	public static final Block dummy_port_assembler = new DummyBlockAssembler(Material.IRON, "dummy_port_assembler").setCreativeTab(null).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block dummy_block_chemplant = new DummyBlockChemplant(Material.IRON, "dummy_block_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_chemplant = new DummyBlockChemplant(Material.IRON, "dummy_port_chemplant").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_reactor_small = new DummyBlockMachine(Material.IRON, "dummy_block_reactor_small", false, guiID_reactor_small, machine_reactor_small).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_reactor_small = new DummyBlockMachine(Material.IRON, "dummy_port_reactor_small", true, guiID_reactor_small, machine_reactor_small).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_centrifuge = new DummyBlockCentrifuge(Material.IRON, "dummy_block_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_gascent = new DummyBlockMachine(Material.IRON, "dummy_block_gascent", false, guiID_gascent, machine_gascent).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_uf6 = new DummyBlockMachine(Material.IRON, "dummy_block_uf6", false, guiID_uf6_tank, machine_uf6_tank).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_block_puf6 = new DummyBlockMachine(Material.IRON, "dummy_block_puf6", false, guiID_puf6_tank, machine_puf6_tank).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_fluidtank = new DummyBlockFluidTank(Material.IRON, "dummy_block_fluidtank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_fluidtank = new DummyBlockFluidTank(Material.IRON, "dummy_port_fluidtank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_refinery = new DummyBlockRefinery(Material.IRON, "dummy_block_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_refinery = new DummyBlockRefinery(Material.IRON, "dummy_port_refinery").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_cyclotron = new DummyBlockCyclotron(Material.IRON, "dummy_block_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_cyclotron = new DummyBlockCyclotron(Material.IRON, "dummy_port_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_vault = new DummyBlockVault(Material.IRON, "dummy_block_vault").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null);
	public static final Block dummy_block_blast = new DummyBlockBlast(Material.IRON, "dummy_block_blast").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(null);
	
	public static final Block dummy_block_radgen = new DummyBlockRadGen(Material.IRON, "dummy_block_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_radgen = new DummyBlockRadGen(Material.IRON, "dummy_port_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_well = new DummyBlockWell(Material.IRON, "dummy_block_well").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_well = new DummyBlockWell(Material.IRON, "dummy_port_well").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_pumpjack = new DummyBlockPumpjack(Material.IRON, "dummy_block_pumpjack").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_pumpjack = new DummyBlockPumpjack(Material.IRON, "dummy_port_pumpjack").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_flare = new DummyBlockFlare(Material.IRON, "dummy_block_flare").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_flare = new DummyBlockFlare(Material.IRON, "dummy_port_flare").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_drill = new DummyBlockDrill(Material.IRON, "dummy_block_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_drill = new DummyBlockDrill(Material.IRON, "dummy_port_drill").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_turbofan = new DummyBlockTurbofan(Material.IRON, "dummy_block_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_turbofan = new DummyBlockTurbofan(Material.IRON, "dummy_port_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
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
