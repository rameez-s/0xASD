package scenes;

import engine.objects.Scene;
import objects.npcs.MrDiamond;

/**
 * Created by 18iwahlqvist on 4/26/2017.
 */
public class English extends Scene {
    //Test
    MrDiamond mrDiamond;
    public English(){
        setMap("hallway.png");
        genMap();
        mrDiamond = new MrDiamond();
        mrDiamond.currentScene = this;
    }

    public void update(){
        super.update();
        mrDiamond.update();
    }

    public void render(){
        super.render();
        mrDiamond.render();
    }
}
