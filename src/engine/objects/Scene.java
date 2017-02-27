package engine.objects;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.rendering.Quad;
import engine.rendering.Texture;
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
    public ArrayList<Sprite> tiles = new ArrayList<>();

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
        Vector3f position = new Vector3f(-map.mapData.length/2 * 128, -map.mapData[0].length/2 * 128, 1);
//        for(byte[] r : map.mapData){
//            for(byte data : r){
        for(int x = 0; x < map.mapData.length; x++){
            for(int y = 0; y < map.mapData[0].length; y++){
                position.add(new Vector3f(128, 0, 0));
                Sprite s = new Sprite(128, 0f);
                s.texture = Texture.tileSheet;
                s.position.set(position.x, position.y, position.z);
                switch (map.mapData[x][y]) {
                    case 0:
                        s.textureCoords.set(0, 0);
                        break;
                    case 1:
                        s.textureCoords.set(0.125f, 0);
                        break;
                    case 2:
                        s.textureCoords.set(0, 0.125f);
                        break;
                    case 3:
                        s.textureCoords.set(0.125f, 0.125f);
                        break;
                    case 4:
                        s.textureCoords.set(0, 0);
                        break;
                }
                add(s, 0);
                s.update();
                s.render();
                System.out.println("Added + " + "\t x:" + x + "\t y:" + y + "\tSprite Position = " + s.position + "\tWanted Position" + position + "\t\t Shader " + s.shader);
            }
            position.add(new Vector3f(-(map.mapData[0].length) * 128, 128, 0));
        }
    }

    public void update(){
//        for(Sprite s : tiles){
//            s.update();
//        }
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
//        for(Sprite s : tiles){
//            s.render();
//        }
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
