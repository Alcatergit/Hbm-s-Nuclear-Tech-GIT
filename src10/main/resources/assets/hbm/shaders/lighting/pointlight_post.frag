#version 120

uniform vec2 windowSize;
uniform vec3 light_pos;
uniform vec3 cam_pos;
uniform float brightness;
uniform float radius;
uniform vec3 light_color;

uniform sampler2D mc_tex;
uniform sampler2D depthBuffer;
uniform mat4 inv_ViewProjectionMatrix;

//Unprojects from the depth buffer
//Project.gluUnProject is a good reference for this
vec3 worldPos(vec3 screenPos){
	vec4 ndcPos;
	ndcPos.xy = (2*screenPos.xy)/windowSize - 1;
	ndcPos.z = (2*screenPos.z - gl_DepthRange.near - gl_DepthRange.far)/(gl_DepthRange.far - gl_DepthRange.near);
	ndcPos.w = 1;
	
	vec4 worldPos = inv_ViewProjectionMatrix * ndcPos;
	return worldPos.xyz/worldPos.w;
}

//Really good and well explained article about normal reconstruction
//https://atyuwen.github.io/posts/normal-reconstruction/
//Has a ton of noise for some unknown reason, can't use.
vec3 generateNormal(vec3 world_pos){
	//vec2 coord1 = vec2(gl_FragCoord.x+1, gl_FragCoord.y);
	//vec3 worldpos1 = worldPos(vec3(coord1, texture2D(depthBuffer, coord1/windowSize)));
	//vec2 coord2 = vec2(gl_FragCoord.x, gl_FragCoord.y+1);
	//vec3 worldpos2 = worldPos(vec3(coord2, texture2D(depthBuffer, coord2/windowSize)));
	//vec3 norm = normalize(cross(worldpos1 - worldPos0, worldpos2 - worldPos0));
	
	vec2 texel = 1/windowSize;
	vec2 screenPos = gl_FragCoord.xy*texel;
	float depth = texture2D(depthBuffer, screenPos).x;
	
	vec4 horizontal;
	horizontal.x = texture2D(depthBuffer, screenPos - vec2(texel.x, 0)).x;
	horizontal.y = texture2D(depthBuffer, screenPos + vec2(texel.x, 0)).x;
	horizontal.z = texture2D(depthBuffer, screenPos - vec2(texel.x*2, 0)).x;
	horizontal.w = texture2D(depthBuffer, screenPos + vec2(texel.x*2, 0)).x;
	//Perspective correct linear extrapolation of depth, whichever samples most directly align with the current depth are used for the derivative
	vec2 hDist = abs((horizontal.xy * horizontal.zw)/(2*horizontal.zw - horizontal.xy) - depth);
	vec3 hDeriv;
	if(hDist.x < hDist.y){
		hDeriv = worldPos(vec3(gl_FragCoord.x - 1, gl_FragCoord.y, horizontal.x)) - worldPos(vec3(gl_FragCoord.x - 2, gl_FragCoord.y, horizontal.z));
	} else {
		hDeriv = worldPos(vec3(gl_FragCoord.x + 1, gl_FragCoord.y, horizontal.y)) - worldPos(vec3(gl_FragCoord.x + 2, gl_FragCoord.y, horizontal.w));
	}
	
	vec4 vertical;
	vertical.x = texture2D(depthBuffer, screenPos - vec2(0, texel.y)).x;
	vertical.y = texture2D(depthBuffer, screenPos + vec2(0, texel.y)).x;
	vertical.z = texture2D(depthBuffer, screenPos - vec2(0, texel.y*2)).x;
	vertical.w = texture2D(depthBuffer, screenPos + vec2(0, texel.y*2)).x;
	//Perspective correct linear extrapolation of depth, whichever samples most directly align with the current depth are used for the derivative
	vec2 vDist = abs((vertical.xy * vertical.zw)/(2*vertical.zw - vertical.xy) - depth);
	vec3 vDeriv;
	if(vDist.x < vDist.y){
		vDeriv = worldPos(vec3(gl_FragCoord.x, gl_FragCoord.y - 1, vertical.x)) - worldPos(vec3(gl_FragCoord.x, gl_FragCoord.y - 2, vertical.z));
	} else {
		vDeriv = worldPos(vec3(gl_FragCoord.x, gl_FragCoord.y + 1, vertical.y)) - worldPos(vec3(gl_FragCoord.x, gl_FragCoord.y + 2, vertical.w));
	}
	
	vec3 norm = normalize(cross(hDeriv, vDeriv));
	
	return dot(world_pos - cam_pos, norm) > 0 ? -norm : norm;
}

float pointLightFalloff(float radius, float dist){
	float distOverRad = dist/radius;
	float distOverRad2 = distOverRad*distOverRad;
	float distOverRad4 = distOverRad2*distOverRad2;
	
	float falloff = clamp(1-distOverRad4, 0, 1);
	return (falloff * falloff)/(dist*dist + 1);
}


void main(){
	vec2 screenPos = gl_FragCoord.xy/windowSize;
	float depth = texture2D(depthBuffer, screenPos).x;
	
	vec3 worldPos0 = worldPos(vec3(gl_FragCoord.xy, depth));
	vec3 normal = generateNormal(worldPos0);
	vec4 color = texture2D(mc_tex, screenPos);
	
	float dproduct = clamp(dot(normal, -normalize(worldPos0 - light_pos)), 0, 1);
	float distanceFade = pointLightFalloff(radius, length(worldPos0 - light_pos))*brightness;
	
	gl_FragColor = vec4(color.rgb*light_color*dproduct*distanceFade, 1);
}