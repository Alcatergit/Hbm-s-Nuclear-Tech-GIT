#version 330 compatibility

out vec2 texCoord;
out vec4 color;

void main(){
	texCoord = gl_MultiTexCoord0.st;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	color = gl_Color;
}