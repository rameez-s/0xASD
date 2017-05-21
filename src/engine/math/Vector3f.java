package engine.math;


/**
 * Created by Isak Wahlqvist on 2/13/2017.
 */
public class Vector3f {
    public float x, y, z;

    public Vector3f(){
        x = 0.0f;
        y = 0.0f;
        z = 0.0f;
    }

    public Vector3f(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void add(Vector3f vector3f){
        x += vector3f.x;
        y += vector3f.y;
        z += vector3f.z;
    }

    public void subtract(Vector3f vector3f){
        x -= vector3f.x;
        y -= vector3f.y;
        z -= vector3f.z;
    }

    public void subtract(Vector2f vector3f){
        x -= vector3f.x;
        y -= vector3f.y;
    }

    public float distance(Vector3f other){
        Vector3f v = new Vector3f(x, y, z);
        v.subtract(other);
        return (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }
    public float distance(Vector2f other){
        Vector3f v = new Vector3f(x, y, z);
        v.subtract(other);
        return (float) Math.sqrt(Math.pow(v.x, 2) + Math.pow(v.y, 2));
    }
    //Test

    public Vector2f pointTowards(Vector2f other){
        Vector2f v = new Vector2f(x, y);
        v.subtractInv(other);
        v.x = (float)Math.tanh(v.x/20);
        v.y = (float)Math.tanh(v.y/20) * 16.0F/9.0F;
        return v;
    }

    public String toString(){
        return "{" + x + ", " + y + ", " + z + "}";
    }
}
