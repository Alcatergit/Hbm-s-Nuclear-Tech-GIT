#version 120

attribute vec3 pos;
attribute vec4 vColor;
attribute vec2 tex;
attribute vec2 lightTex;
attribute vec2 projTex;

varying vec2 texCoord;
varying vec2 lightCoord;
varying vec2 projCoord;
varying vec3 worldNormal;
varying vec3 worldTangent;
varying vec4 color;
varying vec3 lighting;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 1);
	texCoord = (gl_TextureMatrix[0] * vec4(tex, 0, 1)).st;
	lightCoord = (gl_TextureMatrix[1] * vec4(lightTex*255, 0, 1)).st;
	projCoord = projTex;
	color = vColor;
	
	vec3 totalLighting = vec3(gl_LightModel.ambient) * vec3(gl_FrontMaterial.emission);
	vec3 normal = (gl_NormalMatrix * vec3(0, 1, 0)).xyz;
	worldNormal = normal;
	worldTangent = (gl_NormalMatrix * vec3(1, 0, 0)).xyz;
	vec4 difftot = vec4(0.0F);
	
	for (int i = 0; i < gl_MaxLights; i ++){
			
		vec4 diff = gl_FrontLightProduct[i].diffuse * max(dot(normal,gl_LightSource[i].position.xyz), 0.0f);
		diff = clamp(diff, 0.0F, 1.0F);     
			
		difftot += diff;
	}
	lighting = clamp((difftot + gl_LightModel.ambient).rgb, 0.0F, 1.0F);
	//color = vColor;
	//texCoord = tex;
	//lightCoord = lightTex;
	//projCoord = projTex;
	
	//worldTangent = gl_NormalMatrix * vec3(1, 0, 0);
	//worldNormal = gl_NormalMatrix * vec3(0, 1, 0);
	//gl_Position = gl_ModelViewProjectionMatrix * vec4(pos, 1);
}