#version 330 compatibility

out vec2 texCoord;
out vec2 lightCoord;
out vec4 color;

void main(){
	texCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;
	lightCoord = (gl_TextureMatrix[1] * gl_MultiTexCoord1).st;
	color = gl_Color;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}