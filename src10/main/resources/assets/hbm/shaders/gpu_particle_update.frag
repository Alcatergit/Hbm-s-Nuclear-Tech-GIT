#version 120
#extension GL_EXT_gpu_shader4 : enable

//Contains position and color
uniform sampler2D particleData0;
//Contains velocity and scale
uniform sampler2D particleData1;
//Contains age, max age, and particle id
uniform sampler2D particleData2;

varying vec2 texCoord;

vec4 colorFromFloat(float f){
	int argb = int(f*2147483647);
	float a = (argb >> 24) & 0xFF;
	float r = (argb >> 16) & 0xFF;
	float g = (argb >> 8) & 0xFF;
	float b = (argb) & 0xFF;
	return vec4(r, g, b, a);
}

float floatFromColor(vec4 color){
	int r = int(color.r*255);
	int g = int(color.g*255);
	int b = int(color.b*255);
	int a = int(color.a*255);
	int argb = (a << 24) | (r << 16) | (g << 8) | (b);
	return argb/2147483647;
}

void main(){
	vec4 data0 = texture2D(particleData0, texCoord);
	vec4 data1 = texture2D(particleData0, texCoord);
	vec4 data2 = texture2D(particleData0, texCoord);
	
	int age = int(data2.x*32767);
	int maxAge = int(data2.y*32767);
	int particleType = int(data2.z*32767);
	vec4 color = colorFromFloat(data0.w);
	
	if(particleType == 0){
		float alpha = 1-(age/maxAge);
		color.a = alpha;
	}
	
	age = (age+1)*int(age != maxAge)+(-1)*int(age == maxAge);
	data2.x = age/32767;
	data0.w = floatFromColor(color);
	
	gl_FragData[0] = data0;
	gl_FragData[1] = data1;
	gl_FragData[2] = data2;
}