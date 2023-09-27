package com.hbm.inventory.control_panel;

import com.hbm.main.MainRegistry;
import net.minecraft.nbt.NBTTagCompound;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.main.ResourceManager;
import com.hbm.render.RenderHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SubElementPlacement extends SubElement {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/gui_control_grid.png");
	public static ResourceLocation grid = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/placement_grid.png");
	
	public float ogPosX;
	public float ogPosY;
	public Control selectedControl;
	public boolean controlGrabbed = false;

	public GuiButton deleteElement;
	public GuiButton newElement;
	public GuiButton editElement;
	public GuiButton panelResize;
	public GuiButton btn_globalVars;
	private boolean gridGrabbed = false;
	protected float gridX = 0;
	protected float gridY = 0;
	protected float gridScale = 0.1F;
	private float prevMouseX;
	private float prevMouseY;

	private int ctrl_index = 0;
	
	public SubElementPlacement(GuiControlEdit gui){
		super(gui);
	}
	
	@Override
	protected void initGui() {
		int cX = gui.width/2;
		int cY = gui.height/2;
		newElement = gui.addButton(new GuiButton(gui.currentButtonId(), cX-103, cY-69, 20, 20, "+"));
		deleteElement = gui.addButton(new GuiButton(gui.currentButtonId(), cX-103, cY-91, 20, 20, "-"));
		editElement = gui.addButton(new GuiButton(gui.currentButtonId(), cX-103, cY-113, 20, 20, "E"));
		panelResize = gui.addButton(new GuiButton(gui.currentButtonId(), cX+22, cY-91, 80, 20, "Resize"));
		btn_globalVars = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+30, cY-91, 95, 20, "Global Variables"));
		float[] cGrid = convertToGridSpace(cX+10, cY+20);
		gridX = -cGrid[0];
		gridY = cGrid[1];
		super.initGui();
	}
	
	protected void clear(){
		ogPosX = 0;
		ogPosY = 0;
		selectedControl = null;
		gridGrabbed = false;
		controlGrabbed = false;
		gridX = 0;
		gridY = 0;
		gridScale = 1;
	}
	
	public void resetPrevPos(){
		prevMouseX = gui.mouseX;
		prevMouseY = gui.mouseY;
	}

	@Override
	protected void drawScreen() {
		editElement.enabled = selectedControl != null;

		float dWheel = Mouse.getDWheel();
		float dScale = dWheel*gridScale*0.00075F;
		
		//Correction so we scale around mouse position
		float prevX = (gui.mouseX-gui.getGuiLeft())*gridScale;
		float prevY = (gui.mouseY-gui.getGuiTop())*gridScale;
		gridScale = MathHelper.clamp(gridScale-dScale, 0.025F, 0.15F);
		float currentX = (gui.mouseX-gui.getGuiLeft())*gridScale;
		float currentY = (gui.mouseY-gui.getGuiTop())*gridScale;
		gridX += prevX-currentX;
		gridY -= prevY-currentY;

		if(gridGrabbed || gui.currentEditControl != null){
			float dX = (gui.mouseX-prevMouseX)*gridScale;
			float dY = (gui.mouseY-prevMouseY)*gridScale;
			if(gridGrabbed){
				gridX -= dX;
				gridY += dY;
			} else if(gui.currentEditControl != null && !gui.isEditMode) {
				gui.currentEditControl.posX += dX;
				gui.currentEditControl.posY += dY;
			}
		}
		if((prevMouseX != gui.mouseX || prevMouseY != gui.mouseY) && controlGrabbed){
			ctrl_index = gui.control.panel.controls.indexOf(selectedControl);
			gui.control.panel.controls.remove(selectedControl);
			gui.currentEditControl = selectedControl;
			selectedControl = null;
			controlGrabbed = false;
			gui.control.markDirty();
		}
		prevMouseX = gui.mouseX;
		prevMouseY = gui.mouseY;
		GlStateManager.disableLighting();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		int cX = gui.width/2;
		int cY = gui.height/2;
		int minX = (cX-78)*gui.res.getScaleFactor();
		int minY = (cY-111)*gui.res.getScaleFactor();
		int maxX = (cX+102)*gui.res.getScaleFactor();
		int maxY = (cY+69)*gui.res.getScaleFactor();
		GL11.glScissor(minX, minY, Math.max(maxX-minX, 0), Math.max(maxY-minY, 0));
		
		gui.mc.getTextureManager().bindTexture(grid);

		float baseSizeX = (1-(gui.control.panel.b_off+gui.control.panel.d_off));
		float baseSizeY = (1-(gui.control.panel.a_off+gui.control.panel.c_off));
		float panel_hyp = (float) (baseSizeY/Math.cos(Math.abs(gui.control.panel.angle)));

		float[] box = gui.control.getBox();
		float[] pos1 = convertFromGridSpace(box[0]-0.03125F, box[1]-0.03125F);
		float[] pos2 = convertFromGridSpace(box[2]+0.03125F, box[3]+0.03125F);

		float uScale = (baseSizeX*10)/4;
		float vScale = (panel_hyp*10)/4;
		RenderHelper.drawGuiRect(pos1[0], pos1[1], 0, 0, pos2[0]-pos1[0], pos2[1]-pos1[1], uScale+0.015625F, vScale+0.015625F);

		// bottom,right lines to close the grid, cus somehow scaling the grid looks worse
		GlStateManager.disableTexture2D();
		GlStateManager.color(.4F, .4F, .4F, 1);
		RenderHelper.drawGuiRect(pos1[0], pos2[1]-(1/(gridScale*15)), 0, 0, pos2[0]-pos1[0], 1/(gridScale*15), uScale+0.015625F, 0.015625F);
		RenderHelper.drawGuiRect(pos2[0]-(1/(gridScale*15)), pos1[1], 0, 0, 1/(gridScale*15), pos2[1]-pos1[1], 0.015625F, vScale+0.015625F);
		GlStateManager.enableTexture2D();

		GL11.glPushMatrix();
		
		float spX = gui.getGuiLeft();
		float spY = gui.getGuiTop();
		GL11.glTranslated(spX, spY, 0);	
		GL11.glScaled(1/gridScale, 1/gridScale, 1/gridScale);
		GL11.glTranslated(-spX, -spY, 0);
		GL11.glTranslated(-gridX, gridY, 0);
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, ClientProxy.AUX_GL_BUFFER);
		Matrix4f mat = new Matrix4f();
		mat.load(ClientProxy.AUX_GL_BUFFER);
		ClientProxy.AUX_GL_BUFFER.rewind();
		float gridMX = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
		float gridMY = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
		renderItems(gridMX, gridMY);
		GL11.glPopMatrix();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	public void renderItems(float mx, float my){
		for(Control c : gui.control.panel.controls){
			renderControl(c);
		}
		if(gui.currentEditControl != null){
			boolean ctrl = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
			float prevX = gui.currentEditControl.posX;
			float prevY = gui.currentEditControl.posY;

			if (!ctrl) {
				gui.currentEditControl.posX = (float) Math.round(prevX*10)*.1F;
				gui.currentEditControl.posY = (float) Math.round(prevY*10)*.1F;
			}

			boolean canPlace = canPlace();
			if(!canPlace)
				GlStateManager.colorMask(true, false, false, true);
			renderControl(gui.currentEditControl);
			if (!ctrl) {
				gui.currentEditControl.posX = prevX;
				gui.currentEditControl.posY = prevY;
			}
			if(!canPlace)
				GlStateManager.colorMask(true, true, true, true);
		}
	}
	
	public void renderControl(Control c){
		Tessellator tes = Tessellator.getInstance();
		BufferBuilder buf = tes.getBuffer();
		Minecraft.getMinecraft().getTextureManager().bindTexture(c.getGuiTexture());
		buf.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX_COLOR);
		float[] box = c.getBox();
		float[] rgb = new float[] {1, (c == selectedControl)? .8F : 1F, 1F};
		buf.pos(box[0], box[1], 0).tex(0, 0).color(rgb[0], rgb[1], rgb[2], 1).endVertex();
		buf.pos(box[0], box[3], 0).tex(0, 1).color(rgb[0], rgb[1], rgb[2], 1).endVertex();
		buf.pos(box[2], box[3], 0).tex(1, 1).color(rgb[0], rgb[1], rgb[2], 1).endVertex();
		buf.pos(box[2], box[1], 0).tex(1, 0).color(rgb[0], rgb[1], rgb[2], 1).endVertex();
		tes.draw();
	}
	
	@Override
	protected void renderBackground() {
		gui.mc.getTextureManager().bindTexture(texture);

		gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, gui.getXSize(), gui.getYSize());
	}

	@Override
	protected void actionPerformed(GuiButton button) {
		if (button == newElement) {
			gui.isEditMode = false;
			gui.pushElement(gui.choice);
		}
		else if (button == deleteElement) {
			gui.isEditMode = false;
			gui.control.panel.controls.remove(selectedControl);
			selectedControl = null;
		}
		else if (button == editElement) {
			if (selectedControl != null) {
				gui.currentEditControl = selectedControl;
				gui.itemConfig.last_control = null;
				gui.itemConfig.variants = ControlRegistry.getAllControlsOfType(gui.currentEditControl.getControlType());
				gui.pushElement(gui.itemConfig);
				gui.isEditMode = true;
				selectedControl = null;
			}
		}
		else if (button == panelResize) {
			gui.pushElement(gui.panelResize);
		}
		else if (button == btn_globalVars) {
			gui.pushElement(gui.globalVars);
		}
	}
	
	protected boolean canPlace(){
		if(gui.currentEditControl == null)
			return false;
		for(Control c : gui.control.panel.controls){
			if(RenderHelper.boxesOverlap(c.getBox(), gui.currentEditControl.getBox())){
				return false;
			}
		}
		if(!RenderHelper.boxContainsOther(gui.control.getBox(), gui.currentEditControl.getBox()))
			return false;
		return true;
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button){
		float gridMX = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
		float gridMY = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
		if(button == 0){
			if(gui.currentEditControl != null) {
				float prevX = gui.currentEditControl.posX;
				float prevY = gui.currentEditControl.posY;
				if(!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
					gui.currentEditControl.posX = (float) Math.round(prevX*10)*.1F;
					gui.currentEditControl.posY = (float) Math.round(prevY*10)*.1F;
				}
				if(canPlace()){
					gui.control.panel.controls.add(gui.currentEditControl);
					gui.currentEditControl = null;
				} else {
					gui.currentEditControl.posX = prevX;
					gui.currentEditControl.posY = prevY;
				}
			} else {
				for(Control c : gui.control.panel.controls){
					if(RenderHelper.intersects2DBox(gridMX, gridMY, c.getBox())){
						selectedControl = c;
						controlGrabbed = true;
						return;
					}
				}
				selectedControl = null;
				controlGrabbed = false;
			}
		}
		if(button == 2){
			gridGrabbed = true;
			prevMouseX = gui.mouseX;
			prevMouseY = gui.mouseY;
		}
	}
	
	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state){
		if(state == 2){
			gridGrabbed = false;
		}
		if(state == 0){
			if(canPlace()){
				if(!Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
					gui.currentEditControl.posX = (float)Math.round(gui.currentEditControl.posX*10)*.1F;
					gui.currentEditControl.posY = (float)Math.round(gui.currentEditControl.posY*10)*.1F;
				}
				gui.control.panel.controls.add(ctrl_index, gui.currentEditControl);
				gui.currentEditControl = null;
			}
			controlGrabbed = false;
		}
	}

	@Override
	protected void enableButtons(boolean enable) {
		newElement.enabled = enable;
		newElement.visible = enable;
		deleteElement.enabled = enable;
		deleteElement.visible = enable;
		editElement.enabled = enable;
		editElement.visible = enable;
		panelResize.enabled = enable;
		panelResize.visible = enable;
		btn_globalVars.enabled = enable;
		btn_globalVars.visible = enable;
	}

	protected float[] convertToGridSpace(float x, float y){
		float gridMX = (x-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
		float gridMY = (y-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
		return new float[]{gridMX, gridMY};
	}
	
	protected float[] convertFromGridSpace(float gridMX, float gridMY){
		float x = ((gridMX-gridX)-gui.getGuiLeft())/gridScale+gui.getGuiLeft();
		float y = ((gridMY+gridY)-gui.getGuiTop())/gridScale+gui.getGuiTop();
		return new float[]{x, y};
	}
}
