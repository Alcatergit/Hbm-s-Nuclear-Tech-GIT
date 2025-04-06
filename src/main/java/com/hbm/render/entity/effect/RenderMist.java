package com.hbm.render.entity.effect;

import com.hbm.entity.effect.EntityMist;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import org.jetbrains.annotations.Nullable;


public class RenderMist extends Render<EntityMist> {

    public static final IRenderFactory<EntityMist> FACTORY = manager -> {return new RenderMist(manager);};
    protected RenderMist(RenderManager renderManager) {
        super(renderManager);
    }

    @Override
    public void doRender(EntityMist entity, double x, double y, double z, float f0, float f1) {
    }

    @Override
    protected @Nullable ResourceLocation getEntityTexture(EntityMist entity) {
        return null;
    }

}

