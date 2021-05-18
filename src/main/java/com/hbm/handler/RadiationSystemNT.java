package com.hbm.handler;

import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import com.hbm.config.GeneralConfig;
import com.hbm.config.RadiationConfig;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.lib.RefStrings;
import com.hbm.packet.AuxParticlePacket;
import com.hbm.packet.PacketDispatcher;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraftforge.event.world.ChunkDataEvent;
import net.minecraftforge.event.world.ChunkEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

@Mod.EventBusSubscriber(modid = RefStrings.MODID)
public class RadiationSystemNT {

	private static Map<World, WorldRadiationData> worldMap = new HashMap<>();
	private static int ticks;
	
	public static void incrementRad(World world, BlockPos pos, float amount, float max){
		if(pos.getY() < 0 || pos.getY() > 255 || !world.isBlockLoaded(pos))
			return;
		RadPocket p = getPocket(world, pos);
		if(p.radiation < max){
			p.radiation += amount;
		}
		if(amount > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.activePockets.add(p);
		}
	}
	
	public static void decrementRad(World world, BlockPos pos, float amount){
		if(pos.getY() < 0 || pos.getY() > 255 || !isSubChunkLoaded(world, pos))
			return;
		RadPocket p = getPocket(world, pos);
		p.radiation -= Math.max(amount, 0);
		if(p.radiation < 0){
			p.radiation = 0;
		}
	}
	
	public static void setRadForCoord(World world, BlockPos pos, float amount){
		RadPocket p = getPocket(world, pos);
		p.radiation = Math.max(amount, 0);
		if(amount > 0){
			WorldRadiationData data = getWorldRadData(world);
			data.activePockets.add(p);
		}
	}
	
	public static float getRadForCoord(World world, BlockPos pos){
		if(!isSubChunkLoaded(world, pos))
			return 0;
		return getPocket(world, pos).radiation;
	}
	
	public static void jettisonData(World world){
		WorldRadiationData data = getWorldRadData(world);
		data.data.clear();
		data.activePockets.clear();
	}
	
	public static RadPocket getPocket(World world, BlockPos pos){
		return getSubChunkStorage(world, pos).getPocket(pos);
	}
	
	public static Collection<RadPocket> getActiveCollection(World world){
		return getWorldRadData(world).activePockets;
	}
	
	public static boolean isSubChunkLoaded(World world, BlockPos pos){
		if(pos.getY() > 255 || pos.getY() < 0)
			return false;
		WorldRadiationData worldRadData = worldMap.get(world);
		if(worldRadData == null){
			return false;
		}
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkPos(pos));
		if(st == null){
			return false;
		}
		SubChunkRadiationStorage sc = st.getForYLevel(pos.getY());
		if(sc == null){
			return false;
		}
		return true;
	}
	
	public static SubChunkRadiationStorage getSubChunkStorage(World world, BlockPos pos){
		ChunkRadiationStorage st = getChunkStorage(world, pos);
		SubChunkRadiationStorage sc = st.getForYLevel(pos.getY());
		if(sc == null){
			rebuildChunkPockets(world.getChunkFromBlockCoords(pos), pos.getY() >> 4);
		}
		sc = st.getForYLevel(pos.getY());
		return sc;
	}
	
	private static WorldRadiationData getWorldRadData(World world){
		WorldRadiationData worldRadData = worldMap.get(world);
		if(worldRadData == null){
			worldRadData = new WorldRadiationData(world);
			worldMap.put(world, worldRadData);
		}
		return worldRadData;
	}
	
	public static ChunkRadiationStorage getChunkStorage(World world, BlockPos pos){
		WorldRadiationData worldRadData = getWorldRadData(world);
		ChunkRadiationStorage st = worldRadData.data.get(new ChunkPos(pos));
		if(st == null){
			st = new ChunkRadiationStorage(worldRadData, world.getChunkFromBlockCoords(pos));
			worldRadData.data.put(new ChunkPos(pos), st);
		}
		return st;
	}
	
	@SubscribeEvent
	public static void onUpdate(ServerTickEvent e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(e.phase == Phase.END){
			ticks ++;
			if(ticks % 20 == 17){
				//long mil = System.nanoTime();
				updateRadiation();
				//System.out.println("rad tick took: " + (System.nanoTime()-mil));
			}
			rebuildDirty();
		}
	}
	
	@SubscribeEvent
	public static void onChunkUnload(ChunkEvent.Unload e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			WorldRadiationData data = getWorldRadData(e.getWorld());
			if(data.data.containsKey(e.getChunk().getPos())){
				data.data.get(e.getChunk().getPos()).unload();
				data.data.remove(e.getChunk().getPos());
			}
		}
	}
	
	@SubscribeEvent
	public static void onChunkLoad(ChunkDataEvent.Load e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			if(e.getData().hasKey("hbmRadDataNT")){
				WorldRadiationData data = getWorldRadData(e.getWorld());
				ChunkRadiationStorage cData = new ChunkRadiationStorage(data, e.getChunk());
				cData.readFromNBT(e.getData().getCompoundTag("hbmRadDataNT"));
				data.data.put(e.getChunk().getPos(), cData);
			}
		}
	}
	
	@SubscribeEvent
	public static void onChunkSave(ChunkDataEvent.Save e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			WorldRadiationData data = getWorldRadData(e.getWorld());
			if(data.data.containsKey(e.getChunk().getPos())){
				NBTTagCompound tag = new NBTTagCompound();
				data.data.get(e.getChunk().getPos()).writeToNBT(tag);
				e.getData().setTag("hbmRadDataNT", tag);
			}
		}
	}
	
	@SubscribeEvent
	public static void onWorldLoad(WorldEvent.Load e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			worldMap.put(e.getWorld(), new WorldRadiationData(e.getWorld()));
		}
	}
	
	@SubscribeEvent
	public static void onWorldUnload(WorldEvent.Unload e){
		if(!GeneralConfig.enableRads || !GeneralConfig.advancedRadiation)
			return;
		if(!e.getWorld().isRemote){
			worldMap.remove(e.getWorld());
		}
	}
	
	public static void updateRadiation(){
		long time = System.currentTimeMillis();
		//long lTime = System.nanoTime();
		for(WorldRadiationData w : worldMap.values()){
			//Avoid concurrent modification
			List<RadPocket> itrActive = new ArrayList<>(w.activePockets);
			Iterator<RadPocket> itr = itrActive.iterator();
			while(itr.hasNext()){
				RadPocket p = itr.next();
				BlockPos pos = p.parent.parent.getWorldPos(p.parent.yLevel);
				PlayerChunkMapEntry entry = ((WorldServer)w.world).getPlayerChunkMap().getEntry(p.parent.parent.chunk.x, p.parent.parent.chunk.z);
				if(entry == null || entry.getWatchingPlayers().isEmpty()){
					((WorldServer)w.world).getChunkProvider().queueUnload(p.parent.parent.chunk);
				}
				p.radiation *= 0.999F;
				p.radiation -= 0.05F;
				p.parent.parent.chunk.markDirty();
				if(p.radiation <= 0) {
					p.radiation = 0;
					itr.remove();
					p.parent.parent.chunk.markDirty();
					continue;
				}
				
				if(p.radiation > RadiationConfig.fogRad && w.world != null && w.world.rand.nextInt(RadiationConfig.fogCh) == 0) {
					for(int i = 0; i < 10; i ++){
						BlockPos randPos = new BlockPos(w.world.rand.nextInt(16), w.world.rand.nextInt(16), w.world.rand.nextInt(16));
						if(p.parent.pocketsByBlock == null || p.parent.pocketsByBlock[randPos.getX()*16*16+randPos.getY()*16+randPos.getZ()] == p){
							randPos = randPos.add(p.parent.parent.getWorldPos(p.parent.yLevel));
							IBlockState state = w.world.getBlockState(randPos);
							if(state.getBlock().isAir(state, w.world, randPos)){
								PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacket(randPos.getX()+0.5F, randPos.getY()+0.5F, randPos.getZ()+0.5F, 3), new TargetPoint(w.world.provider.getDimension(), randPos.getX(), randPos.getY(), randPos.getZ(), 100));
								break;
							}
						}
					}
				}
				
				float count = 0;
				for(EnumFacing e : EnumFacing.VALUES){
					count += p.connectionIndices[e.ordinal()].size();
				}
				float amountPer = 0.4F/count;
				if(count == 0 || p.radiation < 1){
					amountPer = 0;
				}
				if(p.radiation > 0 && amountPer > 0){
					for(EnumFacing e : EnumFacing.VALUES){
						BlockPos nPos = pos.offset(e, 16);
						if(!p.parent.parent.chunk.getWorld().isBlockLoaded(nPos) || nPos.getY() < 0 || nPos.getY() > 255)
							continue;
						if(p.connectionIndices[e.ordinal()].size() == 1 && p.connectionIndices[e.ordinal()].get(0) == -1){
							rebuildChunkPockets(p.parent.parent.chunk.getWorld().getChunkFromBlockCoords(nPos), nPos.getY() >> 4);
						} else {
							SubChunkRadiationStorage sc2 = getSubChunkStorage(p.parent.parent.chunk.getWorld(), nPos);
							for(int idx : p.connectionIndices[e.ordinal()]){
								sc2.pockets[idx].accumulatedRads += p.radiation*amountPer;
								w.activePockets.add(sc2.pockets[idx]);
							}
						}
					}
				}
				if(amountPer != 0){
					p.accumulatedRads += p.radiation * 0.6F;
				}
				if(System.currentTimeMillis()-time > 20){
					break;
				}
			}
			for(RadPocket p : w.activePockets){
				p.radiation = p.accumulatedRads;
				p.accumulatedRads = 0;
			}
		}
		//System.out.println(System.nanoTime()-lTime);
		if(System.currentTimeMillis()-time > 50){
			System.out.println("Rads took too long: " + (System.currentTimeMillis()-time));
		}
	}
	
	public static void markChunkForRebuild(World world, BlockPos pos){
		if(!GeneralConfig.advancedRadiation)
			return;
		BlockPos chunkPos = new BlockPos(pos.getX() >> 4, pos.getY() >> 4, pos.getZ() >> 4);
		WorldRadiationData r = getWorldRadData(world);
		if(r.iteratingDirty){
			r.dirtyChunks2.add(chunkPos);
		} else {
			r.dirtyChunks.add(chunkPos);
		}
	}
	
	private static void rebuildDirty(){
		for(WorldRadiationData r : worldMap.values()){
			r.iteratingDirty = true;
			for(BlockPos b : r.dirtyChunks){
				rebuildChunkPockets(r.world.getChunkFromChunkCoords(b.getX(), b.getZ()), b.getY());
			}
			r.iteratingDirty = false;
			r.dirtyChunks.clear();
			r.dirtyChunks.addAll(r.dirtyChunks2);
			r.dirtyChunks2.clear();
		}
	}
	
	private static void rebuildChunkPockets(Chunk chunk, int yIndex){
		BlockPos subChunkPos = new BlockPos(chunk.getPos().x << 4, yIndex << 4, chunk.getPos().z << 4);
		List<RadPocket> pockets = new ArrayList<>();
		ExtendedBlockStorage blocks = chunk.getBlockStorageArray()[yIndex];
		RadPocket[] pocketsByBlock = new RadPocket[16*16*16];
		ChunkRadiationStorage st = getChunkStorage(chunk.getWorld(), subChunkPos);
		SubChunkRadiationStorage subChunk = new SubChunkRadiationStorage(st, subChunkPos.getY(), null, null);
		if(blocks != null){
			for(int x = 0; x < 16; x ++){
				for(int y = 0; y < 16; y ++){
					for(int z = 0; z < 16; z ++){
						Block block = blocks.get(x, y, z).getBlock();
						if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1) && pocketsByBlock[x*16*16+y*16+z] == null){
							pockets.add(buildPocket(subChunk, chunk.getWorld(), new BlockPos(x, y, z), subChunkPos, blocks, pocketsByBlock, pockets.size()));
						}
					}
				}
			}
		} else {
			//Behold, duplicated code with 4 for loop nests because I couldn't be bothered to figure out a good way to change another method.
			RadPocket pocket = new RadPocket(subChunk, 0);
			for(EnumFacing facing : EnumFacing.VALUES){
				for(int x = 0; x < 16; x ++){
					for(int y = 0; y < 16; y ++){
						for(int z = 0; z < 16; z ++){
							BlockPos newPos = new BlockPos(x, y, z).offset(facing);
							if(Math.max(Math.max(newPos.getX(), newPos.getY()), newPos.getZ()) > 15 || Math.min(Math.min(newPos.getX(), newPos.getY()), newPos.getZ()) < 0){
								BlockPos outPos = newPos.add(subChunkPos);
								Block block = chunk.getWorld().getBlockState(outPos).getBlock();
								if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
									if(!isSubChunkLoaded(chunk.getWorld(), outPos)){
										if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
											pocket.connectionIndices[facing.ordinal()].add(-1);
										}
									} else {
										RadPocket outPocket = getPocket(chunk.getWorld(), outPos);
										if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
											pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
									}
								}
							}
						}
					}
				}
			}
			pockets.add(pocket);
		}
		subChunk.pocketsByBlock = pockets.size() == 1 ? null : pocketsByBlock;
		subChunk.pockets = pockets.toArray(new RadPocket[pockets.size()]);
		st.setForYLevel(yIndex << 4, subChunk);
	}
	
	private static RadPocket buildPocket(SubChunkRadiationStorage subChunk, World world, BlockPos start, BlockPos subChunkWorldPos, ExtendedBlockStorage chunk, RadPocket[] pocketsByBlock, int index){
		RadPocket pocket = new RadPocket(subChunk, index);
		Queue<BlockPos> stack = new ArrayDeque<>();
		stack.add(start);
		while(!stack.isEmpty()){
			BlockPos pos = stack.poll();
			Block block = chunk.get(pos.getX(), pos.getY(), pos.getZ()).getBlock();
			if((block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1) || pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] != null){
				continue;
			}
			pocketsByBlock[pos.getX()*16*16+pos.getY()*16+pos.getZ()] = pocket;
			for(EnumFacing facing : EnumFacing.VALUES){
				BlockPos newPos = pos.offset(facing);
				if(Math.max(Math.max(newPos.getX(), newPos.getY()), newPos.getZ()) > 15 || Math.min(Math.min(newPos.getX(), newPos.getY()), newPos.getZ()) < 0){
					BlockPos outPos = newPos.add(subChunkWorldPos);
					if(outPos.getY() < 0 || outPos.getY() > 255)
						continue;
					//Will also attempt to load the chunk, which will case its data to be updated correctly if it's unloaded.
					block = world.getBlockState(outPos).getBlock();
					if(!(block instanceof IRadResistantBlock && ((IRadResistantBlock) block).getResistance() == 1)){
						if(!isSubChunkLoaded(world, outPos)){
							if(!pocket.connectionIndices[facing.ordinal()].contains(-1)){
								pocket.connectionIndices[facing.ordinal()].add(-1);
							}
						} else {
							RadPocket outPocket = getPocket(world, outPos);
							if(!pocket.connectionIndices[facing.ordinal()].contains(Integer.valueOf(outPocket.index)))
								pocket.connectionIndices[facing.ordinal()].add(outPocket.index);
						}
					}
					continue;
				}
				stack.add(newPos);
			}
		}
		return pocket;
	}
	
	//A list of pockets completely closed off by radiation resistant blocks
	public static class RadPocket {
		public SubChunkRadiationStorage parent;
		public int index;
		public float radiation;
		//Used internally so the system doesn't interfere with itself when updating
		private float accumulatedRads = 0;
		//If an array contains -1, that means the chunk on that side hasn't been initialized, so it's an implicit connection
		@SuppressWarnings("unchecked")
		public List<Integer>[] connectionIndices = new List[EnumFacing.VALUES.length];
		
		public RadPocket(SubChunkRadiationStorage parent, int index) {
			this.parent = parent;
			this.index = index;
			for(int i = 0; i < EnumFacing.VALUES.length; i ++){
				connectionIndices[i] = new ArrayList<>(1);
			}
		}
		
		protected void remove(World world, BlockPos pos){
			for(EnumFacing e : EnumFacing.VALUES){
				connectionIndices[e.ordinal()].clear();
			}
			parent.parent.parent.activePockets.remove(this);
		}

		public BlockPos getSubChunkPos() {
			return parent.parent.getWorldPos(parent.yLevel);
		}
	}
	
	//the smaller 16*16*16 chunk
	public static class SubChunkRadiationStorage {
		public ChunkRadiationStorage parent;
		//If it's null, that means there's only 1 pocket, which will be most chunks, so this saves memory.
		public int yLevel;
		public RadPocket[] pocketsByBlock;
		public RadPocket[] pockets;
		
		public SubChunkRadiationStorage(ChunkRadiationStorage parent, int yLevel, RadPocket[] pocketsByBlock, RadPocket[] pockets) {
			this.parent = parent;
			this.yLevel = yLevel;
			this.pocketsByBlock = pocketsByBlock;
			this.pockets = pockets;
		}
				
		public RadPocket getPocket(BlockPos pos){
			if(pocketsByBlock == null){
				return pockets[0];
			} else {
				int x = pos.getX()&15;
				int y = pos.getY()&15;
				int z = pos.getZ()&15;
				RadPocket p = pocketsByBlock[x*16*16+y*16+z];
				return p == null ? pockets[0] : p;
			}
		}
		
		public void setRad(SubChunkRadiationStorage other){
			float total = 0;
			for(RadPocket p : other.pockets){
				total += p.radiation;
			}
			float radPer = total/pockets.length;
			for(RadPocket p : pockets){
				p.radiation = radPer;
				if(radPer > 0){
					p.parent.parent.parent.activePockets.add(p);
				}
			}
		}
		
		public void remove(World world, BlockPos pos){
			for(RadPocket p : pockets){
				p.remove(world, pos);
			}
			for(EnumFacing e : EnumFacing.VALUES){
				//Tries to load the chunk so it updates right.
				world.getBlockState(pos.offset(e, 16));
				if(isSubChunkLoaded(world, pos.offset(e, 16))){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, pos.offset(e, 16));
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
				}
			}
		}
		
		public void add(World world, BlockPos pos){
			for(EnumFacing e : EnumFacing.VALUES){
				//Tries to load the chunk so it updates right.
				world.getBlockState(pos.offset(e, 16));
				if(isSubChunkLoaded(world, pos.offset(e, 16))){
					SubChunkRadiationStorage sc = getSubChunkStorage(world, pos.offset(e, 16));
					for(RadPocket p : sc.pockets){
						p.connectionIndices[e.getOpposite().ordinal()].clear();
					}
					for(RadPocket p : pockets){
						List<Integer> indc = p.connectionIndices[e.ordinal()];
						for(int idx : indc){
							sc.pockets[idx].connectionIndices[e.getOpposite().ordinal()].add(p.index);
						}
					}
				}
			}
		}
	}
	
	//for a whole 16*256*16 chunk
	public static class ChunkRadiationStorage {
		//Half a megabyte is good enough isn't it? Right?
		//This is going to come back to bite me later, isn't it.
		private static ByteBuffer buf = ByteBuffer.allocate(524288);
		
		public WorldRadiationData parent;
		private Chunk chunk;
		private SubChunkRadiationStorage[] chunks = new SubChunkRadiationStorage[16];
		
		public ChunkRadiationStorage(WorldRadiationData parent, Chunk chunk) {
			this.parent = parent;
			this.chunk = chunk;
		}
		
		public SubChunkRadiationStorage getForYLevel(int y){
			int idx = y >> 4;
			if(idx < 0 || idx > chunks.length){
				return null;
			}
			return chunks[y >> 4];
		}
		
		public BlockPos getWorldPos(int y){
			return new BlockPos(chunk.getPos().x << 4, y, chunk.getPos().z << 4);
		}
		
		public void setForYLevel(int y, SubChunkRadiationStorage sc){
			if(chunks[y >> 4] != null){
				chunks[y >> 4].remove(chunk.getWorld(), getWorldPos(y));
				if(sc != null)
					sc.setRad(chunks[y >> 4]);
			}
			if(sc != null){
				sc.add(chunk.getWorld(), getWorldPos(y));
			}
			chunks[y >> 4] = sc;
		}
		
		public void unload(){
			for(int y = 0; y < chunks.length; y ++){
				if(chunks[y] == null)
					continue;
				for(RadPocket p : chunks[y].pockets){
					parent.activePockets.remove(p);
				}
				chunks[y] = null;
			}
		}
		
		public NBTTagCompound writeToNBT(NBTTagCompound tag){
			for(SubChunkRadiationStorage st : chunks){
				if(st == null){
					buf.put((byte) 0);
				} else {
					buf.put((byte) 1);
					buf.putShort((short) st.yLevel);
					buf.putShort((short)st.pockets.length);
					for(RadPocket p : st.pockets){
						writePocket(buf, p);
					}
					if(st.pocketsByBlock == null){
						buf.put((byte) 0);
					} else {
						buf.put((byte) 1);
						for(RadPocket p : st.pocketsByBlock){
							buf.putShort(arrayIndex(p, st.pockets));
						}
					}
				}
			}
			buf.flip();
			byte[] data = new byte[buf.limit()];
			buf.get(data);
			tag.setByteArray("chunkRadData", data);
			buf.clear();
			return tag;
		}
		
		public short arrayIndex(RadPocket p, RadPocket[] pockets){
			for(short i = 0; i < pockets.length; i ++){
				if(p == pockets[i])
					return i;
			}
			return -1;
		}
		
		public void writePocket(ByteBuffer buf, RadPocket p){
			buf.putInt(p.index);
			buf.putFloat(p.radiation);
			for(EnumFacing e : EnumFacing.VALUES){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				buf.putShort((short) indc.size());
				for(int idx : indc){
					buf.putShort((short) idx);
				}
			}
		}
		
		public void readFromNBT(NBTTagCompound tag){
			ByteBuffer data = ByteBuffer.wrap(tag.getByteArray("chunkRadData"));
			for(int i = 0; i < chunks.length; i ++){
				boolean subChunkExists = data.get() == 1 ? true : false;
				if(subChunkExists){
					int yLevel = data.getShort();
					SubChunkRadiationStorage st = new SubChunkRadiationStorage(this, yLevel, null, null);
					int pocketsLength = data.getShort();
					st.pockets = new RadPocket[pocketsLength];
					for(int j = 0; j < pocketsLength; j ++){
						st.pockets[j] = readPocket(data, st);
						if(st.pockets[j].radiation > 0){
							parent.activePockets.add(st.pockets[j]);
						}
					}
					boolean perBlockDataExists = data.get() == 1 ? true : false;
					if(perBlockDataExists){
						st.pocketsByBlock = new RadPocket[16*16*16];
						for(int j = 0; j < 16*16*16; j ++){
							int idx = data.getShort();
							if(idx >= 0)
								st.pocketsByBlock[j] = st.pockets[idx];
						}
					} else {
					}
					chunks[i] = st;
				} else {
					chunks[i] = null;
				}
			}
		}
		
		public RadPocket readPocket(ByteBuffer buf, SubChunkRadiationStorage parent){
			int index = buf.getInt();
			RadPocket p = new RadPocket(parent, index);
			p.radiation = buf.getFloat();
			for(EnumFacing e : EnumFacing.VALUES){
				List<Integer> indc = p.connectionIndices[e.ordinal()];
				int size = buf.getShort();
				for(int i = 0; i < size; i ++){
					indc.add((int) buf.getShort());
				}
			}
			return p;
		}
	}
	
	public static class WorldRadiationData {
		public World world;
		//Keep two lists to avoid concurrent modification. If one is being iterated over, mark it dirty in the other set.
		private Set<BlockPos> dirtyChunks = new HashSet<>();
		private Set<BlockPos> dirtyChunks2 = new HashSet<>();
		private boolean iteratingDirty = false;
		
		public Set<RadPocket> activePockets = new HashSet<>();
		public Map<ChunkPos, ChunkRadiationStorage> data = new HashMap<>();
		
		public WorldRadiationData(World world) {
			this.world = world;
		}
	}
}
