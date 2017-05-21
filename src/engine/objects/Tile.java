package engine.objects;

/**
 * Created by Isak Wahlqvist on 3/1/2017.
 */
public class Tile extends Sprite {
    public boolean wall = false;

    public Tile(float size, float z){
        super(size, z);
    }

    public void pushBack(Sprite s){
        boolean stillPush = collidesWith(s);
        //Test
        while(stillPush){
            if(s.velocity.x < 0){
                s.position.set(position.x + (size + 10), s.position.y, s.position.z);
            }else if(s.velocity.x > 0){
                s.position.set(position.x - (size + 10), s.position.y, s.position.z);
            }else if(s.velocity.y < 0){
                s.position.set(s.position.x, position.y + (size + 10), s.position.z);
            }else if(s.velocity.y > 0){
                s.position.set(s.position.x, position.y - (size + 10), s.position.z);
            }
            //s.position.set(s.position.x-s.velocity.x/10, s.position.y-s.velocity.y/10, s.position.z);
//            s.velocity.set(s.velocity.x * 2, s.velocity.y * 2, s.velocity.z * 2);
//            s.update();
            stillPush = collidesWith(s);
        }
    }
}
//Alex sux