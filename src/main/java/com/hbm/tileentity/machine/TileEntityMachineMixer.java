package com.hbm.tileentity.machine;

import com.hbm.config.MachineConfig;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.UpgradeManager;
import com.hbm.inventory.container.ContainerMixer;
import com.hbm.inventory.gui.GUIMixer;
import com.hbm.inventory.MixerRecipes;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.forgefluid.FFUtils;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade.UpgradeType;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.Library;
import com.hbm.lib.DirPos;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.EnumFacing;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityMachineMixer extends TileEntityMachineBase implements ITickable, IGUIProvider, IFluidHandler, IEnergyUser, ITankPacketAcceptor {
	
	public long power;
	public static final long maxPower = 10_000;
	public int progress;
	public int processTime;
	public Fluid outputFluid;
	
	public float rotation;
	public float prevRotation;
	public boolean wasOn = false;

	private int consumption = 50;

	public boolean uuMixer = false;
	public static final int uuConsumption = 1_000_000;
	public static final long uuMaxPower = 200_000_000;
	
	public FluidTank[] tanks;
	private final UpgradeManager upgradeManager = new UpgradeManager();

	public TileEntityMachineMixer() {
		super(5);
		this.outputFluid = null;
		this.tanks = new FluidTank[3];
		this.tanks[0] = new FluidTank(16_000); //Input 1
		this.tanks[1] = new FluidTank(16_000); //Input 2
		this.tanks[2] = new FluidTank(24_000); //Output
	}

	@Override
	public String getName() {
		if(uuMixer) return "container.machineUUMixer";
		return "container.machineMixer";
	}

	@Override
	public void update() {
		updateTankSizes();
		if(!world.isRemote) {
			this.power = Library.chargeTEFromItems(inventory, 0, power, getMaxPower());
			
			upgradeManager.eval(inventory, 3, 4);
			int speedLevel = Math.min(upgradeManager.getLevel(UpgradeType.SPEED), 3);
			int powerLevel = Math.min(upgradeManager.getLevel(UpgradeType.POWER), 3);
			int overLevel = upgradeManager.getLevel(UpgradeType.OVERDRIVE);
			uuMixer = upgradeManager.getLevel(UpgradeType.SCREAM) > 0;

			updateTankType();
			
			if(uuMixer){
				updateInputUUtank();
			} else {
				updateInputTankTypes();
			}

			this.consumption = getConsumption();

			this.consumption *= (speedLevel+1);
			this.consumption /= (powerLevel+1);
			this.consumption *= (overLevel * 3 + 1);
			
			for(DirPos pos : getConPos()) {
				this.trySubscribe(world, pos.getPos(), pos.getDir());
			}
			
			this.wasOn = this.canProcess();
			
			if(this.wasOn) {
				this.progress++;
				this.power -= this.getConsumption();
				
				this.processTime -= this.processTime * speedLevel / 4;
				this.processTime /= (overLevel + 1);
				
				if(processTime <= 0) this.processTime = 1;
				
				if(this.progress >= this.processTime) {
					this.process();
					this.progress = 0;
				}
				
			} else {
				this.progress = 0;
			}
			
			for(DirPos pos : getConPos()) {
				if(tanks[2].getFluidAmount() > 0)
					FFUtils.fillFluid(this, tanks[2], world, pos.getPos(), tanks[2].getCapacity() >> 1);
			}
			
			NBTTagCompound data = new NBTTagCompound();
			if(outputFluid != null){
				data.setString("f", outputFluid.getName());
			} else {
				if(tanks[2].getFluid() != null){
					data.setString("f", tanks[2].getFluid().getFluid().getName());
				} else {
					data.setString("f", "None");
				}
			}
			data.setLong("power", power);
			data.setInteger("processTime", processTime);
			data.setInteger("progress", progress);
			data.setBoolean("wasOn", wasOn);
			data.setBoolean("uu", uuMixer);
			data.setTag("tanks", FFUtils.serializeTankArray(tanks));
			
			this.networkPack(data, 50);
			if(!uuMixer && power > getMaxPower()) power = getMaxPower();
			
		} else {
			
			this.prevRotation = this.rotation;
			
			if(this.wasOn) {
				if(this.uuMixer){
					this.rotation += 40F;
				} else {
					this.rotation += 20F;
				}
			}
			
			if(this.rotation >= 360) {
				this.rotation -= 360;
				this.prevRotation -= 360;
			}
		}
	}

	private void updateTankType() {
        ItemStack slotId = inventory.getStackInSlot(2);
        Item itemId = slotId.getItem();
        if(itemId == ModItems.forge_fluid_identifier) {
            Fluid fluid = ItemForgeFluidIdentifier.getType(slotId);

            if(outputFluid != fluid && ((uuMixer && MachineConfig.isFluidAllowed(fluid)) || MixerRecipes.hasMixerRecipe(fluid))) {
                outputFluid = fluid;
                tanks[2].setFluid(new FluidStack(fluid, 0));

                this.markDirty();
            }
        }
    }

    private void updateTankSizes(){
    	if(uuMixer){
	    	if(tanks[0].getCapacity() != 2_000_000_000){
		    	tanks[0] = FFUtils.changeTankSize(tanks[0], 2_000_000_000);
		    	tanks[1] = FFUtils.changeTankSize(tanks[1], 2_000_000_000);
		    	tanks[2] = FFUtils.changeTankSize(tanks[2], 2_000_000_000/MachineConfig.uuMixerFluidRatio);
		    	this.markDirty();
		    }
	    } else {
		    if(tanks[0].getCapacity() != 16_000){
		    	tanks[0] = FFUtils.changeTankSize(tanks[0], 16_000);
		    	tanks[1] = FFUtils.changeTankSize(tanks[1], 16_000);
		    	tanks[2] = FFUtils.changeTankSize(tanks[2], 24_000);
		    	this.markDirty();
		    }
		}
    }

    private void updateInputUUtank(){
    	if(tanks[0].getFluid() == null || tanks[0].getFluid().getFluid() != ModForgeFluids.uu_matter)
    		tanks[0].setFluid(new FluidStack(ModForgeFluids.uu_matter, 0));
    	if(tanks[1].getFluid() != null) tanks[1].setFluid(null);
    	if(outputFluid == null && tanks[2].getFluid() != null) outputFluid = tanks[2].getFluid().getFluid();
    }

    private void updateInputTankTypes() {
    	if(outputFluid == null) return;
    	
    	Fluid[] f = MixerRecipes.getInputFluids(outputFluid);
    	if(f == null){
    		if(tanks[0].getFluid() != null) tanks[0].setFluid(null);
	    	if(tanks[1].getFluid() != null) tanks[1].setFluid(null);
    	} else if(f.length > 0){
    		FluidStack t1 = tanks[0].getFluid();
    		if(t1 == null || t1.getFluid() != f[0]){
    			tanks[0].setFluid(new FluidStack(f[0], 0));
	    	}
	    	if(f.length == 2){
		    	FluidStack t2 = tanks[1].getFluid();
	    		if(t2 == null || t2.getFluid() != f[1]){
	    			tanks[1].setFluid(new FluidStack(f[1], 0));
		    	}
		    } else {
		    	tanks[1].setFluid(null);
		    }
    	}
    }

	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		if(nbt.hasKey("f")) {
			if(nbt.getString("f").equals("None"))
				this.outputFluid = null;
			else
            	this.outputFluid = FluidRegistry.getFluid(nbt.getString("f"));
        }
		this.power = nbt.getLong("power");
		this.processTime = nbt.getInteger("processTime");
		this.progress = nbt.getInteger("progress");
		this.wasOn = nbt.getBoolean("wasOn");
		this.uuMixer = nbt.getBoolean("uu");
		if(nbt.hasKey("tanks")){
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		}
	}
	
	public boolean canProcess() {
		//Enought Power?
		if(this.power < getConsumption()) return false;

		//Mixing uu matter?
		if(uuMixer){
			this.processTime = 200;
			if(outputFluid != null && tanks[2].getFluidAmount() < tanks[2].getCapacity() && FFUtils.hasEnoughFluid(tanks[0], new FluidStack(ModForgeFluids.uu_matter, MachineConfig.uuMixerFluidRatio))){
				return true;
			}
			return false;
		}

		//has recipe?
		if(!MixerRecipes.hasMixerRecipe(outputFluid)) {
			this.outputFluid = null;
			return false;
		}
		//has enough Fluid
		FluidStack[] fluidInputs = MixerRecipes.getInputFluidStacks(outputFluid);
		if(fluidInputs != null){
			if(fluidInputs.length >= 1 && !FFUtils.hasEnoughFluid(tanks[0], fluidInputs[0])) return false;
			if(fluidInputs.length == 2 && !FFUtils.hasEnoughFluid(tanks[1], fluidInputs[1])) return false;
		}
		
		//has enough space left in output tank
		if(tanks[2].getCapacity() - tanks[2].getFluidAmount() < MixerRecipes.getFluidOutputAmount(outputFluid)) return false;
		//has correct item in inputSlot
		if(!MixerRecipes.matchesInputItem(outputFluid, inventory.getStackInSlot(1))) return false;
		//has enough of that item
		if(inventory.getStackInSlot(1).getCount() < MixerRecipes.getInputItemCount(outputFluid)) return false;
		
		this.processTime = MixerRecipes.getRecipeDuration(outputFluid);
		return true;
	}
	
	protected void process() {

		if(uuMixer){
			int mbProduction = Math.min(tanks[2].getCapacity()-tanks[2].getFluidAmount(), tanks[0].getFluidAmount()/MachineConfig.uuMixerFluidRatio);
			tanks[0].drain(mbProduction * MachineConfig.uuMixerFluidRatio, true);
			tanks[2].fill(new FluidStack(outputFluid, mbProduction), true);
			this.markDirty();
			return;
		}
		
		FluidStack[] fluidInputs = MixerRecipes.getInputFluidStacks(outputFluid);
		if(fluidInputs != null){
			if(fluidInputs.length >= 1)
				tanks[0].drain(fluidInputs[0].amount, true);
			if(fluidInputs.length == 2)
				tanks[1].drain(fluidInputs[1].amount, true);
		}

		int itemuse = MixerRecipes.getInputItemCount(outputFluid);
		if(itemuse > 0){
			ItemStack stack = inventory.getStackInSlot(1);
			stack.shrink(itemuse);
			if(stack.getCount() == 0)
				inventory.setStackInSlot(1, ItemStack.EMPTY);
		}
		
		tanks[2].fill(new FluidStack(outputFluid, MixerRecipes.getFluidOutputAmount(outputFluid)), true);
		this.markDirty();
	}
	
	public int getConsumption() {
		if(uuMixer) return uuConsumption;
		return consumption;
	}
	
	protected DirPos[] getConPos() {
		return new DirPos[] {
				new DirPos(pos.add(0, -1, 0), Library.NEG_Y),
				new DirPos(pos.add(1, 0, 0), Library.POS_X),
				new DirPos(pos.add(-1, 0, 0), Library.NEG_X),
				new DirPos(pos.add(0, 0, 1), Library.POS_Z),
				new DirPos(pos.add(0, 0, -1), Library.NEG_Z),
		};
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return new int[] { 1 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		if(i == 1) return MixerRecipes.matchesInputItem(outputFluid, itemStack);
		return false;
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);
		if(nbt.hasKey("f")) {
            if(nbt.getString("f").equals("None"))
				this.outputFluid = null;
			else
            	this.outputFluid = FluidRegistry.getFluid(nbt.getString("f"));
        }
        this.uuMixer = nbt.getBoolean("uu");
		this.power = nbt.getLong("power");
		this.progress = nbt.getInteger("progress");
		this.processTime = nbt.getInteger("processTime");
		if(nbt.hasKey("tanks")){
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		if(outputFluid != null){
			nbt.setString("f", outputFluid.getName());
		} else {
			if(tanks[2].getFluid() != null){
				nbt.setString("f", tanks[2].getFluid().getFluid().getName());
			} else {
				nbt.setString("f", "None");
			}
		}
		nbt.setBoolean("uu", uuMixer);
		nbt.setLong("power", power);
		nbt.setInteger("progress", progress);
		nbt.setInteger("processTime", processTime);
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		return super.writeToNBT(nbt);
	}

	@Override
	public long getPower() {
		return power;
	}

	@Override
	public void setPower(long power) {
		this.power = power;
	}

	@Override
	public long getMaxPower() {
		if(uuMixer) return uuMaxPower;
		return maxPower;
	}

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerMixer(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIMixer(player.inventory, this);
	}
	
	AxisAlignedBB aabb;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(aabb != null)
			return aabb;
		
		aabb = new AxisAlignedBB(pos.getX(), pos.getY(), pos.getZ(), pos.getX() + 1, pos.getY() + 3, pos.getZ() + 1);
		return aabb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
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
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0] };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(resource == null) return 0;

		if(tanks[0].getFluid() != null && resource.isFluidEqual(tanks[0].getFluid())) {
			return tanks[0].fill(resource, doFill);
		}

		if(tanks[1].getFluid() != null && resource.isFluidEqual(tanks[1].getFluid())) {
			return tanks[1].fill(resource, doFill);
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(resource == null || resource.getFluid() != outputFluid) {
			return null;
		}
		return tanks[2].drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tanks[2].drain(maxDrain, doDrain);
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
