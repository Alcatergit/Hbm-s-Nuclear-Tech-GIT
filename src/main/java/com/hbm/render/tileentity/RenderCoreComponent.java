package com.hbm.render.tileentity;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;
import com.leafia.contents.machines.powercores.dfc.DFCBaseTE;
import com.leafia.contents.machines.powercores.dfc.creativeemitter.TileEntityCoreCreativeEmitter;
import com.leafia.contents.machines.powercores.dfc.exchanger.DFCExchangerTE;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.lwjgl.opengl.GL11;

public class RenderCoreComponent extends TileEntitySpecialRenderer<TileEntityMachineBase> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineBase te) {
		return true;
	}

	boolean isFace(EnumFacing face, Vec3d direction) {
		double component = 0;
		if (face.getAxis() == Axis.X) component = direction.x;
		else if (face.getAxis() == Axis.Y) component = direction.y;
		else if (face.getAxis() == Axis.Z) component = direction.z;
		if (face.getAxisDirection() == AxisDirection.NEGATIVE) component *= -1;
		return component > 0.15;
	}

	static final ResourceLocation dfc_cemitter_tex = new ResourceLocation(RefStrings.MODID, "textures/models/machines/core_cemitter.png");
	static final ResourceLocation dfc_exchanger_tex = new ResourceLocation(RefStrings.MODID, "textures/models/leafia/core_exchanger.png");
	static final IModelCustom dfc_exchanger_mdl = AdvancedModelLoader.loadModel(new ResourceLocation(RefStrings.MODID,"models/leafia/dfc_rotatable/exchanger.obj"));

	@Override
	public void render(TileEntityMachineBase tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		if (!(tileEntity instanceof DFCBaseTE)) return;
		IModelCustom mdl;
		if (tileEntity instanceof TileEntityCoreCreativeEmitter) {
			bindTexture(dfc_cemitter_tex);
			mdl = ResourceManager.dfc_emitter;
		} else if (tileEntity instanceof TileEntityCoreEmitter) {
			bindTexture(ResourceManager.dfc_emitter_tex);
			mdl = ResourceManager.dfc_emitter;
		} else if (tileEntity instanceof TileEntityCoreReceiver) {
			bindTexture(ResourceManager.dfc_receiver_tex);
			if (getWorld().getBlockState(tileEntity.getPos()).getBlock() == ModBlocks.dfc_reinforced)
				mdl = ResourceManager.dfc_reinforced;
			else
				mdl = ResourceManager.dfc_receiver;
		} else if (tileEntity instanceof TileEntityCoreInjector) {
			bindTexture(ResourceManager.dfc_injector_tex);
			mdl = ResourceManager.dfc_injector;
		} else if (tileEntity instanceof TileEntityCoreStabilizer) {
			bindTexture(ResourceManager.dfc_stabilizer_tex);
			mdl = ResourceManager.dfc_stabilizer;
		} else if (tileEntity instanceof DFCExchangerTE) {
			bindTexture(dfc_exchanger_tex);
			mdl = dfc_exchanger_mdl;
		} else return;
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 0.5, z + 0.5);
		GL11.glPushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.disableCull();

		//GL11.glRotatef(90, 0F, 1F, 0F); What is this for bruh
		DFCBaseTE te = (DFCBaseTE) tileEntity;
		Vec3i relative = te.getTargetPosition().subtract(te.getPos());
		Vec3d unit = new Vec3d(relative).normalize();
		double yaw = Math.toDegrees(Math.atan2(-relative.getX(), -relative.getZ()));
		double pitch = Math.toDegrees(Math.atan2(relative.getY(), Math.sqrt(relative.getX() * relative.getX() + relative.getZ() * relative.getZ())));
		GL11.glRotated(yaw, 0, 1, 0);
		GL11.glRotated(pitch, 1, 0, 0);
		mdl.renderPart("Core");
		if (mdl == ResourceManager.dfc_reinforced) {
			LeafiaGls.pushMatrix();
			assert te instanceof TileEntityCoreReceiver;
			LeafiaGls.rotate(-((TileEntityCoreReceiver)te).fanAngle-partialTicks*720,0,0,1);
			mdl.renderPart("Fan");
			LeafiaGls.popMatrix();
		}
        /*
		switch(tileEntity.getBlockMetadata()) {
		case 0:
	        GL11.glTranslated(0.0D, 0.5D, -0.5D);
			GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 1:
	        GL11.glTranslated(0.0D, 0.5D, 0.5D);
			GL11.glRotatef(90, -1F, 0F, 0F); break;
		case 2:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(-90, 0F, 1F, 0F); break;
		}*/

		//GL11.glTranslated(0.0D, 0D, 0.0D);

		//GL11.glTranslated(0, 0.5, 0);

		double range = 0;
		if (te.lastGetCore != null)
			range = new Vec3d(te.getPos()).add(0.5, 0.5, 0.5).distanceTo(new Vec3d(te.lastGetCore.getPos()).add(0.5, 0.5, 0.5));
		if (tileEntity instanceof TileEntityCoreStabilizer) {
			TileEntityCoreStabilizer stabilizer = (TileEntityCoreStabilizer) tileEntity;
			int outerColor = stabilizer.lens.outerColor;
			int innerColor = stabilizer.lens.innerColor;
			if (stabilizer.cl_hasLens) {
				LeafiaGls.enableBlend();
				IBakedModel baked = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(new ItemStack(stabilizer.lens.item), getWorld(), null);
				bindByIconName(baked.getParticleTexture().getIconName());
				LeafiaGls.blendFunc(SourceFactor.ONE, DestFactor.SRC_COLOR);
				mdl.renderPart("lens");
				bindTexture(ResourceManager.dfc_stabilizer_tex);
				LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
				LeafiaGls.disableBlend();
				if (range > 0 && stabilizer.isOn) {
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, outerColor, innerColor, 0, 1, 0F, 2, 0.125F);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, outerColor, innerColor, (int) tileEntity.getWorld().getTotalWorldTime() * -8 % 360, (int) Math.round(range * 3), 0.125F, 2, 0.04F);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, outerColor, innerColor, (int) tileEntity.getWorld().getTotalWorldTime() * -8 % 360 + 180, (int) Math.round(range * 3), 0.125F, 2, 0.04F);
				}
			}
			LeafiaGls.enableBlend();
			LeafiaGls.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
			GlStateManager.enableLighting();
			GL11.glAlphaFunc(GL11.GL_ALWAYS, 0);
			mdl.renderPart("Glass");
			GL11.glAlphaFunc(GL11.GL_GREATER, 0);
			LeafiaGls.disableBlend();
		}

		if (tileEntity instanceof TileEntityCoreEmitter) {
			//int range = ((TileEntityCoreEmitter)tileEntity).beam;
			RayTraceResult result = ((TileEntityCoreEmitter) tileEntity).lastRaycast;
			if (result != null) {
				range = new Vec3d(te.getPos()).add(0.5, 0.5, 0.5).distanceTo(result.hitVec);
				if (((TileEntityCoreEmitter) tileEntity).isActive) {
					float width = (float) Math.max(1, Math.log10(((TileEntityCoreEmitter) tileEntity).prev) - 6) / 8F;
					int colorA = 0x401500;
					int colorB = 0x5B1D00;
					if (tileEntity instanceof TileEntityCoreCreativeEmitter) {
						colorA = 0x281332;
						colorB = 0x110165;
					}
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, colorA, 0x7F7F7F, 0, 1, 0F, 2, width);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.RANDOM, EnumBeamType.SOLID, colorA, 0x7F7F7F, (int) tileEntity.getWorld().getTotalWorldTime() % 1000, (int) (0.3F * range / width), width * 0.75F, 2, width * 0.5F);
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.RANDOM, EnumBeamType.SOLID, colorB, 0x7F7F7F, (int) tileEntity.getWorld().getTotalWorldTime() % 1000 + 1, (int) (0.3F * range / width), width * 0.75F, 2, width * 0.5F);
				}
			}
		}

		if (tileEntity instanceof TileEntityCoreInjector) {
			TileEntityCoreInjector injector = (TileEntityCoreInjector) tileEntity;
			//int range = injector.beam;

			if (range > 0) {
				RenderHelper.bindBlockTexture();
				if (injector.tanks[0].getFluidAmount() > 0)
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, ModForgeFluids.getFluidColor(injector.tanks[0].getFluid().getFluid()), 0x7F7F7F, (int) tileEntity.getWorld().getTotalWorldTime() * -2 % 360, (int) Math.round(range), 0.09F, 3, 0.0625F);
				if (injector.tanks[1].getFluidAmount() > 0)
					BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, ModForgeFluids.getFluidColor(injector.tanks[1].getFluid().getFluid()), 0x7F7F7F, (int) tileEntity.getWorld().getTotalWorldTime() * -2 % 360 + 180, (int) Math.round(range), 0.09F, 3, 0.0625F);
			}
			bindTexture(ResourceManager.dfc_injector_tex);
		}

		if (tileEntity instanceof TileEntityCoreReceiver) {
			TileEntityCoreReceiver absorber = (TileEntityCoreReceiver) tileEntity;
			if (absorber.core != null) {
				double mspk = absorber.core.expellingSpk * 20 / absorber.core.absorbers.size() * absorber.level;// /10;
				mspk *= (getWorld().rand.nextDouble() * 99 + 1); // What the fuck why is it not
				mspk = Math.min(100000, mspk);
				int distance = (int) Math.round(Math.sqrt(absorber.getPos().distanceSq(absorber.core.getPos())));
				GL11.glTranslated(0, 0, -distance);
				if (mspk > 0) {
					for (int i = 0; i < (int) Math.pow(mspk / 200, 0.5) + 1; i++) {
						BeamPronter.prontBeam(
								Vec3.createVectorHelper(0, 0, distance - 0.5),
								EnumWaveType.RANDOM,
								EnumBeamType.SOLID,
								0x5B1D00, 0x7F7F7F,
								(int) Math.floorMod(absorber.getWorld().getTotalWorldTime() * 3 + (int) (partialTicks / 7) + i + 33, 1500),
								distance * (i + 1),
								0.2F + (float) (Math.pow(mspk / 1000, 0.25) - 1) * 0.025F,
								3,
								0.1F + 0.0666F*(float)(Math.pow(mspk / 1000, 0.25) - 1)
						);
					}
				}
			}
		}
		if (tileEntity instanceof DFCExchangerTE) {
			DFCExchangerTE exchanger = (DFCExchangerTE) tileEntity;
			//int range = injector.beam;

			if (range > 0) {
				//0xffa200
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x9b4100, 0x9b4100, (int)tileEntity.getWorld().getTotalWorldTime() * -25 % 360, (int)(range * 3), 0.125F/1.5f, 1, 0.01f);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x9b4100, 0x9b4100, (int)tileEntity.getWorld().getTotalWorldTime() * -15 % 360 + 180, (int)(range * 3), 0.125F/1.5f, 1, 0.01f);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x9b4100, 0x9b4100, (int)tileEntity.getWorld().getTotalWorldTime() * -5 % 360 + 180, (int)(range * 3), 0.125F/1.5f, 1, 0.01f);
				BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, -range), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0xffd000, 0xffd000, 0, 1, 0, 1, 0.01f);
			}
			bindTexture(dfc_exchanger_tex);
		}

		GlStateManager.enableLighting();
		GL11.glPopMatrix();
		double maxAbs = Math.max(Math.abs(unit.x), Math.max(Math.abs(unit.y), Math.abs(unit.z)));
		for (EnumFacing face : EnumFacing.values()) {
			GL11.glPushMatrix();
			if (face == EnumFacing.UP)
				GL11.glRotatef(90, 1, 0, 0);
			else if (face == EnumFacing.DOWN)
				GL11.glRotatef(-90, 1, 0, 0);
			else
				GL11.glRotatef(90 * (2 - face.getHorizontalIndex()), 0, 1, 0);
			if (!isFace(face.getOpposite(), unit))
				mdl.renderPart("Frame");

			boolean isFront = false;
			double expected = maxAbs * face.getAxisDirection().getOffset();
			if (face.getAxis() == Axis.X)
				isFront = unit.x == expected;
			else if (face.getAxis() == Axis.Y)
				isFront = unit.y == expected;
			else if (face.getAxis() == Axis.Z)
				isFront = unit.z == expected;
			if (isFront) {
				for (int i = 0; i < 4; i++) {
					if (face == EnumFacing.UP) {
						if (!isFace(EnumFacing.byHorizontalIndex(Math.floorMod(-i, 4)), unit))
							mdl.renderPart("Arm");
					} else if (face == EnumFacing.DOWN) {
						if (!isFace(EnumFacing.byHorizontalIndex(Math.floorMod(2 - i, 4)), unit))
							mdl.renderPart("Arm");
					} else {
						EnumFacing check;
						if (i == 0) check = EnumFacing.UP;
						else if (i == 1) check = face.rotateY().getOpposite();
						else if (i == 2) check = EnumFacing.DOWN;
						else check = face.rotateY();
						if (!isFace(check, unit))
							mdl.renderPart("Arm");
					}
					GL11.glRotatef(90, 0, 0, 1);
				}
			}
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}

	void bindByIconName(String resource) { // copied from RenderPWRMeshedWreck lmao
		// convert format like "hbm:         blocks/brick_concrete    "
		//                  to "hbm:textures/blocks/brick_concrete.png"
		bindTexture(new ResourceLocation(resource.replaceFirst("(\\w+:)?(.*)", "$1textures/$2.png")));
	}
}
