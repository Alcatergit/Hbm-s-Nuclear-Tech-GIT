package com.hbm.animloader;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import com.hbm.util.BobMathUtil;

import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.util.math.MathHelper;

public class AnimatedModel {

	public static FloatBuffer auxGLMatrix = GLAllocation.createDirectFloatBuffer(16);

	public AnimationController controller;

	public String name = "";

	public float[] transform;

	boolean hasGeometry = true;
	boolean hasTransform = false;

	public String geo_name = "";
	public AnimatedModel parent;
	public List<AnimatedModel> children = new ArrayList<AnimatedModel>();
	int callList;

	public AnimatedModel() {
	}

	public void renderAnimated(long sysTime) {
		if(controller.activeAnim == AnimationWrapper.EMPTY) {
			render();
			return;
		}

		AnimationWrapper activeAnim = controller.activeAnim;
		int numKeyFrames = activeAnim.anim.numKeyFrames;
		int diff = (int) (sysTime - activeAnim.startTime);
		diff *= activeAnim.speedScale;
		if(diff > activeAnim.anim.length) {
			int diff2 = diff % activeAnim.anim.length;
			switch(activeAnim.endResult.type) {
			case END:
				controller.activeAnim = AnimationWrapper.EMPTY;
				render();
				return;
			case REPEAT:
				activeAnim.startTime = sysTime - diff2;
				break;
			case REPEAT_REVERSE:
				activeAnim.startTime = sysTime - diff2;
				activeAnim.reverse = !activeAnim.reverse;
				break;
			case START_NEW:
				activeAnim.cloneStats(activeAnim.endResult.next);
				activeAnim.startTime = sysTime - diff2;
				break;
			case STAY:
				activeAnim.startTime = sysTime - activeAnim.anim.length;
				break;
			}
		}
		diff = (int) (sysTime - activeAnim.startTime);
		if(activeAnim.reverse)
			diff = activeAnim.anim.length - diff;
		diff *= activeAnim.speedScale;
		float remappedTime = MathHelper.clamp(BobMathUtil.remap(diff, 0, activeAnim.anim.length, 0, numKeyFrames - 1), 0, numKeyFrames - 1);
		int index = (int) remappedTime;
		int first = index;
		int next;
		if(index < numKeyFrames - 1) {
			next = index + 1;
		} else {
			next = first;
		}

		renderWithIndex((float) fract(remappedTime), first, next);

	}

	protected void renderWithIndex(float inter, int firstIndex, int nextIndex) {
		GL11.glPushMatrix();
		boolean hidden = false;
		if(hasTransform) {
			Transform[] transforms = controller.activeAnim.anim.objectTransforms.get(name);
			if(transforms != null) {
				hidden = transforms[firstIndex].hidden;
				transforms[firstIndex].interpolateAndApply(transforms[nextIndex], inter);
			} else {
				auxGLMatrix.put(transform);
				auxGLMatrix.rewind();
				GL11.glMultMatrix(auxGLMatrix);
			}
		}
		if(hasGeometry && !hidden) {
			GL11.glCallList(callList);
		}

		for(AnimatedModel m : children) {
			m.renderWithIndex(inter, firstIndex, nextIndex);
		}
		GL11.glPopMatrix();
	}

	public void render() {
		GL11.glPushMatrix();
		if(hasTransform) {
			auxGLMatrix.put(transform);
			auxGLMatrix.rewind();
			GL11.glMultMatrix(auxGLMatrix);
		}
		if(hasGeometry) {
			GL11.glCallList(callList);
		}
		for(AnimatedModel m : children) {
			m.render();
		}
		GL11.glPopMatrix();
	}

	private static float fract(float number) {
		return (float) (number - Math.floor(number));
	}
}
