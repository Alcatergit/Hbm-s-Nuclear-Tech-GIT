package api.hbm.energy;

import api.hbm.energy.IEnergyConnector.ConnectionPriority;
import api.hbm.energy.network.NTMNetworkInstance;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Basic IPowerNet implementation. The behavior of this demo might change inbetween releases, but the API remains the same.
 * For more consistency please implement your own IPowerNet.
 * @author hbm
 */
/**
 * I had to change this which means its no longer consistent between releases now :V
 */
public class PowerNet extends NTMNetworkInstance<IEnergyConductor,IEnergyConnector> {
	private HashMap<Integer, IEnergyConductor> links = new HashMap();
	private HashMap<Integer, Integer> proxies = new HashMap();
	@Override
	public HashMap<Integer,IEnergyConductor> getConductorMap() {
		return links;
	}
	@Override
	public HashMap<Integer,Integer> getConductorRedirections() {
		return proxies;
	}
	/*
	private boolean valid = true;
	private HashMap<Integer, IEnergyConductor> links = new HashMap();
	private HashMap<Integer, Integer> proxies = new HashMap();
	private List<IEnergyConnector> subscribers = new ArrayList();
*/
	public static List<PowerNet> trackingInstances = null;
	protected long totalTransfer = 0;

	public long getTotalTransfer() {
		return this.totalTransfer;
	}

	public long lastCleanup = System.currentTimeMillis();

	public long transferPower(long power) {

		List<PowerNet> cache = new ArrayList();
		if(trackingInstances != null && !trackingInstances.isEmpty()) {
			cache.addAll(trackingInstances);
		}

		trackingInstances = new ArrayList();
		trackingInstances.add(this);
		long result = fairTransfer(this.getMembers(), power);
		trackingInstances.addAll(cache);
		return result;
	}

	@Override
	public void cleanup() {
		getMembers().removeIf(x ->
				x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid() || !x.isLoaded()
		);
	}
	public static void cleanup(List<IEnergyConnector> subscribers) {

		subscribers.removeIf(x ->
			x == null || !(x instanceof TileEntity) || ((TileEntity)x).isInvalid() || !x.isLoaded()
		);
	}

	public static boolean shouldSend(ConnectionPriority senderPrio, ConnectionPriority p, IEnergyConnector x){
		return (x.getPriority() == p) && (!x.isStorage() || (senderPrio.compareTo(p) <= 0));
	}

	public static long fairTransferWithPrio(ConnectionPriority senderPrio, List<IEnergyConnector> subscribers, long power) {

		if(power <= 0) return 0;

		if(subscribers.isEmpty())
			return power;

		cleanup(subscribers);

		ConnectionPriority[] priorities = new ConnectionPriority[] {ConnectionPriority.HIGH, ConnectionPriority.NORMAL, ConnectionPriority.LOW};

		long totalTransfer = 0;

		for(ConnectionPriority p : priorities) {

			List<IEnergyConnector> subList = new ArrayList();
			subscribers.forEach(x -> {
				if(shouldSend(senderPrio, p, x)) {
					subList.add(x);
				}
			});

			if(subList.isEmpty())
				continue;

			List<Long> weight = new ArrayList();
			long totalReq = 0;

			for(IEnergyConnector con : subList) {
				long req = con.getTransferWeight();
				weight.add(req);
				totalReq += req;
			}

			if(totalReq == 0)
				continue;

			long totalGiven = 0;

			for(int i = 0; i < subList.size(); i++) {
				IEnergyConnector con = subList.get(i);
				long req = weight.get(i);
				double fraction = (double)req / (double)totalReq;

				long given = (long) Math.floor(fraction * power);

				totalGiven += (given - con.transferPower(given));

				if(con instanceof TileEntity) {
					TileEntity tile = (TileEntity) con;
					tile.getWorld().markChunkDirty(tile.getPos(), tile);
				}
			}

			power -= totalGiven;
			totalTransfer += totalGiven;
		}

		if(trackingInstances != null) {

			for(int i = 0; i < trackingInstances.size(); i++) {
				PowerNet net = trackingInstances.get(i);
				net.totalTransfer += totalTransfer;
			}

			trackingInstances.clear();
		}

		return power;
	}

	public static long fairTransfer(List<IEnergyConnector> subscribers, long power) {

		if(power <= 0) return 0;

		if(subscribers.isEmpty())
			return power;

		cleanup(subscribers);

		ConnectionPriority[] priorities = new ConnectionPriority[] {ConnectionPriority.HIGH, ConnectionPriority.NORMAL, ConnectionPriority.LOW};

		long totalTransfer = 0;

		for(ConnectionPriority p : priorities) {

			List<IEnergyConnector> subList = new ArrayList();
			subscribers.forEach(x -> {
				if(x.getPriority() == p) {
					subList.add(x);
				}
			});

			if(subList.isEmpty())
				continue;

			List<Long> weight = new ArrayList();
			long totalReq = 0;

			for(IEnergyConnector con : subList) {
				long req = con.getTransferWeight();
				weight.add(req);
				totalReq += req;
			}

			if(totalReq == 0)
				continue;

			long totalGiven = 0;

			for(int i = 0; i < subList.size(); i++) {
				IEnergyConnector con = subList.get(i);
				long req = weight.get(i);
				double fraction = (double)req / (double)totalReq;

				long given = (long) Math.floor(fraction * power);

				totalGiven += (given - con.transferPower(given));

				if(con instanceof TileEntity) {
					TileEntity tile = (TileEntity) con;
					tile.getWorld().markChunkDirty(tile.getPos(), tile);
				}
			}

			power -= totalGiven;
			totalTransfer += totalGiven;
		}

		if(trackingInstances != null) {

			for(int i = 0; i < trackingInstances.size(); i++) {
				PowerNet net = trackingInstances.get(i);
				net.totalTransfer += totalTransfer;
			}

			trackingInstances.clear();
		}

		return power;
	}
}
