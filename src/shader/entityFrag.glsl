# version 410 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;

out vec4 out_color;

uniform sampler2D textureSampler;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {

    vec3 unitNormalVector = normalize(surfaceNormal);
    vec3 unitCameraVector = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for (int i = 0; i < 4; ++i) {

        float distance = length(toLightVector[i]);
        float attenuationFactor = attenuation[i].x + attenuation[i].y * distance + attenuation[i].z * distance * distance;


        vec3 unitLightVector = normalize(toLightVector[i]);
        float brightness = max(dot(unitNormalVector, unitLightVector), 0);

        vec3 reflectedLightDirection = reflect(-unitLightVector, unitNormalVector);
        float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);

        // diffuse lighting
        totalDiffuse = totalDiffuse + brightness * lightColor[i] / attenuationFactor;

        // specular lighting
        totalSpecular = totalSpecular + dampedFactor * reflectivity * lightColor[i] / attenuationFactor;
    }

    totalDiffuse = max(totalDiffuse, 0.2);

    vec4 textureColor = texture(textureSampler, pass_textureCoords);

    if (textureColor.a < 0.2){
        discard;
    }

    out_color = vec4(totalDiffuse, 1.0) * textureColor + vec4(totalSpecular, 1.0);
    out_color = mix (vec4(skyColor, 1.0), out_color, visibility); // 0 -> skyColor, 1 out_color
}

