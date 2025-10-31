package com.leafia.contents.network.computers.audiocable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;
import pl.asie.computronics.api.audio.AudioPacket;
import pl.asie.computronics.api.audio.IAudioReceiver;
import pl.asie.computronics.audio.AudioUtils;

import javax.annotation.Nullable;

@Optional.InterfaceList({
		@Optional.Interface(iface = "pl.asie.computronics.api.audio.IAudioReceiver", modid = "computronics")
})
public class AudioCableTE extends TileEntity implements IAudioReceiver {
	@Nullable
	@Override
	public World getSoundWorld() {
		return null;
	}
	@Override
	public Vec3d getSoundPos() {
		return Vec3d.ZERO;
	}
	@Override
	public int getSoundDistance() {
		return 0;
	}
	@Override
	public void receivePacket(AudioPacket audioPacket,@Nullable EnumFacing enumFacing) {

	}
	@Override
	public String getID() {
		return AudioUtils.positionId(this.getPos());
	}
	@Override
	public boolean connectsAudio(EnumFacing enumFacing) {
		return true;
	}
}
