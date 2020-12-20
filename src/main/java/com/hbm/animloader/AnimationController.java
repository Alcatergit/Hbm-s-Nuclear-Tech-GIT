package com.hbm.animloader;

public class AnimationController {

	protected AnimationWrapper activeAnim = AnimationWrapper.EMPTY;

	public void setAnim(AnimationWrapper w) {
		activeAnim = w;
	}

	public void stopAnim() {
		activeAnim = AnimationWrapper.EMPTY;
	}

	public AnimationWrapper getAnim() {
		return activeAnim;
	}

}