package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.Control;
import com.hbm.inventory.control_panel.ControlPanel;
import com.hbm.inventory.control_panel.DataValueFloat;
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

import java.util.Collections;
import java.util.List;


public class ButtonHazard extends Control {

    public ButtonHazard(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("isPushed", new DataValueFloat(0));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.BUTTON;
    }

    @Override
    public float[] getSize() {
        return new float[] {1.5F, 1.5F, 1.1F};
    }

    @Override
    public void render() {
        boolean isPushed = getVar("isPushed").getBoolean();

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_button2_tex);
        Tessellator tes = Tessellator.instance;

        IModelCustom model = getModel();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1F);
        model.tessellatePart(tes, "base");
        tes.draw();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, (isPushed)?-0.09:0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1F);
        model.tessellatePart(tes, "top");
        tes.draw();

        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return ResourceManager.ctrl_button2;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_button2_gui_tex;
    }

    @Override
	public List<String> getOutEvents() {
		return Collections.singletonList("ctrl_button_press");
	}

    @Override
    public Control newControl(ControlPanel panel) {
        return new ButtonHazard(name, panel);
    }
}
