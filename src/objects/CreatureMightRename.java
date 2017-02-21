package objects;

import engine.math.Vector3f;
import engine.objects.Projectile;
import engine.objects.Sprite;


/**
 * Created by 18iwahlqvist on 2/19/2017.
 */
public class CreatureMightRename extends Sprite {
    public int health = 100;
    public boolean test = false;

    private long previousFireTime, fireTime = 1000000000;
    private  boolean fireReady;
    public void update(){
        super.update();
//        if(health < 1){
//            currentScene.remove(this, 1);
//        }
//        for (int j = 0; j < currentScene.projectiles.size(); j++) {
//            if (collidesWith(currentScene.projectiles.get(j))) {
//                health -= 10;
//                System.out.println("Health: " + health);
//            }
//        }

//        if (test) {
//            if(fireReady == true) {
//                Projectile s2 = new Projectile(16f, 1, 1000000000);
//                s2.setTexture("projectile.png");
//                s2.startTime = System.nanoTime();
//                currentScene.add(s2, 2);
//                s2.position = new Vector3f(position.x + 35, position.y, position.z);
//                s2.velocity.add(new Vector3f(velocity.x, 0, 0));
//                fireReady = false;
//                previousFireTime = System.nanoTime();
//            }else{
//                if((previousFireTime + fireTime) < System.nanoTime()){
//                    fireReady = true;
//                }
//            }
//        }
    }
}
