package com.hbm.tileentity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.hbm.interfaces.IKeypadHandler;
import com.hbm.lib.ForgeDirection;
import com.hbm.util.Keypad;
import com.hbm.util.KeypadClient;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class TileEntityKeypadBase extends TileEntity implements ITickable, IKeypadHandler {

	public Keypad keypad;
	
	public TileEntityKeypadBase() {
		if(FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER){
			keypad = new Keypad(this);
		} else {
			keypad = makeClientKeypad();
		}
	}
	
	//Hopefully side onlying this method stops the server crashing.
	@SideOnly(Side.CLIENT)
	private Keypad makeClientKeypad(){
		return new KeypadClient(this, new Matrix4f());
	}
	
	@Override
	public void onLoad() {
		if(world.isRemote){
			setupKeypadClient();
		} else {
			NBTTagCompound nbt = keypad.writeToNbt(new NBTTagCompound());
			keypad = new Keypad(this);
			keypad.readFromNbt(nbt);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void setupKeypadClient() {
		float rot = ForgeDirection.getOrientation(this.getBlockMetadata()).getRotationRadians();
		Matrix4f mat = new Matrix4f();
		mat.rotate(rot, new Vector3f(0, 1, 0));
		mat.translate(new Vector3f(0, 0, -0.5F));
		keypad = new KeypadClient(this, mat);
	}
	
	@Override
	public void update() {
		keypad.update();
	}

	@Override
	public Keypad getKeypad() {
		return keypad;
	}

	@Override
	public void keypadActivated() {
		
	}
	
	@Override
	public void passwordSet() {
		
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		keypad.writeToNbt(compound);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		keypad.readFromNbt(compound);
		super.readFromNBT(compound);
	}

}
