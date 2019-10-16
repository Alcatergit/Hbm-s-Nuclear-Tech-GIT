package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderPress extends TileEntitySpecialRenderer<TileEntityMachinePress> {
	
	
	public RenderPress() {
		super();
	}
	@Override
	public void render(TileEntityMachinePress te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5D, y, z + 0.5D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glRotatef(180, 0F, 1F, 0F);
		this.bindTexture(ResourceManager.press_body_tex);
		
		ResourceManager.press_body.renderAll();
			
	GL11.glPopMatrix();
		renderTileEntityAt2(te, x, y, z, partialTicks);
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1 - 0.125D, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glScalef(0.95F, 1, 0.95F);

			TileEntityMachinePress press = (TileEntityMachinePress)tileEntity;
			float f1 = press.progress * (1 - 0.125F) / TileEntityMachinePress.maxProgress;
			GL11.glTranslated(0, -f1, 0);
		
			this.bindTexture(ResourceManager.press_head_tex);
		
			ResourceManager.press_head.renderAll();
			
		GL11.glPopMatrix();
		
        renderTileEntityAt3(tileEntity, x, y, z, f);
    }
    
	public void renderTileEntityAt3(TileEntity tileEntity, double x, double y, double z, float f) {
		GL11.glPushMatrix();
			GL11.glTranslated(x + 0.5D, y + 1, z - 0.5);
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glRotatef(180, 0F, 1F, 0F);
			GL11.glRotatef(-90, 1F, 0F, 0F);
			
			TileEntityMachinePress press = (TileEntityMachinePress)tileEntity;
			ItemStack stack = new ItemStack(Item.getItemById(press.item), 1, press.meta);
			
			if(!(stack.getItem() instanceof ItemBlock) && !stack.isEmpty()) {
				IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(stack, tileEntity.getWorld(), null);
				model = ForgeHooksClient.handleCameraTransforms(model, TransformType.FIXED, false);
				Minecraft.getMinecraft().getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
				GL11.glTranslatef(0.0F, 1.0F, 0.0F);
				GL11.glRotatef(180, 0F, 1F, 0F);
				GL11.glScalef(0.5F, 0.5F, 0.5F);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, model);
			}
			
		GL11.glPopMatrix();
    }

}
