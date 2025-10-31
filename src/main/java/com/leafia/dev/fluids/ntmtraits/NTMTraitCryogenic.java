package com.leafia.dev.fluids.ntmtraits;

public class NTMTraitCryogenic extends NTMTraitBase {
	public NTMTraitCryogenic() {
		super(134,185);
	}
	@Override
	public boolean needsSpecializedContainer() {
		return false;
	}
}
