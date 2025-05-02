package com.hbm.entity.missile;

import java.util.ArrayList;
import java.util.List;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.bomb.BlockTaint;
import com.hbm.interfaces.IConstantRenderer;
import com.hbm.config.BombConfig;
import com.hbm.entity.effect.EntityNukeTorex;
import com.hbm.entity.logic.EntityBalefire;
import com.hbm.entity.logic.EntityNukeExplosionMK5;
import com.hbm.entity.logic.IChunkLoader;
import com.hbm.explosion.ExplosionChaos;
import com.hbm.explosion.ExplosionLarge;
import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.FuelType;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.items.weapon.ItemMissile.WarheadType;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.LoopedEntitySoundPacket;
import com.hbm.render.amlfrom1710.Vec3;

import api.hbm.entity.IRadarDetectable;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.block.Block;
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

public class EntityMissileCustom extends Entity implements IChunkLoader, IRadarDetectable, IConstantRenderer {

	public static final DataParameter<Integer> HEALTH = EntityDataManager.createKey(EntityMissileCustom.class, DataSerializers.VARINT);
	public static final DataParameter<MissileStruct> TEMPLATE = EntityDataManager.createKey(EntityMissileCustom.class, MissileStruct.SERIALIZER);
	public static final double particleSpeed = 1.75D;

	int chunkX = 0;
	int chunkZ = 0;

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
		accelXZ = decelY = 1/vector.length();
		decelY *= 2;
		
		velocity = 0.0;

		ItemMissile fuselage = (ItemMissile) template.fuselage;
		ItemMissile thruster = (ItemMissile) template.thruster;

		this.fuel = (Float)fuselage.attributes[1];
		this.consumption = (Float)thruster.attributes[1];

        this.setSize(1.5F, 11F);
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
		if(!world.isRemote && loaderTicket != null) {
            for(ChunkPos chunk : loadedChunks) {
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

            for(ChunkPos chunk : loadedChunks) {
                ForgeChunkManager.forceChunk(loaderTicket, chunk);
            }
        }
	}

	public void clearLoadedChunks() {
		if(!world.isRemote && loaderTicket != null && loadedChunks != null) {
			for(ChunkPos chunk : loadedChunks) {
				ForgeChunkManager.unforceChunk(loaderTicket, chunk);
			}
		}
	}

	private ChunkPos mainChunk;
	public void loadMainChunk() {
		if(!world.isRemote && loaderTicket != null){
			ChunkPos currentChunk = new ChunkPos((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
			if(mainChunk == null){
				ForgeChunkManager.forceChunk(loaderTicket, currentChunk);
				this.mainChunk = currentChunk;
			} else if(!mainChunk.equals(currentChunk)){
				ForgeChunkManager.forceChunk(loaderTicket, currentChunk);
				ForgeChunkManager.unforceChunk(loaderTicket, this.mainChunk);
				this.mainChunk = currentChunk;
			}
		}
	}
	public void unloadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk != null) {
			ForgeChunkManager.unforceChunk(loaderTicket, this.mainChunk);
		}
	}

	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, Type.ENTITY));
        this.getDataManager().register(HEALTH, this.health);
        this.getDataManager().register(TEMPLATE, template);
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
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		//load own chunk
		loadMainChunk();
		if(this.ticksExisted < 10){
			ExplosionLarge.spawnParticlesRadial(world, posX, posY, posZ, 15);
			return;
		}
		
		this.getDataManager().set(HEALTH, this.health);
		
		double oldPosY = this.posY;
		this.setLocationAndAngles(posX + this.motionX * velocity, posY + this.motionY * velocity, posZ + this.motionZ * velocity, (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI), (float)(Math.atan2(this.motionY, MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ)) * 180.0D / Math.PI) - 90);
		this.prevPosY = oldPosY;
		
		
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
			
			if(this.velocity < 6)
				this.velocity += 0.005 * consumption;
		} else {

			motionX *= 0.99;
			motionZ *= 0.99;
			
			if(motionY > -1.5)
				motionY -= 0.05;
		}

		Block b = this.world.getBlockState(new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ)).getBlock();
		if((b != Blocks.AIR && b != Blocks.WATER && b != Blocks.FLOWING_WATER) || posY < 1) {
			if(posY < 1){
				this.setLocationAndAngles((int)this.posX, world.getHeight((int)this.posX, (int)this.posZ), (int)this.posZ, 0, 0);
			}
			if (!this.world.isRemote) {
				if(this.ticksExisted > 100)
					onImpact();
			}
			this.clearLoadedChunks();
			unloadMainChunk();
			this.setDead();
			return;
		}
		PacketDispatcher.wrapper.sendToAll(new LoopedEntitySoundPacket(this.getEntityId()));
		if((int) (posX / 16) != chunkX || (int) (posZ / 16) != chunkZ){
			chunkX = (int) (posX / 16);
			chunkZ = (int) (posZ / 16);
			loadNeighboringChunks(chunkX, chunkZ);
		}

		if(world.isRemote){
			template = this.getDataManager().get(TEMPLATE);
			spawnRocketExhaust();
		}

		WarheadType wType = (WarheadType)template.warhead.attributes[0];

		if(wType == WarheadType.MIRV){
			mirvSplit();   		
		}
	}
	  public void mirvSplit(){
    	if(motionY <= 0) {
			
			if(world.isRemote)
				return;    
			               
			this.setDead();
			           
			double modx = 0;
			double modz = 0;
			for(int i = 0; i < 7; i++) {
				EntityMIRV nuke3 = new EntityMIRV(this.world);
				nuke3.setPosition(posX,posY,posZ);
				if(i==0){ modx = 0; modz = 0;}
				if(i==1){ modx = 0.45; modz = 0;}
				if(i==2){ modx = -0.45; modz = 0;}
				if(i==3){ modx = 0.15; modz = 0.3;}
				if(i==4){ modx = -0.15; modz = -0.3;}
				if(i==5){ modx = 0.15; modz = -0.3;}
				if(i==6){ modx = -0.15; modz = 0.3;}

				nuke3.motionX = this.motionX+modx;
				nuke3.motionY = this.motionY;
				nuke3.motionZ = this.motionZ+modz;
				this.world.spawnEntity(nuke3);
			}	
		}
	}

	private void spawnRocketExhaust(){
		FuelType type = (FuelType)template.fuselage.attributes[0];
		
		String smoke = "exDark";
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
			return;
		}
		Vec3 v = Vec3.createVectorHelper(this.motionX, this.motionY, this.motionZ).normalize();
		
		for(int i = 0; i < 2; i++){
			MainRegistry.proxy.spawnParticle(posX - v.xCoord * i, posY - v.yCoord * i, posZ - v.zCoord * i, smoke, new float[]{(float)(this.motionX * -particleSpeed), (float)(this.motionY * -particleSpeed), (float)(this.motionZ * -particleSpeed)});
		}
	}
		
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance) {
        return distance < 5000;
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
			ExplosionLarge.buster(world, posX, posY, posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), strength, strength);
			break;
		case NUCLEAR:
		case TX:
		case MIRV:
	    	world.spawnEntity(EntityNukeExplosionMK5.statFac(world, (int) strength, posX, posY, posZ));
	    	if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, posX, posY, posZ, strength);
			}
			break;
		case VOLCANO:
			ExplosionLarge.buster(world, posX, posY, posZ, Vec3.createVectorHelper(motionX, motionY, motionZ), strength, strength * 2);
			for(int x = -1; x <= 1; x++) {
				for(int y = -1; y <= 1; y++) {
					for(int z = -1; z <= 1; z++) {
						world.setBlockState(new BlockPos((int)Math.floor(posX + x), (int)Math.floor(posY + y), (int)Math.floor(posZ + z)), ModBlocks.volcanic_lava_block.getDefaultState());
					}
				}
			}
			world.setBlockState(new BlockPos((int)Math.floor(posX), (int)Math.floor(posY), (int)Math.floor(posZ)), ModBlocks.volcano_core.getDefaultState());
			break;
		case BALEFIRE:
			EntityBalefire bf = new EntityBalefire(world);
			bf.posX = this.posX;
			bf.posY = this.posY;
			bf.posZ = this.posZ;
			bf.destructionRange = (int) strength;
			world.spawnEntity(bf);
			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, posX, posY, posZ, strength);
			}
			break;
		case N2:
	    	world.spawnEntity(EntityNukeExplosionMK5.statFacNoRad(world, (int) strength, posX, posY, posZ));

			if(BombConfig.enableNukeClouds) {
				EntityNukeTorex.statFac(world, posX, posY, posZ, strength);
			}
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
