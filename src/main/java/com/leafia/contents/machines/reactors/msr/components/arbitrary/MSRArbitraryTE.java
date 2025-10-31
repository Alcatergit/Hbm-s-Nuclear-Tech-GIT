package com.leafia.contents.machines.reactors.msr.components.arbitrary;

import com.leafia.contents.machines.reactors.msr.components.MSRTEBase;
import com.leafia.dev.container_utility.LeafiaPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

public class MSRArbitraryTE extends MSRTEBase {
	public ItemStackHandler inventory;
	public MSRArbitraryTE() {
		inventory = new ItemStackHandler(1);
	}
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	@Override
	public String getPacketIdentifier() {
		return "MSRArbitrary";
	}
	public LeafiaPacket generateSyncPacket() {
		return LeafiaPacket._start(this).__write((byte)0,writeToNBT(new NBTTagCompound()));
	}
	public void syncLocals() {
		generateSyncPacket().__sendToAffectedClients();//.__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK).__sendToAllInDimension();
	}
	@Override
	public void markDirty() {
		super.markDirty();
		if (!world.isRemote)
			syncLocals();
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		super.onReceivePacketLocal(key,value);
		if (key == 0) {
			if (value instanceof NBTTagCompound nbt)
				readFromNBT(nbt);
		}
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) { }
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		generateSyncPacket().__sendToClient(plr);
	}
}
