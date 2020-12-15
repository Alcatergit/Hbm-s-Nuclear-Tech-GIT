#version 330 compatibility

out vec3 frag_normal;
out vec3 frag_pos;

void main(){
	frag_normal = (gl_NormalMatrix * gl_Normal).xyz;
	frag_pos = (gl_ModelViewMatrix * gl_Vertex).xyz;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
}