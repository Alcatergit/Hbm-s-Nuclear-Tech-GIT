#version 120

varying vec3 worldPos;
varying vec3 lightSum;
varying vec3 fragNormal;

struct Flashlight {
	vec3 pos;
	vec3 dir;
	float angle;
};

uniform Flashlight lights[20];
uniform int lightCount;
uniform vec4 colorMult;

uniform sampler2D tex;
uniform sampler2D lightmap;
uniform sampler2D flashlightDepth;

void main(){
	vec4 color0 = texture2D(lightmap, gl_TexCoord[1].st);
	
	vec4 color1 = texture2D(tex, gl_TexCoord[0].st);
	
	vec4 color3 = gl_Color * color1;
	if (color3.w == 1 && !(color3.x == 0 && color3.y == 0 && color3.z == 0)){
		color3 = vec4(mix(color3.xyz,colorMult.xyz,colorMult.w),color3.w);
	}
	
	color3 = color3 * color0;
	
	float extraLight = 1;
	for(int i = 0; i < lightCount; i ++){
		Flashlight flash = lights[i];
		vec3 normal = normalize(flash.dir);
		vec3 pointNormal = normalize(worldPos - flash.pos);
		if(1.0 - dot(normal, pointNormal) < flash.angle){
			extraLight += 0.5;
		}
	}
	
	
	
	//gl_FragColor = vec4(color3.rgb * lightSum * extraLight, color3.a);
	gl_FragData[0] = vec4(color3.rgb * lightSum * extraLight, color3.a);
	gl_FragData[1] = worldPos;
	gl_FragData[2] = fragNormal;
}