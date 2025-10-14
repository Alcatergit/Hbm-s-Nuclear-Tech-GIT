package com.hbm.tileentity.machine.rbmk;

public interface IRBMKFluxReceiver {
	enum NType {
		FAST("trait.rbmk.neutron.fast"),
		SLOW("trait.rbmk.neutron.slow"),
		ANY("trait.rbmk.neutron.any");	//not to be used for reactor flux calculation, only for the fuel designation
		
		public final String unlocalized;
		
		NType(String loc) {
			this.unlocalized = loc;
		}
	}
	
	void receiveFlux(NType type, double flux);
}
