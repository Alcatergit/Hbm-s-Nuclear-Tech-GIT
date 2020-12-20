package com.hbm.tileentity;

import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import com.hbm.blocks.BlockDummyable;
import com.hbm.interfaces.IKeypadHandler;
import com.hbm.interfaces.Spaghetti;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.machine.TileEntitySlidingBlastDoor;
import com.hbm.util.Keypad;
import com.hbm.util.KeypadClient;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Spaghetti("Weird stuff to make it work property client side")
public class TileEntitySlidingBlastDoorKeypad extends TileEntityKeypadBase {

	public boolean foundCore = false;
	
	@Override
	@SideOnly(Side.CLIENT)
	public void setupKeypadClient() {
	}
	
	@SideOnly(Side.CLIENT)
	public void setupKeypadClient(BlockPos corePos, int meta){
		ForgeDirection dir = ForgeDirection.getOrientation(meta);
		if(((BlockDummyable)getBlockType()).hasExtra(getBlockMetadata())){
			dir = dir.getOpposite();
		}
		float rot = dir.getRotationRadians();
		Matrix4f mat = new Matrix4f();
		mat.rotate(rot, new Vector3f(0, 1, 0));
		mat.translate(new Vector3f(-0.03125F, 0.27812F, -0.46875F));
		mat.scale(new Vector3f(0.35F, 0.4125F, 0.25F));
		keypad = new KeypadClient(this, mat);
	}
	
	@Override
	public void update() {
		super.update();
		if(world.isRemote && !foundCore){
			int[] corePos = ((BlockDummyable)this.getBlockType()).findCore(world, pos.getX(), pos.getY(), pos.getZ());
			if(corePos == null)
				return;
			int meta = world.getBlockState(new BlockPos(corePos[0], corePos[1], corePos[2])).getValue(BlockDummyable.META)-BlockDummyable.offset;
			setupKeypadClient(new BlockPos(corePos[0], corePos[1], corePos[2]), meta);
			foundCore = true;
		}
	}
	
	@Override
	public void keypadActivated() {
		Block b = this.getBlockType();
		if(b instanceof BlockDummyable){
			int[] corePos = ((BlockDummyable) b).findCore(world, pos.getX(), pos.getY(), pos.getZ());
			TileEntity core = world.getTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]));
			if(core instanceof TileEntitySlidingBlastDoor){
				((TileEntitySlidingBlastDoor) core).toggle();
			}
		}
	}
	
	@Override
	public void passwordSet() {
		Block b = this.getBlockType();
		if(b instanceof BlockDummyable){
			int[] corePos = ((BlockDummyable) b).findCore(world, pos.getX(), pos.getY(), pos.getZ());
			TileEntity core = world.getTileEntity(new BlockPos(corePos[0], corePos[1], corePos[2]));
			if(core instanceof TileEntitySlidingBlastDoor){
				((TileEntitySlidingBlastDoor) core).keypadLocked = true;
				BlockPos otherPad = this.pos.subtract(new BlockPos(corePos[0], corePos[1], corePos[2]));
				otherPad = new BlockPos(-otherPad.getX(), otherPad.getY(), -otherPad.getZ()).add(new BlockPos(corePos[0], corePos[1], corePos[2]));
				if(world.getTileEntity(otherPad) instanceof IKeypadHandler){
					Keypad pad = ((IKeypadHandler)world.getTileEntity(otherPad)).getKeypad();
					pad.clearCode();
					pad.isSettingCode = false;
					pad.storedCode = this.keypad.storedCode;
				}
			}
		}
	}
}
