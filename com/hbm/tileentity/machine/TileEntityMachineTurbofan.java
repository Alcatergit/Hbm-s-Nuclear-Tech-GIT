package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.hbm.entity.particle.EntitySSmokeFX;
import com.hbm.entity.particle.EntityTSmokeFX;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ISource;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TETurbofanPacket;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineTurbofan extends TileEntity implements ITickable, ISource, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;

	public long power;
	public int soundCycle = 0;
	public static final long maxPower = 150000;
	public int age = 0;
	public List<IConsumer> list = new ArrayList<IConsumer>();
	public FluidTank tank;
	Random rand = new Random();
	public int afterburner;
	public boolean isRunning;
	public int spin;
	public boolean needsUpdate = false;

	//private static final int[] slots_top = new int[] { 0 };
	//private static final int[] slots_bottom = new int[] { 0, 0 };
	//private static final int[] slots_side = new int[] { 0 };

	private String customName;
	
	public TileEntityMachineTurbofan() {
		inventory = new ItemStackHandler(3){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tank = new FluidTank(64000);
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.machineTurbofan";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("powerTime");
		tank.readFromNBT(compound);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		tank.writeToNBT(compound);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}
	
	@Override
	public void update() {
		int nrg = 1250;
		int cnsp = 1;
		
		afterburner = 0;
		if(!inventory.getStackInSlot(2).isEmpty()) {
			if(inventory.getStackInSlot(2).getItem() == ModItems.upgrade_afterburn_1) {
				nrg *= 2;
				cnsp *= 2.5;
				afterburner = 1;
			}
			if(inventory.getStackInSlot(2).getItem() == ModItems.upgrade_afterburn_2) {
				nrg *= 3;
				cnsp *= 5;
				afterburner = 2;
			}
			if(inventory.getStackInSlot(2).getItem() == ModItems.upgrade_afterburn_3) {
				nrg *= 4;
				cnsp *= 7.5;
				afterburner = 3;
			}
		}
		
		if (!world.isRemote) {
			int prevFluidAmount = tank.getFluidAmount();
			long prevPower = power;
			if (needsUpdate) {
				needsUpdate = false;
			}
			age++;
			if (age >= 20) {
				age = 0;
			}

			if (age == 9 || age == 19)
				ffgeuaInit();

			//Tank Management
			//Drillgon200: tank number doesn't matter, only one tank.
			if(this.inputValidForTank(-1, 0))
				if(FFUtils.fillFromFluidContainer(inventory, tank, 0, 1))
					needsUpdate = true;
			
			isRunning = false;
				
			if(tank.getFluidAmount() >= cnsp) {
				tank.drain(cnsp, true);
				needsUpdate = true;
				power += nrg;

				isRunning = true;
				
				spin += 3;
				spin = spin % 360;
				
				if(power > maxPower)
					power = maxPower;
				
				int meta = getBlockMetadata();

				double posX = pos.getX() + 0.5;
				double posY = pos.getY();
				double posZ = pos.getZ() + 0.5;

				if(meta == 4) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(world);
						smoke.posX = pos.getX() + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = pos.getZ() + 4.25;
						smoke.motionX = rand.nextGaussian() * 0.3;
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = 2.5 + (rand.nextFloat() * 3.5);
						if(!world.isRemote)
							world.spawnEntity(smoke);
					}
					
					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(world);
							smoke.posX = pos.getX() + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = pos.getZ() + 4.25;
							smoke.motionX = rand.nextGaussian() * 0.3;
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = 2.5 + (rand.nextFloat() * 3.5);
							if(!world.isRemote)
								world.spawnEntity(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ + 4.5, posX + 1.5, posY + 3, posZ + 12));
					
					for(Entity e : list) {
						e.motionZ += 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ - 12, posX + 1.5, posY + 3, posZ - 4.5));
					
					for(Entity e : list) {
						e.motionZ += 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ - 5.5, posX + 1.5, posY + 3, posZ - 4.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 5) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(world);
						smoke.posX = pos.getX() + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = pos.getZ() - 4.25;
						smoke.motionX = rand.nextGaussian() * 0.3;
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = -2.5 - (rand.nextFloat() * 3.5);
						if(!world.isRemote)
							world.spawnEntity(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(world);
							smoke.posX = pos.getX() + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = pos.getZ() - 4.25;
							smoke.motionX = rand.nextGaussian() * 0.3;
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = -2.5 - (rand.nextFloat() * 3.5);
							if(!world.isRemote)
								world.spawnEntity(smoke);
						}

					//Exhaust push
					List<Entity> list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ - 12, posX + 1.5, posY + 3, posZ - 4.5));
					
					for(Entity e : list) {
						e.motionZ -= 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}

					//Intake pull
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ + 4.5, posX + 1.5, posY + 3, posZ + 12));
					
					for(Entity e : list) {
						e.motionZ -= 0.5;
					}

					//Intake kill
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 1.5, posY, posZ + 4.5, posX + 1.5, posY + 3, posZ + 5.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 3) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(world);
						smoke.posX = pos.getX() + 4.25;
						smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = pos.getZ() + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.motionX = 2.5 + (rand.nextFloat() * 3.5);
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = rand.nextGaussian() * 0.3;
						if(!world.isRemote)
							world.spawnEntity(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(world);
							smoke.posX = pos.getX() + 4.25;
							smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = pos.getZ() + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.motionX = 2.5 + (rand.nextFloat() * 3.5);
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = rand.nextGaussian() * 0.3;
							if(!world.isRemote)
								world.spawnEntity(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX + 4.5, posY, posZ - 1.5, posX + 12, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX += 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 12, posY, posZ - 1.5, posX - 4.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX += 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 5.5, posY, posZ - 1.5, posX - 4.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
				if(meta == 2) {
					if(afterburner == 0 && rand.nextInt(3) == 0) {
						EntityTSmokeFX smoke = new EntityTSmokeFX(world);
						smoke.posX = pos.getX() - 4.25;
						smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
						smoke.posZ = pos.getZ() + 0.5 + (rand.nextGaussian() * 0.5);
						smoke.motionX = -2.5 - (rand.nextFloat() * 3.5);
						smoke.motionY = rand.nextGaussian() * 0.3;
						smoke.motionZ = rand.nextGaussian() * 0.3;
						if(!world.isRemote)
							world.spawnEntity(smoke);
					}

					for(int i = 0; i < afterburner * 5; i++)
						if(afterburner > 0 && rand.nextInt(2) == 0) {
							EntitySSmokeFX smoke = new EntitySSmokeFX(world);
							smoke.posX = pos.getX() - 4.25;
							smoke.posY = pos.getY() + 1.5 + (rand.nextGaussian() * 0.5);
							smoke.posZ = pos.getZ() + 0.5 + (rand.nextGaussian() * 0.5);
							smoke.motionX = -2.5 - (rand.nextFloat() * 3.5);
							smoke.motionY = rand.nextGaussian() * 0.3;
							smoke.motionZ = rand.nextGaussian() * 0.3;
							if(!world.isRemote)
								world.spawnEntity(smoke);
						}
					
					//Exhaust push
					List<Entity> list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX - 12, posY, posZ - 1.5, posX - 4.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX -= 0.5;
						if(afterburner > 0)
							e.setFire(3 * afterburner);
					}
					
					//Intake pull
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX + 4.5, posY, posZ - 1.5, posX + 12, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.motionX -= 0.5;
					}
					
					//Intake kill
					list = (List<Entity>)world.getEntitiesWithinAABBExcludingEntity(null, 
							new AxisAlignedBB(posX + 4.5, posY, posZ - 1.5, posX + 5.5, posY + 3, posZ + 1.5));
					
					for(Entity e : list) {
						e.attackEntityFrom(ModDamageSource.turbofan, 1000);
					}
				}
			}
			if(prevFluidAmount != tank.getFluidAmount() || prevPower != power){
				markDirty();
			}
		}
		
		if(!world.isRemote) {
			PacketDispatcher.wrapper.sendToAllAround(new TETurbofanPacket(pos.getX(), pos.getY(), pos.getZ(), spin, isRunning), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(pos), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] {tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		}
	}
	
	protected boolean inputValidForTank(int tank, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty()){
			if(isValidFluid(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))){
				return true;	
			}
		}
		return false;
	}
	
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return stack.getFluid() == ModForgeFluids.kerosene;
	}

	@Override
	public void ffgeua(BlockPos pos, boolean newTact) {
		
		Library.ffgeua(new BlockPos.MutableBlockPos(pos), newTact, this, world);
	}

	@Override
	public void ffgeuaInit() {
		ffgeua(pos.add(2, 1, -1), getTact());
		ffgeua(pos.add(2, 1, 1), getTact());
		ffgeua(pos.add(1, 1, 2), getTact());
		ffgeua(pos.add(-1, 1, 2), getTact());
		ffgeua(pos.add(-2, 1, 1), getTact());
		ffgeua(pos.add(-2, 1, -1), getTact());
		ffgeua(pos.add(-1, 1, -2), getTact());
		ffgeua(pos.add(1, 1, -2), getTact());
	}

	@Override
	public boolean getTact() {
		if (age >= 0 && age < 10) {
			return true;
		}

		return false;
	}

	@Override
	public long getSPower() {
		return power;
	}

	@Override
	public void setSPower(long i) {
		this.power = i;
	}

	@Override
	public List<IConsumer> getList() {
		return list;
	}

	@Override
	public void clearList() {
		this.list.clear();
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
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (isValidFluid(resource)) {
			if(tank.fill(resource, false) > 0)
				needsUpdate = true;
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
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return true;
		} else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY){
			return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory);
		} else if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}

}
