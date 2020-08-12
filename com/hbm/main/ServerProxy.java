package com.hbm.main;

import java.io.File;

import com.hbm.render.amlfrom1710.Vec3;
import com.hbm.sound.AudioWrapper;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ServerProxy
{
	public void registerRenderInfo() { }
	public void registerTileEntitySpecialRenderer() { }
	public void registerItemRenderer() { }
	public void registerEntityRenderer() { }
	public void registerBlockRenderer() { }
	
	public void particleControl(double x, double y, double z, int type) { }

	public void spawnParticle(double x, double y, double z, String type, float[] args) { }
	
	public void spawnSFX(World world, double posX, double posY, double posZ, int type, Vec3 payload) { }

	public void effectNT(NBTTagCompound data) { }
	
	public void registerMissileItems(IRegistry<ModelResourceLocation, IBakedModel> reg) { }

	public AudioWrapper getLoopedSound(SoundEvent sound, SoundCategory cat, float x, float y, float z, float volume, float pitch) { return null; }
	
	public void preInit(FMLPreInitializationEvent evt) {}
	
	public File getDataDir(){
		return FMLCommonHandler.instance().getMinecraftServerInstance().getDataDirectory();
	}
	
	public void postInit(FMLPostInitializationEvent e){
		
	}
}