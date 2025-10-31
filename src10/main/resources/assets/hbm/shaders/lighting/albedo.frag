#version 120

uniform sampler2D texture;

varying vec2 texCoord;

void main(){
	gl_FragColor = texture2D(texture, texCoord) * gl_Color;
}