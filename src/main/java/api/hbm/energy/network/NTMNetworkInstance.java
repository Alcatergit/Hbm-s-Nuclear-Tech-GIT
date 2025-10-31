package api.hbm.energy.network;

import com.hbm.config.GeneralConfig;
import com.llib.exceptions.LeafiaDevFlaw;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class NTMNetworkInstance<C extends NTMNetworkConductor<? extends NTMNetworkInstance<C,M>>,M extends NTMNetworkMember> {

	protected boolean valid = true;

	/**
	 * @return The ID -- Conductor map for use in the class
	 */
	abstract public HashMap<Integer,C> getConductorMap();

	/**
	 * @return The ID redirection map useful for handling multiblock tileentity machines.
	 */
	abstract public HashMap<Integer,Integer> getConductorRedirections();
	public static int generateIdFromTE(TileEntity te) { return generateIdFromPos(te.getPos()); }
	public static int generateIdFromPos(BlockPos pos) {
		final int prime = 27644437; // must be this large to minimize localized collisions
		int result = 1;
		result = prime * result + pos.getX();
		result = prime * result + pos.getY();
		result = prime * result + pos.getZ();
		return result;
	}
	public C getConductorFromId(int id) {
		C conductor = getConductorMap().get(id);
		if (conductor == null) {
			if (getConductorRedirections().containsKey(id))
				conductor = getConductorMap().get(getConductorRedirections().get(id));
		}
		return conductor;
	}

	protected final List<M> members = new ArrayList<>();
	public void cleanup() {
		getMembers().removeIf(x ->
				x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid()
		);
	}

	/**
	 * Joins the <tt>other</tt> network into <tt>this</tt> network.
	 * @param other The network to join into this network
	 * @return Itself, for chaining
	 */
	public NTMNetworkInstance<C,M> joinFrom(NTMNetworkInstance<C,M> other) {
		if(other == this)
			return this; //wtf?!

		for (C conductor : other.getConductors())
			assignConductor(conductor);
		other.getConductors().clear();

		for (M member : other.getMembers())
			addMember(member);

		other.destroy();
		return this;
	}
	public NTMNetworkInstance<C,M> assert_joinFrom(NTMNetworkInstance<?,?> other) { return joinFrom(_assertNetwork(other)); }

	/**
	 * Adds the conductor to the list and removes it from network it was previously assigned to.
	 * Use this for devices (most likely cables) that consist this network.
	 * @param conductor The conductor to add
	 * @return Itself, for chaining
	 */
	public NTMNetworkInstance<C,M> assignConductor(C conductor) {
		if (conductor.getNetwork() != null)
			conductor.getNetwork().removeConductor(conductor);

		conductor.assert_setNetwork(this);
		int identity = conductor.generateId();
		getConductorMap().put(identity, conductor);

		if(conductor.needsRedirectionMap()) {
			for (BlockPos pos : conductor.getMultiblockPositions())
				getConductorRedirections().put(generateIdFromPos(pos),identity);
		}
		return this;
	}
	public NTMNetworkInstance<C,M> assert_assignConductor(NTMNetworkConductor<?> conductor) { return assignConductor(_assertConductor(conductor)); }

	/**
	 * Removes the conductor from the list.
	 * Use this for devices (most likely cables) that consist this network.
	 * @param conductor The conductor to remove
	 * @return Itself, for chaining
	 */
	public NTMNetworkInstance<C,M> removeConductor(C conductor) {
		conductor.setNetwork(null);
		int identity = conductor.generateId();
		getConductorMap().remove(identity);

		if(conductor.needsRedirectionMap()) {
			for (BlockPos pos : conductor.getMultiblockPositions())
				getConductorRedirections().remove(generateIdFromPos(pos));
		}
		return this;
	}
	public NTMNetworkInstance<C,M> assert_removeConductor(NTMNetworkConductor<?> conductor) { return removeConductor(_assertConductor(conductor)); }

	/**
	 * Adds the member to the list.
	 * Use this for devices adjacent to conductors.
	 * @param member The member to add
	 * @return Itself, for chaining
	 */
	public NTMNetworkInstance<C,M> addMember(M member) { getMembers().add(member); return this; }
	public NTMNetworkInstance<C,M> assert_addMember(NTMNetworkMember member) { return addMember(_assertMember(member)); }

	/**
	 * Removes the member from the list.
	 * Use this for devices adjacent to conductors.
	 * @param member The member to remove
	 * @return Itself, for chaining
	 */
	public NTMNetworkInstance<C,M> removeMember(M member) { getMembers().remove(member); return this; }
	public NTMNetworkInstance<C,M> assert_removeMember(NTMNetworkMember member) { return removeMember(_assertMember(member)); }

	/**
	 * @param member The member to test
	 * @return Whether the network contains the member or not
	 */
	public boolean containsMember(M member) { return getMembers().contains(member); }
	public boolean assert_containsMember(NTMNetworkMember member) { return containsMember(_assertMember(member)); }

	public void destroy() {
		this.valid = false;
		this.members.clear();
		for(C conductor : getConductors())
			conductor.setNetwork(null);
		this.getConductors().clear();
	};

	/**
	 * When a link is removed, instead of destroying the network, causing it to be recreated from currently loaded conductors,
	 * we re-evaluate it, creating new nets based on the previous links.
	 */
	public void reevaluate() {
		if(!GeneralConfig.enableReEval) {
			this.destroy();
			return;
		}
		NTMNetworkInstance<C,M> snapshot = createSnapshot();
		for (C conductor : getConductors())
			removeConductor(conductor);

		for (C conductor : snapshot.getConductors()) {
			conductor.setNetwork(null); // just to be safe, although removeConductor already does it
			conductor.assert_reevaluate(snapshot);
			if (conductor.getNetwork() == null) {
				try {
					conductor.assert_setNetwork(this.getClass().newInstance().assignConductor(conductor));
				} catch (IllegalAccessException | InstantiationException | RuntimeException exception) {
					throw new LeafiaDevFlaw(this.getClass().getName(),exception);
				}
			}
		}
	}

	/**
	 * @return A copy of <tt>this</tt> network, with its data also copied <u>in a way so
	 * changes made on <tt>this</tt> network does <b>not</b> affect the snapshot</u>
	 * <p><br>Should not affect conductors/members associated with this network, for example
	 * reassigning conductors of <tt>this</tt> into the snapshot shouldn't be done.
	 */
	public NTMNetworkInstance<C,M> createSnapshot() {
		try {
			NTMNetworkInstance<C,M> snapshot = this.getClass().newInstance();
			snapshot.getConductorMap().putAll(getConductorMap());
			snapshot.getConductorRedirections().putAll(getConductorRedirections());
			snapshot.getMembers().addAll(this.getMembers());
			return snapshot;
		} catch (IllegalAccessException | InstantiationException | RuntimeException exception) {
			throw new LeafiaDevFlaw(this.getClass().getName()+": Snapshot creation failed",exception);
		}
	}

	public boolean isValid() { return valid; };

	public List<C> getConductors() {
		return new ArrayList<>(getConductorMap().values());
	}
	public List<M> getMembers() {
		return members;
	}

	@Nullable
	public C _castConductor(NTMNetworkConductor<?> conductor) {
		try {
			return (C)conductor;
		} catch (ClassCastException e) {
			return null;
			//throw new LeafiaDevFlaw(this.getClass().getName()+": _castConductor failed",e);
		}
	}
	public C _assertConductor(NTMNetworkConductor<?> conductor) {
		try {
			return (C)conductor;
		} catch (ClassCastException e) {
			throw new LeafiaDevFlaw(this.getClass().getName()+": _assertConductor failed",e);
		}
	}

	@Nullable
	public M _castMember(NTMNetworkMember member) {
		try {
			return (M)member;
		} catch (ClassCastException e) {
			return null;
			//throw new LeafiaDevFlaw(this.getClass().getName()+": _castMember failed",e);
		}
	}
	public M _assertMember(NTMNetworkMember member) {
		try {
			return (M)member;
		} catch (ClassCastException e) {
			throw new LeafiaDevFlaw(this.getClass().getName()+": _assertMember failed",e);
		}
	}

	@Nullable
	public NTMNetworkInstance<C,M> _castNetwork(NTMNetworkInstance<?,?> network) {
		try {
			return (NTMNetworkInstance<C,M>)network;
		} catch (ClassCastException e) {
			return null;
			//throw new LeafiaDevFlaw(this.getClass().getName()+": _castMember failed",e);
		}
	}
	public NTMNetworkInstance<C,M> _assertNetwork(NTMNetworkInstance<?,?> network) {
		try {
			return (NTMNetworkInstance<C,M>)network;
		} catch (ClassCastException e) {
			throw new LeafiaDevFlaw(this.getClass().getName()+": _assertNetwork failed",e);
		}
	}
}
