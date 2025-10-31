package com.leafia.contents.effects.folkvangr;

import com.hbm.entity.logic.IChunkLoader;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.leafia.CommandLeaf;
import com.leafia.contents.effects.folkvangr.particles.ParticleFleijaVacuum;
import com.leafia.contents.effects.folkvangr.visual.EntityCloudFleija;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.technical.LeafiaEase;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.common.ForgeChunkManager;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

import static net.minecraft.world.chunk.Chunk.NULL_BLOCK_STORAGE;

public class EntityNukeFolkvangr extends Entity implements IChunkLoader {
	private ForgeChunkManager.Ticket loaderTicket;
	public static EntityCloudFleija lastCloud = null;
	public static final Set<EntityNukeFolkvangr> awaitingBind = new HashSet<>();
	public EntityCloudFleija cloudBound = null;
	protected UUID cloudUUID = null;

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if (compound.hasUniqueId("cloud"))
			cloudUUID = compound.getUniqueId("cloud");
		row = compound.getShort("row");
	}
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		if (cloudUUID != null)
			compound.setUniqueId("cloud",cloudUUID);
		compound.setShort("row",row);
	}
	public EntityNukeFolkvangr(World world) { super(world); }
	public EntityNukeFolkvangr(World world,Vec3d pos,EntityCloudFleija cloud) {
		super(world);
		this.setPosition(pos.x,pos.y,pos.z);
		this.cloudBound = cloud;
		if (cloud == null) {
			if (lastCloud != null) {
				if (lastCloud.isEntityAlive() && lastCloud.isAddedToWorld()) {
					if (lastCloud.bound == null) {
						if (lastCloud.ticksExisted < 5) {
							if (lastCloud.getPositionVector().distanceTo(pos) <= 1.5) {
								this.cloudBound = lastCloud;
								lastCloud.bound = this;
								for (EntityPlayer player : world.playerEntities) {
									if (player.getHeldItemMainhand().getItem() == ModItems.wand_d)
										player.sendMessage(new TextComponentString("Yipe!").setStyle(new Style().setColor(TextFormatting.YELLOW)));
								}
								return;
							}
						}
					}
				}
			}
			awaitingBind.add(this);
		}
	}
	short row = 0;
	int columnIndex = 0;
	int postEffect = -1;
	double vacuumStart = 0;
	double vacuumEnd = 0;
	public static LeafiaEase vacuumEase = new LeafiaEase(LeafiaEase.Ease.EXPO,LeafiaEase.Direction.O);
	double vacuumForce = 0;
	public static final double vacuumForceMultiplier = 1.75;
	boolean played = false;
	@Override
	public void onUpdate() {
		if (this.isDead) return;
		super.onUpdate();
		if (world.isRemote) return;
		if (postEffect >= 0) {
			postEffect--;
			if (postEffect <= 0) {
				this.setDead();
				unloadMainChunk();
			} else {
				List<Entity> entities = world.getEntitiesWithinAABB(
						Entity.class,
						new AxisAlignedBB(
								posX-vacuumEnd,posY-vacuumEnd,posZ-vacuumEnd,
								posX+vacuumEnd,posY+vacuumEnd,posZ+vacuumEnd
						)
				);
				for (Entity entity : entities) {
					double distance = entity.getPositionVector().distanceTo(getPositionVector());
					if (distance <= vacuumEnd) {
						double force = vacuumEase.get((distance-vacuumStart)/(vacuumEnd-vacuumStart),1,0,true);
						Vec3d pullVec = getPositionVector().subtract(entity.getPositionVector()).normalize();
						entity.motionX += pullVec.x*vacuumForce*vacuumForceMultiplier*force;
						entity.motionY += pullVec.y*vacuumForce*vacuumForceMultiplier*force;
						entity.motionZ += pullVec.z*vacuumForce*vacuumForceMultiplier*force;
						if (distance <= vacuumStart) {
							if (entity instanceof EntityOcelot) continue;
							entity.attackEntityFrom(ModDamageSource.fleija,3);
						}
					}
				}
			}
			return;
		}
		{
			if ((cloudUUID != null) && (cloudBound == null)) {
				for (Entity entity : world.loadedEntityList) {
					if (entity instanceof EntityCloudFleija) {
						if (entity.isEntityAlive() && entity.isAddedToWorld()) {
							if (entity.getUniqueID().equals(this.cloudUUID)) {
								awaitingBind.remove(this);
								cloudBound = (EntityCloudFleija) entity;
								break;
							}
						}
					}
				}
			}
			if ((cloudUUID == null) && (cloudBound != null)) {
				if (cloudBound.isEntityAlive() && cloudBound.isAddedToWorld()) {
					cloudUUID = cloudBound.getUniqueID();
				}
			}
			if (this.ticksExisted > 10 && (cloudUUID == null || cloudBound == null)) {
				awaitingBind.remove(this);
				this.setDead();
				for (EntityPlayer player : world.playerEntities) {
					player.sendMessage(new TextWarningLeafia("EntityCloudFleija did not bind in time!"));
				}
				return;
			}
		}
		if ((cloudUUID != null) && (cloudBound != null)) {
			if (!played) {
				world.playSound(null,getPosition(),HBMSoundEvents.nuke_folkvangr,SoundCategory.BLOCKS,cloudBound.getMaxAge(),1);
				PacketDispatcher.wrapper.sendToAllAround(
						new CommandLeaf.ShakecamPacket(new String[]{
								"duration="+cloudBound.getMaxAge(),
								"range="+cloudBound.getMaxAge()*2
						}).setPos(getPosition()),
						new NetworkRegistry.TargetPoint(dimension,posX,posY,posZ,cloudBound.getMaxAge()*2.25)
				);
				if (cloudBound.isAntischrab) {
					PacketDispatcher.wrapper.sendToAllAround(
							new CommandLeaf.ShakecamPacket(new String[]{
									"type=smooth","duration=2",
									"speed=8","ease=expoOut","intensity=12",
									"range="+cloudBound.getMaxAge()*2
							}).setPos(getPosition()),
							new NetworkRegistry.TargetPoint(dimension,posX,posY,posZ,cloudBound.getMaxAge()*2.25)
					);
				}
			}
			played = true;
			loadMainChunk();
			double curRange = cloudBound.scale/16d;
			short start = (short)Math.floor(curRange);
			int destColumn = (int)Math.floor((curRange-Math.floor(curRange))*getRowTotal(start));
			if (!cloudBound.isEntityAlive() || (cloudBound.scale > cloudBound.getMaxAge())) {
				start = (short)Math.ceil(cloudBound.getMaxAge()/16d);
				destColumn = getRowTotal(start);
				//this.setDead();
				postEffect = (int)(Math.min(Math.pow(cloudBound.getMaxAge()/50d+1,1.25)-1,20*20)*20);
				vacuumStart = cloudBound.getMaxAge();
				vacuumEnd = cloudBound.getMaxAge()*3;
				vacuumForce = Math.pow(cloudBound.getMaxAge()/4d,0.75)/30d;
				FolkvangrVacuumPacket packet = new FolkvangrVacuumPacket();
				packet.pos = getPositionVector();
				packet.postEffect = postEffect;
				packet.vacuumStart = vacuumStart;
				packet.vacuumEnd = vacuumEnd;
				packet.vacuumForce = vacuumForce;
				PacketDispatcher.wrapper.sendToAllAround(
						packet,
						new NetworkRegistry.TargetPoint(dimension,posX,posY,posZ,vacuumEnd+120)
				);
			}
			List<Entity> entities = world.getEntitiesWithinAABB(
					Entity.class,
					new AxisAlignedBB(
							posX-cloudBound.scale,posY-cloudBound.scale,posZ-cloudBound.scale,
							posX+cloudBound.scale,posY+cloudBound.scale,posZ+cloudBound.scale
					)
			);
			for (Entity entity : entities) {
				if (entity instanceof EntityOcelot) continue;
				if (entity.getPositionVector().distanceTo(getPositionVector()) <= cloudBound.scale) {
					entity.attackEntityFrom(ModDamageSource.back,2147483647); // :leafeon_troll:
					if (entity instanceof EntityLivingBase living)
						living.setHealth(0); // HEHEHEHEHEHEEH
				}
			}
			while ((row < start) || (columnIndex < destColumn)) {
				byte side = (byte)Math.floorDiv(columnIndex,getSideLength(row));
				int sideOffset = columnIndex-side*getSideLength(row);
				switch(side) {
					case 0: processChunk(getSideStart(row)+sideOffset,row); break;
					case 1: processChunk(row,-getSideStart(row)-sideOffset); break;
					case 2: processChunk(-getSideStart(row)-sideOffset,-row); break;
					case 3: processChunk(-row,getSideStart(row)+sideOffset); break;
				}
				if (++columnIndex >= getRowTotal(row)) {
					row++;
					columnIndex = 0;
				}
			}
		}
	}
	protected boolean carveChunk(int cx,int cy,int cz) {
		long radius = cloudBound.getMaxAge();
		//if (getPositionVector().distanceTo(new Vec3d(cx*16+MathHelper.positiveModulo(posX,16),cy*16+MathHelper.positiveModulo(posY,16),cz*16+MathHelper.positiveModulo(posZ,16))) > radius+14) return;
		ChunkPos chunkPos = new ChunkPos(cx,cz);
		boolean carved = false; // optimization
		for (int x = chunkPos.getXStart(); x <= chunkPos.getXEnd(); x++) {
			for (int z = chunkPos.getZStart(); z <= chunkPos.getZEnd(); z++) {
				if (world.getHeight(x,z) < cy*16) continue;
				carved = true;
				long rx = x-getPosition().getX();
				long rz = z-getPosition().getZ();
				double distFromRing = radius*radius-(rx*rx + rz*rz);
				if (distFromRing > 1) {
					int yheight = (int)Math.sqrt(Math.max(0,distFromRing-1));
					int ystart = MathHelper.clamp(getPosition().getY()-yheight,cy*16,cy*16+15);
					int yend = MathHelper.clamp(getPosition().getY()+yheight,cy*16,cy*16+15);
					if (ystart <= 0) ystart++;
					if (yend <= 0) yend++;
					for (int y = ystart; y <= yend; y++) {
						eraseBlock(new BlockPos(x,y,z));
					}
				}
			}
		}
		return carved;
	}
	protected void processChunk(int x,int z) {
		int radius = cloudBound.getMaxAge();
		Chunk chunk = world.getChunk(chunkCoordX+x,chunkCoordZ+z);
		ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
		if (
				((Math.ceil((posX-radius*0.707)/16) <= chunkCoordX+x) && (chunkCoordX+x+1 < Math.floor((posX+radius*0.707)/16)))
						&& ((Math.ceil((posZ-radius*0.707)/16) <= chunkCoordZ+z) && (chunkCoordZ+z+1 < Math.floor((posZ+radius*0.707)/16)))
		) {
			int minCY = (int)MathHelper.clamp(Math.ceil((posY-radius*0.707)/16),1,storage.length-1);
			int maxCY = (int)MathHelper.clamp(Math.floor((posY+radius*0.707)/16),1,storage.length-1);
			if (eraseChunk(chunk,(byte)minCY,(byte)maxCY)) {
				ClearChunkPacket packet = new ClearChunkPacket();
				packet.pos = chunk.getPos();
				packet.min = (byte)minCY;
				packet.max = (byte)maxCY;
				PacketDispatcher.wrapper.sendToDimension(packet,dimension);
			}
			if (minCY >= maxCY) {
				for (int cy = 0; cy < 16; cy++) {
					if (!carveChunk(chunk.x,cy,chunk.z)) break; // optimization
				}
			} else {
				for (int cy = 0; cy < minCY; cy++) {
					if (!carveChunk(chunk.x,cy,chunk.z)) break; // optimization
				}
				for (int cy = maxCY+1; cy < 16; cy++) {
					if (!carveChunk(chunk.x,cy,chunk.z)) break; // optimization
				}
			}
		} else {
			for (int cy = 0; cy < 16; cy++) {
				if (!carveChunk(chunk.x,cy,chunk.z)) break; // optimization
			}
		}




        /*
        IBlockState air = Blocks.AIR.getDefaultState();
        for (int i = 0; i < 4096; i++)
            storage[0].set(i&15,i&240>>4,i&3840>>8,air);
         */
	}
	protected void eraseBlock(BlockPos pos) {
		if (world.isValid(pos))
			// FLAGS: [Block update]
			//        [Sends the change to clients]
			//        [Prevents the block from being re-rendered]
			//        [If remote, forces re-renders to work on main thread instead of worker pool]
			//        [Prevent observers from seeing this change]
			world.setBlockState(pos,Blocks.AIR.getDefaultState(),0b00010);
	}
	protected static boolean eraseChunk(Chunk chunk,byte min,byte max) {
		if (min > max) return false;
		List<BlockPos> removeQueue = new ArrayList<>(); // ConcurrentModificationException sucks >:(
		for (BlockPos pos : chunk.getTileEntityMap().keySet()) {
			if ((pos.getY() >= min*16) && (pos.getY() < max*16))
				removeQueue.add(pos);
		}
		for (BlockPos pos : removeQueue) {
			chunk.removeTileEntity(pos);
		}
		ExtendedBlockStorage[] storage = chunk.getBlockStorageArray();
		for (int i = min; i <= max; i++)
			storage[i] = NULL_BLOCK_STORAGE;
		chunk.setModified(true);
		return true;
	}
	public static final byte preferredChunksPerTick = 2;
	static protected int getSideStart(short row) { return -Math.max(0,row-1); }
	static protected int getSideLength(short row) { return row- getSideStart(row)+1; }
	static protected int getRowTotal(short row) {
		if (row == 0) return 1;
		return getSideLength(row)*4;
	}
	public static double getPreferredSpeedMultiplier(short row) {
		return Math.min(16d/(getRowTotal(row)/(float) preferredChunksPerTick),1);
	}









	@Override
	protected void entityInit() {
		init(ForgeChunkManager.requestTicket(MainRegistry.instance, world, ForgeChunkManager.Type.ENTITY));
	}
	@Override
	public void init(ForgeChunkManager.Ticket ticket) {
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
	private ChunkPos mainChunk;
	public void loadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk == null) {
			this.mainChunk = new ChunkPos((int) Math.floor(this.posX / 16D), (int) Math.floor(this.posZ / 16D));
			ForgeChunkManager.forceChunk(loaderTicket, this.mainChunk);
		}
	}
	public void unloadMainChunk() {
		if(!world.isRemote && loaderTicket != null && this.mainChunk != null) {
			ForgeChunkManager.unforceChunk(loaderTicket, this.mainChunk);
		}
	}







	public static class ClearChunkPacket extends RecordablePacket {
		public ChunkPos pos;
		public byte min;
		public byte max;
		public ClearChunkPacket() {
		}
		@Override
		public void fromBits(LeafiaBuf buf) {
			pos = new ChunkPos(buf.readInt(),buf.readInt());
			min = buf.readByte();
			max = buf.readByte();
		}
		@Override
		public void toBits(LeafiaBuf buf) {
			buf.writeInt(pos.x);
			buf.writeInt(pos.z);
			buf.writeByte(min);
			buf.writeByte(max);
		}
		public static class Handler implements IMessageHandler<ClearChunkPacket, IMessage> {
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(ClearChunkPacket message,MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					Chunk chunk = Minecraft.getMinecraft().world.getChunk(message.pos.x,message.pos.z);
					EntityNukeFolkvangr.eraseChunk(chunk,message.min,message.max);
					Minecraft.getMinecraft().world.markBlockRangeForRenderUpdate(
							message.pos.getXStart(),0,message.pos.getZStart(),
							message.pos.getXEnd(),256,message.pos.getZEnd()
					);
				});
				return null;
			}
		}
	}
	public static class VacuumInstance {
		int postEffect = -1;
		public double vacuumStart = 0;
		public double vacuumEnd = 0;
		public double vacuumForce = 0;
		public Vec3d pos;
		public VacuumInstance() {}
		public VacuumInstance(Vec3d pos,int postEffect,double vacuumStart,double vacuumEnd,double vacuumForce) {
			this.pos = pos;
			this.postEffect = postEffect;
			this.vacuumStart = vacuumStart;
			this.vacuumEnd = vacuumEnd;
			this.vacuumForce = vacuumForce;
		}
	}
	/*
	public static class SessrumnirSphereSyncPacket extends VacuumInstance implements IMessage {
		UUID uuid;
		double scale;
		double tickrate;
		public SessrumnirSphereSyncPacket() {
		}
		public SessrumnirSphereSyncPacket(EntityCloudFleija cloud,double tickrate) {
			this.scale = cloud.scale;
			this.tickrate = tickrate;
		}
		@Override
		public void fromBytes(ByteBuf buf) {
			uuid = new UUID(buf.readLong(),buf.readLong());
			scale = buf.readDouble();
			tickrate = buf.readDouble();
		}

		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeLong(uuid.getMostSignificantBits());
			buf.writeLong(uuid.getLeastSignificantBits());
			buf.writeDouble(scale);
			buf.writeDouble(tickrate);
		}

		public static class Handler implements IMessageHandler<SessrumnirSphereSyncPacket,IMessage> {
			static final List<VacuumInstance> vacuums = new ArrayList<>();

			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(SessrumnirSphereSyncPacket message,MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					vacuums.add(new VacuumInstance(
							message.pos,
							message.postEffect,
							message.vacuumStart,
							message.vacuumEnd,
							message.vacuumForce
					));
				});
				return null;
			}
		}
	}*/
	public static class FolkvangrVacuumPacket extends VacuumInstance implements IMessage {
		public FolkvangrVacuumPacket() {
		}
		@Override
		public void fromBytes(ByteBuf buf) {
			pos = new Vec3d(buf.readDouble(),buf.readDouble(),buf.readDouble());
			postEffect = buf.readInt();
			vacuumStart = buf.readDouble();
			vacuumEnd = buf.readDouble();
			vacuumForce = buf.readDouble();
		}
		@Override
		public void toBytes(ByteBuf buf) {
			buf.writeDouble(pos.x);
			buf.writeDouble(pos.y);
			buf.writeDouble(pos.z);
			buf.writeInt(postEffect);
			buf.writeDouble(vacuumStart);
			buf.writeDouble(vacuumEnd);
			buf.writeDouble(vacuumForce);
		}
		public static class Handler implements IMessageHandler<FolkvangrVacuumPacket, IMessage> {
			static final List<VacuumInstance> vacuums = new ArrayList<>();
			@Override
			@SideOnly(Side.CLIENT)
			public IMessage onMessage(FolkvangrVacuumPacket message,MessageContext ctx) {
				Minecraft.getMinecraft().addScheduledTask(() -> {
					vacuums.add(new VacuumInstance(
							message.pos,
							message.postEffect,
							message.vacuumStart,
							message.vacuumEnd,
							message.vacuumForce
					));
				});
				return null;
			}
			@SideOnly(Side.CLIENT)
			public static void localTick() {
				EntityPlayer player = Minecraft.getMinecraft().player;
				if (player != null) {
					int i = 0;
					while (i < vacuums.size()) {
						VacuumInstance vacuum = vacuums.get(i);
						double distance = player.getPositionVector().distanceTo(vacuum.pos);
						if (distance <= vacuum.vacuumEnd) {
							double force = vacuumEase.get((distance-vacuum.vacuumStart)/(vacuum.vacuumEnd-vacuum.vacuumStart),1,0,true);
							Vec3d pullVec = vacuum.pos.subtract(player.getPositionVector()).normalize();
							player.motionX += pullVec.x*vacuum.vacuumForce*vacuumForceMultiplier*force;
							player.motionY += pullVec.y*vacuum.vacuumForce*vacuumForceMultiplier*force;
							player.motionZ += pullVec.z*vacuum.vacuumForce*vacuumForceMultiplier*force;
						}
						Random rand = Minecraft.getMinecraft().world.rand;
						for (int p = -rand.nextInt(4)*2; p <= 10; p++) {
							Vec3d particleDir = Vec3d.fromPitchYaw(rand.nextFloat() * 150 - 75,rand.nextFloat() * 360)
									.scale(vacuum.vacuumStart*1.2+rand.nextDouble()*((vacuum.vacuumEnd-vacuum.vacuumStart)*0.6));
							ParticleFleijaVacuum fx = new ParticleFleijaVacuum(
									Minecraft.getMinecraft().world,
									vacuum.pos.x+particleDir.x,
									vacuum.pos.y+particleDir.y,
									vacuum.pos.z+particleDir.z,
									rand.nextFloat()*2 + 2,
									rand.nextFloat()*0.1f+0.1f,
									vacuum
							);
							Minecraft.getMinecraft().effectRenderer.addEffect(fx);
						}
						vacuum.postEffect--;
						if (vacuum.postEffect <= 0)
							vacuums.remove(i);
						else i++;
					}
				}
			}
		}
	}
}
