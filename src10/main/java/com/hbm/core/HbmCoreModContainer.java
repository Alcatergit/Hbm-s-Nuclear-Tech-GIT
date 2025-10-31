package com.hbm.core;

import java.util.Arrays;

import com.google.common.eventbus.EventBus;

import net.minecraftforge.fml.common.DummyModContainer;
import net.minecraftforge.fml.common.LoadController;
import net.minecraftforge.fml.common.ModMetadata;

public class HbmCoreModContainer extends DummyModContainer {

	public HbmCoreModContainer() {
		super(new ModMetadata());
        ModMetadata meta = getMetadata();
        meta.modId = "hbmcore";
        meta.name = "NTMCore";
        meta.description = "Hbm core mod";
        meta.version = "1.12.2-1.0";
        meta.authorList = Arrays.asList("Hbm/TheBobcat", "Drillgon200", "TheOriginalGolem");
	}
	
	@Override
	public boolean registerBus(EventBus bus, LoadController controller) {
		bus.register(this);
		return true;
	}
}
