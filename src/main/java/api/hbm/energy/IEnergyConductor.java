package api.hbm.energy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.hbm.lib.ForgeDirection;

import net.minecraft.util.math.BlockPos;
import net.minecraft.tileentity.TileEntity;

/**
 * For compatible cables with no buffer, using the IPowertNet. You can make your own cables with IEnergyConnector as well, but they won't join their power network.
 * @author hbm
 */
public interface IEnergyConductor extends IEnergyConnector {

	IPowerNet getPowerNet();
	
	void setPowerNet(IPowerNet network);
	
	/**
	 * A unique identifier for every conductor tile. Used to prevent duplicates when loading previously persistent unloaded tiles.
	 * @return
	 */
	default int getIdentity() {
		return getIdentityFromTile((TileEntity) this);
	}
	
	static int getIdentityFromTile(TileEntity te) {
		return getIdentityFromPos(te.getPos());
	}
	
	static int getIdentityFromPos(BlockPos pos) {
		final int prime = 27644437; // must be this large to minimize localized collisions
		int result = 1;
		result = prime * result + pos.getX();
		result = prime * result + pos.getY();
		result = prime * result + pos.getZ();
		return result;
	}
	
	/**
	 * Whether the link should be part of reeval when the network is changed.
	 * I.e. if this link should join any of the new networks (FALSE for switches that are turned off for example)
	 * @return
	 */
	default boolean canReevaluate() {
		return !((TileEntity) this).isInvalid();
	}
	
	/**
	 * When a link leaves the network, the net has to manually calculate the resulting networks.
	 * Each link has to decide what other links will join the same net.
	 * @param copy
	 */
	default void reevaluate(HashMap<Integer, IEnergyConductor> copy, HashMap<Integer, Integer> proxies) {

		for(BlockPos pos : getConnectionPoints()) {
			int id = IEnergyConductor.getIdentityFromPos(pos);
			
			IEnergyConductor neighbor = copy.get(id);
			
			if(neighbor == null) {
				Integer newId = proxies.get(id);
				
				if(newId != null) {
					neighbor = copy.get(newId);
				}
			}
			
			if(neighbor != null && this.canReevaluate() && neighbor.canReevaluate()) {
				
				if(neighbor.getPowerNet() != null) {
					
					//neighbor net and no self net
					if(this.getPowerNet() == null) {
						neighbor.getPowerNet().joinLink(this);
					//neighbor net and self net
					} else {
						this.getPowerNet().joinNetworks(neighbor.getPowerNet());
					}
				}	
			}
		}
	}
	
	/**
	 * Creates a list of positions for the re-eval process. In short - what positions should be considered as connected.
	 * Also used by pylons to quickly figure out what positions to connect to.
	 * DEFAULT: Connects to all six neighboring blocks.
	 * @return
	 */
	default List<BlockPos> getConnectionPoints() {

		List<BlockPos> pos = new ArrayList();
		TileEntity tile = (TileEntity) this;
		
		for(ForgeDirection dir : ForgeDirection.VALID_DIRECTIONS) {
			pos.add(tile.getPos().add(dir.offsetX, dir.offsetY, dir.offsetZ));
		}
		
		return pos;
	}
	
	/**
	 * Since isLoaded is only currently used for weeding out unwanted subscribers, and cables shouldn't (although technically can) be
	 * subscribers, we just default to true because I don't feel like wasting time implementing things that we don't actually need.
	 * Perhaps this indicates a minor flaw in the new API, but I physically lack the ability to worry about it.
	 */
	@Override
    default boolean isLoaded() {
		return true;
	}

	//TODO: check if this standard implementation doesn't break anything (it shouldn't but right now it's a bit redundant) also: remove duplicate implementations
	@Override
    default long transferPower(long power) {
		
		if(this.getPowerNet() == null)
			return power;
		
		return this.getPowerNet().transferPower(power);
	}
	
	/**
	 * Returns whether the conductor has mutliblock proxies which need to be taken into consideration for re-eval.
	 * @return
	 */
	default boolean hasProxies() {
		return false;
	}
	
	/**
	 * Returns the identities (position-based) of proxies which resolve into the conductor's own identity.
	 * @return
	 */
	default List<Integer> getProxies() {
		return new ArrayList();
	}
}
