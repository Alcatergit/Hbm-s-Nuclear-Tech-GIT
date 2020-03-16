package com.hbm.particle;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class ParticleDSmokeFX extends Particle {

	public static TextureAtlasSprite[] sprites = new TextureAtlasSprite[8];

	public ParticleDSmokeFX(World p_i1225_1_, double p_i1225_2_, double p_i1225_4_, double p_i1225_6_, double p_i1225_8_, double p_i1225_10_, double p_i1225_12_) {
		this(p_i1225_1_, p_i1225_2_, p_i1225_4_, p_i1225_6_, p_i1225_8_, p_i1225_10_, p_i1225_12_, 1.0F);
	}

	public ParticleDSmokeFX(World p_i1226_1_, double p_i1226_2_, double p_i1226_4_, double p_i1226_6_, double p_i1226_8_, double p_i1226_10_, double p_i1226_12_, float p_i1226_14_) {
		super(p_i1226_1_, p_i1226_2_, p_i1226_4_, p_i1226_6_, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += p_i1226_8_;
		this.motionY += p_i1226_10_;
		this.motionZ += p_i1226_12_;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0f - ((float) (Math.random() * 0.30000001192092896D));
		this.particleScale *= 10.75F;
		this.particleScale *= p_i1226_14_;
		// this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
		// this.particleMaxAge = (int)((float)this.particleMaxAge *
		// p_i1226_14_);
		this.canCollide = true;
	}

	public void setMotion(double x, double y, double z) {
		this.motionX = x;
		this.motionY = y;
		this.motionZ = z;
	}

	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (particleMaxAge < 100) {
			particleMaxAge = rand.nextInt(21) + 65;
		}

		this.particleAge++;

		if (this.particleAge >= particleMaxAge) {
			this.setExpired();
		}

		this.motionX *= 0.7599999785423279D;
		this.motionY *= 0.7599999785423279D;
		this.motionZ *= 0.7599999785423279D;

		if (this.onGround) {
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}

		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
	}

	@Override
	public int getFXLayer() {
		return 1;
	}

	@Override
	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
		particleTexture = null;
		if (this.particleAge <= this.particleMaxAge && this.particleAge >= this.particleMaxAge / 8 * 7) {
			particleTexture = sprites[7];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 7 && this.particleAge >= this.particleMaxAge / 8 * 6) {
			particleTexture = sprites[6];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 6 && this.particleAge >= this.particleMaxAge / 8 * 5) {
			particleTexture = sprites[5];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 5 && this.particleAge >= this.particleMaxAge / 8 * 4) {
			particleTexture = sprites[4];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 4 && this.particleAge >= this.particleMaxAge / 8 * 3) {
			particleTexture = sprites[3];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 3 && this.particleAge >= this.particleMaxAge / 8 * 2) {
			particleTexture = sprites[2];
		}

		if (this.particleAge < this.particleMaxAge / 8 * 2 && this.particleAge >= this.particleMaxAge / 8 * 1) {
			particleTexture = sprites[1];
		}

		if (this.particleAge < this.particleMaxAge / 8 && this.particleAge >= 0) {
			particleTexture = sprites[0];
		}

		if (particleTexture != null) {

			////
			Random randy = new Random(this.hashCode());
			////
			Random rand = new Random(100);
			for (int i = 0; i < 5; i++) {
				double d = randy.nextInt(10) * 0.05;
				GL11.glColor3d(1 - d, 1 - d, 1 - d);
				this.particleRed = this.particleBlue = this.particleGreen = (float) (1 - d);
				
				double dX = (rand.nextGaussian() - 1D) * 0.15D;
				double dY = (rand.nextGaussian() - 1D) * 0.15D;
				double dZ = (rand.nextGaussian() - 1D) * 0.15D;
				double size = rand.nextDouble() * 0.5D + 0.35D;
				
				this.particleScale *= size;
				interpPosX += dX;
				interpPosY += dY;
				interpPosZ += dZ;
				super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
				interpPosX -= dX;
				interpPosY -= dY;
				interpPosZ -= dZ;
				this.particleScale /= size;
			}

		}

	}
}
