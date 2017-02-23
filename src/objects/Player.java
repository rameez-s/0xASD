package objects;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Projectile;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 2/16/2017.
 */
public class Player extends CreatureMightRename {
    public boolean facingRight = true;
    private boolean fireReady = true;
    private long fireTime = 250000000;
    private long previousFireTime;

    public boolean controllable = true;
    long window = Engine.instance.getWindow();
    public void update(){
        super.update();
        if(controllable) {
            if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                velocity.x += 0.8f;
                facingRight = true;
            }
            if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                velocity.x -= 0.8f;
                facingRight = false;
            }
            if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                velocity.y += 0.8f;
            }
            if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
                velocity.y -= 0.8f;
            }
            currentScene.projection.translate(new Vector3f(position.x, position.y, 0));
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
                if ((previousFireTime + fireTime) < System.nanoTime()) {
                    fireReady = true;
                }
            }
    }
        velocity.set(velocity.x * 0.9f, velocity.y * 0.9f, 0);
    }
}
