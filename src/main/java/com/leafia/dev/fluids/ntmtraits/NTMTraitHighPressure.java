package com.leafia.dev.fluids.ntmtraits;

public class NTMTraitHighPressure extends NTMTraitBase {
	public NTMTraitHighPressure() {
		super(73,185);
		iconTexture = null;
	}
	@Override
	public boolean needsSpecializedContainer() {
		return true;
	}
}
