package engine.sound;

/**
 * Created by Isak Wahlqvist on 4/30/2017.
 */
public class Sound {
    public int buffer = -1, source = -1;
    public int length = -1;
    public static Sound hit, song;
    public String fileName;
    public static void init(){
        hit = new Sound("hit.ogg", 183000);
        song = new Sound("song.ogg", 183000);
        hit.length = 100;
        song.length = 5000;
    }
    public Sound(String fileName, int length){
        this.fileName = fileName;
        this.length = length;
    }

}
