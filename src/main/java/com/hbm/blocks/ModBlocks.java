package com.hbm.blocks;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.bomb.Balefire;
import com.hbm.blocks.bomb.BlockCloudResidue;
import com.hbm.blocks.bomb.BlockCrashedBomb;
import com.hbm.blocks.bomb.BlockFireworks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.blocks.bomb.BombFlameWar;
import com.hbm.blocks.bomb.BombFloat;
import com.hbm.blocks.bomb.BombMulti;
import com.hbm.blocks.bomb.BombThermo;
import com.hbm.blocks.bomb.CheaterVirus;
import com.hbm.blocks.bomb.CheaterVirusSeed;
import com.hbm.blocks.bomb.CompactLauncher;
import com.hbm.blocks.bomb.CrystalPulsar;
import com.hbm.blocks.bomb.CrystalVirus;
import com.hbm.blocks.bomb.DetCord;
import com.hbm.blocks.bomb.DetMiner;
import com.hbm.blocks.bomb.Landmine;
import com.hbm.blocks.bomb.LaunchPad;
import com.hbm.blocks.bomb.LaunchTable;
import com.hbm.blocks.bomb.NukeBalefire;
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
import com.hbm.blocks.generic.BarbedWire;
import com.hbm.blocks.generic.BlockAbsorber;
import com.hbm.blocks.generic.BlockAmmoCrate;
import com.hbm.blocks.generic.BlockBallsSpawner;
import com.hbm.blocks.generic.BlockCanCrate;
import com.hbm.blocks.generic.BlockCap;
import com.hbm.blocks.generic.BlockChain;
import com.hbm.blocks.generic.BlockClorine;
import com.hbm.blocks.generic.BlockClorineSeal;
import com.hbm.blocks.generic.BlockCoalBurning;
import com.hbm.blocks.generic.BlockCoalOil;
import com.hbm.blocks.generic.BlockCrate;
import com.hbm.blocks.generic.BlockFallingRad;
import com.hbm.blocks.generic.BlockGeysir;
import com.hbm.blocks.generic.BlockGlyph;
import com.hbm.blocks.generic.BlockJungleCrate;
import com.hbm.blocks.generic.BlockLithium;
import com.hbm.blocks.generic.BlockMarker;
import com.hbm.blocks.generic.BlockMetalFence;
import com.hbm.blocks.generic.BlockModDoor;
import com.hbm.blocks.generic.BlockMush;
import com.hbm.blocks.generic.BlockMushHuge;
import com.hbm.blocks.generic.BlockNTMDirt;
import com.hbm.blocks.generic.BlockNTMGlass;
import com.hbm.blocks.generic.BlockNoDrop;
import com.hbm.blocks.generic.BlockOre;
import com.hbm.blocks.generic.BlockPinkLog;
import com.hbm.blocks.generic.BlockPinkSlab;
import com.hbm.blocks.generic.BlockPinkStairs;
import com.hbm.blocks.generic.BlockPlasma;
import com.hbm.blocks.generic.BlockRadResistant;
import com.hbm.blocks.generic.BlockRotatablePillar;
import com.hbm.blocks.generic.BlockSmolder;
import com.hbm.blocks.generic.BlockStorageCrate;
import com.hbm.blocks.generic.BlockVent;
import com.hbm.blocks.generic.DecoBlock;
import com.hbm.blocks.generic.DecoBlockAlt;
import com.hbm.blocks.generic.DecoPoleSatelliteReceiver;
import com.hbm.blocks.generic.DecoPoleTop;
import com.hbm.blocks.generic.DecoSteelPoles;
import com.hbm.blocks.generic.DecoTapeRecorder;
import com.hbm.blocks.generic.Guide;
import com.hbm.blocks.generic.RedBarrel;
import com.hbm.blocks.generic.ReinforcedLamp;
import com.hbm.blocks.generic.Spikes;
import com.hbm.blocks.generic.TrappedBrick;
import com.hbm.blocks.generic.WasteEarth;
import com.hbm.blocks.generic.WasteLog;
import com.hbm.blocks.generic.YellowBarrel;
import com.hbm.blocks.machine.*;
import com.hbm.blocks.network.BlockCable;
import com.hbm.blocks.network.BlockConveyor;
import com.hbm.blocks.network.BlockFluidDuct;
import com.hbm.blocks.network.BlockFluidPipeMk2;
import com.hbm.blocks.network.CableSwitch;
import com.hbm.blocks.test.KeypadTest;
import com.hbm.blocks.test.TestObjTester;
import com.hbm.blocks.test.TestRender;
import com.hbm.main.MainRegistry;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialLiquid;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.BlockRenderLayer;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class ModBlocks {

	public static List<Block> ALL_BLOCKS = new ArrayList<Block>();
	
	//public static final Block fatduck = new BlockBase(Material.IRON, "fatduck");
	
	public static final Block test_render = new TestRender(Material.ROCK, "test_render");
	public static final Block obj_tester = new TestObjTester(Material.IRON, "obj_tester").setCreativeTab(null).setHardness(2.5F).setResistance(10.0F);
	
	public static final Block cheater_virus = new CheaterVirus(Material.IRON, "cheater_virus").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	public static final Block cheater_virus_seed = new CheaterVirusSeed(Material.IRON, "cheater_virus_seed").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	public static final Block crystal_virus = new CrystalVirus(Material.IRON, "crystal_virus").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	public static final Block crystal_hardened = new BlockBase(Material.IRON, "crystal_hardened").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	public static final Block crystal_pulsar = new CrystalPulsar(Material.IRON, "crystal_pulsar").setHardness(15.0F).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	public static final Block taint = new BlockTaint(Material.IRON, "taint").setCreativeTab(null).setHardness(15.0F).setResistance(10.0F);
	public static final Block residue = new BlockCloudResidue(Material.IRON, "residue").setHardness(0.5F).setResistance(0.5F).setCreativeTab(null);
	public static final Block balefire = new Balefire("balefire").setHardness(0.0F).setLightLevel(1.0F).setCreativeTab(null);
	
	
	//Generic blocks
	public static final Block asphalt = new BlockBase(Material.ROCK, "asphalt").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F);
	public static final Block reinforced_brick = new BlockRadResistant(Material.ROCK, "reinforced_brick").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(8000.0F);
	public static final Block reinforced_glass = new BlockNTMGlass(Material.GLASS, BlockRenderLayer.CUTOUT, "reinforced_glass").setCreativeTab(MainRegistry.blockTab).setLightOpacity(0).setHardness(15.0F).setResistance(200.0F);
	public static final Block reinforced_light = new BlockBase(Material.ROCK, "reinforced_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setLightLevel(1.0F).setHardness(15.0F).setResistance(300.0F);
	public static final Block reinforced_sand = new BlockBase(Material.ROCK, "reinforced_sand").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(400.0F);
	public static final Block reinforced_lamp_off = new ReinforcedLamp(Material.ROCK, false, "reinforced_lamp_off").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(300.0F);
	public static final Block reinforced_lamp_on = new ReinforcedLamp(Material.ROCK, true, "reinforced_lamp_on").setCreativeTab(null).setHardness(15.0F).setResistance(300.0F);
	public static final Block reinforced_stone = new BlockBase(Material.ROCK, "reinforced_stone").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(3000.0F);
	public static final Block concrete_smooth = new BlockBase(Material.ROCK, "concrete_smooth").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(4000.0F);
	public static final Block concrete = new BlockBase(Material.ROCK, "concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(4000.0F);
	public static final Block concrete_pillar = new BlockRotatablePillar(Material.ROCK, "concrete_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(4000.0F);
	
	//Ores
	public static final Block ore_uranium = new BlockBase(Material.ROCK, "ore_uranium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block ore_uranium_scorched = new BlockBase(Material.ROCK, "ore_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_schrabidium = new BlockOre(Material.ROCK, 0.1F, 0.5F, "ore_schrabidium").setHardness(15.0F).setResistance(600.0F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block ore_nether_coal = new BlockCoalBurning(Material.ROCK, "ore_nether_coal").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_smoldering = new BlockSmolder(Material.ROCK, "ore_nether_smoldering").setCreativeTab(MainRegistry.blockTab).setLightLevel(1F).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_uranium = new BlockOre(Material.ROCK, "ore_nether_uranium").setHardness(0.4F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block ore_nether_uranium_scorched = new BlockOre(Material.ROCK, "ore_nether_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
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
	public static final Block ore_asbestos = new BlockOre(Material.ROCK, "ore_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block ore_rare = new BlockOre(Material.ROCK, "ore_rare").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block ore_nether_plutonium = new BlockBase(Material.ROCK, "ore_nether_plutonium").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_tungsten = new BlockBase(Material.ROCK, "ore_nether_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_sulfur = new BlockOre(Material.ROCK, "ore_nether_sulfur").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_nether_fire = new BlockOre(Material.ROCK, "ore_nether_fire").setCreativeTab(MainRegistry.blockTab).setHardness(0.4F).setResistance(10.0F);
	public static final Block ore_coal_oil = new BlockCoalOil(Material.ROCK, "ore_coal_oil").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block ore_coal_oil_burning = new BlockCoalBurning(Material.ROCK, "ore_coal_oil_burning").setCreativeTab(MainRegistry.blockTab).setLightLevel(10F/15F).setHardness(5.0F).setResistance(15.0F);
	
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
	
	public static final Block stone_gneiss = new BlockBase(Material.ROCK, "stone_gneiss").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_iron = new BlockOre(Material.ROCK, "ore_gneiss_iron").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_gold = new BlockOre(Material.ROCK, "ore_gneiss_gold").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_uranium = new BlockOre(Material.ROCK, "ore_gneiss_uranium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_uranium_scorched = new BlockOre(Material.ROCK, "ore_gneiss_uranium_scorched").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_copper = new BlockOre(Material.ROCK, "ore_gneiss_copper").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_asbestos = new BlockOre(Material.ROCK, "ore_gneiss_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_lithium = new BlockOre(Material.ROCK, "ore_gneiss_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_schrabidium = new BlockOre(Material.ROCK, "ore_gneiss_schrabidium").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_rare = new BlockOre(Material.ROCK, "ore_gneiss_rare").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	public static final Block ore_gneiss_gas = new BlockOre(Material.ROCK, "ore_gneiss_gas").setCreativeTab(MainRegistry.blockTab).setHardness(1.5F).setResistance(10.0F);
	
	public static final Block ore_tikite = new BlockBase(Material.ROCK, "ore_tikite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block block_meteor = new BlockOre(Material.ROCK, "block_meteor").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_cobble = new BlockOre(Material.ROCK, "block_meteor_cobble").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_broken = new BlockOre(Material.ROCK, "block_meteor_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_molten = new BlockOre(Material.ROCK, true, "block_meteor_molten").setLightLevel(0.75F).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block block_meteor_treasure = new BlockOre(Material.ROCK, "block_meteor_treasure").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_polished = new BlockBase(Material.ROCK, "meteor_polished").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_brick = new BlockBase(Material.ROCK, "meteor_brick").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_brick_mossy = new BlockBase(Material.ROCK, "meteor_brick_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_brick_cracked = new BlockBase(Material.ROCK, "meteor_brick_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_brick_chiseled = new BlockBase(Material.ROCK, "meteor_brick_chiseled").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_pillar = new BlockRotatablePillar(Material.ROCK, "meteor_pillar").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_spawner = new BlockCybercrab(Material.ROCK, "meteor_spawner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block meteor_battery = new BlockBase(Material.ROCK, "meteor_battery").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	
	public static final Block brick_jungle = new BlockBase(Material.ROCK, "brick_jungle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_jungle_cracked = new BlockBase(Material.ROCK, "brick_jungle_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_jungle_lava = new BlockBase(Material.ROCK, "brick_jungle_lava").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setLightLevel(5F/15F);
	public static final Block brick_jungle_ooze = new BlockOre(Material.ROCK, "brick_jungle_ooze").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setLightLevel(5F/15F);
	public static final Block brick_jungle_mystic = new BlockOre(Material.ROCK, "brick_jungle_mystic").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F).setLightLevel(5F/15F);
	public static final Block brick_jungle_trap = new TrappedBrick(Material.ROCK, "brick_jungle_trap").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_jungle_glyph = new BlockGlyph(Material.ROCK, "brick_jungle_glyph").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_jungle_circle = new BlockBallsSpawner(Material.ROCK, "brick_jungle_circle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	
	public static final Block brick_dungeon = new BlockBase(Material.ROCK, "brick_dungeon").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_dungeon_flat = new BlockBase(Material.ROCK, "brick_dungeon_flat").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_dungeon_tile = new BlockBase(Material.ROCK, "brick_dungeon_tile").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	public static final Block brick_dungeon_circle = new BlockBase(Material.ROCK, "brick_dungeon_circle").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(900.0F);
	
	//Material Blocks
	public static final Block block_niter = new BlockBase(Material.IRON, "block_niter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_sulfur = new BlockBase(Material.IRON, "block_sulfur").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_thorium = new BlockBase(Material.IRON, "block_thorium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_thorium_fuel = new BlockBase(Material.IRON, "block_thorium_fuel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_neptunium = new BlockOre(Material.IRON, 10F, 100F, "block_neptunium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_polonium = new BlockOre(Material.IRON, 30F, 300F, "block_polonium").setSoundType(SoundType.METAL).setBlockUnbreakable().setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_mox_fuel = new BlockOre(Material.IRON, 15F, 150F, "block_mox_fuel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_plutonium = new BlockOre(Material.IRON, 15F, 150F, "block_plutonium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_pu238 = new BlockOre(Material.IRON, 0.1F, 1.5F, "block_pu238").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_pu239 = new BlockOre(Material.IRON, 15F, 150F, "block_pu239").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_pu240 = new BlockOre(Material.IRON, 10F, 100F, "block_pu240").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_plutonium_fuel = new BlockOre(Material.IRON, 5F, 50F, "block_plutonium_fuel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_uranium = new BlockOre(Material.IRON, 0.1F, 1.5F, "block_uranium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_u233 = new BlockOre(Material.IRON, 10F, 100F, "block_u233").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_u235 = new BlockOre(Material.IRON, 10F, 100F, "block_u235").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_u238 = new BlockOre(Material.IRON, 0.1F, 1.5F, "block_u238").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_uranium_fuel = new BlockOre(Material.IRON, 2.5F, 50F, "block_uranium_fuel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_titanium = new BlockBase(Material.IRON, "block_titanium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_copper = new BlockBase(Material.IRON, "block_copper").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_red_copper = new BlockBase(Material.IRON, "block_red_copper").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_advanced_alloy = new BlockBase(Material.IRON, "block_advanced_alloy").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_tungsten = new BlockBase(Material.IRON, "block_tungsten").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_aluminium = new BlockBase(Material.IRON, "block_aluminium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_fluorite = new BlockBase(Material.IRON, "block_fluorite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_steel = new BlockBase(Material.IRON, "block_steel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_lead = new BlockBase(Material.IRON, "block_lead").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_trinitite = new BlockOre(Material.IRON, 3F, 35F, "block_trinitite").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_beryllium = new BlockBase(Material.IRON, "block_beryllium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_schraranium = new BlockOre(Material.IRON, 5F, 50F, "block_schraranium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_schrabidium = new BlockOre(Material.IRON, 20F, 250F, "block_schrabidium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_schrabidate = new BlockOre(Material.IRON, 5F, 50F, "block_schrabidate").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_solinium = new BlockBase(Material.IRON, "block_solinium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_schrabidium_fuel = new BlockOre(Material.IRON, 20F, 250F, "block_schrabidium_fuel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_euphemium = new BlockBase(Material.IRON, "block_euphemium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F);
	public static final Block block_dineutronium = new BlockBase(Material.IRON, "block_dineutronium").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F);
	public static final Block block_schrabidium_cluster = new BlockRotatablePillar(Material.ROCK, "block_schrabidium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F);
	public static final Block block_euphemium_cluster = new BlockRotatablePillar(Material.ROCK, "block_euphemium_cluster").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(60000.0F);
	public static final Block block_combine_steel = new BlockBase(Material.IRON, "block_combine_steel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_magnetized_tungsten = new BlockBase(Material.IRON, "block_magnetized_tungsten").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(35.0F);
	public static final Block block_desh = new BlockBase(Material.IRON, "block_desh").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_dura_steel = new BlockBase(Material.IRON, "block_dura_steel").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block gravel_obsidian = new BlockFallingBase(Material.IRON, "gravel_obsidian", SoundType.GROUND).setHardness(5.0F).setResistance(600F).setCreativeTab(MainRegistry.blockTab);
	public static final Block gravel_diamond = new BlockFallingBase(Material.SAND, "gravel_diamond", SoundType.GROUND).setCreativeTab(MainRegistry.blockTab).setHardness(0.6F);
	
	public static final Block brick_concrete = new BlockRadResistant(Material.ROCK, "brick_concrete").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(6000.0F);
	public static final Block brick_concrete_mossy = new BlockBase(Material.ROCK, "brick_concrete_mossy").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(6000.0F);
	public static final Block brick_concrete_cracked = new BlockBase(Material.ROCK, "brick_concrete_cracked").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(2000.0F);
	public static final Block brick_concrete_broken = new BlockBase(Material.ROCK, "brick_concrete_broken").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(1500.0F);
	public static final Block brick_light = new BlockBase(Material.ROCK, "brick_light").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(1000.0F);
	public static final Block brick_compound = new BlockBase(Material.ROCK, "brick_compound").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(10000.0F);
	public static final Block cmb_brick = new BlockBase(Material.ROCK, "cmb_brick").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(6000.0F);
	public static final Block cmb_brick_reinforced = new BlockBase(Material.ROCK, "cmb_brick_reinforced").setCreativeTab(MainRegistry.blockTab).setHardness(25.0F).setResistance(60000.0F);
	public static final Block brick_asbestos = new BlockBase(Material.ROCK, "brick_asbestos").setCreativeTab(MainRegistry.blockTab).setResistance(1000.0F);
	public static final Block brick_obsidian = new BlockBase(Material.ROCK, "brick_obsidian").setCreativeTab(MainRegistry.blockTab).setLightOpacity(15).setHardness(15.0F).setResistance(8000.0F);
	public static final Block block_scrap = new BlockFallingBase(Material.SAND, "block_scrap", SoundType.GROUND).setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F);
	public static final Block block_electrical_scrap = new BlockFallingBase(Material.IRON, "block_electrical_scrap", SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(2.5F).setResistance(5.0F);
	
	public static final Block tile_lab = new BlockBase(Material.ROCK, "tile_lab").setSoundType(SoundType.GLASS).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F);
	public static final Block tile_lab_cracked = new BlockBase(Material.ROCK, "tile_lab_cracked").setSoundType(SoundType.GLASS).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F);
	public static final Block tile_lab_broken = new BlockBase(Material.ROCK, "tile_lab_broken").setSoundType(SoundType.GLASS).setCreativeTab(MainRegistry.blockTab).setHardness(1.0F).setResistance(20.0F);
	
	//Deco blocks
	public static final Block deco_titanium = new BlockOre(Material.IRON, "deco_titanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_red_copper = new BlockOre(Material.IRON, "deco_red_copper").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_tungsten = new BlockOre(Material.IRON, "deco_tungsten").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_aluminium = new BlockOre(Material.IRON, "deco_aluminium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_steel = new BlockOre(Material.IRON, "deco_steel").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_lead = new BlockOre(Material.IRON, "deco_lead").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_beryllium = new BlockOre(Material.IRON, "deco_beryllium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block deco_asbestos = new BlockOre(Material.IRON, "deco_asbestos").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block hazmat = new BlockBase(Material.CLOTH, "hazmat").setSoundType(SoundType.CLOTH).setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(100.0F);
	
	public static final Block tape_recorder = new DecoTapeRecorder(Material.ROCK, "tape_recorder").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	//Drillgon200: Thank god there was an obj file for this.
	public static final Block steel_poles = new DecoSteelPoles(Material.ROCK, "steel_poles").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block pole_top = new DecoPoleTop(Material.ROCK, "pole_top").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block pole_satellite_receiver = new DecoPoleSatelliteReceiver(Material.ROCK, "pole_satellite_receiver").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block steel_wall = new DecoBlock(Material.ROCK, "steel_wall").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block steel_corner = new DecoBlock(Material.ROCK, "steel_corner").setCreativeTab(MainRegistry.blockTab).setHardness(15.0F).setResistance(15.0F);
	public static final Block steel_roof = new DecoBlock(Material.ROCK, "steel_roof").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block steel_beam = new DecoBlock(Material.ROCK, "steel_beam").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block steel_scaffold = new DecoBlock(Material.ROCK, "steel_scaffold").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(15.0F);
	
	//Radiation blocks
	public static final Block mush = new BlockMush(Material.PLANTS, "mush").setLightLevel(0.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block = new BlockMushHuge(Material.PLANTS, "mush_block").setLightLevel(1.0F).setHardness(0.2F).setCreativeTab(MainRegistry.blockTab);
	public static final Block mush_block_stem = new BlockMushHuge(Material.PLANTS, "mush_block_stem").setLightLevel(1.0F).setHardness(0.3F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block block_waste = new BlockOre(Material.IRON, 5F, 60F, "block_waste").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_waste_painted = new BlockOre(Material.IRON, 5F, 60F, "block_waste_painted").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block waste_earth = new WasteEarth(Material.GROUND, true, 0.25F, 7.5F, "waste_earth").setSoundType(SoundType.GROUND).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_mycelium = new WasteEarth(Material.GROUND, true, 1.0F, 25.0F, "waste_mycelium").setSoundType(SoundType.GROUND).setLightLevel(1.0F).setHardness(0.5F).setResistance(1.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite = new BlockOre(Material.SAND, "waste_trinitite").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_trinitite_red = new BlockOre(Material.SAND, "waste_trinitite_red").setSoundType(SoundType.SAND).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_log = new WasteLog(Material.WOOD, "waste_log").setSoundType(SoundType.WOOD).setHardness(5.0F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	public static final Block waste_planks = new BlockOre(Material.WOOD, "waste_planks").setSoundType(SoundType.WOOD).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block frozen_grass = new WasteEarth(Material.GROUND, false, "frozen_grass").setSoundType(SoundType.GLASS).setHardness(0.5F).setResistance(2.5F).setCreativeTab(MainRegistry.blockTab);
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
	
	public static final Block geysir_water = new BlockGeysir(Material.ROCK, "geysir_water").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block geysir_chlorine = new BlockGeysir(Material.ROCK, "geysir_chlorine").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block geysir_vapor = new BlockGeysir(Material.ROCK, "geysir_vapor").setSoundType(SoundType.STONE).setHardness(5.0F);
	public static final Block geysir_nether = new BlockGeysir(Material.ROCK, "geysir_nether").setSoundType(SoundType.STONE).setLightLevel(1.0F).setHardness(2.0F);
	
	public static final Block block_yellowcake = new BlockFallingRad(Material.SAND, 0.5F, 3F, "block_yellowcake").setSoundType(SoundType.SAND).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_starmetal = new BlockBase(Material.IRON, "block_starmetal").setSoundType(SoundType.METAL).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(600.0F);
	public static final Block block_asbestos = new BlockBase(Material.CLOTH, "block_asbestos").setSoundType(SoundType.CLOTH).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_cobalt = new BlockBase(Material.IRON, "block_cobalt").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_lithium = new BlockLithium(Material.IRON, "block_lithium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_insulator = new BlockRotatablePillar(Material.CLOTH, "block_insulator").setSoundType(SoundType.CLOTH).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_fiberglass = new BlockRotatablePillar(Material.CLOTH, "block_fiberglass").setSoundType(SoundType.CLOTH).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_white_phosphorus = new BlockBase(Material.ROCK, "block_white_phosphorus").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_red_phosphorus = new BlockFallingBase(Material.SAND, "block_red_phosphorus", SoundType.SAND).setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block block_australium = new BlockBase(Material.IRON, "block_australium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_weidanium = new BlockBase(Material.IRON, "block_weidanium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_reiium = new BlockBase(Material.IRON, "block_reiium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_unobtainium = new BlockBase(Material.IRON, "block_unobtainium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_daffergon = new BlockBase(Material.IRON, "block_daffergon").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	public static final Block block_verticium = new BlockBase(Material.IRON, "block_verticium").setCreativeTab(MainRegistry.blockTab).setHardness(5.0F).setResistance(10.0F);
	
	public static final Block block_cap_nuka = new BlockCap(Material.IRON, "block_cap_nuka").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_quantum = new BlockCap(Material.IRON, "block_cap_quantum").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_rad = new BlockCap(Material.IRON, "block_cap_rad").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_sparkle = new BlockCap(Material.IRON, "block_cap_sparkle").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_korl = new BlockCap(Material.IRON, "block_cap_korl").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_fritz = new BlockCap(Material.IRON, "block_cap_fritz").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_sunset = new BlockCap(Material.IRON, "block_cap_sunset").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block block_cap_star = new BlockCap(Material.IRON, "block_cap_star").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	
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
	
	public static final int guiID_nuke_fstbmb = 97;
	public static final Block nuke_fstbmb = new NukeBalefire(Material.IRON, guiID_nuke_fstbmb, "nuke_fstbmb").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	
	public static final Block nuke_custom = new NukeCustom(Material.IRON, "nuke_custom").setCreativeTab(MainRegistry.nukeTab).setHardness(5.0F).setResistance(6000.0F);
	public static final int guiID_nuke_custom = 37;
	
	public static final Block bomb_multi = new BombMulti(Material.IRON, "bomb_multi").setCreativeTab(MainRegistry.nukeTab).setResistance(6000.0F);
	public static final int guiID_bomb_multi = 10;
	
	public static final Block crashed_balefire = new BlockCrashedBomb(Material.IRON, "crashed_bomb").setCreativeTab(MainRegistry.nukeTab).setBlockUnbreakable().setResistance(6000.0F);
	public static final Block fireworks = new BlockFireworks(Material.IRON, "fireworks").setCreativeTab(MainRegistry.nukeTab).setResistance(5.0F);
	
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
	public static final Block det_miner = new DetMiner(Material.IRON, "det_miner").setCreativeTab(MainRegistry.nukeTab).setHardness(0.1F).setResistance(0.0F);
	public static final Block red_barrel = new RedBarrel(Material.IRON, "red_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block pink_barrel = new RedBarrel(Material.IRON, "pink_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block yellow_barrel = new YellowBarrel(Material.IRON, "yellow_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block vitrified_barrel = new YellowBarrel(Material.IRON, "vitrified_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block lox_barrel = new RedBarrel(Material.IRON, "lox_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	public static final Block taint_barrel = new RedBarrel(Material.IRON, "taint_barrel").setCreativeTab(MainRegistry.nukeTab).setHardness(0.5F).setResistance(2.5F);
	
	public static final Block barrel_plastic = new BlockFluidBarrel(Material.IRON, 12000, "barrel_plastic").setSoundType(SoundType.STONE).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block barrel_corroded = new BlockFluidBarrel(Material.IRON, 6000, "barrel_corroded").setSoundType(SoundType.METAL).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block barrel_iron = new BlockFluidBarrel(Material.IRON, 8000, "barrel_iron").setSoundType(SoundType.METAL).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block barrel_steel = new BlockFluidBarrel(Material.IRON, 16000, "barrel_steel").setSoundType(SoundType.METAL).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block barrel_antimatter = new BlockFluidBarrel(Material.IRON, 16000, "barrel_antimatter").setSoundType(SoundType.METAL).setHardness(2.0F).setResistance(5.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_barrel = 92;
	
	//Turrets
	public static final Block turret_light = new TurretLight(Material.IRON, "turret_light").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_heavy = new TurretHeavy(Material.IRON, "turret_heavy").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_rocket = new TurretRocket(Material.IRON, "turret_rocket").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_flamer = new TurretFlamer(Material.IRON, "turret_flamer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_tau = new TurretTau(Material.IRON, "turret_tau").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_spitfire = new TurretSpitfire(Material.IRON, "turret_spitfire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_cwis = new TurretCIWS(Material.IRON, "turret_cwis").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	public static final Block turret_cheapo = new TurretCheapo(Material.IRON, "turret_cheapo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.weaponTab);
	
	//Rails
	public static final Block rail_highspeed = new RailHighspeed("rail_highspeed").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.TRANSPORTATION);
	public static final Block rail_booster = new RailBooster("rail_booster").setHardness(5.0F).setResistance(10.0F).setCreativeTab(CreativeTabs.TRANSPORTATION);
	
	//Machines
	public static final Block machine_siren = new MachineSiren(Material.IRON, "machine_siren").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_siren = 57;
	
	public static final Block broadcaster_pc = new PinkCloudBroadcaster(Material.ROCK, "broadcaster_pc").setCreativeTab(MainRegistry.machineTab).setHardness(5.0F).setResistance(15.0F);
	public static final Block geiger = new GeigerCounter(Material.ROCK, "geiger").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F);
	
	public static final Block fence_metal = new BlockMetalFence(Material.ROCK, MapColor.GRAY, "fence_metal").setCreativeTab(MainRegistry.machineTab).setHardness(15.0F).setResistance(0.25F);
	
	public static final Block sand_uranium = new BlockFallingBase(Material.SAND, "sand_uranium", SoundType.SAND).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F);
	public static final Block sand_polonium = new BlockFallingBase(Material.SAND, "sand_polonium", SoundType.SAND).setCreativeTab(MainRegistry.machineTab).setHardness(0.5F);
	public static final Block glass_uranium = new BlockNTMGlass(Material.GLASS, BlockRenderLayer.TRANSLUCENT, "glass_uranium").setSoundType(SoundType.GLASS).setLightLevel(5F/15F).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
	public static final Block glass_trinitite = new BlockNTMGlass(Material.GLASS, BlockRenderLayer.TRANSLUCENT, "glass_trinitite").setSoundType(SoundType.GLASS).setLightLevel(5F/15F).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
	public static final Block glass_polonium = new BlockNTMGlass(Material.GLASS, BlockRenderLayer.TRANSLUCENT, "glass_polonium").setSoundType(SoundType.GLASS).setLightLevel(5F/15F).setCreativeTab(MainRegistry.machineTab).setHardness(0.3F);
	
	public static final Block seal_frame = new BlockBase(Material.IRON, "seal_frame").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block seal_controller = new BlockSeal(Material.IRON, "seal_controller").setHardness(10.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block seal_hatch = new BlockHatch(Material.IRON, "seal_hatch").setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY).setCreativeTab(null);
	
	public static final Block vault_door = new VaultDoor(Material.IRON, "vault_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block blast_door = new BlastDoor(Material.IRON, "blast_door").setHardness(10.0F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block sliding_blast_door = new BlockSlidingBlastDoor(Material.IRON, "sliding_blast_door").setHardness(15.0F).setResistance(7500.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block sliding_blast_door_2 = new BlockSlidingBlastDoor(Material.IRON, "sliding_blast_door_2").setHardness(15.0F).setResistance(7500.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block sliding_blast_door_keypad = new BlockSlidingBlastDoor(Material.IRON, "sliding_blast_door_keypad").setHardness(15.0F).setResistance(7500.0F).setCreativeTab(null);
	
	public static final Block keypad_test = new KeypadTest(Material.IRON, "keypad_test").setHardness(15.0F).setResistance(7500.0F).setCreativeTab(null);
	
	public static final Block door_metal = new BlockModDoor(Material.IRON, "door_metal").setHardness(5.0F).setResistance(5.0F);
	public static final Block door_office = new BlockModDoor(Material.IRON, "door_office").setHardness(10.0F).setResistance(10.0F);
	public static final Block door_bunker = new BlockModDoor(Material.IRON, "door_bunker").setHardness(10.0F).setResistance(100.0F);
	
	public static final Block barbed_wire = new BarbedWire(Material.IRON, "barbed_wire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block barbed_wire_fire = new BarbedWire(Material.IRON, "barbed_wire_fire").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block barbed_wire_poison = new BarbedWire(Material.IRON, "barbed_wire_poison").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block barbed_wire_acid = new BarbedWire(Material.IRON, "barbed_wire_acid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block barbed_wire_wither = new BarbedWire(Material.IRON, "barbed_wire_wither").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block barbed_wire_ultradeath = new BarbedWire(Material.IRON, "barbed_wire_ultradeath").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block spikes = new Spikes(Material.IRON, "spikes").setHardness(2.5F).setResistance(5.0F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block tesla = new MachineTesla(Material.IRON, "tesla").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	
	public static final Block crate = new BlockCrate(Material.IRON, "crate").setSoundType(SoundType.WOOD).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_weapon = new BlockCrate(Material.IRON, "crate_weapon").setSoundType(SoundType.WOOD).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_lead = new BlockCrate(Material.IRON, "crate_lead").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_metal = new BlockCrate(Material.IRON, "crate_metal").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_red = new BlockCrate(Material.IRON, "crate_red").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block crate_iron = new BlockStorageCrate(Material.IRON, "crate_iron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block crate_steel = new BlockStorageCrate(Material.IRON, "crate_steel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block crate_can = new BlockCanCrate(Material.WOOD, "crate_can").setSoundType(SoundType.WOOD).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_jungle = new BlockJungleCrate(Material.ROCK, "crate_jungle").setSoundType(SoundType.STONE).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block crate_ammo = new BlockAmmoCrate(Material.IRON, "crate_ammo").setSoundType(SoundType.METAL).setHardness(1.0F).setResistance(2.5F).setCreativeTab(MainRegistry.consumableTab);
	public static final Block safe = new BlockStorageCrate(Material.IRON, "safe").setHardness(7.5F).setResistance(10000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_crate_iron = 46;
	public static final int guiID_crate_steel = 47;
	public static final int guiID_safe = 70;
	
	public static final Block machine_keyforge = new MachineKeyForge(Material.IRON, "machine_keyforge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.consumableTab);
	public static final int guiID_keyforge = 67;
	
	public static final Block machine_solar_boiler = new MachineSolarBoiler(Material.IRON, "machine_solar_boiler").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block solar_mirror = new SolarMirror(Material.IRON, "solar_mirror").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_solar_boiler = 104;
	
	public static final Block machine_telelinker = new MachineTeleLinker(Material.IRON, "machine_telelinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.nukeTab);
	public static final int guiID_telelinker = 68;
	
	public static final Block machine_satlinker = new MachineSatLinker(Material.IRON, "machine_satlinker").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_satlinker = 64;
	
	public static final Block sat_dock = new MachineSatDock(Material.IRON, "sat_dock").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block soyuz_capsule = new SoyuzCapsule(Material.IRON, "soyuz_capsule").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_dock = 80;
	public static final int guiID_capsule = 93;
	
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
	public static final Block machine_fensu = new MachineFENSU(Material.IRON, "machine_fensu").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
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
	
	public static final Block machine_diesel = new MachineDiesel(Material.IRON, "machine_diesel").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_diesel = 31;
	
	public static final Block machine_industrial_generator = new MachineIGenerator(Material.IRON, "machine_industrial_generator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_industrial_generator = 39;
	
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
	public static final Block machine_large_turbine = new MachineLargeTurbine(Material.IRON, "machine_large_turbine").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_large_turbine = 102;
	
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
	
	public static final Block machine_reactor = new MachineReactor(Material.IRON, "machine_reactor").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_reactor_on = new MachineReactor(Material.IRON, "machine_reactor_on").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final int guiID_reactor = 9;
	
	public static final Block launch_pad = new LaunchPad(Material.IRON, "launch_pad").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_launch_pad = 19;
	
	public static final Block machine_centrifuge = new MachineCentrifuge(Material.IRON, "machine_centrifuge").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_centrifuge = 5;
	
	public static final Block machine_gascent = new MachineGasCent(Material.IRON, "machine_gascent").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_gascent = 71;
	
	public static final Block machine_crystallizer = new MachineCrystallizer(Material.IRON, "machine_crystallizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_crystallizer = 95;
	
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
	
	public static final Block machine_mining_laser = new MachineMiningLaser(Material.IRON, "machine_mining_laser").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block barricade = new BlockNoDrop(Material.SAND, "barricade").setHardness(1.0F).setResistance(2.5F).setCreativeTab(null);
	public static final int guiID_mining_laser = 96;
	
	public static final Block machine_turbofan = new MachineTurbofan(Material.IRON, "machine_turbofan").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_turbofan = 52;
	
	public static final Block machine_schrabidium_transmutator = new MachineSchrabidiumTransmutator(Material.IRON, "machine_schrabidium_transmutator").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_schrabidium_transmutator = 30;
	
	public static final Block machine_combine_factory = new MachineCMBFactory(Material.IRON, "machine_combine_factory").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_combine_factory = 35;
	
	public static final Block machine_teleporter = new MachineTeleporter(Material.IRON, "machine_teleporter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_teleporter = 36;
	
	public static final Block machine_forcefield = new MachineForceField(Material.IRON, "machine_forcefield").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_forcefield = 75;
	
	public static final Block machine_radar = new MachineRadar(Material.IRON, "machine_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_radar = 59;
	
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
	
	public static final int guiID_microwave = 98;
	public static final Block machine_microwave = new MachineMicrowave(Material.IRON, "machine_microwave").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block machine_cyclotron = new MachineCyclotron(Material.IRON, "machine_cyclotron").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_machine_cyclotron = 41;
	
	public static final Block machine_radgen = new MachineRadGen(Material.IRON, "machine_radgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_radgen = 58;
	
	public static final Block machine_amgen = new MachineAmgen(Material.IRON, "machine_amgen").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_geo = new MachineAmgen(Material.IRON, "machine_geo").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_minirtg = new MachineMiniRTG(Material.IRON, "machine_minirtg").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_powerrtg = new MachineMiniRTG(Material.IRON, "rtg_polonium").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block machine_spp_bottom = new SPPBottom(Material.IRON, "machine_spp_bottom").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_spp_top = new SPPTop(Material.IRON, "machine_spp_top").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block marker_structure = new BlockMarker(Material.IRON, "marker_structure").setHardness(0.0F).setResistance(0.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block muffler = new BlockBase(Material.CLOTH, "muffler").setSoundType(SoundType.CLOTH).setHardness(0.8F).setCreativeTab(MainRegistry.machineTab);
	
	public static final Block struct_launcher = new BlockBase(Material.IRON, "struct_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block struct_scaffold = new BlockBase(Material.IRON, "struct_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block struct_launcher_core = new BlockStruct(Material.IRON, "struct_launcher_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block struct_launcher_core_large = new BlockStruct(Material.IRON, "struct_launcher_core_large").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block struct_soyuz_core = new BlockSoyuzStruct(Material.IRON, "struct_soyuz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block struct_iter_core = new BlockITERStruct(Material.IRON, "struct_iter_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block struct_plasma_core = new BlockPlasmaStruct(Material.IRON, "struct_plasma_core").setLightLevel(1F).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	
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
	public static final Block iter = new MachineITER("iter").setHardness(5.0F).setResistance(6000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_iter = 100;
	public static final Block plasma_heater = new MachinePlasmaHeater("plasma_heater").setHardness(5.0F).setResistance(6000.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_plasma_heater = 101;
	
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
	public static final Block fwatz_tank = new BlockNTMGlass(Material.IRON, BlockRenderLayer.CUTOUT, "fwatz_tank").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_scaffold = new BlockBase(Material.IRON, "fwatz_scaffold").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_hatch = new FWatzHatch(Material.IRON, "fwatz_hatch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_computer = new BlockBase(Material.IRON, "fwatz_computer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_core = new FWatzCore(Material.IRON, "fwatz_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fwatz_plasma = new BlockPlasma(Material.IRON, "fwatz_plasma").setHardness(5.0F).setResistance(6000.0F).setLightLevel(1.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_fwatz_multiblock = 33;
	
	//Drillgon200: AMS won't be removed after all
	public static final Block ams_base = new BlockAMSBase(Material.IRON, "ams_base").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block ams_emitter = new BlockAMSEmitter(Material.IRON, "ams_emitter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block ams_limiter = new BlockAMSLimiter(Material.IRON, "ams_limiter").setHardness(5.0F).setResistance(100.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_ams_base = 54;
	public static final int guiID_ams_emitter = 55;
	public static final int guiID_ams_limiter = 56;
	
	public static final Block dfc_emitter = new CoreComponent(Material.IRON, "dfc_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block dfc_injector = new CoreComponent(Material.IRON, "dfc_injector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block dfc_receiver = new CoreComponent(Material.IRON, "dfc_receiver").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block dfc_stabilizer = new CoreComponent(Material.IRON, "dfc_stabilizer").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block dfc_core = new CoreCore(Material.IRON, "dfc_core").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_dfc_emitter = 87;
	public static final int guiID_dfc_injector = 90;
	public static final int guiID_dfc_receiver = 88;
	public static final int guiID_dfc_stabilizer = 91;
	public static final int guiID_dfc_core = 89;
	
	public static final Block hadron_plating = new BlockHadronPlating(Material.IRON, "hadron_plating").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_blue = new BlockHadronPlating(Material.IRON, "hadron_plating_blue").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_black = new BlockHadronPlating(Material.IRON, "hadron_plating_black").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_yellow = new BlockHadronPlating(Material.IRON, "hadron_plating_yellow").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_striped = new BlockHadronPlating(Material.IRON, "hadron_plating_striped").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_voltz = new BlockHadronPlating(Material.IRON, "hadron_plating_voltz").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_plating_glass = new BlockNTMGlass(Material.IRON, BlockRenderLayer.CUTOUT, "hadron_plating_glass").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_coil_alloy = new BlockHadronCoil(Material.IRON, 1, "hadron_coil_alloy").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_coil_schrabidium = new BlockHadronCoil(Material.IRON, 3, "hadron_coil_schrabidium").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_coil_starmetal = new BlockHadronCoil(Material.IRON, 10, "hadron_coil_starmetal").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_coil_mese = new BlockHadronCoil(Material.IRON, 25, "hadron_coil_mese").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_diode = new BlockHadronDiode(Material.IRON, "hadron_diode").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_analysis = new BlockHadronPlating(Material.IRON, "hadron_analysis").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_analysis_glass = new BlockNTMGlass(Material.IRON, BlockRenderLayer.CUTOUT, "hadron_analysis_glass").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_access = new BlockHadronAccess(Material.IRON, "hadron_access").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_core = new BlockHadronCore(Material.IRON, "hadron_core").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block hadron_power = new BlockHadronPower(Material.IRON, "hadron_power").setSoundType(SoundType.METAL).setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final int guiID_hadron = 103;
	
	public static final Block machine_missile_assembly = new MachineMissileAssembly(Material.IRON, "machine_missile_assembly").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block compact_launcher = new CompactLauncher(Material.IRON, "compact_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block launch_table = new LaunchTable(Material.IRON, "launch_table").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final Block soyuz_launcher = new SoyuzLauncher(Material.IRON, "soyuz_launcher").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.missileTab);
	public static final int guiID_missile_assembly = 83;
	public static final int guiID_compact_launcher = 85;
	public static final int guiID_launch_table = 84;
	public static final int guiID_soyuz_launcher = 86;
	
	public static final Block sat_mapper = new DecoBlock(Material.IRON, "deco_sat_mapper").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sat_radar = new DecoBlock(Material.IRON, "deco_sat_radar").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sat_scanner = new DecoBlock(Material.IRON, "deco_sat_scanner").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sat_laser = new DecoBlock(Material.IRON, "deco_sat_laser").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sat_foeq = new DecoBlock(Material.IRON, "deco_sat_foeq").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block sat_resonator = new DecoBlock(Material.IRON, "deco_sat_resonator").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	
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
	public static final int guiID_railgun = 99;
	
	public static final Block book_crafting = new BlockBlackBook(Material.WOOD, "book_crafting").setHardness(2.0F).setResistance(2.0F).setCreativeTab(null);
	
	//Cables
	public static final Block red_cable = new BlockCable(Material.IRON, "red_cable").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block red_wire_coated = new WireCoated(Material.IRON, "red_wire_coated").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block cable_switch = new CableSwitch(Material.IRON, "cable_switch").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block machine_detector = new PowerDetector(Material.IRON, "machine_detector").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	//Drillgon200: Removed, by order of lord Bob.
	//public static final Block oil_duct_solid = new OilDuctSolid(Material.IRON, "oil_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	//public static final Block oil_duct = new BlockOilDuct(Material.IRON, "oil_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	//public static final Block gas_duct_solid = new GasDuctSolid(Material.IRON, "gas_duct_solid").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	//public static final Block gas_duct = new BlockGasDuct(Material.IRON, "gas_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fluid_duct = new BlockFluidDuct(Material.IRON, "fluid_duct").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block fluid_duct_mk2 = new BlockFluidPipeMk2(Material.IRON, "fluid_duct_mk2").setHardness(5.0F).setResistance(10.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block conveyor = new BlockConveyor(Material.IRON, "conveyor").setHardness(0.0F).setResistance(2.0F).setCreativeTab(MainRegistry.machineTab);
	public static final Block chain = new BlockChain(Material.IRON, "dungeon_chain").setHardness(0.25F).setResistance(2.0F).setCreativeTab(MainRegistry.blockTab);
	
	//Fluids
	public static final Material fluidtoxic = new MaterialLiquid(MapColor.GREEN).setReplaceable();
	public static Block toxic_block;
	
	public static final Material fluidmud = (new MaterialLiquid(MapColor.ADOBE).setReplaceable());
	public static Block mud_block;
	
	public static final Material fluidschrabidic = (new MaterialLiquid(MapColor.CYAN));
	public static Block schrabidic_block;
	
	//Weird stuff
	public static final Block boxcar = new DecoBlock(Material.IRON, "boxcar").setSoundType(SoundType.METAL).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	public static final Block boat = new DecoBlock(Material.IRON, "boat").setSoundType(SoundType.METAL).setHardness(10.0F).setResistance(10.0F).setCreativeTab(MainRegistry.blockTab);
	
	//Drillgon200: Can't name with # symbol because json doesn't like it.
	public static final Block statue_elb = new DecoBlockAlt(Material.IRON, "null").setCreativeTab(null).setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
	public static final Block statue_elb_g = new DecoBlockAlt(Material.IRON, "void").setCreativeTab(null).setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
	public static final Block statue_elb_w = new DecoBlockAlt(Material.IRON, "ngtv").setCreativeTab(null).setHardness(Float.POSITIVE_INFINITY).setResistance(Float.POSITIVE_INFINITY);
	public static final Block statue_elb_f = new DecoBlockAlt(Material.IRON, "undef").setCreativeTab(null).setHardness(Float.POSITIVE_INFINITY).setLightLevel(1.0F).setResistance(Float.POSITIVE_INFINITY);
	
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
	
	public static final Block dummy_plate_compact_launcher = new DummyBlockMachine(Material.IRON, "dummy_plate_compact_launcher", false, guiID_compact_launcher, compact_launcher).setBounds(0, 16, 0, 16, 16, 16).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_compact_launcher = new DummyBlockMachine(Material.IRON, "dummy_port_compact_launcher", true, guiID_compact_launcher, compact_launcher).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_plate_launch_table = new DummyBlockMachine(Material.IRON, "dummy_plate_launch_table", false, guiID_launch_table, launch_table).setBounds(0, 16, 0, 16, 16, 16).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_launch_table = new DummyBlockMachine(Material.IRON, "dummy_port_launch_table", true, guiID_launch_table, launch_table).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_block_ams_limiter = new DummyBlockAMSLimiter(Material.IRON, "dummy_block_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_ams_limiter = new DummyBlockAMSLimiter(Material.IRON, "dummy_port_ams_limiter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_block_ams_emitter = new DummyBlockAMSEmitter(Material.IRON, "dummy_block_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_ams_emitter = new DummyBlockAMSEmitter(Material.IRON, "dummy_port_ams_emitter").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_block_ams_base = new DummyBlockAMSBase(Material.IRON, "dummy_block_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	public static final Block dummy_port_ams_base = new DummyBlockAMSBase(Material.IRON, "dummy_port_ams_base").setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block dummy_plate_cargo = new DummyBlockMachine(Material.IRON, "dummy_plate_cargo", false, guiID_dock, sat_dock).setBounds(0, 0, 0, 16, 8, 16).setHardness(5.0F).setResistance(10.0F).setCreativeTab(null);
	
	public static final Block ntm_dirt = new BlockNTMDirt("ntm_dirt").setSoundType(SoundType.GROUND).setHardness(0.5F).setCreativeTab(null);
	
	public static final Block pink_log = new BlockPinkLog("pink_log").setSoundType(SoundType.WOOD).setHardness(0.5F).setCreativeTab(null);
	public static final Block pink_planks = new BlockBase(Material.WOOD, "pink_planks").setSoundType(SoundType.WOOD).setCreativeTab(null);
	public static final Block pink_slab = new BlockPinkSlab(Material.WOOD, false, "pink_slab").setSoundType(SoundType.WOOD).setCreativeTab(null);
	public static final Block pink_double_slab = new BlockPinkSlab(Material.WOOD, true, "pink_double_slab").setSoundType(SoundType.WOOD).setCreativeTab(null);
	public static final Block pink_stairs = new BlockPinkStairs(pink_planks.getDefaultState(), "pink_stairs").setSoundType(SoundType.WOOD).setCreativeTab(null);
	
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
