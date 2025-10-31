package com.leafia.dev.fluids.ntmtraits;

public class NTMTraitCorrosive extends NTMTraitBase {
	public NTMTraitCorrosive() {
		super(195,124);
	}
	@Override
	public boolean needsSpecializedContainer() {
		return true;
	}
}
