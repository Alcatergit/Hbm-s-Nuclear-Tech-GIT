package com.hbm.render.misc;

import com.hbm.util.BobMathUtil;

public class ColorGradient {

	private float[][] colors;
	
	/**
	 * @param colors - float arrays of colors and times. Should be formatted as R,G,B,A,T where T is the time that color setting gets.
	 * Times should always be normalized between 0-1.
	 * This class is unsafe and I'm not putting in the effort to make it robust. If you format data wrong, it will crash with a less than helpful error message.
	 * If I was doing this in a shader, it would be a texture, but since this is minecraft, I won't use shaders.
	 */
	public ColorGradient(float[]... colors) {
		this.colors = colors;
	}
	
	public float[] getColor(float time){
		if(time < 0){
			return colors[0];
		} else if(time > 1){
			return colors[colors.length-1];
		} else {
			for(int i = 0; i < colors.length-1; i ++){
				if(colors[i][4] <= time && colors[i+1][4] > time){
					float interp = BobMathUtil.remap(time, 0, 1, colors[i][4], colors[i+1][4]);
					float[] color = new float[4];
					color[0] = colors[i][0] + (colors[i+1][0] - colors[i][0])*interp;
					color[1] = colors[i][1] + (colors[i+1][1] - colors[i][1])*interp;
					color[2] = colors[i][2] + (colors[i+1][2] - colors[i][2])*interp;
					color[3] = colors[i][3] + (colors[i+1][3] - colors[i][3])*interp;
					return color;
				}
			}
		}
		return colors[0];
	}
}
