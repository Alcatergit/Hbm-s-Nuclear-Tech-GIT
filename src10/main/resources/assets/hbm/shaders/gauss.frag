#version 120

varying vec2 texCoordOut;

uniform sampler2D image;
uniform float worldTime;

void main(){
	float duck = sin(worldTime/4)/2 + 0.5;
	duck = duck/2;
	gl_FragColor = texture2D(image, texCoordOut) + duck;
}