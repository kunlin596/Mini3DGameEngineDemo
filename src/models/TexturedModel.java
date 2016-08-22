package models;

import textures.ModelTexture;

public class TexturedModel {

    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public void setRawModel(RawModel rawModel) {
        this.rawModel = rawModel;
    }

    public void setTexture(ModelTexture texture) {
        this.texture = texture;
    }

    public RawModel getRawModel() {

        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
}
