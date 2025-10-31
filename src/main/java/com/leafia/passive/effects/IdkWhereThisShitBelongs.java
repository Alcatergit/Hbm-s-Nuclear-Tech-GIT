package com.leafia.passive.effects;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.generic.WasteLog;
import com.hbm.config.BombConfig;
import com.hbm.entity.logic.EntityTomBlast;
import com.hbm.handler.RadiationSystemNT;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.AuxSavedData;
import com.hbm.saveddata.RadiationSavedData;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkProviderServer;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.*;

public class IdkWhereThisShitBelongs {
    // I JUST SUCK.
    static final Random rand = new Random();
    public static float fovM = 1.0F;
    static int ticks = 0;
    public static double infernal;
    public static double quake;
    public static double darkness;
    public static boolean evacuated = false;
    public static double getTomImpactLargest(World world,String key,int dimension) {
        AuxSavedData data = AuxSavedData.getData(world);
        double out = 0;
        if (data.exists("tomImpact")) {
            for (NBTBase impact : data.getL("tomImpact")) {
                NBTTagCompound nbt = (NBTTagCompound) impact;
                if (nbt.getInteger("dim") != dimension) continue;
                out = Math.max(out, nbt.getDouble(key));
            }
        }
        return out;
    }
    public static double getTomImpactLargestPos(World world,String key,BlockPos pos,int dimension) {
        AuxSavedData data = AuxSavedData.getData(world);
        double out = 0;
        if (data.exists("tomImpact")) {
            for (NBTBase impact : data.getL("tomImpact")) {
                double localIntensity = 0;
                NBTTagCompound nbt = (NBTTagCompound) impact;
                if (nbt.getInteger("dim") != dimension) continue;
                double dx = nbt.getInteger("x") - pos.getX();
                double dz = nbt.getInteger("z") - pos.getZ();
                double dist = Math.sqrt(dx * dx + dz * dz);
                if (dist < BombConfig.tomImpactRadius)
                    localIntensity = Math.pow((BombConfig.tomImpactRadius - dist) / BombConfig.tomImpactRadius, BombConfig.tomImpactExponent);
                else continue;
                out = Math.max(out, nbt.getDouble(key) * localIntensity);
            }
        }
        return out;
    }
    public static boolean isEntityInShelter(Entity entity,Boolean fallback) {
        RadiationSavedData data = RadiationSavedData.getData(entity.world);
        return data.isSealed(entity.getPosition(),fallback);
    }
    public static void serverTick(World world) {
        int rate = 40;
        ticks = (ticks+1)%rate;
        if (ticks == 20) {
            AuxSavedData data = AuxSavedData.getData(world);
            for (EntityPlayer player : world.playerEntities) {
                double infernal = 0;
                double quake = 0;
                double darkness = 0;
                if (data.exists("tomImpact")) {
                    for (NBTBase impact : data.getL("tomImpact")) {
                        double localIntensity = 0;
                        NBTTagCompound nbt = (NBTTagCompound)impact;
                        double dx = nbt.getInteger("x")-player.getPosition().getX();
                        double dz = nbt.getInteger("z")-player.getPosition().getZ();
                        double dist = Math.sqrt(dx*dx+dz*dz);
                        if (dist < BombConfig.tomImpactRadius)
                            localIntensity = Math.pow((BombConfig.tomImpactRadius-dist)/BombConfig.tomImpactRadius,BombConfig.tomImpactExponent);
                        else continue;
                        infernal = Math.max(infernal,nbt.getDouble("infernal")*localIntensity);
                        quake = Math.max(quake,nbt.getDouble("seismic")*localIntensity);
                        darkness = Math.max(darkness,nbt.getDouble("dust")*localIntensity); // limitation moment
                    }
                }
                //player.getEntityData().getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG).setDouble("tomSeismic",intensity);
                TomImpactPacket packet = new TomImpactPacket();
                packet.infernal = infernal;
                packet.quake = quake;
                packet.darkness = darkness;
                packet.evacuated = isEntityInShelter(player,false); // fuck off 2
                LeafiaPacket._sendToClient(packet,player);
            }
            if (data.exists("tomImpact")) {
                int i = 0;
                for (NBTBase impact : data.getL("tomImpact")) {
                    NBTTagCompound nbt = (NBTTagCompound)impact;
                    if (nbt.getDouble("infernal") > 0) {
                        double cool = 1.0 / (BombConfig.tomImpactTimeInfernal * 60 * 20 / rate);
                        nbt.setDouble("infernal", MathHelper.clamp(nbt.getDouble("infernal") - cool, 0, 1));
                        nbt.setDouble("dust", MathHelper.clamp(nbt.getDouble("dust") + cool, 0, 1));
                        if (nbt.getDouble("infernal") <= 0)
                            nbt.setDouble("dust", 1); // just to be safe lol
                    } else {
                        double settle0 = 1.0 / (BombConfig.tomImpactTimeVolcanic * 86400 * 20 / rate);
                        double settle1 = 1.0 / (BombConfig.tomImpactTimeSeismic * 86400 * 20 / rate);
                        double settle2 = 1.0 / (BombConfig.tomImpactTimeDarkness * 86400 * 20 / rate);
                        nbt.setDouble("volcanic", MathHelper.clamp(nbt.getDouble("volcanic") - settle0, 0, 1));
                        nbt.setDouble("seismic", MathHelper.clamp(nbt.getDouble("seismic") - settle1, 0, 1));
                        nbt.setDouble("dust", MathHelper.clamp(nbt.getDouble("dust") - settle2, 0, 1));
                        if ((nbt.getDouble("volcanic") <= 0) && (nbt.getDouble("seismic") <= 0) && (nbt.getDouble("dust") <= 0))
                            data.getL("tomImpact").removeTag(i);
                        else i++;
                    }
                }
                data.markDirty();
            }
        }
    }
    static float shakeX = 0;
    static float shakeY = 0;
    public static int dustDisplayTicks = 0;
    @SideOnly(Side.CLIENT)
    public static void localTick() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        //RadiationSavedData data = RadiationSavedData.getData(player.world);
        //if (data.isSealed(player.getPosition(),true))
        if (darkness > 0 && !evacuated)
            dustDisplayTicks = MathHelper.clamp(dustDisplayTicks+1,0,30);
        else
            dustDisplayTicks = MathHelper.clamp(dustDisplayTicks-1,0,30);
        if (quake > 0 && player.onGround) {
            float mul = dustDisplayTicks/30f*0.9f+0.1f;
            fovM = 1.0F - (rand.nextInt(2) * 0.02F)*mul;
            shakeX = (rand.nextFloat()-0.5F)*mul;
            shakeY = (rand.nextFloat()-0.5F)*mul;
        } else {
            fovM = 1.0F;
            shakeX = 0F;
            shakeY = 0F;
        }
    }
    @SideOnly(Side.CLIENT)
    public static void shakeCam() {
        GL11.glTranslated(shakeX*0.05F,shakeY*0.005F,0);
    }
    public static void tryCollapseBlock(World world, BlockPos pos, IBlockState state, Block block) {
        boolean canCollapse = true;
        if (block instanceof IRadResistantBlock)
            if (((IRadResistantBlock)block).isRadResistant(world,pos))
                canCollapse = false;
        if (canCollapse)
            if (world.rand.nextInt(100) == 0) {
                if (!state.isFullBlock())
                    world.destroyBlock(pos,true);
                else if (!state.isOpaqueCube())
                    world.destroyBlock(pos,true);
                else {
                    BlockPos newPos = pos;
                    for (boolean stop = false; !stop;) {
                        BlockPos downPos = newPos.down();
                        stop = true;
                        if (!world.isBlockLoaded(downPos))
                            break;
                        if (!world.isValid(downPos))
                            break;
                        if (world.isAirBlock(downPos))
                            stop = false;
                        else {
                            IBlockState dstate = world.getBlockState(downPos);
                            if (!dstate.isOpaqueCube() || !dstate.isFullBlock()) {
                                stop = false;
                                world.destroyBlock(downPos,true);
                            }
                        }
                        if (!stop) {
                            newPos = downPos;
                            for (int j2 = 0; j2 < 2; ++j2) {
                                for (int k2 = 0; k2 < 2; ++k2) {
                                    for (int l2 = 0; l2 < 2; ++l2) {
                                        double d0 = ((double) j2 + 0.5D) / 2.0D;
                                        double d1 = ((double) k2 + 0.5D) / 2.0D;
                                        double d2 = ((double) l2 + 0.5D) / 2.0D;
                                        NBTTagCompound data = new NBTTagCompound();
                                        data.setString("type", "vanillaExt");
                                        data.setString("mode","blockdust");
                                        //data.setFloat("scale", 4);
                                        data.setInteger("block",Block.getIdFromBlock(block));
                                        data.setDouble("mX",d0-0.5);
                                        data.setDouble("mY",d1-0.75);
                                        data.setDouble("mZ",d2-0.5);
                                        //PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data,downPos.getX()+d0,downPos.getY()+d1,downPos.getZ()+d2), new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5, 250));
                                        //MainRegistry.proxy.effectNT(data); fuck off this causes a critical memory leak Bruh
                                        // whoops
                                    }
                                }
                            }
                        }
                    }
                    if (newPos != pos) {
                        world.setBlockState(newPos,state);
                        world.setBlockToAir(pos);
                        world.playSound(null,newPos,state.getBlock().getSoundType().getPlaceSound(), SoundCategory.BLOCKS,1,1);
                        TomImpactCollapsePacket packet = new TomImpactCollapsePacket();
                        packet.x = newPos.getX();
                        packet.y0 = pos.getY();
                        packet.y1 = newPos.getY();
                        packet.z = newPos.getZ();
                        PacketDispatcher.wrapper.sendToAllAround(packet,new NetworkRegistry.TargetPoint(world.provider.getDimension(),newPos.getX()+0.5,newPos.getY()+0.5,newPos.getZ()+0.5,128));
                    }
                }
            }
    }
    private static void handleIndividualDestruction(World world, BlockPos pos, IBlockState state, Block block) {
        if (state.getMaterial().isLiquid()) return;
        if (state.getMaterial() == ModBlocks.materialGas) return;
        if (state.getMaterial().isReplaceable()) return;
        if (block == Blocks.GRASS) {
            world.setBlockState(pos,ModBlocks.burning_earth.getDefaultState());
        } else if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
            world.setBlockToAir(pos);
        } else if (block == Blocks.LOG || block == Blocks.LOG2) {
            world.setBlockState(pos, ((WasteLog)ModBlocks.waste_log).getSameRotationState(state));
        } else if (state.getMaterial() == Material.WOOD) {
            if (state.isFullBlock())
                if (state.getBlock() != ModBlocks.waste_log) {
                    world.setBlockState(pos,ModBlocks.waste_planks.getDefaultState());
                }
        } else if (state.getMaterial() == Material.PLANTS) {
            world.setBlockState(pos,Blocks.FIRE.getDefaultState());
        } else if (state.getMaterial() == Material.LEAVES) {
            if (world.rand.nextInt(100) == 0)
                world.setBlockState(pos,Blocks.FIRE.getDefaultState());
            else
                world.setBlockToAir(pos);
        }
        if (state.getMaterial().getCanBurn()) {
            if (world.rand.nextInt(100) == 0) {
                EnumFacing face = EnumFacing.random(world.rand);
                BlockPos pos1 = pos.add(face.getXOffset(), face.getYOffset(), face.getZOffset());
                if (world.isValid(pos1))
                    if (world.isAirBlock(pos1))
                        world.setBlockState(pos, Blocks.FIRE.getDefaultState());
            }
        }
    }
    public static final List<Chunk> tomQueue = new ArrayList<>();
    public static final Set<EntityTomBlast> waitFor = new HashSet<>();
    public static void processPockets(World world) {
        int ihateyoujava = waitFor.size();
        while (ihateyoujava > 0) {
            EntityTomBlast subject = (EntityTomBlast)(waitFor.toArray()[0]);
            if (subject.isEntityAlive())
                return;
            waitFor.remove(subject);
            ihateyoujava--;
        }
        AuxSavedData data = AuxSavedData.getData(world);
        if (!data.exists("tomImpact"))
            return;
        if (data.getL("tomImpact").tagCount() <= 0) return;
        WorldServer serv = (WorldServer)world;
        ChunkProviderServer provider = (ChunkProviderServer) serv.getChunkProvider();
        /*
        Object[] storages = RadiationSystemNT.getAll(world).toArray();
        if (storages.length <= 0) return;
        RadiationSystemNT.ChunkRadiationStorage storage = (RadiationSystemNT.ChunkRadiationStorage)(storages[world.rand.nextInt(storages.length)]);
        //RadiationSystemNT.SubChunkRadiationStorage subc = storage.chunks[world.rand.nextInt(storage.chunks.length)];*/
        if (tomQueue.size() <= 0) {
            Collection<Chunk> chunks = provider.getLoadedChunks();
            if (chunks.size() <= 0) return;
            for (Chunk chunk : chunks) {
                if (chunk.isLoaded())
                    tomQueue.add(chunk);
            }
        }
        //Chunk chunk = (Chunk)(chunks.toArray()[world.rand.nextInt(chunks.size())]);
        if (tomQueue.size() <= 0) return;
        Chunk chunk = tomQueue.remove(0);
        if (!chunk.isLoaded()) return;

        RadiationSystemNT.ChunkRadiationStorage storage = RadiationSystemNT.getChunkStorage2(world,chunk.getPos());
        for (int i = 0; i < storage.chunks.length; i++) {
            RadiationSystemNT.SubChunkRadiationStorage subc = storage.queryGetSubChunk(i);
            if (subc != null)
                for (RadiationSystemNT.RadPocket pocket : subc.pockets) {
                    processPocket(world,pocket);
                }
        }
    }
    public static void processPocket(World world, RadiationSystemNT.RadPocket p) {
        int ihateyoujava = waitFor.size();
        while (ihateyoujava > 0) {
            EntityTomBlast subject = (EntityTomBlast)(waitFor.toArray()[0]);
            if (subject.isEntityAlive())
                return;
            waitFor.remove(subject);
            ihateyoujava--;
        }
        if (!p.isSealed()) {
            BlockPos startPos = p.getSubChunkPos();
            RadiationSystemNT.RadPocket[] pocketsByBlock = p.parent.pocketsByBlock;
            double infernal = getTomImpactLargestPos(world,"infernal",startPos.add(8,8,8),world.provider.getDimension());
            double quake = getTomImpactLargestPos(world,"seismic",startPos.add(8,8,8),world.provider.getDimension());
            for (int i = 0; i < 16; i++) {
                for (int j = 0; j < 16; j++) {
                    for (int k = 0; k < 16; k++) {
                        if (pocketsByBlock != null && pocketsByBlock[i * 16 * 16 + j * 16 + k] != p) {
                            continue;
                        }
                        BlockPos pos = startPos.add(i, j, k);
                        IBlockState state = world.getBlockState(pos);
                        Block block = state.getBlock();
                        if (infernal > 0)
                            handleIndividualDestruction(world,pos,state,block);
                        if (quake > 0)
                            tryCollapseBlock(world,pos,state,block);
                    }
                }
            }
        }
    }
    public static void processChunk(ChunkProviderServer provider, ChunkPos coords) {
        int ihateyoujava = waitFor.size();
        while (ihateyoujava > 0) {
            EntityTomBlast subject = (EntityTomBlast)(waitFor.toArray()[0]);
            if (subject.isEntityAlive())
                return;
            waitFor.remove(subject);
            ihateyoujava--;
        }
        World world = provider.world;
        double infernal = getTomImpactLargestPos(world,"infernal",new BlockPos(coords.x*16,0, coords.z*16),provider.world.provider.getDimension());
        double quake = getTomImpactLargestPos(world,"seismic",new BlockPos(coords.x*16,0, coords.z*16),provider.world.provider.getDimension());
        if (infernal + quake > 0) {
            if (provider.chunkExists(coords.x,coords.z)) {
                for(int a = 0; a < 16; a ++) {
                    for (int b = 0; b < 16; b++) {
                        int x = coords.getXStart() + a;
                        int z = coords.getZStart() + b;
                        for (int y = 0; y < world.getHeight(x, z); y++) {
                            BlockPos pos = new BlockPos(x,y,z);
                            IBlockState state = world.getBlockState(pos);
                            Block block = state.getBlock();
                            if (infernal > 0)
                                handleIndividualDestruction(world,pos,state,block);
                            if (quake > 0)
                                tryCollapseBlock(world,pos,state,block);
                        }
                    }
                }
            }
        }
    }
    public static class TomImpactPacket extends RecordablePacket {
        public boolean evacuated;
        double infernal;
        double quake;
        double darkness;
        public TomImpactPacket() {
        }
        @Override
        public void fromBits(LeafiaBuf buf) {
            infernal = buf.readDouble();
            quake = buf.readDouble();
            darkness = buf.readDouble();
            evacuated = buf.readBoolean();
        }
        @Override
        public void toBits(LeafiaBuf buf) {
            buf.writeDouble(infernal);
            buf.writeDouble(quake);
            buf.writeDouble(darkness);
            buf.writeBoolean(evacuated);
        }
        public static class Handler implements IMessageHandler<TomImpactPacket, IMessage> {
            @Override
            @SideOnly(Side.CLIENT)
            public IMessage onMessage(TomImpactPacket message, MessageContext ctx) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    IdkWhereThisShitBelongs.infernal = message.infernal;
                    IdkWhereThisShitBelongs.quake = message.quake;
                    IdkWhereThisShitBelongs.darkness = message.darkness;
                    IdkWhereThisShitBelongs.evacuated = message.evacuated;
                });
                return null;
            }
        }
    }
    public static class TomImpactCollapsePacket extends RecordablePacket {
        public int x;
        public int y0;
        public int y1;
        public int z;
        public TomImpactCollapsePacket() {
        }
        @Override
        public void fromBits(LeafiaBuf buf) {
            x = buf.readInt();
            y0 = buf.readInt();
            y1 = buf.readInt();
            z = buf.readInt();
        }
        @Override
        public void toBits(LeafiaBuf buf) {
            buf.writeInt(x);
            buf.writeInt(y0);
            buf.writeInt(y1);
            buf.writeInt(z);
        }
        public static class Handler implements IMessageHandler<TomImpactCollapsePacket, IMessage> {
            @Override
            @SideOnly(Side.CLIENT)
            public IMessage onMessage(TomImpactCollapsePacket message, MessageContext ctx) {
                Minecraft.getMinecraft().addScheduledTask(() -> {
                    BlockPos pos = new BlockPos(message.x,message.y1,message.z);
                    World world = Minecraft.getMinecraft().world;
                    IBlockState state = world.getBlockState(pos);
                    if (world.isAirBlock(pos)) {
                        BlockPos pos0 = new BlockPos(message.x,message.y0,message.z);
                        if (!world.isAirBlock(pos0))
                            state = world.getBlockState(pos0);
                    }
                    for (int i = 1; i <= (message.y0-message.y1); i++) {
                        for (int j2 = 0; j2 < 3; ++j2) {
                            for (int k2 = 0; k2 < 3; ++k2) {
                                for (int l2 = 0; l2 < 3; ++l2) {
                                    double d0 = ((double) j2 + 0.5D) / 3.0D;
                                    double d1 = ((double) k2 + 0.5D) / 3.0D;
                                    double d2 = ((double) l2 + 0.5D) / 3.0D;
                                    world.spawnParticle(EnumParticleTypes.BLOCK_DUST,pos.getX()+d0,pos.getY()+i+d1,pos.getZ()+d2,(d0-0.5)/i,d1-0.75,(d2-0.5)/i,Block.getStateId(state));
                                }
                            }
                        }
                    }
                        //Minecraft.getMinecraft().effectRenderer.addBlockDestroyEffects(pos.up(i),state);
                });
                return null;
            }
        }
    }
}
