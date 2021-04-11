#version 330 compatibility

uniform sampler2D tex_mask;
uniform float amount;

in vec2 texCoord;

void main(){
	vec4 mask = texture2D(tex_mask, texCoord);
	gl_FragColor = vec4(amount*smoothstep(0, 1, mask.a), 0, 0, 1);
}