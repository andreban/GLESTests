/*
 * Copyright 2019 André Cipriani Bandarra
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

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
