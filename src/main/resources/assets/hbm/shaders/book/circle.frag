#version 330 compatibility

uniform sampler2D texture;
uniform float angle;

in vec2 texCoord;

void main(){
	vec2 loc = normalize(texCoord*2 - 1);
	gl_FragColor = texture2D(texture, texCoord) * int(dot(vec2(0, 1), loc) <= angle);
}