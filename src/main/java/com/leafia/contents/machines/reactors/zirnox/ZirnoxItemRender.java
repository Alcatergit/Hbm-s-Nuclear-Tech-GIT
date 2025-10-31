package com.leafia.contents.machines.reactors.zirnox;

import com.hbm.main.ResourceManager;
import com.hbm.render.item.ItemRenderBase;
import com.leafia.dev.items.LeafiaGripOffsetHelper;
import com.leafia.transformer.LeafiaGls;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import org.lwjgl.opengl.GL11;

public class ZirnoxItemRender extends ItemRenderBase {
	static LeafiaGripOffsetHelper offsets = new LeafiaGripOffsetHelper()
			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(0,10,-10.25).setRotation(25,0,-95).getHelper()
			.get(TransformType.FIRST_PERSON_LEFT_HAND)
			.setPosition(-4.5,0,9.75).setRotation(0,-60,0).getHelper()
			.get(TransformType.GUI)
			.setScale(2.2).setPosition(-2.55,-0.85,0).setRotation(65,0,-25).getHelper()
			.get(TransformType.GROUND)
			.setScale(0.25).setPosition(5.25,3.25,-5.00).getHelper()
			.get(TransformType.FIXED)
			.setScale(0.4).setPosition(0.75,2.75,-3.15).setRotation(0,0,-90).getHelper()
			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
			.setScale(0.25).setPosition(5,1.5,-3.75).getHelper();
	@Override
	public void renderCommon() {
		LeafiaGls.pushMatrix();
		offsets.apply(type);
		Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.zirnox_tex);
		LeafiaGls.shadeModel(GL11.GL_SMOOTH);
		ResourceManager.zirnox.renderAll();
		LeafiaGls.shadeModel(GL11.GL_FLAT);
		LeafiaGls.popMatrix();
	}
}
