package com.mobplug.android.glestests.glutils.models.md3;

import com.mobplug.android.glestests.glutils.GLShader;
import com.mobplug.android.glestests.glutils.MatrixStack;

/**
 *
 * @author andreban
 */
public class Md3Model {
    private Md3ModelPart head;
    private Md3ModelPart torso;
    private Md3ModelPart lower;
    public void loadModels() {
        Md3ModelPartLoader loader = new Md3ModelPartLoader();
//        try {
//            head = loader.loadModel("/home/andreban/Downloads/ironsnout-md3/ironhead.md3", false);
//            lower = loader.loadModel("/home/andreban/Downloads/ironsnout-md3/ironlegs.md3", true);
//            torso = loader.loadModel("/home/andreban/Downloads/ironsnout-md3/irontorso.md3", false);
//        } catch(IOException ex) {
//            ex.printStackTrace();
//        }
    }
    
    public void render(GLShader shader, MatrixStack mvMatrix) {
        
        mvMatrix.push();
        
//        mvMatrix.push();        
        mvMatrix.rotate(90, 0.0f, 1.0f, 0);
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());                
        lower.render(shader.getAttributeLocations(), 0, 0); 
//        mvMatrix.pop();
        
        Md3Tag tag = lower.getTag("tag_torso");        
        mvMatrix.push();
        mvMatrix.translate(tag.getV3Origin()[0], tag.getV3Origin()[1], tag.getV3Origin()[2]);
        mvMatrix.multiply3(tag.getM33Rotation());
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());                
        torso.render(shader.getAttributeLocations(), 0, 0);

        
        mvMatrix.push();
        tag = torso.getTag("tag_head");
        mvMatrix.translate(tag.getV3Origin()[0], tag.getV3Origin()[1], tag.getV3Origin()[2]);   
        mvMatrix.multiply3(tag.getM33Rotation());        
        shader.setUniformMatrix4("mvMatrix", false, mvMatrix.getMatrix());        
        head.render(shader.getAttributeLocations(), 0, 0);
        
        mvMatrix.pop();        
        mvMatrix.pop();                        
        mvMatrix.pop();                
    }
}
