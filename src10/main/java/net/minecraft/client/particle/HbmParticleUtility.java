package net.minecraft.client.particle;

//Stupid minecraft particles have all their fields protected. Solution? Make a class in the same package!
public class HbmParticleUtility {

	public static void setNoClip(Particle p){
		p.canCollide = false;
	}
	
	public static void setMotion(Particle p, double mX, double mY, double mZ){
		p.motionX = mX;
		p.motionY = mY;
		p.motionZ = mZ;
	}
	
	public static void setMaxAge(Particle p, int maxAge){
		p.particleMaxAge = maxAge;
	}
	
	public static void setSmokeScale(ParticleSmokeNormal p, float scale){
		p.smokeParticleScale = scale;
	}
	
	public static void resetSmokeScaleWithMult(ParticleSmokeNormal p, float mult){
		p.particleScale *= mult;
		p.smokeParticleScale = p.particleScale;
	}
	
}
