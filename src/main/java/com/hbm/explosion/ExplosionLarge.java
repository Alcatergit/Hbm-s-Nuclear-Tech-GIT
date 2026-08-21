package com.hbm.explosion;

import java.util.List;
import java.util.Random;

import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.particle.EntityGasFlameFX;
import com.hbm.entity.projectile.EntityOilSpill;
import com.hbm.entity.projectile.EntityRubble;
import com.hbm.entity.projectile.EntityShrapnel;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import com.hbm.util.ContaminationUtil;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ExplosionLarge {

	static Random rand = new Random();

	public static void spawnParticlesRadial(World world, double x, double y, double z, int count) {

		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "radial");
		data.setInteger("count", count);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),  new TargetPoint(world.provider.getDimension(), x, y, z, 250));
	}

	public static void spawnParticles(World world, double x, double y, double z, int count) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "cloud");
		data.setInteger("count", count);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y, z),  new TargetPoint(world.provider.getDimension(), x, y, z, 250));
	}
	
	public static void spawnBurst(World world, double x, double y, double z, int count, double strength) {
		
		Vec3d vec = new Vec3d(strength, 0, 0);
		vec = vec.rotateYaw(rand.nextInt(360));
		
		for(int i = 0; i < count; i++) {
			EntityGasFlameFX fx = new EntityGasFlameFX(world, x, y, z, 0.0, 0.0, 0.0);
			fx.motionY = 0;
			fx.motionX = vec.x;
			fx.motionZ = vec.z;
			world.spawnEntity(fx);
			
			vec = vec.rotateYaw((float) 360 / count);
		}
	}
	
	public static void spawnShock(World world, double x, double y, double z, int count, double strength) {
		
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "smoke");
		data.setString("mode", "shock");
		data.setInteger("count", count);
		data.setDouble("strength", strength);
		PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, x, y + 0.5, z),  new TargetPoint(world.provider.getDimension(), x, y, z, 250));
	}

	public static void spawnRubble(World world, double x, double y, double z, int count) {

		for (int i = 0; i < count; i++) {
			EntityRubble rubble = new EntityRubble(world);
			rubble.posX = x;
			rubble.posY = y;
			rubble.posZ = z;
			rubble.motionY = 0.75 * (1 + ((count + rand.nextInt(count * 5))) / 25);
			rubble.motionX = rand.nextGaussian() * 0.75 * (1 + (count / 50));
			rubble.motionZ = rand.nextGaussian() * 0.75 * (1 + (count / 50));
			rubble.setMetaBasedOnBlock(Blocks.STONE, 0);
			world.spawnEntity(rubble);
		}
	}

	public static void spawnShrapnels(World world, double x, double y, double z, int count) {

		for (int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count);
			shrapnel.motionX = rand.nextGaussian() * 1 * (1 + (count / 50));
			shrapnel.motionZ = rand.nextGaussian() * 1 * (1 + (count / 50));
			shrapnel.setTrail(rand.nextInt(3) == 0);
			world.spawnEntity(shrapnel);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static void jolt(World world, double posX, double posY, double posZ, double strength, int count, double vel) {
		if(!CompatibilityConfig.isWarDim(world)){
			return;
		}
		for(int j = 0; j < count; j++) {
			
			double phi = rand.nextDouble() * (Math.PI * 2);
			double costheta = rand.nextDouble() * 2 - 1;
			double theta = Math.acos(costheta);
			double x = Math.sin( theta) * Math.cos( phi );
			double y = Math.sin( theta) * Math.sin( phi );
			double z = Math.cos( theta );
				
			Vec3d vec = new Vec3d(x, y, z);
			MutableBlockPos pos = new BlockPos.MutableBlockPos();
			
			for(int i = 0; i < strength; i ++) {
				double x0 = posX + (vec.x * i);
				double y0 = posY + (vec.y * i);
				double z0 = posZ + (vec.z * i);
				pos.setPos((int)x0, (int)y0, (int)z0);
				
				if(!world.isRemote) {
					IBlockState blockstate = world.getBlockState(pos);
					Block block = blockstate.getBlock();
					if(blockstate.getMaterial().isLiquid()) {
						world.setBlockToAir(pos);
					}
					
					if(block != Blocks.AIR) {
						
						if(block.getExplosionResistance(null) > 70)
							continue;
			            
			            EntityRubble rubble = new EntityRubble(world);
						rubble.posX = x0 + 0.5F;
						rubble.posY = y0 + 0.5F;
						rubble.posZ = z0 + 0.5F;
						rubble.setMetaBasedOnBlock(block, block.getMetaFromState(blockstate));
						
						Vec3d vec4 = new Vec3d(posX - rubble.posX, posY - rubble.posY, posZ - rubble.posZ);
						vec4.normalize();

						rubble.motionX = vec4.x * vel;
						rubble.motionY = vec4.y * vel;
						rubble.motionZ = vec4.z * vel;
						
						world.spawnEntity(rubble);
					
						world.setBlockToAir(pos);
						break;
					}
				}
			}
		}
	}
	
	public static void spawnTracers(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count) * 0.25F;
			shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.25F;
			shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.25F;
			shrapnel.setTrail(true);
			world.spawnEntity(shrapnel);
		}
	}
	
	public static void spawnShrapnelShower(World world, double x, double y, double z, double motionX, double motionY, double motionZ, int count, double deviation) {
		
		for(int i = 0; i < count; i++) {
			EntityShrapnel shrapnel = new EntityShrapnel(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionX = motionX + rand.nextGaussian() * deviation;
			shrapnel.motionY = motionY + rand.nextGaussian() * deviation;
			shrapnel.motionZ = motionZ + rand.nextGaussian() * deviation;
			shrapnel.setTrail(rand.nextInt(3) == 0);
			world.spawnEntity(shrapnel);
		}
	}
	
	public static void spawnMissileDebris(World world, double x, double y, double z, double motionX, double motionY, double motionZ, double deviation, List<ItemStack> debris, ItemStack rareDrop) {
		
		if(debris != null) {
            for (ItemStack stack : debris) {
                if (stack != null) {
                    int k = rand.nextInt(stack.getCount() + 1);
                    for (int j = 0; j < k; j++) {
                        EntityItem item = new EntityItem(world, x, y, z, new ItemStack(stack.getItem()));
                        item.motionX = (motionX + rand.nextGaussian() * deviation) * 0.85;
                        item.motionY = (motionY + rand.nextGaussian() * deviation) * 0.85;
                        item.motionZ = (motionZ + rand.nextGaussian() * deviation) * 0.85;
                        item.posX = item.posX + item.motionX * 2;
                        item.posY = item.posY + item.motionY * 2;
                        item.posZ = item.posZ + item.motionZ * 2;

                        world.spawnEntity(item);
                    }
                }
            }
		}
	}

	public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		explode(world, x, y, z, strength, cloud, rubble, shrapnel, true);
	}

	public static void explode(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel, boolean nuke) {
		if (nuke && CompatibilityConfig.isWarDim(world)){
			world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int)strength, x, y, z));

			ContaminationUtil.radiate(world, x, y, z, strength, 0, 0, 0, strength*30F);
		}
		world.spawnEntity(createShockwave(world, (int)strength, x, y, z));
		if (cloud)
			spawnParticles(world, x, y+2, z, cloudFunction((int) strength));
		if (rubble)
			spawnRubble(world, x, y+2, z, rubbleFunction((int) strength));
		if (shrapnel)
			spawnShrapnels(world, x, y+2, z, shrapnelFunction((int) strength));
	}

    public static void explodeArea(World world, double x, double y, double z, float radius, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
        if (CompatibilityConfig.isWarDim(world)){
            List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(x-radius, y-radius, z-radius, x+radius, y+radius, z+radius));

            for(Entity e : entities) {
                Vec3 vec = Vec3.createVectorHelper(e.posX - x, (e.posY + e.getEyeHeight()) - y, e.posZ - z);
                double len = vec.length();

                if(len > radius) continue;
                e.attackEntityFrom(ModDamageSource.blast, (float) (strength / (len/radius)));
            }
        }
		world.spawnEntity(createShockwave(world, (int)radius, x, y, z));
        if (cloud)
            spawnParticles(world, x, y+2, z, cloudFunction((int) strength));
        if (rubble)
            spawnRubble(world, x, y+2, z, rubbleFunction((int) strength));
        if (shrapnel)
            spawnShrapnels(world, x, y+2, z, shrapnelFunction((int) strength));
    }

	public static int cloudFunction(int i) {
		// return (int)(345 * (1 - Math.pow(Math.E, -i/15)) + 15);
		return (int) (545 * (1 - Math.pow(Math.E, -i / 15)) + 15);
	}

	public static int rubbleFunction(int i) {
		return i / 10;
	}

	public static int shrapnelFunction(int i) {
		return i / 3;
	}

	public static void explodeFire(World world, double x, double y, double z, float strength, boolean cloud, boolean rubble, boolean shrapnel) {
		if (CompatibilityConfig.isWarDim(world)){
			world.spawnEntity(EntityNukeExplosionMK5.statFacNoRadFire(world, (int)strength, x, y, z));

			ContaminationUtil.radiate(world, x, y, z, strength, 0, 0, strength*20F, strength*5F);
		}
		world.spawnEntity(createShockwave(world, (int)strength, x, y, z));
		if(cloud)
			spawnParticles(world, x, y+2, z, cloudFunction((int)strength));
		if(rubble)
			spawnRubble(world, x, y+2, z, rubbleFunction((int)strength));
		if(shrapnel)
			spawnShrapnels(world, x, y+2, z, shrapnelFunction((int)strength));
	}
	
	public static void spawnOilSpills(World world, double x, double y, double z, int count) {
		
		for(int i = 0; i < count; i++) {
			EntityOilSpill shrapnel = new EntityOilSpill(world);
			shrapnel.posX = x;
			shrapnel.posY = y;
			shrapnel.posZ = z;
			shrapnel.motionY = ((rand.nextFloat() * 0.5) + 0.5) * (1 + (count / (15 + rand.nextInt(21)))) + (rand.nextFloat() / 50 * count) * 0.25F;
			shrapnel.motionX = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.15F;
			shrapnel.motionZ = rand.nextGaussian() * 1	* (1 + (count / 50)) * 0.15F;
			world.spawnEntity(shrapnel);
		}
	}
	
	public static void buster(World world, double x, double y, double z, Vec3 vector, float strength, float depth) {
		vector = vector.normalize();
		if (CompatibilityConfig.isWarDim(world)){
			for(int i = 0; i <= depth; i += 3) {
				
				ContaminationUtil.radiate(world, x + vector.xCoord * i, y + vector.yCoord * i, z + vector.zCoord * i, strength, 0, 0, 0, strength*10F);
				world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int)strength, x + vector.xCoord * i, y + vector.yCoord * i, z + vector.zCoord * i));
			}
		}
		world.spawnEntity(createShockwave(world, (int)strength, x, y, z));
		spawnParticles(world, x, y+2, z, cloudFunction((int)strength));
		spawnRubble(world, x, y+2, z, rubbleFunction((int)strength));
		spawnShrapnels(world, x, y+2, z, shrapnelFunction((int)strength));
	}

	public static EntityShockwave createShockwave(World world, int radius, double x, double y, double z) {
		EntityShockwave sw = new EntityShockwave(world, radius);
		sw.setPosition(x, y, z);
		return sw;
	}

	public static class EntityShockwave extends Entity {
		public static final DataParameter<Integer> EXPLOSION_RADIUS = EntityDataManager.createKey(EntityShockwave.class, DataSerializers.VARINT);
		public static final DataParameter<Integer> TICKS_EXISTED = EntityDataManager.createKey(EntityShockwave.class, DataSerializers.VARINT);
		public static final DataParameter<Boolean> IS_RELOADED = EntityDataManager.createKey(EntityShockwave.class, DataSerializers.BOOLEAN);
		public static final DataParameter<Boolean> IS_INITIALIZED = EntityDataManager.createKey(EntityShockwave.class, DataSerializers.BOOLEAN);

		public int explosionRadius;
		public boolean didPlaySound = false;
		public boolean didShake = false;
		public int lastSyncedTick = 0;
		private boolean isDataReady = false;
		public boolean isReloaded = false;
		public boolean isClientSynced = false;
		public boolean isInitialized = false;

		public EntityShockwave(World world) {
			super(world);
			this.setSize(1F, 1F);
			this.ignoreFrustumCheck = true;
		}

		public EntityShockwave(World world, int radius) {
			this(world);
			this.explosionRadius = radius;
			this.dataManager.set(EXPLOSION_RADIUS, radius);
		}

		@Override
		protected void entityInit() {
			this.dataManager.register(EXPLOSION_RADIUS, 0);
			this.dataManager.register(TICKS_EXISTED, 0);
			this.dataManager.register(IS_RELOADED, false);
			this.dataManager.register(IS_INITIALIZED, false);
		}

		@Override
		public void notifyDataManagerChange(DataParameter<?> key) {
			super.notifyDataManagerChange(key);
			if (key == TICKS_EXISTED && this.world.isRemote) {
				if (!this.isDataReady) {
					this.explosionRadius = this.dataManager.get(EXPLOSION_RADIUS);
					this.ticksExisted = this.dataManager.get(TICKS_EXISTED);
					this.isReloaded = this.dataManager.get(IS_RELOADED);
					this.isInitialized = this.dataManager.get(IS_INITIALIZED);
					this.isDataReady = true;
				}
				if (this.isReloaded) {
					if (this.ticksExisted < this.dataManager.get(TICKS_EXISTED)) {
						this.ticksExisted = this.dataManager.get(TICKS_EXISTED);
					}
				}
			}
		}

		@Override
		public void onUpdate() {
			this.dataManager.set(TICKS_EXISTED, this.ticksExisted);
			if (world.isRemote) {
				int explosionRadius = this.dataManager.get(EXPLOSION_RADIUS);
				int blastDuration = (int)Math.ceil(80 * Math.cbrt(explosionRadius / 100.0));
				double shockSpeed = Math.max(2D, 2D * explosionRadius / (double)blastDuration);
				EntityPlayer player = MainRegistry.proxy.me();

				if (player != null) {
					float dist = player.getDistance(this);
					if(this.ticksExisted == 1 || (!this.isInitialized && !this.isClientSynced)) {
						this.isClientSynced = true;
					} else if (!this.isClientSynced) {
						if (MainRegistry.proxy.me() != null && MainRegistry.proxy.me().getDistance(this) < Math.min(15 * explosionRadius, this.ticksExisted * shockSpeed + shockSpeed)) {
							this.didPlaySound = true;
							this.didShake = true;
						}
					}

					int historyTick = 0;
					int tickDelta = this.ticksExisted - this.lastSyncedTick;
					boolean historyMode = this.isReloaded && !this.isClientSynced;
					boolean jumpMode = this.isReloaded && tickDelta > 1;
					if (jumpMode) this.ticksExisted -= tickDelta;

					for (int h = 0; h < (historyMode ? this.ticksExisted : jumpMode ? tickDelta : 1); h++) {
						if (historyMode) {
							historyTick++;
						} else if (jumpMode) {
							this.ticksExisted ++;
						}
						int currentTick = historyMode ? historyTick : this.ticksExisted;

						if (!didPlaySound) {
							if(currentTick * shockSpeed < 5 * explosionRadius) {
								if(MainRegistry.proxy.me() != null && MainRegistry.proxy.me().getDistance(this) < ticksExisted * shockSpeed + shockSpeed) {
									MainRegistry.proxy.playSoundClient(posX, posY, posZ, HBMSoundHandler.explosionLargeNear, SoundCategory.HOSTILE, 10_000F, 0.9F + rand.nextFloat() * 0.2F);
									didPlaySound = true;
									if(currentTick * shockSpeed >= 2 * explosionRadius) didShake = true;
								}
							} else if (currentTick * shockSpeed < 15 * explosionRadius) {
								if(MainRegistry.proxy.me() != null && MainRegistry.proxy.me().getDistance(this) < currentTick * shockSpeed + shockSpeed) {
									MainRegistry.proxy.playSoundClient(posX, posY, posZ, HBMSoundHandler.explosionLargeFar, SoundCategory.HOSTILE, 10_000F, 0.9F + rand.nextFloat() * 0.2F);
									didPlaySound = true;
									didShake = true;
								}
							}
						}
						if (didPlaySound && !didShake && dist < 5 * explosionRadius && System.currentTimeMillis() - ModEventHandlerClient.shakeTimestamp > 1_000) {
							ModEventHandlerClient.shakeTimestamp = System.currentTimeMillis();
							ModEventHandlerClient.shakeMultiplier = Math.max(((explosionRadius * 2D) - (double) dist) / (explosionRadius * 2D), 0D);
							player.hurtTime = Math.max((int) (((((explosionRadius * 2F) - dist)) / (explosionRadius * 2F)) * 150F), 0);
							player.maxHurtTime = Math.max((int) (((((explosionRadius * 2F) - dist)) / (explosionRadius * 2F)) * 100F), 0);
							player.attackedAtYaw = 0F;
							didShake = true;
						}
					}

					this.lastSyncedTick = this.ticksExisted;
					if (historyMode) this.isClientSynced = true;
				}
			} else if(this.ticksExisted == 1){
				this.isInitialized = true;
				this.dataManager.set(IS_INITIALIZED, true);
			}
			if (!world.isRemote && this.ticksExisted >= explosionRadius * 15) {
				this.setDead();
			}
		}

		@Override
		protected void readEntityFromNBT(NBTTagCompound nbt) {
			if (nbt.hasKey("explosionRadius")) {
				int r = nbt.getInteger("explosionRadius");
				this.explosionRadius = r;
				this.dataManager.set(EXPLOSION_RADIUS, r);
			}
			if (nbt.hasKey("ticksExisted")) {
				int t = nbt.getInteger("ticksExisted");
				this.ticksExisted = t;
				this.dataManager.set(TICKS_EXISTED, t);
			}
			this.isReloaded = true;
			this.dataManager.set(IS_RELOADED, true);
			this.isInitialized = true;
			this.dataManager.set(IS_INITIALIZED, true);
		}

		@Override
		protected void writeEntityToNBT(NBTTagCompound nbt) {
			nbt.setInteger("explosionRadius", this.dataManager.get(EXPLOSION_RADIUS));
			nbt.setInteger("ticksExisted", this.dataManager.get(TICKS_EXISTED));
		}

		@Override
		@SideOnly(Side.CLIENT)
		public boolean isInRangeToRenderDist(double distance) {
			return true;
		}
	}
}
