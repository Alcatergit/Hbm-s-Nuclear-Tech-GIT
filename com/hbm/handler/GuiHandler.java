package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerAMSBase;
import com.hbm.inventory.container.ContainerAMSEmitter;
import com.hbm.inventory.container.ContainerAMSLimiter;
import com.hbm.inventory.container.ContainerBombMulti;
import com.hbm.inventory.container.ContainerCentrifuge;
import com.hbm.inventory.container.ContainerCompactLauncher;
import com.hbm.inventory.container.ContainerConverterHeRf;
import com.hbm.inventory.container.ContainerConverterRfHe;
import com.hbm.inventory.container.ContainerCoreAdvanced;
import com.hbm.inventory.container.ContainerCoreTitanium;
import com.hbm.inventory.container.ContainerCrateIron;
import com.hbm.inventory.container.ContainerCrateSteel;
import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.inventory.container.ContainerElectricFurnace;
import com.hbm.inventory.container.ContainerFWatzCore;
import com.hbm.inventory.container.ContainerFusionMultiblock;
import com.hbm.inventory.container.ContainerLaunchPadTier1;
import com.hbm.inventory.container.ContainerLaunchTable;
import com.hbm.inventory.container.ContainerMachineArcFurnace;
import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.inventory.container.ContainerMachineBoiler;
import com.hbm.inventory.container.ContainerMachineBoilerElectric;
import com.hbm.inventory.container.ContainerMachineCMBFactory;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.inventory.container.ContainerMachineCoal;
import com.hbm.inventory.container.ContainerMachineCyclotron;
import com.hbm.inventory.container.ContainerMachineDiesel;
import com.hbm.inventory.container.ContainerMachineEPress;
import com.hbm.inventory.container.ContainerMachineFluidTank;
import com.hbm.inventory.container.ContainerMachineGasCent;
import com.hbm.inventory.container.ContainerMachineGasFlare;
import com.hbm.inventory.container.ContainerMachineGenerator;
import com.hbm.inventory.container.ContainerMachineKeyForge;
import com.hbm.inventory.container.ContainerMachineMiningDrill;
import com.hbm.inventory.container.ContainerMachineMissileAssembly;
import com.hbm.inventory.container.ContainerMachineOilWell;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.inventory.container.ContainerMachinePumpjack;
import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.inventory.container.ContainerMachineRadGen;
import com.hbm.inventory.container.ContainerMachineReactorSmall;
import com.hbm.inventory.container.ContainerMachineRefinery;
import com.hbm.inventory.container.ContainerMachineSatLinker;
import com.hbm.inventory.container.ContainerMachineSchrabidiumTransmutator;
import com.hbm.inventory.container.ContainerMachineSelenium;
import com.hbm.inventory.container.ContainerMachineShredder;
import com.hbm.inventory.container.ContainerMachineSiren;
import com.hbm.inventory.container.ContainerMachineTeleLinker;
import com.hbm.inventory.container.ContainerMachineTeleporter;
import com.hbm.inventory.container.ContainerMachineTurbine;
import com.hbm.inventory.container.ContainerMachineTurbofan;
import com.hbm.inventory.container.ContainerNukeBoy;
import com.hbm.inventory.container.ContainerNukeCustom;
import com.hbm.inventory.container.ContainerNukeFleija;
import com.hbm.inventory.container.ContainerNukeFurnace;
import com.hbm.inventory.container.ContainerNukeGadget;
import com.hbm.inventory.container.ContainerNukeMan;
import com.hbm.inventory.container.ContainerNukeMike;
import com.hbm.inventory.container.ContainerNukeN2;
import com.hbm.inventory.container.ContainerNukeN45;
import com.hbm.inventory.container.ContainerNukePrototype;
import com.hbm.inventory.container.ContainerNukeSolinium;
import com.hbm.inventory.container.ContainerNukeTsar;
import com.hbm.inventory.container.ContainerPuF6Tank;
import com.hbm.inventory.container.ContainerRailgun;
import com.hbm.inventory.container.ContainerReactor;
import com.hbm.inventory.container.ContainerReactorControl;
import com.hbm.inventory.container.ContainerReactorMultiblock;
import com.hbm.inventory.container.ContainerRtgFurnace;
import com.hbm.inventory.container.ContainerSafe;
import com.hbm.inventory.container.ContainerSatDock;
import com.hbm.inventory.container.ContainerUF6Tank;
import com.hbm.inventory.container.ContainerWasteDrum;
import com.hbm.inventory.container.ContainerWatzCore;
import com.hbm.inventory.gui.GUIAMSBase;
import com.hbm.inventory.gui.GUIAMSEmitter;
import com.hbm.inventory.gui.GUIAMSLimiter;
import com.hbm.inventory.gui.GUIBombMulti;
import com.hbm.inventory.gui.GUIConverterHeRf;
import com.hbm.inventory.gui.GUIConverterRfHe;
import com.hbm.inventory.gui.GUICoreAdvanced;
import com.hbm.inventory.gui.GUICoreTitanium;
import com.hbm.inventory.gui.GUICrateIron;
import com.hbm.inventory.gui.GUICrateSteel;
import com.hbm.inventory.gui.GUIFWatzCore;
import com.hbm.inventory.gui.GUIFusionMultiblock;
import com.hbm.inventory.gui.GUILaunchPadTier1;
import com.hbm.inventory.gui.GUIMachineArcFurnace;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.gui.GUIMachineBattery;
import com.hbm.inventory.gui.GUIMachineBoiler;
import com.hbm.inventory.gui.GUIMachineBoilerElectric;
import com.hbm.inventory.gui.GUIMachineCMBFactory;
import com.hbm.inventory.gui.GUIMachineCentrifuge;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.gui.GUIMachineCoal;
import com.hbm.inventory.gui.GUIMachineCompactLauncher;
import com.hbm.inventory.gui.GUIMachineCyclotron;
import com.hbm.inventory.gui.GUIMachineDiesel;
import com.hbm.inventory.gui.GUIMachineEPress;
import com.hbm.inventory.gui.GUIMachineElectricFurnace;
import com.hbm.inventory.gui.GUIMachineFluidTank;
import com.hbm.inventory.gui.GUIMachineGasCent;
import com.hbm.inventory.gui.GUIMachineGasFlare;
import com.hbm.inventory.gui.GUIMachineGenerator;
import com.hbm.inventory.gui.GUIMachineKeyForge;
import com.hbm.inventory.gui.GUIMachineLaunchTable;
import com.hbm.inventory.gui.GUIMachineMiningDrill;
import com.hbm.inventory.gui.GUIMachineMissileAssembly;
import com.hbm.inventory.gui.GUIMachineOilWell;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.gui.GUIMachinePuF6Tank;
import com.hbm.inventory.gui.GUIMachinePumpjack;
import com.hbm.inventory.gui.GUIMachineRTG;
import com.hbm.inventory.gui.GUIMachineRadGen;
import com.hbm.inventory.gui.GUIMachineReactor;
import com.hbm.inventory.gui.GUIMachineReactorSmall;
import com.hbm.inventory.gui.GUIMachineRefinery;
import com.hbm.inventory.gui.GUIMachineSatLinker;
import com.hbm.inventory.gui.GUIMachineSchrabidiumTransmutator;
import com.hbm.inventory.gui.GUIMachineSelenium;
import com.hbm.inventory.gui.GUIMachineShredder;
import com.hbm.inventory.gui.GUIMachineSiren;
import com.hbm.inventory.gui.GUIMachineTeleLinker;
import com.hbm.inventory.gui.GUIMachineTeleporter;
import com.hbm.inventory.gui.GUIMachineTurbine;
import com.hbm.inventory.gui.GUIMachineTurbofan;
import com.hbm.inventory.gui.GUIMachineUF6Tank;
import com.hbm.inventory.gui.GUINukeBoy;
import com.hbm.inventory.gui.GUINukeCustom;
import com.hbm.inventory.gui.GUINukeFleija;
import com.hbm.inventory.gui.GUINukeFurnace;
import com.hbm.inventory.gui.GUINukeGadget;
import com.hbm.inventory.gui.GUINukeMan;
import com.hbm.inventory.gui.GUINukeMike;
import com.hbm.inventory.gui.GUINukeN2;
import com.hbm.inventory.gui.GUINukeN45;
import com.hbm.inventory.gui.GUINukePrototype;
import com.hbm.inventory.gui.GUINukeSolinium;
import com.hbm.inventory.gui.GUINukeTsar;
import com.hbm.inventory.gui.GUIRailgun;
import com.hbm.inventory.gui.GUIReactorControl;
import com.hbm.inventory.gui.GUIReactorMultiblock;
import com.hbm.inventory.gui.GUIRtgFurnace;
import com.hbm.inventory.gui.GUISafe;
import com.hbm.inventory.gui.GUISatDock;
import com.hbm.inventory.gui.GUIScreenBobmazon;
import com.hbm.inventory.gui.GUIScreenDesignator;
import com.hbm.inventory.gui.GUIScreenSatInterface;
import com.hbm.inventory.gui.GUIScreenTemplateFolder;
import com.hbm.inventory.gui.GUITestDiFurnace;
import com.hbm.inventory.gui.GUIWasteDrum;
import com.hbm.inventory.gui.GUIWatzCore;
import com.hbm.items.ModItems;
import com.hbm.tileentity.bomb.TileEntityBombMulti;
import com.hbm.tileentity.bomb.TileEntityCompactLauncher;
import com.hbm.tileentity.bomb.TileEntityLaunchPad;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;
import com.hbm.tileentity.bomb.TileEntityNukeBoy;
import com.hbm.tileentity.bomb.TileEntityNukeCustom;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeGadget;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.bomb.TileEntityNukeMike;
import com.hbm.tileentity.bomb.TileEntityNukeN2;
import com.hbm.tileentity.bomb.TileEntityNukeN45;
import com.hbm.tileentity.bomb.TileEntityNukePrototype;
import com.hbm.tileentity.bomb.TileEntityNukeSolinium;
import com.hbm.tileentity.bomb.TileEntityNukeTsar;
import com.hbm.tileentity.bomb.TileEntityRailgun;
import com.hbm.tileentity.machine.TileEntityAMSBase;
import com.hbm.tileentity.machine.TileEntityAMSEmitter;
import com.hbm.tileentity.machine.TileEntityAMSLimiter;
import com.hbm.tileentity.machine.TileEntityConverterHeRf;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;
import com.hbm.tileentity.machine.TileEntityCoreAdvanced;
import com.hbm.tileentity.machine.TileEntityCoreTitanium;
import com.hbm.tileentity.machine.TileEntityCrateIron;
import com.hbm.tileentity.machine.TileEntityCrateSteel;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityFWatzCore;
import com.hbm.tileentity.machine.TileEntityFusionMultiblock;
import com.hbm.tileentity.machine.TileEntityMachineArcFurnace;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineBoiler;
import com.hbm.tileentity.machine.TileEntityMachineBoilerElectric;
import com.hbm.tileentity.machine.TileEntityMachineCMBFactory;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCoal;
import com.hbm.tileentity.machine.TileEntityMachineCyclotron;
import com.hbm.tileentity.machine.TileEntityMachineDiesel;
import com.hbm.tileentity.machine.TileEntityMachineEPress;
import com.hbm.tileentity.machine.TileEntityMachineElectricFurnace;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineGasFlare;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.tileentity.machine.TileEntityMachineKeyForge;
import com.hbm.tileentity.machine.TileEntityMachineMiningDrill;
import com.hbm.tileentity.machine.TileEntityMachineMissileAssembly;
import com.hbm.tileentity.machine.TileEntityMachineOilWell;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachinePuF6Tank;
import com.hbm.tileentity.machine.TileEntityMachinePumpjack;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.tileentity.machine.TileEntityMachineRadGen;
import com.hbm.tileentity.machine.TileEntityMachineReactor;
import com.hbm.tileentity.machine.TileEntityMachineReactorLarge;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineRefinery;
import com.hbm.tileentity.machine.TileEntityMachineSatDock;
import com.hbm.tileentity.machine.TileEntityMachineSatLinker;
import com.hbm.tileentity.machine.TileEntityMachineSchrabidiumTransmutator;
import com.hbm.tileentity.machine.TileEntityMachineSeleniumEngine;
import com.hbm.tileentity.machine.TileEntityMachineShredder;
import com.hbm.tileentity.machine.TileEntityMachineSiren;
import com.hbm.tileentity.machine.TileEntityMachineTeleLinker;
import com.hbm.tileentity.machine.TileEntityMachineTeleporter;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;
import com.hbm.tileentity.machine.TileEntityMachineUF6Tank;
import com.hbm.tileentity.machine.TileEntityNukeFurnace;
import com.hbm.tileentity.machine.TileEntityReactorControl;
import com.hbm.tileentity.machine.TileEntityRtgFurnace;
import com.hbm.tileentity.machine.TileEntitySafe;
import com.hbm.tileentity.machine.TileEntityWasteDrum;
import com.hbm.tileentity.machine.TileEntityWatzCore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if (entity != null) {
			switch (ID) {
			case ModBlocks.guiID_machine_press: {
				if (entity instanceof TileEntityMachinePress) {
					return new ContainerMachinePress(player.inventory, (TileEntityMachinePress) entity);
				}
				return null;
			}
			case ModBlocks.guiID_test_difurnace: {
				if (entity instanceof TileEntityDiFurnace) {
					return new ContainerDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
				}
				return null;
			}
			case ModBlocks.guiID_machine_assembler:
				if (entity instanceof TileEntityMachineAssembler) {
					return new ContainerMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
				}
				return null;
			case ModBlocks.guiID_machine_chemplant:
				if (entity instanceof TileEntityMachineChemplant) {
					return new ContainerMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
				}
				return null;
			case ModBlocks.guiID_nuke_man:
				if (entity instanceof TileEntityNukeMan) {
					return new ContainerNukeMan(player.inventory, (TileEntityNukeMan) entity);
				}
				return null;
			case ModBlocks.guiID_nuke_fleija:
				if (entity instanceof TileEntityNukeFleija) {
					return new ContainerNukeFleija(player.inventory, (TileEntityNukeFleija) entity);
				}
				return null;
			case ModBlocks.guiID_machine_coal:
				if (entity instanceof TileEntityMachineCoal) {
					return new ContainerMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
				}
				return null;
			case ModBlocks.guiID_machine_generator:
				if (entity instanceof TileEntityMachineGenerator) {
					return new ContainerMachineGenerator(player.inventory, (TileEntityMachineGenerator) entity);
				}
				return null;
			case ModBlocks.guiID_reactor_small:
				if (entity instanceof TileEntityMachineReactorSmall) {
					return new ContainerMachineReactorSmall(player.inventory, (TileEntityMachineReactorSmall) entity);
				}
				return null;
			case ModBlocks.guiID_machine_rtg:
				if (entity instanceof TileEntityMachineRTG) {
					return new ContainerMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
				}
				return null;
			case ModBlocks.guiID_machine_battery:
				if (entity instanceof TileEntityMachineBattery) {
					return new ContainerMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
				}
				return null;
			case ModBlocks.guiID_converter_he_rf:
				if (entity instanceof TileEntityConverterHeRf) {
					return new ContainerConverterHeRf(player.inventory, (TileEntityConverterHeRf) entity);
				}
				return null;
			case ModBlocks.guiID_converter_rf_he:
				if (entity instanceof TileEntityConverterRfHe) {
					return new ContainerConverterRfHe(player.inventory, (TileEntityConverterRfHe) entity);
				}
				return null;
			case ModBlocks.guiID_machine_turbine:
				if (entity instanceof TileEntityMachineTurbine) {
					return new ContainerMachineTurbine(player.inventory, (TileEntityMachineTurbine) entity);
				}
				return null;
			case ModBlocks.guiID_launch_pad:
				if (entity instanceof TileEntityLaunchPad) {
					return new ContainerLaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
				}
				return null;
			case ModBlocks.guiID_machine_boiler:
				if (entity instanceof TileEntityMachineBoiler) {
					return new ContainerMachineBoiler(player.inventory, (TileEntityMachineBoiler) entity);
				}
				return null;
			case ModBlocks.guiID_machine_boiler_electric:
				if (entity instanceof TileEntityMachineBoilerElectric) {
					return new ContainerMachineBoilerElectric(player.inventory, (TileEntityMachineBoilerElectric) entity);
				}
				return null;
			case ModBlocks.guiID_machine_epress:
				if (entity instanceof TileEntityMachineEPress) {
					return new ContainerMachineEPress(player.inventory, (TileEntityMachineEPress) entity);
				}
				return null;
			case ModBlocks.guiID_centrifuge:
				if(entity instanceof TileEntityMachineCentrifuge){
					return new ContainerCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
				}
			case ModBlocks.guiID_gascent:
				if(entity instanceof TileEntityMachineGasCent){
					return new ContainerMachineGasCent(player.inventory, (TileEntityMachineGasCent) entity);
				}
			case ModBlocks.guiID_uf6_tank:
				if(entity instanceof TileEntityMachineUF6Tank){
					return new ContainerUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
				}
			case ModBlocks.guiID_puf6_tank:
				if(entity instanceof TileEntityMachinePuF6Tank){
					return new ContainerPuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
				}
			case ModBlocks.guiID_railgun:
				if(entity instanceof TileEntityRailgun){
					return new ContainerRailgun(player.inventory, (TileEntityRailgun)entity);
				}
			case ModBlocks.guiID_reactor:
				if(entity instanceof TileEntityMachineReactor){
					return new ContainerReactor(player.inventory, (TileEntityMachineReactor)entity);
				}
			case ModBlocks.guiID_machine_shredder:
				if(entity instanceof TileEntityMachineShredder){
					return new ContainerMachineShredder(player.inventory, (TileEntityMachineShredder)entity);
				}
			case ModBlocks.guiID_machine_fluidtank:
				if(entity instanceof TileEntityMachineFluidTank){
					return new ContainerMachineFluidTank(player.inventory, (TileEntityMachineFluidTank)entity);
				}
			case ModBlocks.guiID_machine_refinery:
				if(entity instanceof TileEntityMachineRefinery){
					return new ContainerMachineRefinery(player.inventory, (TileEntityMachineRefinery)entity);
				}
			case ModBlocks.guiID_machine_cyclotron:
				if(entity instanceof TileEntityMachineCyclotron){
					return new ContainerMachineCyclotron(player.inventory, (TileEntityMachineCyclotron)entity);
				}
			case ModBlocks.guiID_schrabidium_transmutator:
				if(entity instanceof TileEntityMachineSchrabidiumTransmutator){
					return new ContainerMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator)entity);
				}
			case ModBlocks.guiID_siren:
				if(entity instanceof TileEntityMachineSiren){
					return new ContainerMachineSiren(player.inventory, (TileEntityMachineSiren)entity);
				}
			case ModBlocks.guiID_crate_iron:
				if(entity instanceof TileEntityCrateIron){
					return new ContainerCrateIron(player.inventory, (TileEntityCrateIron)entity);
				}
			case ModBlocks.guiID_crate_steel:
				if(entity instanceof TileEntityCrateSteel){
					return new ContainerCrateSteel(player.inventory, (TileEntityCrateSteel)entity);
				}
			case ModBlocks.guiID_safe:
				if(entity instanceof TileEntitySafe){
					return new ContainerSafe(player.inventory, (TileEntitySafe)entity);
				}
			case ModBlocks.guiID_keyforge:
				if(entity instanceof TileEntityMachineKeyForge){
					return new ContainerMachineKeyForge(player.inventory, (TileEntityMachineKeyForge)entity);
				}
			case ModBlocks.guiID_nuke_furnace:
				if(entity instanceof TileEntityNukeFurnace){
					return new ContainerNukeFurnace(player.inventory, (TileEntityNukeFurnace)entity);
				}
			case ModBlocks.guiID_rtg_furnace:
				if(entity instanceof TileEntityRtgFurnace){
					return new ContainerRtgFurnace(player.inventory, (TileEntityRtgFurnace)entity);
				}
			case ModBlocks.guiID_machine_selenium:
				if(entity instanceof TileEntityMachineSeleniumEngine){
					return new ContainerMachineSelenium(player.inventory, (TileEntityMachineSeleniumEngine)entity);
				}
			case ModBlocks.guiID_machine_controller:
				if(entity instanceof TileEntityReactorControl){
					return new ContainerReactorControl(player.inventory, (TileEntityReactorControl)entity);
				}
			case ModBlocks.guiID_radgen:
				if(entity instanceof TileEntityMachineRadGen){
					return new ContainerMachineRadGen(player.inventory, (TileEntityMachineRadGen)entity);
				}
			case ModBlocks.guiID_electric_furnace:
				if(entity instanceof TileEntityMachineElectricFurnace){
					return new ContainerElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace)entity);
				}
			case ModBlocks.guiID_machine_arc:
				if(entity instanceof TileEntityMachineArcFurnace){
					return new ContainerMachineArcFurnace(player.inventory, (TileEntityMachineArcFurnace)entity);
				}
			case ModBlocks.guiID_waste_drum:
				if(entity instanceof TileEntityWasteDrum){
					return new ContainerWasteDrum(player.inventory, (TileEntityWasteDrum)entity);
				}
			case ModBlocks.guiID_machine_well:
				if(entity instanceof TileEntityMachineOilWell){
					return new ContainerMachineOilWell(player.inventory, (TileEntityMachineOilWell)entity);
				}
			case ModBlocks.guiID_machine_pumpjack:
				if(entity instanceof TileEntityMachinePumpjack){
					return new ContainerMachinePumpjack(player.inventory, (TileEntityMachinePumpjack)entity);
				}
			case ModBlocks.guiID_machine_flare:
				if(entity instanceof TileEntityMachineGasFlare){
					return new ContainerMachineGasFlare(player.inventory, (TileEntityMachineGasFlare)entity);
				}
			case ModBlocks.guiID_machine_drill:
				if(entity instanceof TileEntityMachineMiningDrill){
					return new ContainerMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill)entity);
				}
			case ModBlocks.guiID_machine_turbofan:
				if(entity instanceof TileEntityMachineTurbofan){
					return new ContainerMachineTurbofan(player.inventory, (TileEntityMachineTurbofan)entity);
				}
			case ModBlocks.guiID_combine_factory:
				if(entity instanceof TileEntityMachineCMBFactory){
					return new ContainerMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory)entity);
				}
			case ModBlocks.guiID_machine_teleporter:
				if(entity instanceof TileEntityMachineTeleporter){
					return new ContainerMachineTeleporter(player.inventory, (TileEntityMachineTeleporter)entity);
				}
			case ModBlocks.guiID_factory_titanium:
				if(entity instanceof TileEntityCoreTitanium){
					return new ContainerCoreTitanium(player.inventory, (TileEntityCoreTitanium)entity);
				}
			case ModBlocks.guiID_factory_advanced:
				if(entity instanceof TileEntityCoreAdvanced){
					return new ContainerCoreAdvanced(player.inventory, (TileEntityCoreAdvanced)entity);
				}
			case ModBlocks.guiID_reactor_multiblock:
				if(entity instanceof TileEntityMachineReactorLarge){
					return new ContainerReactorMultiblock(player.inventory, (TileEntityMachineReactorLarge)entity);
				}
			case ModBlocks.guiID_fusion_multiblock:
				if(entity instanceof TileEntityFusionMultiblock){
					return new ContainerFusionMultiblock(player.inventory, (TileEntityFusionMultiblock)entity);
				}
			case ModBlocks.guiID_watz_multiblock:
				if(entity instanceof TileEntityWatzCore){
					return new ContainerWatzCore(player.inventory, (TileEntityWatzCore)entity);
				}
			case ModBlocks.guiID_fwatz_multiblock:
				if(entity instanceof TileEntityFWatzCore){
					return new ContainerFWatzCore(player.inventory, (TileEntityFWatzCore)entity);
				}
			case ModBlocks.guiID_nuke_gadget:
				if(entity instanceof TileEntityNukeGadget){
					return new ContainerNukeGadget(player.inventory, (TileEntityNukeGadget)entity);
				}
			case ModBlocks.guiID_nuke_boy:
				if(entity instanceof TileEntityNukeBoy){
					return new ContainerNukeBoy(player.inventory, (TileEntityNukeBoy)entity);
				}
			case ModBlocks.guiID_nuke_mike:
				if(entity instanceof TileEntityNukeMike){
					return new ContainerNukeMike(player.inventory, (TileEntityNukeMike)entity);
				}
			case ModBlocks.guiID_nuke_tsar:
				if(entity instanceof TileEntityNukeTsar){
					return new ContainerNukeTsar(player.inventory, (TileEntityNukeTsar)entity);
				}
			case ModBlocks.guiID_nuke_prototype:
				if(entity instanceof TileEntityNukePrototype){
					return new ContainerNukePrototype(player.inventory, (TileEntityNukePrototype)entity);
				}
			case ModBlocks.guiID_nuke_solinium:
				if(entity instanceof TileEntityNukeSolinium){
					return new ContainerNukeSolinium(player.inventory, (TileEntityNukeSolinium)entity);
				}
			case ModBlocks.guiID_nuke_n2:
				if(entity instanceof TileEntityNukeN2){
					return new ContainerNukeN2(player.inventory, (TileEntityNukeN2)entity);
				}
			case ModBlocks.guiID_nuke_n45:
				if(entity instanceof TileEntityNukeN45){
					return new ContainerNukeN45(player.inventory, (TileEntityNukeN45)entity);
				}
			case ModBlocks.guiID_nuke_custom:
				if(entity instanceof TileEntityNukeCustom){
					return new ContainerNukeCustom(player.inventory, (TileEntityNukeCustom)entity);
				}
			case ModBlocks.guiID_bomb_multi:
				if(entity instanceof TileEntityBombMulti){
					return new ContainerBombMulti(player.inventory, (TileEntityBombMulti)entity);
				}
			case ModBlocks.guiID_telelinker:
				if(entity instanceof TileEntityMachineTeleLinker){
					return new ContainerMachineTeleLinker(player.inventory, (TileEntityMachineTeleLinker)entity);
				}
			case ModBlocks.guiID_missile_assembly:
				if(entity instanceof TileEntityMachineMissileAssembly){
					return new ContainerMachineMissileAssembly(player.inventory, (TileEntityMachineMissileAssembly)entity);
				}
			case ModBlocks.guiID_compact_launcher:
				if(entity instanceof TileEntityCompactLauncher){
					return new ContainerCompactLauncher(player.inventory, (TileEntityCompactLauncher)entity);
				}
			case ModBlocks.guiID_launch_table:
				if(entity instanceof TileEntityLaunchTable){
					return new ContainerLaunchTable(player.inventory, (TileEntityLaunchTable)entity);
				}
			case ModBlocks.guiID_ams_base:
				if(entity instanceof TileEntityAMSBase){
					return new ContainerAMSBase(player.inventory, (TileEntityAMSBase)entity);
				}
			case ModBlocks.guiID_ams_emitter:
				if(entity instanceof TileEntityAMSEmitter){
					return new ContainerAMSEmitter(player.inventory, (TileEntityAMSEmitter)entity);
				}
			case ModBlocks.guiID_ams_limiter:
				if(entity instanceof TileEntityAMSLimiter){
					return new ContainerAMSLimiter(player.inventory, (TileEntityAMSLimiter)entity);
				}
			case ModBlocks.guiID_satlinker:
				if(entity instanceof TileEntityMachineSatLinker){
					return new ContainerMachineSatLinker(player.inventory, (TileEntityMachineSatLinker)entity);
				}
			case ModBlocks.guiID_dock:
				if(entity instanceof TileEntityMachineSatDock){
					return new ContainerSatDock(player.inventory, (TileEntityMachineSatDock)entity);
				}
			case ModBlocks.guiID_machine_diesel:
				if(entity instanceof TileEntityMachineDiesel){
					return new ContainerMachineDiesel(player.inventory, (TileEntityMachineDiesel)entity);
				}
			}
		
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(new BlockPos(x, y, z));
		if (entity != null) {
			switch (ID) {
			case ModBlocks.guiID_machine_press: {
				if (entity instanceof TileEntityMachinePress) {
					return new GUIMachinePress(player.inventory, (TileEntityMachinePress) entity);
				}
				return null;
			}
			case ModBlocks.guiID_test_difurnace: {
				if (entity instanceof TileEntityDiFurnace) {
					return new GUITestDiFurnace(player.inventory, (TileEntityDiFurnace) entity);
				}
				return null;
			}
			case ModBlocks.guiID_machine_assembler:
				if (entity instanceof TileEntityMachineAssembler) {
					return new GUIMachineAssembler(player.inventory, (TileEntityMachineAssembler) entity);
				}
				return null;
			case ModBlocks.guiID_machine_chemplant:
				if (entity instanceof TileEntityMachineChemplant) {
					return new GUIMachineChemplant(player.inventory, (TileEntityMachineChemplant) entity);
				}
				return null;
			case ModBlocks.guiID_nuke_man:
				if (entity instanceof TileEntityNukeMan) {
					return new GUINukeMan(player.inventory, (TileEntityNukeMan) entity);
				}
			case ModBlocks.guiID_nuke_fleija:
				if (entity instanceof TileEntityNukeFleija) {
					return new GUINukeFleija(player.inventory, (TileEntityNukeFleija) entity);
				}
				return null;
			case ModBlocks.guiID_machine_coal:
				if (entity instanceof TileEntityMachineCoal) {
					return new GUIMachineCoal(player.inventory, (TileEntityMachineCoal) entity);
				}
				return null;
			case ModBlocks.guiID_machine_generator:
				if (entity instanceof TileEntityMachineGenerator) {
					return new GUIMachineGenerator(player.inventory, (TileEntityMachineGenerator) entity);
				}
				return null;
			case ModBlocks.guiID_reactor_small:
				if (entity instanceof TileEntityMachineReactorSmall) {
					return new GUIMachineReactorSmall(player.inventory, (TileEntityMachineReactorSmall) entity);
				}
				return null;
			case ModBlocks.guiID_machine_rtg:
				if (entity instanceof TileEntityMachineRTG) {
					return new GUIMachineRTG(player.inventory, (TileEntityMachineRTG) entity);
				}
				return null;
			case ModBlocks.guiID_machine_battery:
				if (entity instanceof TileEntityMachineBattery) {
					return new GUIMachineBattery(player.inventory, (TileEntityMachineBattery) entity);
				}
				return null;
			case ModBlocks.guiID_converter_he_rf:
				if (entity instanceof TileEntityConverterHeRf) {
					return new GUIConverterHeRf(player.inventory, (TileEntityConverterHeRf) entity);
				}
				return null;
			case ModBlocks.guiID_converter_rf_he:
				if (entity instanceof TileEntityConverterRfHe) {
					return new GUIConverterRfHe(player.inventory, (TileEntityConverterRfHe) entity);
				}
				return null;
			case ModBlocks.guiID_machine_turbine:
				if (entity instanceof TileEntityMachineTurbine) {
					return new GUIMachineTurbine(player.inventory, (TileEntityMachineTurbine) entity);
				}
				return null;
			case ModBlocks.guiID_launch_pad:
				if (entity instanceof TileEntityLaunchPad) {
					return new GUILaunchPadTier1(player.inventory, (TileEntityLaunchPad) entity);
				}
				return null;
			case ModBlocks.guiID_machine_boiler:
				if (entity instanceof TileEntityMachineBoiler) {
					return new GUIMachineBoiler(player.inventory, (TileEntityMachineBoiler) entity);
				}
				return null;
			case ModBlocks.guiID_machine_boiler_electric:
				if (entity instanceof TileEntityMachineBoilerElectric) {
					return new GUIMachineBoilerElectric(player.inventory, (TileEntityMachineBoilerElectric) entity);
				}
				return null;
			case ModBlocks.guiID_machine_epress:
				if (entity instanceof TileEntityMachineEPress) {
					return new GUIMachineEPress(player.inventory, (TileEntityMachineEPress) entity);
				}
				return null;
			case ModBlocks.guiID_centrifuge:
				if(entity instanceof TileEntityMachineCentrifuge){
					return new GUIMachineCentrifuge(player.inventory, (TileEntityMachineCentrifuge) entity);
				}
			case ModBlocks.guiID_gascent:
				if(entity instanceof TileEntityMachineGasCent){
					return new GUIMachineGasCent(player.inventory, (TileEntityMachineGasCent) entity);
				}
			case ModBlocks.guiID_uf6_tank:
				if(entity instanceof TileEntityMachineUF6Tank){
					return new GUIMachineUF6Tank(player.inventory, (TileEntityMachineUF6Tank) entity);
				}
			case ModBlocks.guiID_puf6_tank:
				if(entity instanceof TileEntityMachinePuF6Tank){
					return new GUIMachinePuF6Tank(player.inventory, (TileEntityMachinePuF6Tank) entity);
				}
			case ModBlocks.guiID_railgun:
				if(entity instanceof TileEntityRailgun){
					return new GUIRailgun(player.inventory, (TileEntityRailgun)entity);
				}
			case ModBlocks.guiID_reactor:
				if(entity instanceof TileEntityMachineReactor){
					return new GUIMachineReactor(player.inventory, (TileEntityMachineReactor)entity);
				}
			case ModBlocks.guiID_machine_shredder:
				if(entity instanceof TileEntityMachineShredder){
					return new GUIMachineShredder(player.inventory, (TileEntityMachineShredder)entity);
				}
			case ModBlocks.guiID_machine_fluidtank:
				if(entity instanceof TileEntityMachineFluidTank){
					return new GUIMachineFluidTank(player.inventory, (TileEntityMachineFluidTank)entity);
				}
			case ModBlocks.guiID_machine_refinery:
				if(entity instanceof TileEntityMachineRefinery){
					return new GUIMachineRefinery(player.inventory, (TileEntityMachineRefinery)entity);
				}
			case ModBlocks.guiID_machine_cyclotron:
				if(entity instanceof TileEntityMachineCyclotron){
					return new GUIMachineCyclotron(player.inventory, (TileEntityMachineCyclotron)entity);
				}
			case ModBlocks.guiID_schrabidium_transmutator:
				if(entity instanceof TileEntityMachineSchrabidiumTransmutator){
					return new GUIMachineSchrabidiumTransmutator(player.inventory, (TileEntityMachineSchrabidiumTransmutator)entity);
				}
			case ModBlocks.guiID_siren:
				if(entity instanceof TileEntityMachineSiren){
					return new GUIMachineSiren(player.inventory, (TileEntityMachineSiren)entity);
				}
			case ModBlocks.guiID_crate_iron:
				if(entity instanceof TileEntityCrateIron){
					return new GUICrateIron(player.inventory, (TileEntityCrateIron)entity);
				}
			case ModBlocks.guiID_crate_steel:
				if(entity instanceof TileEntityCrateSteel){
					return new GUICrateSteel(player.inventory, (TileEntityCrateSteel)entity);
				}
			case ModBlocks.guiID_safe:
				if(entity instanceof TileEntitySafe){
					return new GUISafe(player.inventory, (TileEntitySafe)entity);
				}
			case ModBlocks.guiID_keyforge:
				if(entity instanceof TileEntityMachineKeyForge){
					return new GUIMachineKeyForge(player.inventory, (TileEntityMachineKeyForge)entity);
				}
			case ModBlocks.guiID_nuke_furnace:
				if(entity instanceof TileEntityNukeFurnace){
					return new GUINukeFurnace(player.inventory, (TileEntityNukeFurnace)entity);
				}
			case ModBlocks.guiID_rtg_furnace:
				if(entity instanceof TileEntityRtgFurnace){
					return new GUIRtgFurnace(player.inventory, (TileEntityRtgFurnace)entity);
				}
			case ModBlocks.guiID_machine_selenium:
				if(entity instanceof TileEntityMachineSeleniumEngine){
					return new GUIMachineSelenium(player.inventory, (TileEntityMachineSeleniumEngine)entity);
				}
			case ModBlocks.guiID_machine_controller:
				if(entity instanceof TileEntityReactorControl){
					return new GUIReactorControl(player.inventory, (TileEntityReactorControl)entity);
				}
			case ModBlocks.guiID_radgen:
				if(entity instanceof TileEntityMachineRadGen){
					return new GUIMachineRadGen(player.inventory, (TileEntityMachineRadGen)entity);
				}
			case ModBlocks.guiID_electric_furnace:
				if(entity instanceof TileEntityMachineElectricFurnace){
					return new GUIMachineElectricFurnace(player.inventory, (TileEntityMachineElectricFurnace)entity);
				}
			case ModBlocks.guiID_machine_arc:
				if(entity instanceof TileEntityMachineArcFurnace){
					return new GUIMachineArcFurnace(player.inventory, (TileEntityMachineArcFurnace)entity);
				}
			case ModBlocks.guiID_waste_drum:
				if(entity instanceof TileEntityWasteDrum){
					return new GUIWasteDrum(player.inventory, (TileEntityWasteDrum)entity);
				}
			case ModBlocks.guiID_machine_well:
				if(entity instanceof TileEntityMachineOilWell){
					return new GUIMachineOilWell(player.inventory, (TileEntityMachineOilWell)entity);
				}
			case ModBlocks.guiID_machine_pumpjack:
				if(entity instanceof TileEntityMachinePumpjack){
					return new GUIMachinePumpjack(player.inventory, (TileEntityMachinePumpjack)entity);
				}
			case ModBlocks.guiID_machine_flare:
				if(entity instanceof TileEntityMachineGasFlare){
					return new GUIMachineGasFlare(player.inventory, (TileEntityMachineGasFlare)entity);
				}
			case ModBlocks.guiID_machine_drill:
				if(entity instanceof TileEntityMachineMiningDrill){
					return new GUIMachineMiningDrill(player.inventory, (TileEntityMachineMiningDrill)entity);
				}
			case ModBlocks.guiID_machine_turbofan:
				if(entity instanceof TileEntityMachineTurbofan){
					return new GUIMachineTurbofan(player.inventory, (TileEntityMachineTurbofan)entity);
				}
			case ModBlocks.guiID_combine_factory:
				if(entity instanceof TileEntityMachineCMBFactory){
					return new GUIMachineCMBFactory(player.inventory, (TileEntityMachineCMBFactory)entity);
				}
			case ModBlocks.guiID_machine_teleporter:
				if(entity instanceof TileEntityMachineTeleporter){
					return new GUIMachineTeleporter(player.inventory, (TileEntityMachineTeleporter)entity);
				}
			case ModBlocks.guiID_factory_titanium:
				if(entity instanceof TileEntityCoreTitanium){
					return new GUICoreTitanium(player.inventory, (TileEntityCoreTitanium)entity);
				}
			case ModBlocks.guiID_factory_advanced:
				if(entity instanceof TileEntityCoreAdvanced){
					return new GUICoreAdvanced(player.inventory, (TileEntityCoreAdvanced)entity);
				}
			case ModBlocks.guiID_reactor_multiblock:
				if(entity instanceof TileEntityMachineReactorLarge){
					return new GUIReactorMultiblock(player.inventory, (TileEntityMachineReactorLarge)entity);
				}
			case ModBlocks.guiID_fusion_multiblock:
				if(entity instanceof TileEntityFusionMultiblock){
					return new GUIFusionMultiblock(player.inventory, (TileEntityFusionMultiblock)entity);
				}
			case ModBlocks.guiID_watz_multiblock:
				if(entity instanceof TileEntityWatzCore){
					return new GUIWatzCore(player.inventory, (TileEntityWatzCore)entity);
				}
			case ModBlocks.guiID_fwatz_multiblock:
				if(entity instanceof TileEntityFWatzCore){
					return new GUIFWatzCore(player.inventory, (TileEntityFWatzCore)entity);
				}
			case ModBlocks.guiID_nuke_gadget:
				if(entity instanceof TileEntityNukeGadget){
					return new GUINukeGadget(player.inventory, (TileEntityNukeGadget)entity);
				}
			case ModBlocks.guiID_nuke_boy:
				if(entity instanceof TileEntityNukeBoy){
					return new GUINukeBoy(player.inventory, (TileEntityNukeBoy)entity);
				}
			case ModBlocks.guiID_nuke_mike:
				if(entity instanceof TileEntityNukeMike){
					return new GUINukeMike(player.inventory, (TileEntityNukeMike)entity);
				}
			case ModBlocks.guiID_nuke_tsar:
				if(entity instanceof TileEntityNukeTsar){
					return new GUINukeTsar(player.inventory, (TileEntityNukeTsar)entity);
				}
			case ModBlocks.guiID_nuke_prototype:
				if(entity instanceof TileEntityNukePrototype){
					return new GUINukePrototype(player.inventory, (TileEntityNukePrototype)entity);
				}
			case ModBlocks.guiID_nuke_solinium:
				if(entity instanceof TileEntityNukeSolinium){
					return new GUINukeSolinium(player.inventory, (TileEntityNukeSolinium)entity);
				}
			case ModBlocks.guiID_nuke_n2:
				if(entity instanceof TileEntityNukeN2){
					return new GUINukeN2(player.inventory, (TileEntityNukeN2)entity);
				}
			case ModBlocks.guiID_nuke_n45:
				if(entity instanceof TileEntityNukeN45){
					return new GUINukeN45(player.inventory, (TileEntityNukeN45)entity);
				}
			case ModBlocks.guiID_nuke_custom:
				if(entity instanceof TileEntityNukeCustom){
					return new GUINukeCustom(player.inventory, (TileEntityNukeCustom)entity);
				}
			case ModBlocks.guiID_bomb_multi:
				if(entity instanceof TileEntityBombMulti){
					return new GUIBombMulti(player.inventory, (TileEntityBombMulti)entity);
				}
			case ModBlocks.guiID_telelinker:
				if(entity instanceof TileEntityMachineTeleLinker){
					return new GUIMachineTeleLinker(player.inventory, (TileEntityMachineTeleLinker)entity);
				}
			case ModBlocks.guiID_missile_assembly:
				if(entity instanceof TileEntityMachineMissileAssembly){
					return new GUIMachineMissileAssembly(player.inventory, (TileEntityMachineMissileAssembly)entity);
				}
			case ModBlocks.guiID_compact_launcher:
				if(entity instanceof TileEntityCompactLauncher){
					return new GUIMachineCompactLauncher(player.inventory, (TileEntityCompactLauncher)entity);
				}
			case ModBlocks.guiID_launch_table:
				if(entity instanceof TileEntityLaunchTable){
					return new GUIMachineLaunchTable(player.inventory, (TileEntityLaunchTable)entity);
				}
			case ModBlocks.guiID_ams_base:
				if(entity instanceof TileEntityAMSBase){
					return new GUIAMSBase(player.inventory, (TileEntityAMSBase)entity);
				}
			case ModBlocks.guiID_ams_emitter:
				if(entity instanceof TileEntityAMSEmitter){
					return new GUIAMSEmitter(player.inventory, (TileEntityAMSEmitter)entity);
				}
			case ModBlocks.guiID_ams_limiter:
				if(entity instanceof TileEntityAMSLimiter){
					return new GUIAMSLimiter(player.inventory, (TileEntityAMSLimiter)entity);
				}
			case ModBlocks.guiID_satlinker:
				if(entity instanceof TileEntityMachineSatLinker){
					return new GUIMachineSatLinker(player.inventory, (TileEntityMachineSatLinker)entity);
				}
			case ModBlocks.guiID_dock:
				if(entity instanceof TileEntityMachineSatDock){
					return new GUISatDock(player.inventory, (TileEntityMachineSatDock)entity);
				}
			case ModBlocks.guiID_machine_diesel:
				if(entity instanceof TileEntityMachineDiesel){
					return new GUIMachineDiesel(player.inventory, (TileEntityMachineDiesel)entity);
				}
			}

		} else {
			// CLIENTONLY GUIS

			switch (ID) {
			case ModItems.guiID_item_folder:
				return new GUIScreenTemplateFolder(player);
			case ModItems.guiID_item_designator:
				return new GUIScreenDesignator(player, x);
			case ModItems.guiID_item_sat_interface:
				return new GUIScreenSatInterface(player);
			case ModItems.guiID_item_bobmazon:
				if(BobmazonOfferFactory.getOffers(player.getHeldItemMainhand()) != null)
					return new GUIScreenBobmazon(player, BobmazonOfferFactory.getOffers(player.getHeldItemMainhand()));
			}
		}
		return null;
	}

}
