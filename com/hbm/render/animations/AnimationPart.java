package com.hbm.render.animations;

public class AnimationPart {

	private long length;

	private double rotationX = 0.0D;
	private double rotationY = 0.0D;
	private double rotationZ = 0.0D;
	private double translationX = 0.0D;
	private double translationY = 0.0D;
	private double translationZ = 0.0D;
	private double scaleX = 0.0D;
	private double scaleY = 0.0D;
	private double scaleZ = 0.0D;

	private boolean inverted;

	private AnimationType type = AnimationType.NORMAL;

	public AnimationPart(long length) {
		this.length = length;
	}

	public AnimationPart rotations(double endRotX, double endRotY, double endRotZ) {
		this.rotationX = endRotX;
		this.rotationY = endRotY;
		this.rotationZ = endRotZ;
		return this;
	}

	public AnimationPart translations(double endTransX, double endTransY, double endTransZ) {
		this.translationX = endTransX;
		this.translationY = endTransY;
		this.translationZ = endTransZ;
		return this;
	}

	public AnimationPart scales(double endScaleX, double endScaleY, double endScaleZ) {
		this.scaleX = endScaleX;
		this.scaleY = endScaleY;
		this.scaleZ = endScaleZ;
		return this;
	}

	public AnimationPart type(AnimationType type) {
		this.type = type;
		return this;
	}

	public AnimationPart setInverted() {
		inverted = true;
		return this;
	}

	public long getLength() {
		return length;
	}

	public double getRotationX(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return rotationX * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationX;
			} else {
				return rotationX * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return rotationX * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationX;
			} else {
				return rotationX * (1 - difference / length);
			}
		}
	}

	public double getRotationY(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return rotationY * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationY;
			} else {
				return rotationY * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return rotationY * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationY;
			} else {
				return rotationY * (1 - difference / length);
			}
		}
	}

	public double getRotationZ(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return rotationZ * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationZ;
			} else {
				return rotationZ * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return rotationZ * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return rotationZ;
			} else {
				return rotationZ * (1 - difference / length);
			}
		}
	}

	public double getTranslationX(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return translationX * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationX;
			} else {
				return translationX * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return translationX * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationX;
			} else {
				return translationX * (1 - difference / length);
			}
		}
	}

	public double getTranslationY(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return translationY * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationY;
			} else {
				return translationY * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return translationY * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationY;
			} else {
				return translationY * (difference / length);
			}
		}
	}

	public double getTranslationZ(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return translationZ * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationZ;
			} else {
				return translationZ * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return translationZ * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return translationZ;
			} else {
				return translationZ * (1 - difference / length);
			}
		}
	}

	public double getScaleX(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return scaleX * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleX;
			} else {
				return scaleX * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return scaleX * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleX;
			} else {
				return scaleX * (1 - difference / length);
			}
		}
	}

	public double getScaleY(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return scaleY * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleY;
			} else {
				return scaleY * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return scaleY * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleY;
			} else {
				return scaleY * (1 - difference / length);
			}
		}
	}

	public double getScaleZ(long difference) {
		if (!inverted) {
			if (type == AnimationType.INOUT) {
				return scaleZ * (1 - Math.abs((((difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleZ;
			} else {
				return scaleZ * (difference / length);
			}
		} else {
			if (type == AnimationType.INOUT) {
				return scaleZ * (1 - Math.abs((((1 - difference / length) * 2) - 1)));
			} else if (type == AnimationType.STATIC) {
				return scaleZ;
			} else {
				return scaleZ * (1 - difference / length);
			}
		}
	}

	public enum AnimationType {
		NORMAL, STATIC, INOUT, GAUSSIAN;
	}
}
