package com.hbm.tileentity.machine;

import api.hbm.tile.IHeatSource;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.inventory.container.ContainerOilburner;
import com.hbm.inventory.gui.GUIOilburner;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.RefStrings;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;

import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class TileEntityHeaterOilburner extends TileEntityMachineBase implements IGUIProvider, IHeatSource, IControlReceiver, IFluidHandler, ITickable, ITankPacketAcceptor {
    public boolean isOn = false;

    public FluidTank tank;
    public Fluid fluidType;

    private int cacheHeat = 0;

    public int setting = 1;

    public int heatEnergy = 0;

    public static final int maxHeatEnergy = 1_000_000;

    public TileEntityHeaterOilburner() {
        super(3);

        tank = new FluidTank(16000);
        fluidType = ModForgeFluids.gas;
        cacheHeat = FluidCombustionRecipes.getFlameEnergy(fluidType);
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            this.updateTankType();

            PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[]{tank}), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));

            if(inputValidForTank()) {
                if(FFUtils.fillFromFluidContainer(inventory, tank, 0, 1)) {
                    if(tank.getFluid() != null) {
                        fluidType = tank.getFluid().getFluid();
                    }

                    this.markDirty();
                }
            }

            boolean shouldCool = true;
            if(this.isOn && cacheHeat > 0 && this.heatEnergy < maxHeatEnergy) {
                int burnRate = setting;
                int toBurn = Math.min(burnRate, tank.getFluidAmount());

                tank.drain(toBurn, true);

                this.heatEnergy += cacheHeat * toBurn;
                this.markDirty();

                shouldCool = false;
            }

            if(this.heatEnergy >= maxHeatEnergy) {
                shouldCool = false;
            }

            if(shouldCool) {
                this.heatEnergy = Math.max(this.heatEnergy - Math.max(this.heatEnergy / 1000, 1), 0);
                this.markDirty();
            }

            NBTTagCompound data = new NBTTagCompound();
            tank.writeToNBT(data);
            data.setString("fluidType", fluidType.getName());
            data.setBoolean("isOn", isOn);
            data.setInteger("heatEnergy", heatEnergy);
            data.setByte("setting", (byte) this.setting);
            data.setInteger("cacheHeat", this.cacheHeat);

            this.networkPack(data, 25);
        }
    }

    private void updateTankType() {
        ItemStack slotId = inventory.getStackInSlot(2);
        Item itemId = slotId.getItem();
        if(itemId == ModItems.forge_fluid_identifier) {
            Fluid fluid = ItemForgeFluidIdentifier.getType(slotId);
            int energy = FluidCombustionRecipes.getFlameEnergy(fluid);

            if(fluidType != fluid) {
                fluidType = fluid;
                tank.setFluid(null);
                cacheHeat = energy;

                this.markDirty();
            }
        }
    }

    private boolean inputValidForTank() {
        ItemStack slotInput = inventory.getStackInSlot(0);
        if(slotInput != ItemStack.EMPTY && tank != null) {
            return FFUtils.checkRestrictions(slotInput, f -> f.getFluid() == fluidType);
        }

        return false;
    }

    @Override
    public void recievePacket(NBTTagCompound[] tags) {
        if(tags.length != 1) {
            return;

        }
        tank.readFromNBT(tags[0]);
    }

    @Override
    public String getName() {
        return "container.heaterOilburner";
    }

    @Override
    public void networkUnpack(NBTTagCompound nbt) {
        tank.readFromNBT(nbt);
        if(nbt.hasKey("fluidType")) {
            fluidType = FluidRegistry.getFluid(nbt.getString("fluidType"));
        }

        isOn = nbt.getBoolean("isOn");
        heatEnergy = nbt.getInteger("heatEnergy");
        setting = nbt.getByte("setting");
        cacheHeat = nbt.getInteger("cacheHeat");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        tank.readFromNBT(nbt);
        if(nbt.hasKey("fluidType")) {
            fluidType = FluidRegistry.getFluid(nbt.getString("fluidType"));
        }

        isOn = nbt.getBoolean("isOn");
        heatEnergy = nbt.getInteger("heatEnergy");
        setting = nbt.getByte("setting");
        cacheHeat = nbt.getInteger("cacheHeat");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        tank.writeToNBT(nbt);
        if(fluidType != null) {
            nbt.setString("fluidType", fluidType.getName());
        }

        nbt.setBoolean("isOn", isOn);
        nbt.setInteger("heatEnergy", heatEnergy);
        nbt.setByte("setting", (byte) this.setting);
        nbt.setInteger("cacheHeat", this.cacheHeat);

        return super.writeToNBT(nbt);
    }

    public void toggleSettingUp() {
        setting++;

        if(setting > 100) {
            setting = 1;
        }
    }

    public void toggleSettingDown() {
        setting--;

        if(setting < 1) {
            setting = 100;
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{tank.getTankProperties()[0]};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource != null && resource.getFluid() == fluidType && resource.amount > 0) {
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

    @Override
    public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return new ContainerOilburner(player.inventory, this);
    }

    @SideOnly(Side.CLIENT)
    private ResourceLocation texture;

    @Override
    @SideOnly(Side.CLIENT)
    public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if(texture == null) {
            texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/machine/gui_oilburner.png");
        }

        return new GUIOilburner(player.inventory, this, texture);
    }

    @Override
    public int getHeatStored() {
        return heatEnergy;
    }

    @Override
    public void useUpHeat(int heat) {
        this.heatEnergy = Math.max(0, this.heatEnergy - heat);
    }

    @Override
    public boolean hasPermission(EntityPlayer player) {
        return player.getDistanceSq(pos) <= 256;
    }

    @Override
    public void receiveControl(NBTTagCompound data) {
        if(data.hasKey("toggle")) {
            this.isOn = !this.isOn;
        }

        this.markDirty();
    }

    AxisAlignedBB bb = null;

    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        if(bb == null) {
            bb = new AxisAlignedBB(pos.getX() - 1, pos.getY(), pos.getZ() - 1, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }
}
