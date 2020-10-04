package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.DummyBlockBlast;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEVaultPacket;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityBlastDoor extends TileEntityLockableBase implements ITickable {

	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public boolean redstoned = false;
	
	@Override
	public AxisAlignedBB getRenderBoundingBox() {
		return TileEntity.INFINITE_EXTENT_AABB;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public double getMaxRenderDistanceSquared()
	{
		return 65536.0D;
	}

	@Override
	public void update() {
		if(!world.isRemote) {
			
			if(!isLocked() && world.isBlockIndirectlyGettingPowered(pos) > 0 || world.isBlockIndirectlyGettingPowered(pos.up(6)) > 0) {
				
				if(!redstoned) {
					this.tryToggle();
				}
				redstoned = true;
				
			} else {
				redstoned = false;
			}
	    			
	    	if(state != 1) {
	    		timer = 0;
	    	} else {
	    		timer++;
    			
    			if(isOpening) {
    				if(timer >= 0) {
    					removeDummy(pos.up(1));
    				}
    				if(timer >= 20) {
    					removeDummy(pos.up(2));
    				}
    				if(timer >= 40) {
    					removeDummy(pos.up(3));
    				}
    				if(timer >= 60) {
    					removeDummy(pos.up(4));
    				}
    				if(timer >= 80) {
    					removeDummy(pos.up(5));
    				}
    			} else {
    				if(timer >= 20) {
    					placeDummy(pos.up(5));
    				}
    				if(timer >= 40) {
    					placeDummy(pos.up(4));
    				}
    				if(timer >= 60) {
    					placeDummy(pos.up(3));
    				}
    				if(timer >= 80) {
    					placeDummy(pos.up(2));
    				}
    				if(timer >= 100) {
    					placeDummy(pos.up(1));
    				}
    			}
	    		
	    		if(timer >= 100) {
	    			
	    			if(isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	
	    	PacketDispatcher.wrapper.sendToAllTracking(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 0, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 150));
		}
	}
	
	public void open() {
		if(state == 0) {
	    	PacketDispatcher.wrapper.sendToAllTracking(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 1, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 150));
			isOpening = true;
			state = 1;

			this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStart, SoundCategory.BLOCKS, 0.5F, 0.75F);
		}
	}
	
	public void finishOpen() {
		state = 2;

		this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStop, SoundCategory.BLOCKS, 0.5F,
				1.0F);
	}
	
	public void close() {
		if(state == 2) {
	    	PacketDispatcher.wrapper.sendToAllTracking(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 1, 0), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 150));
			isOpening = false;
			state = 1;

			this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStart, SoundCategory.BLOCKS, 0.5F, 0.75F);
		}
	}
	
	public void finishClose() {
		state = 0;

		this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.reactorStop, SoundCategory.BLOCKS, 0.5F, 1.0F);
	}
	
	public void openNeigh() {

		TileEntity te0 = world.getTileEntity(pos.add(1, 0, 0));
		TileEntity te1 = world.getTileEntity(pos.add(-1, 0, 0));
		TileEntity te2 = world.getTileEntity(pos.add(0, 0, 1));
		TileEntity te3 = world.getTileEntity(pos.add(0, 0, -1));
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canOpen() && (!((TileEntityBlastDoor)te0).isLocked() || ((TileEntityBlastDoor)te0).lock == lock)) {
				((TileEntityBlastDoor)te0).open();
				((TileEntityBlastDoor)te0).openNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canOpen() && (!((TileEntityBlastDoor)te1).isLocked() || ((TileEntityBlastDoor)te1).lock == lock)) {
				((TileEntityBlastDoor)te1).open();
				((TileEntityBlastDoor)te1).openNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canOpen() && (!((TileEntityBlastDoor)te2).isLocked() || ((TileEntityBlastDoor)te2).lock == lock)) {
				((TileEntityBlastDoor)te2).open();
				((TileEntityBlastDoor)te2).openNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canOpen() && (!((TileEntityBlastDoor)te3).isLocked() || ((TileEntityBlastDoor)te3).lock == lock)) {
				((TileEntityBlastDoor)te3).open();
				((TileEntityBlastDoor)te3).openNeigh();
			}
		}
	}
	
	@Override
	public void lock() {
		super.lock();
		lockNeigh();
	}
	
	public void closeNeigh() {

		TileEntity te0 = world.getTileEntity(pos.add(1, 0, 0));
		TileEntity te1 = world.getTileEntity(pos.add(-1, 0, 0));
		TileEntity te2 = world.getTileEntity(pos.add(0, 0, 1));
		TileEntity te3 = world.getTileEntity(pos.add(0, 0, -1));
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te0).canClose() && (!((TileEntityBlastDoor)te0).isLocked() || ((TileEntityBlastDoor)te0).lock == lock)) {
				((TileEntityBlastDoor)te0).close();
				((TileEntityBlastDoor)te0).closeNeigh();
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te1).canClose() && (!((TileEntityBlastDoor)te1).isLocked() || ((TileEntityBlastDoor)te1).lock == lock)) {
				((TileEntityBlastDoor)te1).close();
				((TileEntityBlastDoor)te1).closeNeigh();
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te2).canClose() && (!((TileEntityBlastDoor)te2).isLocked() || ((TileEntityBlastDoor)te2).lock == lock)) {
				((TileEntityBlastDoor)te2).close();
				((TileEntityBlastDoor)te2).closeNeigh();
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {
			
			if(((TileEntityBlastDoor)te3).canClose() && (!((TileEntityBlastDoor)te3).isLocked() || ((TileEntityBlastDoor)te3).lock == lock)) {
				((TileEntityBlastDoor)te3).close();
				((TileEntityBlastDoor)te3).closeNeigh();
			}
		}
	}
	
	public void lockNeigh() {

		TileEntity te0 = world.getTileEntity(pos.add(1, 0, 0));
		TileEntity te1 = world.getTileEntity(pos.add(-1, 0, 0));
		TileEntity te2 = world.getTileEntity(pos.add(0, 0, 1));
		TileEntity te3 = world.getTileEntity(pos.add(0, 0, -1));
		
		if(te0 instanceof TileEntityBlastDoor) {
			
			if(!((TileEntityBlastDoor)te0).isLocked()) {
				((TileEntityBlastDoor)te0).setPins(this.lock);
				((TileEntityBlastDoor)te0).lock();
				((TileEntityBlastDoor)te0).setMod(lockMod);
			}
		}
		
		if(te1 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te1).isLocked()) {
				((TileEntityBlastDoor)te1).setPins(this.lock);
				((TileEntityBlastDoor)te1).lock();
				((TileEntityBlastDoor)te1).setMod(lockMod);
			}
		}
		
		if(te2 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te2).isLocked()) {
				((TileEntityBlastDoor)te2).setPins(this.lock);
				((TileEntityBlastDoor)te2).lock();
				((TileEntityBlastDoor)te2).setMod(lockMod);
			}
		}
		
		if(te3 instanceof TileEntityBlastDoor) {

			if(!((TileEntityBlastDoor)te3).isLocked()) {
				((TileEntityBlastDoor)te3).setPins(this.lock);
				((TileEntityBlastDoor)te3).lock();
				((TileEntityBlastDoor)te3).setMod(lockMod);
			}
		}
	}
	
	public boolean canOpen() {
		return state == 0;
	}
	
	public boolean canClose() {
		return state == 2;
	}
	
	public void tryToggle() {

		if(canOpen()) {
			open();
			openNeigh();
		} else if(canClose()) {
			close();
			closeNeigh();
		}
	}
	
	public boolean placeDummy(BlockPos pos) {
		
		if(!world.getBlockState(pos).getBlock().isReplaceable(world, pos))
			return false;
		
		world.setBlockState(pos, ModBlocks.dummy_block_blast.getDefaultState());
		
		TileEntity te = world.getTileEntity(pos);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.target = this.pos;
		}
		
		return true;
	}
	
	public void removeDummy(BlockPos pos) {
		
		if(world.getBlockState(pos).getBlock() == ModBlocks.dummy_block_blast) {
			DummyBlockBlast.safeBreak = true;
			world.setBlockState(pos, Blocks.AIR.getDefaultState());
			DummyBlockBlast.safeBreak = false;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isOpening = compound.getBoolean("isOpening");
		state = compound.getInteger("state");
		sysTime = compound.getLong("sysTime");
		timer = compound.getInteger("timer");
		redstoned = compound.getBoolean("redstoned");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isOpening", isOpening);
		compound.setInteger("state", state);
		compound.setLong("sysTime", sysTime);
		compound.setInteger("timer", timer);
		compound.setBoolean("redstoned", redstoned);
		return super.writeToNBT(compound);
	}
	
}
