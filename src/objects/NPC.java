package objects;

import engine.objects.Sprite;
import engine.rendering.Texture;

/**
 * Created by 18iwahlqvist on 3/27/2017.
 */
public class NPC extends Sprite {

    public NPC(){
        super();
        texture = new Texture("npcSheet.png");
    }

}
