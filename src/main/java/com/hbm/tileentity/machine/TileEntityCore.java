package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.MachineFieldDisturber;
import com.hbm.entity.logic.EntityNukeExplosionMK3;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModFluidProperties;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.ArmorUtil;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemCatalyst;
import com.hbm.items.special.ItemAMSCore;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.hbm.main.AdvancementManager;
import com.hbm.main.ClientProxy;
import com.hbm.main.MainRegistry;
import com.hbm.packet.PacketDispatcher;
import com.hbm.sound.AudioWrapper;
import com.hbm.tileentity.TileEntityMachineBase;
import com.leafia.CommandLeaf;
import com.leafia.LeafiaHelper;
import com.leafia.contents.effects.folkvangr.EntityNukeFolkvangr.VacuumInstance;
import com.leafia.contents.effects.folkvangr.particles.ParticleFleijaVacuum;
import com.leafia.contents.effects.folkvangr.visual.EntityCloudFleijaRainbow;
import com.leafia.contents.machines.powercores.dfc.particles.ParticleEyeOfHarmony;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.leafia.dev.custompacket.LeafiaCustomPacket;
import com.leafia.dev.custompacket.LeafiaCustomPacketEncoder;
import com.leafia.dev.optimization.LeafiaParticlePacket.DFCBlastParticle;
import com.leafia.dev.optimization.bitbyte.LeafiaBuf;
import com.leafia.passive.LeafiaPassiveLocal;
import com.leafia.passive.LeafiaPassiveServer;
import com.llib.exceptions.LeafiaDevFlaw;
import com.leafia.dev.math.FiaMatrix;
import com.llib.math.LeafiaColor;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;

public class TileEntityCore extends TileEntityMachineBase implements ITickable, LeafiaPacketReceiver {
	public enum Cores {
		ams_core_sing(HBMSoundEvents.dfc_vs, (intended,distance) ->
				Math.pow(MathHelper.clamp(1 - (distance - 3) / 15, 0, 1), 1.5)),
		ams_core_wormhole(HBMSoundEvents.dfc_tw, (intended,distance) ->
				Math.pow(MathHelper.clamp(1 - (distance - 3) / 40, 0, 1), 2)),
		ams_core_eyeofharmony(HBMSoundEvents.dfc_eoh, (intended,distance) ->
				Math.pow(MathHelper.clamp(1 - (distance - 3) / 150, 0, 1), 3));
		public final SoundEvent sfx;
		public final BiFunction<Float, Double, Double> attentuationFunction;

		Cores(SoundEvent sfx, BiFunction<Float, Double, Double> attentuationFunction) {
			this.sfx = sfx;
			this.attentuationFunction = attentuationFunction;
		}
	}

	public boolean hasCore = false;
	public int field;
	public int heat;
	public int color;
	public FluidTank[] tanks;
	public int overload = 0;

	public enum packetKeys {
		TEMP, STABILIZATION, MAXIMUM,
		CONTAINED, EXPELLING, POTENTIAL,

		TANK_A, TANK_B,
		EXPEL_TICK, COLOR, COLOR_CATALYST, CORE_TYPE,

		PLAY_SOUND, JAMMER,
		COLLAPSE,

		HASCORE;

		public int key;

		packetKeys() {
			this.key = this.ordinal();
		}
	}

	public double temperature = 0;
	public double stabilization = 0;
	public double containedEnergy = 0; // 1 = 1MSPK
	public double expellingEnergy = 0;
	public double potentialGain = 0;
	public double gainedEnergy = 0;

	public double collapsing = 0;
	public int stabilizers = 0; // used for crucifix chance calculation
	public int lastStabilizers = 0; // used for crucifix chance calculation
	public boolean wasBoosted = false; // used for crucifix chance calculation

	public double internalEnergy = 0;
	public double[] expelTicks = new double[20];
	public double energyMod = 1;
	public double bonus = 0;
	public final List<TileEntityCoreReceiver> absorbers = new ArrayList<>();
	boolean destroyed = false;
	double explosionIn = -1;
	public long explosionClock = 0;
	public BlockPos jammerPos = null;
	public static double failsafeLevel = 250000000;
	public static double maxEnergy = 100_000; // 1PSPK or 5EHE

	public double incomingSpk = 0;
	public double expellingSpk = 0;

	public double getStabilizationDiv() {
		return 1 + Math.sqrt(stabilization * 10);
	}

	public double getStabilizationDivAlt() {
		return Math.pow(stabilization / 3, 2) * 3 + 1;
	}

	public double getEnergyBase(double corePower) {
		double value = (Math.pow(Math.max(temperature / meltingPoint, 0) / 666 + 1, 0.666) - 1) * 666;
		// ^^ fuck this shit :D

		value = (Math.pow(Math.tanh(temperature / 666), 2) * 0.5
				+ Math.pow(Math.max(0, Math.atan((temperature - 1300) / 666)), 2)) * 2.5
				+ Math.pow(temperature / 5000, 3);

		value = Math.pow(value / Math.max(corePower, 0.001)/* anti-division by zero */, 0.666) * corePower;
		if (temperature > meltingPoint)
			value = value + (temperature - meltingPoint) / 100;
		return value;
	}

	public double getEnergyCurved(double energy) {
		return Math.pow(energy / 200, 2.25) * 200;
	}

	public int meltingPoint = 2250;
	public int ticks = 0;

	public TileEntityCore() {
		super(3);
		tanks = new FluidTank[2];
		tanks[0] = new FluidTank(128000);
		tanks[1] = new FluidTank(128000);
	}

	@Override
	public String getName() {
		return "container.dfcCore";
	}


	public double client_maxDial = 0.95;
	public Cores client_type = null;
	AudioWrapper client_sfx = null;
	boolean sfxPlaying = false;
	AudioWrapper meltdownSFX = null;
	AudioWrapper overloadSFX = null;
	AudioWrapper extinguishSFX = null;
	AudioWrapper explosionsSFX = null;
	public float angle = 0;
	public float lightRotateSpeed = 15/20f;
	boolean finalPhase = false;

	@Override
	public void invalidate() {
		if (client_sfx != null) {
			client_sfx.stopSound();
			client_sfx = null;
			sfxPlaying = false;
		}
		if (meltdownSFX != null) {
			meltdownSFX.stopSound();
			meltdownSFX = null;
			LeafiaDebug.debugLog(world,"STOP: 2");
		}
		if (extinguishSFX != null) {
			extinguishSFX.stopSound();
			extinguishSFX = null;
		}
		if (overloadSFX != null) {
			overloadSFX.stopSound();
			overloadSFX = null;
		}
		if (explosionsSFX != null) {
			explosionsSFX.stopSound();
			explosionsSFX = null;
		}
		MinecraftServer server = world.getMinecraftServer();
		if (server != null && !world.isRemote) {
			if (!server.isDedicatedServer())
				LeafiaPassiveLocal.trackingCores.remove(this);
		}
		super.invalidate();
	}

	@Override
	public void validate() {
		super.validate();
		MinecraftServer server = world.getMinecraftServer();
		if (server != null && !world.isRemote) {
			if (!server.isDedicatedServer())
				LeafiaPassiveLocal.trackingCores.add(this);
		}
		if (MainRegistry.proxy instanceof ClientProxy) {
			meltdownSFX = MainRegistry.proxy.getLoopedSound(
							HBMSoundEvents.dfc_meltdown,
							SoundCategory.BLOCKS,pos.getX()+.5f,pos.getY()+.5f,pos.getZ()+.5f,
							1f,1
					).setCustomAttentuation((intended,distance)->Math.pow(MathHelper.clamp(1-(distance-50)/500,0,1),6.66))
					.setLooped(false);
			extinguishSFX = MainRegistry.proxy.getLoopedSound(
							SoundEvents.BLOCK_FIRE_EXTINGUISH,
							SoundCategory.BLOCKS,pos.getX()+.5f,pos.getY()+.5f,pos.getZ()+.5f,
							1f,0.8f
					).setCustomAttentuation((intended,distance)->Math.pow(MathHelper.clamp(1-(distance-50)/500,0,1),6.66))
					.setLooped(false);
			overloadSFX = MainRegistry.proxy.getLoopedSound(
							HBMSoundEvents.overload,
							SoundCategory.BLOCKS,pos.getX()+.5f,pos.getY()+.5f,pos.getZ()+.5f,
							1f,1
					).setCustomAttentuation((intended,distance)->Math.pow(MathHelper.clamp(1-(distance-20)/100,0,1),6.66))
					.setLooped(false);
			explosionsSFX = MainRegistry.proxy.getLoopedSound(
							HBMSoundEvents.longexplosion,
							SoundCategory.BLOCKS,pos.getX()+.5f,pos.getY()+.5f,pos.getZ()+.5f,
							1f,1
					).setCustomAttentuation((intended,distance)->Math.pow(MathHelper.clamp(1-(distance-20)/100,0,1),6.66))
					.setLooped(false);
		}
	}
	int overloadTimer = 0;
	public int colorCatalyst = 0xFFFFFF;
	@Override
	public void update() {
		if (destroyed) return;
		if (!world.isRemote) {
			lastStabilizers = stabilizers;
			stabilizers = 0;
			/*
			if(heat > 0 && heat >= field) {
				
				int fill = tanks[0].getFluidAmount() + tanks[1].getFluidAmount();
				int max = tanks[0].getCapacity() + tanks[1].getCapacity();
				int mod = heat * 10;
				
				int size = Math.max(Math.min(fill * mod / max, 1000), 50);
				
				//System.out.println(fill + " * " + mod + " / " + max + " = " + size);

	    		world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 100000.0F, 1.0F);

				EntityNukeExplosionMK3 exp = new EntityNukeExplosionMK3(world);
				exp.posX = pos.getX();
				exp.posY = pos.getY();
				exp.posZ = pos.getZ();
				exp.destructionRange = size;
				exp.speed = 25;
				exp.coefficient = 1.0F;
				exp.waste = false;
				if(overload >= 60 && !EntityNukeExplosionMK3.isJammed(this.world, exp)){
					world.spawnEntity(exp);
		    		
		    		EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, size);
		    		cloud.posX = pos.getX();
		    		cloud.posY = pos.getY();
		    		cloud.posZ = pos.getZ();
		    		world.spawnEntity(cloud);
		    	}
		    	overload++;
			} else {
				if(overload > 0) overload = 0;
			}*/
			ItemStack catalystA = inventory.getStackInSlot(0);
			ItemStack catalystB = inventory.getStackInSlot(2);
			NBTTagCompound tagA = null;
			NBTTagCompound tagB = null;
			double damageA = 100;
			double damageB = 100;
			if (catalystA.getItem() instanceof ItemCatalyst && catalystB.getItem() instanceof ItemCatalyst) {
				LeafiaColor col = new LeafiaColor(calcAvgHex(
						((ItemCatalyst) catalystA.getItem()).getColor(),
						((ItemCatalyst) catalystB.getItem()).getColor()
				));
				colorCatalyst = col.toInARGB();
				tagA = catalystA.getTagCompound();
				tagB = catalystB.getTagCompound();
				if (tagA == null) {
					tagA = new NBTTagCompound();
					catalystA.setTagCompound(tagA);
				}
				if (tagB == null) {
					tagB = new NBTTagCompound();
					catalystB.setTagCompound(tagB);
				}
				damageA = tagA.getDouble("damage");
				damageB = tagB.getDouble("damage");
				color = col.lerp(new LeafiaColor(
						world.rand.nextFloat(), world.rand.nextFloat(), world.rand.nextFloat()
				), Math.pow(Math.max(damageA / 100, damageB / 100), 2)).toInARGB();
				hasCore = true;
			} else {
				color = 0;
				hasCore = false;
			}
			expellingSpk = 0;

			if (inventory.getStackInSlot(1).getItem() instanceof ItemAMSCore /*&& tanks[0].getFluid() != null && tanks[1].getFluid() != null*/) {
				if (tagA != null && tagB != null) {
					meltingPoint = Math.min(1500000, Math.min(ItemCatalyst.getMelting(catalystA), ItemCatalyst.getMelting(catalystB)));

					double corePower = getCorePower();
					double coreHeatMod = getCoreHeat();
					double coreInefficiency = getCoreFuel();
					//1 SPK = 5,000HE
					long catalystPower = ItemCatalyst.getPowerAbs(catalystA) + ItemCatalyst.getPowerAbs(catalystB);
					float catalystPowerMod = ItemCatalyst.getPowerMod(catalystA) * ItemCatalyst.getPowerMod(catalystB);
					float catalystHeatMod = ItemCatalyst.getHeatMod(catalystA) * ItemCatalyst.getHeatMod(catalystB);
					float catalystFuelMod = ItemCatalyst.getFuelMod(catalystA) * ItemCatalyst.getFuelMod(catalystB);
					double catalystPowerSPK = catalystPower / 5000d;
					FluidStack f1s = tanks[0].getFluid();
					FluidStack f2s = tanks[1].getFluid();
					Fluid f1;
					Fluid f2;
					if (f1s == null) f1 = ModForgeFluids.DEUTERIUM; else f1 = f1s.getFluid();
					if (f2s == null) f2 = ModForgeFluids.TRITIUM; else f2 = f2s.getFluid();
					double fill0 = tanks[0].getFluidAmount()/(double)tanks[0].getCapacity();
					double fill1 = tanks[0].getFluidAmount()/(double)tanks[0].getCapacity();
					double fuelPower = ModFluidProperties.getDFCEfficiency(f1) * ModFluidProperties.getDFCEfficiency(f2);

					double tempRatio = temperature/meltingPoint;
					double energyRatio = containedEnergy/maxEnergy;

					ticks++;
					//LeafiaDebug.debugLog(world,"incomingSpk: "+incomingSpk);

					Tracker._startProfile(this,"NeoTick");
					{
						potentialGain = energyMod; //Math.max(0,Math.pow(energyMod,0.75));
						if (temperature >= 100) {
							double randRange = Math.pow(tempRatio,0.65)*10;
							potentialGain += world.rand.nextDouble()*randRange/getStabilizationDivAlt()/getStabilizationDiv() + Math.pow(collapsing,0.666)*66;
						}
					}
					{ // i wanted to redo everything this sucks ASS
						//containedEnergy += incomingSpk;
						double combustionPotential = Math.pow(energyRatio,0.25);
						int consumption = (int)Math.ceil(Math.pow(incomingSpk*catalystFuelMod*getCoreFuel(),0.5));//(int)(combustionPotential*100);
						Tracker._tracePosition(this,pos.up(3),"incomingSpk: ",incomingSpk);
						tanks[0].drain(consumption,true);
						tanks[1].drain(consumption,true);
						//Tracker._tracePosition(this,pos.east(6),"combustionPotential: "+combustionPotential,"cons: "+consumption);

						//Tracker._tracePosition(this,pos.down(3),"potAdd: "+potAdd,"potSub: "+potSub,"","potMul:"+potMul,"Total: "+potFinal);
						//Tracker._tracePosition(this,pos.down(4),"potAbsorb: "+potAbsorb);
						double boost = catalystPowerMod*energyMod;
						double deltaEnergy = (Math.pow(Math.pow(incomingSpk, 0.666/2) + 1, 0.666/2) - 1) * 6.666 / 3 * Math.pow(1.2,potentialGain);
						double addition0 = (deltaEnergy*corePower+Math.pow(Math.max(0,incomingSpk-deltaEnergy),0.9))*boost*fill0*fill1;
						//containedEnergy += Math.pow(Math.min(temperature,10000)/100,1.2)*potentialRelease*boost*fill0*fill1;
						double addition1 = Math.pow(Math.min(temperature,10000)/100,0.75)*corePower*potentialGain*boost*fill0*fill1*fuelPower/666 * Math.pow(0.9,potentialGain);
						addition0 = Math.max(addition0,0);
						addition1 = Math.max(addition1,0);
						containedEnergy = Math.min(Math.min(containedEnergy+addition0,failsafeLevel)+addition1,failsafeLevel);
						//containedEnergy += Math.pow(Math.min(tempRatio,3),3)*100;
						double tgtTemp = temperature;
						//tgtTemp = Math.max(0,temperature-(1-energyRatio)*100*(Math.pow(tempRatio,2)+0.001));
						//temperature += Math.pow(deltaEnergy,0.1*Math.pow(potentialRelease,0.8))*100*catalystHeatMod*coreHeatMod;

						//temperature = Math.pow(temperature,0.9);
						tgtTemp += Math.pow(deltaEnergy*666*catalystHeatMod,2/(1+stabilization))*(1-tempRatio/2)*coreHeatMod*Math.pow(potentialGain,0.25);//Math.pow(deltaEnergy,0.1)*5*Math.pow(potentialRelease,1.5);
						double rdc = 1-energyRatio;
						tgtTemp -= Math.pow(Math.abs(rdc),0.5)*Math.signum(rdc)*tempRatio;//*10;



						Tracker._tracePosition(this,pos.down(3),"containedEnergy: ",containedEnergy);
						Tracker._tracePosition(this,pos.down(4),"deltaEnergy: ",deltaEnergy);

						double absorbDiv = 0.001;
						for (TileEntityCoreReceiver absorber : absorbers)
							absorbDiv += absorber.level;

						gainedEnergy = containedEnergy;

						double absorbed = Math.pow(containedEnergy,0.75+energyRatio*0.25)/20*absorbDiv;
						double transferred = 0;
						for (TileEntityCoreReceiver absorber : absorbers) {
							long absorb = (long)(absorbed/absorbDiv*absorber.level*1000_000);
							containedEnergy -= absorb/1000_000d;
							transferred += absorb/1000_000d;
							double val = (catalystPower*Math.pow(tempRatio,0.1)+incomingSpk*2000_000)/absorbDiv*absorber.level;
							val = Math.min(val,Long.MAX_VALUE);
							absorber.joules += absorb + (long)val;
						}
						expellingSpk = transferred;
						expelTicks[Math.floorMod(ticks, 20)] = expellingSpk;
						containedEnergy = Math.max(containedEnergy,0);
						double targetEnergy = Math.pow(containedEnergy,0.99);
						//double deltaSubEnergy = containedEnergy-targetEnergy; what's the point??
						//Tracker._tracePosition(this,pos.down(5),"deltaSubEnergy: ",deltaSubEnergy);
						//containedEnergy -= deltaSubEnergy*Math.pow(Math.max(0,1-energyRatio),0.25);

						containedEnergy += collapsing*1_000_000;

						containedEnergy = Math.min(containedEnergy,failsafeLevel);

						tgtTemp -= Math.max(0,Math.pow(temperature/meltingPoint,4)*temperature*getStabilizationDivAlt())*(0.5+(Math.pow(Math.abs(rdc),0.01)*Math.signum(rdc))/2);
						tgtTemp = Math.min(Math.max(tgtTemp,0),5000000);
						double deltaTemp = tgtTemp-temperature;
						if (!finalPhase) {
							double limit = 1000+world.rand.nextInt(1000) + world.rand.nextDouble();
							temperature += Math.min(Math.pow(Math.abs(deltaTemp),0.5)*Math.signum(deltaTemp),limit);
						} else {
							temperature = temperature + world.rand.nextInt(5000)+10000 + world.rand.nextDouble();
						}
						temperature = Math.max(temperature,0);
						/*

						for (TileEntityCoreReceiver absorber : absorbers) {
							absorber.joules += (long) (expelling * 333_333) / absorbers.size();
						}
						expellingSpk = expelling;
						expelTicks[Math.floorMod(ticks, 20)] = expelling;
						 */
						if (shockCooldown > 0) shockCooldown--;
						double energyPerShock = 3_000_000*0.75;
						if (containedEnergy >= 1_000_000*(world.rand.nextInt(150)+6.66)+0.5 && shockCooldown <= 0) {
							double count = Math.ceil(containedEnergy/energyPerShock);
							for (int i = 0; i < Math.pow(count,0.25); i++) shock();
							world.playSound(null,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,HBMSoundEvents.mus_sfx_a_lithit,SoundCategory.BLOCKS,6.66f,1+(float)world.rand.nextGaussian()*0.1f);
							PacketDispatcher.wrapper.sendToAllAround(
									new CommandLeaf.ShakecamPacket(new String[]{
											"type=smooth",
											"preset=RUPTURE",
											"duration/4",
											"blurDulling*2",
											"intensity/2",
											"range=50"
									}).setPos(pos),
									new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 100)
							);
							PacketDispatcher.wrapper.sendToAllAround(
									new CommandLeaf.ShakecamPacket(new String[]{
											"type=smooth",
											"preset=QUAKE",
											"duration/2",
											"intensity/4",
											"range=100"
									}).setPos(pos),
									new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 150)
							);
							containedEnergy = Math.max(containedEnergy-count*energyPerShock,0);
							shockCooldown = 100-(int)(90*Math.pow(collapsing,1.75));
						}
					}
					Tracker._endProfile(this);
					/*
					if (false) {

						double desiredBonus = Math.sqrt(incomingSpk);
						bonus += (desiredBonus - bonus) * 0.01;

						containedEnergy += (Math.pow(Math.pow(incomingSpk, 0.666) + 1, 0.666) - 1) * 6.666 / 3;
						double multiplier = (1 + world.rand.nextGaussian() * 0.75 / getStabilizationDivAlt())
								* (1 + Math.sin(ticks * Math.PI) * 0.75 / getStabilizationDivAlt());

						double tempChange = Math.pow(Math.pow(containedEnergy,
								MathHelper.clampedLerp(0.44, 0.666, Math.pow(1 / getStabilizationDivAlt(), 0.666))) / 100, 0.666) * 100 * 7
								* Math.max(1 - Math.pow(temperature / meltingPoint, 2) / 1.25 * Math.max(1 - Math.pow(containedEnergy / 10000, 1), 0.1), 0.5) //Math.pow(internalEnergy/corePower,6.666/getStabilizationDiv()/getStabilizationDiv())*corePower //(Math.pow(internalEnergy/(meltingPoint/2d),6.666/getStabilizationDiv()/getStabilizationDiv())*(meltingPoint/2d))
								/ 20 * coreHeatMod * multiplier;
						tempChange = tempChange - Math.signum(tempChange) * Math.max(Math.abs(tempChange) - 10, 0) / 5;
						temperature += tempChange;
						temperature *= 0.99 * Math.pow(1 / getStabilizationDiv(), 0.0025);


						//tanks[0].drain(demand, true);
						//tanks[1].drain(demand, true);


						containedEnergy += Math.pow(temperature, 0.666) * getEnergyBase(corePower) * corePower / 65 //Math.pow(temperature*getEnergyBase()/corePower,0.5)*corePower
								/ 20 / Math.pow(getStabilizationDiv(), 2) * energyMod * multiplier * fuelPower;

						if (Double.isNaN(temperature)) temperature = failsafeLevel; // fuck you
						if (Double.isNaN(containedEnergy)) containedEnergy = failsafeLevel; // fuck you
						if (Double.isNaN(tempChange)) tempChange = 0; // fuck you
						if (temperature < -100) temperature = failsafeLevel; // for real fuck you piece of dipshti
						temperature = Math.min(failsafeLevel, temperature); // for technical reasons there has to be a limit
						containedEnergy = Math.min(failsafeLevel, containedEnergy); // for technical reasons there has to be a limit

						double taxes = Math.max(0, containedEnergy - (temperature / meltingPoint) * 1000);
						//containedEnergy -= taxes/14;
						//containedEnergy = getEnergyCurved(internalEnergy)*energyMod;
						{
							double sin = Math.sin(ticks / 20d * Math.PI);
							double abs = Math.abs(sin);
							double sign = Math.signum(sin);
							double wave = (1 - Math.pow(abs, 0.5)) * (1 + Math.sin(ticks / 20d * Math.PI * 22) * 0.7);//sign*Math.pow(abs,0.5);
							double craziness = Math.pow(Math.abs(Math.sin(ticks / 20d * Math.PI * 0.666)), 6.666) * 25 / getStabilizationDiv() / getStabilizationDivAlt();
							craziness *= Math.max(0, Math.pow(containedEnergy / 10000, 4) - 0.2);
							//double waveScaled = wave/2/getStabilizationDiv()+0.5;
							potentialGain = getEnergyBase(corePower) / 200 * energyMod + wave * containedEnergy / 2000 / getStabilizationDivAlt() + craziness; //waveScaled * containedEnergy/200;
							potentialGain = Math.abs(potentialGain); // overflows are expected
						}
						double energyBefore = containedEnergy;
						if (world.rand.nextInt(101) >= 50 / getStabilizationDivAlt())
							containedEnergy = Math.max(containedEnergy - (containedEnergy *potentialGain/ 20 + taxes / 14) * absorbers.size(), 0);
						//containedEnergy = getEnergyCurved(internalEnergy)*energyMod;
						double expelBonus = Math.min(bonus * bonus, bonus * bonus *potentialGain/ 20 * absorbers.size());
						bonus -= Math.sqrt(expelBonus);

						double expelling = energyBefore - containedEnergy + expelBonus;
						for (TileEntityCoreReceiver absorber : absorbers) {
							absorber.joules += (long) (expelling * 333_333) / absorbers.size();
						}
						expellingSpk = expelling;
						expelTicks[Math.floorMod(ticks, 20)] = expelling;

					}*/
					double timeToMeltdown = 10;
					double timeToRegen = 30;
					tagA.setDouble("damage", MathHelper.clamp(damageA
									+ (temperature >= ItemCatalyst.getMelting(catalystA) ? 5 / timeToMeltdown : -5 / timeToRegen),
							0, 100
					));
					tagB.setDouble("damage", MathHelper.clamp(damageB
									+ (temperature >= ItemCatalyst.getMelting(catalystB) ? 5 / timeToMeltdown : -5 / timeToRegen),
							0, 100
					));
				}
			}
			if ((damageA >= 100 || damageB >= 100) && temperature > 100 || finalPhase) {
				EntityNukeExplosionMK3 exp = null;
				if (jammerPos != null) {
					if (!(world.getBlockState(jammerPos).getBlock() instanceof MachineFieldDisturber))
						jammerPos = null;
				}
				if (explosionIn < 10 || jammerPos == null) { // stand by
					exp = new EntityNukeExplosionMK3(world);
					exp.posX = pos.getX();
					exp.posY = pos.getY();
					exp.posZ = pos.getZ();
					exp.destructionRange = 20+(int)Math.pow(temperature,0.4);
					exp.speed = 25;
					exp.coefficient = 1.0F;
					exp.waste = false;
				}
				if (jammerPos == null) {
					if (overloadTimer <= 20*6) {
						if (overloadTimer == 0) {
							LeafiaPacket._start(this)
									.__write(packetKeys.PLAY_SOUND.key, 2)
									.__sendToAll();
						}
						overloadTimer++;
					} else {
						world.playSound(null, pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.BLOCKS, 100000.0F, 1.0F);

						world.playSound(null, pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f, HBMSoundEvents.actualexplosion, SoundCategory.BLOCKS, 50.0F, 1.0F);
						PacketDispatcher.wrapper.sendToAllAround(
								new CommandLeaf.ShakecamPacket(new String[]{
										"type=smooth",
										"preset=RUPTURE",
										"blurDulling*2",
										"speed*1.5",
										"duration/2",
										"range=300"
								}).setPos(pos),
								new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 400)
						);
						LeafiaColor col = new LeafiaColor(colorCatalyst);
						DFCBlastParticle blast = new DFCBlastParticle((float)col.red,(float)col.green,(float)col.blue);
						blast.emit(new Vec3d(pos).add(0.5,0.5,0.5),new Vec3d(0,1,0),world.provider.getDimension(),200);

						ExplosionNT nt = new ExplosionNT(world,null,pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,50);
						nt.maxExplosionResistance = 20;
						nt.iterationLimit = 150;
						nt.ignoreBlockPoses.add(pos);
						nt.explode();

						if (!EntityNukeExplosionMK3.isJammed(this.world, exp)) {
							destroyed = true;
							world.spawnEntity(exp);
							EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, exp.destructionRange);
							cloud.posX = pos.getX();
							cloud.posY = pos.getY();
							cloud.posZ = pos.getZ();
							world.spawnEntity(cloud);
						} else {
							jammerPos = EntityNukeExplosionMK3.lastDetectedJammer;
							if (explosionIn < 0) {
								explosionIn = 120;
								explosionClock = System.currentTimeMillis();
								LeafiaPacket._start(this)
										.__write(packetKeys.PLAY_SOUND.key, 0)
										.__sendToAll();
							}
						}
					}
				}
				if (jammerPos != null) {
					boolean tick = true;
					MinecraftServer server = world.getMinecraftServer();
					if (server != null) {
						//LeafiaDebug.debugLog(world, "isSinglePlayer: " + server.isSinglePlayer());
						//LeafiaDebug.debugLog(world, "isServerInOnlineMode: " + server.isServerInOnlineMode());
						//LeafiaDebug.debugLog(world, "isDedicatedServer: " + server.isDedicatedServer());
						//LeafiaDebug.debugLog(world, TextFormatting.GOLD + "Time Left: " + explosionIn);
						if (!server.isDedicatedServer())
							tick = !Minecraft.getMinecraft().isGamePaused();
					}
					if (tick) {
						long time = System.currentTimeMillis();
						explosionIn = Math.max(explosionIn - (time - explosionClock) / 1000d, 0);
						collapsing = MathHelper.clamp(1-explosionIn/120,0,1);
						explosionClock = time;
						if (explosionIn <= 15 && !finalPhase) {
							finalPhase = true;
							PacketDispatcher.wrapper.sendToAllAround(
									new CommandLeaf.ShakecamPacket(new String[]{
											"type=smooth",
											"preset=RUPTURE",
											"blurDulling*2",
											"speed*1.5",
											"duration/2",
											"range=300"
									}).setPos(pos),
									new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 400)
							);
							PacketDispatcher.wrapper.sendToAllAround(
									new CommandLeaf.ShakecamPacket(new String[]{
											"type=smooth",
											"preset=QUAKE",
											"blurDulling*4",
											"speed*3",
											"duration=40",
											"range=300"
									}).setPos(pos),
									new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 400)
							);
							LeafiaPacket._start(this)
									.__write(packetKeys.PLAY_SOUND.key, 3)
									.__sendToAll();

							ExplosionNT nt = new ExplosionNT(world,null,pos.getX()+0.5f, pos.getY()+0.5f, pos.getZ()+0.5f,150);
							nt.iterationLimit = 150;
							nt.overrideResolution(24);
							nt.ignoreBlockPoses.add(pos);
							nt.addAttrib(ExAttrib.FIRE);
							nt.addAttrib(ExAttrib.DFC_FALL);
							nt.explode();
						}
						if (explosionIn <= 0 && exp != null) {
							PacketDispatcher.wrapper.sendToAllAround(
									new CommandLeaf.ShakecamPacket(new String[]{
											"type=smooth",
											"preset=PWR_NEAR",
											"duration*2",
											"intensity*1.5",
											"range=200"
									}).setPos(pos),
									new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 300)
							);
							world.playSound(null, pos, HBMSoundEvents.dfc_explode, SoundCategory.BLOCKS, 100, 1);
							destroyed = true;
							world.spawnEntity(exp);
							EntityCloudFleijaRainbow cloud = new EntityCloudFleijaRainbow(world, exp.destructionRange);
							cloud.posX = pos.getX();
							cloud.posY = pos.getY();
							cloud.posZ = pos.getZ();
							world.spawnEntity(cloud);
						}
					}
				}
			} else {
				if (explosionIn >= 0) {
					jammerPos = null;
					explosionIn = -1;
					collapsing = 0;
					LeafiaPacket._start(this)
							.__write(packetKeys.PLAY_SOUND.key, 1)
							.__sendToAll();
				} else if (overloadTimer > 0) {
					LeafiaPacket._start(this)
							.__write(packetKeys.PLAY_SOUND.key, 1)
							.__sendToAll();
					overloadTimer = 0;
				}
			}

			expellingEnergy = 0;
			for (double energy : expelTicks)
				expellingEnergy += energy;
			wasBoosted = incomingSpk > 0;
			incomingSpk = 0;
			energyMod = 1;
			absorbers.clear();

			if (temperature > 100) {
				vaporization();
			}
			/*
			NBTTagCompound data = new NBTTagCompound();
			data.setString("tank0", tanks[0].getFluid() == null ? "HBM_EMPTY" : tanks[0].getFluid().getFluid().getName());
			data.setString("tank1", tanks[1].getFluid() == null ? "HBM_EMPTY" : tanks[1].getFluid().getFluid().getName());
			data.setInteger("fill0", tanks[0].getFluidAmount());
			data.setInteger("fill1", tanks[1].getFluidAmount());
			data.setInteger("field", field);
			data.setInteger("heat", heat);
			data.setInteger("color", color);
			data.setBoolean("hasCore", hasCore);
			networkPack(data, 250);
			*/
			//PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, tanks), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			NBTTagCompound fluidA = new NBTTagCompound();
			NBTTagCompound fluidB = new NBTTagCompound();
			tanks[0].writeToNBT(fluidA);
			tanks[1].writeToNBT(fluidB);
			Integer coreId = null;
			try {
				coreId = Cores.valueOf(inventory.getStackInSlot(1).getItem().getRegistryName().getPath()).ordinal();
			} catch (IllegalArgumentException | NullPointerException ignored) {
			} // fuck you im lazy
			LeafiaPacket._start(this)
					.__write(packetKeys.TANK_A.key, fluidA)
					.__write(packetKeys.TANK_B.key, fluidB)

					.__write(packetKeys.TEMP.key, temperature)
					.__write(packetKeys.STABILIZATION.key, stabilization)
					.__write(packetKeys.CONTAINED.key, containedEnergy/* gainedEnergy + bonus * bonus*/) // wtf?
					.__write(packetKeys.EXPELLING.key, expellingEnergy)
					.__write(packetKeys.POTENTIAL.key,potentialGain)

					.__write(packetKeys.EXPEL_TICK.key, expellingSpk)
					.__write(packetKeys.MAXIMUM.key, meltingPoint)

					.__write(packetKeys.COLOR.key, color)
					.__write(packetKeys.COLOR_CATALYST.key, colorCatalyst)
					.__write(packetKeys.CORE_TYPE.key, coreId)

					.__write(packetKeys.JAMMER.key, jammerPos)
					.__write(packetKeys.COLLAPSE.key, collapsing)

					.__write(packetKeys.HASCORE.key,hasCore)

					.__sendToAffectedClients();

			heat = 0;
			stabilization = 0;
			if (this.collapsing > 0) {
				List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null,LeafiaHelper.getAABBRadius(LeafiaHelper.getBlockPosCenter(this.pos),getPullRange()));
				for (Entity e : list) {
					if (!(e instanceof EntityFallingBlock))
						pull(e);
				}
			}
			this.markDirty();
			/*testDebug++;
			if (testDebug > 30) {
				testDebug = 0;
				shock();
				shock();
				shock();
			}*/
		} else {
			ticks++;
			client_maxDial = world.rand.nextDouble() * 0.08 + 0.9;

			if (client_sfx != null) {
				if (temperature >= 100 && !sfxPlaying) {
					sfxPlaying = true;
					client_sfx.startSound();
				} else if (temperature < 100 && sfxPlaying) {
					sfxPlaying = false;
					client_sfx.stopSound();
				}
			}
			if (collapsing > 0.666)
				pullLocal();
			for (DFCShock shock : dfcShocks) {
				shock.ticks++;
			}
			while (!dfcShocks.isEmpty()) {
				if (dfcShocks.get(0).ticks > 4)
					dfcShocks.remove(0);
				else break;
			}
			if (temperature > 100) {
				if (client_type == Cores.ams_core_eyeofharmony) {
					float r = (color>>16&255)/255F;
					float g = (color>>8&255)/255F;
					float b = (color&255)/255F;
					float scale = (float) Math.log(temperature/50+1);
					ParticleEyeOfHarmony fx = new ParticleEyeOfHarmony(world,pos,r,g,b,scale);
					Minecraft.getMinecraft().effectRenderer.addEffect(fx);
					angle = angle + lightRotateSpeed;
					if (angle > 360)
						angle -= 360;
				}
			}
			ringSpinSpeed = 360/20f;
			if (120-collapsing*120 <= 15)
				finalPhase = true;
			if (collapsing > 0.95) {
				double percent = (collapsing-0.95)/0.05;
				ringSpinSpeed += 3600/20f*(float)percent;
			}
			if (finalPhase) {
				ringAlpha = MathHelper.clamp(ringAlpha+0.025f,0,1);
			}
			ringAngle = MathHelper.positiveModulo(ringAngle+ringSpinSpeed,360);
		}
	}
	public float ringSpinSpeed = 360/20f;
	public float ringAngle = 0;
	public float ringAlpha = 0;
	@SideOnly(Side.CLIENT)
	void pullLocal() {
		pull(Minecraft.getMinecraft().player);
		Vec3d p = LeafiaHelper.getBlockPosCenter(pos);
		VacuumInstance vacuum = new VacuumInstance(p,0,10,getPullRange(),0.1);
		p = new FiaMatrix(p).rotateY(world.rand.nextDouble()*360).rotateX(world.rand.nextDouble()*360).translate(0,0,world.rand.nextDouble()*getPullRange()).position;

		ParticleFleijaVacuum fx = new ParticleFleijaVacuum(
				Minecraft.getMinecraft().world,
				p.x,
				p.y,
				p.z,
				world.rand.nextFloat()*2 + 2,
				world.rand.nextFloat()*0.1f+0.1f,
				vacuum
		);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}
	int shockCooldown = 0;
	//int testDebug = 0;
	public void shock() {
		double rlen = 1;
		double length = 0.5;
		Vec3d core = LeafiaHelper.getBlockPosCenter(pos);
		Vec3d p0 = LeafiaHelper.getBlockPosCenter(pos);
		Vec3d p1 = new FiaMatrix(p0).rotateY(world.rand.nextDouble()*360).rotateX(world.rand.nextDouble()*360).translate(0,0,length+world.rand.nextDouble()*rlen).position;
		DFCShockPacket packet = new DFCShockPacket();
		packet.pos = pos;
		packet.poses0.add(p0);
		packet.poses0.add(p1);
		Tracker._startProfile(this,"shock");
		for (int i = 0; i < 25; i++) {
			p0 = p1;
			p1 = new FiaMatrix(p1,core).translate(world.rand.nextGaussian()*2,world.rand.nextGaussian()*2,length+world.rand.nextDouble()*rlen).position;
			RayTraceResult res = Library.leafiaRayTraceBlocks(world,p0,p1,false,true,false);
			if (res != null && res.hitVec != null) {
				p1 = res.hitVec;
				packet.poses0.add(p1);
				world.newExplosion(null,p1.x,p1.y,p1.z,world.rand.nextFloat()*5+2,true,true);
				break;
			}
			packet.poses0.add(p1);
		}
		for (int i = 0; i < packet.poses0.size()-1; i++) {
			Tracker._traceLine(this,packet.poses0.get(i),packet.poses0.get(i+1),i);
		}
		Tracker._endProfile(this);
		LeafiaCustomPacket.__start(packet).__sendToAll();
	}
	public static class DFCShock {
		public final List<Vec3d> poses;
		public int ticks = 0;
		public DFCShock(List<Vec3d> poses) {
			this.poses = poses;
		}
	}
	public List<DFCShock> dfcShocks = new ArrayList<>();
	public static class DFCShockPacket implements LeafiaCustomPacketEncoder {
		BlockPos pos;
		List<Vec3d> poses0 = new ArrayList<>();
		@Override
		public void encode(LeafiaBuf buf) {
			buf.writeVec3i(pos);
			buf.writeByte(poses0.size());
			for (Vec3d pos : poses0) {
				buf.writeFloat((float)pos.x);
				buf.writeFloat((float)pos.y);
				buf.writeFloat((float)pos.z);
			}
		}
		@Nullable
		@Override
		@SideOnly(Side.CLIENT)
		public Consumer<MessageContext> decode(LeafiaBuf buf) {
			List<Vec3d> poses = new ArrayList<>();
			TileEntity te = Minecraft.getMinecraft().world.getTileEntity(new BlockPos(buf.readVec3i()));
			int leng = buf.readByte();
			for (int i = 0; i < leng; i++)
				poses.add(new Vec3d(buf.readFloat(),buf.readFloat(),buf.readFloat()));
			return (context)->{
				if (te == null) return;
				if (!(te instanceof TileEntityCore)) throw new LeafiaDevFlaw("TileEntity is not a TileEntityCore");
				for (int i = 0; i < poses.size(); i++) {
					LeafiaDebug.debugPos(Minecraft.getMinecraft().world,new BlockPos(poses.get(i)),1,0xFFD800,Integer.toString(i));
				}
				TileEntityCore core = (TileEntityCore)te;
				core.dfcShocks.add(new DFCShock(poses));
			};
		}
	}
	/*
	@Override
	public void networkUnpack(NBTTagCompound data) {
		String s0 = data.getString("tank0");
		String s1 = data.getString("tank1");
		if("HBM_EMPTY".equals(s0)){
			tanks[0].setFluid(null);
		} else {
			tanks[0].setFluid(new FluidStack(FluidRegistry.getFluid(s0), data.getInteger("fill0")));
		}
		if("HBM_EMPTY".equals(s1)){
			tanks[1].setFluid(null);
		} else {
			tanks[1].setFluid(new FluidStack(FluidRegistry.getFluid(s1), data.getInteger("fill1")));
		}
		field = data.getInteger("field");
		heat = data.getInteger("heat");
		color = data.getInteger("color");
		hasCore = data.getBoolean("hasCore");
	}*/

	private double getPullRange() {
		return 150; // constant for now
	}
	private double getPull(Entity e) {
		Vec3d p = new Vec3d(pos).add(0.5,0.5,0.5);
		double distance = p.distanceTo(e.getPositionVector());
		if (distance > getPullRange()) return 0;
		double pull = MathHelper.clamp(1-distance/getPullRange(),0,1);
		pull = Math.pow(pull,1.5);
		return pull*0.1*Math.pow(Math.max(0,collapsing/0.666-1)*2,0.5);
	}
	public void pull(Entity e) {
		//if (e instanceof EntityPlayer && ((EntityPlayer) e).isCreative()) return;
		double pull = getPull(e);
		if (pull <= 0) return;
		Vec3d p = new Vec3d(pos).add(0.5,0.5,0.5);
		Vec3d lookAt = new FiaMatrix(e.getPositionVector(),p).frontVector;
		e.addVelocity(lookAt.x*pull,lookAt.y*pull,lookAt.z*pull);
	}

	boolean isFixTool(Entity e) {
		if (e instanceof EntityItem)
			return ((EntityItem)e).getItem().getItem() == ModItems.fix_tool;
		return false;
	}

	boolean isSurvivalFixTool(Entity e) {
		if (e instanceof EntityItem)
			return ((EntityItem)e).getItem().getItem() == ModItems.fix_survival;
		return false;
	}

	private void vaporization() {

		double scale = (int) Math.log(temperature / 50 + 1) * 1.25 / 4 + 0.5;

		int range = (int) (scale * 4);
		List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - range + 0.5, pos.getY() - range + 0.5, pos.getZ() - range + 0.5, pos.getX() + range + 0.5, pos.getY() + range + 0.5, pos.getZ() + range + 0.5));

		for (Entity e : list) {
			if (isFixTool(e)) continue;
			boolean isPlayer = e instanceof EntityPlayer;
			if (!(isPlayer && ArmorUtil.checkForHazmat((EntityPlayer) e))) {
				if (!(Library.isObstructed(world, pos.getX() + 0.5, pos.getY() + 0.5 + 6, pos.getZ() + 0.5, e.posX, e.posY + e.getEyeHeight(), e.posZ))) {
					if (!isPlayer || (isPlayer && !((EntityPlayer) e).capabilities.isCreativeMode))
						e.attackEntityFrom(ModDamageSource.dfc, (int) (this.temperature / 100));
					e.setFire(3);
				}
			}
			if (isPlayer) {
				AdvancementManager.grantAchievement(((EntityPlayer) e), AdvancementManager.progress_dfc);
			}
		}
/*
		List<Entity> list2 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX() - scale + 0.5, pos.getY() - scale + 0.5, pos.getZ() - scale + 0.5, pos.getX() + scale + 0.5, pos.getY() + scale + 0.5, pos.getZ() + scale + 0.5));

		for (Entity e : list2) {
			boolean isPlayer = e instanceof EntityPlayer;
			if (!(isPlayer && ArmorUtil.checkForHaz2((EntityPlayer) e))) {
				if (!isPlayer || (isPlayer && !((EntityPlayer) e).capabilities.isCreativeMode))
					e.attackEntityFrom(ModDamageSource.amsCore, (int) (this.temperature / 10));
				e.setFire(3);
			}
		}*/
		List<Entity> list3 = world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(pos.getX()+0.4,pos.getY()+0.4,pos.getZ()+0.4,pos.getX()+0.6,pos.getY()+0.6,pos.getZ()+0.6));
		if (collapsing > 0) {
			for (Entity e : list3) {
				if (isFixTool(e) || isSurvivalFixTool(e)) {
					e.setEntityInvulnerable(false);
					e.setDead();
					world.createExplosion(null,pos.getX()+0.5,pos.getY()+0.5,pos.getZ()+0.5,20,false);
					if (wasBoosted || isSurvivalFixTool(e) && world.rand.nextInt(100) < 80-lastStabilizers*10) {
						world.playSound(null,pos,HBMSoundEvents.crucifix_fail,SoundCategory.BLOCKS,20,1);
						continue;
					}
					temperature = 0;
					containedEnergy = 0;
					tanks[0].drain(1000000000,true);
					tanks[1].drain(1000000000,true);
					world.playSound(null,pos,HBMSoundEvents.crucifix,SoundCategory.BLOCKS,20,1);
					continue;
				}
				e.attackEntityFrom(ModDamageSource.dfcMeltdown,(int) (this.temperature / 10));
				e.setFire(10);
				if (!(e instanceof EntityLivingBase))
					e.setDead();
			}
		}
	}

	public int getFieldScaled(int i) {
		return (field * i) / 100;
	}

	public int getHeatScaled(int i) {
		return (heat * i) / 100;
	}

	public boolean isReady() {

		if (getCorePower() == 0)
			return false;

		if (color == 0)
			return false;

		if (tanks[0].getFluid() == null || tanks[1].getFluid() == null)
			return false;

		if (ModFluidProperties.getDFCEfficiency(tanks[0].getFluid().getFluid()) <= 0 || ModFluidProperties.getDFCEfficiency(tanks[1].getFluid().getFluid()) <= 0)
			return false;

		return true;
	}

	//100 emitter watt = 10000 joules = 1 heat = 10mB burned
	public long burn(long joules) {

		//check if a reaction can take place
		if (!isReady())
			return joules;

		int demand = (int) Math.sqrt((double) joules);

		long powerAbs = ItemCatalyst.getPowerAbs(inventory.getStackInSlot(0)) + ItemCatalyst.getPowerAbs(inventory.getStackInSlot(2));
		float powerMod = ItemCatalyst.getPowerMod(inventory.getStackInSlot(0)) * ItemCatalyst.getPowerMod(inventory.getStackInSlot(2));
		float heatMod = ItemCatalyst.getHeatMod(inventory.getStackInSlot(0)) * ItemCatalyst.getHeatMod(inventory.getStackInSlot(2));
		float fuelMod = ItemCatalyst.getFuelMod(inventory.getStackInSlot(0)) * ItemCatalyst.getFuelMod(inventory.getStackInSlot(2));

		demand = (int) (getCoreFuel() * demand * fuelMod);

		//check if the reaction has enough valid fuel
		if (tanks[0].getFluidAmount() < demand || tanks[1].getFluidAmount() < demand)
			return joules;

		heat += (int) (getCoreHeat() * heatMod * Math.ceil((double) joules / 10000D));

		Fluid f1 = tanks[0].getFluid().getFluid();
		Fluid f2 = tanks[1].getFluid().getFluid();

		tanks[0].drain(demand, true);
		tanks[1].drain(demand, true);

		long powerOutput = (long) Math.max(0, (powerMod * joules * getCorePower() * ModFluidProperties.getDFCEfficiency(f1) * ModFluidProperties.getDFCEfficiency(f2)) + powerAbs);
		if (powerOutput > 0 && heat == 0)
			heat = 1;
		return powerOutput;
	}

	//TODO: move stats to the AMSCORE class
	//Alcater: ok did that
	public int getCorePower() {
		return ItemAMSCore.getPowerBase(inventory.getStackInSlot(1));
	}

	public float getCoreHeat() {
		return ItemAMSCore.getHeatBase(inventory.getStackInSlot(1));
	}

	public float getCoreFuel() {
		return ItemAMSCore.getFuelBase(inventory.getStackInSlot(1));
	}

	public int calcAvgHex(int h1, int h2) {

		int r1 = ((h1 & 0xFF0000) >> 16);
		int g1 = ((h1 & 0x00FF00) >> 8);
		int b1 = ((h1 & 0x0000FF) >> 0);

		int r2 = ((h2 & 0xFF0000) >> 16);
		int g2 = ((h2 & 0x00FF00) >> 8);
		int b2 = ((h2 & 0x0000FF) >> 0);

		int r = (((r1 + r2) / 2) << 16);
		int g = (((g1 + g2) / 2) << 8);
		int b = (((b1 + b2) / 2) << 0);

		return r | g | b;
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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("tanks"))
			FFUtils.deserializeTankArray(compound.getTagList("tanks", 10), tanks);
		temperature = compound.getDouble("temperature");
		containedEnergy = compound.getDouble("energy");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setTag("tanks", FFUtils.serializeTankArray(tanks));
		compound.setDouble("temperature",temperature);
		compound.setDouble("energy",containedEnergy);
		return super.writeToNBT(compound);
	}

	@Override
	public String getPacketIdentifier() {
		return "dfcore";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onReceivePacketLocal(byte key, Object value) {
		for (packetKeys pkt : packetKeys.values()) {
			if (key == pkt.key) {
				switch (pkt) {
					case TEMP:
						temperature = (double) value;
						break;
					case STABILIZATION:
						stabilization = (double) value;
						break;
					case MAXIMUM:
						meltingPoint = (int) value;
						break;
					case CONTAINED:
						containedEnergy = (double) value;
						break;
					case EXPELLING:
						expellingEnergy = (double) value;
						break;
					case POTENTIAL:
						potentialGain = (double) value;
						break;
					case TANK_A:
						tanks[0].readFromNBT((NBTTagCompound) value);
						break;
					case TANK_B:
						tanks[1].readFromNBT((NBTTagCompound) value);
						break;
					case EXPEL_TICK:
						expellingSpk = (double) value;
						break;
					case COLOR:
						color = (int) value;
						break;
					case COLOR_CATALYST:
						colorCatalyst = (int) value;
						break;
					case JAMMER:
						jammerPos = (BlockPos) value;
						break;
					case CORE_TYPE:
						Cores lastCore = client_type;
						Integer id = (Integer) value;
						if (id == null) client_type = null;
						else client_type = Cores.values()[id];
						if (client_type != lastCore) {
							if (client_sfx != null) {
								client_sfx.stopSound();
								client_sfx = null;
								sfxPlaying = false;
							}
							if (client_type != null) {
								client_sfx = MainRegistry.proxy.getLoopedSound(
										client_type.sfx, SoundCategory.BLOCKS,
										pos.getX(), pos.getY(), pos.getZ(),
										1, 1
								).setCustomAttentuation(client_type.attentuationFunction);
							}
						}
						break;
					case PLAY_SOUND:
						if (value == null) break;
						int type = (int) value;
						if (type == 0 || type == 1) {
							if (meltdownSFX != null) meltdownSFX.stopSound();
							if (explosionsSFX != null) explosionsSFX.stopSound();
							if (type == 0 && meltdownSFX != null) meltdownSFX.startSound();
							if (type == 1 && extinguishSFX != null) extinguishSFX.startSound();
							if (type == 1) LeafiaDebug.debugLog(world,"STOP: 1");
						} else if (type == 2 && overloadSFX != null)
							overloadSFX.startSound();
						else if (type == 3 && explosionsSFX != null) {
							explosionsSFX.startSound();
							finalPhase = true;
						}
						break;
					case COLLAPSE:
						collapsing = (double)value;
						break;
					case HASCORE:
						hasCore = (boolean)value;
						break;
				}
			}
		}
	}

	@Override
	public void onReceivePacketServer(byte key, Object value, EntityPlayer plr) {

	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {

	}

	@Override
	public double affectionRange() {
		return 300;
	}
}
