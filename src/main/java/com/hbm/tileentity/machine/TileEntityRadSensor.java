package com.hbm.tileentity.machine;

import com.hbm.blocks.ModBlocks;
import com.hbm.lib.Library;
import com.hbm.saveddata.RadiationSavedData;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;

public class TileEntityRadSensor extends TileEntity implements ITickable {

	public float chunkRads = 0;
	public float recievedDose = 0;

	public int redstoneOutput = 0;
	public int comparatorOutput = 0;

	public int lastRedstoneOutput = 0;
	public int lastComparatorOutput = 0;
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		chunkRads = compound.getFloat("chunkRads");
		recievedDose = compound.getFloat("recievedDose");
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setFloat("chunkRads", chunkRads);
		compound.setFloat("recievedDose", recievedDose);
		return super.writeToNBT(compound);
	}

	public int getRestoneOutput(){
		if(chunkRads < 0.01)
			return 0;
		if(chunkRads < 0.5)
			return 1;
		if(chunkRads < 1)
			return 2;
		if(chunkRads < 2)
			return 3;
		if(chunkRads < 4)
			return 4;
		if(chunkRads < 8)
			return 5;
		if(chunkRads < 16)
			return 6;
		if(chunkRads < 32)
			return 7;
		if(chunkRads < 64)
			return 8;
		if(chunkRads < 128)
			return 9;
		if(chunkRads < 256)
			return 10;
		if(chunkRads < 512)
			return 11;
		if(chunkRads < 1024)
			return 12;
		if(chunkRads < 2048)
			return 13;
		if(chunkRads < 4096)
			return 14;
		return 15;
	}

	public int getComparatorOutput(){
		if(recievedDose < 0.01)
			return 0;
		if(recievedDose < 8)
			return 1;
		if(recievedDose < 16)
			return 2;
		if(recievedDose < 32)
			return 3;
		if(recievedDose < 64)
			return 4;
		if(recievedDose < 128)
			return 5;
		if(recievedDose < 256)
			return 6;
		if(recievedDose < 512)
			return 7;
		if(recievedDose < 1024)
			return 8;
		if(recievedDose < 2048)
			return 9;
		if(recievedDose < 4096)
			return 10;
		if(recievedDose < 8192)
			return 11;
		if(recievedDose < 16384)
			return 12;
		if(recievedDose < 32768)
			return 13;
		if(recievedDose < 65536)
			return 14;
		return 15;
	}

	
	@Override
	public void update() {
		if(!world.isRemote) {
			lastRedstoneOutput = redstoneOutput;
			lastComparatorOutput = comparatorOutput;

			RadiationSavedData data = RadiationSavedData.getData(world);
			chunkRads = data.getRadNumFromCoord(pos);
			recievedDose += chunkRads/20F;

			redstoneOutput = getRestoneOutput();
			comparatorOutput = getComparatorOutput();

			if(redstoneOutput != lastRedstoneOutput || lastComparatorOutput != comparatorOutput)
				world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
		}
	}
}
