package com.leafia.dev.fluids.ntmtraits;

import com.hbm.util.Tuple.Pair;
import com.leafia.dev.fluids.LeafiaFluidTrait;

public class NTMTraitMagnetic extends LeafiaFluidTrait {
	public NTMTraitMagnetic() {
		redirections.add(new Pair<>("magnetic","hover")); // Magnets are able to hover this fluid
	}
	@Override
	public boolean needsSpecializedContainer() {
		return false;
	}
}
