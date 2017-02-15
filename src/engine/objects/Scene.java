package engine.objects;

import engine.math.Matrix4f;
import engine.rendering.Quad;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public abstract class Scene {
    public ArrayList<Quad> elements = new ArrayList<>();
    public Matrix4f projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);

    public Scene(){
        projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);
    }

    public void add(Quad element){
        elements.add(element);
        element.currentScene = this;
    }

    public void update(){
        elements.forEach(Quad::update);
    }

    public void render(){
        elements.forEach(Quad::render);
    }
}
