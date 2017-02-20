package engine;

import engine.math.Vector3f;
import engine.multiplayer.client.Client;
import engine.objects.Scene;
import example.ExampleScene;
import objects.CreatureMightRename;
import objects.Player;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by 18iwahlqvist on 2/12/2017.
 */
public class Engine {
    private long window;

    private int width = 1280, height = 720;
    private boolean fullscreen = false;

    public static Engine instance;

    public int currentScene;
    public ArrayList<Scene> scenes = new ArrayList<>();

    public boolean vSync = true;

    GLFWFramebufferSizeCallback fsCallback;
    public int updateRate = 120;

    public long getWindow() {
        return window;
    }

    public void start(){
        instance = this;
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }



    private void init(){
        if(glfwInit() == false){
            System.out.println("GLHF");
            System.exit(1);
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        if(fullscreen) {
            glfwWindowHint(GLFW_RED_BITS, vidMode.redBits());
            glfwWindowHint(GLFW_GREEN_BITS, vidMode.greenBits());
            glfwWindowHint(GLFW_BLUE_BITS, vidMode.blueBits());
            glfwWindowHint(GLFW_REFRESH_RATE, vidMode.refreshRate());

            window = glfwCreateWindow(vidMode.width(), vidMode.height(), "Game", glfwGetPrimaryMonitor(), 0);
        }else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            window = glfwCreateWindow(width, height, "Game", 0, 0);
            glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        }

        if (vSync) {
            glfwSwapInterval(1);
        }else{
            glfwSwapInterval(0);
        }

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

    }
    private void loop(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        Scene s = new ExampleScene();
        s.add(new Player(), 3);
        CreatureMightRename sprite = new CreatureMightRename();
        sprite.test = true;
        sprite.position = new Vector3f(-300, -257, 1);
        s.add(sprite, 1);
        scenes.add(s);
        currentScene = 0;
        long lastTime = System.nanoTime();
        long updateTime = 1000000000/updateRate;
        long oneSecondTime = lastTime + 1000000000;
        update();
        int fps = 0;
        int ups = 0;
        new Client();
        while(!glfwWindowShouldClose(window)){
            long now = System.nanoTime();
            if(now >= (lastTime + updateTime)){
                lastTime = now;
                update();
                ups++;
            }

            render();
            fps++;
            if(System.nanoTime() > oneSecondTime){
                System.out.println("Frames per second: " + fps + "\tUpdates per second: " + ups);
                ups = 0;
                fps = 0;
                oneSecondTime += 1000000000;
            }
        }

    }

    private void update(){
        for(Scene s : scenes) {
            if(s.backgroundUpdate) {
                s.update();
            }
        }
        glfwPollEvents();
    }

    private void render(){
        glClear(GL_COLOR_BUFFER_BIT);
        scenes.get(currentScene).render();
        glfwSwapBuffers(window);
    }



    public static void main(String[] args){
        new Engine().start();
    }
}
