package scenes;

import engine.math.Vector2f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.EmptyChair;
import objects.npcs.MrDiamond;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 4/26/2017.
 */
public class English extends Scene {
    //Test
    MrDiamond mrDiamond;
    EmptyChair emptyChair;
    ArrayList<Sprite> chairMain = new ArrayList<>();
    public English(){
        setMap("hallway.png");
        genMap();
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                chairMain.add(new Sprite(128, 0));
                chairMain.get((x*10)+y).setTexture("characterSheet.png");
                chairMain.get((x*10)+y).animationManager.textureCoord = new Vector2f(0.125f, 1 - 0.125f);
                chairMain.get((x*10)+y).position.set((-1000 + 200 * x), (0+(100*y)), 0);
                System.out.println(chairMain.get((x*10)+y).position + "\t" + ((x*10)+y));
            }
        }

        emptyChair = new EmptyChair();
        emptyChair.currentScene = this;

        mrDiamond = new MrDiamond();
        mrDiamond.currentScene = this;
    }

    public void update(){
        super.update();
        for(Sprite chair : chairMain) {
            if(chair.currentScene != null) {
                chair.update();
            }else{
                chair.currentScene = this;
                System.out.println("still not working if continues");
            }
        }
        mrDiamond.update();
    }

    public void render(){
        super.render();

        for(Sprite chair : chairMain) {
            chair.render();
        }
        mrDiamond.render();
    }
}

