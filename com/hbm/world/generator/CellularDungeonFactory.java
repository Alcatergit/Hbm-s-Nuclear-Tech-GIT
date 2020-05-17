package com.hbm.world.generator;

import com.hbm.world.generator.room.TestDungeonRoom1;
import com.hbm.world.generator.room.TestDungeonRoom2;
import com.hbm.world.generator.room.TestDungeonRoom3;
import com.hbm.world.generator.room.TestDungeonRoom4;
import com.hbm.world.generator.room.TestDungeonRoom5;
import com.hbm.world.generator.room.TestDungeonRoom6;
import com.hbm.world.generator.room.TestDungeonRoom7;
import com.hbm.world.generator.room.TestDungeonRoom8;

import net.minecraft.util.EnumFacing;

public class CellularDungeonFactory {
	
	public static CellularDungeon test;
	
	public static void init() {
		
		test = new TestDungeon(11, 7, 11, 11, 150);
		test.rooms.add(new TestDungeonRoom1(test));
		test.rooms.add(new TestDungeonRoom2(test));
		test.rooms.add(new TestDungeonRoom3(test));
		test.rooms.add(new TestDungeonRoom4(test, new TestDungeonRoom5(test), EnumFacing.NORTH));
		test.rooms.add(new TestDungeonRoom6(test));
		test.rooms.add(new TestDungeonRoom7(test));
		test.rooms.add(new TestDungeonRoom8(test));
	}

}
