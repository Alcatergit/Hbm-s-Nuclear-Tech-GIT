#version 330 compatibility

uniform sampler2D texture;
uniform vec4 duck;

in vec2 pass_tex;
in vec4 pass_color;

void main(){
	gl_FragColor = duck;
}