package engine.objects;

import engine.math.Vector3f;

/**
 * Created by 18iwahlqvist on 2/15/2017.
 */
public class Projectile extends Sprite {

    public long startTime;
    private long killTime;

    public Projectile(float size, float z, long killTime){
        super(size, z);
        this.killTime = killTime;
    }
    public void update(){
        position.add(velocity);
        shader.bind();
        shader.setUniform("sampler", 1);
        shader.setUniform("projection", currentScene.projection);
        shader.setUniform("position", position);
        if(System.nanoTime() > (startTime + killTime)){
            currentScene.remove(this);
        }
    }

    public void render(){
        super.render();
    }

}
