package com.hbm.inventory.control_panel.controls.configs;

import com.hbm.inventory.control_panel.*;
import com.hbm.main.MainRegistry;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.util.HashMap;
import java.util.Map;

public class SubElementDisplaySevenSeg extends SubElement {

    private float colorR;
    private float colorG;
    private float colorB;

    GuiSlider slide_colorR;
    GuiSlider slide_colorG;
    GuiSlider slide_colorB;

    public SubElementDisplaySevenSeg(GuiControlEdit gui, Map<String, DataValue> map) {
        super(gui);
        this.colorR = map.get("colorR").getNumber();
        this.colorG = map.get("colorG").getNumber();
        this.colorB = map.get("colorB").getNumber();
    }

    public Map<String, DataValue> getConfigs() {
        Map<String, DataValue> m = new HashMap<>();
        m.put("colorR", new DataValueFloat(colorR));
        m.put("colorG", new DataValueFloat(colorG));
        m.put("colorB", new DataValueFloat(colorB));
        return m;
    }

    @Override
    public void initGui() {
        int cX = gui.width/2;
        int cY = gui.height/2;

        slide_colorR = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-74, gui.getGuiTop()+70, 80, 15, TextFormatting.RED+"R ", "", 0, 100, colorR*100, false, true));
        slide_colorG = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-74, gui.getGuiTop()+90, 80, 15, TextFormatting.GREEN+"G ", "", 0, 100, colorG*100, false, true));
        slide_colorB = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-74, gui.getGuiTop()+110, 80, 15, TextFormatting.BLUE+"B ", "", 0, 100, colorB*100, false, true));

        super.initGui();
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int state) {
        colorR = (float) slide_colorR.sliderValue;
        colorG = (float) slide_colorG.sliderValue;
        colorB = (float) slide_colorB.sliderValue;
    }

    @Override
    public void enableButtons(boolean enable) {
        slide_colorR.visible = enable;
        slide_colorR.enabled = enable;
        slide_colorG.visible = enable;
        slide_colorG.enabled = enable;
        slide_colorB.visible = enable;
        slide_colorB.enabled = enable;
    }
}
