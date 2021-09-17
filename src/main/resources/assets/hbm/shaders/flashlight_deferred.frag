#version 120

uniform sampler2D colors;
uniform sampler2D positions;
uniform sampler2D normals;
uniform sampler2D proj_coords;
uniform sampler2D shadowTex;
uniform sampler2D flashlightTex;
uniform vec2 windowSize;
uniform vec3 pos;
uniform float height;

void main(){
	vec2 tex = gl_FragCoord.xy/windowSize;
	vec4 color = texture2D(colors, tex);
	vec3 worldPos = texture2D(positions, tex).xyz;
	vec3 proj_coord = texture2D(proj_coords, tex).xyz;
	vec3 normal = texture2D(normals, tex).xyz;
	
	//Clamp it so only the fragments that fit on the flashlight texture show up
	int test = int(proj_coord.s >= 0 && proj_coord.s <= 1 && proj_coord.t >= 0 && proj_coord.t <= 1);
	//Dot product, fragments that the flashlight directly faces shound be brighter, while normals parallel to the light direction should be dimmer.
	float dproduct = dot(normal, -normalize(worldPos - pos));
	//Fade with distance, light becomes less bright as it reaches the end of its cone.
	float distanceFade = 1-length(worldPos - pos)/height;
	//The shadow calculation, how much of it is in the light
	float shadow = proj_coord.p;
	
	//Multiply everything together to get the final color (also threw in a 2 so it looked bright enough)
	gl_FragColor = vec4(texture2D(flashlightTex, proj_coord.st).rgb*color.rgb*2*test*distanceFade*dproduct*shadow, 1);
}