#version 330 compatibility

const ivec2[] offsets = ivec2[](ivec2(1, 0), ivec2(-1, 0), ivec2(0, 1), ivec2(0, -1));

uniform float cutoff;
uniform sampler2D gravmap;

void main(){
	ivec2 fragPos = ivec2(gl_FragCoord.xy);
	vec3 currentTexel = texelFetch(gravmap, fragPos, 0).xyz;
	vec2 grav = currentTexel.xy*2 - 1;
	float cutScale = max(currentTexel.z-cutoff, 0)/(1-cutoff);
	float val = cutScale * cutScale * int(currentTexel.z > cutoff);
	float amountLost = abs(grav.x)*val + abs(grav.y)*val;
	amountLost = max(amountLost - max(-((currentTexel.z-amountLost)-cutoff), 0), 0);
	float maxToAdd = max(0.9-(currentTexel.z-amountLost), 0);
	
	float amountToAdd = 0;
	for(int i = 0; i < 4; i ++){
		ivec2 offPos = fragPos + offsets[i];
		vec3 sample = texelFetch(gravmap, offPos, 0).xyz;
		vec2 sampleGrav = sample.xy*2 - 1;
		float cutScaleSample = max(sample.z-cutoff, 0)/(1-cutoff);
		float amountLostSample = cutScaleSample * cutScaleSample * int(sample.z > cutoff);
		//amountLostSample = max(amountLostSample - max(-((sample.z-amountLostSample)-cutoff), 0), 0);
		float maxToAddSample = max(0.9-(sample.z-amountLostSample), 0);
		amountToAdd = amountToAdd + max(dot(-sampleGrav, offsets[i]), 0) * amountLostSample;
	}
	amountToAdd = min(amountToAdd, maxToAdd);
	gl_FragColor = vec4(currentTexel.xy, clamp((currentTexel.z-amountLost)+amountToAdd, 0, 1), 1);
}