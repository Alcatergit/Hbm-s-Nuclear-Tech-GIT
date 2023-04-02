package com.hbm.tileentity.machine.oil;

import com.hbm.blocks.ModBlocks;
import com.hbm.entity.particle.EntityGasFX;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.world.feature.OilSpot;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class TileEntityMachineFrackingTower extends TileEntityOilDrillBase {

    public static long maxPower = 5000000;

    protected static int consumption = 5000;
    private static int solutionRequired = 10;

    protected static int delay = 20;
    protected static int oilPerDeposit = 1000;

    protected static int oilPerBedrockDeposit = 100;
    protected static int gasPerBedrockDepositMin = 10;
    protected static int extraGasPerBedrockDepositMax = 50;

    protected static int destructionRange = 32;


    public TileEntityMachineFrackingTower() {
        super();
        super.maxPower = this.maxPower;
        super.consumption = this.consumption;
        super.delay = this.delay;
        tanks[2] = new FluidTank(64000);
        tankTypes[2] = ModForgeFluids.fracksol;
    }

    public String getInventoryName() { return this.hasCustomInventoryName() ? this.getCustomName() : "container.frackingTower"; }



    @Override
    public void update() {
        int timer = delay;

        age++;
        age2++;
        if(age >= timer)
            age -= timer;
        if(age2 >= 20)
            age2 -= 20;
        if(!world.isRemote) {
            int tank0Amount = tanks[0].getFluidAmount();
            int tank1Amount = tanks[1].getFluidAmount();
            int tank2Amount = tanks[2].getFluidAmount();

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

            if(power >= consumption && tank2Amount >= solutionRequired && !(tank0Amount >= tanks[0].getCapacity() || tank1Amount >= tanks[1].getCapacity())) {

                // operation start

                if(age == timer - 1) {
                    warning = 0;

                    // warning 0, green: fracking tower is operational
                    // warning 1, red: fracking tower is full, has no power, has no fracking solution or the
                    // drill is jammed
                    // warning 2, yellow: drill has reached max depth

                    for(int i = pos.getY() - 1; i > pos.getY() - 1 - 100; i--) {

                        Block b = world.getBlockState(new BlockPos(pos.getX(), i, pos.getZ())).getBlock();
                        if(b == ModBlocks.oil_pipe)
                            continue;

                        if((b.isReplaceable(world, new BlockPos(pos.getX(), i, pos.getZ())) || b.getExplosionResistance(null) < 100) && !(b == ModBlocks.ore_oil || b == ModBlocks.ore_oil_empty)) {
                            world.setBlockState(new BlockPos(pos.getX(), i, pos.getZ()), ModBlocks.oil_pipe.getDefaultState());

                            // Code 2: The drilling ended
                            if(i == pos.getY() - 200)
                                warning = 2;
                            break;

                        } else if(this.tanks[0].getFluidAmount() < this.tanks[0].getCapacity() && this.tanks[1].getFluidAmount() < this.tanks[1].getCapacity()) {

                            final int succNumber = succ(pos.getX(), i, pos.getZ());
                            if(succNumber != 0) {

                                if (succNumber == 1) {
                                    this.tanks[0].fill(new FluidStack(tankTypes[0], oilPerDeposit), true);
                                    this.tanks[1].fill(new FluidStack(tankTypes[1], (gasPerDepositMin + rand.nextInt(extraGasPerDepositMax))), true);
                                }
                                else {
                                    this.tanks[0].fill(new FluidStack(tankTypes[0], oilPerBedrockDeposit), true);
                                    this.tanks[1].fill(new FluidStack(tankTypes[1], (gasPerBedrockDepositMin + rand.nextInt(extraGasPerBedrockDepositMax))), true);
                                }
                                needsUpdate = true;

                                OilSpot.generateOilSpot(world, pos.getX(), pos.getZ(), destructionRange, 10);

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

                power -= consumption;
                tanks[2].drain(solutionRequired, true);
            } else {
                warning = 1;
            }

            warning2 = 0;
            if(tanks[1].getFluidAmount() > 0) {
                if(inventory.getStackInSlot(5).getItem() == ModItems.fuse || inventory.getStackInSlot(5).getItem() == ModItems.screwdriver) {
                    warning2 = 2;
                    tanks[1].drain(50, true);
                    needsUpdate = true;
                    world.spawnEntity(new EntityGasFX(world, pos.getX() + 0.5F, pos.getY() + 19F, pos.getZ() + 0.5F, 0.0, 0.0, 0.0));
                } else {
                    warning2 = 1;
                }
            }

            PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
            PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[] { tanks[0], tanks[1], tanks[2] }), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
            if(tank0Amount != tanks[0].getFluidAmount() || tank1Amount != tanks[1].getFluidAmount() || tank2Amount != tanks[2].getFluidAmount()){
                markDirty();
            }
        }
    }




    public void fillFluidInit(FluidTank tank) {
        needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(1, 0, 0), 2000) || needsUpdate;
        needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(-1, 0, 0), 2000) || needsUpdate;
        needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 0, 1), 2000) || needsUpdate;
        needsUpdate = FFUtils.fillFluid(this, tank, world, pos.add(0, 0, -1), 2000) || needsUpdate;

    }



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
          }
        else if (resource.getFluid() == tankTypes[2]) {
            int prevAmount = tanks[2].getFluidAmount();
            FluidStack drained = tanks[2].drain(resource.amount, doDrain);
            if (tanks[2].getFluidAmount() != prevAmount)
                needsUpdate = true;
            return drained;
        }
        else {
            return null;
        }
    }

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
          }
        else if(tanks[2].getFluidAmount() > 0) {
            int prevAmount = tanks[2].getFluidAmount();
            FluidStack drained = tanks[2].drain(maxDrain, doDrain);
            if(tanks[2].getFluidAmount() != prevAmount)
                needsUpdate = true;
            return drained;
        }
        else {
            return null;
        }
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if(resource != null){
            if(resource.getFluid() == ModForgeFluids.fracksol){
                return tanks[2].fill(resource, doFill);
            }

        }
        return 0;
    }


    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] { tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0]};
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
    public void readFromNBT(NBTTagCompound compound) {
        tankTypes[2] = ModForgeFluids.fracksol;

        super.readFromNBT(compound);
    }

    @Override
    public long getPowerScaled(long i) {
        return (power * i) / maxPower;
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
}