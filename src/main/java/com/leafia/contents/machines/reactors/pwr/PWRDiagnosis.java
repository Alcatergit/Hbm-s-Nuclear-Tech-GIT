package com.leafia.contents.machines.reactors.pwr;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.fluid.BlockLiquidCorium;
import com.hbm.items.ModItems;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.util.Tuple.Pair;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRChannelBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRConductorBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRExchangerBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.element.PWRElementTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.llib.exceptions.messages.TextWarningLeafia;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import com.llib.math.MathLeafia;
import com.llib.math.range.RangeInt;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

public class PWRDiagnosis {
	// We have to judge by the listener's position instead of changed neighbors position
	//   because that way the separated assemblies triggered by the same block would not update in a corner situation
	// This set is cleared every world tick
	public static final Set<BlockPos> preventScan = new HashSet<>();

	public final boolean isMeltdown;
	public static final Set<PWRDiagnosis> ongoing = new HashSet<>();
	private static boolean cleanupInProgress = false; // idk if this is necessary but I hate crashes so much so have this anyway
	public static void cleanup() {
		if (cleanupInProgress) return;
		cleanupInProgress = true;
		Set<PWRDiagnosis> removalQueue = new HashSet<>();
		for (PWRDiagnosis task : ongoing) {
			if (task.checkTimeElapsed() >= 10_000 || task.closure)
				removalQueue.add(task);
		}
		for (PWRDiagnosis diagnosis : removalQueue) {
			diagnosis.destroy();
		}
		cleanupInProgress = false;
	}
	public double cr;
	public double cg;
	public double cb;

	public int lastConfirmed;
	public final Set<BlockPos> activePos = new HashSet<>();
	public final Set<BlockPos> blockPos = new HashSet<>();
	public final Set<BlockPos> corePos = new HashSet<>();
	public final Set<BlockPos> potentialPos = new HashSet<>();
	public final Set<BlockPos> fuelPositions = new HashSet<>();
	public final Set<BlockPos> coriums = new HashSet<>();
	public final Set<BlockPos> controlPositions = new HashSet<>();
	public final LeafiaMap<Pair<Integer,Integer>,Pair<Integer,Boolean>> projected = new LeafiaMap<>();
	RangeInt rangeX = new RangeInt(Integer.MAX_VALUE,Integer.MIN_VALUE);
	RangeInt rangeZ = new RangeInt(Integer.MAX_VALUE,Integer.MIN_VALUE);
	float maxHardness = 0;
	float sumHardness = 0;
	float divide = 0;
	void gridFill() {
		for (Entry<Pair<Integer,Integer>,Pair<Integer,Boolean>> entry : projected.entrySet()) {
			int fromHeight = entry.getValue().getA();
			for (EnumFacing face : EnumFacing.HORIZONTALS) {
				List<Pair<Integer,Integer>> buffer = new ArrayList<>();
				for (int i = 1; true; i++) {
					Pair<Integer,Integer> offset = new Pair<>(entry.getKey().getA()+face.getXOffset()*i,entry.getKey().getB()+face.getZOffset()*i);
					if (!rangeX.isInRange(offset.getA()) || !rangeZ.isInRange(offset.getB()))
						break;
					if (projected.containsKey(offset)) {
						if (buffer.size() > 0) {
							int toHeight = projected.get(offset).getA();
							int finalHeight = Math.min(fromHeight,toHeight); // TODO: min or max?
							for (Pair<Integer,Integer> pair : buffer)
								projected.put(pair,new Pair<>(finalHeight,false));
						}
						break;
					} else
						buffer.add(offset);
				}
			}
		}
	}
	void addProjection(BlockPos pos,boolean isFuel) {
		Pair<Integer,Integer> pos2d = new Pair<>(pos.getX(),pos.getZ());
		int height = -1;
		boolean hasToBeFuel = false;
		if (projected.containsKey(pos2d)) {
			height = projected.get(pos2d).getA();
			hasToBeFuel = projected.get(pos2d).getB();
		}
		if ((pos.getY() > height || (isFuel && !hasToBeFuel)) && (isFuel || !hasToBeFuel)) {
			projected.put(pos2d,new Pair<>(pos.getY(),isFuel));
			rangeX.min = Math.min(rangeX.min,pos.getX());
			rangeX.max = Math.max(rangeX.max,pos.getX());
			rangeZ.min = Math.min(rangeZ.min,pos.getZ());
			rangeZ.max = Math.max(rangeZ.max,pos.getZ());
		}
	}
	boolean closure = false;
	World world = null;
	/*
	Creates PWRDiagnosis instance, and automatically adds to ongoing Set
	 */
	final BlockPos triggerPos;
	public PWRDiagnosis(World world,BlockPos trigger) {
		confirmLife();
		this.world = world;
		ongoing.add(this);
		cr = world.rand.nextDouble();
		cg = world.rand.nextDouble();
		cb = world.rand.nextDouble();
		triggerPos = trigger;
		isMeltdown = world.getBlockState(trigger).getBlock() instanceof BlockLiquidCorium;
	}
	PWRComponentBlock getPWRBlock(BlockPos pos) {
		Block block = world.getBlockState(pos).getBlock();
		if (block instanceof PWRComponentBlock)
			return (PWRComponentBlock)block;
		return null;
	}
	PWRComponentEntity getPWREntity(BlockPos pos) {
		PWRComponentBlock pwr = getPWRBlock(pos);
		if (pwr != null)
			return pwr.getPWR(world,pos);
		return null;
	}
	void debugSpawnParticle(BlockPos pos) {
		NBTTagCompound data = new NBTTagCompound();
		data.setString("type", "vanillaExt");
		data.setString("mode", "reddust");
		data.setDouble("mX", cr);
		data.setDouble("mY", cg);
		data.setDouble("mZ", cb);
		for (EntityPlayer player : world.playerEntities) {
			if (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d)
				LeafiaPacket._sendToClient(new AuxParticlePacketNT(data,pos.getX()+0.5,pos.getY()+0.1,pos.getZ()+0.5),player);
		}
	}
	public void explorePosition(BlockPos pos) {
		if (closure) return;
		activePos.add(pos);
		for (EnumFacing facing : EnumFacing.values()) {
			for (int i = 0; i <= 1; i++) {
				BlockPos neighbor = pos.offset(facing,i+1);
				IBlockState state = world.getBlockState(neighbor);
				Block block = state.getBlock();
				if (i == 0) {
					if (block instanceof PWRComponentBlock) {
						addPosition(neighbor);
					} else if (block instanceof BlockLiquidCorium) {
						if (!coriums.contains(pos)) {
							coriums.add(pos);
							explorePosition(pos);
						}
					} else if (block == ModBlocks.block_corium)
						addPosition(neighbor);
					else if (block == ModBlocks.block_corium_cobble)
						addPosition(neighbor);
				}
				if (!world.isValid(neighbor)) break;
				if (block instanceof PWRComponentBlock) continue;
				Material material = state.getMaterial();
				divide++;
				if (material.isSolid()) {
					float strength = block.getExplosionResistance(null);
					sumHardness += strength;
					maxHardness = Math.max(strength,maxHardness);
				}
			}
		}
		activePos.remove(pos);
		//for (EntityPlayer player : world.playerEntities) {
		//	player.sendMessage(new TextComponentString(""+activePos.size()));
		//}
		if (activePos.size() <= 0) {
			close();
		}
	}
	public void addPosition(BlockPos pos) {
		if (closure) return;
		if (!blockPos.contains(pos)) {
			blockPos.add(pos);
			if (!world.isRemote)
				preventScan.add(pos);
			confirmLife();
			PWRComponentBlock pwr = getPWRBlock(pos);
			debugSpawnParticle(pos.up());
			boolean isFuel = false;
			if (pwr != null) {
				// if this block isn't under control of topping blocks,
				if (pwr.tileEntityShouldCreate(world,pos)) {
					// then allow it to be assigned as core
					TileEntity entity = world.getTileEntity(pos);
					if (entity != null) {
						if (entity instanceof ITickable) // only assign tickable entities as a core
							potentialPos.add(pos);
					}
					if (pwr instanceof PWRElementBlock) {
						fuelPositions.add(pos);
						isFuel = true;
					} if (pwr instanceof PWRControlBlock)
						controlPositions.add(pos);
				}
				if (pwr.shouldRenderOnGUI())
					addProjection(pos,isFuel);
			}
			PWRComponentEntity entity = getPWREntity(pos);
			if (entity != null) {
				// if this block is the core of the affected assembly..
				if (entity.getCore() != null) {
					// check if it's in right place
					if (potentialPos.contains(pos))
						corePos.add(pos); // mark it so one of valid cores are chosen when joining assemblies
					else
						entity.assignCore(null); // destroy it if it's on the wrong place
					debugSpawnParticle(pos.up(2));
				}
			}
			try {
				explorePosition(pos);
			} catch (StackOverflowError error) {
				closure = true;
				for (EntityPlayer player : world.playerEntities) {
					if (player.getHeldItem(EnumHand.OFF_HAND).getItem() == ModItems.wand_d)
						player.sendMessage(new TextWarningLeafia("STACK OVERFLOW! PWR too gigantic!"));
				}
			}
		}
	}
	void close() {
		if (closure) return;
		closure = true;
		List<BlockPos> members = new ArrayList<>(blockPos); // for darned precision of contains()

		BlockPos outCorePos = null;
		if (!world.isRemote) {
			if (corePos.size() >= 2) { // if theres multiple cores
				// pick random...
				Object[] array = corePos.toArray();
				outCorePos = (BlockPos)(array[world.rand.nextInt(array.length)]);
				// and then remove the rest
				for (BlockPos pos : corePos) {
					if (!pos.equals(outCorePos)) {
						PWRComponentEntity entity = getPWREntity(pos);
						if (entity != null)
							entity.assignCore(null);
					}
				}
			} else if (corePos.size() <= 0) { // if theres not a single available core
				// pick random member...
				Object[] array = potentialPos.toArray();
				if (array.length > 0) {
					// and book it as the new core
					outCorePos = (BlockPos)(array[world.rand.nextInt(array.length)]);
				}
			} else // if theres exactly one, keep it
				outCorePos = (BlockPos)corePos.toArray()[0];
		} else {
			outCorePos = triggerPos;
		}

		// iterate over all members (blocks)
		Set<BlockPos> channels = new LeafiaSet<>();
		Set<BlockPos> exchangers = new LeafiaSet<>();
		Set<PWRElementTE> teElements = new LeafiaSet<>();
		int conductors = 0;
		for (BlockPos pos : members) {
			boolean shouldHaveCoreCoords = false;
			PWRComponentBlock pwr = getPWRBlock(pos);
			// check if it should have a tile entity
			if (pwr != null) {
				shouldHaveCoreCoords = pwr.tileEntityShouldCreate(world,pos);
				if (pwr instanceof PWRChannelBlock)
					channels.add(pos);
				if (pwr instanceof PWRConductorBlock) {
					channels.add(pos);
					conductors++;
				}
				if (pwr instanceof PWRExchangerBlock)
					exchangers.add(pos);
			}
			PWRComponentEntity entity = getPWREntity(pos);
			if (entity != null) { // give coordinates of the core for each valid blocks
				if (!world.isRemote) {
					PWRData link = entity.getLinkedCore();
					if (link != null) {
						if (!members.contains(link.corePos)) {
							if (isMeltdown) {
								link.explode(world,null,null);
								return;
							}
						}
					}
					if (entity instanceof PWRElementTE)
						teElements.add((PWRElementTE)entity);
				}
				entity.setCoreLink(shouldHaveCoreCoords ? outCorePos : null);
			}
		}
		// if position for core is booked,
		if (outCorePos != null) {
			debugSpawnParticle(outCorePos.up(3));
			PWRComponentEntity entity = getPWREntity(outCorePos);
			if (entity != null) {
				if (entity.getCore() == null && !world.isRemote) { // and if the target block doesn't have a core yet
					entity.assignCore(new PWRData((TileEntity)entity)); // assign it
				}
				PWRData core = entity.getCore();
				if (core != null) {
					core.members = blockPos;
					core.controls = controlPositions;
					core.fuels = fuelPositions;
				}
				if (!world.isRemote) {
					core.coriums = this.coriums.size();
					float avg = sumHardness/Math.max(divide,1);
					float hardness = avg*0.8f+maxHardness*0.2f;
					core.toughness = (int)(Math.pow(hardness,0.25)*4800);
					core.lastChannels = channels.size();
					core.lastConductors = conductors;
					core.resizeTanks(channels.size(),conductors);
					gridFill();
					LeafiaSet<BlockPos> projection = new LeafiaSet<>();
					for (Entry<Pair<Integer,Integer>,Pair<Integer,Boolean>> entry : projected.entrySet())
						projection.add(new BlockPos(entry.getKey().getA(),entry.getValue().getA(),entry.getKey().getB()));
					core.projection = projection;
					core.onDiagnosis(world);
				}
			}
		}
		if (!world.isRemote) {
			for (BlockPos pos : members) {
				PWRComponentEntity entity = getPWREntity(pos);
				if (entity != null)
					entity.onDiagnosis();
			}
		}
		float channelRange = 3f;
		float channelExponent = 0.2f;
		for (PWRElementTE elm : teElements) {
			if (!elm.isInvalid()) {
				BlockPos pos = elm.getPos();
				int height = elm.getHeight();
				float ch = 0;
				float ex = 0;
				for (BlockPos channel : channels) {
					BlockPos local = channel.subtract(pos);
					local = local.up(MathHelper.clamp(-local.getY(),0,height));
					ch += Math.pow(Math.max(1-local.getDistance(0,0,0)/channelRange,0),channelExponent);
				}
				for (BlockPos exchanger : exchangers) {
					BlockPos local = exchanger.subtract(pos);
					local = local.up(MathHelper.clamp(-local.getY(),0,height));
					ex += Math.pow(Math.max(1-local.getDistance(0,0,0)/channelRange,0),channelExponent);
				}
				elm.channelScale = (float)(Math.pow(ch,0.35)/Math.pow(height,0.35));
				elm.exchangerScale = 1+ex/height; //(float)Math.pow(height,0.5);
			}
		}
		LeafiaDebug.debugLog(world,"PWR Diagnosis complete, core position "+((outCorePos == null) ? "removed" : "set"));
	}
	public void confirmLife() {
		lastConfirmed = MathLeafia.getTime32s();
	}
	public int checkTimeElapsed() {
		return MathLeafia.getTimeDifference32s(MathLeafia.getTime32s(),lastConfirmed);
	}
	public void destroy() {
		if (ongoing.contains(this))
			ongoing.remove(this);
		if (world == null) return;
		if (world.playerEntities == null) return;
		LeafiaDebug.debugLog(world,"PWR Diagnosis instance removed");
	}
}
