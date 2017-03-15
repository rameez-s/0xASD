package example;

import engine.Engine;
import engine.objects.Scene;

/**
 * Created by 18iwahlqvist on 2/14/2017.
 */
public class ExampleScene extends Scene {
    long window = Engine.instance.getWindow();

    public ExampleScene(){
        setMap("ColorTest.png");
        genMap();
    }

    public void update() {
        super.update();
    }

    public void render(){
        super.render();
    }
}
