package com.mobplug.games.framework.game3d;

/**
 *
 * @author andreban
 */
public class Vector3D {
    private float x = 0.0f;
    private float y = 0.0f;
    private float z = 0.0f;

    public Vector3D() {
        this(0.0f, 0.0f, 0.0f);
    }

    public Vector3D(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(Vector3D vec) {
        this(vec.x, vec.y, vec.z);
    }

    public void add(float x, float y, float z) {
        this.x += x;
        this.y += y;
        this.z += z;
    }

    public void add(Vector3D vec) {
        add(vec.x, vec.y, vec.z);
    }
    
    public void subtract(float x, float y, float z) {
        add(-x, -y, -z);
    }

    public void subtract(Vector3D vec) {
        subtract(vec.x, vec.y, vec.z);
    }
    
    public void multiply(float magnitude) {
        this.x *= magnitude;
        this.y *= magnitude;
        this.z *= magnitude;
    }

    public void divide(float magnitude) {
        this.x /= magnitude;
        this.y /= magnitude;
        this.z /= magnitude;
    }
}
