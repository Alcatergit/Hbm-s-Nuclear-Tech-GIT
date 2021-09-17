#version 130

//const int radius = 1;

uniform float angle;
uniform vec3 pos;
uniform vec3 direction;
uniform float height;
uniform sampler2D flashlightTex;
uniform sampler2D shadowTex;
uniform sampler2D mc_tex;

in vec3 worldPos;
//Calculated in geometry shader because minecraft doesn't store normals in the block format.
in vec3 normal;
in vec3 color;
in vec4 fragPosShadowSpace;
in vec2 texture_coord;

//float calcShadow(vec3 proj_coord){
	//float texel = 1/1024;
	//float light = 0;
	//for(int i = -radius; i <= radius; i ++){
	//	for(int j = -radius; j <= radius; j ++){
	//		float shadow = texture2D(shadowTex, proj_coord.st + vec2(i, j)*texel).r;
	//		light += float(shadow + 0.00018 > proj_coord.p);
	//	}
	//}
	//float shadow1 = texture2D(shadowTex, proj_coord.st).r;
	//If the shadow + an arbitrary bias so fragments don't shadow themselves is less than the flashlight projected depth, it's in shadow, and we don't light it up.
	//if(shadow1 + 0.00018 <= proj_coord.p){
	//	return 0;
	//}
	//float area = radius*2 + 1;
	//area = area * area;
	//return 1;
//}

float calcShadow(vec3 proj_coord){
	float shadow = 0.0;
	vec2 texelSize = 1.0 / textureSize(shadowTex, 0);
	for(int x = -1; x <= 1; ++x)
	{
    	for(int y = -1; y <= 1; ++y)
    	{
        	float pcfDepth = texture(shadowTex, proj_coord.xy + vec2(x, y) * texelSize).r; 
        	shadow += proj_coord.z - 0.0001777 > pcfDepth ? 1.0 : 0.0;        
    	}    
	}
	shadow /= 9.0;
	return 1-shadow;
}

void main(){
	//Perspective divide and remap from -1 to 1 space to the texture coordinate 0 to 1 space
	vec3 proj_coord = fragPosShadowSpace.xyz/fragPosShadowSpace.w;
	proj_coord = proj_coord * 0.5 + 0.5;
	float shadow = calcShadow(proj_coord);
	proj_coord.p = shadow;
	
	gl_FragData[0] = texture2D(mc_tex, texture_coord);
	gl_FragData[1] = vec4(worldPos, 1);
	gl_FragData[2] = vec4(proj_coord, 1);
	gl_FragData[3] = vec4(normal, 1);
}