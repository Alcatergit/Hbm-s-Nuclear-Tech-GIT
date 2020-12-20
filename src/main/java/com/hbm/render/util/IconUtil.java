package com.hbm.render.util;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class IconUtil {

	public static TextureAtlasSprite getTextureFromBlock(Block b) {
		return Minecraft.getMinecraft().getBlockRendererDispatcher().getModelForState(b.getDefaultState()).getParticleTexture();
	}

	//Drillgon200: Pretty sure I can't do this in 1.12.2, particle textures will have to do.
	/*public static ResourceLocation getTextureFromBlockAndSide(Block b, int side) {


		RenderBlocks rb = RenderBlocks.getInstance();

        IIcon icon = rb.getBlockIconFromSide(b, side);
		ResourceLocation loc = new ResourceLocation(RefStrings.MODID + ":textures/blocks/" + icon.getIconName().substring(4, icon.getIconName().length()) + ".png");

		return loc;
	}*/
}
