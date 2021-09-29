package com.hbm.lib;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;

public enum ForgeDirection {

	//Drillgon200: Screw it, copy paste ForgeDirection from 1.7.10 because EnumFacing doesn't have UNKNOWN
	
	/** -Y */
    DOWN(0, -1, 0),

    /** +Y */
    UP(0, 1, 0),

    /** -Z */
    NORTH(0, 0, -1),

    /** +Z */
    SOUTH(0, 0, 1),

    /** -X */
    WEST(-1, 0, 0),

    /** +X */
    EAST(1, 0, 0),

    /**
     * Used only by getOrientation, for invalid inputs
     */
    UNKNOWN(0, 0, 0);

    public final int offsetX;
    public final int offsetY;
    public final int offsetZ;
    public final int flag;
    public static final ForgeDirection[] VALID_DIRECTIONS = {DOWN, UP, NORTH, SOUTH, WEST, EAST};
    public static final int[] OPPOSITES = {1, 0, 3, 2, 5, 4, 6};
    // Left hand rule rotation matrix for all possible axes of rotation
    public static final int[][] ROTATION_MATRIX = {
        {0, 1, 4, 5, 3, 2, 6},
        {0, 1, 5, 4, 2, 3, 6},
    	{5, 4, 2, 3, 0, 1, 6},
    	{4, 5, 2, 3, 1, 0, 6},
    	{2, 3, 1, 0, 4, 5, 6},
    	{3, 2, 0, 1, 4, 5, 6},
    	{0, 1, 2, 3, 4, 5, 6},
    };

    private ForgeDirection(int x, int y, int z)
    {
        offsetX = x;
        offsetY = y;
        offsetZ = z;
        flag = 1 << ordinal();
    }

    public static ForgeDirection getOrientation(int id)
    {
        if (id >= 0 && id < VALID_DIRECTIONS.length)
        {
            return VALID_DIRECTIONS[id];
        }
        return UNKNOWN;
    }

    public ForgeDirection getOpposite()
    {
        return getOrientation(OPPOSITES[ordinal()]);
    }

    public ForgeDirection getRotation(ForgeDirection axis)
    {
    	return getOrientation(ROTATION_MATRIX[axis.ordinal()][ordinal()]);
    }
    
    public Rotation getBlockRotation(){
    	switch(this){
    	case NORTH: return Rotation.NONE;
    	case SOUTH: return Rotation.CLOCKWISE_180;
    	case EAST: return Rotation.COUNTERCLOCKWISE_90;
    	case WEST: return Rotation.CLOCKWISE_90;
    	default:
    	return Rotation.NONE;
    	}
    }
    
    public EnumFacing toEnumFacing(){
    	return EnumFacing.values()[ordinal()];
    }
    
    public float getRotationDegrees(){
    	switch(this.ordinal()) {
		case 2: return 0;
		case 4: return 90;
		case 3: return 180;
		case 5: return 270;
		default: return 0;
		}
    }
    
    public float getRotationRadians(){
    	return (float) Math.toRadians(getRotationDegrees());
    }
}
