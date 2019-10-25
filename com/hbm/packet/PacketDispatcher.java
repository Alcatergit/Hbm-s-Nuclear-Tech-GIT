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
	}

}
