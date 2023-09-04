package com.hbm.inventory.control_panel.controls.configs;

import com.hbm.inventory.control_panel.*;

import java.util.HashMap;
import java.util.Map;

public class SubElementBaseConfig extends SubElement {
    public SubElementBaseConfig(GuiControlEdit gui) {
        super(gui);
    }

    public Map<String, DataValue> getConfigs() {
        return new HashMap<>();
    }
}
