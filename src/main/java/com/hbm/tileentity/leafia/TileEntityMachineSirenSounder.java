package com.hbm.tileentity.leafia;

import com.hbm.tileentity.machine.TileEntityMachineSiren;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import pl.asie.computronics.api.audio.AudioPacket;
import pl.asie.computronics.api.audio.IAudioReceiver;

import javax.annotation.Nullable;

@Optional.InterfaceList({
        @Optional.Interface(iface = "pl.asie.computronics.api.audio.IAudioReceiver", modid = "computronics")
})
public class TileEntityMachineSirenSounder extends TileEntity implements IAudioReceiver {
    TileEntityMachineSiren host;
    int id;
    public TileEntityMachineSirenSounder(TileEntityMachineSiren host,int id) {
        this.host = host;
        this.id = id;
    }

    @Override
    @Optional.Method(modid="computronics")
    public World getSoundWorld() {
        return host.getSoundWorld();
    }

    @Override
    @Optional.Method(modid="computronics")
    public Vec3d getSoundPos() {
        return host.getSoundPos();
    }

    @Override
    @Optional.Method(modid="computronics")
    public int getSoundDistance() {
        return host.getSoundDistance();
    }

    @Override
    public void receivePacket(AudioPacket audioPacket, @Nullable EnumFacing enumFacing) {}

    @Override
    @Optional.Method(modid="computronics")
    public String getID() {
        return host.getID()+" ["+id+"]";
    }

    @Override
    @Optional.Method(modid="computronics")
    public boolean connectsAudio(EnumFacing enumFacing) {
        return false;
    }
}
