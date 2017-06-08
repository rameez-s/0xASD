package engine.math;
import java.nio.FloatBuffer;

//Class for the camera
public class Matrix4f {

    public static final int SIZE = 4 * 4;
    public float[] elements = new float[SIZE];

    public Matrix4f() {
    }

    public static Matrix4f identity() {
        Matrix4f result = new Matrix4f();
        for (int i = 0; i < SIZE; i++) {
            result.elements[i] = 0.0f;
        }
        result.elements[0 + 0 * 4] = 1.0f;
        result.elements[1 + 1 * 4] = 1.0f;
        result.elements[2 + 2 * 4] = 1.0f;
        result.elements[3 + 3 * 4] = 1.0f;

        return result;
    }

    //Set the Matrix4f
    public static Matrix4f orthographic(float left, float right, float bottom, float top, float near, float far) {
        Matrix4f result = identity();

        result.elements[0 + 0 * 4] = 2.0f / (right - left);

        result.elements[1 + 1 * 4] = 2.0f / (top - bottom);

        result.elements[2 + 2 * 4] = 2.0f / (near - far);

        result.elements[0 + 3 * 4] = (left + right) / (left - right);
        result.elements[1 + 3 * 4] = (bottom + top) / (bottom - top);
        result.elements[2 + 3 * 4] = (far + near) / (far - near);

        return result;
    }

    public void move(Vector3f vector3f){
        elements[0 + 3 * 4] -= vector3f.x;
        elements[1 + 3 * 4] -= vector3f.y;
        elements[2 + 3 * 4] += vector3f.z;
    }

    public void setPosition(Vector3f vector3f){
        elements[0 + 3 * 4] = vector3f.x/-512f;
        elements[1 + 3 * 4] = vector3f.y/-512f;
        elements[2 + 3 * 4] = 1;
    }

    public FloatBuffer toFloatBuffer() {
        return engine.util.BufferUtil.createFloatBuffer(elements);
    }

}
