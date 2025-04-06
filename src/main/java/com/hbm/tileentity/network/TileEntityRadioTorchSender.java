package com.hbm.tileentity.network;

public class TileEntityRadioTorchSender extends TileEntityRadioTorchBase {

	@Override
	public void update() {
		
		if(!world.isRemote) {
			int redstonePower = world.getStrongPower(pos);

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
