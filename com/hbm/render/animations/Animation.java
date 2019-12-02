package com.hbm.render.animations;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.GlStateManager;

//Drillgon animation API 1.0
//Sigh... this is going to need a TON of changes to make it more dynamic

public class Animation {

	private long prevSystemTime = 0;
	private long prevAnimationPartTime = 0;
	
	private double rotationX = 0.0D;
	private double rotationY = 0.0D;
	private double rotationZ = 0.0D;
	private double translationX = 0.0D;
	private double translationY = 0.0D;
	private double translationZ = 0.0D;
	private double scaleX = 0.0D;
	private double scaleY = 0.0D;
	private double scaleZ = 0.0D;
	
	private AnimationPart[] sections;
	private int currentPart = 0;
	private long totalLength = 0;
	
	private boolean isAnimating = false;
	
	public Animation(AnimationPart[] anis){
		
		sections = anis;
		for(AnimationPart part : sections){
			totalLength += part.getLength();
		}
	}
	
	public void start(){
		
		if(sections.length == 0){
			new IllegalStateException("No animations to start!").printStackTrace();
			return;
		}
			
		if(isAnimating){
			new IllegalStateException("Already animating!").printStackTrace();
			return;
		}
		prevSystemTime = System.currentTimeMillis();
		isAnimating = true;
	}
	
	public void end(){
		if(!isAnimating){
			new IllegalStateException("Not animating!").printStackTrace();
			return;
		}
		rotationX = 0.0D;
		rotationY = 0.0D;
		rotationZ = 0.0D;
		translationX = 0.0D;
		translationY = 0.0D;
		translationZ = 0.0D;
		scaleX = 0.0D;
		scaleY = 0.0D;
		scaleZ = 0.0D;
		isAnimating = false;
	}
	
	public void apply(){
		if(!isAnimating)
			return;
		if(!updateValues())
			return;
		GlStateManager.scale(scaleX, scaleY, scaleZ);
		GL11.glRotated(rotationX, 1, 0, 0);
		GL11.glRotated(rotationY, 0, 1, 0);
		GL11.glRotated(rotationZ, 0, 0, 1);
		GlStateManager.translate(translationX, translationY, translationZ);
	}
	
	private boolean updateValues(){
		long difference = System.currentTimeMillis() - prevSystemTime;
		long dif = System.currentTimeMillis() - prevAnimationPartTime;
		if(difference > totalLength){
			end();
			return false;
		}
		
		
			
		
		if(currentPart > sections.length-1){
			end();
			return false;
		}
		if(dif > sections[currentPart].getLength()){
			currentPart += 1;
			if(currentPart > sections.length-1){
				end();
				return false;
			}
			prevAnimationPartTime = System.currentTimeMillis();
		}
		AnimationPart part = sections[currentPart];
		
		rotationX = part.getRotationX(dif);
		rotationY = part.getRotationY(dif);
		rotationZ = part.getRotationZ(dif);
		
		translationX = part.getTranslationX(dif);
		translationY = part.getTranslationY(dif);
		translationZ = part.getTranslationZ(dif);
		
		scaleX = part.getScaleX(dif);
		scaleY = part.getScaleY(dif);
		scaleZ = part.getScaleZ(dif);
		
		return true;
	}
}
