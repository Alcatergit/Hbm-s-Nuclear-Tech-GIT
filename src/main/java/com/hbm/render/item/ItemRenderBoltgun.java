package com.hbm.render.item;

import com.hbm.main.ResourceManager;
import com.hbm.render.anim.HbmAnimations;
import com.hbm.render.util.ViewModelPositonDebugger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import org.lwjgl.opengl.GL11;

public class ItemRenderBoltgun extends TEISRBase {

    ViewModelPositonDebugger offsets = new ViewModelPositonDebugger()
            .get(TransformType.GUI)
            .setScale(0.11f).setPosition(-4.15, 3.30, -3.35).setRotation(0, 135, -90)
            .getHelper()
            .get(TransformType.FIRST_PERSON_RIGHT_HAND)
            .setPosition(-6.75, 0.55, 2.25).setRotation(80, 5, -180)
            .getHelper()
            .get(TransformType.FIRST_PERSON_LEFT_HAND)
            .setPosition(-10.5, -1, 0).setRotation(180, 165, -180)
            .getHelper()
            .get(TransformType.THIRD_PERSON_RIGHT_HAND)
            .setScale(0.1f).setPosition(-4.25, 5, -5.5).setRotation(-5, 90, 0)
            .getHelper()
            .get(TransformType.THIRD_PERSON_LEFT_HAND)
            .setScale(1.03f).setPosition(-0.75, -0.25, 0.25).setRotation(5, 0, 0)
            .getHelper()
            .get(TransformType.GROUND)
            .setPosition(-10, 10, -10).setRotation(0, 0, 0).setScale(0.05f)
            .getHelper();
    //Norwood: This is great and all but eulerian angles' order of rotation is important. You should probably use quaternions instead but I'm too lazy to do that.
    //For now, just queue multiple rotations in the correct order. //TODO: Make angles use quaternions
    ViewModelPositonDebugger.offset corrections = new ViewModelPositonDebugger.offset(offsets)
            .setRotation(0, 5, 0);


    @Override
    public void renderByItem(ItemStack itemStackIn) {

        GL11.glPushMatrix();

        EntityPlayer player = Minecraft.getMinecraft().player;

        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glShadeModel(GL11.GL_SMOOTH);
        Minecraft.getMinecraft().renderEngine.bindTexture(ResourceManager.boltgun_tex);


        switch (type) {

            case FIRST_PERSON_LEFT_HAND:
            case FIRST_PERSON_RIGHT_HAND:

                offsets.apply(type);
                offsets.applyCustomOffset(corrections);

                GL11.glPushMatrix();
                double[] anim = HbmAnimations.getRelevantTransformation("RECOIL", type == TransformType.FIRST_PERSON_LEFT_HAND ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND);
                GL11.glTranslated(0, 0, -anim[0]);
                if (anim[0] != 0) player.isSwingInProgress = false;
                ResourceManager.boltgun.renderPart("Barrel");
                GL11.glPopMatrix();

                break;
            default:
                offsets.apply(type);
                break;
        }

        ResourceManager.boltgun.renderPart("Gun");
        if (type != type.FIRST_PERSON_RIGHT_HAND && type != type.FIRST_PERSON_LEFT_HAND) {
            ResourceManager.boltgun.renderPart("Barrel");
        }
        GL11.glShadeModel(GL11.GL_FLAT);

        GL11.glPopMatrix();
    }
}
