package com.leafia.contents.machines.reactors.pwr.blocks.components.element;

import com.hbm.blocks.ModBlocks;
import com.hbm.interfaces.IRadResistantBlock;
import com.hbm.lib.InventoryHelper;
import com.hbm.saveddata.RadiationSavedData;
import com.hbm.tileentity.TileEntityInventoryBase;
import com.hbm.util.I18nUtil;
import com.hbm.util.Tuple.Pair;
import com.leafia.contents.control.fuel.nuclearfuel.LeafiaRodItem;
import com.leafia.contents.machines.reactors.pwr.PWRData;
import com.leafia.contents.machines.reactors.pwr.blocks.PWRReflectorBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.PWRComponentEntity;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRChannelBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.channel.PWRConductorBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlBlock;
import com.leafia.contents.machines.reactors.pwr.blocks.components.control.PWRControlTE;
import com.leafia.dev.LeafiaDebug.Tracker;
import com.leafia.dev.container_utility.LeafiaPacket;
import com.leafia.dev.container_utility.LeafiaPacketReceiver;
import com.llib.exceptions.LeafiaDevFlaw;
import com.llib.group.LeafiaMap;
import com.llib.group.LeafiaSet;
import com.llib.math.range.RangeDouble;
import com.llib.math.range.RangeInt;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;

public class PWRElementTE extends TileEntityInventoryBase implements PWRComponentEntity, ITickable, LeafiaPacketReceiver {
	public static LeafiaMap<PWRElementTE,LeafiaSet<BlockPos>> listeners = new LeafiaMap<>();
	BlockPos corePos = null;
	PWRData data = null;
	int height = 1;

	LeafiaSet<BlockPos> listenPositions() {
		if (this.isInvalid()) return new LeafiaSet<>(); // dummy set
		if (!listeners.containsKey(this)) {
			listeners.put(this,new LeafiaSet<>());
		}
		return listeners.get(this);
	}

	@Override
	public void onDiagnosis() {
		height = getHeight();
		updateObstacleMappings();
	}
	public void updateObstacleMappings() {
		listenPositions().clear();
		// call those ultimately complex functions o-o
		updateCornerMap();
		updateLinearMap();
	}

	public int getHeight() {
		Tracker._startProfile(this,"getHeight");
		int height = 1;
		for (BlockPos p = pos.down(); world.isValid(p); p = p.down()) {
			Tracker._tracePosition(this,p);
			if (world.getBlockState(p).getBlock() instanceof PWRElementBlock)
				height++;
			else
				break;
		}
		Tracker._endProfile(this);
		return height;
	}
	static abstract class MapConsumer {
		int i = 0;
		abstract HeatRetrival accept(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> areas);
		public MapConsumer() {}
		public MapConsumer(int i) {
			this.i = i;
		}
	}

	public final Set<HeatRetrival> linearFuelMap = new HashSet<>();
	public final Set<Pair<HeatRetrival,HeatRetrival>> cornerFuelMap = new HashSet<>();
	void updateCornerMap() {
		Tracker._startProfile(this,"updateCornerMap");
		cornerFuelMap.clear();
		RangeInt range = new RangeInt(0,height-1);
		for (int bin = 0; bin <= 0b11; bin++) {
			Pair<HeatRetrival,HeatRetrival> pair = new Pair<>(null,null);
			Pair<HeatRetrival,HeatRetrival> pairRef = new Pair<>(null,null);
			for (int bin2 = 0b01; bin2 <= 0b10; bin2++) {
				final Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls = new HashMap<>();
				List<RangeInt> areas = new ArrayList<>();
				areas.add(range.clone());
				Set<Integer> moderatedRows = new HashSet<>();
				int xMul = bin2>>1;
				int zMul = bin2&1;
				linetraceNeutrons(pos.add((((bin>>1)&1)*2-1)*xMul,0,((bin&1)*2-1)*zMul),areas,controls,moderatedRows,new MapConsumer() {
					@Override
					HeatRetrival accept(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> areas) {
						HeatRetrival retrival = new HeatRetrival(fuelPos,controls,areas);
						retrival.entity = PWRElementTE.this;
						if (pairRef.getA() == null)
							pairRef.setA(retrival);
						else {
							pairRef.setB(retrival);
							cornerFuelMap.add(pairRef);
						}
						return retrival;
					}
				});
				searchFuelAndAdd(pos.add(((bin>>1)&1)*2-1,0,(bin&1)*2-1),areas,controls,moderatedRows,new MapConsumer() {
					@Override
					HeatRetrival accept(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> areas) {
						HeatRetrival retrival = new HeatRetrival(fuelPos,controls,areas);
						retrival.entity = PWRElementTE.this;
						if (pair.getA() == null)
							pair.setA(retrival);
						else {
							pair.setB(retrival);
							cornerFuelMap.add(pair);
						}
						return retrival;
					}
				});
			}
		}
		Tracker._endProfile(this);
	}
	void updateLinearMap() {
		Tracker._startProfile(this,"updateLinearMap");
		linearFuelMap.clear();
		RangeInt range = new RangeInt(0,height-1);
		for (EnumFacing facing : EnumFacing.HORIZONTALS) {
			final LeafiaMap<BlockPos,Pair<RangeDouble,RangeDouble>> controls = new LeafiaMap<>();
			List<RangeInt> areas = new ArrayList<>();
			areas.add(range.clone());
			showRangesInt(pos.add(facing.getDirectionVec()),areas,"areas start");
			Set<Integer> moderatedRows = new HashSet<>();
			for (int i = 1; (areas.size() > 0) && (i < 20); i++) {
				BlockPos basePos = pos.add(facing.getXOffset()*i,0,facing.getZOffset()*i);
				if (!world.isValid(basePos)) break;
				Tracker._tracePosition(this,basePos,"basePos");
				linetraceNeutrons(basePos,areas,controls,moderatedRows,new MapConsumer(i) {
					@Override
					HeatRetrival accept(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> areas) {
						HeatRetrival retrival = new HeatRetrival(fuelPos,controls,areas,this.i);
						retrival.entity = PWRElementTE.this;
						linearFuelMap.add(retrival);
						return retrival;
					}
				});
				showRangesInt(basePos,areas,"areas");
				searchFuelAndAdd(basePos,new ArrayList<>(areas),controls.clone(),moderatedRows,new MapConsumer(i) {
					@Override
					HeatRetrival accept(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> areas) {
						HeatRetrival retrival = new HeatRetrival(fuelPos,controls,areas,this.i);
						retrival.entity = PWRElementTE.this;
						linearFuelMap.add(retrival);
						return retrival;
					}
				});
			}
		}
		Tracker._endProfile(this);
	}
	static Set<RangeDouble> intersectRanges(Set<RangeDouble> a,Set<RangeDouble> b) {
		Set<RangeDouble> intersection = new HashSet<>();
		for (RangeDouble rangeA : a) {
			for (RangeDouble rangeB : b) {
				RangeDouble range = new RangeDouble(Math.max(rangeA.min,rangeB.min),Math.min(rangeA.max,rangeB.max));
				if (range.min <= range.max)
					intersection.add(range);
			}
		}
		return intersection;
	}
	void showRanges(BlockPos horizontalPos,Collection<RangeDouble> areas,String name) {
		Vec3d base = new Vec3d(horizontalPos.getX(),getPos().getY(),horizontalPos.getZ()).add(0.5+Math.signum(getPos().getX()-horizontalPos.getX())*0.6,0,0.5+Math.signum(getPos().getZ()-horizontalPos.getZ())*0.6);
		for (RangeDouble area : areas)
			Tracker._traceLine(this,base.add(0,1-area.min*height,0),base.add(0,1-area.max*height,0),name);
	}
	void showRangesInt(BlockPos horizontalPos,Collection<RangeInt> areas,String name) {
		Vec3d base = new Vec3d(horizontalPos.getX(),getPos().getY(),horizontalPos.getZ()).add(0.5+Math.signum(getPos().getX()-horizontalPos.getX())*0.6,0,0.5+Math.signum(getPos().getZ()-horizontalPos.getZ())*0.6);
		for (RangeInt area : areas)
			Tracker._traceLine(this,base.add(0,1-area.min,0),base.add(0,-area.max,0),name);
	}
	void searchFuelAndAdd(BlockPos basePos,List<RangeInt> areas,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<Integer> moderatedRows,MapConsumer callback) {
		Tracker._startProfile(this,"searchFuelAndAdd");
		Tracker._tracePosition(this,basePos,"basePos");
		Set<RangeDouble> scaledAreas = new HashSet<>();
		for (RangeInt area : areas) {
			/* Add 1 basically because:
			For example we had 3 blocks,
			With integers, the range would be 1 <= x <= 3
			But with doubles, the range should be 1 <= x < 4 because 3.99 is also within block 3's area
			 */
			scaledAreas.add(new RangeDouble(area.min / (double) height,(area.max+1) / (double) height));
		}
		List<Integer> blocked = new ArrayList<>();
		//int neutronSources = 0; The "Heat Function" is DESIGNED to omit the need of neutron sources.
		//                        Yeah, there's no way this is getting along.
		for (RangeInt area : areas) {
			for (Integer depth : area) {
				int curDepth = depth;
				while (!blocked.contains(curDepth) && world.isValid(basePos.down(curDepth))) {
					blocked.add(curDepth);
					Block block = world.getBlockState(basePos.down(curDepth)).getBlock();
					Tracker._tracePosition(this,basePos.down(curDepth),"Searching fuels");
					if (block instanceof PWRElementBlock) {
						if (((PWRElementBlock) block).tileEntityShouldCreate(world,basePos.down(curDepth))) {
							int bottomDepth = depth;
							while (world.isValid(basePos.down(bottomDepth+1))) {
								if (world.getBlockState(basePos.down(bottomDepth + 1)).getBlock() instanceof PWRElementBlock) {
									bottomDepth++;
									blocked.add(bottomDepth);
								} else
									break;
							}
							int elementHeight = bottomDepth-curDepth+1;
							int moderation = 0;
							for (int depthMod = curDepth; depthMod <= bottomDepth; depthMod++) {
								if (moderatedRows.contains(depthMod))
									moderation++;
							}
							Set<RangeDouble> myArea = new HashSet<>();
							/* Add 1 to bottomDepth basically because:
							For example we had 3 blocks,
							With integers, the range would be 1 <= x <= 3
							But with doubles, the range should be 1 <= x < 4 because 3.99 is also within block 3's area
							 */
							myArea.add(new RangeDouble(curDepth/(double)height,(bottomDepth+1)/(double)height));
							Tracker._tracePosition(this,basePos.down(curDepth),"Detected fuel");
							showRanges(basePos,scaledAreas,"scaledAreas");
							showRanges(basePos,myArea,"myArea");
							showRanges(basePos,intersectRanges(scaledAreas,myArea),"intersectRanges");
							callback.accept(basePos.down(curDepth),controls,intersectRanges(scaledAreas,myArea)).moderation = moderation/(double)elementHeight;
							break;
						}
					} else
						break;
					curDepth--;
				}
			}
		}
		Tracker._endProfile(this);
	}
	void linetraceNeutrons(BlockPos basePos,List<RangeInt> areas,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<Integer> moderatedRows,MapConsumer reflectionCall) {
		Tracker._startProfile(this,"linetraceNeutrons");
		Tracker._tracePosition(this,basePos,"basePos");
		RangeInt range = new RangeInt(0,height-1);
		Set<RangeDouble> reflectAreas = new HashSet<>();
		for (RangeInt area : areas) {
			/* Add 1 basically because:
			For example we had 3 blocks,
			With integers, the range would be 1 <= x <= 3
			But with doubles, the range should be 1 <= x < 4 because 3.99 is also within block 3's area
			 */
			reflectAreas.add(new RangeDouble(area.min / (double) height,(area.max+1) / (double) height));
		}
		boolean doReflect = false;
		boolean[] reflectors = new boolean[height];
		/* carving out neutron rays */ {
			for (Integer depth : range) {
				BlockPos searchPos = basePos.down(depth);

				RangeInt subject = null;
				for (RangeInt area : areas) { if (area.isInRange(depth)) { subject = area; break; } }
				if (subject == null) continue;
				listenPositions().add(searchPos);

				Block block = world.getBlockState(searchPos).getBlock();
				if (world.isValid(searchPos)) {
					Tracker._tracePosition(this,searchPos,"Looking for graphite");
					if (block.getRegistryName() != null) {
						if (("_"+block.getRegistryName().getPath()+"_").matches(".*[^a-z]graphite[^a-z].*") || block instanceof PWRChannelBlock || block instanceof PWRConductorBlock || block == Blocks.WATER || block == Blocks.FLOWING_WATER)
							moderatedRows.add(depth);
					}
				}
				if (block instanceof PWRReflectorBlock) {
					Tracker._tracePosition(this,searchPos,"Detected reflector");
					reflectors[depth] = true;
					doReflect = true;
				}

				if (block instanceof IRadResistantBlock) {
					if (!(((IRadResistantBlock) block).isRadResistant(world,searchPos)))
						continue;
				} else
					continue;

				areas.remove(subject);
				int condition = ((subject.min == depth) ? 0b10 : 0)+
						((subject.max == depth) ? 0b01 : 0);
				Tracker._tracePosition(this,searchPos,TextFormatting.RED+"Carving out neutron");
				switch(condition) {
					case 0b00:
						areas.add(new RangeInt(subject.min,depth-1));
						areas.add(new RangeInt(depth+1,subject.max));
						break;
					case 0b01:
						subject.max--;
						areas.add(subject);
						break;
					case 0b10:
						subject.min++;
						areas.add(subject);
						break;
					case 0b11:
						break;
				}
				showRangesInt(basePos,areas,"carved areas");
			}
		}

		/* handle reflectors */ if (doReflect) {
			// this runs before detecting rods but those rods that's ignored are just the rods in the same column so we're fine with that
			LeafiaSet<RangeDouble> ranges = new LeafiaSet<>();
			int moderation = 0;
			boolean nextCreate = true;
			for (Integer depth : range) {
				if (!reflectors[depth])
					nextCreate = true;
				else  {
					if (nextCreate) {
						ranges.add(0,new RangeDouble(depth/(double)height,(depth+1d)/height));
						nextCreate = false;
					}
					ranges.get(0).max = (depth+1d)/height;
					if (moderatedRows.contains(depth))
						moderation++;
				}
			}
			if (ranges.size() > 0)
				reflectionCall.accept(getPos(),controls,intersectRanges(reflectAreas,ranges)).moderation = moderation/(double)height;
		}

		/* detect rods */ {
			Integer rodTop = null;
			if (world.getBlockState(basePos).getBlock() instanceof PWRControlBlock) {
				rodTop = 0;
				for (BlockPos searchPos = basePos.up(); world.isValid(searchPos); searchPos = searchPos.up()) {
					if (world.getBlockState(searchPos).getBlock() instanceof PWRControlBlock)
						rodTop -= 1;
					else
						break;
				}
			}
			int depth = 0;
			for (BlockPos searchPos = basePos; (world.isValid(searchPos) || (rodTop != null)); searchPos = searchPos.down()) {
				boolean isControl = world.isValid(searchPos); if (isControl) isControl = world.getBlockState(searchPos).getBlock() instanceof PWRControlBlock;
				if (rodTop == null) {
					if (isControl)
						rodTop = depth;
				} else {
					if (!isControl) {
						int rodBottom = depth-1;
						Tracker._tracePosition(this,basePos.down(rodTop),"rodTop");
						Tracker._tracePosition(this,basePos.down(rodBottom),"rodBottom");
						double rodHeight = rodBottom-rodTop+1;
						if (rodBottom < height+rodHeight) {
							int rodPeak = height-rodTop;
							int rodUnderneath = rodTop-height;
							Tracker._tracePosition(this,basePos.down(rodTop).up(rodPeak),"rodPeak");
							Tracker._tracePosition(this,basePos.down(rodTop).up(rodUnderneath),"rodUnderneath");
							double topStart = rodTop/rodHeight;
							double bottomStart = (rodBottom+1-height)/rodHeight;
							RangeDouble rangeBottom = new RangeDouble(
									bottomStart,
									bottomStart+height/rodHeight
							);
							RangeDouble rangeTop = new RangeDouble(
									topStart,
									topStart-height/rodHeight
							);
							Tracker._traceLine(
									this,
									new Vec3d(basePos.down(rodTop)).add(0.5,1+rangeBottom.min*rodHeight,0.5),
									new Vec3d(basePos.down(rodTop)).add(0.5,1+rangeBottom.max*rodHeight,0.5),
									"rangeBottom",String.format("%01.2f%% ~ %01.2f%%",rangeBottom.min*100,rangeBottom.max*100)
							);
							Tracker._traceLine(
									this,
									new Vec3d(basePos.down(rodBottom)).add(0.5,rangeTop.min*rodHeight,0.5),
									new Vec3d(basePos.down(rodBottom)).add(0.5,rangeTop.max*rodHeight,0.5),
									"rangeTop",String.format("%01.2f%% ~ %01.2f%%",rangeTop.min*100,rangeTop.max*100)
							);
							controls.put(new BlockPos(basePos.getX(),basePos.getY() - rodTop,basePos.getZ()),new Pair<>(rangeBottom,rangeTop));
						}
						rodTop = null;
					}
				}
				depth++;
			}
		}
		Tracker._endProfile(this);
	}
	public static class HeatRetrival {
		public final Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls;
		public final BlockPos fuelPos;
		public final double divisor;
		public final Set<RangeDouble> areas;
		public double moderation;
		PWRElementTE entity;
		public HeatRetrival(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> copiedAreas,int distance) {
			this.fuelPos = fuelPos;
			this.divisor = Math.pow(2,distance/2d-1);
			this.areas = copiedAreas;
			this.controls = copyControlMap(controls);
		}
		public HeatRetrival(BlockPos fuelPos,Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls,Set<RangeDouble> copiedAreas) {
			this.fuelPos = fuelPos;
			this.divisor = 2;
			this.areas = copiedAreas;
			this.controls = copyControlMap(controls);
		}
		Map<BlockPos,Pair<RangeDouble,RangeDouble>> copyControlMap(Map<BlockPos,Pair<RangeDouble,RangeDouble>> controls) {
			Map<BlockPos,Pair<RangeDouble,RangeDouble>> copiedControls = new LeafiaMap<>();
			for (Entry<BlockPos,Pair<RangeDouble,RangeDouble>> entry : controls.entrySet()) {
				copiedControls.put(
						entry.getKey(),
						new Pair<>(entry.getValue().getA().clone(),entry.getValue().getB().clone())
				);
			}
			return copiedControls;
		}
		void traceAreas(Vec3d vec,Set<RangeDouble> ranges,Object... messages) {
			double h = entity.height;
			for (RangeDouble range : ranges)
				Tracker._traceLine(entity,vec.subtract(0,range.min*h,0),vec.subtract(0,range.max*h,0),messages);
		}
		public double getControlMin(World world) {
			/*double control = 1;
			for (BlockPos pos : controls) {
				control = Math.min(control,getControl(world,pos));
			}*/
			Tracker._startProfile(entity,"getControlMin");
			Set<RangeDouble> intersection = new LeafiaSet<>();
			double h = entity.height;
			Vec3d midPos = (new Vec3d(entity.getPos()).add(new Vec3d(fuelPos.getX(),entity.getPos().getY(),fuelPos.getZ()))).scale(0.5).add(0.5,1,0.5);
			for (RangeDouble area : areas) {
				Tracker._traceLine(entity,midPos.subtract(0,area.min*h,0),midPos.subtract(0,area.max*h,0),"areaTest");
				intersection.add(area.clone());
			}
			for (Map.Entry<BlockPos,Pair<RangeDouble,RangeDouble>> entry : controls.entrySet()) {
				Tracker._tracePosition(entity,entry.getKey(),"Accounting control...");
				Vec3d vec = new Vec3d(entry.getKey().getX()+0.5,entity.getPos().getY()+1,entry.getKey().getZ()+0.5);
				double rodPos = getControl(world,entry.getKey());
				double normalizedPosB = Math.max(entry.getValue().getA().ratio(rodPos),0);
				double normalizedPosT = Math.max(entry.getValue().getB().ratio(rodPos),0);Tracker._traceLine(entity,vec.subtract(0,(1-normalizedPosB)*h,0),vec.subtract(0,(normalizedPosT)*h,0),"Blockage test","Bottom: "+normalizedPosB,"Top: "+normalizedPosT);

				Set<RangeDouble> openAreas = new LeafiaSet<>();
				if (normalizedPosT > 0)
					openAreas.add(new RangeDouble(0,normalizedPosT));
				if (normalizedPosB > 0)
					openAreas.add(new RangeDouble(1-normalizedPosB,1));
				traceAreas(vec,openAreas,"openAreas");
				if (openAreas.size() <= 0) return 0;
				else intersection = intersectRanges(intersection,openAreas);
			}
			traceAreas(midPos,intersection,"Final intersection");
			double control = 0;
			for (RangeDouble range : intersection)
				control += Math.abs(range.max-range.min);
			Tracker._tracePosition(entity,new BlockPos(fuelPos.getX(),entity.getPos().getY(),fuelPos.getZ()),"Final control: "+control);
			Tracker._endProfile(entity);
			return control;
		}
		public double getControlAvg(World world) {
			throw new LeafiaDevFlaw("getControlAvg is scrapped");
		}
		public double getControl(World world,BlockPos pos) {
			TileEntity entity = world.getTileEntity(pos);
			if (entity != null) {
				if (entity instanceof PWRControlTE) {
					return ((PWRControlTE) entity).position;
				}
			}
			if (world.getBlockState(pos).getBlock() instanceof IRadResistantBlock) {
				return ((IRadResistantBlock) world.getBlockState(pos).getBlock()).isRadResistant(world,pos) ? 0 : 1;
			}
			return 1;
		}
	}
	public double getHeatFromHeatRetrival(HeatRetrival retrival,LeafiaRodItem rod) {
		BlockPos pos = retrival.fuelPos;
		Tracker._startProfile(this,"getHeatFromRetrival");
		if (world.getBlockState(pos).getBlock() instanceof PWRElementBlock) {
			if (((PWRElementBlock) world.getBlockState(pos).getBlock()).tileEntityShouldCreate(world,pos)) {
				TileEntity entity = world.getTileEntity(pos);
				if (entity != null) {
					if (entity instanceof PWRElementTE) {
						ItemStackHandler items = ((PWRElementTE) entity).inventory;
						if (items != null) {
							Tracker._endProfile(this);
							double value = rod.getFlux(items.getStackInSlot(0))*(1-retrival.moderation)+rod.getFlux(items.getStackInSlot(0),true)*retrival.moderation;
							Tracker._tracePosition(this,pos,value);
							return value;
						}
					}
				}
			}
		}
		Tracker._endProfile(this);
		return 0;
	}

	public PWRElementTE() {
		super(1);
	}

	public void connectUpper() { // For clients, called only on validate()
		if (!this.isInvalid() && world.isBlockLoaded(pos)) {
			Chunk chunk = world.getChunk(pos);
			if (world.isRemote) { // Keep in mind that neighborChanged in Block does NOT get called for Remotes
				if (world.getBlockState(pos.down()).getBlock() instanceof PWRElementBlock) {
					TileEntity entityBelow = chunk.getTileEntity(pos.down(),Chunk.EnumCreateEntityType.CHECK);
					if (entityBelow != null) {
						if (entityBelow instanceof PWRElementTE) {
							((PWRElementTE)entityBelow).connectUpper();
						}
					}
				}
				if (world.getBlockState(pos.up()).getBlock() instanceof PWRElementBlock) {
					inventory.setStackInSlot(0,ItemStack.EMPTY);
					invalidate();
				}
				return;
			}
			BlockPos upPos = pos.up();
			boolean mustTransmit = false;
			PWRElementTE target = null;
			while (world.isValid(upPos)) {
				if (world.getBlockState(upPos).getBlock() instanceof PWRElementBlock) {
					mustTransmit = true;
					TileEntity entity = chunk.getTileEntity(upPos,Chunk.EnumCreateEntityType.CHECK);
					target = null;
					if (entity != null) {
						if (entity instanceof PWRElementTE) {
							if (!entity.isInvalid()) {
								target = (PWRElementTE) entity;
								if (!target.inventory.getStackInSlot(0).isEmpty())
									target = null;
							}
						}
					}
				} else
					break;
				upPos = upPos.up();
			}
			if (mustTransmit) {
				if (target != null) {
					target.inventory.setStackInSlot(0,inventory.getStackInSlot(0));
					this.inventory.setStackInSlot(0,ItemStack.EMPTY);
				} else
					InventoryHelper.dropInventoryItems(world,pos,this);
				this.invalidate();
			}
		}
	}
	@Override
	public void setCoreLink(@Nullable BlockPos pos) {
		corePos = pos;
	}

	@Override
	public PWRData getLinkedCore() {
		return PWRComponentEntity.getCoreFromPos(world,corePos);
	}

	@Override
	public void assignCore(@Nullable PWRData data) {
		if (this.data != data) {
			PWRData.addDataToPacket(LeafiaPacket._start(this),data).__sendToAffectedClients();
		}
		this.data = data;
	}
	@Override
	public PWRData getCore() {
		return data;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		if (compound.hasKey("corePosX"))
			corePos = new BlockPos(
					compound.getInteger("corePosX"),
					compound.getInteger("corePosY"),
					compound.getInteger("corePosZ")
			);
		super.readFromNBT(compound);
		if (compound.hasKey("data")) { // DO NOT MOVE THIS ABOVE SUPER CALL! super.readFromNBT() is where this.pos gets initialized!!
			data = new PWRData(this);
			data.readFromNBT(compound);
		}
	}

	@Override
	public String getName() {
		return I18nUtil.resolveKey("tile.pwr_element.name");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		if (corePos != null) {
			compound.setInteger("corePosX",corePos.getX());
			compound.setInteger("corePosY",corePos.getY());
			compound.setInteger("corePosZ",corePos.getZ());
		}
		if (data != null) {
			data.writeToNBT(compound);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void markDirty() {
		super.markDirty();
		if (!world.isRemote)
			syncLocals();
	}

	@Override
	public void invalidate() {
		listeners.remove(this);
		super.invalidate();
		if (this.data != null)
			this.data.invalidate(world);
	}

	@Override
	public void validate() {
		super.validate();
		//if (world.isRemote) { // so long lol
		//if (!compound.hasKey("_isSyncSignal")) {
		//LeafiaPacket._validate(this);
		//LeafiaPacket._start(this).__write((byte)0,true).__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK).__sendToServer();
		//}
		//}
		connectUpper();
	}
	@Nullable
	PWRData gatherData() {
		/*
		if (this.corePos != null) {
			TileEntity entity = world.getTileEntity(corePos);
			if (entity != null) {
				if (entity instanceof PWRComponentEntity) {
					return ((PWRComponentEntity) entity).getCore();
				}
			}
		}
		return null;*/
		return this.getLinkedCore();
	}
	public double getHeat() {
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof LeafiaRodItem) {
				if (stack.getTagCompound() != null)
					return stack.getTagCompound().getDouble("heat");
			}
		}
		return 20;
	}
	public void setHeat(double heat) {
		ItemStack stack = this.inventory.getStackInSlot(0);
		if (!stack.isEmpty()) {
			if (stack.getItem() instanceof LeafiaRodItem) {
				if (stack.getTagCompound() != null)
					stack.getTagCompound().setDouble("heat",heat);
			}
		}
	}
	public float channelScale = 0;
	public float exchangerScale = 1;
	@Override
	public void update() {
		if (this.data != null)
			this.data.update();
		if (!world.isRemote) {
			ItemStack stack = this.inventory.getStackInSlot(0);
			if (!stack.isEmpty()) {
				if (stack.getItem() instanceof LeafiaRodItem) {
					int height = getHeight();
					double coolin = 0;
					PWRData gathered = gatherData();
					double coolantTemp = 400;
					if (gathered != null) {
						// DONE PROBABLY: make it detect only nearby channels
						// DONE PROBABLY: exchangers would increase coolant consumption rate
						coolin = Math.pow(gathered.tanks[0].getFluidAmount()/(double)Math.max(gathered.tanks[0].getCapacity(),1),0.4)
								;//*(gathered.tanks[0].getCapacity()/1250d);
						coolantTemp = gathered.tankTypes[1].getTemperature()-273;
					}
					LeafiaRodItem rod = (LeafiaRodItem)(stack.getItem());
					double heatDetection = 0;
					for (Pair<HeatRetrival,HeatRetrival> pair : cornerFuelMap) {
						// getControlAvg is scrapped
						heatDetection += getHeatFromHeatRetrival(pair.getA(),rod)*pair.getA().getControlMin(world)*height/2;
						heatDetection += getHeatFromHeatRetrival(pair.getB(),rod)*pair.getB().getControlMin(world)*height/2;
					}
					for (HeatRetrival retrival : linearFuelMap)
						heatDetection += getHeatFromHeatRetrival(retrival,rod)*retrival.getControlMin(world)*height;
					double heat = (stack.getTagCompound() != null) ? stack.getTagCompound().getDouble("heat") : 20;
					//double coolingCap = MathHelper.clamp(heat,20,400+Math.pow(Math.max(heat-400,0),0.5));


					rod.HeatFunction(stack,true,heatDetection,channelScale*coolin,400,400*exchangerScale);
					double rad = Math.pow(heatDetection,0.65)/2;
					RadiationSavedData.incrementRad(world,pos,(float)rad/8,(float)rad);
					//DONE PROBABLY: add neutron radiations to indicate emitted chunk radiations

					rod.decay(stack,inventory,0);
					NBTTagCompound data = stack.getTagCompound();
					double cooled = 0;
					if (data != null) {
						if (data.getBoolean("nuke")) {
							if (gathered != null)
								gathered.explode(world,stack,rod);
							inventory.setStackInSlot(0,ItemStack.EMPTY);
							return;
						} else if (data.getInteger("spillage") > 100) {
							/*if (rod.meltdownPriority > 0) {
								if (gathered != null)
									gathered.explode(world,stack);
							} else */
							if (gathered != null && gathered.tankTypes[1].isGaseous())
								gathered.explode(world,stack,null);
							else {
								//inventory.setStackInSlot(0,ItemStack.EMPTY);
								//world.destroyBlock(pos,false);
								BlockPos pos = this.pos.down(height);
								int tries = height;
								while (tries >= 0) {
									tries--;
									if (world.getBlockState(pos).getBlock() == ModBlocks.block_corium) {
										pos = pos.up();
										continue;
									} else if (world.getBlockState(pos).getBlock() == ModBlocks.corium_block)
										break;
									else {
										world.playEvent(2001,pos,Block.getStateId(world.getBlockState(pos)));
										world.setBlockState(pos,ModBlocks.corium_block.getDefaultState());
										//BlockPos nextPos = pos.down();
										/*while (world.isValid(nextPos)) {
											if (world.getBlockState(nextPos).getBlock() instanceof MachinePWRElement)
												world.setBlockState(nextPos,ModBlocks.corium_block.getDefaultState());
											else
												break;
											nextPos = nextPos.down();
										}*/
										world.setBlockState(pos,ModBlocks.corium_block.getDefaultState());
									}
								}
							}
						}
						cooled = data.getDouble("cooled");
					}
					if (cooled > 0 && gathered != null) {
						gathered.spendCoolant(cooled,stack);
					}
					NBTTagCompound nbt = stack.getTagCompound();
					LeafiaPacket packet = LeafiaPacket._start(this);
					if (nbt.hasKey("heat"))
						packet.__write(1,nbt.getDouble("heat"));
					if (nbt.hasKey("depletion"))
						packet.__write(2,nbt.getDouble("depletion"));
					if (nbt.hasKey("incoming"))
						packet.__write(3,nbt.getDouble("incoming"));
					if (nbt.hasKey("melting"))
						packet.__write(4,nbt.getBoolean("melting"));
					packet.__sendToAffectedClients();
				}
			}
		}
	}

	@Override
	public String getPacketIdentifier() {
		return "PWRElement";
	}
	public LeafiaPacket generateSyncPacket() {
		NBTTagCompound nbt = writeToNBT(new NBTTagCompound());
		if (nbt.hasKey("data"))
			nbt.removeTag("data");
		return LeafiaPacket._start(this).__write((byte)0,nbt);
	}
	public void syncLocals() {
		generateSyncPacket().__sendToAffectedClients();//.__setTileEntityQueryType(Chunk.EnumCreateEntityType.CHECK).__sendToAllInDimension();
	}
	@SideOnly(Side.CLIENT)
	@Override
	public void onReceivePacketLocal(byte key,Object value) {
		switch(key) {
			case 0:
				if (value instanceof NBTTagCompound) {
					NBTTagCompound nbt = (NBTTagCompound)value;
					nbt.setBoolean("_isSyncSignal",true);
					readFromNBT(nbt);
				}
				break;
			case 1:
			case 2:
			case 3:
				String[] doubleArray = new String[]{"heat","depletion","incoming"};
				if (value instanceof Double) {
					if (inventory != null) {
						if (!inventory.getStackInSlot(0).isEmpty()) {
							if (inventory.getStackInSlot(0).getItem() instanceof LeafiaRodItem) {
								NBTTagCompound nbt = inventory.getStackInSlot(0).getTagCompound();
								if (nbt == null) nbt = new NBTTagCompound();
								nbt.setDouble(doubleArray[key-1],(double)value);
								inventory.getStackInSlot(0).setTagCompound(nbt);
							}
						}
					}
				}
				break;
			case 4:
				if (value instanceof Boolean) {
					if (inventory != null) {
						if (!inventory.getStackInSlot(0).isEmpty()) {
							if (inventory.getStackInSlot(0).getItem() instanceof LeafiaRodItem) {
								NBTTagCompound nbt = inventory.getStackInSlot(0).getTagCompound();
								if (nbt == null) nbt = new NBTTagCompound();
								nbt.setBoolean("melting",(boolean)value);
								inventory.getStackInSlot(0).setTagCompound(nbt);
							}
						}
					}
				}
				break;
			case 31:
				data = PWRData.tryLoadFromPacket(this,value);
				break;
		}
		if (this.data != null)
			this.data.onReceivePacketLocal(key,value);
	}
	@Override
	public void onReceivePacketServer(byte key,Object value,EntityPlayer plr) {/*
        if (key == 0) {
            if (value.equals(true)) {
            }
        }*/
		if (this.data != null)
			this.data.onReceivePacketServer(key,value,plr);
	}
	@Override
	public void onPlayerValidate(EntityPlayer plr) {
		LeafiaPacket packet = generateSyncPacket();
		if (this.data != null) {
			PWRData.addDataToPacket(packet,this.data);
		}
		packet.__sendToClient(plr);
	}
}
