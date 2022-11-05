package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.entity.effect.EntityNukeCloudSmall;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK4;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;
import com.hbm.render.amlfrom1710.Vec3;

import api.hbm.entity.IRadarDetectable;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.common.ForgeChunkManager.Type;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityMissileCustom extends Entity implements IChunkLoader, IRadarDetectable {

	public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(EntityMissileCustom.class, DataSerializers.VARINT);
	public static final DataParameter<MissileStruct> TEMPLATE = EntityDataManager.createKey(EntityMissileCustom.class, MissileStruct.SERIALIZER);
	
	int startX;
	int startZ;
	int targetX;
	int targetZ;
	double velocity;
	double decelY;
	double accelXZ;
	float fuel;
	float consumption;
    private Ticket loaderTicket;
    public int health = 50;
    MissileStruct template;
	
	public EntityMissileCustom(World worldIn) {
		super(worldIn);
		this.ignoreFrustumCheck = true;
		startX = (int) posX;
		startZ = (int) posZ;
		targetX = (int) posX;
		targetZ = (int) posZ;
	}
	
	public EntityMissileCustom(World world, float x, float y, float z, int a, int b, MissileStruct template) {
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
		
		this.template = template;
		this.getDataManager().set(TEMPLATE, template);
		
        Vec3d vector = new Vec3d(targetX - startX, 0, targetZ - startZ);
		accelXZ = decelY = 1/vector.lengthVector();
		decelY *= 2;
		
		velocity = 0.0;

		ItemMissile fuselage = (ItemMissile) template.fuselage;
		ItemMissile thruster = (ItemMissile) template.thruster;

		this.fuel = (Float)fuselage.attributes[1];
		this.consumption = (Float)thruster.attributes[1];

        this.setSize(1.5F, 1.5F);
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            if (!this.isDead && !this.world.isRemote)
            {
            	health -= amount;
            	
                if (this.health <= 0)
                {
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
    }
	
	@Override
	public void init(Ticket ticket) {
		if(!world.isRemote) {
			
            if(ticket != null) {
            	
                if(loaderTicket == null) {
                	
                	loaderTicket = ticket;
                	loaderTicket.bindEntity(this);
                	loaderTicket.getModData();
                }

                ForgeChunkManager.forceChunk(loaderTicket, new ChunkPos(chunkCoordX, chunkCoordZ));
            }
        }
	}
	
	List<ChunkPos> loadedChunks = new ArrayList<ChunkPos>();

	@Override
	public void loadNeighboringChunks(int newChunkX, int newChunkZ) {
		if(!world.isRemote && loaderTicket != null)
        {
            for(ChunkPos chunk : loadedChunks)
            {
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

            for(ChunkPos chunk : loadedChunks)
            {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
        this.getDataManager().register(HEALTH, this.health);

        this.getDataManager().register(TEMPLATE, template);
       /* if(template != null) {
	        //System.out.println("yeah");
        	//Drillgon200: What ^
	        this.dataWatcher.addObject(9, Integer.valueOf(Item.getIdFromItem(template.warhead)));
	        this.dataWatcher.addObject(10, Integer.valueOf(Item.getIdFromItem(template.fuselage)));
	        
	        if(template.fins != null)
	        	this.dataWatcher.addObject(11, Integer.valueOf(Item.getIdFromItem(template.fins)));
	        else
	        	this.dataWatcher.addObject(11, Integer.valueOf(0));
	        
	        this.dataWatcher.addObject(12, Integer.valueOf(Item.getIdFromItem(template.thruster)));
        } else {
	        this.dataWatcher.addObject(9, Integer.valueOf(0));
	        this.dataWatcher.addObject(10, Integer.valueOf(0));
	        this.dataWatcher.addObject(11, Integer.valueOf(0));
	        this.dataWatcher.addObject(12, Integer.valueOf(0));
        }*/
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
		fuel = nbt.getFloat("fuel");
		consumption = nbt.getFloat("consumption");
		int i = nbt.getInteger("fins");
		if(nbt.hasKey("noTemplate")){
			template = null;
			this.setDead();
		} else {
			template = new MissileStruct(Item.getItemById(nbt.getInteger("warhead")), Item.getItemById(nbt.getInteger("fuselage")), i < 0 ? null : Item.getItemById(i), Item.getItemById(nbt.getInteger("thruster")));
		}
		this.getDataManager().set(TEMPLATE, template);
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
		nbt.setFloat("fuel", fuel);
		nbt.setFloat("consumption", consumption);
		template = this.getDataManager().get(TEMPLATE);
		if(template == null){
			//Drillgon200: Should never happen but apparently mo creatures likes spawning other people's mobs
			nbt.setBoolean("noTemplate", true);	
		} else {
			nbt.setInteger("warhead", Item.getIdFromItem(template.warhead));
			nbt.setInteger("fuselage", Item.getIdFromItem(template.fuselage));
			nbt.setInteger("fins", template.fins == null ? -1 : Item.getIdFromItem(template.fins));
			nbt.setInteger("thruster", Item.getIdFromItem(template.thruster));
		}
	}
	
	protected void rotation() {
        float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, f2) * 180.0D / Math.PI) - 90; this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
        {
            this.prevRotationPitch += 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw < -180.0F)
        {
            this.prevRotationYaw -= 360.0F;
        }

        while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
        {
            this.prevRotationYaw += 360.0F;
        }
	}
	
	@Override
	public void onUpdate() {
		
		this.getDataManager().set(HEALTH, this.health);
		if(world.isRemote)
			template = this.getDataManager().get(TEMPLATE);
        
		this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, 0, 0);

		this.rotation();
		
		if(fuel > 0 || world.isRemote) {
			
			fuel -= consumption;
	
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
				velocity += 0.01;
		} else {

			motionX *= 0.99;
			motionZ *= 0.99;
			
			if(motionY > -1.5)
				motionY -= 0.05;
		}

		if (this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock() != Blocks.AIR
				&& this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock() != Blocks.WATER
				&& this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock() != Blocks.FLOWING_WATER) {

			if (!this.world.isRemote) {
				onImpact();
			}
			this.setDead();
			return;
		}

		if (this.world.isRemote) {
			Vec3 v = Vec3.createVectorHelper(motionX, motionY, motionZ);
			v = v.normalize();
			
			String smoke = "";
			
			FuelType type = (FuelType)template.fuselage.attributes[0];
			
			switch(type) {
			case BALEFIRE:
				smoke = "exBalefire";
				break;
			case HYDROGEN:
				smoke = "exHydrogen";
				break;
			case KEROSENE:
				smoke = "exKerosene";
				break;
			case SOLID:
				smoke = "exSolid";
				break;
			case XENON:
				break;
			}
			
			for(int i = 0; i < velocity; i++)
				MainRegistry.proxy.spawnParticle(posX - v.xCoord * i, posY - v.yCoord * i, posZ - v.zCoord * i, smoke, null);
		}

		loadNeighboringChunks((int)(posX / 16), (int)(posZ / 16));
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return distance < 2500000;
    }

	public void onImpact() {
		

		WarheadType type = (WarheadType)template.warhead.attributes[0];
		float strength = (Float)template.warhead.attributes[1];
		int maxLifetime = (int)Math.max(100, 5 * 48 * (Math.pow(strength, 3)/Math.pow(48, 3)));
		switch(type) {
		case HE:
			ExplosionLarge.explode(world, posX, posY, posZ, strength, true, false, true);
			ExplosionLarge.jolt(world, posX, posY, posZ, strength, (int) (strength * 50), 0.25);
			break;
		case INC:
			ExplosionLarge.explodeFire(world, posX, posY, posZ, strength, true, false, true);
			ExplosionLarge.jolt(world, posX, posY, posZ, strength * 1.5, (int) (strength * 50), 0.25);
			break;
		case CLUSTER:
			break;
		case BUSTER:
			ExplosionLarge.buster(world, posX, posY, posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), strength, strength * 4);
			break;
		case NUCLEAR:
		case TX:
	    	world.spawnEntity(EntityNukeExplosionMK4.statFac(world, (int) strength, posX, posY, posZ));
	    	
			EntityNukeCloudSmall nuke = new EntityNukeCloudSmall(world, maxLifetime, strength * 0.005F);
			nuke.posX = posX;
			nuke.posY = posY;
			nuke.posZ = posZ;
			world.spawnEntity(nuke);
			break;
		case BALEFIRE:
			EntityBalefire bf = new EntityBalefire(world);
			bf.posX = this.posX;
			bf.posY = this.posY;
			bf.posZ = this.posZ;
			bf.destructionRange = (int) strength;
			world.spawnEntity(bf);
			world.spawnEntity(EntityNukeCloudSmall.statFacBale(world, posX, posY + 5, posZ, strength * 1.5F, 1000));
			break;
		case N2:
	    	world.spawnEntity(EntityNukeExplosionMK4.statFacNoRad(world, (int) strength, posX, posY, posZ));

			EntityNukeCloudSmall n2 = new EntityNukeCloudSmall(world, maxLifetime, strength * 0.005F);
			n2.posX = posX;
			n2.posY = posY;
			n2.posZ = posZ;
			world.spawnEntity(n2);
			break;
		case TAINT:
            int r = (int) strength;
            MutableBlockPos mPos = new BlockPos.MutableBlockPos();
		    for(int i = 0; i < r * 10; i++) {
		    	int a = rand.nextInt(r) + (int)posX - (r / 2 - 1);
		    	int b = rand.nextInt(r) + (int)posY - (r / 2 - 1);
		    	int c = rand.nextInt(r) + (int)posZ - (r / 2 - 1);
		           if(world.getBlockState(mPos.setPos(a, b, c)).getBlock().isReplaceable(world, mPos.setPos(a, b, c)) && BlockTaint.hasPosNeightbour(world, mPos.setPos(a, b, c))) {
		        		   world.setBlockState(mPos.setPos(a, b, c), ModBlocks.taint.getDefaultState().withProperty(BlockTaint.TEXTURE, rand.nextInt(3) + 4), 2);
		           }
		    }
			break;
		case CLOUD:
            this.world.playEvent(2002, new BlockPos((int)Math.round(this.posX), (int)Math.round(this.posY), (int)Math.round(this.posZ)), 0);
			ExplosionChaos.spawnChlorine(world, posX - motionX, posY - motionY, posZ - motionZ, 750, 2.5, 2);
			break;
		default:
			break;
		
		}
	}

	@Override
	public RadarTargetType getTargetType(){
		ItemMissile part = this.dataManager.get(TEMPLATE).fuselage;

		PartSize top = part.top;
		PartSize bottom = part.bottom;

		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_10)
			return RadarTargetType.MISSILE_10;
		if(top == PartSize.SIZE_10 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_10_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_15)
			return RadarTargetType.MISSILE_15;
		if(top == PartSize.SIZE_15 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_15_20;
		if(top == PartSize.SIZE_20 && bottom == PartSize.SIZE_20)
			return RadarTargetType.MISSILE_20;

		return RadarTargetType.PLAYER;
	}

}
