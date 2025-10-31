package com.leafia.contents.machines.manfacturing.assemfac;

import api.hbm.energy.IEnergyUser;
import com.custom.TypedFluidTank;
import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.inventory.AssemblerRecipes;
import com.hbm.inventory.RecipesCommon.AStack;
import com.hbm.inventory.UpgradeManager;
import com.hbm.items.machine.ItemAssemblyTemplate;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.main.MainRegistry;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.manfacturing.assemfac.AssemblyFactoryTE.AssemblerArm.ArmActionState;
import com.leafia.contents.machines.manfacturing.assemfac.container.AssemblyFactoryContainer;
import com.leafia.contents.machines.manfacturing.assemfac.container.AssemblyFactoryGUI;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.group.LeafiaSet;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Random;

public class AssemblyFactoryTE extends TileEntityMachineBase implements LeafiaQuickModel, LeafiaPacketReceiver, ITickable, IGUIProvider, IEnergyUser, IFluidHandler {
	public AssemblerArm[] arms;
	public long power = 0;
	public int progress;
	public boolean needsProcess = true;
	public int[] maxProgresses = new int[8];
	public int[] progresses = new int[8];
	int curLoad = 0;
	int age = 0;
	int consumption = 100;
	int speed = 100;

	@Override
	public String getName() {
		return "nyaaa";
	}

	@Override
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AssemblyFactoryContainer(player.inventory,this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AssemblyFactoryGUI(player.inventory,this);
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public long getMaxPower() {
		return 10_000_000;
	}

	private void updateConnections() {
		for (DirPos pos : getConPos()) {
			this.trySubscribe(world, pos.getPos(), pos.getDir());
		}
	}

	public DirPos[] getConPos() {
		int xCoord = getPos().getX();
		int yCoord = getPos().getY();
		int zCoord = getPos().getZ();

		ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - BlockDummyable.offset);
		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		return new DirPos[] {
				new DirPos(xCoord - dir.offsetX * 3 + rot.offsetX * 5, yCoord, zCoord - dir.offsetZ * 3 + rot.offsetZ * 5, rot),
				new DirPos(xCoord + dir.offsetX * 2 + rot.offsetX * 5, yCoord, zCoord + dir.offsetZ * 2 + rot.offsetZ * 5, rot),
				new DirPos(xCoord - dir.offsetX * 3 - rot.offsetX * 4, yCoord, zCoord - dir.offsetZ * 3 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 2 - rot.offsetX * 4, yCoord, zCoord + dir.offsetZ * 2 - rot.offsetZ * 4, rot.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 5 + rot.offsetX * 3, yCoord, zCoord - dir.offsetZ * 5 + rot.offsetZ * 3, dir.getOpposite()),
				new DirPos(xCoord - dir.offsetX * 5 - rot.offsetX * 2, yCoord, zCoord - dir.offsetZ * 5 - rot.offsetZ * 2, dir.getOpposite()),
				new DirPos(xCoord + dir.offsetX * 4 + rot.offsetX * 3, yCoord, zCoord + dir.offsetZ * 4 + rot.offsetZ * 3, dir),
				new DirPos(xCoord + dir.offsetX * 4 - rot.offsetX * 2, yCoord, zCoord + dir.offsetZ * 4 - rot.offsetZ * 2, dir)
		};
	}

	@Override
	public String getPacketIdentifier() {
		return "assemfac";
	}
	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();
	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}

	LeafiaPacket startPacket() {
		return LeafiaPacket._start(this)
				.__write(0,power)
				.__write(1,water.writeToNBT(new NBTTagCompound()))
				.__write(2,steam.writeToNBT(new NBTTagCompound()));
				//.__write(4,maxProgresses);
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0: power = (long)value; break;
			case 1: water.readFromNBT((NBTTagCompound)value); break;
			case 2: steam.readFromNBT((NBTTagCompound)value); break;
			case 3: {
				if (!value.getClass().isArray()) {
					LeafiaDebug.debugLog(world,new TextWarningLeafia("PROGRESSES NOT AN ARRAY").toString());
					break;
				}
				for (int i = 0; i < progresses.length; i++)
					progresses[i] = (int)Array.get(value,i);
				break;
			}
			case 4:
				if (!value.getClass().isArray()) {
					LeafiaDebug.debugLog(world,new TextWarningLeafia("MAXPROGRESSES NOT AN ARRAY").toString());
					break;
				}
				for (int i = 0; i < maxProgresses.length; i++)
					maxProgresses[i] = (int)Array.get(value,i);
				break;
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) { }
	@Override
	public void onPlayerValidate(EntityPlayer plr) { }

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		power = compound.getLong("power");
		water.tank.readFromNBT(compound.getCompoundTag("water"));
		steam.tank.readFromNBT(compound.getCompoundTag("steam"));
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power",power);
		compound.setTag("water",water.tank.writeToNBT(new NBTTagCompound()));
		compound.setTag("steam",steam.tank.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}

	@Override
	public double affectionRange() {
		return 128;
	}
	private int getWaterRequired() {
		return 100 / this.speed;
	}
	@Override
	public void update() {
		if(!world.isRemote) {
			if(world.getTotalWorldTime() % 60 == 0) {
				this.updateConnections();
			}

			this.speed = 100;
			this.consumption = 100;

			upgradeManager.eval(inventory, 1, 4);
			int speedLevel = upgradeManager.getLevel(UpgradeType.SPEED);
			int powerLevel = upgradeManager.getLevel(UpgradeType.POWER);
			int overLevel = upgradeManager.getLevel(UpgradeType.OVERDRIVE);

			this.speed -= speedLevel * 15;
			this.consumption += speedLevel * 300;
			this.speed += powerLevel * 5;
			this.consumption -= powerLevel * 30;
			this.speed /= (overLevel + 1);
			this.consumption *= (overLevel + 1);

			for(DirPos pos : getConPos()) {
				FFUtils.fillFluid(this,steam.tank, world, pos.getPos(),steam.getTank().getFluidAmount());
			}

			curLoad = 0;
			for (int i = 0; i < 8; i++) {
				int minIndex = i*14+5;
				ItemStack template = inventory.getStackInSlot(minIndex+12);
				List<AStack> ingredients = AssemblerRecipes.getRecipeFromTempate(template);
				if (ingredients != null && power >= consumption && water.tank.getFluidAmount() >= getWaterRequired() && steam.tank.getCapacity()-steam.tank.getFluidAmount() >= getWaterRequired()) {
					maxProgresses[i] = Math.max(ItemAssemblyTemplate.getProcessTime(template),1);
					if (removeItems(minIndex,ingredients,cloneItemStackProper(inventory))) {
						// VV wtf is this spaghetti
						if(inventory.getStackInSlot(minIndex+13).isEmpty() || (!inventory.getStackInSlot(minIndex+13).isEmpty() && inventory.getStackInSlot(minIndex+13).getItem() == AssemblerRecipes.getOutputFromTempate(template).copy().getItem()) && inventory.getStackInSlot(minIndex+13).getCount() + AssemblerRecipes.getOutputFromTempate(template).copy().getCount() <= inventory.getStackInSlot(minIndex+13).getMaxStackSize()) {
							progresses[i]++;
							power -= consumption;
							water.tank.drain(getWaterRequired(),true);
							steam.tank.fill(new FluidStack(ModForgeFluids.HOTCOOLANT,getWaterRequired()),true);
							curLoad = Math.max(curLoad,ItemAssemblyTemplate.getLoad(template));
							if (progresses[i] >= maxProgresses[i]) {
								progresses[i] = 0;
								removeItems(minIndex,ingredients,inventory);
								if (inventory.getStackInSlot(minIndex+13).isEmpty()) {
									inventory.setStackInSlot(minIndex+13,AssemblerRecipes.getOutputFromTempate(template).copy());
								} else {
									inventory.getStackInSlot(minIndex+13).grow(AssemblerRecipes.getOutputFromTempate(template).copy().getCount());
								}
							}
							continue;
						}
					}
				}
				progresses[i] = 0;
			}

			startPacket().__sendToListeners();
			LeafiaPacket._start(this).__write(3,progresses).__sendToAffectedClients();
		} else
			updateArm();
	}
	@SideOnly(Side.CLIENT)
	void updateArm() {
		boolean operating = false;
		for (int i = 0; i < arms.length; i++) {
			float index = i*6/5.01f;
			AssemblerArm arm = arms[i];
			arm.updateInterp();
			if (progresses[(int)Math.floor(index)] > 0 || progresses[(int)Math.ceil(index)] > 0) {
				boolean playSound = arm.lastState == null;
				arm.updateArm();
				if (!arm.state.equals(arm.lastState) || playSound) {
					if (arm.state.equals(ArmActionState.ASSUME_POSITION))
						world.playSound(Minecraft.getMinecraft().player,pos,HBMSoundEvents.assemblerStart,SoundCategory.BLOCKS,0.5f,1+(float)world.rand.nextGaussian()*0.105f);
					else if (arm.state.equals(ArmActionState.EXTEND_STRIKER))
						world.playSound(Minecraft.getMinecraft().player,pos,HBMSoundEvents.assemblerStrike,SoundCategory.BLOCKS,0.5f,1+(float)world.rand.nextGaussian()*0.05f);
					else if (arm.state.equals(ArmActionState.WELD))
						world.playSound(Minecraft.getMinecraft().player,pos,HBMSoundEvents.mechcrafting_weld,SoundCategory.BLOCKS,0.85f,1);
				}
				operating = true;
			} else
				arm.lastState = null;
		}
		if (operating && audio == null) {
			audio = MainRegistry.proxy.getLoopedSound(HBMSoundEvents.motor, SoundCategory.BLOCKS, pos.getX(), pos.getY(), pos.getZ(), 0.5f, 1.0F);
			audio.startSound();
		} else if (!operating && audio != null) {
			audio.stopSound();
			audio = null;
		}
	}
	private AudioWrapper audio = null;

	@Override
	public void invalidate() {
		if (audio != null) {
			audio.stopSound();
			audio = null;
		}
		super.invalidate();
	}

	//I can't believe that worked.
	public ItemStackHandler cloneItemStackProper(IItemHandlerModifiable array) {
		ItemStackHandler stack = new ItemStackHandler(array.getSlots());

		for(int i = 0; i < array.getSlots(); i++)
			if(array.getStackInSlot(i).getItem() != Items.AIR)
				stack.setStackInSlot(i, array.getStackInSlot(i).copy());
			else
				stack.setStackInSlot(i, ItemStack.EMPTY);
		;

		return stack;
	}
	//boolean true: remove items, boolean false: simulation mode
	public boolean removeItems(int startSlot, List<AStack> stack, IItemHandlerModifiable array) {
		if(stack == null)
			return false;

		for(int i = 0; i < stack.size(); i++) {
			for(int j = 0; j < stack.get(i).count(); j++) {
				AStack sta = stack.get(i).copy();
				sta.singulize();
				if(!canRemoveItemFromArray(startSlot, sta, array)){
					return false;
				}
			}
		}

		return true;

	}

	public boolean canRemoveItemFromArray(int startSlot, AStack stack, IItemHandlerModifiable array) {
		AStack st = stack.copy();

		if(st == null)
			return true;

		for(int i = startSlot; i < startSlot+12; i++) {

			if(!array.getStackInSlot(i).isEmpty()) {

				ItemStack sta = array.getStackInSlot(i).copy();
				sta.setCount(1);

				if(st.isApplicable(sta) && array.getStackInSlot(i).getCount() > 0) {
					array.getStackInSlot(i).shrink(1);

					if(array.getStackInSlot(i).isEmpty())
						array.setStackInSlot(i, ItemStack.EMPTY);

					return true;
				}
			}
		}

		return false;
	}

	IFluidTankProperties[] properties;

	@Override
	public IFluidTankProperties[] getTankProperties() {
		if(properties == null) {
			properties = new IFluidTankProperties[2];

			properties[0] = water.tank.getTankProperties()[0];
			properties[1] = steam.tank.getTankProperties()[0];
		}

		return properties;
	}
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return stack.getFluid() == ModForgeFluids.COOLANT;
	}
	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (isValidFluid(resource)) {
			return water.getTank().fill(resource,doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return steam.tank.drain(resource,doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return steam.tank.drain(maxDrain,doDrain);
	}

	@Override
	public <T> T getCapability(Capability<T> capability,EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}

		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return true;
		}

		return super.hasCapability(capability, facing);
	}

	public static class AssemblerArm {
		public double[] angles = new double[4];
		public double[] prevAngles = new double[4];
		public double[] targetAngles = new double[4];
		public double[] speed = new double[4];

		Random rand = new Random();

		int actionMode;
		ArmActionState state;
		ArmActionState lastState;
		int actionDelay = 0;

		public AssemblerArm(int actionMode) {
			this.actionMode = actionMode;

			if(this.actionMode == 0) {
				speed[0] = 15;	//Pivot
				speed[1] = 15;	//Arm
				speed[2] = 15;	//Piston
				speed[3] = 0.5;	//Striker
			} else if(this.actionMode == 1) {
				speed[0] = 3;		//Pivot
				speed[1] = 3;		//Arm
				speed[2] = 1;		//Piston
				speed[3] = 0.125;	//Striker
			}

			state = ArmActionState.ASSUME_POSITION;
			lastState = null;
			chooseNewArmPoistion();
			actionDelay = rand.nextInt(20);
		}

		public void updateArm() {

			lastState = state;
			if(actionDelay > 0) {
				actionDelay--;
				return;
			}

			switch(state) {
				//Move. If done moving, set a delay and progress to EXTEND
				case ASSUME_POSITION:
					if(move()) {
						if(this.actionMode == 0) {
							actionDelay = 2;
						} else if(this.actionMode == 1) {
							actionDelay = 10;
						}
						state = ArmActionState.EXTEND_STRIKER;
						targetAngles[3] = 1D;
					}
					break;
				case EXTEND_STRIKER:
					if(move()) {
						if(this.actionMode == 0) {
							state = ArmActionState.RETRACT_STRIKER;
							targetAngles[3] = 0D;
						} else if(this.actionMode == 1) {
							state = ArmActionState.WELD;
							targetAngles[2] -= 20;
							actionDelay = 5 + rand.nextInt(5);
						}
					}
					break;
				case WELD:
					if(move()) {
						state = ArmActionState.RETRACT_STRIKER;
						targetAngles[3] = 0D;
						actionDelay = 10 + rand.nextInt(5);
					}
					break;
				case RETRACT_STRIKER:
					if(move()) {
						if(this.actionMode == 0) {
							actionDelay = 2 + rand.nextInt(5);
						} else if(this.actionMode == 1) {
							actionDelay = 5 + rand.nextInt(3);
						}
						chooseNewArmPoistion();
						state = ArmActionState.ASSUME_POSITION;
					}
					break;

			}
		}

		public void chooseNewArmPoistion() {

			if(this.actionMode == 0) {
				targetAngles[0] = -rand.nextInt(50);		//Pivot
				targetAngles[1] = -targetAngles[0];			//Arm
				targetAngles[2] = rand.nextInt(30) - 15;	//Piston
			} else if(this.actionMode == 1) {
				targetAngles[0] = -rand.nextInt(30) + 10;	//Pivot
				targetAngles[1] = -targetAngles[0];			//Arm
				targetAngles[2] = rand.nextInt(10) + 10;	//Piston
			}
		}

		private void updateInterp() {
			for(int i = 0; i < angles.length; i++) {
				prevAngles[i] = angles[i];
			}
		}

		/**
		 * @return True when it has finished moving
		 */
		private boolean move() {
			boolean didMove = false;

			for(int i = 0; i < angles.length; i++) {
				if(angles[i] == targetAngles[i])
					continue;

				didMove = true;

				double angle = angles[i];
				double target = targetAngles[i];
				double turn = speed[i];
				double delta = Math.abs(angle - target);

				if(delta <= turn) {
					angles[i] = targetAngles[i];
					continue;
				}

				if(angle < target) {
					angles[i] += turn;
				} else {
					angles[i] -= turn;
				}
			}

			return !didMove;
		}

		public static enum ArmActionState {
			ASSUME_POSITION,
			EXTEND_STRIKER,
			WELD,
			RETRACT_STRIKER
		}
	}
	public TypedFluidTank water;
	public TypedFluidTank steam;

	public UpgradeManager upgradeManager = new UpgradeManager();

	@Override
	public void slotContentsChanged(int slot,ItemStack stack) {
		if (slot >= 1 && slot <= 4 && stack.getItem() instanceof ItemMachineUpgrade)
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, HBMSoundEvents.upgradePlug, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	public AssemblyFactoryTE() {
		super(14 * 8 + 4 + 1); //8 assembler groups with 14 slots, 4 upgrade slots, 1 battery slot

		arms = new AssemblerArm[6];
		for(int i = 0; i < arms.length; i++) {
			arms[i] = new AssemblerArm(i % 3 == 1 ? 1 : 0); //the second of every group of three becomes a welder
		}

		water = new TypedFluidTank(FluidRegistry.WATER, new FluidTank(64_000));
		steam = new TypedFluidTank(ModForgeFluids.SPENTSTEAM, new FluidTank(64_000));
	}

	@Override
	public String _resourcePath() {
		return "assemfac";
	}

	@Override
	public String _assetPath() {
		return "machines/cat1/assemfac";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new AssemblyFactoryRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_assemfac;
	}

	@Override
	public double _sizeReference() {
		return 14.9;
	}

	@Override
	public double _itemYoffset() {
		return 0.07;
	}
}
