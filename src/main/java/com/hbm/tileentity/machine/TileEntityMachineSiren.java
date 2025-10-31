package com.hbm.tileentity.machine;

import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlEventSystem;
import com.hbm.inventory.control_panel.IControllable;
import com.hbm.items.machine.ItemCassette;
import com.hbm.items.machine.ItemCassette.SoundType;
import com.hbm.items.machine.ItemCassette.TrackType;
import com.hbm.lib.InventoryHelper;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TESirenPacket;
import com.hbm.tileentity.leafia.TileEntityMachineSirenSounder;
import gnu.trove.set.hash.TIntHashSet;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import pl.asie.computronics.api.audio.AudioPacket;
import pl.asie.computronics.api.audio.IAudioReceiver;
import pl.asie.computronics.audio.AudioUtils;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static pl.asie.computronics.reference.Capabilities.AUDIO_RECEIVER_CAPABILITY;
@Optional.InterfaceList({
		@Optional.Interface(iface = "pl.asie.computronics.api.audio.IAudioReceiver", modid = "computronics")
})
public class TileEntityMachineSiren extends TileEntity implements IAudioReceiver, ITickable, IControllable {

	public ItemStackHandler inventory;
	
	///private static final int[] slots_top = new int[] { 0 };
	///private static final int[] slots_bottom = new int[] { 0 };
	//private static final int[] slots_side = new int[] { 0 };
	
	public boolean lock = false;
	public boolean ctrlActive = false;
	public boolean speakerMode = false;
	
	private String customName;
	private List<TileEntityMachineSirenSounder> sounders = new ArrayList<>();
	final boolean computronics;

	public TileEntityMachineSiren() {
		computronics = Loader.isModLoaded("computronics");
		for (int i = 0; i < 4; i++)
			sounders.add(new TileEntityMachineSirenSounder(this,i));
		inventory = new ItemStackHandler(1){
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}
	
	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.siren";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}
	
	public void setCustomName(String name) {
		this.customName = name;
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		if (speakerMode) return false;
		if(world.getTileEntity(pos) != this)
		{
			return false;
		}else{
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <=64;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			boolean spk = false;
			for (EnumFacing face : EnumFacing.VALUES) {
				TileEntity ate = world.getTileEntity(pos.offset(face));
				if (computronics && ate != null && ate.hasCapability(AUDIO_RECEIVER_CAPABILITY,face.getOpposite())) {
					spk = true;
					InventoryHelper.dropInventoryItems(world,pos,this);
					break;
				}
			}
			speakerMode = spk;

			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			
			if(getCurrentType().name().equals(TrackType.NULL.name())) {
				PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());
				return;
			}
			
			boolean active = ctrlActive || world.getRedstonePowerFromNeighbors(pos) > 0;
			if (speakerMode)
				active = false;
			
			if(getCurrentType().getType().name().equals(SoundType.LOOP.name())) {
				
				PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, active), world.provider.getDimension());
			} else {
				
				if(!lock && active) {
					lock = true;
					PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());
					PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, true), world.provider.getDimension());
				}
				
				if(lock && !active) {
					lock = false;
				}
			}
		}
	}

	@Override
	public void onChunkUnload() {
		if(!world.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());		
		}
	}

	@Override
	public void invalidate() {
		if(!world.isRemote) {
			int id = Arrays.asList(TrackType.values()).indexOf(getCurrentType());
			PacketDispatcher.wrapper.sendToDimension(new TESirenPacket(pos.getX(), pos.getY(), pos.getZ(), id, false), world.provider.getDimension());		
		}
		ControlEventSystem.get(world).removeControllable(this);
		for (TileEntityMachineSirenSounder sounder : sounders)
			sounder.invalidate();
		sounders.clear();
		super.invalidate();
	}
	
	public TrackType getCurrentType() {
		if(inventory.getStackInSlot(0).getItem() instanceof ItemCassette) {
			return TrackType.getEnum(inventory.getStackInSlot(0).getItemDamage());
		}
		
		return TrackType.NULL;
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}

	@Override
	public void receiveEvent(BlockPos from, ControlEvent e){
		if(e.name.equals("siren_set_state")){
			ctrlActive = e.vars.get("isOn").getBoolean();
		}
	}

	@Override
	public List<String> getInEvents(){
		return Arrays.asList("siren_set_state");
	}

	@Override
	public BlockPos getControlPos(){
		return getPos();
	}

	@Override
	public World getControlWorld(){
		return getWorld();
	}
	
	@Override
	public void validate(){
		super.validate();
		for (TileEntityMachineSirenSounder sounder : sounders)
			sounder.validate();
		ControlEventSystem.get(world).addControllable(this);
	}
	// TAPES //
	@Override
	@Optional.Method(modid="computronics")
	public World getSoundWorld() {
		return world;
	}

	@Override
	@Optional.Method(modid="computronics")
	public Vec3d getSoundPos() {
		return new Vec3d(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D);
	}

	@Override
	@Optional.Method(modid="computronics")
	public int getSoundDistance() {
		return 128;
	}
	private final TIntHashSet packetIds = new TIntHashSet();
	private long idTick = -1;
	@Override
	@Optional.Method(modid="computronics")
	public void receivePacket(AudioPacket packet, @Nullable EnumFacing direction) {
		if(!hasWorld() || idTick == world.getTotalWorldTime()) {
			if(packetIds.contains(packet.id)) {
				return;
			}
		} else {
			idTick = world.getTotalWorldTime();
			packetIds.clear();
		}
		packetIds.add(packet.id);
		for (TileEntityMachineSirenSounder sounder : sounders)
			packet.addReceiver(sounder); // fuck it, I ain't coding a whole new packet handler just to make it louder
	}

	@Override
	@Optional.Method(modid="computronics")
	public String getID() {
		return AudioUtils.positionId(getPos());
	}

	@Override
	@Optional.Method(modid="computronics")
	public boolean connectsAudio(EnumFacing enumFacing) {
		return true;
	}
}
