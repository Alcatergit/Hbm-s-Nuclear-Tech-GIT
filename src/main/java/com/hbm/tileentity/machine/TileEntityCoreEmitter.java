package com.hbm.tileentity.machine;

import api.hbm.energy.IEnergyUser;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.interfaces.ILaserable;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.control_panel.*;
import com.hbm.lib.ForgeDirection;
import com.hbm.lib.Library;
import com.hbm.lib.ModDamageSource;
import com.leafia.contents.machines.powercores.dfc.DFCBaseTE;
import com.leafia.dev.LeafiaDebug;
import com.leafia.dev.LeafiaUtil;
import com.leafia.dev.container_utility.LeafiaPacket;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.*;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.*;

@Optional.InterfaceList({@Optional.Interface(iface = "li.cil.oc.api.network.SimpleComponent", modid = "OpenComputers")})
public class TileEntityCoreEmitter extends DFCBaseTE implements ITickable, IEnergyUser, IFluidHandler, ILaserable, ITankPacketAcceptor, SimpleComponent, IControllable {

	public long power;
	public static final long maxPower = 1000000000L;
	public int watts = 1;
	public int beam;
	public long joules;
	public boolean isOn;
	public FluidTank tank;
	public long prev;
	public int prevWatts = -1;

	public static final int range = 50;

	public TileEntityCoreEmitter() {
		super(0);
		tank = new FluidTank(64000);
	}

	public RayTraceResult raycast(long out) {
		return Library.leafiaRayTraceBlocksCustom(world, new Vec3d(pos).add(0.5, 0.5, 0.5), new Vec3d(pos).add(0.5, 0.5, 0.5).add(getDirection().scale(range)), (process, config, current) -> {
			if (!world.isRemote) {
				Vec3d centerVec = current.posIntended.add(new Vec3d(config.pivotAxisFace.getDirectionVec()).scale(0.5)
						.add(config.secondaryVector.scale(0.5)));
				List<Entity> list = world.getEntitiesWithinAABB(Entity.class, LeafiaUtil.createAABB(
						centerVec.subtract(0.5, 0.5, 0.5), centerVec.add(0.5, 0.5, 0.5)
				));
				for (Entity e : list) {
					e.attackEntityFrom(ModDamageSource.amsCore, joules * 0.000001F);
					e.setFire(10);
				}
			}
			if (current.posSnapped.equals(pos)) return process.CONTINUE();

			RayTraceResult miss = new RayTraceResult(Type.MISS, current.posIntended, config.pivotAxisFace, current.posSnapped);
			if (!current.block.canCollideCheck(current.state, true))
				return process.CONTINUE(miss);

			RayTraceResult result = current.state.collisionRayTrace(world, current.posSnapped, current.posIntended.subtract(config.unitDir.scale(2)), current.posIntended.add(config.unitDir.scale(2)));
			if (result == null)
				return process.CONTINUE(miss);

			Vec3d vec = result.hitVec;
			TileEntity te = world.getTileEntity(current.posSnapped);
			if (te instanceof ILaserable) {
				if (!world.isRemote) ((ILaserable) te).addEnergy(out * 100 * watts / 10000, config.pivotAxisFace);
				return process.RETURN(result);
			}

			if (te instanceof TileEntityCore) {
				//out = Math.max(0, ((TileEntityCore)te).burn(out));
				if (!world.isRemote) ((TileEntityCore) te).incomingSpk += out / 1000_000d;
				//continue;
				//break;
				result.hitVec = new Vec3d(te.getPos()); // align to the center
				return process.RETURN(result);
			}

			IBlockState state = current.state;

			if (current.block != Blocks.AIR) { //(!state.getRenderType().equals(EnumBlockRenderType.INVISIBLE)) {
				if (!world.isRemote) {
					if (state.getMaterial().isLiquid()) {
						world.playSound(null, vec.x, vec.y, vec.z, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
						world.setBlockToAir(result.getBlockPos());
						return process.RETURN(result);
					}
					@SuppressWarnings("deprecation")
					float hardness = state.getBlock().getExplosionResistance(null);
					if (hardness < 10000 && world.rand.nextDouble() / 20 < (out * 0.00000001F) / hardness) {
						world.playSound(null, vec.x, vec.y, vec.z, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 1.0F, 1.0F);
						world.getBlockState(result.getBlockPos()).getBlock().onBlockExploded(world, result.getBlockPos(), new Explosion(world, null, result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), 5, false, false));
						//world.destroyBlock(pos1, false);
					}
				}
				return process.RETURN(result);
			} else
				return process.CONTINUE(result);
		});
	}
	public boolean isActive = false;

	@Override
	public void update() {
		if (!world.isRemote) {
			LeafiaPacket._start(this).__write(31,targetPosition).__sendToAffectedClients();

			this.updateStandardConnections(world, pos);
			this.updateSPKConnections(world, pos);

			watts = MathHelper.clamp(watts, 1, 100);
			long demand = maxPower * Math.min(watts, 100) / 2000;
			isActive = false;

			beam = 0;

			if (joules > 0 || prev > 0) {

				if (tank.getFluidAmount() >= 20) {
					tank.drain(20, true);
				} else {
					world.setBlockState(pos, Blocks.FLOWING_LAVA.getDefaultState());
					return;
				}
			}

			if (isOn) {
				//i.e. 50,000,000 HE = 10,000 SPK
				//1 SPK = 5,000HE

				if (power >= demand) {
					power -= demand;
					long add = watts * 100;
					if (add > Long.MAX_VALUE - joules)
						joules = Long.MAX_VALUE;
					else
						joules += add;
				}
				prev = joules;

				if (joules > 0) {

					long out = joules;

					/*
					EnumFacing dir = EnumFacing.getFront(this.getBlockMetadata());
					for(int i = 1; i <= range; i++) {

						beam = i;
		
						int x = pos.getX() + dir.getFrontOffsetX() * i;
						int y = pos.getY() + dir.getFrontOffsetY() * i;
						int z = pos.getZ() + dir.getFrontOffsetZ() * i;
						
						BlockPos pos1 = new BlockPos(x, y, z);
						

					}*/
					isActive = true;
					raycast(out);

					joules = 0;
				}
			} else {
				joules = 0;
				prev = 0;
			}

			this.markDirty();

			LeafiaPacket packet = LeafiaPacket._start(this)
					.__write(0, isOn)
					.__write(1, watts)
					.__write(2, prev)
					.__write(3, isActive);
			//if (watts != prevWatts)
			//	packet.__write(1,watts);
			packet.__sendToAffectedClients();
			/*
			//PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, beam, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
			if(watts != prevWatts) PacketDispatcher.wrapper.sendToAllTracking(new AuxGaugePacket(pos, watts, 1), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
			PacketDispatcher.wrapper.sendToAllTracking(new AuxLongPacket(pos, prev, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 250));
			prevWatts = watts;
			*/
			//this.networkPack(data, 250);
		} else if (isOn) {
			lastRaycast = raycast(0);
		}
	}

	public RayTraceResult lastRaycast = null;

	@Override
	public void onReceivePacketLocal(byte key, Object value) {
		super.onReceivePacketLocal(key, value);
		switch (key) {
			case 0:
				isOn = (boolean) value;
				break;
			case 1:
				watts = (int) value;
				break;
			case 2:
				prev = (long) value;
				break;
			case 3:
				isActive = (boolean) value;
				break;
		}
	}

	@Override
	public boolean canConnect(ForgeDirection dir) {
		return dir != ForgeDirection.UNKNOWN;
	}

	@Override
	public String getName() {
		return "container.dfcEmitter";
	}

	public long getPowerScaled(long i) {
		return (power * i) / maxPower;
	}

	public int getWattsScaled(int i) {
		return (watts * i) / 100;
	}

	@Override
	public void setPower(long i) {
		this.power = i;
	}

	@Override
	public long getPower() {
		return this.power;
	}

	@Override
	public long getMaxPower() {
		return maxPower;
	}

	@Override
	public void addEnergy(long energy, EnumFacing dir) {
		//do not accept lasers from the front
		if (dir.getOpposite().ordinal() != this.getBlockMetadata()) {
			if (Long.MAX_VALUE - joules < energy)
				joules = Long.MAX_VALUE;
			else
				joules += energy;
		}
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource != null && resource.getFluid() == ModForgeFluids.CRYOGEL)
			return tank.fill(resource, doFill);
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
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
		power = compound.getLong("power");
		watts = compound.getInteger("watts");
		joules = compound.getLong("joules");
		prev = compound.getLong("prev");
		isOn = compound.getBoolean("isOn");
		tank.readFromNBT(compound.getCompoundTag("tank"));
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setLong("power", power);
		compound.setInteger("watts", watts);
		compound.setLong("joules", joules);
		compound.setLong("prev", prev);
		compound.setBoolean("isOn", isOn);
		compound.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(compound);
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags) {
		if (tags.length == 1)
			tank.readFromNBT(tags[0]);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
		if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public String getComponentName() {
		return "dfc_emitter";
	}

	@Callback(doc = "setLevel(newLevel: number)->(previousLevel: number)")
	public Object[] setLevel(Context context, Arguments args) {
		Object[] prev = new Object[]{watts};
		watts = MathHelper.clamp(args.checkInteger(0), 1, 100);
		return prev;
	}

	@Callback(doc = "getLevel()->(level: number)")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[]{watts};
	}

	@Callback
	public Object[] getEmitted(Context context, Arguments args) {
		return new Object[]{prev};
	}

	@Callback(doc = "getActive()->(active: boolean)")
	public Object[] getActive(Context context, Arguments args) {
		return new Object[]{isOn};
	}

	@Callback(doc = "setActive(active: boolean)->(previously: boolean)")
	public Object[] setActive(Context context, Arguments args) {
		boolean wasOn = isOn;
		isOn = args.checkBoolean(0);
		return new Object[]{wasOn};
	}

	@Callback(doc = "getPower(); returns the current power level - long")
	public Object[] getPower(Context context, Arguments args) {
		return new Object[]{power};
	}

	@Callback(doc = "getMaxPower(); returns the maximum power level - long")
	public Object[] getMaxPower(Context context, Arguments args) {
		return new Object[]{getMaxPower()};
	}

	@Callback(doc = "getChargePercent(); returns the charge in percent - double")
	public Object[] getChargePercent(Context context, Arguments args) {
		return new Object[]{100D * getPower() / (double) getMaxPower()};
	}

	@Callback
	public Object[] storedCoolnt(Context context, Arguments args) {
		return new Object[]{tank.getFluidAmount()};
	}

	@Override
	public String getPacketIdentifier() {
		return "DFC_BOOSTER";
	}

	@Override
	public BlockPos getControlPos() {
		return getPos();
	}

	@Override
	public World getControlWorld() {
		return getWorld();
	}

	@Override
	public void receiveEvent(BlockPos from,ControlEvent e) {
		if (e.name.equals("set_booster_active")) {
			isOn = e.vars.get("active").getNumber() >= 1f;
		} else if (e.name.equals("set_booster_level")) {
			watts = Math.round(e.vars.get("level").getNumber());
		}
	}
	@Override
	public Map<String,DataValue> getQueryData() {
		Map<String,DataValue> map = new HashMap<>();
		map.put("active",new DataValueFloat(isOn ? 1 : 0));
		map.put("level",new DataValueFloat(watts));
		map.put("emitted",new DataValueFloat(prev));
		return map;
	}

	@Override
	public List<String> getInEvents() {
		return Arrays.asList("set_booster_level","set_booster_active");
	}

	@Override
	public void validate(){
		super.validate();
		ControlEventSystem.get(world).addControllable(this);
	}

	@Override
	public void invalidate(){
		super.invalidate();
		ControlEventSystem.get(world).removeControllable(this);
	}
}
