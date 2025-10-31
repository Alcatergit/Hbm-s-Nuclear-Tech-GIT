package com.leafia.dev.fluids.ntmtraits;

public class NTMTraitAntimatter extends NTMTraitBase {
	public NTMTraitAntimatter() {
		super(73,185);
		preventations.add("hover");
	}
	@Override
	public boolean needsSpecializedContainer() {
		return true;
	}
}
