#version 120

uniform sampler2D DiffuseSampler;

varying vec2 texCoord;

uniform vec2 InSize;
uniform float Size;

void main(){
	float ratio = InSize.y/InSize.x;
    vec4 diffuseColor = texture2D(DiffuseSampler, texCoord);

	float Increment = max(Size/10.,0.0001); // anti-freeze
	vec3 mixColor = vec3(0.0,0.0,0.0);
	float laid = 0.;
	for (float d = 0.; d <= Size; d+=Increment) {
		int divisions = int(floor(3.+Size*64.));//int(floor(pow(d*2.,0.75)*(128.*pow(Size,0.9))))+1;
		for (int i = 0; i < divisions; i++) {
			float theta = 6.28318*(float(i)/float(divisions));
			vec2 surroundingCoord = texCoord+vec2(cos(theta)*d*ratio,sin(theta)*d);
			if (surroundingCoord.x < 0.) continue;
			if (surroundingCoord.x > 1.) continue;
			if (surroundingCoord.y < 0.) continue;
			if (surroundingCoord.y > 1.) continue;
			vec4 surroundingColor = texture2D(DiffuseSampler,surroundingCoord);
			laid++;
			mixColor += vec3(surroundingColor.rgb);
		}
	}

    gl_FragColor = vec4(mixColor.r/laid,mixColor.g/laid,mixColor.b/laid,diffuseColor.a);
}
