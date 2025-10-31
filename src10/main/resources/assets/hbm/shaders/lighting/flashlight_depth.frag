#version 120

uniform sampler2D tex;

varying vec2 texCoord;
varying vec4 position;

void main(){
	gl_FragColor = texture2D(tex, texCoord);
	//gl_FragDepth = (gl_FragCoord.z/gl_FragCoord.w)/28;
	//gl_FragDepth = gl_FragCoord.x/512;
}