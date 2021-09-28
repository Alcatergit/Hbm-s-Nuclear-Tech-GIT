package net.minecraft.world;

public class HbmWorldUtility {

	public static void setImmediateScheduledUpdates(World world, boolean update){
		world.scheduledUpdatesAreImmediate = update;
	}
}
