package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.BlockSiloHatch;
import com.hbm.blocks.machine.DummyBlockSiloHatch;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.interfaces.IAnimatedDoor;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEDoorAnimationPacket;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntitySiloHatch extends TileEntityLockableBase implements ITickable, IAnimatedDoor {

	//0: closed, 1: open, 2: closing, 3: opening
	public byte state = 0;
	public long sysTime;
	public int timer = -1;
	public boolean redstoned = false;
	public EnumFacing facing = null;
	public AxisAlignedBB renderBox = null;

	@Override
	public void update() {
		if(!world.isRemote){
			if(!this.isLocked()){
				boolean rs = world.isBlockIndirectlyGettingPowered(pos) > 0;
				if(rs && !redstoned)
					tryToggle();
				redstoned = rs;
			}
			int oldState = state;
			if(timer < 0)
				oldState = -1;
			if(state < 2) {
				timer = 0;
			} else {
				if(facing == null)
					facing = world.getBlockState(pos).getValue(BlockSiloHatch.FACING).getOpposite();
				timer ++;
				if(state == 2){
					if(timer == 1){
						BlockPos hydrolics = pos.offset(facing, 5);
						this.world.playSound(null, hydrolics.getX(), hydrolics.getY(), hydrolics.getZ(), HBMSoundHandler.siloclose, SoundCategory.BLOCKS, 3F, 1F);
					}
					if(timer == 50){
						BlockPos mid = pos.offset(facing, 3);
						for(int i = -1; i <= 1; i ++){
							for(int j = -1; j <= 1; j ++){
								placeDummy(mid.add(i, 0, j));
							}
						}
					}
					if(timer > 100){
						state = 0;
					}
				} else if(state == 3){
					if(timer == 1){
						BlockPos hydrolics = pos.offset(facing, 5);
						this.world.playSound(null, hydrolics.getX(), hydrolics.getY(), hydrolics.getZ(), HBMSoundHandler.siloopen, SoundCategory.BLOCKS, 4F, 1F);
					}
					if(timer == 70){
						BlockPos mid = pos.offset(facing, 3);
						for(int i = -1; i <= 1; i ++){
							for(int j = -1; j <= 1; j ++){
								removeDummy(mid.add(i, 0, j));
							}
						}
					}
					if(timer > 100){
						state = 1;
					}
				}
			}
			if(oldState != state)
				PacketDispatcher.wrapper.sendToAllTracking(new TEDoorAnimationPacket(pos, state), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 200));
		}
	}

	public void tryToggle() {
		if(this.state == 0) {
			if(!world.isRemote) {
				this.state = 3;
				timer = -1;
			}
		} else if(this.state == 1) {
			if(!world.isRemote) {
				this.state = 2;
				timer = -1;
			}
		}
	}

	public boolean placeDummy(BlockPos pos) {

		if(!world.getBlockState(pos).getBlock().isReplaceable(world, pos))
			return false;

		world.setBlockState(pos, ModBlocks.dummy_block_silo_hatch.getDefaultState());

		TileEntity te = world.getTileEntity(pos);

		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.target = this.pos;
		}

		return true;
	}

	public void removeDummy(BlockPos pos) {
		if(world.getBlockState(pos).getBlock() == ModBlocks.dummy_block_silo_hatch) {
			DummyBlockSiloHatch.safeBreak = true;
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			DummyBlockSiloHatch.safeBreak = false;
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		state = compound.getByte("state");
		redstoned = compound.getBoolean("redstoned");
		super.readFromNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setByte("state", state);
		compound.setBoolean("redstoned", redstoned);
		return super.writeToNBT(compound);
	}

	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		if(facing == null)
			facing = world.getBlockState(pos).getValue(BlockSiloHatch.FACING).getOpposite();
		if(renderBox == null)
			renderBox = new AxisAlignedBB(-3.3, 0, -3.3, 4.3, 2, 4.3).offset(pos.offset(facing, 3));
		return renderBox;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void open() {
		if(state == 0)
			toggle();
	}

	@Override
	public void close() {
		if(state == 1)
			toggle();
	}

	@Override
	public DoorState getState() {
		return DoorState.values()[state];
	}

	@Override
	public void toggle() {
		tryToggle();
	}

	@Override
	public void handleNewState(byte state) {
		if(this.state != state){
			if(this.state < 2 && state >= 2){
				sysTime = System.currentTimeMillis();
			}
			this.state = state;
		}
	}

}