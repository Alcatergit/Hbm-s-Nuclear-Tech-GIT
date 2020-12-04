#version 330 compatibility

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}