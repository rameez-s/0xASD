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
    //TestHejHej
    public RobertsOfficeScene(){
        setMap("robertsOffice.png");
        genMap();
        Sprite introPicture = new Sprite();
        introPicture.texture = new Texture("Roberts.png");
        introPicture.setTextureCoords(0.125f, 0);
    }
//w
    public void update() {
        super.update();
    }

    public void render(){
        super.render();
    }
}
