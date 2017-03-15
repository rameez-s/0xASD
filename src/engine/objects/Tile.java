package engine.objects;

/**
 * Created by 18iwahlqvist on 3/1/2017.
 */
public class Tile extends Sprite {
    public boolean wall = false;

    public Tile(float size, float z){
        super(size, z);
    }

    public void pushBack(Sprite s){
        boolean stillPush = collidesWith(s);
        while(stillPush){
            s.position.set(s.position.x-s.velocity.x/10, s.position.y-s.velocity.y/10, s.position.z);
//            s.velocity.set(s.velocity.x * 2, s.velocity.y * 2, s.velocity.z * 2);
//            s.update();
            stillPush = collidesWith(s);
            System.out.println(stillPush);
        }
    }
}
