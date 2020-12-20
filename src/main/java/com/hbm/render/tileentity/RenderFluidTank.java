package com.hbm.render.tileentity;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.amlfrom1710.AdvancedModelLoader;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.tileentity.machine.TileEntityMachineFluidTank;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;

public class RenderFluidTank extends TileEntitySpecialRenderer<TileEntityMachineFluidTank> {

	private static final ResourceLocation body = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/fluidtank_main.obj");
	private static final ResourceLocation rotor = new ResourceLocation(/*"/assets/" + */RefStrings.MODID, "models/fluidtank_label.obj");
	private IModelCustom genModel;
	private IModelCustom rotModel;
    private ResourceLocation genTexture;
    private ResourceLocation rotTexture;
	
	public RenderFluidTank() {
		genModel = AdvancedModelLoader.loadModel(body);
		rotModel = AdvancedModelLoader.loadModel(rotor);
		//gadgetTexture = new ResourceLocation(RefStrings.MODID, "textures/models/TheGadget3_.png");
		genTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank.png");
		rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_none.png");
	}
	
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
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(te.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		}

        bindTexture(genTexture);
 
        genModel.renderAll();

        GL11.glPopMatrix();
        GlStateManager.enableCull();
        GL11.glEnable(GL11.GL_CULL_FACE);
        renderTileEntityAt2(te, x, y, z, partialTicks);
	}
	
	public void renderTileEntityAt2(TileEntity tileEntity, double x, double y, double z, float f)
    {
        GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5D, y, z + 0.5D);
        GlStateManager.enableLighting();
        GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glRotatef(180, 0F, 1F, 0F);
		GL11.glRotatef(90, 0F, 1F, 0F);
		switch(tileEntity.getBlockMetadata())
		{
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
	        //GL11.glTranslated(0.5D, 0.0D, 0.0D);
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
		
		rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_" + s + ".png");
		
		try {
			Minecraft.getMinecraft().getResourceManager().getResource(rotTexture);
		} catch (IOException e) {
			//Drillgon200: Set to my really ugly unknown texture
		 rotTexture = new ResourceLocation(RefStrings.MODID, "textures/models/tank_UNKNOWN.png");
		}

        bindTexture(rotTexture);
        rotModel.renderAll();

        GL11.glPopMatrix();
    }
}
