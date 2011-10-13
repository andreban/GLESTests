package com.mobplug.android.glestests.glutils.models.md3;

/**
 *
 * @author andreban
 */
public class Md3Frame {
    private int num;
    private String name;
    private float[] v3MinBounds;
    private float[] v3MaxBounds;
    private float[] v3LocalOrigin;
    private float radius;

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    void setNum(int num) {
        this.num = num;
    }

    public float getRadius() {
        return radius;
    }

    void setRadius(float radius) {
        this.radius = radius;
    }

    public float[] getV3MaxBounds() {
        return v3MaxBounds;
    }

    void setV3MaxBounds(float[] v3MaxBounds) {
        this.v3MaxBounds = v3MaxBounds;
    }

    public float[] getV3MinBounds() {
        return v3MinBounds;
    }

    void setV3MinBounds(float[] v3MinBounds) {
        this.v3MinBounds = v3MinBounds;
    }

    public float[] getV3LocalOrigin() {
        return v3LocalOrigin;
    }

    void setV3LocalOrigin(float[] v3LocalOrigin) {
        this.v3LocalOrigin = v3LocalOrigin;
    }        
}
