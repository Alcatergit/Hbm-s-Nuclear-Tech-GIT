#version 120

uniform vec4 color;
uniform float fadeout_mult;

varying vec3 frag_normal;
varying vec3 frag_pos;

void main(){
	float fade = clamp(dot(-normalize(frag_normal), normalize(frag_pos))*fadeout_mult, 0, 1);
	gl_FragColor = vec4(color.rgb, color.a*fade);
}