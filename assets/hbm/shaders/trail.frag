#version 330 compatibility

uniform sampler2D texture;

in vec2 pass_tex;
in vec4 pass_color;

void main(){
	gl_FragColor = texture2D(texture, pass_tex);
}