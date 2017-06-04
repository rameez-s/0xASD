package scenes;

import engine.objects.Scene;
import engine.objects.*;
import engine.animation.*;
import engine.math.*;
import engine.rendering.*;


/**
 * Created by Isak Wahlqvist on 3/27/2017.
 */
public class RobertsOfficeScene extends Scene {
    Sprite door;
    public RobertsOfficeScene(){
        setMap("robertsOffice.png");
        genMap();
        door.currentScene = this;
        door.position = new Vector3f(0, 0, 0);
        door.setTexture("Door Spritesheet.png");
    }
//w
    public void update() {
        super.update();
    }

    public void render(){
        super.render();
    }
}
