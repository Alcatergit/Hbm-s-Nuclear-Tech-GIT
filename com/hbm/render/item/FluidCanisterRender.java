package com.hbm.render.item;

import org.lwjgl.opengl.GL11;

import com.hbm.forgefluid.SpecialContainerFillLists.EnumCanister;
import com.hbm.render.RenderHelper;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidUtil;

public class FluidCanisterRender extends TileEntityItemStackRenderer {

	public static final FluidCanisterRender INSTANCE = new FluidCanisterRender();
	public IBakedModel itemModel;
	public TransformType type;
	
	@Override
	public void renderByItem(ItemStack stack) {
		IBakedModel model = null;
		if(FluidUtil.getFluidContained(stack) != null && EnumCanister.contains(FluidUtil.getFluidContained(stack).getFluid()))
			model = EnumCanister.getEnumFromFluid(FluidUtil.getFluidContained(stack).getFluid()).getRenderModel();
		if(model == null)
			model = itemModel;
		
		RenderHelper.bindBlockTexture();
		
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = Tessellator.getInstance().getBuffer();
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.ITEM);
		for (EnumFacing enumfacing : EnumFacing.values()) {
			Minecraft.getMinecraft().getRenderItem().renderQuads(buf,
					model.getQuads((IBlockState) null, enumfacing, 0L), -1, stack);
		}
		Minecraft.getMinecraft().getRenderItem().renderQuads(buf,
				model.getQuads((IBlockState) null, (EnumFacing) null, 0L), -1, stack);
		tes.draw();
		super.renderByItem(stack);
	}
	
}
