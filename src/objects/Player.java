package objects;

import engine.Engine;
import engine.animation.Animation;
import engine.animation.AnimationManager;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Map;
import engine.objects.Projectile;
import engine.objects.Sprite;
import engine.rendering.Texture;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_FALSE;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 2/16/2017.
 */
//and alex
//mostly Isak
public class Player extends Sprite
{
    public Character direction = 'D';
    private boolean fireReady = true;
    private long fireTime = 250000000;
    private long previousFireTime;
    private boolean useArrowKeys = false;
    public Player(){
        super(128f, 0f);
        isPlayer = true;
        setTexture("characterSheet.png");
        animationManager.add(new Animation("RunRight", new Vector2f(0.125f * 3, 0), 0.15f, 1));
        animationManager.add(new Animation("RunLeft", new Vector2f(0.125f * 2, 0), 0.15f, 1));
        animationManager.add(new Animation("RunUp", new Vector2f(0.125f * 1, 0), 0.2f, 2));
        animationManager.add(new Animation("RunDown", new Vector2f(0.125f * 0, 0), 0.2f, 2));
        animationManager.run("RunDown");
    }

    public boolean controllable = true;
    long window = Engine.instance.getWindow();

    public void update(){
        super.update();
        if(controllable) {
            if(useArrowKeys) {
                if (glfwGetKey(window, GLFW_KEY_RIGHT) == GL_TRUE) {
                    velocity.x += 1.6f;
                    animationManager.run("RunRight");
                    direction = 'R';
                }
                if (glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE) {
                    velocity.x -= 1.6f;
                    animationManager.run("RunLeft");
                    direction = 'L';
                }
                if (glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {
                    velocity.y += 2.8444f;
                    direction = 'U';
                }
                if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {
                    velocity.y -= 2.8444f;
                    direction = 'D';
                }
                if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_FALSE && glfwGetKey(window, GLFW_KEY_UP) == GL_FALSE && glfwGetKey(window, GLFW_KEY_RIGHT) == GL_FALSE && glfwGetKey(window, GLFW_KEY_LEFT) == GL_FALSE) {
                    animationManager.stop();
                }
            }else{
                if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                    velocity.x += 1.6f;
                    animationManager.run("RunRight");
                    direction = 'R';
                }
                if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                    velocity.x -= 1.6f;
                    animationManager.run("RunLeft");
                    direction = 'L';
                }
                if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                    velocity.y += 2.8444f;
                    direction = 'U';
                }
                if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
                    velocity.y -= 2.8444f;
                    direction = 'D';
                }
                if (glfwGetKey(window, GLFW_KEY_S) == GL_FALSE && glfwGetKey(window, GLFW_KEY_W) == GL_FALSE && glfwGetKey(window, GLFW_KEY_D) == GL_FALSE && glfwGetKey(window, GLFW_KEY_A) == GL_FALSE) {
                    animationManager.stop();
                }
            }
            if (collidesWithColor())
            {
                velocity.x = 0;
                velocity.y = 0;
            }
            currentScene.projection.move(new Vector3f(velocity.x/640, velocity.y/640, 0));
            if (fireReady == true) {
                if (glfwGetKey(window, GLFW_KEY_ENTER) == GL_TRUE) {
                    Projectile s2 = new Projectile(16f, 1, 1000000000);
                    s2.setTexture("projectile.png");
                    s2.startTime = System.nanoTime();
                    System.out.println("TEST");
                    currentScene.add(s2, 2);
                    if (direction == 'R') {
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
        velocity.set(velocity.x * 0.8f, velocity.y * 0.8f, 0);
    }
}
