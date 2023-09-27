package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineReactor;
import com.hbm.config.MobConfig;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemFuelRod;
import com.hbm.items.tool.ItemSwordMeteorite;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxGaugePacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineReactorSmall extends TileEntity implements ITickable, IFluidHandler, ITankPacketAcceptor {

	// 4 blocks when extended + 5 pixels tall
	private static final AxisAlignedBB SMALL_REACTOR_BB = new AxisAlignedBB(0, 0, 0, 1, 5, 1);

	public ItemStackHandler inventory;

	public int hullHeat;
	public static final int maxHullHeat = 100000;
	public int coreHeat;
	public static final int maxCoreHeat = 50000;
	public int rods;
	public static final int rodsMax = 100;
	public boolean retracting = true;
	public int age = 0;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	public boolean needsUpdate;
	public int compression = 0;

	private double decayMod = 1.0D;
	private double coreHeatMod = 1.0D;
	private double hullHeatMod = 1.0D;
	private double conversionMod = 1.0D;

	// private static final int[] slots_top = new int[] { 0 };
	// private static final int[] slots_bottom = new int[] { 0, 1, 2, 3, 4, 5,
	// 6, 7, 8, 9, 10, 11, 13, 15, 16 };
	// private static final int[] slots_side = new int[] { 0, 1, 2, 3, 4, 5, 6,
	// 7, 8, 9, 10, 11, 12, 14, 16 };

	private String customName;

	public TileEntityMachineReactorSmall() {
		inventory = new ItemStackHandler(16) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}

			@Override
			public boolean isItemValid(int i, ItemStack itemStack) {
				if(i == 0 || i == 1 || i == 2 || i == 3 || i == 4 || i == 5 || i == 6 || i == 7 || i == 8 || i == 9 || i == 10 || i == 11)
					if(itemStack.getItem() instanceof ItemFuelRod)
						return true;
				if(i == 12)
					if(FFUtils.containsFluid(itemStack, FluidRegistry.WATER))
						return true;
				if(i == 14)
					if(FFUtils.containsFluid(itemStack, ModForgeFluids.coolant))
						return true;
				return false;
			}

			@Override
			public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
				if(isItemValid(slot, stack))
					return super.insertItem(slot, stack, simulate);
				return stack;
			}
		};

		tanks = new FluidTank[3];
		tankTypes = new Fluid[3];
		tanks[0] = new FluidTank(32000);
		tankTypes[0] = FluidRegistry.WATER;
		tanks[1] = new FluidTank(16000);
		tankTypes[1] = ModForgeFluids.coolant;
		tanks[2] = new FluidTank(8000);
		tankTypes[2] = ModForgeFluids.steam;
		needsUpdate = true;

	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.reactorSmall";
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
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64;
		}
	}

	public void compress(int level) {
		if(level == compression)
			return;
		if(level >= 0 && level < 3) {
			if(compression == 0) {
				if(level == 1) {
					tankTypes[2] = ModForgeFluids.hotsteam;
					int newAmount = (int) (tanks[2].getFluidAmount() / 10D);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
				if(level == 2) {
					tankTypes[2] = ModForgeFluids.superhotsteam;
					int newAmount = (int) (tanks[2].getFluidAmount() / 100D);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
			}
			if(compression == 1) {
				if(level == 0) {
					tankTypes[2] = ModForgeFluids.steam;
					int newAmount = (int) (tanks[2].getFluidAmount() * 10);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
				if(level == 2) {
					tankTypes[2] = ModForgeFluids.superhotsteam;
					int newAmount = (int) (tanks[2].getFluidAmount() / 10D);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
			}
			if(compression == 2) {
				if(level == 0) {
					tankTypes[2] = ModForgeFluids.steam;
					int newAmount = (int) (tanks[2].getFluidAmount() * 100);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
				if(level == 1) {
					tankTypes[2] = ModForgeFluids.hotsteam;
					int newAmount = (int) (tanks[2].getFluidAmount() * 10D);
					tanks[2].drain(tanks[2].getCapacity(), true);
					tanks[2].fill(new FluidStack(tankTypes[2], newAmount), true);
				}
			}

			compression = level;
		}
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			age++;
			if(age >= 20) {
				age = 0;
			}

			if(needsUpdate) {
				needsUpdate = false;
			}
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] { tanks[0], tanks[1], tanks[2] }), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

			if(age == 9 || age == 19)
				fillFluidInit(tanks[2]);

			if(inputValidForTank(0, 12))
				FFUtils.fillFromFluidContainer(inventory, tanks[0], 12, 13);
			if(inputValidForTank(1, 14))
				FFUtils.fillFromFluidContainer(inventory, tanks[1], 14, 15);

			if(retracting && rods > 0) {

				if(rods == rodsMax)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStart, SoundCategory.BLOCKS, 1.0F, 0.75F);
				rods--;

				if(rods == 0)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStop, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
			if(!retracting && rods < rodsMax) {

				if(rods == 0)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStart, SoundCategory.BLOCKS, 1.0F, 0.75F);

				rods++;

				if(rods == rodsMax)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStop, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}

			if(rods >= rodsMax)
				
				for(int i = 0; i < 12; i++) {
					if(inventory.getStackInSlot(i).getItem() instanceof ItemFuelRod)
						decay(i);
				}

			coreHeatMod = 1.0;
			hullHeatMod = 1.0;
			conversionMod = 1.0;
			decayMod = 1.0;

			getInteractions();

			if(this.coreHeat > 0 && this.tanks[1].getFluidAmount() > 0 && this.hullHeat < this.maxHullHeat) {
				this.hullHeat += this.coreHeat * 0.175 * hullHeatMod;
				this.coreHeat -= this.coreHeat * 0.1;

				this.tanks[1].drain(10, true);
			}

			if(this.hullHeat > maxHullHeat) {
				this.hullHeat = maxHullHeat;
			}

			if(this.hullHeat > 0 && this.tanks[0].getFluidAmount() > 0) {
				generateSteam();
				this.hullHeat -= this.hullHeat * 0.085;
			}

			if(this.coreHeat > maxCoreHeat) {
				this.explode();
			}

			if(rods > 0 && coreHeat > 0 && !isContained()) {

				/*List<Entity> list = (List<Entity>) world.getEntitiesWithinAABBExcludingEntity(null,
						AxisAlignedBB.getBoundingBox(xCoord + 0.5 - 5, yCoord + 1.5 - 5, zCoord + 0.5 - 5,
								xCoord + 0.5 + 5, yCoord + 1.5 + 5, zCoord + 0.5 + 5));
				
				for (Entity e : list) {
					if (e instanceof EntityLivingBase)
						Library.applyRadiation((EntityLivingBase)e, 80, 24, 60, 19);
				}*/

				float rad = (float) coreHeat / (float) maxCoreHeat * 50F;
				RadiationSavedData.incrementRad(world, pos, rad, rad * 4);
			}

			detectAndSendChanges();
		}
	}

	private void explode() {
		for(int i = 0; i < inventory.getSlots(); i++) {
			inventory.setStackInSlot(i, ItemStack.EMPTY);
		}

		world.setBlockToAir(pos);
		world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 18.0F, true);
		ExplosionNukeGeneric.waste(world, pos.getX(), pos.getY(), pos.getZ(), 35);
		world.setBlockState(pos, ModBlocks.block_corium_cobble.getDefaultState());

		RadiationSavedData.incrementRad(world, pos, 1000F, 2000F);
		if(MobConfig.enableElementals) {
			List<EntityPlayer> players = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5).grow(100, 100, 100));

			for(EntityPlayer player : players) {
				player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setBoolean("radMark", true);
			}
		}
	}

	@SuppressWarnings("deprecation")
	private boolean isContained() {
		boolean side1 = blocksRad(pos.add(1, 1, 0));
		if(!side1){
			return false;
		}

		boolean side2 = blocksRad(pos.add(-1, 1, 0));
		if(!side2){
			return false;
		}

		boolean side3 = blocksRad(pos.add(0, 1, 1));
		if(!side3){
			return false;
		}

		boolean side4 = blocksRad(pos.add(0, 1, -1));
		if(!side4){
			return false;
		}

		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean blocksRad(BlockPos pos) {

		Block b = world.getBlockState(pos).getBlock();

		if(b instanceof IRadResistantBlock)
			return ((IRadResistantBlock)b).isRadResistant(world, pos);

		if(b == Blocks.FLOWING_WATER || b == Blocks.WATER)
			return true;

		return false;
	}

	private void generateSteam() {

		// function of SHS produced per tick
		// maxes out at heat% * tank capacity / 20
		double steam = (((double) hullHeat / (double) maxHullHeat) * ((double) tanks[2].getCapacity() / 50D)) * conversionMod;

		double water = steam;

		if(tankTypes[2] == ModForgeFluids.steam) {
			water /= 100D;
		} else if(tankTypes[2] == ModForgeFluids.hotsteam) {
			water /= 10D;
		}

		tanks[0].drain((int) Math.ceil(water), true);
		tanks[2].fill(new FluidStack(tankTypes[2], (int) Math.floor(steam)), true);

	}

	private void getInteractions() {

		getInteractionForBlock(pos.add(1, 1, 0));
		getInteractionForBlock(pos.add(-1, 1, 0));
		getInteractionForBlock(pos.add(0, 1, 1));
		getInteractionForBlock(pos.add(0, 1, -1));

		TileEntity te1 = world.getTileEntity(pos.add(2, 0, 0));
		TileEntity te2 = world.getTileEntity(pos.add(-2, 0, 0));
		TileEntity te3 = world.getTileEntity(pos.add(0, 0, 2));
		TileEntity te4 = world.getTileEntity(pos.add(0, 0, -2));

		boolean b1 = blocksRad(pos.add(1, 1, 0));
		boolean b2 = blocksRad(pos.add(-1, 1, 0));
		boolean b3 = blocksRad(pos.add(0, 1, 1));
		boolean b4 = blocksRad(pos.add(0, 1, -1));

		TileEntityMachineReactorSmall[] reactors = new TileEntityMachineReactorSmall[4];

		reactors[0] = ((te1 instanceof TileEntityMachineReactorSmall && !b1) ? (TileEntityMachineReactorSmall) te1 : null);
		reactors[1] = ((te2 instanceof TileEntityMachineReactorSmall && !b2) ? (TileEntityMachineReactorSmall) te2 : null);
		reactors[2] = ((te3 instanceof TileEntityMachineReactorSmall && !b3) ? (TileEntityMachineReactorSmall) te3 : null);
		reactors[3] = ((te4 instanceof TileEntityMachineReactorSmall && !b4) ? (TileEntityMachineReactorSmall) te4 : null);

		for(int i = 0; i < 4; i++) {

			if(reactors[i] != null && reactors[i].rods >= rodsMax && reactors[i].getRodCount() > 0) {
				decayMod += reactors[i].getRodCount() / 2D;
			}
		}
	}

	private void getInteractionForBlock(BlockPos pos) {

		Block b = world.getBlockState(pos).getBlock();
		TileEntity te = world.getTileEntity(pos);

		if(b == Blocks.LAVA || b == Blocks.FLOWING_LAVA) {
			hullHeatMod *= 3;
			conversionMod *= 0.5;

		} else if(b == Blocks.REDSTONE_BLOCK) {
			conversionMod *= 1.15;

		} else if(b == ModBlocks.block_lead) {
			decayMod += 1;

		} else if(b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
			tanks[0].fill(new FluidStack(tankTypes[0], 25), true);
		} else if(b == ModBlocks.block_niter || b == ModBlocks.block_niter_reinforced) {
			if(tanks[0].getFluidAmount() >= 50 && tanks[1].getFluidAmount() + 5 <= tanks[1].getCapacity()) {
				tanks[0].drain(50, true);
				tanks[1].fill(new FluidStack(tankTypes[1], 5), true);
			}
		} else if(b == ModBlocks.machine_reactor) {

			int[] pos1 = ((MachineReactor) ModBlocks.machine_reactor).findCore(world, pos.getX(), pos.getY(), pos.getZ());

			if(pos1 != null) {

				TileEntity tile = world.getTileEntity(new BlockPos(pos1[0], pos1[1], pos1[2]));

				if(tile instanceof TileEntityMachineReactor) {

					TileEntityMachineReactor reactor = (TileEntityMachineReactor) tile;

					if(reactor.charge <= 1 && this.hullHeat > 0) {
						reactor.charge = 1;
						reactor.heat = (int) Math.floor(hullHeat * 4 / maxHullHeat) + 1;
					}
				}
			}

		} else if(te instanceof TileEntityNukeFurnace) {
			TileEntityNukeFurnace reactor = (TileEntityNukeFurnace) te;
			if(reactor.dualPower < 1 && this.coreHeat > 0)
				reactor.dualPower = 1;

		} else if(b == ModBlocks.block_uranium) {
			coreHeatMod *= 1.05;

		} else if(b == Blocks.COAL_BLOCK) {
			hullHeatMod *= 1.1;

		} else if(b == ModBlocks.block_beryllium) {
			hullHeatMod *= 0.95;
			conversionMod *= 1.05;

		} else if(b == ModBlocks.block_schrabidium) {
			decayMod += 1;
			conversionMod *= 1.25;
			hullHeatMod *= 1.1;

		} else if(b == ModBlocks.block_waste) {
			decayMod += 3;
		}
	}

	private void decay(int id) {
		if(id > 11)
			return;

		int decay = getNeightbourCount(id) + 1;

		decay *= decayMod;

		for(int i = 0; i < decay; i++) {
			ItemFuelRod rod = ((ItemFuelRod) inventory.getStackInSlot(id).getItem());
			this.coreHeat += rod.getHeatPerTick() * coreHeatMod;
			ItemFuelRod.setLifetime(inventory.getStackInSlot(id), ItemFuelRod.getLifeTime(inventory.getStackInSlot(id)) + 1);

			if(ItemFuelRod.getLifeTime(inventory.getStackInSlot(id)) > ((ItemFuelRod) inventory.getStackInSlot(id).getItem()).getMaxLifeTime()) {
				onRunOut(id);
				return;
			}
		}
	}

	private void onRunOut(int id) {

		// System.out.println("aaa");

		Item item = inventory.getStackInSlot(id).getItem();

		if(item == ModItems.rod_uranium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_uranium_fuel_depleted));

		} else if(item == ModItems.rod_thorium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_thorium_fuel_depleted));

		} else if(item == ModItems.rod_plutonium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_plutonium_fuel_depleted));

		} else if(item == ModItems.rod_mox_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_mox_fuel_depleted));

		} else if(item == ModItems.rod_schrabidium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_schrabidium_fuel_depleted));

		} else if(item == ModItems.rod_dual_uranium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_dual_uranium_fuel_depleted));

		} else if(item == ModItems.rod_dual_thorium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_dual_thorium_fuel_depleted));

		} else if(item == ModItems.rod_dual_plutonium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_dual_plutonium_fuel_depleted));

		} else if(item == ModItems.rod_dual_mox_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_dual_mox_fuel_depleted));

		} else if(item == ModItems.rod_dual_schrabidium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_dual_schrabidium_fuel_depleted));

		} else if(item == ModItems.rod_quad_uranium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_quad_uranium_fuel_depleted));

		} else if(item == ModItems.rod_quad_thorium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_quad_thorium_fuel_depleted));

		} else if(item == ModItems.rod_quad_plutonium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_quad_plutonium_fuel_depleted));

		} else if(item == ModItems.rod_quad_mox_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_quad_mox_fuel_depleted));

		} else if(item == ModItems.rod_quad_schrabidium_fuel) {
			inventory.setStackInSlot(id, new ItemStack(ModItems.rod_quad_schrabidium_fuel_depleted));
		}
	}

	private int getNeightbourCount(int id) {

		int[] neighbours = this.getNeighbouringSlots(id);

		if(neighbours == null)
			return 0;

		int count = 0;

		for(int i = 0; i < neighbours.length; i++)
			if(hasFuelRod(neighbours[i]))
				count++;

		return count;

	}

	private boolean hasFuelRod(int id) {
		if(id > 11)
			return false;

		if(inventory.getStackInSlot(id) != ItemStack.EMPTY)
			return inventory.getStackInSlot(id).getItem() instanceof ItemFuelRod;

		return false;
	}

	protected boolean inputValidForTank(int tank, int slot) {
		if(inventory.getStackInSlot(slot) != ItemStack.EMPTY && tanks[tank] != null) {
			if(isValidFluidForTank(tank, FluidUtil.getFluidContained(inventory.getStackInSlot(slot)))) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidFluidForTank(int tank, FluidStack stack) {
		if(stack == null || tanks[tank] == null)
			return false;
		return stack.getFluid() == tankTypes[tank];
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		coreHeat = nbt.getInteger("heat");
		detectHeat = coreHeat + 1;
		hullHeat = nbt.getInteger("hullHeat");
		detectHullHeat = hullHeat + 1;
		rods = nbt.getInteger("rods");
		detectRods = rods + 1;
		retracting = nbt.getBoolean("ret");
		detectRetracting = !retracting;

		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		if(nbt.hasKey("tanks"))
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		tankTypes[0] = FluidRegistry.WATER;
		tankTypes[1] = ModForgeFluids.coolant;
		compression = nbt.getInteger("compression");
		detectCompression = compression + 1;

		if(compression == 0) {
			tankTypes[2] = ModForgeFluids.steam;
		} else if(compression == 1) {
			tankTypes[2] = ModForgeFluids.hotsteam;
		} else if(compression == 2) {
			tankTypes[2] = ModForgeFluids.superhotsteam;
		}
		super.readFromNBT(nbt);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setInteger("heat", coreHeat);
		nbt.setInteger("hullHeat", hullHeat);
		nbt.setInteger("rods", rods);
		nbt.setBoolean("ret", retracting);
		nbt.setInteger("compression", compression);
		nbt.setTag("inventory", inventory.serializeNBT());
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(nbt);
	}

	public int getCoreHeatScaled(int i) {
		return (coreHeat * i) / maxCoreHeat;
	}

	public int getHullHeatScaled(int i) {
		return (hullHeat * i) / maxHullHeat;
	}

	public int getSteamScaled(int i) {
		return (tanks[2].getFluidAmount() * i) / tanks[2].getCapacity();
	}

	public boolean hasCoreHeat() {
		return coreHeat > 0;
	}

	public boolean hasHullHeat() {
		return hullHeat > 0;
	}

	private int[] getNeighbouringSlots(int id) {

		switch(id) {
		case 0:
			return new int[] { 1, 5 };
		case 1:
			return new int[] { 0, 6 };
		case 2:
			return new int[] { 3, 7 };
		case 3:
			return new int[] { 2, 4, 8 };
		case 4:
			return new int[] { 3, 9 };
		case 5:
			return new int[] { 0, 6, 0xA };
		case 6:
			return new int[] { 1, 5, 0xB };
		case 7:
			return new int[] { 2, 8 };
		case 8:
			return new int[] { 3, 7, 9 };
		case 9:
			return new int[] { 4, 8 };
		case 10:
			return new int[] { 5, 0xB };
		case 11:
			return new int[] { 6, 0xA };
		}

		return null;
	}

	public int getFuelPercent() {

		if(getRodCount() == 0)
			return 0;

		int rodMax = 0;
		int rod = 0;

		for(int i = 0; i < 12; i++) {

			if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() instanceof ItemFuelRod) {
				rodMax += ((ItemFuelRod) inventory.getStackInSlot(i).getItem()).getMaxLifeTime();
				rod += ((ItemFuelRod) inventory.getStackInSlot(i).getItem()).getMaxLifeTime() - ItemFuelRod.getLifeTime(inventory.getStackInSlot(i));
			}
		}

		if(rodMax == 0)
			return 0;

		return rod * 100 / rodMax;
	}

	public int getRodCount() {

		int count = 0;

		for(int i = 0; i < 12; i++) {

			if(inventory.getStackInSlot(i) != ItemStack.EMPTY && inventory.getStackInSlot(i).getItem() instanceof ItemFuelRod)
				count++;
		}

		return count;
	}

	public void fillFluidInit(FluidTank tank) {
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.west(), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.east(), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.north(), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.south(), 4000) || needsUpdate;

		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(-1, 2, 0), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(1, 2, 0), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 2, -1), 4000) || needsUpdate;
		needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 2, 1), 4000) || needsUpdate;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0] };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null) {
			return 0;
		} else if(resource.getFluid() == tankTypes[0]) {
			return tanks[0].fill(resource, doFill);
		} else if(resource.getFluid() == tankTypes[1]) {
			return tanks[1].fill(resource, doFill);
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource != null && resource.getFluid() == tankTypes[2]) {
			return tanks[2].drain(resource.amount, doDrain);
		} else {
			return null;
		}
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(tanks[2].getFluidAmount() > 0) {
			return tanks[2].drain(maxDrain, doDrain);
		} else {
			return null;
		}
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 3) {
			return;
		} else {
			tanks[0].readFromNBT(tags[0]);
			tanks[1].readFromNBT(tags[1]);
			tanks[2].readFromNBT(tags[2]);
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return SMALL_REACTOR_BB.offset(pos);
	}

	@Override
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this) : super.getCapability(capability, facing);
	}

	private int detectHeat;
	private int detectHullHeat;
	private int detectRods;
	private boolean detectRetracting;
	private int detectCompression;
	private FluidTank[] detectTanks = new FluidTank[] { null, null, null };

	private void detectAndSendChanges() {
		boolean mark = false;
		if(detectHeat != coreHeat) {
			mark = true;
			detectHeat = coreHeat;
		}
		if(detectHullHeat != hullHeat) {
			mark = true;
			detectHullHeat = hullHeat;
		}
		if(detectRods != rods) {
			mark = true;
			detectRods = rods;
		}
		if(detectRetracting != retracting) {
			mark = true;
			detectRetracting = retracting;
		}
		if(detectCompression != compression) {
			mark = true;
			detectCompression = compression;
		}
		if(!FFUtils.areTanksEqual(tanks[0], detectTanks[0])) {
			mark = true;
			needsUpdate = true;
			detectTanks[0] = FFUtils.copyTank(tanks[0]);
		}
		if(!FFUtils.areTanksEqual(tanks[1], detectTanks[1])) {
			mark = true;
			needsUpdate = true;
			detectTanks[1] = FFUtils.copyTank(tanks[1]);
		}
		if(!FFUtils.areTanksEqual(tanks[2], detectTanks[2])) {
			mark = true;
			needsUpdate = true;
			detectTanks[2] = FFUtils.copyTank(tanks[2]);
		}
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), coreHeat, 2), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), hullHeat, 3), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), rods, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
		PacketDispatcher.wrapper.sendToAllAround(new AuxGaugePacket(pos.getX(), pos.getY(), pos.getZ(), retracting ? 1 : 0, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		if(mark)
			markDirty();
	}

	public boolean[] getSubmergedDirection() {
		boolean[] sides = new boolean[4];
		sides[0] = world.getBlockState(pos.add(0, 1, -1)).getMaterial() == Material.WATER;//North
		sides[1] = world.getBlockState(pos.add(0, 1, 1)).getMaterial() == Material.WATER;//South
		sides[2] = world.getBlockState(pos.add(1, 1, 0)).getMaterial() == Material.WATER;//East
		sides[3] = world.getBlockState(pos.add(-1, 1, 0)).getMaterial() == Material.WATER;//West
		return sides;
	}

	public boolean isSubmerged() {
		return world.getBlockState(pos.add(0, 1, -1)).getMaterial() == Material.WATER || //North
		world.getBlockState(pos.add(0, 1, 1)).getMaterial() == Material.WATER || //South
		world.getBlockState(pos.add(1, 1, 0)).getMaterial() == Material.WATER || //East
		world.getBlockState(pos.add(-1, 1, 0)).getMaterial() == Material.WATER;//West
	}

}
