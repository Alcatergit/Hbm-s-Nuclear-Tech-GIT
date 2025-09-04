package com.hbm.items.armor;

import com.hbm.handler.ArmorModHandler;

public class ItemModBattery extends ItemArmorMod {

    public double mod;

    public ItemModBattery(double mod, String s) {
        super(ArmorModHandler.battery, true, true, true, true, s);
        this.mod = mod;
    }
}
