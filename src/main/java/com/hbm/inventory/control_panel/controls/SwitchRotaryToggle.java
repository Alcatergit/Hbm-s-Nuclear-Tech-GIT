package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.Control;
import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import java.util.Collections;
import java.util.List;

public class SwitchRotaryToggle extends Control {

    public SwitchRotaryToggle(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("isOnPosition", new DataValueFloat(0));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.SWITCH;
    }

    @Override
    public float[] getSize() {
        return new float[] {1, 1, .68F};
    }

    @Override
    public void render() {
        boolean isFlipped = getVar("isOnPosition").getBoolean();

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_switch_rotary_toggle_tex);
        Tessellator tes = Tessellator.instance;

        IModelCustom model = getModel();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1);
        model.tessellatePart(tes, "base");
        tes.draw();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setColorRGBA_F(1, 1, 1, 1);
        if (isFlipped) { // idk if there's a better way to do this...
            Matrix4f rot_mat = new Matrix4f().rotate((float) Math.toRadians(-90), new Vector3f(0, 1, 0));
            Matrix4f trans_mat = new Matrix4f().translate(new Vector3f(posX, 0, posY));
            Matrix4f transform_mat = new Matrix4f();
            Matrix4f.mul(trans_mat, rot_mat, transform_mat);
            transform_mat.store(ClientProxy.AUX_GL_BUFFER);
            ClientProxy.AUX_GL_BUFFER.rewind();
            GlStateManager.multMatrix(ClientProxy.AUX_GL_BUFFER);
        } else {
            tes.setTranslation(posX, 0, posY);
        }
        model.tessellatePart(tes, "lever");
        tes.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return ResourceManager.ctrl_switch_rotary_toggle;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_switch_rotary_toggle_gui_tex;
    }

    @Override
    public List<String> getOutEvents() {
        return Collections.singletonList("ctrl_press");
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new SwitchRotaryToggle(name, panel);
    }
}
