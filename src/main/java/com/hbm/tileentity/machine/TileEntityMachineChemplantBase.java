package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.inventory.ChemplantRecipes;
import com.hbm.inventory.ChemplantRecipes.EnumChemistryTemplate;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.InventoryUtil;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public abstract class TileEntityMachineChemplantBase extends TileEntityMachineBase implements IEnergyUser, ITickable, IFluidHandler {
	public long power;
	public int[] progress;
	public int[] maxProgress;
	public boolean isProgressing;

	public static class TypedFluidTank {
		protected Fluid type;
		protected final FluidTank tank;

		protected TypedFluidTank(Fluid type, FluidTank tank) {
			this.type = type;
			this.tank = tank;
		}

		public void setType(@Nullable Fluid type) {
			if (type == null) {
				this.tank.setFluid(null);
			}

			if (this.type == type) {
				return;
			}

			this.type = type;
			this.tank.setFluid(null);
		}

		public void writeToNBT(NBTTagCompound nbt) {
			if (this.type != null) {
				nbt.setString("type", this.type.getName());
			}

			this.tank.writeToNBT(nbt);
		}

		public void readFromNBT(NBTTagCompound nbt) {
			if (nbt.hasKey("type")) {
				this.type = FluidRegistry.getFluid(nbt.getString("type"));
			}
			this.tank.readFromNBT(nbt);
		}

		public FluidTank getTank() {
			return tank;
		}

		public Fluid getType() {
			return type;
		}
	}

	public TypedFluidTank[] tanks;

	int consumption = 100;
	int speed = 100;

	public TileEntityMachineChemplantBase(int scount) {
		super(scount);

		int count = this.getRecipeCount();

		progress = new int[count];
		maxProgress = new int[count];

		tanks = new TypedFluidTank[4 * count];
		for (int idx = 0; idx < tanks.length; ++idx) {
			tanks[idx] = new TypedFluidTank(null, new FluidTank(this.getTankCapacity()));
		}
	}

	@Override
	public void update() {
		if (!world.isRemote) {
			int count = this.getRecipeCount();

			this.isProgressing = false;
			this.power = Library.chargeTEFromItems(inventory, 0, this.power, this.getMaxPower());

			for (int idx = 0; idx < count; ++idx) {
				loadItems(idx);
				unloadItems(idx);
			}

			for (int i = 0; i < count; i++) {
				if (!canProcess(i)) {
					this.progress[i] = 0;
				} else {
					isProgressing = true;
					process(i);
				}
			}
		}
	}

	protected boolean canProcess(int index) {
		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);
		if (templateStack.isEmpty() || templateStack.getItem() != ModItems.chemistry_template) {
			return false;
		}

		if (templateStack.getItemDamage() >= EnumChemistryTemplate.values().length) {
			return false;
		}

		List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
		FluidStack[] fluidInputs = ChemplantRecipes.getFluidInputFromTempate(templateStack);
		ItemStack[] itemOutputs = ChemplantRecipes.getChemOutputFromTempate(templateStack);
		FluidStack[] fluidOutputs = ChemplantRecipes.getFluidOutputFromTempate(templateStack);

		setupTanks(fluidInputs, fluidOutputs, index);

		if (this.power < this.consumption) {
			return false;
		}

		if (!hasRequiredFluids(fluidInputs, index)) {
			return false;
		}

		if (!hasSpaceForFluids(fluidOutputs, index)) {
			return false;
		}

		if (!hasRequiredItems(itemInputs, index)) {
			return false;
		}

		if (!hasSpaceForItems(itemOutputs, index)) {
			return false;
		}

		return true;
	}

	private void setupTanks(@Nullable FluidStack[] inputs, @Nullable FluidStack[] outputs, int index) {
		if (inputs != null) {
			for (int i = 0; i < inputs.length; i++) {
				if (inputs[i] != null) {
					tanks[index * 4 + i].setType(inputs[i].getFluid());
				}
			}
		}

		if (outputs != null) {
			for (int i = 0; i < outputs.length; i++) {
				if (outputs[i] != null) {
					tanks[index * 4 + i + 2].setType(outputs[i].getFluid());
				}
			}
		}
	}

	private boolean hasRequiredFluids(@Nullable FluidStack[] inputs, int index) {
		if (inputs == null) {
			return true;
		}

		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] != null) {
				if (tanks[index * 4 + i].tank.getFluidAmount() < inputs[i].amount) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean hasSpaceForFluids(@Nullable FluidStack[] inputs, int index) {
		if (inputs == null) {
			return true;
		}

		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] != null) {
				if (tanks[index * 4 + i + 2].tank.getFluidAmount() + inputs[i].amount > tanks[index * 4 + i + 2].tank.getCapacity()) {
					return false;
				}
			}
		}

		return true;
	}

	private boolean hasRequiredItems(@Nullable List<AStack> inputs, int index) {
		if (inputs == null) {
			return true;
		}

		int[] indices = getSlotIndicesFromIndex(index);
		return InventoryUtil.doesArrayHaveIngredients(inventory, indices[0], indices[1], inputs);
	}

	private boolean hasSpaceForItems(@Nullable ItemStack[] outputs, int index) {
		if (outputs == null) {
			return true;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		return InventoryUtil.doesArrayHaveSpace(inventory, indices[2], indices[3], outputs);
	}

	protected void process(int index) {
		this.power -= this.consumption;
		this.progress[index]++;

		if (inventory.getStackInSlot(0).getItem() == ModItems.meteorite_sword_machined) {
			inventory.setStackInSlot(0, new ItemStack(ModItems.meteorite_sword_machined));
		}

		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);

		List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
		FluidStack[] fluidInputs = ChemplantRecipes.getFluidInputFromTempate(templateStack);
		ItemStack[] itemOutputs = ChemplantRecipes.getChemOutputFromTempate(templateStack);
		FluidStack[] fluidOutputs = ChemplantRecipes.getFluidOutputFromTempate(templateStack);

		this.maxProgress[index] = ChemplantRecipes.getProcessTime(templateStack) * this.speed / 100;

		if (this.progress[index] >= this.maxProgress[index]) {
			consumeFluids(fluidInputs, index);
			produceFluids(fluidOutputs, index);
			consumeItems(itemInputs, index);
			produceItems(itemOutputs, index);
			this.progress[index] = 0;
			this.markDirty();
		}
	}

	private void consumeFluids(@Nullable FluidStack[] inputs, int index) {
		if (inputs == null) {
			return;
		}

		for (int i = 0; i < inputs.length; i++) {
			if (inputs[i] != null) {
				tanks[index * 4 + i].tank.drain(inputs[i].amount, true);
			}
		}
	}

	private void produceFluids(@Nullable FluidStack[] outputs, int index) {
		if (outputs == null) {
			return;
		}

		for (int i = 0; i < outputs.length; i++) {
			if (outputs[i] != null) {
				tanks[index * 4 + i + 2].tank.fill(outputs[i], true);
			}
		}
	}

	private void consumeItems(@Nullable List<AStack> inputs, int index) {
		if (inputs == null) {
			return;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		for (AStack in : inputs) {
			if (in != null)
				InventoryUtil.tryConsumeAStack(inventory, indices[0], indices[1], in);
		}
	}

	private void produceItems(@Nullable ItemStack[] outputs, int index) {
		if (outputs == null) {
			return;
		}

		int[] indices = getSlotIndicesFromIndex(index);

		for (ItemStack out : outputs) {
			if (out != null)
				InventoryUtil.tryAddItemToInventory(inventory, indices[2], indices[3], out.copy());
		}
	}

	private void loadItems(int index) {
		int templateIdx = getTemplateIndex(index);
		ItemStack templateStack = inventory.getStackInSlot(templateIdx);
		if (templateStack.isEmpty() || templateStack.getItem() != ModItems.chemistry_template) {
			return;
		}

		if (templateStack.getItemDamage() < EnumChemistryTemplate.values().length) {
			List<AStack> itemInputs = ChemplantRecipes.getChemInputFromTempate(templateStack);
			if (itemInputs == null) {
				return;
			}

			BlockPos[] positions = getInputPositions();
			int[] indices = getSlotIndicesFromIndex(index);

			for (BlockPos pos : positions) {
				TileEntity te = world.getTileEntity(pos);
				if (te instanceof IInventory) {
					IInventory inv = (IInventory) te;
					ISidedInventory sided = inv instanceof ISidedInventory ? (ISidedInventory) inv : null;

					for (AStack ingredient : itemInputs) {
						if (!InventoryUtil.doesArrayHaveIngredients(inventory, indices[0], indices[1], ingredient)) {
							for (int i = 0; i < inv.getSizeInventory(); ++i) {
								ItemStack stack = inv.getStackInSlot(i);

								if (ingredient.matchesRecipe(stack, true) && (sided == null || sided.canExtractItem(i, stack, EnumFacing.DOWN))) {
									for (int j = indices[0]; j <= indices[1]; ++j) {
										ItemStack cur = inventory.getStackInSlot(j);
										if (!cur.isEmpty() && cur.getCount() < cur.getMaxStackSize() & InventoryUtil.doesStackDataMatch(cur, stack)) {
											inv.decrStackSize(i, 1);
											cur.setCount(cur.getCount() + 1);
											return;
										}
									}

									for (int j = indices[0]; j < indices[1]; ++j) {
										ItemStack cur = inventory.getStackInSlot(j);
										if (cur.isEmpty()) {
											ItemStack stackCopy = stack.copy();
											stackCopy.setCount(1);
											inventory.setStackInSlot(j, stackCopy);
											inv.decrStackSize(i, 1);
											return;
										}
									}
								}
							}
						}
					}
				}
			}

		}
	}

	private void unloadItems(int index) {
		BlockPos[] positions = getOutputPositions();
		int[] indices = getSlotIndicesFromIndex(index);

		for (BlockPos pos : positions) {
			TileEntity te = world.getTileEntity(pos);

			if (te instanceof IInventory) {
				IInventory inv = (IInventory) te;

				for (int i = indices[2]; i <= indices[3]; ++i) {
					ItemStack out = inventory.getStackInSlot(i);

					if (!out.isEmpty()) {
						for (int j = 0; j < inv.getSizeInventory(); j++) {
							if (!inv.isItemValidForSlot(j, out)) {
								continue;
							}

							ItemStack target = inv.getStackInSlot(j);
							if (InventoryUtil.doesStackDataMatch(out, target) && target.getCount() < target.getMaxStackSize() && target.getCount() < inv.getInventoryStackLimit()) {
								inventory.extractItem(i, 1, false);
								target.setCount(target.getCount() + 1);

								return;
							}
						}

						for (int j = 0; j < inv.getSizeInventory(); j++) {
							if (!inv.isItemValidForSlot(j, out)) {
								continue;
							}

							if (inv.getStackInSlot(j).isEmpty() && inv.isItemValidForSlot(j, out)) {
								ItemStack copy = out.copy();
								copy.setCount(1);
								inv.setInventorySlotContents(j, copy);
								inventory.extractItem(i, 1, false);

								return;
							}
						}
					}
				}
			}
		}
	}


	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	protected List<TypedFluidTank> inTanks() {
		List<TypedFluidTank> inTanks = new ArrayList<>();

		for (int i = 0; i < tanks.length; ++i) {
			if (i % 4 < 2) {
				inTanks.add(tanks[i]);
			}
		}

		return inTanks;
	}

	public List<TypedFluidTank> outTanks() {
		List<TypedFluidTank> outTanks = new ArrayList<>();

		for (int i = 0; i < tanks.length; ++i) {
			if (i % 4 > 1) {
				outTanks.add(tanks[i]);
			}
		}

		return outTanks;
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource.amount <= 0) {
			return null;
		}

		List<TypedFluidTank> send = new ArrayList<>();
		for (TypedFluidTank tank : outTanks()) {
			if (tank.type == resource.getFluid()) {
				send.add(tank);
			}
		}

		if (send.isEmpty()) {
			return null;
		}

		int offer = 0;
		List<Integer> weight = new ArrayList<>();
		for (TypedFluidTank tank : send) {
			int drainWeight = tank.tank.getFluidAmount();
			if (drainWeight < 0) {
				drainWeight = 0;
			}

			offer += drainWeight;
			weight.add(drainWeight);
		}

		if (offer <= 0) {
			return null;
		}

		if (!doDrain) {
			return new FluidStack(resource.getFluid(), offer);
		}

		int needed = resource.amount;
		for (int i = 0; i < send.size(); ++i) {
			TypedFluidTank tank = send.get(i);
			int fillWeight = weight.get(i);
			int part = resource.amount * fillWeight / offer;

			FluidStack drained = tank.tank.drain(part, true);
			if (drained != null) {
				needed -= drained.amount;
			}
		}

		for (int i = 0; i < 100 && needed > 0; i++) {
			TypedFluidTank tank = send.get(i);
			if (tank.tank.getFluidAmount() > 0) {
				int total = Math.min(tank.tank.getFluidAmount(), needed);
				tank.tank.drain(total, true);
				needed -= total;
			}
		}

		int drained = resource.amount - needed;
		if (drained != 0) {
			return new FluidStack(resource.getFluid(), drained);
		}

		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		for (TypedFluidTank tank : outTanks()) {
			if (tank.type != null && tank.tank.getFluidAmount() > 0) {
				return tank.tank.drain(maxDrain, doDrain);
			}
		}

		return null;
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		int total = resource.amount;
		int remaining = total;

		if (total <= 0) {
			return 0;
		}

		Fluid inType = resource.getFluid();
		List<TypedFluidTank> rec = new ArrayList<>();
		for (TypedFluidTank tank : inTanks()) {
			if (tank.type == inType) {
				rec.add(tank);
			}
		}

		if (rec.isEmpty()) {
			return 0;
		}

		int demand = 0;
		List<Integer> weight = new ArrayList<>();
		for (TypedFluidTank tank : rec) {
			int fillWeight = tank.tank.getCapacity() - tank.tank.getFluidAmount();
			if (fillWeight < 0) {
				fillWeight = 0;
			}

			demand += fillWeight;
			weight.add(fillWeight);
		}

		if (demand <= 0) {
			return 0;
		}

		if (!doFill) {
			return demand;
		}

		for (int i = 0; i < rec.size(); ++i) {
			TypedFluidTank tank = rec.get(i);
			int fillWeight = weight.get(i);
			int part = (int) (Math.min((long) total, (long) demand) * (long) fillWeight / (long) demand);
			tank.tank.fill(new FluidStack(resource.getFluid(), part), true);
			remaining -= part;
		}

		return remaining;
	}

	protected NBTTagList serializeTanks() {
		NBTTagList tankList = new NBTTagList();
		for (int i = 0; i < tanks.length; ++i) {
			NBTTagCompound tank = new NBTTagCompound();
			tanks[i].writeToNBT(tank);
			tank.setByte("index", (byte) i);

			tankList.appendTag(tank);
		}

		return tankList;
	}

	protected void deserializeTanks(NBTTagList tankList) {
		for (int i = 0; i < tankList.tagCount(); ++i) {
			NBTTagCompound tank = tankList.getCompoundTagAt(i);
			int index = tank.getByte("index");

			tanks[index].readFromNBT(tank);
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("power", power);
		nbt.setIntArray("progress", progress);

		nbt.setTag("tanks", serializeTanks());

		return super.writeToNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.progress = nbt.getIntArray("progress");

		if (progress.length == 0) {
			progress = new int[getRecipeCount()];
		}

		NBTTagList tankList = nbt.getTagList("tanks", 10);
		deserializeTanks(tankList);
	}

	public abstract int getRecipeCount();

	public abstract int getTankCapacity();

	public abstract int getTemplateIndex(int index);

	/**
	 * @param index
	 * @return A size 4 int array containing min input, max input, min output and max output indices in that order.
	 */
	public abstract int[] getSlotIndicesFromIndex(int index);

	public abstract BlockPos[] getInputPositions();

	public abstract BlockPos[] getOutputPositions();
}
