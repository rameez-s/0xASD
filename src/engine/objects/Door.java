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
    Character direction;
    Texture texture1 = new Texture ("Door Spritesheet.png");

    public Door()
    {
        super();
        texture = texture1;
        setHypotheticalSize();
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

        boolean characterIsFacingHere = false;
        if(ply.facingUp && direction=='D')
        {
            characterIsFacingHere = true;
        }
        if(ply.facingDown && direction=='U')
        {
            characterIsFacingHere = true;
        }
        if(ply.facingRight && direction=='L')
        {
            characterIsFacingHere = true;
        }
        if(ply.facingLeft && direction == 'R')
        {
            characterIsFacingHere = true;
        }
        return(collidesX && collidesY && characterIsFacingHere);
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
