#version 120

attribute vec3 pos;
attribute vec2 tex;
attribute vec4 in_color;

uniform sampler2D noise;
uniform float time;

varying vec4 color;

void main(){
	color = in_color;
	vec3 noise_a = texture2DLod(noise, tex.ss + vec2(time*0.001, time*-0.02), 0).xyz;
	vec3 noise_b = texture2DLod(noise, tex.ss + vec2(time*0.01, time*0.015), 0).xyz;
	vec3 offset = (noise_a.xyz*noise_b.zxy-vec3(0.5))*0.1;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos+offset, 1);
}