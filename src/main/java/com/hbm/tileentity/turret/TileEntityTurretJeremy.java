package com.hbm.tileentity.turret;

import java.util.ArrayList;
import java.util.List;

import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityTurretJeremy extends TileEntityTurretBaseNT {

	static List<Integer> configs = new ArrayList<>();

	static {
		configs.add(BulletConfigSyncingUtil.SHELL_NORMAL);
		configs.add(BulletConfigSyncingUtil.SHELL_EXPLOSIVE);
		configs.add(BulletConfigSyncingUtil.SHELL_AP);
		configs.add(BulletConfigSyncingUtil.SHELL_DU);
		configs.add(BulletConfigSyncingUtil.SHELL_W9);
	}

	@Override
	protected List<Integer> getAmmoList(){
		return configs;
	}

	@Override
	public String getName(){
		return "container.turretJeremy";
	}

	@Override
	public double getDecetorGrace(){
		return 16D;
	}

	@Override
	public double getTurretDepression(){
		return 45D;
	}

	@Override
	public long getMaxPower(){
		return 10000;
	}

	@Override
	public double getBarrelLength(){
		return 4.25D;
	}

	@Override
	public double getDecetorRange(){
		return 80D;
	}

	public int timer;
	public int reload;

	@Override
	public void update(){
		if(reload > 0)
			reload--;
		
		if(reload == 1)
			this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.jeremy_reload, SoundCategory.BLOCKS, 2.0F, 1.0F);
		
		super.update();
	}

	@Override
	public void updateFiringTick(){
		timer++;

		if(timer % 40 == 0) {

			BulletConfiguration conf = this.getFirstConfigLoaded();

			if(conf != null) {
				this.spawnBullet(conf);
				this.conusmeAmmo(conf.ammo);
				this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.jeremy_fire, SoundCategory.BLOCKS, 4.0F, 1.0F);
				
				Vec3 pos = new Vec3(this.getTurretPos());
				Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
				vec.rotateAroundZ((float) -this.rotationPitch);
				vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));

				reload = 20;

				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "vanillaExt");
				data.setString("mode", "largeexplode");
				data.setFloat("size", 0F);
				data.setByte("count", (byte)5);
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 50));
			}
		}
	}
}
