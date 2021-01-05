#version 330 compatibility

uniform sampler2D texture;
uniform sampler2D noise;
uniform vec4 duck;
uniform float age;

in vec2 pass_tex;
in vec2 noise_tex;
in vec4 pass_color;
in float fade;

void main(){
	float noise = texture2D(noise, pass_tex * vec2(6, 0.25)).g;
	gl_FragColor = duck*vec4(step(fade, noise))*vec4(pass_color.rgb, step(0.5, pass_color.a));
}