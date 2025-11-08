package com.hbm.entity.missile;

import java.util.List;

import com.hbm.config.WeaponConfig;
import com.hbm.entity.logic.EntityChunky;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import api.hbm.entity.IRadarDetectable;
import com.hbm.packet.LoopedEntitySoundPacket;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityMissileBaseAdvanced extends EntityChunky implements IConstantRenderer, IRadarDetectable {

	public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(EntityMissileBaseAdvanced.class, DataSerializers.VARINT);
	public static final double particleSpeed = 1.75D;

	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double velocity;
	double decelY;
	double accelXZ;
	boolean isCluster = false;
	public static double acceleration = 1;
	public int health;

	public EntityMissileBaseAdvanced(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
        this.health = getHP();
	}

	public EntityMissileBaseAdvanced(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 2;

		Vec3d vector = new Vec3d(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1 / vector.length();
		decelY *= 2;

		velocity = 0.0;
		this.setSize(1.5F, 9F);
        this.health = getHP();
	}

    public int getHP(){
        return switch (getTargetType()){
            case MISSILE_TIER0 -> 20;
            case MISSILE_TIER1 -> 30;
            case MISSILE_TIER2 -> 40;
            case MISSILE_TIER3 -> 50;
            case MISSILE_TIER4 -> 60;
            default -> 10;
        };
    }

	public void setAcceleration(double multiplier){
		acceleration = multiplier;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source)) {
			return false;
		} else {
			if (!this.isDead && !this.world.isRemote) {
				health -= (int) amount;

				if (this.health <= 0) {
					this.setDead();
					this.killMissile();
				}
                this.getDataManager().set(HEALTH, this.health);
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(world, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(world, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
		if(WeaponConfig.dropMissileParts)
			ExplosionLarge.spawnMissileDebris(world, posX, posY, posZ, motionX, motionY, motionZ, 0.25, getDebris(), getDebrisRareDrop());
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.getDataManager().register(HEALTH, this.health);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		motionX = nbt.getDouble("moX");
		motionY = nbt.getDouble("moY");
		motionZ = nbt.getDouble("moZ");
		posX = nbt.getDouble("poX");
		posY = nbt.getDouble("poY");
		posZ = nbt.getDouble("poZ");
		decelY = nbt.getDouble("decel");
		accelXZ = nbt.getDouble("accel");
		targetX = nbt.getInteger("tX");
		targetZ = nbt.getInteger("tZ");
		startX = nbt.getInteger("sX");
		startZ = nbt.getInteger("sZ");
		velocity = nbt.getDouble("veloc");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setDouble("moX", motionX);
		nbt.setDouble("moY", motionY);
		nbt.setDouble("moZ", motionZ);
		nbt.setDouble("poX", posX);
		nbt.setDouble("poY", posY);
		nbt.setDouble("poZ", posZ);
		nbt.setDouble("decel", decelY);
		nbt.setDouble("accel", accelXZ);
		nbt.setInteger("tX", targetX);
		nbt.setInteger("tZ", targetZ);
		nbt.setInteger("sX", startX);
		nbt.setInteger("sZ", startZ);
		nbt.setDouble("veloc", velocity);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if(this.ticksExisted < 10 && world.isRemote){
			ExplosionLarge.spawnParticlesRadial(world, posX, posY, posZ, 15);
			return;
		}
		this.getDataManager().set(HEALTH, this.health);
        doMovement();
        doContrail();
        checkImpact();
        checkCluster();

		PacketDispatcher.wrapper.sendToAll(new LoopedEntitySoundPacket(this.getEntityId()));
	}

    public void doContrail(){
        if(this.world.isRemote) {
            Vec3 v = Vec3.createVectorHelper(motionX, motionY, motionZ);
            v = v.normalize();
            for(int i = 0; i < velocity; i++){
                MainRegistry.proxy.spawnParticle(posX - v.xCoord * i, posY - v.yCoord * i, posZ - v.zCoord * i, "exDark", new float[]{(float)(this.motionX * -particleSpeed), (float)(this.motionY * -particleSpeed), (float)(this.motionZ * -particleSpeed)});
            }
        }
    }

    public void doMovement(){
        double oldPosY = this.posY;
        this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI), (float)(Math.atan2(this.motionY, MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / Math.PI) - 90);
        this.prevPosY = oldPosY;

        this.motionY -= decelY * velocity;

        Vec3 vector = Vec3.createVectorHelper(targetX - startX, 0, targetZ - startZ);
        vector = vector.normalize();
        vector.xCoord *= accelXZ * velocity;
        vector.zCoord *= accelXZ * velocity;

        if (motionY > 0) {
            motionX += vector.xCoord;
            motionZ += vector.zCoord;
        }

        if (motionY < 0) {
            motionX -= vector.xCoord;
            motionZ -= vector.zCoord;
        }

        if(velocity < 5)
            velocity += 0.005 * acceleration;
    }

    public void checkCluster(){
        if (this.isCluster && !world.isRemote && posY < 300 && motionY < -1) {
            cluster();
            this.setDead();
        }
    }

    public void checkImpact(){
        Block b = this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock();
        if((b != Blocks.AIR && b != Blocks.WATER && b != Blocks.FLOWING_WATER) || posY < 1) {
            if(posY < 1){
                this.setLocationAndAngles((int)this.posX, world.getHeight((int)this.posX, (int)this.posZ), (int)this.posZ, 0, 0);
            }
            if (!this.world.isRemote && this.ticksExisted > 100) onImpact();
            this.setDead();
        }
    }

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 5000;
	}

	public abstract void onImpact();

	public abstract List<ItemStack> getDebris();

	public abstract ItemStack getDebrisRareDrop();

	public void cluster() {
	}
}
