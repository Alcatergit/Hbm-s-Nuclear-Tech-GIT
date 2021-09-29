package com.hbm.inventory.control_panel;

import java.util.function.Function;
import java.util.function.Supplier;

import org.lwjgl.opengl.GL11;

import com.hbm.inventory.control_panel.nodes.Node;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.NBTTagCompound;

public class NodeDropdown extends NodeElement {

	public ItemList list;
	public Supplier<String> nameGetter;
	
	public NodeDropdown(Node parent, int idx, Function<String, ItemList> func, Supplier<String> nameGetter){
		super(parent, idx);
		list = new ItemList(0, 0, 32, func);
		list.alpha = 0.95F;
		list.r = list.g = list.b = 0.3F;
		list.textColor = 0xFFDFDFDF;
		list.close();
		resetOffset();
		this.nameGetter = nameGetter;
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag, NodeSystem sys){
		throw new RuntimeException("Dropdown node should not be serialized!");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tag, NodeSystem sys){
		throw new RuntimeException("Dropdown node should not be serialized!");
	}
	
	@Override
	public void resetOffset(){
		super.resetOffset();
		if(list != null){
			list.posX = this.offsetX+4;
			list.posY = this.offsetY+14;
		}
	}
	
	public void setOffset(float x, float y){
		this.offsetX = x;
		this.offsetY = y;
		list.posX = x+4;
		list.posY = y+14;
	}
	
	@Override
	public void render(float mX, float mY){
		Minecraft.getMinecraft().getTextureManager().bindTexture(NodeSystem.node_tex);
		Tessellator.getInstance().getBuffer().begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		float x = offsetX+4;
		float y = offsetY+8;
		RenderHelper.drawGuiRectBatchedColor(x, y, 0F, 0.890625F, 32, 6, 0.609375F, 0.984375F, 1, 1, 1, 1);
		Tessellator.getInstance().draw();
		list.render(mX, mY);
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		GL11.glPushMatrix();
		GL11.glTranslated(x, y, 0);
		GL11.glScaled(0.35, 0.35, 0.35);
		GL11.glTranslated(-x, -y, 0);
		String s = nameGetter.get();
		font.drawString(s, x+43-font.getStringWidth(s)/2, y+5, 0xFF5F5F5F, false);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		float width = font.getCharWidth('^');
		float height = font.FONT_HEIGHT;
		x = x+27.5F;
		y = y+2.5F;
		GL11.glTranslated(x+width*0.4F, y+height*0.2F, 0);
		GL11.glScaled(0.5, 0.5, 0.5);
		if(list.isClosed)
			GL11.glRotated(180, 0, 0, 1);
		GL11.glTranslated(-x-width*0.4F, -y-height*0.2F, 0);
		font.drawString("^", x, y, 0xFF5F5F5F, false);
		GL11.glPopMatrix();
	}
	
	@Override
	public boolean onClick(float x, float y){
		if(!list.isClosed && RenderHelper.intersects2DBox(x, y, list.getBoundingBox())){
			if(list.mouseClicked(x, y)){
				list.close();
				return true;
			}
		}
		if(RenderHelper.intersects2DBox(x, y, getBox())){
			if(list.isClosed){
				list.open();
			} else {
				list.close();
			}
			return true;
		}
		return false;
	}
	
	public float[] getBox(){
		return new float[]{3+offsetX, -3+offsetY+10, 37+offsetX, 3+offsetY+10};
	}
	
}
