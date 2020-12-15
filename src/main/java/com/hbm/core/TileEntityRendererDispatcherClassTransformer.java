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

public class TileEntityRendererDispatcherClassTransformer implements IClassTransformer {

	private static final String[] classesBeingTransformed = { "net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher" };

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

	private static void transformProfiler(ClassNode dispatcher, boolean isObfuscated) {

		for (MethodNode method : dispatcher.methods) {
			if ((method.name.equals("render") || method.name.equals("func_203602_a")) && method.desc.equals("(Lnet/minecraft/tileentity/TileEntity;DDDFIF)V")) {
				
				method.instructions.insertBefore(method.instructions.get(2), new MethodInsnNode(INVOKESTATIC, Type.getInternalName(ModEventHandlerClient.class), "onTileEntityRender", "(Lnet/minecraft/tileentity/TileEntity;)V", false));
				method.instructions.insertBefore(method.instructions.get(2), new VarInsnNode(ALOAD, 1));
			}
		}
	}
}
