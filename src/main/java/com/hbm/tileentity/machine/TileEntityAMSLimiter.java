package com.hbm.tileentity.machine;

import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
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

public class TileEntityAMSLimiter extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;

	public long power = 0;
	public static final long maxPower = 10000000;
	public int efficiency = 0;
	public static final int maxEfficiency = 100;
	public int heat = 0;
	public static final int maxHeat = 2500;
	public int age = 0;
	public int warning = 0;
	public int mode = 0;
	public boolean locked = false;
	public FluidTank tank;
	public Fluid tankType;
	public boolean needsUpdate;
	
	Random rand = new Random();

	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	
	private String customName;
	
	public TileEntityAMSLimiter() {
		inventory = new ItemStackHandler(4){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tank = new FluidTank(8000);
		tankType = ModForgeFluids.coolant;
		needsUpdate = false;
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.amsLimiter";
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
		tank.readFromNBT(compound);
		efficiency = compound.getInteger("efficiency");
		heat = compound.getInteger("heat");
		locked = compound.getBoolean("locked");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		tank.writeToNBT(compound);
		compound.setInteger("efficiency", efficiency);
		compound.setInteger("heat", heat);
		compound.setBoolean("locked", locked);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			
			if(!locked) {
				if(needsUpdate){
					needsUpdate = false;
				}
				
				if(power > 0) {
					//" - (maxHeat / 2)" offsets center to 50% instead of 0%
					efficiency = Math.round(calcEffect(power, heat - (maxHeat / 2)) * 100);
					power -= Math.ceil(power * 0.025);
					warning = 0;
				} else {
					efficiency = 0;
					warning = 1;
				}
				
				if(tankType == ModForgeFluids.cryogel) {
					
					if(tank.getFluidAmount() >= 5) {
						if(heat > 0){
							tank.drain(5, true);
							needsUpdate = true;
						}

						if(heat <= maxHeat / 2)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 10; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 10; i++)
							if(heat > maxHeat / 2)
								heat--;
					} else {
						heat += efficiency;
					}
				} else if(tankType == ModForgeFluids.coolant) {
					
					if(tank.getFluidAmount() >= 5) {
						if(heat > 0){
							tank.drain(5, true);
							needsUpdate = true;
						}

						if(heat <= maxHeat / 4)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 5; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 5; i++)
							if(heat > maxHeat / 4)
								heat--;
					} else {
						heat += efficiency;
					}
				} else if(tankType == FluidRegistry.WATER) {
					
					if(tank.getFluidAmount() >= 15) {
						if(heat > 0){
							tank.drain(15, true);
							needsUpdate = true;
						}

						if(heat <= maxHeat * 0.85)
							if(efficiency > 0)
								heat += efficiency;
							else
								for(int i = 0; i < 2; i++)
									if(heat > 0)
										heat--;
						
						for(int i = 0; i < 2; i++)
							if(heat > maxHeat * 0.85)
								heat--;
					} else {
						heat += efficiency;
					}
				} else {
					heat += efficiency;
					warning = 2;
				}
				
				mode = 0;
				if(!inventory.getStackInSlot(2).isEmpty()) {
					if(inventory.getStackInSlot(2).getItem() == ModItems.ams_focus_limiter)
						mode = 1;
					else if(inventory.getStackInSlot(2).getItem() == ModItems.ams_focus_booster)
						mode = 2;
					else {
						this.efficiency = 0;
						this.warning = 2;
					}
				} else {
					this.efficiency = 0;
					this.warning = 2;
				}
				
				if(tank.getFluidAmount() <= 5 || heat > maxHeat * 0.9)
					warning = 2;
				
				if(heat > maxHeat) {
					heat = maxHeat;
					locked = true;
					ExplosionLarge.spawnShock(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 24, 3);
					ExplosionLarge.spawnBurst(world, pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5, 24, 3);
		            this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.oldExplosion, SoundCategory.BLOCKS, 10.0F, 1);
			        this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.shutdown, SoundCategory.BLOCKS, 10.0F, 1.0F);
				}
	
				power = Library.chargeTEFromItems(inventory, 3, power, maxPower);
				
			} else {
				//fire particles n stuff
				int meta = this.getBlockMetadata();
				
				double pos2 = rand.nextDouble() * 2.5;
				double off = 0.25;
				if(meta == 2)
					world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5 + off, pos.getY() + 5.5, pos.getZ() + 0.5 - pos2, 0.0, 0.0, 0.0));
				if(meta == 3)
					world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5 - off, pos.getY() + 5.5, pos.getZ() + 0.5 + pos2, 0.0, 0.0, 0.0));
				if(meta == 4)
					world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5 - pos2, pos.getY() + 5.5, pos.getZ() + 0.5 - off, 0.0, 0.0, 0.0));
				if(meta == 5)
					world.spawnEntity(new EntityGasFlameFX(world, pos.getX() + 0.5 + pos2, pos.getY() + 5.5, pos.getZ() + 0.5 + off, 0.0, 0.0, 0.0));
				
				efficiency = 0;
				power = 0;
				warning = 3;
			}
			
			tankType = ModForgeFluids.cryogel;
			tank.drain(tank.getCapacity(), true);
			tank.fill(new FluidStack(ModForgeFluids.cryogel, tank.getCapacity()), true);

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, locked ? 1 : 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, efficiency, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[]{tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 15));
		}
	}

	private float gauss(float a, float x) {
		
		//Greater values -> less difference of temperate impact
		double amplifier = 0.10;
		
		return (float) ( (1/Math.sqrt(a * Math.PI)) * Math.pow(Math.E, -1 * Math.pow(x, 2)/amplifier) );
	}
	
	private float calcEffect(float a, float x) {
		return (float) (gauss( 1 / a, x / maxHeat) * Math.sqrt(Math.PI * 2) / (Math.sqrt(2) * Math.sqrt(maxPower)));
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	public int getEfficiencyScaled(int i) {
		return (efficiency * i) / maxEfficiency;
	}
	
	public int getHeatScaled(int i) {
		return (heat * i) / maxHeat;
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
	
	public boolean isValidFluid(Fluid fluid){
		if(fluid != null && (fluid == FluidRegistry.WATER || fluid == ModForgeFluids.coolant || fluid == ModForgeFluids.cryogel))
			return true;
		return false;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null){
			return 0;
		} else if ((tank.getFluid() == null && this.isValidFluid(resource.getFluid())) || (tank.getFluid() != null && tank.getFluid().getFluid() == resource.getFluid())){
			return tank.fill(resource, doFill);
		}
		return 0;
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
		if(tags.length != 1){
			return;
		} else {
			tank.readFromNBT(tags[0]);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
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

}
