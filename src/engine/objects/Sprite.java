package engine.objects;

import engine.Engine;
import engine.animation.AnimationManager;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.rendering.Quad;
import engine.rendering.Shader;
import engine.rendering.Texture;
import java.util.ArrayList;

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
    public Vector2f textureCoords;
    public boolean isPlayer = false;
    AnimationManager animationManager = new AnimationManager();

    public Sprite(){
        super();
        position = new Vector3f();
        velocity = new Vector3f();
        texture = animationManager.texture;
        shader = new Shader("default");
        textureCoords = new Vector2f(0, 0);
    }

    public Sprite(float size, float z){
        super(size, z);
        position = new Vector3f();
        velocity = new Vector3f();
        shader = new Shader("default");
        texture = animationManager.texture;
        textureCoords = new Vector2f(0, 0);
    }

    public void setTexture(String textureFile){
        animationManager.setTexture(textureFile);
        texture = animationManager.texture;
    }

    public void setTextureCoords(float x, float y){
        textureCoords.x = x;
        textureCoords.y = y;
    }

    public void setTextureCoords(Vector2f v2f){
        textureCoords.x = v2f.x;
        textureCoords.y = v2f.y;
    }


    public void update(){
        shader.bind();
        shader.setUniform("sampler", 1);
        animationManager.update();
        setTextureCoords(animationManager.textureCoord);
        shader.setUniform("texture_coordinate_shift", textureCoords);
        shader.setUniform("projection", currentScene.projection);
        shader.setUniform("position", position);
        if(!isPlayer || !collidesWithColor())
        {
            position.add(velocity);
        }
    }

    public void update(boolean check){
        shader.bind();
        if(check) {
            shader.setUniform("projection", currentScene.projection);
        }else {
            shader.setUniform("sampler", 1);
            shader.setUniform("texture_coordinate_shift", textureCoords);
            shader.setUniform("position", position);
            if(!isPlayer || !collidesWithColor())
            {
                position.add(velocity);
            }
        }
    }

    public boolean collidesWith(Sprite s){
        boolean collidesX = Math.abs(position.x - s.position.x) < (s.size + size);
        boolean collidesY = Math.abs(position.y - s.position.y) < (s.size + size);
        return collidesX && collidesY;
    }

    public boolean collidesWith(Sprite s, Vector3f hyp, Vector2f hypSize){
        boolean collidesX = Math.abs(hyp.x - s.position.x) < (s.size + hypSize.x);
        boolean collidesY = Math.abs(hyp.y - s.position.y) < (s.size + hypSize.y);
        return collidesX && collidesY;
    }
    public boolean collidesWithColor()
    {
        Vector3f hypotheticalPosition = new Vector3f(position.x, position.y, position.z);
        hypotheticalPosition.add(velocity);
        for(Sprite c: Map.collidablePixels) {
            if(collidesWith(c, hypotheticalPosition, new Vector2f(-50, 85))){
                return true;
            }
        }
        return false;
    }

    public void render(){
        shader.bind();
        texture.bind(1);
        super.render();
    }

}
//heyo
//my
//name
//is
//rameez