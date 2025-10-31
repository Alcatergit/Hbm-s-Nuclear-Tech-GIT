package com.hbm.blocks.turret;

import com.hbm.blocks.ModBlocks;
import com.hbm.main.MainRegistry;
import com.hbm.tileentity.TileEntityProxyCombo;
import com.hbm.tileentity.turret.TileEntityTurretFriendly;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class TurretFriendly extends TurretBaseNT {

	public TurretFriendly(Material materialIn, String s){
		super(materialIn, s);
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta){
		if(meta >= 12)
			return new TileEntityTurretFriendly();
		return new TileEntityProxyCombo(true, true, false);
	}
	
	@Override
	public void openGUI(World world, EntityPlayer player, int x, int y, int z){
		player.openGui(MainRegistry.instance, ModBlocks.guiID_friendly, world, x, y, z);
	}
}
