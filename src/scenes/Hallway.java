package scenes;

import engine.Engine;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.Player;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 4/9/2017.
 */
public class Hallway extends Scene{
    private Sprite doorTo1 = new Sprite(), doorTo2 = new Sprite(), doorTo3 = new Sprite(), doorTo4 = new Sprite(), doorTo5 = new Sprite();
    public Hallway(){
        setMap("hallway.png");
        doorTo1.position = new Vector3f(-100, 100, 0);
        doorTo2.position = new Vector3f(-50, 100, 0);
        doorTo3.position = new Vector3f(0, 100, 0);
        doorTo4.position = new Vector3f(50, 100, 0);
        doorTo5.position = new Vector3f(100, 100, 0);
        add(doorTo1, 0);
        add(doorTo2, 0);
        add(doorTo3, 0);
        add(doorTo4, 0);
        add(doorTo5, 0);
        genMap();
    }

    public void update(){
        super.update();
        for(Sprite elem : elements){
            if(elem.position.distance(players.get(0).position) < 200){
//                if(elem.collidesHypotheticalWith(players.get(0), new Vector2f(10, 20))){
//
//                }
                if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE){
                    System.out.println("Hej");
                }
            }
        }
    }
}
