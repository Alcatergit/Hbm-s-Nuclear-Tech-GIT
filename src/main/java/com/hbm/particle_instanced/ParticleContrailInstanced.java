package com.hbm.particle_instanced;

import java.nio.ByteBuffer;
import java.util.Random;

import com.hbm.main.ModEventHandlerClient;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleContrailInstanced extends ParticleInstanced {

	private int age = 0;
	private int maxAge;
	private float scale;
	private float[] vals = new float[4*6];

	
	public ParticleContrailInstanced(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
		this.particleTexture = ModEventHandlerClient.contrail;
		maxAge = 400 + rand.nextInt(50);

		this.particleRed = this.particleGreen = this.particleBlue = 0;
		this.scale = 4F;
		initVals();
	}
	
	public ParticleContrailInstanced(World p_i1218_1_, double p_i1218_2_, double p_i1218_4_, double p_i1218_6_, float red, float green, float blue, float scale) {
		super(p_i1218_1_, p_i1218_2_, p_i1218_4_, p_i1218_6_);
		this.particleTexture = ModEventHandlerClient.contrail;
		maxAge = 600 + rand.nextInt(50);

		this.particleRed = red;
		this.particleGreen = green;
		this.particleBlue = blue;

		this.scale = scale*4;
		initVals();
	}
	
	public void initVals(){
		Random urandom = new Random(this.hashCode());
		for(int i = 0; i < 6; i ++){
			//The three random values that are added to the position when rendering
			vals[i*4] = (float) (urandom.nextGaussian()*0.5F);
			vals[i*4+1] = (float) (urandom.nextGaussian()*0.5F);
			vals[i*4+2] = (float) (urandom.nextGaussian()*0.5F);
		}
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		particleAlpha = 1F - (float)Math.pow((float) this.age / (float) this.maxAge, 2);

		this.age++;

		if (this.age == this.maxAge) {
			this.setExpired();
			;
		}
	}
	
	@Override
	public void addDataToBuffer(ByteBuffer buf, float partialTicks) {
		float x = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX));
		float y = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY));
		float z = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ));
		this.particleScale = (1-particleAlpha)*3F + 1F + 0.5F * this.scale;
		float mod = particleAlpha * 0.1F;
		for(int ii = 0; ii < 6; ii++){
			
			buf.putFloat(x+vals[ii*4]);
			buf.putFloat(y+vals[ii*4+1]);
			buf.putFloat(z+vals[ii*4+2]);
			
			buf.putFloat(this.particleScale);
			
			buf.putFloat(this.particleTexture.getMinU());
			buf.putFloat(this.particleTexture.getMinV());
			buf.putFloat(this.particleTexture.getMaxU()-this.particleTexture.getMinU());
			buf.putFloat(this.particleTexture.getMaxV()-this.particleTexture.getMinV());
			
			byte r = (byte) (MathHelper.clamp(this.particleRed+mod, 0, 1)*255);
			byte g = (byte) (MathHelper.clamp(this.particleGreen+mod, 0, 1)*255);
			byte b = (byte) (MathHelper.clamp(this.particleBlue+mod, 0, 1)*255);
			byte a = (byte) (this.particleAlpha*255);
			buf.put(r);
			buf.put(g);
			buf.put(b);
			buf.put(a);
			
			//int i = this.getBrightnessForRender(partialTicks);
			//int j = i >> 16 & 65535;
			//int k = i & 65535;
			//Bruh I have no clue how these lightmap coords work. They don't seem to be like regular uvs.
			buf.put((byte) 240);
			buf.put((byte) 240);
		}
	}
	
	@Override
	public int getFaceCount() {
		return 6;
	}
	
	@Override
	public int getBrightnessForRender(float p_189214_1_) {
		return 240;
	}

}
