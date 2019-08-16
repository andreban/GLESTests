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
