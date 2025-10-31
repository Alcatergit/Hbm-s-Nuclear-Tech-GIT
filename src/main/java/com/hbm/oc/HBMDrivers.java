package com.hbm.oc;

import li.cil.oc.api.API;

public class HBMDrivers {

    public static void init()
    {
        API.driver.add(new PWRDriver());
    }
}
