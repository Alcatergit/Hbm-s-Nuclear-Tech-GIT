#version 120

uniform sampler2D tex;
uniform sampler2D depthTex;
uniform vec2 windowSize;
uniform vec2 zNearFar;

float linearizeDepth(float depth){
	float z_n = 2.0*depth-1.0;
	float linear = 2*zNearFar.x*zNearFar.y/(zNearFar.y + zNearFar.x - z_n*(zNearFar.y - zNearFar.x));
	return linear/zNearFar.y;
}

void main(){
	vec2 texelSize = 1/windowSize;
	vec2 downTexelSize = texelSize*0.5;
	vec2 texCoord = gl_FragCoord.xy*texelSize;
	float depth = texture2D(depthTex, texCoord).r;
	
	int xOffset = int(mod(gl_FragCoord.x, 2)) == 0 ? -1 : 1;
	int yOffset = int(mod(gl_FragCoord.y, 2)) == 0 ? -1 : 1;
	ivec2[] offsets = ivec2[](ivec2(0, 0), ivec2(0, yOffset), ivec2(xOffset, 0), ivec2(xOffset, yOffset));
	
	vec3 totalColor = vec3(0);
	float totalWeight = 0;
	for(int i = 0; i < 4; i ++){
		vec3 color = texture2D(tex, texCoord + offsets[i]*downTexelSize).rgb;
		float currentDepth = texture2D(depthTex, texCoord + offsets[i]*downTexelSize).r;
		
		float weight = max(0, 1 - 100*abs(currentDepth-depth));
		totalColor += color*weight;
		totalWeight += weight;
	}
	gl_FragColor = vec4(totalColor/(totalWeight+0.0001), 1);
}