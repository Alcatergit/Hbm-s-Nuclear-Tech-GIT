package api.hbm.energy;

import api.hbm.energy.network.NTMNetworkConductor;

/**
 * For compatible cables with no buffer, using the IPowertNet. You can make your own cables with IEnergyConnector as well, but they won't join their power network.
 * @author hbm
 */
public interface IEnergyConductor extends IEnergyConnector, NTMNetworkConductor<PowerNet> {
	/**
	 * Since isLoaded is only currently used for weeding out unwanted subscribers, and cables shouldn't (although technically can) be
	 * subscribers, we just default to true because I don't feel like wasting time implementing things that we don't actually need.
	 * Perhaps this indicates a minor flaw in the new API, but I physically lack the ability to worry about it.
	 */
	@Override
	public default boolean isLoaded() {
		return true;
	}

	//TODO: check if this standard implementation doesn't break anything (it shouldn't but right now it's a bit redundant) also: remove duplicate implementations
	@Override
	public default long transferPower(long power) {

		if(this.getNetwork() == null)
			return power;

		return this.getNetwork().transferPower(power);
	}
}
