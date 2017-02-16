package engine.rendering;

import com.sun.deploy.util.BufferUtil;
import engine.objects.Scene;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created by 18iwahlqvist on 2/12/2017.
 */
public class Quad {
    private int v_id, t_id, i_id;
    private int draw_count;
    public Scene currentScene;
    public float size;
    public Quad(){
        this(0.2f, 0.0f);
    }

    public Quad(float size, float z){
        this.size = size;
        float[] vertices = {
                -size, size * (16.0f/9), z,
                size, size * (16.0f/9), z,
                size, -size * (16.0f/9), z,
                -size, -size * (16.0f/9), z
        };

        float[] tcs = {
                1, 0,
                0, 0,
                0, 1,
                1, 1
        };

        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };

        draw_count = indices.length;

        v_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glBufferData(GL_ARRAY_BUFFER, engine.util.BufferUtil.createFloatBuffer(vertices), GL_STATIC_DRAW);

        t_id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glBufferData(GL_ARRAY_BUFFER, engine.util.BufferUtil.createFloatBuffer(tcs), GL_STATIC_DRAW);

        i_id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, engine.util.BufferUtil.createIntBuffer(indices), GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

    }

    public void update(){

    }

    public void render(){
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, v_id);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, t_id);
        glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, i_id);
        glDrawElements(GL_TRIANGLES, draw_count, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
    }
}
