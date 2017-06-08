package objects;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Projectile;
import engine.objects.Sprite;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 17aelbashir on 23-Feb-17.
 */

public class ControlScheme2 extends Sprite {
    public boolean facingRight = true;
    private boolean fireReady = true;
    private long previousFireTime;

    public boolean controllable = true;
    long window = Engine.instance.getWindow();
    public void update(){
        super.update();
        if(controllable) {

            if (glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE) {
                velocity.x += 0.8f;
                facingRight = true;
            }
            if (glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE) {
                velocity.x -= 0.8f;
                facingRight = false;
            }
            if (fireReady == true) {
                if (glfwGetKey(window, GLFW_KEY_ENTER) == GL_TRUE) {
                    Projectile s2 = new Projectile(16f, 1, 1000000000);
                    s2.setTexture("projectile.png");
                    s2.startTime = System.nanoTime();
                    currentScene.add(s2, 2);
                    if (facingRight) {
                        s2.position = new Vector3f(position.x + 35, position.y, position.z);
                        s2.velocity.x = 7f;
                    } else {
                        s2.position = new Vector3f(position.x - 35, position.y, position.z);
                        s2.velocity.x = -7f;
                    }
                    s2.velocity.add(new Vector3f(velocity.x, 0, 0));

                    fireReady = false;
                    previousFireTime = System.nanoTime();
                }
            } else {
                long fireTime = 250000000;
                if ((previousFireTime + fireTime) < System.nanoTime()) {
                    fireReady = true;
                }
            }
        }
        if (position.y > -256) {
            velocity.y -= 2.5f;
        } else {
            position.y = -257;
            velocity.y = 0;
            if(controllable) {
                if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {
                    velocity.y = 45;
                }
            }
        }
        velocity.set(velocity.x * 0.9f, velocity.y * 0.9f, 0);
    }
}
