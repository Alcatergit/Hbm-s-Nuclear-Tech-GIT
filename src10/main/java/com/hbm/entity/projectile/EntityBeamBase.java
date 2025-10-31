package com.hbm.entity.projectile;

import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityBeamBase extends Entity {

	public static final DataParameter<String> PLAYER_NAME = EntityDataManager.createKey(EntityBeamBase.class, DataSerializers.STRING);
	
	public EntityBeamBase(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
	}

	public EntityBeamBase(World world, EntityPlayer player) {
		super(world);

		this.ignoreFrustumCheck = true;
		this.getDataManager().set(PLAYER_NAME, player.getDisplayName().getUnformattedText());

		Vec3 vec = new Vec3(player.getLookVec());
		vec.rotateAroundY(-90F);
		float l = 0.075F;
		vec.xCoord *= l;
		vec.yCoord *= l;
		vec.zCoord *= l;

		Vec3 vec0 = new Vec3(player.getLookVec());
		float d = 0.1F;
		vec0.xCoord *= d;
		vec0.yCoord *= d;
		vec0.zCoord *= d;

		this.setPosition(player.posX + vec.xCoord + vec0.xCoord, player.posY + player.getEyeHeight() + vec0.yCoord, player.posZ + vec.zCoord + vec0.zCoord);
	}
	
	@Override
	protected void entityInit() {
		this.getDataManager().register(PLAYER_NAME, "");
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender() {
		return 15728880;
	}
	
	@Override
	public float getBrightness() {
		return 1.0F;
	}

}
