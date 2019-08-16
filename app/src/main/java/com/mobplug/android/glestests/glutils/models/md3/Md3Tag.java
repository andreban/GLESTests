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
