package example;

import engine.Engine;
import engine.objects.Scene;

/**
 * Created by Isak Wahlqvist
 */
public class ExampleScene extends Scene {

    public ExampleScene(){
        super();
        setMap("robertsOffice.png");
        genMap();
    }

    public void update() {
        super.update();
    }

    public void render(){
        super.render();
    }
}
