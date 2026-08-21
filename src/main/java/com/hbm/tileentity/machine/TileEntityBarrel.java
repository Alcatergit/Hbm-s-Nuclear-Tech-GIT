package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.forgefluid.FFPipeNetworkMk2;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.FluidTypeHandler;
import com.hbm.forgefluid.FluidTypeHandler.FluidTrait;
import com.hbm.interfaces.IFluidPipeMk2;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityProxyCombo;

import net.minecraft.block.Block;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import org.jetbrains.annotations.NotNull;

public class TileEntityBarrel extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor {

	public FluidTank tank;
	public short mode = 0;
	public static final short modes = 4;
	public FFPipeNetworkMk2 network;
	private boolean updatingNetwork = false;

	private static final int[] slots_top = new int[] {0};
	private static final int[] slots_bottom = new int[] {1, 3};
	private static final int[] slots_side = new int[] {2};
	
	public TileEntityBarrel() {
		super(4);
		tank = new FluidTank(-1);
	}
	
	public TileEntityBarrel(int cap) {
		super(4);
		tank = new FluidTank(cap);
	}

	@Override
	public void update() {
		
		if(!world.isRemote){
			FluidTank compareTank = FFUtils.copyTank(tank);
			FFUtils.fillFromFluidContainer(inventory, tank, 0, 1);
			FFUtils.fillFluidContainer(inventory, tank, 2, 3);

			updateFluidNetwork();
			
			if(tank.getFluid() != null && tank.getFluidAmount() > 0) {
				checkFluidInteraction();
			}
			
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, new FluidTank[]{tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 100));
			if(!FFUtils.areTanksEqual(tank, compareTank))
				markDirty();
		}
	}

	protected void updateFluidNetwork() {
		if(updatingNetwork) return;
		updatingNetwork = true;

		Fluid tankFluid = tank.getFluid() != null ? tank.getFluid().getFluid() : null;

		if(mode == 1) {
			if(network != null && network.getType() != tankFluid && tankFluid != null) {
				network.removeProvider(this);
				network.removeReceiver(this);
				network = null;
			}

			if(network == null) {
				Fluid fluid = tankFluid;
				if(fluid == null) {
					BlockPos[] conPositions = getConnectionPositions();
					for(BlockPos conPos : conPositions) {
						if(!world.isBlockLoaded(conPos)) continue;
						TileEntity te = world.getTileEntity(conPos);
						if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
							te = world.getTileEntity(dummy.target);
						}
						if(te instanceof TileEntityProxyCombo proxy) {
							TileEntity resolved = proxy.getTE();
							if(resolved != null) te = resolved;
						}
						if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
							fluid = barrel.tank.getFluid().getFluid();
							break;
						} else if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
							fluid = tank.tank.getFluid().getFluid();
							break;
						} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
							fluid = pipe.getType();
							break;
						}
					}
				}
				network = new FFPipeNetworkMk2(fluid);
			}

			BlockPos[] conPositions = getConnectionPositions();
			for(BlockPos conPos : conPositions) {
				if(!world.isBlockLoaded(conPos)) continue;
				TileEntity te = world.getTileEntity(conPos);
				if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
					te = world.getTileEntity(dummy.target);
				}
				if(te instanceof TileEntityProxyCombo proxy) {
					TileEntity resolved = proxy.getTE();
					if(resolved != null) te = resolved;
				}
				if(te instanceof TileEntityBarrel barrel) {
					if(barrel.mode == 1) {
						if(barrel.network != null && barrel.network != this.network) {
							if(this.network == null) {
								this.network = barrel.network;
							} else {
								this.network = FFPipeNetworkMk2.mergeNetworks(this.network, barrel.network);
							}
							barrel.network = this.network;
						} else if(barrel.network == null && this.network != null) {
							barrel.network = this.network;
							this.network.addProvider(barrel);
							this.network.addReceiver(barrel);
						}
					} else if(barrel.network != null && barrel.network != this.network && barrel.mode != 3) {
						if(this.network == null) {
							this.network = barrel.network;
						} else {
							this.network = FFPipeNetworkMk2.mergeNetworks(this.network, barrel.network);
						}
						barrel.network = this.network;
					} else if(barrel.network == null && this.network != null && barrel.mode != 3) {
						barrel.network = this.network;
						if(barrel.mode == 0) {
							this.network.addReceiver(barrel);
						} else if(barrel.mode == 2) {
							this.network.addProvider(barrel);
						}
					}
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					if(tank.mode == 1) {
						if(tank.network != null && tank.network != this.network) {
							if(this.network == null) {
								this.network = tank.network;
							} else {
								this.network = FFPipeNetworkMk2.mergeNetworks(this.network, tank.network);
							}
							tank.network = this.network;
						} else if(tank.network == null && this.network != null) {
							tank.network = this.network;
							this.network.addProvider(tank);
							this.network.addReceiver(tank);
						}
					} else if(tank.network != null && tank.network != this.network && tank.mode != 3) {
						if(this.network == null) {
							this.network = tank.network;
						} else {
							this.network = FFPipeNetworkMk2.mergeNetworks(this.network, tank.network);
						}
						tank.network = this.network;
					} else if(tank.network == null && this.network != null && tank.mode != 3) {
						tank.network = this.network;
						if(tank.mode == 0) {
							this.network.addReceiver(tank);
						} else if(tank.mode == 2) {
							this.network.addProvider(tank);
						}
					}
				} else if(te instanceof IFluidPipeMk2 pipe) {
					if(pipe.getNetwork() != null && pipe.getNetwork() != this.network
							&& (this.network == null || this.network.getType() == null || pipe.getType() == this.network.getType())) {
						if(this.network == null) {
							this.network = pipe.getNetwork();
						} else {
							this.network = FFPipeNetworkMk2.mergeNetworks(this.network, pipe.getNetwork());
						}
					}
				} else if(this.network != null) {
					this.network.tryAdd(te);
				}
			}

			if(this.network != null) {
				this.network.addProvider(this);
				this.network.addReceiver(this);

				for(BlockPos conPos : conPositions) {
					if(!world.isBlockLoaded(conPos)) continue;
					TileEntity te = world.getTileEntity(conPos);
					if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
						te = world.getTileEntity(dummy.target);
					}
					if(te instanceof TileEntityProxyCombo proxy) {
						TileEntity resolved = proxy.getTE();
						if(resolved != null) te = resolved;
					}
					if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
					} else if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					}
				}

				this.network.update(world);
			}
		} else if(mode == 0) {
			BlockPos[] conPositions = getConnectionPositions();
			boolean found = false;
			for(BlockPos conPos : conPositions) {
				if(!world.isBlockLoaded(conPos)) continue;
				TileEntity te = world.getTileEntity(conPos);
				if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
					te = world.getTileEntity(dummy.target);
				}
				if(te instanceof TileEntityProxyCombo proxy) {
					TileEntity resolved = proxy.getTE();
					if(resolved != null) te = resolved;
				}
				FFPipeNetworkMk2 foundNet = null;
				if(te instanceof TileEntityBarrel barrel) {
					foundNet = barrel.network;
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					foundNet = tank.network;
				} else if(te instanceof IFluidPipeMk2 pipe) {
					foundNet = pipe.getNetwork();
				}
				if(foundNet != null) {
					foundNet.addReceiver(this);
					if(this.network != foundNet) {
						if(this.network != null) this.network.removeReceiver(this);
						this.network = foundNet;
					}
					found = true;
				} else if(!found && this.network == null) {
					Fluid fluid = null;
					if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
						fluid = barrel.tank.getFluid().getFluid();
					} else if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
						fluid = tank.tank.getFluid().getFluid();
					} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
						fluid = pipe.getType();
					}
					this.network = new FFPipeNetworkMk2(fluid);
					this.network.addReceiver(this);
					found = true;
				}
			}
			if(!found && this.network != null) {
				this.network.removeReceiver(this);
				this.network = null;
			}
			if(this.network != null) {
				for(BlockPos conPos : conPositions) {
					if(!world.isBlockLoaded(conPos)) continue;
					TileEntity te = world.getTileEntity(conPos);
					if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
						te = world.getTileEntity(dummy.target);
					}
					if(te instanceof TileEntityProxyCombo proxy) {
						TileEntity resolved = proxy.getTE();
						if(resolved != null) te = resolved;
					}
					if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
					} else if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					}
				}
				this.network.update(world);
			}
		} else if(mode == 2) {
			BlockPos[] conPositions = getConnectionPositions();
			boolean found = false;
			for(BlockPos conPos : conPositions) {
				if(!world.isBlockLoaded(conPos)) continue;
				TileEntity te = world.getTileEntity(conPos);
				if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
					te = world.getTileEntity(dummy.target);
				}
				if(te instanceof TileEntityProxyCombo proxy) {
					TileEntity resolved = proxy.getTE();
					if(resolved != null) te = resolved;
				}
				FFPipeNetworkMk2 foundNet = null;
				if(te instanceof TileEntityBarrel barrel) {
					foundNet = barrel.network;
				} else if(te instanceof TileEntityMachineFluidTank tank) {
					foundNet = tank.network;
				} else if(te instanceof IFluidPipeMk2 pipe) {
					foundNet = pipe.getNetwork();
				}
				if(foundNet != null) {
					foundNet.addProvider(this);
					if(this.network != foundNet) {
						if(this.network != null) this.network.removeProvider(this);
						this.network = foundNet;
					}
					found = true;
				} else if(!found && this.network == null) {
					Fluid fluid = null;
					if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
						fluid = barrel.tank.getFluid().getFluid();
					} else if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
						fluid = tank.tank.getFluid().getFluid();
					} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
						fluid = pipe.getType();
					}
					this.network = new FFPipeNetworkMk2(fluid);
					this.network.addProvider(this);
					found = true;
				}
			}
			if(!found && this.network != null) {
				this.network.removeProvider(this);
				this.network = null;
			}
			if(this.network != null) {
				for(BlockPos conPos : conPositions) {
					if(!world.isBlockLoaded(conPos)) continue;
					TileEntity te = world.getTileEntity(conPos);
					if(te instanceof TileEntityDummyFluidPort dummy && dummy.target != null && !dummy.target.equals(pos)) {
						te = world.getTileEntity(dummy.target);
					}
					if(te instanceof TileEntityProxyCombo proxy) {
						TileEntity resolved = proxy.getTE();
						if(resolved != null) te = resolved;
					}
					if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
					} else if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					}
				}
				this.network.update(world);
			}
		} else {
			if(this.network != null) {
				this.network.removeProvider(this);
				this.network.removeReceiver(this);
				this.network = null;
			}
		}

		updatingNetwork = false;
	}

	public BlockPos[] getConnectionPositions() {
		return new BlockPos[] {
				pos.up(), pos.down(), pos.north(), pos.south(), pos.east(), pos.west()
		};
	}
	
	@Override
	public void invalidate() {
		super.invalidate();
		if(!world.isRemote && network != null) {
			network.removeProvider(this);
			network.removeReceiver(this);
			network = null;
		}
	}
	
	@Override
	public void onChunkUnload() {
		if(!world.isRemote && network != null) {
			network.removeProvider(this);
			network.removeReceiver(this);
		}
		super.onChunkUnload();
	}
	
	public void checkFluidInteraction(){
		Block b = this.getBlockType();
		Fluid f = tank.getFluid().getFluid();
		
		//for when you fill antimatter into a matter tank
		if(b != ModBlocks.barrel_antimatter && FluidTypeHandler.containsTrait(f, FluidTrait.AMAT)) {
			world.destroyBlock(pos, false);
			world.newExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, true, true);
		}
		
		//for when you fill hot or corrosive liquids into a plastic tank
		if(b == ModBlocks.barrel_plastic && (FluidTypeHandler.isCorrosivePlastic(f) || FluidTypeHandler.isHot(f))) {
			world.destroyBlock(pos, false);
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
		}
		
		//for when you fill corrosive liquid into an iron tank
		if((b == ModBlocks.barrel_iron && FluidTypeHandler.isCorrosivePlastic(f)) || (b == ModBlocks.barrel_steel && FluidTypeHandler.isCorrosiveIron(f))) {
			
			world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
			world.setBlockState(pos, ModBlocks.barrel_corroded.getDefaultState());
			
			TileEntityBarrel corroded_barrel = (TileEntityBarrel)world.getTileEntity(pos);
			
			corroded_barrel.tank.fill(tank.getFluid(), true);
		}
		
		if(b == ModBlocks.barrel_corroded && world.rand.nextInt(3) == 0) {
			tank.drain(1, true);
		}
	}
	
	public void fillFluidInit(FluidTank tank) {
		fillFluid(pos.east(), tank);
		fillFluid(pos.west(), tank);
		fillFluid(pos.up(), tank);
		fillFluid(pos.down(), tank);
		fillFluid(pos.south(), tank);
		fillFluid(pos.north(), tank);
	}

	public void fillFluid(BlockPos pos1, FluidTank tank) {
		FFUtils.fillFluid(this, tank, world, pos1, 4000);
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if(mode == 2 || mode == 3)
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(mode == 0 || mode == 3)
			return null;
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public String getName() {
		return "container.barrel";
	}
	
	@Override
	public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setShort("mode", mode);
		compound.setInteger("cap", tank.getCapacity());
		tank.writeToNBT(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		mode = compound.getShort("mode");
		if(tank == null || tank.getCapacity() <= 0)
			tank = new FluidTank(compound.getInteger("cap"));
		tank.readFromNBT(compound);
		super.readFromNBT(compound);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length == 1)
			tank.readFromNBT(tags[0]);
	}

	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e) {
		int i = e.ordinal();
		return i == 0 ? slots_bottom : (i == 1 ? slots_top : slots_side);
	}
	
	@Override
	public boolean isItemValidForSlot(int i, ItemStack stack) {
		if(i == 0){
			return true;
		}

        return i == 2;
    }
	
	@Override
	public boolean canInsertItem(int slot, ItemStack itemStack, int amount) {
		return isItemValidForSlot(slot, itemStack);
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemStack, int amount) {
		if(slot == 1){
			return true;
		}

        return slot == 3;
    }
}
