package objects.npcs;

        import engine.animation.Animation;
        import engine.math.Vector2f;
        import engine.math.Vector3f;
        import engine.objects.Sprite;
        import objects.NPC;

        import java.util.ArrayList;

/**
 * Created by 17aperezcaceres on 5/8/2017.
 */
public class PeTeacher extends NPC {
    public PeTeacher () {
        super(128);
        setTexture("characterSheet.png");
    }

    public void update(){
        super.update();
        if(position.x < -900){
            velocity.set(10, 0, 0);
        }else{
            velocity.set(-10, 0, 0);
        }
    }
}
