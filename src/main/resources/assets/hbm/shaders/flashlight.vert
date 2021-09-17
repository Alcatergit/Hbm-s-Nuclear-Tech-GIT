#version 130

uniform mat4 shadow_viewproj;
uniform mat4 mc_view;

out vec3 worldPosV;
out vec3 colorV;
out vec4 fragPosShadowSpaceV;
out vec2 texture_coordV;

void main(){
	vec3 pos = gl_Vertex.xyz;
	colorV = gl_Color.rgb;
	texture_coordV = gl_MultiTexCoord0.st;
	vec4 world = mc_view * gl_ModelViewMatrix * gl_Vertex;
	worldPosV = world.xyz;
	gl_Position = gl_ProjectionMatrix * world;
	
	fragPosShadowSpaceV = shadow_viewproj * gl_ModelViewMatrix * gl_Vertex;
}