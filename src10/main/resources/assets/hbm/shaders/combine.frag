#version 120

uniform sampler2D image;
uniform sampler2D mcImage;
varying vec2 texCoord;

void main(){
	gl_FragColor = texture2D(mcImage, texCoord) + texture2D(image, texCoord);
}