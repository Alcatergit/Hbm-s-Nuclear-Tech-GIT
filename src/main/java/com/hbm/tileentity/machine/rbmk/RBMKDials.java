package com.hbm.tileentity.machine.rbmk;

import com.hbm.config.GeneralConfig;

import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

public class RBMKDials {
	public static int clientColumnHeight = 3;

	public static final String KEY_PASSIVE_COOLING = "dialPassiveCooling";
	public static final String KEY_COLUMN_HEAT_FLOW = "dialColumnHeatFlow";
	public static final String KEY_FUEL_DIFFUSION_MOD = "dialDiffusionMod";
	public static final String KEY_HEAT_PROVISION = "dialHeatProvision";
	public static final String KEY_COLUMN_HEIGHT = "dialColumnHeight";
	public static final String KEY_PERMANENT_SCRAP = "dialEnablePermaScrap";
	public static final String KEY_BOILER_HEAT_CONSUMPTION = "dialBoilerHeatConsumption";
	public static final String KEY_CONTROL_SPEED_MOD = "dialControlSpeed";
	public static final String KEY_REACTIVITY_MOD = "dialReactivityMod";
	public static final String KEY_OUTGASSER_MOD = "dialOutgasserSpeedMod";
	public static final String KEY_SURGE_MOD = "dialControlSurgeMod";
	public static final String KEY_FLUX_RANGE = "dialFluxRange";
	public static final String KEY_REASIM_RANGE = "dialReasimRange";
	public static final String KEY_REASIM_COUNT = "dialReasimCount";
	public static final String KEY_REASIM_MOD = "dialReasimOutputMod";
	public static final String KEY_REASIM_BOILERS = "dialReasimBoilers";
	public static final String KEY_REASIM_BOILER_SPEED = "dialReasimBoilerSpeed";
	public static final String KEY_DISABLE_MELTDOWNS = "dialDisableMeltdowns";
	public static final String KEY_ENABLE_MELTDOWN_OVERPRESSURE = "dialEnableMeltdownOverpressure";
	public static final String KEY_MODERATOR_EFFICIENCY = "dialModeratorEfficiency";
	public static final String KEY_ABSORBER_EFFICIENCY = "dialAbsorberEfficiency";
	public static final String KEY_REFLECTOR_EFFICIENCY = "dialReflectorEfficiency";
	public static final String KEY_DISABLE_DEPLETION = "dialDisableDepletion";
	public static final String KEY_DISABLE_XENON = "dialDisableXenon";
	public static final String KEY_ABSORBER_HEAT_CONVERSION = "dialAbsorberHeatConversion";
	public static final String KEY_PASSIVE_COOLING_INNER = "dialPassiveCoolingInner";
	public static final String KEY_ENABLE_MELTDOWN_FLAME_EFFECT = "dialEnableMeltdownFlameEffect";
	
	public static void createDials(World world) {
		GameRules rules = world.getGameRules();

		rules.addGameRule(KEY_PASSIVE_COOLING, rules.hasRule(KEY_PASSIVE_COOLING) ? rules.getString(KEY_PASSIVE_COOLING) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_COLUMN_HEAT_FLOW, rules.hasRule(KEY_COLUMN_HEAT_FLOW) ? rules.getString(KEY_COLUMN_HEAT_FLOW) : "0.2", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_FUEL_DIFFUSION_MOD, rules.hasRule(KEY_FUEL_DIFFUSION_MOD) ? rules.getString(KEY_FUEL_DIFFUSION_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_HEAT_PROVISION, rules.hasRule(KEY_HEAT_PROVISION) ? rules.getString(KEY_HEAT_PROVISION) : "0.2", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_COLUMN_HEIGHT, rules.hasRule(KEY_COLUMN_HEIGHT) ? rules.getString(KEY_COLUMN_HEIGHT) : "4", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_PERMANENT_SCRAP, rules.hasRule(KEY_PERMANENT_SCRAP) ? rules.getString(KEY_PERMANENT_SCRAP) : "true", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_BOILER_HEAT_CONSUMPTION, rules.hasRule(KEY_BOILER_HEAT_CONSUMPTION) ? rules.getString(KEY_BOILER_HEAT_CONSUMPTION) : "0.1", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_CONTROL_SPEED_MOD, rules.hasRule(KEY_CONTROL_SPEED_MOD) ? rules.getString(KEY_CONTROL_SPEED_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REACTIVITY_MOD, rules.hasRule(KEY_REACTIVITY_MOD) ? rules.getString(KEY_REACTIVITY_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_OUTGASSER_MOD, rules.hasRule(KEY_OUTGASSER_MOD) ? rules.getString(KEY_OUTGASSER_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_SURGE_MOD, rules.hasRule(KEY_SURGE_MOD) ? rules.getString(KEY_SURGE_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_FLUX_RANGE, rules.hasRule(KEY_FLUX_RANGE) ? rules.getString(KEY_FLUX_RANGE) : "5", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REASIM_RANGE, rules.hasRule(KEY_REASIM_RANGE) ? rules.getString(KEY_REASIM_RANGE) : "10", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REASIM_COUNT, rules.hasRule(KEY_REASIM_COUNT) ? rules.getString(KEY_REASIM_COUNT) : "6", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REASIM_MOD, rules.hasRule(KEY_REASIM_MOD) ? rules.getString(KEY_REASIM_MOD) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REASIM_BOILERS, rules.hasRule(KEY_REASIM_BOILERS) ? rules.getString(KEY_REASIM_BOILERS) : "false", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_REASIM_BOILER_SPEED, rules.hasRule(KEY_REASIM_BOILER_SPEED) ? rules.getString(KEY_REASIM_BOILER_SPEED) : "0.05", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_DISABLE_MELTDOWNS, rules.hasRule(KEY_DISABLE_MELTDOWNS) ? rules.getString(KEY_DISABLE_MELTDOWNS) : "false", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_ENABLE_MELTDOWN_OVERPRESSURE, rules.hasRule(KEY_ENABLE_MELTDOWN_OVERPRESSURE) ? rules.getString(KEY_ENABLE_MELTDOWN_OVERPRESSURE) : "false", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_MODERATOR_EFFICIENCY, rules.hasRule(KEY_MODERATOR_EFFICIENCY) ? rules.getString(KEY_MODERATOR_EFFICIENCY) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_ABSORBER_EFFICIENCY, rules.hasRule(KEY_ABSORBER_EFFICIENCY) ? rules.getString(KEY_ABSORBER_EFFICIENCY) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_REFLECTOR_EFFICIENCY, rules.hasRule(KEY_REFLECTOR_EFFICIENCY) ? rules.getString(KEY_REFLECTOR_EFFICIENCY) : "1.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_DISABLE_DEPLETION, rules.hasRule(KEY_DISABLE_DEPLETION) ? rules.getString(KEY_DISABLE_DEPLETION) : "false", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_DISABLE_XENON, rules.hasRule(KEY_DISABLE_XENON) ? rules.getString(KEY_DISABLE_XENON) : "false", GameRules.ValueType.BOOLEAN_VALUE);
		rules.addGameRule(KEY_ABSORBER_HEAT_CONVERSION, rules.hasRule(KEY_ABSORBER_HEAT_CONVERSION) ? rules.getString(KEY_ABSORBER_HEAT_CONVERSION) : "0.0", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_PASSIVE_COOLING_INNER, rules.hasRule(KEY_PASSIVE_COOLING_INNER) ? rules.getString(KEY_PASSIVE_COOLING_INNER) : "0.1", GameRules.ValueType.NUMERICAL_VALUE);
		rules.addGameRule(KEY_ENABLE_MELTDOWN_FLAME_EFFECT, rules.hasRule(KEY_ENABLE_MELTDOWN_FLAME_EFFECT) ? rules.getString(KEY_ENABLE_MELTDOWN_FLAME_EFFECT) : "true", GameRules.ValueType.BOOLEAN_VALUE);
	}
	
	/**
	 * Returns the amount of heat per tick removed from components passively
	 * @param world
	 * @return >0
	 */
	public static double getPassiveCooling(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_PASSIVE_COOLING), 5.0D), 0.0D);
	}
	
	/**
	 * Returns the percentual step size how quickly neighboring component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getColumnHeatFlow(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_COLUMN_HEAT_FLOW), 5.0D), 0.0D, 1.0D);
	}
	
	/**
	 * Returns a modifier for fuel rod diffusion, i.e. how quickly the core and hull temperatures equalize.
	 * @param world
	 * @return >0
	 */
	public static double getFuelDiffusionMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_FUEL_DIFFUSION_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * Returns the percentual step size how quickly the fuel hull heat and the component heat equalizes. 1 is instant, 0.5 is in 50% steps, et cetera.
	 * @param world
	 * @return [0;1]
	 */
	public static double getFuelHeatProvision(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_HEAT_PROVISION), 0.2D), 0.0D, 1.0D);
	}
	
	/**
	 * Simple integer that decides how tall the structure is.
	 * @param world
	 * @return [0;250]
	 */
	public static int getColumnHeight(World world) {
		if(world.isRemote)
			return clientColumnHeight;
		return MathHelper.clamp(shittyWorkaroundParseInt(world.getGameRules().getString(KEY_COLUMN_HEIGHT), 4), 1, 250) - 1;
	}
	
	/**
	 * Whether or not scrap entities despawn on their own or remain alive until picked up.
	 * @param world
	 * @return
	 */
	public static boolean getPermaScrap(World world) {
		return world.getGameRules().getBoolean(KEY_PERMANENT_SCRAP);
	}
	
	/**
	 * How many heat units are consumed per steam unit (scaled per type) produced.
	 * @param world
	 * @return >0
	 */
	public static double getBoilerHeatConsumption(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_BOILER_HEAT_CONSUMPTION), 0.1D), 0D);
	}
	
	/**
	 * A multiplier for how quickly the control rods move.
	 * @param world
	 * @return >0
	 */
	public static double getControlSpeed(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_CONTROL_SPEED_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return >0
	 */
	public static double getReactivityMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_REACTIVITY_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * A multiplier for how much flux the rods give out.
	 * @param world
	 * @return >0
	 */
	public static double getOutgasserMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_OUTGASSER_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * A multiplier for how high the power surge goes when inserting control rods.
	 * @param world
	 * @return >0
	 */
	public static double getSurgeMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_SURGE_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * Simple integer that decides how far the flux of a normal fuel rod reaches.
	 * @param world
	 * @return [1;100]
	 */
	public static int getFluxRange(World world) {
		return MathHelper.clamp(shittyWorkaroundParseInt(world.getGameRules().getString(KEY_FLUX_RANGE), 5), 1, 100);
	}
	
	/**
	 * Simple integer that decides how far the flux of a ReaSim fuel rod reaches.
	 * @param world
	 * @return [1;100]
	 */
	public static int getReaSimRange(World world) {
		return MathHelper.clamp(shittyWorkaroundParseInt(world.getGameRules().getString(KEY_REASIM_RANGE), 10), 1, 100);
	}
	
	/**
	 * Simple integer that decides how many neutrons are created from ReaSim fuel rods.
	 * @param world
	 * @return [1;24]
	 */
	public static int getReaSimCount(World world) {
		return MathHelper.clamp(shittyWorkaroundParseInt(world.getGameRules().getString(KEY_REASIM_COUNT), 6), 1, 24);
	}
	
	/**
	 * Returns a modifier for the outgoing flux of individual streams from the ReaSim fuel rod to compensate for the potentially increased stream count.
	 * @param world
	 * @return >0
	 */
	public static double getReaSimOutputMod(World world) {
		return Math.max(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_REASIM_MOD), 1.0D), 0.0D);
	}
	
	/**
	 * Whether or not all components should act like boilers with dedicated in/outlet blocks
	 * @param world
	 * @return
	 */
	public static boolean getReasimBoilers(World world) {
		return world.getGameRules().getBoolean(KEY_REASIM_BOILERS) || (GeneralConfig.enable528 && GeneralConfig.enable528ReasimBoilers);
	}
	
	/**
	 * How much % of the possible steam ends up being produced per tick
	 * @param world
	 * @return [0;1]
	 */
	public static double getReaSimBoilerSpeed(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_REASIM_BOILER_SPEED), 0.05D), 0.0D, 1.0D);
	}
	
	//why make the double representation accessible in a game rule when you can just force me to add a second pointless parsing operation?
	public static double shittyWorkaroundParseDouble(String s, double def) {
		try {
			return Double.parseDouble(s);
		} catch(Exception ex) { }
		return def;
	}
	public static int shittyWorkaroundParseInt(String s, int def) {
		try {
			return Integer.parseInt(s);
		} catch(Exception ignored) { }
		return def;
	}

	public static boolean getMeltdownsDisabled(World world) {
		return world.getGameRules().getBoolean(KEY_DISABLE_MELTDOWNS);
	}

	public static boolean getMeltdownOverpressure(World world) {
		return world.getGameRules().getBoolean(KEY_ENABLE_MELTDOWN_OVERPRESSURE);
	}

	public static double getModeratorEfficiency(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_MODERATOR_EFFICIENCY), 1.0D), 0.0D, 1.0D);
	}

	public static double getAbsorberEfficiency(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_ABSORBER_EFFICIENCY), 1.0D), 0.0D, 1.0D);
	}

	public static double getReflectorEfficiency(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_REFLECTOR_EFFICIENCY), 1.0D), 0.0D, 1.0D);
	}

	public static boolean getDepletion(World world) {
		return !world.getGameRules().getBoolean(KEY_DISABLE_DEPLETION);
	}

	public static boolean getXenon(World world) {
		return !world.getGameRules().getBoolean(KEY_DISABLE_XENON);
	}

	public static double getAbsorberHeatConversion(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_ABSORBER_HEAT_CONVERSION), 0.05D), 0.0D, 1.0D);
	}

	public static double getPassiveCoolingInner(World world) {
		return MathHelper.clamp(shittyWorkaroundParseDouble(world.getGameRules().getString(KEY_PASSIVE_COOLING_INNER), 0.1D), 0.0D, 1.0D);
	}

	public static boolean getMeltdownFlameEffect(World world) {
		return world.getGameRules().getBoolean(KEY_ENABLE_MELTDOWN_FLAME_EFFECT);
	}

}
