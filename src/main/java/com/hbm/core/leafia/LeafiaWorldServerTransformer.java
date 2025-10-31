package com.hbm.core.leafia;

import net.minecraft.launchwrapper.IClassTransformer;

public class LeafiaWorldServerTransformer implements IClassTransformer {
	static final int index = 1;
	@Override
	public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
		//System.out.println("#Leaf: Transform Input: " + name + " : " + transformedName);
		boolean isObfuscated = !name.equals(transformedName);
		return classBeingTransformed;//(classesBeingTransformed[index].equals(transformedName))
				//? TransformerCoreLeafia.transform(index, classBeingTransformed, isObfuscated)
				//: classBeingTransformed;
	}
}
