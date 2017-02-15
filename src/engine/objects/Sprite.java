package engine.objects;

import engine.Engine;
import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.rendering.Quad;
import engine.rendering.Shader;
import engine.rendering.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public class Sprite extends Quad {
    public Vector3f position;
    public Vector3f velocity;
    public Texture texture;
    public Shader shader;

    public Sprite(){
        super();
        position = new Vector3f();
        velocity = new Vector3f();
        texture = new Texture("default.png");
        shader = new Shader("default");
    }

    public Sprite(float size, float z){
        super(size, z);
        position = new Vector3f(0, 0, 0);
        velocity = new Vector3f(0, 0, 0);
        texture = new Texture("default.png");
        shader = new Shader("default");
    }

    public void update(){
        velocity.set(velocity.x*0.80f, velocity.y*0.80f, velocity.z);
        position.add(velocity);
        shader.bind();
        shader.setUniform("sampler", 1);
        shader.setUniform("projection", currentScene.projection);
        shader.setUniform("position", position);
    }

    public void render(){
        shader.bind();
        texture.bind(1);
        super.render();
    }

}
