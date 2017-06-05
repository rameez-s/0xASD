package scenes;

import engine.Engine;
import engine.objects.Scene;
import engine.objects.*;
import engine.animation.*;
import engine.math.*;
import engine.rendering.*;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.opengl.GL11.GL_TRUE;


/**
 * Created by Isak Wahlqvist on 3/27/2017.
 */
public class RobertsOfficeScene extends Scene {
    Sprite door, roberts, passText;
    private boolean shouldRenderText = false;
    public RobertsOfficeScene(){
        setMap("robertsOffice.png");
        genMap();
        passText = new Sprite(1024, 576, 0, 1, 1);
        passText.setTexture("text.png");
        passText.currentScene = this;
        passText.position.y = 700;
        roberts = new Sprite(70, 141, 0, 0.125f, 0.125f*2);
        roberts.currentScene = this;
        roberts.position = new Vector3f(0, 700, 0);
        roberts.setTexture("characterSheet.png");
        roberts.animationManager.textureCoord = new Vector2f(0.625f, 0.125f);
        door = new Sprite(128, 0);
        door.currentScene = this;
        door.position = new Vector3f(-200, 0, 0);
        door.setTexture("Door Spritesheet.png");
    }
//w
    long timer = System.nanoTime();
    public void update() {
        if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_BACKSPACE) == GLFW_TRUE){
            Hallway.switchToScene(1);
        }
        super.update();
        door.update();
        roberts.update();
        passText.update();
        if(players.get(0).position.distance(roberts.position) < 50){
            if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE){
                shouldRenderText = true;
                timer = System.nanoTime() + 1000000000;
                System.out.println("Ran");
            }
        }
        if(shouldRenderText)
        if(timer < System.nanoTime()){
            shouldRenderText = false;
        }
        if(players.get(0).position.distance(door.position) < 50){
            if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE) {
                Hallway.switchToScene(1);
            }
        }
    }

    public void render(){
        super.render();
        roberts.render();
        door.render();
        players.get(0).render();
        if(shouldRenderText){
            passText.render();
        }
    }
}
