#version 120

varying vec3 worldPos;
varying vec3 lightSum;
varying vec3 fragNormal;

uniform ivec3 chunkPos;
uniform int lightingEnabled;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	gl_TexCoord[0] = gl_TextureMatrix[0] * gl_MultiTexCoord0;
	gl_TexCoord[1] = gl_TextureMatrix[1] * gl_MultiTexCoord1;
	gl_FrontColor = gl_Color;
	worldPos = (gl_ModelViewMatrix * gl_Vertex).xyz;
	
	if(lightingEnabled == 1){
		vec3 totalLighting = vec3(gl_LightModel.ambient) * vec3(gl_FrontMaterial.emission);
		vec3 normal = (gl_NormalMatrix * gl_Normal).xyz;
		fragNormal = normal;
		vec4 difftot = vec4(0.0F);
	
		for (int i = 0; i < gl_MaxLights; i ++){
			
			vec4 diff = gl_FrontLightProduct[i].diffuse * max(dot(normal,gl_LightSource[i].position.xyz), 0.0f);
			diff = clamp(diff, 0.0F, 1.0F);     
			
			difftot += diff;
		}
		lightSum = clamp((difftot + gl_LightModel.ambient).rgb ,0.0F, 1.0F);
	} else {
		lightSum = vec3(1.0F);
		fragNormal = vec3(0.0F, 0.0F, 0.0F);
	}
	
}
