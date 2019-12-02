package com.hbm.packet;

import com.hbm.lib.RefStrings;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketDispatcher {
	
	public static final SimpleNetworkWrapper wrapper = NetworkRegistry.INSTANCE.newSimpleChannel(RefStrings.MODID);
	
	public static final void registerPackets(){
		int i = 0;
		//PressPacket
		wrapper.registerMessage(TEPressPacket.Handler.class, TEPressPacket.class, i++, Side.CLIENT);
		//Send chunk radiation packet to individual players
		wrapper.registerMessage(RadSurveyPacket.Handler.class, RadSurveyPacket.class, i++, Side.CLIENT);
		//Packet for rendering of rubble
		wrapper.registerMessage(ParticleBurstPacket.Handler.class, ParticleBurstPacket.class, i++, Side.CLIENT);
		//Packet for updating assembler progress
		wrapper.registerMessage(TEAssemblerPacket.Handler.class, TEAssemblerPacket.class, i++, Side.CLIENT);
		//Updates electricity data
		wrapper.registerMessage(AuxElectricityPacket.Handler.class, AuxElectricityPacket.class, i++, Side.CLIENT);
		//Sounds packets
		wrapper.registerMessage(LoopedSoundPacket.Handler.class, LoopedSoundPacket.class, i++, Side.CLIENT);
		//Particle packet
		wrapper.registerMessage(AuxParticlePacket.Handler.class, AuxParticlePacket.class, i++, Side.CLIENT);
		//Chemplant packet
		wrapper.registerMessage(TEChemplantPacket.Handler.class, TEChemplantPacket.class, i++, Side.CLIENT);
	}

}
