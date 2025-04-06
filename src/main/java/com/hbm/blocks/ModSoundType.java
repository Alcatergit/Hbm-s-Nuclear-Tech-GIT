package com.hbm.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import java.util.Random;

public class ModSoundType extends SoundType {

	protected final SoundEvent placeSound;
	protected final SoundEvent breakSound;
	protected final SoundEvent stepSound;

	protected ModSoundType(SoundEvent placeSound, SoundEvent breakSound, SoundEvent stepSound, float volume, float pitch) {
		super(volume, pitch, placeSound, breakSound, stepSound, stepSound, stepSound);
		this.placeSound = placeSound;
		this.breakSound = breakSound;
		this.stepSound = stepSound;
	}

	public ModEnvelopedSoundType enveloped() {
		return new ModEnvelopedSoundType(placeSound, breakSound, stepSound, volume, pitch);
	}

	public ModEnvelopedSoundType enveloped(Random random) {
		return new ModEnvelopedSoundType(placeSound, breakSound, stepSound, volume, pitch, random);
	}

	@Override
	public SoundEvent getPlaceSound() {
		return placeSound;
	}

	@Override
	public SoundEvent getBreakSound() {
		return breakSound;
	}

	@Override
	public SoundEvent getStepSound() {
		return stepSound;
	}

	// creates a sound type with vanilla-like sound strings name-spaced to the mod
	public static ModSoundType mod(String soundName, float volume, float pitch) {
		return new ModSoundType(modDig(soundName), modDig(soundName), modStep(soundName), volume, pitch);
	}

	// these permutations allow creating a sound type with one of the three sounds being custom
	// and the other ones defaulting to vanilla-like sound strings name-spaced to the mod

	public static ModSoundType customPlace(String soundName, SoundEvent placeSound, float volume, float pitch) {
		return new ModSoundType(placeSound, modDig(soundName), modStep(soundName), volume, pitch);
	}

	public static ModSoundType customBreak(String soundName, SoundEvent breakSound, float volume, float pitch) {
		return new ModSoundType(modDig(soundName), breakSound, modStep(soundName), volume, pitch);
	}

	public static ModSoundType customStep(String soundName, SoundEvent stepSound, float volume, float pitch) {
		return new ModSoundType(modDig(soundName), modDig(soundName), stepSound, volume, pitch);
	}

	public static ModSoundType customDig(String soundName, SoundEvent digSound, float volume, float pitch) {
		return new ModSoundType(digSound, digSound, modStep(soundName), volume, pitch);
	}

	// these permutations copy sounds from an existing sound type and modify one of the sounds,
	// but with a manual path for the custom sound

	public static ModSoundType customPlace(SoundType from, SoundEvent placeSound, float volume, float pitch) {
		return new ModSoundType(placeSound, from.getBreakSound(), from.getStepSound(), volume, pitch);
	}

	public static ModSoundType customBreak(SoundType from, SoundEvent breakSound, float volume, float pitch) {
		return new ModSoundType(from.getPlaceSound(), breakSound, from.getStepSound(), volume, pitch);
	}

	public static ModSoundType customStep(SoundType from, SoundEvent stepSound, float volume, float pitch) {
		return new ModSoundType(from.getPlaceSound(), from.getBreakSound(), stepSound, volume, pitch);
	}

	public static ModSoundType customDig(SoundType from, SoundEvent dig, float volume, float pitch) {
		return new ModSoundType(dig, dig, from.getStepSound(), volume, pitch);
	}

	// customizes all sounds
	public static ModSoundType placeBreakStep(SoundEvent placeSound, SoundEvent breakSound, SoundEvent stepSound, float volume, float pitch) {
		return new ModSoundType(placeSound, breakSound, stepSound, volume, pitch);
	}

	private static SoundEvent modDig(String soundName) {
		return new SoundEvent(new ResourceLocation("dig." + soundName));
	}

	private static SoundEvent modStep(String soundName) {
		return new SoundEvent(new ResourceLocation("step." + soundName));
	}

	public static class ModEnvelopedSoundType extends ModSoundType {
		private final Random random;

		ModEnvelopedSoundType(SoundEvent placeSound, SoundEvent breakSound, SoundEvent stepSound, float volume, float pitch, Random random) {
			super(placeSound, breakSound, stepSound, volume, pitch);
			this.random = random;
		}

		ModEnvelopedSoundType(SoundEvent placeSound, SoundEvent breakSound, SoundEvent stepSound, float volume, float pitch) {
			this(placeSound, breakSound, stepSound, volume, pitch, new Random());
		}

		// a bit of a hack, but most of the time, playSound is called with the sound path queried first, and then volume and pitch
		private SubType probableSubType = SubType.PLACE;

		@Override
		public SoundEvent getPlaceSound() {
			probableSubType = SubType.PLACE;
			return super.getPlaceSound();
		}

		@Override
		public SoundEvent getBreakSound() {
			probableSubType = SubType.BREAK;
			return super.getBreakSound();
		}

		@Override
		public SoundEvent getStepSound() {
			probableSubType = SubType.STEP;
			return super.getStepSound();
		}

		private Envelope volumeEnvelope = null;
		private Envelope pitchEnvelope = null;

		public ModEnvelopedSoundType volumeFunction(Envelope volumeEnvelope) {
			this.volumeEnvelope = volumeEnvelope;
			return this;
		}

		public ModEnvelopedSoundType pitchFunction(Envelope pitchEnvelope) {
			this.pitchEnvelope = pitchEnvelope;
			return this;
		}

		@Override
		public float getVolume() {
			if (volumeEnvelope == null)
				return super.getVolume();
			else
				return volumeEnvelope.compute(super.getVolume(), random, probableSubType);
		}

		@Override
		public float getPitch() {
			if (pitchEnvelope == null)
				return super.getPitch();
			else
				return pitchEnvelope.compute(super.getPitch(), random, probableSubType);
		}

		@FunctionalInterface
		public interface Envelope {
			float compute(float in, Random rand, SubType type);
		}
	}

	public enum SubType {
		PLACE, BREAK, STEP
	}
}
