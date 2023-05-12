package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.blocks.machine.DummyBlockVault;
import com.hbm.blocks.machine.VaultDoor;
import com.hbm.lib.HBMSoundHandler;
import com.hbm.packet.PacketDispatcher;
import com.hbm.packet.TEVaultPacket;

import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.ITickable;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityVaultDoor extends TileEntityLockableBase implements ITickable {

	public boolean isOpening = false;
	//0: closed, 1: opening/closing, 2:open
	public int state = 0;
	public long sysTime;
	private int timer = 0;
	public int type;
	public static final int maxTypes = 32;
	
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
			
			if(!isLocked()) {
				boolean flagX = false;
				boolean flagZ = false;
				int xCoord = pos.getX();
				int yCoord = pos.getY();
				int zCoord = pos.getZ();

				for(int x = pos.getX() - 2; x <= xCoord + 2; x++)
					for(int y = yCoord; y <= yCoord + 5; y++)
						if(world.isBlockIndirectlyGettingPowered(new BlockPos(x, y, zCoord)) > 0) {
							flagX = true;
							break;
						}
				
				for(int z = zCoord - 2; z <= zCoord + 2; z++)
					for(int y = yCoord; y <= yCoord + 5; y++)
						if(world.isBlockIndirectlyGettingPowered(new BlockPos(xCoord, y, z)) > 0) {
							flagZ = true;
							break;
						}

				if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.Z) {
					if(flagX)
						this.tryOpen();
					else
						this.tryClose();
				
				} else if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.X) {
					if(flagZ)
						this.tryOpen();
					else
						this.tryClose();
				}
			}

	    	if(isOpening && state == 1) {
				
	    		if(timer == 0)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultScrapeNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 45)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 55)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 65)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 75)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 85)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 95)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 105)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 115)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    	}
	    	if(!isOpening && state == 1) {

	    		if(timer == 0)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 10)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 20)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 30)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 40)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 50)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 60)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		if(timer == 70)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultThudNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    		
	    		if(timer == 80)
					this.world.playSound(null, pos.getX(), pos.getY(), pos.getZ(), HBMSoundHandler.vaultScrapeNew, SoundCategory.BLOCKS, 1.0F, 1.0F);
	    	}	
	    	
	    	if(state != 1) {
	    		timer = 0;
	    	} else {
	    		timer++;
	    		
	    		if(timer >= 120) {
	    			
	    			if(isOpening)
	    				finishOpen();
	    			else
	    				finishClose();
	    		}
	    	}
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 0, type), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 300));
		}
	}
	
	public void open() {
		if(state == 0) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 1, type), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 300));
			isOpening = true;
			state = 1;
			timer = 0;
			openHatch();
		}
	}
	
	public void finishOpen() {
		state = 2;
	}
	
	public void close() {
		if(state == 2) {
	    	PacketDispatcher.wrapper.sendToAllAround(new TEVaultPacket(pos.getX(), pos.getY(), pos.getZ(), isOpening, state, 1, type), new TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 300));
			isOpening = false;
			state = 1;
			timer = 0;
			closeHatch();
		}
	}
	
	public void finishClose() {
		state = 0;
	}
	
	public boolean canOpen() {
		return state == 0;
	}
	
	public boolean canClose() {
		return state == 2 && isHatchFree();
	}

	public void tryOpen() {

		if(canOpen())
			open();
	}

	public void tryToggle() {

		if(canOpen())
			open();
		else if(canClose())
			close();
	}
	
	public void tryClose() {

		if(canClose())
			close();
	}
	
	public boolean placeDummy(int x, int y, int z){
		return placeDummy(new BlockPos(x, y, z));
	}
	
	public boolean placeDummy(BlockPos dummyPos) {
		if(!world.getBlockState(dummyPos).getBlock().isReplaceable(world, dummyPos))
			return false;
		
		world.setBlockState(dummyPos, ModBlocks.dummy_block_vault.getDefaultState());
		
		TileEntity te = world.getTileEntity(dummyPos);
		
		if(te instanceof TileEntityDummy) {
			TileEntityDummy dummy = (TileEntityDummy)te;
			dummy.target = pos;
		}
		
		return true;
	}
	
	public void removeDummy(BlockPos dummyPos) {
		if(world.getBlockState(dummyPos).getBlock() == ModBlocks.dummy_block_vault) {
			DummyBlockVault.safeBreak = true;
			world.setBlockState(dummyPos, Blocks.AIR.getDefaultState());
			DummyBlockVault.safeBreak = false;
		}
	}
	
	private boolean isHatchFree() {

		if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.Z)
			return checkNS();
		else if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.X)
			return checkEW();
		else
			return true;
	}
	
	private void closeHatch() {

		if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.Z)
			fillNS();
		else if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.X)
			fillEW();
	}
	
	private void openHatch() {
		if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.Z)
			removeNS();
		else if(world.getBlockState(pos).getValue(VaultDoor.FACING).getAxis() == Axis.X)
			removeEW();
	}
	
	private boolean checkNS() {
		return world.getBlockState(pos.add(-1, 1, 0)).getBlock().isReplaceable(world, pos.add(-1, 1, 0)) &&
				world.getBlockState(pos.add(0, 1, 0)).getBlock().isReplaceable(world, pos.add(0, 1, 0)) &&
				world.getBlockState(pos.add(1, 1, 0)).getBlock().isReplaceable(world, pos.add(1, 1, 0)) &&
				world.getBlockState(pos.add(-1, 2, 0)).getBlock().isReplaceable(world, pos.add(-1, 2, 0)) &&
				world.getBlockState(pos.add(0, 2, 0)).getBlock().isReplaceable(world, pos.add(0, 2, 0)) &&
				world.getBlockState(pos.add(1, 2, 0)).getBlock().isReplaceable(world, pos.add(1, 2, 0)) &&
				world.getBlockState(pos.add(-1, 3, 0)).getBlock().isReplaceable(world, pos.add(-1, 3, 0)) &&
				world.getBlockState(pos.add(0, 3, 0)).getBlock().isReplaceable(world, pos.add(0, 3, 0)) &&
				world.getBlockState(pos.add(1, 3, 0)).getBlock().isReplaceable(world, pos.add(1, 3, 0));
	}
	
	private boolean checkEW() {
		return world.getBlockState(pos.add(0, 1, -1)).getBlock().isReplaceable(world, pos.add(0, 1, -1)) &&
				world.getBlockState(pos.add(0, 1, 0)).getBlock().isReplaceable(world, pos.add(0, 1, 0)) &&
				world.getBlockState(pos.add(0, 1, 1)).getBlock().isReplaceable(world, pos.add(0, 1, 1)) &&
				world.getBlockState(pos.add(0, 2, -1)).getBlock().isReplaceable(world, pos.add(0, 2, -1)) &&
				world.getBlockState(pos.add(0, 2, 0)).getBlock().isReplaceable(world, pos.add(0, 2, 0)) &&
				world.getBlockState(pos.add(0, 2, 1)).getBlock().isReplaceable(world, pos.add(0, 2, 1)) &&
				world.getBlockState(pos.add(0, 3, -1)).getBlock().isReplaceable(world, pos.add(0, 3, -1)) &&
				world.getBlockState(pos.add(0, 3, 0)).getBlock().isReplaceable(world, pos.add(0, 3, 0)) &&
				world.getBlockState(pos.add(0, 3, 1)).getBlock().isReplaceable(world, pos.add(0, 3, 1));
	}
	
	private void fillNS() {

		placeDummy(pos.add(-1, 1, 0));
		placeDummy(pos.add(-1, 2, 0));
		placeDummy(pos.add(-1, 3, 0));
		placeDummy(pos.add(0, 1, 0));
		placeDummy(pos.add(0, 2, 0));
		placeDummy(pos.add(0, 3, 0));
		placeDummy(pos.add(1, 1, 0));
		placeDummy(pos.add(1, 2, 0));
		placeDummy(pos.add(1, 3, 0));
	}
	
	private void fillEW() {

		placeDummy(pos.add(0, 1, -1));
		placeDummy(pos.add(0, 2, -1));
		placeDummy(pos.add(0, 3, -1));
		placeDummy(pos.add(0, 1, 0));
		placeDummy(pos.add(0, 2, 0));
		placeDummy(pos.add(0, 3, 0));
		placeDummy(pos.add(0, 1, 1));
		placeDummy(pos.add(0, 2, 1));
		placeDummy(pos.add(0, 3, 1));
	}
	
	private void removeNS() {

		removeDummy(pos.add(-1, 1, 0));
		removeDummy(pos.add(-1, 2, 0));
		removeDummy(pos.add(-1, 3, 0));
		removeDummy(pos.add(0, 1, 0));
		removeDummy(pos.add(0, 2, 0));
		removeDummy(pos.add(0, 3, 0));
		removeDummy(pos.add(1, 1, 0));
		removeDummy(pos.add(1, 2, 0));
		removeDummy(pos.add(1, 3, 0));
	}
	
	private void removeEW() {

		removeDummy(pos.add(0, 1, -1));
		removeDummy(pos.add(0, 2, -1));
		removeDummy(pos.add(0, 3, -1));
		removeDummy(pos.add(0, 1, 0));
		removeDummy(pos.add(0, 2, 0));
		removeDummy(pos.add(0, 3, 0));
		removeDummy(pos.add(0, 1, 1));
		removeDummy(pos.add(0, 2, 1));
		removeDummy(pos.add(0, 3, 1));
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		isOpening = compound.getBoolean("isOpening");
		state = compound.getInteger("state");
		sysTime = compound.getLong("sysTime");
		timer = compound.getInteger("timer");
		type = compound.getInteger("type");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setBoolean("isOpening", isOpening);
		compound.setInteger("state", state);
		compound.setLong("sysTime", sysTime);
		compound.setInteger("timer", timer);
		compound.setInteger("type", type);
		return super.writeToNBT(compound);
	}
}
