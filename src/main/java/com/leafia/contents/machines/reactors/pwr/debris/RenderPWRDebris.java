package com.leafia.contents.machines.reactors.pwr.debris;

import com.hbm.hfr.render.loader.HFRWavefrontObject;
import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.leafia.contents.machines.reactors.pwr.debris.PWRDebrisEntity.DebrisType;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class RenderPWRDebris extends Render<PWRDebrisEntity> {

	public static final IRenderFactory<PWRDebrisEntity> FACTORY = man -> new RenderPWRDebris(man);

	//for fallback only
	private static class Meshes {
		static ResourceLocation res(String s) {
			return new ResourceLocation(RefStrings.MODID, s);
		}
		static final ResourceLocation channelTop = res("textures/blocks/pwr/pwr_channel_top.png");
		static final ResourceLocation channelSide = res("textures/blocks/pwr/pwr_channel_side.png");
		static final HFRWavefrontObject channel3x = new HFRWavefrontObject(res("models/leafia/pwrdebris/channel3x.obj"));
		static final HFRWavefrontObject channel1x = new HFRWavefrontObject(res("models/leafia/pwrdebris/channel1x.obj"));

		static final ResourceLocation controlTop = res("textures/blocks/pwr/pwr_control_top.png");
		static final ResourceLocation controlSide = res("textures/blocks/pwr/pwr_control_side.png");
		static final HFRWavefrontObject control_frame = new HFRWavefrontObject(res("models/leafia/pwrdebris/control_frame.obj"));
		static final HFRWavefrontObject control_rod = new HFRWavefrontObject(res("models/leafia/pwrdebris/control_rod.obj"));

		static final HFRWavefrontObject concrete = new HFRWavefrontObject(res("models/leafia/pwrdebris/concrete_unwrap.obj"));
		static final HFRWavefrontObject blank = new HFRWavefrontObject(res("models/leafia/pwrdebris/blank_unwrap.obj"));
		static final HFRWavefrontObject shrap = new HFRWavefrontObject(res("models/leafia/pwrdebris/shrapnel.obj"));
	}

	protected RenderPWRDebris(RenderManager renderManager){
		super(renderManager);
	}

	public void drawModel(DebrisType type,String block_rsc,int meta) {
		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(block_rsc));
		IBlockState display = block.getStateFromMeta(meta);
		IBakedModel baked = Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(display);
		switch(type) {
			case BLANK: drawAll(Meshes.blank,display,baked); break;
			case CONCRETE: drawAll(Meshes.concrete,display,baked); break;
			case GRAPHITE: drawAll(ResourceManager.deb_graphite,display,baked); break;
			case SHRAPNEL: drawAll(Meshes.shrap,display,baked); break;
			case CHANNEL_1X: draw(Meshes.channel1x,display,baked); break;
			case CHANNEL_3X: draw(Meshes.channel3x,display,baked); break;
			case CONTROL_FRAME: draw(Meshes.control_frame,display,baked); break;
			case CONTROL_ROD: draw(Meshes.control_rod,display,baked); break;
		}
	}

	@Override
	public void doRender(PWRDebrisEntity entity,double x,double y,double z,float entityYaw,float partialTicks){
		GL11.glPushMatrix();
		GL11.glTranslated(x, y + 0.125D, z);

		PWRDebrisEntity debris = (PWRDebrisEntity)entity;

		GL11.glRotatef(debris.getEntityId() % 360, 0, 1, 0); //rotate based on entity ID to add unique randomness
		GL11.glRotatef(debris.lastRot + (debris.rot - debris.lastRot) * partialTicks, 1, 1, 1);

		drawModel(debris.getType(),entity.getDataManager().get(PWRDebrisEntity.BLOCK_RSC),entity.getDataManager().get(PWRDebrisEntity.BLOCK_META));

		GL11.glPopMatrix();
	}
	void draw(IModelCustom mesh,IBlockState display,IBakedModel baked) {
		bindByIconName(baked.getParticleTexture().getIconName());
		mesh.renderPart("SIDE");
		tryBindQuads(baked,display,EnumFacing.UP);
		mesh.renderPart("TOP");
	}
	void drawAll(IModelCustom mesh,IBlockState display,IBakedModel baked) {
		bindByIconName(baked.getParticleTexture().getIconName());
		mesh.renderAll();
	}
	void tryBindQuads(IBakedModel baked,IBlockState display,EnumFacing face) {
		List<BakedQuad> quads = baked.getQuads(display,face,0);
		if (quads.size() > 0)
			bindByIconName(quads.get(0).getSprite().getIconName());
		else
			bindByIconName(baked.getParticleTexture().getIconName());
	}
	void bindByIconName(String resource) {
		// convert format like "hbm:         blocks/brick_concrete    "
		//                  to "hbm:textures/blocks/brick_concrete.png"
		bindTexture(new ResourceLocation(resource.replaceFirst("(\\w+:)?(.*)","$1textures/$2.png")));
	}
	@Override
	protected ResourceLocation getEntityTexture(PWRDebrisEntity entity){
		return Meshes.channelTop;
	}
}
