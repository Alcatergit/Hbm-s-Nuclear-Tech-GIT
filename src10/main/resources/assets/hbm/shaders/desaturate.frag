#version 120

uniform sampler2D tex;
varying vec2 texCoord;

void main(){
	vec4 color = texture2D(tex, texCoord);
	float desat = 0.2126*color.r + 0.7152*color.g + 0.0722*color.b;
	gl_FragColor = vec4(desat, desat, desat, 1);
}