package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.*;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Map;


public class DisplaySevenSeg extends Control {

    private float[] color = new float[] {1, 1, 1};

    public DisplaySevenSeg(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("value", new DataValueFloat(0));
        config_map.put("colorR", new DataValueFloat(color[0]));
        config_map.put("colorG", new DataValueFloat(color[1]));
        config_map.put("colorB", new DataValueFloat(color[2]));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.DISPLAY;
    }

    @Override
    public float[] getSize() {
        return new float[] {0.625F, 1F, 0};
    }

    @Override
    public void applyConfigs(Map<String, DataValue> configs) {
        super.applyConfigs(configs);

        for (Map.Entry<String, DataValue> e : config_map.entrySet()) {
            switch (e.getKey()) {
                case "colorR" : {
                    color[0] = e.getValue().getNumber();
                    break;
                }
                case "colorG" : {
                    color[1] = e.getValue().getNumber();
                    break;
                }
                case "colorB" : {
                    color[2] = e.getValue().getNumber();
                    break;
                }
            }
        }
    }

//    A
//  F   B
//    G
//  E   C
//    D

    // abcdefg encoding
    private static final byte[] chars = {
            0x7E, 0x30, 0x6D, 0x79, 0x33, 0x5B, 0x5F, 0x70, 0x7F, 0x7B, 0x77, 0x1F, 0x4E, 0x3D, 0x4F, 0x47
    };

    @Override
    public void render() {
        int value = ((int) getVar("value").getNumber() & 0xF);

        byte character = chars[value];

        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_display0_tex);
        Tessellator tes = Tessellator.instance;

        IModelCustom model = getModel();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1);
        model.tessellatePart(tes, "base");
        tes.draw();

        float lX = OpenGlHelper.lastBrightnessX;
        float lY = OpenGlHelper.lastBrightnessY;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        for (int i=0; i<7; i++) {
            boolean enabled = (character & (1<<i)) != 0;
            float cMul = (enabled)? 1 : 0.1F;

            GlStateManager.disableTexture2D();
            tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            tes.setTranslation(posX, 0, posY);
            tes.setColorRGBA_F(color[0]*cMul, color[1]*cMul, color[2]*cMul, 1);
            model.tessellatePart(tes, "seg_"+(6-i)); // gotta go through these segments in reverse
            tes.draw();
            GlStateManager.enableTexture2D();
        }

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return ResourceManager.ctrl_display0;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_button2_gui_tex;
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return null;
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new DisplaySevenSeg(name, panel);
    }
}
