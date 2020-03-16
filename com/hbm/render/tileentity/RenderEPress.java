package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineEPress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderEPress extends TileEntitySpecialRenderer<TileEntityMachineEPress> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineEPress te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineEPress te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GlStateManager.enableLighting();
		GL11.glRotatef(180, 0F, 1F, 0F);
		
		switch(te.getBlockMetadata()) {
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}
		
		this.bindTexture(ResourceManager.epress_body_tex);
		
		ResourceManager.epress_body.renderAll();
			
	GL11.glPopMatrix();
	
    renderTileEntityAt2(te, x, y, z, partialTicks);
	}
	
	public void renderTileEntityAt2(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1 + 1 - 0.125, z + 0.5D);
			GlStateManager.enableLighting();
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			switch(tileentity.getBlockMetadata()) {
			case 2:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			}

			TileEntityMachineEPress press = (TileEntityMachineEPress)tileentity;
			float f1 = press.progress * (1 - 0.125F) / TileEntityMachineEPress.maxProgress;
			GL11.glTranslated(0, -f1, 0);
		
			this.bindTexture(ResourceManager.epress_head_tex);
		
			ResourceManager.epress_head.renderAll();
			
		GL11.glPopMatrix();
		
        renderTileEntityAt3(tileentity, x, y, z, f);
    }
    
	public void renderTileEntityAt3(TileEntity tileentity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1, z + 0.5);
			GlStateManager.enableLighting();
			GL11.glRotatef(180, 0F, 1F, 0F);
			
			switch(tileentity.getBlockMetadata()) {
			case 2:
				GL11.glRotatef(270, 0F, 1F, 0F); break;
			case 4:
				GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 3:
				GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 5:
				GL11.glRotatef(180, 0F, 1F, 0F); break;
			}

			GL11.glRotatef(90, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			GL11.glTranslatef(1.0F, 1.0F - 0.0625F * 165/100, 0.0F);
			GL11.glTranslatef(-1, -1.15F, 0);
			
			TileEntityMachineEPress press = (TileEntityMachineEPress)tileentity;
			ItemStack stack = new ItemStack(Item.getItemById(press.item), 1, press.meta);
			
			if(!(stack.getItem() instanceof ItemBlock)) {
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, tileentity.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GL11.glTranslatef(0.0F, 0.125F, 0.0F);
				GL11.glRotatef(180, 0F, 1F, 0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			}
			
		GL11.glPopMatrix();
    }
}
