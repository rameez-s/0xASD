package engine.objects;

import engine.math.Vector3f;

/**
 * Created by Isak Wahlqvist on 2/15/2017.
 */
public class Projectile extends Sprite {

    public long startTime;
    private long killTime;

    public Projectile(float size, float z, long killTime){
        super(size, z);
        this.killTime = killTime;
    }
    public void update(){
        super.update();
        position.add(velocity);
        if(System.nanoTime() > (startTime + killTime)){
            currentScene.remove(this, 2);
        }
    }

    public void render(){
        super.render();
    }

}
