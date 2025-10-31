package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyGenerator;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.items.machine.ItemSatChip;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.satellites.SatelliteResonator;
import com.hbm.saveddata.satellites.SatelliteSavedData;
import com.leafia.contents.effects.folkvangr.visual.EntityCloudFleijaRainbow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;
import scala.util.Random;

import java.util.List;

public class TileEntityAMSBase extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor, IEnergyGenerator {

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
	public boolean syncResonators = false;
	
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
		tankTypes[0] = ModForgeFluids.COOLANT;
		
		tanks[1] = new FluidTank(8000);
		tankTypes[1] = ModForgeFluids.CRYOGEL;
		
		tanks[2] = new FluidTank(8000);
		tankTypes[2] = ModForgeFluids.DEUTERIUM;
		
		tanks[3] = new FluidTank(8000);
		tankTypes[3] = ModForgeFluids.TRITIUM;
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
		tankTypes[0] = FluidRegistry.getFluid(compound.getString("coolantA"));
		if (tankTypes[0] == null) tankTypes[0] =  ModForgeFluids.COOLANT;
		tankTypes[1] = FluidRegistry.getFluid(compound.getString("coolantB"));
		if (tankTypes[1] == null) tankTypes[1] = ModForgeFluids.CRYOGEL;
		tankTypes[2] = FluidRegistry.getFluid(compound.getString("fuelA"));
		if (tankTypes[2] == null) tankTypes[2] = ModForgeFluids.DEUTERIUM;
		tankTypes[3] = FluidRegistry.getFluid(compound.getString("fuelB"));
		if (tankTypes[3] == null) tankTypes[3] = ModForgeFluids.TRITIUM;
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
		compound.setString("coolantA", tankTypes[0].getName());
		compound.setString("coolantB", tankTypes[1].getName());
		compound.setString("fuelA", tankTypes[2].getName());
		compound.setString("fuelB", tankTypes[3].getName());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		boolean isSetUp = inventory.getStackInSlot(8).getItem() instanceof ItemCatalyst && inventory.getStackInSlot(9).getItem() instanceof ItemCatalyst &&
				inventory.getStackInSlot(10).getItem() instanceof ItemCatalyst && inventory.getStackInSlot(11).getItem() instanceof ItemCatalyst &&
				inventory.getStackInSlot(12).getItem() instanceof ItemAMSCore;
		if (!world.isRemote) {
			if(needsUpdate){
				needsUpdate = false;
			}
				
			
			/*for(int i = 0; i < tanks.length; i++){
				tanks[i].fill(new FluidStack(tankTypes[i], tanks[i].getCapacity()), true);
				needsUpdate = true;
			}*/
			
			if(!locked) {
				for (int t = 0; t < 8; t+=2) {
					if(inventory.getStackInSlot(t).getItem() instanceof ItemForgeFluidIdentifier && inventory.getStackInSlot(t+1).isEmpty()){
						Fluid f = ItemForgeFluidIdentifier.getType(inventory.getStackInSlot(t));
						if (tankTypes[t/4*2] == f || tankTypes[t/4*2+1] == f) continue;
						inventory.setStackInSlot(t+1,inventory.getStackInSlot(t));
						inventory.setStackInSlot(t,ItemStack.EMPTY);
						if (f == ModForgeFluids.CRYOGEL || f == ModForgeFluids.COOLANT || f == FluidRegistry.WATER || f == ModForgeFluids.OIL) {
							if(tankTypes[t/2] != f)
								tanks[t/2].setFluid(null);
							tankTypes[t/2] = f;
						}
					}
				}

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
				
				if(isSetUp && hasResonators() && efficiency > 0) {
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

					powerBase = ItemAMSCore.getPowerBase(inventory.getStackInSlot(12))*2000000L;
					heatBase = (int)(Math.sqrt(ItemAMSCore.getHeatBase(inventory.getStackInSlot(12)))*220);
					fuelBase = (int)(ItemAMSCore.getFuelBase(inventory.getStackInSlot(12))*(100/15f));
					
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
				warning = 33;
			}
			this.sendPower(world, pos);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, hasResonators() ? 1 : 0, 4), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, locked ? 1 : 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, color, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, efficiency, 2), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, field, 3), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos, new FluidTank[] {tanks[0], tanks[1], tanks[2], tanks[3]}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
		} else {
			if (!hasResonators())
				warning = 3;
			else if (!isSetUp)
				warning = 2;
			else if (efficiency <= 0)
				warning = 1;
			else
				warning = 0;
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
		} else if(type.getFluid() == ModForgeFluids.OIL){
			return 15;
		} else if(type.getFluid() == ModForgeFluids.COOLANT){
			return this.heat / 250;
		} else if(type.getFluid() == ModForgeFluids.CRYOGEL){
			return this.heat > heat/2 ? 25 : 5;
		} else {
			return 0;
		}
	}
	
	private int getFuelPower(FluidStack type) {
		if(type == null)
			return 0;
		else if(type.getFluid() == ModForgeFluids.DEUTERIUM){
			return 50;
		} else if(type.getFluid() == ModForgeFluids.TRITIUM){
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
		if (world.isRemote) return syncResonators;
		// not anymore lmao
		if(!inventory.getStackInSlot(13).isEmpty() && !inventory.getStackInSlot(14).isEmpty() && !inventory.getStackInSlot(15).isEmpty() &&
				inventory.getStackInSlot(13).getItem() == ModItems.sat_chip && inventory.getStackInSlot(14).getItem() == ModItems.sat_chip && inventory.getStackInSlot(15).getItem() == ModItems.sat_chip) {
			
		    SatelliteSavedData data = (SatelliteSavedData)world.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
		    if(data == null) {
		        world.getPerWorldStorage().setData("satellites", new SatelliteSavedData());
		        data = (SatelliteSavedData)world.getPerWorldStorage().getOrLoadData(SatelliteSavedData.class, "satellites");
		    }
		    data.markDirty();

		    int i1 = ItemSatChip.getFreq(inventory.getStackInSlot(13));
		    int i2 = ItemSatChip.getFreq(inventory.getStackInSlot(14));
		    int i3 = ItemSatChip.getFreq(inventory.getStackInSlot(15));
		    
		    if(data.getSatFromFreq(i1) != null && data.getSatFromFreq(i2) != null && data.getSatFromFreq(i3) != null &&
		    		data.getSatFromFreq(i1) instanceof SatelliteResonator && data.getSatFromFreq(i2) instanceof SatelliteResonator && data.getSatFromFreq(i3) instanceof SatelliteResonator &&
		    		i1 != i2 && i1 != i3 && i2 != i3)
		    	return true;
			
		}
		
		return false;
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
		} /*else if(resource.getFluid() == ModForgeFluids.coolant){
			return tanks[0].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.cryogel){
			return tanks[1].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.deuterium){
			return tanks[2].fill(resource, doFill);
		} else if(resource.getFluid() == ModForgeFluids.tritium){
			return tanks[3].fill(resource, doFill);
		} else {*/
		for (int i = 0; i < 4; i++) {
			if (tankTypes[i] == resource.getFluid())
				return tanks[i].fill(resource,doFill);
		}
			return 0;
		//}
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

	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public boolean isLoaded() {
		return true;
	}
}
