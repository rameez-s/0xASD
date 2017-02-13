package engine.util;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Created by ISAKPC on 2/13/2017.
 */
public class BufferUtil {
    public static FloatBuffer createFloatBuffer(float[] array){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }

    public static IntBuffer createIntBuffer(int[] array){
        IntBuffer buffer = BufferUtils.createIntBuffer(array.length);
        buffer.put(array);
        buffer.flip();
        return buffer;
    }
}
