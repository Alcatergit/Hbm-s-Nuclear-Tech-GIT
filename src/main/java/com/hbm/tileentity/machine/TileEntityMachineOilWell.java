package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IConsumer;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineOilWell extends TileEntity implements ITickable, IConsumer, IFluidHandler, ITankPacketAcceptor {

	public ItemStackHandler inventory;

	public long power;
	public int warning;
	public int warning2;
	public static final long maxPower = 100000;
	public int age = 0;
	public int age2 = 0;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	public boolean needsUpdate;

	private Set<BlockPos> processed = new HashSet<BlockPos>();

	// private static final int[] slots_top = new int[] {1};
	// private static final int[] slots_bottom = new int[] {2, 0};
	// private static final int[] slots_side = new int[] {0};
	Random rand = new Random();

	private String customName;

	public TileEntityMachineOilWell() {
		inventory = new ItemStackHandler(6) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
		tanks = new FluidTank[2];
		tankTypes = new Fluid[2];
		tanks[0] = new FluidTank(64000);
		tankTypes[0] = ModForgeFluids.oil;
		tanks[1] = new FluidTank(64000);
		tankTypes[1] = ModForgeFluids.gas;
		needsUpdate = false;
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.oilWell";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 128;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("powerTime");
		this.age = compound.getInteger("age");
		tankTypes[0] = ModForgeFluids.oil;
		tankTypes[1] = ModForgeFluids.gas;
		if(compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		compound.setInteger("age", age);
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@SuppressWarnings("deprecation")
	@Override
	public void update() {
		int timer = 50;

		age++;
		age2++;
		if(age >= timer)
			age -= timer;
		if(age2 >= 20)
			age2 -= 20;
		if(!world.isRemote) {
			int tank0Amount = tanks[0].getFluidAmount();
			int tank1Amount = tanks[1].getFluidAmount();
			if(age2 == 9 || age2 == 19) {
				fillFluidInit(tanks[0]);
				fillFluidInit(tanks[1]);
			}
			
			if(FFUtils.fillFluidContainer(inventory, tanks[0], 1, 2))
				needsUpdate = true;
			if(FFUtils.fillFluidContainer(inventory, tanks[1], 3, 4))
				needsUpdate = true;

			if(needsUpdate) {
				needsUpdate = false;
			}
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);

			if(power >= 100) {

				// operation start

				if(age == timer - 1) {
					warning = 0;

					// warning 0, green: derrick is operational
					// warning 1, red: derrick is full, has no power or the
					// drill is jammed
					// warning 2, yellow: drill has reached max depth

					for(int i = pos.getY() - 1; i > pos.getY() - 1 - 100; i--) {

						if(i <= 5) {
							// Code 2: The drilling ended
							warning = 2;
							break;
						}

						Block b = world.getBlockState(new BlockPos(pos.getX(), i, pos.getZ())).getBlock();
						if(b == ModBlocks.oil_pipe)
							continue;

						if((b.isReplaceable(world, new BlockPos(pos.getX(), i, pos.getZ())) || b.getExplosionResistance(null) < 100) && !(b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty)) {
							world.setBlockState(new BlockPos(pos.getX(), i, pos.getZ()), ModBlocks.oil_pipe.getDefaultState());

							// Code 2: The drilling ended
							if(i == pos.getY() - 100)
								warning = 2;
							break;

						} else if(this.tanks[0].getFluidAmount() < this.tanks[0].getCapacity() && this.tanks[1].getFluidAmount() < this.tanks[1].getCapacity()) {
							if(succ(pos.getX(), i, pos.getZ())) {

								this.tanks[0].fill(new FluidStack(tankTypes[0], 500), true);
								this.tanks[1].fill(new FluidStack(tankTypes[1], (100 + rand.nextInt(401))), true);
								needsUpdate = true;

								ExplosionLarge.spawnOilSpills(world, pos.getX() + 0.5F, pos.getY() + 5.5F, pos.getZ() + 0.5F, 3);
								world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_SWIM, SoundCategory.BLOCKS, 2.0F, 0.5F);

								break;
							} else {
								world.setBlockState(new BlockPos(pos.getX(), i, pos.getZ()), ModBlocks.oil_pipe.getDefaultState());
								break;
							}

						} else {
							// Code 1: Drill jammed
							warning = 1;
							break;
						}
					}
				}

				// operation end

				power -= 100;
			} else {
				warning = 1;
			}

			warning2 = 0;
			if(tanks[1].getFluidAmount() > 0) {
				if(inventory.getStackInSlot(5).getItem() == ModItems.fuse || inventory.getStackInSlot(5).getItem() == ModItems.screwdriver) {
					warning2 = 2;
					tanks[1].drain(50, true);
					needsUpdate = true;
					world.spawnEntity(new EntityGasFX(world, pos.getX() + 0.5F, pos.getY() + 6.5F, pos.getZ() + 0.5F, 0.0, 0.0, 0.0));
				} else {
					warning2 = 1;
				}
			}

			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] { tanks[0], tanks[1] }), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			if(tank0Amount != tanks[0].getFluidAmount() || tank1Amount != tanks[1].getFluidAmount()){
				markDirty();
			}
		}
	}

	public boolean succ(int x, int y, int z) {

		list.clear();

		succ1(x, y, z);
		succ2(x, y, z);

		if(!list.isEmpty()) {

			int i = rand.nextInt(list.size());
			int a = list.get(i)[0];
			int b = list.get(i)[1];
			int c = list.get(i)[2];
			BlockPos abc = new BlockPos(a, b, c);

			if(world.getBlockState(abc).getBlock() == ModBlocks.ore_oil) {

				world.setBlockState(abc, ModBlocks.ore_oil_empty.getDefaultState());
				return true;
			}
		}

		processed.clear();

		return false;
	}

	public void succInit1(int x, int y, int z) {
		succ1(x + 1, y, z);
		succ1(x - 1, y, z);
		succ1(x, y + 1, z);
		succ1(x, y - 1, z);
		succ1(x, y, z + 1);
		succ1(x, y, z - 1);
	}

	public void succInit2(int x, int y, int z) {
		succ2(x + 1, y, z);
		succ2(x - 1, y, z);
		succ2(x, y + 1, z);
		succ2(x, y - 1, z);
		succ2(x, y, z + 1);
		succ2(x, y, z - 1);
	}

	List<int[]> list = new ArrayList<int[]>();

	public void succ1(int x, int y, int z) {
		BlockPos bp = new BlockPos(x, y, z);
		if(world.getBlockState(bp).getBlock() == ModBlocks.ore_oil_empty && !processed.contains(bp)) {
			processed.add(bp);
			succInit1(x, y, z);
		}
	}

	public void succ2(int x, int y, int z) {
		BlockPos bp = new BlockPos(x, y, z);
		if(world.getBlockState(bp).getBlock() == ModBlocks.ore_oil_empty && processed.contains(bp)) {
			processed.remove(bp);
			succInit2(x, y, z);
		} else if(world.getBlockState(bp).getBlock() == ModBlocks.ore_oil) {
			list.add(new int[] { x, y, z });
		}
	}

	public void fillFluidInit(FluidTank tank) {
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(-2, 0, 0), 2000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(2, 0, 0), 2000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 0, -2), 2000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 0, 2), 2000) || needsUpdate;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0] };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource == null) {
			return null;
		} else if(resource.getFluid() == tankTypes[0]) {
			int prevAmount = tanks[0].getFluidAmount();
			FluidStack drained = tanks[0].drain(resource.amount, doDrain);
			if(tanks[0].getFluidAmount() != prevAmount)
				needsUpdate = true;
			return drained;
		} else if(resource.getFluid() == tankTypes[1]) {
			int prevAmount = tanks[1].getFluidAmount();
			FluidStack drained = tanks[1].drain(resource.amount, doDrain);
			if(tanks[1].getFluidAmount() != prevAmount)
				needsUpdate = true;
			return drained;
		} else {
			return null;
		}
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tanks[0].getFluidAmount() > 0) {
			int prevAmount = tanks[0].getFluidAmount();
			FluidStack drained = tanks[0].drain(maxDrain, doDrain);
			if(tanks[0].getFluidAmount() != prevAmount)
				needsUpdate = true;
			return drained;
		} else if(tanks[1].getFluidAmount() > 0) {
			int prevAmount = tanks[1].getFluidAmount();
			FluidStack drained = tanks[1].drain(maxDrain, doDrain);
			if(tanks[1].getFluidAmount() != prevAmount)
				needsUpdate = true;
			return drained;
		} else {
			return null;
		}
	}

	@Override
	public void setPower(long i) {
		power = i;

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
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 2) {
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
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
