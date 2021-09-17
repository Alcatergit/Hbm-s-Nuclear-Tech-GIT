#version 120
#extension GL_EXT_gpu_shader4 : enable

attribute vec3 pos;
attribute vec2 tex;
attribute vec4 color;

uniform int vertices;
uniform float age;
uniform float fadeoverride;

varying vec2 pass_tex;
varying vec2 noise_tex;
varying vec4 pass_color;
varying float fade;

void main(){
	if(fadeoverride != 1){
		fade = fadeoverride;
	} else {
		float vertPosN = float((vertices - gl_VertexID + age*1.4))/max(float(vertices), 10);
		fade = clamp((vertPosN-1)*2, 0, 1);
	}
	pass_tex = tex;
	pass_color = color;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 1);
	noise_tex = (gl_Position.xy/gl_Position.ww + 1)*3;
}