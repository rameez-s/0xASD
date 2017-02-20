package engine.objects;

import engine.math.Matrix4f;
import engine.rendering.Quad;
import objects.CreatureMightRename;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public abstract class Scene {
    public ArrayList<Quad> elements = new ArrayList<>();
    public ArrayList<CreatureMightRename> creatures = new ArrayList<>();
    public ArrayList<CreatureMightRename> players = new ArrayList<>();
    public ArrayList<Projectile> projectiles = new ArrayList<>();
    public Matrix4f projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);

    public boolean backgroundUpdate = true;

    public Scene(){
        projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);
    }

    public void add(Quad element, int array){
        switch (array){
            case 0:
                elements.add(element);
                break;
            case 1:
                creatures.add((CreatureMightRename) element);
                break;
            case 2:
                projectiles.add((Projectile) element);
                break;
            case 3:
                players.add((CreatureMightRename) element);
                break;
            default:
                throw new UnknownError();
        }
        element.currentScene = this;
    }

    public boolean remove(Quad element, int array){
        switch (array){
            case 0:
                return elements.remove(element);
            case 1:
                return creatures.remove(element);
            case 2:
                return projectiles.remove(element);
        }
        return elements.remove(element);
    }

    public void update(){
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).update();
        }
        for (int i = creatures.size() - 1; i >= 0; i--) {
            creatures.get(i).update();
        }
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            projectiles.get(i).update();
        }
        for (int i = players.size() - 1; i >= 0; i--) {
            players.get(i).update();
        }
    }

    public void render(){
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).render();
        }
        for (int i = creatures.size() - 1; i >= 0; i--) {
            creatures.get(i).render();
        }
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            projectiles.get(i).render();
        }
        for (int i = players.size() - 1; i >= 0; i--) {
            players.get(i).render();
        }
    }
}
