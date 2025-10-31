#version 120

varying vec2 blurTexCoords[11];
varying vec2 texCoord;
uniform float targetHeight;

void main(){
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	texCoord = gl_MultiTexCoord0.xy;
	float pixelSize = 1.0/targetHeight;
	
	for(int i = -5; i <=5; i++){
		blurTexCoords[i+5] = texCoord + vec2(0.0, pixelSize*i);
	}
}