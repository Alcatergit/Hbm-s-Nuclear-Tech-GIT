package api.hbm.energy;

import java.util.List;

/**
 * Not mandatory to use, but making your cables IPowerNet-compliant will allow them to connect to NTM cables.
 * Cables will still work without it as long as they implement IEnergyConductor (or even IEnergyConnector) + self-built network code
 * @author hbm
 */
public interface IPowerNet {
	
	void joinNetworks(IPowerNet network);

	IPowerNet joinLink(IEnergyConductor conductor);
	void leaveLink(IEnergyConductor conductor);

	void subscribe(IEnergyConnector connector);
	void unsubscribe(IEnergyConnector connector);
	boolean isSubscribed(IEnergyConnector connector);

	void destroy();
	
	/**
	 * When a link is removed, instead of destroying the network, causing it to be recreated from currently loaded conductors,
	 * we re-evaluate it, creating new nets based on the previous links.
	 */
    void reevaluate();
	
	boolean isValid();

	List<IEnergyConductor> getLinks();
	List<IEnergyConnector> getSubscribers();
	
	long transferPower(long power);
	long getTotalTransfer();
}
