package com.hbm.config;

import java.util.Locale;

import org.apache.logging.log4j.Level;
import org.lwjgl.opengl.GLContext;

import com.hbm.main.MainRegistry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class GeneralConfig {

	public static boolean enableDebugMode = true;
	public static boolean enableMycelium = false;
	public static boolean enablePlutoniumOre = false;
	public static boolean enableDungeons = true;
	public static boolean enableMDOres = true;
	public static boolean enableMines = true;
	public static boolean enableRad = true;
	public static boolean enableNITAN = true;
	public static boolean enableNukeClouds = true;
	public static boolean enableAutoCleanup = false;
	public static boolean enableMeteorStrikes = true;
	public static boolean enableMeteorShowers = true;
	public static boolean enableMeteorTails = true;
	public static boolean enableSpecialMeteors = true;
	public static boolean enableBomberShortMode = false;
	public static boolean enableVaults = true;
	public static boolean enableRads = true;
	public static boolean advancedRadiation = true;
	public static boolean enableCataclysm = false;
	public static boolean enableExtendedLogging = false;
	public static boolean enableHardcoreTaint = false;
	public static boolean enableGuns = true;
	public static boolean ssgAnim = true;
	public static boolean enableVirus = true;
	public static boolean enableCrosshairs = true;
	public static boolean instancedParticles = true;
	public static boolean callListModels = true;
	public static boolean useShaders = false;
	public static boolean useShaders2 = true;
	public static boolean bloom = true;
	public static boolean heatDistortion = true;
	public static boolean enableBabyMode = false;
	public static boolean recipes = true;
	public static boolean shapeless = true;
	public static boolean oredict = true;
	public static boolean shaped = true;
	public static boolean nonoredict = true;
	public static boolean jei = true;
	public static boolean depthEffects = true;
	public static boolean flashlight = true;
	public static boolean flashlightVolumetric = true;
	public static boolean bulletHoleNormalMapping = true;
	public static int flowingDecalAmountMax = 20;
	public static boolean bloodFX = true;
	
	public static boolean disableShadersMac = true;
	
	public static void loadFromConfig(Configuration config){
		final String CATEGORY_GENERAL = "01_general";
		enableDebugMode = config.get(CATEGORY_GENERAL, "1.00_enableDebugMode", false).getBoolean(false);
		enableMycelium = config.get(CATEGORY_GENERAL, "1.01_enableMyceliumSpread", false).getBoolean(false);
		enablePlutoniumOre = config.get(CATEGORY_GENERAL, "1.02_enablePlutoniumNetherOre", false).getBoolean(false);
		enableDungeons = config.get(CATEGORY_GENERAL, "1.03_enableDungeonSpawn", true).getBoolean(true);
		enableMDOres = config.get(CATEGORY_GENERAL, "1.04_enableOresInModdedDimensions", true).getBoolean(true);
		enableMines = config.get(CATEGORY_GENERAL, "1.05_enableLandmineSpawn", true).getBoolean(true);
		enableRad = config.get(CATEGORY_GENERAL, "1.06_enableRadHotspotSpawn", true).getBoolean(true);
		enableNITAN = config.get(CATEGORY_GENERAL, "1.07_enableNITANChestSpawn", true).getBoolean(true);
		enableNukeClouds = config.get(CATEGORY_GENERAL, "1.08_enableMushroomClouds", true).getBoolean(true);
		enableAutoCleanup = config.get(CATEGORY_GENERAL, "1.09_enableAutomaticRadCleanup", false).getBoolean(false);
		enableMeteorStrikes = config.get(CATEGORY_GENERAL, "1.10_enableMeteorStrikes", true).getBoolean(true);
		enableMeteorShowers = config.get(CATEGORY_GENERAL, "1.11_enableMeteorShowers", true).getBoolean(true);
		enableMeteorTails = config.get(CATEGORY_GENERAL, "1.12_enableMeteorTails", true).getBoolean(true);
		enableSpecialMeteors = config.get(CATEGORY_GENERAL, "1.13_enableSpecialMeteors", false).getBoolean(false);
		enableBomberShortMode = config.get(CATEGORY_GENERAL, "1.14_enableBomberShortMode", false).getBoolean(false);
		enableVaults = config.get(CATEGORY_GENERAL, "1.15_enableVaultSpawn", true).getBoolean(true);
		enableRads = config.get(CATEGORY_GENERAL, "1.16_enableNewRadiation", true).getBoolean(true);
		enableCataclysm = config.get(CATEGORY_GENERAL, "1.17_enableCataclysm", false).getBoolean(false);
		enableExtendedLogging = config.get(CATEGORY_GENERAL, "1.18_enableExtendedLogging", false).getBoolean(false);
		enableHardcoreTaint = config.get(CATEGORY_GENERAL, "1.19_enableHardcoreTaint", false).getBoolean(false);
		enableGuns = config.get(CATEGORY_GENERAL, "1.20_enableGuns", true).getBoolean(true);
		enableVirus = config.get(CATEGORY_GENERAL, "1.21_enableVirus", false).getBoolean(false);
        enableCrosshairs = config.get(CATEGORY_GENERAL, "1.22_enableCrosshairs", true).getBoolean(true);
		Property shaders = config.get(CATEGORY_GENERAL, "1.23_enableShaders", false);
		shaders.setComment("Experimental, don't use");
		useShaders = shaders.getBoolean(false);
		if(FMLCommonHandler.instance().getSide() == Side.CLIENT)
			if(!OpenGlHelper.shadersSupported) {
				MainRegistry.logger.log(Level.WARN, "GLSL shaders are not supported; not using shaders");
				useShaders = false;
			} else if(!GLContext.getCapabilities().OpenGL30) {
				MainRegistry.logger.log(Level.WARN, "OpenGL 3.0 is not supported; not using shaders");
				useShaders = false;
			}
		useShaders = false;
		Property ssg_anim = config.get(CATEGORY_GENERAL, "1.24_ssgAnimType", true);
		ssg_anim.setComment("Which supershotgun reload animation to use. True is Drillgon's animation, false is Bob's animation");
		ssgAnim = ssg_anim.getBoolean();
		instancedParticles = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_instancedParticles", "Enables instanced particle rendering for some particles, which makes them render several times faster. May not work on all computers, and will break with shaders.", true);
		depthEffects = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_depthBufferEffects", "Enables effects that make use of reading from the depth buffer", true);
		flashlight = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_flashlights", "Enables dynamic directional lights", true);
		flashlightVolumetric = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_flashlight_volumetrics", "Enables volumetric lighting for directional lights", true);
		bulletHoleNormalMapping = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_bullet_hole_normal_mapping", "Enables normal mapping on bullet holes, which can improve visuals", true);
		flowingDecalAmountMax = CommonConfig.createConfigInt(config, CATEGORY_GENERAL, "1.25_flowing_decal_max", "The maximum number of 'flowing' decals that can exist at once (eg blood that can flow down walls)", 20);
		disableShadersMac  = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.25_disable_shader_mac", "Automatically disables shaders on macs", true);
		
		callListModels = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.26_callListModels", "Enables call lists for a few models, making them render extremely fast", true);
		enableBabyMode = config.get(CATEGORY_GENERAL, "1.27_enableBabyMode", false).getBoolean(false);
		
		recipes = config.get(CATEGORY_GENERAL, "1.28_enableRecipes", true).getBoolean(true);
		shapeless = config.get(CATEGORY_GENERAL, "1.28_enableShapeless", true).getBoolean(true);
		oredict = config.get(CATEGORY_GENERAL, "1.28_enableOreDict", true).getBoolean(true);
		shaped = config.get(CATEGORY_GENERAL, "1.28_enableShaped", true).getBoolean(true);
		nonoredict = config.get(CATEGORY_GENERAL, "1.28_enableNonOreDict", true).getBoolean(true);
		
		jei = config.get(CATEGORY_GENERAL, "1.28_enableJei", true).getBoolean(true);
		useShaders2 = config.get(CATEGORY_GENERAL, "1.29_enableShaders2", true).getBoolean(true);
		bloom = config.get(CATEGORY_GENERAL, "1.30_enableBloom", true).getBoolean(true);
		heatDistortion = config.get(CATEGORY_GENERAL, "1.30_enableHeatDistortion", true).getBoolean(true);
		
		Property adv_rads = config.get(CATEGORY_GENERAL, "1.31_enableAdvancedRadiation", true);
		adv_rads.setComment("Enables a 3 dimensional version of the radiation system that also allows some blocks (like concrete bricks) to stop it from spreading");
		advancedRadiation = adv_rads.getBoolean(true);
		
		bloodFX = CommonConfig.createConfigBool(config, CATEGORY_GENERAL, "1.32_enable_blood_effects", "Enables the over-the-top blood visual effects for some weapons", true);
	
		String s = System.getProperty("os.name").toLowerCase(Locale.ROOT);
		boolean mac = disableShadersMac && s.contains("mac");
		if(((instancedParticles || depthEffects || flowingDecalAmountMax > 0 || bloodFX) && !MainRegistry.proxy.opengl33()) || !useShaders2 || mac){
			MainRegistry.logger.error("Warning - Open GL 3.3 not supported! Disabling 3.3 effects...");
			if(!useShaders2){
				MainRegistry.logger.error("Shader effects manually disabled");
			}
			if(mac){
				MainRegistry.logger.error("Shader effects automatically disabled - we're running on a mac.");
			}
			instancedParticles = false;
			depthEffects = false;
			flowingDecalAmountMax = 0;
			bloodFX = false;
			useShaders2 = false;
			bloom = false;
			heatDistortion = false;
		}
		if(!depthEffects){
			flashlight = false;
			bulletHoleNormalMapping = false;
		}
		if(!flashlight){
			flashlightVolumetric = false;
		}
	}

}
