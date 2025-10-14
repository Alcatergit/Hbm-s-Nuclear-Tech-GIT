package com.hbm.items;


import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Used in items that require model baking;
 * Will automatically bake once correct methods are supplied
 */
public interface IDynamicModels {
    List<IDynamicModels> INSTANCES = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    static void bakeModels(ModelBakeEvent event) {
            INSTANCES.forEach(blockMeta -> blockMeta.bakeModel(event));
    }


    void bakeModel(ModelBakeEvent event);


    @SideOnly(Side.CLIENT)
    static void registerModels(){
        INSTANCES.forEach(IDynamicModels::registerModel);
    }

    void registerModel();

    @SideOnly(Side.CLIENT)
    static void registerSprites(TextureMap map){
        INSTANCES.forEach(dynamicSpirte -> dynamicSpirte.registerSprite(map));
    }
    @SideOnly(Side.CLIENT)
    void registerSprite(TextureMap map);

}
