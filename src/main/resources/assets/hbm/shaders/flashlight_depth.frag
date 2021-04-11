#version 330 compatibility

uniform sampler2D tex;

in vec2 texCoord;
in vec4 position;

void main(){
	gl_FragColor = texture2D(tex, texCoord);
	//gl_FragDepth = (gl_FragCoord.z/gl_FragCoord.w)/28;
	//gl_FragDepth = gl_FragCoord.x/512;
}