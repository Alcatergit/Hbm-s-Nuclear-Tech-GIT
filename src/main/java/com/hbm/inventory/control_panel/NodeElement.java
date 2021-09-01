package com.hbm.inventory.control_panel;

public class NodeElement {

	public Node parent;
	public int index;
	public float offsetX;
	public float offsetY;
	
	public NodeElement(Node parent, int idx){
		this.parent = parent;
		this.index = idx;
		resetOffset();
	}
	
	public void render(float mX, float mY){
	}
	
	public void resetOffset(){
		offsetX = parent.posX;
		offsetY = parent.posY+index*8;
	}
	
	public boolean onClick(float x, float y){
		return false;
	}
}
