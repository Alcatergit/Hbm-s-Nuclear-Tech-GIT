package com.hbm.core;

import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.VarInsnNode;

import net.minecraft.entity.Entity;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.hbm.core"})
public class HbmCorePlugin implements IFMLLoadingPlugin, IClassTransformer {

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.hbm.core.HbmCorePlugin"};
	}

	public static boolean hasPermanentTag(Entity entity) {
		return entity.getEntityData().getBoolean("isPermanent");
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (!transformedName.equals("net.minecraft.entity.passive.EntitySheep"))
			return basicClass;
		try {
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, 0);

			String className = classNode.name;
			String superName = classNode.superName;
			MethodNode eatGrassBonusNode = null;
			int setShearedIndex = -1;

			for (MethodNode method : classNode.methods) {
				if (eatGrassBonusNode == null && method.desc.equals("()V")) {
					for (int i = 0; i < method.instructions.size(); i++) {
						AbstractInsnNode insn = method.instructions.get(i);
						if (insn instanceof MethodInsnNode) {
							MethodInsnNode m = (MethodInsnNode) insn;
							if (m.desc.equals("(Z)V") && (m.owner.equals(className) || m.owner.equals(superName))) {
								eatGrassBonusNode = method;
								setShearedIndex = i;
								break;
							}
						}
					}
				}
			}

			if (eatGrassBonusNode == null)
				return basicClass;

			LabelNode skipLabel = new LabelNode();
			InsnList inject = new InsnList();
			inject.add(new VarInsnNode(Opcodes.ALOAD, 0));
			inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC,
					Type.getInternalName(HbmCorePlugin.class), "hasPermanentTag",
					Type.getMethodDescriptor(Type.BOOLEAN_TYPE, Type.getType(Entity.class)), false));
			inject.add(new JumpInsnNode(Opcodes.IFNE, skipLabel));

			AbstractInsnNode targetInsn = eatGrassBonusNode.instructions.get(setShearedIndex);
			eatGrassBonusNode.instructions.insertBefore(eatGrassBonusNode.instructions.get(setShearedIndex - 2), inject);
			eatGrassBonusNode.instructions.insert(targetInsn, skipLabel);

			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(writer);
			return writer.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return basicClass;
	}

	@Override
	public String getModContainerClass() {
		return "com.hbm.core.HbmCoreModContainer";
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
