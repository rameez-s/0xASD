package engine.animation;

import engine.math.Vector2f;

/**
 * Created by Isak Wahlqvist on 3/29/2017.
 */
public class Animation {
    //Define the variables needed
    public String name;
    public boolean stopped = false;
    public int frames = 3; //How many frames the animation is -1
    public float totalTime = 1; //How long the animation will take in seconds
    public Vector2f startPos;
    public int currentFrame = 0;

    //Constructers
    public Animation(String name, Vector2f startPos){
        this.name = name;
        this.startPos = startPos;
    }
    public Animation(String name, Vector2f startPos, float totalTime){
        this.name = name;
        this.startPos = startPos;
        this.totalTime = totalTime;
    }

    public Animation(String name, Vector2f startPos, float totalTime, int frames){
        this.name = name;
        this.startPos = startPos;
        this.totalTime = totalTime;
        this.frames = frames;
    }

    //Get what texture coordinate the animation is on
    public Vector2f getPos(){
        return new Vector2f(startPos.x, startPos.y + (float)(0.125 * currentFrame));
    }
}
