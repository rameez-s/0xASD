package engine.animation;

import engine.math.Vector2f;
import engine.rendering.Texture;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 3/29/2017.
 */
public class AnimationManager {
    ArrayList<Animation> animations = new ArrayList<>();
    public Texture texture;
    public Vector2f textureCoord;
    Animation currentAnimation;
    private long lastTime;

    public AnimationManager(){
        texture = Texture.defaultSheet;
        textureCoord = new Vector2f(0, 0);
        lastTime = System.nanoTime();
    }

    public void setTexture(String textureFile){
        if(textureFile.equals("characterSheet.png")){
            texture = Texture.characterSheet;
        }else if(textureFile.equals("tileSheet.png")){
            texture = Texture.tileSheet;
        }else if(textureFile.equals("npcSheet.png")){
            texture = Texture.npcSheet;
        }else {
            texture = new Texture("" + textureFile);
        }
    }

    public void add(Animation animation){
        animations.add(animation);
    }

    public void remove(String animName){
        for (Animation a : animations) {
            if(a.name.equals(animName)){
                animations.remove(a);
            }
        }
    }

    public void run(String animName){
        for(Animation a : animations){
            if(a.name.equals(animName)) {
                currentAnimation = a;
                currentAnimation.stopped = false;
            }
        }
    }

    public void stop(){
        currentAnimation.stopped = true;
    }


    public void update(){
        if(currentAnimation == null){
        }else {
            if (currentAnimation.stopped || currentAnimation.totalTime == 0 || currentAnimation.frames == 0) {
                currentAnimation.currentFrame = 0;
                textureCoord = currentAnimation.getPos();
            } else if (System.nanoTime() > lastTime) {
                if (currentAnimation.currentFrame < currentAnimation.frames) {
                    currentAnimation.currentFrame++;
                } else {
                    currentAnimation.currentFrame = 0;
                }
                lastTime = (long) (System.nanoTime() + (1000000000 * currentAnimation.totalTime/(currentAnimation.frames + 1)));
                textureCoord = currentAnimation.getPos();
            }
        }
    }
}
