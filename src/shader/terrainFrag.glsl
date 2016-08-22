# version 410 core

in vec2 pass_textureCoords;
in vec3 surfaceNormal;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;
out vec4 out_color;

uniform sampler2D backgroundTexture;
uniform sampler2D rTexture;
uniform sampler2D gTexture;
uniform sampler2D bTexture;
uniform sampler2D blendMap;

uniform vec3 lightColor[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

void main() {

    vec4 blendMapColor = texture(blendMap, pass_textureCoords);
    float backTextureAmount = 1 - (blendMapColor.r, blendMapColor.g, blendMapColor.b);
    vec2 titledCoords = pass_textureCoords * 40.0;
    vec4 backgroundTextureColor = texture(backgroundTexture, titledCoords) * backTextureAmount;

    vec4 rTextureColor = texture(rTexture, titledCoords) * blendMapColor.r;
    vec4 gTextureColor = texture(gTexture, titledCoords) * blendMapColor.g;
    vec4 bTextureColor = texture(bTexture, titledCoords) * blendMapColor.b;
    vec4 totalColor = backgroundTextureColor + rTextureColor + gTextureColor + bTextureColor;


    // * * * * * * * * * * * * * * LIGHTING STUFF * * * * * * * * * * * * * * * *
    vec3 unitNormalVector = normalize(surfaceNormal);
    vec3 unitCameraVector = normalize(toCameraVector);

    vec3 totalDiffuse = vec3(0.0);
    vec3 totalSpecular = vec3(0.0);

    for(int i = 0; i < 4; ++i) {

        float distance = length(toLightVector[i]);
        float attenuationFactor = attenuation[i].x + attenuation[i].y * distance + attenuation[i].z * distance * distance;

        vec3 unitLightVector = normalize(toLightVector[i]);
        float brightness = max(dot(unitNormalVector, unitLightVector), 0);

        vec3 reflectedLightDirection = reflect(-unitLightVector, unitNormalVector);
        float specularFactor = max(dot(reflectedLightDirection, unitCameraVector), 0.0);
        float dampedFactor = pow(specularFactor, shineDamper);

        // diffuse lighting
        totalDiffuse = totalDiffuse + (brightness * lightColor[i]) / attenuationFactor;

        // specular lighting
        totalSpecular = totalSpecular + (dampedFactor * reflectivity * lightColor[i]) / attenuationFactor;
    }

    totalDiffuse = max(totalDiffuse, 0.2);

    if (totalColor.a < 0.2){
        discard;
    }

    out_color = vec4(totalDiffuse, 1.0) * totalColor + vec4(totalSpecular, 1.0);
    out_color = mix (vec4(skyColor, 1.0), out_color, visibility); // 0 -> skyColor, 1 out_color
}

