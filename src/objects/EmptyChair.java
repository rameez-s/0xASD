package objects;

import engine.animation.Animation;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.objects.Sprite;

/**
 * Created by 18iwahlqvist on 5/8/2017.
 */
public class EmptyChair extends Sprite {

    public EmptyChair(){
        super(128, 0);

        position = new Vector3f(0, 0, 0);
        setTexture("characterSheet.png");
        animationManager.add(new Animation("empty", new Vector2f(0, 1-0.25f), 1f, 0));
        animationManager.add(new Animation("full", new Vector2f(0, 1-0.125f), 1f, 0));
        animationManager.run("empty");
    }

}
