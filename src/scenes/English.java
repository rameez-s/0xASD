package scenes;

import engine.math.Vector2f;
import engine.objects.Scene;
import engine.objects.Sprite;
import objects.npcs.MrDiamond;

import java.util.ArrayList;

/**
 * Created by 18iwahlqvist on 4/26/2017.
 */
public class English extends Scene {
    //Test
    MrDiamond mrDiamond;
    ArrayList<Sprite> chairMain = new ArrayList<>();
    public English(){
        setMap("hallway.png");
        genMap();
        for (int i = 0; i < 10; i++) {
            chairMain.add(new Sprite(128, 0));
        }
        for(Sprite chair : chairMain) {
            chair.setTexture("characterSheet.png");
            chair.animationManager.textureCoord = new Vector2f(0.125f*(int)(Math.random() * 6), 1 - 0.125f);
            chair.currentScene = this;
            chair.position.set((int)(1000 * Math.random()-1000), (int)(1000 * Math.random()), 0);
        }

        mrDiamond = new MrDiamond();
        mrDiamond.currentScene = this;
    }

    public void update(){
        super.update();
        for(Sprite chair : chairMain) {
            chair.update();
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

