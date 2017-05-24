package scenes;

import engine.Engine;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.EmptyChair;
import objects.Player;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by 17ajamal on 5/16/2017.
 */
public class Geography extends Scene {
    public Sprite[] backdrops = new Sprite[6];
    public float progress = 0;
    public float speed = 1;
    public Sprite progressBar;
    Sprite instructions;
    public Geography(){

        progressBar = new Sprite(128, 0);
        progressBar.setTexture("npcSheet.png");
        progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
        progressBar.currentScene = this;

        instructionsShown=true;
        hasMap = false;
        instructions = new Sprite(1024, 2048, 0, 0.283203125f, 0.458984375f);
        instructions.setTexture("textSheet.png");
        instructions.currentScene = this;
        instructions.animationManager.textureCoord = new Vector2f(0f, 0f);
        for(int i = 0; i < 20; i++){
            if((i) % 15 == 1){
                Sprite l = new Sprite(128, 0);
                l.position = new Vector3f(400 +(float)(400.0 * Math.random()) + 800 * (i+1), -200, 0);
                l.currentScene = this;
                l.setTexture("characterSheet.png");
                l.animationManager.textureCoord = new Vector2f(0, 1-0.25f);
                npc.add(l);
            }
            Sprite s = new Sprite(128, 0);
            s.position = new Vector3f(200 + (float)(400.0 * Math.random()) + 800 * (i+1), -200, 0);
            s.currentScene = this;
            s.setTexture("npcSheet.png");
            s.animationManager.textureCoord = new Vector2f(0, 0.125f);
            npc.add(s);
        }
        backdrops[0] = new Sprite(512, 0);
        backdrops[1] = new Sprite(512, 0);
        backdrops[2] = new Sprite(512, 0);
        backdrops[3] = new Sprite(512, 0);
        backdrops[4] = new Sprite(512, 0);
        backdrops[5] = new Sprite(512, 0);
        backdrops[0].position = new Vector3f(-512,  128, 0);
        backdrops[1].position = new Vector3f(0,     128, 0);
        backdrops[2].position = new Vector3f(512,   128, 0);
        backdrops[3].position = new Vector3f(512*2, 128, 0);
        backdrops[4].position = new Vector3f(512*3, 128, 0);
        backdrops[5].position = new Vector3f(512*4, 128, 0);
        backdrops[0].currentScene = this;
        backdrops[1].currentScene = this;
        backdrops[2].currentScene = this;
        backdrops[3].currentScene = this;
        backdrops[4].currentScene = this;
        backdrops[5].currentScene = this;
        projection = new Matrix4f().orthographic(-384, 640, -512, 512, 10, -10);
//        projection = new Matrix4f().orthographic(-1024, 16000, -8512, 8512, 10, -10);
        backdrops[0].setTexture("npcSheet.png");
        backdrops[0].animationManager.textureCoord = new Vector2f(0f, 0.25f);
        backdrops[1].setTexture("npcSheet.png");
        backdrops[1].animationManager.textureCoord = new Vector2f(0.25f, 0.25f);
        backdrops[2].setTexture("npcSheet.png");
        backdrops[2].animationManager.textureCoord = new Vector2f(0.125f, 0.25f);
        backdrops[3].setTexture("npcSheet.png");
        backdrops[3].animationManager.textureCoord = new Vector2f(0f, 0.25f);
        backdrops[4].setTexture("npcSheet.png");
        backdrops[4].animationManager.textureCoord = new Vector2f(0.25f, 0.25f);
        backdrops[5].setTexture("npcSheet.png");
        backdrops[5].animationManager.textureCoord = new Vector2f(0.125f, 0.25f);

    }
    public void update(){
        if(instructionsShown){
            if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_A)==GLFW_TRUE){
                instructions.position = new Vector3f(40000, 0, 0);
                instructionsShown = false;
                instructions.render();
            }
            instructions.update();
            System.out.println(instructions.position);
        }else {
            if (((Player) players.get(0)).controllable) {
                ((Player) players.get(0)).controllable = false;
                players.get(0).velocity.x += 0.00000001f;
            }
            for (Sprite backdrop : backdrops) {
                backdrop.update();
                backdrop.velocity.x = -speed;
                if (backdrop.position.x < -1024) {
                    backdrop.position.x += 512 * 6;
                }
            }
            for (Sprite object : npc) {
                object.update();
                if (object.collidesHypotheticalWith(players.get(0), new Vector2f(-100, -50))) {
                    System.out.println(progress);
                    progress = 0;
                    speed = 1;
                    for (Sprite object2 : npc) {
                        object2.position.x += 1024;
                    }

                    backdrops[0].position = new Vector3f(-512, 128, 0);
                    backdrops[1].position = new Vector3f(0, 128, 0);
                    backdrops[2].position = new Vector3f(512, 128, 0);
                    backdrops[3].position = new Vector3f(512 * 2, 128, 0);
                    backdrops[4].position = new Vector3f(512 * 3, 128, 0);
                    backdrops[5].position = new Vector3f(512 * 4, 128, 0);
                }
                object.velocity.x = -speed;
                if (object.position.x < -512) {
                    object.position.x += 16000;
                }
            }
            if (speed < 20) {
                speed += 0.01;
            }if(progress < 1){
                progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
            }else if(progress < 2){
                progressBar.animationManager.textureCoord = new Vector2f(0.125f*0, 0.5f - 0.125f);
            }else if(progress < 2.8){
                progressBar.animationManager.textureCoord = new Vector2f(0.125f*1, 0.5f - 0.125f);
            }else if(progress < 3.6){
                progressBar.animationManager.textureCoord = new Vector2f(0.125f*2, 0.5f - 0.125f);
            }else{
                progressBar.animationManager.textureCoord = new Vector2f(0.125f*3, 0.5f - 0.125f);
            }
            progressBar.update();

            if (progress < 4) {
                progress += 0.001;
            } else {
                //Win
                Engine.instance.save.completedSocialStudies = true;
                Hallway.switchToScene(1);
            }

            if (players.get(0).position.y < -200) {
                players.get(0).velocity.x = 0;
                players.get(0).velocity.y = 0;
                players.get(0).animationManager.run("RunRight");
                if (glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GLFW_TRUE) {
                    players.get(0).velocity.y = 25;
                    players.get(0).animationManager.stop();
                }

            } else {
                if (glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GLFW_TRUE) {
                    players.get(0).velocity.y -= 1;
                } else {
                    players.get(0).velocity.y -= 2;
                }
            }
            players.get(0).update();
        }
    }

    public void render(){
        if(instructionsShown){
            instructions.render();
        }else {
            for (Sprite backdrop : backdrops) {
                backdrop.render();
            }

            for (Sprite object : npc) {
                object.render();
            }

            players.get(0).render();
        }
        progressBar.render();
    }
}
