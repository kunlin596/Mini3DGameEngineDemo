package entity;

import org.lwjgl.util.vector.Vector3f;

public class Light {

    private Vector3f position;
    private Vector3f color;
    private Vector3f attuation = new Vector3f(1, 0, 0);

    public Light(Vector3f position, Vector3f color) {
        this.position = position;
        this.color = color;
    }

    public Light(Vector3f position, Vector3f color, Vector3f attuation) {
        this.position = position;
        this.color = color;
        this.attuation = attuation;
    }

    public Vector3f getAttuation() {
        return attuation;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void setColor(Vector3f color) {
        this.color = color;
    }

    public Vector3f getPosition() {

        return position;
    }

    public Vector3f getColor() {
        return color;
    }
}
