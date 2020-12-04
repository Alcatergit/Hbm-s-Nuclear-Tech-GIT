#version 330 compatibility

layout (location = 0) in vec3 pos;
layout (location = 1) in vec2 tex;
layout (location = 2) in vec4 color;

out vec2 pass_tex;
out vec4 pass_color;

void main(){
	pass_tex = tex;
	pass_color = color;
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 1);
}