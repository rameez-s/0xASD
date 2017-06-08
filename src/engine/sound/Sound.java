package engine.sound;

/**
 * Created by Isak Wahlqvist on 4/30/2017.
 */
public class Sound {
    public int buffer = -1, source = -1;
    public int length = -1;
    public String fileName;

    public Sound(String fileName, int length){
        this.fileName = fileName;
        this.length = length;
    }

}
