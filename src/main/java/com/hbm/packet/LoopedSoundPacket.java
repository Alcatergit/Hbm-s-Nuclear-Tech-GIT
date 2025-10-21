package com.hbm.packet;

import com.hbm.lib.HBMSoundHandler;
import com.hbm.sound.SoundLoopAssembler;
import com.hbm.sound.SoundLoopBroadcaster;
import com.hbm.sound.SoundLoopCentrifuge;
import com.hbm.sound.SoundLoopChemplant;
import com.hbm.sound.SoundLoopTurbofan;
import com.hbm.sound.SoundLoopFel;
import com.hbm.tileentity.machine.TileEntityBroadcaster;
import com.hbm.tileentity.machine.TileEntityMachineAssembler;
import com.hbm.tileentity.machine.TileEntityMachineCentrifuge;
import com.hbm.tileentity.machine.TileEntityMachineChemplant;
import com.hbm.tileentity.machine.TileEntityMachineChemfac;
import com.hbm.tileentity.machine.TileEntityMachineGasCent;
import com.hbm.tileentity.machine.TileEntityMachineTurbofan;
import com.hbm.tileentity.machine.TileEntityMachineMiningLaser;
import com.hbm.tileentity.machine.TileEntityFEL;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class LoopedSoundPacket implements IMessage {

	int x;
	int y;
	int z;

	public LoopedSoundPacket()
	{
		
	}

	public LoopedSoundPacket(BlockPos pos)
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}
	
	public LoopedSoundPacket(int xPos, int yPos, int zPos){
		x = xPos;
		y = yPos;
		z = zPos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		x = buf.readInt();
		y = buf.readInt();
		z = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(x);
		buf.writeInt(y);
		buf.writeInt(z);
	}

	public static class Handler implements IMessageHandler<LoopedSoundPacket, IMessage> {
		
		@Override
		//Tamaized, I love you!
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(LoopedSoundPacket m, MessageContext ctx) {
			
			Minecraft.getMinecraft().addScheduledTask(() -> {
				BlockPos pos = new BlockPos(m.x, m.y, m.z);
				TileEntity te = Minecraft.getMinecraft().world.getTileEntity(pos);
				
				if (te != null && te instanceof TileEntityMachineChemplant plant) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopChemplant.list.size(); i++)  {
                        if (SoundLoopChemplant.list.get(i).getTE() == te && !SoundLoopChemplant.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && plant.isProgressing)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopChemplant(HBMSoundHandler.chemplantOperate, te));
				}

				if (te != null && te instanceof TileEntityMachineChemfac fac) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopChemplant.list.size(); i++)  {
                        if (SoundLoopChemplant.list.get(i).getTE() == te && !SoundLoopChemplant.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && fac.isProgressing)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopChemplant(HBMSoundHandler.chemplantOperate, te));
				}

				if (te != null && te instanceof TileEntityFEL fel) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopFel.list.size(); i++)  {
                        if (SoundLoopFel.list.get(i).getTE() == te && !SoundLoopFel.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && fel.isOn)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopFel(HBMSoundHandler.fel, te));
				}

				if (te != null && te instanceof TileEntityMachineMiningLaser la) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopFel.list.size(); i++)  {
                        if (SoundLoopFel.list.get(i).getTE() == te && !SoundLoopFel.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && la.isOn)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopFel(HBMSoundHandler.fel, te));
				}
				
				if (te != null && te instanceof TileEntityMachineAssembler ass) {

                    boolean flag = true;
                    for (int i = 0; i < SoundLoopAssembler.list.size(); i++) {
                        if (SoundLoopAssembler.list.get(i).getTE() == te && !SoundLoopAssembler.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
                    }

                    if (flag && te.getWorld().isRemote && ass.isProgressing)
                        Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopAssembler(HBMSoundHandler.assemblerOperate, te));
                }

				if (te != null && te instanceof TileEntityMachineTurbofan fan) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopTurbofan.list.size(); i++)  {
                        if (SoundLoopTurbofan.list.get(i).getTE() == te && !SoundLoopTurbofan.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && fan.isRunning)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopTurbofan(HBMSoundHandler.turbofanOperate, te));
				}
				
				if (te != null && te instanceof TileEntityBroadcaster) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopBroadcaster.list.size(); i++)  {
                        if (SoundLoopBroadcaster.list.get(i).getTE() == te && !SoundLoopBroadcaster.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					int j = te.getPos().getX() + te.getPos().getY() + te.getPos().getZ();
					int rand = Math.abs(j) % 3 + 1;
					SoundEvent sound = switch (rand) {
                        case 1 -> HBMSoundHandler.broadcast1;
                        case 2 -> HBMSoundHandler.broadcast2;
                        case 3 -> HBMSoundHandler.broadcast3;
                        default -> HBMSoundHandler.broadcast1;
                    };

                    if(flag && te.getWorld().isRemote)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopBroadcaster(sound, te));
				}
				
				if (te != null && te instanceof TileEntityMachineCentrifuge cen) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopCentrifuge.list.size(); i++)  {
                        if (SoundLoopCentrifuge.list.get(i).getTE() == te && !SoundLoopCentrifuge.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					
					if(flag && te.getWorld().isRemote && cen.isProgressing)
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopCentrifuge(HBMSoundHandler.centrifugeOperate, te));
				}
				
				if (te != null && te instanceof TileEntityMachineGasCent gasCent) {
					
					boolean flag = true;
					for(int i = 0; i < SoundLoopCentrifuge.list.size(); i++)  {
                        if (SoundLoopCentrifuge.list.get(i).getTE() == te && !SoundLoopCentrifuge.list.get(i).isDonePlaying()) {
                            flag = false;
                            break;
                        }
					}
					
					if(flag && te.getWorld().isRemote && gasCent.isProgressing && !gasCent.hasMuffler())
						Minecraft.getMinecraft().getSoundHandler().playSound(new SoundLoopCentrifuge(HBMSoundHandler.centrifugeOperate, te));
				}
			});

			return null;
		}
	}
}
