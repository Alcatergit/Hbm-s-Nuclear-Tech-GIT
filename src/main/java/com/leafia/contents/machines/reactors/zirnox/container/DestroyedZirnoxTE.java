package com.leafia.contents.machines.reactors.zirnox.container;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.ContaminationUtil;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DestroyedZirnoxTE extends TileEntity implements ITickable {
    public boolean burning = true;
    int timer = 3600*2*20+1500;
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        burning = compound.getBoolean("burning");
        timer = compound.getInteger("timer");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setBoolean("burning", burning);
        compound.setInteger("timer", timer);
        return super.writeToNBT(compound);
    }
    @Override
    public void update() {
        if (!world.isRemote && burning) {
            if (timer <= 0) {
                if (world.rand.nextInt(5000) == 0)
                    burning = false;
            } else timer--;
            if (world.getBlockState(pos.up(2)).getBlock() == ModBlocks.block_foam) {
                if (world.rand.nextInt(25) == 0)
                    burning = false;
            }
            if (world.rand.nextInt(10) == 0) {
                if (world.isAirBlock(pos.up(2)))
                    world.setBlockState(pos.up(2), ModBlocks.gas_radon_death.getDefaultState());
            }
            if (burning && world.getTotalWorldTime()%50 == 0) {
                NBTTagCompound data = new NBTTagCompound();
                data.setString("type", "rbmkflame");
                data.setInteger("maxAge", 90);
                PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.getX() + 0.25 + world.rand.nextDouble() * 0.5, pos.getY() + 2.75, pos.getZ() + 0.25 + world.rand.nextDouble() * 0.5), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 1.75, pos.getZ() + 0.5, 75));
                MainRegistry.proxy.effectNT(data);
                world.playSound(null, pos.getX() + 0.5F, pos.getY() + 1.5, pos.getZ() + 0.5, SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.BLOCKS, 1.0F + world.rand.nextFloat(), world.rand.nextFloat() * 0.7F + 0.3F);
                ContaminationUtil.radiate(world,pos.getX(),pos.getY()+1,pos.getZ(),64,90,0,40);
                RadiationSavedData.incrementRad(world, pos, 50, 105);
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public double getMaxRenderDistanceSquared()
    {
        return 65536.0D;
    }
}