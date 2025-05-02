package com.hbm.render.amlfrom1710;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Common interface for advanced model loading from files, based on file suffix
 * Model support can be queried through the {@link #getSupportedSuffixes()} method.
 * Instances can be created by calling {@link #loadModel(String)} with a class-loadable-path
 *
 * @author cpw
 *
 */
@SideOnly(Side.CLIENT)
public class AdvancedModelLoader {
    private static Map<String, IModelCustomLoader> instances = Maps.newHashMap();

    /**
     * Register a new model handler
     * @param modelHandler The model handler to register
     */
    public static void registerModelHandler(IModelCustomLoader modelHandler)
    {
        for (String suffix : modelHandler.getSuffixes())
        {
            instances.put(suffix, modelHandler);
        }
    }

    /**
     * Load the model from the supplied classpath resolvable resource name
     * @param resource The resource name
     * @return A model
     * @throws IllegalArgumentException if the resource name cannot be understood
     * @throws ModelFormatException if the underlying model handler cannot parse the model format
     */
    @SuppressWarnings("deprecation")
	public static IModelCustom loadModel(ResourceLocation resource) throws IllegalArgumentException, ModelFormatException
    {
        String name = resource.getPath();
        int i = name.lastIndexOf('.');
        if (i == -1)
        {
            FMLLog.severe("The resource name %s is not valid", resource);
            throw new IllegalArgumentException("The resource name is not valid");
        }
        String suffix = name.substring(i+1);
        IModelCustomLoader loader = instances.get(suffix);
        if (loader == null)
        {
            FMLLog.severe("The resource name %s is not supported", resource);
            throw new IllegalArgumentException("The resource name is not supported");
        }

        return loader.loadInstance(resource);
    }

    public static Collection<String> getSupportedSuffixes()
    {
        return instances.keySet();
    }


    static {
        registerModelHandler(new ObjModelLoader());
    }
}