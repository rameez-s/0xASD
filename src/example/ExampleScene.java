package example;

import engine.Engine;
import engine.math.Vector3f;
import engine.objects.Projectile;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.rendering.Quad;

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
                Sprite s = (Sprite) elements.get(i);
                if (glfwGetKey(window, GLFW_KEY_D) == GL_TRUE) {
                    s.velocity.x += 3;
                }
                if (glfwGetKey(window, GLFW_KEY_A) == GL_TRUE) {
                    s.velocity.x -= 3;
                }

                if(glfwGetKey(window, GLFW_KEY_ENTER) == GL_TRUE){
                    Projectile s2 = new Projectile(16f, 1, 500000000);
                    s2.startTime = System.nanoTime();
                    add(s2);
                    Sprite s1 = (Sprite)elements.get(0);
                    s2.position = new Vector3f(s1.position.x, s1.position.y,s1.position.z);
                    s2.velocity.x = 100;
                    s2.velocity.add(new Vector3f(s1.velocity.x, 0, 0));
                    System.out.println(elements.size());
                }
                if (s.position.y > -256) {
                    s.velocity.y -= 10;
                } else {
                    s.position.y = -257;
                    s.velocity.y = 0;
                    if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE) {
                        s.velocity.y = 70;
                    }
                }
            }else if(i == 1){
                Sprite s = (Sprite)elements.get(1);
                if(s.collidesWith((Sprite) elements.get(0))){
                    System.out.println("Collides");
                }
            }
        }
        super.update();
    }

    public void render(){
        super.render();
    }
}
