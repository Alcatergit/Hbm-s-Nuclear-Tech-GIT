package com.hbm.core.leafia;

import net.minecraft.launchwrapper.IClassTransformer;

import static com.hbm.core.leafia.TransformerCoreLeafia.classesBeingTransformed;

public class LeafiaGlsTransformer implements IClassTransformer {
	static final int index = 0;
	@Override
	public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
		//System.out.println("#Leaf: Transform Input: " + name + " : " + transformedName);
		boolean isObfuscated = !name.equals(transformedName);
		return (classesBeingTransformed[index].equals(transformedName))
				? TransformerCoreLeafia.transform(index, classBeingTransformed, isObfuscated)
				: classBeingTransformed;
	}
}
