package com.leafia.contents.network.spk_cable;

import api.hbm.energy.network.NTMNetworkInstance;
import com.hbm.interfaces.ILaserable;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.Tuple.Pair;
import com.leafia.contents.network.spk_cable.network.ISPKConductor;
import com.leafia.contents.network.spk_cable.network.ISPKMember;
import com.leafia.contents.network.spk_cable.network.SPKNet;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class SPKCableTE extends TileEntity implements ILaserable, ISPKConductor, ITickable {
	protected SPKNet nw;
	@Override
	public SPKNet getNetwork() {
		return nw;
	}
	@Override
	public SPKCableTE setNetwork(SPKNet network) {
		nw = network;
		return this;
	}
	/**
	 * Only update until a power net is formed, in >99% of the cases it should be the first tick. Everything else is handled by neighbors and the net itself.
	 */
	public boolean canUpdate() {
		return (this.nw == null || !this.nw.isValid()) && !this.isInvalid();
	}
	@Override
	public void update() {

		if(!world.isRemote && canUpdate()) {

			//we got here either because the net doesn't exist or because it's not valid, so that's safe to assume
			this.setNetwork(null);

			this.connect();

			if(this.getNetwork() == null) {
				SPKNet net = new SPKNet();
				net.assignConductor(this);
				this.setNetwork(net);
			}
		} else if (world.isRemote && isCorner) {
			for (EffectLink link : links) {
				boolean allow = getFromFacing(link.direction);
				if (link.link != null) {
					if (!allow) link.reset();
					else {
						TileEntity te = world.getTileEntity(link.link);
						if (te != null && !te.isInvalid()) {
							if (te instanceof SPKCableTE && !link.nonCable) {
								SPKCableTE cab = (SPKCableTE)te;
								if (!pos.equals(cab.links[link.direction.getOpposite().ordinal()].link))
									link.reset();
							} else if (!link.nonCable)
								link.reset();
						} else
							link.reset();
					}
				}
				for (int d = 1; true; d++) {
					TileEntity te = world.getTileEntity(pos.offset(link.direction,d));
					if (te instanceof SPKCableTE && !te.isInvalid()) {
						SPKCableTE cab = (SPKCableTE)te;
						if (!cab.isCorner) continue;
						if (link.link == cab.getPos() && !link.nonCable) break;
						if (cab.getFromFacing(link.direction.getOpposite())) {
							link.setLink(cab);
							break;
						}
					} else if (te instanceof ILaserable && !te.isInvalid()) {
						if (link.link == te.getPos() && link.nonCable) break;
						link.setLinkNonCable(te.getPos());
						break;
					} else break;
				}
			}
		}
	}
	public boolean pX = false;
	public boolean pY = false;
	public boolean pZ = false;
	public boolean nX = false;
	public boolean nY = false;
	public boolean nZ = false;
	public boolean isCorner = false;
	@SideOnly(Side.CLIENT)
	protected boolean getFromFacing(EnumFacing facing) {
		switch(facing) {
			case EAST: return pX;
			case UP: return pY;
			case SOUTH: return pZ;
			case WEST: return nX;
			case DOWN: return nY;
			case NORTH: return nZ;
			default: return false;
		}
	}

	protected void connect() {
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			TileEntity te = world.getTileEntity(pos.add(dir.offsetX, dir.offsetY, dir.offsetZ));
			if(te instanceof ISPKConductor) {
				ISPKConductor conductor = (ISPKConductor)te;

				if(this.getNetwork() == null && conductor.getNetwork() != null) {
					conductor.getNetwork().assignConductor(this);
				}
				if(this.getNetwork() != null && conductor.getNetwork() != null && this.getNetwork() != conductor.getNetwork()) {
					conductor.getNetwork().joinFrom(this.getNetwork());
				}
			}
		}
	}
	@Override
	public void invalidate() {
		super.invalidate();

		if(!world.isRemote) {
			if(this.nw != null) {
				this.nw.reevaluate();
				this.nw = null;
			}
		}
	}

	public static class EffectLink {
		BlockPos link = null;
		boolean emit = false;
		boolean nonCable = false;
		final EnumFacing direction;
		final SPKCableTE self;
		EffectLink(SPKCableTE self,int i) {
			this.self = self;
			direction = EnumFacing.values()[i];
		}
		public void reset() {
			link = null;
			emit = false;
			nonCable = false;
		}
		public void setLink(SPKCableTE other) {
			reset();
			link = other.getPos();
			EffectLink lnk = other.links[direction.getOpposite().ordinal()];
			if (lnk.link == null || !lnk.link.equals(self.getPos())) {
				lnk.reset();
				lnk.link = self.getPos();
				emit = true;
			}
		}
		public void setLinkNonCable(BlockPos pos) {
			reset();
			link = pos;
			nonCable = true;
			emit = true;
		}
	}
	public EffectLink[] links = new EffectLink[EnumFacing.values().length];
	public SPKCableTE() {
		for (int i = 0; i < EnumFacing.values().length; i++)
			links[i] = new EffectLink(this,i);
	}
	@Override
	public void addEnergy(long energy,EnumFacing dir) {
		if (nw != null) {
			nw.cleanup();
			List<Pair<ILaserable,EnumFacing>> targets = new ArrayList<>();
			for (ISPKMember m : nw.getMembers()) {
				if (!(m instanceof ILaserable)) continue; // what
				ILaserable member = (ILaserable)m;
				TileEntity te = (TileEntity)m;

				// return if the device's behind this
				if (te.getPos().equals(this.getPos().offset(dir.getOpposite()))) continue;

				// don't feed onto itself if that's even possible
				if (m instanceof ISPKConductor) continue;

				for (EnumFacing face : EnumFacing.values()) {
					int id = NTMNetworkInstance.generateIdFromPos(te.getPos().offset(face));
					if (nw.getConductorMap().containsKey(id) || nw.getConductorRedirections().containsKey(id)) {
						EnumFacing faceIn = face.getOpposite();
						if (member.isInputPreferable(faceIn)) {
							targets.add(new Pair<>(member,faceIn));
							break;
						}
					}
				}
			}
			for (Pair<ILaserable,EnumFacing> target : targets)
				target.getA().addEnergy(energy/targets.size(),target.getB());
		}
	}
}
