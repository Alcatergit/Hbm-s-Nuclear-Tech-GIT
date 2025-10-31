#version 120

uniform sampler2D image;
varying vec2 texCoord;
varying vec2 blurTexCoords[11];

void main(){
	vec4 sum = vec4(0.0);
	
	sum += texture2D(image, blurTexCoords[0])*0.0093;
	sum += texture2D(image, blurTexCoords[1])*0.028002;
	sum += texture2D(image, blurTexCoords[2])*0.065984;
	sum += texture2D(image, blurTexCoords[3])*0.121703;
	sum += texture2D(image, blurTexCoords[4])*0.175713;

	sum += texture2D(image, blurTexCoords[5])*0.198596;
	
	sum += texture2D(image, blurTexCoords[6])*0.175713;
	sum += texture2D(image, blurTexCoords[7])*0.121703;
	sum += texture2D(image, blurTexCoords[8])*0.065984;
	sum += texture2D(image, blurTexCoords[9])*0.028002;
	sum += texture2D(image, blurTexCoords[10])*0.0093;
	
	gl_FragColor = sum;
}