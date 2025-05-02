package com.hbm.tileentity.network;

import com.hbm.tileentity.network.RTTYSystem.RTTYChannel;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.MathHelper;

public class TileEntityRadioTorchReceiver extends TileEntityRadioTorchBase {

	@Override
	public void update() {
		
		if(!world.isRemote) {
			
			if(!this.channel.isEmpty()) {
				
				RTTYChannel chan = RTTYSystem.listen(world, this.channel);
				
				if(chan != null && (this.polling || (chan.timeStamp > this.lastUpdate - 1 && chan.timeStamp != -1))) { // if we're either polling or a new message has come in
					String msg = "" + chan.signal;
					this.lastUpdate = world.getTotalWorldTime();
					int nextState = 0; //if no remap apply, default to 0
					
					if(this.customMap) {
						for(int i = 15; i >= 0; i--) { // highest to lowest, if duplicates exist for some reason
							if(msg.equals(this.mapping[i])) {
								nextState = i;
								break;
							}
						}
					} else {
						int sig = 0;
						try { 
							sig = Integer.parseInt(msg); 
						} catch(Exception x) {
						}
						nextState = MathHelper.clamp(sig, 0, 15);
					}
					
					if(chan.timeStamp < this.lastUpdate - 2 && this.polling) {
						nextState = 0;
					}
					
					if(this.lastState != nextState) {
						this.lastState = nextState;
						EnumFacing dir = EnumFacing.byIndex(this.getBlockMetadata());
						BlockPos strongPos = new BlockPos(pos.getX() + dir.getXOffset(), pos.getY() + dir.getYOffset(), pos.getZ() + dir.getZOffset());
						
						world.notifyNeighborsOfStateChange(pos, getBlockType(), true);
						world.notifyNeighborsOfStateChange(strongPos, getBlockType(), true);
						world.neighborChanged(strongPos, getBlockType(), pos);
						// IBlockState state = world.getBlockState(pos);
						// world.markAndNotifyBlock(pos, world.getChunk(pos), state, state, 2);
						this.markDirty();
					}
				}
			}
		}
		
		super.update();
	}
}
