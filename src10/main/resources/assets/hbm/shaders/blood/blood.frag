#version 120

varying vec2 texCoord;
varying vec2 lightCoord;
varying vec4 color;

uniform sampler2D texture;
uniform sampler2D lightmap;

void main(){
	vec4 texture = texture2D(texture, texCoord);
	vec4 lmap = texture2D(lightmap, lightCoord);
	
	float edge = 1-color.a;
	edge = pow(max(edge, 0.01), 1.5);
	float a = clamp(smoothstep(edge, edge+0.1, texture.b), 0, 1);
	gl_FragColor = vec4((texture.bbb*0.5+0.5)*color.rgb, a * clamp(texture.b*3, 0, 1)) * lmap;
}