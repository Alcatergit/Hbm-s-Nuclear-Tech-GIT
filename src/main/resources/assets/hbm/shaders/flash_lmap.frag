#version 330 compatibility

in vec2 texCoord;
in vec2 lightCoord;
in vec4 color;
in vec3 lighting;
out vec4 FragColor;

uniform sampler2D texture;
uniform sampler2D lightmap;
uniform sampler2D flash_map;

void main(){
	vec4 col = color * texture2D(texture, texCoord) * texture2D(lightmap, lightCoord);
	vec4 fl = texture2D(flash_map, texCoord);
	FragColor = vec4(col.rgb * lighting + (pow(fl.rgb, vec3(1.3))-0.1)*vec3(1, 0.72, 0.46), col.a);
}