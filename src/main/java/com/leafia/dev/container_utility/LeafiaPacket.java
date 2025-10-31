package com.leafia.dev.container_utility;

import com.hbm.packet.PacketDispatcher;
import com.hbm.util.Tuple.Pair;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.technical.FiaLatch;
import com.llib.technical.FifthString;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.lang.reflect.Array;
import java.util.*;

public class LeafiaPacket extends RecordablePacket {

    int x, y, z, identifier;
    int dimension;
    private byte readMode = 0;
    // private byte writeMode = 0; ended up being integrated into function
    Chunk.EnumCreateEntityType checkType = Chunk.EnumCreateEntityType.IMMEDIATE;

    public final Map<Byte, Pair<Byte, Object>> signal = new HashMap<>();
    String identifierString = "Anonymous"; // used for crashlogs, server only (not sent on clients!)

    public LeafiaPacket() {
    }

    public LeafiaPacket(BlockPos pos, String identifier) {
        x = pos.getX();
        y = pos.getY();
        z = pos.getZ();
        this.identifier = identifier.hashCode();
        this.identifierString = identifier;
    }

    public LeafiaPacket __setTileEntityQueryType(Chunk.EnumCreateEntityType type) {
        this.checkType = type;
        return this;
    }

    private static byte getType(byte entry) {
        //BitSet bit = BitSet.valueOf(new byte[]{entry}).get(5,8);
        return (byte) ((entry & 255) >>> 5);//(bit.cardinality() == 0) ? 0 : bit.toByteArray()[0];
    }

    private static byte getKey(byte entry) {
        //BitSet bit = BitSet.valueOf(new byte[]{entry}).get(0,5);
        return (byte) (entry & 0b00011111);//(bit.cardinality() == 0) ? 0 : bit.toByteArray()[0];
    }

    List<FiaLatch<?>> latches = new ArrayList<>();

    public LeafiaPacket __write(int id, Object valueIn) {
        Object value = valueIn;
        if (value instanceof FiaLatch) {
            FiaLatch<?> latch = (FiaLatch<?>) value;
            value = latch.getInterest();
            if (latch.needsUpdate())
                latches.add(latch);
            else
                return this;
        }
        if (id == -1) return this;
        byte key = (byte) id;
        //BitSet bit = BitSet.valueOf(new byte[]{key});
        //if ((key&255) >>> 5 > 0)//bit.get(5,8).cardinality() != 0)
        if (id < 0 || id > 0b00011111)
            throw new LeafiaDevFlaw("LeafiaPacket >> [Sender, identifier: " + identifierString + "] Entry ID given for LeafiaPacket wasn't in the range of 0 ~ 31");
        byte mode = 0;
        boolean skip = false;
        if (valueIn != null && valueIn.getClass().isArray()) {
            if (Array.getLength(valueIn) <= 0) {
                skip = true;
                mode = 1;
                key += 5 << 5; // null key
            } else
                value = Array.get(valueIn, 0);
        }
        if (!skip) {
            if (value instanceof Boolean) {
            } else if (value instanceof Byte) {
                key += 1 << 5;
            } else if (value instanceof Short) {
                key += 2 << 5;
            } else if (value instanceof Integer) {
                key += 3 << 5;
            } else if (value instanceof Long) {
                key += 4 << 5;
            } else if (value instanceof Float) {
                key += 5 << 5;
            } else if (value instanceof Double) {
                key += 6 << 5;
            } else {
                mode = 1;
                if (value instanceof NBTTagCompound) {
                } else if (value instanceof ItemStack) {
                    key += 1 << 5;
                } else if (value instanceof String || value instanceof FifthString) {
                    key += 2 << 5;
                } else if (value instanceof BlockPos) {
                    key += 3 << 5;
                } else if (value instanceof Vec3d) {
                    key += 4 << 5;
                } else if (value == null) {
                    key += 5 << 5;
                } else
                    throw new LeafiaDevFlaw("LeafiaPacket >> [Sender, identifier: " + identifierString + "] Given type not supported for LeafiaPacket.. (" + value.getClass().getName() + ")");
            }
        }
        //this.signal.put((bit.cardinality() == 0) ? 0 : bit.toByteArray()[0],value);
        this.signal.put(key, new Pair<>(mode, valueIn));
        return this;
    }

    private void encodeByMode(int mode, Set<Byte> entries, LeafiaBuf buf) {
        int nextMode = 31;
        Set<Byte> remaining = new HashSet<>();
        for (Byte entry : entries) {
            Pair<Byte, Object> pair = signal.get(entry);
            if (pair.getA() == (byte) mode) {
                int c = getType(entry) + mode * 10;
                int arrayLength = -1;
                Object valueIn = pair.getB();
                if (valueIn != null && valueIn.getClass().isArray()) {
                    buf.writeByte((7 << 5) + 31); // code for Array (theres no way we would need 31 fucking entire readerset modes anyway)
                    arrayLength = Array.getLength(valueIn);
                    buf.writeInt((entry & 0xFF) | arrayLength << 8);
                } else
                    buf.writeByte(entry);
                for (int i = 0; (arrayLength < 0) ? (i == 0) : (i < arrayLength); i++) {
                    Object value = (arrayLength < 0) ? valueIn : Array.get(valueIn, i);
                    switch (c) {
                        case 0:
                            buf.writeBoolean((boolean) value);
                            break;
                        case 1:
                            buf.writeByte((byte) value);
                            break;
                        case 2:
                            buf.writeShort((short) value);
                            break;
                        case 3:
                            buf.writeInt((int) value);
                            break;
                        case 4:
                            buf.writeLong((long) value);
                            break;
                        case 5:
                            buf.writeFloat((float) value);
                            break;
                        case 6:
                            buf.writeDouble((double) value);
                            break;
                        case 10:
                            buf.writeNBT((NBTTagCompound) value);
                            break;
                        case 11:
                            buf.writeItemStack((ItemStack) value);
                            break;
                        case 12:
                            buf.writeFifthString(new FifthString((String) value));
                            break;
                        case 13:
                            BlockPos bpos = (BlockPos) value;
                            buf.writeInt(bpos.getX());
                            buf.writeInt(bpos.getY());
                            buf.writeInt(bpos.getZ());
                            break;
                        case 14:
                            Vec3d vec3d = (Vec3d) value;
                            buf.writeDouble(vec3d.x);
                            buf.writeDouble(vec3d.y);
                            buf.writeDouble(vec3d.z);
                            break;
                        case 15:
                            break;
                        default:
                            throw new LeafiaDevFlaw("LeafiaPacket >> [Sender, identifier: " + identifierString + "] Unrecognized data type " + c + ":" + readMode + "." + String.format("%03d", Integer.parseInt(Integer.toBinaryString(getType(entry)))) + "!");
                    }
                }
            } else {
                nextMode = Math.min(nextMode, pair.getA());
                remaining.add(entry);
            }
        }
        if (remaining.size() > 0) {
            buf.writeByte((7 << 5) + nextMode);
            encodeByMode(nextMode, remaining, buf);
        }
    }

    @Override
    public void toBits(LeafiaBuf buf) {
        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);
        buf.writeInt(identifier);
        buf.writeByte((byte) (checkType.ordinal()));
        encodeByMode(0, signal.keySet(), buf);
    }

    @Override
    public void fromBits(LeafiaBuf buf) {
        signal.clear();
        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();
        identifier = buf.readInt();
        checkType = Chunk.EnumCreateEntityType.values()[buf.readByte()];
        readMode = 0;
        while (buf.readableBits() >= 1) {
            byte entry = buf.readByte();
            byte type = getType(entry);

            Object[] array = null;
            if (type == 7) {
                byte nextMode = getKey(entry);
                if (nextMode == 31) { // 31 code for Array (Theres no way we would need 31 fucking readerset modes anyway)
                    int entryArray = buf.readInt();
                    entry = (byte) (entryArray & 0b111_11111);
                    type = getType(entry);
                    array = new Object[entryArray >>> 8];
                } else {
                    readMode = nextMode;
                    continue;
                }
            }
            for (int arrayIndex = 0; (array == null) ? (arrayIndex == 0) : (arrayIndex < array.length); arrayIndex++) {
                Object value;
                int c = type + readMode * 10;
                switch (c) {
                    case 0:
                        value = buf.readBoolean();
                        break;
                    case 1:
                        value = buf.readByte();
                        break;
                    case 2:
                        value = buf.readShort();
                        break;
                    case 3:
                        value = buf.readInt();
                        break;
                    case 4:
                        value = buf.readLong();
                        break;
                    case 5:
                        value = buf.readFloat();
                        break;
                    case 6:
                        value = buf.readDouble();
                        break;
                    case 10:
                        value = buf.readNBT();
                        break;
                    case 11:
                        value = buf.readItemStack();
                        break;
                    case 12:
                        value = buf.readFifthString().toString();
                        break;
                    case 13:
                        value = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
                        break;
                    case 14:
                        value = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
                        break;
                    case 15:
                        value = null;
                        break;
                    default:
                        throw new LeafiaDevFlaw("LeafiaPacket >> [Receiver] Unrecognized data type " + c + ":" + readMode + "." + String.format("%03d", Integer.parseInt(Integer.toBinaryString(type))) + "!");
                }
                if (array == null)
                    signal.put(entry, new Pair<>(readMode, value));
                else
                    array[arrayIndex] = value;
            }
            if (array != null)
                signal.put(entry, new Pair<>(readMode, array));
        }
    }

    TileEntity te = null;

    public static LeafiaPacket _start(TileEntity entity) {
        LeafiaPacket packet = new LeafiaPacket(entity.getPos(), ((LeafiaPacketReceiver) entity).getPacketIdentifier());
        packet.dimension = entity.getWorld().provider.getDimension();
        packet.te = entity;
        return packet;
    }

    /**
     * Send a signal to server to update the client
     * <p>This is called automatically
     *
     * @param entity The TileEntity this whole thing is on about
     */
    public static void _validate(TileEntity entity) {
        if (!entity.getWorld().isRemote) return; // ayo you're lazy
        LeafiaPacket packet = new LeafiaPacket(entity.getPos(), ((LeafiaPacketReceiver) entity).getPacketIdentifier());
        packet.dimension = entity.getWorld().provider.getDimension();
        packet.te = entity;
        packet.isValidation = true;
        packet.__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK);
        packet.__sendToServer();
    }

    /**
     * Internal variable, client only; never sent to server.
     * <br>Flagging this will bypass the empty value check.
     */
    boolean isValidation = false;

    boolean onSending() {
        if (signal.size() <= 0 && !isValidation) return true;
        for (FiaLatch<?> latch : latches)
            latch.update();
        return false;
    }

    public void __sendToAll() {
        if (onSending()) return;
        PacketDispatcher.wrapper.sendToAll(this);
    }

    @Deprecated
    public void __sendToAllInDimension() {
        if (onSending()) return;
        PacketDispatcher.wrapper.sendToDimension(this, dimension);
    }

    public void __sendToAffectedClients() {
        if (onSending()) return;
        this.checkType = Chunk.EnumCreateEntityType.CHECK;
        this.__sendToClients(((LeafiaPacketReceiver) te).affectionRange() * 1.3);
    }

    public void __sendToListeners() {
        if (te instanceof LeafiaPacketReceiver rec) {
            for (EntityPlayer plr : rec.getListeners())
                __sendToClient(plr);
        } else
            throw new LeafiaDevFlaw("__sendToListeners used when the TileEntity isn't a LeafiaPacketReceiver. How?!");
    }

    public void __sendToServer() {
        if (onSending()) return;
        PacketDispatcher.wrapper.sendToServer(this);
    }

    public void __sendToClients(double range) {
        if (onSending()) return;
        PacketDispatcher.wrapper.sendToAllAround(this, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
    }

    public void __sendToClient(EntityPlayer player) {
        if (onSending()) return;
        _sendToClient(this, player);
    }

    public static void _sendToClient(IMessage message, EntityPlayer player) {
        if (player instanceof EntityPlayerMP)
            PacketDispatcher.wrapper.sendTo(message, (EntityPlayerMP) player);
        else
            PacketDispatcher.wrapper.sendToAll(message);
    }

    public static class Handler implements IMessageHandler<LeafiaPacket, IMessage> {
        @SideOnly(Side.CLIENT)
        public void handleLocal(LeafiaPacket m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                World world = Minecraft.getMinecraft().world;
                BlockPos pos = new BlockPos(m.x, m.y, m.z);
                Chunk chunk = world.getChunk(pos);
                TileEntity LocalEntity = chunk.getTileEntity(pos, m.checkType);
                if (LocalEntity == null) return;
                if (LocalEntity instanceof LeafiaPacketReceiver) {
                    LeafiaPacketReceiver receiver = (LeafiaPacketReceiver) LocalEntity;
                    if (receiver.getPacketIdentifier().hashCode() == m.identifier) {
                        m.signal.forEach((Byte entry, Pair<Byte, Object> value) -> {
                            receiver.onReceivePacketLocal(getKey(entry), value.getB());
                        });
                    }
                }
            });
        }

        @Override
        public IMessage onMessage(LeafiaPacket m, MessageContext ctx) {
            if (ctx.side.isClient()) {
                handleLocal(m, ctx);
            } else {
                ctx.getServerHandler().player.getServer().addScheduledTask(() -> {
                    EntityPlayer p = ctx.getServerHandler().player;
                    BlockPos pos = new BlockPos(m.x, m.y, m.z);
                    if (!p.world.isBlockLoaded(pos))
                        return;
                    Chunk chunk = p.world.getChunk(pos);
                    TileEntity ServerEntity = chunk.getTileEntity(pos, m.checkType);
                    if (ServerEntity == null) return;
                    if (ServerEntity instanceof LeafiaPacketReceiver) {
                        LeafiaPacketReceiver receiver = (LeafiaPacketReceiver) ServerEntity;
                        if (receiver.getPacketIdentifier().hashCode() == m.identifier) {
                            if (m.signal.size() <= 0)
                                receiver.onPlayerValidate(p);
                            else {
                                m.signal.forEach((Byte entry, Pair<Byte, Object> value) -> {
                                    receiver.onReceivePacketServer(getKey(entry), value.getB(), p);
                                });
                            }
                        }
                    }
                });
            }
            return null;
        }
    }
}
