package objects.npcs;

import engine.animation.Animation;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Sprite;
import objects.NPC;
import objects.Path;
import objects.PathPoint;
import scenes.English;

import java.util.ArrayList;

/**
 * Created by Isak Wahlqvist on 4/26/2017.
 */
public class MrDiamond extends NPC {
    ArrayList<PathPoint> positions = new ArrayList<>();
    private Vector2f sideA, sideB, sideC;
    char facing;
    Sprite visionCone, n1, n2;
    Path p;

    float speedMultiplier = 2f;
    public MrDiamond(){
        super(128);
        setTexture("characterSheet.png");
        animationManager.textureCoord = new Vector2f(0, 0.5f);
        visionCone = new Sprite(512, 0);
        n1 = new Sprite(16, 0);
        n2 = new Sprite(16, 0);
        visionCone.setTexture("npcSheet.png");
        visionCone.animationManager.textureCoord = new Vector2f(0.125f, 0f);
        visionCone.animationManager.add(new Animation("Up", new Vector2f(0.125f, 0f), 1f, 0));
        visionCone.animationManager.add(new Animation("Left", new Vector2f(0.125f*2, 0f), 1f, 0));
        visionCone.animationManager.add(new Animation("Right", new Vector2f(0.125f*3, 0f), 1f, 0));
        visionCone.animationManager.add(new Animation("Down", new Vector2f(0.125f*4, 0f), 1f, 0));
        animationManager.add(new Animation("Down", new Vector2f(0, 0.375f), 0.2f, 2));
        animationManager.add(new Animation("Up", new Vector2f((0.125f * 1), 0.375f), 0.2f, 2));
        animationManager.add(new Animation("Left", new Vector2f((0.125f * 2), 0.375f), 0.2f, 1));
        animationManager.add(new Animation("Right", new Vector2f((0.125f * 3), 0.375f), 0.2f, 1));

        p = new Path();
    }

    boolean waitingToCalmDown = false;
    long waitTimer = -1;
    public void update(){
        if(visionCone.currentScene == null){
            visionCone.currentScene = currentScene;
            n1.currentScene = currentScene;
            n2.currentScene = currentScene;
        }
        if((Math.abs(velocity.y) - Math.abs(velocity.x)) > 0){
            if(velocity.y > 0){
                facing = 'U';
            }else{
                facing = 'D';
            }
        }else{
            if(velocity.x > 0){
                facing = 'R';
            }else{
                facing = 'L';
            }
        }
        super.update();
        sideA = new Vector2f(position.x, position.y);

        switch (facing){
            case 'U':
                sideB = new Vector2f(sideA.x - 225, position.y + (int)(500.0 * (16/9.0)));
                sideC = new Vector2f(sideA.x + 225, position.y + (int)(500.0 * (16/9.0)));
                animationManager.run("Up");
                visionCone.animationManager.run("Up");
                break;
            case 'D':
                sideB = new Vector2f(sideA.x - 225, position.y - (int)(500.0 * (16/9.0)));
                sideC = new Vector2f(sideA.x + 225, position.y - (int)(500.0 * (16/9.0)));
                animationManager.run("Down");
                visionCone.animationManager.run("Down");
                break;
            case 'L':
                sideB = new Vector2f(sideA.x - 500, position.y - (int)(225.0 * (16/9.0)));
                sideC = new Vector2f(sideA.x - 500, position.y + (int)(225.0 * (16/9.0)));
                animationManager.run("Left");
                visionCone.animationManager.run("Left");
                break;
            case 'R':
                sideB = new Vector2f(sideA.x + 500, position.y - (int)(225.0 * (16/9.0)));
                sideC = new Vector2f(sideA.x + 500, position.y + (int)(225.0 * (16/9.0)));
                animationManager.run("Right");
                visionCone.animationManager.run("Right");
                break;
            default:
                sideB = new Vector2f(sideA.x - 225, position.y + (int)(500.0 * (16/9.0)));
                sideC = new Vector2f(sideA.x + 225, position.y + (int)(500.0 * (16/9.0)));
                break;
        }
        Vector2f v = new Vector2f((sideA.x + sideB.x + sideC.x)/3, (sideA.y + sideB.y + sideC.y)/3);
        visionCone.position = new Vector3f((sideB.x*0.25f + sideC.x*0.25f + sideA.x*0.5f), (sideB.y*0.25f + sideC.y*0.25f + sideA.y*0.5f), 0);
        visionCone.update();
        Vector3f v3 = new Vector3f(v.x, v.y, 0);
        if(!((English)currentScene).sittingInChair)
        if(v3. distance(((English) currentScene).emptyChair.position) < 200){
            System.out.println("Sees Chair");
            speedMultiplier = 4;
            waitingToCalmDown = true;
        }

        if(waitingToCalmDown){
            if(waitTimer == -1){
                waitTimer = System.nanoTime() + 10000000000L;
            }else{
                if(System.nanoTime() > waitTimer){
                    speedMultiplier = 2;
                    waitingToCalmDown = false;
                    waitTimer = -1;
                }
            }
        }
        if(v3.distance(currentScene.players.get(0).position) < 600){
            if(triangularCollision(currentScene.players.get(0))){
                //Collision with player
                if(((English)currentScene).sittingInChair){

                }else{
                    ((English) currentScene).reset();
                }
            }
        }
        n1.position.set(sideB.x, sideB.y, 0);
        n2.position.set(sideC.x, sideC.y, 0);
        if(p.current != null) {
            if (this.position.distance(p.current.position) > 0.1) {
                Vector2f v2 = position.pointTowards(new Vector2f(p.current.position.x, p.current.position.y));
                this.velocity = new Vector3f(v2.x * speedMultiplier, v2.y * speedMultiplier, velocity.z);
            }else{
                p.chooseNextPath();
                velocity.x = 0;velocity.y = 0;
            }
        }else{
            new Exception("Current path point is null");
        }
        n1.update();
        n2.update();

    }


    public void render(){
        visionCone.render();
        n1.render();
        n2.render();
        super.render();
    }
    private boolean triangularCollision(Sprite other){
        Vector2f p = new Vector2f(other.position.x, other.position.y);
        Vector2f v0 = Vector2f.subtract(sideC, sideA), v1 = Vector2f.subtract(sideB, sideA), v2 = Vector2f.subtract(p, sideA);
        float dotAA = dot(v0, v0);
        float dotAB = dot(v0, v1);
        float dotAC = dot(v0, v2);
        float dotBB = dot(v1, v1);
        float dotBC = dot(v1, v2);
        float invDenom = 1/(dotAA * dotBB - dotAB * dotAB);
        float u = (dotBB * dotAC - dotAB * dotBC) * invDenom;
        float v = (dotAA * dotBC - dotAB * dotAC) * invDenom;
        boolean check = (u >= 0) && (v >= 0) && (u + v < 1);
        return check;
    }

    private float dot(Vector2f a, Vector2f b){
        return a.x*b.x + a.y*b.y;
    }

}
