#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform float Intensity;
uniform float Exponent;
uniform float Brightness;

void main(){
    vec4 diffuseColor = texture2D(DiffuseSampler, texCoord);
    vec2 center = vec2(0.5,0.5);
    vec3 outCol = vec3(diffuseColor.rgb);
    int SampleCount = int(128.*Exponent);
    if (Intensity > 0.) {
        float SampleCountF = float(SampleCount);
        for (int i = 1; i <= SampleCount; i++) {
            float iF = float(i);
            vec2 pos = (texCoord-center)*pow(iF/SampleCountF,Exponent)+center;
            vec4 col = texture2D(DiffuseSampler,vec2(clamp(pos.x,0.,1.),clamp(pos.y,0.,1.)));
            outCol = outCol + vec3(col.rgb)*Intensity;
        }
        outCol = outCol/(SampleCountF*Intensity+1.)*((Brightness-1.)*Intensity+1.);
    }
    gl_FragColor = vec4(outCol,diffuseColor.a);
}
