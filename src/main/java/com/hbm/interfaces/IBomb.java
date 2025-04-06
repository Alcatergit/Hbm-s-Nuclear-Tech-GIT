package com.hbm.interfaces;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBomb {

	/////////////people
	
	
	
	//Months later I found this joke again
	//I'm not even sorry
	
	//Drillgon200: Months later (probably) I found this joke and don't understand it. Probably another reference...

	BombReturnCode explode(World world, BlockPos pos);

	enum BombReturnCode {
		UNDEFINED(false, ""),										//non-null type for passing to clients that don't process the return code
		DETONATED(true, "bomb.detonated"),							//success for blowing up bombs
		TRIGGERED(true, "bomb.triggered"),							//success for triggering other things
		LAUNCHED(true, "bomb.launched"),							//success for launching missiles
		ERROR_MISSING_COMPONENT(false, "bomb.missingComponent"),	//error for bomb parts missing
		ERROR_INCOMPATIBLE(false, "bomb.incompatible"),				//error for target being incompatible (but still implements IBomb for some reason), like locked blast doors
		ERROR_NO_BOMB(false, "bomb.nobomb");						//not to be used by the bombs themselves, this is the generic error when trying to trigger no-bomb blocks

		private String unloc;
		private boolean success;

		private BombReturnCode(boolean success, String unloc) {
			this.unloc = unloc;
			this.success = success;
		}

		public String getUnlocalizedMessage() {
			return this.unloc;
		}

		public boolean wasSuccessful() {
			return this.success;
		}
	}
}
