package scenes;

import engine.Engine;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.math.Vector2f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.Player;
import objects.npcs.PeTeacher;

import java.util.ArrayList;
/**
 * Created by Isak Wahlqvist on 4/20/2017.
 */
public class Gym extends Scene {
    public int currentProgress = 0;
    Sprite progressBar;
    PeTeacher pe, pe2;
    public Gym(){
        projection = new Matrix4f().orthographic(-512, 512, 0, 1024, 10, -10);
        optimize = false;
        setMap("Gym.png");
        genMap();
        pe = new PeTeacher();
        this.add(pe, 1);
        pe2 = new PeTeacher();
        this.add(pe2, 1);

        progressBar = new Sprite(128, 0);
        progressBar.setTexture("npcSheet.png");
        progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
        progressBar.currentScene = this;
        progressBar.position.set(200, 100, 0);

    }

    public void update(){
        if(players.get(0).position.y < 100) players.get(0).position.y = 100;
        ((Player)players.get(0)).sideMoveOnly = true;
        super.update();
        progressBar.position.add(players.get(0).velocity);
        if(currentProgress > 250){
            win();
        }

        progressBar.update();

        if(currentProgress < 62){
            progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
        }else if(currentProgress < 125){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*0, 0.5f - 0.125f);
        }else if(currentProgress < 175){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*1, 0.5f - 0.125f);
        }else if(currentProgress < 225){
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*2, 0.5f - 0.125f);
        }else{
            progressBar.animationManager.textureCoord = new Vector2f(0.125f*3, 0.5f - 0.125f);
        }
    }
    public void win(){
        Engine.instance.save.completedGym = true;
        Hallway.switchToScene(1);
    }
    public void lose(){
        pe.position = new Vector3f((int) (Math.random() * 600 - 300), 900, 0);
        pe.reduce = 0;
        pe.fireTimer = 0;
        pe2.position = new Vector3f((int) (Math.random() * 600 - 300), 900, 0);
        pe2.reduce = 0;
        pe2.fireTimer = 0;

        currentProgress = 0;
    }

    public void render(){
        super.render();
        progressBar.render();
    }
}
