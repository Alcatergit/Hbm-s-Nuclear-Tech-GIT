package com.leafia.contents.resources.bedrockore;

import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Grade;
import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Overlay;
import com.leafia.contents.resources.bedrockore.BedrockOreV2Item.V2Type;
import com.leafia.dev.LeafiaDebug;
import com.leafia.transformer.LeafiaGls;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.math.LeafiaColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

public class BedrockOreV2Render extends TileEntityItemStackRenderer {
	public static final BedrockOreV2Render INSTANCE = new BedrockOreV2Render();
	public TransformType type;
	public final Map<V2Type,IBakedModel[]> itemModels = new HashMap<>();
	public final IBakedModel[] overlays = new IBakedModel[V2Overlay.values().length];
	public BedrockOreV2Render() {
		for (V2Type type : V2Type.values())
			itemModels.put(type,new IBakedModel[V2Grade.values().length]);
	}
	final double HALF_A_PIXEL = 0.03125;
	@Override
	public void renderByItem(ItemStack stack) {
		LeafiaGls.pushMatrix();
		if (stack.getItem() instanceof BedrockOreV2Item ore) {
			if (stack.getMetadata() >= 0 && stack.getMetadata() < V2Grade.values().length) {
				IBakedModel mdl = itemModels.get(ore.type)[stack.getMetadata()];
				if (mdl == null)
					LeafiaDebug.debugLog(Minecraft.getMinecraft().world,new TextWarningLeafia("Cannot find model for "+ore.getRegistryName()+"!").toString());
				else {
					GL11.glTranslated(0.5, 0.5, -HALF_A_PIXEL);
					V2Grade grade = V2Grade.values()[stack.getMetadata()];
					LeafiaColor tint = new LeafiaColor(grade.tint);
					LeafiaColor color = new LeafiaColor(ore.type.light);
					//LeafiaColor dark = new LeafiaColor(ore.type.dark);
					//LeafiaColor color = new LeafiaColor(light.red/2+dark.red/2,light.green/2+dark.green/2,light.blue/2+dark.blue/2);
					color = color.multiply(tint.red,tint.green,tint.blue);
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(-0.5F, -0.5F, 0);
					Minecraft.getMinecraft().getRenderItem().renderModel(mdl,color.toARGB());
					LeafiaGls.popMatrix();
					LeafiaGls.color(1,1,1);
					LeafiaGls.translate(0,0,0.5);
					for (V2Overlay overlay : grade.traits) {
						IBakedModel overlayMdl = overlays[overlay.ordinal()];
						if (overlayMdl == null)
							LeafiaDebug.debugLog(Minecraft.getMinecraft().world,new TextWarningLeafia("Cannot find model for overlay "+overlay.name()+"!").toString());
						else
							Minecraft.getMinecraft().getRenderItem().renderItem(stack,overlayMdl);
					}
				}
			}
		}
		LeafiaGls.popMatrix();
	}
}
