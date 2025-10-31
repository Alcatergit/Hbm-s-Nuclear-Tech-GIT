package com.leafia.contents.machines.reactors.pwr.blocks.wreckage;

import com.leafia.dev.Sus;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class PWRMeshedWreckEntity extends TileEntity implements LeafiaPacketReceiver {
	public PWRMeshedWreck wreckType = null;
	public String resourceLocation = null;
	public int meta = 0;
	// bit structure:
	//                        |Erosion
	// [B A 9 8,7 6 5 4][3 2 1|E,E|s s s]
	// |Burn timer                |Scorch
	// Actually I lied That one's scrapped
	//                     |Erosion
	// It's actually [v v v|E,E|s s s]
	//      Variation|         |Scorch
	public int scorch = 0; // 0~7
	public int variation = 0; // 0~7
	public PWRMeshedWreck.Erosion erosion = PWRMeshedWreck.Erosion.NORMAL;

	public byte toBits() {
		return (byte)(scorch+erosion.getBits()+(variation<<5));
	}
	public PWRMeshedWreckEntity fromBits(int bits) {
		scorch = bits&0b111;
		erosion = PWRMeshedWreck.Erosion.values()[(bits>>3)&0b11];
		variation = (bits>>5)&0b111;
		return this;
	}


	public PWRMeshedWreckEntity() {}
	public PWRMeshedWreckEntity(PWRMeshedWreck wreckType) {
		this.wreckType = wreckType;
	}
	// that's all fuck youi


	// (1 hour later VV) ok maybe not...
	@Override
	@Sus("Might cause ClassCastException??")
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("block"))
			resourceLocation = compound.getString("block");
		if (compound.hasKey("meta"))
			meta = compound.getInteger("meta");
		super.readFromNBT(compound);
		wreckType = (PWRMeshedWreck)getBlockType(); // Here (Moving it to validate() causes infinite loop)
		if (compound.hasKey("detail"))
			fromBits(compound.getByte("detail"));
		//LeafiaPacket._start(this).__write(0,resourceLocation).__write(1,meta).__sendToAffectedClients();
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (resourceLocation != null)
			compound.setString("block",resourceLocation);
		compound.setInteger("meta",meta);
		compound.setByte("detail",toBits());
		return super.writeToNBT(compound);
	}
	@Override
	public void markDirty() {
		super.markDirty();
		if (this.wreckType != null)
			LeafiaPacket._start(this).__write(0,resourceLocation).__write(1,meta).__write(2,toBits()).__sendToAffectedClients();
	}
	public PWRMeshedWreck.Variation getVariation() {
		return wreckType.getVariations().get(erosion,variation);
	}

	@Override
	public String getPacketIdentifier() {
		return "PWRWreckBase";
	}
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0: this.resourceLocation = (String)value; break;
			case 1: this.meta = (int)value; break;
			case 2: this.fromBits((byte)value); break;
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {}
	@Override
	public double affectionRange() {
		return 2048; // Crisps computer :D
	}
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		LeafiaPacket._start(this).__write(0,resourceLocation).__write(1,meta).__write(2,toBits()).__sendToClient(plr);
	}
}
