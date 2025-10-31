package com.leafia.contents.network.spk_cable.network;

import api.hbm.energy.network.NTMNetworkInstance;

import java.util.HashMap;

public class SPKNet extends NTMNetworkInstance<ISPKConductor,ISPKMember> {
	private HashMap<Integer,ISPKConductor> conductorMap = new HashMap();
	private HashMap<Integer, Integer> conductorRedirections = new HashMap();
	@Override
	public HashMap<Integer,ISPKConductor> getConductorMap() {
		return conductorMap;
	}
	@Override
	public HashMap<Integer,Integer> getConductorRedirections() {
		return conductorRedirections;
	}
}
