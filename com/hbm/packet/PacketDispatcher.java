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
		//Syncing fluid tanks
		wrapper.registerMessage(FluidTankPacket.Handler.class, FluidTankPacket.class, i++, Side.CLIENT);
		//Assembler Recipe Sync Packet, so clients can see the right recipes
		wrapper.registerMessage(AssemblerRecipeSyncPacket.Handler.class, AssemblerRecipeSyncPacket.class, i++, Side.CLIENT);
		//Universal package for machine gauges and states
		wrapper.registerMessage(AuxGaugePacket.Handler.class, AuxGaugePacket.class, i++, Side.CLIENT);
		//Universal button packet
		wrapper.registerMessage(AuxButtonPacket.Handler.class, AuxButtonPacket.class, i++, Side.SERVER);
		//For handling fluid tank type updates
		wrapper.registerMessage(FluidTypePacketTest.Handler.class, FluidTypePacketTest.class, i++, Side.CLIENT);
		//Fluid pipe type update for rendering
		wrapper.registerMessage(TEFluidTypePacketTest.Handler.class, TEFluidTypePacketTest.class, i++, Side.CLIENT);
		//Client request update packet in case they load the new tile entity and the server doesn't know it needs an update and doesn't send any packets
		wrapper.registerMessage(ClientRequestUpdatePacket.Handler.class, ClientRequestUpdatePacket.class, i++, Side.SERVER);
		//Turret basic packet for making the client has the right ammo amounts
		wrapper.registerMessage(TETurretPacket.Handler.class, TETurretPacket.class, i++, Side.CLIENT);
		//CIWS has a really long range, so stuff might not even exist on client, so rotation needs to be sent
		wrapper.registerMessage(TETurretCIWSPacket.Handler.class, TETurretCIWSPacket.class, i++, Side.CLIENT);
		//Packet for updating the bomber flying sound, I think
		wrapper.registerMessage(LoopedEntitySoundPacket.Handler.class, LoopedEntitySoundPacket.class, i++, Side.CLIENT);
		//Missile type for render sync with client
		wrapper.registerMessage(TEMissilePacket.Handler.class, TEMissilePacket.class, i++, Side.CLIENT);
		//Packet for sending designator data to server
		wrapper.registerMessage(ItemDesignatorPacket.Handler.class, ItemDesignatorPacket.class, i++, Side.SERVER);
		//New particle packet
		wrapper.registerMessage(EnumParticlePacket.Handler.class, EnumParticlePacket.class, i++, Side.CLIENT);
		//Gun firing packet
		wrapper.registerMessage(GunButtonPacket.Handler.class, GunButtonPacket.class, i++, Side.SERVER);
	}

}
