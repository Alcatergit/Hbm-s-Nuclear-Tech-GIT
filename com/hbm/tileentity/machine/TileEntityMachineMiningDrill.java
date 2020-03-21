package com.hbm.tileentity.machine;

import java.util.Random;

import com.hbm.blocks.ModBlocks;
import com.hbm.handler.MultiblockHandler;
import com.hbm.interfaces.IConsumer;
import com.hbm.items.ModItems;
import com.hbm.lib.Library;
import com.hbm.packet.AuxElectricityPacket;
import com.hbm.packet.LoopedSoundPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDrillPacket;
import com.hbm.sound.SoundLoopMachine;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityMachineMiningDrill extends TileEntity implements ITickable, IConsumer {

	public ItemStackHandler inventory;

	public long power;
	public int warning;
	public static final long maxPower = 100000;
	int age = 0;
	int timer = 50;
	int radius = 100;
	int consumption = 100;
	int fortune = 0;
	boolean flag = true;
	public float torque;
	public float rotation;
	SoundLoopMachine sound;

	// private static final int[] slots_top = new int[] {1};
	// private static final int[] slots_bottom = new int[] {2, 0};
	// private static final int[] slots_side = new int[] {0};
	Random rand = new Random();

	private String customName;

	public TileEntityMachineMiningDrill() {
		inventory = new ItemStackHandler(13) {
			@Override
			protected void onContentsChanged(int slot) {
				markDirty();
				super.onContentsChanged(slot);
			}
		};
	}

	public String getInventoryName() {
		return this.hasCustomInventoryName() ? this.customName : "container.miningDrill";
	}

	public boolean hasCustomInventoryName() {
		return this.customName != null && this.customName.length() > 0;
	}

	public void setCustomName(String name) {
		this.customName = name;
	}

	public boolean isUseableByPlayer(EntityPlayer player) {
		if(world.getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 128;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		this.power = compound.getLong("powerTime");
		if(compound.hasKey("inventory"))
			inventory.deserializeNBT(compound.getCompoundTag("inventory"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("powerTime", power);
		compound.setTag("inventory", inventory.serializeNBT());
		return super.writeToNBT(compound);
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	@Override
	public void update() {
		this.consumption = 100;
		this.timer = 50;
		this.radius = 1;
		this.fortune = 0;

		for(int i = 10; i < 13; i++) {
			ItemStack stack = inventory.getStackInSlot(i);

			if(stack != null) {
				if(stack.getItem() == ModItems.upgrade_effect_1) {
					this.radius += 1;
					this.consumption += 80;
				}
				if(stack.getItem() == ModItems.upgrade_effect_2) {
					this.radius += 2;
					this.consumption += 160;
				}
				if(stack.getItem() == ModItems.upgrade_effect_3) {
					this.radius += 3;
					this.consumption += 240;
				}
				if(stack.getItem() == ModItems.upgrade_speed_1) {
					this.timer -= 15;
					this.consumption += 300;
				}
				if(stack.getItem() == ModItems.upgrade_speed_2) {
					this.timer -= 30;
					this.consumption += 600;
				}
				if(stack.getItem() == ModItems.upgrade_speed_3) {
					this.timer -= 45;
					this.consumption += 900;
				}
				if(stack.getItem() == ModItems.upgrade_power_1) {
					this.consumption -= 30;
					this.timer += 5;
				}
				if(stack.getItem() == ModItems.upgrade_power_2) {
					this.consumption -= 60;
					this.timer += 10;
				}
				if(stack.getItem() == ModItems.upgrade_power_3) {
					this.consumption -= 90;
					this.timer += 15;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_1) {
					this.fortune += 1;
					this.timer += 15;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_2) {
					this.fortune += 2;
					this.timer += 30;
				}
				if(stack.getItem() == ModItems.upgrade_fortune_3) {
					this.fortune += 3;
					this.timer += 45;
				}
			}
		}

		if(timer < 5)
			timer = 5;
		if(consumption < 40)
			consumption = 40;
		if(radius > 4)
			radius = 4;
		if(fortune > 3)
			fortune = 3;

		age++;
		if(age >= timer)
			age -= timer;

		if(!world.isRemote) {
			power = Library.chargeTEFromItems(inventory, 0, power, maxPower);

			if(power >= consumption) {

				// operation start

				if(age == timer - 1) {
					warning = 0;

					// warning 0, green: drill is operational
					// warning 1, red: drill is full, has no power or the drill
					// is jammed
					// warning 2, yellow: drill has reached max depth

					for(int i = pos.getY() - 1; i > pos.getY() - 1 - 100; i--) {

						if(i <= 5) {
							// Code 2: The drilling ended
							warning = 2;
							break;
						}

						IBlockState ibs = world.getBlockState(new BlockPos(pos.getX(), i, pos.getZ()));
						IBlockState ibs1 = world.getBlockState(new BlockPos(pos.getX(), i - 1, pos.getZ()));
						Block b = ibs.getBlock();
						Block b1 = ibs1.getBlock();
						ItemStack stack = new ItemStack(b.getItemDropped(ibs, rand, fortune), b.quantityDropped(ibs, fortune, rand), b.damageDropped(ibs));
						ItemStack stack1 = new ItemStack(b1.getItemDropped(ibs1, rand, fortune), b1.quantityDropped(ibs1, fortune, rand), b1.damageDropped(ibs1));

						if(i == pos.getY() - 1 && world.getBlockState(new BlockPos(pos.getX(), i, pos.getZ())).getBlock() != ModBlocks.drill_pipe) {
							if(this.isOreo(new BlockPos(pos.getX(), i, pos.getZ())) && this.hasSpace(stack)) {
								// if(stack != null)
								// this.addItemToInventory(stack);
								world.setBlockState(new BlockPos(pos.getX(), i, pos.getZ()), ModBlocks.drill_pipe.getDefaultState());
								break;
							} else {
								// Code 2: Drill jammed
								warning = 1;
								break;
							}
						}

						if(b1 == ModBlocks.drill_pipe) {
							continue;
						} else {

							flag = i != pos.getY() - 1;

							if(this.radius == 1)
								if(!this.drill1(pos.getX(), i, pos.getZ())) {
									if(this.isOreo(new BlockPos(pos.getX(), i - 1, pos.getZ())) && this.hasSpace(stack1)) {
										// if(stack1 != null)
										// this.addItemToInventory(stack1);
										world.setBlockState(new BlockPos(pos.getX(), i - 1, pos.getZ()), ModBlocks.drill_pipe.getDefaultState());
									} else {
										// Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 2)
								if(!this.drill2(pos.getX(), i, pos.getZ())) {
									if(this.isOreo(new BlockPos(pos.getX(), i - 1, pos.getZ())) && this.hasSpace(stack1)) {
										// if(stack1 != null)
										// this.addItemToInventory(stack1);
										world.setBlockState(new BlockPos(pos.getX(), i - 1, pos.getZ()), ModBlocks.drill_pipe.getDefaultState());
									} else {
										// Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 3)
								if(!this.drill3(pos.getX(), i, pos.getZ())) {
									if(this.isOreo(new BlockPos(pos.getX(), i - 1, pos.getZ())) && this.hasSpace(stack1)) {
										// if(stack1 != null)
										// this.addItemToInventory(stack1);
										world.setBlockState(new BlockPos(pos.getX(), i - 1, pos.getZ()), ModBlocks.drill_pipe.getDefaultState());
									} else {
										// Code 2: Drill jammed
										warning = 1;
									}
								}
							if(this.radius == 4)
								if(!this.drill4(pos.getX(), i, pos.getZ())) {
									if(this.isOreo(new BlockPos(pos.getX(), i - 1, pos.getZ())) && this.hasSpace(stack1)) {
										// if(stack1 != null)
										// this.addItemToInventory(stack1);
										world.setBlockState(new BlockPos(pos.getX(), i - 1, pos.getZ()), ModBlocks.drill_pipe.getDefaultState());
									} else {
										// Code 2: Drill jammed
										warning = 1;
									}
								}
							break;
						}
					}
				}

				// operation end

				power -= consumption;
			} else {
				warning = 1;
			}

			int meta = getBlockMetadata();
			TileEntity te = null;
			if(meta == 2) {
				te = world.getTileEntity(pos.add(-2, 0, 0));
				// world.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 3) {
				te = world.getTileEntity(pos.add(2, 0, 0));
				// world.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 4) {
				te = world.getTileEntity(pos.add(0, 0, 2));
				// world.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}
			if(meta == 5) {
				te = world.getTileEntity(pos.add(0, 0, -2));
				// world.setBlock(xCoord - 2, yCoord, zCoord, Blocks.dirt);
			}

			/*if(te != null && te instanceof TileEntityChest) {
				TileEntityChest chest = (TileEntityChest) te;

				for(int i = 1; i < 10; i++)
					if(tryFillContainerCap(chest.getca, i))
						break;
			}

			if(te != null && te instanceof TileEntityHopper) {
				TileEntityHopper hopper = (TileEntityHopper) te;

				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}

			if(te != null && te instanceof TileEntityCrateIron) {
				TileEntityCrateIron hopper = (TileEntityCrateIron) te;

				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}

			if(te != null && te instanceof TileEntityCrateSteel) {
				TileEntityCrateSteel hopper = (TileEntityCrateSteel) te;

				for(int i = 1; i < 10; i++)
					if(tryFillContainer(hopper, i))
						break;
			}*/
			if(te != null && te instanceof ICapabilityProvider){
				ICapabilityProvider capte = (ICapabilityProvider)te;
				if(capte.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY())){
					IItemHandler cap = capte.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, MultiblockHandler.intToEnumFacing(meta).rotateY());
					for(int i = 1; i < 10; i ++)
						if(tryFillContainerCap(cap, i))
							break;
				}
			}

			if(warning == 0) {
				torque += 0.1;
				if(torque > (100 / timer))
					torque = (100 / timer);
			} else {
				torque -= 0.1F;
				if(torque < -(100 / timer))
					torque = -(100 / timer);
			}

			if(torque < 0) {
				torque = 0;
			}
			rotation += torque;
			if(rotation >= 360)
				rotation -= 360;

			PacketDispatcher.wrapper.sendToAllAround(new TEDrillPacket(pos.getX(), pos.getY(), pos.getZ(), rotation, torque), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
			PacketDispatcher.wrapper.sendToAllAround(new LoopedSoundPacket(pos), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 50));
			PacketDispatcher.wrapper.sendToAllAround(new AuxElectricityPacket(pos, power), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
		}
	}

/*	public boolean tryFillContainer(ICapabilityProvider cap, int slot) {
		IItemHandlerModifiable inventory
		int size = inventory.getSizeInventory();

		for(int i = 0; i < size; i++) {
			if(inventory.getStackInSlot(i) != null) {
				
				if(slots[slot] == null)
					return false;
				
				ItemStack sta1 = inventory.getStackInSlot(i).copy();
				ItemStack sta2 = slots[slot].copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);
					sta2.setCount(1);
				
					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inventory.getStackInSlot(i).stackSize < inventory.getStackInSlot(i).getMaxStackSize()) {
						inventory.getStackInSlot(slot).shrink(1);
						
						if(inventory.getStackInSlot(slot).isEmpty())
							inventory.setInventorySlotContents(slot, ItemStack.EMPTY);
						
						ItemStack sta3 = inventory.getStackInSlot(i).copy();
						sta3.grow(1);
						inventory.setInventorySlotContents(i, sta3);
					
						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {
			
			if(slots[slot] == null)
				return false;
			
			ItemStack sta2 = slots[slot].copy();
			if(inventory.getStackInSlot(i) == null && sta2 != null) {
				sta2.stackSize = 1;
				slots[slot].stackSize--;
				
				if(slots[slot].stackSize <= 0)
					slots[slot] = null;
				
				inventory.setInventorySlotContents(i, sta2);
					
				return true;
			}
		}
		
		return false;
	}*/

	// Unloads output into chests. Capability version.
	public boolean tryFillContainerCap(IItemHandler inv, int slot) {

		int size = inv.getSlots();

		for(int i = 0; i < size; i++) {
			if(inv.getStackInSlot(i) != null) {

				if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
					return false;

				ItemStack sta1 = inv.getStackInSlot(i).copy();
				ItemStack sta2 = inventory.getStackInSlot(slot).copy();
				if(sta1 != null && sta2 != null) {
					sta1.setCount(1);
					sta2.setCount(1);

					if(ItemStack.areItemStacksEqual(sta1, sta2) && ItemStack.areItemStackTagsEqual(sta1, sta2) && inv.getStackInSlot(i).getCount() < inv.getStackInSlot(i).getMaxStackSize()) {
						inventory.getStackInSlot(slot).shrink(1);

						if(inventory.getStackInSlot(slot).isEmpty())
							inventory.setStackInSlot(slot, ItemStack.EMPTY);

						ItemStack sta3 = inv.getStackInSlot(i).copy();
						sta3.setCount(1);
						inv.insertItem(i, sta3, false);

						return true;
					}
				}
			}
		}
		for(int i = 0; i < size; i++) {

			if(inventory.getStackInSlot(slot).getItem() == Items.AIR)
				return false;

			ItemStack sta2 = inventory.getStackInSlot(slot).copy();
			if(inv.getStackInSlot(i).getItem() == Items.AIR && sta2 != null) {
				sta2.setCount(1);
				inventory.getStackInSlot(slot).shrink(1);
				;

				if(inventory.getStackInSlot(slot).isEmpty())
					inventory.setStackInSlot(slot, ItemStack.EMPTY);

				inv.insertItem(i, sta2, false);

				return true;
			}
		}

		return false;
	}

	// Method: isOre
	// "make it oreo!"
	// "ok"
	public boolean isOreo(BlockPos pos) {

		/*Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if(b == Blocks.air || b == Blocks.grass || b == Blocks.dirt || 
				b == Blocks.stone || b == Blocks.sand || b == Blocks.sandstone || 
				b == Blocks.clay || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
				b == Blocks.gravel || b.isReplaceable(world, x, y, z))
			return true;
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}*/

		IBlockState b = world.getBlockState(pos);
		float hardness = b.getBlockHardness(world, pos);

		return hardness < 70 && hardness >= 0;
	}

	public boolean isMinableOreo(BlockPos pos) {

		/*Block b = world.getBlock(x, y, z);
		int meta = world.getBlockMetadata(x, y, z);
		
		if(b == Blocks.grass || b == Blocks.dirt || 
				b == Blocks.stone || b == Blocks.sand || b == Blocks.sandstone || 
				b == Blocks.clay || b == Blocks.hardened_clay || b == Blocks.stained_hardened_clay || 
				b == Blocks.gravel || b.isReplaceable(world, x, y, z))
			return true;
		
		int[] ids = OreDictionary.getOreIDs(new ItemStack(b, 1, meta));
		
		for(int i = 0; i < ids.length; i++) {
			
			String s = OreDictionary.getOreName(ids[i]);
			
			if(s.length() > 3 && s.substring(0, 3).equals("ore"))
				return true;
		}
		
		return false;*/

		IBlockState b = world.getBlockState(pos);
		float hardness = b.getBlockHardness(world, pos);

		return hardness < 70 && hardness >= 0;
	}

	//TODO Drillgon200: Fix this absolute mess
	/**
	 * returns true if there has been a successful mining operation returns
	 * false if no block could be mined and the drill is ready to extend
	 */
	public boolean drill1(int x, int y, int z) {

		if(!flag)
			return false;

		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x, y - 1, z))
											return false;

		return true;
	}

	public boolean drill2(int x, int y, int z) {

		if(!flag)
			return false;

		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x, y - 1, z))
																							return false;

		return true;
	}

	public boolean drill3(int x, int y, int z) {

		if(!flag)
			return false;

		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x + 3, y, z))
																							if(!tryDrill(x + 3, y, z + 1))
																								if(!tryDrill(x + 2, y, z + 2))
																									if(!tryDrill(x + 1, y, z + 3))
																										if(!tryDrill(x, y, z + 3))
																											if(!tryDrill(x - 1, y, z + 3))
																												if(!tryDrill(x - 2, y, z + 2))
																													if(!tryDrill(x - 3, y, z + 1))
																														if(!tryDrill(x - 3, y, z))
																															if(!tryDrill(x - 3, y, z - 1))
																																if(!tryDrill(x - 2, y, z - 2))
																																	if(!tryDrill(x - 1, y, z - 3))
																																		if(!tryDrill(x, y, z - 3))
																																			if(!tryDrill(x + 1, y, z - 3))
																																				if(!tryDrill(x + 2, y, z - 2))
																																					if(!tryDrill(x + 3, y, z - 1))

																																						if(!tryDrill(x, y - 1, z))
																																							return false;

		return true;
	}

	public boolean drill4(int x, int y, int z) {

		if(!flag)
			return false;

		if(!tryDrill(x + 1, y, z))
			if(!tryDrill(x + 1, y, z + 1))
				if(!tryDrill(x, y, z + 1))
					if(!tryDrill(x - 1, y, z + 1))
						if(!tryDrill(x - 1, y, z))
							if(!tryDrill(x - 1, y, z - 1))
								if(!tryDrill(x, y, z - 1))
									if(!tryDrill(x + 1, y, z - 1))

										if(!tryDrill(x + 2, y, z))
											if(!tryDrill(x + 2, y, z + 1))
												if(!tryDrill(x + 1, y, z + 2))
													if(!tryDrill(x, y, z + 2))
														if(!tryDrill(x - 1, y, z + 2))
															if(!tryDrill(x - 2, y, z + 1))
																if(!tryDrill(x - 2, y, z))
																	if(!tryDrill(x - 2, y, z - 1))
																		if(!tryDrill(x - 1, y, z - 2))
																			if(!tryDrill(x, y, z - 2))
																				if(!tryDrill(x + 1, y, z - 2))
																					if(!tryDrill(x + 2, y, z - 1))

																						if(!tryDrill(x + 3, y, z))
																							if(!tryDrill(x + 3, y, z + 1))
																								if(!tryDrill(x + 2, y, z + 2))
																									if(!tryDrill(x + 1, y, z + 3))
																										if(!tryDrill(x, y, z + 3))
																											if(!tryDrill(x - 1, y, z + 3))
																												if(!tryDrill(x - 2, y, z + 2))
																													if(!tryDrill(x - 3, y, z + 1))
																														if(!tryDrill(x - 3, y, z))
																															if(!tryDrill(x - 3, y, z - 1))
																																if(!tryDrill(x - 2, y, z - 2))
																																	if(!tryDrill(x - 1, y, z - 3))
																																		if(!tryDrill(x, y, z - 3))
																																			if(!tryDrill(x + 1, y, z - 3))
																																				if(!tryDrill(x + 2, y, z - 2))
																																					if(!tryDrill(x + 3, y, z - 1))

																																						if(!tryDrill(x + 4, y, z))
																																							if(!tryDrill(x + 4, y, z + 1))
																																								if(!tryDrill(x + 4, y, z + 2))
																																									if(!tryDrill(x + 3, y, z + 2))
																																										if(!tryDrill(x + 3, y, z + 3))
																																											if(!tryDrill(x + 2, y, z + 3))
																																												if(!tryDrill(x + 2, y, z + 4))
																																													if(!tryDrill(x + 1, y, z + 4))
																																														if(!tryDrill(x, y, z + 4))
																																															if(!tryDrill(x - 1, y, z + 4))
																																																if(!tryDrill(x - 2, y, z + 4))
																																																	if(!tryDrill(x - 2, y, z + 3))
																																																		if(!tryDrill(x - 3, y, z + 3))
																																																			if(!tryDrill(x - 3, y, z + 2))
																																																				if(!tryDrill(x - 4, y, z + 2))
																																																					if(!tryDrill(x - 4, y, z + 1))
																																																						if(!tryDrill(x - 4, y, z))
																																																							if(!tryDrill(x - 4, y, z - 1))
																																																								if(!tryDrill(x - 4, y, z - 2))
																																																									if(!tryDrill(x - 3, y, z - 2))
																																																										if(!tryDrill(x - 3, y, z - 3))
																																																											if(!tryDrill(x - 2, y, z - 3))
																																																												if(!tryDrill(x - 2, y, z - 4))
																																																													if(!tryDrill(x - 1, y, z - 4))
																																																														if(!tryDrill(x, y, z - 4))
																																																															if(!tryDrill(x + 1, y, z - 4))
																																																																if(!tryDrill(x + 2, y, z - 4))
																																																																	if(!tryDrill(x + 2, y, z - 3))
																																																																		if(!tryDrill(x + 3, y, z - 3))
																																																																			if(!tryDrill(x + 3, y, z - 2))
																																																																				if(!tryDrill(x + 4, y, z - 2))
																																																																					if(!tryDrill(x + 4, y, z - 1))

																																																																						if(!tryDrill(x, y - 1, z))
																																																																							return false;

		return true;
	}

	/**
	 * returns true if there has been a successful mining operation returns
	 * false if no block could be mined, as it is either air or unmineable
	 */
	public boolean tryDrill(int x, int y, int z) {
		BlockPos pos = new BlockPos(x, y, z);
		if(world.getBlockState(pos).getBlock() == Blocks.AIR || !isMinableOreo(pos))
			return false;
		if(world.getBlockState(pos).getMaterial().isLiquid()) {
			world.destroyBlock(pos, false);
			return false;
		}

		IBlockState b = world.getBlockState(pos);
		ItemStack stack = new ItemStack(b.getBlock().getItemDropped(b, rand, fortune), b.getBlock().quantityDropped(b, fortune, rand), b.getBlock().damageDropped(b));

		// yup that worked
		if(stack != null && stack.getItem() == null) {
			world.destroyBlock(pos, false);
			return true;
		}

		if(hasSpace(stack)) {
			this.addItemToInventory(stack);
			world.destroyBlock(pos, false);
			return true;
		}

		return true;
	}

	public boolean hasSpace(ItemStack stack) {

		ItemStack st = stack.copy();

		if(st == null)
			return true;

		for(int i = 1; i < 10; i++) {
			if(inventory.getStackInSlot(i).isEmpty())
				return true;
		}

		st.setCount(1);

		boolean flag = true;
		for(int i = 0; i < stack.getCount(); i++) {
			if(!canAddItemToArray(st, inventory))
				flag = false;
		}

		return flag;
	}

	public void addItemToInventory(ItemStack stack) {

		ItemStack st = stack.copy();

		if(st == null)
			return;

		int size = st.getCount();
		st.setCount(1);

		for(int i = 0; i < size; i++)
			canAddItemToArray(st, inventory);

	}

	public boolean canAddItemToArray(ItemStack stack, ItemStackHandler array) {

		ItemStack st = stack.copy();

		if(stack == null || st == null)
			return true;

		for(int i = 1; i < 10; i++) {

			if(!array.getStackInSlot(i).isEmpty()) {
				ItemStack sta = array.getStackInSlot(i).copy();

				if(stack == null || st == null)
					return true;
				if(sta != null && sta.getItem() == st.getItem() && sta.getCount() < st.getMaxStackSize()) {
					array.getStackInSlot(i).grow(1);
					return true;
				}
			}
		}

		for(int i = 1; i < 10; i++) {
			if(array.getStackInSlot(i).isEmpty()) {
				array.setStackInSlot(i, stack.copy());
				return true;
			}
		}

		return false;
	}

	@Override
	public void setPower(long i) {
		power = i;

	}

	@Override
	public long getPower() {
		return power;

	}

	@Override
	public long getMaxPower() {
		return maxPower;
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
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY ? CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inventory) : super.getCapability(capability, facing);
	}

}
