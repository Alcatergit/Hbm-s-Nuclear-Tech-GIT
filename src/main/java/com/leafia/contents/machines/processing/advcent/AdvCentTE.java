package com.leafia.contents.machines.processing.advcent;

import api.hbm.energy.IEnergyUser;
import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.MachineGasCent;
import com.hbm.inventory.MachineRecipes;
import com.hbm.inventory.MachineRecipes.GasCentOutputV2;
import com.hbm.inventory.MachineRecipes.GasCentRecipeV2;
import com.hbm.items.ModItems.Upgrades;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.contents.machines.processing.advcent.container.AdvCentContainer;
import com.leafia.contents.machines.processing.advcent.container.AdvCentGUI;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.blockitems.LeafiaQuickModel;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.leafia.dev.proxy.IFluidHandlerOut;
import com.llib.group.LeafiaSet;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AdvCentTE extends TileEntityMachineBase implements LeafiaQuickModel, ITickable, IGUIProvider, LeafiaPacketReceiver, IFluidHandlerOut, IEnergyUser {
	public long power;
	public int progress;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 150; //200;
	public boolean needsUpdate = false;
	public boolean isSicko = false;
	
	public FluidTank in = new FluidTank(8000);
	public FluidTank out0 = new FluidTank(8000);
	public FluidTank out1 = new FluidTank(8000);
	public FluidTank out2 = new FluidTank(8000);

	public LeafiaSet<EntityPlayer> listeners = new LeafiaSet<>();

	public AdvCentTE() {
		super(9);
	}
	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processingSpeed;
	}

	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}

	@Override
	public List<EntityPlayer> getListeners() {
		return listeners;
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0:
				power = (long)value;
				break;
			case 1:
				progress = (int)value;
				break;
			case 2:
				in.readFromNBT((NBTTagCompound)value);
				break;
			case 3:
				out0.readFromNBT((NBTTagCompound)value);
				break;
			case 4:
				out1.readFromNBT((NBTTagCompound)value);
				break;
			case 5:
				out2.readFromNBT((NBTTagCompound)value);
				break;
			case 6:
				isProgressing = (boolean)value;
				break;
			case 7:
				isSicko = (boolean)value;
				break;
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) { }

	@Override
	public void onPlayerValidate(EntityPlayer plr) { }

	@Override
	public String getPacketIdentifier() {
		return "AdvCentrifuge";
	}
	
	int getGrade(FluidStack stack) {
		int grade = 0;
		if (stack.tag != null) {
			if (stack.tag.hasKey("enrichment"))
				grade = stack.tag.getByte("enrichment");
		}
		return grade;
	}
	
	// Leafia: If it works, it works.
	ItemStackHandler cloneItemStackProper(IItemHandlerModifiable array) {
		ItemStackHandler stack = new ItemStackHandler(array.getSlots());

		for(int i = 0; i < array.getSlots(); i++)
			if(array.getStackInSlot(i).getItem() != Items.AIR)
				stack.setStackInSlot(i, array.getStackInSlot(i).copy());
			else
				stack.setStackInSlot(i, ItemStack.EMPTY);
		;

		return stack;
	}
	boolean yieldItems(ItemStackHandler handler,ItemStack... stacks) {
		for (ItemStack stackOg : stacks) {
			ItemStack stack = stackOg.copy();
			boolean success = false;
			for (int i = 2; i < handler.getSlots(); i++) {
				ItemStack slot = handler.getStackInSlot(i);
				if (slot.isEmpty()) {
					handler.setStackInSlot(i,stack);
					success = true;
					break;
				} else {
					if (stack.isItemEqual(slot)) {
						int expectedAmount = slot.getCount()+stack.getCount();
						int over = Math.max(0,expectedAmount-slot.getMaxStackSize());
						slot.grow(stack.getCount()-over);
						stack.setCount(over);
						if (over <= 0) {
							success = true;
							break;
						}
					}
				}
			}
			if (!success)
				return false;
		}
		return true;
	}


	@Override
	public void update() {
		if (!world.isRemote) {
			IBlockState state = world.getBlockState(pos);
			if (state.getBlock() instanceof MachineGasCent) {
				//BlockPos output = pos.offset(state.getValue(MachineGasCent.FACING));
				//fillFluid(output,out0);
				//LeafiaDebug.debugPos(world,output,0.05f,0xFFFF00,"output");
			}
			this.updateConnectionsExcept(world, pos, Library.POS_Y);
			isProgressing = false;
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			boolean sicko = inventory.getStackInSlot(1).getItem() == Upgrades.upgrade_gc_speed;
			FluidStack stack = in.getFluid();
			if (stack != null && power >= (sicko ? 300 : 200)) {
				GasCentRecipeV2 reciple = MachineRecipes.gasCentRecipes.get(in.getFluid().getFluid());
				if (reciple != null) {
					int grade = getGrade(stack);
					if (grade < reciple.grades.length && (grade < 3 || sicko)) {
						GasCentOutputV2 output = reciple.grades[grade];
						if (in.getFluidAmount() >= output.consumption && out0.getCapacity()-out0.getFluidAmount() >= output.production) {
							if (yieldItems(cloneItemStackProper(inventory),output.outputs)) {
								progress++;
								this.power -= 200;
								if (sicko)
									power -= 100;

								isProgressing = true;
								if (progress >= processingSpeed) {
									progress = 0;
									FluidStack st = in.drain(output.consumption,true);
									assert st != null;
									if (output.production > 0) {
										if (st.tag == null) st.tag = new NBTTagCompound();
										st.tag.setByte("enrichment",(byte) (grade+1));
										st.amount = output.production;
										out0.fill(st,true);
									}
									yieldItems(inventory,output.outputs);
								}
							}
						}
					}
				}
			}
			if (!isProgressing)
				progress = 0;
			PacketDispatcher.wrapper.sendToAll(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()));

			LeafiaPacket._start(this)
					.__write(0,power)
					.__write(1,progress)
					.__write(2,in.writeToNBT(new NBTTagCompound()))
					.__write(3,out0.writeToNBT(new NBTTagCompound()))
					.__write(4,out1.writeToNBT(new NBTTagCompound()))
					.__write(5,out2.writeToNBT(new NBTTagCompound()))
					.__sendToListeners();
			LeafiaPacket._start(this).__write(6,isProgressing).__write(7,sicko && isProgressing).__sendToAffectedClients();
		} else {
			if (isSicko) {
				if (sound == null) {
					sound = MainRegistry.proxy.getLoopedSound(HBMSoundEvents.centrifugeOperate,SoundCategory.BLOCKS,pos.getX()+0.5f,pos.getY()+0.5f,pos.getZ()+0.5f,2,2);
					sound.startSound();
				}
			} else {
				if (sound != null) {
					sound.stopSound();
					sound = null;
				}
			}
		}
	}
	AudioWrapper sound = null;

	@Override
	public void invalidate() {
		if (sound != null) {
			sound.stopSound();
			sound = null;
		}
		super.invalidate();
	}
	
	
	@Override
	public String _resourcePath() {
		return "advcent";
	}

	@Override
	public String _assetPath() {
		return "xenoulexi/adv_centrifuge";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public TileEntitySpecialRenderer<TileEntity> _renderer() {
		return new AdvCentRender();
	}

	@Override
	public Block _block() {
		return ModBlocks.machine_advcent;
	}

	@Override
	public double _sizeReference() {
		return 6.13;
	}

	@Override
	public double _itemYoffset() {
		return -0.15;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{
				in.getTankProperties()[0],
				out0.getTankProperties()[0],
				out1.getTankProperties()[0],
				out2.getTankProperties()[0]
		};
	}

	@Override
	public int fillForce(FluidStack resource,boolean doFill) {
		return in.fill(resource,doFill);
	}

	@Override
	public @Nullable FluidStack drain(FluidStack resource,boolean doDrain) {
		return in.drain(resource,doDrain);
	}

	@Override
	public @Nullable FluidStack drain(int maxDrain,boolean doDrain) {
		return in.drain(maxDrain,doDrain);
	}

	@Override
	public boolean hasCapability(Capability<?> capability,EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public String getName() {
		return "tile.advcent.name";
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
	public void setPower(long power) {
		this.power = power;
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
	public Container provideContainer(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AdvCentContainer(player.inventory,this);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public GuiScreen provideGUI(int ID,EntityPlayer player,World world,int x,int y,int z) {
		return new AdvCentGUI(player.inventory,this);
	}
}
