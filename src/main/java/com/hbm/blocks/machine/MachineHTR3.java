package com.hbm.blocks.machine;

import com.hbm.blocks.BlockDummyable;
import com.hbm.blocks.ILookOverlay;
import com.hbm.dim.CelestialBody;
import com.hbm.inventory.fluid.FluidType;
import com.hbm.inventory.fluid.tank.FluidTankNTM;
import com.hbm.inventory.fluid.trait.FT_Heatable;
import com.hbm.inventory.fluid.trait.FT_Rocket;
import com.hbm.items.machine.IItemFluidIdentifier;
import com.hbm.lib.ForgeDirection;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.machine.TileEntityMachineHTR3;
import com.hbm.util.BobMathUtil;
import com.hbm.util.I18nUtil;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Pre;

import java.util.ArrayList;
import java.util.List;

public class MachineHTR3 extends BlockDummyable implements ILookOverlay {

	public MachineHTR3(String s) {
		super(Material.IRON, s);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		if(meta >= 12) return new TileEntityMachineHTR3();
		if(meta >= 6) return new TileEntityProxyCombo(false, false, true);
		return null;
	}

	@Override
	public int[] getDimensions() {
		return new int[] {6, 0, 3, 3, 5, 5};
	}

	@Override
	public int getOffset() {
		return 6;
	}

	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		
		if(!world.isRemote && !player.isSneaking()) {
				
			if(!player.getHeldItem(hand).isEmpty() && player.getHeldItem(hand).getItem() instanceof IItemFluidIdentifier) {
				int[] posC = this.findCore(world, pos.getX(), pos.getY(), pos.getZ());
					
				if(posC == null)
					return false;
				
				TileEntity te = world.getTileEntity(new BlockPos(posC[0], posC[1], posC[2]));
				
				if(!(te instanceof TileEntityMachineHTR3))
					return false;
				
				TileEntityMachineHTR3 htr3 = (TileEntityMachineHTR3) te;
				
				FluidType type = ((IItemFluidIdentifier) player.getHeldItem(hand).getItem()).getType(world, posC[0], posC[1], posC[2], player.getHeldItem(hand));

				FT_Heatable heatable = type.getTrait(FT_Heatable.class);

				if(heatable != null && heatable.getFirstStep().typeProduced.hasTrait(FT_Rocket.class)) {
					htr3.tanks[0].setTankType(heatable.getFirstStep().typeProduced);
					htr3.markDirty();
				}
				
				return true;
			}
			return false;
			
		} else {
			return true;
		}
	}
	
	@Override
	public ForgeDirection getDirModified(ForgeDirection dir) {
		return dir.getRotation(ForgeDirection.DOWN);
	}

	@Override
	public void fillSpace(World world, int x, int y, int z, ForgeDirection dir, int o) {
		super.fillSpace(world, x, y, z, dir, o);

		ForgeDirection rot = dir.getRotation(ForgeDirection.UP);

		x += dir.offsetX * 3;
		z += dir.offsetZ * 3;

		this.makeExtra(world, x - rot.offsetX, y, z - rot.offsetZ);
		this.makeExtra(world, x + rot.offsetX, y, z + rot.offsetZ);
	}

	@Override
	public void printHook(Pre event, World world, int x, int y, int z) {
		if(!CelestialBody.inOrbit(world)) return;

		int[] pos = this.findCore(world, x, y, z);
		
		if(pos == null) return;
		
		TileEntity te = world.getTileEntity(new BlockPos(pos[0], pos[1], pos[2]));
		
		if(!(te instanceof TileEntityMachineHTR3))
			return;
		
		TileEntityMachineHTR3 thruster = (TileEntityMachineHTR3) te;

		List<String> text = new ArrayList<String>();

		if(!thruster.isFacingPrograde()) {
			text.add("&[" + (BobMathUtil.getBlink() ? 0xff0000 : 0xffff00) + "&]! ! ! " + I18nUtil.resolveKey("atmosphere.engineFacing") + " ! ! !");
		} else {
			for(int i = 0; i < thruster.tanks.length; i++) {
				FluidTankNTM tank = thruster.tanks[i];
				text.add(TextFormatting.GREEN + "-> " + TextFormatting.RESET + tank.getTankType().getLocalizedName() + ": " + tank.getFill() + "/" + tank.getMaxFill() + "mB");
			}
		}

		ILookOverlay.printGeneric(event, I18nUtil.resolveKey(getTranslationKey() + ".name"), 0xffff00, 0x404000, text);
	}

}
