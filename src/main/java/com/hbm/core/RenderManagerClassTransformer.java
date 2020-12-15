package com.hbm.core;

import static org.objectweb.asm.Opcodes.ALOAD;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

import java.util.Arrays;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.hbm.main.ModEventHandlerClient;
import org.objectweb.asm.Type;

import net.minecraft.launchwrapper.IClassTransformer;

public class RenderManagerClassTransformer implements IClassTransformer {

	private static final String[] classesBeingTransformed = { "net.minecraft.client.renderer.entity.RenderManager" };

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
				transformProfiler(classNode, isObfuscated);
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

	private static void transformProfiler(ClassNode profilerClass, boolean isObfuscated) {

		for (MethodNode method : profilerClass.methods) {
			//I must have my mappings wrong or something, I see this as doRenderEntity
			if (method.name.equals("renderEntity") || method.name.equals("func_188391_a")) {
				
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModEventHandlerClient.class), "onEntityRender", "(Lnet/minecraft/entity/Entity;)V", false));
				method.instructions.insert(new VarInsnNode(ALOAD, 1));
			}
		}
	}
}
