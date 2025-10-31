package com.leafia.contents.machines.manfacturing.soldering;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.SolderingRecipes;
import com.hbm.inventory.SolderingRecipes.SolderingRecipe;
import com.hbm.inventory.UpgradeManager;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.leafia.dev.optimization.LeafiaParticlePacket.FiaSpark;
import com.llib.group.LeafiaSet;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class SolderingTE extends TileEntityMachineBase implements IEnergyUser, ITickable, IFluidHandler, ITankPacketAcceptor, LeafiaQuickModel, LeafiaPacketReceiver {
	public long power;
	public long maxPower = 2_000;
	public long consumption;
	public boolean collisionPrevention = false;

	public int progress;
	public int processTime = 1;

	public FluidTank tank;
	public ItemStack display;
	public UpgradeManager upgradeManager = new UpgradeManager();

	public SolderingTE() {
		super(10);
		tank = new FluidTank(8000);
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
	public void recievePacket(NBTTagCompound[] tags) {

	}

	@Override
	public String getName() {
		return "container.machineSoldering";
	}

	@Override
	public String _resourcePath() {
		return "soldering";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/soldering_station";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new SolderingRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_soldering;
	}

	@Override
	public double _sizeReference() {
		return 4.2;
	}

	@Override
	public double _itemYoffset() {
		return 0.01;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[0];
	}

	// thanks alcater
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource != null && resource.amount > 0 && (tank.getFluid() == null || tank.getFluid().getFluid() == resource.getFluid()) && SolderingRecipes.fluids.contains(resource.getFluid())) {
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
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
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

	public LeafiaPacket startPacket() {
		return LeafiaPacket._start(this)
				.__write(0,collisionPrevention)
				.__write(2,tank.writeToNBT(new NBTTagCompound()))
				.__write(3,power).__write(4,consumption)
				.__write(5,progress).__write(6,processTime);
	}

	@Override
	public String getPacketIdentifier() {
		return "soldering";
	}
	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();
	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0:
				this.collisionPrevention = (boolean)value;
				break;
			case 1:
				this.wasOn = (boolean)value;
				break;
			case 2:
				tank.readFromNBT((NBTTagCompound)value);
				break;
			case 3:
				this.power = (long)value;
				break;
			case 4:
				this.consumption = (long)value;
				break;
			case 5:
				this.progress = (int)value;
				break;
			case 6:
				this.processTime = (int)value;
				break;
			case 7:
				this.display = (ItemStack)value;
				break;
		}
	}

	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {
		if (key == 0) {
			int signal = (int)value;
			if (signal == 0)
				collisionPrevention = !collisionPrevention;
			else if (signal == 1)
				tank.drain(8192,true);
			//startPacket().__sendToListeners();
		}
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}
	private SolderingRecipe recipe;
	boolean wasOn = false;
	// thanks alcater
	protected DirPos[] getConPos() {

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(pos.add(dir.offsetX, 0, dir.offsetZ), dir),
				new DirPos(pos.add(dir.offsetX + rot.offsetX, 0, dir.offsetZ + rot.offsetZ), dir),
				new DirPos(pos.add(- dir.offsetX * 2, 0, - dir.offsetZ * 2), dir.getOpposite()),
				new DirPos(pos.add(- dir.offsetX * 2 + rot.offsetX, 0, - dir.offsetZ * 2 + rot.offsetZ), dir.getOpposite()),
				new DirPos(pos.add(- rot.offsetX, 0, - rot.offsetZ), rot.getOpposite()),
				new DirPos(pos.add(- dir.offsetX - rot.offsetX, 0, - dir.offsetZ - rot.offsetZ), rot.getOpposite()),
				new DirPos(pos.add(rot.offsetX * 2, 0, rot.offsetZ * 2), rot),
				new DirPos(pos.add(- dir.offsetX + rot.offsetX * 2, 0, - dir.offsetZ + rot.offsetZ * 2), rot),
		};
	}
	AudioWrapper client_sfx = null;
	boolean sfxPlaying = false;

	@Override
	public void invalidate() {
		if (sfxPlaying && client_sfx != null) {
			client_sfx.stopSound();
			client_sfx = null;
			sfxPlaying = false;
		}
		super.invalidate();
	}

	@Override
	public void update() {
		// thanks alcater
		if(!world.isRemote) {
			this.wasOn = false;
			this.power = Library.chargeTEFromItems(inventory, 7, this.getPower(), this.getMaxPower());

			if(world.getTotalWorldTime() % 20 == 0) {
				for(DirPos pos : getConPos()) {
					this.trySubscribe(world, pos.getPos(), pos.getDir());
					if(tank.getFluidAmount() > 0) FFUtils.fillFluid(this, tank, world, pos.getPos(), tank.getCapacity() >> 1);
				}
			}

			recipe = SolderingRecipes.getRecipe(new ItemStack[] {
					inventory.getStackInSlot(0),
					inventory.getStackInSlot(1),
					inventory.getStackInSlot(2),
					inventory.getStackInSlot(3),
					inventory.getStackInSlot(4),
					inventory.getStackInSlot(5)
			});
			long intendedMaxPower;

			upgradeManager.eval(inventory, 8, 9);
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

						if(inventory.getStackInSlot(6).isEmpty()) {
							inventory.setStackInSlot(6, recipe.output.copy());
						} else {
							inventory.getStackInSlot(6).grow(recipe.output.getCount());
						}

						this.markDirty();
					}

				} else {
					this.progress = 0;
				}

			} else {
				this.display = inventory.getStackInSlot(6).isEmpty() ? null : inventory.getStackInSlot(6);
				this.progress = 0;
				this.consumption = 100;
				intendedMaxPower = 2000;
			}

			startPacket().__sendToListeners();
			this.maxPower = Math.max(intendedMaxPower, power);
			LeafiaPacket._start(this).__write(1,wasOn).__write(7,display).__sendToAffectedClients();
		} else {
			if (client_sfx == null)
				client_sfx = MainRegistry.proxy.getLoopedSound(HBMSoundEvents.crafting_tech1_part,SoundCategory.BLOCKS,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,1,1);
			if(wasOn){
				if (!sfxPlaying) {
					sfxPlaying = true;
					client_sfx.startSound();
				}
				if(world.getTotalWorldTime() % 4 == world.rand.nextInt(4)) {
					ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
				/*
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "tau");
				data.setByte("count", (byte) 3);
				data.setDouble("posX", pos.getX() + 0.5 - dir.offsetX * 0.5 + rot.offsetX * 0.5);
				data.setDouble("posY", pos.getY() + 1.125);
				data.setDouble("posZ", pos.getZ() + 0.5 - dir.offsetZ * 0.5 + rot.offsetZ * 0.5);
				MainRegistry.proxy.effectNT(data);*/
					FiaSpark spark = new FiaSpark();
					spark.color = 0xFFEE80;
					spark.count = world.rand.nextInt(3)+1;
					spark.thickness = 0.014f;
					spark.emitLocal(new Vec3d(pos.getX() + 0.5 - dir.offsetX * 0.5 + rot.offsetX * 0.5,pos.getY() + 1.125,pos.getZ() + 0.5 - dir.offsetZ * 0.5 + rot.offsetZ * 0.5),new Vec3d(0,1,0));
				}
			} else if (sfxPlaying) {
				sfxPlaying = false;
				client_sfx.stopSound();
			}
		}
	}

	// thanks alcater
	public boolean canProcess(SolderingRecipe recipe) {

		if(this.power < this.consumption) return false;

		if(recipe.fluid != null && tank.getFluid() != null) {
			if(this.tank.getFluid().getFluid() != recipe.fluid.getFluid()) return false;
			if(this.tank.getFluidAmount() < recipe.fluid.amount) return false;
		}

		if(collisionPrevention && recipe.fluid == null && this.tank.getFluidAmount() > 0) return false;

		if(!inventory.getStackInSlot(6).isEmpty()) {
			ItemStack slot6 = inventory.getStackInSlot(6);
			if(slot6.getItem() != recipe.output.getItem()) return false;
			if(slot6.getItemDamage() != recipe.output.getItemDamage()) return false;
			return slot6.getCount() + recipe.output.getCount() <= slot6.getMaxStackSize();
		}

		return true;
	}

	// thanks alcater
	public void consumeItems(SolderingRecipe recipe) {

		for(AStack aStack : recipe.toppings) {
			for(int i = 0; i < 3; i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if(aStack.matchesRecipe(stack, true) && stack.getCount() >= aStack.count()) {
					stack.shrink(aStack.count());
					if(stack.getCount() == 0) inventory.setStackInSlot(i, ItemStack.EMPTY);
					break;
				}
			}
		}

		for(AStack aStack : recipe.pcb) {
			for(int i = 3; i < 5; i++) {
				ItemStack stack = inventory.getStackInSlot(i);
				if(aStack.matchesRecipe(stack, true) && stack.getCount() >= aStack.count()) {
					stack.shrink(aStack.count());
					if(stack.getCount() == 0) inventory.setStackInSlot(i, ItemStack.EMPTY);
					break;
				}
			}
		}

		for(AStack aStack : recipe.solder) {
			for(int i = 5; i < 6; i++) {
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
}
