package com.leafia.contents.machines.reactors.pwr;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.fluid.BlockLiquidCorium;
import com.hbm.explosion.ExplosionNT;
import com.hbm.explosion.ExplosionNT.ExAttrib;
import com.hbm.explosion.ExplosionNukeGeneric;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.HeatRecipes;
import com.hbm.items.ModItems;
import com.hbm.items.machine.ItemForgeFluidIdentifier;
import com.hbm.lib.HBMSoundEvents;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.PacketDispatcher;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.util.Tuple.Pair;
import com.leafia.CommandLeaf;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodItem;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRChannelBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRConductorBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlTE;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.contents.machines.reactors.pwr.blocks.wreckage.PWRMeshedWreck;
import com.leafia.contents.machines.reactors.pwr.blocks.wreckage.PWRMeshedWreck.Erosion;
import com.leafia.contents.machines.reactors.pwr.debris.PWRDebrisEntity;
import com.leafia.dev.LeafiaUtil;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.lang.reflect.Array;
import java.util.*;
import java.util.Map.Entry;

public class PWRData implements ITickable, IFluidHandler, ITankPacketAcceptor, LeafiaPacketReceiver {
	public BlockPos corePos;
	public FluidTank[] tanks;
	public Fluid[] tankTypes;
	public String coolantName = ModForgeFluids.COOLANT.getName();
	public int compression = 0;
	//public double heat = 20;
	public int lastTickDrain;
	public int coriums = 0;
	public double masterControl = 0.25;
	public final Map<String, Double> controlDemand = new HashMap<>();
	public int toughness = 16_000;

	PWRComponentBlock getPWRBlock(World world, BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof PWRComponentBlock)
			return (PWRComponentBlock) block;
		return null;
	}

	PWRComponentBlock getPWRComponent(World world, BlockPos pos) {
		PWRComponentBlock block = getPWRBlock(world, pos);
		if (block == null) return null;
		return block.tileEntityShouldCreate(world, pos) ? block : null;
	}

	PWRComponentEntity getPWREntity(World world, BlockPos pos) {
		PWRComponentBlock pwr = getPWRBlock(world, pos);
		if (pwr != null)
			return pwr.getPWR(world, pos);
		return null;
	}

	public Set<BlockPos> members = new HashSet<>();
	public Set<BlockPos> controls = new HashSet<>();
	public Set<BlockPos> fuels = new HashSet<>();
	public LeafiaSet<BlockPos> projection = new LeafiaSet<>();

	public void onDiagnosis(World world) {
		if (world.isRemote) {

		} else {
			int slots = 0;
			for (BlockPos pos : projection) {
				PWRComponentBlock block = getPWRComponent(world, pos);
				if (block != null) {
					if (block instanceof PWRElementBlock) {
						slots++;
					}
				}
			}
			if (remoteSize != slots) {
				remoteContainer = new ItemStackHandler(slots);
				remoteSize = slots;
			}
			for (FluidTank tank : tanks) {
				if (tank.getFluidAmount() > tank.getCapacity())// whoops
					tank.drain(tank.getFluidAmount() - tank.getCapacity(), true);
			}
			addDataToPacket(LeafiaPacket._start(companion), this).__sendToAffectedClients();
		}
	}

	public Pair<LeafiaSet<BlockPos>, LeafiaSet<BlockPos>> getProjectionFuelAndControlPositions() {
		Pair<LeafiaSet<BlockPos>, LeafiaSet<BlockPos>> output = new Pair<>(new LeafiaSet<>(), new LeafiaSet<>());
		for (BlockPos pos : projection) {
			Block block = getWorld().getBlockState(pos).getBlock();
			if (block instanceof PWRElementBlock)
				output.getA().add(pos);
			else if (block instanceof PWRControlBlock)
				output.getB().add(pos);
		}
		return output;
	}

	public BlockPos terminal_toGlobal(IBlockState terminal, BlockPos terminalPos, BlockPos pos) {
		EnumFacing face = terminal.getValue(BlockHorizontal.FACING).getOpposite();
		Vec3d lookVector = new Vec3d(face.getDirectionVec()); // amazingly, Vec3i does not has .scale() method LMAO
		Vec3d rightVector = lookVector.crossProduct(new Vec3d(0, 1, 0));
		return new BlockPos(new Vec3d(terminalPos).add(0.5, 0.5 + pos.getY(), 0.5).add(lookVector.scale(-pos.getZ())).add(rightVector.scale(pos.getX())));
	}

	public BlockPos terminal_toLocal(IBlockState terminal, BlockPos terminalPos, BlockPos pos) {
		EnumFacing face = terminal.getValue(BlockHorizontal.FACING).getOpposite();
		BlockPos relative = pos.subtract(terminalPos);
		switch (face) { // IM STUPID OK??
			case NORTH:
				return relative;
			case SOUTH:
				return new BlockPos(-relative.getX(), relative.getY(), -relative.getZ());
			case WEST:
				return new BlockPos(-relative.getZ(), relative.getY(), relative.getX());
			case EAST:
				return new BlockPos(relative.getZ(), relative.getY(), -relative.getX());
			default:
				throw new LeafiaDevFlaw("PWR Terminals should only face sideways, got " + face.getName() + " how the fuck does this even happen");
		}
	}

	public TileEntity companion;

	public ItemStackHandler resourceContainer = new ItemStackHandler(3) {
		@Override
		protected void onContentsChanged(int slot) {
			super.onContentsChanged(slot);
			companion.markDirty();
			if (slot == 0)
				updateCoolantTypeFromItem(getStackInSlot(slot));
		}
	};
	void updateCoolantTypeFromItem(ItemStack stack) {
		if (stack.getItem() instanceof ItemForgeFluidIdentifier) {
			Fluid type = ItemForgeFluidIdentifier.getType(stack);
			if (updateCoolantType(type.getName()))
				coolantName = type.getName();
		}
	}
	boolean updateCoolantType(String coolantName) {
		Fluid coolant = FluidRegistry.getFluid(coolantName);
		if (coolant != null) {
			Pair<Fluid,Fluid> hots = getBoiledCoolant(coolant);
			if (hots != null) {
				if (tankTypes[0] != coolant)
					tanks[0].drain(tanks[0].getCapacity(), true);
				if (tankTypes[1] != hots.getA())
					tanks[1].drain(tanks[1].getCapacity(), true);
				if (tankTypes[2] != hots.getB())
					tanks[2].drain(tanks[2].getCapacity(), true);
				tankTypes[0] = coolant;
				tankTypes[1] = hots.getA();
				tankTypes[2] = hots.getB();
				resizeTanks(lastChannels,lastConductors);
					/*
					Fluid hotter = hot;
					while (true) {
						Fluid hottest = HeatRecipes.getBoilFluid(hotter);
						if (hottest == null) break;
						else {
							hotter = hottest;
							if (hottest.isGaseous()) {
								if (tankTypes[2] != hottest)
									tanks[2].drain(tanks[2].getCapacity(), true);
								tankTypes[2] = hottest;
							}
						}
					}*/
				return true;
			}
		}
		return false;
	}
	public ItemStackHandler remoteContainer = new ItemStackHandler(0);
	int remoteSize = 0;

	public void invalidate(World world) {
		if (!(world instanceof WorldServer)) return;
		BlockPos checkPos = companion.getPos();
		((PWRComponentEntity) companion).assignCore(null);
		world.getMinecraftServer().addScheduledTask(() -> {
			if (world.getBlockState(checkPos).getBlock() instanceof BlockLiquidCorium)
				this.explode(world, null,null);
		});
	}

	public PWRData(TileEntity entity) {
		tanks = new FluidTank[]{
				new FluidTank(128_000),
				new FluidTank(128_000),
				new FluidTank(16_000),

				new FluidTank(512_000),
				new FluidTank(256_000)
		};
		tankTypes = new Fluid[]{
				ModForgeFluids.COOLANT,
				ModForgeFluids.HOTCOOLANT,
				ModForgeFluids.MALCOOLANT,

				FluidRegistry.WATER,
				ModForgeFluids.STEAM
		};
		this.companion = entity;
		this.corePos = companion.getPos();
		//valid = !entity.isInvalid(); im fucking stupid
	}

	public World getWorld() {
		return this.companion.getWorld();
	}

	public void onUpdateCompression() {
		if (compression == 0) {
			if (tankTypes[4] != ModForgeFluids.STEAM)
				tanks[4].drain(tanks[4].getCapacity(), true);
			tankTypes[4] = ModForgeFluids.STEAM;
		} else if (compression == 1) {
			if (tankTypes[4] != ModForgeFluids.HOTSTEAM)
				tanks[4].drain(tanks[4].getCapacity(), true);
			tankTypes[4] = ModForgeFluids.HOTSTEAM;
		} else if (compression == 2) {
			if (tankTypes[4] != ModForgeFluids.SUPERHOTSTEAM)
				tanks[4].drain(tanks[4].getCapacity(), true);
			tankTypes[4] = ModForgeFluids.SUPERHOTSTEAM;
		}
	}

	protected void resizeTank(FluidTank tank, int newCapacity) {
		int drain = tank.getFluidAmount() - newCapacity;
		if (drain > 0)
			tank.drain(drain, true);
		tank.setCapacity(newCapacity);
	}

	public int lastChannels = 0;
	public int lastConductors = 0;
	public void resizeTanks(int channels, int conductors) {
		resizeTank(tanks[0], 3200 * channels); // coolant
		resizeTank(tanks[1], 3200 * (int)(channels/(tankTypes[1].isGaseous() ? 1.5 : 1))); // hot coolant
		resizeTank(tanks[2], toughness/(tankTypes[1].isGaseous() ? 10 : 1)); // emergency buffer
		resizeTank(tanks[3], 25600 * conductors); // water
		resizeTank(tanks[4], 12800 * conductors); // steam
	}

	Pair<Fluid,Fluid> getBoiledCoolant(Fluid f) {
		Fluid hot = HeatRecipes.getBoilFluid(f);
		if (hot == null) return null;
		Fluid secondhot = HeatRecipes.getBoilFluid(f);
		while (true) {
			Fluid hotter = HeatRecipes.getBoilFluid(hot);
			if (hotter != null) {
				secondhot = hot;
				hot = hotter;
			} else break;
		}
		return new Pair<>(secondhot,hot);
	};

	public PWRData readFromNBT(NBTTagCompound nbt) {
		nbt = nbt.getCompoundTag("data");
		if (nbt.hasKey("compression"))
			compression = nbt.getInteger("compression");
		onUpdateCompression();
		tankTypes[0] = ModForgeFluids.COOLANT;
		tankTypes[1] = ModForgeFluids.HOTCOOLANT;
		tankTypes[2] = ModForgeFluids.MALCOOLANT;
		if (nbt.hasKey("coolantName")) {
			coolantName = nbt.getString("coolantName");
			updateCoolantType(coolantName);
		}
		//if (nbt.hasKey("heat"))
		//	heat = nbt.getDouble("heat");
		if (nbt.hasKey("tanks"))
			FFUtils.deserializeTankArray(nbt.getTagList("tanks", 10), tanks);
		if (nbt.hasKey("remoteContainerSize"))
			remoteSize = nbt.getInteger("remoteContainerSize");
		if (nbt.hasKey("resourceContainer"))
			resourceContainer.deserializeNBT(nbt.getCompoundTag("resourceContainer"));
		if (nbt.hasKey("projectionMap")) {
			projection.clear();
			NBTTagList nbtjection = nbt.getTagList("projectionMap", 11/*INT[], refer to NBTBase*/);
			for (NBTBase item : nbtjection) {
				NBTTagIntArray array = (NBTTagIntArray) item;
				int[] coords = array.getIntArray();
				projection.add(new BlockPos(coords[0], coords[1], coords[2]));
			}
		}
		if (nbt.hasKey("controlMaster"))
			masterControl = nbt.getDouble("controlMaster");
		if (nbt.hasKey("controlDemand"))
			readControlPositions(nbt.getCompoundTag("controlDemand"));
		return this;
	}

	public NBTTagCompound writeToNBT(NBTTagCompound mainCompound) {
		NBTTagCompound nbt = new NBTTagCompound();
		//nbt.setDouble("heat", heat);
		if (!ModForgeFluids.COOLANT.getName().equals(coolantName))
			nbt.setString("coolantName",coolantName);
		nbt.setInteger("compression", compression);
		nbt.setTag("tanks", FFUtils.serializeTankArray(tanks));
		nbt.setInteger("remoteContainerSize", remoteSize);
		nbt.setTag("resourceContainer", resourceContainer.serializeNBT());
		NBTTagList nbtjection = new NBTTagList();
		for (BlockPos pos : projection) {
			nbtjection.appendTag(new NBTTagIntArray(new int[]{pos.getX(), pos.getY(), pos.getZ()}));
		}
		nbt.setTag("projectionMap", nbtjection);

		nbt.setDouble("controlMaster", masterControl);
		nbt.setTag("controlDemand", writeControlPositions());

		mainCompound.setTag("data", nbt);
		return mainCompound;
	}

	public void readControlPositions(NBTTagCompound nbt) {
		controlDemand.clear();
		for (String key : nbt.getKeySet())
			controlDemand.put(key, nbt.getDouble(key));
	}

	public void resyncControls() {
		LeafiaPacket._start(companion).__write(27, writeFullControls()).__sendToAffectedClients();
	}

	public NBTTagCompound writeFullControls() {
		NBTTagList nbt = new NBTTagList();
		for (BlockPos pos : controls) {
			TileEntity entity = getWorld().getTileEntity(pos);
			if (entity instanceof PWRControlTE) {
				PWRControlTE control = (PWRControlTE) entity;
				nbt.appendTag(control.writeControlDateToNBT());
			}
		}
		NBTTagCompound result = new NBTTagCompound();
		result.setTag("controls", nbt);
		return result;
	}

	public NBTTagCompound writeControlPositions() {
		Set<String> existingNames = new LeafiaSet<>();
		for (BlockPos pos : controls) {
			TileEntity entity = getWorld().getTileEntity(pos);
			if (entity instanceof PWRControlTE) {
				PWRControlTE control = (PWRControlTE) entity;
				existingNames.add(control.name);
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		for (Entry<String, Double> entry : controlDemand.entrySet()) {
			if (existingNames.contains(entry.getKey()))
				nbt.setDouble(entry.getKey(), entry.getValue());
		}
		return nbt;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if (tags.length != 5) {
			return;
		} else {
			for (int i = 0; i < 5; i++) {
				tanks[i].readFromNBT(tags[i]);
			}
		}
	}

	boolean valid = false;
	float timeToDrainMalcoolant = 15;
	@SideOnly(Side.CLIENT)
	public int warnTicks = 0;
	public double stressTimer = 300;

	protected boolean inputValidForTank(int tank, int slot) {
		if (!resourceContainer.getStackInSlot(slot).isEmpty() && tanks[tank] != null) {
			if (resourceContainer.getStackInSlot(slot).getItem() == ModItems.fluid_barrel_infinite || isValidFluidForTank(tank, FluidUtil.getFluidContained(resourceContainer.getStackInSlot(slot)))) {
				return true;
			}
		}
		return false;
	}

	private boolean isValidFluidForTank(int tank, FluidStack stack) {
		if (stack == null || tanks[tank] == null)
			return false;
		return stack.getFluid() == tankTypes[tank];
	}

	public double spendAccum = 0;

	@Override
	public void update() {
		if (!valid) {
			if (!companion.isInvalid()) {
				valid = true;
				Block block = companion.getBlockType();
				if (block instanceof PWRComponentBlock) {
					this.corePos = companion.getPos();
					((PWRComponentBlock) block).beginDiagnosis(getWorld(), companion.getPos(), companion.getPos());
				} else
					companion.invalidate(); // you're coming with me
			}
			return;
		}
		if (getWorld().isRemote) {
			//Minecraft.getMinecraft().player.sendMessage(new TextComponentString("hello... here at "+companion.getPos()+".. ,,uwu,,"));
			// The debug code above is a serious sign of mental illness.
			warnTicks = Math.floorMod(warnTicks + 1, 8);
		} else {
			//for (EntityPlayer player : getWorld().playerEntities) {
			//    player.sendMessage(new TextComponentString("" + getWorld().isRemote + "! Im at " + companion.getPos()));
			//}
			if (this.inputValidForTank(3, 1))
				FFUtils.fillFromFluidContainer(resourceContainer, tanks[3], 1, 2);

			if (coriums > 0)
				spendCoolant(Math.pow(coriums * 2727, 0.414), null);
			if (tanks[2].getFluidAmount() > 0) {
				int decr = Math.min(tanks[2].getFluidAmount(), tanks[2].getCapacity() / Math.round(timeToDrainMalcoolant * 20));
				if (tanks[1].getCapacity() - tanks[1].getFluidAmount() >= decr * 4) {
					tanks[1].fill(new FluidStack(tankTypes[1], decr * 4), true);
					tanks[2].drain(decr, true);
				}
				// directly copied from zirnox port lmfao
				double stress = tanks[2].getFluidAmount() / (double) tanks[2].getCapacity();
				stressTimer -= Math.pow(stress, 0.9) * 64;
				if (stressTimer <= 0) {
					BlockPos pos = (BlockPos) members.toArray()[getWorld().rand.nextInt(members.size())];
					getWorld().playSound(null, pos.getX() + 0.5, pos.getY() + 2.5, pos.getZ() + 0.5, HBMSoundEvents.stressSounds[getWorld().rand.nextInt(7)], SoundCategory.BLOCKS, (float) MathHelper.clampedLerp(0.25, 14, Math.pow(stress, 4)), 1.0F);
				}
			}
			if (tanks[3].getCapacity() > 0) {
				int conversionRate = 8;
				int consumption = (int) Math.round(Math.pow(tanks[1].getFluidAmount() / (double) Math.max(tanks[3].getCapacity(), 1) * 4, 0.4) * 80 * conversionRate);
				FluidStack stack = tanks[1].drain(consumption / conversionRate, false);
				FluidStack stack2 = tanks[3].drain(consumption, false);
				if (stack != null && stack2 != null) {
					int boilAmt = Math.min(stack.amount * conversionRate, stack2.amount);
					int division = (int) Math.pow(10, compression);
					int filled = tanks[4].fill(new FluidStack(tankTypes[4], boilAmt * 10 / division), true);
					tanks[1].drain(Math.min(boilAmt, filled * division / 10) / conversionRate, true);
					tanks[3].drain(Math.min(boilAmt, filled * division / 10), true);
				}
			}
			LeafiaPacket._start(companion).__write(30, new int[]{
					compression,
					tanks[0].getCapacity(),
					tanks[1].getCapacity(),
					tanks[2].getCapacity(),
					tanks[3].getCapacity(),
					tanks[4].getCapacity(),
					tanks[0].getFluidAmount(),
					tanks[1].getFluidAmount(),
					tanks[2].getFluidAmount(),
					tanks[3].getFluidAmount(),
					tanks[4].getFluidAmount()
			}).__sendToAffectedClients();
		}
	}

	int boilingAccum = 0;

	public void spendCoolant(double cooled, @Nullable ItemStack stack) {
		double drainD = cooled * 8;
		int drain = (int) Math.floor(drainD);///12500*tanks[0].getCapacity());

		double accum = drainD - drain;
		spendAccum += accum;
		int add = (int) Math.floor(spendAccum);
		spendAccum -= add;
		drain += add;

		FluidStack fs = tanks[0].drain(drain, true);
		if (fs != null) {
			int drained = fs.amount;
			lastTickDrain = drained;
			for (int tank = 1; drained > 0; tank++) {
				switch (tank) {
					case 1: {
						int filled = tanks[tank].fill(new FluidStack(tankTypes[tank], drained), true);
						drained -= filled;
					}
					break;
					case 2: {
						int fill = drained / 4;
						boilingAccum += Math.floorMod(drained, 4);
						int fillAdd = boilingAccum / 4;
						boilingAccum -= fillAdd;
						int filled = tanks[tank].fill(new FluidStack(tankTypes[tank], fill + fillAdd), true);
						drained -= filled * 4;
					}
					break;
					default:
						if (tanks[2].getFluidAmount() >= tanks[2].getCapacity())
							explode(getWorld(), stack, null); // Blowout
						return;
				}
			}
		}
		/*
		if (tanks[0].getFluidAmount() > 0) {
			if ()
				hotType = 2;
			tanks[hotType].fill(new FluidStack(tankTypes[hotType],drain),true);
			//TODO: make it so both hot coolant buffer and emergency buffer are scaled by blast resistances of surrounding blocks
			//TODO: lower blast res. would result in easier failure but less destructive
			//TODO: resistance would be determined by average resistance of surrounding blocks with little more distribution to the stronger blocks
			//TODO: weak blocks surrounded by strong blocks wouldn't explode this way as individual resistances are not taken into account
			if (tanks[2].getFluidAmount() >= tanks[2].getCapacity())
		}*/
	}

	boolean exploded = false;
	World explodeWorld = null;

	Set<BlockPos> growMembers(Set<BlockPos> set) { // have this highly laggy retarded Solution
		Set<BlockPos> offsets = new HashSet<>();
		for (BlockPos member : set) {
			for (EnumFacing facing : EnumFacing.values()) {
				BlockPos offset = member.add(facing.getXOffset(), facing.getYOffset(), facing.getZOffset());
				//if (!members.contains(offset)) {
				//if (explodeWorld.getBlockState(offset).getBlock().getExplosionResistance(null) >= 50)
				offsets.add(offset);
				//}
			}
		}
		for (BlockPos offset : offsets) {
			members.add(offset); // fuck you ConcurrentModificationException
		}
		return offsets;
	}

	double signedPow(double x, double y) {
		return Math.pow(Math.abs(x), y) * Math.signum(x);
	}

	protected class PWRExplosion {
		final World world;
		final Vec3d centerPoint;
		final int minX;
		final int minY;
		final int minZ;
		final int maxX;
		final int maxY;
		final int maxZ;

		public PWRExplosion(World world, Vec3d centerPoint, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
			this.world = world;
			this.centerPoint = centerPoint;
			this.minX = minX;
			this.minY = minY;
			this.minZ = minZ;
			this.maxX = maxX;
			this.maxY = maxY;
			this.maxZ = maxZ;
		}

		protected void explodeLv1() {
			for (BlockPos member : members) {
				Block block = world.getBlockState(member).getBlock();
				if (block instanceof PWRChannelBlock || block instanceof PWRConductorBlock) {
					world.setBlockToAir(member);
					for (EnumFacing face : EnumFacing.values()) {
						BlockPos offs = member.offset(face);
						if (world.isValid(offs) && world.getBlockState(offs).getBlock() instanceof PWRComponentBlock) {
							if (world.rand.nextInt(5) == 0)
								world.setBlockToAir(offs);
						}
					}
				}
			}
			world.createExplosion(null, centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, 8.0F, true);
		}

		protected void explodeLv2() {
			for (BlockPos member : members) {
				if (world.getBlockState(member).getBlock() instanceof PWRComponentBlock)
					world.setBlockToAir(member);
			}
			world.newExplosion(null, centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, 24.0F, true, true);
		}

		protected IBlockState getBlockLv3(BlockPos pos, LeafiaMap<BlockPos, IBlockState> map, World world) {
			if (map.containsKey(pos))
				return map.get(pos);
			else
				return world.getBlockState(pos);
		}

		protected void explodeLv3() {
			double reactorSize = (maxX - minX + 1 + 2 + 4 + maxY - minY + 1 + 2 + 4 + maxZ - minZ + 1 + 2 + 4) / 3d;
			for (EntityPlayer plr : world.playerEntities) {
				if (plr.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d) {
					plr.sendMessage(new TextComponentString("PWR Exploded"));
					plr.sendMessage(new TextComponentString("  Size: " + reactorSize));
					plr.sendMessage(new TextComponentString("  x: " + minX + " : " + maxX));
					plr.sendMessage(new TextComponentString("  y: " + minY + " : " + maxY));
					plr.sendMessage(new TextComponentString("  z: " + minZ + " : " + maxZ));
				}
			}
			// mmm this is gonna be crispy computer
			growMembers(growMembers(growMembers(growMembers(members))));

			// HashSet is one giant dick. It randomly doesn't detect its OWN element by contains(). You just wasted my precious time a LOT
			// Don't believe me? Try replacing all "motherfucker" occurrences to original "members" (which is a Set) and boom IT BREAKS SOMEHOW
			List<BlockPos> motherfucker = new ArrayList<>();
			motherfucker.addAll(members);
			//world.newExplosion(null,centerPoint.x+0.5,centerPoint.y+0.5,centerPoint.z+0.5,24*(toughness/15_000f),true,true);
			List<BlockPos> placeWrecks = new ArrayList<>();
			List<BlockPos> vaporized = new ArrayList<>();
			List<BlockPos> remains = new ArrayList<>();
			List<BlockPos> allDebris = new ArrayList<>();
			Vec3d pressure = new Vec3d(0, 0, 0);
			for (BlockPos member : motherfucker) {
				Vec3d ray = new Vec3d(member).add(0.5, 0.5, 0.5).subtract(centerPoint);
				if (!world.getBlockState(member).getMaterial().isSolid()) {
					pressure = pressure.add(ray.scale(2d / motherfucker.size()));
					continue;
				}
				IBlockState state = world.getBlockState(member);
				Block block = state.getBlock();
				if (block instanceof BlockFire) continue;
				if (block instanceof BlockLiquidCorium) continue;
				if (block instanceof PWRElementBlock) {
					//world.newExplosion(null,member.getX()+0.5,member.getY()+0.5,member.getZ()+0.5,11,true,true);
					//world.setBlockState(member,ModBlocks.corium_block.getDefaultState());
					world.setBlockToAir(member);
					continue;
				}
				//Block fuckyou = Blocks.BLACK_GLAZED_TERRACOTTA;

				boolean destroyed = false;
				int counter = 0;
				int threshold = 7 + world.rand.nextInt(3);
				for (double s = 0; s <= 2; s += 0.2) {
					BlockPos rayHit = new BlockPos(centerPoint.add(ray.scale(Math.pow(s, 1.5) + 1))/*.add(ray.normalize().scale(1.732/1.5))*/);
					//member;//new BlockPos(centerPoint.add(ray.scale(s) ));
					if (!world.isValid(rayHit) || world.isAirBlock(rayHit) || motherfucker.contains(rayHit)) {
					/*
					switch (counter+2) {
						case 0: fuckyou = Blocks.LIGHT_BLUE_GLAZED_TERRACOTTA; break;
						case 1: fuckyou = Blocks.BLUE_GLAZED_TERRACOTTA; break;
						case 2: fuckyou = Blocks.GREEN_GLAZED_TERRACOTTA; break;
						case 3: fuckyou = Blocks.LIME_GLAZED_TERRACOTTA; break;
						case 4: fuckyou = Blocks.YELLOW_GLAZED_TERRACOTTA; break;
						case 5: fuckyou = Blocks.ORANGE_GLAZED_TERRACOTTA; break;
						case 6: fuckyou = Blocks.RED_GLAZED_TERRACOTTA; break;
					}*/
						if (++counter >= threshold) {
							//world.setBlockToAir(member);
							vaporized.add(member);
							destroyed = true;
							pressure = pressure.add(ray.scale(1d / motherfucker.size()));
							break;
						}
					}
				}
				//if (motherfucker.contains(member)) { The line that proves the fact HashSet sometimes doesn't detect its own elements
				// This looks like always "true" if branch, except it is not for HashSet. Bruh.
				//	fuckyou = Blocks.LIME_GLAZED_TERRACOTTA;
				//}
				if (!destroyed) {
					// myaaaaa
					if (LeafiaUtil.isSolidVisibleCube(world.getBlockState(member)) || block instanceof PWRComponentBlock)
						remains.add(member);
					pressure = pressure.subtract(ray.scale(1d / motherfucker.size()));
				/*
				if (block instanceof PWRComponentBlock) {
					world.setBlockState(member,((world.rand.nextInt(3) == 0) ? ModBlocks.pribris_burning : ModBlocks.pribris).getDefaultState());
				} else if (block.isFullBlock(state) && !block.isPassable(world,member)) {
					// almost broken
					if (counter >= threshold-2) {
						//world.setBlockState(member,ModBlocks.pribris_radiating.getDefaultState()); // Test
						placeWrecks.add(member);
					}
				}*/
					//world.setBlockState(member,fuckyou.getDefaultState());
				}
			}
			LeafiaMap<BlockPos, IBlockState> placeMap = new LeafiaMap<>();
			LeafiaSet<BlockPos> antiPlaceSet = new LeafiaSet<>();
			List<PWRDebrisEntity> entitiesToSpawn = new ArrayList<>();
			for (BlockPos pos : vaporized) {
				if (placeWrecks.contains(pos)) continue; // Somehow
				boolean converted = false;
				for (EnumFacing face : EnumFacing.values()) {
					if (remains.contains(pos.offset(face))) {
						if (LeafiaUtil.isSolidVisibleCube(world.getBlockState(pos)) || world.getBlockState(pos).getBlock() instanceof PWRComponentBlock) {
							placeWrecks.add(pos);
							allDebris.add(pos);
							placeMap.put(pos, world.getBlockState(pos));
							converted = true;
						}
						break;
					}
				}
				if (!converted) {
					Block block = world.getBlockState(pos).getBlock();
					if (!(block instanceof IFluidBlock) && LeafiaUtil.isSolidVisibleCube(world.getBlockState(pos))) {
						if (world.getBlockState(pos).getBlockHardness(world, pos) >= 1) {
							Vec3d ray = new Vec3d(pos).add(0.5, 0.5, 0.5).subtract(centerPoint);
							PWRDebrisEntity debris = new PWRDebrisEntity(world, pos.getX() + 0.5D, pos.getY() + 0.5, pos.getZ() + 0.5D, world.getBlockState(pos));
							debris.motionX = signedPow(ray.x, 1) / reactorSize * (1 + world.rand.nextDouble() * 4) + signedPow(pressure.x, 0.8) / 2;
							debris.motionY = signedPow(ray.y, 1) / reactorSize * (1 + world.rand.nextDouble() * 4) + signedPow(pressure.y, 0.8) / 2;
							debris.motionZ = signedPow(ray.z, 1) / reactorSize * (1 + world.rand.nextDouble() * 4) + signedPow(pressure.z, 0.8) / 2;
							entitiesToSpawn.add(debris);
						}
					}
					world.setBlockToAir(pos);
				}
			}
			// TODO: add cam shake

			for (BlockPos pos : remains) {
				placeMap.put(pos, world.getBlockState(pos));
				world.setBlockState(pos, Blocks.BEDROCK.getDefaultState());
			}
			ExplosionNT nt = new ExplosionNT(world, null, centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, 24 * (toughness / 15_000f));
			nt.addAttrib(ExAttrib.FIRE);
			nt.addAttrib(ExAttrib.NODROP);
			nt.overrideResolution(32);
			nt.explode();
			double shakeIntensity = toughness / 10_000d;
			PacketDispatcher.wrapper.sendToAllAround(
					new CommandLeaf.ShakecamPacket(new String[]{
							"type=smooth",
							"preset=PWR_NEAR",
							"bloomDulling*" + (0.8125 - Math.pow(shakeIntensity, 0.5) / 20) / 0.75,
							"duration*" + Math.pow(shakeIntensity, 0.5),
							"range=" + reactorSize * 4
					}).setPos(new BlockPos(centerPoint)),
					new NetworkRegistry.TargetPoint(world.provider.getDimension(), centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, reactorSize * 4.25)
			);
			PacketDispatcher.wrapper.sendToAllAround(
					new CommandLeaf.ShakecamPacket(new String[]{
							"type=smooth",
							"preset=PWR_FAR",
							"duration*" + Math.pow(shakeIntensity, 0.5),
							"range=" + reactorSize * 9
					}).setPos(new BlockPos(centerPoint)),
					new NetworkRegistry.TargetPoint(world.provider.getDimension(), centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, reactorSize * 9.25)
			);

			for (BlockPos pos : remains) {
				boolean buried = true;
				for (EnumFacing face : EnumFacing.values()) {
					if (vaporized.contains(pos.offset(face)) || !LeafiaUtil.isSolidVisibleCube(getBlockLv3(pos.offset(face), placeMap, world))) {
						buried = false;
						break;
					}
				}
				if (!buried)
					allDebris.add(pos);
			}
			for (BlockPos member : allDebris) {
				IBlockState state = getBlockLv3(member, placeMap, world);
				Block block = state.getBlock();
				SoundType soundType = block.getSoundType();
				Material material = block.getMaterial(state);
				Vec3d ray = new Vec3d(member).add(0.5, 0.5, 0.5).subtract(centerPoint);

				if (block instanceof PWRControlBlock) {
					world.setBlockState(member, ModBlocks.block_electrical_scrap.getDefaultState());
					antiPlaceSet.add(member);
					//continue;
				}
				double heatBase = MathHelper.clamp(Math.pow(MathHelper.clamp(1 - ray.length() / (reactorSize / 2), 0, 1), 0.45) * 8, 0, 7);
				int heat = (int) heatBase;
				int heatRand = world.rand.nextInt(3);
				if (heatRand == 1)
					heat = (int) Math.round(heatBase);
				else if (heatRand == 2)
					heat = (int) Math.ceil(heatBase);
				boolean noErosion = true;
				if (placeWrecks.contains(member)) {
					noErosion = false;
					EnumFacing face = EnumFacing.UP;
					double absX = Math.abs(ray.x);
					double absY = Math.abs(ray.y);
					double absZ = Math.abs(ray.z);
					if ((absX > absY) && (absX > absZ))
						face = (ray.x > 0) ? EnumFacing.WEST : EnumFacing.EAST;
					else if ((absY > absX) && (absY > absZ))
						face = (ray.y > 0) ? EnumFacing.DOWN : EnumFacing.UP;
					else if ((absZ > absX) && (absZ > absY))
						face = (ray.z > 0) ? EnumFacing.NORTH : EnumFacing.SOUTH;
					EnumFacing most = null;
					int spaces = -1;
					int reliableSurround = 0;
					boolean surrounded = false;
					for (int i = 0; i < 7; i++) {
						EnumFacing curFace = (i > 0) ? EnumFacing.values()[i - 1] : face;
						if (i > 0 && curFace.equals(face)) continue; // skip if duplicate
						if (!getBlockLv3(member.offset(curFace, -1), placeMap, world).isFullBlock()) continue;
						if (placeWrecks.contains(member.offset(curFace, -1))) continue;
						if (!getBlockLv3(member.offset(curFace), placeMap, world).getBlock().isPassable(world, member.offset(curFace)))
							continue;
						if (placeWrecks.contains(member.offset(curFace))) continue;
						int mySpaces = 0;
						EnumFacing[] sides = null;
						switch (curFace.getAxis()) {
							case X:
								sides = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH};
								break;
							case Y:
								sides = EnumFacing.HORIZONTALS;
								break;
							case Z:
								sides = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.EAST, EnumFacing.WEST};
								break;
						}
						if (sides == null) continue;
						int surround = 0;
						int reliable = 0;
						for (EnumFacing side : sides) {
							if (placeWrecks.contains(member.offset(side)))
								surround++;
							else if (getBlockLv3(member.offset(side), placeMap, world).isFullBlock()) {
								surround++;
								reliable++;
							}
							if (LeafiaUtil.isSolidVisibleCube(getBlockLv3(member.offset(curFace).offset(side), placeMap, world)))
								continue;
							if (placeWrecks.contains(member.offset(curFace).offset(side))) continue;
							mySpaces++;
						}
						if (mySpaces > spaces) {
							spaces = mySpaces;
							most = curFace;
							surrounded = surround >= 4;
							reliableSurround = reliable;
						}
					}
					if (most != null) {
						// TODO: make RUBBLE model
						// RUBBLE should be used when (most != face)
						// Actually use it when (!surrounded)
						// TODO: use SLIGHT if verysurrounded
						Erosion erosion = PWRMeshedWreck.Erosion.NORMAL;
						if (reliableSurround >= 2) erosion = Erosion.SLIGHT;
						else if (!surrounded) erosion = Erosion.RUBBLE;
						if (material.equals(Material.IRON)) {
							if (soundType.equals(SoundType.STONE))
								ModBlocks.PWR.wreck_stone.create(world, member, most, state, erosion, heat);
							else
								ModBlocks.PWR.wreck_metal.create(world, member, most, state, erosion, heat);
							antiPlaceSet.add(member);
						} else
							noErosion = true;
					}
				}
				if (noErosion) {
					Block[] sellafieldLevels = new Block[]{
							ModBlocks.sellafield_slaked,
							ModBlocks.sellafield_0,
							ModBlocks.sellafield_1,
							ModBlocks.sellafield_2,
							ModBlocks.sellafield_3,
							ModBlocks.sellafield_4,
							ModBlocks.sellafield_core
					};
					if (heat > 0) {
						if (block instanceof BlockGrass)
							world.setBlockState(member, ModBlocks.waste_earth.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockGravel)
							world.setBlockState(member, ModBlocks.waste_gravel.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockDirt || block == Blocks.FARMLAND)
							world.setBlockState(member, ModBlocks.waste_dirt.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockSnow)
							world.setBlockState(member, ModBlocks.waste_snow.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockSnowBlock)
							world.setBlockState(member, ModBlocks.waste_snow_block.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockMycelium)
							world.setBlockState(member, ModBlocks.waste_mycelium.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockRedSandstone)
							world.setBlockState(member, ModBlocks.waste_sandstone_red.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockSandStone)
							world.setBlockState(member, ModBlocks.waste_sandstone.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockHardenedClay || block instanceof BlockStainedHardenedClay)
							world.setBlockState(member, ModBlocks.waste_terracotta.getStateFromMeta(Math.min(heat, 6)));
						else if (block instanceof BlockSand) {
							BlockSand.EnumType meta = state.getValue(BlockSand.VARIANT);
							world.setBlockState(member, ((meta == BlockSand.EnumType.SAND) ? ModBlocks.waste_sand : ModBlocks.waste_sand_red).getStateFromMeta(Math.min(heat, 6)));
						} else {
							int level = -1;
							if (block == Blocks.COBBLESTONE || block == Blocks.STONE || block instanceof BlockStone || block == sellafieldLevels[0])
								level = 0;
							else {
								for (int i = 1; i < sellafieldLevels.length; i++) {
									if (block == sellafieldLevels[i]) {
										level = i;
										break;
									}
								}
							}
							if ((level >= 0) && (heat >= level))
								world.setBlockState(member, sellafieldLevels[Math.min(heat, 6)].getStateFromMeta(world.rand.nextInt(4)));
							else
								ModBlocks.PWR.wreck_stone.create(world, member, EnumFacing.UP, state, Erosion.NONE, heat);
						}
						antiPlaceSet.add(member);
					}
				}
			}
			for (Entry<BlockPos, IBlockState> entry : placeMap.entrySet()) {
				if (!antiPlaceSet.contains(entry.getKey()))
					world.setBlockState(entry.getKey(), entry.getValue());
			}
			for (PWRDebrisEntity debris : entitiesToSpawn)
				world.spawnEntity(debris);
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "rbmkmush");
			data.setFloat("scale", (float) reactorSize);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5), new NetworkRegistry.TargetPoint(world.provider.getDimension(), centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, 250));
			world.playSound(null, centerPoint.x + 0.5, centerPoint.y + 0.5, centerPoint.z + 0.5, HBMSoundEvents.rbmk_explosion, SoundCategory.BLOCKS, 50.0F, 1.0F);
		}
	}

	public void explode(World world, @Nullable ItemStack prevStack,@Nullable LeafiaRodItem doNuke) {
		if (exploded) return;
		exploded = true;
		explodeWorld = world;
		if (members.size() <= 0) return;
		Vec3d centerPoint = new Vec3d(0, 0, 0);
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int minZ = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		int maxZ = Integer.MIN_VALUE;
		for (BlockPos member : members) {
			minX = Math.min(minX, member.getX());
			minY = Math.min(minY, member.getY());
			minZ = Math.min(minZ, member.getZ());
			maxX = Math.max(maxX, member.getX());
			maxY = Math.max(maxY, member.getY());
			maxZ = Math.max(maxZ, member.getZ());
			centerPoint = centerPoint.add(new Vec3d(member.getX(), member.getY(), member.getZ()).scale(1d / members.size()));
			Block block = world.getBlockState(member).getBlock();
			if (block instanceof PWRComponentBlock) {
				if (block instanceof PWRElementBlock) {
					if (((PWRElementBlock) block).tileEntityShouldCreate(world, member)) {
						TileEntity entity = world.getTileEntity(member);
						if (entity != null) {
							if (entity instanceof PWRElementTE) {
								PWRElementTE element = (PWRElementTE) entity;
								if (element.inventory != null) {
									prevStack = LeafiaRodItem.comparePriority(element.inventory.getStackInSlot(0), prevStack);
									element.inventory.setStackInSlot(0,ItemStack.EMPTY);
								}
							}
						}
					}
				}
				//world.setBlockToAir(member);
			}
		}

		PWRExplosion boom = new PWRExplosion(world, centerPoint, minX, minY, minZ, maxX, maxY, maxZ);
		if (toughness >= 15_000)
			boom.explodeLv3();
		else if (toughness >= 10_000)
			boom.explodeLv2();
		else
			boom.explodeLv1();

		boolean nope = true;
        /*
        if (prevStack != null) {
            if (prevStack.getItem() instanceof LeafiaRodItem) {
                nope = false;
                LeafiaRodItem rod = (LeafiaRodItem) (prevStack.getItem());
                rod.resetDetonate();
                rod.detonateRadius = 18;
                rod.detonateVisualsOnly = true;
                rod.detonate(world, new BlockPos(centerPoint));
            }
        }*/
		if (doNuke != null)
			doNuke.nuke(world,new BlockPos(centerPoint));
		ExplosionNukeGeneric.waste(world, (int) centerPoint.x, (int) centerPoint.y, (int) centerPoint.z, 35);
		RadiationSavedData.incrementRad(world, new BlockPos(centerPoint), 3000F, 4000F);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tanks[0].getTankProperties()[0], tanks[1].getTankProperties()[0], tanks[2].getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource == null) {
			return 0;
		} else if (resource.getFluid() == tankTypes[0]) {
			return tanks[0].fill(resource, doFill);
		} else if (resource.getFluid() == tankTypes[3]) {
			return tanks[3].fill(resource, doFill);
		} else {
			return 0;
		}
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource != null && resource.getFluid() == tankTypes[1]) {
			return tanks[1].drain(resource.amount, doDrain);
		} else if (resource != null && resource.getFluid() == tankTypes[4]) {
			return tanks[4].drain(resource.amount, doDrain);
		} else {
			return null;
		}
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tanks[1].drain(maxDrain, doDrain);
	}

	public static LeafiaPacket addDataToPacket(LeafiaPacket packet, @Nullable PWRData self) {
		return packet.__write(31, (self != null) ? self.writeToNBT(new NBTTagCompound()) : false);
	}

	@Nullable
	public static PWRData tryLoadFromPacket(TileEntity entity, Object value) {
		if (value.equals(false)) return null;
		else if (value instanceof NBTTagCompound)
			return new PWRData(entity).readFromNBT((NBTTagCompound) value);
		else
			return null;
	}

	void sendControlPositions() {
		LeafiaPacket._start(companion)
				.__write(29, writeControlPositions())
				.__write(28, masterControl)
				.__sendToAffectedClients();
	}

	@Override
	public String getPacketIdentifier() {
		throw new LeafiaDevFlaw("Method PWRData.getPacketIdentifier() is not supposed to be used! Spaghetti coding moment.");
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void onReceivePacketLocal(byte key, Object value) {
		if (key == 30) { // Tank packets
			if (!value.getClass().isArray()) {
				Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Malformed PWR tank packet! (Given value wasn't Array)"));
				return;
			}
			if (Array.getLength(value) != 11) {
				Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Malformed PWR tank packet! (Array length must be 5, got " + Array.getLength(value) + ")"));
				return;
			}
			int readIndex = 0;
			int prevCompression = compression;
			compression = (int) Array.get(value, readIndex++);
			if (prevCompression != compression)
				onUpdateCompression();
			for (int i = 0; i < 5; i++)
				tanks[i].setCapacity((int) Array.get(value, readIndex++));
			for (int i = 0; i < 5; i++)
				tanks[i].setFluid(new FluidStack(tankTypes[i], (int) Array.get(value, readIndex++)));
			// if we somehow got non-int values in the array, well... #ripbozo
		} else if (key == 29) { // Rod sync packets
			if (value instanceof NBTTagCompound) {
				readControlPositions((NBTTagCompound) value);
			}
		} else if (key == 31) {
			new PWRDiagnosis(companion.getWorld(), companion.getPos()).addPosition(companion.getPos());
		} else if (key == 28) { // Master rod sync
			masterControl = (double) value;
		} else if (key == 27) { // Server Rod sync
			if (value instanceof NBTTagCompound) {
				NBTTagList list = ((NBTTagCompound) value).getTagList("controls", ((NBTTagCompound) value).getId());
				for (int i = 0; i < list.tagCount(); i++) {
					NBTTagCompound nbt = list.getCompoundTagAt(i);
					if (nbt.hasKey("x") && nbt.hasKey("y") && nbt.hasKey("z")) {
						BlockPos pos = new BlockPos(nbt.getInteger("x"), nbt.getInteger("y"), nbt.getInteger("z"));
						if (controls.contains(pos)) {
							TileEntity entity = getWorld().getTileEntity(pos);
							if (entity instanceof PWRControlTE) {
								PWRControlTE control = (PWRControlTE) entity;
								control.readControlDataFromNBT(nbt);
							}
						}
					}
				}
			} else {
				Minecraft.getMinecraft().player.sendMessage(new TextWarningLeafia("Malformed PWR control rod packet! (Given value wasn't NBTTagList)"));
			}
		}
	}

	@Override
	public void onReceivePacketServer(byte key, Object value, EntityPlayer plr) {
		if (key == 30) { // control rods request
			if (value instanceof NBTTagCompound) {
				NBTTagCompound nbt = (NBTTagCompound) value;
				if (nbt.hasKey("name")) {
					controlDemand.put(nbt.getString("name"), nbt.getDouble("level"));
					manipulateRod(nbt.getString("name"));
				} else {
					masterControl = nbt.getDouble("level");
					manipulateRod(null);
				}
				sendControlPositions();
			}
		} else if (key == 29) {
			if (value instanceof Integer) {
				compression = Math.floorMod((int) value, 3);
				onUpdateCompression();
			}
		}
	}

	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		addDataToPacket(LeafiaPacket._start(companion), this).__sendToClient(plr);
	}

	void manipulateRod(String name) {
		for (BlockPos pos : controls) {
			TileEntity entity = getWorld().getTileEntity(pos);
			if (entity instanceof PWRControlTE) {
				PWRControlTE control = (PWRControlTE) entity;
				double newTarget = 0;
				if (controlDemand.containsKey(control.name))
					newTarget = controlDemand.get(control.name) * masterControl;
				if (name != null) {
					if (!control.name.equals(name)) continue;
				}
				control.targetPosition = newTarget;
			}
		}
	}
}
