#version 120

uniform sampler2D tex_mask;
uniform sampler2D noise;
uniform sampler2D fbo_tex;
uniform float amount;
uniform float time;
uniform vec2 windowSize;

varying vec2 texCoord;

void main(){
	vec2 screen_uv = gl_FragCoord.xy/windowSize;
	vec4 mask = texture2D(tex_mask, texCoord);
	vec2 offset = texture2D(noise, texCoord*0.5+time*0.2).rg*2-1;
	offset *= texture2D(noise, texCoord*0.3-time*0.15).gb*2-1;
	offset = offset * amount * smoothstep(0, 1, mask.a) * 0.1;
	gl_FragColor = texture2D(fbo_tex, screen_uv + offset);
}