package com.hbm.render.item;

import com.hbm.inventory.CrucibleRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemChemistryTemplate;
import com.hbm.items.machine.ItemCrucibleTemplate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class CrucibleTemplateRender extends TileEntityItemStackRenderer {

    public static final CrucibleTemplateRender INSTANCE = new CrucibleTemplateRender();
    public IBakedModel itemModel;
    public ItemCameraTransforms.TransformType type;
    @Override
    public void renderByItem(ItemStack stack) {
        if (stack.getItem() instanceof ItemCrucibleTemplate && type == ItemCameraTransforms.TransformType.GUI) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                GL11.glTranslated(0.5, 0.5, 0);
                ItemStack renderStack = CrucibleRecipes.indexMapping.get(stack.getItemDamage()).icon;
                Minecraft.getMinecraft().getRenderItem().renderItem(renderStack, Minecraft.getMinecraft().getRenderItem().getItemModelWithOverrides(renderStack, Minecraft.getMinecraft().world, Minecraft.getMinecraft().player));
            } else {
                GL11.glTranslated(0.5, 0.5, 0);
                Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
            }
        } else {
            Minecraft.getMinecraft().getRenderItem().renderItem(stack, itemModel);
        }
        super.renderByItem(stack);
    }
}
