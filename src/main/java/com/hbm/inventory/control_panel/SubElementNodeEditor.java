package com.hbm.inventory.control_panel;

import java.util.*;

import com.hbm.inventory.control_panel.nodes.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;

import com.hbm.lib.RefStrings;
import com.hbm.main.ClientProxy;
import com.hbm.render.RenderHelper;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class SubElementNodeEditor extends SubElement {

	public static ResourceLocation texture = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/gui_placement_front.png");
	public static ResourceLocation grid = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/grid.png");
	
	public GuiButton btn_back;
	public GuiButton btn_variables;

	public ItemList addMenu;
	
	private NodeSystem currentSystem;
	private Deque<NodeSystem> systemHistoryStack = new ArrayDeque<>();

	private ControlEvent currentEvent;
	private List<ControlEvent> sendEvents;
	private boolean gridGrabbed = false;
	protected float gridX = 0;
	protected float gridY = 0;
	protected float gridScale = 1;
	private float prevMouseX;
	private float prevMouseY;
	
	public SubElementNodeEditor(GuiControlEdit gui){
		super(gui);
	}
	
	protected void setData(Map<String, NodeSystem> map, ControlEvent c, List<ControlEvent> sendEvents){
		currentSystem = map.computeIfAbsent(c.name, e -> new NodeSystem(gui.currentEditControl, this));
		currentSystem.nodeEditor = this;
		currentSystem.gui = gui;
		currentSystem.activeNode = null;
		currentSystem.selectedNodes = new ArrayList<>();
		currentSystem.drag = false;
		currentSystem.dragDist = 0;
		
		currentEvent = c;
		gridX = 0;
		gridY = 0;
		gridScale = 1;
		this.sendEvents = sendEvents;
	}

	private void descendSubsystem(Node node) {
		systemHistoryStack.push(currentSystem);
		currentSystem = currentSystem.subSystems.get(node);

		currentSystem.nodeEditor = this;
		currentSystem.gui = gui;
		currentSystem.activeNode = null;
		currentSystem.selectedNodes = new ArrayList<>();
		currentSystem.drag = false;
		currentSystem.dragDist = 0;
	}
	
	@Override
	protected void initGui(){
		int cX = gui.width/2;
		int cY = gui.height/2;
		btn_back = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+7, gui.getGuiTop()+13, 30, 20, "Back"));
		btn_variables = gui.addButton(new GuiButton(gui.currentButtonId(), gui.getGuiLeft()+54, gui.getGuiTop()+13, 58, 20, "Variables"));
		super.initGui();
	}
	
	@Override
	protected void keyTyped(char typedChar, int code){
		if(code == Keyboard.KEY_A && Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			if(addMenu != null){
				addMenu.close();
			}
			addMenu = new ItemList(gui.mouseX, gui.mouseY, 32, s -> {
				if(s.endsWith("Input")){
					ItemList list = new ItemList(0, 0, 32, s2 -> {
						final float x = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
						final float y = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
						Node node = null;
						if(s2.equals("Event Data")){
							Map<String, DataValue> vars = new HashMap<>(currentEvent.vars);
							vars.put(sendEvents == null ? "to index" : "from index", new DataValueFloat(0));
							node = new NodeInput(x, y, "Event Data").setVars(vars);
						} else if(s2.equals("Get Variable")){
							node = new NodeGetVar(x, y, gui.currentEditControl);
						} else if (s2.equals("Query Block")) {
							node = new NodeQueryBlock(x, y, gui.currentEditControl);
						}
						if(node != null){
							addMenu.close();
							addMenu = null;
							currentSystem.addNode(node);
							currentSystem.activeNode = node;
						}
						return null;
					});
					list.addItems("Event Data");
					list.addItems("Get Variable");
					list.addItems("Query Block");
					return list;
				} else if(s.endsWith("Math")){
					ItemList list = new ItemList(0, 0, 32, s2 -> {
						final float x = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
						final float y = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
						Node node = null;
						if(s2.equals("Math Node")){
							node = new NodeMath(x, y);
						}
						if(node != null){
							addMenu.close();
							addMenu = null;
							currentSystem.addNode(node);
							currentSystem.activeNode = node;
						}
						return null;
					});
					list.addItems("Math Node");
					return list;
				} else if(s.endsWith("Boolean")){
					ItemList list = new ItemList(0, 0, 32, s2 -> {
						final float x = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
						final float y = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
						Node node = null;
						if(s2.equals("Boolean Node")){
							node = new NodeBoolean(x, y);
						}
						if(node != null){
							addMenu.close();
							addMenu = null;
							currentSystem.addNode(node);
							currentSystem.activeNode = node;
						}
						return null;
					});
					list.addItems("Boolean Node");
					return list;
				} else if(s.endsWith("Logic")){
					ItemList list = new ItemList(0, 0, 32, s2 -> {
						final float x = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
						final float y = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
						Node node = null;
						if (s2.equals("Function")) {
							node = new NodeFunction(x, y);
						}
						else if (s2.equals("Buffer")) {
							node = new NodeBuffer(x, y);
						}
						else if (s2.equals("Conditional")) {
							node = new NodeConditional(x, y);
						}
						if (node != null) {
							addMenu.close();
							addMenu = null;
							currentSystem.addNode(node);
							currentSystem.activeNode = node;
						}
						return null;
					});
					list.addItems("Function");
					list.addItems("Buffer");
					list.addItems("Conditional");
					return list;
				} else if(s.endsWith("Output")){
					ItemList list = new ItemList(0, 0, 32, s2 -> {
						final float x = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
						final float y = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
						Node node = null;
						if(s2.equals("Broadcast")){
							node = new NodeEventBroadcast(x, y, sendEvents);
						} else if(s2.equals("Cancel")){
							node = new NodeCancelEvent(x, y);
						} else if(s2.equals("Set Variable")){
							node = new NodeSetVar(x, y, gui.currentEditControl);
						}
						if(node != null){
							addMenu.close();
							addMenu = null;
							currentSystem.addNode(node);
							currentSystem.activeNode = node;
						}
						return null;
					});
					if(sendEvents != null){
						if(sendEvents.size() > 0)
							list.addItems("Broadcast");
					} else {
						list.addItems("Cancel");
					}
					list.addItems("Set Variable");
					return list;
				}
				return null;
			});
			addMenu.addItems("{expandable}Input", "{expandable}Output", "{expandable}Math", "{expandable}Boolean", "{expandable}Logic");
		}
		if(code == Keyboard.KEY_DELETE || code == Keyboard.KEY_X){
			List<Node> selected = new ArrayList<>(currentSystem.selectedNodes);
			for(Node n : selected){
				currentSystem.removeNode(n);
			}
		}
		if(currentSystem != null){
			currentSystem.keyTyped(typedChar, code);
		}
	}
	
	@Override
	protected void renderBackground(){
		gui.mc.getTextureManager().bindTexture(texture);
		gui.drawTexturedModalRect(gui.getGuiLeft(), gui.getGuiTop(), 0, 0, gui.getXSize(), gui.getYSize());
	}
	
	@Override
	protected void drawScreen(){
		float dWheel = Mouse.getDWheel();
		float dScale = dWheel*gridScale*0.00075F;
		
		//Correction so we scale around mouse position
		float prevX = (gui.mouseX-gui.getGuiLeft())*gridScale;
		float prevY = (gui.mouseY-gui.getGuiTop())*gridScale;
		gridScale = MathHelper.clamp(gridScale-dScale, 0.25F, 2.5F);
		float currentX = (gui.mouseX-gui.getGuiLeft())*gridScale;
		float currentY = (gui.mouseY-gui.getGuiTop())*gridScale;
		gridX += prevX-currentX;
		gridY -= prevY-currentY;
		
		if(gridGrabbed){
			float dX = gui.mouseX-prevMouseX;
			float dY = gui.mouseY-prevMouseY;
			gridX -= dX*gridScale;
			gridY += dY*gridScale;
			prevMouseX = gui.mouseX;
			prevMouseY = gui.mouseY;
		}
		GlStateManager.disableLighting();
		GL11.glEnable(GL11.GL_SCISSOR_TEST);
		int cX = gui.width/2;
		int cY = gui.height/2;
		int minX = (cX-72)*gui.res.getScaleFactor();
		int minY = (cY-114)*gui.res.getScaleFactor();
		int maxX = (cX+120)*gui.res.getScaleFactor();
		int maxY = (cY+78)*gui.res.getScaleFactor();
		//System.out.println(cY);
		//System.out.println(Minecraft.getMinecraft().displayHeight/2);
		GL11.glScissor(minX, minY, maxX-minX, maxY-minY);
		
		////GlStateManager.matrixMode(GL11.GL_TEXTURE);
		//GL11.glPushMatrix();
		gui.mc.getTextureManager().bindTexture(grid);
		float x = gridX/gui.getXSize();
		float y = -gridY/gui.getYSize();
		//float scalePointX = x + gui.mouseX/gui.getXSize();
		//float scalePointY = y + gui.mouseY/gui.getYSize();
		//GL11.glTranslated(scalePointX, scalePointY, 0);
		//GL11.glScaled(gridScale, gridScale, 0);
		//GL11.glTranslated(-scalePointX, -scalePointY, 0);
		RenderHelper.drawGuiRectColor(gui.getGuiLeft(), gui.getGuiTop(), x, y, gui.getXSize(), gui.getYSize(), gridScale+x, gridScale+y, 0.2F, 0.2F, 0.2F, 1);
		//GL11.glPopMatrix();
		//GlStateManager.matrixMode(GL11.GL_MODELVIEW);
		
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
		//System.out.println(gui.getGuiLeft() + " " + gui.getGuiTop() + " " + mat.m30 + " " + mat.m31);
		ClientProxy.AUX_GL_BUFFER.rewind();
		float gridMX = (gui.mouseX-gui.getGuiLeft())*gridScale + gui.getGuiLeft() + gridX;
		float gridMY = (gui.mouseY-gui.getGuiTop())*gridScale + gui.getGuiTop() - gridY;
		currentSystem.render(gridMX, gridMY);
		GL11.glPopMatrix();
		if(addMenu != null){
			addMenu.render(gui.mouseX, gui.mouseY);
		}
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int button){
		if(addMenu != null && button == 0){
			if(RenderHelper.intersects2DBox(mouseX, mouseY, addMenu.getBoundingBox())){
				addMenu.mouseClicked(mouseX, mouseY);
			} else {
				addMenu.close();
				addMenu = null;
			}
		} else if(button == 0){
			// doing this here for now cus i want buttons to be able to make gui changes
			NodeElement pressed = currentSystem.getNodeElementPressed(mouseX, mouseY);
			if (pressed != null) {
				switch (pressed.name) {
					case "Edit Body": {
						descendSubsystem(pressed.parent);
					}
				}
			}
			currentSystem.onClick(mouseX, mouseY);
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
		if(addMenu == null && state == 0){
			currentSystem.clickReleased(mouseX, mouseY);
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button){
		if(button == btn_back){
			if(currentSystem != null){
				currentSystem.removeClientData();
				currentSystem = null;

				if (!systemHistoryStack.isEmpty()) {
					currentSystem = systemHistoryStack.getFirst();
					systemHistoryStack.pop();
					return;
				}
			}
			gui.popElement();
		}
		if (button == btn_variables) {
			gui.pushElement(gui.variables);
		}
	}
	
	@Override
	public void onClose(){
		if(currentSystem != null){
			currentSystem.removeClientData();
			currentSystem = null;
		}
	}
	
	@Override
	protected void enableButtons(boolean enable){
		btn_back.enabled = enable;
		btn_back.visible = enable;
		btn_variables.enabled = enable;
		btn_variables.visible = enable;
	}
	
}
