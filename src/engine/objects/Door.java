package engine.objects;

import engine.Engine;
import objects.Player;
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
 * Created by 17aelbashir on 23-Mar-17.
 */
public class Door extends Sprite {
    private Character direction;
    private Vector2f hypotheticalSize;

    public Door()
    {
        super(160, 1);
        texture = new Texture("Door Spritesheet.png");
        setHypotheticalSize();
        direction = 'D';
    }
    public Door(float x, float y, Character dir)
    {
        super(160, 1);
        texture = new Texture("Door Spritesheet.png");
        position.x = x;
        position.y = y;
        position.z = 1;
        direction = dir;
        setHypotheticalSize();
    }
    public boolean isClose()
    {
        Player ply = (Player)(currentScene.players.get(0));
        boolean collidesX = Math.abs(position.x - ply.position.x) < (60);
        boolean collidesY = Math.abs(position.y - ply.position.y) < (150);
        return(collidesX && collidesY);
    }
    public void update()
    {
        super.update();
        if(isClose())
        {
            setTextureCoords(0.125f, 0);
        }
        else
        {
            setTextureCoords(0, 0);
        }
    }
    public void setHypotheticalSize()
    {
        hypotheticalSize = new Vector2f (-100, -60);
    }
}
