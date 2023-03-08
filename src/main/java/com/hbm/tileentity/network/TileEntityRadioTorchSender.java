package com.hbm.tileentity.network;

import com.hbm.tileentity.network.RTTYSystem;

import net.minecraft.block.state.IBlockState;

public class TileEntityRadioTorchSender extends TileEntityRadioTorchBase {

	@Override
	public void update() {
		
		if(!world.isRemote) {
			int redstonePower = world.isBlockIndirectlyGettingPowered(pos);

			boolean shouldSend = this.polling;
			if(redstonePower != this.lastState) {
				this.markDirty();
				this.lastState = redstonePower;
				shouldSend = true;
			}

			if(shouldSend && !this.channel.isEmpty()) {
				RTTYSystem.broadcast(world, this.channel, this.customMap ? this.mapping[redstonePower] : (redstonePower + ""));
			}
		}
		
		super.update();
	}
}
