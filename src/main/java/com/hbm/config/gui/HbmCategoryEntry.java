package com.hbm.config.gui;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.GuiConfigEntries;
import net.minecraftforge.fml.client.config.IConfigElement;

public class HbmCategoryEntry extends GuiConfigEntries.CategoryEntry {

    private GuiConfig childScreen;

    public HbmCategoryEntry(GuiConfig owningScreen, GuiConfigEntries owningEntryList,
                            IConfigElement configElement) {
        super(owningScreen, owningEntryList, configElement);
    }

    @Override
    public boolean enabled() {
        return !(owningScreen.allRequireWorldRestart && owningScreen.isWorldRunning);
    }

    @Override
    public void drawEntry(int slotIndex, int x, int y, int listWidth, int slotHeight,
                          int mouseX, int mouseY, boolean isSelected, float partial) {
        super.drawEntry(slotIndex, x, y, listWidth, slotHeight, mouseX, mouseY, isSelected, partial);
        this.btnSelectCategory.enabled = true;
        this.btnSelectCategory.drawButton(this.mc, mouseX, mouseY, partial);
    }

    @Override
    protected GuiScreen buildChildScreen() {
        final String title = ((this.owningScreen.titleLine2 == null ? "" : this.owningScreen.titleLine2)
                + " > " + this.configElement.getLanguageKey());
        childScreen = new GuiConfig(this.owningScreen, this.configElement.getChildElements(),
                this.owningScreen.modID,
                owningScreen.allRequireWorldRestart || this.configElement.requiresWorldRestart(),
                owningScreen.allRequireMcRestart || this.configElement.requiresMcRestart(),
                this.owningScreen.title, title) {
            private boolean doneClicked = false;

            @Override
            protected void actionPerformed(GuiButton button) {
                if (button.id == 2000 && this.entryList != null) {
                    doneClicked = true;
                    boolean isParentScreen = false;
                    for (GuiConfigEntries.IConfigEntry e : this.entryList.listEntries) {
                        if (e instanceof HbmCategoryEntry) {
                            isParentScreen = true;
                            break;
                        }
                    }
                    if (isParentScreen) {
                        saveAllRecursive(this.entryList, true);
                        if (!this.allRequireMcRestart) {
                            HbmConfigGui.saveAllConfigs();
                        }
                        this.needsRefresh = true;
                    }
                }
                super.actionPerformed(button);
            }

            @Override
            public void onGuiClosed() {
                if (!doneClicked && this.parentScreen instanceof GuiConfig) {
                    ((GuiConfig) this.parentScreen).needsRefresh = true;
                }
                if (doneClicked && this.entryList != null && this.parentScreen instanceof GuiConfig) {
                    boolean isParentScreen = false;
                    for (GuiConfigEntries.IConfigEntry e : this.entryList.listEntries) {
                        if (e instanceof HbmCategoryEntry) {
                            isParentScreen = true;
                            break;
                        }
                    }
                    if (!isParentScreen) {
                        ((GuiConfig) this.parentScreen).needsRefresh = false;
                    }
                }
                super.onGuiClosed();
            }
        };
        return childScreen;
    }

    void saveChildEntriesRecursive(boolean undoChanges) {
        if (childScreen != null && childScreen.entryList != null) {
            saveAllRecursive(childScreen.entryList, undoChanges);
        }
    }

    static void saveAllRecursive(GuiConfigEntries entries, boolean undoChanges) {
        for (GuiConfigEntries.IConfigEntry entry : entries.listEntries) {
            if (entry instanceof HbmCategoryEntry) {
                ((HbmCategoryEntry) entry).saveChildEntriesRecursive(undoChanges);
            }
            entry.saveConfigElement();
            if (undoChanges) {
                entry.undoChanges();
            }
        }
    }
}