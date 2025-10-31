#version 120

varying vec2 texCoord;
varying vec2 lightCoord;
varying vec2 projCoord;
varying vec3 worldNormal;
varying vec3 worldTangent;
varying vec4 color;
varying vec3 lighting;

uniform sampler2D blockTex;
uniform sampler2D lightmap;
uniform sampler2D normalMap;
uniform sampler2D occlusionMap;
uniform vec2 windowSize;

mat3 generateTBN(vec3 N, vec3 T){
	vec3 B = cross(N, T);
	return mat3(T, B, N);
}

void main(){
	mat3 TBN = generateTBN(worldNormal, worldTangent);
	vec3 mappedNormal = TBN * normalize(texture2D(normalMap, projCoord).xyz*2.0-1.0);
	float occlusion = texture2D(occlusionMap, projCoord).r;
	
	vec4 lmap = texture2D(lightmap, vec2(lightCoord.y, lightCoord.x));
	
	vec4 difftot = vec4(0.0F);
	
	vec3 totalLighting = vec3(gl_LightModel.ambient) * vec3(gl_FrontMaterial.emission);
	for (int i = 0; i < gl_MaxLights; i ++){
			
		vec4 diff = gl_FrontLightProduct[i].diffuse * max(dot(mappedNormal,gl_LightSource[i].position.xyz), 0.0f);
		diff = clamp(diff, 0.0F, 1.0F);     
			
		difftot += diff;
	}
	vec3 lighting2 = clamp((difftot + gl_LightModel.ambient).rgb, 0.0F, 1.0F);
	
	vec4 col = color * texture2D(blockTex, texCoord) * lmap;
	float a = clamp((1-occlusion)*10, 0, 1);
	gl_FragColor = vec4(col.rgb*occlusion*lighting2, col.a*a);
}