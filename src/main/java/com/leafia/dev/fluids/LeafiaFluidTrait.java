package com.leafia.dev.fluids;

import com.hbm.util.Tuple.Pair;
import com.llib.group.LeafiaSet;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class LeafiaFluidTrait {
	public static final Map<String,LeafiaFluidTrait> reg = new HashMap<>();
	public final String name;
	public final Set<String> preventations = new HashSet<>();
	public final Set<Pair<String,String>> redirections = new LeafiaSet<>();
	public ResourceLocation iconTexture = null;
	public float iconU0 = 0;
	public float iconV0 = 0;
	public float iconU1 = 1;
	public float iconV1 = 1;
	public float iconPriority = -9999;
	public LeafiaFluidTrait() {
		name = this.getClass().getSimpleName();
		reg.put(name,this);
	}
	abstract public boolean needsSpecializedContainer();
	@Nullable public Runnable onViolation(World world,BlockPos pos,FluidStack stack,Object container) {
		if (container instanceof ISpecializedContainer)
			return ((ISpecializedContainer) container).onViolationOverride(this.name);
		return null;
	}
}