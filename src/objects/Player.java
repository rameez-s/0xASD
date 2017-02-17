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
 * Created by 18iwahlqvist on 2/16/2017.
 */
public class Player extends Sprite {
    public boolean facingRight = true;
    private boolean fireReady = true;
    private long fireTime = 250000000;
    private long previousfireTime;
    long window = Engine.instance.getWindow();
    public void update(){
        super.update();
        if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
            velocity.x += 3;
            facingRight = true;
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
            velocity.x -= 3;
            facingRight = false;
        }
        if(fireReady == true) {
            if (glfwGetKey(window, GLFW_KEY_ENTER) == GL_TRUE) {
                Projectile s2 = new Projectile(16f, 1, 500000000);
                s2.startTime = System.nanoTime();
                currentScene.add(s2);
                s2.position = new Vector3f(position.x, position.y, position.z);
                if(facingRight) {
                    s2.velocity.x = 100;
                }else{
                    s2.velocity.x = -100;
                }
                s2.velocity.add(new Vector3f(velocity.x, 0, 0));

                fireReady = false;
                previousfireTime = System.nanoTime();
            }
        }else{
            if((previousfireTime + fireTime) < System.nanoTime()){
                fireReady = true;
            }
        }
        if (position.y > -256) {
            velocity.y -= 10;
        } else {
            position.y = -257;
            velocity.y = 0;
            if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {
                velocity.y = 70;
            }
        }
        velocity.set(velocity.x * 0.8f, velocity.y * 0.8f, 0);
    }
}
