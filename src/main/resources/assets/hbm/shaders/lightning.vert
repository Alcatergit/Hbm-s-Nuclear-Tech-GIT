#version 330 compatibility

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;
layout (location = 2) in vec4 color;

uniform int vertices;
uniform float age;
uniform float fadeoverride;

out vec2 pass_tex;
out vec2 noise_tex;
out vec4 pass_color;
out float fade;

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