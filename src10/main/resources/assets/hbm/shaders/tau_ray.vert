#version 120

varying vec2 texCoord;
varying vec4 color;

void main(){
	texCoord = gl_MultiTexCoord0.st;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	color = gl_Color;
}