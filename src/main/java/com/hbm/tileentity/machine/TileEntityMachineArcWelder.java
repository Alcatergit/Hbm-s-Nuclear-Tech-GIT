package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMachineArcWelder;
import com.hbm.inventory.gui.GUIMachineArcWelder;
import com.hbm.inventory.ArcWelderRecipes;
import com.hbm.inventory.ArcWelderRecipes.ArcWelderRecipe;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.*;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class TileEntityMachineArcWelder extends TileEntityMachineBase implements ITickable, IGUIProvider, IFluidHandler, IEnergyUser, ITankPacketAcceptor {

	public boolean wasOn;
	public long power;
	public long maxPower = 2_000;
	public long consumption;

	public int progress;
	public int processTime = 1;

	public FluidTank tank;
	public ItemStack display;

	public UpgradeManager upgradeManager = new UpgradeManager();

	public TileEntityMachineArcWelder() {
		super(7);
		this.tank = new FluidTank(24_000);
	}

	@Override
	public String getName() {
		return "container.machineArcWelder";
	}

//	@Override
//	public void setInventorySlotContents(int i, ItemStack stack) {
//		super.setInventorySlotContents(i, stack);
//
//		if(stack != null && stack.getItem() instanceof ItemMachineUpgrade && i >= 6 && i <= 7) {
//			worldObj.playSoundEffect(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, "hbm:item.upgradePlug", 1.0F, 1.0F);
//		}
//	}

	@Override
	public void update() {

		if(!world.isRemote) {
			this.wasOn = false;
			this.power = Library.chargeTEFromItems(inventory, 4, this.getPower(), this.getMaxPower());

			if(world.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(world, pos.getPos(), pos.getDir());
					if(tank.getFluidAmount() > 0) FFUtils.fillFluid(this, tank, world, pos.getPos(), tank.getCapacity() >> 1);
				}
			}
			
			ArcWelderRecipe recipe = ArcWelderRecipes.getRecipe(new ItemStack[] {
					inventory.getStackInSlot(0),
					inventory.getStackInSlot(1),
					inventory.getStackInSlot(2)
			});
			long intendedMaxPower;

			upgradeManager.eval(inventory, 5, 6);
			int redLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int blueLevel = upgradeManager.getLevel(UpgradeType.POWER);

			if(recipe != null) {
				this.display = recipe.output.copy();
				this.processTime = recipe.duration - (recipe.duration * redLevel / 6) + (recipe.duration * blueLevel / 3);
				this.consumption = recipe.consumption + (recipe.consumption * redLevel) - (recipe.consumption * blueLevel / 6);
				intendedMaxPower = recipe.consumption * 20;

				if(canProcess(recipe)) {
					this.wasOn = true;
					this.progress++;
					this.power -= this.consumption;

					if(progress >= processTime) {
						this.progress = 0;
						this.consumeItems(recipe);

						if(inventory.getStackInSlot(3).isEmpty()) {
							inventory.setStackInSlot(3, recipe.output.copy());
						} else {
							inventory.getStackInSlot(3).grow(recipe.output.getCount());
						}

						this.markDirty();
					}

				} else {
					this.progress = 0;
				}

			} else {
				this.display = null;
				this.progress = 0;
				this.consumption = 100;
				intendedMaxPower = 2000;
			}

			this.maxPower = Math.max(intendedMaxPower, power);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setLong("maxPower", maxPower);
			data.setLong("power", power);
			data.setInteger("progress", progress);
			data.setInteger("processTime", processTime);
			data.setBoolean("wasOn", wasOn);
			if(display != null) {
				data.setInteger("itemId", Item.getIdFromItem(display.getItem()));
				data.setInteger("itemMeta", display.getItemDamage());
			} else {
				data.setInteger("itemId", -1);
			}
			tank.writeToNBT(data);
			this.networkPack(data, 25);
		} else if (wasOn) {
			if(world.getTotalWorldTime() % 2 == 0) {
				ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", world.getTotalWorldTime() % 20 == 0 ? "tau" : "hadron");
				data.setByte("count", (byte) 5);
				data.setDouble("posX", pos.getX() + 0.5 - dir.offsetX * 0.5);
				data.setDouble("posY", pos.getY() + 1.25);
				data.setDouble("posZ", pos.getZ() + 0.5 - dir.offsetZ * 0.5);
				MainRegistry.proxy.effectNT(data);
			}
		}
	}

	public boolean canProcess(ArcWelderRecipe recipe) {

		if(this.power < this.consumption) return false;

		if(recipe.fluid != null && tank.getFluid() != null) {
			if(this.tank.getFluid().getFluid() != recipe.fluid.getFluid()) return false;
			if(this.tank.getFluidAmount() < recipe.fluid.amount) return false;
		}

		if(!inventory.getStackInSlot(3).isEmpty()) {
			ItemStack slot3 = inventory.getStackInSlot(3);
			if(slot3.getItem() != recipe.output.getItem()) return false;
			if(slot3.getItemDamage() != recipe.output.getItemDamage()) return false;
			return slot3.getCount() + recipe.output.getCount() <= slot3.getMaxStackSize();
		}

		return true;
	}

	public void consumeItems(ArcWelderRecipe recipe) {

		for(AStack aStack : recipe.ingredients) {

			for(int i = 0; i < 3; i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if(aStack.matchesRecipe(stack, true) && stack.getCount() >= aStack.count()) {
					stack.shrink(aStack.count());
					if(stack.getCount() == 0) inventory.setStackInSlot(i, ItemStack.EMPTY);
					break;
				}
			}
		}

		if(recipe.fluid != null) {
			this.tank.drain(recipe.fluid.amount, true);
		}
	}

	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(pos.getX() + dir.offsetX, pos.getY(), pos.getZ() + dir.offsetZ, dir),
				new DirPos(pos.getX() + dir.offsetX + rot.offsetX, pos.getY(), pos.getZ() + dir.offsetZ + rot.offsetZ, dir),
				new DirPos(pos.getX() + dir.offsetX - rot.offsetX, pos.getY(), pos.getZ() + dir.offsetZ - rot.offsetZ, dir),
				new DirPos(pos.getX() - dir.offsetX * 2, pos.getY(), pos.getZ() - dir.offsetZ * 2, dir.getOpposite()),
				new DirPos(pos.getX() - dir.offsetX * 2 + rot.offsetX, pos.getY(), pos.getZ() - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite()),
				new DirPos(pos.getX() - dir.offsetX * 2 - rot.offsetX, pos.getY(), pos.getZ() - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
				new DirPos(pos.getX() + rot.offsetX * 2, pos.getY(), pos.getZ() + rot.offsetZ * 2, rot),
				new DirPos(pos.getX() - dir.offsetX + rot.offsetX * 2, pos.getY(), pos.getZ() - dir.offsetZ + rot.offsetZ * 2, rot),
				new DirPos(pos.getX() - rot.offsetX * 2, pos.getY(), pos.getZ() - rot.offsetZ * 2, rot.getOpposite()),
				new DirPos(pos.getX() - dir.offsetX - rot.offsetX * 2, pos.getY(), pos.getZ() - dir.offsetZ - rot.offsetZ * 2, rot.getOpposite())
		};
	}

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		this.wasOn = nbt.getBoolean("wasOn");
		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		if(nbt.getInteger("itemId") > 0)
			this.display = new ItemStack(Item.getItemById(nbt.getInteger("itemId")), 1, nbt.getInteger("itemMeta"));
		else
			this.display = null;
		tank.readFromNBT(nbt);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		this.power = nbt.getLong("power");
		this.maxPower = nbt.getLong("maxPower");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		tank.readFromNBT(nbt);
	}

	@Override
	public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {

		nbt.setLong("power", power);
		nbt.setLong("maxPower", maxPower);
		nbt.setInteger("progress", progress);
		nbt.setInteger("processTime", processTime);
		tank.writeToNBT(nbt);
		return super.writeToNBT(nbt);
	}

	@Override
	public long getPower() {
		return Math.max(Math.min(power, maxPower), 0);
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return slot < 3;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack  stack, int side) {
		return slot == 3;
	}

	private static final int[][] rotSlots = {
			{1, 0, 3, 2},//from south
			{3, 2, 1, 0},//from north
			{0, 3, 2, 1},//from east
			{2, 1, 0, 3} //from west
	};

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {//S-W-N-E 0123
		if(e.getIndex() == 0) return new int[] { 3 };
		if(e.getIndex() == 1) return new int[] { };
		return new int[] { rotSlots[this.getBlockMetadata()-12][e.getHorizontalIndex()] };
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMachineArcWelder(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMachineArcWelder(player.inventory, this);
	}

	AxisAlignedBB bb = null;

	@Override
	public AxisAlignedBB getRenderBoundingBox() {

		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 1,
					pos.getY(),
					pos.getZ() - 1,
					pos.getX() + 2,
					pos.getY() + 3,
					pos.getZ() + 2
					);
		}

		return bb;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

//	@Override
//	public boolean canProvideInfo(UpgradeType type, int level, boolean extendedInfo) {
//		return type == UpgradeType.SPEED || type == UpgradeType.POWER;
//	}
//
//	@Override
//	public void provideInfo(UpgradeType type, int level, List<String> info, boolean extendedInfo) {
//		info.add(IUpgradeInfoProvider.getStandardLabel(ModBlocks.machine_arc_welder));
//		if(type == UpgradeType.SPEED) {
//			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_DELAY, "-" + (level * 100 / 6) + "%"));
//			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "+" + (level * 100) + "%"));
//		}
//		if(type == UpgradeType.POWER) {
//			info.add(EnumChatFormatting.GREEN + I18nUtil.resolveKey(this.KEY_CONSUMPTION, "-" + (level * 100 / 6) + "%"));
//			info.add(EnumChatFormatting.RED + I18nUtil.resolveKey(this.KEY_DELAY, "+" + (level * 100 / 3) + "%"));
//		}
//	}
//
//	@Override
//	public HashMap<UpgradeType, Integer> getValidUpgrades() {
//		HashMap<UpgradeType, Integer> upgrades = new HashMap<>();
//		upgrades.put(UpgradeType.SPEED, 3);
//		upgrades.put(UpgradeType.POWER, 3);
//		return upgrades;
//	}
	

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1) {
			return;

		}
		tank.readFromNBT(tags[0]);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.amount > 0 && (tank.getFluid() == null || tank.getFluid().getFluid() == resource.getFluid()) && ArcWelderRecipes.fluids.contains(resource.getFluid())) {
			return tank.fill(resource, doFill);
		} else {
			return 0;
		}
	}

	@Nullable
	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}
}
