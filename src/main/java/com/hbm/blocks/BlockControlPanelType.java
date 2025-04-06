package com.hbm.blocks;

import net.minecraft.util.IStringSerializable;

import java.util.Locale;

public enum BlockControlPanelType implements IStringSerializable {
    CUSTOM_PANEL,
    FRONT_PANEL;

    @Override
    public String getName() {
        return toString().toLowerCase(Locale.ENGLISH);
    }
}
