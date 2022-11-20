package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.main.MainRegistry;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;
import api.hbm.entity.IRadarDetectable;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityMissileBaseAdvanced extends Entity implements IChunkLoader, IConstantRenderer, IRadarDetectable {

	public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(EntityMissileBaseAdvanced.class, DataSerializers.VARINT);

	int startX;
	int startZ;
	int targetX;
	int targetZ;
	public int velocity;
	double decelY;
	double accelXZ;
	boolean isCluster = false;
	private Ticket loaderTicket;
	public int health = 50;

	public double prevPosX2;
	public double prevPosY2;
	public double prevPosZ2;

	public EntityMissileBaseAdvanced(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}

	public EntityMissileBaseAdvanced(World world, float x, float y, float z, int a, int b) {
		super(world);
		this.ignoreFrustumCheck = true;
		/*this.posX = x;
		this.posY = y;
		this.posZ = z;*/
		this.setLocationAndAngles(x, y, z, 0, 0);
		startX = (int) x;
		startZ = (int) z;
		targetX = a;
		targetZ = b;
		this.motionY = 2;

		Vec3d vector = new Vec3d(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1 / vector.lengthVector();
		decelY *= 2;

		velocity = 1;

		this.setSize(1.5F, 1.5F);
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
				health -= amount;

				if (this.health <= 0) {
					this.setDead();
					this.killMissile();
				}
			}

			return true;
		}
	}

	private void killMissile() {
		ExplosionLarge.explode(world, posX, posY, posZ, 5, true, false, true);
		ExplosionLarge.spawnShrapnelShower(world, posX, posY, posZ, motionX, motionY, motionZ, 15, 0.075);
		ExplosionLarge.spawnMissileDebris(world, posX, posY, posZ, motionX, motionY, motionZ, 0.25, getDebris(), getDebrisRareDrop());
	}

	List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();

	@Override
	public void init(Ticket ticket) {
		if (!world.isRemote) {

			if (ticket != null) {

				if (loaderTicket == null) {

					loaderTicket = ticket;
					loaderTicket.bindEntity(this);
					loaderTicket.getModData();
				}

				ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
			}
		}
	}

	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if (!world.isRemote && loaderTicket != null) {
			for (ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}

			loadedChunks.clear();
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ - 1));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ - 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX + 1, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ + 1));
			loadedChunks.add(new ChunkPos(newChunkX - 1, newChunkZ));
			loadedChunks.add(new ChunkPos(newChunkX, newChunkZ - 1));

			for (ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.forceChunk(loaderTicket, chunk);
			}
		}
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
		this.getDataManager().register(HEALTH, Integer.valueOf(this.health));
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
		velocity = nbt.getInteger("veloc");

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
		nbt.setInteger("veloc", velocity);

	}

	@Override
	public void onUpdate() {

		if (velocity < 1)
			velocity = 1;
		if (this.ticksExisted > 80)
			velocity = 3;
		else if (this.ticksExisted > 40)
			velocity = 2;

		this.getDataManager().set(HEALTH, Integer.valueOf(this.health));

		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		this.prevPosX2 = this.posX;
		this.prevPosY2 = this.posY;
		this.prevPosZ2 = this.posZ;

		 //TODO: instead of crappy skipping, implement a hitscan
		for (int i = 0; i < velocity; i++) {
			// this.posX += this.motionX;
			// this.posY += this.motionY;
			// this.posZ += this.motionZ;
			this.setLocationAndAngles(posX + this.motionX, posY + this.motionY, posZ + this.motionZ, 0, 0);

			this.rotation();

			this.motionY -= decelY;

			Vec3d vector = new Vec3d(targetX - startX, 0, targetZ - startZ);
			vector = vector.normalize();
			vector = new Vec3d(vector.x * accelXZ, vector.y, vector.z * accelXZ);

			if (motionY > 0) {
				motionX += vector.x;
				motionZ += vector.z;
			}

			if (motionY < 0) {
				motionX -= vector.x;
				motionZ -= vector.z;
			}

			if (!this.world.isRemote)
				// this.worldObj.spawnEntityInWorld(new
				// EntitySmokeFX(this.worldObj, this.posX, this.posY, this.posZ,
				// 0.0, 0.0, 0.0));
				PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(posX, posY, posZ, 2), new TargetPoint(world.provider.getDimension(), posX, posY, posZ, 300));
			BlockPos pos = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
			if (this.world.getBlockState(pos).getBlock() != Blocks.AIR && this.world.getBlockState(pos).getBlock() != Blocks.WATER && this.world.getBlockState(pos) != Blocks.FLOWING_WATER) {

				if (!this.world.isRemote) {
					onImpact();
				}
				this.setDead();
				return;
			}

			loadNeighboringChunks((int) (posX / 16), (int) (posZ / 16));

			if (motionY < -1 && this.isCluster && !world.isRemote) {
				cluster();
				this.setDead();
				return;
			}
		}
	}

	protected void rotation() {
		float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
		this.rotationYaw = (float) (Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

		for (this.rotationPitch = (float) (Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F) {
			;
		}

		while (this.rotationPitch - this.prevRotationPitch >= 180.0F) {
			this.prevRotationPitch += 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw < -180.0F) {
			this.prevRotationYaw -= 360.0F;
		}

		while (this.rotationYaw - this.prevRotationYaw >= 180.0F) {
			this.prevRotationYaw += 360.0F;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean isInRangeToRenderDist(double distance) {
		return distance < 500000;
	}

	public abstract void onImpact();

	public abstract List<ItemStack> getDebris();

	public abstract ItemStack getDebrisRareDrop();

	public void cluster() {
	}

}
