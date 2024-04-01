package com.hbm.render.tileentity;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.render.misc.BeamPronter;
import com.hbm.render.misc.BeamPronter.EnumBeamType;
import com.hbm.render.misc.BeamPronter.EnumWaveType;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.machine.TileEntityCoreEmitter;
import com.hbm.tileentity.machine.TileEntityCoreInjector;
import com.hbm.tileentity.machine.TileEntityCoreReceiver;
import com.hbm.tileentity.machine.TileEntityCoreStabilizer;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class RenderCoreComponent extends TileEntitySpecialRenderer<TileEntityMachineBase> {

	@Override
	public boolean isGlobalRenderer(TileEntityMachineBase te) {
		return true;
	}
	
	@Override
	public void render(TileEntityMachineBase tileEntity, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		GL11.glPushMatrix();
        GL11.glTranslated(x + 0.5, y, z + 0.5);
        GlStateManager.enableLighting();
        GlStateManager.disableCull();
        
        GL11.glRotatef(90, 0F, 1F, 0F);
        
		switch(tileEntity.getBlockMetadata()) {
		case 0:
	        GL11.glTranslated(0.0D, 0.5D, -0.5D);
			GL11.glRotatef(90, 1F, 0F, 0F); break;
		case 1:
	        GL11.glTranslated(0.0D, 0.5D, 0.5D);
			GL11.glRotatef(90, -1F, 0F, 0F); break;
		case 2:
			GL11.glRotatef(90, 0F, 1F, 0F); break;
		case 4:
			GL11.glRotatef(180, 0F, 1F, 0F); break;
		case 3:
			GL11.glRotatef(270, 0F, 1F, 0F); break;
		case 5:
			GL11.glRotatef(0, 0F, 1F, 0F); break;
		}
		
        GL11.glTranslated(0.0D, 0D, 0.0D);

        if(tileEntity instanceof TileEntityCoreEmitter) {
	        bindTexture(ResourceManager.dfc_emitter_tex);
	        ResourceManager.dfc_emitter.renderAll();
	    }

	    if(tileEntity instanceof TileEntityCoreReceiver) {
	        bindTexture(ResourceManager.dfc_receiver_tex);
	        ResourceManager.dfc_receiver.renderAll();
        }

        if(tileEntity instanceof TileEntityCoreInjector) {
	        bindTexture(ResourceManager.dfc_injector_tex);
	        ResourceManager.dfc_injector.renderAll();
	    }

	    if(tileEntity instanceof TileEntityCoreStabilizer) {
	        bindTexture(ResourceManager.dfc_stabilizer_tex);
	        ResourceManager.dfc_injector.renderAll();
	    }
	    
        if(tileEntity instanceof TileEntityCoreStabilizer) {
	        GL11.glTranslated(0, 0.5, 0);
	        int range = ((TileEntityCoreStabilizer)tileEntity).beam;

	        if(range > 0) {
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0x002333, 0x7F7F7F, 0, 1, 0F, 2, 0.125F);
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x002333, 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() * -8 % 360, range * 3, 0.125F, 2, 0.04F);
	    		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, 0x003C56, 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() * -8 % 360 + 180, range * 3, 0.125F, 2, 0.04F);
	        }
        }

        if(tileEntity instanceof TileEntityCoreEmitter) {
	        GL11.glTranslated(0, 0.5, 0);
	        int range = ((TileEntityCoreEmitter)tileEntity).beam;
	        
	        if(range > 0) {
	        	float width = (float)Math.max(1, Math.log10(((TileEntityCoreEmitter)tileEntity).prev) - 6) / 8F;
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.STRAIGHT, EnumBeamType.SOLID, 0x401500, 0x7F7F7F, 0, 1, 0F, 2, width);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x401500, 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() % 1000, (int)(0.3F * range/width), width * 0.75F, 2, width * 0.5F);
		        BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.RANDOM, EnumBeamType.SOLID, 0x5B1D00, 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() % 1000 + 1, (int)(0.3F * range/width), width * 0.75F, 2, width * 0.5F);
	        }
        }

        if(tileEntity instanceof TileEntityCoreInjector) {      
	        GL11.glTranslated(0, 0.5, 0);
	        TileEntityCoreInjector injector = (TileEntityCoreInjector)tileEntity;
	        int range = injector.beam;
	        
	        if(range > 0) {
	        	RenderHelper.bindBlockTexture();
	        	if(injector.tanks[0].getFluidAmount() > 0)
	        		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, ModForgeFluids.getFluidColor(injector.tanks[0].getFluid().getFluid()), 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() * -2 % 360, range, 0.09F, 3, 0.0625F);
	        	if(injector.tanks[1].getFluidAmount() > 0)
	        		BeamPronter.prontBeam(Vec3.createVectorHelper(0, 0, range), EnumWaveType.SPIRAL, EnumBeamType.SOLID, ModForgeFluids.getFluidColor(injector.tanks[1].getFluid().getFluid()), 0x7F7F7F, (int)tileEntity.getWorld().getTotalWorldTime() * -2 % 360 + 180, range, 0.09F, 3, 0.0625F);
	        }
        }
        
        GlStateManager.enableLighting();
        GL11.glPopMatrix();
	}
}
