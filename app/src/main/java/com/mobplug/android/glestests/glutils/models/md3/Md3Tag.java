package com.mobplug.android.glestests.glutils.models.md3;

/**
 *
 * @author andreban
 */
public class Md3Tag {
    private String name;
    private float[] v3Origin;
    private float[] m33Rotation;

    public float[] getM33Rotation() {
        return m33Rotation;
    }

    void setM33Rotation(float[] m33Rotation) {
        this.m33Rotation = m33Rotation;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public float[] getV3Origin() {
        return v3Origin;
    }

    void setV3Origin(float[] v3Origin) {
        this.v3Origin = v3Origin;
    }
       
}
