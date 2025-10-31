package com.hbm.world.generator;

import com.hbm.world.generator.room.*;

import net.minecraft.util.EnumFacing;

public class CellularDungeonFactory {
	
	public static CellularDungeon meteor;
	public static CellularDungeon jungle;
    public static CellularDungeon vault;

    public static void init() {
		
		meteor = new MeteorDungeon(11, 7, 11, 11, 150, 3);
		meteor.rooms.add(new MeteorDungeonRoom1(meteor));
		meteor.rooms.add(new MeteorDungeonRoom2(meteor));
		meteor.rooms.add(new MeteorDungeonRoom3(meteor));
		meteor.rooms.add(new MeteorDungeonRoom4(meteor, new MeteorDungeonRoom5(meteor), EnumFacing.NORTH));
		meteor.rooms.add(new MeteorDungeonRoom6(meteor));
		meteor.rooms.add(new MeteorDungeonRoom7(meteor));
		meteor.rooms.add(new MeteorDungeonRoom8(meteor));

		jungle = new JungleDungeon(5, 5, 25, 25, 700, 6);
		for(int i = 0; i < 10; i++) jungle.rooms.add(new JungleDungeonRoom(jungle));
		jungle.rooms.add(new JungleDungeonRoomArrow(jungle));
		jungle.rooms.add(new JungleDungeonRoomArrowFire(jungle));
		jungle.rooms.add(new JungleDungeonRoomFire(jungle));
		jungle.rooms.add(new JungleDungeonRoomMagic(jungle));
		jungle.rooms.add(new JungleDungeonRoomMine(jungle));
		jungle.rooms.add(new JungleDungeonRoomPillar(jungle));
		jungle.rooms.add(new JungleDungeonRoomPoison(jungle));
		jungle.rooms.add(new JungleDungeonRoomRad(jungle));
		jungle.rooms.add(new JungleDungeonRoomRubble(jungle));
		jungle.rooms.add(new JungleDungeonRoomSlowness(jungle));
		jungle.rooms.add(new JungleDungeonRoomSpiders(jungle));
		jungle.rooms.add(new JungleDungeonRoomSpikes(jungle));
		jungle.rooms.add(new JungleDungeonRoomWeakness(jungle));
		jungle.rooms.add(new JungleDungeonRoomWeb(jungle));
		jungle.rooms.add(new JungleDungeonRoomZombie(jungle));

        vault = new VaultDungeon(17, 9, 7, 7, 150, 4);
        vault.rooms.add(new VaultDungeonRoomWiese(vault));
        vault.rooms.add(new VaultDungeonRoomFarm(vault));
        vault.rooms.add(new VaultDungeonRoomArmory(vault));
        vault.rooms.add(new VaultDungeonRoomControl(vault));
        vault.rooms.add(new VaultDungeonRoomCanteen(vault));
        vault.rooms.add(new VaultDungeonRoomMedical(vault));
        vault.rooms.add(new VaultDungeonRoomPower(vault));
        vault.rooms.add(new VaultDungeonRoomSleep(vault));
        vault.rooms.add(new VaultDungeonRoomTall(vault, 2));
        vault.rooms.add(new VaultDungeonRoomTall(vault, 6));
        vault.rooms.add(new VaultDungeonRoomTall(vault, 10));
    }
}
