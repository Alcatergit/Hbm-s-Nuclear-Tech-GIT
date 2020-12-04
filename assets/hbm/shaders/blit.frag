#version 330 compatibility

uniform sampler2D tex;

in vec2 tex_coord;

void main(){
	gl_FragColor = texture2D(tex, tex_coord);
}