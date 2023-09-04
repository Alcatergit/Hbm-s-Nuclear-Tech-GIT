package com.hbm.inventory.control_panel;

import com.hbm.lib.RefStrings;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Map;

public class SubElementGlobalVars extends SubElement {

    public static ResourceLocation list_bg = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/gui_var_list_bg.png");

    GuiButton btn_clearAll;
    GuiButton btn_back;
    GuiButton btn_newNumber;
    GuiButton btn_newString;
    GuiButton btn_confirmNewVar;
    GuiButton btn_nextPage;
    GuiButton btn_prevPage;

    GuiTextField txt_newVarName;
    GuiTextField txt_newVarData;

    DataValue newVarData;

    public int numPages = 1;
    public int currentPage = 1;

    int list_offset = 0;
    boolean isCreatingNewVar = false;

    // each variable's own elements
    Map<String, GuiButton> btns_var_delete = new HashMap<>();
    Map<String, GuiTextField> txts_var_data = new HashMap<>();

    public SubElementGlobalVars(GuiControlEdit gui) {
        super(gui);
        newVarData = new DataValueFloat(0);
    }

    @Override
    protected void initGui() {
        int cX = gui.width/2;
        int cY = gui.height/2;

        btn_back = gui.addButton(new GuiButton(gui.currentButtonId(), cX-104, cY-112, 20, 20, "<"));
        btn_clearAll = gui.addButton(new GuiButton(gui.currentButtonId(), cX-104, cY-90, 20, 20, "C"));

        btn_prevPage = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+150, cY-90, 15, 20, "<"));
        btn_nextPage = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+195, cY-90, 15, 20, ">"));

        btn_newNumber = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+35, cY-90, 50, 20, "Number"));
        btn_newString = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+90, cY-90, 50, 20, "String"));

        btn_confirmNewVar = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+185, cY-60, 20, 20, "+"));
        btn_confirmNewVar.packedFGColour = 0x5dac5d;

        Keyboard.enableRepeatEvents(true);
        txt_newVarName = new GuiTextField(gui.currentButtonId(), gui.getFontRenderer(), gui.getGuiLeft()+53, cY-58, 50, 16);
        txt_newVarName.setText("name");

        txt_newVarData = new GuiTextField(gui.currentButtonId(), gui.getFontRenderer(), gui.getGuiLeft()+118, cY-58, 60, 16);

        super.initGui();
    }

    @Override
    protected void renderBackground() {
        gui.mc.getTextureManager().bindTexture(list_bg);
        gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, gui.getXSize(), gui.getYSize());
    }

    @Override
    protected void drawScreen() {
        int cX = gui.width/2;
        int cY = gui.height/2;
        String text = "Global Variables";
        gui.getFontRenderer().drawString(text, cX - gui.getFontRenderer().getStringWidth(text)/2F+10, cY-110, 0xFF777777, false);

        text = currentPage + "/" + numPages;
        gui.getFontRenderer().drawString(text, (gui.getGuiLeft()+180) - gui.getFontRenderer().getStringWidth(text)/2F, cY-84, 0xFF777777, false);

        if (isCreatingNewVar) {
            this.list_offset = 35;
            drawCreateVar();
        } else {
            this.list_offset = 0;
        }
        btn_confirmNewVar.enabled = isCreatingNewVar;
        btn_confirmNewVar.visible = isCreatingNewVar;
        drawVarList();
    }

    protected void drawVarList() {
        int cY = gui.height/2 + this.list_offset;

        gui.getButtons().removeAll(btns_var_delete.values());
        btns_var_delete.clear();
        txts_var_data.clear();

        String text;
        int i = 0;

        for (Map.Entry<String, DataValue> e : gui.control.panel.globalVars.entrySet()) {
            int j = i%6;
            text = e.getKey();

            btns_var_delete.put(e.getKey(), new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+35, (cY-60)+(j*28), 20, 20, "X"));
            GuiButton btn_del = btns_var_delete.get(e.getKey());
            btn_del.enabled = false;
            btn_del.visible = false;

            if ((i >= (currentPage-1)*6) && (i < ((currentPage-1)*6)+6)) {
                if (!(isCreatingNewVar && j==5)) { // lazy. last element of each page is hidden when isCreatingNewVar
                    btn_del.enabled = true;
                    btn_del.visible = true;

                    gui.getFontRenderer().drawString((gui.getFontRenderer().getStringWidth(text) > 70) ? text.substring(0, 10) + "..." : text, gui.getGuiLeft() + 60, (cY - 54) + (j * 28), 0xFF777777, false);
                    gui.getFontRenderer().drawString(" = ", gui.getGuiLeft() + 130, (cY - 54) + (j * 28), 0xFF777777, false);

                    GuiTextField txt = new GuiTextField(i + 3000, gui.getFontRenderer(), gui.getGuiLeft() + 143, (cY - 58) + (j * 28), 60, 16);
                    txt.setText(e.getValue().toString());
                    txt.setTextColor((e.getValue().getType() == DataValue.DataType.NUMBER) ? 0x99CCFF : 0xFFFF99);
                    txts_var_data.put(e.getKey(), txt);
                    txt.drawTextBox();

                    if (!(isCreatingNewVar && j==4)) {
                        gui.getFontRenderer().drawString("-----------------------------", gui.getGuiLeft() + 33, (cY - 39.5F) + (j * 28), 0xFF777777, false);
                    }
                }
            }

            i++;
        }
        numPages = (int) Math.ceil(btns_var_delete.size() / 6F);
        currentPage = MathHelper.clamp(currentPage, 1, numPages);

        gui.getButtons().addAll(btns_var_delete.values());
    }

    protected void drawCreateVar() {
        int cY = gui.height/2;

        String text = "";
        switch (newVarData.getType()) {
            case NUMBER : text = "N : "; break;
            case STRING : text = "S : "; break;
        }
        gui.getFontRenderer().drawString(text, gui.getGuiLeft()+35, (cY-54), 0xFF777777, false);
        txt_newVarName.drawTextBox();
        gui.getFontRenderer().drawString(" = ", gui.getGuiLeft()+104, (cY-54), 0xFF777777, false);
        txt_newVarData.drawTextBox();

        gui.getFontRenderer().drawString("∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙ ∙", gui.getGuiLeft()+31, (cY-37.5F), 0xFF777777, false);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int button) {
        super.mouseClicked(mouseX, mouseY, button);
        this.txt_newVarName.mouseClicked(mouseX, mouseY, button);
        this.txt_newVarData.mouseClicked(mouseX, mouseY, button);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) {
        super.keyTyped(typedChar, keyCode);
        this.txt_newVarName.textboxKeyTyped(typedChar, keyCode);
        this.txt_newVarData.textboxKeyTyped(typedChar, keyCode);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == btn_newNumber) {
            newVarData = new DataValueFloat(0);
            txt_newVarData.setText(newVarData.toString());
            isCreatingNewVar = true;
        }
        else if (button == btn_newString) {
            newVarData = new DataValueString("");
            txt_newVarData.setText(newVarData.toString());
            isCreatingNewVar = true;
        }
        else if (button == btn_prevPage) {
            currentPage = Math.max(1, currentPage-1);
        }
        else if (button == btn_nextPage) {
            currentPage = Math.min(numPages, currentPage+1);
        }
        else if (button == btn_clearAll) {
            gui.control.panel.globalVars.clear();
        }
        else if (button == btn_back) {
            gui.popElement();
        }
        else if (button == btn_confirmNewVar) {
            if (txt_newVarName.getText().isEmpty() || txt_newVarData.getText().isEmpty()) {
                return;
            }
            isCreatingNewVar = false;
            switch (newVarData.getType()) {
                case NUMBER : {
                    gui.control.panel.globalVars.put(txt_newVarName.getText(), new DataValueFloat(Float.parseFloat(txt_newVarData.getText())));
                    break;
                }
                case STRING : {
                    gui.control.panel.globalVars.put(txt_newVarName.getText(), new DataValueString(txt_newVarData.getText()));
                    break;
                }
            }
            txt_newVarName.setText("name");
        }
        else if (btns_var_delete.containsValue(button)) {
            for (Map.Entry<String, GuiButton> e : btns_var_delete.entrySet()) {
                if (e.getValue().equals(button)) {
                    gui.control.panel.globalVars.remove(e.getKey());
                }
            }
        }
    }

    @Override
    protected void enableButtons(boolean enable) {
        btn_clearAll.enabled = enable;
        btn_clearAll.visible = enable;
        btn_back.enabled = enable;
        btn_back.visible = enable;
        btn_nextPage.enabled = enable;
        btn_nextPage.visible = enable;
        btn_prevPage.enabled = enable;
        btn_prevPage.visible = enable;
        btn_newNumber.enabled = enable;
        btn_newNumber.visible = enable;
        btn_newString.enabled = enable;
        btn_newString.visible = enable;
        btn_confirmNewVar.enabled = enable;
        btn_confirmNewVar.visible = enable;

        txt_newVarName.setEnabled(enable);
        txt_newVarName.setVisible(enable);
        txt_newVarData.setEnabled(enable);
        txt_newVarData.setVisible(enable);

        for (Map.Entry<String, GuiTextField> e : txts_var_data.entrySet()) {
            e.getValue().setEnabled(enable);
            e.getValue().setVisible(enable);
        }

        for (Map.Entry<String, GuiButton> b : btns_var_delete.entrySet()) {
            b.getValue().enabled = enable;
            b.getValue().visible = enable;
        }
    }

}
