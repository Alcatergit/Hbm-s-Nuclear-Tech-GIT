package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityCore extends TileEntityMachineBase implements ITickable {

	public boolean hasCore = false;
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;
	
	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(128000);
		tanks[1] = new FluidTank(128000);
	}

	@Override
	public String getName() {
		return "container.dfcCore";
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			if(heat > 0 && heat >= field) {
				
				int fill = tanks[0].getFluidAmount() + tanks[1].getFluidAmount();
				int max = tanks[0].getCapacity() + tanks[1].getCapacity();
				int mod = heat * 10;
				
				int size = Math.max(Math.min(fill * mod / max, 1000), 50);
				
				//System.out.println(fill + " * " + mod + " / " + max + " = " + size);

	    		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 100000.0F, 1.0F);

				EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(world);
				exp.posX = pos.getX();
				exp.posY = pos.getY();
				exp.posZ = pos.getZ();
				exp.destructionRange = size;
				exp.speed = 25;
				exp.coefficient = 1.0F;
				exp.waste = false;

				world.spawnEntity(exp);
	    		
	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, size);
	    		cloud.posX = pos.getX();
	    		cloud.posY = pos.getY();
	    		cloud.posZ = pos.getZ();
	    		world.spawnEntity(cloud);
			}
			
			if(inventory.getStackInSlot(0).getItem() instanceof ItemCatalyst && inventory.getStackInSlot(2).getItem() instanceof ItemCatalyst){
				color = calcAvgHex(
						((ItemCatalyst)inventory.getStackInSlot(0).getItem()).getColor(),
						((ItemCatalyst)inventory.getStackInSlot(2).getItem()).getColor()
				);
				hasCore = true;
			}else{
				color = 0;
				hasCore = false;
			}
			
			if(heat > 0)
				radiation();
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("tank0", tanks[0].getFluid() == null ? "HBM_EMPTY" : tanks[0].getFluid().getFluid().getName());
			data.setString("tank1", tanks[1].getFluid() == null ? "HBM_EMPTY" : tanks[1].getFluid().getFluid().getName());
			data.setInteger("fill0", tanks[0].getFluidAmount());
			data.setInteger("fill1", tanks[1].getFluidAmount());
			data.setInteger("field", field);
			data.setInteger("heat", heat);
			data.setInteger("color", color);
			data.setBoolean("hasCore", hasCore);
			networkPack(data, 250);
			
			//PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, tanks), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			
			heat = 0;
			field = 0;
			this.markDirty();
		} else {
			
			//TODO: sick particle effects
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound data) {
		String s0 = data.getString("tank0");
		String s1 = data.getString("tank1");
		if("HBM_EMPTY".equals(s0)){
			tanks[0].setFluid(null);
		} else {
			tanks[0].setFluid(new FluidStack(FluidRegistry.getFluid(s0), data.getInteger("fill0")));
		}
		if("HBM_EMPTY".equals(s1)){
			tanks[1].setFluid(null);
		} else {
			tanks[1].setFluid(new FluidStack(FluidRegistry.getFluid(s1), data.getInteger("fill1")));
		}
		field = data.getInteger("field");
		heat = data.getInteger("heat");
		color = data.getInteger("color");
		hasCore = data.getBoolean("hasCore");
	}
	
	private void radiation() {
		
		double scale = 2;
		
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - 10 + 0.5, pos.getY() - 10 + 0.5 + 6, pos.getZ() - 10 + 0.5, pos.getX() + 10 + 0.5, pos.getY() + 10 + 0.5 + 6, pos.getZ() + 10 + 0.5));
		
		for(Entity e : list) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e)))
				if(!Library.isObstructed(world, pos.getX() + 0.5, pos.getY() + 0.5 + 6, pos.getZ() + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ)) {
					e.attackEntityFrom(ModDamageSource.ams, 1000);
					e.setFire(3);
				}
		}

		List<Entity> list2 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - scale + 0.5, pos.getY() - scale + 0.5 + 6, pos.getZ() - scale + 0.5, pos.getX() + scale + 0.5, pos.getY() + scale + 0.5 + 6, pos.getZ() + scale + 0.5));
		
		for(Entity e : list2) {
			if(!(e instanceof EntityPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e)))
				e.attackEntityFrom(ModDamageSource.amsCore, 10000);
		}
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}
	
	public boolean isReady() {
		
		if(getCore() == 0)
			return false;
		
		if(color == 0)
			return false;
		
		if(tanks[0].getFluid() == null || tanks[1].getFluid() == null)
			return false;
		
		if(getFuelEfficiency(tanks[0].getFluid().getFluid()) <= 0 || getFuelEfficiency(tanks[1].getFluid().getFluid()) <= 0)
			return false;
		
		return true;
	}
	
	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {
		
		//check if a reaction can take place
		if(!isReady())
			return joules;
		
		int demand = (int)Math.ceil((double)joules / 1000D);

		long powerAbs = ItemCatalyst.getPowerAbs(inventory.getStackInSlot(0)) + ItemCatalyst.getPowerAbs(inventory.getStackInSlot(2));
		float powerMod = ItemCatalyst.getPowerMod(inventory.getStackInSlot(0)) + ItemCatalyst.getPowerMod(inventory.getStackInSlot(2)) - 1F;
		float heatMod = ItemCatalyst.getHeatMod(inventory.getStackInSlot(0)) + ItemCatalyst.getHeatMod(inventory.getStackInSlot(2)) - 1F;
		float fuelMod = ItemCatalyst.getFuelMod(inventory.getStackInSlot(0)) + ItemCatalyst.getFuelMod(inventory.getStackInSlot(2)) - 1F;	

		demand = (int)(demand * fuelMod);
		
		//check if the reaction has enough valid fuel
		if(tanks[0].getFluidAmount() < demand || tanks[1].getFluidAmount() < demand)
			return joules;
		
		heat += (int)(heatMod * Math.ceil((double)joules / 10000D));
		
		Fluid f1 = tanks[0].getFluid().getFluid();
		Fluid f2 = tanks[1].getFluid().getFluid();

		tanks[0].drain(demand, true);
		tanks[1].drain(demand, true);
		
		return (long) (powerMod * joules * getCore() * getFuelEfficiency(f1) * getFuelEfficiency(f2)) + powerAbs;
	}
	
	public float getFuelEfficiency(Fluid type) {
		
		if(type == ModForgeFluids.hydrogen){
			return 1.0F;
		} else if(type == ModForgeFluids.deuterium){
			return 1.5F;
		} else if(type == ModForgeFluids.tritium){
			return 1.7F;
		} else if(type == ModForgeFluids.oxygen){
			return 1.2F;
		} else if(type == ModForgeFluids.acid){
			return 1.4F;
		} else if(type == ModForgeFluids.xenon){
			return 1.5F;
		} else if(type == ModForgeFluids.sas3){
			return 2.0F;
		} else if(type == ModForgeFluids.balefire){
			return 2.5F;
		} else if(type == ModForgeFluids.amat){
			return 2.2F;
		} else if(type == ModForgeFluids.aschrab){
			return 2.7F;
		}
		
		return 0;
	}
	
	//TODO: move stats to the AMSCORE class
	public int getCore() {
		
		if(inventory.getStackInSlot(1).isEmpty()) {
			return 0;
		}
		
		if(inventory.getStackInSlot(1).getItem() == ModItems.ams_core_sing)
			return 500;
		
		if(inventory.getStackInSlot(1).getItem() == ModItems.ams_core_wormhole)
			return 650;
		
		if(inventory.getStackInSlot(1).getItem() == ModItems.ams_core_eyeofharmony)
			return 800;
		
		if(inventory.getStackInSlot(1).getItem() == ModItems.ams_core_thingy)
			return 2500;
		
		return 0;
	}
	
	private int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);
		
		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);
		
		return r | g | b;
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(compound);
	}

}
