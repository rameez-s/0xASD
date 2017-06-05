package scenes;

import engine.objects.Sprite;

/**
 * Created by 18iwahlqvist on 6/5/2017.
 */
public class Beat extends Sprite {

    public boolean hit = false;
    public boolean missed = false;

    public Beat(float s, float z){
        super(s,z);
    }
    public void update(){
        super.update();
    }

    public void render(){
        super.render();
    }
}
