#version 120

uniform sampler2D image;

varying vec2 texCoord;
varying vec4 color;

void main(){
	vec4 tex = texture2D(image, vec2(texCoord.s, texCoord.t + sin(texCoord.s*40)*0.45));
	gl_FragColor = tex * color;
}