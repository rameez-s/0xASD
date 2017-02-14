package engine;

import engine.math.Matrix4f;
import engine.math.Vector3f;
import engine.rendering.Quad;
import engine.rendering.Shader;
import engine.rendering.Texture;
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
    int updateRate = 60;
    public void start(){
        init();
        loop();
        glfwTerminate();
    }

    private void init(){
        if(glfwInit() == false){
            System.out.println("GLFW Init is not working");
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

        glfwShowWindow(window);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();
    }

    Quad q;
    Texture t;
    Shader s;
    Matrix4f projection;
    Vector3f position;
    private void loop(){
        q = new Quad(32, 0);
        t = new Texture("default.png");
        s = new Shader("default");
        projection = new Matrix4f().orthographic(-512, 512, -512, 512, 10, -10);
        position = new Vector3f(1, 0, 0);
        long lastTime = System.nanoTime();
        long updateTime = 1000000000/updateRate;
        update();
        while(!glfwWindowShouldClose(window)){
            long now = System.nanoTime();
            if(now > (lastTime + updateTime)){
                System.out.println("Updated");
                lastTime = now;
                update();
            }
            render();
        }

    }

    private void update(){
        if(glfwGetKey(window, GLFW_KEY_ESCAPE) == GL_TRUE){
            glfwSetWindowShouldClose(window, true);
        }
        if(glfwGetKey(window, GLFW_KEY_D) == GL_TRUE){
            position.x += 3;
        }
        if(glfwGetKey(window, GLFW_KEY_A) == GL_TRUE){
            position.x -= 3;
        }
        if(glfwGetKey(window, GLFW_KEY_SPACE) == GL_TRUE){
            position.y++;
        }
        if(position.y > -256) {
            position.y -= 9.81;
        }else{
            position.y = -257;
        }
        glfwPollEvents();
        s.bind();
        s.setUniform("sampler", 1);
        s.setUniform("projection", projection);
        s.setUniform("position", position);
    }

    private void render(){
        glClear(GL_COLOR_BUFFER_BIT);
        t.bind(1);
        q.render();
        glfwSwapBuffers(window);
    }

    public static void main(String[] args){
        new Engine().start();
    }
}
