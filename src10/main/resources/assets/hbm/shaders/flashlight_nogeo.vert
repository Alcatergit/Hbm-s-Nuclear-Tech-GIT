#version 120

uniform mat4 shadow_view;
uniform mat4 shadow_proj;
uniform mat4 mc_view;

varying vec3 worldPos;
varying vec3 color;
varying vec4 fragPosShadowSpace;
varying vec2 texture_coord;
varying vec3 normal;

void main(){
	vec3 pos = gl_Vertex.xyz;
	color = gl_Color.rgb;
	texture_coord = gl_MultiTexCoord0.st;
	vec4 world = mc_view * gl_ModelViewMatrix * gl_Vertex;
	worldPos = world.xyz;
	gl_Position = gl_ProjectionMatrix * world;
	normal = (mat3(mc_view) * gl_NormalMatrix * gl_Normal).xyz;
	
	fragPosShadowSpace = shadow_proj * shadow_view * gl_ModelViewMatrix * gl_Vertex;
}