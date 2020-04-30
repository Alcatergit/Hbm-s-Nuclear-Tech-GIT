package com.hbm.animloader;

import java.io.FileNotFoundException;

import org.apache.commons.io.IOUtils;

import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ColladaLoader {

	private static IResourceManager manager;

	public static void onResourceManagerReload(IResourceManager resourceManager) {
		manager = resourceManager;
	}

	public static void load(ResourceLocation file) throws Exception {
		
		IResource resource = null;
		try {
			try {
				resource = manager.getResource(file);
			} catch(FileNotFoundException e) {
				if(file.getResourcePath().startsWith("models/block/"))
					resource = manager.getResource(new ResourceLocation(file.getResourceDomain(), "models/item/" + file.getResourcePath().substring("models/block/".length())));
				else if(file.getResourcePath().startsWith("models/item/"))
					resource = manager.getResource(new ResourceLocation(file.getResourceDomain(), "models/block/" + file.getResourcePath().substring("models/item/".length())));
				else
					throw e;
			}
			parse(resource);
		} finally {
			IOUtils.closeQuietly(resource);
		}
	}
	
	private static void parse(IResource model){
	}

}
