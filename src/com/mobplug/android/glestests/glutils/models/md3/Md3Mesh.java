package com.mobplug.android.glestests.glutils.models.md3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andreban
 */
public class Md3Mesh {
    private int id;
    private String name;
    private int flags;
    private int numFrames;
    private int numShaders;
    private int numVertices;
    private int numTriangles;

    private List<Md3MeshFrame> frames = new ArrayList<Md3MeshFrame>();
    
    public int getFlags() {
        return flags;
    }

    void setFlags(int flags) {
        this.flags = flags;
    }

    public int getId() {
        return id;
    }

    void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public int getNumFrames() {
        return numFrames;
    }

    void setNumFrames(int numFrames) {
        this.numFrames = numFrames;
    }

    public int getNumShaders() {
        return numShaders;
    }

    void setNumShaders(int numShaders) {
        this.numShaders = numShaders;
    }

    public int getNumTriangles() {
        return numTriangles;
    }

    void setNumTriangles(int numTriangles) {
        this.numTriangles = numTriangles;
    }

    public int getNumVertices() {
        return numVertices;
    }

    void setNumVertices(int numVertices) {
        this.numVertices = numVertices;
    }
            
    void addFrame(Md3MeshFrame frame) {
        frames.add(frame);
    }
    
    public void render(Map<String, Integer> shaderParams, int frame) {
        frames.get(frame).render(shaderParams);
    }
}
