package com.hbm.render.tileentity;

import com.hbm.blocks.BlockDummyable;
import com.hbm.main.ResourceManager;
import com.hbm.tileentity.machine.TileEntityOrbitalStation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import org.lwjgl.opengl.GL11;

public class RenderOrbitalStation extends TileEntitySpecialRenderer<TileEntityOrbitalStation> {
	
	@Override
	public void render(TileEntityOrbitalStation station, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {

		GL11.glPushMatrix();
		{

			GL11.glTranslated(x + 0.5D, y + 1.0D, z + 0.5D);
			GL11.glEnable(GL11.GL_LIGHTING);
	
			switch(station.getBlockMetadata() - BlockDummyable.offset) {
			case 2: GL11.glRotatef(0, 0F, 1F, 0F); break;
			case 4: GL11.glRotatef(90, 0F, 1F, 0F); break;
			case 3: GL11.glRotatef(180, 0F, 1F, 0F); break;
			case 5: GL11.glRotatef(270, 0F, 1F, 0F); break;
			}

			GL11.glShadeModel(GL11.GL_SMOOTH);
	
			bindTexture(ResourceManager.docking_port_tex);
			ResourceManager.docking_port.renderPart("Port");

			float rotation = station.prevRot + (station.rot - station.prevRot) * partialTicks;

			for(int i = 0; i < 4; i++) {
				GL11.glPushMatrix();
				{

					// one hop this time
					GL11.glTranslatef(0, -1.75F, -2);

					// criss cross
					GL11.glRotatef(-rotation, 1, 0, 0);

					// one hop this time
					GL11.glTranslatef(0, 1.75F, 2);

					// let's go to work
					ResourceManager.docking_port.renderPart("ArmZP");

				}
				GL11.glPopMatrix();

				// cha cha real smooth
				GL11.glRotatef(90, 0, 1, 0);
			}

			GL11.glShadeModel(GL11.GL_FLAT);

		}
		GL11.glPopMatrix();
	}

}
