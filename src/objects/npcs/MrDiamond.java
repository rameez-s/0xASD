package objects.npcs;

import engine.animation.Animation;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Sprite;
import objects.NPC;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 4/26/2017.
 */
public class MrDiamond extends NPC {
    ArrayList<Vector2f> positions = new ArrayList<>();
    private int atPosition = -1;
    private Vector2f sideA, sideB, sideC;
    char facing;
    Sprite visionCone, n1, n2;
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

        //Test
        positions.add(new Vector2f(0, 0));
        positions.add(new Vector2f(0, 1000));
        positions.add(new Vector2f(500, 1000));
        positions.add(new Vector2f(500, 0));
        positions.add(new Vector2f(0, 0));
        positions.add(new Vector2f(0, 1000));
        positions.add(new Vector2f(-500, 1000));
        positions.add(new Vector2f(-500, 0));
    }

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
        if(position.distance(currentScene.players.get(0).position) < 600){
            if(triangularCollision(currentScene.players.get(0))){
            }
        }
        n1.position.set(sideB.x, sideB.y, 0);
        n2.position.set(sideC.x, sideC.y, 0);
        if(positions.size() > atPosition + 1) {
            if (this.position.distance(positions.get(atPosition + 1)) > 0.1) {
                Vector2f v2 = position.pointTowards(positions.get(atPosition + 1));
                this.velocity = new Vector3f(v2.x * 2f, v2.y * 2f, velocity.z);
            }else{
                atPosition++;
                velocity.x = 0;velocity.y = 0;
            }
        }else{
            atPosition = -1;
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
