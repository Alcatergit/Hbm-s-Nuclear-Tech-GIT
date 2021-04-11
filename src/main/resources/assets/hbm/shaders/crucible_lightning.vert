#version 330 compatibility

uniform sampler2D noise;
uniform float time;

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;
layout (location = 2) in vec4 in_color;

out vec4 color;

void main(){
	color = in_color;
	vec3 noise_a = textureLod(noise, tex.ss + vec2(time*0.001, time*-0.02), 0).xyz;
	vec3 noise_b = textureLod(noise, tex.ss + vec2(time*0.01, time*0.015), 0).xyz;
	vec3 offset = (noise_a.xyz*noise_b.zxy-vec3(0.5))*0.1;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos+offset, 1);
}