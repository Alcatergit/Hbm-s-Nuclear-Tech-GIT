package com.hbm.render.amlfrom1710;

import net.minecraft.util.ResourceLocation;

public interface IModelCustomLoader {
    /**
     * Get the main type name for this loader
     * @return the type name
     */
    String getType();
    /**
     * Get resource suffixes this model loader recognizes
     * @return a list of suffixes
     */
    String[] getSuffixes();
    /**
     * Load a model instance from the supplied path
     * @param resource The ResourceLocation of the model
     * @return A model instance
     * @throws ModelFormatException if the model format is not correct
     */
    IModelCustom loadInstance(ResourceLocation resource) throws ModelFormatException;
}
