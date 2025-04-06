package com.hbm.items;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in items that require custom textures that cannot be assigned during compile time
 */
public interface IDynamicSprites {
    public static List<IDynamicSprites> INSTANCES = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    static void registerSprites(TextureMap map){
        INSTANCES.forEach(dynamicSpirte -> dynamicSpirte.registerSprite(map));
    }
    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map);
}
