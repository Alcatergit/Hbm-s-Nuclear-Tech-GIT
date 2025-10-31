package com.hbm.world;

import com.hbm.main.MainRegistry;
import com.hbm.main.ModEventHandlerClient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProviderSurface;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

public class WorldProviderNTM extends WorldProviderSurface {
	public static DimensionType dimType;
	private float[] colorsSunriseSunset = new float[4];

	@Override
	public DimensionType getDimensionType()
	{
		return dimType;
	}

	public WorldProviderNTM() {
	}

	@Override
	public float calculateCelestialAngle(long worldTime, float partialTicks) {
		return super.calculateCelestialAngle(worldTime, partialTicks);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float[] calcSunriseSunsetColors(float par1, float par2) {
		float f2 = 0.4F;
		float f3 = MathHelper.cos(par1 * (float) Math.PI * 2.0F) - 0.0F;
		float f4 = -0.0F;
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());

		if(f3 >= f4 - f2 && f3 <= f4 + f2) {
			float f5 = (f3 - f4) / f2 * 0.5F + 0.5F;
			float f6 = 1.0F - (1.0F - MathHelper.sin(f5 * (float) Math.PI)) * 0.99F;
			f6 *= f6;
			this.colorsSunriseSunset[0] = (f5 * 0.3F + 0.7F) * (1 - dust);
			this.colorsSunriseSunset[1] = (f5 * f5 * 0.7F + 0.2F) * (1 - dust);
			this.colorsSunriseSunset[2] = (f5 * f5 * 0.0F + 0.2F) * (1 - dust);
			this.colorsSunriseSunset[3] = f6 * (1 - dust);
			return this.colorsSunriseSunset;
		} else {
			return null;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getStarBrightness(float par1) {
		float starBr = world.getStarBrightnessBody(par1);
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());
		float f1 = world.getCelestialAngle(par1);
		float f2 = 1.0F - (MathHelper.cos(f1 * (float) Math.PI * 2.0F) * 2.0F + 0.25F);

		if(f2 < 0.0F) {
			f2 = 0.0F;
		}

		if(f2 > 1.0F) {
			f2 = 1.0F;
		}
		return starBr * (1 - dust);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public float getSunBrightness(float par1) {
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());//ImpactWorldHandler.getDustForClient(MainRegistry.proxy.me().world);
		float sunBr = world.getSunBrightnessFactor(par1);
		return (sunBr * 0.8F + 0.2F) * (1 - dust);
	}

	@Override
	public boolean isDaytime() {
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());

		if(dust >= 0.75F) {
			return false;
		}
		return super.isDaytime();
	}

	@Override
	public float getSunBrightnessFactor(float par1) {
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());
		float sunBr = super.getSunBrightnessFactor(par1);
		float dimSun = sunBr * (1 - dust);
		return dimSun;
	}

	/**
	 * Return Vec3D with biome specific fog color
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
		Vec3d fog = super.getFogColor(p_76562_1_, p_76562_2_);
		// Oh don't get tricked by the way: THIS ISN't USED
		// Actual fog color is defined within ModEventHandlerClient
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());
		float fire = MainRegistry.proxy.getImpactFire(world,dimType.getId());

		float f3 = (float) fog.x;
		float f4 = (float) fog.y * (1 - (dust * 0.5F));
		float f5 = (float) fog.z * (1 - dust);

		if(fire > 0) {
			return new Vec3d((double) f3 * (Math.max((1 - (dust * 2)), 0)), (double) f4 * (Math.max((1 - (dust * 2)), 0)), (double) f5 * (Math.max((1 - (dust * 2)), 0)));
		}
		return new Vec3d((double) f3 * (1 - dust), (double) f4 * (1 - dust), (double) f5 * (1 - dust));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Vec3d getSkyColor(Entity cameraEntity, float partialTicks) {
		Vec3d sky = super.getSkyColor(cameraEntity, partialTicks);
		float dust = MainRegistry.proxy.getImpactDust(world,dimType.getId());
		float fire = MainRegistry.proxy.getImpactFire(world,dimType.getId());

		float f4;
		float f5;
		float f6;

		if(fire > 0) {
			f4 = (float) (sky.x * 1.3f);
			f5 = (float) sky.y * ((Math.max((1 - (dust * 1.4f)), 0)));
			f6 = (float) sky.z * ((Math.max((1 - (dust * 4)), 0)));
		} else {
			f4 = (float) sky.x;
			f5 = (float) sky.y * (1 - (dust * 0.5F));
			f6 = (float) sky.z * (1 - dust);
		}

		return new Vec3d((double) f4 * (fire + (1 - dust)), (double) f5 * (fire + (1 - dust)), (double) f6 * (fire + (1 - dust)));
	}

	@Nullable
	@SideOnly(Side.CLIENT)
	@Override
	public net.minecraft.client.audio.MusicTicker.MusicType getMusicType()
	{
		Minecraft mc = Minecraft.getMinecraft();
		if (MainRegistry.proxy.getImpactDust(mc.world, mc.player.dimension) > 0.75F) {
			return ModEventHandlerClient.darkness; //fuck off//ModEventHandlerClient.nextMusic ? ModEventHandlerClient.darkness : ModEventHandlerClient.darkness2;
		}
		return null;
	}
}