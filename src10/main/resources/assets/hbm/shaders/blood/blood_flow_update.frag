#version 120

const vec2[] offsets = vec2[](vec2(1, 0), vec2(-1, 0), vec2(0, 1), vec2(0, -1));

uniform float cutoff;
uniform sampler2D gravmap;
uniform vec2 size;

void main(){
	vec2 fragPos = gl_FragCoord.xy/size;
	vec3 currentTexel = texture2D(gravmap, fragPos).xyz;
	vec2 grav = currentTexel.xy*2 - 1;
	float cutScale = max(currentTexel.z-cutoff, 0)/(1-cutoff);
	float val = cutScale * cutScale * int(currentTexel.z > cutoff);
	float amountLost = abs(grav.x)*val + abs(grav.y)*val;
	amountLost = max(amountLost - max(-((currentTexel.z-amountLost)-cutoff), 0), 0);
	float maxToAdd = max(0.9-(currentTexel.z-amountLost), 0);
	
	float amountToAdd = 0;
	for(int i = 0; i < 4; i ++){
		vec2 offPos = fragPos + offsets[i]/size;
		vec3 sample = texture2D(gravmap, offPos).xyz;
		vec2 sampleGrav = sample.xy*2 - 1;
		float cutScaleSample = max(sample.z-cutoff, 0)/(1-cutoff);
		float amountLostSample = cutScaleSample * cutScaleSample * int(sample.z > cutoff);
		//amountLostSample = max(amountLostSample - max(-((sample.z-amountLostSample)-cutoff), 0), 0);
		float maxToAddSample = max(0.9-(sample.z-amountLostSample), 0);
		amountToAdd = amountToAdd + max(dot(-sampleGrav, offsets[i]), 0) * amountLostSample;
	}
	amountToAdd = min(amountToAdd, maxToAdd);
	gl_FragColor = vec4(currentTexel.xy, clamp((currentTexel.z-amountLost)+amountToAdd, 0, 1), 1);
	//gl_FragColor = vec4(currentTexel, 1);
}