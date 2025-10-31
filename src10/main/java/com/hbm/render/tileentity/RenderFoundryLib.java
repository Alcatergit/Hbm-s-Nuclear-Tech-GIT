package com.hbm.render.tileentity;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.tileentity.machine.IRenderFoundry;
import com.hbm.tileentity.machine.TileEntityFoundryCastingBase;
import com.hbm.render.amlfrom1710.Tessellator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.items.ItemStackHandler;

public class RenderFoundryLib {
	
	public static final ResourceLocation lava = new ResourceLocation(RefStrings.MODID, "textures/models/machines/lava_gray.png");

	private static void drawItem(ItemStack stack, double height, World world) {
		GL11.glPushMatrix();
		GL11.glTranslated(0.5D, height, 0.5D);

		if(!(stack.getItem() instanceof ItemBlock)) {
			GL11.glRotatef(-180, 0F, 1F, 0F);
			GL11.glScaled(0.5F, 0.5F, 0.5F);
		} else {
			GL11.glTranslated(0, -0.352, 0);
		}
		
		double scale = 24D / 16D;
		GL11.glScaled(scale, scale, scale);
		
		GL11.glRotated(90, 1, 0, 0);
		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, world, null);
		model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);
		Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
		GL11.glEnable(GL11.GL_ALPHA_TEST);

		GL11.glPopMatrix();
	}

	public static void renderFoundry(TileEntity te, double x, double y, double z, float interp) {
		IRenderFoundry foundry = (IRenderFoundry) te;
		
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, z);
		
		if(te instanceof TileEntityFoundryCastingBase) {
			ItemStackHandler inv = ((TileEntityFoundryCastingBase) te).inventory;
			
			ItemStack mold = inv.getStackInSlot(0);
			if(mold != null && !mold.isEmpty()) {
				drawItem(mold, foundry.moldHeight(), te.getWorld());
			}
			
			ItemStack out = inv.getStackInSlot(1);
			if(out != null && !out.isEmpty()) {
				drawItem(out, foundry.outHeight(), te.getWorld());
			}
		}

		if(foundry.shouldRender()) {
			
			int hex = foundry.getMat().moltenColor;
			Color color = new Color(hex);
	
			GL11.glPushMatrix();
			GL11.glDisable(GL11.GL_CULL_FACE);
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
			Minecraft.getMinecraft().getTextureManager().bindTexture(lava);
			
			Tessellator tess = new Tessellator();
        	tess.setNormal(0F, 1F, 0F);
        	tess.setBrightness(240);
			tess.startDrawingQuadsColor();
			tess.setColorRGBA_F(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, 1F);
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.minX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.maxX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.maxZ(), foundry.maxZ(), foundry.minX());
			tess.addVertexWithUV(foundry.maxX(), foundry.getLevel(), foundry.minZ(), foundry.minZ(), foundry.minX());
			tess.draw();

			GL11.glEnable(GL11.GL_CULL_FACE);
			GL11.glPopMatrix();
		}
		
		GL11.glPopMatrix();
	}
}
