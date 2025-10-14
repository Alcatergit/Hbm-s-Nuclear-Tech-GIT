package com.hbm.world.generator.room;

import com.hbm.world.generator.CellularDungeon;

public class VaultDungeonRoomTall extends VaultDungeonRoom {
    int extra = 0;
    public VaultDungeonRoomTall(CellularDungeon parent, int extraHeight) {
        super(parent, 4);
        this.extra = extraHeight;
    }

    @Override
    public int getH(){
        return parent.height + extra;
    }
}