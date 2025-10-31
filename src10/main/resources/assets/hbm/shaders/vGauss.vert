#version 120

varying vec2 texCoord;
varying vec2 blurTexCoords[11];
uniform float targetWidth;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	texCoord = gl_MultiTexCoord0.xy;
	float pixelSize = 1.0/targetWidth;
	
	for(int i = -5; i <=5; i++){
		blurTexCoords[i+5] = texCoord + vec2(pixelSize*i, 0.0);
	}
}