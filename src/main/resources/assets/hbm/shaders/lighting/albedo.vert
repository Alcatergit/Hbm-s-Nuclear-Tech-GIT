#version 330 compatibility

out vec2 texCoord;

void main(){
	gl_FrontColor = gl_Color;
	texCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}