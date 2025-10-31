package com.leafia.contents.resources;

import com.hbm.inventory.material.Mats;
import com.hbm.inventory.material.NTMMaterial;
import com.leafia.contents.resources.bedrockore.BedrockOreV2Item;
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

public class ItemMaterialsAutogenTintRender extends TileEntityItemStackRenderer {
	public static final ItemMaterialsAutogenTintRender INSTANCE = new ItemMaterialsAutogenTintRender();
	public TransformType type;
	public final Map<ItemMaterialsAutogenTint,Map<Integer,IBakedModel>> itemModels = new HashMap<>();
	public ItemMaterialsAutogenTintRender() {
		for (ItemMaterialsAutogenTint item : ItemMaterialsAutogenTint.ALL_AUTOGEN)
			itemModels.put(item,new HashMap<>());
	}
	final double HALF_A_PIXEL = 0.03125;
	@Override
	public void renderByItem(ItemStack stack) {
		LeafiaGls.pushMatrix();
		if (stack.getItem() instanceof ItemMaterialsAutogenTint item) {
			Map<Integer,IBakedModel> map = itemModels.get(item);
			int meta = stack.getMetadata();
			NTMMaterial mat = Mats.matById.get(meta);
			if (map != null && map.containsKey(meta) && mat != null) {
				IBakedModel mdl = map.get(meta);
				if (mdl == null)
					LeafiaDebug.debugLog(Minecraft.getMinecraft().world,new TextWarningLeafia("Cannot find model for Mat "+mat.id+"!").toString());
				else {
					GL11.glTranslated(0.5, 0.5, -HALF_A_PIXEL);
					LeafiaColor tint = new LeafiaColor(mat.solidColorLight);
					LeafiaGls.pushMatrix();
					LeafiaGls.translate(-0.5F, -0.5F, 0);
					Minecraft.getMinecraft().getRenderItem().renderModel(mdl,tint.toARGB());
					LeafiaGls.popMatrix();
					LeafiaGls.color(1,1,1);
					LeafiaGls.translate(0,0,0.5);
				}
			}
		}
		LeafiaGls.popMatrix();
	}
}
