#version 120

const float G_SCATTERING = 0.5;
const float PI = 3.1415926535897932384626433832795;
const float PI_RCP = 0.31830988618379067153776752674503;
const float TAU = 0.0001;
const float PHI = 100000;
const float NUM_STEPS = 20;

const float ditherPattern[16] = float[](0.0f, 0.5f, 0.125f, 0.625f, 0.75f, 0.22f, 0.875f, 0.375f, 0.1875f, 0.6875f, 0.0625f, 0.5625, 0.9375f, 0.4375f, 0.8125f, 0.3125);

uniform sampler2D tex;
uniform sampler2D shadow;
uniform sampler2D depth;

uniform vec2 windowSize;
uniform vec3 pos;
uniform vec3 direction;
uniform float height;
uniform float radius;
uniform float cosAngle;
uniform vec3 camPos;

uniform bool useShadows;

uniform mat4 inv_ViewProjectionMatrix;
uniform mat4 flashlight_ViewProjectionMatrix;

struct intersection
{
float t1;
float t2;
float hitType;
};

struct hit
{
vec3 pos1;
vec3 pos2;
float hitType;
};

//Unprojects from the depth buffer
//Project.gluUnProject is a good reference for this
vec3 worldPos(vec3 screenPos){
	vec4 ndcPos;
	ndcPos.xy = (2*screenPos.xy)/(windowSize*0.5) - 1;
	ndcPos.z = (2*screenPos.z - gl_DepthRange.near - gl_DepthRange.far)/(gl_DepthRange.far - gl_DepthRange.near);
	ndcPos.w = 1;
	
	vec4 worldPos = inv_ViewProjectionMatrix * ndcPos;
	return worldPos.xyz/worldPos.w;
}

vec3 quadf(float a, float b, float c){
	vec3 result;
	float discrim = b*b-4*a*c;
	if(discrim < 0){
		result.z = -1;
		return result;
	} else if(discrim == 0){
		result.z = 0;
	} else {
		result.z = 1;
	}
	discrim = sqrt(discrim);
	float rcp2a = 1/(2*a);
	result.x = (-b+discrim)*rcp2a;
	result.y = (-b-discrim)*rcp2a;
	return result;
}

intersection intersectInfMirroredCone(vec3 rayPos, vec3 rayDir, vec3 conePos, vec3 coneDir, float cosa){
	vec3 co = rayPos - conePos;

    float a = dot(rayDir,coneDir)*dot(rayDir,coneDir) - cosa*cosa;
    float b = 2. * (dot(rayDir,coneDir)*dot(co,coneDir) - dot(rayDir,co)*cosa*cosa);
    float c = dot(co,coneDir)*dot(co,coneDir) - dot(co,co)*cosa*cosa;

	float didHit = -1;

    float det = b*b - 4.*a*c;
    if (det > 0){
    	didHit = 1;
    } else if(det == 0){
    	didHit = 0;
    }

    det = sqrt(det);
    float t1 = (-b - det) / (2. * a);
    float t2 = (-b + det) / (2. * a);

    return intersection(t1, t2, didHit);
}

//Derived as a general quadratic from lenSq(rayPos + t*RayDir - spherePos) - radius^2 == 0
intersection raycastSphere(vec3 rayPos, vec3 rayDir, vec3 spherePos, float radius){
	vec3 sphereToRay = rayPos-spherePos;
	float a = dot(rayDir, rayDir);
	float b = 2*dot(sphereToRay, rayDir);
	float c = dot(sphereToRay, sphereToRay) - radius*radius;
	vec3 quad = quadf(a, b, c);
	return intersection(quad.x, quad.y, quad.z);
}

//https://lousodrome.net/blog/light/2017/01/03/intersection-of-a-ray-and-a-cone/
//http://www.skipifzero.com/posts/2016-02-02-volumetric-lighting-2.html
hit intersectSpotlight(vec3 rayPos, vec3 rayDir, vec3 conePos, vec3 coneDir, float cosa, float coneHeight){
	intersection coneTest = intersectInfMirroredCone(rayPos, rayDir, conePos, coneDir, cosa);
	if(coneTest.hitType < 0){
		return hit(vec3(0), vec3(0), -1);
	}
	intersection sphereTest = raycastSphere(rayPos, rayDir, conePos, coneHeight);
	
	//Vectors of each intersection to the cone, used for testing if each point is actually valid.
	vec3 c1 = rayPos + rayDir*coneTest.t1 - conePos;
	vec3 c2 = rayPos + rayDir*coneTest.t2 - conePos;
	vec3 s1 = rayPos + rayDir*sphereTest.t1 - conePos;
	vec3 s2 = rayPos + rayDir*sphereTest.t2 - conePos;
	
	float hits[4];
	int index = 0;
	if(dot(c1, coneDir) > 0 && length(c1) < coneHeight){
		hits[index] = coneTest.t1;
		index ++;
	}
	if(dot(c2, coneDir) > 0 && length(c2) < coneHeight){
		hits[index] = coneTest.t2;
		index ++;
	}
	if(dot(normalize(s1), coneDir) > cosa){
		hits[index] = sphereTest.t1;
		index ++;
	}
	if(dot(normalize(s2), coneDir) > cosa){
		hits[index] = sphereTest.t2;
		index ++;
	}
	float close = min(hits[0], hits[1]);
	float far = max(hits[0], hits[1]);
	
	float didHit = -1;
	if(index == 2 && far >= 0){
		didHit = 1;
	}
	return hit(rayPos + max(0, close)*rayDir, rayPos + max(0, far)*rayDir, didHit);
}

float computeScattering(vec3 currentPos, float lightDotView, float stepSize){
	float result = 1 - G_SCATTERING*G_SCATTERING;
	float divisor = (4.0 * PI * pow(1.0 + G_SCATTERING*G_SCATTERING - 2*G_SCATTERING * lightDotView, 1.5));
	return result/divisor;
	//float d = length(currentPos-pos);
	//float dRcp = 1/d;
	
	//return TAU*((PHI*0.25*PI_RCP)*dRcp*dRcp) * exp(-d*TAU) * exp(-1*TAU) * stepSize;
}

float pointLightFalloff(float radius, float dist){
	float distOverRad = dist/radius;
	float distOverRad2 = distOverRad*distOverRad;
	float distOverRad4 = distOverRad2*distOverRad2;
	
	float falloff = clamp(1-distOverRad4, 0, 1);
	return (falloff * falloff)/(dist*dist + 1);
}


void main(){
	float offset = ditherPattern[int(mod(gl_FragCoord.x, 4))*4 + int(mod(gl_FragCoord.y, 4))];
	vec2 uv = gl_FragCoord.xy/(windowSize*0.5);
	float depthSample = texture2D(depth, uv).r;
	vec3 world = worldPos(vec3(gl_FragCoord.xy, depthSample)) - camPos;
	float lenWorld = length(world);
	vec3 viewDirection = world/lenWorld;
	vec3 lightPos = pos - camPos;
	hit intersect = intersectSpotlight(vec3(0), viewDirection, lightPos, direction, cosAngle, height);
	//vec3[2] intersect = intersectCone(vec3(0), viewDirection, pos, vec3(0, 1, 0), cos(20*0.0174533), 10);
	if(intersect.hitType >= 0 && length(intersect.pos1) < lenWorld){
		if(length(intersect.pos2) > lenWorld){
			intersect.pos2 = world;
		}
		vec3 ray = intersect.pos2-intersect.pos1;
		float len = length(ray);
		vec3 rayDir = ray/len;
		
		vec4 texPos1 = flashlight_ViewProjectionMatrix * vec4(intersect.pos1 + camPos, 1);
		vec4 texPos2 = flashlight_ViewProjectionMatrix * vec4(intersect.pos2 + camPos, 1);
		texPos1 = flashlight_ViewProjectionMatrix * vec4(intersect.pos1 + camPos, 1);
		texPos2 = flashlight_ViewProjectionMatrix * vec4(intersect.pos2 + camPos, 1);
		
		float stepSize = len/NUM_STEPS;
		vec3 currentPos = intersect.pos1;
		vec3 stepVec = rayDir*stepSize;
		currentPos += stepVec*offset;
		vec3 accum = vec3(0);
		for(int i = 0; i < NUM_STEPS; i ++){
			vec3 posRelLight = currentPos-lightPos;
			float distfalloff = pointLightFalloff(height, length(posRelLight));
			float distToLine = length(posRelLight - direction * dot(direction, posRelLight));
			distfalloff *= pointLightFalloff(radius, distToLine);
			vec3 scattering = vec3(computeScattering(currentPos, -dot(direction, rayDir), stepSize));
			vec4 currentTexPos = mix(texPos1, texPos2, float(i)/NUM_STEPS);
			vec3 fs_texCoord = ((currentTexPos.xyz/currentTexPos.w)*0.5 + 0.5);
			scattering = 
			scattering 
			* distfalloff
			* texture2D(tex, fs_texCoord.xy).r
			;
			if(useShadows){
				scattering = scattering * float(fs_texCoord.z- 0.0001777 < texture2D(shadow, fs_texCoord.xy).r);
			}
			accum += scattering;
			currentPos += stepVec;
		}
	
		gl_FragColor = max(vec4(accum*2, 1), vec4(0));
		//float dist = length(intersect.pos1 - intersect.pos2)*0.1;
		//float dist2 = (30-length(intersect.pos1-pos))/30;
		//gl_FragColor = vec4(dist, dist, dist, 1);
	} else {
		gl_FragColor = vec4(0);
	}
	//gl_FragColor = vec4(world, 1);
	//vec2 uv = gl_FragCoord.xy/windowSize;
	//float depthSample = texture2D(depth, uv).r;
	//vec3 world = worldPos(vec3(gl_FragCoord.xy, depthSample));
	//gl_FragColor = vec4(normalize(world), 1);
}