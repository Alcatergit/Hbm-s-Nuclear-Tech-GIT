package com.hbm.tileentity.machine;

import com.hbm.blocks.machine.BlockHadronDiode;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityTickingBase;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntityHadronDiode extends TileEntityTickingBase {

	int age = 0;
	boolean fatherIAskOfYouToUpdateMe = false;

	public DiodeConfig[] sides = new DiodeConfig[6];
	
	public TileEntityHadronDiode() {
		for(int i = 0; i < 6; i ++){
			sides[i] = DiodeConfig.NONE;
		}
	}
	
	@Override
	public void update() {
		if(!world.isRemote) {
			age++;

			if(age >= 20) {
				age = 0;
				sendSides();
			}
			
			if(fatherIAskOfYouToUpdateMe) {
				fatherIAskOfYouToUpdateMe = false;
				//world.markBlockRangeForRenderUpdate(pos, pos);
				BlockHadronDiode.resetBlockState(world, pos);
			}
		}
	}

	@Override
	public String getInventoryName() {
		return "";
	}
	
	public void sendSides() {

		NBTTagCompound data = new NBTTagCompound();

		for(int i = 0; i < 6; i++) {

			if(sides[i] != null)
				data.setInteger("" + i, sides[i].ordinal());
		}
		BlockHadronDiode.resetBlockState(world, pos);
		this.networkPack(data, 250);
	}
	
	@Override
	public void networkUnpack(NBTTagCompound nbt) {
		for(int i = 0; i < 6; i++) {
			sides[i] = DiodeConfig.values()[nbt.getInteger("" + i)];
		}
		//world.markBlockRangeForRenderUpdate(pos, pos);
	}
	
	public DiodeConfig getConfig(int side) {

		if(ForgeDirection.getOrientation(side) == ForgeDirection.UNKNOWN)
			return DiodeConfig.NONE;

		DiodeConfig conf = sides[side];

		if(conf == null)
			return DiodeConfig.NONE;

		return conf;
	}

	public void setConfig(int side, int config) {
		sides[side] = DiodeConfig.values()[config];
		this.markDirty();
		sendSides();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		for(int i = 0; i < 6; i++) {
			sides[i] = DiodeConfig.values()[compound.getInteger("side_" + i)];
		}

		fatherIAskOfYouToUpdateMe = true;
		super.readFromNBT(compound);
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(int i = 0; i < 6; i++) {

			if(sides[i] != null) {
				compound.setInteger("side_" + i, sides[i].ordinal());
			}
		}
		return super.writeToNBT(compound);
	}

	public static enum DiodeConfig {
		NONE,
		IN,
		OUT
	}
	
}
