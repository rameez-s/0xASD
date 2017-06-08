package scenes;

import engine.Engine;
import engine.ThreadManager;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.sound.Sound;
import objects.Player;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Created by 18iwahlqvist on 5/31/2017.
 */
public class Music extends Scene {
    Beat[] beats = new Beat[10];
    Sprite musicBar, fill, great, perfect, miss, progressBar;
    int progress = 500;
    public Music(){
        hasMap = false;
        musicBar = new Sprite(192, 0);
        fill = new Sprite(192, 0);
        musicBar.position = new Vector3f(-300, 0, 0);
        fill.position = new Vector3f(-300, 0, 0);
        ThreadManager.shouldStop = false;
        ThreadManager.play = false;
        musicBar.setTexture("characterSheet.png");
        musicBar.animationManager.textureCoord = new Vector2f(1-0.25f, 1-0.125f);
        musicBar.currentScene = this;

        progressBar = new Sprite(128, 0);
        progressBar.position = new Vector3f(-200, -400, 0);
        progressBar.setTexture("npcSheet.png");
        progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
        progressBar.currentScene = this;

        fill.setTexture("characterSheet.png");
        fill.animationManager.textureCoord = new Vector2f(1-0.125f, 1-0.125f);
        fill.currentScene = this;

        miss = new Sprite(192, 0);
        miss.setTexture("characterSheet.png");
        miss.animationManager.textureCoord = new Vector2f(1-0.375f, 1-0.125f);
        miss.currentScene = this;

        great = new Sprite(192, 0);
        great.setTexture("characterSheet.png");
        great.animationManager.textureCoord = new Vector2f(1-0.25f, 1-0.25f);
        great.currentScene = this;

        perfect = new Sprite(200, 138, 0, 0.25f, 0.125f);
        perfect.setTexture("characterSheet.png");
        perfect.animationManager.textureCoord = new Vector2f(1-0.5f, 1-0.25f);
        perfect.currentScene = this;

        miss.position = new Vector3f(-300, 300, 0);
        great.position = new Vector3f(-300, 300, 0);
        perfect.position = new Vector3f(-300, 300, 0);

        for(int i = 0; i < beats.length; i++){
            beats[i] = new Beat(150, 0);
            beats[i].currentScene = this;
            beats[i].position.set(2500 + i * 600 + 300 * (float)(Math.random() * 0.5 + 0.25), 0, 0);
            beats[i].setTexture("characterSheet.png");
            beats[i].animationManager.textureCoord = new Vector2f(1-0.125f, 1-0.25f);
        }
    }
    long startTime = 0;
    long timer = 0;
    boolean hasStarted = false;
    boolean hasHit;
    int hitType = -1;
    public void update(){
        players.get(0).update();
        if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_BACKSPACE) == GLFW_TRUE){
            Hallway.switchToScene(1);
        }
        if(ThreadManager.play == false){
            ThreadManager.soundToPlay = new Sound("Childish Gambino - Redbone [8-BIT REMIX].ogg", 183000000);
            ThreadManager.play = true;
            startTime = System.nanoTime();
            hasStarted = true;
            System.out.println("started Childish Gambino");
        }else{
            if(!hasStarted){
                ThreadManager.play = false;
                System.out.println("Not started");
            }
        }
        if( ((Player)players.get(0)).controllable){
            ((Player)players.get(0)).controllable = false;
        }
        for(Beat beat : beats){
            if(!beat.missed && !beat.hit) {
                float distance = beat.position.distance(fill.position);
                beat.update();
                if (beat.position.x > fill.position.x - 100 && distance < 800) {
                    if (timer < System.nanoTime()) {
                        if (glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GLFW_TRUE) {
                            if (distance < 20) {
                                beat.hit = true;
                                System.out.println("PERFECT");
                                hitType = 2;
                                progress += 50;
                            } else if (distance < 70) {
                                beat.hit = true;
                                System.out.println("GREAT");
                                hitType = 1;
                            } else {
                                System.out.println("MISS");
                                hitType = 0;
                                progress -= 50;
                            }
                            hasHit = true;
                            timer = System.nanoTime() + 250000000;
                        }
                    }
                }
            }
            if(!beat.missed && !beat.hit && beat.position.x < -600){
                beat.missed = true;
                progress -= 50;
                System.out.println(progress);
            }
            beat.velocity.set(-10, 0, 0);
        }

        if(beats[beats.length-1].position.x < -400 || beats[beats.length-1].hit){
            Hallway.switchToScene(1);
            Engine.instance.save.completedMusic = true;
        }

        if(progress < 0){
            for(int i = 0; i < beats.length; i++){
                beats[i] = new Beat(150, 0);
                beats[i].currentScene = this;
                beats[i].position.set(2500 + i * 600 + 300 * (float)(Math.random() * 0.5 + 0.25), 0, 0);
                beats[i].setTexture("characterSheet.png");
                beats[i].animationManager.textureCoord = new Vector2f(1-0.125f, 1-0.25f);
            }
            progress = 500;
            hitType = -1;
        }

        if(progress < 250){
            progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
        }else if(progress < 500){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*0, 0.5f - 0.125f);
        }else if(progress < 700){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*1, 0.5f - 0.125f);
        }else if(progress < 900){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*2, 0.5f - 0.125f);
        }else{
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*3, 0.5f - 0.125f);
        }
        musicBar.update();
        fill.update();
        great.update();
        miss.update();
        perfect.update();
    }

    public void render(){
        fill.render();
        for(Sprite beat : beats){
            beat.render();
        }
        musicBar.render();
        if(hasHit) {
            switch (hitType) {
                case 0:
                    miss.render();
                    break;
                case 1:
                    great.render();
                    break;
                case 2:
                    perfect.render();
            }
        }
    }
}
