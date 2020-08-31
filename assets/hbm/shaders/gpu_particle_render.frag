#version 330 core

in vec2 pass_tex;
in vec2 pass_lightmap;
in vec4 pass_color;

uniform sampler2D texture;
uniform sampler2D lightmap;

out vec4 FragColor;

void main(){
	vec4 tex = texture2D(texture, pass_tex);
	vec4 lmap = texture2D(lightmap, pass_lightmap);
	
	FragColor = tex * lmap * pass_color;
}