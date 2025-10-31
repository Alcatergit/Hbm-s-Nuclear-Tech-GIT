package com.hbm.tileentity.machine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.GasCentrifugeRecipes;
import com.hbm.inventory.GasCentrifugeRecipes.*;
import com.hbm.inventory.UpgradeManager;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemMachineUpgrade;
import com.hbm.lib.Library;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;

import api.hbm.energy.IEnergyUser;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class TileEntityMachineGasCent extends TileEntityMachineBase implements ITickable, IEnergyUser, ITankPacketAcceptor, IFluidHandler {

	
	public long power;
	public int progress;
    public int processTime = 200;
	public boolean isProgressing;
	public static final int maxPower = 100000;
	public static final int processingSpeed = 200;
    public static final int baseConsumption = 200;
    public boolean needsUpdate = false;
    public boolean hasCentUpgrade = false;
	
	public FluidTank tank;

    private final UpgradeManager upgradeManager = new UpgradeManager();

    //private static final int[] slots_top = new int[] {3};
	//private static final int[] slots_bottom = new int[] {5, 6, 7, 8};
	//private static final int[] slots_side = new int[] {0, 3};
	
	private String customName;
	
	public TileEntityMachineGasCent() {
		super(9);
		tank = new FluidTank(16000);
	}
	
	public String getName() {
		return "container.gasCentrifuge";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		power = nbt.getLong("powerTime");
		progress = nbt.getShort("CookTime");
		tank.readFromNBT(nbt);
		if(nbt.hasKey("inventory"))
			inventory.deserializeNBT(nbt.getCompoundTag("inventory"));
		
		super.readFromNBT(nbt);
	}
	
	@Override
	public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("powerTime", power);
		nbt.setShort("cookTime", (short) progress);
		tank.writeToNBT(nbt);
		nbt.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(nbt);
	}
	
	public int getCentrifugeProgressScaled(int i) {
		return (progress * i) / processTime;
	}
	
	public long getPowerRemainingScaled(int i) {
		return (power * i) / maxPower;
	}
	
	private boolean canProcess() {
		
		if(power > 0 && this.tank.getFluid() != null) {

            if(this.tank.getFluidAmount() < GasCentrifugeRecipes.getFluidConsumedGasCent(hasCentUpgrade, tank.getFluid().getFluid())) return false;

            GasCentRecipe recipe = GasCentrifugeRecipes.getGasCentRecipe(tank.getFluid().getFluid());
			
			if(recipe == null)
				return false;
			
			if(recipe.outputListA.isEmpty() || recipe.outputListA.size() > 4)
				return false;

            List<GasCentOutput> list = hasCentUpgrade && recipe.outputListB != null ? recipe.outputListB : recipe.outputListA;
			
			for(int i = 0; i < list.size(); i++) {
				
				int slot = i + 5;
				
				if(inventory.getStackInSlot(slot).isEmpty())
					continue;
				
				if(inventory.getStackInSlot(slot).getItem() == list.get(i).output.getItem() &&
						inventory.getStackInSlot(slot).getItemDamage() == list.get(i).output.getItemDamage() &&
						inventory.getStackInSlot(slot).getCount() + list.get(i).output.getCount() <= inventory.getStackInSlot(slot).getMaxStackSize())
					continue;
				
				return false;
			}
			
			return true;
		}
		
		return false;
	}
	
	private void process() {

        GasCentRecipe recipe = GasCentrifugeRecipes.getGasCentRecipe(tank.getFluid().getFluid());
        boolean useB = hasCentUpgrade && recipe.outputListB != null;
        List<GasCentOutput> out = useB ? recipe.outputListB : recipe.outputListA;
        int amount = useB ? recipe.amountB : recipe.amountA;
        this.progress = 0;
		tank.drain(amount, true);
		
		List<GasCentOutput> random = new ArrayList<GasCentOutput>();

        for (GasCentOutput gasCentOutput : out) {
            for (int j = 0; j < gasCentOutput.weight; j++) {
                random.add(gasCentOutput);
            }
        }
		
		Collections.shuffle(random);
		
		GasCentOutput result = random.get(world.rand.nextInt(random.size()));

		int slot = result.slot + 4;
		
		if(inventory.getStackInSlot(slot).isEmpty()) {
			inventory.setStackInSlot(slot, result.output.copy());
		} else {
			inventory.getStackInSlot(slot).grow(result.output.getCount());
		}
	}

    public boolean hasCentUpgrade(){
        return inventory.getStackInSlot(1).getItem() == ModItems.upgrade_gc_speed;
    }
	
	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			if (needsUpdate) {
				needsUpdate = false;
			}
            upgradeManager.eval(inventory, 2, 3);
            int speedLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.SPEED), 9);
            int powerLevel = Math.min(upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.POWER), 3);
            int overLevel = upgradeManager.getLevel(ItemMachineUpgrade.UpgradeType.OVERDRIVE);

            int consumption = baseConsumption * (1 + speedLevel);
            consumption *= (overLevel * 3 + 1);
            consumption /= (1 + powerLevel);

			this.updateConnectionsExcept(world, pos, Library.POS_Y);

			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);
			
			//First number doesn't matter, there's only one tank.
			if(this.inputValidForTank(-1, 3))
				FFUtils.fillFromFluidContainer(inventory, tank, 3, 4);
			
			
			 this.hasCentUpgrade = hasCentUpgrade();
			if(this.power >= consumption && canProcess()) {
				
				isProgressing = true;
				
				this.progress++;
				
				this.power -= consumption;
				
				if(this.power < 0)
					power = 0;

                this.processTime = (int) (processingSpeed * (2 + powerLevel)/2D);
                this.processTime -= (int) (this.processTime * speedLevel / 10D);
                this.processTime /= (overLevel + 1);

                if(this.processTime <= 0) this.processTime = 1;

                if(this.progress >= this.processTime) {
					process();
				}
				
			} else {
				isProgressing = false;
				this.progress = 0;
			}

            if(countMufflers() == 0) PacketDispatcher.wrapper.sendToAll(new LoopedSoundPacket(pos.getX(), pos.getY(), pos.getZ()));
            detectAndSendChanges();
		}

		
	}
	
	private long detectPower;
	private int detectProgress;
	private boolean detectIsProgressing;
	private FluidTank detectTank;
	
	private void detectAndSendChanges(){
		boolean mark = false;
		if(detectPower != power){
			detectPower = power;
			mark = true;
		}
		if(detectProgress != progress){
			detectProgress = progress;
			mark = true;
		}
		if(detectIsProgressing != isProgressing){
			detectIsProgressing = isProgressing;
			mark = true;
		}
		if(!FFUtils.areTanksEqual(tank, detectTank)){
			detectTank = FFUtils.copyTank(tank);
			needsUpdate = true;
			mark = true;
		}

        NBTTagCompound data = new NBTTagCompound();
        tank.writeToNBT(data);
        data.setBoolean("ip", isProgressing);
        data.setInteger("pr", progress);
        data.setInteger("t", processTime);
        data.setLong("p", power);
        this.networkPack(data, 150);
        if(mark)
			markDirty();
	}

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        this.tank.readFromNBT(nbt);
        this.isProgressing = nbt.getBoolean("ip");

        this.progress = nbt.getInteger("pr");
        this.processTime = nbt.getInteger("t");
        this.power = nbt.getLong("p");
    }

	protected boolean inputValidForTank(int tank, int slot){
		if(!inventory.getStackInSlot(slot).isEmpty()){
            return isValidFluid(FluidUtil.getFluidContained(inventory.getStackInSlot(slot)));
		}
		return false;
	}
	
	private boolean isValidFluid(FluidStack stack) {
		if(stack == null)
			return false;
		return GasCentrifugeRecipes.recipes.containsKey(stack.getFluid());
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		return slot > 3;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return new int[]{0, 3, 4, 5, 6, 7, 8};
	}
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return new AxisAlignedBB(pos, pos.add(1, 4, 1));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
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
		if(tags.length == 1){
			tank.readFromNBT(tags[0]);
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (isValidFluid(resource)) {
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
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
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
