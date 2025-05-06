package com.hbm.tileentity.machine;

import java.util.List;

import com.hbm.forgefluid.FFUtils;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.container.ContainerFurnaceCombo;
import com.hbm.inventory.gui.GUIFurnaceCombo;
import com.hbm.inventory.CombinationRecipes;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.main.MainRegistry;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.tileentity.IGUIProvider;
import com.hbm.tileentity.TileEntityMachineBase;
import com.hbm.util.Tuple.Pair;

import api.hbm.tile.IHeatSource;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.ITickable;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityFurnaceCombination extends TileEntityMachineBase implements IGUIProvider, IFluidHandler, ITickable, ITankPacketAcceptor {

	public boolean wasOn;
	public int progress;
	public static final int processTime = 20_000;
	
	public int heat;
	public static final int maxHeat = 100_000;
	public static final double diffusion = 0.25D;

	public FluidTank tank;

	public TileEntityFurnaceCombination() {
		super(4);
		tank = new FluidTank(24000);
	}

	@Override
	public String getName() {
		return "container.furnaceCombination";
	}

	@Override
	public void update() {
		
		if(!world.isRemote) {
			tryPullHeat();
			
			if(this.world.getTotalWorldTime() % 20 == 0) {
				for(int i = 2; i < 6; i++) {
					ForgeDirection dir = ForgeDirection.getOrientation(i);
					ForgeDirection rot = dir.getRotation(ForgeDirection.UP);
					
					for(int y = pos.getY(); y <= pos.getY() + 1; y++) {
						for(int j = -1; j <= 1; j++) {
							if(tank.getFluidAmount() > 0)
								FFUtils.fillFluid(this, tank, world, pos.add(dir.offsetX * 2 + rot.offsetX * j, 0, dir.offsetZ * 2 + rot.offsetZ * j), 8000);
						}
					}
				}
			}
			
			this.wasOn = false;

			FFUtils.fillFluidContainer(inventory, tank, 2, 3);
			
			if(canSmelt()) {
				int burn = heat / 100;
				
				if(burn > 0) {
					this.wasOn = true;
					this.progress += burn;
					this.heat -= burn;
					
					if(progress >= processTime) {
						this.markDirty();
						progress -= processTime;
						
						Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(inventory.getStackInSlot(0));
						ItemStack out = pair.getKey();
						FluidStack fluid = pair.getValue();
						
						if(out != null && !out.isEmpty())  {
							if(inventory.getStackInSlot(1).isEmpty()) {
								inventory.setStackInSlot(1, out.copy());
							} else {
								inventory.getStackInSlot(1).grow(out.getCount());
							}
						}
						
						if(fluid != null) {
							tank.fill(fluid.copy(), true);
						}

						inventory.getStackInSlot(0).shrink(1);
						if(inventory.getStackInSlot(0).isEmpty())
							inventory.setStackInSlot(0, ItemStack.EMPTY);
					}
					
					List<Entity> entities = world.getEntitiesWithinAABB(Entity.class, new AxisAlignedBB(pos.getX() - 0.5, pos.getY() + 2, pos.getZ() - 0.5, pos.getX() + 1.5, pos.getY() + 4, pos.getZ() + 1.5));
					
					for(Entity e : entities) e.setFire(5);
					
					if(world.getTotalWorldTime() % 10 == 0) this.world.playSound(null, pos.up(), HBMSoundHandler.flamethrowerShoot, SoundCategory.BLOCKS, 0.25F, 0.5F);
				}
			} else {
				this.progress = 0;
			}

			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, tank) , new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 120));

			NBTTagCompound data = new NBTTagCompound();
			data.setInteger("progress", progress);
			data.setInteger("heat", heat);
			data.setBoolean("wasOn", wasOn);
			this.networkPack(data, 50);
		} else if(this.wasOn) {
			
			if(world.rand.nextInt(15) == 0) {
				world.spawnParticle(EnumParticleTypes.LAVA, pos.getX() + 0.5 + world.rand.nextGaussian() * 0.5, pos.getY() + 2, pos.getZ() + 0.5 + world.rand.nextGaussian() * 0.5, 0, 0, 0);
			}
			if(world.rand.nextInt(40) == 0) {
				NBTTagCompound data = new NBTTagCompound();
				data.setString("type", "rbmkflame");
				data.setInteger("maxAge", 300);
				data.setFloat("scale", 0.5F);
				data.setDouble("posX", pos.getX() + world.rand.nextDouble());
				data.setDouble("posY", pos.getY() + 1.95);
				data.setDouble("posZ", pos.getZ() + world.rand.nextDouble());
				MainRegistry.proxy.effectNT(data);
			}
		}
	}
	
	public boolean canSmelt() {
		if(inventory.getStackInSlot(0).isEmpty()) return false;
		Pair<ItemStack, FluidStack> pair = CombinationRecipes.getOutput(inventory.getStackInSlot(0));
		
		if(pair == null) return false;
		
		ItemStack out = pair.getKey();
		FluidStack fluid = pair.getValue();
		
		if(out != null && !out.isEmpty()) {
			ItemStack outSlotStack = inventory.getStackInSlot(1);
			if(!outSlotStack.isEmpty()) {
				if(!out.isItemEqual(outSlotStack)) return false;
				if(out.getCount() + outSlotStack.getCount() > outSlotStack.getMaxStackSize()) return false;
			}
		}
		
		if(fluid != null && tank.getFluid() != null) {
			if(tank.getFluid().getFluid() != fluid.getFluid()) {
                return tank.getFluidAmount() <= 0;
			} else return tank.getFluidAmount() + fluid.amount <= tank.getCapacity();
		}
		
		return true;
	}
	
	protected void tryPullHeat() {
		
		if(this.heat >= maxHeat) return;
		
		TileEntity con = world.getTileEntity(pos.down());
		
		if(con instanceof IHeatSource source) {
            int diff = source.getHeatStored() - this.heat;
			
			if(diff == 0) {
				return;
			}
			
			if(diff > 0) {
				diff = (int) Math.ceil(diff * diffusion);
				source.useUpHeat(diff);
				this.heat += diff;
				if(this.heat > maxHeat)
					this.heat = maxHeat;
				return;
			}
		}
		
		this.heat = Math.max(this.heat - Math.max(this.heat / 1000, 1), 0);
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
			return null;
		}
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if(tags.length != 1) {
			return;
		}
		tank.readFromNBT(tags[0]);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing side) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemStack) {
		return i == 0 && CombinationRecipes.getOutput(itemStack) != null;
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemStack, int j) {
		return i == 1;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { tank.getTankProperties()[0] };
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.tank.readFromNBT(nbt.getCompoundTag("tank"));
		this.progress = nbt.getInteger("prog");
		this.heat = nbt.getInteger("heat");
		super.readFromNBT(nbt);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		nbt.setInteger("prog", progress);
		nbt.setInteger("heat", heat);
		return super.writeToNBT(nbt);
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

	@Override
	public Container provideContainer(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new ContainerFurnaceCombo(player.inventory, this);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiScreen provideGUI(int ID, EntityPlayer player, World world, int x, int y, int z) {
		return new GUIFurnaceCombo(player.inventory, this);
	}
	
	AxisAlignedBB bb = null;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		
		if(bb == null) {
			bb = new AxisAlignedBB(
					pos.getX() - 1,
					pos.getY(),
					pos.getZ() - 1,
					pos.getX() + 2,
					pos.getY() + 2.125,
					pos.getZ() + 2
					);
		}
		
		return bb;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared() {
		return 65536.0D;
	}

	@Override
	public void networkUnpack(NBTTagCompound data) {
		this.wasOn = data.getBoolean("wasOn");
		this.heat = data.getInteger("heat");
		this.progress = data.getInteger("progress");
	}
}
