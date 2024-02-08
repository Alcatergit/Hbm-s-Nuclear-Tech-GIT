package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.effect.EntityCloudFleijaRainbow;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import scala.util.Random;

public class TileEntityAMSBase extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;

	public long power = 0;
	public static final long maxPower = 1000000000000000L;
	public int field = 0;
	public static final int maxField = 100;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 5000;
	public int age = 0;
	public int warning = 0;
	public int mode = 0;
	public boolean locked = false;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	public int color = -1;
	public boolean needsUpdate;
	
	Random rand = new Random();

	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSBase() {
		inventory = new ItemStackHandler(16){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tanks = new FluidTank[4];
		tankTypes = new Fluid[4];
		needsUpdate = false;
		
		tanks[0] = new FluidTank(8000);
		tankTypes[0] = ModForgeFluids.coolant;
		
		tanks[1] = new FluidTank(8000);
		tankTypes[1] = ModForgeFluids.cryogel;
		
		tanks[2] = new FluidTank(8000);
		tankTypes[2] = ModForgeFluids.deuterium;
		
		tanks[3] = new FluidTank(8000);
		tankTypes[3] = ModForgeFluids.tritium;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.amsBase";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=128;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		power = compound.getLong("power");
		field = compound.getInteger("field");
		efficiency = compound.getInteger("efficiency");
		heat = compound.getInteger("heat");
		locked = compound.getBoolean("locked");
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		tankTypes[0] = ModForgeFluids.coolant;
		tankTypes[1] = ModForgeFluids.cryogel;
		tankTypes[2] = ModForgeFluids.deuterium;
		tankTypes[3] = ModForgeFluids.tritium;
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("field", field);
		compound.setInteger("efficiency", efficiency);
		compound.setInteger("heat", heat);
		compound.setBoolean("locked", locked);
		compound.setTag("inventory", inventory.serializeNBT());
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			if(needsUpdate){
				needsUpdate = false;
			}
				
			
			for(int i = 0; i < tanks.length; i++){
				tanks[i].fill(new FluidStack(tankTypes[i], tanks[i].getCapacity()), true);
				needsUpdate = true;
			}
			
			if(!locked) {
				
				age++;
				if(age >= 20)
				{
					age = 0;
				}
				
				int f1 = 0, f2 = 0, f3 = 0, f4 = 0;
				int booster = 0;

				if(world.getTileEntity(pos.add(6, 0, 0)) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)world.getTileEntity(pos.add(6, 0, 0));
					if(!te.locked && te.getBlockMetadata() == 4) {
						f1 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(world.getTileEntity(pos.add(-6, 0, 0)) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)world.getTileEntity(pos.add(-6, 0, 0));
					if(!te.locked && te.getBlockMetadata() == 5) {
						f2 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(world.getTileEntity(pos.add(0, 0, 6)) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)world.getTileEntity(pos.add(0, 0, 6));
					if(!te.locked && te.getBlockMetadata() == 2) {
						f3 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				if(world.getTileEntity(pos.add(0, 0, -6)) instanceof TileEntityAMSLimiter) {
					TileEntityAMSLimiter te = (TileEntityAMSLimiter)world.getTileEntity(pos.add(0, 0, -6));
					if(!te.locked && te.getBlockMetadata() == 3) {
						f4 = te.efficiency;
						if(te.mode == 2)
							booster++;
					}
				}
				
				this.field = Math.round(calcField(f1, f2, f3, f4));
				
				mode = 0;
				if(field > 0)
					mode = 1;
				if(booster > 0)
					mode = 2;
				
				if(world.getTileEntity(pos.add(0, 9, 0)) instanceof TileEntityAMSEmitter) {
					TileEntityAMSEmitter te = (TileEntityAMSEmitter)world.getTileEntity(pos.add(0, 9, 0));
						this.efficiency = te.efficiency;
				}
				
				this.color = -1;
				
				float powerMod = 1;
				float heatMod = 1;
				float fuelMod = 1;
				long powerBase = 0;
				int heatBase = 0;
				int fuelBase = 0;
				
				if(inventory.getStackInSlot(8).getItem() instanceof ItemCatalyst && inventory.getStackInSlot(9).getItem() instanceof ItemCatalyst &&
						inventory.getStackInSlot(10).getItem() instanceof ItemCatalyst && inventory.getStackInSlot(11).getItem() instanceof ItemCatalyst &&
						inventory.getStackInSlot(12).getItem() instanceof ItemAMSCore && hasResonators() && efficiency > 0) {
					int a = ((ItemCatalyst)inventory.getStackInSlot(8).getItem()).getColor();
					int b = ((ItemCatalyst)inventory.getStackInSlot(9).getItem()).getColor();
					int c = ((ItemCatalyst)inventory.getStackInSlot(10).getItem()).getColor();
					int d = ((ItemCatalyst)inventory.getStackInSlot(11).getItem()).getColor();

					int e = this.calcAvgHex(a, b);
					int f = this.calcAvgHex(c, d);
					
					int g = this.calcAvgHex(e, f);
					
					this.color = g;

					
					for(int i = 8; i < 12; i++) {
						powerBase += ItemCatalyst.getPowerAbs(inventory.getStackInSlot(i));
						powerMod *= ItemCatalyst.getPowerMod(inventory.getStackInSlot(i));
						heatMod *= ItemCatalyst.getHeatMod(inventory.getStackInSlot(i));
						fuelMod *= ItemCatalyst.getFuelMod(inventory.getStackInSlot(i));
					}

					powerBase = (int)ItemAMSCore.getPowerBase(inventory.getStackInSlot(12));
					heatBase = (int)ItemAMSCore.getHeatBase(inventory.getStackInSlot(12));
					fuelBase = (int)ItemAMSCore.getFuelBase(inventory.getStackInSlot(12));
					
					powerBase *= this.efficiency;
					powerBase *= Math.pow(1.25F, booster);
					heatBase *= Math.pow(1.25F, booster);
					heatBase *= (100 - field);
					
					if(this.getFuelPower(tanks[2].getFluid()) > 0 && this.getFuelPower(tanks[3].getFluid()) > 0 &&
							tanks[2].getFluidAmount() > 0 && tanks[3].getFluidAmount() > 0) {

						power += (powerBase * powerMod * gauss(1, (heat - (maxHeat / 2)) / maxHeat)) / 1000 * getFuelPower(tanks[2].getFluid()) * getFuelPower(tanks[3].getFluid());
						heat += (heatBase * heatMod) / (float)(this.field / 100F);
						tanks[2].drain((int)(fuelBase * fuelMod), true);
						tanks[3].drain((int)(fuelBase * fuelMod), true);
						
						radiation();

						if(heat > maxHeat) {
							explode();
							heat = maxHeat;
						}
						
						if(field <= 0)
							explode();
					}
				}
				
				if(power > maxPower)
					power = maxPower;
				
				
				if(heat > 0 && tanks[0].getFluidAmount() > 0 && tanks[1].getFluidAmount() > 0) {
					heat -= (this.getCoolingStrength(tanks[0].getFluid()) * this.getCoolingStrength(tanks[1].getFluid()));

					tanks[0].drain(10, true);
					tanks[1].drain(10, true);
					
					if(heat < 0)
						heat = 0;
				}
				
			} else {
				field = 0;
				efficiency = 0;
				power = 0;
				warning = 3;
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, locked ? 1 : 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, color, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, efficiency, 2), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, field, 3), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos, new FluidTank[] {tanks[0], tanks[1], tanks[2], tanks[3]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
		}
	}
	
	private void radiation() {
		
		double maxSize = 5;
		double minSize = 0.5;
		double scale = minSize;
		scale += ((((double)this.tanks[2].getFluidAmount()) / ((double)this.tanks[2].getCapacity())) + (((double)this.tanks[3].getFluidAmount()) / ((double)this.tanks[3].getCapacity()))) * ((maxSize - minSize) / 2);

		scale *= 0.60;
		
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
	
	private void explode() {
		if(!world.isRemote) {
			
			for(int i = 0; i < 10; i++) {

	    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(this.world, 100);
	    		cloud.posX = pos.getX() + rand.nextInt(201) - 100;
	    		cloud.posY = pos.getY() + rand.nextInt(201) - 100;
	    		cloud.posZ = pos.getZ() + rand.nextInt(201) - 100;
	    		this.world.spawnEntity(cloud);
			}
			
			int radius = (int)(50 + (double)(tanks[2].getFluidAmount() + tanks[3].getFluidAmount()) / 16000D * 150);
			
			world.spawnEntity(EntityNukeExplosionMK5.statFac(world, radius, pos.getX(), pos.getY(), pos.getZ()));
			
			world.setBlockToAir(pos);
		}
	}
	
	private int getCoolingStrength(FluidStack type) {
		if(type == null)
			return 0;
		else if(type.getFluid() == FluidRegistry.WATER){
			return 5;
		} else if(type.getFluid() == ModForgeFluids.oil){
			return 15;
		} else if(type.getFluid() == ModForgeFluids.coolant){
			return this.heat / 250;
		} else if(type.getFluid() == ModForgeFluids.cryogel){
			return this.heat > heat/2 ? 25 : 5;
		} else {
			return 0;
		}
	}
	
	private int getFuelPower(FluidStack type) {
		if(type == null)
			return 0;
		else if(type.getFluid() == ModForgeFluids.deuterium){
			return 50;
		} else if(type.getFluid() == ModForgeFluids.tritium){
			return 75;
		} else {
			return 0;
		}
	}
	
	private float gauss(float a, float x) {
		
		//Greater values -> less difference of temperate impact
		double amplifier = 0.10;
		
		return (float) ( (1/Math.sqrt(a * Math.PI)) * Math.pow(Math.E, -1 * Math.pow(x, 2)/amplifier) );
	}
	
	/*private float calcEffect(float a, float x) {
		return (float) (gauss( 1 / a, x / maxHeat) * Math.sqrt(Math.PI * 2) / (Math.sqrt(2) * Math.sqrt(maxPower)));
	}*/
	
	private float calcField(int a, int b, int c, int d) {
		return (float)(a + b + c + d) * (a * 25 + b * 25 + c * 25 + d  * 25) / 40000;
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
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (efficiency * i) / maxEfficiency;
	}
	
	public int getFieldScaled(int i) {
		return (field * i) / maxField;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
	}
	
	public boolean hasResonators() {
		//Drillgon200: Always returns true anyway
		/*if(slots[13] != null && slots[14] != null && slots[15] != null &&
				slots[13].getItem() == ModItems.sat_chip && slots[14].getItem() == ModItems.sat_chip && slots[15].getItem() == ModItems.sat_chip) {
			
		    SatelliteSavedData data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		        worldObj.perWorldStorage.setData("satellites", new SatelliteSavedData(worldObj));
		        data = (SatelliteSavedData)worldObj.perWorldStorage.loadData(SatelliteSavedData.class, "satellites");
		    }
		    data.markDirty();

		    int i1 = ItemSatChip.getFreq(slots[13]);
		    int i2 = ItemSatChip.getFreq(slots[14]);
		    int i3 = ItemSatChip.getFreq(slots[15]);
		    
		    if(data.getSatFromFreq(i1) != null && data.getSatFromFreq(i2) != null && data.getSatFromFreq(i3) != null &&
		    		data.getSatFromFreq(i1).satelliteType.getID() == SatelliteType.RESONATOR.getID() && data.getSatFromFreq(i2).satelliteType.getID() == SatelliteType.RESONATOR.getID() && data.getSatFromFreq(i3).satelliteType.getID() == SatelliteType.RESONATOR.getID() &&
		    		i1 != i2 && i1 != i3 && i2 != i3)
		    	return true;
			
		}*/
		
		return true;
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{
				tanks[0].getTankProperties()[0],
				tanks[1].getTankProperties()[0],
				tanks[2].getTankProperties()[0],
				tanks[3].getTankProperties()[0]
		};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null){
			return 0;
		} else if(resource.getFluid() == ModForgeFluids.coolant){
			return tanks[0].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.cryogel){
			return tanks[1].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.deuterium){
			return tanks[2].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.tritium){
			return tanks[3].fill(resource, doFill);
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 4){
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			tanks[2].readFromNBT(tags[2]);
			tanks[3].readFromNBT(tags[3]);
		}
	}

}
