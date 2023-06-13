package com.hbm.tileentity.turret;

import java.util.List;

import com.hbm.forgefluid.FFUtils;
import com.hbm.forgefluid.ModForgeFluids;
import com.hbm.handler.BulletConfigSyncingUtil;
import com.hbm.handler.BulletConfiguration;
import com.hbm.interfaces.ITankPacketAcceptor;
import com.hbm.inventory.FluidCombustionRecipes;
import com.hbm.items.ModItems;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.AuxParticlePacketNT;
import com.hbm.packet.FluidTankPacket;
import com.hbm.packet.PacketDispatcher;
import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class TileEntityTurretFritz extends TileEntityTurretBaseNT implements IFluidHandler, ITankPacketAcceptor {

	public FluidTank tank;

	public static int drain = 2;
	
	public TileEntityTurretFritz() {
		super();
		this.tank = new FluidTank(16000);
	}
	
	@Override
	public String getName() {
		return "container.turretFritz";
	}

	@Override
	protected List<Integer> getAmmoList() {
		return null;
	}
	
	@Override
	public double getDecetorRange() {
		return 32D;
	}
	
	@Override
	public double getDecetorGrace() {
		return 2D;
	}

	@Override
	public double getTurretElevation() {
		return 45D;
	}

	@Override
	public long getMaxPower() {
		return 10000;
	}

	@Override
	public double getBarrelLength() {
		return 2.25D;
	}

	@Override
	public double getAcceptableInaccuracy() {
		return 15;
	}
	
	@Override
	public void updateFiringTick() {
		
		if(this.tank.getFluid() != null && FluidCombustionRecipes.hasFuelRecipe(tank.getFluid().getFluid()) && this.tank.getFluidAmount() >= drain) {
			
			BulletConfiguration conf = BulletConfigSyncingUtil.pullConfig(BulletConfigSyncingUtil.FLA_NORMAL);
			this.spawnBullet(conf, FluidCombustionRecipes.getFlameEnergy(tank.getFluid().getFluid()) * 0.002F);
			this.tank.drain(drain, true);
			
			Vec3 pos = new Vec3(this.getTurretPos());
			Vec3 vec = Vec3.createVectorHelper(this.getBarrelLength(), 0, 0);
			vec.rotateAroundZ((float) -this.rotationPitch);
			vec.rotateAroundY((float) -(this.rotationYaw + Math.PI * 0.5));
			
			world.playSound(null, this.pos.getX(), this.pos.getY(), this.pos.getZ(), HBMSoundHandler.flamethrowerShoot, SoundCategory.BLOCKS, 2F, 1F + world.rand.nextFloat() * 0.5F);
			
			NBTTagCompound data = new NBTTagCompound();
			data.setString("type", "vanillaburst");
			data.setString("mode", "flame");
			data.setInteger("count", 2);
			data.setDouble("motion", 0.025D);
			PacketDispatcher.wrapper.sendToAllAround(new AuxParticlePacketNT(data, pos.xCoord + vec.xCoord, pos.yCoord + vec.yCoord, pos.zCoord + vec.zCoord), new TargetPoint(world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 50));
		}
	}
	
	public int getDelay() {
		return 2;
	}
	
	@Override
	public void update(){
		super.update();
		if(!world.isRemote) {
			FFUtils.fillFromFluidContainer(inventory, tank, 5, 9);
			PacketDispatcher.wrapper.sendToAllAround(new FluidTankPacket(pos, tank), new TargetPoint(world.provider.getDimension(), this.pos.getX(), this.pos.getY(), this.pos.getZ(), 10));
			for(int i = 1; i < 10; i++) {
				if(inventory.getStackInSlot(i).getItem() == ModItems.ammo_fuel) {
					if((this.tank.getFluid() == null || tank.getFluid().getFluid() == ModForgeFluids.diesel) && this.tank.getFluidAmount() + 1000 <= this.tank.getCapacity()) {
						this.tank.fill(new FluidStack(ModForgeFluids.diesel, 1000), true);
						inventory.getStackInSlot(i).shrink(1);
						if(inventory.getStackInSlot(i).isEmpty())
							inventory.setStackInSlot(i, ItemStack.EMPTY);
					}
				}
			}
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt){
		nbt.setTag("tank", tank.writeToNBT(new NBTTagCompound()));
		return super.writeToNBT(nbt);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt){
		tank.readFromNBT(nbt.getCompoundTag("tank"));
		super.readFromNBT(nbt);
	}
	
	@Override
	public int[] getAccessibleSlotsFromSide(EnumFacing e){
		return new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	}

	@Override
	public IFluidTankProperties[] getTankProperties(){
		return tank.getTankProperties();
	}

	@Override
	public int fill(FluidStack resource, boolean doFill){
		if(resource == null || !FluidCombustionRecipes.hasFuelRecipe(resource.getFluid()))
			return 0;
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain){
		return tank.drain(resource, doDrain);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain){
		return tank.drain(maxDrain, doDrain);
	}
	
	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing){
		return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing){
		if(capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)
			return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
		return super.getCapability(capability, facing);
	}

	@Override
	public void recievePacket(NBTTagCompound[] tags){
		if(tags.length == 1){
			tank.readFromNBT(tags[0]);
		}
	}
}
