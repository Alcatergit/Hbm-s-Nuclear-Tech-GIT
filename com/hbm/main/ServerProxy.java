package com.hbm.main;

import java.io.File;

import com.hbm.render.amlfrom1710.Vec3;

import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy
{
	public void registerRenderInfo()
	{
		
	}
	
	public void registerTileEntitySpecialRenderer() { }
	
	public void particleControl(double x, double y, double z, int type) { }

	public void spawnParticle(double x, double y, double z, String type, float[] args) { }
	
	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Vec3 payload) { }

	public void registerMissileItems() { }

	public void preInit(FMLPreInitializationEvent evt) {}
	
	public File getDataDir(){
		return FMLCommonHandler.instance().getMinecraftServerInstance().getDataDirectory();
	}
	
	public void postInit(FMLPostInitializationEvent e){
		
	}
}