package scenes;

import engine.Engine;
import engine.animation.Animation;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.EmptyChair;
import objects.npcs.MrDiamond;

import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_RIGHT;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_SPACE;
import static org.lwjgl.glfw.GLFW.glfwGetKey;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by 18iwahlqvist on 4/26/2017.
 */ // isak is idiot
public class English extends Scene {
    //Test
    private boolean hasCopiedAnswers;
    public boolean sittingInChair;
    MrDiamond mrDiamond;
    EmptyChair emptyChair;
    ArrayList<Sprite> chairs = new ArrayList<>();
    ArrayList<Sprite> tables = new ArrayList<>();
    int currentPersonToCheat = -1;
    int currentCheatingProgress = 0;
    Sprite arrow;
    int timesCheated = -1;
    public English(){
        setMap("hallway.png");
        genMap();
        for (int x = 0; x < 5; x++) {
            for (int y = 0; y < 3; y++) {
                tables.add(new Sprite(128, 0));
                chairs.add(new Sprite(128, 0));
                tables.get((x*3)+y).setTexture("characterSheet.png");
                chairs.get((x*3)+y).setTexture("characterSheet.png");
                tables.get((x*3)+y).animationManager.textureCoord = new Vector2f(0.125f, 1-0.125f*2);
                chairs.get((x*3)+y).animationManager.textureCoord = new Vector2f(0.125f * (int)(Math.random() * 4 + 1), 1 - 0.125f);
                tables.get((x*3)+y).position.set((-1025 + 400 * x), (100+(400*y)), 0);
                chairs.get((x*3)+y).position.set((-1000 + 400 * x), (100+(400*y)), 0);
                System.out.println(chairs.get((x*3)+y).position + "\t" + ((x*3)+y));
            }
        }
        arrow = new Sprite(128, 0);
        arrow.setTexture("characterSheet.png");
        arrow.animationManager.textureCoord = new Vector2f(0.125f*2, 1-0.125f*2);
        arrow.currentScene = this;
        emptyChair = new EmptyChair();
        emptyChair.currentScene = this;

        mrDiamond = new MrDiamond();
        mrDiamond.currentScene = this;
    }
    long spaceTimer = 0;

    public void update(){
        if((currentCheatingProgress == 100 && hasCopiedAnswers) || currentPersonToCheat == -1){
            currentPersonToCheat = (int)(Math.random() * 15);
            currentCheatingProgress = 0;
            hasCopiedAnswers = false;
            timesCheated++;
        }
        if(timesCheated > 4){
            //Win
            Hallway.switchToScene(1);
        }
        if(currentCheatingProgress == 100) {
            arrow.position = new Vector3f(emptyChair.position.x + 10, emptyChair.position.y + 120, emptyChair.position.z);
        }else{
            arrow.position = new Vector3f(chairs.get(currentPersonToCheat).position.x + 10, chairs.get(currentPersonToCheat).position.y + 150, chairs.get(currentPersonToCheat).position.z);
        }
        arrow.update();

        if (glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE) {
            if(players.get(0).position.distance(emptyChair.position) < 75) {
                if (spaceTimer < System.nanoTime()) {
                    if(currentCheatingProgress == 100){
                        hasCopiedAnswers = true;
                    }
                    sittingInChair = !sittingInChair;
                    spaceTimer = System.nanoTime() + 150000000;
                }
            }else if(players.get(0).position.distance(chairs.get(currentPersonToCheat).position)< 75){
                if (spaceTimer < System.nanoTime()) {
                    if(currentCheatingProgress <= 90) {
                        currentCheatingProgress += 10;
                    }
                    spaceTimer = System.nanoTime() + 500000000;
                    System.out.println(currentCheatingProgress);
                }
            }
        }


        if(sittingInChair){
            for (int i = elements.size() - 1; i >= 0; i--) {
                if(players.size() > 0) {
                    if (elements.get(i).position.distance(players.get(0).position) < 1000) {
                        elements.get(i).update(true);
                    }
                }
            }
            for (int i = npc.size() - 1; i >= 0; i--) {
                npc.get(i).update();
            }
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                projectiles.get(i).update();
            }
        }else {
            super.update();
        }
        emptyChair.update();
        for(Sprite table : tables){
            if(table.currentScene != null) {
                table.update();
            }else{
                table.currentScene = this;
                System.out.println("still not working if continues");
            }
        }
        for(Sprite chair : chairs) {
            if(chair.currentScene != null) {
                chair.update();
            }else{
                chair.currentScene = this;
                System.out.println("still not working if continues");
            }
        }
        if(sittingInChair){
            emptyChair.animationManager.run("full");
        }else {
            emptyChair.animationManager.run("empty");
        }
        mrDiamond.update();
    }

    public void render(){
        if(sittingInChair){
            for (int i = elements.size() - 1; i >= 0; i--) {
                if(players.size() > 0) {
                    if (elements.get(i).position.distance(players.get(0).position) < 1000) {
                        if (elements.get(i).position.distance(players.get(0).position) < 1000) {
                            elements.get(i).render();
                        }
                    }
                }
            }
            for (int i = npc.size() - 1; i >= 0; i--) {
                npc.get(i).render();
            }
            for (int i = projectiles.size() - 1; i >= 0; i--) {
                projectiles.get(i).render();
            }
        }else {
            super.render();
        }
        for(Sprite chair : chairs) {
            chair.render();
        }
        for(Sprite table : tables){
            table.render();
        }
        emptyChair.render();
        mrDiamond.render();
        arrow.render();
    }

    public void reset(){
        timesCheated = -1;
        hasCopiedAnswers = false;
        currentCheatingProgress = 0;
        currentPersonToCheat = -1;
        sittingInChair = true;
        players.get(0).position = new Vector3f();
        mrDiamond.position = new Vector3f(0, 1000, 0);
        projection.setPosition(new Vector3f());
    }
}