package com.hbm.tileentity.machine.rbmk;

import com.hbm.blocks.machine.rbmk.RBMKControl;
import com.hbm.interfaces.IControlReceiver;
import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.tileentity.machine.rbmk.TileEntityRBMKConsole.ColumnType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;

import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.SimpleComponent;

public class TileEntityRBMKControlManual extends TileEntityRBMKControl implements IControlReceiver, SimpleComponent {

	public RBMKColor color;
	public double startingLevel;
	
	public TileEntityRBMKControlManual() {
		super();
	}

	public void setColor(int color) {
		RBMKColor new_color = RBMKColor.values()[color];
		this.color = new_color;
	}

	@Override
	public String getName() {
		return "container.rbmkControl";
	}
	
	@Override
	public boolean isModerated() {
		return ((RBMKControl)this.getBlockType()).moderated;
	}

	@Override
	public void setTarget(double target) {
		this.targetLevel = target;
		this.startingLevel = this.level;
	}

	@Override
	public double getMult() {
		
		double surge = 0;
		
		if(this.targetLevel < this.startingLevel && Math.abs(this.level - this.targetLevel) > 0.01D) {
			surge = Math.sin(Math.pow((1D - this.level), 15) * Math.PI) * (this.startingLevel - this.targetLevel) * RBMKDials.getSurgeMod(world);
			
		}
		
		return this.level + surge;
	}

	@Override
	public boolean hasPermission(EntityPlayer player) {
		return Vec3.createVectorHelper(pos.getX() - player.posX, pos.getY() - player.posY, pos.getZ() - player.posZ).lengthVector() < 20;
	}

	@Override
	public void receiveControl(NBTTagCompound data) {
		
		if(data.hasKey("level")) {
			this.setTarget(data.getDouble("level"));
		}
		
		if(data.hasKey("color")) {
			int c = Math.abs(data.getInteger("color")) % RBMKColor.values().length; //to stop naughty kids from sending packets that crash the server
			
			RBMKColor newCol = RBMKColor.values()[c];
			
			if(newCol == this.color) {
				this.color = null;
			} else {
				this.color = newCol;
			}
		}
		
		this.markDirty();
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		super.readFromNBT(nbt);

		if(nbt.hasKey("startingLevel"))
			this.startingLevel = nbt.getDouble("startingLevel");
		
		if(nbt.hasKey("color"))
			this.color = RBMKColor.values()[nbt.getInteger("color")];
		else
			this.color = null;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		super.writeToNBT(nbt);

		nbt.setDouble("startingLevel", this.startingLevel);
		nbt.setDouble("mult", this.getMult());
		
		if(color != null)
			nbt.setInteger("color", color.ordinal());
		return nbt;
	}
	
	public static enum RBMKColor {
		RED,
		YELLOW,
		GREEN,
		BLUE,
		PURPLE
	}

	@Override
	public ColumnType getConsoleType() {
		return ColumnType.CONTROL;
	}

	@Override
	public NBTTagCompound getNBTForConsole() {
		NBTTagCompound data = super.getNBTForConsole();
		
		if(this.color != null)
			data.setShort("color", (short)this.color.ordinal());
		else
			data.setShort("color", (short)-1);
		
		return data;
	}

	// opencomputers interface

	@Override
	public String getComponentName() {
		return "rbmk_control_rod_manual";
	}
	
	@Callback(doc = "func(): double - returns hull temp")
	public Object[] getHullTemp(Context context, Arguments args) {
		return new Object[] {heat};
	}

	@Callback(doc = "func(): double - returns steam quantity")
	public Object[] getSteam(Context context, Arguments args) {
		return new Object[] {steam};
	}

	@Callback(doc = "func(): double - returns water quantity")
	public Object[] getWater(Context context, Arguments args) {
		return new Object[] {water};
	}

	@Callback(doc= "func(): bool - returns true if moderated")
	public Object[] isModerated(Context context, Arguments args) {
		return new Object[] {isModerated()};
	}

	@Callback(doc = "func(): double - returns insertion level")
	public Object[] getLevel(Context context, Arguments args) {
		return new Object[] {getMult()};
	}

	@Callback(doc = "func(): double - returns target insersion level")
	public Object[] getTargetLevel(Context context, Arguments args) {
		return new Object[] {targetLevel};
	}

	@Callback(doc = "func(): int - returns ordinal of color value")
	public Object[] getColor(Context context, Arguments args) {
		return new Object[] {color.ordinal()};
	}

	@Callback(doc = "func(x:str): null - sets the color given its ordinal number x[0:4]")
	public Object[] setColor(Context context, Arguments args) {
		RBMKColor new_color = RBMKColor.values()[Integer.parseInt(args.checkString(0))];
		color = new_color;
		return new Object[] {};
	}

	@Callback(doc = "func(n:str): null - sets target insertion level where 0<=n<=100")
	public Object[] setLevel(Context context, Arguments args) {
		double newLevel = Double.parseDouble(args.checkString(0))/100.0;
		if (newLevel > 1) { // check if its above 100 so the control rod wont do funny things
			newLevel = 1;
		}
		targetLevel = newLevel;
		return new Object[] {};
	}
}