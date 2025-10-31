package com.leafia.contents.resources;

import com.hbm.inventory.material.MaterialShapes;
import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.hbm.items.special.ItemMaterialsAutogen;
import com.hbm.lib.RefStrings;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ItemMaterialsAutogenTint extends ItemMaterialsAutogen {
	String registryName;
	public static final List<ItemMaterialsAutogenTint> ALL_AUTOGEN = new ArrayList<>();
	public ItemMaterialsAutogenTint(String s,MaterialShapes shape) {
		super(s,shape);
		registryName = s;
		ALL_AUTOGEN.add(this);
	}
	private HashMap<NTMMaterial, String> resourceOverrides = new HashMap();
	/** add override texture */
	public ItemMaterialsAutogenTint aor(NTMMaterial mat,String res) {
		resourceOverrides.put(mat,res);
		return this;
	}
	@Override
	public ModelResourceLocation getResourceLocation(int meta) {
		NTMMaterial mat = Mats.matById.get(meta);
		if (resourceOverrides.containsKey(mat))
			return new ModelResourceLocation(RefStrings.MODID + ":"+resourceOverrides.get(mat), "inventory");
		else
			return new ModelResourceLocation(RefStrings.MODID + ":"+registryName, "inventory");
	}
}
