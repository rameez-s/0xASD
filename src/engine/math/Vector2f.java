package engine.math;

/**
 * Created by 18iwahlqvist on 2/27/2017.
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

    public boolean equals(Vector2f other)
    {
        if((int)x==(int)other.x && (int)y==(int)other.y)
        {
            return true;
        }
        return false;
    }
}
