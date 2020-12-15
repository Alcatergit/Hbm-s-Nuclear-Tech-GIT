#version 330 compatibility

uniform vec4 color;
uniform float fadeout_mult;

in vec3 frag_normal;
in vec3 frag_pos;

void main(){
	float fade = clamp(dot(-normalize(frag_normal), normalize(frag_pos))*fadeout_mult, 0, 1);
	gl_FragColor = vec4(color.rgb, color.a*fade);
	
}