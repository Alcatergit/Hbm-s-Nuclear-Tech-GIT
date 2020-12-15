package com.hbm.handler;

import java.io.IOException;

import com.hbm.items.weapon.ItemMissile;
import com.hbm.items.weapon.ItemMissile.PartType;
import com.hbm.render.misc.MissileMultipart;
import com.hbm.render.misc.MissilePart;

import io.netty.buffer.ByteBuf;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MissileStruct {

	public ItemMissile warhead;
	public ItemMissile fuselage;
	public ItemMissile fins;
	public ItemMissile thruster;
	
	public MissileStruct() { }
	
	public MissileStruct(ItemStack w, ItemStack f, ItemStack s, ItemStack t) {

		if(w != null && w.getItem() instanceof ItemMissile)
			warhead = (ItemMissile) w.getItem();
		if(f != null && f.getItem() instanceof ItemMissile)
			fuselage = (ItemMissile) f.getItem();
		if(s != null && s.getItem() instanceof ItemMissile)
			fins = (ItemMissile) s.getItem();
		if(t != null && t.getItem() instanceof ItemMissile)
			thruster = (ItemMissile) t.getItem();
	}
	
	public MissileStruct(Item w, Item f, Item s, Item t) {

		if(w instanceof ItemMissile)
			warhead = (ItemMissile) w;
		if(f instanceof ItemMissile)
			fuselage = (ItemMissile) f;
		if(s instanceof ItemMissile)
			fins = (ItemMissile) s;
		if(t instanceof ItemMissile)
			thruster = (ItemMissile) t;
	}
	
	public void writeToByteBuffer(ByteBuf buf) {


		if(warhead != null && warhead.type == PartType.WARHEAD)
			buf.writeInt(Item.getIdFromItem(warhead));
		else
			buf.writeInt(0);
		
		if(fuselage != null && fuselage.type == PartType.FUSELAGE)
			buf.writeInt(Item.getIdFromItem(fuselage));
		else
			buf.writeInt(0);
		
		if(fins != null && fins.type == PartType.FINS)
			buf.writeInt(Item.getIdFromItem(fins));
		else
			buf.writeInt(0);
		
		if(thruster != null && thruster.type == PartType.THRUSTER)
			buf.writeInt(Item.getIdFromItem(thruster));
		else
			buf.writeInt(0);
	}
	
	public static MissileStruct readFromByteBuffer(ByteBuf buf) {
		
		MissileStruct multipart = new MissileStruct();

		int w = buf.readInt();
		int f = buf.readInt();
		int s = buf.readInt();
		int t = buf.readInt();
		
		if(w != 0)
			multipart.warhead = (ItemMissile) Item.getItemById(w);
		
		if(f != 0)
			multipart.fuselage = (ItemMissile) Item.getItemById(f);
		
		if(s != 0)
			multipart.fins = (ItemMissile) Item.getItemById(s);
		
		if(t != 0)
			multipart.thruster = (ItemMissile) Item.getItemById(t);
		
		return multipart;
	}
	
	@SideOnly(Side.CLIENT)
	public MissileMultipart multipart(){
		MissileMultipart missile = new MissileMultipart();
        missile.warhead = MissilePart.getPart(this.warhead);
        missile.fuselage = MissilePart.getPart(this.fuselage);
        missile.fins = MissilePart.getPart(this.fins);
        missile.thruster = MissilePart.getPart(this.thruster);
        return missile;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj == this)
			return true;
		if(!(obj instanceof MissileStruct))
			return false;
		MissileStruct str = (MissileStruct) obj;
		return this.warhead == str.warhead && this.fuselage == str.fuselage && this.fins == str.fins && this.thruster == str.thruster;
	}
	
	@Override
	public int hashCode() {
		int hashcode = 17;
		hashcode = 31 * hashcode + warhead.hashCode();
		hashcode = 31 * hashcode + fuselage.hashCode();
		if(fins != null)
			hashcode = 31 * hashcode + fins.hashCode();
		hashcode = 31 * hashcode + thruster.hashCode();
		return hashcode;
	}
	
	public static final DataSerializer<MissileStruct> SERIALIZER = new DataSerializer<MissileStruct>(){

		@Override
		public void write(PacketBuffer buf, MissileStruct value) {
			if(value == null){
				buf.writeInt(-1);
				return;
			}
			buf.writeInt(Item.getIdFromItem(value.warhead));
			buf.writeInt(Item.getIdFromItem(value.fuselage));
			buf.writeInt(value.fins == null ? -1 : Item.getIdFromItem(value.fins));
			buf.writeInt(Item.getIdFromItem(value.thruster));
		}

		@Override
		public MissileStruct read(PacketBuffer buf) throws IOException {
			int i = buf.readInt();
			if(i < 0)
				return null;
			Item w = Item.getItemById(i);
			Item f = Item.getItemById(buf.readInt());
			Item s;
			i = buf.readInt();
			if(i < 0)
				s = null;
			else
				s = Item.getItemById(i);
			Item t = Item.getItemById(buf.readInt());
			return new MissileStruct(w, f, s, t);
		}

		@Override
		public DataParameter<MissileStruct> createKey(int id) {
			return new DataParameter<MissileStruct>(id, this);
		}

		@Override
		public MissileStruct copyValue(MissileStruct value) {
			return value;
		}
		
	};

}