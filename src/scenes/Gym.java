package scenes;

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
 * Created by 18iwahlqvist on 4/20/2017.
 */
public class Gym extends Scene {
    public int currentProgress = 0;
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

    }

    public void update(){
        if(players.get(0).position.y < 100) players.get(0).position.y = 100;
        ((Player)players.get(0)).sideMoveOnly = true;
        super.update();
        if(currentProgress > 250){
            win();
        }
    }
    public void win(){
        Hallway.switchToScene(2);
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
    }
}
