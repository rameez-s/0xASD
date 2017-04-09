package scenes;

import engine.objects.Scene;
import engine.objects.Sprite;

/**
 * Created by 18iwahlqvist on 4/9/2017.
 */
public class Hallway extends Scene{
    private Sprite doorTo1 = new Sprite(), doorTo2 = new Sprite(), doorTo3 = new Sprite(), doorTo4 = new Sprite();
    public Hallway(){
        setMap("hallway.png");
        genMap();
    }

    public void update(){

    }
}
