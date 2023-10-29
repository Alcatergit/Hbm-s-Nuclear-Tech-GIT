package com.hbm.inventory.control_panel.controls;

import com.hbm.inventory.control_panel.*;
import com.hbm.inventory.control_panel.nodes.*;
import com.hbm.main.ResourceManager;
import com.hbm.render.amlfrom1710.IModelCustom;
import com.hbm.render.amlfrom1710.Tessellator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SliderVertical extends Control {

    public SliderVertical(String name, ControlPanel panel) {
        super(name, panel);
        vars.put("value", new DataValueFloat(0));
    }

    @Override
    public ControlType getControlType() {
        return ControlType.SLIDER;
    }

    @Override
    public float[] getSize() {
        return new float[] {1, 2.5F, .18F};
    }

    @Override
    public void render() {
        GlStateManager.shadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ResourceManager.ctrl_slider_vertical_tex);
        Tessellator tes = Tessellator.instance;
        IModelCustom model = getModel();

        int position = (int) Math.abs(getVar("value").getNumber()) % 5;

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY);
        tes.setColorRGBA_F(1, 1, 1, 1);
        model.tessellatePart(tes, "base");
        tes.draw();

        GlStateManager.disableTexture2D();

        tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
        tes.setTranslation(posX, 0, posY-(.4375F*position));
        tes.setColorRGBA_F(51/255F, 51/255F, 51/255F, 1);
        model.tessellatePart(tes, "slider");
        tes.draw();

        float lX = OpenGlHelper.lastBrightnessX;
        float lY = OpenGlHelper.lastBrightnessY;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240, 240);

        for (int i=0; i<=position; i++) {
            tes.startDrawing(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
            tes.setTranslation(posX, 0, posY);
            tes.setColorRGBA_F(1, 1, 173/255F, 1);
            model.tessellatePart(tes, "light"+i);
            tes.draw();
        }

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lX, lY);
        GlStateManager.enableTexture2D();
        GlStateManager.shadeModel(GL11.GL_FLAT);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IModelCustom getModel() {
        return ResourceManager.ctrl_slider_vertical;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ResourceLocation getGuiTexture() {
        return ResourceManager.ctrl_slider_vertical_gui_tex;
    }

    @Override
    public List<String> getOutEvents() {
        return Collections.singletonList("ctrl_press");
    }

    @Override
    public void populateDefaultNodes(List<ControlEvent> receiveEvents) {
        NodeSystem ctrl_press = new NodeSystem(this);
        {
            Map<String, DataValue> vars = new HashMap<>(receiveEvents.get(0).vars);
            vars.put("from index", new DataValueFloat(0));
            NodeInput node0 = new NodeInput(170, 100, "Event Data").setVars(vars);
            ctrl_press.addNode(node0);
            NodeBoolean node1 = new NodeBoolean(230, 100).setData(NodeBoolean.BoolOperation.NOT);
            node1.inputs.get(0).setData(node0, 1, true);
            ctrl_press.addNode(node1);
            NodeFunction node2 = new NodeFunction(230, 140);
            NodeSystem node2_subsystem = new NodeSystem(this);
            {
                NodeGetVar node2_0 = new NodeGetVar(170, 100, this).setData("value", false);
                node2_subsystem.addNode(node2_0);
                NodeMath node2_1 = new NodeMath(230, 120).setData(NodeMath.Operation.SUB);
                node2_1.inputs.get(0).setData(node2_0, 0, true);
                node2_1.inputs.get(1).setDefault(new DataValueFloat(1));
                node2_subsystem.addNode(node2_1);
                NodeMath node2_2 = new NodeMath(290, 80).setData(NodeMath.Operation.GEQUAL);
                node2_2.inputs.get(0).setData(node2_1, 0, true);
                node2_2.inputs.get(1).setDefault(new DataValueFloat(0));
                node2_subsystem.addNode(node2_2);
                NodeConditional node2_3 = new NodeConditional(350, 100);
                node2_3.inputs.get(0).setData(node2_2, 0, true);
                node2_3.inputs.get(1).setData(node2_1, 0, true);
                node2_3.inputs.get(2).setDefault(new DataValueFloat(0));
                node2_subsystem.addNode(node2_3);
                NodeSetVar node2_4 = new NodeSetVar(410, 110, this).setData("value", false);
                node2_4.inputs.get(0).setData(node2_3, 0, true);
                node2_subsystem.addNode(node2_4);
            }
            node2.inputs.get(0).setData(node0, 1, true);
            ctrl_press.subSystems.put(node2, node2_subsystem);
            ctrl_press.addNode(node2);
            NodeFunction node3 = new NodeFunction(290, 120);
            NodeSystem node3_subsystem = new NodeSystem(this);
            {
                NodeGetVar node3_0 = new NodeGetVar(170, 100, this).setData("value", false);
                node3_subsystem.addNode(node3_0);
                NodeMath node3_1 = new NodeMath(230, 120).setData(NodeMath.Operation.ADD);
                node3_1.inputs.get(0).setData(node3_0, 0, true);
                node3_1.inputs.get(1).setDefault(new DataValueFloat(1));
                node3_subsystem.addNode(node3_1);
                NodeMath node3_2 = new NodeMath(290, 80).setData(NodeMath.Operation.LEQUAL);
                node3_2.inputs.get(0).setData(node3_1, 0, true);
                node3_2.inputs.get(1).setDefault(new DataValueFloat(4));
                node3_subsystem.addNode(node3_2);
                NodeConditional node3_3 = new NodeConditional(350, 100);
                node3_3.inputs.get(0).setData(node3_2, 0, true);
                node3_3.inputs.get(1).setData(node3_1, 0, true);
                node3_3.inputs.get(2).setDefault(new DataValueFloat(4));
                node3_subsystem.addNode(node3_3);
                NodeSetVar node3_4 = new NodeSetVar(410, 110, this).setData("value", false);
                node3_4.inputs.get(0).setData(node3_3, 0, true);
                node3_subsystem.addNode(node3_4);
            }
            node3.inputs.get(0).setData(node1, 0, true);
            ctrl_press.subSystems.put(node3, node3_subsystem);
            ctrl_press.addNode(node3);
        }
        receiveNodeMap.put("ctrl_press", ctrl_press);
    }

    @Override
    public Control newControl(ControlPanel panel) {
        return new SliderVertical(name, panel);
    }

}
