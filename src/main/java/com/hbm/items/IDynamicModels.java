package com.hbm.items;

import com.hbm.blocks.generic.BlockSellafieldSlaked;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.init.Blocks;
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
    public static List<IDynamicModels> INSTANCES = new ArrayList<>();

    @SideOnly(Side.CLIENT)
    public static void bakeModels(ModelBakeEvent event) {
            INSTANCES.forEach(blockMeta -> blockMeta.bakeModel(event));
    }


    public void bakeModel(ModelBakeEvent event);


    @SideOnly(Side.CLIENT)
    public static void registerModels(){
        INSTANCES.forEach(IDynamicModels::registerModel);
    }

    public void registerModel();

    @SideOnly(Side.CLIENT)
    static void registerSprites(TextureMap map){
        INSTANCES.forEach(dynamicSpirte -> dynamicSpirte.registerSprite(map));
    }
    @SideOnly(Side.CLIENT)
    public void registerSprite(TextureMap map);

}
