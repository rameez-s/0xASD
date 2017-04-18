package scenes;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by Isak Wahlqvist
 */
public class Hallway extends Scene{
    private ArrayList<Sprite> door = new ArrayList<>();
    private Sprite doorTo1 = new Sprite(), doorTo2 = new Sprite(), doorTo3 = new Sprite(), doorTo4 = new Sprite(), doorTo5 = new Sprite();
    public Hallway(){
        setMap("hallway.png");
        doorTo1.position = new Vector3f(-1000,  1180, -1);
        doorTo2.position = new Vector3f(-500,   1180, -1);
        doorTo3.position = new Vector3f(0,      1180, -1);
        doorTo4.position = new Vector3f(500,    1180, -1);
        doorTo5.position = new Vector3f(1000,   1180, -1);
        add(doorTo1);
        add(doorTo2);
        add(doorTo3);
        add(doorTo4);
        add(doorTo5);
        genMap();
    }

    public void add(Sprite s){
        door.add(s);
        s.currentScene = this;
    }

    public void update(){
        super.update();
        for(Sprite elem : door){
            elem.update();
            if(elem.position.distance(players.get(0).position) < 80){
                if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_F) == GL_TRUE){
                    if(elem.equals(doorTo1)){
                        switchToScene(1);
                    }else if(elem.equals(doorTo2)){
                        switchToScene(2);
                    }else if(elem.equals(doorTo3)){
                        switchToScene(3);
                    }else if(elem.equals(doorTo4)){
                        switchToScene(4);
                    }else if(elem.equals(doorTo5)){
                        switchToScene(5);
                    }else{
                        System.out.println("test");
                    }
                    System.out.println(elem.position.distance(players.get(0).position));
                }
            }
        }
    }

    public void render(){
        super.render();
        for(Sprite d : door){
            d.render();
        }
    }

    private void switchToScene(int scene){
        Engine.instance.currentScene = scene;
        Engine.instance.scenes.get(Engine.instance.currentScene).add(players.get(0), 3);
        players.get(0).currentScene = Engine.instance.scenes.get(Engine.instance.currentScene);
        players.get(0).position = new Vector3f(0, 0, 0);
    }
}
