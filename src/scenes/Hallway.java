package scenes;

import engine.Engine;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.objects.Door;
import objects.Player;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by Isak Wahlqvist
 */
public class Hallway extends Scene{
    private ArrayList<Door> door = new ArrayList<>();
    private Door doorTo1 = new Door(), doorTo2 = new Door(), doorTo3 = new Door(), doorTo4 = new Door(), doorTo5 = new Door();
    public Hallway(){
        setMap("hallway.png");
        doorTo1.position = new Vector3f(-1000,  1080, -1);
        doorTo2.position = new Vector3f(-500,   1080, -1);
        doorTo3.position = new Vector3f(0,      1080, -1);
        doorTo4.position = new Vector3f(500,    1080, -1);
        doorTo5.position = new Vector3f(1000,   1080, -1);
        add(doorTo1);
        add(doorTo2);
        add(doorTo3);
        add(doorTo4);
        add(doorTo5);
        genMap();
    }

    public void add(Door s){
        door.add(s);
        s.currentScene = this;
    }

    public void update(){
        super.update();
        if(!((Player)players.get(0)).controllable){
            ((Player)players.get(0)).controllable = true;
        }
        if(((Player)players.get(0)).sideMoveOnly){
            ((Player)players.get(0)).sideMoveOnly = false;
        }
        for(Door elem : door){
            elem.update();
            if(elem.isClose()){
                if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE){
                    if(elem.equals(doorTo1)){
                        if(!Engine.instance.save.completedGym)
                        switchToScene(2);
                        else
                            elem.completed = true;
                    }else if(elem.equals(doorTo2)){
                        if(!Engine.instance.save.completedEnglish)
                        switchToScene(3);
                        else
                            elem.completed = true;
                    }else if(elem.equals(doorTo3)){
                        if(!Engine.instance.save.completedSocialStudies)
                        switchToScene(4);
                        else
                            elem.completed = true;
                    }else if(elem.equals(doorTo4)){
                        if(!Engine.instance.save.completedMusic)
                        switchToScene(5);
                        else
                        elem.completed = true;
                    }else if(elem.equals(doorTo5)){
                        switchToScene(6);
                    }else{

                    }
                }
            }
        }
    }

    public void render(){
        super.render();
        door.forEach(Sprite::render);
        players.get(0).render();
    }

    //Test
    public static int switchToScene(int scene){
        if(Engine.instance.scenes.size() > scene) {
            Player p = (Player)Engine.instance.scenes.get(Engine.instance.currentScene).players.get(0);
            p.sideMoveOnly = false;
            Engine.instance.currentScene = scene;
            Engine.instance.scenes.get(Engine.instance.currentScene).set(p, 3, 0);
            p.currentScene = Engine.instance.scenes.get(Engine.instance.currentScene);
            p.position = new Vector3f(0, 0, 0);
            if(scene == 1) {
                p.controllable = true;
                System.out.println(p.controllable + "\t" + p.sideMoveOnly);
                p.velocity = new Vector3f(0, 0, 0);
                Engine.instance.scenes.get(Engine.instance.currentScene).projection = new Matrix4f().orthographic(-512, 512, -512, 512, -10, 10);
            }
            return 0;
        }else{
            System.out.println("Scene does not works");
            return -1;
        }
    }
}
