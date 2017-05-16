package scenes;

import engine.Engine;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.Player;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.GLFW_TRUE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;

/**
 * Created by 18iwahlqvist on 5/16/2017.
 */
public class Geography extends Scene {
    public Sprite[] backdrops = new Sprite[3];
    public float progress = 0;
    public float speed = 1;

    public Geography(){
        backdrops[0] = new Sprite(512, 0);
        backdrops[1] = new Sprite(512, 0);
        backdrops[2] = new Sprite(512, 0);
        backdrops[0].currentScene = this;
        backdrops[1].currentScene = this;
        backdrops[2].currentScene = this;
        backdrops[0].animationManager.setTexture("npcSheet.png");
        backdrops[1].animationManager.setTexture("npcSheet.png");
        backdrops[2].animationManager.setTexture("npcSheet.png");

    }
    public void update(){
        if(((Player)players.get(0)).controllable)
        for(Sprite backdrop : backdrops){
            backdrop.update();
            backdrop.velocity.x = -speed;
            if(backdrop.position.x < -500){
                backdrop.position.x += 1000;
            }
        }
        for(Sprite object : npc){
            object.update();
            if(object.collidesWith(players.get(0))){
                //Lose
                speed = 1;
            }
            object.velocity.x = -speed;
        }
        if(speed < 20) {
            speed += 0.01;
        }
        if(progress < 100){
            progress += 0.001;
        }else{
            //Win
        }

        if(players.get(0).position.x < 100){
            if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GLFW_TRUE){
                players.get(0).velocity.y = 20;
            }

        }

    }

    public void render(){
        for(Sprite backdrop : backdrops){
            backdrop.render();
        }

        players.get(0).render();

        for(Sprite object : npc){
            object.render();
        }
    }
}
