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

    public boolean remove(Quad element){
        return elements.remove(element);
    }

    public void update(){
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).update();
        }
        //elements.forEach(Quad::update);
    }

    public void render(){
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).render();
        }
        //elements.forEach(Quad::render);
    }
}
