package com.hbm.render.entity.mob;

import com.hbm.entity.mob.EntityThermonuclearCat;
import com.hbm.lib.RefStrings;
import net.minecraft.client.renderer.entity.RenderOcelot;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderThermonuclearCat extends RenderOcelot {

	public static final IRenderFactory<EntityThermonuclearCat> FACTORY = man -> new RenderThermonuclearCat(man);
	
	public static ResourceLocation[] cat_tex;


    public RenderThermonuclearCat(RenderManager man) {
		super(man);
        cat_tex = new ResourceLocation[EntityThermonuclearCat.catCount];
        for (int i = 0; i < EntityThermonuclearCat.catCount; i++) {
            cat_tex[i] = new ResourceLocation(RefStrings.MODID, "textures/entity/cat/"+i+".png");
        }
	}
	
	@Override
    protected ResourceLocation getEntityTexture(EntityOcelot entity) {
        int skinIndex = entity.getTameSkin();
        if(skinIndex >= 0 && skinIndex < EntityThermonuclearCat.catCount) return cat_tex[skinIndex];
        return cat_tex[0];
    }
}
