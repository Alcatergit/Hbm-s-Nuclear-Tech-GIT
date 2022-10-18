package com.hbm.render.tileentity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderFluidTank extends TileEntitySpecialRenderer<TileEntityMachineFluidTank> {
	
	@Override
	public boolean isGlobalRenderer(TileEntityMachineFluidTank te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineFluidTank te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

        bindTexture(ResourceManager.tank_tex);
        ResourceManager.fluidtank.renderPart("Frame");

        GlStateManager.shadeModel(GL11.GL_FLAT);
        GL11.glPopMatrix();
        GlStateManager.enableCull();
        renderTileEntityAt2(te, x, y, z, partialTicks);
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        GlStateManager.enableAlpha();
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		}

		String s = "NONE";
		
		if(tileEntity instanceof TileEntityMachineFluidTank){
			if(((TileEntityMachineFluidTank)tileEntity).tank.getFluid() != null){
				s = FluidRegistry.getFluidName(((TileEntityMachineFluidTank)tileEntity).tank.getFluid()).toUpperCase();
				if(s.substring(0, 3).equals("HBM")){
					s = s.substring(3);
				}

			}
		}
		
		ResourceLocation rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_" + s + ".png");
		
		try {
			Minecraft.getMinecraft().getResourceManager().getResource(rotTexture);
		} catch (IOException e) {
			//Drillgon200: Set to my really ugly unknown texture
			rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_UNKNOWN.png");
		}

        bindTexture(rotTexture);
        ResourceManager.fluidtank.renderPart("Tank");

        GL11.glPopMatrix();
    }
}
