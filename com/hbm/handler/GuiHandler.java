package com.hbm.handler;

import com.hbm.blocks.ModBlocks;
import com.hbm.inventory.container.ContainerConverterHeRf;
import com.hbm.inventory.container.ContainerConverterRfHe;
import com.hbm.inventory.container.ContainerDiFurnace;
import com.hbm.inventory.container.ContainerMachineAssembler;
import com.hbm.inventory.container.ContainerMachineBattery;
import com.hbm.inventory.container.ContainerMachineChemplant;
import com.hbm.inventory.container.ContainerMachineCoal;
import com.hbm.inventory.container.ContainerMachineGenerator;
import com.hbm.inventory.container.ContainerMachinePress;
import com.hbm.inventory.container.ContainerMachineRTG;
import com.hbm.inventory.container.ContainerMachineReactorSmall;
import com.hbm.inventory.container.ContainerMachineTurbine;
import com.hbm.inventory.container.ContainerNukeFleija;
import com.hbm.inventory.container.ContainerNukeMan;
import com.hbm.inventory.gui.GUIConverterHeRf;
import com.hbm.inventory.gui.GUIConverterRfHe;
import com.hbm.inventory.gui.GUIMachineAssembler;
import com.hbm.inventory.gui.GUIMachineBattery;
import com.hbm.inventory.gui.GUIMachineChemplant;
import com.hbm.inventory.gui.GUIMachineCoal;
import com.hbm.inventory.gui.GUIMachineGenerator;
import com.hbm.inventory.gui.GUIMachinePress;
import com.hbm.inventory.gui.GUIMachineRTG;
import com.hbm.inventory.gui.GUIMachineReactorSmall;
import com.hbm.inventory.gui.GUIMachineTurbine;
import com.hbm.inventory.gui.GUINukeFleija;
import com.hbm.inventory.gui.GUINukeMan;
import com.hbm.inventory.gui.GUITestDiFurnace;
import com.hbm.tileentity.bomb.TileEntityNukeFleija;
import com.hbm.tileentity.bomb.TileEntityNukeMan;
import com.hbm.tileentity.machine.TileEntityConverterHeRf;
import com.hbm.tileentity.machine.TileEntityConverterRfHe;
import com.hbm.tileentity.machine.TileEntityDiFurnace;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineBattery;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineCoal;
import com.hbm.tileentity.machine.TileEntityMachineGenerator;
import com.hbm.tileentity.machine.TileEntityMachinePress;
import com.hbm.tileentity.machine.TileEntityMachineRTG;
import com.hbm.tileentity.machine.TileEntityMachineReactorSmall;
import com.hbm.tileentity.machine.TileEntityMachineTurbine;

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
			case ModBlocks.guiID_nuke_fleija:
				if(entity instanceof TileEntityNukeFleija){
					return new ContainerNukeFleija(player.inventory, (TileEntityNukeFleija)entity);
				}
			case ModBlocks.guiID_machine_coal:
				if(entity instanceof TileEntityMachineCoal){
					return new ContainerMachineCoal(player.inventory, (TileEntityMachineCoal)entity);
				}
			case ModBlocks.guiID_machine_generator:
				if(entity instanceof TileEntityMachineGenerator){
					return new ContainerMachineGenerator(player.inventory, (TileEntityMachineGenerator)entity);
				}
			case ModBlocks.guiID_reactor_small:
				if(entity instanceof TileEntityMachineReactorSmall){
					return new ContainerMachineReactorSmall(player.inventory, (TileEntityMachineReactorSmall)entity);
				}
			case ModBlocks.guiID_machine_rtg:
				if(entity instanceof TileEntityMachineRTG){
					return new ContainerMachineRTG(player.inventory, (TileEntityMachineRTG)entity);
				}
			case ModBlocks.guiID_machine_battery:
				if(entity instanceof TileEntityMachineBattery){
					return new ContainerMachineBattery(player.inventory, (TileEntityMachineBattery)entity);
				}
			case ModBlocks.guiID_converter_he_rf:
				if(entity instanceof TileEntityConverterHeRf){
					return new ContainerConverterHeRf(player.inventory, (TileEntityConverterHeRf)entity);
				}
			case ModBlocks.guiID_converter_rf_he:
				if(entity instanceof TileEntityConverterRfHe){
					return new ContainerConverterRfHe(player.inventory, (TileEntityConverterRfHe)entity);
				}
			case ModBlocks.guiID_machine_turbine:
				if(entity instanceof TileEntityMachineTurbine){
					return new ContainerMachineTurbine(player.inventory, (TileEntityMachineTurbine)entity);
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
			case ModBlocks.guiID_nuke_man:
				if(entity instanceof TileEntityNukeMan){
					return new GUINukeMan(player.inventory, (TileEntityNukeMan)entity);
				}
			case ModBlocks.guiID_nuke_fleija:
				if(entity instanceof TileEntityNukeFleija){
					return new GUINukeFleija(player.inventory, (TileEntityNukeFleija)entity);
				}
			case ModBlocks.guiID_machine_coal:
				if(entity instanceof TileEntityMachineCoal){
					return new GUIMachineCoal(player.inventory, (TileEntityMachineCoal)entity);
				}
			case ModBlocks.guiID_machine_generator:
				if(entity instanceof TileEntityMachineGenerator){
					return new GUIMachineGenerator(player.inventory, (TileEntityMachineGenerator)entity);
				}
			case ModBlocks.guiID_reactor_small:
				if(entity instanceof TileEntityMachineReactorSmall){
					return new GUIMachineReactorSmall(player.inventory, (TileEntityMachineReactorSmall)entity);
				}
			case ModBlocks.guiID_machine_rtg:
				if(entity instanceof TileEntityMachineRTG){
					return new GUIMachineRTG(player.inventory, (TileEntityMachineRTG)entity);
				}
			case ModBlocks.guiID_machine_battery:
				if(entity instanceof TileEntityMachineBattery){
					return new GUIMachineBattery(player.inventory, (TileEntityMachineBattery)entity);
				}
			case ModBlocks.guiID_converter_he_rf:
				if(entity instanceof TileEntityConverterHeRf){
					return new GUIConverterHeRf(player.inventory, (TileEntityConverterHeRf)entity);
				}
			case ModBlocks.guiID_converter_rf_he:
				if(entity instanceof TileEntityConverterRfHe){
					return new GUIConverterRfHe(player.inventory, (TileEntityConverterRfHe)entity);
				}
			case ModBlocks.guiID_machine_turbine:
				if(entity instanceof TileEntityMachineTurbine){
					return new GUIMachineTurbine(player.inventory, (TileEntityMachineTurbine)entity);
				}
			}

		}
		return null;
	}

}
