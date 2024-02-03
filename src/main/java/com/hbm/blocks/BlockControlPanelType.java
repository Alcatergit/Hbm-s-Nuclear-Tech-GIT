package com.hbm.blocks;

import java.util.Locale;
import net.minecraft.util.IStringSerializable;

public enum BlockControlPanelType implements IStringSerializable {
    CUSTOM_PANEL,
    FRONT_PANEL;

    @Override
    public String getName() {
        return toString().toLowerCase(Locale.ENGLISH);
    }
}
