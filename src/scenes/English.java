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

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by Isak Wahlqvist on 4/26/2017.
 * At least I know that I can comment in multi-line comments and I do not have to create a separate comment Alex.
 */ // isak is idiot
public class English extends Scene {
    //Test
    private boolean hasCopiedAnswers;
    public boolean sittingInChair;
    MrDiamond mrDiamond;
    public EmptyChair emptyChair;
    ArrayList<Sprite> chairs = new ArrayList<>();
    ArrayList<Sprite> tables = new ArrayList<>();
    int currentPersonToCheat = -1;
    int currentCheatingProgress = 0;
    Sprite arrow;
    Sprite progressBar;
    int timesCheated = -1;
    Sprite instructions;
    public English(){
        instructionsShown = true;
        setMap("hallway.png");
        genMap();
        instructions = new Sprite(1024, 512, 0, 0.4345703125f, 0.2880859375f);
        instructions.setTexture("textSheet.png");
        instructions.currentScene = this;
        instructions.animationManager.textureCoord = new Vector2f(0.5f, 0.5f);
        instructions.animationManager.add(new Animation("idle", new Vector2f(0.5f, 0.5f),0, 0));
        instructions.animationManager.run("idle");
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
        progressBar = new Sprite(128, 0);
        progressBar.setTexture("npcSheet.png");
        progressBar.animationManager.textureCoord = new Vector2f(0.125f*2, 1-0.125f*2);
        progressBar.currentScene = this;
        emptyChair = new EmptyChair();
        emptyChair.currentScene = this;

        mrDiamond = new MrDiamond();
        mrDiamond.currentScene = this;
    }
    long spaceTimer = 0;

    public void update(){
        if(instructionsShown){
            if(glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_A) == GL_TRUE){
                instructions.position = new Vector3f(40000, 0, 0);
                instructionsShown = false;
                instructions.render();
            }
            instructions.update();
        }else {
            if ((currentCheatingProgress == 100 && hasCopiedAnswers) || currentPersonToCheat == -1) {
                currentPersonToCheat = (int) (Math.random() * 15);
                currentCheatingProgress = 0;
                hasCopiedAnswers = false;
                timesCheated++;
            }
            if (timesCheated > 4) {
                //Win
                Engine.instance.save.completedEnglish = true;
                Hallway.switchToScene(1);
            }
            if (currentCheatingProgress == 100) {
                arrow.position = new Vector3f(emptyChair.position.x - 10, emptyChair.position.y + 120, emptyChair.position.z);
                progressBar.position = new Vector3f(emptyChair.position.x + 25, emptyChair.position.y + 120, emptyChair.position.z);
                progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
            } else {
                arrow.position = new Vector3f(chairs.get(currentPersonToCheat).position.x - 10, chairs.get(currentPersonToCheat).position.y + 150, chairs.get(currentPersonToCheat).position.z);
                progressBar.position = new Vector3f(chairs.get(currentPersonToCheat).position.x + 25, chairs.get(currentPersonToCheat).position.y + 150, chairs.get(currentPersonToCheat).position.z);
            }
            arrow.update();
            progressBar.update();
            if (glfwGetKey(Engine.instance.getWindow(), GLFW_KEY_SPACE) == GL_TRUE) {
                if (players.get(0).position.distance(emptyChair.position) < 75) {
                    if (spaceTimer < System.nanoTime()) {
                        if (currentCheatingProgress == 100) {
                            hasCopiedAnswers = true;
                            progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
                        }
                        sittingInChair = !sittingInChair;
                        spaceTimer = System.nanoTime() + 150000000;
                    }
                } else if (players.get(0).position.distance(chairs.get(currentPersonToCheat).position) < 75) {
                    if (spaceTimer < System.nanoTime()) {
                        if (currentCheatingProgress <= 90) {
                            currentCheatingProgress += 10;

                            if(currentCheatingProgress < 10){
                                progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
                            }else if(currentCheatingProgress < 25){
                                progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f-0.125f);
                            }else if (currentCheatingProgress < 50){
                                progressBar.animationManager.textureCoord = new Vector2f(0.125f*1, 0.5f - 0.125f);
                            }else if (currentCheatingProgress < 70){
                                progressBar.animationManager.textureCoord = new Vector2f(0.125f*2, 0.5f - 0.125f);
                            }else if (currentCheatingProgress < 85){
                                progressBar.animationManager.textureCoord = new Vector2f(0.125f*3, 0.5f - 0.125f);
                            }
                        }
                        spaceTimer = System.nanoTime() + 500000000;
                        System.out.println(currentCheatingProgress);
                    }
                }
            }


            if (sittingInChair) {
                for (int i = elements.size() - 1; i >= 0; i--) {
                    if (players.size() > 0) {
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
            } else {
                super.update();
            }
            emptyChair.update();
            for (Sprite table : tables) {
                if (table.currentScene != null) {
                    table.update();
                } else {
                    table.currentScene = this;
                    System.out.println("still not working if continues");
                }
            }
            for (Sprite chair : chairs) {
                if (chair.currentScene != null) {
                    chair.update();
                } else {
                    chair.currentScene = this;
                    System.out.println("still not working if continues");
                }
            }
            if (sittingInChair) {
                emptyChair.animationManager.run("full");
            } else {
                emptyChair.animationManager.run("empty");
            }
            mrDiamond.update();
        }
    }

    public void render(){
        if(instructionsShown){
            instructions.render();
        }else {
            if (sittingInChair) {
                for (int i = elements.size() - 1; i >= 0; i--) {
                    if (players.size() > 0) {
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
            } else {
                super.render();
            }
            for (Sprite chair : chairs) {
                chair.render();
            }
            for (Sprite table : tables) {
                table.render();
            }
            emptyChair.render();
            mrDiamond.render();
            arrow.render();
            progressBar.render();
        }
    }

    public void reset(){
        progressBar.animationManager.textureCoord = new Vector2f(0, 0.5f);
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