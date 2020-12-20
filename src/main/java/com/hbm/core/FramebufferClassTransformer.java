package com.hbm.core;

import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

import net.minecraft.launchwrapper.IClassTransformer;

public class FramebufferClassTransformer implements IClassTransformer {

	private static final String[] classesBeingTransformed = { "net.minecraft.client.shader.Framebuffer" };

	@Override
	public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
		boolean isObfuscated = !name.equals(transformedName);
		int index = Arrays.asList(classesBeingTransformed).indexOf(transformedName);
		return index != -1 ? transform(index, classBeingTransformed, isObfuscated) : classBeingTransformed;
	}

	private static byte[] transform(int index, byte[] classBeingTransformed, boolean isObfuscated) {
		System.out.println("Transforming: " + classesBeingTransformed[index]);
		try {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(classBeingTransformed);
			classReader.accept(classNode, 0);

			switch (index) {
			case 0:
				transformFramebuffer(classNode, isObfuscated);
				break;
			}

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			return classWriter.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return classBeingTransformed;
	}

	private static void transformFramebuffer(ClassNode clazz, boolean isObfuscated) {

		for (MethodNode method : clazz.methods) {
			if(method.name.equals("createFramebuffer") || method.name.equals("func_147605_b")){
				for(int i = 0; i < method.instructions.toArray().length; i++){
					AbstractInsnNode m = method.instructions.get(i);
					if(m instanceof MethodInsnNode){
						MethodInsnNode m2 = ((MethodInsnNode)m);
						if(m2.name.equals("glTexImage2D")){
							AbstractInsnNode node = method.instructions.get(i - 10);
							if(node instanceof LdcInsnNode){
								LdcInsnNode rgba_8 = (LdcInsnNode) node;
								if(rgba_8.cst instanceof Integer && ((Integer)rgba_8.cst).intValue() == 32856){
									//Change the frame buffer color storage from rgba8 to rgba16 to support hdr.
									rgba_8.cst = 32859;
								}
								//System.out.println(rgba_8.cst);
							}
							//System.out.println(m2.desc);
							//method.instructions.set(m, new MethodInsnNode(i.getOpcode(), i.owner, i.name, i.desc, i.itf));
						}
					}
				}
			}
			
		}
	}
}
