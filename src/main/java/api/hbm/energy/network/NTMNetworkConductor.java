package api.hbm.energy.network;

import com.hbm.lib.ForgeDirection;
import com.llib.exceptions.LeafiaDevFlaw;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public interface NTMNetworkConductor<T extends NTMNetworkInstance<? extends NTMNetworkConductor<T>,? extends NTMNetworkMember>> extends NTMNetworkMember {
	T getNetwork();
	NTMNetworkConductor<T> setNetwork(T network);

	// fuck off
	default NTMNetworkConductor<T> assert_setNetwork(NTMNetworkInstance<?,?> network) { return setNetwork(_assertNetwork(network)); }
	@Nullable
	default T _castNetwork(NTMNetworkInstance<?,?> network) {
		try {
			return (T)network;
		} catch (ClassCastException e) {
			return null;
			//throw new LeafiaDevFlaw(this.getClass().getName()+": _castMember failed",e);
		}
	}
	default T _assertNetwork(NTMNetworkInstance<?,?> network) {
		try {
			return (T)network;
		} catch (ClassCastException e) {
			throw new LeafiaDevFlaw(this.getClass().getName()+": _assertNetwork failed",e);
		}
	}

	default int generateId() {
		return NTMNetworkInstance.generateIdFromTE((TileEntity)this);
	}

	/**
	 * @return Whether the conductor has mutliblock proxies which need to be taken into consideration for re-eval.
	 */
	default boolean needsRedirectionMap() { return false; }

	/**
	 * @return The list of positions for assoicated dummy blocks for this machine (assuming it's a multiblock)
	 * <p>Positions should be in world space, not relative offsets.
	 */
	default List<BlockPos> getMultiblockPositions() { return new ArrayList<>(); }

	/**
	 * Whether the link should be part of reeval when the network is changed.
	 * I.e. if this link should join any of the new networks (FALSE for switches that are turned off for example)
	 * @return
	 */
	public default boolean canReevaluate() {
		return !((TileEntity) this).isInvalid();
	}
	/**
	 * When a link leaves the network, the net has to manually calculate the resulting networks.
	 * Each link has to decide what other links will join the same net.
	 * @param snapshot
	 */
	default void reevaluate(T snapshot) {
		for(BlockPos pos : getConnectionPoints()) {
			NTMNetworkConductor<T> neighbor = snapshot.getConductorFromId(NTMNetworkInstance.generateIdFromPos(pos));

			if(neighbor != null && this.canReevaluate() && neighbor.canReevaluate()) {

				if(neighbor.getNetwork() != null) {

					//neighbor net and no self net
					if(this.getNetwork() == null) {
						neighbor.getNetwork().assert_assignConductor(this);
						//neighbor net and self net
					} else {
						this.getNetwork().assert_joinFrom(neighbor.getNetwork());
					}
				}
			}
		}
	}
	default void assert_reevaluate(NTMNetworkInstance<?,?> network) { reevaluate(_assertNetwork(network)); }
	/**
	 * Creates a list of positions for the re-eval process. In short - what positions should be considered as connected.
	 * Also used by pylons to quickly figure out what positions to connect to.
	 * DEFAULT: Connects to all six neighboring blocks.
	 * @return
	 */
	public default List<BlockPos> getConnectionPoints() {

		List<BlockPos> pos = new ArrayList();
		TileEntity tile = (TileEntity) this;

		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			pos.add(tile.getPos().add(dir.offsetX, dir.offsetY, dir.offsetZ));
		}

		return pos;
	}
}