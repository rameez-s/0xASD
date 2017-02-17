package example;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Projectile;
import engine.objects.Scene;
import engine.objects.Sprite;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public class ExampleScene extends Scene {
    long window = Engine.instance.getWindow();
    public void update() {
        for (int i = 0; i < elements.size(); i++) {
            if(i < 1) {
            }else if(i == 1){
                Sprite s = (Sprite)elements.get(1);
                for (int j = 0; j < elements.size(); j++) {
                    if(j != 1) {
                        if (s.collidesWith((Sprite) elements.get(j))) {
                            System.out.println(j + " collided with " + i);
                        }
                    }
                }
            }
        }
        super.update();
    }

    public void render(){
        super.render();
    }
}
