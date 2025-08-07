package com.hbm.handler;

import java.util.HashMap;
import java.util.Map;

public class MultiblockRegistry {

    private static final Map<String, MultiblockDefinition> definitions = new HashMap<>();

    static {
        definitions.put("iGenDimensionNorth", new MultiblockDefinition(1, 1, 2, 0, 3, 2));
        definitions.put("iGenDimensionEast", new MultiblockDefinition(2, 3, 2, 0, 1, 1));
        definitions.put("iGenDimensionSouth", new MultiblockDefinition(1, 1, 2, 0, 2, 3));
        definitions.put("iGenDimensionWest", new MultiblockDefinition(3, 2, 2, 0, 1, 1));
        definitions.put("centDimension", new MultiblockDefinition(0, 0, 3, 0, 0, 0));
        definitions.put("cyclDimension", new MultiblockDefinition(1, 1, 5, 0, 1, 1));
        definitions.put("wellDimension", new MultiblockDefinition(1, 1, 5, 0, 1, 1));
        definitions.put("flareDimension", new MultiblockDefinition(1, 1, 11, 0, 1, 1));
        definitions.put("drillDimension", new MultiblockDefinition(1, 1, 3, 0, 1, 1));
        definitions.put("assemblerDimensionNorth", new MultiblockDefinition(2, 1, 1, 0, 1, 2));
        definitions.put("assemblerDimensionEast", new MultiblockDefinition(2, 1, 1, 0, 2, 1));
        definitions.put("assemblerDimensionSouth", new MultiblockDefinition(1, 2, 1, 0, 2, 1));
        definitions.put("assemblerDimensionWest", new MultiblockDefinition(1, 2, 1, 0, 1, 2));
        definitions.put("chemplantDimensionNorth", new MultiblockDefinition(2, 1, 2, 0, 1, 2));
        definitions.put("chemplantDimensionEast", new MultiblockDefinition(2, 1, 2, 0, 2, 1));
        definitions.put("chemplantDimensionSouth", new MultiblockDefinition(1, 2, 2, 0, 2, 1));
        definitions.put("chemplantDimensionWest", new MultiblockDefinition(1, 2, 2, 0, 1, 2));
        definitions.put("fluidTankDimensionNS", new MultiblockDefinition(1, 1, 2, 0, 2, 2));
        definitions.put("fluidTankDimensionEW", new MultiblockDefinition(2, 2, 2, 0, 1, 1));
        definitions.put("refineryDimensions", new MultiblockDefinition(1, 1, 8, 0, 1, 1));
        definitions.put("pumpjackDimensionNorth", new MultiblockDefinition(1, 1, 4, 0, 6, 0));
        definitions.put("pumpjackDimensionEast", new MultiblockDefinition(0, 6, 4, 0, 1, 1));
        definitions.put("pumpjackDimensionSouth", new MultiblockDefinition(1, 1, 4, 0, 0, 6));
        definitions.put("pumpjackDimensionWest", new MultiblockDefinition(6, 0, 4, 0, 1, 1));
        definitions.put("turbofanDimensionNorth", new MultiblockDefinition(1, 1, 2, 0, 3, 3));
        definitions.put("turbofanDimensionEast", new MultiblockDefinition(3, 3, 2, 0, 1, 1));
        definitions.put("turbofanDimensionSouth", new MultiblockDefinition(1, 1, 2, 0, 3, 3));
        definitions.put("turbofanDimensionWest", new MultiblockDefinition(3, 3, 2, 0, 1, 1));
        definitions.put("AMSLimiterDimensionNorth", new MultiblockDefinition(0, 0, 5, 0, 2, 2));
        definitions.put("AMSLimiterDimensionEast", new MultiblockDefinition(2, 2, 5, 0, 0, 0));
        definitions.put("AMSLimiterDimensionSouth", new MultiblockDefinition(0, 0, 5, 0, 2, 2));
        definitions.put("AMSLimiterDimensionWest", new MultiblockDefinition(2, 2, 5, 0, 0, 0));
        definitions.put("AMSEmitterDimension", new MultiblockDefinition(2, 2, 5, 0, 2, 2));
        definitions.put("AMSBaseDimension", new MultiblockDefinition(1, 1, 1, 0, 1, 1));
        definitions.put("radGenDimensionNorth", new MultiblockDefinition(4, 1, 2, 0, 1, 1));
        definitions.put("radGenDimensionEast", new MultiblockDefinition(1, 1, 2, 0, 4, 1));
        definitions.put("radGenDimensionSouth", new MultiblockDefinition(1, 4, 2, 0, 1, 1));
        definitions.put("radGenDimensionWest", new MultiblockDefinition(1, 1, 2, 0, 1, 4));
        definitions.put("reactorSmallDimension", new MultiblockDefinition(0, 0, 2, 0, 0, 0));
        definitions.put("uf6Dimension", new MultiblockDefinition(0, 0, 1, 0, 0, 0));
    }

    public static MultiblockDefinition getDefinition(String name) {
        return definitions.get(name);
    }
}
