package engine.math;


/**
 * Created by ISAKPC on 2/13/2017.
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

    public String toString(){
        return "{" + x + ", " + y + ", " + z + "}";
    }
}
