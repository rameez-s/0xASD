package engine;

import engine.objects.Scene;
import engine.objects.StartMenuSprite;
import engine.rendering.Texture;
import engine.sound.AudioManager;
import engine.sound.Sound;
import engine.util.Save;
import example.StartMenuScene;
import objects.Player;
import org.lwjgl.glfw.GLFWFramebufferSizeCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;
import scenes.*;

import java.io.*;
import java.util.ArrayList;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

/**
 * Created by Isak Wahlqvist
 */
public class Engine {
    //Define the variables
    private long window;
    Thread audioManager;

    public Save save = new Save();

    public static Engine instance;

    public int currentScene;
    public ArrayList<Scene> scenes = new ArrayList<>();

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
        saveData();
    }

    //Save the game data
    private void saveData(){
        try {
            FileOutputStream outputStream = new FileOutputStream("save.dat");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(save);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


    //Set up everything for use
    private void init(){

        if(!glfwInit()){
            System.out.println("GLHF");
            System.exit(1);
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        boolean fullscreen = false;
        if(fullscreen) {
            glfwWindowHint(GLFW_RED_BITS, vidMode.redBits());
            glfwWindowHint(GLFW_GREEN_BITS, vidMode.greenBits());
            glfwWindowHint(GLFW_BLUE_BITS, vidMode.blueBits());
            glfwWindowHint(GLFW_REFRESH_RATE, vidMode.refreshRate());

            window = glfwCreateWindow(vidMode.width(), vidMode.height(), "Game", glfwGetPrimaryMonitor(), 0);
        }else {
            glfwWindowHint(GLFW_RESIZABLE, GLFW_FALSE);
            int height = 720;
            int width = 1280;
            window = glfwCreateWindow(width, height, "Game", 0, 0);
            glfwSetWindowPos(window, (vidMode.width() - width) / 2, (vidMode.height() - height) / 2);

        }

        boolean vSync = false;
        if (vSync) {
            glfwSwapInterval(1);
        }else{
            glfwSwapInterval(0);
        }

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        audioManager = new Thread(new Runnable() {
            @Override
            public void run() {
                AudioManager am = new AudioManager();
            }
        });
        audioManager.start();
        ThreadManager.play = false;

        boolean loadData = false;
        if(loadData){
            try {
                FileInputStream inputStream = new FileInputStream("save.dat");
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                save = (Save) objectInputStream.readObject();
                System.out.println(save.completedGym);
            } catch (java.io.IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Texture.init();
    }

    private void loop(){
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE ) {
                try {
                    System.out.println(audioManager.getState());
                    ThreadManager.shouldStop = true;
                    System.out.println(audioManager.getState());
                }catch(Exception e){
                    e.printStackTrace();
                }
                glfwSetWindowShouldClose(window, true);
            }
        });
        //Add the scenes to the Engine
        currentScene = 0;
        Scene intro = new Intro();
        scenes.add(intro);
        Scene s = new Hallway();
        Player thisPlayer = new Player();
        thisPlayer.setTexture("characterSheet.png");
        s.add(thisPlayer, 3);
        scenes.add(s);
        Scene gym = new Gym();
        scenes.add(gym);

        Scene english = new English();
        scenes.add(english);

        Scene geography = new Geography();
        scenes.add(geography);

        Scene music = new Music();
        scenes.add(music);

        Scene robertsOffice = new RobertsOfficeScene();
        scenes.add(robertsOffice);

        //Set up the main loop
        long lastTime = System.nanoTime();
        int updateRate = 60;
        long updateTime = 1000000000/ updateRate;
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
                ups = 0;
                fps = 0;
                oneSecondTime += 1000000000;
            }
        }

    }

    //Remove the save
    public void clearSave(){
        save = new Save();
        File f = new File("save.dat");
        System.out.println(f.getAbsolutePath());
        boolean b = f.delete();
        System.out.println(b);
    }

    //Update the currentScene
    private void update(){
        if((currentScene < scenes.size())) {
            scenes.get(currentScene).update();
        }
        glfwPollEvents();
    }

    //Render the currentScene
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
