#version 120

varying vec2 texCoord;

struct Flashlight {
	vec3 pos;
	vec3 dir;
	float angle;
};

uniform Flashlight lights[20];
uniform int lightCount;

uniform sampler2D colorTexture;
uniform sampler2D positionTexture;
uniform sampler2D normalTexture;

void main(){
	vec3 fragColor = texture2D(colorTexture, texCoord).rgb;
	vec3 fragPos = texture2D(positionTexture, texCoord).rgb;
	vec3 fragNormal = texture2D(normalTexture, texCoord).rgb;
	
	gl_FragColor = vec4(fragColor, 1.0F);
}