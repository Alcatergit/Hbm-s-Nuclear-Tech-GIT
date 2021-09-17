#version 120

uniform vec3 tanDirection;
uniform mat3 matrix;

varying vec2 texCoord;
varying vec3 normal;
varying vec3 tangent;

void main(){
	texCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).st;
	normal = gl_Normal;
	tangent = normalize(cross(normal, tanDirection));
	//gl_Position = gl_Vertex;
	float depth = (matrix * gl_Vertex.xyz).z;
	gl_Position = vec4(gl_MultiTexCoord0.s*2-1, gl_MultiTexCoord0.t*2-1, depth, 1);
}