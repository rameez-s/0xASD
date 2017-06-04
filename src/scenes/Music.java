package scenes;

import engine.ThreadManager;
import engine.math.Vector2f;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.sound.Sound;
import objects.Player;

/**
 * Created by 18iwahlqvist on 5/31/2017.
 */
public class Music extends Scene {
    Sprite[] beats = new Sprite[100];
    Sprite musicBar;
    public Music(){
        hasMap = false;
        musicBar = new Sprite(1024, 0);
        ThreadManager.shouldStop = false;
        ThreadManager.play = false;
        musicBar.setTexture("npcSheet.png");
        musicBar.animationManager.textureCoord = new Vector2f(0.125f, 0.125f);
        musicBar.currentScene = this;
        for(int i = 0; i < beats.length; i++){
            beats[i] = new Sprite();
            beats[i].currentScene = this;
            beats[i].position.set(i * 700, 0, 0);
            beats[i].setTexture("default.png");
        }
    }
    long startTime = 0;
    public void update(){
        if(ThreadManager.play = false){
            ThreadManager.soundToPlay = new Sound("Childish Gambino - Redbone [8-BIT REMIX].ogg", 183000);
            ThreadManager.play = true;
            startTime = System.nanoTime();
        }
        if( ((Player)players.get(0)).controllable){
            ((Player)players.get(0)).controllable = false;
        }
        for(Sprite beat : beats){
            beat.update();
            beat.velocity.set(-10, 0, 0);
        }
        musicBar.update();
    }

    public void render(){
        for(Sprite beat : beats){
            beat.render();
        }
        musicBar.render();
    }
}
