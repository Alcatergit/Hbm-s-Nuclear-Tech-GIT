package com.hbm.items.armor;

import java.util.List;

import com.hbm.handler.ArmorModHandler;
import com.hbm.items.ModItems;
import com.hbm.render.model.ModelM65;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderPlayerEvent.Pre;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemModGasmask extends ItemArmorMod {

	@SideOnly(Side.CLIENT)
	private ModelM65 modelM65;
	
	private ResourceLocation tex = new ResourceLocation("hbm:textures/models/ModelM65.png");
	private ResourceLocation tex_mono = new ResourceLocation("hbm:textures/models/ModelM65Mono.png");
	
	public ItemModGasmask(String s) {
		super(ArmorModHandler.helmet_only, true, false, false, false, s);
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> list, ITooltipFlag flagIn){
		if(this == ModItems.attachment_mask)
			list.add(TextFormatting.GREEN + "Gas protection");
		if(this == ModItems.attachment_mask_mono)
			list.add(TextFormatting.GREEN + "Carbon monoxide protection");
		
		list.add("");
		super.addInformation(stack, worldIn, list, flagIn);
	}
	
	@Override
	public void addDesc(List<String> list, ItemStack stack, ItemStack armor){
		if(this == ModItems.attachment_mask)
			list.add(TextFormatting.GREEN + "  " + stack.getDisplayName() + " (gas protection)");
		if(this == ModItems.attachment_mask_mono)
			list.add(TextFormatting.GREEN + "  " + stack.getDisplayName() + " (carbon monoxide protection)");
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void modRender(Pre event, ItemStack armor){
		if(this.modelM65 == null) {
			this.modelM65 = new ModelM65();
		}
		
		RenderPlayer renderer = event.getRenderer();
		ModelBiped model = renderer.getMainModel();
		EntityPlayer player = event.getEntityPlayer();

		modelM65.isSneak = model.isSneak;
		
		float interp = event.getPartialRenderTick();
		float yawHead = player.prevRotationYawHead + (player.rotationYawHead - player.prevRotationYawHead) * interp;
		float yawWrapped = MathHelper.wrapDegrees(yawHead+180);
		float pitch = player.rotationPitch;

		if(this == ModItems.attachment_mask)
			Minecraft.getMinecraft().renderEngine.bindTexture(tex);
		if(this == ModItems.attachment_mask_mono)
			Minecraft.getMinecraft().renderEngine.bindTexture(tex_mono);
		
		modelM65.render(event.getEntityPlayer(), 0.0F, 0.0F, 0, yawWrapped, pitch, 0.0625F);
	}
}
