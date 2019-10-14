package com.hbm.main;

import com.hbm.items.ModItems;
import com.hbm.render.item.ItemRedstoneSwordRender;
import com.hbm.render.tileentity.RenderPress;
import com.hbm.tileentity.machine.TileEntityMachinePress;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends ServerProxy {
	
	public static final ModelResourceLocation IRRELEVANT_MRL = new ModelResourceLocation("hbm:placeholdermodel", "inventory");
	
	@Override
	public void registerRenderInfo()
	{
		MinecraftForge.EVENT_BUS.register(new ModEventHandlerClient());
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityMachinePress.class, new RenderPress());
		
	}
	@Override
	public void registerMissileItems() {
		
	}
	@Override
	public void registerTileEntitySpecialRenderer() {
		
	}
	@Override
	public void particleControl(double x, double y, double z, int type) {
		
	}
	//version 2, now with strings!
	@Override
	public void spawnParticle(double x, double y, double z, String type, float args[]) {
		
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent evt){
		OBJLoader.INSTANCE.addDomain("hbm");
		ModItems.redstone_sword.setTileEntityItemStackRenderer(ItemRedstoneSwordRender.instance);
	}
	
}
