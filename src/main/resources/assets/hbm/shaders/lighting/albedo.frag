#version 330 compatibility

uniform sampler2D texture;

in vec2 texCoord;

void main(){
	gl_FragColor = texture2D(texture, texCoord) * gl_Color;
}