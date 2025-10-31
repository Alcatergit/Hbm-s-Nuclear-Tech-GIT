package com.leafia.dev.fluids;

import javax.annotation.Nullable;

public interface ISpecializedContainer {
	String[] protections();
	@Nullable default Runnable onViolationOverride(String trait) { return null; }
}
