package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartSize;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePronter;
import com.hbm.tileentity.bomb.TileEntityLaunchTable;

import net.minecraft.block.BlockHorizontal;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;

public class RenderLaunchTable extends TileEntitySpecialRenderer<TileEntityLaunchTable> {

	@Override
	public boolean isGlobalRenderer(TileEntityLaunchTable te) {
		return true;
	}
	
	@Override
	public void render(TileEntityLaunchTable launcher, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
		
		GL11.glTranslatef((float) x + 0.5F, (float) y, (float) z + 0.5F);
		GlStateManager.enableCull();
		
		switch(launcher.getWorld().getBlockState(launcher.getPos()).getValue(BlockHorizontal.FACING))
		{
		case NORTH:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case SOUTH:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case EAST:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case WEST:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		default:
			break;
		}
		
		bindTexture(ResourceManager.launch_table_base_tex);
		ResourceManager.launch_table_base.renderAll();

		if(launcher.padSize == PartSize.SIZE_10 || launcher.padSize == PartSize.SIZE_15) {
			bindTexture(ResourceManager.launch_table_small_pad_tex);
			ResourceManager.launch_table_small_pad.renderAll();
		}
		if(launcher.padSize == PartSize.SIZE_20) {
			bindTexture(ResourceManager.launch_table_large_pad_tex);
			ResourceManager.launch_table_large_pad.renderAll();
		}

		GL11.glPushMatrix();
		
		if(launcher.load != null) {
			MissileMultipart mp = MissileMultipart.loadFromStruct(launcher.load);
			
			if(mp != null && mp.fuselage != null)
				launcher.height = (int) mp.getHeight();
		}
		
		int height = (int) (launcher.height * 0.75);
		ResourceLocation base = ResourceManager.launch_table_large_scaffold_base_tex;
		ResourceLocation connector = ResourceManager.launch_table_large_scaffold_connector_tex;
		IModelCustom baseM = ResourceManager.launch_table_large_scaffold_base;
		IModelCustom connectorM = ResourceManager.launch_table_large_scaffold_connector;
		IModelCustom emptyM = ResourceManager.launch_table_large_scaffold_empty;


		if(launcher.padSize == PartSize.SIZE_10) {
			base = ResourceManager.launch_table_small_scaffold_base_tex;
			connector = ResourceManager.launch_table_small_scaffold_connector_tex;
			baseM = ResourceManager.launch_table_small_scaffold_base;
			connectorM = ResourceManager.launch_table_small_scaffold_connector;
			emptyM = ResourceManager.launch_table_small_scaffold_empty;
			GL11.glTranslatef(0F, 0F, -1F);
		}
		GL11.glTranslatef(0F, 1F, 3.5F);
		for(int i = 0; i < launcher.height + 1; i++) {

			if(i < height) {
				bindTexture(base);
				baseM.renderAll();
			} else if(i > height) {
				bindTexture(base);
				emptyM.renderAll();
			} else {
				
				if(launcher.load != null && launcher.load.fuselage != null && ((ItemMissile)launcher.load.fuselage).top == launcher.padSize) {
					bindTexture(connector);
					connectorM.renderAll();
				} else {
					bindTexture(base);
					baseM.renderAll();
				}
			}
			
			GL11.glTranslatef(0F, 1F, 0F);
		}
		GL11.glPopMatrix();

		GL11.glTranslatef(0F, 2.0625F, 0F);
		
		/// DRAW MISSILE START
		GL11.glPushMatrix();
		
		if(launcher.load != null && launcher.load.fuselage != null && launcher.load.fuselage.top == launcher.padSize && launcher.clearingTimer == 0)
			MissilePronter.prontMissile(MissileMultipart.loadFromStruct(launcher.load), Minecraft.getMinecraft().getTextureManager());
		
		GL11.glPopMatrix();
		/// DRAW MISSILE END

		GL11.glPopMatrix();
	}
}
