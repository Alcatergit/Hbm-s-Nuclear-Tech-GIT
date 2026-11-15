package com.hbm.explosion;

import com.hbm.config.BombConfig;
import com.hbm.config.CompatibilityConfig;
import com.hbm.entity.effect.EntityFalloutRain;
import com.hbm.handler.threading.BombForkJoinPool;
import com.hbm.interfaces.BitMask;
import com.hbm.interfaces.IExplosionRay;
import com.hbm.interfaces.ServerThread;
import com.hbm.lib.Library;
import com.hbm.main.MainRegistry;
import com.hbm.util.AtomicLongBitSet;
import com.hbm.util.ChunkUtil;
import com.hbm.util.CompatDynamicTrees;
import it.unimi.dsi.fastutil.ints.Int2DoubleMap;
import it.unimi.dsi.fastutil.ints.Int2DoubleOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongArrays;
import it.unimi.dsi.fastutil.longs.LongIterator;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.Block;
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

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static com.hbm.lib.internal.UnsafeHolder.U;
import static com.hbm.lib.internal.UnsafeHolder.fieldOffset;

/** Threaded DDA raytracer for mk5 nuclear explosions. */
    public class ExplosionNukeRayParallelized implements IExplosionRay, BombForkJoinPool.IJobCancellable {

    static final int WORLD_HEIGHT = 256;
    static final int BITSET_SIZE = 16 * WORLD_HEIGHT * 16;
    static final int SUB_MASK_SIZE = 16 * 16 * 16;
    static final int SUBCHUNK_PER_CHUNK = WORLD_HEIGHT >> 4;
    static final float NUKE_RESISTANCE_CUTOFF = 2_000_000F;
    static final float INITIAL_ENERGY_FACTOR = 0.3F;
    static final double RESOLUTION_FACTOR = 1.0;
    static final int LUT_RESISTANCE_BINS = 256;
    static final int LUT_DISTANCE_BINS = 256;
    static final float LUT_MAX_RESISTANCE = 100.0F;
    static final float[][] ENERGY_LOSS_LUT = new float[LUT_RESISTANCE_BINS][LUT_DISTANCE_BINS];
    static final float DAMAGE_PER_BLOCK = 0.50F;
    static final float DAMAGE_THRESHOLD_MULT = 1.00F;
    static final float LOW_R_BOUND = 0.25F;
    static final float LOW_R_PASS_LENGTH_BREAK = 0.75F;
    static final double GOLDEN_ANGLE = Math.PI * (3.0 - Math.sqrt(5.0));
    static final double RAY_DIRECTION_EPSILON = 1e-6;
    static final double PROCESSING_EPSILON = 1e-9;
    static final float MIN_EFFECTIVE_DIST_FOR_ENERGY_CALC = 0.01f;
    static final int PAUSED_RAY_WORDS = 6;
    static final long OFF_MAP_ACQUIRED = fieldOffset(ExplosionNukeRayParallelized.class, "mapAcquired");
    static final long OFF_CONSOLIDATION_STARTED = fieldOffset(ExplosionNukeRayParallelized.class, "consolidationStarted");
    static final long OFF_PENDING_RAYS = fieldOffset(ExplosionNukeRayParallelized.class, "pendingRays");
    static final long OFF_ACTIVE_WORKER_TASKS = fieldOffset(ExplosionNukeRayParallelized.class, "activeWorkerTasks");
    static final long OFF_FINISH_QUEUED = fieldOffset(ExplosionNukeRayParallelized.class, "finishQueued");
    static final long OFF_COLLECT_FINISHED = fieldOffset(ExplosionNukeRayParallelized.class, "collectFinished");
    static final long OFF_CONSOLIDATION_FINISHED = fieldOffset(ExplosionNukeRayParallelized.class, "consolidationFinished");
    static final long OFF_DESTROY_FINISHED = fieldOffset(ExplosionNukeRayParallelized.class, "destroyFinished");
    static final long OFF_POOL_ACQUIRED = fieldOffset(ExplosionNukeRayParallelized.class, "poolAcquired");
    static final long OFF_JOB_REGISTERED = fieldOffset(ExplosionNukeRayParallelized.class, "jobRegistered");
    static final long OFF_CANCEL_CLEANUP = fieldOffset(ExplosionNukeRayParallelized.class, "cancelCleanup");

    static {
        for (int r = 0; r < LUT_RESISTANCE_BINS; r++) {
            float resistance = (r / (float) (LUT_RESISTANCE_BINS - 1)) * LUT_MAX_RESISTANCE;
            for (int d = 0; d < LUT_DISTANCE_BINS; d++) {
                float distFrac = d / (float) (LUT_DISTANCE_BINS - 1);
                ENERGY_LOSS_LUT[r][d] = (float) (Math.pow(resistance + 1.0, 3.0 * distFrac) - 1.0);
            }
        }
    }

    static final ThreadLocal<MutableBlockPos> TL_POS = ThreadLocal.withInitial(MutableBlockPos::new);
    static final ThreadLocal<LocalAgg> TL_LOCAL_AGG = ThreadLocal.withInitial(LocalAgg::new);
    static final TLPool<IntDoubleAccumulator> ACC_POOL = new TLPool<>(IntDoubleAccumulator::new, IntDoubleAccumulator::clear, 64);
    static final ThreadLocal<LongOpenHashSet> TL_EDGES = ThreadLocal.withInitial(() -> new LongOpenHashSet(64));
    static final TLPool<LongArrayList> LONG_LIST_POOL = new TLPool<>(() -> new LongArrayList(64), LongArrayList::clear, 8);
    static final TLPool<Long2IntOpenHashMap> LONG2INT_POOL = new TLPool<>(Long2IntOpenHashMap::new, Long2IntOpenHashMap::clear, 4);
    static final TLPool<IntArrayList> INT_LIST_POOL = new TLPool<>(() -> new IntArrayList(64), IntArrayList::clear, 16);
    static final TLPool<Long2ObjectOpenHashMap<IBlockState>> LONG2OBJECT_POOL = new TLPool<>(Long2ObjectOpenHashMap::new, Long2ObjectOpenHashMap::clear, 4);
    final WorldServer worldServer;
    final double explosionX, explosionY, explosionZ;
    final double invRadius, invRayIndexScale;
    final int originX, originY, originZ;
    final int radius;
    final int strength;
    int algorithm;
    int waterLevel;
    boolean ignoreWater;
    int rayCount;
    volatile UUID detonator;
    final ConcurrentHashMap<Long, AtomicLongBitSet> destructionMap;
    final ConcurrentHashMap<Long, ChunkAgg> aggMap;
    final ConcurrentHashMap<Long, ConcurrentLinkedQueue<Integer>> waitingRoom;
    final ConcurrentHashMap<Long, ConcurrentLinkedQueue<ResumeItem>> postLoadQueues;
    final ConcurrentLinkedQueue<Long> chunkLoadQueue;
    final Long2IntOpenHashMap sectionMaskByChunk;
    final PriorityBlockingQueue<PendingCarve> pendingCarves;
    int[] rayOrder;
    volatile long[] pausedRays;
    final ConcurrentHashMap.KeySetView<Long, Boolean> populatedChunks = ConcurrentHashMap.newKeySet();
    final ConcurrentHashMap.KeySetView<Long, Boolean> pendingLoads = ConcurrentHashMap.newKeySet();
    final ConcurrentHashMap<Long, List<PendingCarve>> carveWaiting = new ConcurrentHashMap<>();
    volatile boolean awaitingFlush;
    volatile Throwable failure;
    int jobDimension = Integer.MIN_VALUE;
    private boolean raysStarted;
    private int phase0Expected, phase0Loaded, phase0StallTicks;
    volatile int mapAcquired, consolidationStarted, finishQueued, pendingRays, activeWorkerTasks,
            collectFinished, consolidationFinished, destroyFinished, poolAcquired, jobRegistered, cancelCleanup;
    volatile boolean cancelling;
    volatile boolean isContained = true;
    int carveWaitTicks;
    ForkJoinPool pool;
    ConcurrentHashMap<Long, Chunk> mirror;

    public ExplosionNukeRayParallelized(World worldIn, double x, double y, double z, int strength, int radius, int algorithm, boolean ignoreWater) {
        this(worldIn, x, y, z, strength, radius);
        this.algorithm = algorithm;
        this.ignoreWater = ignoreWater;
        initializeAndStartWorkers();
    }

    public ExplosionNukeRayParallelized(World worldIn, double x, double y, double z, int strength, int radius) {
        this.worldServer = (WorldServer) worldIn;
        explosionX = x;
        explosionY = y;
        explosionZ = z;
        originX = (int) Math.floor(x);
        originY = (int) Math.floor(y);
        originZ = (int) Math.floor(z);
        this.strength = strength;
        this.radius = radius;
        invRadius = radius > 0 ? 1.0 / radius : 0.0;

        this.waterLevel = EntityFalloutRain.getInt(CompatibilityConfig.fillCraterWithWater.get(worldServer.provider.getDimension()));
        if (this.waterLevel == 0) {
            this.waterLevel = worldServer.getSeaLevel();
        } else if (this.waterLevel < 0 && this.waterLevel > -worldServer.getSeaLevel()) {
            this.waterLevel = worldServer.getSeaLevel() - this.waterLevel;
        }

        if (!CompatibilityConfig.isWarDim(worldServer)) {
            U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
            U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
            U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
            isContained = true;
            chunkLoadQueue = new ConcurrentLinkedQueue<>();
            pendingCarves = new PriorityBlockingQueue<>(16, this::compareChunk);
            destructionMap = new ConcurrentHashMap<>(16);
            aggMap = new ConcurrentHashMap<>(16);
            waitingRoom = new ConcurrentHashMap<>(16);
            postLoadQueues = new ConcurrentHashMap<>(16);
            sectionMaskByChunk = new Long2IntOpenHashMap(0);
            sectionMaskByChunk.defaultReturnValue(0);
            invRayIndexScale = 0.0;
            rayCount = 0;
            pendingRays = 0;
            return;
        }

        rayCount = Math.max(0, (int) (2.5 * Math.PI * strength * strength * RESOLUTION_FACTOR));
        invRayIndexScale = rayCount > 1 ? 1.0 / (rayCount - 1) : 0.0;
        pendingRays = rayCount;

        int estimatedChunkCount = Math.max(16, count(false));
        int chunkCap = capFor(estimatedChunkCount * 2);
        int subChunkCap = capFor(Math.max(16, count(true)));

        destructionMap = new ConcurrentHashMap<>(chunkCap);
        aggMap = new ConcurrentHashMap<>(chunkCap);
        waitingRoom = new ConcurrentHashMap<>(subChunkCap);
        postLoadQueues = new ConcurrentHashMap<>(subChunkCap);
        sectionMaskByChunk = new Long2IntOpenHashMap(estimatedChunkCount);
        sectionMaskByChunk.defaultReturnValue(0);
        chunkLoadQueue = new ConcurrentLinkedQueue<>();
        pendingCarves = new PriorityBlockingQueue<>(chunkCap, this::compareChunk);
    }

    @Override
    public void update(int processTimeMs) {
        if (failure != null) {
            cancel();
            return;
        }
        if (destroyFinished != 0) return;
        if (awaitingFlush) {
            if (!flushTouchedChunks()) return;
            awaitingFlush = false;
            U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
            return;
        }
        long deadline = System.nanoTime() + TimeUnit.MILLISECONDS.toNanos(processTimeMs);
        while (System.nanoTime() < deadline) {
            boolean loaded = loadOneMissingChunk();
            PendingCarve job = pendingCarves.poll();
            if (job != null) {
                applyCarveJobOnMain(job);
            }
            if (!loaded && job == null) break;
        }
        if (!raysStarted) {
            boolean drained = chunkLoadQueue.isEmpty() && phase0Loaded >= phase0Expected;
            if (!drained && chunkLoadQueue.isEmpty()) {
                phase0StallTicks++;
            } else {
                phase0StallTicks = 0;
            }
            if (drained || phase0StallTicks > 200) {
                raysStarted = true;
                ForkJoinPool tracerPool = pool;
                if (tracerPool == null || tracerPool.isShutdown()) {
                    U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
                } else {
                    int minRayGrain = (rayOrder != null) ? 4096 : 512;
                    tracerPool.submit(new RayTracerTask(0, rayCount, computeTaskGrain(rayCount, minRayGrain)));
                }
            }
        }
        if (!carveWaiting.isEmpty() && chunkLoadQueue.isEmpty() && activeWorkerTasks == 0) {
            if (++carveWaitTicks > 200) {
                for (List<PendingCarve> held : carveWaiting.values()) pendingCarves.addAll(held);
                carveWaiting.clear();
            }
        } else {
            carveWaitTicks = 0;
        }
        maybeFinish();
    }

    @Override
    public boolean isComplete() {
        return failure == null && collectFinished != 0 && consolidationFinished != 0 && destroyFinished != 0;
    }

    @Override
    public boolean hasFailed() {
        return failure != null;
    }

    @Override
    public boolean isContained() {
        return isContained;
    }

    @Override
    public void cancel() {
        cancelling = true;
        U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
        U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
        U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
        if (activeWorkerTasks == 0) {
            finishCancellation();
        } else {
            worldServer.addScheduledTask(this::pollCancelCleanup);
        }
    }

    @Override
    public void cancelJob() {
        cancel();
    }

    @Override
    public void setDetonator(UUID detonator) {
        this.detonator = detonator;
    }

    synchronized void fail(Throwable cause) {
        if (failure != null) return;
        failure = cause;
        MainRegistry.logger.error("Nuclear terrain work failed at {}, {}, {}", originX, originY, originZ, cause);
    }

    @ServerThread
    void pollCancelCleanup() {
        if (activeWorkerTasks == 0) {
            finishCancellation();
        } else {
            worldServer.addScheduledTask(this::pollCancelCleanup);
        }
    }

    void initializeAndStartWorkers() {
        if (rayCount <= 0) {
            U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
            U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
            return;
        }
        if (U.compareAndSetInt(this, OFF_POOL_ACQUIRED, 0, 1)) {
            pool = BombForkJoinPool.acquire();
        }
        registerJobIfNeeded();
        buildRayOrder();
        mirror = ChunkUtil.acquireMirrorMap(worldServer);
        U.putIntRelease(this, OFF_MAP_ACQUIRED, 1);
        mirror.clear();
        int cr = (radius + 15) >> 4;
        int cx0 = originX >> 4, cz0 = originZ >> 4;
        double rangeLimit = radius + 12.0;
        double rangeLimitSq = rangeLimit * rangeLimit;
        phase0Expected = 0;
        phase0Loaded = 0;
        phase0StallTicks = 0;
        raysStarted = false;
        for (int cx = cx0 - cr; cx <= cx0 + cr; cx++) {
            for (int cz = cz0 - cr; cz <= cz0 + cr; cz++) {
                double dx = (cx << 4) + 8 - originX;
                double dz = (cz << 4) + 8 - originZ;
                if (dx * dx + dz * dz > rangeLimitSq) continue;
                long cp = ChunkPos.asLong(cx, cz);
                phase0Expected++;
                Chunk loadedChunk = worldServer.getChunkProvider().loadedChunks.get(cp);
                if (loadedChunk != null && loadedChunk.isTerrainPopulated()) {
                    populatedChunks.add(cp);
                    mirror.put(cp, loadedChunk);
                    phase0Loaded++;
                    continue;
                }
                if (pendingLoads.add(cp)) chunkLoadQueue.offer(cp);
            }
        }
    }

    private void buildRayOrder() {
        int n = rayCount;
        if (n <= 0) return;
        if (n < 200_000) return;
        long[] packed = new long[n];
        ForkJoinPool p = pool;
        int par = (p == null) ? 1 : p.getParallelism();
        boolean doParallelFill = par > 1 && n >= 512_000;
        if (doParallelFill) {
            int grain = computeTaskGrain(n, 16384);
            p.invoke(new BuildPackedTask(packed, 0, n, grain, invRayIndexScale));
        } else {
            for (int i = 0; i < n; i++) {
                double y = 1.0 - (i * invRayIndexScale) * 2.0;
                double r = Math.sqrt(Math.max(0.0, 1.0 - y * y));
                double t = GOLDEN_ANGLE * i;
                double x = Math.cos(t) * r;
                double z = Math.sin(t) * r;
                int key = rayKeyCubeMorton(x, y, z);
                packed[i] = (((long) key) << 32) | (i & 0xFFFF_FFFFL);
            }
        }
        if (n >= 1_000_000) {
            LongArrays.parallelRadixSort(packed);
        } else {
            LongArrays.radixSort(packed);
        }

        int[] order = new int[n];
        if (par > 1 && n >= 1_000_000) {
            int grain = computeTaskGrain(n, 32768);
            p.invoke(new UnpackOrderTask(packed, order, 0, n, grain));
        } else {
            for (int i = 0; i < n; i++) order[i] = (int) packed[i];
        }
        rayOrder = order;
    }

    void registerJobIfNeeded() {
        if (U.compareAndSetInt(this, OFF_JOB_REGISTERED, 0, 1)) {
            int dim = worldServer == null ? Integer.MIN_VALUE : worldServer.provider.getDimension();
            jobDimension = dim;
            BombForkJoinPool.register(dim, this);
        }
    }

    void unregisterJobIfNeeded() {
        if (U.getAndSetInt(this, OFF_JOB_REGISTERED, 0) != 0) {
            int dim = jobDimension;
            jobDimension = Integer.MIN_VALUE;
            if (dim != Integer.MIN_VALUE) BombForkJoinPool.unregister(dim, this);
        }
    }

    void releasePoolIfHeld() {
        unregisterJobIfNeeded();
        if (U.getAndSetInt(this, OFF_POOL_ACQUIRED, 0) != 0) {
            BombForkJoinPool.release();
            pool = null;
        }
    }

    @ServerThread
    boolean loadOneMissingChunk() {
        Long ck = chunkLoadQueue.poll();
        if (ck == null) return false;
        pendingLoads.remove(ck);
        processChunkLoadRequest(ck.longValue());
        return true;
    }

    @ServerThread
    void processChunkLoadRequest(long chunkPos) {
        int cx = Library.getChunkPosX(chunkPos);
        int cz = Library.getChunkPosZ(chunkPos);
        Chunk chunk = worldServer.getChunk(cx, cz);
        // Loaded in isolation (E/S/SE missing): decoration was skipped, so populate now, on intact terrain, before carving.
        if (chunk != null && !chunk.isTerrainPopulated()) {
            ChunkProviderServer provider = (ChunkProviderServer) worldServer.getChunkProvider();
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    if (dx != 0 || dz != 0) worldServer.getChunk(cx + dx, cz + dz);
                }
            }
            chunk.populate(provider, provider.chunkGenerator);
            chunk.generateSkylightMap();
        }
        if (chunk != null && chunk.isTerrainPopulated()) {
            if (populatedChunks.add(chunkPos)) phase0Loaded++;
            List<PendingCarve> waitingCarves = carveWaiting.remove(chunkPos);
            if (waitingCarves != null) {
                for (PendingCarve waitingJob : waitingCarves) pendingCarves.offer(waitingJob);
            }
            int cr = (radius + 15) >> 4;
            int minCX = (originX >> 4) - cr - 1;
            int maxCX = (originX >> 4) + cr + 1;
            int minCZ = (originZ >> 4) - cr - 1;
            int maxCZ = (originZ >> 4) + cr + 1;
            if (raysStarted) {
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dz = -1; dz <= 1; dz++) {
                        if (dx == 0 && dz == 0) continue;
                        int nx = cx + dx;
                        int nz = cz + dz;
                        if (nx < minCX || nx > maxCX || nz < minCZ || nz > maxCZ) continue;
                        long n = ChunkPos.asLong(nx, nz);
                        if (!populatedChunks.contains(n) && pendingLoads.add(n)) {
                            chunkLoadQueue.offer(n);
                        }
                    }
                }
            }
        }
        if (chunk != null) mirror.put(chunkPos, chunk);
        ForkJoinPool p = pool;
        boolean poolActive = (p != null && !p.isShutdown());
        ConcurrentLinkedQueue<Integer> waiters = waitingRoom.remove(chunkPos);
        if (waiters != null) {
            if (!poolActive) {
                ensureCollectorRegistered(chunkPos, waiters);
                chunkLoadQueue.offer(chunkPos);
            } else {
                IntArrayList batch = new IntArrayList();
                Integer v;
                while ((v = waiters.poll()) != null) batch.add(v.intValue());
                if (!batch.isEmpty()) {
                    p.submit(new ResumeBatchTask(batch, 0, batch.size(), computeTaskGrain(batch.size(), rayOrder == null ? 512 : 4096)));
                }
            }
        }
        ConcurrentLinkedQueue<ResumeItem> q = postLoadQueues.remove(chunkPos);
        if (q != null) {
            if (!poolActive) {
                ConcurrentLinkedQueue<ResumeItem> prev = postLoadQueues.putIfAbsent(chunkPos, q);
                if (prev != null && prev != q) {
                    ResumeItem it;
                    while ((it = q.poll()) != null) prev.offer(it);
                }
                chunkLoadQueue.offer(chunkPos);
                return;
            }

            ArrayList<CarveSubTask> rebuilt = new ArrayList<>(8);
            ExtendedBlockStorage[] ebs = chunk.getBlockStorageArray();
            ResumeItem item;
            while ((item = q.poll()) != null) {
                switch (item.kind) {
                    case ResumeItem.CARVE:
                        rebuilt.add(new CarveSubTask(item.subY, item.mask));
                        break;
                    case ResumeItem.APPLY_MASKS: {
                        Int2ObjectOpenHashMap<BitMask> masks = item.masks;
                        U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, 1);
                        p.submit(() -> {
                            try {
                                prepareAndEnqueue(chunkPos, masks);
                            } finally {
                                workerFinished();
                            }
                        });
                        break;
                    }
                    case ResumeItem.APPLY_AGG: {
                        ChunkAgg agg = item.agg;
                        U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, 1);
                        p.submit(() -> {
                            try {
                                if (destroyFinished == 0) prepareAndEnqueue(chunkPos, buildMasksFromAgg(agg, ebs));
                            } finally {
                                agg.clear();
                                workerFinished();
                            }
                        });
                        break;
                    }
                }
            }
            if (!rebuilt.isEmpty()) pendingCarves.offer(new PendingCarve(chunkPos, rebuilt));
        }
    }

    void handleMissingChunk(LocalAgg agg, long chunkPos, int dirIndex) {
        ConcurrentLinkedQueue<Integer> group = waitingRoom.get(chunkPos);
        if (group == null) {
            ConcurrentLinkedQueue<Integer> created = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<Integer> prev = waitingRoom.putIfAbsent(chunkPos, created);
            group = (prev != null) ? prev : created;
            if (prev == null) {
                chunkLoadQueue.offer(chunkPos);
                group.offer(dirIndex);
                return;
            }
        }
        agg.deferMissing(chunkPos, dirIndex);
    }

    void flushDeferredMissing(LocalAgg agg) {
        if (!agg.hasDeferredMissing()) return;
        Long2ObjectOpenHashMap<IntArrayList> map = agg.missingChunks();
        ObjectIterator<Long2ObjectMap.Entry<IntArrayList>> iterator = map.long2ObjectEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Long2ObjectMap.Entry<IntArrayList> entry = iterator.next();
            IntArrayList list = entry.getValue();
            if (list == null || list.isEmpty()) continue;
            long chunkPos = entry.getLongKey();
            ConcurrentLinkedQueue<Integer> collector = waitingRoom.get(chunkPos);
            boolean amFirst = false;
            if (collector == null) {
                ConcurrentLinkedQueue<Integer> created = new ConcurrentLinkedQueue<>();
                ConcurrentLinkedQueue<Integer> prev = waitingRoom.putIfAbsent(chunkPos, created);
                if (prev == null) {
                    collector = created;
                    amFirst = true;
                } else {
                    collector = prev;
                }
            }
            for (int i = 0; i < list.size(); i++) collector.offer(list.getInt(i));
            boolean requeued = ensureCollectorRegistered(chunkPos, collector);
            if (amFirst || requeued) {
                chunkLoadQueue.offer(chunkPos);
            }
            list.clear();
            INT_LIST_POOL.recycle(list);
        }
        map.clear();
    }

    boolean ensureCollectorRegistered(long chunkPos, ConcurrentLinkedQueue<Integer> collector) {
        while (true) {
            ConcurrentLinkedQueue<Integer> current = waitingRoom.get(chunkPos);
            if (current == collector) return false;
            if (current == null) {
                if (waitingRoom.putIfAbsent(chunkPos, collector) == null) return true;
                continue;
            }
            Integer drained;
            while ((drained = collector.poll()) != null) current.offer(drained);
            collector = current;
        }
    }

    void enqueueForMissingChunk(long chunkPos, ResumeItem item) {
        if (destroyFinished != 0) {
            discardResumeItem(item);
            return;
        }
        ConcurrentLinkedQueue<ResumeItem> q = postLoadQueues.get(chunkPos);
        if (q == null) {
            ConcurrentLinkedQueue<ResumeItem> created = new ConcurrentLinkedQueue<>();
            ConcurrentLinkedQueue<ResumeItem> prev = postLoadQueues.putIfAbsent(chunkPos, created);
            q = prev == null ? created : prev;
            if (prev == null) {
                chunkLoadQueue.offer(chunkPos);
            }
        }
        q.offer(item);
    }

    static void discardResumeItem(ResumeItem item) {
        switch (item.kind) {
            case ResumeItem.CARVE:
                if (item.mask != null) item.mask.free();
                break;
            case ResumeItem.APPLY_MASKS:
                if (item.masks != null) {
                    for (BitMask mask : item.masks.values()) mask.free();
                    item.masks.clear();
                }
                break;
            case ResumeItem.APPLY_AGG:
                if (item.agg != null) item.agg.clear();
                break;
        }
    }

    long[] pausedRays() {
        long[] states = pausedRays;
        if (states != null) return states;
        synchronized (this) {
            if (pausedRays == null) pausedRays = new long[Math.multiplyExact(rayCount, PAUSED_RAY_WORDS)];
            return pausedRays;
        }
    }

    void pauseRay(int index, int x, int y, int z, double energy, double position,
                  double maxX, double maxY, double maxZ) {
        long[] states = pausedRays();
        int offset = index * PAUSED_RAY_WORDS;
        states[offset] = Double.doubleToRawLongBits(energy);
        states[offset + 1] = Library.blockPosToLong(x, y, z);
        states[offset + 2] = Double.doubleToRawLongBits(position);
        states[offset + 3] = Double.doubleToRawLongBits(maxX);
        states[offset + 4] = Double.doubleToRawLongBits(maxY);
        states[offset + 5] = Double.doubleToRawLongBits(maxZ);
    }

    void prepareAndEnqueue(long cpLong, Int2ObjectOpenHashMap<BitMask> masks) {
        if (masks == null || masks.isEmpty()) return;
        if (destroyFinished != 0) {
            for (BitMask mask : masks.values()) mask.free();
            return;
        }
        List<CarveSubTask> tasks = new ArrayList<>(masks.size());
        ObjectIterator<Int2ObjectMap.Entry<BitMask>> it = masks.int2ObjectEntrySet().fastIterator();
        while (it.hasNext()) {
            Int2ObjectMap.Entry<BitMask> e = it.next();
            tasks.add(new CarveSubTask(e.getIntKey(), e.getValue()));
        }
        pendingCarves.offer(new PendingCarve(cpLong, tasks));
    }

    @ServerThread
    void applyCarveJobOnMain(PendingCarve job) {
        long ck = job.chunkPos;
        boolean inPop = populatedChunks.contains(ck);
        if (!inPop) {
            carveWaiting.computeIfAbsent(ck, k -> new ArrayList<>()).add(job);
            return;
        }
        int cx = Library.getChunkPosX(ck);
        int cz = Library.getChunkPosZ(ck);
        Chunk chunk = worldServer.getChunkProvider().loadedChunks.get(ck);
        if (chunk == null) {
            for (CarveSubTask task : job.tasks) {
                enqueueForMissingChunk(ck, new ResumeItem(task.subY, task.mask));
            }
            return;
        }
        ExtendedBlockStorage[] storages = chunk.getBlockStorageArray();
        Long2ObjectOpenHashMap<IBlockState> modified = LONG2OBJECT_POOL.borrow();
        Long2ObjectOpenHashMap<IBlockState> blockEntities = LONG2OBJECT_POOL.borrow();
        LongArrayList neighborNotifies = LONG_LIST_POOL.borrow();
        Long2IntOpenHashMap neighborMask = LONG2INT_POOL.borrow();
        try {
            modified.clear();
            blockEntities.clear();
            neighborNotifies.clear();
            neighborMask.clear();
            int selfMask = 0;
            MutableBlockPos pos = TL_POS.get();
            LongOpenHashSet edges = TL_EDGES.get();
            for (CarveSubTask task : job.tasks) {
                try {
                    edges.clear();
                    ExtendedBlockStorage carved = ChunkUtil.copyAndCarveLocal(worldServer, cx, cz, task.subY, storages,
                            task.mask, edges, modified, blockEntities);
                    if (carved != null) {
                        storages[task.subY] = carved;
                        selfMask |= 1 << task.subY;
                        ChunkUtil.rebuildHeightMap(chunk, task.subY);
                    }
                    LongIterator edgeIterator = edges.iterator();
                    while (edgeIterator.hasNext()) {
                        long packed = edgeIterator.nextLong();
                        neighborNotifies.add(packed);
                        Library.fromLong(pos, packed);
                        long neighbor = ChunkPos.asLong(pos.getX() >> 4, pos.getZ() >> 4);
                        neighborMask.put(neighbor, neighborMask.get(neighbor) | (1 << (pos.getY() >>> 4)));
                    }
                } finally {
                    task.mask.free();
                }
            }
            if (selfMask != 0 || !blockEntities.isEmpty()) {
                chunkFixup(ck, modified, blockEntities, neighborNotifies, selfMask, neighborMask);
                chunk.markDirty();
            }
        } finally {
            LONG2OBJECT_POOL.recycle(modified);
            LONG2OBJECT_POOL.recycle(blockEntities);
            LONG_LIST_POOL.recycle(neighborNotifies);
            LONG2INT_POOL.recycle(neighborMask);
        }
    }

    void chunkFixup(long cpLong, Long2ObjectOpenHashMap<IBlockState> modified,
                    Long2ObjectOpenHashMap<IBlockState> blockEntities, LongArrayList neighborNotifies, int selfMask,
                    Long2IntOpenHashMap neighborMask) {
        sectionMaskByChunk.put(cpLong, sectionMaskByChunk.get(cpLong) | selfMask);
        ObjectIterator<Long2IntMap.Entry> iterator = neighborMask.long2IntEntrySet().fastIterator();
        while (iterator.hasNext()) {
            Long2IntMap.Entry e = iterator.next();
            long cpk = e.getLongKey();
            int m = e.getIntValue();
            sectionMaskByChunk.put(cpk, sectionMaskByChunk.get(cpk) | m);
        }
        MutableBlockPos p = TL_POS.get();
        ObjectIterator<Long2ObjectMap.Entry<IBlockState>> blockEntityIterator = blockEntities.long2ObjectEntrySet().fastIterator();
        while (blockEntityIterator.hasNext()) {
            Library.fromLong(p, blockEntityIterator.next().getLongKey());
            ChunkUtil.replaceEmptied(worldServer, p, Blocks.AIR.getDefaultState(), 3);
        }
        ObjectIterator<Long2ObjectMap.Entry<IBlockState>> modifiedIterator = modified.long2ObjectEntrySet().fastIterator();
        while (modifiedIterator.hasNext()) {
            Long2ObjectMap.Entry<IBlockState> entry = modifiedIterator.next();
            long lp = entry.getLongKey();
            IBlockState state = entry.getValue();
            Library.fromLong(p, lp);
            try {
                state.getBlock().breakBlock(worldServer, p, state);
            } catch (Throwable e) {
                MainRegistry.logger.error("breakBlock failed during explosion cleanup for {} at {}; the block is already carved out, continuing", state, p, e);
            }
            if (CompatDynamicTrees.isTreePart(state.getBlock())) {
                CompatDynamicTrees.destroyOrphanedNeighbors(worldServer, p.toImmutable());
            }
        }
        for (int i = 0, n = neighborNotifies.size(); i < n; i++) {
            long lp = neighborNotifies.getLong(i);
            Library.fromLong(p, lp);
            // Notify neighbours except water, so a water body does not flow back into the crater.
            for (EnumFacing facing : EnumFacing.values()) {
                BlockPos neighborPos = p.offset(facing);
                if (worldServer.isBlockLoaded(neighborPos)) {
                    IBlockState neighborState = worldServer.getBlockState(neighborPos);
                    Block neighborBlock = neighborState.getBlock();
                    if (neighborBlock != Blocks.WATER && neighborBlock != Blocks.FLOWING_WATER && !hasWaterNeighbor(neighborPos)) {
                        worldServer.neighborChanged(neighborPos, Blocks.AIR, p);
                    }
                }
            }
        }
    }

    boolean hasWaterNeighbor(BlockPos pos) {
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

    boolean traceSingle(int dirIndex, LocalAgg agg) {
        double energy = strength * INITIAL_ENERGY_FACTOR;
        double px = explosionX, py = explosionY, pz = explosionZ;
        int x = originX, y = originY, z = originZ;
        double dirY = 1.0 - (dirIndex * invRayIndexScale) * 2.0;
        double r = Math.sqrt(Math.max(0.0, 1.0 - dirY * dirY));
        double t = GOLDEN_ANGLE * dirIndex;
        double dirX = Math.cos(t) * r;
        double dirZ = Math.sin(t) * r;
        double currentRayPosition = 0.0;
        double absDirX = Math.abs(dirX);
        int stepX = (absDirX < RAY_DIRECTION_EPSILON) ? 0 : (dirX > 0 ? 1 : -1);
        double tDeltaX = (stepX == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirX;

        double absDirY = Math.abs(dirY);
        int stepY = (absDirY < RAY_DIRECTION_EPSILON) ? 0 : (dirY > 0 ? 1 : -1);
        double tDeltaY = (stepY == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirY;

        double absDirZ = Math.abs(dirZ);
        int stepZ = (absDirZ < RAY_DIRECTION_EPSILON) ? 0 : (dirZ > 0 ? 1 : -1);
        double tDeltaZ = (stepZ == 0) ? Double.POSITIVE_INFINITY : 1.0 / absDirZ;

        double minDeltaT = Math.min(tDeltaX, Math.min(tDeltaY, tDeltaZ));
        double radiusLimit = radius - PROCESSING_EPSILON;
        boolean useAggDamage = (algorithm == 2);

        double tMaxX = (stepX == 0) ? Double.POSITIVE_INFINITY : ((stepX > 0 ? (x + 1 - px) : (px - x)) * tDeltaX);
        double tMaxY = (stepY == 0) ? Double.POSITIVE_INFINITY : ((stepY > 0 ? (y + 1 - py) : (py - y)) * tDeltaY);
        double tMaxZ = (stepZ == 0) ? Double.POSITIVE_INFINITY : ((stepZ > 0 ? (z + 1 - pz) : (pz - z)) * tDeltaZ);

        long[] states = pausedRays;
        int stateOffset = dirIndex * PAUSED_RAY_WORDS;
        if (states != null && states[stateOffset] != 0) {
            energy = Double.longBitsToDouble(states[stateOffset]);
            long packed = states[stateOffset + 1];
            x = Library.getBlockPosX(packed);
            y = Library.getBlockPosY(packed);
            z = Library.getBlockPosZ(packed);
            currentRayPosition = Double.longBitsToDouble(states[stateOffset + 2]);
            tMaxX = Double.longBitsToDouble(states[stateOffset + 3]);
            tMaxY = Double.longBitsToDouble(states[stateOffset + 4]);
            tMaxZ = Double.longBitsToDouble(states[stateOffset + 5]);
            states[stateOffset] = 0;
        }

        long cachedCPLong = 0L;
        ExtendedBlockStorage[] storages = null;
        int lastCX = Integer.MIN_VALUE, lastCZ = Integer.MIN_VALUE;
        int chunkMinX = 0, chunkMaxX = 0, chunkMinZ = 0, chunkMaxZ = 0;
        int emptySubMask = 0;
        long bitsetCPLong = Long.MIN_VALUE;
        AtomicLongBitSet currentBits = null;
        int loopCount = 0;

        try {
            if (energy <= 0) return true;
            while (energy > 0) {
                if ((loopCount++ & 0x3FF) == 0) {
                    if (destroyFinished != 0 || y < 0 || y >= WORLD_HEIGHT || Thread.currentThread().isInterrupted())
                        break;
                    if (currentRayPosition >= radiusLimit) break;
                } else {
                    if (currentRayPosition >= radiusLimit) break;
                    if (y < 0 || y >= WORLD_HEIGHT) break;
                }
                int cx = x >> 4;
                int cz = z >> 4;

                if (cx != lastCX || cz != lastCZ) {
                    cachedCPLong = ChunkPos.asLong(cx, cz);
                    Chunk entryChunk = ChunkUtil.getLoadedChunk(mirror, cachedCPLong);
                    storages = (entryChunk != null && entryChunk.isTerrainPopulated())
                            ? entryChunk.getBlockStorageArray() : null;
                    if (storages == null) {
                        pauseRay(dirIndex, x, y, z, energy, currentRayPosition, tMaxX, tMaxY, tMaxZ);
                        handleMissingChunk(agg, cachedCPLong, dirIndex);
                        return false;
                    }
                    lastCX = cx;
                    lastCZ = cz;
                    chunkMinX = cx << 4;
                    chunkMaxX = chunkMinX + 16;
                    chunkMinZ = cz << 4;
                    chunkMaxZ = chunkMinZ + 16;
                    int mask = 0;
                    for (int i = 0; i < storages.length; i++) {
                        ExtendedBlockStorage s = storages[i];
                        if (s == Chunk.NULL_BLOCK_STORAGE || s.isEmpty()) {
                            mask |= (1 << i);
                        }
                    }
                    emptySubMask = mask;
                    if (!useAggDamage) {
                        if (bitsetCPLong != cachedCPLong) {
                            AtomicLongBitSet bs = destructionMap.get(cachedCPLong);
                            if (bs == null) {
                                AtomicLongBitSet created = new AtomicLongBitSet(BITSET_SIZE);
                                AtomicLongBitSet prev = destructionMap.putIfAbsent(cachedCPLong, created);
                                bs = (prev != null) ? prev : created;
                            }
                            currentBits = bs;
                            bitsetCPLong = cachedCPLong;
                        }
                    }
                }

                int subY = y >> 4;
                boolean subIsEmpty = ((emptySubMask >>> subY) & 1) != 0;
                if (subIsEmpty) {
                    double minY = (subY << 4);
                    double maxY = minY + 16.0;

                    double tExitAbs = Double.POSITIVE_INFINITY;
                    if (stepX > 0) tExitAbs = Math.min(tExitAbs, (chunkMaxX - px) * tDeltaX);
                    else if (stepX < 0) tExitAbs = Math.min(tExitAbs, (px - chunkMinX) * tDeltaX);
                    if (stepY > 0) tExitAbs = Math.min(tExitAbs, (maxY - py) * tDeltaY);
                    else if (stepY < 0) tExitAbs = Math.min(tExitAbs, (py - minY) * tDeltaY);
                    if (stepZ > 0) tExitAbs = Math.min(tExitAbs, (chunkMaxZ - pz) * tDeltaZ);
                    else if (stepZ < 0) tExitAbs = Math.min(tExitAbs, (pz - chunkMinZ) * tDeltaZ);

                    double deltaT = Math.min(tExitAbs, radius) - currentRayPosition;
                    if (deltaT <= PROCESSING_EPSILON) deltaT = minDeltaT;
                    currentRayPosition += deltaT;
                    if (currentRayPosition >= radiusLimit) break;

                    final double bias = 1e-9;
                    x = (int) Math.floor(px + dirX * (currentRayPosition - bias));
                    y = (int) Math.floor(py + dirY * (currentRayPosition - bias));
                    z = (int) Math.floor(pz + dirZ * (currentRayPosition - bias));

                    tMaxX = (stepX == 0) ? Double.POSITIVE_INFINITY : ((stepX > 0 ? (x + 1 - px) : (px - x)) * tDeltaX);
                    tMaxY = (stepY == 0) ? Double.POSITIVE_INFINITY : ((stepY > 0 ? (y + 1 - py) : (py - y)) * tDeltaY);
                    tMaxZ = (stepZ == 0) ? Double.POSITIVE_INFINITY : ((stepZ > 0 ? (z + 1 - pz) : (pz - z)) * tDeltaZ);
                    continue;
                }
                ExtendedBlockStorage storage = storages[subY];

                int xLocal = x & 0xF, yLocal = y & 0xF, zLocal = z & 0xF;
                IBlockState state = storage.get(xLocal, yLocal, zLocal);

                double tExitVoxel = Math.min(tMaxX, Math.min(tMaxY, tMaxZ));
                double segLen = tExitVoxel - currentRayPosition;
                double remaining = radius - currentRayPosition;
                if (remaining <= PROCESSING_EPSILON) break;
                boolean clipAtRadius = segLen > remaining - 1e-12;
                if (clipAtRadius) segLen = remaining;

                if (state.getBlock() != Blocks.AIR) {
                    if (segLen <= PROCESSING_EPSILON) segLen = 1.0;   // voxel corner: treat the zero-length pass as a full block
                    float resistance = getNukeResistance(state);
                    if (resistance >= NUKE_RESISTANCE_CUTOFF) {
                        energy = 0;
                    } else {
                        double distFrac = Math.max(currentRayPosition, MIN_EFFECTIVE_DIST_FOR_ENERGY_CALC) * invRadius;
                        double energyLoss = getEnergyLossFactor(resistance, distFrac) * segLen;
                        energy -= energyLoss;

                        Block b = state.getBlock();
                        boolean preserveWater = ignoreWater && y < waterLevel && (b == Blocks.WATER || b == Blocks.FLOWING_WATER);

                        if (!preserveWater) {
                            if (useAggDamage) {
                                int bitIndex = ((WORLD_HEIGHT - 1 - y) << 8) | ((x & 0xF) << 4) | (z & 0xF);
                                double damageInc = Math.max(DAMAGE_PER_BLOCK * segLen, energyLoss) * INITIAL_ENERGY_FACTOR;
                                agg.recordHit(cachedCPLong, bitIndex, damageInc, segLen);
                            } else if (energy > 0) {
                                int bitIndex = ((WORLD_HEIGHT - 1 - y) << 8) | ((x & 0xF) << 4) | (z & 0xF);
                                currentBits.set(bitIndex);
                            }
                        }
                    }
                }

                currentRayPosition = tExitVoxel;
                if (energy <= 0.0 || clipAtRadius) break;
                if (tMaxX < tMaxY) {
                    if (tMaxX < tMaxZ) {
                        x += stepX;
                        tMaxX += tDeltaX;
                    } else {
                        z += stepZ;
                        tMaxZ += tDeltaZ;
                    }
                } else {
                    if (tMaxY < tMaxZ) {
                        y += stepY;
                        tMaxY += tDeltaY;
                    } else {
                        z += stepZ;
                        tMaxZ += tDeltaZ;
                    }
                }
            }

            if (energy > 0) isContained = false;
            return true;
        } catch (Exception e) {
            fail(new IllegalStateException("Ray " + dirIndex + " failed", e));
            return false;
        }
    }

    void mergeLocalAgg(LocalAgg agg) {
        if (algorithm != 2) return;
        ObjectIterator<Long2ObjectMap.Entry<IntDoubleAccumulator>> damageIter = agg.localDamage.long2ObjectEntrySet().fastIterator();
        while (damageIter.hasNext()) {
            Long2ObjectMap.Entry<IntDoubleAccumulator> entry = damageIter.next();
            long ck = entry.getLongKey();
            IntDoubleAccumulator accumulator = entry.getValue();
            aggMap.computeIfAbsent(ck, k -> new ChunkAgg()).merge(accumulator, true);
        }
        ObjectIterator<Long2ObjectMap.Entry<IntDoubleAccumulator>> lenIter = agg.localLen.long2ObjectEntrySet().fastIterator();
        while (lenIter.hasNext()) {
            Long2ObjectMap.Entry<IntDoubleAccumulator> entry = lenIter.next();
            long ck = entry.getLongKey();
            IntDoubleAccumulator accumulator = entry.getValue();
            aggMap.computeIfAbsent(ck, k -> new ChunkAgg()).merge(accumulator, false);
        }
        agg.localDamage.clear();
        agg.localLen.clear();
        agg.clear();
    }

    void maybeCompleteRays() {
        if (pendingRays != 0 || activeWorkerTasks != 0 || destroyFinished != 0 || failure != null) return;
        if (!U.compareAndSetInt(this, OFF_COLLECT_FINISHED, 0, 1)) return;
        rayOrder = null;
        pausedRays = null;
        if (U.compareAndSetInt(this, OFF_CONSOLIDATION_STARTED, 0, 1)) {
            ForkJoinPool p = pool;
            if (p != null && !p.isShutdown()) {
                p.submit(this::runConsolidation);
            } else {
                runConsolidation();
            }
        }
    }

    void runConsolidation() {
        U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, 1);
        try {
            if (destroyFinished != 0 || failure != null) return;
            if (algorithm == 2) {
                Long[] keys = aggMap.keySet().toArray(new Long[0]);
                if (keys.length != 0) {
                    int thresh = computeKeyTaskGrain(keys.length);
                    new ConsolidateAggTask(keys, 0, keys.length, thresh).invoke();
                }
                aggMap.clear();
            } else {
                Long[] keys = destructionMap.keySet().toArray(new Long[0]);
                if (keys.length != 0) {
                    int thresh = computeKeyTaskGrain(keys.length);
                    new ConsolidateMaskTask(keys, 0, keys.length, thresh).invoke();
                }
            }
            U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
        } catch (Exception e) {
            fail(e);
        } finally {
            workerFinished();
        }
    }

    static BitMask getOrCreateSubMask(Int2ObjectOpenHashMap<BitMask> map, int subY) {
        BitMask mask = map.get(subY);
        if (mask == null) {
            mask = new AtomicLongBitSet(SUB_MASK_SIZE);
            map.put(subY, mask);
        }
        return mask;
    }

    static Int2ObjectOpenHashMap<BitMask> splitBySubchunk(AtomicLongBitSet chunkMask) {
        Int2ObjectOpenHashMap<BitMask> m = new Int2ObjectOpenHashMap<>();
        int bit = chunkMask.nextSetBit(0);
        while (bit >= 0) {
            int yGlobal = WORLD_HEIGHT - 1 - (bit >>> 8);
            int subY = yGlobal >>> 4;
            int xLocal = (bit >>> 4) & 0xF;
            int zLocal = bit & 0xF;
            int yLocal = yGlobal & 0xF;
            int localIndex = Library.packLocal(xLocal, yLocal, zLocal);
            getOrCreateSubMask(m, subY).set(localIndex);
            bit = chunkMask.nextSetBit(bit + 1);
        }
        return m;
    }

    static Int2ObjectOpenHashMap<BitMask> buildMasksFromAgg(ChunkAgg agg, ExtendedBlockStorage[] storages) {
        Int2ObjectOpenHashMap<BitMask> subChunkBitSets = new Int2ObjectOpenHashMap<>();
        Int2DoubleOpenHashMap dmg = agg.damage;
        Int2DoubleOpenHashMap len = agg.passLen;

        if (!dmg.isEmpty()) {
            ObjectIterator<Int2DoubleMap.Entry> iterator = dmg.int2DoubleEntrySet().fastIterator();
            while (iterator.hasNext()) {
                Int2DoubleMap.Entry e = iterator.next();
                int bitIndex = e.getIntKey();
                int yGlobal = WORLD_HEIGHT - 1 - (bitIndex >>> 8);
                int subY = yGlobal >>> 4;
                int xLocal = (bitIndex >>> 4) & 0xF;
                int zLocal = bitIndex & 0xF;
                int yLocal = yGlobal & 0xF;
                int localIndex = Library.packLocal(xLocal, yLocal, zLocal);
                if (shouldDestroy(bitIndex, storages, e.getDoubleValue(), len.get(bitIndex))) {
                    getOrCreateSubMask(subChunkBitSets, subY).set(localIndex);
                }
            }
        }

        if (!len.isEmpty()) {
            ObjectIterator<Int2DoubleMap.Entry> iterator = len.int2DoubleEntrySet().fastIterator();
            while (iterator.hasNext()) {
                Int2DoubleMap.Entry e = iterator.next();
                int bitIndex = e.getIntKey();
                if (dmg.containsKey(bitIndex)) continue;
                int yGlobal = WORLD_HEIGHT - 1 - (bitIndex >>> 8);
                int subY = yGlobal >>> 4;
                int xLocal = (bitIndex >>> 4) & 0xF;
                int zLocal = bitIndex & 0xF;
                int yLocal = yGlobal & 0xF;
                int localIndex = Library.packLocal(xLocal, yLocal, zLocal);
                if (shouldDestroy(bitIndex, storages, 0.0, e.getDoubleValue())) {
                    getOrCreateSubMask(subChunkBitSets, subY).set(localIndex);
                }
            }
        }
        return subChunkBitSets;
    }

    static boolean shouldDestroy(int bitIndex, ExtendedBlockStorage[] storages, double accumulatedDamage, double passLen) {
        int yGlobal = WORLD_HEIGHT - 1 - (bitIndex >>> 8);
        int subY = yGlobal >>> 4;
        ExtendedBlockStorage s = storages[subY];
        if (s == Chunk.NULL_BLOCK_STORAGE || s.isEmpty()) return false;
        int xLocal = (bitIndex >>> 4) & 0xF;
        int zLocal = bitIndex & 0xF;
        int yLocal = yGlobal & 0xF;
        IBlockState st = s.get(xLocal, yLocal, zLocal);
        if (st.getBlock() == Blocks.AIR) return false;
        float resistance = getNukeResistance(st);
        if (accumulatedDamage >= (resistance * DAMAGE_THRESHOLD_MULT)) return true;
        return resistance <= LOW_R_BOUND && passLen >= LOW_R_PASS_LENGTH_BREAK;
    }

    int count(boolean perSubchunk) {
        int cr = (radius + 15) >> 4;
        int minCX = (originX >> 4) - cr;
        int maxCX = (originX >> 4) + cr;
        int minCZ = (originZ >> 4) - cr;
        int maxCZ = (originZ >> 4) + cr;
        int minSubY = Math.max(0, (originY - radius) >> 4);
        int maxSubY = Math.min(SUBCHUNK_PER_CHUNK - 1, (originY + radius) >> 4);
        if (minSubY > maxSubY) return 0;
        double R2 = (radius + 14) * (radius + 14);
        int total = 0;
        for (int cx = minCX; cx <= maxCX; cx++) {
            double dx = ((cx << 4) + 8) - explosionX;
            double dx2 = dx * dx;
            for (int cz = minCZ; cz <= maxCZ; cz++) {
                double dz = ((cz << 4) + 8) - explosionZ;
                double base = dx2 + dz * dz;
                if (base > R2) continue;
                double ry = Math.sqrt(R2 - base);
                int firstY = (int) Math.ceil((explosionY - ry - 8.0) / 16.0);
                int lastY = (int) Math.floor((explosionY + ry - 8.0) / 16.0);
                if (firstY < minSubY) firstY = minSubY;
                if (lastY > maxSubY) lastY = maxSubY;
                if (perSubchunk) {
                    int countY = lastY - firstY + 1;
                    if (countY > 0) total += countY;
                } else {
                    if (lastY >= firstY) total++;
                }
            }
        }
        return total;
    }

    private boolean flushTouchedChunks() {
        for (Long2IntMap.Entry e : sectionMaskByChunk.long2IntEntrySet()) {
            long cp = e.getLongKey();
            if (ChunkUtil.hasPendingLight(worldServer, cp)) return false;
            int cx = Library.getChunkPosX(cp), cz = Library.getChunkPosZ(cp);
            for (int dz = -1; dz <= 1; dz++) {
                for (int dx = -1; dx <= 1; dx++) {
                    if (ChunkUtil.hasPendingLight(worldServer, ChunkPos.asLong(cx + dx, cz + dz))) return false;
                }
            }
        }
        Long2ObjectMap<Chunk> loaded = worldServer.getChunkProvider().loadedChunks;
        LongOpenHashSet sent = new LongOpenHashSet();
        for (Long2IntMap.Entry e : sectionMaskByChunk.long2IntEntrySet()) {
            long cp = e.getLongKey();
            Chunk chunk = loaded.get(cp);
            if (chunk == null) continue;
            int mask = e.getIntValue() | ChunkUtil.takeLightMask(worldServer, cp);
            if (mask == 0) continue;
            chunk.markDirty();
            ChunkUtil.sendChunk(worldServer, chunk, mask);
            sent.add(cp);
        }
        for (Long2IntMap.Entry e : sectionMaskByChunk.long2IntEntrySet()) {
            long cp = e.getLongKey();
            int cx = Library.getChunkPosX(cp), cz = Library.getChunkPosZ(cp);
            for (int dz = -1; dz <= 1; dz++) {
                for (int dx = -1; dx <= 1; dx++) {
                    long ncp = ChunkPos.asLong(cx + dx, cz + dz);
                    if (!sent.add(ncp)) continue;
                    Chunk chunk = loaded.get(ncp);
                    if (chunk == null) continue;
                    int mask = ChunkUtil.takeLightMask(worldServer, ncp);
                    if (mask == 0) continue;
                    chunk.markDirty();
                    ChunkUtil.sendChunk(worldServer, chunk, mask);
                }
            }
        }
        sectionMaskByChunk.clear();
        return true;
    }

    void maybeFinish() {
        boolean doneCollect = (collectFinished != 0);
        boolean doneConsolidate = (consolidationFinished != 0);
        boolean doneDestroy = (destroyFinished != 0);
        if (!doneCollect || !doneConsolidate || doneDestroy) return;
        if (activeWorkerTasks != 0 || !pendingCarves.isEmpty()) return;
        if (!carveWaiting.isEmpty()) return;
        if (!waitingRoom.isEmpty()) return;
        if (!postLoadQueues.isEmpty()) return;
        if (activeWorkerTasks != 0) return;
        if (!U.compareAndSetInt(this, OFF_FINISH_QUEUED, 0, 1)) return;
        worldServer.addScheduledTask(() -> {
            ChunkUtil.relightAll(worldServer, sectionMaskByChunk);
            awaitingFlush = true;
            releasePoolIfHeld();
            if (U.getAndSetInt(this, OFF_MAP_ACQUIRED, 0) != 0) {
                ChunkUtil.releaseMirrorMap(worldServer);
            }
        });
    }

    @ServerThread
    void finishCancellation() {
        if (activeWorkerTasks != 0 || !U.compareAndSetInt(this, OFF_CANCEL_CLEANUP, 0, 1)) return;
        ChunkUtil.relightAll(worldServer, sectionMaskByChunk);
        sectionMaskByChunk.clear();

        if (waitingRoom != null) waitingRoom.clear();
        PendingCarve job;
        while ((job = pendingCarves.poll()) != null) {
            for (CarveSubTask t : job.tasks) {
                if (t != null && t.mask != null) t.mask.free();
            }
        }
        if (postLoadQueues != null) {
            for (Long cpBoxed : postLoadQueues.keySet()) {
                ConcurrentLinkedQueue<ResumeItem> q = postLoadQueues.remove(cpBoxed);
                if (q != null) {
                    ResumeItem item;
                    while ((item = q.poll()) != null) {
                        discardResumeItem(item);
                    }
                }
            }
            postLoadQueues.clear();
        }

                releasePoolIfHeld();
        rayOrder = null;
        pausedRays = null;
        if (U.getAndSetInt(this, OFF_MAP_ACQUIRED, 0) != 0) {
            ChunkUtil.releaseMirrorMap(worldServer);
        }
        if (destructionMap != null) destructionMap.clear();
        if (aggMap != null) aggMap.clear();
        if (chunkLoadQueue != null) chunkLoadQueue.clear();
        pendingLoads.clear();
        carveWaiting.clear();
    }

    void workerFinished() {
        U.getAndAddInt(this, OFF_ACTIVE_WORKER_TASKS, -1);
        maybeCompleteRays();
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound nbt) {
        if (!CompatibilityConfig.isWarDim(worldServer)) {
            U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
            U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
            U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
            return;
        }

        algorithm = nbt.getInteger("algorithm");
        rayCount = nbt.getInteger("rayCount");
        isContained = nbt.getBoolean("isContained");
        U.putIntRelease(this, OFF_COLLECT_FINISHED, nbt.getBoolean("collectDone") ? 1 : 0);
        U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, nbt.getBoolean("consolidateDone") ? 1 : 0);
        U.putIntRelease(this, OFF_DESTROY_FINISHED, nbt.getBoolean("destroyDone") ? 1 : 0);
        if (nbt.hasKey("sectionMask", Constants.NBT.TAG_LIST)) {
            NBTTagList list = nbt.getTagList("sectionMask", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound t = list.getCompoundTagAt(i);
                long ck = ChunkPos.asLong(t.getInteger("cX"), t.getInteger("cZ"));
                sectionMaskByChunk.put(ck, t.getInteger("mask"));
            }
        }
        if (nbt.hasKey("destructionMap", Constants.NBT.TAG_LIST)) {
            NBTTagList list = nbt.getTagList("destructionMap", Constants.NBT.TAG_COMPOUND);
            for (int i = 0; i < list.tagCount(); i++) {
                NBTTagCompound tag = list.getCompoundTagAt(i);
                long ck = ChunkPos.asLong(tag.getInteger("cX"), tag.getInteger("cZ"));
                long[] bitsetData = ((NBTTagLongArray) tag.getTag("bitset")).data;
                AtomicLongBitSet bs = AtomicLongBitSet.fromLongArray(bitsetData, BITSET_SIZE);
                destructionMap.put(ck, bs);
            }
        }
        if (nbt.hasKey("populatedChunks")) {
            NBTTagLongArray arr = (NBTTagLongArray) nbt.getTag("populatedChunks");
            for (long cp : arr.data) populatedChunks.add(cp);
        }
        if (collectFinished != 0 && consolidationFinished != 0 && destroyFinished == 0) {
            if (U.compareAndSetInt(this, OFF_FINISH_QUEUED, 0, 1)) {
                worldServer.addScheduledTask(() -> awaitingFlush = true);
            }
        } else if (collectFinished == 0 || consolidationFinished == 0) {
            if (!CompatibilityConfig.isWarDim(worldServer)) {
                U.putIntRelease(this, OFF_COLLECT_FINISHED, 1);
                U.putIntRelease(this, OFF_CONSOLIDATION_FINISHED, 1);
                U.putIntRelease(this, OFF_DESTROY_FINISHED, 1);
                return;
            }
            U.putIntRelease(this, OFF_PENDING_RAYS, rayCount);
            initializeAndStartWorkers();
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbt) {
        if (!BombConfig.enableNukeNBTSaving) return;
        nbt.setInteger("algorithm", algorithm);
        nbt.setInteger("rayCount", rayCount);
        nbt.setBoolean("isContained", isContained);
        nbt.setBoolean("collectDone", collectFinished != 0);
        nbt.setBoolean("consolidateDone", consolidationFinished != 0);
        nbt.setBoolean("destroyDone", destroyFinished != 0);
        if (collectFinished != 0 && consolidationFinished != 0 && destroyFinished == 0 && !sectionMaskByChunk.isEmpty()) {
            NBTTagList list = new NBTTagList();
            ObjectIterator<Long2IntMap.Entry> iterator = sectionMaskByChunk.long2IntEntrySet().fastIterator();
            while (iterator.hasNext()) {
                Long2IntMap.Entry e = iterator.next();
                long ck = e.getLongKey();
                int mask = e.getIntValue();
                NBTTagCompound t = new NBTTagCompound();
                t.setInteger("cX", Library.getChunkPosX(ck));
                t.setInteger("cZ", Library.getChunkPosZ(ck));
                t.setInteger("mask", mask);
                list.appendTag(t);
            }
            nbt.setTag("sectionMask", list);
        }
        if (collectFinished == 0 && !destructionMap.isEmpty()) {
            NBTTagList list = new NBTTagList();
            destructionMap.forEach((Long ck, AtomicLongBitSet bitset) -> {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger("cX", Library.getChunkPosX(ck.longValue()));
                tag.setInteger("cZ", Library.getChunkPosZ(ck.longValue()));
                tag.setTag("bitset", new NBTTagLongArray(bitset.toLongArray()));
                list.appendTag(tag);
            });
            nbt.setTag("destructionMap", list);
        }
        if (collectFinished == 0 && !populatedChunks.isEmpty()) {
            long[] arr = new long[populatedChunks.size()];
            int i = 0;
            for (long cp : populatedChunks) arr[i++] = cp;
            nbt.setTag("populatedChunks", new NBTTagLongArray(arr));
        }
    }

    /** Orders carve jobs by distance from the epicenter, so the crater spreads outward. */

    private int compareChunk(PendingCarve a, PendingCarve b) {
        return Double.compare(chunkDistSq(a.chunkPos), chunkDistSq(b.chunkPos));
    }

    private double chunkDistSq(long cp) {
        int cx = Library.getChunkPosX(cp);
        int cz = Library.getChunkPosZ(cp);
        double dx = (cx << 4) + 8.0 - explosionX;
        double dz = (cz << 4) + 8.0 - explosionZ;
        return dx * dx + dz * dz;
    }

    static int capFor(int n) {
        int c = 1;
        while (c < n) c <<= 1;
        return Math.max(16, c);
    }

    static int ceilPow2(int x) {
        if (x <= 1) return 1;
        int hb = Integer.highestOneBit(x - 1);
        int r = hb << 1;
        return r > 0 ? r : (1 << 30);
    }

    static float getNukeResistance(IBlockState state) {
        if (state.getMaterial().isLiquid()) return 0.1F;
        Block block = state.getBlock();
        if (block == Blocks.SANDSTONE) return 4.0F;
        if (block == Blocks.OBSIDIAN) return 18.0F;
        return block.getExplosionResistance(null);
    }

    static double getEnergyLossFactor(float resistance, double distFrac) {
        if (resistance >= NUKE_RESISTANCE_CUTOFF) return resistance;
        if (resistance <= 0) return 0.0;
        if (resistance > LUT_MAX_RESISTANCE) return Math.pow(resistance + 1.0, 3.0 * distFrac) - 1.0;
        int rBin = (int) (resistance * (LUT_RESISTANCE_BINS - 1) / LUT_MAX_RESISTANCE);
        if (rBin == 0 && resistance > 0) return Math.pow(resistance + 1.0, 3.0 * distFrac) - 1.0;
        int dBin = (int) (distFrac * (LUT_DISTANCE_BINS - 1));
        return ENERGY_LOSS_LUT[rBin][dBin];
    }

    private static int rayKeyCubeMorton(double x, double y, double z) {
        double ax = Math.abs(x), ay = Math.abs(y), az = Math.abs(z);
        int face;
        double u, v, inv;
        if (ax >= ay && ax >= az) {
            if (x >= 0) {
                face = 0;
                inv = 1.0 / ax;
                u = (-z * inv + 1.0) * 0.5;
            } else {
                face = 1;
                inv = 1.0 / ax;
                u = (z * inv + 1.0) * 0.5;
            }
            v = (-y * inv + 1.0) * 0.5;
        } else if (ay >= az) {
            if (y >= 0) {
                face = 2;
                inv = 1.0 / ay;
                u = (x * inv + 1.0) * 0.5;
                v = (z * inv + 1.0) * 0.5;
            } else {
                face = 3;
                inv = 1.0 / ay;
                u = (x * inv + 1.0) * 0.5;
                v = (-z * inv + 1.0) * 0.5;
            }
        } else {
            if (z >= 0) {
                face = 4;
                inv = 1.0 / az;
                u = (x * inv + 1.0) * 0.5;
            } else {
                face = 5;
                inv = 1.0 / az;
                u = (-x * inv + 1.0) * 0.5;
            }
            v = (-y * inv + 1.0) * 0.5;
        }
        int uq = (int) (u * 1023.0);
        int vq = (int) (v * 1023.0);
        if (uq < 0) uq = 0;
        else if (uq > 1023) uq = 1023;
        if (vq < 0) vq = 0;
        else if (vq > 1023) vq = 1023;
        int morton = morton10(uq, vq);
        return (face << 20) | morton;
    }

    private static int morton10(int x, int y) {
        return (part1By1_10(x) << 1) | part1By1_10(y);
    }

    private static int part1By1_10(int x) {
        x &= 0x3FF;
        x = (x | (x << 8)) & 0x00FF00FF;
        x = (x | (x << 4)) & 0x0F0F0F0F;
        x = (x | (x << 2)) & 0x33333333;
        x = (x | (x << 1)) & 0x55555555;
        return x;
    }

    private int computeTaskGrain(int totalRays, int minGrain) {
        int p = pool == null ? 1 : pool.getParallelism();
        int targetTasks = Math.max(1, p << 2);
        int batch = (totalRays + targetTasks - 1) / targetTasks;
        batch = ceilPow2(batch);
        if (batch < minGrain) batch = minGrain;
        if (batch > totalRays && totalRays > 0) batch = totalRays;
        return batch;
    }

    private int computeKeyTaskGrain(int totalKeys) {
        int p = pool == null ? 1 : pool.getParallelism();
        int targetTasks = Math.max(1, p << 2);
        int batch = (totalKeys + targetTasks - 1) / targetTasks;
        batch = ceilPow2(batch);
        if (batch < 64) batch = 64;
        if (batch > totalKeys && totalKeys > 0) batch = totalKeys;
        return batch;
    }

    static final class BuildPackedTask extends RecursiveAction {
        final long[] packed;
        final int lo, hi, threshold;
        final double invRayIndexScale;

        BuildPackedTask(long[] packed, int lo, int hi, int threshold, double invRayIndexScale) {
            this.packed = packed;
            this.lo = lo;
            this.hi = hi;
            this.threshold = Math.max(1, threshold);
            this.invRayIndexScale = invRayIndexScale;
        }

        @Override
        protected void compute() {
            int len = hi - lo;
            if (len <= threshold) {
                for (int i = lo; i < hi; i++) {
                    double y = 1.0 - (i * invRayIndexScale) * 2.0;
                    double r = Math.sqrt(Math.max(0.0, 1.0 - y * y));
                    double t = GOLDEN_ANGLE * i;
                    double x = Math.cos(t) * r;
                    double z = Math.sin(t) * r;
                    int key = rayKeyCubeMorton(x, y, z);
                    packed[i] = (((long) key) << 32) | (i & 0xFFFF_FFFFL);
                }
                return;
            }
            int mid = lo + (len >>> 1);
            invokeAll(new BuildPackedTask(packed, lo, mid, threshold, invRayIndexScale), new BuildPackedTask(packed, mid, hi, threshold, invRayIndexScale));
        }
    }

    static final class UnpackOrderTask extends RecursiveAction {
        final long[] packed;
        final int[] order;
        final int lo, hi, threshold;

        UnpackOrderTask(long[] packed, int[] order, int lo, int hi, int threshold) {
            this.packed = packed;
            this.order = order;
            this.lo = lo;
            this.hi = hi;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = hi - lo;
            if (len <= threshold) {
                for (int i = lo; i < hi; i++) order[i] = (int) packed[i];
                return;
            }
            int mid = lo + (len >>> 1);
            invokeAll(new UnpackOrderTask(packed, order, lo, mid, threshold), new UnpackOrderTask(packed, order, mid, hi, threshold));
        }
    }

    final class ConsolidateAggTask extends RecursiveAction {
        final Long[] keys;
        final int start, end, threshold;

        ConsolidateAggTask(Long[] keys, int start, int end, int threshold) {
            this.keys = keys;
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                for (int i = start; i < end; i++) {
                    long cpLong = keys[i].longValue();
                    ChunkAgg agg = aggMap.get(cpLong);
                    if (agg == null) continue;
                    agg.drainUnlocked();
                    ExtendedBlockStorage[] storages = ChunkUtil.getLoadedEBS(mirror, cpLong);
                    aggMap.remove(cpLong);
                    if (storages == null) {
                        enqueueForMissingChunk(cpLong, new ResumeItem(agg));
                    } else {
                        prepareAndEnqueue(cpLong, buildMasksFromAgg(agg, storages));
                        agg.clear();
                    }
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new ConsolidateAggTask(keys, start, mid, threshold), new ConsolidateAggTask(keys, mid, end, threshold));
            }
        }
    }

    final class ConsolidateMaskTask extends RecursiveAction {
        final Long[] keys;
        final int start, end, threshold;

        ConsolidateMaskTask(Long[] keys, int start, int end, int threshold) {
            this.keys = keys;
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                for (int i = start; i < end; i++) {
                    long cpLong = keys[i].longValue();
                    AtomicLongBitSet chunkBitSet = destructionMap.get(cpLong);
                    if (chunkBitSet == null || chunkBitSet.isEmpty()) {
                        destructionMap.remove(cpLong);
                        continue;
                    }

                    ExtendedBlockStorage[] storages = ChunkUtil.getLoadedEBS(mirror, cpLong);
                    Int2ObjectOpenHashMap<BitMask> masks = splitBySubchunk(chunkBitSet);

                    if (storages == null) {
                        enqueueForMissingChunk(cpLong, new ResumeItem(masks));
                    } else {
                        prepareAndEnqueue(cpLong, masks);
                    }

                    destructionMap.remove(cpLong);
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new ConsolidateMaskTask(keys, start, mid, threshold), new ConsolidateMaskTask(keys, mid, end, threshold));
            }
        }
    }

    class RayTracerTask extends RecursiveAction {
        final int start, end, threshold;

        RayTracerTask(int start, int end, int threshold) {
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                U.getAndAddInt(ExplosionNukeRayParallelized.this, OFF_ACTIVE_WORKER_TASKS, 1);
                try {
                    LocalAgg agg = TL_LOCAL_AGG.get();
                    agg.clear();
                    int completed = 0;
                    for (int i = start; i < end; i++) {
                        if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
                        int dirIndex = (rayOrder != null) ? rayOrder[i] : i;
                        if (traceSingle(dirIndex, agg)) completed++;
                    }
                    flushDeferredMissing(agg);
                    mergeLocalAgg(agg);
                    if (completed > 0) {
                        U.getAndAddInt(ExplosionNukeRayParallelized.this, OFF_PENDING_RAYS, -completed);
                    }
                } finally {
                    workerFinished();
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new RayTracerTask(start, mid, threshold), new RayTracerTask(mid, end, threshold));
            }
        }
    }

    class ResumeBatchTask extends RecursiveAction {
        final IntArrayList indices;
        final int start, end, threshold;

        ResumeBatchTask(IntArrayList indices, int start, int end, int threshold) {
            this.indices = indices;
            this.start = start;
            this.end = end;
            this.threshold = Math.max(1, threshold);
        }

        @Override
        protected void compute() {
            int len = end - start;
            if (len <= threshold) {
                U.getAndAddInt(ExplosionNukeRayParallelized.this, OFF_ACTIVE_WORKER_TASKS, 1);
                try {
                    LocalAgg agg = TL_LOCAL_AGG.get();
                    agg.clear();
                    int completed = 0;
                    for (int i = start; i < end; i++) {
                        int dirIndex = indices.getInt(i);
                        if (Thread.currentThread().isInterrupted() || destroyFinished != 0) break;
                        if (traceSingle(dirIndex, agg)) completed++;
                    }
                    flushDeferredMissing(agg);
                    mergeLocalAgg(agg);
                    if (completed > 0) {
                        U.getAndAddInt(ExplosionNukeRayParallelized.this, OFF_PENDING_RAYS, -completed);
                    }
                } finally {
                    workerFinished();
                }
            } else {
                int mid = start + (len >>> 1);
                invokeAll(new ResumeBatchTask(indices, start, mid, threshold), new ResumeBatchTask(indices, mid, end, threshold));
            }
        }
    }

    static final class IntDoubleAccumulator {
        static final int EMPTY = Integer.MIN_VALUE;
        static final int BASE_CAPACITY = 128;
        static final int MAX_RETAINED_CAPACITY = 4096;
        static final double LOAD = 0.50;
        static final int HASH_MUL = 0xC2B2AE35;

        int[] keys;
        double[] vals;
        int mask;
        int shift;
        int size;
        int resizeThreshold;

        IntDoubleAccumulator() {
            this(BASE_CAPACITY);
        }

        IntDoubleAccumulator(int expected) {
            init(capFor(expected));
        }

        void init(int capacity) {
            keys = new int[capacity];
            Arrays.fill(keys, EMPTY);
            vals = new double[capacity];
            mask = capacity - 1;
            shift = 32 - Integer.numberOfTrailingZeros(capacity);
            size = 0;
            resizeThreshold = (int) (capacity * LOAD);
        }

        void clear() {
            if (keys.length > MAX_RETAINED_CAPACITY) {
                init(BASE_CAPACITY);
                return;
            }
            Arrays.fill(keys, EMPTY);
            size = 0;
        }

        void add(int key, double delta) {
            int[] ks = keys;
            double[] vs = vals;
            int m = mask;
            int idx = (key * HASH_MUL) >>> shift;
            while (true) {
                int k = ks[idx];
                if (k == EMPTY) {
                    ks[idx] = key;
                    vs[idx] = delta;
                    if (++size >= resizeThreshold) rehash();
                    return;
                }
                if (k == key) {
                    vs[idx] += delta;
                    return;
                }
                idx = (idx + 1) & m;
            }
        }

        int size() {
            return size;
        }

        void accumulateTo(Int2DoubleOpenHashMap map) {
            int[] ks = keys;
            double[] vs = vals;
            for (int i = 0; i < ks.length; i++) {
                int k = ks[i];
                if (k != EMPTY) map.addTo(k, vs[i]);
            }
        }

        void rehash() {
            int[] oldK = keys;
            double[] oldV = vals;
            int newCap = oldK.length << 1;
            int[] newK = new int[newCap];
            Arrays.fill(newK, EMPTY);
            double[] newV = new double[newCap];
            int newMask = newCap - 1;
            int newShift = 32 - Integer.numberOfTrailingZeros(newCap);
            int newThreshold = (int) (newCap * LOAD);
            int newSize = 0;
            for (int i = 0; i < oldK.length; i++) {
                int k = oldK[i];
                if (k == EMPTY) continue;

                int idx = (k * HASH_MUL) >>> newShift;
                while (newK[idx] != EMPTY) idx = (idx + 1) & newMask;
                newK[idx] = k;
                newV[idx] = oldV[i];
                newSize++;
            }
            keys = newK;
            vals = newV;
            mask = newMask;
            shift = newShift;
            resizeThreshold = newThreshold;
            size = newSize;
        }
    }

    static final class LocalAgg {
        final Long2ObjectOpenHashMap<IntDoubleAccumulator> localDamage = new Long2ObjectOpenHashMap<>(16);
        final Long2ObjectOpenHashMap<IntDoubleAccumulator> localLen = new Long2ObjectOpenHashMap<>(16);
        final Long2ObjectOpenHashMap<IntArrayList> deferredMissing = new Long2ObjectOpenHashMap<>(4);
        long lastCp = Long.MIN_VALUE;
        IntDoubleAccumulator lastDmg;
        IntDoubleAccumulator lastLen;

        void clear() {
            if (!localDamage.isEmpty()) {
                ObjectIterator<Long2ObjectMap.Entry<IntDoubleAccumulator>> it = localDamage.long2ObjectEntrySet().fastIterator();
                while (it.hasNext()) ACC_POOL.recycle(it.next().getValue());
                localDamage.clear();
            }
            if (!localLen.isEmpty()) {
                ObjectIterator<Long2ObjectMap.Entry<IntDoubleAccumulator>> it = localLen.long2ObjectEntrySet().fastIterator();
                while (it.hasNext()) ACC_POOL.recycle(it.next().getValue());
                localLen.clear();
            }
            lastCp = Long.MIN_VALUE;
            lastDmg = null;
            lastLen = null;

            if (!deferredMissing.isEmpty()) {
                for (IntArrayList list : deferredMissing.values()) {
                    list.clear();
                    INT_LIST_POOL.recycle(list);
                }
                deferredMissing.clear();
            }
        }

        void deferMissing(long chunkPos, int dirIndex) {
            IntArrayList list = deferredMissing.get(chunkPos);
            if (list == null) {
                list = INT_LIST_POOL.borrow();
                deferredMissing.put(chunkPos, list);
            }
            list.add(dirIndex);
        }

        boolean hasDeferredMissing() {
            return !deferredMissing.isEmpty();
        }

        Long2ObjectOpenHashMap<IntArrayList> missingChunks() {
            return deferredMissing;
        }

        void recordHit(long chunkPos, int bitIndex, double damageInc, double segLen) {
            if (damageInc <= 0.0 && segLen <= 0.0) return;

            if (chunkPos != lastCp) {
                lastCp = chunkPos;
                lastDmg = localDamage.get(chunkPos);
                lastLen = localLen.get(chunkPos);
            }

            if (damageInc > 0.0) {
                IntDoubleAccumulator acc = lastDmg;
                if (acc == null) {
                    acc = ACC_POOL.borrow();
                    localDamage.put(chunkPos, acc);
                    lastDmg = acc;
                }
                acc.add(bitIndex, damageInc);
            }

            if (segLen > 0.0) {
                IntDoubleAccumulator acc = lastLen;
                if (acc == null) {
                    acc = ACC_POOL.borrow();
                    localLen.put(chunkPos, acc);
                    lastLen = acc;
                }
                acc.add(bitIndex, segLen);
            }
        }
    }

    static final class ChunkAgg {
        final Int2DoubleOpenHashMap damage = new Int2DoubleOpenHashMap(64);
        final Int2DoubleOpenHashMap passLen = new Int2DoubleOpenHashMap(64);
        final ConcurrentLinkedQueue<IntDoubleAccumulator> qDmg = new ConcurrentLinkedQueue<>();
        final ConcurrentLinkedQueue<IntDoubleAccumulator> qLen = new ConcurrentLinkedQueue<>();

        void merge(IntDoubleAccumulator accumulator, boolean isDamage) {
            int sz = accumulator.size();
            if (sz == 0) {
                ACC_POOL.recycle(accumulator);
                return;
            }
            if (isDamage) qDmg.offer(accumulator);
            else qLen.offer(accumulator);
        }

        void drainUnlocked() {
            IntDoubleAccumulator a;
            while ((a = qDmg.poll()) != null) {
                if (a.size() > 0) a.accumulateTo(damage);
                ACC_POOL.recycle(a);
            }
            while ((a = qLen.poll()) != null) {
                if (a.size() > 0) a.accumulateTo(passLen);
                ACC_POOL.recycle(a);
            }
        }

        void clear() {
            damage.clear();
            passLen.clear();

            IntDoubleAccumulator a;
            while ((a = qDmg.poll()) != null) ACC_POOL.recycle(a);
            while ((a = qLen.poll()) != null) ACC_POOL.recycle(a);
        }
    }

    static final class PendingCarve {
        final long chunkPos;
        final List<CarveSubTask> tasks;

        PendingCarve(long chunkPos, List<CarveSubTask> tasks) {
            this.chunkPos = chunkPos;
            this.tasks = tasks;
        }
    }

    static final class CarveSubTask {
        final int subY;
        final BitMask mask;

        CarveSubTask(int subY, BitMask mask) {
            this.subY = subY;
            this.mask = mask;
        }
    }

    static final class ResumeItem {
        static final int CARVE = 0;
        static final int APPLY_MASKS = 1;
        static final int APPLY_AGG = 2;

        final int kind;
        final int subY;
        final BitMask mask;
        final Int2ObjectOpenHashMap<BitMask> masks;
        final ChunkAgg agg;

        ResumeItem(int kind, int subY, BitMask mask, Int2ObjectOpenHashMap<BitMask> masks, ChunkAgg agg) {
            this.kind = kind;
            this.subY = subY;
            this.mask = mask;
            this.masks = masks;
            this.agg = agg;
        }

        ResumeItem(int subY, BitMask mask) {
            this(CARVE, subY, mask, null, null);
        }

        ResumeItem(Int2ObjectOpenHashMap<BitMask> masks) {
            this(APPLY_MASKS, 0, null, masks, null);
        }

        ResumeItem(ChunkAgg agg) {
            this(APPLY_AGG, 0, null, null, agg);
        }
    }

    static final class TLPool<T> {
        private final ThreadLocal<ArrayDeque<T>> local = ThreadLocal.withInitial(ArrayDeque::new);
        private final ConcurrentLinkedQueue<T> shared = new ConcurrentLinkedQueue<>();
        private final Supplier<T> factory;
        private final Consumer<T> reset;
        private final int localCap;

        TLPool(Supplier<T> factory, Consumer<T> reset, int localCap) {
            this.factory = factory;
            this.reset = reset;
            this.localCap = Math.max(1, localCap);
        }

        T borrow() {
            ArrayDeque<T> q = local.get();
            T t = q.pollLast();
            if (t != null) return t;
            t = shared.poll();
            if (t != null) return t;
            return factory.get();
        }

        void recycle(T t) {
            reset.accept(t);
            ArrayDeque<T> q = local.get();
            if (q.size() < localCap) {
                q.addLast(t);
            } else {
                shared.offer(t);
            }
        }
    }
}