package com.hbm.inventory.control_panel.controls.configs;

import com.hbm.inventory.control_panel.*;
import com.hbm.main.MainRegistry;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiCheckBox;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.util.HashMap;
import java.util.Map;

public class SubElementDisplaySevenSeg extends SubElementBaseConfig {

    private float colorR;
    private float colorG;
    private float colorB;
    private int digitCount;
    private boolean isDecimal;

    GuiSlider slide_colorR;
    GuiSlider slide_colorG;
    GuiSlider slide_colorB;
    GuiSlider slide_digitCount;
    GuiCheckBox check_isDecimal;

    public SubElementDisplaySevenSeg(GuiControlEdit gui, Map<String, DataValue> map) {
        super(gui);
        this.colorR = map.get("colorR").getNumber();
        this.colorG = map.get("colorG").getNumber();
        this.colorB = map.get("colorB").getNumber();
        this.digitCount = (int) map.get("digitCount").getNumber();
        this.isDecimal = map.get("isDecimal").getBoolean();
    }

    @Override
    public Map<String, DataValue> getConfigs() {
        Map<String, DataValue> m = new HashMap<>();
        m.put("colorR", new DataValueFloat(colorR));
        m.put("colorG", new DataValueFloat(colorG));
        m.put("colorB", new DataValueFloat(colorB));
        m.put("digitCount", new DataValueFloat(digitCount));
        m.put("isDecimal", new DataValueFloat(isDecimal));
        return m;
    }

    @Override
    public void initGui() {
        int cX = gui.width/2;
        int cY = gui.height/2;

        slide_colorR = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-85, gui.getGuiTop()+70, 80, 15, TextFormatting.RED+"R ", "", 0, 100, colorR*100, false, true));
        slide_colorG = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-85, gui.getGuiTop()+90, 80, 15, TextFormatting.GREEN+"G ", "", 0, 100, colorG*100, false, true));
        slide_colorB = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-85, gui.getGuiTop()+110, 80, 15, TextFormatting.BLUE+"B ", "", 0, 100, colorB*100, false, true));

        slide_digitCount = gui.addButton(new GuiSlider(gui.currentButtonId(), cX+10, gui.getGuiTop()+70, 75, 15, "Digits ", "", 0, 8, digitCount, false, true));
        check_isDecimal = gui.addButton(new GuiCheckBox(gui.currentButtonId(), cX+10, gui.getGuiTop()+92, "Decimal", isDecimal));

        super.initGui();
    }

    @Override
    public void drawScreen() {
        GlStateManager.disableLighting();
        gui.mc.getTextureManager().bindTexture(ResourceManager.white);
        RenderHelper.drawGuiRectColor(gui.getGuiLeft()+20, gui.getGuiTop()+70, 0, 0, 15, 15, 1, 1, colorR, colorG, colorB, 1F);
        GlStateManager.enableLighting();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        colorR = (float) slide_colorR.sliderValue;
        colorG = (float) slide_colorG.sliderValue;
        colorB = (float) slide_colorB.sliderValue;
        digitCount = slide_digitCount.getValueInt();
        isDecimal = check_isDecimal.isChecked();
    }

    @Override
    public void enableButtons(boolean enable) {
        slide_colorR.visible = enable;
        slide_colorR.enabled = enable;
        slide_colorG.visible = enable;
        slide_colorG.enabled = enable;
        slide_colorB.visible = enable;
        slide_colorB.enabled = enable;
        slide_digitCount.visible = enable;
        slide_digitCount.enabled = enable;
        check_isDecimal.visible = enable;
        check_isDecimal.enabled = enable;
    }
}
