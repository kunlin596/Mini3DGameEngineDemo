package entity;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;
import terrains.Terrain;

public class Entity {

    private TexturedModel model;
    private Vector3f position;
    private float rx, ry, rz;
    private float scale;
    private int textureIndex = 0;

    public Entity(TexturedModel model, Vector3f position,
                  float rx, float ry, float rz,
                  float scale,
                  int textureIndex) {
        this.model = model;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
        this.textureIndex = textureIndex;
    }

    public float getTextureOffsetX() {
        int column = textureIndex % model.getTexture().getNumberOfRows();
        return (float) column / (float) (model.getTexture().getNumberOfRows());
    }

    public float getTextureOffsetY() {
        int row = textureIndex / model.getTexture().getNumberOfRows();
        return (float) row / (float) (model.getTexture().getNumberOfRows());
    }

    public TexturedModel getModel() {
        return model;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Vector3f getPosition() {
        return position;
    }

    public float getRx() {
        return rx;
    }

    public float getRy() {
        return ry;
    }

    public float getRz() {
        return rz;
    }

    public float getScale() {
        return scale;
    }

    public Entity(TexturedModel model, Vector3f position, float rx, float ry, float rz, float scale) {

        this.model = model;
        this.position = position;
        this.rx = rx;
        this.ry = ry;
        this.rz = rz;
        this.scale = scale;
    }

    public void increasePosition(float dx, float dy, float dz) {
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }

    public void increaseRotation(float dx, float dy, float dz) {
        this.rx += dx;
        this.ry += dy;
        this.rz += dz;
    }
}
