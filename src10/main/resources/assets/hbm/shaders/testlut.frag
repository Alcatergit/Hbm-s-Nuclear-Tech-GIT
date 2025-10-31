#version 120

uniform sampler2D tex0;
uniform sampler2D tempTest;

varying vec2 texCoord;

void main(){
	vec4 colorIn = texture2D(tex0, texCoord);
	vec3 colorR = colorIn.rgb;
	int index = int(colorR.b*63);
	float v = colorR.g*0.125+((index/8)*0.125);
	float u = colorR.r*0.125+mod(index, 8)*0.125;
	
	vec4 outColor = texture2D(tempTest, vec2(u, v)); 
	gl_FragColor = vec4(outColor.rgb, 1.0);  
}