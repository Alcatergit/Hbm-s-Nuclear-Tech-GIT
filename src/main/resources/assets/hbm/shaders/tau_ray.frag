#version 330 compatibility

uniform sampler2D image;

in vec2 texCoord;
in vec4 color;

void main(){
	vec4 tex = texture2D(image, vec2(texCoord.s, texCoord.t + sin(texCoord.s*40)*0.45));
	gl_FragColor = tex * color;
}