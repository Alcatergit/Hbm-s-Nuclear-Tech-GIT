#version 330 compatibility

uniform mat4 shadow_view;
uniform mat4 shadow_proj;
uniform mat4 mc_view;

layout (location = 0) in vec3 pos;
layout (location = 1) in vec4 vColor;

out vec3 worldPos;
out vec3 color;
out vec4 fragPosShadowSpace;
out vec2 texture_coord;
out vec3 normal;

void main(){
	color = vColor.rgb;
	texture_coord = gl_MultiTexCoord0.st;
	vec4 world = mc_view * gl_ModelViewMatrix * gl_Vertex;
	worldPos = world.xyz;
	gl_Position = gl_ProjectionMatrix * world;
	normal = (mat3(mc_view) * gl_NormalMatrix * gl_Normal).xyz;
	
	fragPosShadowSpace = shadow_proj * shadow_view * gl_ModelViewMatrix * gl_Vertex;
}