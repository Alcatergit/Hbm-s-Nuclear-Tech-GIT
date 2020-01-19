package com.hbm.entity.logic;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.ParticleBurstPacket;

import cofh.redstoneflux.api.IEnergyProvider;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

public class EntityEMP extends Entity {

	List<BlockPos> machines;
	int life = 2 * 60 * 20;

	public EntityEMP(World p_i1582_1_) {
		super(p_i1582_1_);
	}
	
	@Override
	public void onUpdate() {
		
		if(!world.isRemote) {
			if(machines == null) {
				allocate();
			} else {
				shock();
			}
			
			if(this.ticksExisted > life)
				this.setDead();
		}
	}
	
	private void allocate() {
		
		machines = new ArrayList<BlockPos>();
		
		int radius = 100;
		
		for(int x = -radius; x <= radius; x++) {
			
			int x2 = (int) Math.pow(x, 2);
			
			for(int y = -radius; y <= radius; y++) {
				
				int y2 = (int) Math.pow(y, 2);
				
				for(int z = -radius; z <= radius; z++) {
					
					int z2 = (int) Math.pow(z, 2);
					
					if(Math.sqrt(x2 + y2 + z2) <= radius) {
						add(new BlockPos((int)posX + x, (int)posY + y, (int)posZ + z));
					}
				}
			}
		}
	}
	
	private void shock() {
		
		for(BlockPos pos : machines) {
			emp(pos);
		}
	}
	
	private void add(BlockPos pos) {
		TileEntity te = world.getTileEntity(pos);
		if(te == null)
			return;
		if (te instanceof ISource) {
			machines.add(pos);
		} else if (te instanceof IConsumer) {
			machines.add(pos);
		} else if (te instanceof IEnergyProvider) {
			machines.add(pos);
		} else if(te.hasCapability(CapabilityEnergy.ENERGY, null)){
			machines.add(pos);
		}
	}
	
	private void emp(BlockPos pos) {
		
		TileEntity te = world.getTileEntity(pos);
		if(te == null)
			return;
		boolean flag = false;
		
		if (te instanceof ISource) {
			
			((ISource)te).setSPower(0);
			flag = true;
		}
		if (te instanceof IConsumer) {
			
			((IConsumer)te).setPower(0);
			flag = true;
		}
		if (te instanceof IEnergyProvider) {

			((IEnergyProvider)te).extractEnergy(EnumFacing.UP, ((IEnergyProvider)te).getEnergyStored(EnumFacing.UP), false);
			((IEnergyProvider)te).extractEnergy(EnumFacing.DOWN, ((IEnergyProvider)te).getEnergyStored(EnumFacing.DOWN), false);
			((IEnergyProvider)te).extractEnergy(EnumFacing.NORTH, ((IEnergyProvider)te).getEnergyStored(EnumFacing.NORTH), false);
			((IEnergyProvider)te).extractEnergy(EnumFacing.SOUTH, ((IEnergyProvider)te).getEnergyStored(EnumFacing.SOUTH), false);
			((IEnergyProvider)te).extractEnergy(EnumFacing.EAST, ((IEnergyProvider)te).getEnergyStored(EnumFacing.EAST), false);
			((IEnergyProvider)te).extractEnergy(EnumFacing.WEST, ((IEnergyProvider)te).getEnergyStored(EnumFacing.WEST), false);
			flag = true;
		}
		if(te != null && te.hasCapability(CapabilityEnergy.ENERGY, null)){
			IEnergyStorage handle = te.getCapability(CapabilityEnergy.ENERGY, null);
			handle.extractEnergy(handle.getEnergyStored(), false);
			flag = true;
		}
		
		if(flag && rand.nextInt(20) == 0) {
			
			PacketDispatcher.wrapper.sendToAll(new ParticleBurstPacket(pos.getX(), pos.getY(), pos.getZ(), Block.getIdFromBlock(Blocks.STAINED_GLASS), 3));
	        
		}
		
	}

	@Override
	protected void entityInit() { }

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) { }

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) { }
}
