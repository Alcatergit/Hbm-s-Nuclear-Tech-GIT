package com.hbm.tileentity.deco;

import com.hbm.lib.Library;
import com.hbm.particle.bfg.ParticleBFGBeam;
import com.hbm.particle.bfg.ParticleBFGCoreLightning;
import com.hbm.particle.bfg.ParticleBFGParticle;
import com.hbm.particle.bfg.ParticleBFGPrefire;
import com.hbm.particle.bfg.ParticleBFGRing;
import com.hbm.particle.bfg.ParticleBFGShockwave;
import com.hbm.particle.bfg.ParticleBFGSmoke;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.client.Minecraft;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityObjTester extends TileEntity implements ITickable {

	public int fireAge = -1;

	@Override
	public void update() {
		if(world.isRemote) {
			if(fireAge >= 0) {
				fireAge++;
				if(fireAge >= 1 && fireAge <= 40){
					Vec3 attractionPoint = Vec3.createVectorHelper(pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 60);
					for(int i = 0; i < world.rand.nextInt(6); i ++){
						float randPosX = Library.remap(world.rand.nextFloat(), 0, 1, -10, 10);
						float randPosY = Library.remap(world.rand.nextFloat(), 0, 1, -10, 10);
						float randPosZ = Library.remap(world.rand.nextFloat(), 0, 1, 0, 10);
						float randMotionX = world.rand.nextFloat()*0.4F-0.2F;
						float randMotionY = world.rand.nextFloat()*0.4F-0.2F;
						float randMotionZ = world.rand.nextFloat()*0.4F-0.2F;
						Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGParticle(world, pos.getX() + 0.5 + randPosX, pos.getY() + 24 + randPosY, pos.getZ() + 0.5 - 74 +  + randPosZ, randMotionX, randMotionY, randMotionZ, attractionPoint));
					}
				}
				
				if(fireAge >= 1 && fireAge <= 12 && fireAge%3 == 0){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGCoreLightning(world, pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 61));
				}
				if(fireAge >= 28 && fireAge <= 32 && fireAge%2 == 0){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGCoreLightning(world, pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 61));
				}
				if(fireAge > 32 && fireAge <= 52){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGCoreLightning(world, pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 61));
				}
				
				if(fireAge == 10){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGPrefire(world, pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 21));
				}
				
				if(fireAge == 58){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGBeam(world, pos.getX() + 0.5, pos.getY() + 24, pos.getZ() + 0.5 - 25));
				}
				if(fireAge >= 58 && fireAge <= 70){
					for(int i = 0; i < 20; i ++){
						float randPosX = Library.remap(world.rand.nextFloat(), 0, 1, -5, 5);
						float randPosY = Library.remap(world.rand.nextFloat(), 0, 1, -5, 5);
						float randPosZ = Library.remap(world.rand.nextFloat(), 0, 1, 0, -200);
						float randMotionX = world.rand.nextFloat()*0.4F-0.2F;
						float randMotionY = world.rand.nextFloat()*0.4F-0.2F;
						float randMotionZ = world.rand.nextFloat()-5.4F-4F;
						Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGParticle(world, pos.getX() + 0.5 + randPosX, pos.getY() + 24 + randPosY, pos.getZ() + 0.5 - 44 +  + randPosZ, randMotionX, randMotionY, randMotionZ, null));
					}
				}
				if(fireAge == 58 || fireAge == 64){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGSmoke(world, pos.getX() + 0.5, pos.getY() + 23, pos.getZ() + 0.5 - 55));
				}
				if(fireAge == 58 || fireAge == 68 || fireAge == 83){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGRing(world, pos.getX() + 0.5, pos.getY() + 25, pos.getZ() + 0.5 - 55));
				}
				if(fireAge == 60){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGShockwave(world, pos.getX() + 0.5, pos.getY() + 25, pos.getZ() + 0.5 - 55, 2, 30, 1, 0.95F));
				}
				if(fireAge == 65){
					Minecraft.getMinecraft().effectRenderer.addEffect(new ParticleBFGShockwave(world, pos.getX() + 0.5, pos.getY() + 25, pos.getZ() + 0.5 - 65, 5, 25, 0.6F, 0.98F));
				}
				
				
			}
		}
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
}
