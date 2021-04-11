#version 330 compatibility

layout (triangles) in;
layout (triangle_strip, max_vertices = 3) out;

in vec3 worldPosV[];
in vec3 normalV[];
in vec3 colorV[];
in vec4 fragPosShadowSpaceV[];
in vec2 texture_coordV[];

out vec3 worldPos;
out vec3 normal;
out vec3 color;
out vec4 fragPosShadowSpace;
out vec2 texture_coord;

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