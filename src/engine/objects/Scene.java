package engine.objects;

import engine.math.Matrix4f;
import engine.math.Vector3f;
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

    Map map;

    public Matrix4f projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);

    public boolean backgroundUpdate = true;

    public Scene(){
        projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);
    }

    public void setMap(String fileName){
        map = new Map(fileName);
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

    public void genMap(){
        Vector3f position = new Vector3f();
        for(byte[] r : map.mapData){
            for(byte data : r){
                position.add(new Vector3f(32, 0, 0));
                Sprite s = new Sprite(32, 0f);
                s.position = position;
                switch (data) {
                    case 0:
                        s.setTexture("tiles/Tile0.png");
                        break;
                    case 1:
                        s.setTexture("tiles/Tile1.png");
                        break;
                    case 2:
                        s.setTexture("tiles/Tile2.png");
                        break;
                    case 3:
                        s.setTexture("tiles/Tile3.png");
                        break;
                    case 4:
                        s.setTexture("tiles/Tile4.png");
                        break;
                }
                s.render();
                s.currentScene = this;
                s.update();
                System.out.println(s.position);
            }
            position.add(new Vector3f(0, 32, 0));
        }
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
