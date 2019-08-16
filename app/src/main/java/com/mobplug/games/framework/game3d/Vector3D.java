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
