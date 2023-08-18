package com.hbm.inventory.control_panel;

import java.util.List;
import java.util.Map;

import com.hbm.inventory.control_panel.controls.configs.SubElementDisplaySevenSeg;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.text.TextFormatting;

public class SubElementItemConfig extends SubElement {

    public GuiButton btn_done;
    public GuiButton btn_next;
    public GuiButton btn_prev;

    private List<String> variants;
    private int curr_variant = 0;
    private int num_variants = 1;

    SubElementDisplaySevenSeg config_gui;
    private Map<String, DataValue> configs;

    public SubElementItemConfig(GuiControlEdit gui) {
        super(gui);
    }

    @Override
    protected void initGui() {
        int cX = gui.width/2;
        int cY = gui.height/2;
        btn_prev = gui.addButton(new GuiButton(gui.currentButtonId(), cX-30, gui.getGuiTop()+24, 15, 20, "<"));
        btn_next = gui.addButton(new GuiButton(gui.currentButtonId(), cX+15, gui.getGuiTop()+24, 15, 20, ">"));
        btn_done = gui.addButton(new GuiButton(gui.currentButtonId(), cX-74, cY+92, 170, 20, "Done"));

        this.config_gui = new SubElementDisplaySevenSeg(gui, ControlRegistry.registry.get("display_7seg").getConfigs());
        this.config_gui.initGui();
        super.initGui();
    }

    Control last_control = null;

    @Override
    protected void drawScreen() {
        int cX = gui.width/2;
        int cY = gui.height/2;

        variants = ControlRegistry.getAllControlsOfType(gui.currentEditControl.getControlType());
        num_variants = variants.size()-1;

        Control variant = ControlRegistry.registry.get(variants.get(curr_variant));

        String text = variant.name;
        int text_width = gui.getFontRenderer().getStringWidth(text);
        gui.getFontRenderer().drawString(text, (cX-(text_width/2F))+0, gui.getGuiTop()+10, 0xFF777777, false);

        text = (curr_variant+1) + "/" + (num_variants+1);
        text_width = gui.getFontRenderer().getStringWidth(text);
        gui.getFontRenderer().drawString(text, (cX-(text_width/2F))+0, gui.getGuiTop()+30, 0xFF777777, false);

        if (!variant.equals(last_control)) {
            switch (variants.get(curr_variant)) {
                case "display_7seg":
                    this.config_gui = new SubElementDisplaySevenSeg(gui, ControlRegistry.registry.get("display_7seg").getConfigs());
                    break;
            }
            this.config_gui.initGui();
            this.config_gui.enableButtons(true);
        }

        this.last_control = variant;
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        config_gui.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == btn_done) {
            configs = config_gui.getConfigs();
            gui.currentEditControl = ControlRegistry.getNew(variants.get(curr_variant), gui.control.panel);
            gui.currentEditControl.applyConfigs(configs);
            config_gui.enableButtons(false);
            last_control = null;
            gui.pushElement(gui.linker);
        }
        else if (button == btn_next) {
            curr_variant = Math.min(num_variants, curr_variant+1);
        }
        else if (button == btn_prev) {
            curr_variant = Math.max(0, curr_variant-1);
        }
    }

    @Override
    protected void enableButtons(boolean enable) {
        btn_done.visible = enable;
        btn_done.enabled = enable;
        btn_next.visible = enable;
        btn_next.enabled = enable;
        btn_prev.visible = enable;
        btn_prev.enabled = enable;
    }
}
