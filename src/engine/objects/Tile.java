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
            stillPush = collidesWith(s);
        }
    }
}
