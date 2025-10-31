#version 120

varying vec2 texCoord;
varying vec2 lightmapTexCoord;
varying vec3 lightSum;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	texCoord = (gl_TextureMatrix[0] * gl_MultiTexCoord0).xy;
	lightmapTexCoord = (gl_TextureMatrix[1] * gl_MultiTexCoord1).xy;
	gl_FrontColor = gl_Color;
	
	vec3 normal = (gl_NormalMatrix * gl_Normal).xyz;
	vec4 totalLight = vec4(0.0F);

	for (int i = 0; i < gl_MaxLights; i ++){
		
		vec4 diffuse = gl_FrontLightProduct[i].diffuse * max(dot(normal,gl_LightSource[i].position.xyz), 0.0f);
		diffuse = clamp(diffuse, 0.0F, 1.0F);     
		
		totalLight += diffuse + gl_FrontLightProduct[i].ambient;
	}
	lightSum = clamp((totalLight + gl_LightModel.ambient).rgb, 0.0F, 1.0F);
}