package com.leafia.dev.items;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.HashMap;
import java.util.Map;

/**
 * <h3>HOW TO SET UP:
 * Create a new static instance of LeafiaGripOffsetHelper like so:
 * <pre>{@code static final offsets = new LeafiaOffsetHelper();}</pre>
 * Put this in render method:
 * <pre>{@code offsets.apply(transformType);}</pre>
 * That's it!
 * <br><br>
 * <h3>HOW TO MOVE OFFSETS IN-GAME:</h3>
 * Enable <b>debug</b> static variable in this class. That's it.
 * <br>Controls are arrows, IJKL, ctrl+up/down, ctrl+I/K, and space.
 * <br>Hold ALT for smaller increments.
 * <br>Keep in mind that offsets are not automatically saved, and you have to write scale, position and rotations manually via code.
 * <br>FIRST_PERSON_LEFT_HAND is affected by FIRST_PERSON_RIGHT_HAND, and THIRD_PERSON_LEFT_HAND is affected by THIRD_PERSON_RIGHT_HAND.
 * <br><br>
 * <h3>HOW TO SET OFFSETS VIA CODE</h3>
 * Just take this for an example (ZIRNOX item render):
 * <pre>{@code static final LeafiaGripOffsetHelper offsets = new LeafiaGripOffsetHelper()
 * 			.get(TransformType.FIRST_PERSON_RIGHT_HAND)
 * 			.setScale(0.25).setPosition(0,10,-10.25).setRotation(25,0,-95).getHelper()
 *
 * 			.get(TransformType.FIRST_PERSON_LEFT_HAND)
 * 			.setPosition(-4.5,0,9.75).setRotation(0,-60,0).getHelper()
 *
 * 			.get(TransformType.GUI)
 * 			.setScale(2.2).setPosition(-2.55,-0.85,0).setRotation(65,0,-25).getHelper()
 *
 * 			.get(TransformType.GROUND)
 * 			.setScale(0.25).setPosition(5.25,3.25,-5.00).getHelper()
 *
 * 			.get(TransformType.FIXED)
 * 			.setScale(0.4).setPosition(0.75,2.75,-3.15).setRotation(0,0,-90).getHelper()
 *
 * 			.get(TransformType.THIRD_PERSON_RIGHT_HAND)
 * 			.setScale(0.25).setPosition(5,1.5,-3.75).getHelper();}</pre>
 */
public class LeafiaGripOffsetHelper {
    static boolean debug = false;
    static boolean blockInput = false;
    public Map<TransformType,LeafiaGripOffset> offsetMap;
    protected int debugIndex = 0;
    public static class LeafiaGripOffset {
        public double scale;
        public Vec3d rotation;
        public Vec3d position;
        protected LeafiaGripOffsetHelper helper;
        public LeafiaGripOffset(LeafiaGripOffsetHelper helper) {
            scale = 1;
            rotation = new Vec3d(0,0,0);
            position = new Vec3d(0,0,0);
            this.helper = helper;
        }
        public LeafiaGripOffset setPosition(double x,double y,double z) {
            this.position = new Vec3d(x,y,z);
            return this;
        }
        public LeafiaGripOffset setRotation(double x,double y,double z) {
            this.rotation = new Vec3d(x,y,z);
            return this;
        }
        public LeafiaGripOffset setScale(double scale) {
            this.scale = scale;
            return this;
        }
        public LeafiaGripOffset copySettings(TransformType type) {
            LeafiaGripOffset offset = this.helper.get(type);
            this.scale = offset.scale;
            this.position = offset.position;
            this.rotation = offset.rotation;
            return this;
        }
        public LeafiaGripOffsetHelper getHelper() {
            return this.helper;
        }
    }
    public LeafiaGripOffsetHelper() {
        offsetMap = new HashMap<>();
        debugIndex = 0;
        for (TransformType transform : TransformType.values()) {
            LeafiaGripOffset offset = new LeafiaGripOffset(this);
            offset.scale = 0.25;
            offsetMap.put(transform,offset);
            if (transform == TransformType.NONE)
                offset.setScale(1);
            if (transform == TransformType.FIRST_PERSON_LEFT_HAND)
                offset.setScale(1);
            if (transform == TransformType.THIRD_PERSON_LEFT_HAND)
                offset.setScale(1);
            if (transform == TransformType.FIXED)
                offset.setScale(1);
        }
    }
    public LeafiaGripOffset get(TransformType transform) {
        return offsetMap.get(transform);
    }
    public void applyCustomOffset(LeafiaGripOffset offset) {
        GL11.glScaled(offset.scale,offset.scale,offset.scale);
        GL11.glTranslated(-offset.position.x,offset.position.y,offset.position.z);
        GL11.glRotated(-offset.rotation.y,0,1,0);
        GL11.glRotated(offset.rotation.x,1,0,0);
        GL11.glRotated(-offset.rotation.z,0,0,1);
    }
    protected void render(TransformType type) {
        LeafiaGripOffset offset = this.offsetMap.get(type);
        applyCustomOffset(offset);
    }
    public void apply(TransformType type) {
        GL11.glRotatef(-90f,0,1,0);
        switch(type) {
            case FIRST_PERSON_LEFT_HAND: render(TransformType.FIRST_PERSON_RIGHT_HAND); break;
            case THIRD_PERSON_LEFT_HAND: render(TransformType.THIRD_PERSON_RIGHT_HAND); break;
            //case FIXED: render(TransformType.GUI); break;
        }
        render(type);
        render(TransformType.NONE);
        if (debug)
            tickDebug();
    }
    protected void tickDebug() {
        //if (!debug) return;
        boolean[] inputs = new boolean[]{Keyboard.isKeyDown(Keyboard.KEY_UP),Keyboard.isKeyDown(Keyboard.KEY_LEFT),Keyboard.isKeyDown(Keyboard.KEY_DOWN),Keyboard.isKeyDown(Keyboard.KEY_RIGHT),Keyboard.isKeyDown(Keyboard.KEY_I),Keyboard.isKeyDown(Keyboard.KEY_J),Keyboard.isKeyDown(Keyboard.KEY_K),Keyboard.isKeyDown(Keyboard.KEY_L),Keyboard.isKeyDown(Keyboard.KEY_SPACE),Keyboard.isKeyDown(Keyboard.KEY_RSHIFT),Keyboard.isKeyDown(Keyboard.KEY_RCONTROL),Keyboard.isKeyDown(Keyboard.KEY_ADD),Keyboard.isKeyDown(Keyboard.KEY_SUBTRACT)};
        boolean doUnblock = true;
        for (boolean input : inputs) {
            if (input) {
                doUnblock = false;
                break;
            }
        }
        if (doUnblock) blockInput = false;
        if (!blockInput) {
            if (!doUnblock) blockInput = true;
            if (inputs[9])
                debugIndex++;
            if (inputs[10])
                debugIndex--;
            debugIndex = Math.floorMod(debugIndex,TransformType.values().length);
            TransformType curTransform = TransformType.values()[debugIndex];
            LeafiaGripOffset offset = this.offsetMap.get(curTransform);
            double increment = 0.25;
            double incrementAngle = 5;
            double incrementScale = 0.05;
            boolean damn = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
            if (Keyboard.isKeyDown(Keyboard.KEY_TAB)) {
                increment=0.05;
                incrementAngle=1;
                incrementScale=0.01;
            }
            if (inputs[8] || inputs[9] || inputs[10]) {
                EntityPlayer player = Minecraft.getMinecraft().player;
                Minecraft.getMinecraft().ingameGUI.getChatGUI().clearChatMessages(false);
                player.sendMessage(new TextComponentString("-- Current Grip --").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
                player.sendMessage(new TextComponentString(String.format(" Scale: %01.2f",offset.scale)));
                player.sendMessage(new TextComponentString(String.format(" Position: %01.2f, %01.2f, %01.2f",offset.position.x,offset.position.y,offset.position.z)));
                player.sendMessage(new TextComponentString(String.format(" Rotation: %01.0f, %01.0f, %01.0f",offset.rotation.x,offset.rotation.y,offset.rotation.z)));
                player.sendMessage(new TextComponentString(" Type: "+(curTransform == TransformType.NONE ? "CUSTOM" : curTransform.name())).setStyle(new Style().setColor(TextFormatting.GRAY)));
            }
            if (inputs[11])
                offset.scale+=incrementScale;
            if (inputs[12])
                offset.scale-=incrementScale;
            {
                if (inputs[0])
                    offset.position = offset.position.add(0,damn ? 0 : increment,damn ? increment : 0);
                if (inputs[2])
                    offset.position = offset.position.add(0,damn ? 0 : -increment,damn ? -increment : 0);
                if (inputs[1])
                    offset.position = offset.position.add(-increment,0,0);
                if (inputs[3])
                    offset.position = offset.position.add(increment,0,0);
            }
            {
                if (inputs[4])
                    offset.rotation = offset.rotation.add(incrementAngle,0,0);
                if (inputs[6])
                    offset.rotation = offset.rotation.add(-incrementAngle,0,0);
                if (inputs[5])
                    offset.rotation = offset.rotation.add(0,damn ? 0 : -incrementAngle,damn ? incrementAngle : 0);
                if (inputs[7])
                    offset.rotation = offset.rotation.add(0,damn ? 0 : incrementAngle,damn ? -incrementAngle : 0);
            }
        }
    }
}
