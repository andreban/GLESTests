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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author andreban
 */
public class Md3ModelPart {
    private int version;
    private String name;
    private int flags;
    private int numFrames;
    private int numTags;
    private int numMeshes;
    private int numSkins;    
    
    private List<Md3Frame> frames = new ArrayList<Md3Frame>();
    private List<Md3Mesh> meshes = new ArrayList<Md3Mesh>();
    private Map<String, Md3Tag> tags = new HashMap<String, Md3Tag>();

    public int getFlags() {
        return flags;
    }

    void setFlags(int flags) {
        this.flags = flags;
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

    public int getNumMeshes() {
        return numMeshes;
    }

    void setNumMeshes(int numMeshes) {
        this.numMeshes = numMeshes;
    }

    public int getNumSkins() {
        return numSkins;
    }

    void setNumSkins(int numSkins) {
        this.numSkins = numSkins;
    }

    public int getNumTags() {
        return numTags;
    }

    void setNumTags(int numTags) {
        this.numTags = numTags;
    }

    public int getVersion() {
        return version;
    }

    void setVersion(int version) {
        this.version = version;
    }
        
    void addFrame(Md3Frame frame) {
        frames.add(frame);
    }

    void addMesh(Md3Mesh mesh) {
        meshes.add(mesh);
    }
    
    void addTag(Md3Tag tag) {
        tags.put(tag.getName(), tag);
    }    
    
    public Md3Tag getTag(String name) {
        return tags.get(name);
    }
    
    public void render(Map<String, Integer> shaderParams, int meshnum, int framenum) {
        meshes.get(meshnum).render(shaderParams, framenum);
    }
}
