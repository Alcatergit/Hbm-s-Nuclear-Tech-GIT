package com.hbm.packet;

import com.hbm.interfaces.Spaghetti;
import com.hbm.tileentity.INBTPacketReceiver;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.dev.optimization.diagnosis.RecordablePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Deprecated
@Spaghetti("For f*ck's sake we are tired of NBTPacket, fucking don't use it for new work or i'll cut your face down")
public class NBTPacket extends RecordablePacket {

    NBTTagCompound buffer;
    int x;
    int y;
    int z;

    public NBTPacket() {
    }

    public NBTPacket(NBTTagCompound nbt, BlockPos pos) {

        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();

        buffer = (nbt);

    }

    @Override
    public void fromBits(LeafiaBuf buf) {

        x = buf.readInt();
        y = buf.readInt();
        z = buf.readInt();

        buffer = buf.readNBT();
    }

    @Override
    public void toBits(LeafiaBuf buf) {

        buf.writeInt(x);
        buf.writeInt(y);
        buf.writeInt(z);

        buf.writeNBT(buffer);
    }

    public static class Handler implements IMessageHandler<NBTPacket, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NBTPacket m, MessageContext ctx) {
            Minecraft.getMinecraft().addScheduledTask(() -> {
                if (Minecraft.getMinecraft().world == null)
                    return;

                TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(m.x, m.y, m.z));

                {

                    NBTTagCompound nbt = m.buffer;

                    if (nbt != null) {
                        if (te instanceof INBTPacketReceiver)
                            ((INBTPacketReceiver) te).networkUnpack(nbt);
                    }

                }
            });

            return null;
        }
    }

}