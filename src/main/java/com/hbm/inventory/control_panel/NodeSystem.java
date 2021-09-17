package com.hbm.inventory.control_panel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.hbm.lib.RefStrings;
import com.hbm.render.RenderHelper;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

public class NodeSystem {

	public static final ResourceLocation node_tex = new ResourceLocation(RefStrings.MODID + ":textures/gui/control_panel/node.png");
	
	public SubElementNodeEditor nodeEditor;
	public GuiControlEdit gui;
	public List<Node> nodes = new ArrayList<>();
	public List<NodeOutput> outputNodes = new ArrayList<>();
	public Node activeNode = null;
	public List<Node> selectedNodes = new ArrayList<>();
	public NodeConnection connectionInProgress;
	public NodeConnection currentTypingBox;
	
	private Map<String, DataValue> vars = new HashMap<>();
	
	protected boolean drag = false;
	protected float dragDist = 0;
	protected float lastMouseX;
	protected float lastMouseY;
	
	public NodeSystem(SubElementNodeEditor gui){
		nodeEditor = gui;
		this.gui = gui.gui;
	}
	
	public void setVar(String name, DataValue val){
		vars.put(name, val);
	}
	
	public DataValue getVar(String name){
		DataValue val = vars.get(name);
		if(val == null)
			return new DataValueFloat(0);
		return val;
	}
	
	public void render(float mX, float mY){
		if(drag && !Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			float distX = gui.mouseX - lastMouseX;
			float distY = gui.mouseY - lastMouseY;
			dragDist += Math.sqrt(distX*distX + distY*distY);
			for(Node n : selectedNodes){
				n.setPosition(n.posX+(gui.mouseX-lastMouseX)*nodeEditor.gridScale, n.posY+(gui.mouseY-lastMouseY)*nodeEditor.gridScale);
			}
			lastMouseX = gui.mouseX;
			lastMouseY = gui.mouseY;
		}
		GlStateManager.disableTexture2D();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.glLineWidth(3);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		Tessellator.getInstance().getBuffer().begin(GL11.GL_LINES, DefaultVertexFormats.POSITION);
		float nodeMx = mX;
		float nodeMy = mY;
		if(connectionInProgress != null){
			end:
			for(int i = nodes.size()-1; i >= 0; i --){
				Node n = nodes.get(i);
				if(RenderHelper.intersects2DBox(mX, mY, n.getExtendedBoundingBox())){
					for(NodeConnection c : (connectionInProgress.isInput ? n.outputs : n.inputs)){
						if(connectionInProgress.parent != c.parent && RenderHelper.intersects2DBox(mX, mY, c.getPortBox())){
							float[] center = RenderHelper.getBoxCenter(c.getPortBox());
							nodeMx = center[0];
							nodeMy = center[1];
							break end;
						}
					}
				}
			}
		}
		for(Node node : nodes){
			node.drawConnections(nodeMx, nodeMy);
		}
		Tessellator.getInstance().draw();
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GlStateManager.glLineWidth(2);
		GlStateManager.enableTexture2D();
		for(Node node : nodes){
			node.render(mX, mY, activeNode == node, selectedNodes.contains(node));
		}
	}
	
	public void addNode(Node n){
		nodes.add(n);
		if(n instanceof NodeOutput)
			outputNodes.add((NodeOutput)n);
	}
	
	public void removeNode(Node n){
		if(activeNode == n)
			activeNode = null;
		selectedNodes.remove(n);
		outputNodes.remove(n);
		nodes.remove(n);
	}
	
	public void onClick(float x, float y){
		lastMouseX = gui.mouseX;
		lastMouseY = gui.mouseY;
		float gridMX = (gui.mouseX-gui.getGuiLeft())*nodeEditor.gridScale + gui.getGuiLeft() + nodeEditor.gridX;
		float gridMY = (gui.mouseY-gui.getGuiTop())*nodeEditor.gridScale + gui.getGuiTop() - nodeEditor.gridY;
		//Click handling
		for(int i = nodes.size()-1; i >= 0; i --){
			if(nodes.get(i).onClick(gridMX, gridMY))
				return;
		}
		//Do line connection handling
		for(int i = nodes.size()-1; i >= 0; i --){
			Node n = nodes.get(i);
			if(RenderHelper.intersects2DBox(gridMX, gridMY, n.getExtendedBoundingBox())){
				List<NodeConnection> union = new ArrayList<>();
				union.addAll(n.inputs);
				union.addAll(n.outputs);
				for(NodeConnection c : union){
					if(RenderHelper.intersects2DBox(gridMX, gridMY, c.getPortBox())){
						if(c.connection != null){
							connectionInProgress = c.removeConnection();
							connectionInProgress.isDrawingLine = true;
						} else {
							connectionInProgress = c;
							c.isDrawingLine = true;
						}
						return;
					}
				}
			}
		}
		drag = true;
		dragDist = 0;
		boolean shift = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
		if(!shift && selectedNodes.size() <= 1){
			selectedNodes.clear();
			activeNode = null;
		}
		boolean clear = true;
		for(int i = nodes.size()-1; i >= 0; i --){
			Node n = nodes.get(i);
			boolean intersectsBox = RenderHelper.intersects2DBox(gridMX, gridMY, n.getBoundingBox());
			for(NodeConnection c : n.inputs){
				if(c.enumSelector != null){
					if(currentTypingBox != null){
						currentTypingBox.stopTyping();
						currentTypingBox = null;
					}
					if(c.enumSelector.onClick(gridMX, gridMY))
						return;
				}
				if(intersectsBox && RenderHelper.intersects2DBox(gridMX, gridMY, c.getValueBox())){
					if(currentTypingBox != c && c.connection == null){
						c.isTyping = true;
						c.startTyping();
						if(currentTypingBox != null){
							currentTypingBox.stopTyping();
						}
						currentTypingBox = c;
					}
				}
			}
			if(intersectsBox){
				clear = false;
				if(activeNode == n && selectedNodes.size() <= 1){
					selectedNodes.remove(n);
					activeNode = null;
				} else {
					if(!selectedNodes.contains(n))
						selectedNodes.add(n);
					activeNode = n;
				}
				break;
			}
		}
		if(currentTypingBox != null && !RenderHelper.intersects2DBox(gridMX, gridMY, currentTypingBox.getValueBox())){
			currentTypingBox.stopTyping();
			currentTypingBox = null;
		}
		if(clear){
			selectedNodes.clear();
			activeNode = null;
		}
	}
	
	public void clickReleased(float x, float y){
		float gridMX = (gui.mouseX-gui.getGuiLeft())*nodeEditor.gridScale + gui.getGuiLeft() + nodeEditor.gridX;
		float gridMY = (gui.mouseY-gui.getGuiTop())*nodeEditor.gridScale + gui.getGuiTop() - nodeEditor.gridY;
		if(connectionInProgress != null){
			for(int i = nodes.size()-1; i >= 0; i --){
				Node n = nodes.get(i);
				if(RenderHelper.intersects2DBox(gridMX, gridMY, n.getExtendedBoundingBox())){
					for(NodeConnection c : (connectionInProgress.isInput ? n.outputs : n.inputs)){
						if(connectionInProgress.parent != c.parent && RenderHelper.intersects2DBox(gridMX, gridMY, c.getPortBox())){
							c.removeConnection();
							//Only input nodes draw lines, so we don't have to maintain a connection list at each output
							if(c.isInput){
								connectionInProgress.isDrawingLine = false;
								c.isDrawingLine = true;
								c.connection = connectionInProgress.parent;
								c.connectionIndex = connectionInProgress.index;
							} else {
								connectionInProgress.connection = n;
								connectionInProgress.connectionIndex = c.index;
							}
							connectionInProgress = null;
							return;
						}
					}
				}
			}
			connectionInProgress.isDrawingLine = false;
			connectionInProgress = null;
		}
		if(!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && dragDist == 0){
			for(int i = nodes.size()-1; i >= 0; i --){
				Node n = nodes.get(i);
				if(RenderHelper.intersects2DBox(gridMX, gridMY, n.getBoundingBox())){
					selectedNodes.clear();
					selectedNodes.add(n);
					activeNode = n;
					break;
				}
			}
		}
		drag = false;
	}
	
	public void keyTyped(char c, int key){
		if(currentTypingBox != null){
			currentTypingBox.keyTyped(c, key);
			if(!currentTypingBox.isTyping)
				currentTypingBox = null;
		}
	}

	public void resetCachedValues(){
		for(Node n : nodes){
			n.cacheValid = false;
		}
	}

	public void receiveEvent(ControlPanel panel, Control ctrl, ControlEvent evt){
		for(Node n : nodes){
			if(n instanceof InputNode){
				((InputNode)n).setOutputFromVars(evt.vars);
			}
		}
		for(NodeOutput o : outputNodes){
			o.doOutput(panel.parent, ctrl.sendNodeMap, ctrl.connectedSet);
		}
	}

}
