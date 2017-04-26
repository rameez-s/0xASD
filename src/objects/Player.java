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
//mostly Alex
public class Player extends Sprite
{
    public boolean facingRight = true;
    private boolean fireReady = true;
    private long fireTime = 250000000;
    private long previousFireTime;
    private boolean useArrowKeys = false;
    public Character direction = 'D';
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
                    facingRight = true;
                }
                if (glfwGetKey(window, GLFW_KEY_LEFT) == GL_TRUE) {
                    velocity.x -= 1.6f;
                    animationManager.run("RunLeft");
                    facingRight = false;
                }
                if (glfwGetKey(window, GLFW_KEY_UP) == GL_TRUE) {
                    velocity.y += 2.8444f;
                    animationManager.run("RunUp");
                }
                if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_TRUE) {
                    velocity.y -= 2.8444f;
                    animationManager.run("RunDown");
                }
                if (glfwGetKey(window, GLFW_KEY_DOWN) == GL_FALSE && glfwGetKey(window, GLFW_KEY_UP) == GL_FALSE && glfwGetKey(window, GLFW_KEY_RIGHT) == GL_FALSE && glfwGetKey(window, GLFW_KEY_LEFT) == GL_FALSE) {
                    animationManager.stop();
                }
            }else{
                if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                    velocity.x += 1.6f;
                    animationManager.run("RunRight");
                    direction = 'R';
                    facingRight = true;
                }
                if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                    velocity.x -= 1.6f;
                    animationManager.run("RunLeft");
                    direction = 'L';
                    facingRight = false;
                }
                if (glfwGetKey(window, GLFW_KEY_W) == GL_TRUE) {
                    velocity.y += 2.8444f;
                    animationManager.run("RunUp");
                    direction = 'U';
                }
                if (glfwGetKey(window, GLFW_KEY_S) == GL_TRUE) {
                    velocity.y -= 2.8444f;
                    animationManager.run("RunDown");
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
        }
        velocity.set(velocity.x * 0.8f, velocity.y * 0.8f, 0);
    }
}
