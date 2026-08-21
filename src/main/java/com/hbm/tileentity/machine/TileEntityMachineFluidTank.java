package com.hbm.tileentity.machine;

import com.hbm.forgefluid.FFPipeNetworkMk2;
import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.IFluidPipeMk2;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.control_panel.*;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.tileentity.TileEntityProxyCombo;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class TileEntityMachineFluidTank extends TileEntityMachineBase implements ITickable, IFluidHandler, ITankPacketAcceptor, IControllable {

	public FluidTank tank;

	public short mode = 0;
	public static final short modes = 4;
	public int age = 0;
	public static int[] slots = { 2 };
	public FFPipeNetworkMk2 network;
	private boolean updatingNetwork = false;
	
	public TileEntityMachineFluidTank() {
		super(6);
		tank = new FluidTank(256000);
	}
	
	public String getName() {
		return "container.fluidtank";
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		tank.readFromNBT(compound);
		mode = compound.getShort("mode");
		super.readFromNBT(compound);
	}
	
	@Override
	public @NotNull NBTTagCompound writeToNBT(NBTTagCompound compound) {
		tank.writeToNBT(compound);
		compound.setShort("mode", mode);
		return super.writeToNBT(compound);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return slots;
	}
	
	@Override
	public void update() {
		if (!world.isRemote) {
			age++;
			if (age >= 20) {
				age = 0;
			}

			updateFluidNetwork();
			
			FFUtils.fillFromFluidContainer(inventory, tank, 2, 3);
			FFUtils.fillFluidContainer(inventory, tank, 4, 5);
			
			if(tank.getFluid() != null && (tank.getFluid().getFluid() == ModForgeFluids.AMAT || tank.getFluid().getFluid() == ModForgeFluids.ASCHRAB)) {
				world.destroyBlock(pos, false);
				world.newExplosion(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, true, true);
			}
			
			PacketDispatcher.wrapper.sendToAllTracking(new FluidTankPacket(pos.getX(), pos.getY(), pos.getZ(), new FluidTank[] {tank}), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 10));
			NBTTagCompound data = new NBTTagCompound();
			data.setShort("mode", mode);
			this.networkPack(data, 50);
			
			detectAndSendChanges();
		}
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		mode = nbt.getShort("mode");
	}
	
	@Override
	public void handleButtonPacket(int value, int meta) {
		mode = (short) ((mode + 1) % modes);
		if (!world.isRemote) {
			broadcastControlEvt();
		}
		markDirty();
	}

	private BlockPos[] getConnectionPositions() {
		List<BlockPos> positions = new ArrayList<>();
		BlockPos[] corners = { pos.add(1, 0, 1), pos.add(1, 0, -1), pos.add(-1, 0, 1), pos.add(-1, 0, -1) };
		for(BlockPos corner : corners) {
			if(world.getTileEntity(corner) instanceof TileEntityDummyFluidPort) {
				int dx = corner.getX() - pos.getX();
				int dz = corner.getZ() - pos.getZ();
				positions.add(corner.add(dx, 0, 0));
				positions.add(corner.add(0, 0, dz));
			}
		}
		return positions.toArray(new BlockPos[0]);
	}

	protected void updateFluidNetwork() {
		if(updatingNetwork) return;
		updatingNetwork = true;

		Fluid tankFluid = tank.getFluid() != null ? tank.getFluid().getFluid() : null;

		if(mode == 1) {
			if(network != null && (network.getType() == null || network.getType() != tankFluid) && tankFluid != null) {
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
						if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
							fluid = tank.tank.getFluid().getFluid();
							break;
						} else if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
							fluid = barrel.tank.getFluid().getFluid();
							break;
						} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
							fluid = pipe.getType();
							break;
						}
					}
				}
				if(fluid != null) {
					network = new FFPipeNetworkMk2(fluid);
				}
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
				if(te instanceof TileEntityMachineFluidTank tank) {
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
				} else if(te instanceof TileEntityBarrel barrel) {
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
					if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					} else if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
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
				if(te instanceof TileEntityMachineFluidTank tank) {
					foundNet = tank.network;
				} else if(te instanceof TileEntityBarrel barrel) {
					foundNet = barrel.network;
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
					if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
						fluid = tank.tank.getFluid().getFluid();
					} else if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
						fluid = barrel.tank.getFluid().getFluid();
					} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
						fluid = pipe.getType();
					}
					if(fluid != null) {
						this.network = new FFPipeNetworkMk2(fluid);
						this.network.addReceiver(this);
						found = true;
					}
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
					if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					} else if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
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
				if(te instanceof TileEntityMachineFluidTank tank) {
					foundNet = tank.network;
				} else if(te instanceof TileEntityBarrel barrel) {
					foundNet = barrel.network;
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
					if(te instanceof TileEntityMachineFluidTank tank && tank.tank.getFluid() != null) {
						fluid = tank.tank.getFluid().getFluid();
					} else if(te instanceof TileEntityBarrel barrel && barrel.tank.getFluid() != null) {
						fluid = barrel.tank.getFluid().getFluid();
					} else if(te instanceof IFluidPipeMk2 pipe && pipe.getType() != null) {
						fluid = pipe.getType();
					}
					if(fluid != null) {
						this.network = new FFPipeNetworkMk2(fluid);
						this.network.addProvider(this);
						found = true;
					}
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
					if(te instanceof TileEntityMachineFluidTank tank) {
						tank.updateFluidNetwork();
					} else if(te instanceof TileEntityBarrel barrel) {
						barrel.updateFluidNetwork();
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

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}
	
	private FluidTank detectTank;
	
	private void detectAndSendChanges() {
		boolean mark = false;
		if(!FFUtils.areTanksEqual(tank, detectTank)){
			mark = true;
			detectTank = FFUtils.copyTank(tank);
		}
		if(mark)
			markDirty();
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{tank.getTankProperties()[0]};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (this.canFill(resource.getFluid())) {		
			return tank.fill(resource, doFill);
		}
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if(mode == 0 || mode == 3)
			return null;
		if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
			return null;
		}
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if(mode == 0 || mode == 3)
			return null;
		return tank.drain(maxDrain, doDrain);
	}
	
	public boolean canFill(Fluid fluid) {
		if (!this.world.isRemote) {
            return mode != 2 && mode != 3 && (tank.getFluid() == null || tank.getFluid().getFluid() == fluid);
		}
		return false;
	}

	public boolean canDrain(Fluid fluid) {
		if (!this.world.isRemote) {
			return tank.getFluid() != null;
		}
		return false;
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1) {
			return;
		} else {
			tank.readFromNBT(tags[0]);
		}
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		} else {
			return super.getCapability(capability, facing);
		}
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY){
			return true;
		} else {
			return super.hasCapability(capability, facing);
		}
	}

	// control panel

	@Override
	public Map<String, DataValue> getQueryData() {
		Map<String, DataValue> data = new HashMap<>();

		if (tank.getFluid() != null) {
			data.put("t0_fluidType", new DataValueString(tank.getFluid().getLocalizedName()));
		}
		data.put("t0_fluidAmount", new DataValueFloat(tank.getFluidAmount()));
		data.put("mode", new DataValueFloat(mode));

		return data;
	}

	@Override
	public void receiveEvent(BlockPos from, ControlEvent e) {
		if (e.name.equals("tank_set_mode")) {
			mode = (short) (e.vars.get("mode").getNumber() % modes);
			broadcastControlEvt();
		}
	}

	public void broadcastControlEvt() {
		ControlEventSystem.get(world).broadcastToSubscribed(this, ControlEvent.newEvent("tank_set_mode").setVar("mode", new DataValueFloat(mode)));
	}

	@Override
	public List<String> getInEvents() {
		return Collections.singletonList("tank_set_mode");
	}

	@Override
	public List<String> getOutEvents() {
		return Collections.singletonList("tank_set_mode");
	}

	@Override
	public void validate() {
		super.validate();
		ControlEventSystem.get(world).addControllable(this);
	}

	@Override
	public void invalidate() {
		super.invalidate();
		ControlEventSystem.get(world).removeControllable(this);
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

	@Override
	public BlockPos getControlPos() {
		return getPos();
	}

	@Override
	public World getControlWorld() {
		return getWorld();
	}
}
