package com.leafia.contents.worldgen.biomes.effects;

import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ParticleCloudSmall extends Particle {
	float oSize;

	public ParticleCloudSmall(World world,double x,double y,double z) { this(world,x,y,z,0,0,0,0.75f); }
	public ParticleCloudSmall(World world,double x,double y,double z,float scale) { this(world,x,y,z,0,0,0,scale); }
	public ParticleCloudSmall(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double mx, double my, double mz, float scale)
	{
		super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
		this.motionX *= 0.10000000149011612D;
		this.motionY *= 0.10000000149011612D;
		this.motionZ *= 0.10000000149011612D;
		this.motionX += mx;
		this.motionY += my;
		this.motionZ += mz;
		float f = 1.0F - (float)(Math.random() * 0.30000001192092896D);
		this.particleRed = f;
		this.particleGreen = f;
		this.particleBlue = f;
		this.particleScale *= scale;
		//this.particleScale *= 2.5F;
		this.oSize = this.particleScale;
		this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.3D));
		this.particleMaxAge = (int)((float)this.particleMaxAge * 2.5F);
	}

	public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
	{
		float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge * 32.0F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		this.particleScale = this.oSize * f;
		super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
	}

	public void onUpdate()
	{
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}

		this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9599999785423279D;
		this.motionY *= 0.9599999785423279D;
		this.motionZ *= 0.9599999785423279D;
		EntityPlayer entityplayer = this.world.getClosestPlayer(this.posX, this.posY, this.posZ, 2.0D, false);

		if (entityplayer != null)
		{
			AxisAlignedBB axisalignedbb = entityplayer.getEntityBoundingBox();

			if (this.posY > axisalignedbb.minY)
			{
				this.posY += (axisalignedbb.minY - this.posY) * 0.2D;
				this.motionY += (entityplayer.motionY - this.motionY) * 0.2D;
				this.setPosition(this.posX, this.posY, this.posZ);
			}
		}

		if (this.onGround)
		{
			this.motionX *= 0.699999988079071D;
			this.motionZ *= 0.699999988079071D;
		}
	}
}
