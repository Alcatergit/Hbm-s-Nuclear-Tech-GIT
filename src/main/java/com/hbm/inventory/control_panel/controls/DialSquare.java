package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.Control;
import com.hbm.inventory.control_panel.ControlEvent;
import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.List;

public class DialSquare extends Control {

    public DialSquare(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("value", new DataValueFloat(0));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.DIAL;
    }

    @Override
    public float[] getSize() {
        return new float[] {2.2F, 2.2F, .4F};
    }

    @Override
    public void render() {
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_dial_square_tex);
        Tessellator tes = Tessellator.instance;
        IModelCustom model = getModel();

        int value = (int) getVar("value").getNumber(); //TODO: node to clamp the knob

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1);
        model.tessellatePart(tes, "base");
        tes.draw();

        GL11.glPushMatrix();
//            Matrix4f rot_mat = new Matrix4f().rotate((float) -(value*((2*Math.PI)/11F)), new Vector3f(0, 1, 0));
//            Matrix4f.mul(new Matrix4f().translate(new Vector3f(posX, -.04F, posY)), rot_mat, new Matrix4f()).store(ClientProxy.AUX_GL_BUFFER);
//            ClientProxy.AUX_GL_BUFFER.rewind();
//            GlStateManager.multMatrix(ClientProxy.AUX_GL_BUFFER);
            tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
            tes.setColorRGBA_F(1, 1, 1, 1);
            model.tessellatePart(tes, "dial");
            tes.draw();
        GL11.glPopMatrix();

        GL11.glPushMatrix();
            GlStateManager.translate(posX, .07F, posY);
            GL11.glScalef(.023F, .023F, .023F);
            GL11.glNormal3f(0.0F, 0.0F, -1.0F);
            GL11.glRotatef(90, 1, 0, 0);

            float lX = OpenGlHelper.lastBrightnessX;
            float lY = OpenGlHelper.lastBrightnessY;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);
            FontRenderer font = Minecraft.getMinecraft().fontRenderer;

            for (int i=0; i<11; i++) {
                double angle = (Math.PI/2)/11F * i;
                float r = 68;
                double x = r * Math.cos(angle-Math.PI);
                double y = r * Math.sin(angle-Math.PI);
                if (i%2 != 0)
                    font.drawString("·", (float) (32F+x), (float) (29.5F+y), 0x303030, false);
                else
                    font.drawString(Integer.toString(i), (float) (32F+x), (float) (29.5F+y), 0x303030, false);
            }

            font.drawString("POWER", -2F, -5, 0x303030, false);
            font.drawString("(RS)", -2F, 5, 0x303030, false);

            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
        GL11.glPopMatrix();
    }

    @Override
    public IModelCustom getModel() {
        return ResourceManager.ctrl_dial_square;
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_dial_square_gui_tex;
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new DialSquare(name, panel);
    }

    @Override
    public void populateDefaultNodes(List<ControlEvent> receiveEvents) {

    }
}
