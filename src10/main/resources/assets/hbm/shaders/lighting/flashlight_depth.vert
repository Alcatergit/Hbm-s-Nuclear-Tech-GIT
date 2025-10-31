#version 120

varying vec2 texCoord;
varying vec4 position;

void main(){
	texCoord = gl_MultiTexCoord0.st;
	position = gl_ModelViewMatrix * gl_Vertex;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}