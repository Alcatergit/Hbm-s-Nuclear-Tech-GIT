package com.hbm.handler;

public class MultiblockDefinition {
    private final int posX;
    private final int negX;
    private final int posY;
    private final int negY;
    private final int posZ;
    private final int negZ;

    public MultiblockDefinition(int posX, int negX, int posY, int negY, int posZ, int negZ) {
        this.posX = posX;
        this.negX = negX;
        this.posY = posY;
        this.negY = negY;
        this.posZ = posZ;
        this.negZ = negZ;
    }

    public int getPosX() {
        return posX;
    }

    public int getNegX() {
        return negX;
    }

    public int getPosY() {
        return posY;
    }

    public int getNegY() {
        return negY;
    }

    public int getPosZ() {
        return posZ;
    }

    public int getNegZ() {
        return negZ;
    }

    public int[] toArray() {
        return new int[]{posX, negX, posY, negY, posZ, negZ};
    }
}
