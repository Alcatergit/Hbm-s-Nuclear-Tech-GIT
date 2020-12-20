package com.hbm.particle_instanced;

import java.nio.ByteBuffer;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleInstanced extends Particle {

	protected ParticleInstanced(World worldIn, double posXIn, double posYIn, double posZIn) {
		super(worldIn, posXIn, posYIn, posZIn);
	}
	
	public void addDataToBuffer(ByteBuffer buf, float partialTicks){
		float x = (float) ((this.prevPosX + (this.posX - this.prevPosX) * (double) partialTicks - interpPosX));
		float y = (float) ((this.prevPosY + (this.posY - this.prevPosY) * (double) partialTicks - interpPosY));
		float z = (float) ((this.prevPosZ + (this.posZ - this.prevPosZ) * (double) partialTicks - interpPosZ));
		buf.putFloat(x);
		buf.putFloat(y);
		buf.putFloat(z);
		buf.putFloat(this.particleScale);
		buf.putFloat(this.particleTexture.getMinU());
		buf.putFloat(this.particleTexture.getMinV());
		buf.putFloat(this.particleTexture.getMaxU()-this.particleTexture.getMinU());
		buf.putFloat(this.particleTexture.getMaxV()-this.particleTexture.getMinV());
		byte r = (byte) (this.particleRed*255);
		byte g = (byte) (this.particleGreen*255);
		byte b = (byte) (this.particleBlue*255);
		byte a = (byte) (this.particleAlpha*255);
		buf.put(r);
		buf.put(g);
		buf.put(b);
		buf.put(a);
		int i = this.getBrightnessForRender(partialTicks);
		int j = i >> 16 & 65535;
		int k = i & 65535;
		j = 240;
		k = 240;
		buf.put((byte) j);
		buf.put((byte) k);
	}
	
	public int getFaceCount(){
		return 1;
	}

}
