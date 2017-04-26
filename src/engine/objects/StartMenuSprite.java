package engine.objects;
import engine.Engine;
import engine.math.Matrix4f;
import engine.math.Vector2f;
import engine.math.Vector3f;
import engine.rendering.Quad;
import engine.rendering.Shader;
import engine.rendering.Texture;
import java.util.ArrayList;

/**
 * Created by 17aelbashir on 18-Apr-17.
 */
public class StartMenuSprite extends Sprite {
    public StartMenuSprite()
    {
        super(800, 1);
        texture = new Texture ("startMenu.png");
        position = new Vector3f(0, 0, 1);
    }
    //hey mr loomer
}
