package com.hbm.core.leafia;

import com.leafia.contents.worldgen.biomes.effects.HasAcidicRain;
import com.leafia.passive.LeafiaPassiveServer;
import com.leafia.transformer.LeafiaGeneralLocal;
import com.leafia.transformer.LeafiaGls;
import com.leafia.transformer.WorldServerLeafia;
import com.llib.exceptions.LeafiaDevFlaw;
import net.minecraft.launchwrapper.IClassTransformer;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.asm.transformers.deobf.FMLDeobfuscatingRemapper;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Field;
import java.util.*;

import static org.objectweb.asm.Opcodes.*;

public class TransformerCoreLeafia implements IClassTransformer {
	// fuck you in particular
	public static final String[] classesBeingTransformed = {
			"net.minecraft.client.renderer.GlStateManager",
			"net.minecraft.world.WorldServer",
			"net.minecraft.client.gui.GuiMainMenu",
			"net.minecraft.client.renderer.EntityRenderer",
			"net.minecraftforge.fluids.FluidTank",
			"net.minecraft.world.ServerWorldEventHandler"
	};
	@Override
	public byte[] transform(String name, String transformedName, byte[] classBeingTransformed) {
		///System.out.println("#Leaf: Transform Input: " + name + " : " + transformedName);


		/*try {
			if (!name.equals("net.minecraftforge.fml.common.Loader") && !name.equals("net.minecraftforge.fluids.capability.IFluidHandler"))
				System.out.println("#Leaf: AssignableFrom "+IFluidHandler.class.isAssignableFrom(Class.forName(name)));
		} catch (ClassNotFoundException e) {
			System.out.println("#Leaf: Nope, errrrrrrrrr");
		}*/

		boolean isObfuscated = !name.equals(transformedName);
		int index = Arrays.asList(classesBeingTransformed).indexOf(transformedName);
		//return /*index != -1 ? */transform(index, classBeingTransformed, isObfuscated);// : classBeingTransformed;
		return index != -1 ? transform(index, classBeingTransformed, isObfuscated) : classBeingTransformed;
	}
	public static class LeafiaDevErrorGls extends RuntimeException {
		public LeafiaDevErrorGls(String s) {
			super(s);
		}
	}
	public static byte[] transform(int index,byte[] classBeingTransformed,boolean isObfuscated) {
		String name = "anonymous";
		if (index >= 0) {
			name = classesBeingTransformed[index];
			System.out.println("#Leaf: Transforming: " + name);
		}
		//else System.out.println("#Leaf: Transforming anonymous");
		try {
			ClassNode classNode = new ClassNode();
			ClassReader classReader = new ClassReader(classBeingTransformed);
			classReader.accept(classNode, 0);

			if (index < 0) {
				if (classNode.interfaces.contains("net/minecraftforge/fluids/capability/IFluidHandler")) {
					System.out.println("Yeah!");
					doTransform(classNode,isObfuscated,WorldServerLeafia.class,-2);
				} else
					return classBeingTransformed;
			} else {
				switch (index) {
					case 0:
						doTransform(classNode,isObfuscated,LeafiaGls.Handler.class,index);
						break;
					case 1: case 4: case 5:
						doTransform(classNode,isObfuscated,WorldServerLeafia.class,index);
						break;
					case 2: case 3:
						doTransform(classNode,isObfuscated,LeafiaGeneralLocal.class,index);// fuck you.period.
						break;
					default:
						throw new LeafiaDevErrorGls("#Leaf: Unexpected index "+index);
				}
			}

			ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES);
			classNode.accept(classWriter);
			System.out.println("#Leaf: Transform Complete: " + name + " ("+classBeingTransformed.length+" -> "+classWriter.toByteArray().length+")");
			return classWriter.toByteArray();
		} catch (Exception e) {
			System.out.println("#Leaf ERROR: " + name);
			e.printStackTrace();
			if (e instanceof LeafiaDevErrorGls || e instanceof LeafiaDevFlaw) {
				LeafiaPassiveServer.queueFunction(()->{throw e;}); // fuck you
				//throw e;
			}
		}
		System.out.println("#Leaf: Transform End: " + name);
		return classBeingTransformed;
	}
	static final Map<String,String> furtherDeobf = new HashMap<>();
	static final Map<Integer,String> opcodeMap = new HashMap<>();
	static {
		for (Field field : Opcodes.class.getFields()) {
			try {
				if (field.getType().getTypeName().equals("int"))
					opcodeMap.put(field.getInt(null),field.getName());
			} catch (IllegalAccessException ignored) {};
		}
		//furtherDeobf.put();
		{
			furtherDeobf.put("func_179082_a","clearColor");
			furtherDeobf.put("func_179083_b","viewport");
			furtherDeobf.put("func_179084_k","disableBlend");
			furtherDeobf.put("func_179085_a","enableLight");
			furtherDeobf.put("func_179086_m","clear");
			furtherDeobf.put("func_179087_a","enableTexGenCoord");
			furtherDeobf.put("func_179088_q","enablePolygonOffset");
			furtherDeobf.put("func_179089_o","enableCull");
			furtherDeobf.put("func_179090_x","disableTexture2D");
			furtherDeobf.put("func_179091_B","enableRescaleNormal");
			furtherDeobf.put("func_179092_a","alphaFunc");
			furtherDeobf.put("func_179093_d","setFog");
			furtherDeobf.put("func_179094_E","pushMatrix");
			furtherDeobf.put("func_179095_a","setFogDensity");
			furtherDeobf.put("func_179096_D","loadIdentity");
			furtherDeobf.put("func_179097_i","disableDepth");
			furtherDeobf.put("func_179098_w","enableTexture2D");
			furtherDeobf.put("func_179099_b","popAttrib");
			furtherDeobf.put("func_179100_b","disableTexGenCoord");
			furtherDeobf.put("func_179101_C","disableRescaleNormal");
			furtherDeobf.put("func_179102_b","setFogStart");
			furtherDeobf.put("func_179103_j","shadeModel");
			furtherDeobf.put("func_179104_a","colorMaterial");
			furtherDeobf.put("func_179105_a","texGen");
			furtherDeobf.put("func_179106_n","disableFog");
			furtherDeobf.put("func_179107_e","cullFace");
			furtherDeobf.put("func_179108_z","enableNormalize");
			furtherDeobf.put("func_179109_b","translate");
			furtherDeobf.put("func_179110_a","multMatrix");
			furtherDeobf.put("func_179111_a","getFloat");
			//furtherDeobf.put("func_179112_b","blendFunc");
			furtherDeobf.put("func_179113_r","disablePolygonOffset");
			furtherDeobf.put("func_179114_b","rotate");
			furtherDeobf.put("func_179115_u","enableColorLogic");
			furtherDeobf.put("func_179116_f","colorLogicOp");
			furtherDeobf.put("func_179117_G","resetColor");
			furtherDeobf.put("func_179118_c","disableAlpha");
			furtherDeobf.put("func_179119_h","disableColorMaterial");
			//furtherDeobf.put("func_179120_a","tryBlendFuncSeparate");
			furtherDeobf.put("func_179121_F","popMatrix");
			furtherDeobf.put("func_179122_b","disableLight");
			furtherDeobf.put("func_179123_a","pushAttrib");
			furtherDeobf.put("func_179124_c","color");
			furtherDeobf.put("func_179125_c","texGenCoord");
			furtherDeobf.put("func_179126_j","enableDepth");
			furtherDeobf.put("func_179127_m","enableFog");
			furtherDeobf.put("func_179128_n","matrixMode");
			furtherDeobf.put("func_179129_p","disableCull");
			furtherDeobf.put("func_179130_a","ortho");
			furtherDeobf.put("func_179131_c","color");
			furtherDeobf.put("func_179132_a","depthMask");
			furtherDeobf.put("func_179133_A","disableNormalize");
			furtherDeobf.put("func_179134_v","disableColorLogic");
			furtherDeobf.put("func_179135_a","colorMask");
			furtherDeobf.put("func_179136_a","doPolygonOffset");
			furtherDeobf.put("func_179137_b","translate");
			furtherDeobf.put("func_179138_g","setActiveTexture");
			furtherDeobf.put("func_179139_a","scale");
			furtherDeobf.put("func_179140_f","disableLighting");
			furtherDeobf.put("func_179141_d","enableAlpha");
			furtherDeobf.put("func_179142_g","enableColorMaterial");
			furtherDeobf.put("func_179143_c","depthFunc");
			furtherDeobf.put("func_179144_i","bindTexture");
			furtherDeobf.put("func_179145_e","enableLighting");
			furtherDeobf.put("func_179146_y","generateTexture");
			furtherDeobf.put("func_179147_l","enableBlend");
			furtherDeobf.put("func_179148_o","callList");
			furtherDeobf.put("func_179149_a","texGen");
			furtherDeobf.put("func_179150_h","deleteTexture");
			furtherDeobf.put("func_179151_a","clearDepth");
			furtherDeobf.put("func_179152_a","scale");
			furtherDeobf.put("func_179153_c","setFogEnd");
			furtherDeobf.put("func_179198_a","setDisabled");
			furtherDeobf.put("func_179199_a","setState");
			furtherDeobf.put("func_179200_b","setEnabled");
			furtherDeobf.put("func_187402_b","glFog");
			furtherDeobf.put("func_187412_c","glFogi");

			furtherDeobf.put("func_148821_a","blendFunc");
			furtherDeobf.put("func_179112_b","blendFunc");
			furtherDeobf.put("func_179120_a","tryBlendFuncSeparate");
			furtherDeobf.put("func_187401_a","blendFunc");
			furtherDeobf.put("func_187428_a","tryBlendFuncSeparate");
		}
		{
			furtherDeobf.put("func_175715_c","sendBlockBreakProgress");
			furtherDeobf.put("func_180441_b","sendBlockBreakProgress");
		}
		// (func_\w+),(\w+),.,(.*)
		// furtherDeobf.put("$1","$2"); // $3
		{
			furtherDeobf.put("func_180798_a","renderDebugInfoLeft");
			furtherDeobf.put("func_78474_d","renderRainSnow");

			furtherDeobf.put("func_78484_h","addRainParticles");


			furtherDeobf.put("func_177855_a","setBlockState"); //
			furtherDeobf.put("func_177856_a","getBlockState"); //
			furtherDeobf.put("func_177865_a","jsonToFactory"); //
			furtherDeobf.put("func_177951_i","distanceSq"); // Calculate squared distance to the given Vector
			furtherDeobf.put("func_177952_p","getZ"); // Get the Z coordinate
			furtherDeobf.put("func_177954_c","distanceSq"); // Calculate squared distance to the given coordinates
			furtherDeobf.put("func_177955_d","crossProduct"); // Calculate the cross product of this and the given Vector
			furtherDeobf.put("func_177956_o","getY"); // Get the Y coordinate
			furtherDeobf.put("func_177957_d","distanceSqToCenter"); // "Compute square of distance from point x, y, z to center of this Block"
			furtherDeobf.put("func_177958_n","getX"); // Get the X coordinate
			furtherDeobf.put("func_177963_a","add"); // Add the given coordinates to the coordinates of this BlockPos
			furtherDeobf.put("func_177964_d","north"); // Offset this BlockPos n blocks in northern direction
			furtherDeobf.put("func_177965_g","east"); // Offset this BlockPos n blocks in eastern direction
			furtherDeobf.put("func_177967_a","offset"); // Offsets this BlockPos n blocks in the given direction
			furtherDeobf.put("func_177968_d","south"); // Offset this BlockPos 1 block in southern direction
			furtherDeobf.put("func_177969_a","fromLong"); // Create a BlockPos from a serialized long value (created by toLong)
			furtherDeobf.put("func_177970_e","south"); // Offset this BlockPos n blocks in southern direction
			furtherDeobf.put("func_177971_a","add"); // Add the given Vector to this BlockPos
			furtherDeobf.put("func_177972_a","offset"); // Offset this BlockPos 1 block in the given direction
			furtherDeobf.put("func_177973_b","subtract"); // Subtract the given Vector from this BlockPos
			furtherDeobf.put("func_177974_f","east"); // Offset this BlockPos 1 block in eastern direction
			furtherDeobf.put("func_177975_b","getAllInBoxMutable"); // "Like getAllInBox but reuses a single MutableBlockPos instead. If this method is used, the resulting BlockPos instances can only be used inside the iteration loop."
			furtherDeobf.put("func_177976_e","west"); // Offset this BlockPos 1 block in western direction
			furtherDeobf.put("func_177977_b","down"); // Offset this BlockPos 1 block down
			furtherDeobf.put("func_177978_c","north"); // Offset this BlockPos 1 block in northern direction
			furtherDeobf.put("func_177979_c","down"); // Offset this BlockPos n blocks down
			furtherDeobf.put("func_177980_a","getAllInBox"); // Create an Iterable that returns all positions in the box specified by the given corners
			furtherDeobf.put("func_177981_b","up"); // Offset this BlockPos n blocks up
			furtherDeobf.put("func_177982_a","add"); // Add the given coordinates to the coordinates of this BlockPos
			furtherDeobf.put("func_177984_a","up"); // Offset this BlockPos 1 block up
			furtherDeobf.put("func_177985_f","west"); // Offset this BlockPos n blocks in western direction
			furtherDeobf.put("func_177986_g","toLong"); // Serialize this BlockPos into a long value
		}
		{ // thank you for making me go through all this suffering Mojang
			/*
			furtherDeobf.put("field_145850_b","world");
			furtherDeobf.put("field_147300_g","world");
			furtherDeobf.put("field_147550_f","world");
			furtherDeobf.put("field_150660_b","world");
			furtherDeobf.put("field_150867_a","world");
			furtherDeobf.put("field_175128_a","world");
			furtherDeobf.put("field_175130_a","world");
			furtherDeobf.put("field_175946_l","world");
			furtherDeobf.put("field_177261_a","world");
			furtherDeobf.put("field_177463_c","world");
			furtherDeobf.put("field_177515_a","world");
			furtherDeobf.put("field_177680_a","world");
			furtherDeobf.put("field_178167_b","world");
			furtherDeobf.put("field_178588_d","world");
			furtherDeobf.put("field_185952_n","world");
			furtherDeobf.put("field_185995_n","world");
			furtherDeobf.put("field_186110_d","world");
			furtherDeobf.put("field_186474_a","world");
			furtherDeobf.put("field_186499_b","world");
			furtherDeobf.put("field_187122_b","world");
			furtherDeobf.put("field_70170_p","world");
			furtherDeobf.put("field_71441_e","world");
			furtherDeobf.put("field_72701_a","world");
			furtherDeobf.put("field_72769_h","world");
			furtherDeobf.put("field_72782_b","world");
			furtherDeobf.put("field_72795_a","world");
			furtherDeobf.put("field_72815_e","world");
			furtherDeobf.put("field_73092_a","world");
			furtherDeobf.put("field_73163_a","world");
			furtherDeobf.put("field_73230_p","world");
			furtherDeobf.put("field_73235_d","world");
			furtherDeobf.put("field_73251_h","world");
			furtherDeobf.put("field_75039_c","world");
			furtherDeobf.put("field_75161_g","world");
			furtherDeobf.put("field_75172_h","world");
			furtherDeobf.put("field_75177_g","world");
			furtherDeobf.put("field_75342_a","world");
			furtherDeobf.put("field_75367_f","world");
			furtherDeobf.put("field_75386_c","world");
			furtherDeobf.put("field_75394_a","world");
			furtherDeobf.put("field_75411_a","world");
			furtherDeobf.put("field_75443_a","world");
			furtherDeobf.put("field_75448_d","world");
			furtherDeobf.put("field_75513_b","world");
			furtherDeobf.put("field_75537_a","world");
			furtherDeobf.put("field_75556_a","world");
			furtherDeobf.put("field_75586_a","world");
			furtherDeobf.put("field_76579_a","world");
			furtherDeobf.put("field_76637_e","world");
			furtherDeobf.put("field_77287_j","world");
			furtherDeobf.put("field_78722_g","world");
			furtherDeobf.put("field_78878_a","world");
			furtherDeobf.put("field_82627_a","world");
			furtherDeobf.put("field_82860_h","world");
			furtherDeobf.put("field_85192_a","world");*/
		}
		furtherDeobf.put("func_73044_a","saveAllChunks");
	}
	public static class Helper {
		MethodNode method;
		ClassNode target;
		public List<AbstractInsnNode> instructions;
		Class<?> listener;
		public Helper(MethodNode mthd, Class<?> listener,ClassNode target) {
			method = mthd;
			instructions = new ArrayList<>();
			this.listener = listener;
			this.target = target;
		}
		public void stackManipulateStore(int op,int index) {
			instructions.add(new VarInsnNode(op,index));
		}
		public void stackPush(int op) {
			instructions.add(new InsnNode(op));
		}
		public void stackPushInt(int op,int value) {
			instructions.add(new IntInsnNode(op,value));
		}
		public void stackCall(String target,String desc) {
			//               V    Z    C    B    S     I   F     J    D      idk   L     (any)
			// descriptors: void bool char byte short int float long double array object method
			instructions.add(new MethodInsnNode(INVOKESTATIC,Type.getInternalName(listener),target,desc,false));
			System.out.println("#      Added method call "+target+" : "+desc);
		}
		public void bindDirectly(String name,String desc) {
			int index = 0;
			boolean referencing = false;
			int arrayCounter = 0;
			int ignoreCounter = 0;
			StringBuilder descArgs = new StringBuilder();
			for (String s : desc.split("")) {
				if (s.equals("(")) continue;
				if (s.equals(")")) break;
				if (referencing) {
					descArgs.append(s);
					if (s.equals("<"))
						ignoreCounter++;
					else if (s.equals(">"))
						ignoreCounter--;
					if (ignoreCounter <= 0) {
						if (s.equals(";")) {
							referencing = false;
							arrayCounter = 0;
						}
					}
				} else {
					if (!s.equals("x")) { // dummy for in case we wanted to skip some args
						if (s.equals("L")) {
							referencing = true;
							if (arrayCounter <= 0) {
								this.stackManipulateStore(ALOAD,index);
								index++;
							}
						} else if (s.equals("[")) {
							if (arrayCounter++ <= 0) {
								this.stackManipulateStore(ALOAD,index);
								index++;
							}
						} else if (arrayCounter <= 0) {
							switch (s) {
								case "Z": case "C": case "B": case "S":
								case "I": this.stackManipulateStore(ILOAD,index); break;
								case "F": this.stackManipulateStore(FLOAD,index); break;
								case "J": this.stackManipulateStore(LLOAD,index); break;
								case "D": this.stackManipulateStore(DLOAD,index); break;
							}
							index++;
						} else
							arrayCounter = 0;
						descArgs.append(s);
					} else index++;
				}
			}
			this.stackCall(name,"("+descArgs+")V");
		}
	}
	static void printBytecodes(InsnList codes) {
		FMLDeobfuscatingRemapper pain = FMLDeobfuscatingRemapper.INSTANCE;
		for (AbstractInsnNode node : codes.toArray()) {
			// sneaky sneaky
			if (node instanceof LineNumberNode)// {
				System.out.println("#    Line " + ((LineNumberNode) node).line);
			String s = "#      "+String.format("%02Xh : ",node.getOpcode()&0xFF)+(opcodeMap.containsKey(node.getOpcode()&0xFF) ? opcodeMap.get(node.getOpcode()&0xFF) : node.getClass().getSimpleName())+" >> ";
			if (node instanceof MethodInsnNode) {
				String ass = pain.mapMethodName(((MethodInsnNode)node).owner,((MethodInsnNode)node).name,((MethodInsnNode)node).desc);
				s = s + pain.map(((MethodInsnNode)node).owner)+"."+furtherDeobf.getOrDefault(ass,ass)+pain.mapMethodDesc(((MethodInsnNode)node).desc);
			} else if (node instanceof VarInsnNode)
				s = s +((VarInsnNode)node).var;
			else if (node instanceof FieldInsnNode)
				s = s + pain.mapDesc(((FieldInsnNode)node).desc)+" : "+pain.map(((FieldInsnNode)node).owner)+"."+pain.mapFieldName(((FieldInsnNode)node).owner,((FieldInsnNode)node).name,((FieldInsnNode)node).desc);
			else if (node instanceof LabelNode && ((LabelNode) node).getLabel() != null)
				s = s + ((LabelNode) node).getLabel().toString();
			else if (node instanceof JumpInsnNode && ((JumpInsnNode) node).label != null && ((JumpInsnNode) node).label.getLabel() != null)
				s = s + ((JumpInsnNode) node).label.getLabel().toString();
			else if (node instanceof LdcInsnNode && ((LdcInsnNode) node).cst != null)
				s = s + ((LdcInsnNode) node).cst.toString();
			System.out.println(s);
		}
	}
	private static boolean tryBind(String name,String desc,Helper helper,int transformerIndex) {
		FMLDeobfuscatingRemapper pain = FMLDeobfuscatingRemapper.INSTANCE;
		switch(transformerIndex) {
			case 0: {
				if ((helper.method.access&ACC_PUBLIC) == 0)
					return false;
				if (name.startsWith("enable") || name.startsWith("disable")) {
					for (String s : new String[]{
							"Blend","Alpha","Fog","Lighting","ColorMaterial","Texture2D", // shader.preInit
							"ColorLogic","Cull","Normalize","PolygonOffset","RescaleNormal","Depth" // randoms i found
					}) {
						if (name.endsWith(s)) {
							// thank https://godbolt.org/
							helper.stackPush(name.startsWith("enable") ? ICONST_1 : ICONST_0);
							helper.stackCall("rec" + s,"(Z)V");
							return true;
						}
					}
				}
				switch (name) {
					case "depthMask":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackCall("depthMask","(Z)V");
						return true;
					case "depthFunc":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackCall("depthFunc","(I)V");
						return true;
					case "blendFunc":
						if (desc.equals("(II)V")) {
							helper.stackManipulateStore(ILOAD,0);
							helper.stackManipulateStore(ILOAD,1);
							helper.stackCall("blendFunc","(II)V");
							return true;
						} else break;
					case "tryBlendFuncSeparate":
						if (desc.equals("(IIII)V")) {
							helper.stackManipulateStore(ILOAD,0);
							helper.stackManipulateStore(ILOAD,1);
							helper.stackManipulateStore(ILOAD,2);
							helper.stackManipulateStore(ILOAD,3);
							helper.stackCall("tryBlendFuncSeparate","(IIII)V");
							return true;
						} else break;
					case "alphaFunc": case "shadeModel":
					case "setFog": case "setFogStart": case "setFogEnd": case "setFogDensity": case "glFogi": //case "glFog":
						helper.bindDirectly(name,desc);
						return true;

					case "enableLight":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackPush(ICONST_1);
						helper.stackCall("recLight","(IZ)V");
						return true;
					case "glEnableClientState":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackPush(ICONST_1);
						helper.stackCall("recClientState","(IZ)V");
						return true;
					case "disableLight":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackPush(ICONST_0);
						helper.stackCall("recLight","(IZ)V");
						return true;
					case "glDisableClientState":
						helper.stackManipulateStore(ILOAD,0);
						helper.stackPush(ICONST_0);
						helper.stackCall("recClientState","(IZ)V");
						return true;
				}
			} break;
			case 1:
				if (name.equals("saveAllChunks")) {
					helper.stackManipulateStore(ALOAD,0); // for instantized classes, var 0 will be "this" apparently (thanks https://godbolt.org/)
					helper.stackCall("saveAllChunks","(Lnet/minecraft/world/WorldServer;)V");
					return true;
				}
				break;
			case 2:
				if (name.equals("<init>")) {
					for (FieldNode node : helper.target.fields) {
						//System.out.println("#      Field "+node.desc+" >> "+node.name);
					}
					int lastALOAD = -1;
					Integer confirmedListIndex = null;
					Integer possibleListIndex = null;
					boolean randmark = false;
					for (AbstractInsnNode node : helper.method.instructions.toArray()) {
						if (node instanceof MethodInsnNode) {
							MethodInsnNode mthd = (MethodInsnNode)node;
							if (mthd.getOpcode() == INVOKEVIRTUAL) {
								if (mthd.owner.equals("java/util/Random") && mthd.name.equals("nextInt") && mthd.desc.equals("(I)I")) {
									randmark = true;
								}
							} else if (mthd.getOpcode() == INVOKEINTERFACE) {
								if (mthd.owner.equals("java/util/List") && mthd.name.equals("get") && mthd.desc.equals("(I)Ljava/lang/Object;") && randmark) {
									randmark = false;
									possibleListIndex = lastALOAD;
								}
							}
						} else if (node instanceof FieldInsnNode) {
							FieldInsnNode field = (FieldInsnNode)node;
							if (field.getOpcode() == PUTFIELD) {
								if (field.desc.equals("Ljava/lang/String;") && field.owner.equals(helper.target.name) && (possibleListIndex != null)) {
									confirmedListIndex = possibleListIndex;
									break;
								}
							}
						} else if (node instanceof VarInsnNode) {
							VarInsnNode var = (VarInsnNode)node;
							if (var.getOpcode() == ALOAD) {
								lastALOAD = var.var;
							}
						}

						/*
						if (((LineNumberNode) node).line == 99) {
							helper.method.instructions.insert(
									node,
									new MethodInsnNode(
											INVOKESTATIC,Type.getInternalName(helper.listener),
											"injectWackySplashes","(Ljava/util/List;)V",false
									)
							);
							helper.method.instructions.insert(node,new VarInsnNode(ALOAD,2));
							System.out.println("#      Added method call injectWackySplashes : (Ljava/util/List;)V");
							return true;
						}*/
						//}
						/*
						// sneaky sneaky
						if (node instanceof LineNumberNode)// {
							System.out.println("#    Line " + ((LineNumberNode) node).line);
						String s = "#      "+String.format("%02Xh : ",node.getOpcode()&0xFF)+(opcodeMap.containsKey(node.getOpcode()&0xFF) ? opcodeMap.get(node.getOpcode()&0xFF) : node.getClass().getSimpleName())+" >> ";
						if (node instanceof MethodInsnNode)
							s = s + ((MethodInsnNode)node).owner+"."+((MethodInsnNode)node).name+((MethodInsnNode)node).desc;
						else if (node instanceof VarInsnNode)
							s = s +((VarInsnNode)node).var;
						else if (node instanceof FieldInsnNode)
							s = s + ((FieldInsnNode)node).desc+" : "+((FieldInsnNode)node).owner+"."+((FieldInsnNode)node).name;
						else if (node instanceof LabelNode && ((LabelNode) node).getLabel() != null)
							s = s + ((LabelNode) node).getLabel().toString();
						else if (node instanceof JumpInsnNode && ((JumpInsnNode) node).label != null && ((JumpInsnNode) node).label.getLabel() != null)
							s = s + ((JumpInsnNode) node).label.getLabel().toString();
						System.out.println(s);*/
					}
					if (confirmedListIndex != null) {
						for (AbstractInsnNode node : helper.method.instructions.toArray()) {
							if (node instanceof VarInsnNode) {
								VarInsnNode var = (VarInsnNode)node;
								if (var.getOpcode() == ASTORE) {
									if (var.var == confirmedListIndex) {
										helper.method.instructions.insert(
												node,
												new MethodInsnNode(
														INVOKESTATIC,Type.getInternalName(helper.listener),
														"injectWackySplashes","(Ljava/util/List;)V",false
												)
										);
										helper.method.instructions.insert(node,new VarInsnNode(ALOAD,confirmedListIndex));
										System.out.println("#      Added method call injectWackySplashes : (Ljava/util/List;)V");
										return true;
									}
								}
							}
						}
					}
				}
				/*
				if (name.equals("renderDebugInfoLeft")) {
					AbstractInsnNode lastLine = null;
					VarInsnNode lastALOAD = null;
					Integer listIndex = null;
					for (AbstractInsnNode node : helper.method.instructions.toArray()) {
						// sneaky sneaky
						//System.out.println("#      Node: "+Integer.toHexString(node.getOpcode()&0xFF)+" : "+node.getType()+" ["+node.getClass().getSimpleName()+"]");
						if (node instanceof LineNumberNode) {
							lastLine = node;
							lastALOAD = null;
						} else if (node instanceof VarInsnNode) {
							if (node.getOpcode() == ALOAD) {
								lastALOAD = (VarInsnNode)node;
							}
						} else if (node instanceof MethodInsnNode) {
							if (node.getOpcode() == INVOKEINTERFACE) {
								if (lastALOAD != null && listIndex == null)
									listIndex = lastALOAD.var;
							}
						} else if (node instanceof JumpInsnNode) { // we found a loop
							if (lastLine != null && listIndex != null) {
								helper.method.instructions.insertBefore(lastLine,new VarInsnNode(ALOAD,listIndex));
								helper.method.instructions.insertBefore(
										lastLine,
										new MethodInsnNode(
												INVOKESTATIC,Type.getInternalName(helper.listener),
												"injectDebugInfoLeft","(Ljava/util/List;)V",false
										)
								);
								System.out.println("#      Added method call injectDebugInfoLeft : (Ljava/util/List;)V");
								return true;
							}
						}
					}
				}*/
				// guess what's so sad, this whole thing was unnecessary
				// it was possible to modify using events from forge, in the most confusing name of just "Text" Bruh bro
				// Searchability -32768/10
				break;
			case 3:
				if (name.equals("addRainParticles")) {
					//printBytecodes(helper.method.instructions);
					int progress = -10;
					int entityLocation = 0;
					int biomeLocation = 0;
					int posLocation = 0;
					int stateLocation = 0;
					int rxLocation = 0;
					int ryLocation = 0;
					int alignBBLocation = 0;
					LabelNode skip = null;
					LabelNode finalLabel = null;
					for (AbstractInsnNode node : helper.method.instructions.toArray()) {
						if (node instanceof MethodInsnNode) {
							MethodInsnNode insn = (MethodInsnNode)node;
							String ds = pain.mapMethodDesc(insn.desc);
							String nm = pain.mapMethodName(insn.owner,insn.name,insn.desc);
							if (insn.getOpcode() == INVOKEVIRTUAL) {
								if (ds.matches(".*\\)L.*Entity;.*") && progress == -10)
									progress = -11;
								else if (ds.matches(".*\\(L.*BlockPos;\\)L.*Biome;.*") && progress == 0)
									progress = 1;
								else if (furtherDeobf.getOrDefault(nm,nm).equals("down") && progress == 10)
									progress = 11;
								else if (ds.matches(".*\\)L.*IBlockState;.*") && progress == 20)
									progress = 21;
								else if (furtherDeobf.getOrDefault(nm,nm).equals("nextDouble") && progress >= 30 && progress < 40 && progress%2 == 0)
									progress++;
							} else if (insn.getOpcode() == INVOKEINTERFACE && ds.matches(".*\\)L.*AxisAlignedBB;.*") && progress == 40)
								progress = 41;
						} else if (progress > 0 || progress == -11) { // potato coding :D
							if (node instanceof VarInsnNode) {
								VarInsnNode insn = (VarInsnNode)node;
								if (insn.getOpcode() == ASTORE) {
									if (progress == -11) {
										progress = 0;
										entityLocation = insn.var;
									} else if (progress == 1) {
										progress = 10;
										biomeLocation = insn.var;
									} else if (progress == 11) {
										progress = 20;
										posLocation = insn.var;
									} else if (progress == 21) {
										progress = 30;
										stateLocation = insn.var;
									} else if (progress == 41) {
										progress = 50;
										alignBBLocation = insn.var;
									}
								} else if (insn.getOpcode() == DSTORE) {
									if (progress == 31) {
										progress = 32;
										rxLocation = insn.var;
									} else if (progress == 33) {
										progress = 40;
										ryLocation = insn.var;
									}
								}
							} else if (node instanceof JumpInsnNode) {
								JumpInsnNode insn = (JumpInsnNode)node;
								if (opcodeMap.getOrDefault(insn.getOpcode(),"unknown").startsWith("IF")) {
									if (progress == 30)
										skip = insn.label;
									else if (progress == 60) {
										progress = 70;
										finalLabel = insn.label;
									}
								}
							} else if (node instanceof IincInsnNode && progress == 50)
								progress = 60;
							else if (node instanceof LabelNode) {
								if (progress == 70 && finalLabel == node) {
									progress = 80;
									if (skip != null) {
										System.out.println("#      Injecting branch for wasteland biomes");
										MethodInsnNode callback = new MethodInsnNode(
												INVOKESTATIC,
												Type.getInternalName(LeafiaGeneralLocal.class),
												"acidRainParticles",
												"(Lnet/minecraft/entity/Entity;Lnet/minecraft/world/biome/Biome;Lnet/minecraft/block/state/IBlockState;Lnet/minecraft/util/math/BlockPos;DDLnet/minecraft/util/math/AxisAlignedBB;)Z",
												false
										);
										helper.method.instructions.insert(node,callback);
										helper.method.instructions.insert(callback,new JumpInsnNode(IFEQ,skip));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(ALOAD,entityLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(ALOAD,biomeLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(ALOAD,stateLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(ALOAD,posLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(DLOAD,rxLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(DLOAD,ryLocation));
										helper.method.instructions.insertBefore(callback,new VarInsnNode(ALOAD,alignBBLocation));
										return true;
									}
								}
							}
						}
					}
				} else if (name.equals("renderRainSnow")) {
					FieldNode resourceNode = null;
					for (FieldNode node : helper.target.fields) {
						String deobfName = pain.mapFieldName(helper.target.name,node.name,node.desc);
						if ((node.access&ACC_STATIC) > 0) {
							if (deobfName.equals("field_110924_q") || deobfName.equals("RAIN_TEXTURES") || deobfName.equals("locationRainPng")) {
								resourceNode = node;
								System.out.println("#      Field RAIN_TEXTURES successfully found >> " + node.name);
							}
						}
					}
					if (resourceNode != null) {
						int biomeVarCreationCheck = 0;
						Integer biomeVarStoreId = null;
						FieldInsnNode queryNode = null;
						for (AbstractInsnNode node : helper.method.instructions.toArray()) {
							if (node instanceof MethodInsnNode) {
								MethodInsnNode insn = (MethodInsnNode)node;
								if (insn.getOpcode() == INVOKEVIRTUAL) {
									String deobfDesc = pain.mapMethodDesc(insn.desc);
									System.out.println("#      INVOKEVIRTUAL -> "+deobfDesc);
									if (deobfDesc.matches("\\(L[^;]+;\\)L"+Type.getInternalName(Biome.class)+";")) {
										System.out.println("#      Type biome");
										biomeVarCreationCheck = 2;//1;
										continue;
									}
								}/*
							} else if (node instanceof InsnNode) {
								if (biomeVarCreationCheck == 1) {
									if (node.getOpcode() == DUP) {
										biomeVarCreationCheck = 2;
									}
								}*/
							} else if (node instanceof VarInsnNode) {
								VarInsnNode insn = (VarInsnNode)node;
								if (insn.getOpcode() == ASTORE && biomeVarCreationCheck == 2) {
									biomeVarStoreId = insn.var;
									biomeVarCreationCheck = 3; // complete
									System.out.println("#      Detected biome var location: "+biomeVarStoreId);
								}
							} else if (node instanceof FieldInsnNode) {
								FieldInsnNode insn = (FieldInsnNode)node;
								if (insn.owner.equals(helper.target.name) && insn.name.equals(resourceNode.name) && insn.desc.equals(resourceNode.desc)) {
									if (insn.getOpcode() == GETSTATIC && biomeVarStoreId != null) {
										System.out.println("#      Reference to RAIN_TEXTURES successfully found");
										queryNode = insn;
										break;
									}
								}
							}
							if (biomeVarCreationCheck < 2)
								biomeVarCreationCheck = 0;
						}
						if (queryNode != null) {
							System.out.println("#      Injecting branch for wasteland biomes");
							FieldInsnNode acidNode = new FieldInsnNode(GETSTATIC,Type.getInternalName(LeafiaGeneralLocal.class),"acidRain",resourceNode.desc);
							LabelNode skipNode = new LabelNode();
							LabelNode elseNode = new LabelNode();
							helper.method.instructions.insert(queryNode,skipNode);
							helper.method.instructions.insertBefore(queryNode,elseNode);
							helper.method.instructions.insertBefore(elseNode,acidNode);
							helper.method.instructions.insert(acidNode,new JumpInsnNode(GOTO,skipNode));
							helper.method.instructions.insertBefore(acidNode,new VarInsnNode(ALOAD,biomeVarStoreId));
							helper.method.instructions.insertBefore(acidNode,new TypeInsnNode(INSTANCEOF,Type.getInternalName(HasAcidicRain.class)));
							helper.method.instructions.insertBefore(acidNode,new JumpInsnNode(IFEQ,elseNode));
							// this translates to:
							/* ...getstatic RAIN_TEXTURES... transform to:
								...
								aload biome
								^^ instanceof HasAcidicRain
								^^ ifeq (0/false) :: (goto) elseNode
								getstatic acidRain
								goto skipNode
								[elseNode]
								getstatic RAIN_TEXTURES
								[skipNode]
								...
							 */
							// *(brackets) mean that they're not arguments and instead some note
							return true;
						}
					}
				}
				break;
			case 4:
				if (name.equals("fillInternal")) {
					LabelNode skipNode = new LabelNode();
					{
						helper.method.instructions.insert(skipNode);
						helper.method.instructions.insertBefore(skipNode,new VarInsnNode(ALOAD,1));

						helper.method.instructions.insertBefore(skipNode,new VarInsnNode(ALOAD,0));
						helper.method.instructions.insertBefore(skipNode,new FieldInsnNode(GETFIELD,helper.target.name,"tile","Lnet/minecraft/tileentity/TileEntity;"));

						helper.method.instructions.insertBefore(skipNode,new MethodInsnNode(INVOKESTATIC,Type.getInternalName(helper.listener),"fluid_canContinue","(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraft/tileentity/TileEntity;)Z",false));
					}
					helper.method.instructions.insertBefore(skipNode,new JumpInsnNode(IFGT,skipNode));
					helper.method.instructions.insertBefore(skipNode,new InsnNode(ICONST_0));
					helper.method.instructions.insertBefore(skipNode,new InsnNode(IRETURN));
					return true;
				}
				break;
			case 5:
				if (name.equals("sendBlockBreakProgress")) {
					String confirmedField = "ERR_FIELD_NOT_FOUND";
					for (FieldNode field : helper.target.fields) {
						//if (furtherDeobf.containsKey(field.name) && furtherDeobf.get(field.name).equals("world")) {
						if (pain.mapDesc(field.desc).equals("Lnet/minecraft/world/WorldServer;")) { // fuck you
							confirmedField = field.name;
							break;
						}
					}
					if (confirmedField.equals("ERR_FIELD_NOT_FOUND"))
						throw new LeafiaDevFlaw("LeafiaCore mod error: WorldServer field failed to detect in ServerWorldEventHandler"); // this is better
					helper.method.instructions.insert(new MethodInsnNode(INVOKESTATIC,Type.getInternalName(helper.listener),"player_onBreakBlockProgress","(Lnet/minecraft/world/WorldServer;ILnet/minecraft/util/math/BlockPos;I)V",false));
					helper.method.instructions.insert(new VarInsnNode(ILOAD,3));
					helper.method.instructions.insert(new VarInsnNode(ALOAD,2));
					helper.method.instructions.insert(new VarInsnNode(ILOAD,1));
					helper.method.instructions.insert(new FieldInsnNode(GETFIELD,helper.target.name,confirmedField,"Lnet/minecraft/world/WorldServer;"));
					helper.method.instructions.insert(new VarInsnNode(ALOAD,0));
					return true;
				}
				break;
			case -2:
				if (name.equals("fill") && desc.equals("(Lnet/minecraftforge/fluids/FluidStack;Z)I")) {
					helper.method.instructions.insert(new MethodInsnNode(INVOKESTATIC,Type.getInternalName(helper.listener),"fluid_onFilling","(Lnet/minecraftforge/fluids/FluidStack;Lnet/minecraftforge/fluids/capability/IFluidHandler;)V",false));
					helper.method.instructions.insert(new VarInsnNode(ALOAD,0));
					helper.method.instructions.insert(new VarInsnNode(ALOAD,1));
				}
				break;
		}
		return false;
	}
	private static void doTransform(ClassNode profilerClass,boolean isObfuscated,Class<?> listener,int transformIndex) {
		FMLDeobfuscatingRemapper pain = FMLDeobfuscatingRemapper.INSTANCE;
		System.out.println("#Leaf: Processing "+profilerClass.name);
		System.out.println("       srcFile: "+profilerClass.sourceFile);
		System.out.println("       outClass: "+profilerClass.outerClass);
		System.out.println("       outMthd: "+profilerClass.outerMethod);
		System.out.println("       outMthdDesc: "+profilerClass.outerMethodDesc);
		System.out.println("       signature: "+profilerClass.signature);
		System.out.println("       access: "+profilerClass.access);
		List<String> attempt = new ArrayList<>();
		for (MethodNode method : profilerClass.methods) {
			attempt.clear();
			Helper helper = new Helper(method,listener,profilerClass);
			System.out.println("#Leaf: Iterating "+method.name+" : "+ method.desc);
			String deobf = pain.mapMethodName(profilerClass.name,method.name,method.desc);
			if (deobf != null)
				attempt.add(deobf);
			System.out.println("#      De: "+deobf);
			if (furtherDeobf.containsKey(deobf)) {
				System.out.println("#      MCP deobf: " + furtherDeobf.get(deobf));
				attempt.add(furtherDeobf.get(deobf));
			} else
				System.out.println("#      MCP deobf was not able");

			for (int i = attempt.size()-1; i >= 0; i--) {
				String s = attempt.get(i);
				if (tryBind(s,method.desc,helper,transformIndex)) {
					if (transformIndex == 0)
						helper.stackCall("updateLastStack","()V");
					for (int o = helper.instructions.size()-1; o >= 0; o--)
						method.instructions.insert(helper.instructions.get(o));
					System.out.println("#      Patched!");
					break;
				} else if (helper.instructions.size() > 0)
					throw new LeafiaDevErrorGls("tryBind returned false despite modifying stack!");
				/*
				switch(s) {
					case "enableBlend":
						method.instructions.insert(new VarInsnNode(SIPUSH,3));
						// for the (S)V part, see {@link Type}
						method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "enableBlend", "(S)V", false));
						break;
					default: exit = false; break;
				}
				if (exit) {
					System.out.println("#      Patched!");
					break;
				}*/
			}
			System.out.println("#");
			/*
			if (method.name.equals("disableLighting") || method.name.equals("func_179140_f")) {
				System.out.println("#Leaf: Patching");
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "disableLighting", "()V", false));
			}
			if(method.name.equals("enableLighting") || method.name.equals("func_179145_e")){
				System.out.println("#Leaf: Patching");
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "enableLighting", "()V", false));
			}
			if(method.name.equals("translate") || method.name.equals("func_179137_b")){
				System.out.println("#Leaf: Patching");
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "translate", "()V", false));
			}
			if(method.name.equals("enableBlend") || method.name.equals("func_179147_l")){
				System.out.println("#Leaf: Patching");
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "enableBlend", "()V", false));
			}
			if(method.name.equals("disableBlend") || method.name.equals("func_179084_k")){
				System.out.println("#Leaf: Patching");
				method.instructions.insert(new MethodInsnNode(INVOKESTATIC, Type.getInternalName(listener), "disableBlend", "()V", false));
			}*/
		}
	}
}
