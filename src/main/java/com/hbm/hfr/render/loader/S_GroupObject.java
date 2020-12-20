package com.hbm.hfr.render.loader;

import java.util.ArrayList;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class S_GroupObject {
	public String name;
	public ArrayList<S_Face> faces = new ArrayList<S_Face>();
	public int glDrawingMode;

	public S_GroupObject() {
		this("");
	}

	public S_GroupObject(String name) {
		this(name, -1);
	}

	public S_GroupObject(String name, int glDrawingMode) {
		this.name = name;
		this.glDrawingMode = glDrawingMode;
	}

	public void render() {
		if (this.faces.size() > 0) {
			Tessellator tessellator = Tessellator.getInstance();
			tessellator.getBuffer().begin(glDrawingMode, DefaultVertexFormats.POSITION_TEX_NORMAL);
			render(tessellator);
			tessellator.draw();
		}
	}

	public void render(Tessellator tessellator) {
		if (this.faces.size() > 0) {
			for (S_Face face : this.faces) {
				face.addFaceForRender(tessellator.getBuffer());
			}
		}
	}
}
