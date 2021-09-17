#version 120

varying vec2 texCoordOut;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_TexCoord[0] = gl_MultiTexCoord0;
	texCoordOut = gl_TexCoord[0].xy;
}