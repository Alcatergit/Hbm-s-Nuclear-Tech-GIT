#version 330 compatibility

out vec2 tex_coord;

void main(){
	tex_coord = gl_MultiTexCoord0.st;
	gl_Position = gl_Vertex;
}