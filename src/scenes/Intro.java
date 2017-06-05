package scenes;

import engine.Engine;
import engine.ThreadManager;
import engine.objects.Scene;
import engine.objects.Sprite;
import engine.sound.Sound;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 5/31/2017.
 */
public class Intro extends Scene {
    int currentRoberts = 0;
    private Sprite roberts1, roberts2, roberts3, roberts4;
    private Sprite blackBar1, blackBar2, passText;
    private boolean shouldRenderText = false;
    public Intro(){
        hasMap = false;

        passText = new Sprite(1024, 576, 0, 1, 1);
        passText.setTexture("text.png");
        passText.currentScene = this;
        blackBar1 = new Sprite(1024, 300, 0, 1, 1);
        blackBar1.setTexture("blackBar.png");
        blackBar2 = new Sprite(1024, 300, 0, 1, 1);
        blackBar2.setTexture("blackBar.png");
        blackBar1.currentScene = this;
        blackBar2.currentScene = this;

        roberts1 = new Sprite(382, 512, 0, 1, 1);
        roberts1.setTexture("roberts1.png");
        roberts1.currentScene = this;
        roberts2 = new Sprite(382, 512, 0, 1, 1);
        roberts2.setTexture("roberts2.png");
        roberts2.currentScene = this;
        roberts3 = new Sprite(1280, 640, 0, 1, 1);
        roberts3.setTexture("roberts3.png");
        roberts3.currentScene = this;
        roberts4 = new Sprite(1024, 512, 0, 1, 1);
        roberts4.setTexture("roberts4.png");
        roberts4.currentScene = this;

        blackBar1.position.y = -300;
        blackBar2.position.y = 350;
        blackBar1.position.x = 10000;
        blackBar2.position.x = 10000;
        roberts1.position.x = 800;
        roberts2.position.x = 800;
        roberts3.position.x = 800;
        roberts4.position.x = 800;
    }
    long switchTimer = System.nanoTime();
    long startTimer = switchTimer;
    int currentStage = 0;
    public void update(){
        if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE) {
            Engine.instance.currentScene = 1;
            Hallway.switchToScene(Engine.instance.scenes.size() - 1);
            ThreadManager.soundToPlay = new Sound("XO Tour Llif3 [8 Bit Tribute to Lil Uzi Vert] - 8 Bit Universe.ogg", 187000);
            ThreadManager.play = true;
        }
        blackBar1.update();
        blackBar2.update();
        if(currentStage == 0) {
            currentRoberts = 2;

            if (roberts1.position.x > -50) {
                roberts1.velocity.x = -2;
                roberts2.velocity.x = -2;
                roberts3.velocity.x = -2;
                roberts4.velocity.x = -2;
            } else {
                roberts1.velocity.x = 0;
                roberts2.velocity.x = 0;
                roberts3.velocity.x = 0;
                roberts4.velocity.x = 0;
                currentStage = 1;
            }
        }else if (currentStage == 1){
            switchTimer = System.nanoTime() + 250000000;
            if(switchTimer < System.nanoTime()) {
                currentRoberts = 3;
            }
            currentStage = 2;
        }else if (currentStage == 2){
            blackBar1.position.x = -50;
            blackBar2.position.x = -50;
            currentRoberts = 0;
            if(blackBar1.position.y > -800 && blackBar2.position.y < 800){
                blackBar1.velocity.y = -1;
                blackBar2.velocity.y = 1;
            }else{
                currentStage = 3;
                startTimer = System.nanoTime() + 1000000000;
            }
        }else if(currentStage == 3) {
            if(startTimer > System.nanoTime()) {
                if (switchTimer < System.nanoTime()) {
                    if (currentRoberts == 0) currentRoberts = 1;
                    else currentRoberts = 0;
                    switchTimer = System.nanoTime() + 125000000;
                }
            }else{
                currentStage = 4;
                startTimer += 2000000000;
            }
        }else if(currentStage == 4){
            shouldRenderText = true;
            if(startTimer < System.nanoTime()){
                Engine.instance.currentScene = 1;
                Hallway.switchToScene(Engine.instance.scenes.size() - 1);
                ThreadManager.soundToPlay = new Sound("XO Tour Llif3 [8 Bit Tribute to Lil Uzi Vert] - 8 Bit Universe.ogg", 187000);
                ThreadManager.play = true;
            }
        }
        roberts1.update();
        roberts2.update();
        roberts3.update();
        roberts4.update();
        passText.update();
    }

    public void render(){
        switch(currentRoberts){
            case 0:
                roberts1.render();
                break;
            case 1:
                roberts2.render();
                break;
            case 2:
                roberts3.render();
                break;
            case 3:
                roberts4.render();
                break;
        }
        blackBar1.render();
        blackBar2.render();
        if(shouldRenderText) {
            passText.render();
        }
    }

}
