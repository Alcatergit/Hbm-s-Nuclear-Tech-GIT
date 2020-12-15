#version 330 compatibility

uniform sampler2D texture;
uniform sampler2D noise_1;
uniform float time;

in vec2 texCoord;

void main(){
	vec4 tex = texture2D(texture, texCoord);
	vec4 noise = texture2D(noise_1, vec2(texCoord.s*2-time*5, texCoord.t));
	vec4 tex2 = texture2D(texture, texCoord+(noise.rg-0.5)*0.02*int(tex.r < 0.2));
	gl_FragColor = tex2 * vec4(0.6, 0.85, 2, 1)*1.5;
}