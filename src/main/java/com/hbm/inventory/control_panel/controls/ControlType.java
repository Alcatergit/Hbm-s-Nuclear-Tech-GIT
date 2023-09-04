package com.hbm.inventory.control_panel.controls;

public enum ControlType {
    BUTTON("Button"),
    SWITCH("Switch"),
    DISPLAY("Display");

    public String name;
    ControlType(String name){
        this.name = name;
    }

    public static ControlType getByName(String name){
        for(ControlType o : values()){
            if(o.name.equals(name)){
                return o;
            }
        }
        return null;
    }
}
