package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.AdvancementManager;
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
	public int safeTimer = 0;
	
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
				if(safeTimer > 20){
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
				
					if(safeTimer > 1200 || !EntityNukeExplosionMK3.isJammed(this.world, exp)){
						world.spawnEntity(exp);
			    		
			    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, size);
			    		cloud.posX = pos.getX();
			    		cloud.posY = pos.getY();
			    		cloud.posZ = pos.getZ();
			    		world.spawnEntity(cloud);
			    		world.setBlockToAir(pos);
			    	}
		    	}
		    	safeTimer++;
			} else {
				if(safeTimer > 0) safeTimer--;
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
			
			if(heat > 0){
				radiation();
			}
			
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
		
		double scale = (int)Math.log(heat) * 1.25 + 0.5;
		
		int range = (int)(scale * 4);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - range + 0.5, pos.getY() - range + 0.5, pos.getZ() - range + 0.5, pos.getX() + range + 0.5, pos.getY() + range + 0.5, pos.getZ() + range + 0.5));
		
		for(Entity e : list) {
			boolean isPlayer = e instanceof EntityPlayer;
			if(!(isPlayer && ArmorUtil.checkForHazmat((EntityPlayer)e))){
				if(!(Library.isObstructed(world, pos.getX() + 0.5, pos.getY() + 0.5 + 6, pos.getZ() + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ))){
					if(!isPlayer || (isPlayer && !((EntityPlayer)e).capabilities.isCreativeMode))
						e.attackEntityFrom(ModDamageSource.ams, this.heat * 100);
					e.setFire(3);
				}
			}
			if(isPlayer){
				AdvancementManager.grantAchievement(((EntityPlayer) e), AdvancementManager.progress_dfc);
			}
		}

		List<Entity> list2 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - scale + 0.5, pos.getY() - scale + 0.5, pos.getZ() - scale + 0.5, pos.getX() + scale + 0.5, pos.getY() + scale + 0.5, pos.getZ() + scale + 0.5));
		
		for(Entity e : list2) {
			boolean isPlayer = e instanceof EntityPlayer;
			if(!(isPlayer && ArmorUtil.checkForHaz2((EntityPlayer)e))){
				if(!isPlayer || (isPlayer && !((EntityPlayer)e).capabilities.isCreativeMode))
					e.attackEntityFrom(ModDamageSource.amsCore, this.heat * 1000);
				e.setFire(3);
			}
		}
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}
	
	public boolean isReady() {
		
		if(getCorePower() == 0)
			return false;
		
		if(color == 0)
			return false;
		
		if(tanks[0].getFluid() == null || tanks[1].getFluid() == null)
			return false;
		
		if(FluidTypeHandler.getDFCEfficiency(tanks[0].getFluid().getFluid()) <= 0 || FluidTypeHandler.getDFCEfficiency(tanks[1].getFluid().getFluid()) <= 0)
			return false;
		
		return true;
	}
	
	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {
		
		//check if a reaction can take place
		if(!isReady())
			return joules;
		
		int demand = (int)Math.sqrt((double)joules);

		long powerAbs = ItemCatalyst.getPowerAbs(inventory.getStackInSlot(0)) + ItemCatalyst.getPowerAbs(inventory.getStackInSlot(2));
		float powerMod = ItemCatalyst.getPowerMod(inventory.getStackInSlot(0)) * ItemCatalyst.getPowerMod(inventory.getStackInSlot(2));
		float heatMod = ItemCatalyst.getHeatMod(inventory.getStackInSlot(0)) * ItemCatalyst.getHeatMod(inventory.getStackInSlot(2));
		float fuelMod = ItemCatalyst.getFuelMod(inventory.getStackInSlot(0)) * ItemCatalyst.getFuelMod(inventory.getStackInSlot(2));	

		demand = (int)(getCoreFuel() * demand * fuelMod);
		
		//check if the reaction has enough valid fuel
		if(tanks[0].getFluidAmount() < demand || tanks[1].getFluidAmount() < demand)
			return joules;
		
		heat += (int)(getCoreHeat() * heatMod * Math.ceil((double)joules / 10000D));
		
		Fluid f1 = tanks[0].getFluid().getFluid();
		Fluid f2 = tanks[1].getFluid().getFluid();

		tanks[0].drain(demand, true);
		tanks[1].drain(demand, true);

		long powerOutput = (long) Math.max(0, (powerMod * joules * getCorePower() * FluidTypeHandler.getDFCEfficiency(f1) * FluidTypeHandler.getDFCEfficiency(f2)) + powerAbs);
		if(powerOutput > 0 && heat == 0)
			heat = 1;
		return powerOutput;
	}
	
	//TODO: move stats to the AMSCORE class
	//Alcater: ok did that
	public int getCorePower() {
		return ItemAMSCore.getPowerBase(inventory.getStackInSlot(1));
	}

	public float getCoreHeat() {
		return ItemAMSCore.getHeatBase(inventory.getStackInSlot(1));
	}

	public float getCoreFuel() {
		return ItemAMSCore.getFuelBase(inventory.getStackInSlot(1));
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
