package engine.math;

/**
 * Created by Isak Wahlqvist on 2/27/2017.
 */
public class Vector2f {
    public float x, y;

    public Vector2f(float x, float y){
        this.x = x;
        this.y = y;
    }

    public void set(float x, float y){
        this.x = x;
        this.y = y;
    }

    public static Vector2f subtract(Vector2f a, Vector2f b){
        Vector2f newV = new Vector2f(0, 0);
        newV.x = a.x - b.x;
        newV.y = a.y - b.y;
        return newV;
    }

    public void subtractInv(Vector2f vector3f){
        x = vector3f.x - x;
        y = vector3f.y - y;
    }
    public String toString(){
        return "{" + x + ", " + y + "}";
    }
}
