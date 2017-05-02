package engine;

import engine.math.Vector3f;
import engine.objects.*;
import engine.objects.Scene;
import engine.objects.StartMenuSprite;
import engine.rendering.Texture;
import example.ExampleScene;
import example.StartMenuScene;
import objects.Player;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.openal.AL;
import org.lwjgl.openal.ALC;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.opengl.GL;
import scenes.English;
import scenes.Gym;
import scenes.Hallway;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.openal.ALC10.*;
import static org.lwjgl.openal.EXTThreadLocalContext.alcSetThreadContext;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;
import static sun.audio.AudioDevice.device;

/**
 * Created by Isak Wahlqvist
 */
public class Engine {
    //Test
    private long window, audioDevice, context;

    private int width = 1280, height = 720;
    private boolean fullscreen = false;

    public static Engine instance;

    public int currentScene;
    public ArrayList<Scene> scenes = new ArrayList<>();

    private boolean vSync = false;

    GLFWFramebufferSizeCallback fsCallback;
    private int updateRate = 60;

    public long getWindow() {
        return window;
    }

    private void start(){
        instance = this;
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }



    private void init(){
        if(!glfwInit()){
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

        audioDevice = alcOpenDevice((ByteBuffer) null);
        if(audioDevice == NULL)
            throw new Error("Device not found");

        ALCCapabilities deviceCaps = ALC.createCapabilities(audioDevice);

        context = alcCreateContext(audioDevice, (IntBuffer)null);
        alcSetThreadContext(context);
        AL.createCapabilities(deviceCaps);

        Texture.init();
    }

    private void loop(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });
        currentScene = 0;
        Scene english = new English();
        scenes.add(english);
        //Start
        Scene startMenu = new StartMenuScene();
        StartMenuSprite startMenuSprite = new StartMenuSprite();
        startMenu.add(startMenuSprite, 1);
        scenes.add(startMenu);

        Scene s = new Hallway();
        Player thisPlayer = new Player();
        thisPlayer.setTexture("characterSheet.png");
        english.add(thisPlayer, 3);
        scenes.add(s);
        Scene s2 = new ExampleScene();
        scenes.add(s2);
/*        Scene gym = new Gym();
        scenes.add(gym);*/
        //s2.add(thisPlayer, 3);
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
                ups = 0;
                fps = 0;
                oneSecondTime += 1000000000;
            }
        }

        alcMakeContextCurrent(NULL);
        alcDestroyContext(context);
        alcCloseDevice(audioDevice);

    }

    private void update(){
        if((currentScene < scenes.size())) {
            scenes.get(currentScene).update();
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
//isak is lame