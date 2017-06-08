package example;
import engine.objects.Scene;
import engine.Engine;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 17aelbashir on 11-Apr-17.
 */
public class StartMenuScene extends Scene {

    long window = Engine.instance.getWindow();
    public StartMenuScene()
    {

    }

    public void update()
    {
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).update(true);
        }
        for (int i = npc.size() - 1; i >= 0; i--) {
            npc.get(i).update();
        }
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            projectiles.get(i).update();
        }
        for (int i = players.size() - 1; i >= 0; i--) {
            players.get(i).update();
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE)
        {
            Engine.instance.currentScene = 1;
        }
    }

    public void render() {
        for (int i = elements.size() - 1; i >= 0; i--) {
            elements.get(i).render();
        }
        for (int i = npc.size() - 1; i >= 0; i--) {
            npc.get(i).render();
        }
        for (int i = projectiles.size() - 1; i >= 0; i--) {
            projectiles.get(i).render();
        }
        for (int i = players.size() - 1; i >= 0; i--) {
            players.get(i).render();
        }
    }
}
