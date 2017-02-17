package engine;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.rendering.Quad;
import engine.rendering.Shader;
import engine.rendering.Texture;
import example.ExampleScene;
import objects.Player;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.glActiveTexture;

/**
 * Created by 18iwahlqvist on 2/12/2017.
 */
public class Engine {
    private long window;

    private int width = 1280, height = 720;
    private boolean fullscreen = false;

    public static Engine instance;

    GLFWFramebufferSizeCallback fsCallback;
    int updateRate = 60;

    public long getWindow() {
        return window;
    }

    public void start(){
        instance = this;
        init();
        loop();
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
        }else{
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            window = glfwCreateWindow(width, height, "Game", 0, 0);
            glfwSetWindowPos(window, (vidMode.width()-width)/2, (vidMode.height()-height)/2);

        }


        glfwSetFramebufferSizeCallback(window, fsCallback = new GLFWFramebufferSizeCallback() {
            public void invoke(long window, int w, int h) {
                if (w > 0 && h > 0) {
                    width = w;
                    height = h;
                }
            }
        });


        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }
    Scene s;
    private void loop(){
        s = new ExampleScene();
        s.add(new Player());
        Sprite sprite = new Sprite(32, 1);
        sprite.position = new Vector3f(-300, -257, 1);
        s.add(sprite);
        long lastTime = System.nanoTime();
        long updateTime = 1000000000/updateRate;
        long oneSecondTime = lastTime + 1000000000;
        update();
        int fps = 0;
        int ups = 0;
        while(!glfwWindowShouldClose(window)){
            long now = System.nanoTime();
            if(now > (lastTime + updateTime)){
                lastTime = now;
                update();
                ups++;
            }
            fps++;
            render();
            if(System.nanoTime() > oneSecondTime){
                System.out.println("Frames per second: " + fps + "\tUpdates per second: " + ups);
                ups = 0;
                fps = 0;
                oneSecondTime += 1000000000;
            }
        }

    }

    private void update(){
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
            glfwSetWindowShouldClose(window, true);
        }

        s.update();
        glfwPollEvents();
    }

    private void render(){
        glClear(GL_COLOR_BUFFER_BIT);
        s.render();
        glfwSwapBuffers(window);
    }

    public static void main(String[] args){
        new Engine().start();
    }
}
