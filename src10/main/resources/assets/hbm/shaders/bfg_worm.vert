#version 120

varying vec2 texCoord;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_FrontColor = gl_Color;
	texCoord = gl_MultiTexCoord0.xy;
}