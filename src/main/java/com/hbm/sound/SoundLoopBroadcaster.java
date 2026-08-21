package com.hbm.sound;

import java.util.ArrayList;
import java.util.List;

import com.hbm.tileentity.machine.TileEntityBroadcaster;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;

public class SoundLoopBroadcaster extends SoundLoopMachine {
	
	public static List<SoundLoopBroadcaster> list = new ArrayList<SoundLoopBroadcaster>();
	public float intendedVolume = 25.0F;

	public SoundLoopBroadcaster(SoundEvent path, TileEntity te) {
		super(path, te, 1);
		list.add(this);
		this.attenuationType = ISound.AttenuationType.NONE;
	}

	@Override
	public void update() {
		super.update();
        if(this.donePlaying) return;
		EntityPlayerSP player = Minecraft.getMinecraft().player;
		float f = 0;

        if(player != null) {
            f = (float)Math.sqrt(Math.pow(xPosF - player.posX, 2) + Math.pow(yPosF - player.posY, 2) + Math.pow(zPosF - player.posZ, 2));
            // ===== MODIFICATION: Improved volume calculation function =====
            // Original function: volume = func(f, intendedVolume);
			// Problem: The volume suddenly jumps from 0 to 0.008 at the 25-unit boundary, causing popping sounds.
			// New function: Uses a smooth transition to avoid boundary discontinuities.
            volume = smoothVolumeFunc(f, intendedVolume);
            // ==============================================================

            if(!(player.world.getTileEntity(new BlockPos((int)xPosF, (int)yPosF, (int)zPosF)) instanceof TileEntityBroadcaster)) {
                this.donePlaying = true;
                volume = 0;
            }
        } else {
            volume = intendedVolume;
        }
    }

    // ===== NEW: Smooth Volume Calculation Function =====
	/**
	* Improved volume calculation function to address popping noise issue at the 25-grid boundary
	* @param f Current distance
	* @param v Maximum effective distance (25 grids)
	* @return Smoothed volume value
	*/
    public float smoothVolumeFunc(float f, float v) {
        // If the distance exceeds 25 grids, volume is 0
        if(f >= v) {
            return 0.0F;
        }

        // Calculate relative distance ratio (between 0 and 1)
        float ratio = f / v;

        // Use smooth transition function: changes gradually near the boundary to avoid sudden jumps
		// Quadratic function implementation for smooth transition: 1 - (ratio)^2
        float smoothRatio = 1.0F - ratio * ratio;

        // Ensure volume stays between 0 and 1
        return Math.max(0.0F, Math.min(1.0F, smoothRatio));
    }
    // ===================

    // Keep original function for potential future use
/*    public float func(float f, float v) {
        return (f / v) * -2 + 2;
    }
*/
}
