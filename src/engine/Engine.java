package engine;

import engine.objects.Map;
import engine.objects.Scene;
import engine.rendering.Texture;
import objects.Player;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import scenes.Hallway;

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

    public boolean vSync = false;

    GLFWFramebufferSizeCallback fsCallback;
    public int updateRate = 60;

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
        Texture.init();
    }

    private void loop(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        currentScene = 0;
        Scene s = new Hallway();
        Player thisPlayer = new Player();
        s.add(thisPlayer, 3);
        scenes.add(s);
        long lastTime = System.nanoTime();
        long updateTime = 1000000000/updateRate;
        long oneSecondTime = lastTime + 1000000000;
        update();
        int fps = 0;
        int ups = 0;
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
//                float pixelsDistance;
//                float minimum = Float.MAX_VALUE;
//                for(int i=0; i<Map.collidablePixels.size(); i++)
//                {
//                    pixelsDistance = thisPlayer.position.distance(Map.collidablePixels.get(i));
//                    if(pixelsDistance < minimum)
//                        minimum = pixelsDistance;
//                }
//                System.out.println(minimum);
                System.out.println(Map.collidablePixels.size());
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
        if((currentScene < scenes.size())) {
            scenes.get(currentScene).render();
        }
        glfwSwapBuffers(window);
    }
    public static void main(String[] args){
        new Engine().start();
    }
}
