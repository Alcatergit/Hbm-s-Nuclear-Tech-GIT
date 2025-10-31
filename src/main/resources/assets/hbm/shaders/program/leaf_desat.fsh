#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform float Intensity;

void main(){
    vec4 diffuseColor = texture2D(DiffuseSampler, texCoord);
	float avg = (diffuseColor.r+diffuseColor.g+diffuseColor.b)/3.;
    vec4 desatColor = vec4(avg,avg,avg,diffuseColor.a);
    vec4 outColor = mix(diffuseColor, desatColor, Intensity);
    gl_FragColor = outColor;
}
