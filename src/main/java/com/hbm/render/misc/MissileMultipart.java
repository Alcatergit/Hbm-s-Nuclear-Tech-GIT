package com.hbm.render.misc;

import com.hbm.handler.MissileStruct;
import com.hbm.items.weapon.ItemMissile.PartType;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MissileMultipart {

	public MissilePart warhead;
	public MissilePart fuselage;
	public MissilePart fins;
	public MissilePart thruster;
	
	public double getHeight() {
		
		double height = 0;

		if(warhead != null && warhead.type == PartType.WARHEAD)
			height += warhead.height;
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			height += fuselage.height;
		if(thruster != null && thruster.type == PartType.THRUSTER)
			height += thruster.height;
		
		return height;
	}
	
	public static MissileMultipart loadFromStruct(MissileStruct struct) {
		
		if(struct == null)
			return null;
		
		MissileMultipart multipart = new MissileMultipart();

		multipart.warhead = MissilePart.getPart(struct.warhead);
		multipart.fuselage = MissilePart.getPart(struct.fuselage);
		multipart.fins = MissilePart.getPart(struct.fins);
		multipart.thruster = MissilePart.getPart(struct.thruster);
		
		return multipart;
	}
}
