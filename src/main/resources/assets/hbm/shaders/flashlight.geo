#version 120
#extension GL_EXT_geometry_shader4 : enable

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

varying vec3 worldPosV[];
varying vec3 normalV[];
varying vec3 colorV[];
varying vec4 fragPosShadowSpaceV[];
varying vec2 texture_coordV[];

varying vec3 worldPos;
varying vec3 normal;
varying vec3 color;
varying vec4 fragPosShadowSpace;
varying vec2 texture_coord;

void main(){
	normal = normalize(cross(worldPosV[1]-worldPosV[0], worldPosV[2]-worldPosV[0]));
	for(int i = 0; i < 3; i ++){
		worldPos = worldPosV[i];
		color = colorV[i];
		fragPosShadowSpace = fragPosShadowSpaceV[i];
		texture_coord = texture_coordV[i];
		gl_Position = gl_in[i].gl_Position;
		EmitVertex();
	}
	EndPrimitive();
}