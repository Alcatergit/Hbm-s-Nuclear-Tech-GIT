package com.hbm.inventory.control_panel;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.control_panel.nodes.Node;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

import java.util.function.Consumer;

public class NodeButton extends NodeElement {

    public NodeButton(String name, Node parent, int idx) {
        super(parent, idx);
        this.name = name;
        resetOffset();
    }

    @Override
    public void render(float mX, float mY){
        Minecraft.getMinecraft().getTextureManager().bindTexture(NodeSystem.node_tex);
        Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
        float x = offsetX+4;
        float y = offsetY+8;
        RenderHelper.drawGuiRectBatchedColor(x, y, 0F, 0.890625F, 32, 6, 0.609375F, 0.984375F, 1, 1, 1, 1);
        Tessellator.getInstance().draw();

        FontRenderer font = Minecraft.getMinecraft().fontRenderer;
        GL11.glPushMatrix();
        GL11.glTranslated(x, y, 0);
        GL11.glScaled(0.35, 0.35, 0.35);
        GL11.glTranslated(-x, -y, 0);
        font.drawString(name, x+43-font.getStringWidth(name)/2F, y+5, 0xFF5F5F5F, false);
        GL11.glPopMatrix();
    }

    @Override
    public boolean onClick(float x, float y) {
        if (RenderHelper.intersects2DBox(x, y, getBox())) {
            return true;
        }
        return false;
    }

    public float[] getBox() {
        return new float[]{3 + offsetX, -3 + offsetY + 10, 37 + offsetX, 3 + offsetY + 10};
    }

}
