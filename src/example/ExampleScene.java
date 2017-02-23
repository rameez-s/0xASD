package example;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Map;
import engine.objects.Projectile;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.CreatureMightRename;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public class ExampleScene extends Scene {
    long window = Engine.instance.getWindow();

    public ExampleScene(){
        setMap("ColorTest.png");
        genMap();
    }

    public void update() {
        super.update();
    }

    public void render(){
        super.render();
    }
}
