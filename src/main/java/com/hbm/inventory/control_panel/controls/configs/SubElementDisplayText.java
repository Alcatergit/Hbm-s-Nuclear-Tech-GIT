package com.hbm.inventory.control_panel.controls.configs;

import com.hbm.inventory.control_panel.DataValue;
import com.hbm.inventory.control_panel.DataValueFloat;
import com.hbm.inventory.control_panel.GuiControlEdit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.client.config.GuiSlider;

import java.util.HashMap;
import java.util.Map;

public class SubElementDisplayText extends SubElementBaseConfig {

    private int scale;
    private int width;

    GuiSlider slideScale;
    GuiSlider slideWidth;

    public SubElementDisplayText(GuiControlEdit gui, Map<String, DataValue> map) {
        super(gui);
        this.scale = (int) map.get("scale").getNumber();
        this.width = (int) map.get("width").getNumber();
    }

    @Override
    public void initGui() {
        int cX = gui.width/2;
        slideScale = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-85, gui.getGuiTop()+70, 80, 15, "Scale ", "", 10, 100, scale, false, true));
        slideWidth = gui.addButton(new GuiSlider(gui.currentButtonId(), cX-85, gui.getGuiTop()+90, 80, 15, "Width ", "", 10, 100, width, false, true));
        super.initGui();
    }

    @Override
    public Map<String, DataValue> getConfigs() {
        Map<String, DataValue> m = new HashMap<>();
        m.put("scale", new DataValueFloat(scale));
        m.put("width", new DataValueFloat(width));
        return m;
    }

    @Override
    public void mouseReleased(int mX, int mY, int state) {
        scale = slideScale.getValueInt();
        width = slideWidth.getValueInt();
    }

    @Override
    public void enableButtons(boolean enable) {
        slideScale.visible = enable;
        slideScale.enabled = enable;
        slideWidth.visible = enable;
        slideWidth.enabled = enable;
    }
}
