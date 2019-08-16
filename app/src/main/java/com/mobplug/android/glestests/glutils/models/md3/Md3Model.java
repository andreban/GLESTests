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

import java.io.IOException;

import android.content.Context;
import android.opengl.GLES20;

import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.GLTexture;
import com.mobplug.android.glestests.glutils.MatrixStack;

import me.bandarra.android.glestests.R;

/**
 *
 * @author andreban
 */
public class Md3Model {
    private Md3ModelPart head;
    private Md3ModelPart torso;
    private Md3ModelPart lower;
    private GLTexture headTexture;
    private GLTexture torsoTexture;
    private GLTexture lowerTexture;
    
    public void loadModels(Context context) {
        Md3ModelPartLoader loader = new Md3ModelPartLoader();
        try {
            head = loader.loadModel(context.getResources().openRawResource(R.raw.ironhead), false);
            lower = loader.loadModel(context.getResources().openRawResource(R.raw.ironlegs), true);
            torso = loader.loadModel(context.getResources().openRawResource(R.raw.irontorso), false);
            headTexture = new GLTexture(context, R.raw.headorange);
            torsoTexture = new GLTexture(context, R.raw.upperorange);
            lowerTexture = new GLTexture(context, R.raw.lowerorange);
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void render(GLShader shader, MatrixStack mvMatrix) {
        
        mvMatrix.push();
        
//        mvMatrix.push();        
        mvMatrix.rotate(90, 0.0f, 1.0f, 0);
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());   
        lowerTexture.useTexture(GLES20.GL_TEXTURE0);
        lower.render(shader.getAttributeLocations(), 0, 0); 
//        mvMatrix.pop();
        
        Md3Tag tag = lower.getTag("tag_torso");        
        mvMatrix.push();
        mvMatrix.translate(tag.getV3Origin()[0], tag.getV3Origin()[1], tag.getV3Origin()[2]);
        mvMatrix.multiply3(tag.getM33Rotation());
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());
        torsoTexture.useTexture(GLES20.GL_TEXTURE0);        
        torso.render(shader.getAttributeLocations(), 0, 0);

        
        mvMatrix.push();
        tag = torso.getTag("tag_head");
        mvMatrix.translate(tag.getV3Origin()[0], tag.getV3Origin()[1], tag.getV3Origin()[2]);   
        mvMatrix.multiply3(tag.getM33Rotation());        
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());  
        headTexture.useTexture(GLES20.GL_TEXTURE0);        
        head.render(shader.getAttributeLocations(), 0, 0);
        
        mvMatrix.pop();        
        mvMatrix.pop();                        
        mvMatrix.pop();                
    }
}
