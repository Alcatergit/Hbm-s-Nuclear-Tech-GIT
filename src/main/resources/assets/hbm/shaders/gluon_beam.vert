#version 120

//The noise is technically three noise textures in 1, so I can just sample it differently instead of sending a whole bunch of textures.
uniform sampler2D noise_1;
uniform sampler2D noise_2;
uniform float time;
//The space between each vertex on the x axis. Used to calculate where other vertices would be so I don't need a tessellation shader or something.
//uniform float subdivXAmount;
//uniform float subdivUAmount;
//uniform vec3 playerPos;

varying vec2 texCoord;

vec4 getPos(float x, vec2 tex){
	//return vec4(x, sin(tex.s*15)*3, 0, gl_Vertex.w);
	vec2 texCoord1 = vec2(x/16.0-time*0.7, 0.5-time*0.4);
	vec2 texCoord1_1 = vec2(x/16.0-time*0.7, 0.9-time*0.05);
	vec2 texCoord2 = vec2(x/12.0-time*0.8, 0.2-time*0.6);
	vec2 texCoord3 = vec2(tex.s/8.0-time*0.05, 0.2-time*0.3);
	vec4 sample_1 = (texture2DLod(noise_1, texCoord1, 0)*2-1)*clamp(pow(texture2DLod(noise_1, texCoord1_1, 0).b, 8)*12, 0, 0.4);
	vec4 sample_2 = texture2DLod(noise_1, texCoord2, 0);
	vec4 sample_3 = (smoothstep(0, 1, texture2DLod(noise_1, texCoord3, 0))-0.5)*0.4;
	sample_1 += (sample_2*2-1)*0.4*pow(sample_2.g, 4);
	sample_1 *= clamp(tex.x*20, 0, 1) * clamp(20-tex.x*20, 0, 1);
	//sample_1.rg += sample_3.bg * clamp(smoothstep(-0.2, 1, tex.s*2.5), -0.2, 1) * clamp(smoothstep(-0.2, 1, (1-tex.s)*2.5), -0.2, 1);
	sample_1.rg += sample_3.bg * clamp(pow(tex.s*2, 0.25), 0, 1) * clamp(pow((1-tex.s)*2, 0.25), 0, 1);
	
	return vec4(x, sample_1.r, sample_1.g, gl_Vertex.w);
}

void main(){
	texCoord = gl_MultiTexCoord0.st;
	//GPU billboarding. Should hopefully be faster than doing it on the CPU for large numbers of vertices.
	//Alright that didn't work as well as I had hoped.
	//vec3 pos = getPos(gl_Vertex.x, vec2(texCoord.s, texCoord.t)).xyz;
	//vec3 pos2 = getPos(gl_Vertex.x+subdivXAmount, vec2(texCoord.s+subdivUAmount, texCoord.t)).xyz;
	//vec3 pos3 = getPos(gl_Vertex.x-subdivXAmount, vec2(texCoord.s-subdivUAmount, texCoord.t)).xyz;
	//vec3 mid = (pos2-pos)+(pos3-pos);
	//vec3 midOut = cross(pos2-pos,pos3-pos);
	//vec3 across = cross(mid, midOut);
	//int hasDifferentDirection = int(across.x + across.y + across.z > 0.0001 || across.x + across.y + across.z < -0.0001);
	//across = (1-hasDifferentDirection)*(pos3-pos)+hasDifferentDirection*across;
	//across = pos-pos2;
	//vec3 final = normalize(cross(pos-playerPos, across))*(int(gl_Vertex.z > 0)*2-1)*abs(gl_Vertex.z);
	//if(dot(normalize(mid), normalize(pos-playerPos)) > 0){
		//final *= -1;
	//}
	
	
	
	
	
	
	
	gl_Position = gl_ModelViewProjectionMatrix * vec4(gl_Vertex.xyz+vec3(0, getPos(gl_Vertex.x, texCoord).yz), 1);
}