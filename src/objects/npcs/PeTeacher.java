package objects.npcs;

        import engine.animation.Animation;
        import engine.math.Vector2f;
        import engine.math.Vector3f;
        import engine.objects.Sprite;
        import objects.NPC;
        import scenes.Gym;

        import java.util.ArrayList;

/**
 * Created by 17aperezcaceres on 5/8/2017.
 */
public class PeTeacher extends NPC {
    ArrayList<Sprite> balls = new ArrayList<>();
    public PeTeacher () {
        super(128);
        setTexture("characterSheet.png");
        position = new Vector3f((int) (Math.random() * 600 - 300), 900, 0);
        animationManager.textureCoord = new Vector2f(0.5f, 0);
        animationManager.add(new Animation("Run", new Vector2f(0.5f, 0), 0.2f, 2));
        velocity.set(10, 0, 0);
    }

    public long fireTimer = 0;
    public long reduce = 0;
    public void update(){
        super.update();
        for(int x = balls.size() - 1; x > 0; x--){
            if(balls.get(x).position.distance(currentScene.players.get(0).position) < 25){
                System.out.println("Hit");
                balls.remove(x);
                ((Gym)currentScene).lose();
                continue;
            }
            if(balls.get(x).position.y < -50){
                balls.remove(x);
                ((Gym)currentScene).currentProgress++;
                System.out.println(((Gym)currentScene).currentProgress);
                continue;
            }
            balls.get(x).update();
        }
        animationManager.run("Run");
        if(position.x < -550){
            velocity.set(10 + (int)(Math.random() * 6 - 3), 0, 0);
        }else if(position.x > 400){
            velocity.set(-10 + (int)(Math.random() * 6 - 3), 0, 0);
        }
        if(System.nanoTime() > fireTimer){
            balls.add(new Sprite(64,0));
            balls.get(balls.size() - 1).setTexture("characterSheet.png");
            balls.get(balls.size() - 1).animationManager.textureCoord = new Vector2f(0.5f - 0.125f, 1 - 0.25f);
            balls.get(balls.size() - 1).position = new Vector3f(position.x, position.y, position.z);
            balls.get(balls.size() - 1).currentScene = this.currentScene;
            balls.get(balls.size() - 1).velocity = new Vector3f((float)(Math.random() * 8 - 4), -10, 0);
            fireTimer = System.nanoTime() + 900000000 - reduce;
            if(reduce  < 600000000) {
                reduce += (long)(Math.random() * 10000000.0f);
            }
        }
    }

    public void render(){
        super.render();
        for(Sprite ball : balls){
            ball.render();
        }
    }
}
