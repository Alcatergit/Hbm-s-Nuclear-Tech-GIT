package com.hbm.render.icon;

import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * This is made for manual registering sprites because FUCK .JSON it takes too much time to parse it
 * Just makes TextureAtlasSprite's constructor public.. as it should have been, honestly
 * @author Th3_Sl1ze
 */
@SideOnly(Side.CLIENT)
public class TextureAtlasSpriteOpen extends TextureAtlasSprite {

    public TextureAtlasSpriteOpen(String iconName) {
        super(iconName);
    }
}
