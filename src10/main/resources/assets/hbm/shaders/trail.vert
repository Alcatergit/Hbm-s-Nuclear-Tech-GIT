#version 120

attribute vec3 pos;
attribute vec2 tex;
attribute vec4 color;

varying vec2 pass_tex;
varying vec4 pass_color;

void main(){
	pass_tex = tex;
	pass_color = color;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 1);
}