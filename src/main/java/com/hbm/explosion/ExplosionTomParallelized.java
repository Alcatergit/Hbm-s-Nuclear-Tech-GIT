package com.hbm.explosion;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.threading.BombForkJoinPool;
import com.hbm.interfaces.IExplosionRay;
import com.hbm.interfaces.ServerThread;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.config.WorldConfig;
import com.hbm.util.ChunkUtil;
import com.hbm.world.WorldUtil;
import com.hbm.world.biome.BiomeGenDustWastes;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import net.minecraft.world.biome.Biome;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagLongArray;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.common.util.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/** Streaming parallel version of ExplosionTom: loads, converts and applies chunks incrementally, with the same block placement rules as the original. */
public class ExplosionTomParallelized implements IExplosionRay, BombForkJoinPool.IJobCancellable {

    private final WorldServer worldServer;
    private final int originX, originY, originZ;
    private final int radius, radius2;
    private final List<Long> chunksToProcess = new ArrayList<>();
    private final ConcurrentLinkedQueue<Long> chunkLoadQueue = new ConcurrentLinkedQueue<>();
    private final PriorityBlockingQueue<Long> applyQueue = new PriorityBlockingQueue<>(64, (a, b) ->
			Double.compare(chunkDistSq(a.longValue()), chunkDistSq(b.longValue())));
    private final ConcurrentHashMap<Long, ConcurrentMap<Long, IBlockState>> replacements = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Set<Long>> notifyPositions = new ConcurrentHashMap<>();
    private final Set<Long> conversionChunks = ConcurrentHashMap.newKeySet();
    private final Set<Long> scannedChunks = ConcurrentHashMap.newKeySet();
    private final Set<Long> populatedChunks = ConcurrentHashMap.newKeySet();
    private final Set<Long> reloadQueued = ConcurrentHashMap.newKeySet();
    private final Long2IntOpenHashMap sectionMaskByChunk = new Long2IntOpenHashMap();
    private final LinkedHashMap<Long, Integer> pendingSend = new LinkedHashMap<>();
    private final AtomicInteger activeWorkerTasks = new AtomicInteger();
    private final AtomicInteger pendingChunks = new AtomicInteger();
    private volatile boolean poolAcquired, jobRegistered, mapAcquired;
    private volatile boolean collectFinished, destroyFinished, finishQueued, cancelCleanup, cancelling;
    private volatile Throwable failure;
    private int jobDimension = Integer.MIN_VALUE;
    private final ThreadLocal<Long> scanTarget = ThreadLocal.withInitial(() -> Long.MIN_VALUE);
    private ForkJoinPool pool;
    private ConcurrentHashMap<Long, Chunk> mirror;

    public ExplosionTomParallelized(World worldIn, int x, int y, int z, int rad) {
		this.worldServer = (WorldServer) worldIn;
		this.originX = x;
		this.originY = y;
		this.originZ = z;
		this.radius = rad;
		this.radius2 = rad * rad;
	}

    @Override
	public void update(int processTimeMs) {
		if (failure != null) {
			cancel();
			return;
		}
		if (destroyFinished) return;
		if (!collectFinished && mirror == null) startWorkers();
		if (!collectFinished && (worldServer.getWorldTime() & 3) == 0) submitRetry();
		long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(processTimeMs);
		while (System.nanoTime() < deadline) {
			Long cp = chunkLoadQueue.poll();
			if (cp != null) {
				loadChunk(cp.longValue());
			}
			Long apply = applyQueue.poll();
			if (apply != null) {
				applyChunk(apply.longValue());
			}
			if (cp == null && apply == null) break;
		}
		drainSendQueue();
		maybeFinish();
	}

    @Override
	public boolean isComplete() {
		return destroyFinished;
	}

    @Override
	public boolean hasFailed() {
		return failure != null;
	}

    @Override
	public boolean isContained() {
		return true;
	}

    @Override
	public void setDetonator(UUID detonator) {
	}

    public void cancel() {
		cancelling = true;
		collectFinished = true;
		destroyFinished = true;
		if (activeWorkerTasks.get() == 0) {
			finishCancellation();
		} else {
			worldServer.addScheduledTask(this::pollCancelCleanup);
		}
	}

    @Override
	public void cancelJob() {
		cancel();
	}

    synchronized void fail(Throwable cause) {
		if (failure != null) return;
		failure = cause;
		MainRegistry.logger.error("Parallel TOM explosion work failed", cause);
	}

    @ServerThread
	private void startWorkers() {
		if (chunksToProcess.isEmpty()) gatherChunks();
		if (!poolAcquired) {
			pool = BombForkJoinPool.acquire();
			poolAcquired = true;
		}
		registerJobIfNeeded();
		mirror = ChunkUtil.acquireMirrorMap(worldServer);
		mapAcquired = true;

		prepareResumeState();

		Set<Long> enqueued = new HashSet<>(chunksToProcess.size() * 3);
		for (long cp : chunksToProcess) {
			int cx = Library.getChunkPosX(cp);
			int cz = Library.getChunkPosZ(cp);
			for (int dx = -1; dx <= 1; dx++) {
				for (int dz = -1; dz <= 1; dz++) {
					long ncp = ChunkPos.asLong(cx + dx, cz + dz);
					if (!enqueued.add(ncp)) continue;
					Chunk loadedChunk = worldServer.getChunkProvider().loadedChunks.get(ncp);
					if (loadedChunk != null && loadedChunk.isTerrainPopulated()) {
						populatedChunks.add(ncp);
						mirror.put(ncp, loadedChunk);
						continue;
					}
					chunkLoadQueue.offer(ncp);
				}
			}
		}

		if (pool == null || pool.isShutdown()) {
			collectFinished = true;
			maybeFinish();
			return;
		}
		int grain = Math.max(1, chunksToProcess.size() / Math.max(1, pool.getParallelism() * 4));
		pool.submit(new ColumnTask(chunksToProcess, 0, chunksToProcess.size(), grain));
	}

    private void gatherChunks() {
		double range = radius + 12.0;
		double rangeSq = range * range;
		int cx0 = originX >> 4, cz0 = originZ >> 4;
		int cr = (int) Math.ceil(range / 16.0) + 1;
		for (int cx = cx0 - cr; cx <= cx0 + cr; cx++) {
			double dcx = (cx << 4) + 8 - originX;
			if (dcx * dcx > rangeSq) continue;
			int maxDcz = (int) Math.floor(Math.sqrt(rangeSq - dcx * dcx));
			int czOff = (maxDcz + 8) >> 4;
			for (int cz = cz0 - czOff; cz <= cz0 + czOff; cz++) {
				double dcz = (cz << 4) + 8 - originZ;
				if (dcx * dcx + dcz * dcz > rangeSq) continue;
				chunksToProcess.add(ChunkPos.asLong(cx, cz));
			}
		}
	}

    @ServerThread
	private void prepareResumeState() {
		for (Chunk c : worldServer.getChunkProvider().loadedChunks.values()) {
			if (c.isTerrainPopulated()) populatedChunks.add(ChunkPos.asLong(c.x, c.z));
		}
		int preScanned = 0;
		for (long cp : sectionMaskByChunk.keySet()) {
			if (scannedChunks.add(cp)) preScanned++;
		}
		pendingChunks.set(chunksToProcess.size() - preScanned);
		conversionChunks.clear();
		conversionChunks.addAll(chunksToProcess);
	}

    private void registerJobIfNeeded() {
		if (!jobRegistered) {
			jobRegistered = true;
			jobDimension = worldServer.provider.getDimension();
			BombForkJoinPool.register(jobDimension, this);
		}
	}

    private void unregisterJobIfNeeded() {
		if (jobRegistered) {
			jobRegistered = false;
			BombForkJoinPool.unregister(jobDimension, this);
			jobDimension = Integer.MIN_VALUE;
		}
	}

    private void releasePoolIfHeld() {
		unregisterJobIfNeeded();
		if (poolAcquired) {
			poolAcquired = false;
			BombForkJoinPool.release();
			pool = null;
		}
	}

    @ServerThread
	private void loadChunk(long chunkPos) {
		reloadQueued.remove(chunkPos);
		int cx = Library.getChunkPosX(chunkPos);
		int cz = Library.getChunkPosZ(chunkPos);
		Chunk chunk = worldServer.getChunk(cx, cz);
		if (chunk != null) {
			if (!chunk.isTerrainPopulated()) {
				worldServer.getChunk(cx + 1, cz);
				worldServer.getChunk(cx, cz + 1);
				worldServer.getChunk(cx + 1, cz + 1);
				ChunkProviderServer provider = (ChunkProviderServer) worldServer.getChunkProvider();
				chunk.populate(provider, provider.chunkGenerator);
				chunk.generateSkylightMap();
			}
			populatedChunks.add(chunkPos);
			mirror.put(chunkPos, chunk);
		}
		ForkJoinPool p = pool;
		if (p != null && !p.isShutdown()) {
			activeWorkerTasks.incrementAndGet();
			p.submit(() -> {
				try {
					retryChunk(chunkPos);
				} catch (Exception e) {
					fail(e);
				} finally {
					activeWorkerTasks.decrementAndGet();
				}
			});
		}
	}

    private boolean isNeighborhoodPopulated(long cp) {
		int cx = Library.getChunkPosX(cp);
		int cz = Library.getChunkPosZ(cp);
		for (int dx = -1; dx <= 1; dx++) {
			for (int dz = -1; dz <= 1; dz++) {
				if (dx == 0 && dz == 0) continue;
				if (!populatedChunks.contains(ChunkPos.asLong(cx + dx, cz + dz))) return false;
			}
		}
		return true;
	}

    private void retryChunk(long cp) {
		if (!conversionChunks.contains(cp)) {
			return; // margin chunk, loaded only to satisfy the neighbour gate, not converted
		}
		int cx = Library.getChunkPosX(cp);
		int cz = Library.getChunkPosZ(cp);
		Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
		if (chunk == null || !chunk.isTerrainPopulated()) {
			requeueUnloaded(cp);
			return;
		}
		if (!isNeighborhoodPopulated(cp)) {
			return; // not ready yet; submitRetry re-scans via the mirror
		}
		ColumnWorker worker = new ColumnWorker();
		worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
	}

    private void requeueUnloaded(long cp) {
		if (!conversionChunks.contains(cp)) return;
		if (reloadQueued.add(cp)) chunkLoadQueue.offer(cp);
	}

    private void submitRetry() {
		if (destroyFinished || failure != null) return;
		ForkJoinPool p = pool;
		if (p == null || p.isShutdown()) return;
		List<Long> pending = new ArrayList<>();
		for (long cp : conversionChunks) {
			if (!scannedChunks.contains(cp)) pending.add(cp);
		}
		if (pending.isEmpty()) return;
		long[] arr = new long[pending.size()];
		for (int i = 0; i < arr.length; i++) arr[i] = pending.get(i).longValue();
		int grain = Math.max(1, arr.length / Math.max(1, p.getParallelism() * 4));
		p.submit(new RetryTask(arr, 0, arr.length, grain));
	}

    final class ColumnWorker {
		private final Random rnd = new Random(ThreadLocalRandom.current().nextLong());

		void processChunk(long cp, int cx, int cz, ExtendedBlockStorage[] ebs) {
			if (scannedChunks.add(cp)) {
				scanTarget.set(cp);
				try {
					int xBase = cx << 4;
					int zBase = cz << 4;
					for (int x = xBase - 2; x < xBase + 18; x++) {
						for (int z = zBase - 2; z < zBase + 18; z++) {
							breakColumn(x - originX, z - originZ, rnd);
						}
					}
				} finally {
					scanTarget.set(Long.MIN_VALUE);
				}
				pendingChunks.decrementAndGet();
				ConcurrentMap<Long, IBlockState> own = replacements.get(cp);
				if (own != null && !own.isEmpty()) {
					applyQueue.offer(cp);
				}
			} else {
				// Re-scan after an unload-reoffer: replacements were put back by applyChunk, re-queue them.
				ConcurrentMap<Long, IBlockState> existing = replacements.get(cp);
				if (existing != null && !existing.isEmpty()) {
					applyQueue.offer(cp);
				}
			}
		}

/** Convert one column and record the replacements, routed to the owning chunk. */
		private void breakColumn(int xOff, int zOff, Random rnd) {
			int dist = radius2 - (xOff * xOff + zOff * zOff);
			if (dist <= 0) return;
			int pX = originX + xOff;
			int pZ = originZ + zOff;
			double distance = Math.sqrt((double) (xOff * xOff + zOff * zOff));
			int y = 256;
			int terrain = originY - 1;

			double cA = (terrain - Math.pow(Math.E, -Math.pow(Math.sqrt(xOff * xOff + zOff * zOff), 2) / 40000) * 13) + rnd.nextInt(2);
			double cB = cA + Math.pow(Math.E, -Math.pow(Math.sqrt(xOff * xOff + zOff * zOff) - 200, 2) / 400) * 13;
			int craterFloor = (int) (cB + Math.pow(Math.E, -Math.pow(Math.sqrt(xOff * xOff + zOff * zOff) - 500, 2) / 2000) * 37);

			for (int i = 256; i > 0; i--) {
				if (i == craterFloor || readState(pX, i, pZ).getBlock() != Blocks.AIR) {
					y = i;
					break;
				}
			}
			int height = terrain - 14;
			int offset = 20;
			int threshold = (int) ((float) Math.sqrt(xOff * xOff + zOff * zOff) * (float) (height + offset) / (float) radius)
					+ rnd.nextInt(2) - offset;

			if (y < terrain + 1) y = terrain + 1;

			while (y > threshold) {
				if (y == 0) break;
				if (y <= craterFloor) {
					if (rnd.nextInt(200) == 0) {
						putReplacement(pX, y, pZ, ModBlocks.ore_tektite_osmiridium.getDefaultState());
					} else {
						putReplacement(pX, y, pZ, ModBlocks.tektite.getDefaultState());
					}
				} else {
					if (y > terrain + 1) {
						if (distance < 500) {
							for (int i = -2; i < 3; i++) {
								for (int j = -2; j < 3; j++) {
									for (int k = -2; k < 3; k++) {
										Material m = readState(pX + i, y + j, pZ + k).getMaterial();
										if (m == Material.WATER || m == Material.ICE || m == Material.SNOW || m.getCanBurn()) {
											putReplacement(pX + i, y + j, pZ + k, Blocks.AIR.getDefaultState());
											putReplacement(pX, y, pZ, Blocks.AIR.getDefaultState());
										}
									}
								}
							}
							putReplacement(pX, y, pZ, Blocks.AIR.getDefaultState());
						}
					} else {
						for (int i = -2; i < 3; i++) {
							for (int j = -2; j < 3; j++) {
								for (int k = -2; k < 3; k++) {
									Material m = readState(pX + i, y + j, pZ + k).getMaterial();
									IBlockState nb = readState(pX + i, y, pZ + k);
									Material nm = nb.getMaterial();
									if (m == Material.WATER || m == Material.ICE || nb.getBlock() == Blocks.AIR || nm == Material.SNOW || nm.getCanBurn()) {
										putReplacement(pX + i, y, pZ + k, Blocks.LAVA.getDefaultState());
										putReplacement(pX, y, pZ, Blocks.LAVA.getDefaultState());
									}
								}
							}
						}
						putReplacement(pX, y, pZ, Blocks.LAVA.getDefaultState());
					}
				}
				y--;
			}
		}
	}

    private void putReplacement(int x, int y, int z, IBlockState state) {
		long owner = ChunkPos.asLong(x >> 4, z >> 4);
		if (owner != scanTarget.get().longValue()) return;
		ConcurrentMap<Long, IBlockState> out = replacements.computeIfAbsent(owner,
				k -> new ConcurrentHashMap<>());
		out.put(Library.blockPosToLong(x, y, z), state);
		markNotify(x, y, z);
	}

    private void markNotify(int x, int y, int z) {
		notifyPositions.computeIfAbsent(ChunkPos.asLong(x >> 4, z >> 4), k -> ConcurrentHashMap.newKeySet())
				.add(Library.blockPosToLong(x, y, z));
	}

    private IBlockState readState(int x, int y, int z) {
		if (y < 0 || y > 255) return Blocks.AIR.getDefaultState();
		Chunk chunk = mirror.get(ChunkPos.asLong(x >> 4, z >> 4));
		if (chunk == null) return Blocks.AIR.getDefaultState();
		ExtendedBlockStorage[] ebs = chunk.getBlockStorageArray();
		int subY = y >> 4;
		if (subY >= ebs.length) return Blocks.AIR.getDefaultState();
		ExtendedBlockStorage s = ebs[subY];
		if (s == null || s == Chunk.NULL_BLOCK_STORAGE || s.isEmpty()) return Blocks.AIR.getDefaultState();
		return s.get(x & 15, y & 15, z & 15);
	}

    @ServerThread
	private void applyChunk(long cpLong) {
		ConcurrentMap<Long, IBlockState> changes = replacements.remove(cpLong);
		if (changes == null || changes.isEmpty()) return;
		int cx = Library.getChunkPosX(cpLong);
		int cz = Library.getChunkPosZ(cpLong);
		Chunk chunk = worldServer.getChunkProvider().loadedChunks.get(cpLong);
		if (chunk == null) {
			replacements.put(cpLong, changes);
			chunkLoadQueue.offer(cpLong);
			return;
		}
		ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
		Int2ObjectOpenHashMap<IBlockState>[] buckets = new Int2ObjectOpenHashMap[16];
		int selfMask = 0;
		Long2ObjectOpenHashMap<IBlockState> oldStates = new Long2ObjectOpenHashMap<>();
		for (Map.Entry<Long, IBlockState> e : changes.entrySet()) {
			long packed = e.getKey().longValue();
			int x = Library.getBlockPosX(packed);
			int y = Library.getBlockPosY(packed);
			int z = Library.getBlockPosZ(packed);
			int subY = y >> 4;
			if (subY < 0 || subY >= 16) continue;
			if (buckets[subY] == null) buckets[subY] = new Int2ObjectOpenHashMap<>();
			buckets[subY].put(Library.packLocal(x & 15, y & 15, z & 15), e.getValue());
		}
		for (int subY = 0; subY < 16; subY++) {
			Int2ObjectOpenHashMap<IBlockState> bucket = buckets[subY];
			if (bucket == null || bucket.isEmpty()) continue;
			ExtendedBlockStorage src = storages[subY];
			Optional<ExtendedBlockStorage> changed = ChunkUtil.copyAndModify(cx, cz, subY, worldServer.provider.hasSkyLight(), src, bucket, oldStates);
			if (changed == null) continue;
			storages[subY] = changed.orElse(Chunk.NULL_BLOCK_STORAGE);
			selfMask |= 1 << subY;
		}
		for (int s = 0; s < 16; s++) {
			if ((selfMask & (1 << s)) != 0) ChunkUtil.rebuildHeightMap(chunk, s);
		}
		MutableBlockPos p = new MutableBlockPos();
		for (Long2ObjectMap.Entry<IBlockState> e : oldStates.long2ObjectEntrySet()) {
			IBlockState oldState = e.getValue();
			if (oldState.getBlock().hasTileEntity(oldState)) {
				Library.fromLong(p, e.getLongKey());
				worldServer.removeTileEntity(p);
			}
		}
		Set<Long> notify = notifyPositions.remove(cpLong);
		if (notify != null && !notify.isEmpty()) {
			for (long packed : notify) {
				IBlockState oldState = oldStates.get(packed);
				if (oldState == null) continue;
				Library.fromLong(p, packed);
				for (EnumFacing facing : EnumFacing.values()) {
					BlockPos neighborPos = p.offset(facing);
					if (worldServer.isBlockLoaded(neighborPos)) {
						IBlockState neighborState = worldServer.getBlockState(neighborPos);
						Block neighborBlock = neighborState.getBlock();
						if (neighborBlock != Blocks.WATER && neighborBlock != Blocks.FLOWING_WATER && !hasWaterNeighbor(neighborPos)) {
							worldServer.neighborChanged(neighborPos, oldState.getBlock(), p);
						}
					}
				}
			}
		}
		applyBiomeToChunk(chunk, cx, cz);
		if (selfMask != 0) {
			sectionMaskByChunk.put(cpLong, sectionMaskByChunk.get(cpLong) | selfMask);
			ChunkUtil.markBlocksChanged(chunk);
			ChunkUtil.requestLight(chunk, selfMask);
			pendingSend.merge(cpLong, selfMask, (a, b) -> a | b);
		}
	}

    /** Sets the Dust Wastes biome for the chunk's columns inside the crater circle, then syncs to clients. */

    private void applyBiomeToChunk(Chunk chunk, int cx, int cz) {
		if (!WorldConfig.enableDustWastesBiome) return;
		byte[] arr = chunk.getBiomeArray();
		if (arr.length != 256) return;
		int biomeId = Biome.getIdForBiome(BiomeGenDustWastes.dustWastes);
		for (int x = 0; x < 16; x++) {
			for (int z = 0; z < 16; z++) {
				int wx = (cx << 4) + x;
				int wz = (cz << 4) + z;
				int dx = wx - originX;
				int dz = wz - originZ;
				if (dx * dx + dz * dz <= radius2) {
					arr[((z & 15) << 4) | (x & 15)] = (byte) (biomeId & 0xFF);
				}
			}
		}
		chunk.markDirty();
		WorldUtil.syncBiomeChange(worldServer, cx, cz);
	}

    private boolean hasWaterNeighbor(BlockPos pos) {
		for (EnumFacing facing : EnumFacing.values()) {
			BlockPos np = pos.offset(facing);
			if (worldServer.isBlockLoaded(np)) {
				Block b = worldServer.getBlockState(np).getBlock();
				if (b == Blocks.WATER || b == Blocks.FLOWING_WATER) {
					return true;
				}
			}
		}
		return false;
	}

    @ServerThread
	private void secondPass() {
		Long2ObjectMap<Chunk> loaded = worldServer.getChunkProvider().loadedChunks;
		for (Map.Entry<Long, Integer> e : pendingSend.entrySet()) {
			long cp = e.getKey().longValue();
			Chunk chunk = loaded.get(cp);
			if (chunk == null) continue;
			int lightMask = ChunkUtil.takeLightMask(worldServer, cp);
			int mask = e.getValue().intValue() | lightMask;
			if (mask == 0) continue;
			chunk.markDirty();
			ChunkUtil.sendChunk(worldServer, chunk, mask);
		}
		pendingSend.clear();
		ChunkUtil.drainRemaining(worldServer);
		sectionMaskByChunk.clear();
	}

    private void drainSendQueue() {
		if (pendingSend.isEmpty()) return;
		Long2ObjectMap<Chunk> loaded = worldServer.getChunkProvider().loadedChunks;
		Iterator<Map.Entry<Long, Integer>> it = pendingSend.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<Long, Integer> e = it.next();
			long cp = e.getKey().longValue();
			if (ChunkUtil.hasPendingLight(worldServer, cp)) continue;
			int lightMask = ChunkUtil.takeLightMask(worldServer, cp);
			int mask = e.getValue().intValue() | lightMask;
			Chunk chunk = loaded.get(cp);
			if (chunk != null && mask != 0) {
				chunk.markDirty();
				ChunkUtil.sendChunk(worldServer, chunk, mask);
			}
			it.remove();
		}
	}

    private void maybeFinish() {
		if (!collectFinished) {
			if (activeWorkerTasks.get() == 0 && pendingChunks.get() == 0) {
				collectFinished = true;
			} else {
				return;
			}
		}
		if (destroyFinished) return;
		if (activeWorkerTasks.get() != 0 || pendingChunks.get() != 0) return;
		if (!applyQueue.isEmpty() || !chunkLoadQueue.isEmpty()) return;
		if (ChunkUtil.anyPendingLight(worldServer)) {
			return;
		}
		if (finishQueued) return;
		finishQueued = true;
		worldServer.addScheduledTask(() -> {
			secondPass();
			destroyFinished = true;
			releasePoolIfHeld();
			if (mapAcquired) {
				mapAcquired = false;
				ChunkUtil.releaseMirrorMap(worldServer);
			}
		});
	}

    @ServerThread
	private void finishCancellation() {
		if (activeWorkerTasks.get() != 0 || cancelCleanup) return;
		cancelCleanup = true;
		replacements.clear();
		notifyPositions.clear();
		chunkLoadQueue.clear();
		applyQueue.clear();
		sectionMaskByChunk.clear();
		pendingSend.clear();
		releasePoolIfHeld();
		if (mapAcquired) {
			mapAcquired = false;
			ChunkUtil.releaseMirrorMap(worldServer);
		}
	}

    @ServerThread
	private void pollCancelCleanup() {
		if (activeWorkerTasks.get() == 0) {
			finishCancellation();
		} else {
			worldServer.addScheduledTask(this::pollCancelCleanup);
		}
	}

    @Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		if (nbt.getBoolean("collectDone")) collectFinished = true;
		if (nbt.getBoolean("destroyDone")) destroyFinished = true;
		if (nbt.hasKey("sectionMask", Constants.NBT.TAG_LIST)) {
			NBTTagList list = nbt.getTagList("sectionMask", Constants.NBT.TAG_COMPOUND);
			for (int i = 0; i < list.tagCount(); i++) {
				NBTTagCompound t = list.getCompoundTagAt(i);
				long ck = ChunkPos.asLong(t.getInteger("cX"), t.getInteger("cZ"));
				sectionMaskByChunk.put(ck, t.getInteger("mask"));
			}
		}
		if (nbt.hasKey("populatedChunks")) {
			for (long cp : ((NBTTagLongArray) nbt.getTag("populatedChunks")).data) {
				populatedChunks.add(cp);
			}
		}
		if (collectFinished && !destroyFinished) {
			worldServer.addScheduledTask(() -> {
				secondPass();
				destroyFinished = true;
			});
		}
		// else: collectFinished == false → update() re-gathers chunks and calls startWorkers().
	}

    @Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		nbt.setBoolean("collectDone", collectFinished);
		nbt.setBoolean("destroyDone", destroyFinished);
		if (!sectionMaskByChunk.isEmpty()) {
			NBTTagList list = new NBTTagList();
			ObjectIterator<Long2IntOpenHashMap.Entry> iterator = sectionMaskByChunk.long2IntEntrySet().fastIterator();
			while (iterator.hasNext()) {
				Long2IntOpenHashMap.Entry e = iterator.next();
				NBTTagCompound t = new NBTTagCompound();
				t.setInteger("cX", Library.getChunkPosX(e.getLongKey()));
				t.setInteger("cZ", Library.getChunkPosZ(e.getLongKey()));
				t.setInteger("mask", e.getIntValue());
				list.appendTag(t);
			}
			nbt.setTag("sectionMask", list);
		}
		if (!populatedChunks.isEmpty()) {
			long[] arr = new long[populatedChunks.size()];
			int i = 0;
			for (long cp : populatedChunks) arr[i++] = cp;
			nbt.setTag("populatedChunks", new NBTTagLongArray(arr));
		}
	}

    private double chunkDistSq(long cp) {
		int cx = Library.getChunkPosX(cp);
		int cz = Library.getChunkPosZ(cp);
		double dx = (cx << 4) + 8.0 - originX;
		double dz = (cz << 4) + 8.0 - originZ;
		return dx * dx + dz * dz;
	}

    final class ColumnTask extends RecursiveAction {
		final List<Long> chunks;
		final int start, end, threshold;

		ColumnTask(List<Long> chunks, int start, int end, int threshold) {
			this.chunks = chunks;
			this.start = start;
			this.end = end;
			this.threshold = Math.max(1, threshold);
		}

		@Override
		protected void compute() {
			int len = end - start;
			if (len <= threshold) {
				activeWorkerTasks.incrementAndGet();
				try {
					ColumnWorker worker = new ColumnWorker();
					for (int i = start; i < end; i++) {
						if (Thread.currentThread().isInterrupted() || destroyFinished) break;
						long cp = chunks.get(i).longValue();
						int cx = Library.getChunkPosX(cp);
						int cz = Library.getChunkPosZ(cp);
						Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
						if (chunk == null || !chunk.isTerrainPopulated()) {
							requeueUnloaded(cp);
							continue;
						}
						if (!isNeighborhoodPopulated(cp)) continue;
						worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
					}
				} catch (Exception e) {
					fail(e);
				} finally {
					activeWorkerTasks.decrementAndGet();
				}
			} else {
				int mid = start + (len >>> 1);
				invokeAll(new ColumnTask(chunks, start, mid, threshold), new ColumnTask(chunks, mid, end, threshold));
			}
		}
	}

    final class RetryTask extends RecursiveAction {
		final long[] chunks;
		final int start, end, threshold;

		RetryTask(long[] chunks, int start, int end, int threshold) {
			this.chunks = chunks;
			this.start = start;
			this.end = end;
			this.threshold = Math.max(1, threshold);
		}

		@Override
		protected void compute() {
			int len = end - start;
			if (len <= threshold) {
				activeWorkerTasks.incrementAndGet();
				try {
					ColumnWorker worker = new ColumnWorker();
					for (int i = start; i < end; i++) {
						if (Thread.currentThread().isInterrupted() || destroyFinished) break;
						long cp = chunks[i];
						if (scannedChunks.contains(cp)) continue;
						int cx = Library.getChunkPosX(cp);
						int cz = Library.getChunkPosZ(cp);
						Chunk chunk = ChunkUtil.getLoadedChunk(mirror, cp);
						if (chunk == null || !chunk.isTerrainPopulated()) {
							requeueUnloaded(cp);
							continue;
						}
						if (!isNeighborhoodPopulated(cp)) continue;
						worker.processChunk(cp, cx, cz, chunk.getBlockStorageArray());
					}
				} catch (Exception e) {
					fail(e);
				} finally {
					activeWorkerTasks.decrementAndGet();
				}
			} else {
				int mid = start + (len >>> 1);
				invokeAll(new RetryTask(chunks, start, mid, threshold), new RetryTask(chunks, mid, end, threshold));
			}
		}
	}
}
