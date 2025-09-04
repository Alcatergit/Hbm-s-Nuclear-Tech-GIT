package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.lib.DirPos;
import com.hbm.lib.ForgeDirection;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

public class TileEntityCondenserPowered extends TileEntityCondenser implements IEnergyUser {
    public boolean isLoaded = true;

    public long power;
    public float spin;
    public float lastSpin;

    //Configurable values
    public static long maxPower = 10_000_000;
    public static int inputTankSizeP = 1_000_000;
    public static int outputTankSizeP = 1_000_000;
    public static int powerConsumption = 10;

    public TileEntityCondenserPowered() {
        tanks = new FluidTank[2];
        tanks[0] = new FluidTank(inputTankSizeP);
        tanks[1] = new FluidTank(outputTankSizeP);
    }

    @Override
    public void update() {
        if(!world.isRemote) {
            updateConnections();
        }

        super.update();

        if(world.isRemote) {
            this.lastSpin = this.spin;

            if(this.waterTimer > 0) {
                this.spin += 30F;

                if(this.spin >= 360F) {
                    this.spin -= 360F;
                    this.lastSpin -= 360F;
                }

                if(world.getTotalWorldTime() % 4 == 0) {
                    ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
                    world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5 + dir.offsetX * 1.5, pos.getY() + 1.5, pos.getZ() + 0.5 + dir.offsetZ * 1.5, dir.offsetX * 0.1, 0, dir.offsetZ * 0.1);
                    world.spawnParticle(EnumParticleTypes.CLOUD, pos.getX() + 0.5 - dir.offsetX * 1.5, pos.getY() + 1.5, pos.getZ() + 0.5 - dir.offsetZ * 1.5, dir.offsetX * -0.1, 0, dir.offsetZ * -0.1);
                }
            }
        }
    }

    @Override
    public void packExtra(NBTTagCompound data) {
        data.setLong("power", power);
    }

    @Override
    public boolean extraCondition(int convert) {
        return power >= convert * 10L;
    }

    @Override
    public void postConvert(int convert) {
        this.power -= (long) convert * powerConsumption;
        if(this.power < 0) this.power = 0;
    }

    @Override
    public void networkUnpack(NBTTagCompound data) {
        super.networkUnpack(data);
        power = data.getLong("power");
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        this.power = nbt.getLong("power");
    }

    @Override
    public @NotNull NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setLong("power", power);

        return nbt;
    }

    @Override
    public void fillFluidInit(FluidTank tank) {
        for(DirPos pos : getConPos()) {
            fillFluid(pos.getPos().getX(), pos.getPos().getY(), pos.getPos().getZ(), tank);
        }
    }

    protected void updateConnections() {
        for(DirPos pos : getConPos()) {
            this.trySubscribe(world, pos.getPos(), pos.getDir());
        }
    }

    public DirPos[] getConPos() {
        ForgeDirection dir = ForgeDirection.getOrientation(this.getBlockMetadata() - 10);
        ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

        return new DirPos[] {
                new DirPos(pos.getX() + rot.offsetX * 4, pos.getY() + 1, pos.getZ() + rot.offsetZ * 4, rot),
                new DirPos(pos.getX() - rot.offsetX * 4, pos.getY() + 1, pos.getZ() - rot.offsetZ * 4, rot.getOpposite()),
                new DirPos(pos.getX() + dir.offsetX * 2 - rot.offsetX, pos.getY() + 1, pos.getZ() + dir.offsetZ * 2 - rot.offsetZ, dir),
                new DirPos(pos.getX() + dir.offsetX * 2 + rot.offsetX, pos.getY() + 1, pos.getZ() + dir.offsetZ * 2 + rot.offsetZ, dir),
                new DirPos(pos.getX() - dir.offsetX * 2 - rot.offsetX, pos.getY() + 1, pos.getZ() - dir.offsetZ * 2 - rot.offsetZ, dir.getOpposite()),
                new DirPos(pos.getX() - dir.offsetX * 2 + rot.offsetX, pos.getY() + 1, pos.getZ() - dir.offsetZ * 2 + rot.offsetZ, dir.getOpposite())
        };
    }

    AxisAlignedBB bb = null;

    @Override
    public @NotNull AxisAlignedBB getRenderBoundingBox() {
        if(bb == null) {
            bb = new AxisAlignedBB(
                    pos.getX() - 3,
                    pos.getY(),
                    pos.getZ() - 3,
                    pos.getX() + 4,
                    pos.getY() + 3,
                    pos.getZ() + 4
            );
        }

        return bb;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared() {
        return 65536.0D;
    }

    @Override
    public long getPower() {
        return this.power;
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
    public boolean isLoaded() {
        return isLoaded;
    }

    @Override
    public void onChunkUnload() {
        super.onChunkUnload();
        this.isLoaded = false;
    }
}
