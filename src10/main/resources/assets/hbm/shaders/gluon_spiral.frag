#version 120

uniform sampler2D texture;

varying vec2 texCoord;

void main(){
	vec4 tex = texture2D(texture, texCoord);
	gl_FragColor = tex * vec4(0.6, 0.8, 1, 1)*1.5;
}