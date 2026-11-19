package com.hbm.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.InsnNode;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

import com.hbm.entity.effect.EntityNukeTorex;

import net.minecraft.entity.Entity;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

@IFMLLoadingPlugin.MCVersion("1.12.2")
@IFMLLoadingPlugin.TransformerExclusions({"com.hbm.core"})
public class HbmCorePlugin implements IFMLLoadingPlugin, IClassTransformer {

	private static boolean runtimeDeobfEnabled = false;

	@Override
	public String[] getASMTransformerClass() {
		return new String[]{"com.hbm.core.HbmCorePlugin"};
	}

	public static boolean hasPermanentTag(Entity entity) {
		return entity.getEntityData().getBoolean("isPermanent");
	}

	/** Write fireMode and remap magazineType on the legacy gun tag. */
	public static void remapLegacyGunNbt(Object compound) {
		String id = readId(compound);
		String path = id.contains(":") ? id.substring(id.indexOf(':') + 1) : id;
		Object tag = getTag(compound);
		boolean changed = false;
		if(path.equals("gun_proto")) {
			setTagInt(tag, "fireMode", 1);
			changed = true;
		} else if(path.equals("gun_mirv") || path.equals("gun_bf")) {
			setTagInt(tag, "fireMode", 0);
			setTagInt(tag, "magazineType", getTagInt(tag, "magazineType") + (path.equals("gun_bf") ? 6 : 7));
			changed = true;
		}
		if(changed)
			putTag(compound, tag);
	}

	private static String readId(Object compound) {
		try {
			for(Method m : compound.getClass().getDeclaredMethods()) {
				if(m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == String.class && m.getReturnType() == String.class && !java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
					m.setAccessible(true);
					return (String) m.invoke(compound, "id");
				}
			}
		} catch(Exception e) { }
		return "";
	}

	private static Object getTag(Object compound) {
		try {
			for(Method m : compound.getClass().getDeclaredMethods()) {
				if(m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == String.class && m.getReturnType() == compound.getClass() && !java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
					m.setAccessible(true);
					return m.invoke(compound, "tag");
				}
			}
		} catch(Exception e) { }
		return null;
	}

	private static int getTagInt(Object tag, String key) {
		try {
			for(Method m : tag.getClass().getDeclaredMethods()) {
				if(m.getParameterTypes().length == 1 && m.getParameterTypes()[0] == String.class && m.getReturnType() == int.class && !java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
					m.setAccessible(true);
					return ((Integer) m.invoke(tag, key)).intValue();
				}
			}
		} catch(Exception e) { }
		return 0;
	}

	private static void setTagInt(Object tag, String key, int value) {
		try {
			for(Method m : tag.getClass().getDeclaredMethods()) {
				if(m.getParameterTypes().length == 2 && m.getParameterTypes()[0] == String.class && m.getParameterTypes()[1] == int.class && m.getReturnType() == void.class && !java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
					m.setAccessible(true);
					m.invoke(tag, key, value);
					return;
				}
			}
		} catch(Exception e) { }
	}

	private static void putTag(Object compound, Object tag) {
		try {
			for(Method m : compound.getClass().getDeclaredMethods()) {
				if(m.getParameterTypes().length == 2 && m.getParameterTypes()[0] == String.class && m.getParameterTypes()[1].isAssignableFrom(tag.getClass()) && m.getReturnType() == void.class && !java.lang.reflect.Modifier.isStatic(m.getModifiers())) {
					m.setAccessible(true);
					m.invoke(compound, "tag", tag);
					return;
				}
			}
		} catch(Exception e) { }
	}

	private static final java.util.Map<String, Integer> LEGACY_AMMO_META = new java.util.HashMap<>();
	static {
		LEGACY_AMMO_META.put("ammo_357_desh", 0);
		LEGACY_AMMO_META.put("ammo_4gauge_canister", 1);
		LEGACY_AMMO_META.put("ammo_4gauge_claw", 2);
		LEGACY_AMMO_META.put("ammo_4gauge_vampire", 3);
		LEGACY_AMMO_META.put("ammo_4gauge_void", 4);
		LEGACY_AMMO_META.put("ammo_5mm_chlorophyte", 5);
		LEGACY_AMMO_META.put("ammo_9mm_chlorophyte", 6);
		LEGACY_AMMO_META.put("ammo_556_chlorophyte", 7);
		LEGACY_AMMO_META.put("ammo_556_flechette_chlorophyte", 8);
		LEGACY_AMMO_META.put("ammo_50ae_chlorophyte", 9);
		LEGACY_AMMO_META.put("ammo_50bmg_chlorophyte", 10);
		LEGACY_AMMO_META.put("ammo_50bmg_flechette", 11);
		LEGACY_AMMO_META.put("ammo_50bmg_flechette_am", 12);
		LEGACY_AMMO_META.put("ammo_50bmg_flechette_po", 13);
		LEGACY_AMMO_META.put("ammo_44_chlorophyte", 14);
		LEGACY_AMMO_META.put("ammo_22lr_chlorophyte", 15);
		LEGACY_AMMO_META.put("ammo_rocket_canister", 16);
		LEGACY_AMMO_META.put("ammo_20gauge", 17);
		LEGACY_AMMO_META.put("ammo_20gauge_slug", 18);
		LEGACY_AMMO_META.put("ammo_20gauge_flechette", 19);
		LEGACY_AMMO_META.put("ammo_20gauge_incendiary", 20);
		LEGACY_AMMO_META.put("ammo_20gauge_explosive", 21);
		LEGACY_AMMO_META.put("ammo_20gauge_caustic", 22);
		LEGACY_AMMO_META.put("ammo_20gauge_shock", 23);
		LEGACY_AMMO_META.put("ammo_20gauge_wither", 24);
		LEGACY_AMMO_META.put("ammo_20gauge_sleek", 25);
		LEGACY_AMMO_META.put("ammo_20gauge_shrapnel", 26);
		LEGACY_AMMO_META.put("ammo_4gauge", 27);
		LEGACY_AMMO_META.put("ammo_4gauge_slug", 28);
		LEGACY_AMMO_META.put("ammo_4gauge_flechette", 29);
		LEGACY_AMMO_META.put("ammo_4gauge_flechette_phosphorus", 30);
		LEGACY_AMMO_META.put("ammo_4gauge_explosive", 31);
		LEGACY_AMMO_META.put("ammo_4gauge_semtex", 32);
		LEGACY_AMMO_META.put("ammo_4gauge_balefire", 33);
		LEGACY_AMMO_META.put("ammo_4gauge_kampf", 34);
		LEGACY_AMMO_META.put("ammo_4gauge_sleek", 35);
		LEGACY_AMMO_META.put("ammo_rocket", 36);
		LEGACY_AMMO_META.put("ammo_rocket_he", 37);
		LEGACY_AMMO_META.put("ammo_rocket_incendiary", 38);
		LEGACY_AMMO_META.put("ammo_rocket_phosphorus", 39);
		LEGACY_AMMO_META.put("ammo_rocket_shrapnel", 40);
		LEGACY_AMMO_META.put("ammo_rocket_emp", 41);
		LEGACY_AMMO_META.put("ammo_rocket_glare", 42);
		LEGACY_AMMO_META.put("ammo_rocket_toxic", 43);
		LEGACY_AMMO_META.put("ammo_rocket_sleek", 44);
		LEGACY_AMMO_META.put("ammo_rocket_nuclear", 45);
		LEGACY_AMMO_META.put("ammo_rocket_rpc", 46);
		LEGACY_AMMO_META.put("ammo_grenade", 47);
		LEGACY_AMMO_META.put("ammo_grenade_he", 48);
		LEGACY_AMMO_META.put("ammo_grenade_incendiary", 49);
		LEGACY_AMMO_META.put("ammo_grenade_phosphorus", 50);
		LEGACY_AMMO_META.put("ammo_grenade_toxic", 51);
		LEGACY_AMMO_META.put("ammo_grenade_concussion", 52);
		LEGACY_AMMO_META.put("ammo_grenade_finned", 53);
		LEGACY_AMMO_META.put("ammo_grenade_sleek", 54);
		LEGACY_AMMO_META.put("ammo_grenade_nuclear", 55);
		LEGACY_AMMO_META.put("ammo_grenade_tracer", 56);
		LEGACY_AMMO_META.put("ammo_grenade_kampf", 57);
		LEGACY_AMMO_META.put("ammo_shell", 58);
		LEGACY_AMMO_META.put("ammo_shell_explosive", 59);
		LEGACY_AMMO_META.put("ammo_shell_apfsds_t", 60);
		LEGACY_AMMO_META.put("ammo_shell_apfsds_du", 61);
		LEGACY_AMMO_META.put("ammo_shell_w9", 62);
		LEGACY_AMMO_META.put("ammo_dgk", 63);
		LEGACY_AMMO_META.put("ammo_nuke", 64);
		LEGACY_AMMO_META.put("ammo_nuke_safe", 65);
		LEGACY_AMMO_META.put("ammo_nuke_low", 66);
		LEGACY_AMMO_META.put("ammo_nuke_high", 67);
		LEGACY_AMMO_META.put("gun_bf_ammo", 68);
		LEGACY_AMMO_META.put("ammo_nuke_pumpkin", 69);
		LEGACY_AMMO_META.put("ammo_nuke_tots", 70);
		LEGACY_AMMO_META.put("ammo_mirv", 71);
		LEGACY_AMMO_META.put("ammo_mirv_safe", 72);
		LEGACY_AMMO_META.put("ammo_mirv_low", 73);
		LEGACY_AMMO_META.put("ammo_mirv_high", 74);
		LEGACY_AMMO_META.put("ammo_mirv_special", 75);
		LEGACY_AMMO_META.put("ammo_fuel", 76);
		LEGACY_AMMO_META.put("ammo_fuel_napalm", 77);
		LEGACY_AMMO_META.put("ammo_fuel_phosphorus", 78);
		LEGACY_AMMO_META.put("ammo_fuel_vaporizer", 79);
		LEGACY_AMMO_META.put("ammo_fuel_gas", 80);
		LEGACY_AMMO_META.put("ammo_dart", 81);
		LEGACY_AMMO_META.put("ammo_12gauge", 82);
		LEGACY_AMMO_META.put("ammo_12gauge_incendiary", 83);
		LEGACY_AMMO_META.put("ammo_12gauge_shrapnel", 84);
		LEGACY_AMMO_META.put("ammo_12gauge_du", 85);
		LEGACY_AMMO_META.put("ammo_12gauge_sleek", 86);
		LEGACY_AMMO_META.put("ammo_12gauge_marauder", 87);
		LEGACY_AMMO_META.put("ammo_22lr", 88);
		LEGACY_AMMO_META.put("ammo_22lr_ap", 89);
		LEGACY_AMMO_META.put("ammo_44", 90);
		LEGACY_AMMO_META.put("ammo_44_ap", 91);
		LEGACY_AMMO_META.put("ammo_44_du", 92);
		LEGACY_AMMO_META.put("ammo_44_phosphorus", 93);
		LEGACY_AMMO_META.put("ammo_44_pip", 94);
		LEGACY_AMMO_META.put("ammo_44_bj", 95);
		LEGACY_AMMO_META.put("ammo_44_silver", 96);
		LEGACY_AMMO_META.put("ammo_44_rocket", 97);
		LEGACY_AMMO_META.put("ammo_44_star", 98);
		LEGACY_AMMO_META.put("ammo_9mm", 99);
		LEGACY_AMMO_META.put("ammo_9mm_ap", 100);
		LEGACY_AMMO_META.put("ammo_9mm_du", 101);
		LEGACY_AMMO_META.put("ammo_9mm_rocket", 102);
		LEGACY_AMMO_META.put("ammo_556", 103);
		LEGACY_AMMO_META.put("ammo_556_phosphorus", 104);
		LEGACY_AMMO_META.put("ammo_556_ap", 105);
		LEGACY_AMMO_META.put("ammo_556_du", 106);
		LEGACY_AMMO_META.put("ammo_556_star", 107);
		LEGACY_AMMO_META.put("ammo_556_sleek", 108);
		LEGACY_AMMO_META.put("ammo_556_tracer", 109);
		LEGACY_AMMO_META.put("ammo_556_flechette", 110);
		LEGACY_AMMO_META.put("ammo_556_flechette_incendiary", 111);
		LEGACY_AMMO_META.put("ammo_556_flechette_phosphorus", 112);
		LEGACY_AMMO_META.put("ammo_556_flechette_du", 113);
		LEGACY_AMMO_META.put("ammo_556_flechette_sleek", 114);
		LEGACY_AMMO_META.put("ammo_556_k", 115);
		LEGACY_AMMO_META.put("ammo_50bmg", 116);
		LEGACY_AMMO_META.put("ammo_50bmg_incendiary", 117);
		LEGACY_AMMO_META.put("ammo_50bmg_phosphorus", 118);
		LEGACY_AMMO_META.put("ammo_50bmg_explosive", 119);
		LEGACY_AMMO_META.put("ammo_50bmg_du", 120);
		LEGACY_AMMO_META.put("ammo_50bmg_star", 121);
		LEGACY_AMMO_META.put("ammo_50bmg_sleek", 122);
		LEGACY_AMMO_META.put("ammo_75bolt", 123);
		LEGACY_AMMO_META.put("ammo_75bolt_incendiary", 124);
		LEGACY_AMMO_META.put("ammo_75bolt_he", 125);
		LEGACY_AMMO_META.put("ammo_50bmg_ap", 126);
		LEGACY_AMMO_META.put("ammo_5mm", 127);
		LEGACY_AMMO_META.put("ammo_5mm_explosive", 128);
		LEGACY_AMMO_META.put("ammo_5mm_du", 129);
		LEGACY_AMMO_META.put("ammo_5mm_star", 130);
		LEGACY_AMMO_META.put("ammo_50ae", 131);
		LEGACY_AMMO_META.put("ammo_50ae_ap", 132);
		LEGACY_AMMO_META.put("ammo_50ae_du", 133);
		LEGACY_AMMO_META.put("ammo_50ae_star", 134);
		LEGACY_AMMO_META.put("ammo_folly", 135);
		LEGACY_AMMO_META.put("ammo_folly_nuclear", 136);
		LEGACY_AMMO_META.put("ammo_folly_du", 137);
	}

	private static final java.util.Map<String, Integer> LEGACY_CLIP_META = new java.util.HashMap<>();
	static {
		LEGACY_CLIP_META.put("clip_revolver_iron", 0);
		LEGACY_CLIP_META.put("clip_revolver", 1);
		LEGACY_CLIP_META.put("clip_revolver_gold", 2);
		LEGACY_CLIP_META.put("clip_revolver_lead", 3);
		LEGACY_CLIP_META.put("clip_revolver_schrabidium", 4);
		LEGACY_CLIP_META.put("clip_revolver_cursed", 5);
		LEGACY_CLIP_META.put("clip_revolver_nightmare", 6);
		LEGACY_CLIP_META.put("clip_revolver_nightmare2", 7);
		LEGACY_CLIP_META.put("clip_revolver_pip", 8);
		LEGACY_CLIP_META.put("clip_revolver_nopip", 9);
		LEGACY_CLIP_META.put("clip_rpg", 10);
		LEGACY_CLIP_META.put("clip_stinger", 11);
		LEGACY_CLIP_META.put("clip_fatman", 12);
		LEGACY_CLIP_META.put("clip_mirv", 13);
		LEGACY_CLIP_META.put("clip_bf", 14);
		LEGACY_CLIP_META.put("clip_mp40", 15);
		LEGACY_CLIP_META.put("clip_uzi", 16);
		LEGACY_CLIP_META.put("clip_uboinik", 17);
		LEGACY_CLIP_META.put("clip_lever_action", 18);
		LEGACY_CLIP_META.put("clip_bolt_action", 19);
		LEGACY_CLIP_META.put("clip_osipr", 20);
		LEGACY_CLIP_META.put("clip_immolator", 21);
		LEGACY_CLIP_META.put("clip_cryolator", 22);
		LEGACY_CLIP_META.put("clip_mp", 23);
		LEGACY_CLIP_META.put("clip_xvl1456", 24);
		LEGACY_CLIP_META.put("clip_emp", 25);
		LEGACY_CLIP_META.put("clip_jack", 26);
		LEGACY_CLIP_META.put("clip_spark", 27);
		LEGACY_CLIP_META.put("clip_hp", 28);
		LEGACY_CLIP_META.put("clip_euthanasia", 29);
		LEGACY_CLIP_META.put("clip_defabricator", 30);
		LEGACY_CLIP_META.put("ammo_container", 31);
	}

	/** Legacy ammo/clip id -> meta ordinal, -1 if not a legacy item. */
	public static int legacyMetaFor(Object compound) {
		String id = readId(compound);
		if(id == null)
			return -1;
		String path = id.contains(":") ? id.substring(id.indexOf(':') + 1) : id;
		Integer meta = LEGACY_AMMO_META.get(path);
		if(meta != null)
			return meta.intValue();
		meta = LEGACY_CLIP_META.get(path);
		return meta == null ? -1 : meta.intValue();
	}

	/** Resolve a field name for runtime reflection: SRG name in production (obfuscated), MCP name in dev. */
	public static String chooseName(String mcp, String srg) {
		return runtimeDeobfEnabled ? srg : mcp;
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {
		if (transformedName.equals("net.minecraft.item.ItemStack"))
			return transformItemStack(basicClass);
		if (transformedName.equals("net.minecraft.world.World"))
			return transformWorld(basicClass);
		if (transformedName.equals("net.minecraft.client.renderer.EntityRenderer"))
			return transformEntityRenderer(basicClass);
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

	/** Called from the patched World#getSkyColorBody tail. */
	public static Object getSkyFlashColor(double r, double g, double b) {
		float w = flashWeight();

		if(w <= 0F)
			return new Vec3d(r, g, b);

		return new Vec3d(
				r * (1.0D - w) + EntityNukeTorex.flashR * w,
				g * (1.0D - w) + EntityNukeTorex.flashG * w,
				b * (1.0D - w) + EntityNukeTorex.flashB * w);
	}

	/** Called from the patched EntityRenderer#updateLightmap loop body. */
	public static float getLightmapChannel(float base, float skyTerm, int lightIndex, int channel) {
		if(flashWeight() <= 0F)
			return base;

		float[] table = EntityNukeTorex.flashLightTable;

		if(table == null)
			return base;

		int index = lightIndex / 16;

		if(index < 0 || index >= table.length)
			return base;

		float f = EntityNukeTorex.flashI;
		float gap = table[index] - skyTerm;
		float brightened = gap > 0F ? base + gap * f : base;

		float luma = 0.299F * EntityNukeTorex.flashR + 0.587F * EntityNukeTorex.flashG + 0.114F * EntityNukeTorex.flashB;

		if(luma <= 0F)
			return brightened;

		float hue = channel == 0 ? EntityNukeTorex.flashR : channel == 1 ? EntityNukeTorex.flashG : EntityNukeTorex.flashB;
		return brightened * (1F + (hue / luma - 1F) * f);
	}

	private static float flashWeight() {
		return EntityNukeTorex.getFlashWeight();
	}

	/** Instruction array without labels and line numbers. */
	private static AbstractInsnNode[] realInsns(MethodNode method) {
		List<AbstractInsnNode> list = new ArrayList<>();

		for(AbstractInsnNode insn : method.instructions.toArray()) {
			if(insn.getOpcode() >= 0)
				list.add(insn);
		}

		return list.toArray(new AbstractInsnNode[0]);
	}

	/** Previous node that is an actual instruction, skipping labels and line numbers. */
	private static AbstractInsnNode prev(AbstractInsnNode insn) {
		AbstractInsnNode node = insn == null ? null : insn.getPrevious();

		while(node != null && node.getOpcode() < 0)
			node = node.getPrevious();

		return node;
	}

	/** Replaces the Vec3d built at the end of World#getSkyColorBody with a colored variant. */
	private byte[] transformWorld(byte[] basicClass) {
		try {
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, 0);

			MethodNode target = null;

			for(MethodNode method : classNode.methods) {
				for(AbstractInsnNode insn : method.instructions.toArray()) {
					if(insn instanceof MethodInsnNode) {
						MethodInsnNode m = (MethodInsnNode) insn;
						if(m.owner.equals("net/minecraftforge/client/ForgeHooksClient") && m.name.equals("getSkyBlendColour")) {
							target = method;
							break;
						}
					}
				}
				if(target != null)
					break;
			}

			if(target == null) {
				return basicClass;
			}

			AbstractInsnNode ret = null;
			AbstractInsnNode start = null;
			AbstractInsnNode end = null;
			String vecName = null;
			int[] floats = new int[3];

			for(AbstractInsnNode insn : target.instructions.toArray()) {
				if(insn.getOpcode() != Opcodes.ARETURN)
					continue;

				AbstractInsnNode init = prev(insn);

				if(init == null || init.getOpcode() != Opcodes.INVOKESPECIAL || !(init instanceof MethodInsnNode) || !((MethodInsnNode) init).desc.equals("(DDD)V"))
					continue;

				AbstractInsnNode cur = init;
				boolean ok = true;

				for(int i = 2; i >= 0; i--) {
					AbstractInsnNode cast = prev(cur);
					AbstractInsnNode load = prev(cast);

					if(cast == null || cast.getOpcode() != Opcodes.F2D || load == null || load.getOpcode() != Opcodes.FLOAD) {
						ok = false;
						break;
					}

					floats[i] = ((VarInsnNode) load).var;
					cur = load;
				}

				if(!ok)
					continue;

				AbstractInsnNode dup = prev(cur);
				AbstractInsnNode created = prev(dup);

				if(dup == null || dup.getOpcode() != Opcodes.DUP || created == null || created.getOpcode() != Opcodes.NEW)
					continue;

				vecName = ((TypeInsnNode) created).desc;
				ret = insn;
				start = created;
				end = init;
				break;
			}

			if(ret == null) {
				return basicClass;
			}

			InsnList inject = new InsnList();

			for(int i = 0; i < 3; i++) {
				inject.add(new VarInsnNode(Opcodes.FLOAD, floats[i]));
				inject.add(new InsnNode(Opcodes.F2D));
			}

			inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(HbmCorePlugin.class), "getSkyFlashColor", "(DDD)Ljava/lang/Object;", false));
			inject.add(new TypeInsnNode(Opcodes.CHECKCAST, vecName));

			AbstractInsnNode node = start;

			while(node != null) {
				AbstractInsnNode next = node.getNext();
				if(node.getOpcode() >= 0)
					target.instructions.remove(node);
				if(node == end)
					break;
				node = next;
			}

			target.instructions.insertBefore(ret, inject);


			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			classNode.accept(writer);
			return writer.toByteArray();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return basicClass;
	}

	/** Adds the colored flash to the lightmap entries built in EntityRenderer#updateLightmap. */
	private byte[] transformEntityRenderer(byte[] basicClass) {
		try {
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, 0);

			MethodNode target = null;
			int loopVar = -1;

			for(MethodNode method : classNode.methods) {
				AbstractInsnNode[] insns = realInsns(method);

				for(int i = 0; i + 4 < insns.length; i++) {
					if(insns[i].getOpcode() != Opcodes.INVOKEVIRTUAL || !(insns[i] instanceof MethodInsnNode))
						continue;

					if(!((MethodInsnNode) insns[i]).desc.equals("()[F"))
						continue;

					if(insns[i + 1].getOpcode() != Opcodes.ILOAD)
						continue;

					if(!(insns[i + 2] instanceof IntInsnNode) || insns[i + 2].getOpcode() != Opcodes.BIPUSH || ((IntInsnNode) insns[i + 2]).operand != 16)
						continue;

					if(insns[i + 3].getOpcode() != Opcodes.IDIV || insns[i + 4].getOpcode() != Opcodes.FALOAD)
						continue;

					target = method;
					loopVar = ((VarInsnNode) insns[i + 1]).var;
					break;
				}

				if(target != null)
					break;
			}

			if(target == null) {
				return basicClass;
			}

			AbstractInsnNode[] insns = realInsns(target);
			int[] storeVars = new int[3];
			int skyVar = -1;
			AbstractInsnNode insertAfter = null;
			int run = 0;

			for(int i = 0; i + 3 < insns.length; i++) {
				if(insns[i].getOpcode() == Opcodes.FLOAD && insns[i + 1].getOpcode() == Opcodes.FLOAD
						&& insns[i + 2].getOpcode() == Opcodes.FADD && insns[i + 3].getOpcode() == Opcodes.FSTORE) {
					run++;

					if(run <= 3)
						storeVars[run - 1] = ((VarInsnNode) insns[i + 3]).var;

					if(run == 3) {
						skyVar = ((VarInsnNode) insns[i]).var;
						insertAfter = insns[i + 3];
						break;
					}

					i += 3;
				} else {
					run = 0;
				}
			}

			if(insertAfter == null) {
				return basicClass;
			}

			InsnList inject = new InsnList();

			for(int channel = 0; channel < 3; channel++) {
				inject.add(new VarInsnNode(Opcodes.FLOAD, storeVars[channel]));
				inject.add(new VarInsnNode(Opcodes.FLOAD, skyVar));
				inject.add(new VarInsnNode(Opcodes.ILOAD, loopVar));
				inject.add(new InsnNode(Opcodes.ICONST_0 + channel));
				inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(HbmCorePlugin.class), "getLightmapChannel", "(FFII)F", false));
				inject.add(new VarInsnNode(Opcodes.FSTORE, storeVars[channel]));
			}

			target.instructions.insert(insertAfter, inject);


			ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
			classNode.accept(writer);
			return writer.toByteArray();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return basicClass;
	}

	private byte[] transformItemStack(byte[] basicClass) {
		try {
			ClassNode classNode = new ClassNode();
			new ClassReader(basicClass).accept(classNode, 0);

			for (MethodNode method : classNode.methods) {
				if (!method.name.equals("<init>"))
					continue;
				boolean hasGetString = false;
				for (AbstractInsnNode insn : method.instructions.toArray()) {
					if (insn instanceof MethodInsnNode && ((MethodInsnNode) insn).desc.equals("(Ljava/lang/String;)Ljava/lang/String;")) {
						hasGetString = true;
						break;
					}
				}
				if (!hasGetString)
					continue;
				FieldInsnNode putDamageField = null;
				int intPuts = 0;
				for (AbstractInsnNode insn : method.instructions.toArray()) {
					if (insn instanceof FieldInsnNode) {
						FieldInsnNode f = (FieldInsnNode) insn;
						if (f.getOpcode() == Opcodes.PUTFIELD && f.owner.equals(classNode.name) && f.desc.equals("I")) {
							intPuts++;
							if (intPuts == 2) {
								putDamageField = f;
								break;
							}
						}
					}
				}
				if (putDamageField == null)
					return basicClass;

				LabelNode skipMeta = new LabelNode();
				LabelNode endMeta = new LabelNode();
				InsnList inject = new InsnList();
				inject.add(new VarInsnNode(Opcodes.ALOAD, 1));
				inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(HbmCorePlugin.class), "remapLegacyGunNbt", "(Ljava/lang/Object;)V", false));
				inject.add(new VarInsnNode(Opcodes.ALOAD, 1));
				inject.add(new MethodInsnNode(Opcodes.INVOKESTATIC, Type.getInternalName(HbmCorePlugin.class), "legacyMetaFor", "(Ljava/lang/Object;)I", false));
				inject.add(new InsnNode(Opcodes.DUP));
				inject.add(new JumpInsnNode(Opcodes.IFLT, skipMeta));
				inject.add(new VarInsnNode(Opcodes.ALOAD, 0));
				inject.add(new InsnNode(Opcodes.SWAP));
				inject.add(new FieldInsnNode(Opcodes.PUTFIELD, putDamageField.owner, putDamageField.name, putDamageField.desc));
				inject.add(new JumpInsnNode(Opcodes.GOTO, endMeta));
				inject.add(skipMeta);
				inject.add(new InsnNode(Opcodes.POP));
				inject.add(endMeta);

				method.instructions.insert(putDamageField.getNext(), inject);
			}

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
		runtimeDeobfEnabled = (Boolean) data.get("runtimeDeobfuscationEnabled");
	}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}
}
