package com.leafia.dev.fluids.ntmtraits;

public class NTMTraitRadioactive extends NTMTraitBase {
	public NTMTraitRadioactive() {
		super(195,2);
	}
	@Override
	public boolean needsSpecializedContainer() {
		return false;
	}
}
