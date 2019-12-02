package com.hbm.render.animations;

public class AnimationBuilder {

	//Yeah I don't know if this was necessary, but 'AnmationBuilder' sounds cool, so I'll go with it.
	private int index = -1;
	public AnimationPart[] parts;
	
	private AnimationBuilder(int length){
		parts = new AnimationPart[length];
	}
	
	public Animation build(){
		return new Animation(parts);
	}
	
	public static AnimationBuilder createNewAnimation(int length){
		return new AnimationBuilder(length);
	}
	
	public AnimationBuilder addPart(long length){
		index ++;
		if(index > parts.length)
			throw new IllegalStateException("Too many parts for this animation!");
		parts[index] = new AnimationPart(length);
		
		return this;
	}
	
	public AnimationBuilder translations(double x, double y, double z){
		if(parts[index] == null)
			throw new IllegalStateException("Must add part before setting translations!");
		parts[index].translations(x, y, z);
		return this;
	}
	public AnimationBuilder rotations(double x, double y, double z){
		if(parts[index] == null)
			throw new IllegalStateException("Must add part before setting rotations!");
		parts[index].rotations(x, y, z);
		return this;
	}
	public AnimationBuilder scales(double x, double y, double z){
		if(parts[index] == null)
			throw new IllegalStateException("Must add part before setting scales!");
		parts[index].scales(x, y, z);
		return this;
	}
	public AnimationBuilder type(AnimationPart.AnimationType type){
		if(parts[index] == null)
			throw new IllegalStateException("Must add part before setting type!");
		parts[index].type(type);
		return this;
	}
	public AnimationBuilder setInverse(){
		if(parts[index] == null)
			throw new IllegalStateException("Must add part before setting inverse!");
		parts[index].setInverted();
		return this;
	}
}
